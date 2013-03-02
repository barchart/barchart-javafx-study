/*     */ package javafx.scene.control;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.geometry.Side;
/*     */ import javafx.util.Builder;
/*     */ 
/*     */ public class TabPaneBuilder<B extends TabPaneBuilder<B>> extends ControlBuilder<B>
/*     */   implements Builder<TabPane>
/*     */ {
/*     */   private int __set;
/*     */   private boolean rotateGraphic;
/*     */   private SingleSelectionModel<Tab> selectionModel;
/*     */   private Side side;
/*     */   private TabPane.TabClosingPolicy tabClosingPolicy;
/*     */   private double tabMaxHeight;
/*     */   private double tabMaxWidth;
/*     */   private double tabMinHeight;
/*     */   private double tabMinWidth;
/*     */   private Collection<? extends Tab> tabs;
/*     */ 
/*     */   public static TabPaneBuilder<?> create()
/*     */   {
/*  15 */     return new TabPaneBuilder();
/*     */   }
/*     */ 
/*     */   private void __set(int paramInt)
/*     */   {
/*  20 */     this.__set |= 1 << paramInt;
/*     */   }
/*     */   public void applyTo(TabPane paramTabPane) {
/*  23 */     super.applyTo(paramTabPane);
/*  24 */     int i = this.__set;
/*  25 */     while (i != 0) {
/*  26 */       int j = Integer.numberOfTrailingZeros(i);
/*  27 */       i &= (1 << j ^ 0xFFFFFFFF);
/*  28 */       switch (j) { case 0:
/*  29 */         paramTabPane.setRotateGraphic(this.rotateGraphic); break;
/*     */       case 1:
/*  30 */         paramTabPane.setSelectionModel(this.selectionModel); break;
/*     */       case 2:
/*  31 */         paramTabPane.setSide(this.side); break;
/*     */       case 3:
/*  32 */         paramTabPane.setTabClosingPolicy(this.tabClosingPolicy); break;
/*     */       case 4:
/*  33 */         paramTabPane.setTabMaxHeight(this.tabMaxHeight); break;
/*     */       case 5:
/*  34 */         paramTabPane.setTabMaxWidth(this.tabMaxWidth); break;
/*     */       case 6:
/*  35 */         paramTabPane.setTabMinHeight(this.tabMinHeight); break;
/*     */       case 7:
/*  36 */         paramTabPane.setTabMinWidth(this.tabMinWidth); break;
/*     */       case 8:
/*  37 */         paramTabPane.getTabs().addAll(this.tabs);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public B rotateGraphic(boolean paramBoolean)
/*     */   {
/*  48 */     this.rotateGraphic = paramBoolean;
/*  49 */     __set(0);
/*  50 */     return this;
/*     */   }
/*     */ 
/*     */   public B selectionModel(SingleSelectionModel<Tab> paramSingleSelectionModel)
/*     */   {
/*  59 */     this.selectionModel = paramSingleSelectionModel;
/*  60 */     __set(1);
/*  61 */     return this;
/*     */   }
/*     */ 
/*     */   public B side(Side paramSide)
/*     */   {
/*  70 */     this.side = paramSide;
/*  71 */     __set(2);
/*  72 */     return this;
/*     */   }
/*     */ 
/*     */   public B tabClosingPolicy(TabPane.TabClosingPolicy paramTabClosingPolicy)
/*     */   {
/*  81 */     this.tabClosingPolicy = paramTabClosingPolicy;
/*  82 */     __set(3);
/*  83 */     return this;
/*     */   }
/*     */ 
/*     */   public B tabMaxHeight(double paramDouble)
/*     */   {
/*  92 */     this.tabMaxHeight = paramDouble;
/*  93 */     __set(4);
/*  94 */     return this;
/*     */   }
/*     */ 
/*     */   public B tabMaxWidth(double paramDouble)
/*     */   {
/* 103 */     this.tabMaxWidth = paramDouble;
/* 104 */     __set(5);
/* 105 */     return this;
/*     */   }
/*     */ 
/*     */   public B tabMinHeight(double paramDouble)
/*     */   {
/* 114 */     this.tabMinHeight = paramDouble;
/* 115 */     __set(6);
/* 116 */     return this;
/*     */   }
/*     */ 
/*     */   public B tabMinWidth(double paramDouble)
/*     */   {
/* 125 */     this.tabMinWidth = paramDouble;
/* 126 */     __set(7);
/* 127 */     return this;
/*     */   }
/*     */ 
/*     */   public B tabs(Collection<? extends Tab> paramCollection)
/*     */   {
/* 136 */     this.tabs = paramCollection;
/* 137 */     __set(8);
/* 138 */     return this;
/*     */   }
/*     */ 
/*     */   public B tabs(Tab[] paramArrayOfTab)
/*     */   {
/* 145 */     return tabs(Arrays.asList(paramArrayOfTab));
/*     */   }
/*     */ 
/*     */   public TabPane build()
/*     */   {
/* 152 */     TabPane localTabPane = new TabPane();
/* 153 */     applyTo(localTabPane);
/* 154 */     return localTabPane;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.TabPaneBuilder
 * JD-Core Version:    0.6.2
 */