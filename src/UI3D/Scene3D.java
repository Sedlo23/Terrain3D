package UI3D;

import Geometry3D.Point3D;
import Geometry3D.Shape3D;
import Geometry3D.Triangle3D;
import Logs3D.LOG_LEVEL;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.function.Supplier;
import java.util.logging.*;

/**
 * experimental3D
 *
 * @author Jan Frantisek Sedlacek
 * @version 1.0
 * @since 26.03.20
 */
public class Scene3D implements KeyListener, MouseMotionListener, Supplier
{


    private ShapeArray shape3DS;

    private Point3D lightSource;

    private Point3D cameraPosition;

    private int oldXMousePosition ;

    private int oldYMousePosition ;

    private double movementSpeed;

    private Triangle3D staticLight;

    private boolean staticLightEnable;

    private int lightPower;

    private Graphics2D lastGraphics;

    /**
     * The Logger.
     */
    static Logger logger = Logger.getLogger(Scene3D.class.getName());

    private LOG_LEVEL log_level;


    /**
     * Instantiates a new Scene 3 d.
     *
     * @param cameraPosition the camera position
     */
    public Scene3D(Point3D cameraPosition)
    {
        log_level=new LOG_LEVEL();
        this.cameraPosition =cameraPosition;
        oldXMousePosition=0;
        oldYMousePosition=0;
        lightSource= new Point3D(0,0,0);
        movementSpeed=1;
        staticLightEnable=false;
        staticLight=new Triangle3D(new Point3D(0,0,0),new Point3D(0,0,0),new Point3D(0,0,0));
        setShape3DS(new ShapeArray(false));
        getShape3DS().add(staticLight);
        lightPower= 2000;
        logger.setLevel(Level.ALL);
        logger.log(log_level.sceneMove,"Scene INIT");

    }


    /**
     * Paint.
     *
     * @param graphics2D the graphics 2 d
     */
    public void  update(Graphics2D graphics2D)
    {

        ShapeArray tmpArr=new ShapeArray(false);

        tmpArr.addAll(shape3DS);

        shape3DS.setLock(true);


        for (Shape3D tmp: tmpArr)
        {
            tmp.update(graphics2D,cameraPosition);
        }




        updateShading();

        shape3DS=tmpArr;

        shape3DS.setLock(false);

        logger.log(log_level.sceneMove,"Scene UPDATE");


    }


    /**
     * Paint.
     *
     * @param graphics2D the graphics 2 d
     */
    public void  paintClassic(Graphics2D graphics2D )
    {

        ArrayList<Shape3D> shape3DS= new ArrayList<>();
        shape3DS.addAll(getShape3DS());

            shape3DS.sort(new Comparator<Shape3D>(){
                @Override
                public int compare(Shape3D shape3D, Shape3D t1) {

                    if (shape3D.getCenter().getZ()<t1.getCenter().getZ())
                        return 1;
                    else
                    if (shape3D.getCenter().getZ()>t1.getCenter().getZ())
                        return -1;

                    return 0;
                }
            });


        updateShading();

        for (Shape3D tmp: shape3DS)
        {
               tmp.fillClassic(graphics2D,cameraPosition);
        }

        lastGraphics=graphics2D;

        logger.log(log_level.sceneMove,"Scene DRAW");

    }


    /**
     * clear.
     *
     * @param graphics2D the graphics 2 d
     */
    public void  clear(Graphics2D graphics2D )
    {

        if (graphics2D==null)
            return;

        graphics2D.setColor(Color.BLACK);
        graphics2D.fillRect(0, 0, 10000, 10000);

        logger.log(log_level.sceneMove,"Scene CLEAR");

    }

