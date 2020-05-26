package Geometry3D;

import java.awt.*;

/**
 * The type Line 3 d.
 */
public class Line3D extends Shape3D implements Comparable {




    /**
     *
     */
    private Point3D p1;

    /**
     *
     */
    private Point3D p2;

    /**
     *
     */
    private Point3D lastP1;

    /**
     *
     */
    private Point3D lastP2;

    private Point3D center;

    /**
     * Instantiates a new Line 3 d.
     *
     * @param p1    the p 1
     * @param p2    the p 2
     * @param color the color
     */
    public Line3D(Point3D p1, Point3D p2,Color color) {
        this.p1 = p1;
        this.p2 = p2;
        this.setColor(color);

    }


    /**
     * Instantiates a new Line 3 d.
     *
     * @param p1 the p 1
     * @param p2 the p 2
     */
    public Line3D(Point3D p1, Point3D p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.lastP1 = p1;
        this.lastP2 = p2;

    }


    /**
     * Gets p 1.
     *
     * @return p 1
     */
    public Point3D getP1() {
        return p1;
    }

    /**
     * Sets p 1.
     *
     * @param p1 the p 1
     */
    public void setP1(Point3D p1) {
        this.p1 = p1;
    }

    /**
     * Gets p 2.
     *
     * @return p 2
     */
    public Point3D getP2() {
        return p2;
    }

    /**
     * Sets p 2.
     *
     * @param p2 the p 2
     */
    public void setP2(Point3D p2) {
        this.p2 = p2;
    }

    /**
     * @param g
     */
    @Override
    public void draw(Graphics2D g, Point3D cameraPosition)
    {

        lastP1 =p1.projectionTo2D();

        lastP2 =p2.projectionTo2D();

        if(lastP1.getZ()<0)
            return;
        if(lastP2.getZ()<0)
            return;

        double add1=(cameraPosition.getZ()/ lastP1.getZ()*100);

        double add2=(cameraPosition.getZ()/ lastP2.getZ()*100);

        g.setColor(getColor());
            g.drawLine(
                    (int) ((lastP1.getX() * ((add1)))+cameraPosition.getX()),
                    (int) ((lastP1.getY() * ((add1)))+cameraPosition.getY()),
                    (int) ((lastP2.getX() * ((add2)))+cameraPosition.getX()),
                    (int) ((lastP2.getY() * ((add2)))+cameraPosition.getY()));



    }

    @Override
    public void fill(Graphics2D graphics2D, Point3D camPos )
    {
            draw(graphics2D,camPos);
    }

    @Override
    public void fillClassic(Graphics2D graphics2D, Point3D camPos) {
            fill(graphics2D, camPos);
    }

    @Override
    public void update(Graphics2D graphics2D, Point3D camPos)
    {
        this.fill(graphics2D, camPos);
    }

    @Override
    public void setColor(Color color)
    {
            this.setColor( color);
    }

    @Override
    public Point3D getNormal() {
        return new Point3D(getP2().getX()-getP1().getX(),getP2().getY()-getP1().getY(),getP2().getZ()-getP1().getZ());
    }

    @Override
    public Object copy() {
        return new Line3D(getP1().getCopy(),getP2().getCopy());
    }


    /**
     * @param alfa
     */
    @Override
    public void transformAroundZ(double alfa)
    {

        p1.transformAroundZ(alfa);
        p2.transformAroundZ(alfa);

    }

    /**
     * @param alfa
     */
    @Override
    public void transformAroundX(double alfa)
    {

        p1.transformAroundX(alfa);
        p2.transformAroundX(alfa);

    }

    /**
     * @param alfa
     */
    @Override
    public void transformAroundY(double alfa)
    {

        p1.transformAroundY(alfa);
        p2.transformAroundY(alfa);

    }

    @Override
    public void moveX(double X) {
        p1.moveX(X);
        p2.moveX(X);
    }

    @Override
    public void moveY(double Y) {
        p1.moveY(Y);
        p2.moveY(Y);
    }

    @Override
    public void moveZ(double Z) {
        p1.moveZ(Z);
        p2.moveZ(Z);
    }

    @Override
    public Point3D getCenter()
    {

        if (center==null)
        {
            center=new Point3D(
                    (lastP1.getX()+lastP2.getX())/2,
                    (lastP1.getY()+lastP2.getY())/2,
                    (lastP1.getZ()+lastP2.getZ())/2);
        }

        else
        {
            center.setX((lastP1.getX()+lastP2.getX())/2);
            center.setY((lastP1.getY()+lastP2.getY())/2);
            center.setZ((lastP1.getZ()+lastP2.getZ())/2);
        }



        return center;
    }



