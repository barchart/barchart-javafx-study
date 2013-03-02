/*    */ package javafx.embed.swing;
/*    */ 
/*    */ import javafx.scene.Scene;
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class JFXPanelBuilder<B extends JFXPanelBuilder<B>>
/*    */   implements Builder<JFXPanel>
/*    */ {
/*    */   private int __set;
/*    */   private boolean opaque;
/*    */   private Scene scene;
/*    */ 
/*    */   public static JFXPanelBuilder<?> create()
/*    */   {
/* 15 */     return new JFXPanelBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(JFXPanel paramJFXPanel)
/*    */   {
/* 20 */     int i = this.__set;
/* 21 */     if ((i & 0x1) != 0) paramJFXPanel.setOpaque(this.opaque);
/* 22 */     if ((i & 0x2) != 0) paramJFXPanel.setScene(this.scene);
/*    */   }
/*    */ 
/*    */   public B opaque(boolean paramBoolean)
/*    */   {
/* 31 */     this.opaque = paramBoolean;
/* 32 */     this.__set |= 1;
/* 33 */     return this;
/*    */   }
/*    */ 
/*    */   public B scene(Scene paramScene)
/*    */   {
/* 42 */     this.scene = paramScene;
/* 43 */     this.__set |= 2;
/* 44 */     return this;
/*    */   }
/*    */ 
/*    */   public JFXPanel build()
/*    */   {
/* 51 */     JFXPanel localJFXPanel = new JFXPanel();
/* 52 */     applyTo(localJFXPanel);
/* 53 */     return localJFXPanel;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.embed.swing.JFXPanelBuilder
 * JD-Core Version:    0.6.2
 */