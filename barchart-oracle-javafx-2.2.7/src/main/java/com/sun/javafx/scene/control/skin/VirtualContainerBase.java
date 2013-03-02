/*     */ package com.sun.javafx.scene.control.skin;
/*     */ 
/*     */ import com.sun.javafx.scene.control.behavior.BehaviorBase;
/*     */ import java.util.Map;
/*     */ import javafx.collections.MapChangeListener;
/*     */ import javafx.collections.MapChangeListener.Change;
/*     */ import javafx.collections.ObservableMap;
/*     */ import javafx.geometry.Insets;
/*     */ import javafx.scene.control.Control;
/*     */ import javafx.scene.control.IndexedCell;
/*     */ 
/*     */ public abstract class VirtualContainerBase<C extends Control, B extends BehaviorBase<C>, I extends IndexedCell> extends SkinBase<C, B>
/*     */ {
/*     */   public static final String SCROLL_TO_INDEX_CENTERED = "VirtualContainerBase.scrollToIndexCentered";
/*     */   public static final String SCROLL_TO_INDEX_TOP = "VirtualContainerBase.scrollToIndexTop";
/*     */   public static final String SCROLL_TO_OFFSET = "VirtualContainerBase.scrollToOffset";
/*     */   protected final VirtualFlow flow;
/*     */ 
/*     */   public VirtualContainerBase(final C paramC, B paramB)
/*     */   {
/*  50 */     super(paramC, paramB);
/*     */ 
/*  52 */     this.flow = new VirtualFlow();
/*  53 */     handleControlProperties(paramC);
/*     */ 
/*  55 */     paramC.getProperties().addListener(new MapChangeListener()
/*     */     {
/*     */       public void onChanged(MapChangeListener.Change<? extends Object, ? extends Object> paramAnonymousChange) {
/*  58 */         if (paramAnonymousChange.wasAdded())
/*  59 */           VirtualContainerBase.this.handleControlProperties(paramC);
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public abstract I createCell();
/*     */ 
/*     */   public abstract int getItemCount();
/*     */ 
/*     */   double getMaxCellWidth(int paramInt)
/*     */   {
/*  88 */     return getInsets().getLeft() + this.flow.getMaxCellWidth(paramInt) + getInsets().getRight();
/*     */   }
/*     */ 
/*     */   double getVirtualFlowPreferredHeight(int paramInt) {
/*  92 */     double d = 1.0D;
/*     */ 
/*  94 */     for (int i = 0; (i < paramInt) && (i < getItemCount()); i++) {
/*  95 */       d += this.flow.getCellLength(i);
/*     */     }
/*     */ 
/*  98 */     return d + getInsets().getTop() + getInsets().getBottom();
/*     */   }
/*     */ 
/*     */   private void handleControlProperties(C paramC) {
/* 102 */     ObservableMap localObservableMap = paramC.getProperties();
/*     */     Object localObject;
/* 103 */     if (localObservableMap.containsKey("VirtualContainerBase.scrollToIndexCentered")) {
/* 104 */       localObject = localObservableMap.get("VirtualContainerBase.scrollToIndexCentered");
/* 105 */       if ((localObject instanceof Integer))
/*     */       {
/* 107 */         this.flow.scrollTo(((Integer)localObject).intValue(), true);
/*     */       }
/*     */ 
/* 110 */       localObservableMap.remove("VirtualContainerBase.scrollToIndexCentered");
/* 111 */     } else if (localObservableMap.containsKey("VirtualContainerBase.scrollToIndexTop")) {
/* 112 */       localObject = localObservableMap.get("VirtualContainerBase.scrollToIndexTop");
/* 113 */       if ((localObject instanceof Integer))
/*     */       {
/* 115 */         this.flow.scrollTo(((Integer)localObject).intValue(), false);
/*     */       }
/*     */ 
/* 118 */       localObservableMap.remove("VirtualContainerBase.scrollToIndexTop");
/* 119 */     } else if (localObservableMap.containsKey("VirtualContainerBase.scrollToOffset")) {
/* 120 */       localObject = localObservableMap.get("VirtualContainerBase.scrollToOffset");
/* 121 */       if ((localObject instanceof Integer)) {
/* 122 */         this.flow.scrollToOffset(((Integer)localObject).intValue());
/*     */       }
/*     */ 
/* 125 */       localObservableMap.remove("VirtualContainerBase.scrollToOffset");
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.skin.VirtualContainerBase
 * JD-Core Version:    0.6.2
 */