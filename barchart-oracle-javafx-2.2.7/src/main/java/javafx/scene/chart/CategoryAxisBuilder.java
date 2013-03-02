/*    */ package javafx.scene.chart;
/*    */ 
/*    */ import javafx.collections.ObservableList;
/*    */ 
/*    */ public final class CategoryAxisBuilder extends AxisBuilder<String, CategoryAxisBuilder>
/*    */ {
/*    */   private int __set;
/*    */   private ObservableList<String> categories;
/*    */   private double endMargin;
/*    */   private boolean gapStartAndEnd;
/*    */   private double startMargin;
/*    */ 
/*    */   public static CategoryAxisBuilder create()
/*    */   {
/* 15 */     return new CategoryAxisBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(CategoryAxis paramCategoryAxis)
/*    */   {
/* 20 */     super.applyTo(paramCategoryAxis);
/* 21 */     int i = this.__set;
/* 22 */     if ((i & 0x1) != 0) paramCategoryAxis.setCategories(this.categories);
/* 23 */     if ((i & 0x2) != 0) paramCategoryAxis.setEndMargin(this.endMargin);
/* 24 */     if ((i & 0x4) != 0) paramCategoryAxis.setGapStartAndEnd(this.gapStartAndEnd);
/* 25 */     if ((i & 0x8) != 0) paramCategoryAxis.setStartMargin(this.startMargin);
/*    */   }
/*    */ 
/*    */   public CategoryAxisBuilder categories(ObservableList<String> paramObservableList)
/*    */   {
/* 33 */     this.categories = paramObservableList;
/* 34 */     this.__set |= 1;
/* 35 */     return this;
/*    */   }
/*    */ 
/*    */   public CategoryAxisBuilder endMargin(double paramDouble)
/*    */   {
/* 43 */     this.endMargin = paramDouble;
/* 44 */     this.__set |= 2;
/* 45 */     return this;
/*    */   }
/*    */ 
/*    */   public CategoryAxisBuilder gapStartAndEnd(boolean paramBoolean)
/*    */   {
/* 53 */     this.gapStartAndEnd = paramBoolean;
/* 54 */     this.__set |= 4;
/* 55 */     return this;
/*    */   }
/*    */ 
/*    */   public CategoryAxisBuilder startMargin(double paramDouble)
/*    */   {
/* 63 */     this.startMargin = paramDouble;
/* 64 */     this.__set |= 8;
/* 65 */     return this;
/*    */   }
/*    */ 
/*    */   public CategoryAxis build()
/*    */   {
/* 72 */     CategoryAxis localCategoryAxis = new CategoryAxis();
/* 73 */     applyTo(localCategoryAxis);
/* 74 */     return localCategoryAxis;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.chart.CategoryAxisBuilder
 * JD-Core Version:    0.6.2
 */