package app.enums;

import java.util.Arrays;

public enum Recommendation {
    VACCINATION("Vaccination"),
    TESTING("Testing"),
    QUARANTINE("Quarantine"),
    BIOSECURITY("Biosecurity Measures"),
    HYGIENE("Hygienic Practices"),
    REST("Rest"),
    MINIMIZE_CONTACT("Minimize Contact"),
    CULLING("Culling"),
    TICK_PREVENTION("Tick Prevention"),
    MOSQUITO_CONTROL("Mosquito Control"),
    PROPER_NUTRITION("Proper Nutrition"),
    STRESS_REDUCTION("Stress Reduction"),
    SANITATION("Sanitation"),
    EARLY_DETECTION("Early Detection"),
    REDUCE_STANDING_WATER("Reduce Standing Water");

    private final String description;

    Recommendation(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static String[] getAllDisplayNames() {
        return Arrays.stream(values())
                .map(Recommendation::getDescription)
                .toArray(String[]::new);
    }
}
