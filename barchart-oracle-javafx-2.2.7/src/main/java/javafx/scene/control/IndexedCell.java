/*     */ package javafx.scene.control;
/*     */ 
/*     */ import com.sun.javafx.css.StyleManager;
/*     */ import javafx.beans.property.ReadOnlyIntegerProperty;
/*     */ import javafx.beans.property.ReadOnlyIntegerWrapper;
/*     */ import javafx.collections.ObservableList;
/*     */ 
/*     */ public class IndexedCell<T> extends Cell<T>
/*     */ {
/*  75 */   private ReadOnlyIntegerWrapper index = new ReadOnlyIntegerWrapper(this, "index", -1) {
/*     */     protected void invalidated() {
/*  77 */       IndexedCell.this.impl_pseudoClassStateChanged("even");
/*  78 */       IndexedCell.this.impl_pseudoClassStateChanged("odd");
/*     */     }
/*  75 */   };
/*     */   private static final String DEFAULT_STYLE_CLASS = "indexed-cell";
/*     */   private static final String PSEUDO_CLASS_EVEN = "even";
/*     */   private static final String PSEUDO_CLASS_ODD = "odd";
/* 131 */   private static final long ODD_PSEUDOCLASS_STATE = StyleManager.getInstance().getPseudoclassMask("odd");
/* 132 */   private static final long EVEN_PSEUDOCLASS_STATE = StyleManager.getInstance().getPseudoclassMask("even");
/*     */ 
/*     */   public IndexedCell()
/*     */   {
/*  63 */     getStyleClass().addAll(new String[] { "indexed-cell" });
/*     */   }
/*     */ 
/*     */   public final int getIndex()
/*     */   {
/*  86 */     return this.index.get();
/*     */   }
/*     */ 
/*     */   public final ReadOnlyIntegerProperty indexProperty()
/*     */   {
/*  96 */     return this.index.getReadOnlyProperty();
/*     */   }
/*     */ 
/*     */   public void updateIndex(int paramInt)
/*     */   {
/* 112 */     this.index.set(paramInt);
/* 113 */     indexChanged();
/*     */   }
/*     */ 
/*     */   void indexChanged()
/*     */   {
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public long impl_getPseudoClassState()
/*     */   {
/* 139 */     long l = super.impl_getPseudoClassState();
/* 140 */     l |= (getIndex() % 2 == 0 ? EVEN_PSEUDOCLASS_STATE : ODD_PSEUDOCLASS_STATE);
/* 141 */     return l;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.IndexedCell
 * JD-Core Version:    0.6.2
 */