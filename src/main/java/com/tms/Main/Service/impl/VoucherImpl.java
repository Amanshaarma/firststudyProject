package com.tms.Main.Service.impl;

import com.tms.Main.Dto.VoucherEntryRequest;
import com.tms.Main.Dto.VoucherRequest;
import com.tms.Main.Model.VoucherEntries;
import com.tms.Main.Model.Vouchers;
import com.tms.Main.Repository.VoucherEntriesRepository;
import com.tms.Main.Repository.VouchersRepository;
import com.tms.Main.Service.VoucherCounterService;
import com.tms.Main.Service.VoucherService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VoucherImpl implements VoucherService {
    private final VouchersRepository vouchersRepository;
    private final VoucherEntriesRepository voucherEntriesRepository;
    private final VoucherCounterService voucherCounterService;

    @Transactional
    public Long createSalesVoucher(VoucherRequest request) {

        return createVoucher(request);
    }

    @Transactional
    public Long createPurchaseVoucher(VoucherRequest request) {

        return createVoucher(request);
    }

    private Long createVoucher(VoucherRequest request) {

        validateVoucher(request);
        LocalDate date = request.getVoucherDate();
        int year = date.getYear();
        String fiscalYear = year + "-" + String.format("%02d", (year + 1) % 100);
        Long voucherNo =
                voucherCounterService.nextVoucherNumber(
                        request.getCompanyId(),
                        fiscalYear,
                        request.getVoucherTypeId()
                );

        Vouchers voucher = Vouchers.builder()
                .companyId(request.getCompanyId())
                .voucherTypeId(request.getVoucherTypeId())
                .voucherNo(voucherNo)
                .voucherDate(request.getVoucherDate())
                .fiscalYear(request.getFiscalYear())
                .narration(request.getNarration())
                .sourceType(request.getSourceType())
                .voucherMode(request.getVoucherMode())
                .build();

        voucher = vouchersRepository.save(voucher);

        final Long voucherId = voucher.getVoucherId();

        List<VoucherEntries> entries =
                request.getVoucherEntries()
                        .stream()
                        .map(entry -> VoucherEntries.builder()
                                .voucherId(voucherId)
                                .ledgerId(entry.getLedgerId())
                                .amount(entry.getAmount().abs())
                                .description(entry.getDescription())
                                .companyId(request.getCompanyId())
                                .build())
                        .toList();

        voucherEntriesRepository.saveAll(entries);


        return voucherId;
    }

    private BigDecimal calculateDebit(
            List<VoucherEntryRequest> entries) {

        return entries.stream()
                .filter(entry -> entry.getAmount().signum() > 0)
                .map(VoucherEntryRequest::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateCredit(
            List<VoucherEntryRequest> entries) {

        return entries.stream()
                .filter(entry -> entry.getAmount().signum() < 0)
                .map(entry -> entry.getAmount().abs())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void validateBalancedVoucher(
            BigDecimal debit,
            BigDecimal credit) {

        if (debit.compareTo(credit) != 0) {
            throw new IllegalArgumentException(
                    "Voucher is not balanced. Debit: "
                            + debit
                            + ", Credit: "
                            + credit
            );
        }
    }

    private void validateVoucher(VoucherRequest request) {

        if (request.getVoucherEntries() == null
                || request.getVoucherEntries().isEmpty()) {

            throw new IllegalArgumentException(
                    "Voucher must contain at least one entry"
            );
        }
    }
}