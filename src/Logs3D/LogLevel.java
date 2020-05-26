package Logs3D;

import java.util.logging.Level;

/**
 * experimental3D
 *
 * @author Jan Frantisek Sedlacek
 * @version 1.0
 * @since 10.04.20
 */
public class LogLevel extends Level
{

    private static String color;

    /**
     * Gets color.
     *
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * Instantiates a new Log level.
     *
     * @param name  the name
     * @param value the value
     * @param color the color
     */
    protected LogLevel(String name, int value, String color)
     {
    super(name, value);
    this.color=color;
}



}
