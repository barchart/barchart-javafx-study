/*     */ package com.sun.javafx.tk.quantum;
/*     */ 
/*     */ import com.sun.javafx.sg.prism.NGNode;
/*     */ import java.util.Locale;
/*     */ import java.util.ResourceBundle;
/*     */ import javafx.animation.Animation.Status;
/*     */ import javafx.animation.FadeTransition;
/*     */ import javafx.animation.PauseTransition;
/*     */ import javafx.animation.SequentialTransition;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.event.ActionEvent;
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.geometry.Bounds;
/*     */ import javafx.geometry.Rectangle2D;
/*     */ import javafx.scene.Group;
/*     */ import javafx.scene.Scene;
/*     */ import javafx.scene.paint.Color;
/*     */ import javafx.scene.shape.Rectangle;
/*     */ import javafx.scene.text.Font;
/*     */ import javafx.scene.text.Text;
/*     */ import javafx.scene.text.TextAlignment;
/*     */ import javafx.stage.Screen;
/*     */ import javafx.util.Duration;
/*     */ 
/*     */ public class OverlayWarning
/*     */ {
/*  33 */   private static final Locale LOCALE = Locale.getDefault();
/*     */ 
/*  35 */   private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(OverlayWarning.class.getPackage().getName() + ".QuantumMessagesBundle", LOCALE);
/*     */   private static final float PAD = 40.0F;
/*     */   private static final float RECTW = 600.0F;
/*     */   private static final float RECTH = 100.0F;
/*     */   private static final float ARC = 20.0F;
/*     */   private static final int FONTSIZE = 24;
/*     */   private ViewScene view;
/*     */   private Group sceneRoot;
/*     */   private SequentialTransition overlayTransition;
/*     */   private AbstractPainter painter;
/*     */   private boolean warningTransition;
/*     */   private Text text;
/*     */   private Rectangle background;
/*     */   private Group root;
/*     */ 
/*     */   public static String localize(String paramString)
/*     */   {
/*  40 */     return RESOURCES.getString(paramString);
/*     */   }
/*     */ 
/*     */   public OverlayWarning(ViewScene paramViewScene)
/*     */   {
/*  56 */     this.view = paramViewScene;
/*     */ 
/*  58 */     this.sceneRoot = createOverlayGroup();
/*  59 */     this.painter = this.view.getPen().getPainter();
/*     */ 
/*  62 */     Scene.impl_setAllowPGAccess(true);
/*  63 */     this.painter.setOverlayRoot((NGNode)this.sceneRoot.impl_getPGNode());
/*  64 */     Scene.impl_setAllowPGAccess(false);
/*     */ 
/*  66 */     PauseTransition localPauseTransition = new PauseTransition(Duration.millis(4000.0D));
/*  67 */     FadeTransition localFadeTransition = new FadeTransition(Duration.millis(1000.0D), this.sceneRoot);
/*  68 */     localFadeTransition.setFromValue(1.0D);
/*  69 */     localFadeTransition.setToValue(0.0D);
/*     */ 
/*  71 */     this.overlayTransition = new SequentialTransition();
/*  72 */     this.overlayTransition.getChildren().add(localPauseTransition);
/*  73 */     this.overlayTransition.getChildren().add(localFadeTransition);
/*     */ 
/*  75 */     this.overlayTransition.setOnFinished(new EventHandler() {
/*     */       public void handle(ActionEvent paramAnonymousActionEvent) {
/*  77 */         OverlayWarning.this.painter.setRenderOverlay(false);
/*  78 */         OverlayWarning.this.view.entireSceneNeedsRepaint();
/*  79 */         OverlayWarning.this.warningTransition = false;
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   protected ViewScene getView() {
/*  85 */     return this.view;
/*     */   }
/*     */ 
/*     */   protected final void setView(ViewScene paramViewScene) {
/*  89 */     if (this.painter != null) {
/*  90 */       this.painter.setRenderOverlay(false);
/*  91 */       this.view.entireSceneNeedsRepaint();
/*     */     }
/*     */ 
/*  94 */     this.view = paramViewScene;
/*  95 */     this.painter = paramViewScene.getPen().getPainter();
/*     */ 
/*  98 */     Scene.impl_setAllowPGAccess(true);
/*  99 */     this.painter.setOverlayRoot((NGNode)this.sceneRoot.impl_getPGNode());
/* 100 */     Scene.impl_setAllowPGAccess(false);
/*     */ 
/* 102 */     this.painter.setRenderOverlay(true);
/* 103 */     this.view.entireSceneNeedsRepaint();
/*     */ 
/* 105 */     this.overlayTransition.setOnFinished(new EventHandler() {
/*     */       public void handle(ActionEvent paramAnonymousActionEvent) {
/* 107 */         OverlayWarning.this.painter.setRenderOverlay(false);
/* 108 */         OverlayWarning.this.view.entireSceneNeedsRepaint();
/* 109 */         OverlayWarning.this.warningTransition = false;
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   protected void warn() {
/* 115 */     this.warningTransition = true;
/* 116 */     this.painter.setRenderOverlay(true);
/* 117 */     this.overlayTransition.play();
/*     */   }
/*     */ 
/*     */   protected void cancel() {
/* 121 */     if ((this.overlayTransition != null) && (this.overlayTransition.getStatus() == Animation.Status.RUNNING))
/*     */     {
/* 124 */       this.overlayTransition.stop();
/*     */ 
/* 126 */       this.painter.setRenderOverlay(false);
/* 127 */       this.view.entireSceneNeedsRepaint();
/* 128 */       this.warningTransition = false;
/*     */     }
/*     */   }
/*     */ 
/*     */   protected boolean inWarningTransition() {
/* 133 */     return this.warningTransition;
/*     */   }
/*     */ 
/*     */   private Group createOverlayGroup()
/*     */   {
/* 141 */     Scene localScene = new Scene(new Group());
/* 142 */     Font localFont = new Font(Font.getDefault().getFamily(), 24.0D);
/* 143 */     Text localText = new Text();
/* 144 */     Rectangle2D localRectangle2D = Screen.getPrimary().getBounds();
/*     */ 
/* 146 */     localScene.setFill(null);
/*     */ 
/* 148 */     String str = "-fx-effect: dropshadow(two-pass-box, rgba(0,0,0,0.75), 3, 0.0, 0, 2);";
/*     */ 
/* 150 */     localText.setText(localize("OverlayWarningESC"));
/* 151 */     localText.setStroke(Color.WHITE);
/* 152 */     localText.setFill(Color.WHITE);
/* 153 */     localText.setFont(localFont);
/* 154 */     localText.setWrappingWidth(520.0D);
/* 155 */     localText.setStyle(str);
/* 156 */     localText.setTextAlignment(TextAlignment.CENTER);
/*     */ 
/* 158 */     Rectangle localRectangle = createBackground(localText, localRectangle2D);
/*     */ 
/* 160 */     Group localGroup = (Group)localScene.getRoot();
/* 161 */     localGroup.getChildren().add(localRectangle);
/* 162 */     localGroup.getChildren().add(localText);
/*     */ 
/* 164 */     Scene.impl_setAllowPGAccess(true);
/* 165 */     localText.impl_updatePG();
/* 166 */     localRectangle.impl_updatePG();
/* 167 */     localGroup.impl_updatePG();
/* 168 */     Scene.impl_setAllowPGAccess(false);
/* 169 */     return localGroup;
/*     */   }
/*     */ 
/*     */   private Rectangle createBackground(Text paramText, Rectangle2D paramRectangle2D) {
/* 173 */     Rectangle localRectangle = new Rectangle();
/*     */ 
/* 175 */     double d1 = paramText.getLayoutBounds().getWidth();
/* 176 */     double d2 = paramText.getLayoutBounds().getHeight();
/* 177 */     double d3 = (paramRectangle2D.getWidth() - 600.0D) / 2.0D;
/* 178 */     double d4 = paramRectangle2D.getHeight() / 2.0D;
/*     */ 
/* 180 */     localRectangle.setWidth(600.0D);
/* 181 */     localRectangle.setHeight(100.0D);
/* 182 */     localRectangle.setX(d3);
/* 183 */     localRectangle.setY(d4 - 100.0D);
/* 184 */     localRectangle.setArcWidth(20.0D);
/* 185 */     localRectangle.setArcHeight(20.0D);
/* 186 */     localRectangle.setFill(Color.gray(0.0D, 0.6D));
/*     */ 
/* 188 */     paramText.setX(d3 + (600.0D - d1) / 2.0D);
/* 189 */     paramText.setY(d4 - 50.0D + (d2 - paramText.getBaselineOffset()) / 2.0D);
/*     */ 
/* 191 */     return localRectangle;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.quantum.OverlayWarning
 * JD-Core Version:    0.6.2
 */