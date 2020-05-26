package Geometry3D.Interfaces;

import Geometry3D.Point3D;

/**
 * The interface Move 3 d.
 */
public interface IMove3D {

    /**
     * Move x.
     *
     * @param X the x
     */
    void moveX(double X);

    /**
     * Move y.
     *
     * @param Y the y
     */
    void moveY(double Y);

    /**
     * Move z.
     *
     * @param Z the z
     */
    void moveZ(double Z);

    /**
     * Gets center.
     *
     * @return the center
     */
    Point3D getCenter();

}
