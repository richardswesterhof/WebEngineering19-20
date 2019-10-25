package nl.rug.shamzam.Utils;

/**
 * Class that has methods for taking objects that might be null,
 * and returning a 'default' value if they are null
 */
public class Unnullifier {

    public static String unnullify(String input) {
        return input == null ? "" : input;
    }

    public static Integer unnullify(Integer input) {
        return input == null ? new Integer(0) : input;
    }

    public static Float unnullify(Float input) {
        return input == null ? new Float(0.0) : input;
    }
}
