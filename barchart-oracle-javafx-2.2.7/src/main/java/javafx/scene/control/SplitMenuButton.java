/*     */ package javafx.scene.control;
/*     */ 
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.event.ActionEvent;
/*     */ 
/*     */ public class SplitMenuButton extends MenuButton
/*     */ {
/*     */   private static final String DEFAULT_STYLE_CLASS = "split-menu-button";
/*     */ 
/*     */   public SplitMenuButton()
/*     */   {
/*  80 */     this((MenuItem[])null);
/*     */   }
/*     */ 
/*     */   public SplitMenuButton(MenuItem[] paramArrayOfMenuItem)
/*     */   {
/*  89 */     if (paramArrayOfMenuItem != null) {
/*  90 */       getItems().addAll(paramArrayOfMenuItem);
/*     */     }
/*     */ 
/*  93 */     getStyleClass().setAll(new String[] { "split-menu-button" });
/*  94 */     setMnemonicParsing(true);
/*     */   }
/*     */ 
/*     */   public void fire()
/*     */   {
/* 106 */     fireEvent(new ActionEvent());
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.SplitMenuButton
 * JD-Core Version:    0.6.2
 */