    /**
     * Add sphere.
     *
     * @param r      the r
     * @param detail the detail
     * @param x      the x
     * @param y      the y
     * @param z      the z
     */
    public void addSphere(double r,int detail,int x,int y,int z )
    {
        ArrayList<Shape3D> tmp =new ArrayList<>();

        for (int sigma=detail;sigma<=360;sigma+=detail)
            for (int alfa=detail;alfa<=360;alfa+=detail)
            {

                   tmp.add(new Triangle3D(
                           new Point3D(
                           r*Math.sin(Math.toRadians(alfa-detail))*Math.cos(Math.toRadians(sigma))+x,
                           r*Math.sin(Math.toRadians(alfa-detail))*Math.sin(Math.toRadians(sigma))+y,
                           r*Math.cos(Math.toRadians(alfa-detail))+z),
                           new Point3D(
                           r*Math.sin(Math.toRadians(alfa))*Math.cos(Math.toRadians(sigma-detail))+x,
                           r*Math.sin(Math.toRadians(alfa))*Math.sin(Math.toRadians(sigma-detail))+y,
                           r*Math.cos(Math.toRadians(alfa))+z),
                            new Point3D(
                        r*Math.sin(Math.toRadians(alfa))*Math.cos(Math.toRadians(sigma+detail))+x,
                        r*Math.sin(Math.toRadians(alfa))*Math.sin(Math.toRadians(sigma+detail))+y,
                        r*Math.cos(Math.toRadians(alfa))+z)));




                tmp.get(tmp.size()-1).setColor(Color.YELLOW);
                tmp.get(tmp.size()-1).setLightPower(lightPower);
                tmp.get(tmp.size()-1).updateShadeColor(new Point3D(x-r,y-r,z-r));

            }

        logger.log(log_level.calculation,"Sphere INIT");
       getShape3DS().addAll(tmp);
    }

    /**
     * Add terrain.
     *
     * @param size     the size
     * @param depth    the depth
     * @param gridSize the grid size
     * @param range    the range
     */
    public void addTerrain(int size,int depth,int gridSize,int range )
    {

        ArrayList<Shape3D> tmp =new ArrayList<>();

        final int DATA_SIZE = (int)Math.pow(2,size/3)+1;

        final double SEED = depth;
        double[][] data = new double[DATA_SIZE][DATA_SIZE];

        data[0][0] = data[0][DATA_SIZE-1] = data[DATA_SIZE-1][0] =
                data[DATA_SIZE-1][DATA_SIZE-1] = SEED;

        double h = range;
        Random r = new Random();

        for(int sideLength = DATA_SIZE-1;

            sideLength >= 2;

            sideLength /=2, h/= 2.0){

            int halfSide = sideLength/2;

            for(int x=0;x<DATA_SIZE-1;x+=sideLength){
                for(int y=0;y<DATA_SIZE-1;y+=sideLength){

                    double avg = data[x][y] +
                            data[x+sideLength][y] +
                            data[x][y+sideLength] +
                            data[x+sideLength][y+sideLength];
                    avg /= 4.0;


                    data[x+halfSide][y+halfSide] =

                            avg + (r.nextDouble()*2*h) - h;
                }
            }


            for(int x=0;x<DATA_SIZE-1;x+=halfSide){

                for(int y=(x+halfSide)%sideLength;y<DATA_SIZE-1;y+=sideLength){

                    double avg =
                            data[(x-halfSide+DATA_SIZE)%DATA_SIZE][y] +
                                    data[(x+halfSide)%DATA_SIZE][y] +
                                    data[x][(y+halfSide)%DATA_SIZE] +
                                    data[x][(y-halfSide+DATA_SIZE)%DATA_SIZE];
                    avg /= 4.0;


                    avg = avg + (r.nextDouble()*2*h) - h;

                    data[x][y] = avg;

                    if(x == 0)  data[DATA_SIZE-1][y] = avg;
                    if(y == 0)  data[x][DATA_SIZE-1] = avg;
                }
            }
        }

        ArrayList<Color>colors=new ArrayList<>();

        int red = 255; //i.e. FF
        int green = 0;
        double stepSize = 1;

        while(green < 255)
        {
            green += stepSize;
            if(green > 255) { green = 255; }
            colors.add((new Color(red, green, 0)));
        }
        while(red > 0)
        {
            red -= stepSize;
            if(red < 0) { red = 0; }
            colors.add((new Color(red, green, 0)));
        }


        for(int y=1; y< DATA_SIZE-1;y++)
        {
            for(int x=1; x< DATA_SIZE-1;x++)
            {

                tmp.add(new Triangle3D(
                        new Point3D((y)*gridSize,data[y][x],(x)*gridSize),
                        new Point3D(y*gridSize,data[y][x+1],(x+1)*gridSize),
                        new Point3D((y+1)*gridSize,data[y+1][x],(x)*gridSize)));



                int colorIndex=(int)Math.abs(((double)colors.size()/((double)(range*2))*((data[y][x])+range)))%colors.size();

                tmp.get(tmp.size()-1).setColor((colors.get(colorIndex)));
                tmp.get(tmp.size()-1).setLightPower(lightPower);

                tmp.add(new Triangle3D(
                        new Point3D((y)*gridSize,data[y][x+1],(x+1)*gridSize),
                        new Point3D((y+1)*gridSize,data[y+1][x+1],(x+1)*gridSize),
                        new Point3D((y+1)*gridSize,data[y+1][x],(x)*gridSize)));


                tmp.get(tmp.size()-1).setColor(colors.get(colorIndex));
                tmp.get(tmp.size()-1).setLightPower(lightPower);


            }

        }
        logger.log(log_level.calculation,"terrain INIT");
        getShape3DS().addAll(tmp);
    }

