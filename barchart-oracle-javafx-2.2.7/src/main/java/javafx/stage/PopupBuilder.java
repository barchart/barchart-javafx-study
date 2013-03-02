/*    */ package javafx.stage;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import javafx.collections.ObservableList;
/*    */ import javafx.scene.Node;
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class PopupBuilder<B extends PopupBuilder<B>> extends PopupWindowBuilder<B>
/*    */   implements Builder<Popup>
/*    */ {
/*    */   private boolean __set;
/*    */   private Collection<? extends Node> content;
/*    */ 
/*    */   public static PopupBuilder<?> create()
/*    */   {
/* 15 */     return new PopupBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(Popup paramPopup)
/*    */   {
/* 20 */     super.applyTo(paramPopup);
/* 21 */     if (this.__set) paramPopup.getContent().addAll(this.content);
/*    */   }
/*    */ 
/*    */   public B content(Collection<? extends Node> paramCollection)
/*    */   {
/* 30 */     this.content = paramCollection;
/* 31 */     this.__set = true;
/* 32 */     return this;
/*    */   }
/*    */ 
/*    */   public B content(Node[] paramArrayOfNode)
/*    */   {
/* 39 */     return content(Arrays.asList(paramArrayOfNode));
/*    */   }
/*    */ 
/*    */   public Popup build()
/*    */   {
/* 46 */     Popup localPopup = new Popup();
/* 47 */     applyTo(localPopup);
/* 48 */     return localPopup;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.stage.PopupBuilder
 * JD-Core Version:    0.6.2
 */