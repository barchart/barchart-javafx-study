/*     */ package javafx.scene.shape;
/*     */ 
/*     */ import com.sun.javafx.WeakReferenceQueue;
/*     */ import com.sun.javafx.geom.Path2D;
/*     */ import com.sun.javafx.sg.PGPath;
/*     */ import java.util.Iterator;
/*     */ import javafx.beans.property.BooleanProperty;
/*     */ import javafx.beans.property.BooleanPropertyBase;
/*     */ import javafx.scene.Node;
/*     */ 
/*     */ public abstract class PathElement
/*     */ {
/*  52 */   WeakReferenceQueue impl_nodes = new WeakReferenceQueue();
/*     */   private BooleanProperty absolute;
/*     */ 
/*     */   void addNode(Node paramNode)
/*     */   {
/*  55 */     this.impl_nodes.add(paramNode);
/*     */   }
/*     */ 
/*     */   void removeNode(Node paramNode) {
/*  59 */     this.impl_nodes.remove(paramNode);
/*     */   }
/*     */ 
/*     */   void u() {
/*  63 */     Iterator localIterator = this.impl_nodes.iterator();
/*  64 */     while (localIterator.hasNext())
/*  65 */       ((Path)localIterator.next()).markPathDirty();
/*     */   }
/*     */ 
/*     */   abstract void addTo(PGPath paramPGPath);
/*     */ 
/*     */   @Deprecated
/*     */   public abstract void impl_addTo(Path2D paramPath2D);
/*     */ 
/*     */   public final void setAbsolute(boolean paramBoolean)
/*     */   {
/*  90 */     absoluteProperty().set(paramBoolean);
/*     */   }
/*     */ 
/*     */   public final boolean isAbsolute() {
/*  94 */     return (this.absolute == null) || (this.absolute.get());
/*     */   }
/*     */ 
/*     */   public final BooleanProperty absoluteProperty() {
/*  98 */     if (this.absolute == null) {
/*  99 */       this.absolute = new BooleanPropertyBase() {
/*     */         protected void invalidated() {
/* 101 */           PathElement.this.u();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 106 */           return PathElement.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 111 */           return "absolute";
/*     */         }
/*     */       };
/*     */     }
/* 115 */     return this.absolute;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.shape.PathElement
 * JD-Core Version:    0.6.2
 */