    /**
     * Read file.
     *
     * @param fileName the file name
     */
    public void readFile(String fileName)
    {

        int counter=0;
        int indexSize=-1;
        int objectsize=-1;
        ArrayList<Point3D>point3DS=new ArrayList<>();
        ArrayList<Triangle3D>triangle3DS=new ArrayList<>();

        boolean error=false;

        logger.log(log_level.guiInfo,"file reading Start");
        BufferedReader br = null;
        try
        {

        br = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = br.readLine()) != null)
        {

            line=line.replaceAll("\\s+", " ");

            if (Character.isWhitespace(line.charAt(0)))
                line= line.substring(1);


            if (objectsize==-1)
            {
                objectsize=Integer.parseInt(line);
                counter =0;
            }
            else
            if (counter<objectsize)
            {

                double x= Double.parseDouble(line.split(" ")[0]);
                double y= Double.parseDouble(line.split(" ")[2]);
                double z= Double.parseDouble(line.split(" ")[1]);

                point3DS.add(new Point3D(x, y, z));

                counter++;
            }
            else
            if (indexSize==-1)
            {
                indexSize=Integer.parseInt(line);
            }
            else
            {

                Point3D p1=new Point3D(
                        point3DS.get(Integer.parseInt(line.split(" ")[0])).getX(),
                        point3DS.get(Integer.parseInt(line.split(" ")[0])).getY(),
                        point3DS.get(Integer.parseInt(line.split(" ")[0])).getZ()
                );
                Point3D p2=new Point3D(
                        point3DS.get(Integer.parseInt(line.split(" ")[1])).getX(),
                        point3DS.get(Integer.parseInt(line.split(" ")[1])).getY(),
                        point3DS.get(Integer.parseInt(line.split(" ")[1])).getZ()
                );
                Point3D p3=new Point3D(
                        point3DS.get(Integer.parseInt(line.split(" ")[2])).getX(),
                        point3DS.get(Integer.parseInt(line.split(" ")[2])).getY(),
                        point3DS.get(Integer.parseInt(line.split(" ")[2])).getZ()
                );




                triangle3DS.add(new Triangle3D(p1,p2,p3));

                triangle3DS.get(triangle3DS.size()-1).setColor(Color.YELLOW);

                triangle3DS.get(triangle3DS.size()-1).setLightPower(lightPower);



            }

        }
        }
        catch (IOException e)
        {
            logger.log(log_level.errors,"\033[0m"+e.getMessage()); }





        logger.log(log_level.guiInfo,"file reading END");
        getShape3DS().addAll(triangle3DS);



    }


    /**
     * Update shading.
     */
    public void updateShading()
    {


            if (!isStaticLightEnable())
            {
                for(Shape3D shape3D: getShape3DS())
                    shape3D.updateShadeColor(lightSource);
            }
                else
            {
                for(Shape3D shape3D: getShape3DS())
                    shape3D.updateShadeColor(staticLight.getCenter());
            }

        logger.log(log_level.calculation,"Shades Update");


    }

    /**
     * Gets camera position.
     *
     * @return the camera position
     */
    public Point3D getCameraPosition() {
        return cameraPosition;
    }

    /**
     * Sets camera position.
     *
     * @param cameraPosition the camera position
     */
    public void setCameraPosition(Point3D cameraPosition) {
        this.cameraPosition = cameraPosition;
    }

    /**
     * Gets shape 3 ds.
     *
     * @return the shape 3 ds
     * <p>
     * The Shape 3 ds.
     */
