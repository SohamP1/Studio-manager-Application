package enums;

/**
 * Enum representing various locations for the fitness club system.
 * Each enum constant corresponds to a specific location, including city name, ZIP code, and county.
 *
 * @author Soham Patel
 */
public enum Location {

    /**
     * Bridgewater's location, ZIP code 08807, Somerset County.
     */
    BRIDGEWATER("Bridgewater", "08807", "Somerset"),

    /**
     * Edison's location, ZIP code 08837, Middlesex County.
     */
    EDISON("Edison", "08837", "Middlesex"),

    /**
     * Franklin location, ZIP code 08873, Somerset County.
     */
    FRANKLIN("Franklin", "08873", "Somerset"),

    /**
     * Piscataway's location, ZIP code 08854, Middlesex County.
     */
    PISCATAWAY("Piscataway", "08854", "Middlesex"),

    /**
     * Somerville's location, ZIP code 08876, Somerset County.
     */
    SOMERVILLE("Somerville", "08876", "Somerset");

    /**
     * City name of the location
     */
    private final String city;

    /**
     * IP code of the location
     */
    private final String zipCode;

    /**
     * County name of the location
     */
    private final String county;

    /**
     * Constructor for enum constants.
     *
     * @param city    The city name of the location.
     * @param zipCode The ZIP code of the location.
     * @param county  The county name of the location.
     */
    Location(String city, String zipCode, String county) {
        this.city = city;
        this.zipCode = zipCode;
        this.county = county;
    }

    /**
     * Returns the city name of the location.
     *
     * @return The city name.
     */
    public String getCity() {
        return city;
    }

    /**
     * Returns the ZIP code of the location.
     *
     * @return The ZIP code.
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * Returns the county name of the location.
     *
     * @return The county name.
     */
    public String getCounty() {
        return county;
    }

    /**
     * Provides a string representation of the location in the format: "CITY, ZIPCODE, COUNTY".
     *
     * @return A string representation of the location.
     */
    @Override
    public String toString() {
        return city.toUpperCase() + ", " + zipCode + ", " + county.toUpperCase();
    }
}
