/*    */ package javafx.scene.control;
/*    */ 
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public final class IndexRangeBuilder
/*    */   implements Builder<IndexRange>
/*    */ {
/*    */   private int end;
/*    */   private int start;
/*    */ 
/*    */   public static IndexRangeBuilder create()
/*    */   {
/* 15 */     return new IndexRangeBuilder();
/*    */   }
/*    */ 
/*    */   public IndexRangeBuilder end(int paramInt)
/*    */   {
/* 23 */     this.end = paramInt;
/* 24 */     return this;
/*    */   }
/*    */ 
/*    */   public IndexRangeBuilder start(int paramInt)
/*    */   {
/* 32 */     this.start = paramInt;
/* 33 */     return this;
/*    */   }
/*    */ 
/*    */   public IndexRange build()
/*    */   {
/* 40 */     IndexRange localIndexRange = new IndexRange(this.start, this.end);
/* 41 */     return localIndexRange;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.IndexRangeBuilder
 * JD-Core Version:    0.6.2
 */