/*    */ package com.sun.javafx.robot;
/*    */ 
/*    */ import com.sun.javafx.robot.impl.BaseFXRobot;
/*    */ import javafx.scene.Scene;
/*    */ 
/*    */ public class FXRobotFactory
/*    */ {
/*    */   public static FXRobot createRobot(Scene paramScene)
/*    */   {
/* 56 */     return new BaseFXRobot(paramScene);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.robot.FXRobotFactory
 * JD-Core Version:    0.6.2
 */