/*    */ package javafx.scene;
/*    */ 
/*    */ import com.sun.javafx.scene.traversal.TraversalEngine;
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import javafx.collections.ObservableList;
/*    */ 
/*    */ public abstract class ParentBuilder<B extends ParentBuilder<B>> extends NodeBuilder<B>
/*    */ {
/*    */   private int __set;
/*    */   private TraversalEngine impl_traversalEngine;
/*    */   private Collection<? extends String> stylesheets;
/*    */ 
/*    */   public void applyTo(Parent paramParent)
/*    */   {
/* 15 */     super.applyTo(paramParent);
/* 16 */     int i = this.__set;
/* 17 */     if ((i & 0x1) != 0) paramParent.setImpl_traversalEngine(this.impl_traversalEngine);
/* 18 */     if ((i & 0x2) != 0) paramParent.getStylesheets().addAll(this.stylesheets);
/*    */   }
/*    */ 
/*    */   @Deprecated
/*    */   public B impl_traversalEngine(TraversalEngine paramTraversalEngine)
/*    */   {
/* 29 */     this.impl_traversalEngine = paramTraversalEngine;
/* 30 */     this.__set |= 1;
/* 31 */     return this;
/*    */   }
/*    */ 
/*    */   public B stylesheets(Collection<? extends String> paramCollection)
/*    */   {
/* 40 */     this.stylesheets = paramCollection;
/* 41 */     this.__set |= 2;
/* 42 */     return this;
/*    */   }
/*    */ 
/*    */   public B stylesheets(String[] paramArrayOfString)
/*    */   {
/* 49 */     return stylesheets(Arrays.asList(paramArrayOfString));
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.ParentBuilder
 * JD-Core Version:    0.6.2
 */