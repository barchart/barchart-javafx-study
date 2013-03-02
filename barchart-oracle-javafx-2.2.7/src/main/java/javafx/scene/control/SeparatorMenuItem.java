/*    */ package javafx.scene.control;
/*    */ 
/*    */ import javafx.collections.ObservableList;
/*    */ import javafx.geometry.Orientation;
/*    */ 
/*    */ public class SeparatorMenuItem extends CustomMenuItem
/*    */ {
/*    */   private static final String DEFAULT_STYLE_CLASS = "separator-menu-item";
/*    */ 
/*    */   public SeparatorMenuItem()
/*    */   {
/* 63 */     super(new Separator(Orientation.HORIZONTAL), false);
/* 64 */     getStyleClass().add("separator-menu-item");
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.SeparatorMenuItem
 * JD-Core Version:    0.6.2
 */