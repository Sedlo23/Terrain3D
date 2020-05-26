package Geometry3D.Interfaces;

import Geometry3D.Point3D;

import java.awt.*;

/**
 * The interface Draw 3 d.
 */
public interface IDraw3D
{

    /**
     * Draw.
     *
     * @param g      the g
     * @param camPos the cam pos
     */
    void draw(Graphics2D g, Point3D camPos);

    /**
     * Fill.
     *
     * @param graphics2D the graphics 2 d
     * @param camPos     the cam pos
     */
    void fill(Graphics2D graphics2D, Point3D camPos);

    /**
     * Fill.
     *
     * @param graphics2D the graphics 2 d
     * @param camPos     the cam pos
     */
    void fillClassic(Graphics2D graphics2D, Point3D camPos);

    /**
     * Update.
     *
     * @param graphics2D the graphics 2 d
     * @param camPos     the cam pos
     */
    void update(Graphics2D graphics2D, Point3D camPos);

    /**
     * Sets color.
     *
     * @param color the color
     */
    void setColor(Color color);

    /**
     * Gets normal.
     *
     * @return the normal
     */
    Point3D getNormal();

    Object copy();


}
