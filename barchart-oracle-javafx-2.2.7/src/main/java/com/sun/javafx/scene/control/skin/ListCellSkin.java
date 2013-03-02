/*    */ package com.sun.javafx.scene.control.skin;
/*    */ 
/*    */ import com.sun.javafx.scene.control.behavior.ListCellBehavior;
/*    */ import javafx.geometry.Orientation;
/*    */ import javafx.scene.control.ListCell;
/*    */ import javafx.scene.control.ListView;
/*    */ 
/*    */ public class ListCellSkin extends CellSkinBase<ListCell, ListCellBehavior>
/*    */ {
/*    */   public ListCellSkin(ListCell paramListCell)
/*    */   {
/* 37 */     super(paramListCell, new ListCellBehavior(paramListCell));
/*    */   }
/*    */ 
/*    */   protected double computePrefWidth(double paramDouble) {
/* 41 */     double d = super.computePrefWidth(paramDouble);
/* 42 */     ListView localListView = ((ListCell)getSkinnable()).getListView();
/* 43 */     return localListView.getOrientation() == Orientation.VERTICAL ? d : localListView == null ? 0.0D : Math.max(d, getCellSize());
/*    */   }
/*    */ 
/*    */   protected double computePrefHeight(double paramDouble)
/*    */   {
/* 48 */     if (this.cellSizeSet)
/*    */     {
/* 51 */       double d = getCellSize();
/* 52 */       return d == 24.0D ? super.computePrefHeight(paramDouble) : d;
/*    */     }
/* 54 */     return Math.max(24.0D, super.computePrefHeight(paramDouble));
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.skin.ListCellSkin
 * JD-Core Version:    0.6.2
 */