/*    */ package javafx.scene.effect;
/*    */ 
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class FloatMapBuilder<B extends FloatMapBuilder<B>>
/*    */   implements Builder<FloatMap>
/*    */ {
/*    */   private int __set;
/*    */   private int height;
/*    */   private int width;
/*    */ 
/*    */   public static FloatMapBuilder<?> create()
/*    */   {
/* 15 */     return new FloatMapBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(FloatMap paramFloatMap)
/*    */   {
/* 20 */     int i = this.__set;
/* 21 */     if ((i & 0x1) != 0) paramFloatMap.setHeight(this.height);
/* 22 */     if ((i & 0x2) != 0) paramFloatMap.setWidth(this.width);
/*    */   }
/*    */ 
/*    */   public B height(int paramInt)
/*    */   {
/* 31 */     this.height = paramInt;
/* 32 */     this.__set |= 1;
/* 33 */     return this;
/*    */   }
/*    */ 
/*    */   public B width(int paramInt)
/*    */   {
/* 42 */     this.width = paramInt;
/* 43 */     this.__set |= 2;
/* 44 */     return this;
/*    */   }
/*    */ 
/*    */   public FloatMap build()
/*    */   {
/* 51 */     FloatMap localFloatMap = new FloatMap();
/* 52 */     applyTo(localFloatMap);
/* 53 */     return localFloatMap;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.effect.FloatMapBuilder
 * JD-Core Version:    0.6.2
 */