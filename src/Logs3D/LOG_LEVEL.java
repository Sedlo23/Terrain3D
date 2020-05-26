package Logs3D;

import java.util.logging.Logger;

/**
 * experimental3D
 *
 * @author Jan Frantisek Sedlacek
 * @version 1.0
 * @since 10.04.20
 */
public class LOG_LEVEL   {


   /**
    * The Experimental.
    */
   public LogLevel experimental=new LogLevel("EXPERIMENTAL",1,"\u001B[35m");
   /**
    * The Scene move.
    */
   public LogLevel sceneMove= new LogLevel(  "SCENE CHANGE",2,"\u001B[36m");
   /**
    * The Gui info.
    */
   public LogLevel guiInfo=new LogLevel(     "  GUI INFO  ",3,"\u001B[34m");
   /**
    * The Calculation.
    */
   public LogLevel calculation=new LogLevel( " CALCULATION",4,"\u001B[33m");
   /**
    * The Warnings.
    */
   public LogLevel warnings=new LogLevel(    "   WARNING  "  ,5,"\u001B[32m");
   /**
    * The Errors.
    */
   public LogLevel errors=new LogLevel(      "    ERROR   ",6,"\u001B[31m");


}

