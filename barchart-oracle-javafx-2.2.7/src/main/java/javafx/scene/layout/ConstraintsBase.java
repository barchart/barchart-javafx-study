/*    */ package javafx.scene.layout;
/*    */ 
/*    */ import com.sun.javafx.WeakReferenceQueue;
/*    */ import java.util.Iterator;
/*    */ import javafx.scene.Node;
/*    */ import javafx.scene.Parent;
/*    */ 
/*    */ public abstract class ConstraintsBase
/*    */ {
/*    */   public static final double CONSTRAIN_TO_PREF = (-1.0D / 0.0D);
/* 70 */   WeakReferenceQueue impl_nodes = new WeakReferenceQueue();
/*    */ 
/*    */   void add(Node paramNode)
/*    */   {
/* 76 */     this.impl_nodes.add(paramNode);
/*    */   }
/*    */ 
/*    */   void remove(Node paramNode) {
/* 80 */     this.impl_nodes.remove(paramNode);
/*    */   }
/*    */ 
/*    */   protected void requestLayout()
/*    */   {
/* 87 */     Iterator localIterator = this.impl_nodes.iterator();
/* 88 */     while (localIterator.hasNext()) {
/* 89 */       Parent localParent = ((Node)localIterator.next()).getParent();
/* 90 */       if (localParent != null)
/* 91 */         localParent.requestLayout();
/*    */     }
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.layout.ConstraintsBase
 * JD-Core Version:    0.6.2
 */