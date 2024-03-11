package junittest;

import data.Date;
import org.testng.annotations.Test;

import static org.junit.Assert.*;
/**
 * Unit tests for the Date class to validate the correctness of date validations.
 * These tests cover various scenarios including invalid years, months, days, and
 * also validate proper handling of leap years and future dates.
 *
 * @author Soham Patel
 */
public class DateTest {

    /**
     * test method to check if the date is valid.
     * date with year 800.
     * accepted output - false
     * actual output - false
     */
    @Test
    public void yearLessthan1900() {
        Date date = new Date("11/21/800");
        assertFalse(date.isValid());
    }

    /**
     * test method to check date if date is valid.
     * Date with month less than 1
     * accepted output - false
     * actual output - false
     */
    @Test
    public void monthLessthan1() {
        Date date = new Date("0/21/2000");
        assertFalse(date.isValid());
    }

    /**
     * test method to check date if date is valid.
     * Date with month grater than 12.
     * accepted output - false
     * actual output - false
     */
    @Test
    public void monthGreaterthan12() {
        Date date = new Date("13/21/2000");
        assertFalse(date.isValid());
    }

    /**
     * test method to check date if date is valid.
     * Date with non-leap year which has feb month day grater than 28.
     * accepted output - false
     * actual output - false
     */
    @Test
    public void testDaysInFeb_Nonleap() {
        Date date = new Date("2/29/2011");
        assertFalse(date.isValid());
    }


    /**
     * test method to check date if date is valid.
     * future date.
     * accepted output - false
     * actual output - false
     */
    @Test
    public void futureYear() {
        Date date = new Date("2/30/2025");
        assertFalse(date.isValid());
    }


    /**
     * test method to check date if date is valid.
     * valid date with 12/31/2018.
     * accepted output - true
     * actual output - true
     */
    @Test
    public void validDate1() {
        Date date = new Date("12/31/2018");
        assertTrue(date.isValid());
    }


    /**
     * test method to check date if date is valid.
     * valid date with 11/15/2005.
     * accepted output - true
     * actual output - ture
     */
    @Test
    public void validDate2() {
        Date date = new Date("11/15/2005");
        assertTrue(date.isValid());
    }

}