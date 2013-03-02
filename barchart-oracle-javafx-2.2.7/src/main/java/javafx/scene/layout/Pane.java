/*     */ package javafx.scene.layout;
/*     */ 
/*     */ import javafx.beans.DefaultProperty;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.collections.ObservableMap;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.Parent;
/*     */ 
/*     */ @DefaultProperty("children")
/*     */ public class Pane extends Region
/*     */ {
/*     */   static void setConstraint(Node paramNode, Object paramObject1, Object paramObject2)
/*     */   {
/* 100 */     if (paramObject2 == null)
/* 101 */       paramNode.getProperties().remove(paramObject1);
/*     */     else {
/* 103 */       paramNode.getProperties().put(paramObject1, paramObject2);
/*     */     }
/* 105 */     if (paramNode.getParent() != null)
/* 106 */       paramNode.getParent().requestLayout();
/*     */   }
/*     */ 
/*     */   static Object getConstraint(Node paramNode, Object paramObject)
/*     */   {
/* 111 */     if (paramNode.hasProperties()) {
/* 112 */       Object localObject = paramNode.getProperties().get(paramObject);
/* 113 */       if (localObject != null) {
/* 114 */         return localObject;
/*     */       }
/*     */     }
/* 117 */     return null;
/*     */   }
/*     */ 
/*     */   public ObservableList<Node> getChildren()
/*     */   {
/* 132 */     return super.getChildren();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.layout.Pane
 * JD-Core Version:    0.6.2
 */