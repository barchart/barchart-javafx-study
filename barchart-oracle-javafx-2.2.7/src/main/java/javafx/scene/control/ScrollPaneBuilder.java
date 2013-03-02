/*     */ package javafx.scene.control;
/*     */ 
/*     */ import javafx.geometry.Bounds;
/*     */ import javafx.scene.Node;
/*     */ import javafx.util.Builder;
/*     */ 
/*     */ public class ScrollPaneBuilder<B extends ScrollPaneBuilder<B>> extends ControlBuilder<B>
/*     */   implements Builder<ScrollPane>
/*     */ {
/*     */   private int __set;
/*     */   private Node content;
/*     */   private boolean fitToHeight;
/*     */   private boolean fitToWidth;
/*     */   private ScrollPane.ScrollBarPolicy hbarPolicy;
/*     */   private double hmax;
/*     */   private double hmin;
/*     */   private double hvalue;
/*     */   private boolean pannable;
/*     */   private double prefViewportHeight;
/*     */   private double prefViewportWidth;
/*     */   private ScrollPane.ScrollBarPolicy vbarPolicy;
/*     */   private Bounds viewportBounds;
/*     */   private double vmax;
/*     */   private double vmin;
/*     */   private double vvalue;
/*     */ 
/*     */   public static ScrollPaneBuilder<?> create()
/*     */   {
/*  15 */     return new ScrollPaneBuilder();
/*     */   }
/*     */ 
/*     */   private void __set(int paramInt)
/*     */   {
/*  20 */     this.__set |= 1 << paramInt;
/*     */   }
/*     */   public void applyTo(ScrollPane paramScrollPane) {
/*  23 */     super.applyTo(paramScrollPane);
/*  24 */     int i = this.__set;
/*  25 */     while (i != 0) {
/*  26 */       int j = Integer.numberOfTrailingZeros(i);
/*  27 */       i &= (1 << j ^ 0xFFFFFFFF);
/*  28 */       switch (j) { case 0:
/*  29 */         paramScrollPane.setContent(this.content); break;
/*     */       case 1:
/*  30 */         paramScrollPane.setFitToHeight(this.fitToHeight); break;
/*     */       case 2:
/*  31 */         paramScrollPane.setFitToWidth(this.fitToWidth); break;
/*     */       case 3:
/*  32 */         paramScrollPane.setHbarPolicy(this.hbarPolicy); break;
/*     */       case 4:
/*  33 */         paramScrollPane.setHmax(this.hmax); break;
/*     */       case 5:
/*  34 */         paramScrollPane.setHmin(this.hmin); break;
/*     */       case 6:
/*  35 */         paramScrollPane.setHvalue(this.hvalue); break;
/*     */       case 7:
/*  36 */         paramScrollPane.setPannable(this.pannable); break;
/*     */       case 8:
/*  37 */         paramScrollPane.setPrefViewportHeight(this.prefViewportHeight); break;
/*     */       case 9:
/*  38 */         paramScrollPane.setPrefViewportWidth(this.prefViewportWidth); break;
/*     */       case 10:
/*  39 */         paramScrollPane.setVbarPolicy(this.vbarPolicy); break;
/*     */       case 11:
/*  40 */         paramScrollPane.setViewportBounds(this.viewportBounds); break;
/*     */       case 12:
/*  41 */         paramScrollPane.setVmax(this.vmax); break;
/*     */       case 13:
/*  42 */         paramScrollPane.setVmin(this.vmin); break;
/*     */       case 14:
/*  43 */         paramScrollPane.setVvalue(this.vvalue);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public B content(Node paramNode)
/*     */   {
/*  54 */     this.content = paramNode;
/*  55 */     __set(0);
/*  56 */     return this;
/*     */   }
/*     */ 
/*     */   public B fitToHeight(boolean paramBoolean)
/*     */   {
/*  65 */     this.fitToHeight = paramBoolean;
/*  66 */     __set(1);
/*  67 */     return this;
/*     */   }
/*     */ 
/*     */   public B fitToWidth(boolean paramBoolean)
/*     */   {
/*  76 */     this.fitToWidth = paramBoolean;
/*  77 */     __set(2);
/*  78 */     return this;
/*     */   }
/*     */ 
/*     */   public B hbarPolicy(ScrollPane.ScrollBarPolicy paramScrollBarPolicy)
/*     */   {
/*  87 */     this.hbarPolicy = paramScrollBarPolicy;
/*  88 */     __set(3);
/*  89 */     return this;
/*     */   }
/*     */ 
/*     */   public B hmax(double paramDouble)
/*     */   {
/*  98 */     this.hmax = paramDouble;
/*  99 */     __set(4);
/* 100 */     return this;
/*     */   }
/*     */ 
/*     */   public B hmin(double paramDouble)
/*     */   {
/* 109 */     this.hmin = paramDouble;
/* 110 */     __set(5);
/* 111 */     return this;
/*     */   }
/*     */ 
/*     */   public B hvalue(double paramDouble)
/*     */   {
/* 120 */     this.hvalue = paramDouble;
/* 121 */     __set(6);
/* 122 */     return this;
/*     */   }
/*     */ 
/*     */   public B pannable(boolean paramBoolean)
/*     */   {
/* 131 */     this.pannable = paramBoolean;
/* 132 */     __set(7);
/* 133 */     return this;
/*     */   }
/*     */ 
/*     */   public B prefViewportHeight(double paramDouble)
/*     */   {
/* 142 */     this.prefViewportHeight = paramDouble;
/* 143 */     __set(8);
/* 144 */     return this;
/*     */   }
/*     */ 
/*     */   public B prefViewportWidth(double paramDouble)
/*     */   {
/* 153 */     this.prefViewportWidth = paramDouble;
/* 154 */     __set(9);
/* 155 */     return this;
/*     */   }
/*     */ 
/*     */   public B vbarPolicy(ScrollPane.ScrollBarPolicy paramScrollBarPolicy)
/*     */   {
/* 164 */     this.vbarPolicy = paramScrollBarPolicy;
/* 165 */     __set(10);
/* 166 */     return this;
/*     */   }
/*     */ 
/*     */   public B viewportBounds(Bounds paramBounds)
/*     */   {
/* 175 */     this.viewportBounds = paramBounds;
/* 176 */     __set(11);
/* 177 */     return this;
/*     */   }
/*     */ 
/*     */   public B vmax(double paramDouble)
/*     */   {
/* 186 */     this.vmax = paramDouble;
/* 187 */     __set(12);
/* 188 */     return this;
/*     */   }
/*     */ 
/*     */   public B vmin(double paramDouble)
/*     */   {
/* 197 */     this.vmin = paramDouble;
/* 198 */     __set(13);
/* 199 */     return this;
/*     */   }
/*     */ 
/*     */   public B vvalue(double paramDouble)
/*     */   {
/* 208 */     this.vvalue = paramDouble;
/* 209 */     __set(14);
/* 210 */     return this;
/*     */   }
/*     */ 
/*     */   public ScrollPane build()
/*     */   {
/* 217 */     ScrollPane localScrollPane = new ScrollPane();
/* 218 */     applyTo(localScrollPane);
/* 219 */     return localScrollPane;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.ScrollPaneBuilder
 * JD-Core Version:    0.6.2
 */