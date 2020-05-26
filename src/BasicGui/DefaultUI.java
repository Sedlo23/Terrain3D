package BasicGui;

import javax.swing.*;

import Geometry3D.Point3D;
import Geometry3D.Triangle3D;
import Logs3D.LOG_LEVEL;
import UI3D.*;

import java.awt.event.WindowEvent;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * sedloLAA
 *
 * @author Jan Frantisek Sedlacek
 * @version 1.0
 * @since 02.09.19
 */
public class DefaultUI
{

    private static jPanel3D jPanel3D;
    private JPanel MainPanel;
    private JPanel DrawPanelGUI;
    /**
     * The Logger.
     */
    static Logger logger = Logger.getLogger(DefaultUI.class.getName());
    private static LOG_LEVEL log_level=new LOG_LEVEL();

    /**
     * Instantiates a new Default ui.
     */
    public DefaultUI() {
        logger.setLevel(Level.ALL);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        handler.setFormatter(new SimpleFormatter());
        logger.addHandler(handler);

    }

    /**
     * Main.
     *
     * @param args the args
     */
    public static void main(String[] args)
    {
        SimpleFormatter formatter=new SimpleFormatter();


                System.setProperty("java.util.logging.SimpleFormatter.format",
                "\033[32m[%4$-7s] \033[34m[%1$tT] \033[33m%5$s %n");
                DefaultUI defaultUI =new DefaultUI();
                loadArgs(args);
                JFrame frame = new JFrame("Sedlo3D");
                frame.setContentPane(defaultUI.getMainPanel());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
                frame.addKeyListener(getjPanel3D());
                frame.addMouseMotionListener(getjPanel3D());

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(WindowEvent winEvt) {

                logger.log( log_level.guiInfo, "\u001B[0mGUI EXIT");
                System.exit(0);
            }
        });




        logger.log( log_level.guiInfo, "GUI running");
    }

    /**
     * Gets panel 3 d.
     *
     * @return the panel 3 d
     */
    public static UI3D.jPanel3D getjPanel3D() {
        return jPanel3D;
    }

    /**
     * Sets panel 3 d.
     *
     * @param jPanel3D the j panel 3 d
     */
    public static void setjPanel3D(UI3D.jPanel3D jPanel3D) {
        DefaultUI.jPanel3D = jPanel3D;
    }

    private void createUIComponents()
    {
                    logger.log( log_level.guiInfo, "UI creating");
                    setDrawPanelGUI(new jPanel3D(new Point3D(0,0,0)));
                    setjPanel3D((jPanel3D) getDrawPanelGUI());
    }

    /**
     * Load args.
     *
     * @param args the args
     */
    public static void loadArgs(String[] args)
    {


        logger.log( log_level.guiInfo, "Args loading");

        SimpleFormatter format= new SimpleFormatter();

        if (args.length==0)
        {
            System.out.print("for help use --help argument");
            System.exit(0);

        }

        for (int i = 0; i < args.length; ++i) {
            switch (args[i]) {
                case "--test":
                {
                    getjPanel3D().setScene3D(new Scene3D(new Point3D(0,0,0)));
                    args = new String[]{"","--terrain","15","50","50","500","--lightpower","1000"};
                    i=0;
                    logger.log( log_level.guiInfo, "Setting testing scenario");
                    break;
                }
                case "--guilog":
                {
                    ConsoleHandler handler = new ConsoleHandler();
                    handler.setLevel(log_level.guiInfo);
                    handler.setFormatter(format);
                    getjPanel3D().addHandler(handler);
                    logger.log( log_level.guiInfo, "GUI logging sets");
                    break;
                }
                case "--calculationlog":
                {
                    ConsoleHandler handler = new ConsoleHandler();
                    handler.setLevel(log_level.calculation);
                    handler.setFormatter(format);
                    getjPanel3D().addHandler(handler);
                    logger.log( log_level.calculation, "Calulation logging sets");
                    break;
                }
                case "--scenechangelog":
                {
                    ConsoleHandler handler = new ConsoleHandler();
                    handler.setLevel(log_level.sceneMove);
                    handler.setFormatter(format);
                    getjPanel3D().addHandler(handler);
                    logger.log( log_level.sceneMove, "Scene logging sets");
                    break;
                }
                case "--errors":
                {
                    ConsoleHandler handler = new ConsoleHandler();
                    handler.setLevel(log_level.guiInfo);
                    handler.setFormatter(format);
                    getjPanel3D().addHandler(handler);
                    logger.log(log_level.guiInfo, "Errors logging sets");
                    break;
                }
                case "--warnings":
                {
                    ConsoleHandler handler = new ConsoleHandler();
                    handler.setLevel(log_level.warnings);
                    handler.setFormatter(format);
                    getjPanel3D().addHandler(handler);
                    logger.log( log_level.warnings, "Warning logging sets");
                    break;
                }
                case "--all":
                {
                    ConsoleHandler handler = new ConsoleHandler();
                    handler.setLevel(Level.ALL);
                    handler.setFormatter(format);
                    getjPanel3D().addHandler(handler);
                    logger.log(Level.ALL, "All logging sets");
                    break;
                }
                case "--lightpower":
                {
                    getjPanel3D().getScene3D().setLightPower(Integer.parseInt(args[i+1]));
                    logger.log( log_level.guiInfo, "Light power set to: "+Integer.parseInt(args[i+1]));
                    i+=1;
                    break;
                }
                case "--terrain":
                {
                    getjPanel3D().getScene3D().addTerrain(
                            Integer.parseInt(args[i+1]),
                            Integer.parseInt(args[i+2]),
                            Integer.parseInt(args[i+3]),
                            Integer.parseInt(args[i+4]));


                    logger.log( log_level.guiInfo, "New terrain: " +
                            "size="+ Integer.parseInt(args[i+1])+";"+
                            "depth="+ Integer.parseInt(args[i+2])+";"+
                            "gridSize="+Integer.parseInt(args[i+3])+";"+
                            "range="+ Integer.parseInt(args[i+4])+";");
                    i+=4;
                    break;
                }
                case "--sphere":
                {

                    getjPanel3D().getScene3D().addSphere(
                            Integer.parseInt(args[i+1]),
                            Integer.parseInt(args[i+2]),
                            Integer.parseInt(args[i+3]),
                            Integer.parseInt(args[i+4]),
                            Integer.parseInt(args[i+5]));
                    logger.log( log_level.guiInfo, "New sphere: " +
                            "r="+ Integer.parseInt(args[i+1])+";"+
                            "step="+ Integer.parseInt(args[i+2])+";"+
                            "x="+Integer.parseInt(args[i+3])+";"+
                            "y="+ Integer.parseInt(args[i+4])+";"+
                            "z="+ Integer.parseInt(args[i+5])+";");
                    i+=5;
                    break;
                }
                case "--movement":
                {
                    getjPanel3D().getScene3D().setMovementSpeed(Double.parseDouble(args[i+1]));
                    logger.log( log_level.guiInfo, "movement set to "+Double.parseDouble(args[i+1]));
                    i+=1;
                    break;
                }
                case "--lightposition":
                {
                    getjPanel3D().getScene3D().setStaticLight(
                            new Triangle3D(
                                    new Point3D(
                                        Integer.parseInt(args[i+1]),
                                        Integer.parseInt(args[i+2]),
                                        Integer.parseInt(args[i+3])),
                                    new Point3D(
                                            Integer.parseInt(args[i+1]),
                                            Integer.parseInt(args[i+2]),
                                            Integer.parseInt(args[i+3])),
                                    new Point3D(
                                            Integer.parseInt(args[i+1]),
                                            Integer.parseInt(args[i+2]),
                                            Integer.parseInt(args[i+3]))
                    ));

                    logger.log( log_level.guiInfo, "new Static light position: " +
                                    "x="+ Integer.parseInt(args[i+1])+";"+
                                    "y="+ Integer.parseInt(args[i+2])+";"+
                                    "z="+Integer.parseInt(args[i+3]));
                    i+=3;
                    break;
                }
                case "--staticlight":
                {
                    getjPanel3D().getScene3D().setStaticLightEnable(true);
                    logger.log( log_level.guiInfo, "Static light enable");

                    break;
                }
                case "--ex":
                {
                    getjPanel3D().setExperimental(true);
                    logger.log( log_level.guiInfo, "Experimental processing enable");
                    break;
                }
                case "--help":
                {
                    System.out.println("Sedlo 3D viewer");
                    System.out.println("2020");
                    System.out.println("GPLv2");
                    System.out.println("Jan Frantisek \"sedlo\" Sedlacek");
                    System.out.println("[janfrantiseksedlacek@gmail.com]");
                    System.out.println();
                    System.out.println("------ Arguments ------");
                    System.out.println("    --test ..... automatic setting");
                    System.out.println("    --staticlight ..... set light source not to be camera");
                    System.out.println("    --sphere [r] [Drawing step size] [x] [y] [z] ..... draw sphere");
                    System.out.println("    --terrain [size] [init height] [grid size] [Height range] ..... create terrain");
                    System.out.println("    --lightpower [power] ..... set light power");
                    System.out.println("    --movement [speed] ..... set movement speed");
                    System.out.println("    --lightposition [x] [y] [z] ..... set static light position");
                    System.out.println("    --readfile [filename] ..... read file");
                    System.out.println("Format example ");
                    System.out.println("    [number of 3D points]");
                    System.out.println("    [x] [y] [z]");
                    System.out.println("    ... ");
                    System.out.println("    [number of 3D triangles]");
                    System.out.println("    [index of point 1] [index of point 2 ] [index of point 3]");
                    System.out.println("    ... ");
                    System.out.println("example:");
                    System.out.println("    4");
                    System.out.println("    0 0 0");
                    System.out.println("    1 0 0");
                    System.out.println("    1 1 0");
                    System.out.println("    1 1 1");
                    System.out.println("    32");
                    System.out.println("    1 2 3");
                    System.out.println("    1 1 2");
                    System.out.println("    2 3 0");
                    System.out.println();
                    System.out.println("------ Control ------");
                    System.out.println("    W ... movement z+");
                    System.out.println("    S ... movement z-");
                    System.out.println("    A ... movement y+");
                    System.out.println("    D ... movement y-");
                    System.out.println("    Q ... movement x+");
                    System.out.println("    E ... movement x-");
                    System.out.println("    P ... rotation x+");
                    System.out.println("    O ... rotation x-");
                    System.out.println("    P ... rotation y+");
                    System.out.println("    O ... rotation y-");
                    System.out.println("    I ... rotation z+");
                    System.out.println("    U ... rotation z-");
                    System.out.println("    R ... reset panel");
                    System.out.println("    N ... speed++");
                    System.out.println("    M ... speed-- (speed can be negative in that case logic of keys is =*-1 )");
                    System.out.println("    Mouse dragging .... rotation");
                    System.out.println("------ Experimental ------");
                    System.out.println("    --ex ... enable experimental view");
                    System.out.println("    can be handful for larger data");
                    System.out.println("    using background worker");
                    System.out.println("    if program do not render right press R to recalculate view");
                    System.out.println("    pressing R key usually resolve bad projection calculation");
                    System.out.println("    !!! EXPERIMENTAL VIEW IS NOT FULLY TESTED AND DOES NOT WORK CORRECTLY !!!");
                    System.exit(0);

                }
                case "--readfile":
                {
                    logger.log( log_level.guiInfo, "Lokking for file:"+ args[i+1]);
                    getjPanel3D().getScene3D().readFile( args[i+1]);
                    i+=1;
                    break;

                }
                default:
                {
                    logger.log( Level.WARNING, "Wrong argument ["+args[i]+"]");
                    break;
                }

            }
        }

    }

    /**
     * Gets main panel.
     *
     * @return the main panel
     */
    public JPanel getMainPanel() {
        return MainPanel;
    }

    /**
     * Sets main panel.
     *
     * @param mainPanel the main panel
     */
    public void setMainPanel(JPanel mainPanel) {
        MainPanel = mainPanel;
    }

    /**
     * Gets draw panel gui.
     *
     * @return the draw panel gui
     */
    public JPanel getDrawPanelGUI() {
        return DrawPanelGUI;
    }

    /**
     * Sets draw panel gui.
     *
     * @param drawPanelGUI the draw panel gui
     */
    public void setDrawPanelGUI(JPanel drawPanelGUI) {
        DrawPanelGUI = drawPanelGUI;
    }




}
