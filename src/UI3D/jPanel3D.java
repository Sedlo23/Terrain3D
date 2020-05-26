package UI3D;

import Geometry3D.Point3D;
import Geometry3D.Shape3D;
import Logs3D.LOG_LEVEL;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Supplier;
import java.util.logging.*;


/**
 * The type J panel 3 d.
 */
public class jPanel3D extends JPanel implements KeyListener, MouseMotionListener, ActionListener, Supplier<JPanel>
{

    

   private ArrayList<BackgroundTask>backgroundTasks=new ArrayList<>();

   private  Scene3D scene3D;

   private final int REFRESH_RATE=10;

   private Timer timer =new Timer(REFRESH_RATE,this);

    /**
     * The Logger.
     */
    static Logger logger = Logger.getLogger(jPanel3D.class.getName());

   private boolean experimental;

   private boolean wharf = true;

   private LOG_LEVEL log_level=new LOG_LEVEL();

    /**
     * Instantiates a new J panel 3 d.
     *
     * @param cameraPosition the camera position
     */
    public jPanel3D(Point3D cameraPosition)
    {
        scene3D =new Scene3D(cameraPosition);
        timer.start();
        experimental=false;
        logger.setLevel(Level.ALL);
    }


    /**
     * Gets scene 3 d.
     *
     * @return the scene 3 d
     */
    public Scene3D getScene3D() {
        return scene3D;
    }

    /**
     * Sets scene 3 d.
     *
     * @param scene3D the scene 3 d
     */
    public void setScene3D(Scene3D scene3D) {
        this.scene3D = scene3D;
    }

    /**
     * Gets refresh rate.
     *
     * @return the refresh rate
     */
    public int getREFRESH_RATE() {
        return REFRESH_RATE;
    }

    /**
     * Gets timer.
     *
     * @return the timer
     */
    public Timer getTimer() {
        return timer;
    }

    /**
     * Sets timer.
     *
     * @param timer the timer
     */
    public void setTimer(Timer timer) {
        this.timer = timer;
    }


    @Override
    protected void paintComponent(Graphics g) {


        if (scene3D==null)
        return;

        scene3D.setCameraPosition(new Point3D(getWidth()/2,getHeight()/2,10));


        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;


        g2.translate(0, getHeight());
        g2.scale(1, -1);

        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_ALPHA_INTERPOLATION,
                RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);

        g2.setRenderingHints(rh);

        rh = new RenderingHints(
                RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_SPEED);


        g2.setRenderingHints(rh);

        rh = new RenderingHints(
                RenderingHints.KEY_COLOR_RENDERING,
                RenderingHints.VALUE_COLOR_RENDER_SPEED);


        g2.setRenderingHints(rh);

        if (!isExperimental())
            scene3D.paintClassic(g2);





    }

    @Override
    public void keyTyped(KeyEvent keyEvent)
    {
        scene3D.keyTyped(keyEvent);
        executeActionUpdate();

    }

    @Override
    public void keyPressed(KeyEvent e) {
            scene3D.keyPressed(e);

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
            scene3D.keyReleased(keyEvent);

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
            scene3D.mouseDragged(mouseEvent);
            executeActionUpdate();
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
            scene3D.mouseMoved(mouseEvent);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent)
    {


       if (isExperimental())
        {
            boolean sort=true;

                if (backgroundTasks.size()!=0)
                {
                    wharf =true;
                    sort = false;
                }

            if (sort&& wharf)
            {
                wharf =false;

                logger.log(log_level.experimental,"Sorting START ");
                long startTime = System.nanoTime();

                getScene3D().getShape3DS().sort(new Comparator<Shape3D>()
                {

                    @Override
                    public int compare(Shape3D shape3D, Shape3D t1)
                    {

                        if (shape3D.getCenter().getZ()<t1.getCenter().getZ())
                            return 1;
                        else if (shape3D.getCenter().getZ()>t1.getCenter().getZ())
                            return -1;

                        return 0;
                    }
                });

                long endTime = System.nanoTime();

                long duration = (endTime - startTime);
                duration/=1;
                logger.log(log_level.experimental,"Sorting END \033[0m(time= "+duration+"ns)");


            }
        }
    }

    /**
     * Execute action update.
     */
    public void  executeActionUpdate()
    {
        logger.log(log_level.guiInfo,"Light position: \033[0m" + getScene3D().getStaticLight().getCenter());


        if (experimental)
            {
                backgroundTasks.add( new BackgroundTask(this.getScene3D(),this));

                (backgroundTasks.get(backgroundTasks.size()-1)).execute();

            }
        else
            repaint();


    }

    @Override
    public JPanel get() {
        return this;
    }

    /**
     * The type Background task.
     */
    class BackgroundTask extends SwingWorker<Scene3D, Scene3D> {

        /**
         * The Scene 3 d supplier.
         */
        Supplier<Scene3D> scene3DSupplier;
        /**
         * The J panel supplier.
         */
        Supplier<JPanel> jPanelSupplier;

        /**
         * Instantiates a new Background task.
         *
         * @param scene3DSupplier the scene 3 d supplier
         * @param jPanelSupplier  the j panel supplier
         */
        BackgroundTask(Supplier<Scene3D> scene3DSupplier,Supplier<JPanel> jPanelSupplier)
        {
            logger.log(log_level.experimental,"Background worker INIT: \033[0m"+ this.toString());
            this.scene3DSupplier = scene3DSupplier;
            this.jPanelSupplier = jPanelSupplier;

        }

        @Override
        protected Scene3D doInBackground() throws Exception
        {

            logger.log(log_level.experimental,"Background worker WORKING: \033[0m"+ this.toString());
            Graphics2D g2=  ((Graphics2D)jPanelSupplier.get().getGraphics());

            g2.setColor(new Color(0,0,0,100));
            g2.fillRect(0,0,8000,8000);

            g2.translate(0, getHeight());
            g2.scale(1, -1);

            RenderingHints rh = new RenderingHints(
                    RenderingHints.KEY_ALPHA_INTERPOLATION,
                    RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);

            g2.setRenderingHints(rh);

            rh = new RenderingHints(
                    RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_SPEED);


            g2.setRenderingHints(rh);

            rh = new RenderingHints(
                    RenderingHints.KEY_COLOR_RENDERING,
                    RenderingHints.VALUE_COLOR_RENDER_SPEED);


            g2.setRenderingHints(rh);

            scene3DSupplier.get().update(g2);

            getScene3D().updateShading();


            for (Shape3D shape: scene3D.getShape3DS())
                shape.fill(g2,getScene3D().getCameraPosition());


            return scene3DSupplier.get();
        }

        @Override
        public void done()
        {
            logger.log(log_level.experimental,"Background worker done: \033[0m"+ this.toString());
            backgroundTasks.remove(this);
        }

        @Override
        public String toString() {
            return "BackgroundTask{"
                    + this.hashCode()+
                    '}';
        }
    }

    /**
     * Is experimental boolean.
     *
     * @return the boolean
     */
    public boolean isExperimental() {
        return experimental;
    }

    /**
     * Sets experimental.
     *
     * @param experimental the experimental
     */
    public void setExperimental(boolean experimental) {
        this.experimental = experimental;
    }

    /**
     * Add handler.
     *
     * @param handler the handler
     */
    public void addHandler(Handler handler)
    {

           logger.addHandler(handler);

            getScene3D().addHandler(handler);

    }
}