package com.tms.Main.enumData;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TaxType {
    IGST("IGST"), CGST("CGST"), SGST_UTGST("SGST/UTGST");

    private final String label;
    TaxType(String label) { this.label = label; }

    @JsonValue
    public String getLabel() { return label; }

    @JsonCreator
    public static TaxType fromLabel(String value) {
        for (TaxType t : values())
            if (t.label.equalsIgnoreCase(value) || t.name().equalsIgnoreCase(value)) return t;
        throw new IllegalArgumentException("Invalid taxType. Allowed: IGST, CGST, SGST/UTGST");
    }
}