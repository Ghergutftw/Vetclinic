package app.enums;

import java.util.Arrays;

public enum Diagnosis {
    PARVOVIRUS("Parvovirus"),
    RABIES("Rabies"),
    FELINE_LEUKEMIA("Feline Leukemia Virus"),
    CANINE_DISTEMPER("Canine Distemper"),
    BRUCELLOSIS("Brucellosis"),
    FOOT_AND_MOUTH("Foot-and-Mouth Disease"),
    EQUINE_INFLUENZA("Equine Influenza"),
    AVIAN_INFLUENZA("Avian Influenza"),
    MASTITIS("Mastitis"),
    SALMONELLA("Salmonella"),
    COCCIDIOSIS("Coccidiosis"),
    EPM("Equine Protozoal Myeloencephalitis"),
    BOVINE_RESPIRATORY_DISEASE("Bovine Respiratory Disease"),
    LYME_DISEASE("Lyme Disease"),
    SCABIES("Scabies"),
    WEST_NILE_VIRUS("West Nile Virus");

    private final String displayName;

    Diagnosis(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static String[] getAllDisplayNames() {
        return Arrays.stream(values())
                .map(Diagnosis::getDisplayName)
                .toArray(String[]::new);
    }
}
