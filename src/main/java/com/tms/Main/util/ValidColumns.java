package com.tms.Main.util;

import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@Component
public final class ValidColumns {


    // Ledger Entity
    public static final Set<String> LEDGER_COLUMNS = Set.of(
            "ledgerId",
            "companyId",
            "groupId",
            "ledgerName",
            "openingBalance",
            "isBillByBill",
            "mailingName",
            "addressLine1",
            "addressLine2",
            "state",
            "country",
            "pinCode",
            "mobileNo",
            "emailId",
            "gstNo",
            "panNo",
            "bankAcNo",
            "ifscCode",
            "bankName",
            "bankAcHolderName",
            "branchName",
            "createdAt",
            "updatedAt"
    );
    private static final Set<String> VEHICLE_COLUMNS = Set.of(
            "vehicleId", "companyId", "vehicleNo", "vehicleType",
            "ownerLedgerId", "createdAt", "updatedAt"
    );
    // Group Entity
    public static final Set<String> GROUP_COLUMNS = Set.of(
            "groupId",
            "globalGroup",
            "companyProfile",
            "parentGroup",
            "groupName",
            "groupType",
            "createdAt",
            "updatedAt"
    );

    // IndianCities Entity
    public static final Set<String> INDIAN_CITIES_COLUMNS = Set.of(
            "id",
            "cityName",
            "districtName",
            "stateName"
    );
    public Set<String> resolveFields(List<String> select,String columns) {
        if (select == null || select.isEmpty())
            return Set.of(); // empty = all fields

        Set<String> allowed;
        switch (columns)
        {
            case "INDIAN_CITIES_COLUMNS":
                allowed = INDIAN_CITIES_COLUMNS;
                break;
            case "LEDGER_COLUMNS":
                allowed = LEDGER_COLUMNS;
                break;
            case "GROUP_COLUMNS":
                allowed = GROUP_COLUMNS;
                break;
            case  "VEHICLE_COLUMNS":
                allowed = VEHICLE_COLUMNS;
                break;
            default:
                throw new IllegalArgumentException("Invalid columns name: " + columns);
        }


        Set<String> requested = select.stream().map(s -> Character.toLowerCase(s.charAt(0)) + s.substring(1)) // GroupName
                // →
                // groupName
                .filter(allowed::contains).collect(Collectors.toCollection(LinkedHashSet::new));

        if (requested.isEmpty())
            throw new IllegalArgumentException("No valid fields in 'select'. Allowed: " + allowed);

        return requested;
    }

}