package Geometry3D;

import java.awt.*;

/**
 * experimental3D
 *
 * @author Jan Frantisek Sedlacek
 * @version 1.0
 * @since 26.03.20
 */
public class Triangle3D extends Shape3D implements Comparable<Triangle3D>
{




    private Line3D l1;
    private Line3D l2;
    private Line3D l3;

    private Point3D lastP1;
    private Point3D lastP2;
    private Point3D lastP3;

    private Point3D Center;

    private int[] xPoints;

    private int[] yPoints;


    /**
     * Instantiates a new Triangle 3 d.
     *
     * @param p1 the p 1
     * @param p2 the p 2
     * @param p3 the p 3
     */
    public Triangle3D(Point3D p1,Point3D p2,Point3D p3 ) {
        this.setL1(new Line3D(p1,p2));
        this.setL2(new Line3D(p2,p3));
        this.setL3(new Line3D(p3,p1));
        this.setLastP3(p3);
        this.setLastP2(p2);
        this.setLastP1(p1);
        this.setxPoints(new int[]{0,0,0});
        this.setyPoints(new int[]{0,0,0});

    }

    @Override
    public void draw(Graphics2D g, Point3D camPos) {
        getL1().draw(g,camPos);
        getL2().draw(g,camPos);
        getL3().draw(g,camPos);
    }

    @Override
    public void fill(Graphics2D g2, Point3D cameraPosition )
    {

        if (g2==null)
            return;

        g2.setColor(getShadedColor());

        g2.fillPolygon(xPoints, yPoints, 3);

    }

    @Override
    public void fillClassic(Graphics2D g2, Point3D cameraPosition)
    {


        setLastP1(getL1().getP1().projectionTo2D());
        setLastP2(getL1().getP2().projectionTo2D());
        setLastP3(getL2().getP2().projectionTo2D());

        if(getLastP1().getZ()<0)
            return;

        if(getLastP2().getZ()<0)
            return;

        if(getLastP3().getZ()<0)
            return;

        double add1=(cameraPosition.getZ()/ getLastP1().getZ()*100);

        double add2=(cameraPosition.getZ()/ getLastP2().getZ()*100);

        double add3=(cameraPosition.getZ()/ getLastP3().getZ()*100);

        xPoints = new int[]{
                ((int) ((getLastP1().getX() * ((add1)))+cameraPosition.getX())),
                ((int) ((getLastP2().getX() * ((add2)))+cameraPosition.getX())),
                ((int) ((getLastP3().getX() * ((add3)))+cameraPosition.getX()))};


        yPoints = new int[]{
                ((int) ((getLastP1().getY() * ((add1)))+cameraPosition.getY())),
                ((int) ((getLastP2().getY() * ((add2)))+cameraPosition.getY())),
                ((int) ((getLastP3().getY() * ((add3)))+cameraPosition.getY()))};

        g2.setColor(getShadedColor());

        g2.fillPolygon(xPoints, yPoints, 3);
    }

    @Override
    public void update(Graphics2D graphics2D,Point3D cameraPosition)
    {

        setUpdated(false);

        setLastP1(getL1().getP1().projectionTo2D());
        setLastP2(getL1().getP2().projectionTo2D());
        setLastP3(getL2().getP2().projectionTo2D());

        if(getLastP1().getZ()<0)
            return;

        if(getLastP2().getZ()<0)
            return;

        if(getLastP3().getZ()<0)
            return;

        double add1=(cameraPosition.getZ()/ getLastP1().getZ()*100);

        double add2=(cameraPosition.getZ()/ getLastP2().getZ()*100);

        double add3=(cameraPosition.getZ()/ getLastP3().getZ()*100);

        xPoints = new int[]{
                ((int) ((getLastP1().getX() * ((add1)))+cameraPosition.getX())),
                ((int) ((getLastP2().getX() * ((add2)))+cameraPosition.getX())),
                ((int) ((getLastP3().getX() * ((add3)))+cameraPosition.getX()))};


        yPoints = new int[]{
                ((int) ((getLastP1().getY() * ((add1)))+cameraPosition.getY())),
                ((int) ((getLastP2().getY() * ((add2)))+cameraPosition.getY())),
                ((int) ((getLastP3().getY() * ((add3)))+cameraPosition.getY()))};

        setUpdated(true);
    }

    @Override
    public Point3D getNormal() {

        Point3D vecA= getL1().getNormal();
        Point3D vecB= getL2().getNormal();

        double nx=(vecA.getY()*vecB.getZ()-vecA.getZ()*vecB.getY());
        double ny=(vecA.getZ()*vecB.getX()-vecA.getX()*vecB.getZ());
        double nz=(vecA.getX()*vecB.getY()-vecA.getY()*vecB.getX());


        return new Point3D(nx,ny,nz);
    }

    @Override
    public Object copy() {
        return new Triangle3D(getLastP1().getCopy(),getLastP2().getCopy(),getLastP3().getCopy());
    }


    @Override
    public void moveX(double X)
    {
        getL1().moveX(X);
        getL2().moveX(X);
        getL3().moveX(X);
    }

    @Override
    public void moveY(double Y) {
        getL1().moveY(Y);
        getL2().moveY(Y);
        getL3().moveY(Y);
    }

    @Override
    public void moveZ(double Z) {

        getL1().moveZ(Z);
        getL2().moveZ(Z);
        getL3().moveZ(Z);
    }

