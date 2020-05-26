package Geometry3D.Interfaces;

import Geometry3D.Point3D;

/**
 * The interface Rotation 3 d.
 */
public interface IRotation3D
{

    /**
     * Transform around z.
     *
     * @param alfa the alfa
     */
    void transformAroundZ(double alfa);

    /**
     * Transform around x.
     *
     * @param alfa the alfa
     */
    void transformAroundX(double alfa);

    /**
     * Transform around y.
     *
     * @param alfa the alfa
     */
    void transformAroundY(double alfa);

    /**
     * Add transform around z.
     *
     * @param alfa the alfa
     */
    void addTransformAroundZ(double alfa);

    /**
     * Add transform around x.
     *
     * @param alfa the alfa
     */
    void addTransformAroundX(double alfa);

    /**
     * Add transform around y.
     *
     * @param alfa the alfa
     */
    void addTransformAroundY(double alfa);

    /**
     * Rotation x.
     *
     * @param alfa   the alfa
     * @param center the center
     */
    void RotationX(double alfa, Point3D center);

    /**
     * Rotation y.
     *
     * @param alfa   the alfa
     * @param center the center
     */
    void RotationY(double alfa, Point3D center);

    /**
     * Rotation z.
     *
     * @param alfa   the alfa
     * @param center the center
     */
    void RotationZ(double alfa, Point3D center);

}
