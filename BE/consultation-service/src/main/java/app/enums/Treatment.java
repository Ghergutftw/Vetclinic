package app.enums;

import java.util.Arrays;

public enum Treatment {
    FLUID_THERAPY("Fluid Therapy"),
    ANTI_NAUSEA_MEDICATION("Anti-Nausea Medication"),
    ANTIBIOTICS("Antibiotics"),
    ANTIVIRAL_MEDICATION("Antiviral Medication"),
    SUPPORTIVE_CARE("Supportive Care"),
    ANTI_INFLAMMATORY_MEDICATION("Anti-Inflammatory Medication"),
    COCCIDIOSTAT_MEDICATION("Coccidiostat Medication"),
    ACARICIDES("Acaricides"),
    MEDICATED_SHAMPOOS("Medicated Shampoos");

    private final String procedure;

    Treatment(String procedure) {
        this.procedure = procedure;
    }

    public String getProcedure() {
        return procedure;
    }

    public static String[] getAllDisplayNames() {
        return Arrays.stream(values())
                .map(Treatment::getProcedure)
                .toArray(String[]::new);
    }
}