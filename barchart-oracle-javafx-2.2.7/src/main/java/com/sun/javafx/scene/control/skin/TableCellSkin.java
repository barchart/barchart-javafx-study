/*     */ package com.sun.javafx.scene.control.skin;
/*     */ 
/*     */ import com.sun.javafx.scene.control.behavior.TableCellBehavior;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.Observable;
/*     */ import javafx.beans.WeakInvalidationListener;
/*     */ import javafx.beans.property.DoubleProperty;
/*     */ import javafx.beans.property.ReadOnlyDoubleProperty;
/*     */ import javafx.collections.ObservableMap;
/*     */ import javafx.geometry.Insets;
/*     */ import javafx.scene.control.TableCell;
/*     */ import javafx.scene.control.TableColumn;
/*     */ import javafx.scene.shape.Rectangle;
/*     */ 
/*     */ public class TableCellSkin extends CellSkinBase<TableCell, TableCellBehavior>
/*     */ {
/*     */   static final String DEFER_TO_PARENT_PREF_WIDTH = "deferToParentPrefWidth";
/*  45 */   private boolean isDeferToParentForPrefWidth = false;
/*     */ 
/*  47 */   private InvalidationListener columnWidthListener = new InvalidationListener() {
/*     */     public void invalidated(Observable paramAnonymousObservable) {
/*  49 */       TableCellSkin.this.requestLayout();
/*     */     }
/*  47 */   };
/*     */ 
/*  53 */   private WeakInvalidationListener weakColumnWidthListener = new WeakInvalidationListener(this.columnWidthListener);
/*     */ 
/*     */   public TableCellSkin(TableCell paramTableCell)
/*     */   {
/*  57 */     super(paramTableCell, new TableCellBehavior(paramTableCell));
/*     */ 
/*  60 */     Rectangle localRectangle = new Rectangle();
/*  61 */     localRectangle.widthProperty().bind(widthProperty());
/*  62 */     localRectangle.heightProperty().bind(heightProperty());
/*  63 */     setClip(localRectangle);
/*     */ 
/*  66 */     if (((TableCell)getSkinnable()).getTableColumn() != null) {
/*  67 */       ((TableCell)getSkinnable()).getTableColumn().widthProperty().addListener(new WeakInvalidationListener(this.weakColumnWidthListener));
/*     */     }
/*     */ 
/*  71 */     registerChangeListener(paramTableCell.visibleProperty(), "VISIBLE");
/*     */ 
/*  73 */     if (((TableCell)getSkinnable()).getProperties().containsKey("deferToParentPrefWidth"))
/*  74 */       this.isDeferToParentForPrefWidth = true;
/*     */   }
/*     */ 
/*     */   protected void handleControlPropertyChanged(String paramString)
/*     */   {
/*  79 */     super.handleControlPropertyChanged(paramString);
/*  80 */     if (paramString == "VISIBLE")
/*  81 */       setVisible(((TableCell)getSkinnable()).getTableColumn().isVisible());
/*     */   }
/*     */ 
/*     */   protected void layoutChildren()
/*     */   {
/*  87 */     double d1 = getInsets().getLeft();
/*  88 */     double d2 = getInsets().getTop();
/*  89 */     double d3 = snapSize(getWidth()) - (snapSpace(getInsets().getLeft()) + snapSpace(getInsets().getRight()));
/*  90 */     double d4 = getHeight() - (getInsets().getTop() + getInsets().getBottom());
/*     */ 
/*  95 */     layoutLabelInArea(d1, d2, d3, d4 - getPadding().getBottom());
/*     */   }
/*     */ 
/*     */   protected double computePrefWidth(double paramDouble) {
/*  99 */     if (this.isDeferToParentForPrefWidth) {
/* 100 */       return super.computePrefWidth(paramDouble);
/*     */     }
/* 102 */     return ((TableCell)getSkinnable()).getTableColumn().getWidth();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.skin.TableCellSkin
 * JD-Core Version:    0.6.2
 */