/**
     * The Shape 3 ds.
     */
    public ArrayList<Shape3D> getShape3DS()
    {


        return shape3DS;
    }

    /**
     * Gets shape 3 ds.
     *
     * @return the shape 3 ds
     * <p>
     * The Shape 3 ds.
     */
/**
     * The Shape 3 ds.
     */
    public ArrayList<Shape3D> getCopyShape3DS()
    {


    return (ArrayList<Shape3D>)getShape3DS().clone();
}

    /**
     * Sets shape 3 ds.
     *
     * @param shape3DS the shape 3 ds
     */
    public void setShape3DS(ShapeArray shape3DS) {
        this.shape3DS = shape3DS;
    }

    /**
     * Gets old x mouse position.
     *
     * @return the old x mouse position
     */
    public int getOldXMousePosition() {
        return oldXMousePosition;
    }

    /**
     * Sets old x mouse position.
     *
     * @param oldXMousePosition the old x mouse position
     */
    public void setOldXMousePosition(int oldXMousePosition) {
        this.oldXMousePosition = oldXMousePosition;
    }

    /**
     * Gets movement speed.
     *
     * @return the movement speed
     */
    public double getMovementSpeed() {
        return movementSpeed;
    }

    /**
     * Sets movement speed.
     *
     * @param movementSpeed the movement speed
     */
    public void setMovementSpeed(double movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    /**
     * Gets static light.
     *
     * @return the static light
     */
    public Triangle3D getStaticLight() {
        return staticLight;
    }

    /**
     * Sets static light.
     *
     * @param staticLight the static light
     */
    public void setStaticLight(Triangle3D staticLight) {
        getShape3DS().remove(this.staticLight);
        this.staticLight = staticLight;
        getShape3DS().add(this.staticLight);
    }

    /**
     * Is static light enable boolean.
     *
     * @return the boolean
     */
    public boolean isStaticLightEnable() {
        return staticLightEnable;
    }

    /**
     * Sets static light enable.
     *
     * @param staticLightEnable the static light enable
     */
    public void setStaticLightEnable(boolean staticLightEnable)
    {

        this.staticLightEnable = staticLightEnable;
    }

    /**
     * Gets light source.
     *
     * @return the light source
     */
    public Point3D getLightSource() {
        return lightSource;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode()==KeyEvent.VK_W)
            for (Shape3D tmp: getCopyShape3DS())
            {
                tmp.moveZ(-movementSpeed);
            }

        if (e.getKeyCode()==KeyEvent.VK_S)
            for (Shape3D tmp: getCopyShape3DS())
            {
                tmp.moveZ(movementSpeed);    }

        if (e.getKeyCode()==KeyEvent.VK_A)
            for (Shape3D tmp: getCopyShape3DS())
            {
                tmp.moveX(movementSpeed);
            }

        if (e.getKeyCode()==KeyEvent.VK_D)
            for (Shape3D tmp: getCopyShape3DS())
            {
                tmp.moveX(-movementSpeed);    }

        if (e.getKeyCode()==KeyEvent.VK_Q)
            for (Shape3D tmp: getCopyShape3DS())
            {
                tmp.moveY(movementSpeed);
            }

        if (e.getKeyCode()==KeyEvent.VK_E)
            for (Shape3D tmp: getCopyShape3DS())
            {
                tmp.moveY(-movementSpeed);
            }

        if (e.getKeyCode()==KeyEvent.VK_P)
            for (Shape3D tmp: getCopyShape3DS())
            {
                tmp.RotationX(-movementSpeed,new Point3D(0,0,0));
            }

        if (e.getKeyCode()==KeyEvent.VK_O)
            for (Shape3D tmp: getCopyShape3DS())
            {
                tmp.RotationX(movementSpeed,new Point3D(0,0,0));
            }

        if (e.getKeyCode()==KeyEvent.VK_K)
            for (Shape3D tmp: getCopyShape3DS())
            {
                tmp.RotationZ(-movementSpeed,new Point3D(0,0,0));
            }

        if (e.getKeyCode()==KeyEvent.VK_P)
            for (Shape3D tmp: getCopyShape3DS())
            {
                tmp.RotationX(-movementSpeed,new Point3D(0,0,0));
            }

        if (e.getKeyCode()==KeyEvent.VK_I)
            for (Shape3D tmp: getCopyShape3DS())
            {
                tmp.RotationY(movementSpeed,new Point3D(0,0,0));
            }

        if (e.getKeyCode()==KeyEvent.VK_U)
            for (Shape3D tmp: getCopyShape3DS())
            {
                tmp.RotationY(-movementSpeed,new Point3D(0,0,0));
            }


        if (e.getKeyCode()==KeyEvent.VK_N)
            movementSpeed+=1.5f;

        if (e.getKeyCode()==KeyEvent.VK_M)
            movementSpeed-=1.5f;

        logger.log(log_level.guiInfo,"Key pressed");

    }

    @Override
    public void keyReleased(KeyEvent keyEvent)
    {

    }

    @Override
    public void mouseDragged(MouseEvent e)
    {

        logger.log(log_level.guiInfo,"mouse DRAGGED");

        if (e.getX()+5<oldXMousePosition)
            for (Shape3D tmp: getCopyShape3DS())
            {
                tmp.addTransformAroundY(-1);
            }
        else
        if (e.getX()-5>oldXMousePosition)
            for (Shape3D tmp: getCopyShape3DS()) {
                tmp.addTransformAroundY(1);
            }


        if (e.getY()+5<oldYMousePosition)
            for (Shape3D tmp: getCopyShape3DS())
            {
                tmp.addTransformAroundX(-1);
            }
        else
        if (e.getY()-5>oldYMousePosition)
            for (Shape3D tmp: getCopyShape3DS()) {
                tmp.addTransformAroundX(1);
            }

        oldXMousePosition=e.getX();
        oldYMousePosition=e.getY();


    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

    }

    /**
     * Gets light power.
     *
     * @return the light power
     */
    public int getLightPower() {
        return lightPower;
    }

    /**
     * Sets light power.
     *
     * @param lightPower the light power
     */
    public void setLightPower(int lightPower) {
        this.lightPower = lightPower;
    }

    @Override
    public Object get() {
        return this;
    }


    /**
     * The type Shape array.
     */
    class ShapeArray extends ArrayList<Shape3D>
    {
        private boolean lock;

        /**
         * Instantiates a new Shape array.
         *
         * @param lock the lock
         */
        public ShapeArray(boolean lock)
        {
            super();
            this.lock = lock;
        }

        /**
         * Is lock boolean.
         *
         * @return the boolean
         */
        public boolean isLock()
        {

            return lock;
        }

        /**
         * Sets lock.
         *
         * @param lock the lock
         */
        public void setLock(boolean lock) {
            this.lock = lock;
        }

        @Override
        public Iterator<Shape3D> iterator()
        {
            if (!lock)
                return super.iterator();

             return new Iterator<Shape3D>() {
                 @Override
                 public boolean hasNext() {
                     return false;
                 }

                 @Override
                 public Shape3D next() {
                     return null;
                 }
             };

        }

        /**
         * Copy shape array.
         *
         * @return the shape array
         */
        public ShapeArray copy()
        {
            ShapeArray shape3DS1= new ShapeArray(false);

            for (int i =0;i<this.size();i++)
                shape3DS1.add((Shape3D) this.get(i).copy());

            return shape3DS1;

        }


    }

    /**
     * Add handler.
     *
     * @param handler the handler
     */
    public void addHandler(Handler handler)
    {
            logger.addHandler(handler);
    }

}
