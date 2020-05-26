package Geometry3D;

import Geometry3D.Interfaces.IRotation3D;

/**
 * The type Point 3 d.
 */
public class Point3D implements IRotation3D {



    /**
     *
     */
    private double x;

    /**
     *
     */
    private double y;

    /**
     *
     */
    private double z;

    /**
     *
     */
    private double positionX;

    /**
     *
     */
    private double positionY;

    /**
     *
     */
    private double positionZ;

    /**
     *
     */
    private double transX=0;

    /**
     *
     */
    private double transY=0;

    /**
     *
     */
    private double transZ=0;

    /**
     *
     */
    private double mathAround =2;

    /**
     *
     */
    private double sin;

    /**
     *
     */
    private double cos;

    /**
     *
     */
    private double _sin;

    /**
     *
     */
    private double _cos;


    /**
     *
     */
    private Point3D tmpPoint;


    /**
     * Gets position x.
     *
     * @return position x
     */
    public double getPositionX() {
        return positionX;
    }

    /**
     * Sets position x.
     *
     * @param positionX the position x
     */
    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    /**
     * Gets position y.
     *
     * @return position y
     */
    public double getPositionY() {
        return positionY;
    }

    /**
     * Sets position y.
     *
     * @param positionY the position y
     */
    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    /**
     * Gets position z.
     *
     * @return position z
     */
    public double getPositionZ() {
        return positionZ;
    }

    /**
     * Sets position z.
     *
     * @param positionZ the position z
     */
    public void setPositionZ(double positionZ) {
        this.positionZ = positionZ;
    }


    /**
     * Instantiates a new Point 3 d.
     *
     * @param x the x
     * @param y the y
     * @param z the z
     */
    public Point3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.positionX = x;
        this.positionY = y;
        this.positionZ = z;
        sin= Math.sin(Math.toRadians(mathAround));
        cos= Math.cos(Math.toRadians(mathAround));
        _sin= Math.sin(Math.toRadians(-mathAround));
        _cos= Math.cos(Math.toRadians(-mathAround));

    }


    /**
     * Projection to 2 d point 3 d.
     *
     * @return point 3 d
     */
    public Point3D projectionTo2D()
    {

        if (tmpPoint==null)
        {
            tmpPoint = new Point3D(getX(), getY(), getZ());
            tmpPoint.transformAroundX(transX);
            tmpPoint.transformAroundY(transY);
            tmpPoint.transformAroundZ(transZ);
        }else
            {

                if (!(tmpPoint.getX() == x && tmpPoint.getY() == y && tmpPoint.getZ() == z))
                {
                    tmpPoint = new Point3D(getX(), getY(), getZ());

                }
                if (!(tmpPoint.transX == transX ))
                {
                tmpPoint.transformAroundX(transX);
                }
                if (!(tmpPoint.transY == transY ))
                {

                    tmpPoint.transformAroundY(transY);

                }
                if (!(tmpPoint.transZ == transZ))
                {

                    tmpPoint.transformAroundZ(transZ);
                }
        }

        return tmpPoint;
    }


    /**
     * Gets x.
     *
     * @return x x
     */
    public double getX() {
        return x ;
    }

    /**
     * Sets x.
     *
     * @param x the x
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Gets y.
     *
     * @return y y
     */
    public double getY() {
        return y;
    }

    /**
     * Sets y.
     *
     * @param y the y
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Gets z.
     *
     * @return z z
     */
    public double getZ() {
        return z;
    }

    /**
     * Sets z.
     *
     * @param z the z
     */
    public void setZ(double z) {
        this.z = z;
    }

    /**
     * @param alfa
     */
    @Override
    public void transformAroundZ(double alfa)
    {


    if(alfa>0)
        for (double i=0;i<alfa;i+=mathAround)
        {
            x = x *cos+-y*sin;
            y= x *sin+y*cos;
        }
    else
        for (double i=0;i>alfa;i+=-mathAround)
    {

        x = x *_cos-y*_sin;
        y= x *_sin+y*_cos;
    }


    }

    /**
     * @param alfa
     */
    @Override
    public void transformAroundX(double alfa)
    {


       if(alfa>0)
            for (double i=0;i<=alfa;i+=mathAround)
            {

                y=y*cos-z*sin;
                z=y*sin+z*cos;
            }
        else
            for (double i=0;i>=alfa;i+=-mathAround)
            {

                y=y*_cos-z*_sin;
                z=y*_sin+z*_cos;
            }


    }

    /**
     * @param alfa
     */
    @Override
    public void transformAroundY(double alfa)
    {

        if(alfa>0)
            for (double i=0;i<alfa;i+=mathAround)
            {

                x = x *cos+z*sin;
                z=-x *sin+z*cos;
            }
        else
            for (double i=0;i>alfa;i+=-mathAround)
            {

                x = x *_cos+z*_sin;
                z=-x *_sin+z*_cos;
            }



    }


    /**
     * Move x.
     *
     * @param X the x
     */
    public void moveX(double X)
    {x+=X;}

    /**
     * Move y.
     *
     * @param Y the y
     */
    public void moveY(double Y)
    {y+=Y;}

    /**
     * Move z.
     *
     * @param Z the z
     */
    public void moveZ(double Z)
    {z+=Z;}

    @Override
    public void addTransformAroundZ(double alfa) {
transZ=(transZ+alfa)%360;
    }

    @Override
    public void addTransformAroundX(double alfa) {
        transX=(transX+alfa)%360;
    }

    @Override
    public void addTransformAroundY(double alfa) {
        transY=(transY+alfa)%360;
    }

    @Override
    public void RotationX(double alfa, Point3D center)
    {

    }

    @Override
    public void RotationY(double alfa, Point3D center) {

    }

    @Override
    public void RotationZ(double alfa, Point3D center) {

    }


    /**
     * Gets trans x.
     *
     * @return the trans x
     */
    public double getTransX() {
        return transX;
    }

    /**
     * Sets trans x.
     *
     * @param transX the trans x
     */
    public void setTransX(double transX) {
        this.transX = transX;
    }

    /**
     * Gets trans y.
     *
     * @return the trans y
     */
    public double getTransY() {
        return transY;
    }

    /**
     * Sets trans y.
     *
     * @param transY the trans y
     */
    public void setTransY(double transY) {
        this.transY = transY;
    }

    /**
     * Gets trans z.
     *
     * @return the trans z
     */
    public double getTransZ() {
        return transZ;
    }

    /**
     * Sets trans z.
     *
     * @param transZ the trans z
     */
    public void setTransZ(double transZ) {
        this.transZ = transZ;
    }

    /**
     * Distance relative double.
     *
     * @param point3D the point 3 d
     * @return the double
     */
    public  double distanceRelative(Point3D point3D)
    {

       return Math.sqrt(Math.pow(x - point3D.getX(), 2) + Math.pow(y - point3D.getY(), 2) + Math.pow(z - point3D.getZ(), 2));
    }

    /**
     * Gets copy.
     *
     * @return the copy
     */
    Point3D getCopy()
    {
        Point3D p;
        if (tmpPoint!=null)
            p=new Point3D(tmpPoint.getX(),tmpPoint.getY(),tmpPoint.getZ());
        else
            p=new Point3D(0,0,0);

        return p;
    };

    @Override
    public String toString() {
        return "Point3D{" +
                "x=" + Math.round(getX()*100f)/100f +
                ", y=" + Math.round(getY()*100f)/100f +
                ", z=" + Math.round(getZ()*100f)/100f +
                '}';
    }
}
