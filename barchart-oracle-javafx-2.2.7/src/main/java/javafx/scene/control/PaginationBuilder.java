/*    */ package javafx.scene.control;
/*    */ 
/*    */ import javafx.scene.Node;
/*    */ import javafx.util.Builder;
/*    */ import javafx.util.Callback;
/*    */ 
/*    */ public class PaginationBuilder<B extends PaginationBuilder<B>> extends ControlBuilder<B>
/*    */   implements Builder<Pagination>
/*    */ {
/*    */   private int __set;
/*    */   private int currentPageIndex;
/*    */   private int maxPageIndicatorCount;
/*    */   private int pageCount;
/*    */   private Callback<Integer, Node> pageFactory;
/*    */ 
/*    */   public static PaginationBuilder<?> create()
/*    */   {
/* 15 */     return new PaginationBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(Pagination paramPagination)
/*    */   {
/* 20 */     super.applyTo(paramPagination);
/* 21 */     int i = this.__set;
/* 22 */     if ((i & 0x1) != 0) paramPagination.setCurrentPageIndex(this.currentPageIndex);
/* 23 */     if ((i & 0x2) != 0) paramPagination.setMaxPageIndicatorCount(this.maxPageIndicatorCount);
/* 24 */     if ((i & 0x4) != 0) paramPagination.setPageCount(this.pageCount);
/* 25 */     if ((i & 0x8) != 0) paramPagination.setPageFactory(this.pageFactory);
/*    */   }
/*    */ 
/*    */   public B currentPageIndex(int paramInt)
/*    */   {
/* 34 */     this.currentPageIndex = paramInt;
/* 35 */     this.__set |= 1;
/* 36 */     return this;
/*    */   }
/*    */ 
/*    */   public B maxPageIndicatorCount(int paramInt)
/*    */   {
/* 45 */     this.maxPageIndicatorCount = paramInt;
/* 46 */     this.__set |= 2;
/* 47 */     return this;
/*    */   }
/*    */ 
/*    */   public B pageCount(int paramInt)
/*    */   {
/* 56 */     this.pageCount = paramInt;
/* 57 */     this.__set |= 4;
/* 58 */     return this;
/*    */   }
/*    */ 
/*    */   public B pageFactory(Callback<Integer, Node> paramCallback)
/*    */   {
/* 67 */     this.pageFactory = paramCallback;
/* 68 */     this.__set |= 8;
/* 69 */     return this;
/*    */   }
/*    */ 
/*    */   public Pagination build()
/*    */   {
/* 76 */     Pagination localPagination = new Pagination();
/* 77 */     applyTo(localPagination);
/* 78 */     return localPagination;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.PaginationBuilder
 * JD-Core Version:    0.6.2
 */