package Geometry3D;

import Geometry3D.Interfaces.IDraw3D;
import Geometry3D.Interfaces.IMove3D;
import Geometry3D.Interfaces.IRotation3D;

import java.awt.*;

/**
 * The type Shape 3 d.
 */
public abstract class  Shape3D implements IDraw3D, IRotation3D, IMove3D
{



    private boolean updated=true;

    private Color color;

    private Color shadedColor;

    private double lightPower;

    private double shadePower;

    /**
     * Instantiates a new Shape 3 d.
     */
    public Shape3D()
    {

        color=Color.black;
        setShadedColor(Color.black);
        setShadePower(1);
        setLightPower(5000);

    }

    /**
     * Gets color.
     *
     * @return the color
     */
    public Color getColor() {
        return getShadedColor();
    }


    /**
     * Update shade color.
     *
     * @param lightSource the light source
     */
    public void updateShadeColor(Point3D lightSource)
    {

        Point3D center= getCenter();

        setShadePower(1/Math.pow(2,(center.distanceRelative(lightSource)/ getLightPower())));
        setShadePower(getShadePower() * ((angle(new Line3D(lightSource,center)))/180));
        updateShadeColor();

    }

    /**
     * Dot double.
     *
     * @param shape the shape
     * @return the double
     */
    double dot(Shape3D shape)
    {
        Point3D a=getNormal();
        Point3D b=shape.getNormal();

        return a.getX() *b.getX()+ a.getY() * b.getY() + a.getZ() * b.getZ();
    }

    /**
     * Mag double.
     *
     * @return the double
     */
    double mag()
    {
        Point3D a=getNormal();
        return Math.sqrt(a.getX() * a.getX() + a.getY() * a.getY() + a.getZ() * a.getZ());
    }


    /**
     * Angle double.
     *
     * @param b the b
     * @return the double
     */
    public  double angle(Shape3D b)
    {

        if (Double.isNaN(Math.toDegrees(Math.acos(dot(b)/(this.mag()*b.mag())))))
            return 180;

        return  Math.toDegrees(Math.acos(dot(b)/(this.mag()*b.mag())));
    }


    /**
     * Update shade color.
     */
    public void updateShadeColor()
    {

        setShadedColor(new Color((int)(color.getRed()* getShadePower()),(int)(color.getGreen()* getShadePower()),(int)(color.getBlue()* getShadePower())));

    }

    /**
     * Gets shaded color.
     *
     * @return the shaded color
     */
    public Color getShadedColor() {
        return shadedColor;
    }

    /**
     * Gets light power.
     *
     * @return the light power
     */
    public double getLightPower() {
        return lightPower;
    }

    /**
     * Sets light power.
     *
     * @param lightPower the light power
     */
    public void setLightPower(double lightPower) {
        this.lightPower = lightPower;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
        updateShadeColor(getCenter());

    }

    /**
     * Sets shaded color.
     *
     * @param shadedColor the shaded color
     */
    public void setShadedColor(Color shadedColor) {
        this.shadedColor = shadedColor;
    }

    /**
     * Gets shade power.
     *
     * @return the shade power
     */
    public double getShadePower() {
        return shadePower;
    }

    /**
     * Sets shade power.
     *
     * @param shadePower the shade power
     */
    public void setShadePower(double shadePower) {
        this.shadePower = shadePower;
    }

    /**
     * Is updated boolean.
     *
     * @return the boolean
     */
    public boolean isUpdated() {
        return updated;
    }

    /**
     * Sets updated.
     *
     * @param updated the updated
     */
    public void setUpdated(boolean updated) {
        this.updated = updated;
    }


}

