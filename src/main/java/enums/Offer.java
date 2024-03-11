package enums;

/**
 * Enum representing different fitness class offers available in the gym system.
 * Each enum constant corresponds to a specific class type.
 *
 * @author Soham Patel
 */
public enum Offer {
    /** Pilates classes focus on strengthening the body's core through controlled movements and breathing. */
    PILATES("Pilates"),

    /** Spinning classes involve stationary cycling to high-intensity music, aiming to improve cardiovascular health. */
    SPINNING("Spinning"),

    /** Cardio classes are high-energy workouts intended to increase heart rate and promote cardiovascular health. */
    CARDIO("Cardio");

    /** Name of the fitness class */
    private final String className;

    /**
     * Constructor for enum constants.
     *
     * @param className The name of the fitness class.
     */
    Offer(String className) {
        this.className = className;
    }

    /**
     * Returns the name of the fitness class.
     *
     * @return The class name.
     */
    public String getClassName() {
        return className;
    }
}