    @Override
    public void addTransformAroundZ(double alfa) {
      getP2().addTransformAroundZ(alfa);
        getP1().addTransformAroundZ(alfa);
    }

    @Override
    public void addTransformAroundX(double alfa) {
        getP2().addTransformAroundX(alfa);
        getP1().addTransformAroundX(alfa);
    }

    @Override
    public void addTransformAroundY(double alfa) {
        getP2().addTransformAroundY(alfa);
        getP1().addTransformAroundY(alfa);
    }

    @Override
    public void RotationX(double alfa, Point3D center)
    {

        double centerX=center.getX();
        double centerY=center.getY();
        double centerZ=center.getZ();

        Line3D line3D=new Line3D(
                new Point3D(getP1().getX()-centerX,getP1().getY()-centerY,getP1().getZ()-centerZ),
                new Point3D(getP2().getX()-centerX,getP2().getY()-centerY,getP2().getZ()-centerZ)
        );

        line3D.transformAroundX(alfa);

        double newP1X= line3D.getP1().getX()+centerX;
        double newP1Y= line3D.getP1().getY()+centerY;
        double newP1Z= line3D.getP1().getZ()+centerZ;
        double newP2X= line3D.getP2().getX()+centerX;
        double newP2Y= line3D.getP2().getY()+centerY;
        double newP2Z= line3D.getP2().getZ()+centerZ;

        getP1().setX(newP1X);
        getP1().setY(newP1Y);
        getP1().setZ(newP1Z);
        getP2().setX(newP2X);
        getP2().setY(newP2Y);
        getP2().setZ(newP2Z);


    }

    @Override
    public void RotationY(double alfa, Point3D center)
    {
        double centerX=center.getX();
        double centerY=center.getY();
        double centerZ=center.getZ();

        Line3D line3D=new Line3D(
                new Point3D(getP1().getX()-centerX,getP1().getY()-centerY,getP1().getZ()-centerZ),
                new Point3D(getP2().getX()-centerX,getP2().getY()-centerY,getP2().getZ()-centerZ)
        );

        line3D.transformAroundY(alfa);

        double newP1X= line3D.getP1().getX()+centerX;
        double newP1Y= line3D.getP1().getY()+centerY;
        double newP1Z= line3D.getP1().getZ()+centerZ;
        double newP2X= line3D.getP2().getX()+centerX;
        double newP2Y= line3D.getP2().getY()+centerY;
        double newP2Z= line3D.getP2().getZ()+centerZ;

        getP1().setX(newP1X);
        getP1().setY(newP1Y);
        getP1().setZ(newP1Z);
        getP2().setX(newP2X);
        getP2().setY(newP2Y);
        getP2().setZ(newP2Z);
    }

    @Override
    public void RotationZ(double alfa, Point3D center)
    {
        double centerX=center.getX();
        double centerY=center.getY();
        double centerZ=center.getZ();

        Line3D line3D=new Line3D(
                new Point3D(getP1().getX()-centerX,getP1().getY()-centerY,getP1().getZ()-centerZ),
                new Point3D(getP2().getX()-centerX,getP2().getY()-centerY,getP2().getZ()-centerZ)
        );

        line3D.transformAroundZ(alfa);

        double newP1X= line3D.getP1().getX()+centerX;
        double newP1Y= line3D.getP1().getY()+centerY;
        double newP1Z= line3D.getP1().getZ()+centerZ;
        double newP2X= line3D.getP2().getX()+centerX;
        double newP2Y= line3D.getP2().getY()+centerY;
        double newP2Z= line3D.getP2().getZ()+centerZ;

        getP1().setX(newP1X);
        getP1().setY(newP1Y);
        getP1().setZ(newP1Z);
        getP2().setX(newP2X);
        getP2().setY(newP2Y);
        getP2().setZ(newP2Z);
    }


    @Override
    public int compareTo(Object o) {

        Point3D a= getP1().projectionTo2D();
        Point3D b= ((Line3D)o).getP1().projectionTo2D();

        double avg1 =((a.getZ()+a.getZ())/2);
        double avg2 =(b.getZ()+b.getZ())/2;

        if (avg1<avg2)
            return 1;
        else  if (avg1>avg2)
            return -1;

        return 0;
    }


}
