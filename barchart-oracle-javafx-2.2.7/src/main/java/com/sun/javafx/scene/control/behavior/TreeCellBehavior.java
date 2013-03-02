/*     */ package com.sun.javafx.scene.control.behavior;
/*     */ 
/*     */ import com.sun.javafx.PlatformUtil;
/*     */ import com.sun.javafx.logging.PlatformLogger;
/*     */ import com.sun.javafx.scene.control.Logging;
/*     */ import java.util.WeakHashMap;
/*     */ import javafx.geometry.Bounds;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.control.FocusModel;
/*     */ import javafx.scene.control.MultipleSelectionModel;
/*     */ import javafx.scene.control.SelectionMode;
/*     */ import javafx.scene.control.TreeCell;
/*     */ import javafx.scene.control.TreeItem;
/*     */ import javafx.scene.control.TreeView;
/*     */ import javafx.scene.input.MouseButton;
/*     */ import javafx.scene.input.MouseEvent;
/*     */ 
/*     */ public class TreeCellBehavior extends CellBehaviorBase<TreeCell<?>>
/*     */ {
/*  51 */   private static final WeakHashMap<TreeView, Integer> map = new WeakHashMap();
/*     */ 
/*  89 */   private boolean latePress = false;
/*  90 */   private final boolean isEmbedded = PlatformUtil.isEmbedded();
/*  91 */   private boolean wasSelected = false;
/*     */ 
/*     */   static int getAnchor(TreeView paramTreeView)
/*     */   {
/*  54 */     FocusModel localFocusModel = paramTreeView.getFocusModel();
/*  55 */     if (localFocusModel == null) return -1;
/*     */ 
/*  57 */     return hasAnchor(paramTreeView) ? ((Integer)map.get(paramTreeView)).intValue() : localFocusModel.getFocusedIndex();
/*     */   }
/*     */ 
/*     */   static void setAnchor(TreeView paramTreeView, int paramInt) {
/*  61 */     if ((paramTreeView != null) && (paramInt < 0))
/*  62 */       map.remove(paramTreeView);
/*     */     else
/*  64 */       map.put(paramTreeView, Integer.valueOf(paramInt));
/*     */   }
/*     */ 
/*     */   static boolean hasAnchor(TreeView paramTreeView)
/*     */   {
/*  69 */     return (map.containsKey(paramTreeView)) && (((Integer)map.get(paramTreeView)).intValue() != -1);
/*     */   }
/*     */ 
/*     */   public TreeCellBehavior(TreeCell paramTreeCell)
/*     */   {
/* 103 */     super(paramTreeCell);
/*     */   }
/*     */ 
/*     */   public void mousePressed(MouseEvent paramMouseEvent)
/*     */   {
/* 115 */     boolean bool = ((TreeCell)getControl()).isSelected();
/*     */ 
/* 117 */     if (((TreeCell)getControl()).isSelected()) {
/* 118 */       this.latePress = true;
/* 119 */       return;
/*     */     }
/*     */ 
/* 122 */     doSelect(paramMouseEvent);
/*     */ 
/* 124 */     if ((this.isEmbedded) && (bool))
/* 125 */       this.wasSelected = ((TreeCell)getControl()).isSelected();
/*     */   }
/*     */ 
/*     */   public void mouseReleased(MouseEvent paramMouseEvent)
/*     */   {
/* 130 */     if (this.latePress) {
/* 131 */       this.latePress = false;
/* 132 */       doSelect(paramMouseEvent);
/*     */     }
/*     */ 
/* 135 */     this.wasSelected = false;
/*     */   }
/*     */ 
/*     */   public void mouseDragged(MouseEvent paramMouseEvent) {
/* 139 */     this.latePress = false;
/*     */ 
/* 141 */     TreeView localTreeView = ((TreeCell)getControl()).getTreeView();
/* 142 */     if ((localTreeView == null) || (localTreeView.getSelectionModel() == null)) return;
/*     */ 
/* 147 */     if ((this.isEmbedded) && (!this.wasSelected) && (((TreeCell)getControl()).isSelected()))
/* 148 */       localTreeView.getSelectionModel().clearSelection(((TreeCell)getControl()).getIndex());
/*     */   }
/*     */ 
/*     */   private void doSelect(MouseEvent paramMouseEvent)
/*     */   {
/* 162 */     TreeCell localTreeCell = (TreeCell)getControl();
/* 163 */     TreeView localTreeView = localTreeCell.getTreeView();
/* 164 */     if (localTreeView == null) return;
/*     */ 
/* 168 */     if ((localTreeCell.isEmpty()) || (!localTreeCell.contains(paramMouseEvent.getX(), paramMouseEvent.getY()))) {
/* 169 */       PlatformLogger localPlatformLogger = Logging.getControlsLogger();
/* 170 */       if ((localTreeCell.isEmpty()) && (localPlatformLogger.isLoggable(900)));
/* 175 */       return;
/*     */     }
/*     */ 
/* 178 */     int i = localTreeCell.getIndex();
/* 179 */     boolean bool = localTreeCell.isSelected();
/* 180 */     MultipleSelectionModel localMultipleSelectionModel = localTreeView.getSelectionModel();
/* 181 */     if (localMultipleSelectionModel == null) return;
/*     */ 
/* 183 */     FocusModel localFocusModel = localTreeView.getFocusModel();
/* 184 */     if (localFocusModel == null) return;
/*     */ 
/* 188 */     Node localNode = localTreeCell.getDisclosureNode();
/* 189 */     if ((localNode != null) && 
/* 190 */       (localNode.getBoundsInParent().contains(paramMouseEvent.getX(), paramMouseEvent.getY()))) {
/* 191 */       if (localTreeCell.getTreeItem() != null) {
/* 192 */         localTreeCell.getTreeItem().setExpanded(!localTreeCell.getTreeItem().isExpanded());
/*     */       }
/* 194 */       return;
/*     */     }
/*     */ 
/* 202 */     if (paramMouseEvent.isShiftDown()) {
/* 203 */       if (!map.containsKey(localTreeView))
/* 204 */         setAnchor(localTreeView, localFocusModel.getFocusedIndex());
/*     */     }
/*     */     else {
/* 207 */       map.remove(localTreeView);
/*     */     }
/*     */ 
/* 210 */     MouseButton localMouseButton = paramMouseEvent.getButton();
/* 211 */     if ((localMouseButton == MouseButton.PRIMARY) || ((localMouseButton == MouseButton.SECONDARY) && (!bool)))
/* 212 */       if (localMultipleSelectionModel.getSelectionMode() == SelectionMode.SINGLE) {
/* 213 */         simpleSelect(paramMouseEvent);
/*     */       }
/* 215 */       else if ((paramMouseEvent.isControlDown()) || (paramMouseEvent.isMetaDown())) {
/* 216 */         if (bool)
/*     */         {
/* 218 */           localMultipleSelectionModel.clearSelection(i);
/* 219 */           localFocusModel.focus(i);
/*     */         }
/*     */         else {
/* 222 */           localMultipleSelectionModel.select(i);
/*     */         }
/* 224 */       } else if (paramMouseEvent.isShiftDown())
/*     */       {
/* 227 */         int j = getAnchor(localTreeView);
/*     */ 
/* 230 */         int k = Math.min(j, i);
/* 231 */         int m = Math.max(j, i);
/*     */ 
/* 234 */         localMultipleSelectionModel.clearSelection();
/* 235 */         localMultipleSelectionModel.selectRange(k, m + 1);
/*     */ 
/* 237 */         localFocusModel.focus(i);
/*     */       } else {
/* 239 */         simpleSelect(paramMouseEvent);
/*     */       }
/*     */   }
/*     */ 
/*     */   private void simpleSelect(MouseEvent paramMouseEvent)
/*     */   {
/* 246 */     TreeView localTreeView = ((TreeCell)getControl()).getTreeView();
/* 247 */     TreeItem localTreeItem = ((TreeCell)getControl()).getTreeItem();
/* 248 */     int i = ((TreeCell)getControl()).getIndex();
/* 249 */     MultipleSelectionModel localMultipleSelectionModel = localTreeView.getSelectionModel();
/* 250 */     boolean bool = localMultipleSelectionModel.isSelected(i);
/*     */ 
/* 252 */     localTreeView.getSelectionModel().clearAndSelect(i);
/*     */ 
/* 255 */     if (paramMouseEvent.getButton() == MouseButton.PRIMARY)
/* 256 */       if ((paramMouseEvent.getClickCount() == 1) && (bool))
/* 257 */         localTreeView.edit(localTreeItem);
/* 258 */       else if (paramMouseEvent.getClickCount() == 1)
/*     */       {
/* 260 */         localTreeView.edit(null);
/* 261 */       } else if (paramMouseEvent.getClickCount() == 2)
/* 262 */         if (localTreeItem.isLeaf())
/*     */         {
/* 264 */           localTreeView.edit(localTreeItem);
/*     */         }
/*     */         else
/* 267 */           localTreeItem.setExpanded(!localTreeItem.isExpanded());
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.behavior.TreeCellBehavior
 * JD-Core Version:    0.6.2
 */