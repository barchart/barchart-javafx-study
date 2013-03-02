/*     */ package com.sun.javafx.scene.control.behavior;
/*     */ 
/*     */ import com.sun.javafx.PlatformUtil;
/*     */ import com.sun.javafx.logging.PlatformLogger;
/*     */ import com.sun.javafx.scene.control.Logging;
/*     */ import java.util.WeakHashMap;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.scene.control.FocusModel;
/*     */ import javafx.scene.control.ListCell;
/*     */ import javafx.scene.control.ListView;
/*     */ import javafx.scene.control.MultipleSelectionModel;
/*     */ import javafx.scene.control.SelectionMode;
/*     */ import javafx.scene.input.MouseButton;
/*     */ import javafx.scene.input.MouseEvent;
/*     */ 
/*     */ public class ListCellBehavior extends CellBehaviorBase<ListCell>
/*     */ {
/*  47 */   private static final WeakHashMap<ListView, Integer> map = new WeakHashMap();
/*     */ 
/*  78 */   private boolean latePress = false;
/*  79 */   private final boolean isEmbedded = PlatformUtil.isEmbedded();
/*  80 */   private boolean wasSelected = false;
/*     */ 
/*     */   static int getAnchor(ListView paramListView)
/*     */   {
/*  50 */     FocusModel localFocusModel = paramListView.getFocusModel();
/*  51 */     if (localFocusModel == null) return -1;
/*     */ 
/*  53 */     return hasAnchor(paramListView) ? ((Integer)map.get(paramListView)).intValue() : localFocusModel.getFocusedIndex();
/*     */   }
/*     */ 
/*     */   static void setAnchor(ListView paramListView, int paramInt) {
/*  57 */     if ((paramListView != null) && (paramInt < 0))
/*  58 */       map.remove(paramListView);
/*     */     else
/*  60 */       map.put(paramListView, Integer.valueOf(paramInt));
/*     */   }
/*     */ 
/*     */   static boolean hasAnchor(ListView paramListView)
/*     */   {
/*  65 */     return (map.containsKey(paramListView)) && (((Integer)map.get(paramListView)).intValue() != -1);
/*     */   }
/*     */ 
/*     */   public ListCellBehavior(ListCell paramListCell)
/*     */   {
/*  83 */     super(paramListCell);
/*     */   }
/*     */ 
/*     */   public void mousePressed(MouseEvent paramMouseEvent) {
/*  87 */     boolean bool = ((ListCell)getControl()).isSelected();
/*     */ 
/*  89 */     if (((ListCell)getControl()).isSelected()) {
/*  90 */       this.latePress = true;
/*  91 */       return;
/*     */     }
/*     */ 
/*  94 */     doSelect(paramMouseEvent);
/*     */ 
/*  96 */     if ((this.isEmbedded) && (bool))
/*  97 */       this.wasSelected = ((ListCell)getControl()).isSelected();
/*     */   }
/*     */ 
/*     */   public void mouseReleased(MouseEvent paramMouseEvent)
/*     */   {
/* 102 */     if (this.latePress) {
/* 103 */       this.latePress = false;
/* 104 */       doSelect(paramMouseEvent);
/*     */     }
/*     */ 
/* 107 */     this.wasSelected = false;
/*     */   }
/*     */ 
/*     */   public void mouseDragged(MouseEvent paramMouseEvent) {
/* 111 */     this.latePress = false;
/*     */ 
/* 116 */     if ((this.isEmbedded) && (!this.wasSelected) && (((ListCell)getControl()).isSelected()))
/* 117 */       ((ListCell)getControl()).getListView().getSelectionModel().clearSelection(((ListCell)getControl()).getIndex());
/*     */   }
/*     */ 
/*     */   private void doSelect(MouseEvent paramMouseEvent)
/*     */   {
/* 124 */     ListCell localListCell = (ListCell)getControl();
/* 125 */     ListView localListView = ((ListCell)getControl()).getListView();
/* 126 */     if (localListView == null) return;
/*     */ 
/* 130 */     if ((localListCell.isEmpty()) || (!localListCell.contains(paramMouseEvent.getX(), paramMouseEvent.getY()))) {
/* 131 */       PlatformLogger localPlatformLogger = Logging.getControlsLogger();
/* 132 */       if ((localListCell.isEmpty()) && (localPlatformLogger.isLoggable(900)));
/* 137 */       return;
/*     */     }
/*     */ 
/* 140 */     int i = localListView.getItems() == null ? 0 : localListView.getItems().size();
/* 141 */     if (localListCell.getIndex() >= i) return;
/*     */ 
/* 143 */     int j = localListCell.getIndex();
/* 144 */     boolean bool = localListCell.isSelected();
/*     */ 
/* 146 */     MultipleSelectionModel localMultipleSelectionModel = localListView.getSelectionModel();
/* 147 */     if (localMultipleSelectionModel == null) return;
/*     */ 
/* 149 */     FocusModel localFocusModel = localListView.getFocusModel();
/* 150 */     if (localFocusModel == null) return;
/*     */ 
/* 156 */     if (paramMouseEvent.isShiftDown()) {
/* 157 */       if (!map.containsKey(localListView))
/* 158 */         setAnchor(localListView, localFocusModel.getFocusedIndex());
/*     */     }
/*     */     else {
/* 161 */       map.remove(localListView);
/*     */     }
/*     */ 
/* 164 */     MouseButton localMouseButton = paramMouseEvent.getButton();
/* 165 */     if ((localMouseButton == MouseButton.PRIMARY) || ((localMouseButton == MouseButton.SECONDARY) && (!bool)))
/* 166 */       if (localMultipleSelectionModel.getSelectionMode() == SelectionMode.SINGLE) {
/* 167 */         simpleSelect(paramMouseEvent);
/*     */       }
/* 169 */       else if ((paramMouseEvent.isControlDown()) || (paramMouseEvent.isMetaDown())) {
/* 170 */         if (bool)
/*     */         {
/* 172 */           localMultipleSelectionModel.clearSelection(j);
/* 173 */           localFocusModel.focus(j);
/*     */         }
/*     */         else {
/* 176 */           localMultipleSelectionModel.select(j);
/*     */         }
/* 178 */       } else if (paramMouseEvent.isShiftDown())
/*     */       {
/* 181 */         int k = getAnchor(localListView);
/*     */ 
/* 184 */         int m = Math.min(k, j);
/* 185 */         int n = Math.max(k, j);
/*     */ 
/* 188 */         localMultipleSelectionModel.clearSelection();
/* 189 */         localMultipleSelectionModel.selectRange(m, n + 1);
/*     */ 
/* 192 */         localFocusModel.focus(j);
/*     */       } else {
/* 194 */         simpleSelect(paramMouseEvent);
/*     */       }
/*     */   }
/*     */ 
/*     */   private void simpleSelect(MouseEvent paramMouseEvent)
/*     */   {
/* 201 */     ListView localListView = ((ListCell)getControl()).getListView();
/* 202 */     int i = ((ListCell)getControl()).getIndex();
/* 203 */     MultipleSelectionModel localMultipleSelectionModel = localListView.getSelectionModel();
/* 204 */     boolean bool = localMultipleSelectionModel.isSelected(i);
/*     */ 
/* 206 */     localListView.getSelectionModel().clearAndSelect(i);
/*     */ 
/* 209 */     if (paramMouseEvent.getButton() == MouseButton.PRIMARY)
/* 210 */       if ((paramMouseEvent.getClickCount() == 1) && (bool))
/* 211 */         localListView.edit(i);
/* 212 */       else if (paramMouseEvent.getClickCount() == 1)
/*     */       {
/* 214 */         localListView.edit(-1);
/* 215 */       } else if ((paramMouseEvent.getClickCount() == 2) && (((ListCell)getControl()).isEditable()))
/* 216 */         localListView.edit(i);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.behavior.ListCellBehavior
 * JD-Core Version:    0.6.2
 */