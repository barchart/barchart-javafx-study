/*    */ package javafx.scene.web;
/*    */ 
/*    */ import com.sun.javafx.css.StyleableProperty;
/*    */ import com.sun.javafx.scene.web.skin.HTMLEditorSkin;
/*    */ import java.net.URL;
/*    */ import java.security.AccessController;
/*    */ import java.security.PrivilegedAction;
/*    */ import javafx.scene.control.Control;
/*    */ 
/*    */ public class HTMLEditor extends Control
/*    */ {
/*    */   public HTMLEditor()
/*    */   {
/* 28 */     StyleableProperty localStyleableProperty = StyleableProperty.getStyleableProperty(super.skinClassNameProperty());
/* 29 */     localStyleableProperty.set(this, "com.sun.javafx.scene.web.skin.HTMLEditorSkin");
/*    */   }
/*    */ 
/*    */   protected String getUserAgentStylesheet()
/*    */   {
/* 34 */     return (String)AccessController.doPrivileged(new PrivilegedAction() {
/*    */       public String run() {
/* 36 */         return HTMLEditorSkin.class.getResource("html-editor.css").toExternalForm();
/*    */       }
/*    */     });
/*    */   }
/*    */ 
/*    */   public String getHtmlText()
/*    */   {
/* 45 */     return ((HTMLEditorSkin)getSkin()).getHTMLText();
/*    */   }
/*    */ 
/*    */   public void setHtmlText(String paramString)
/*    */   {
/* 62 */     ((HTMLEditorSkin)getSkin()).setHTMLText(paramString);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.web.HTMLEditor
 * JD-Core Version:    0.6.2
 */