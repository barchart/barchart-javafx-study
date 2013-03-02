/*    */ package javafx.scene.web;
/*    */ 
/*    */ import javafx.scene.control.ControlBuilder;
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class HTMLEditorBuilder<B extends HTMLEditorBuilder<B>> extends ControlBuilder<B>
/*    */   implements Builder<HTMLEditor>
/*    */ {
/*    */   private boolean __set;
/*    */   private String htmlText;
/*    */ 
/*    */   public static HTMLEditorBuilder<?> create()
/*    */   {
/* 15 */     return new HTMLEditorBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(HTMLEditor paramHTMLEditor)
/*    */   {
/* 20 */     super.applyTo(paramHTMLEditor);
/* 21 */     if (this.__set) paramHTMLEditor.setHtmlText(this.htmlText);
/*    */   }
/*    */ 
/*    */   public B htmlText(String paramString)
/*    */   {
/* 30 */     this.htmlText = paramString;
/* 31 */     this.__set = true;
/* 32 */     return this;
/*    */   }
/*    */ 
/*    */   public HTMLEditor build()
/*    */   {
/* 39 */     HTMLEditor localHTMLEditor = new HTMLEditor();
/* 40 */     applyTo(localHTMLEditor);
/* 41 */     return localHTMLEditor;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.web.HTMLEditorBuilder
 * JD-Core Version:    0.6.2
 */