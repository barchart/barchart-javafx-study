/*     */ package javafx.scene.input;
/*     */ 
/*     */ public class InputMethodTextRun
/*     */ {
/*  73 */   private String text = "";
/*     */   private InputMethodHighlight highlight;
/*     */ 
/*     */   @Deprecated
/*     */   public static InputMethodTextRun impl_inputMethodTextRun(String paramString, InputMethodHighlight paramInputMethodHighlight)
/*     */   {
/*  63 */     InputMethodTextRun localInputMethodTextRun = new InputMethodTextRun();
/*  64 */     localInputMethodTextRun.text = paramString;
/*  65 */     localInputMethodTextRun.highlight = paramInputMethodHighlight;
/*  66 */     return localInputMethodTextRun;
/*     */   }
/*     */ 
/*     */   public final String getText()
/*     */   {
/*  80 */     return this.text;
/*     */   }
/*     */ 
/*     */   public final InputMethodHighlight getHighlight()
/*     */   {
/*  94 */     return this.highlight;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 102 */     return "InputMethodTextRun text [" + getText() + "], highlight [" + getHighlight() + "]";
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.input.InputMethodTextRun
 * JD-Core Version:    0.6.2
 */