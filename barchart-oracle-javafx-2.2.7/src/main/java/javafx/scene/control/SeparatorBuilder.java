/*    */ package javafx.scene.control;
/*    */ 
/*    */ import javafx.geometry.HPos;
/*    */ import javafx.geometry.Orientation;
/*    */ import javafx.geometry.VPos;
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class SeparatorBuilder<B extends SeparatorBuilder<B>> extends ControlBuilder<B>
/*    */   implements Builder<Separator>
/*    */ {
/*    */   private int __set;
/*    */   private HPos halignment;
/*    */   private Orientation orientation;
/*    */   private VPos valignment;
/*    */ 
/*    */   public static SeparatorBuilder<?> create()
/*    */   {
/* 15 */     return new SeparatorBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(Separator paramSeparator)
/*    */   {
/* 20 */     super.applyTo(paramSeparator);
/* 21 */     int i = this.__set;
/* 22 */     if ((i & 0x1) != 0) paramSeparator.setHalignment(this.halignment);
/* 23 */     if ((i & 0x2) != 0) paramSeparator.setOrientation(this.orientation);
/* 24 */     if ((i & 0x4) != 0) paramSeparator.setValignment(this.valignment);
/*    */   }
/*    */ 
/*    */   public B halignment(HPos paramHPos)
/*    */   {
/* 33 */     this.halignment = paramHPos;
/* 34 */     this.__set |= 1;
/* 35 */     return this;
/*    */   }
/*    */ 
/*    */   public B orientation(Orientation paramOrientation)
/*    */   {
/* 44 */     this.orientation = paramOrientation;
/* 45 */     this.__set |= 2;
/* 46 */     return this;
/*    */   }
/*    */ 
/*    */   public B valignment(VPos paramVPos)
/*    */   {
/* 55 */     this.valignment = paramVPos;
/* 56 */     this.__set |= 4;
/* 57 */     return this;
/*    */   }
/*    */ 
/*    */   public Separator build()
/*    */   {
/* 64 */     Separator localSeparator = new Separator();
/* 65 */     applyTo(localSeparator);
/* 66 */     return localSeparator;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.SeparatorBuilder
 * JD-Core Version:    0.6.2
 */