    /**
     * The Center.
     */
    @Override
    public Point3D getCenter() {

        if (Center==null)
        {
                   Center=
                           new Point3D(
                                   (getLastP1().getX()+ getLastP2().getX()+ getLastP3().getX())/3,
                                   (getLastP1().getY()+ getLastP2().getY()+ getLastP3().getY())/3,
                                   (getLastP1().getZ()+ getLastP2().getZ()+ getLastP3().getZ())/3);
        }
        else
        {
            Center.setX((getLastP1().getX()+ getLastP2().getX()+ getLastP3().getX())/3);
            Center.setY((getLastP1().getY()+ getLastP2().getY()+ getLastP3().getY())/3);
            Center.setZ((getLastP1().getZ()+ getLastP2().getZ()+ getLastP3().getZ())/3);
        }



        return Center;
    }

    @Override
    public void transformAroundZ(double alfa)
    {
            getL1().transformAroundZ(alfa);
            getL2().transformAroundZ(alfa);
            getL3().transformAroundZ(alfa);


    }

    @Override
    public void transformAroundX(double alfa) {
        getL1().transformAroundX(alfa);
        getL2().transformAroundX(alfa);
        getL3().transformAroundX(alfa);
    }

    @Override
    public void transformAroundY(double alfa) {

        getL1().transformAroundY(alfa);
        getL2().transformAroundY(alfa);
        getL3().transformAroundY(alfa);
    }

    @Override
    public void addTransformAroundZ(double alfa) {

        getL1().addTransformAroundZ(alfa);
        getL2().addTransformAroundZ(alfa);
        getL3().addTransformAroundZ(alfa);
    }

    @Override
    public void addTransformAroundX(double alfa) {

        getL1().addTransformAroundX(alfa);
        getL2().addTransformAroundX(alfa);
        getL3().addTransformAroundX(alfa);
    }

    @Override
    public void addTransformAroundY(double alfa) {
        getL1().addTransformAroundY(alfa);
        getL2().addTransformAroundY(alfa);
        getL3().addTransformAroundY(alfa);
    }

    @Override
    public void RotationX(double alfa, Point3D center) {


        getL1().RotationX(alfa,center);
        getL2().RotationX(alfa,center);
        getL3().RotationX(alfa,center);

    }

    @Override
    public void RotationY(double alfa, Point3D center) {
        getL1().RotationY(alfa,center);
        getL2().RotationY(alfa,center);
        getL3().RotationY(alfa,center);
    }

    @Override
    public void RotationZ(double alfa, Point3D center) {
        getL1().RotationZ(alfa,center);
        getL2().RotationZ(alfa,center);
        getL3().RotationZ(alfa,center);
    }

    /**
     * Gets l 1.
     *
     * @return the l 1
     */
    public Line3D getL1() {
        return l1;
    }

    /**
     * Sets l 1.
     *
     * @param l1 the l 1
     */
    public void setL1(Line3D l1) {
        this.l1 = l1;
    }

    /**
     * Gets l 2.
     *
     * @return the l 2
     */
    public Line3D getL2() {
        return l2;
    }

    /**
     * Sets l 2.
     *
     * @param l2 the l 2
     */
    public void setL2(Line3D l2) {
        this.l2 = l2;
    }

    /**
     * Gets l 3.
     *
     * @return the l 3
     */
    public Line3D getL3() {
        return l3;
    }

    /**
     * Sets l 3.
     *
     * @param l3 the l 3
     */
    public void setL3(Line3D l3) {
        this.l3 = l3;
    }

    /**
     * Gets last p 1.
     *
     * @return the last p 1
     */
    public Point3D getLastP1() {
        return lastP1;
    }

    /**
     * Sets last p 1.
     *
     * @param lastP1 the last p 1
     */
    public void setLastP1(Point3D lastP1) {
        this.lastP1 = lastP1;
    }

    /**
     * Gets last p 2.
     *
     * @return the last p 2
     */
    public Point3D getLastP2() {
        return lastP2;
    }

    /**
     * Sets last p 2.
     *
     * @param lastP2 the last p 2
     */
    public void setLastP2(Point3D lastP2) {
        this.lastP2 = lastP2;
    }

    /**
     * Gets last p 3.
     *
     * @return the last p 3
     */
    public Point3D getLastP3() {
        return lastP3;
    }

    /**
     * Sets last p 3.
     *
     * @param lastP3 the last p 3
     */
    public void setLastP3(Point3D lastP3) {
        this.lastP3 = lastP3;
    }

    /**
     * Sets center.
     *
     * @param center the center
     */
    public void setCenter(Point3D center) {
        Center = center;
    }

    /**
     * Getx points int [ ].
     *
     * @return the int [ ]
     */
    public int[] getxPoints() {
        return xPoints;
    }

    /**
     * Sets points.
     *
     * @param xPoints the x points
     */
    public void setxPoints(int[] xPoints) {
        this.xPoints = xPoints;
    }

    /**
     * Gety points int [ ].
     *
     * @return the int [ ]
     */
    public int[] getyPoints() {
        return yPoints;
    }

    /**
     * Sets points.
     *
     * @param yPoints the y points
     */
    public void setyPoints(int[] yPoints) {
        this.yPoints = yPoints;
    }

    @Override
    public int compareTo(Triangle3D triangle3D) {

        if (this.getCenter().getZ()<triangle3D.getCenter().getZ())
            return 1;
        else
        if (this.getCenter().getZ()>=triangle3D.getCenter().getZ())
            return -1;

        return 0;
    }
}



