package com.tms.Main.Service.impl;

import com.tms.Main.Model.CompanyProfiles;
import com.tms.Main.Model.GlobalGroup;
import com.tms.Main.Model.Group;
import com.tms.Main.Repository.GroupRepository;
import com.tms.Main.enumData.GroupType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupInitializationService {

    private final GroupRepository groupRepository;
    private final GlobalGroupServiceImpl globalGroupCacheService; // wraps the @Cacheable lookup

    @Transactional
    public void createGroup(CompanyProfiles customer) {

        GlobalGroup ggAssets = globalGroupCacheService.findByIdCached(1L);
        GlobalGroup ggLiabilities = globalGroupCacheService.findByIdCached(2L);
        GlobalGroup ggIncome = globalGroupCacheService.findByIdCached(3L);
        GlobalGroup ggExpenses = globalGroupCacheService.findByIdCached(4L);
        GlobalGroup ggEquity = globalGroupCacheService.findByIdCached(5L);

        List<Group> pending = new ArrayList<>();

        Group assets = build(customer, ggAssets, "Assets", null, GroupType.G);
        Group currentAssets = build(customer, ggAssets, "Current Assets", assets, GroupType.P);
        pending.add(assets);
        pending.add(currentAssets);
        pending.add(build(customer, ggAssets, "Bank Accounts", currentAssets, GroupType.B));
        pending.add(build(customer, ggAssets, "Cash In Hand", currentAssets, GroupType.C));
        pending.add(build(customer, ggAssets, "Sundry Debtors", currentAssets, GroupType.PS));
        pending.add(build(customer, ggAssets, "Fixed Assets", assets, GroupType.P));

        Group liabilities = build(customer, ggLiabilities, "Liabilities", null, GroupType.G);
        Group currentLiabilities = build(customer, ggLiabilities, "Current Liabilities", liabilities, GroupType.P);
        pending.add(liabilities);
        pending.add(currentLiabilities);
        pending.add(build(customer, ggLiabilities, "Sundry Creditors", currentLiabilities, GroupType.PS));
        pending.add(build(customer, ggLiabilities, "Duties & Taxes", currentLiabilities, GroupType.E));

        pending.add(build(customer, ggEquity, "Equity", null, GroupType.G));

        Group expenses = build(customer, ggExpenses, "Expenses", null, GroupType.G);
        pending.add(expenses);
        pending.add(build(customer, ggExpenses, "Direct Expenses", expenses, GroupType.E));
        pending.add(build(customer, ggExpenses, "Purchase Accounts", expenses, GroupType.E));

        Group income = build(customer, ggIncome, "Income", null, GroupType.G);
        pending.add(income);
        pending.add(build(customer, ggIncome, "Direct Incomes", income, GroupType.I));
        pending.add(build(customer, ggIncome, "Sales Accounts", income, GroupType.I));

        groupRepository.saveAll(pending); // ✅ now actually batchable with SEQUENCE strategy
    }

    private Group build(CompanyProfiles customer, GlobalGroup globalGroup, String name,
                        Group parent, GroupType type) {
        Group group = new Group();
        group.setCompanyProfile(customer);
        group.setGlobalGroup(globalGroup);
        group.setGroupName(name);
        group.setParentGroup(parent); // works because SEQUENCE assigns parent.groupId in memory immediately
        group.setGroupType(type);
        return group;
    }
}