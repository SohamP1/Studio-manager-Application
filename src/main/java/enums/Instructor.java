package enums;

/**
 * Enumerates the instructors available in the fitness club system. Each instructor is represented with their name.
 *
 * @author Soham Patel
 */
public enum Instructor {
    /**
     * Represents Jennifer, an instructor.
     */
    JENNIFER("Jennifer"),

    /**
     * Represents Kim, an instructor.
     */
    KIM("Kim"),

    /**
     * Represents Denise, an instructor.
     */
    DENISE("Denise"),

    /**
     * Represents Davis, an instructor.
     */
    DAVIS("Davis"),

    /**
     * Represents Emma, an instructor.
     */
    EMMA("Emma");

    /**
     * The name of the instructor
     */
    private final String name;

    /**
     * Constructs an Instructor instance with the specified name.
     *
     * @param name The name of the instructor.
     */
    Instructor(String name) {
        this.name = name;
    }
}
