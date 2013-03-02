/*     */ package javafx.stage;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.scene.Scene;
/*     */ import javafx.scene.image.Image;
/*     */ import javafx.util.Builder;
/*     */ 
/*     */ public class StageBuilder<B extends StageBuilder<B>> extends WindowBuilder<B>
/*     */   implements Builder<Stage>
/*     */ {
/*     */   private int __set;
/*     */   private boolean fullScreen;
/*     */   private boolean iconified;
/*     */   private Collection<? extends Image> icons;
/*     */   private double maxHeight;
/*     */   private double maxWidth;
/*     */   private double minHeight;
/*     */   private double minWidth;
/*     */   private boolean resizable;
/*     */   private Scene scene;
/* 149 */   private StageStyle style = StageStyle.DECORATED;
/*     */   private String title;
/*     */ 
/*     */   public static StageBuilder<?> create()
/*     */   {
/*  15 */     return new StageBuilder();
/*     */   }
/*     */ 
/*     */   private void __set(int paramInt)
/*     */   {
/*  20 */     this.__set |= 1 << paramInt;
/*     */   }
/*     */   public void applyTo(Stage paramStage) {
/*  23 */     super.applyTo(paramStage);
/*  24 */     int i = this.__set;
/*  25 */     while (i != 0) {
/*  26 */       int j = Integer.numberOfTrailingZeros(i);
/*  27 */       i &= (1 << j ^ 0xFFFFFFFF);
/*  28 */       switch (j) { case 0:
/*  29 */         paramStage.setFullScreen(this.fullScreen); break;
/*     */       case 1:
/*  30 */         paramStage.setIconified(this.iconified); break;
/*     */       case 2:
/*  31 */         paramStage.getIcons().addAll(this.icons); break;
/*     */       case 3:
/*  32 */         paramStage.setMaxHeight(this.maxHeight); break;
/*     */       case 4:
/*  33 */         paramStage.setMaxWidth(this.maxWidth); break;
/*     */       case 5:
/*  34 */         paramStage.setMinHeight(this.minHeight); break;
/*     */       case 6:
/*  35 */         paramStage.setMinWidth(this.minWidth); break;
/*     */       case 7:
/*  36 */         paramStage.setResizable(this.resizable); break;
/*     */       case 8:
/*  37 */         paramStage.setScene(this.scene); break;
/*     */       case 9:
/*  38 */         paramStage.setTitle(this.title);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public B fullScreen(boolean paramBoolean)
/*     */   {
/*  49 */     this.fullScreen = paramBoolean;
/*  50 */     __set(0);
/*  51 */     return this;
/*     */   }
/*     */ 
/*     */   public B iconified(boolean paramBoolean)
/*     */   {
/*  60 */     this.iconified = paramBoolean;
/*  61 */     __set(1);
/*  62 */     return this;
/*     */   }
/*     */ 
/*     */   public B icons(Collection<? extends Image> paramCollection)
/*     */   {
/*  71 */     this.icons = paramCollection;
/*  72 */     __set(2);
/*  73 */     return this;
/*     */   }
/*     */ 
/*     */   public B icons(Image[] paramArrayOfImage)
/*     */   {
/*  80 */     return icons(Arrays.asList(paramArrayOfImage));
/*     */   }
/*     */ 
/*     */   public B maxHeight(double paramDouble)
/*     */   {
/*  89 */     this.maxHeight = paramDouble;
/*  90 */     __set(3);
/*  91 */     return this;
/*     */   }
/*     */ 
/*     */   public B maxWidth(double paramDouble)
/*     */   {
/* 100 */     this.maxWidth = paramDouble;
/* 101 */     __set(4);
/* 102 */     return this;
/*     */   }
/*     */ 
/*     */   public B minHeight(double paramDouble)
/*     */   {
/* 111 */     this.minHeight = paramDouble;
/* 112 */     __set(5);
/* 113 */     return this;
/*     */   }
/*     */ 
/*     */   public B minWidth(double paramDouble)
/*     */   {
/* 122 */     this.minWidth = paramDouble;
/* 123 */     __set(6);
/* 124 */     return this;
/*     */   }
/*     */ 
/*     */   public B resizable(boolean paramBoolean)
/*     */   {
/* 133 */     this.resizable = paramBoolean;
/* 134 */     __set(7);
/* 135 */     return this;
/*     */   }
/*     */ 
/*     */   public B scene(Scene paramScene)
/*     */   {
/* 144 */     this.scene = paramScene;
/* 145 */     __set(8);
/* 146 */     return this;
/*     */   }
/*     */ 
/*     */   public B style(StageStyle paramStageStyle)
/*     */   {
/* 155 */     this.style = paramStageStyle;
/* 156 */     return this;
/*     */   }
/*     */ 
/*     */   public B title(String paramString)
/*     */   {
/* 165 */     this.title = paramString;
/* 166 */     __set(9);
/* 167 */     return this;
/*     */   }
/*     */ 
/*     */   public Stage build()
/*     */   {
/* 174 */     Stage localStage = new Stage(this.style);
/* 175 */     applyTo(localStage);
/* 176 */     return localStage;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.stage.StageBuilder
 * JD-Core Version:    0.6.2
 */