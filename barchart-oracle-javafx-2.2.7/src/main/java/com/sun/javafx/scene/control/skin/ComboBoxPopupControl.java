/*     */ package com.sun.javafx.scene.control.skin;
/*     */ 
/*     */ import com.sun.javafx.Utils;
/*     */ import com.sun.javafx.scene.control.behavior.ComboBoxBaseBehavior;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.Observable;
/*     */ import javafx.beans.property.DoubleProperty;
/*     */ import javafx.beans.property.ReadOnlyDoubleProperty;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.event.Event;
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.geometry.HPos;
/*     */ import javafx.geometry.Point2D;
/*     */ import javafx.geometry.VPos;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.Parent;
/*     */ import javafx.scene.Scene;
/*     */ import javafx.scene.control.ComboBoxBase;
/*     */ import javafx.scene.control.PopupControl;
/*     */ import javafx.scene.control.Skin;
/*     */ import javafx.scene.control.Skinnable;
/*     */ import javafx.scene.input.MouseEvent;
/*     */ 
/*     */ public abstract class ComboBoxPopupControl<T> extends ComboBoxBaseSkin<T>
/*     */ {
/*     */   private PopupControl popup;
/*     */ 
/*     */   public ComboBoxPopupControl(ComboBoxBase<T> paramComboBoxBase, ComboBoxBaseBehavior paramComboBoxBaseBehavior)
/*     */   {
/*  48 */     super(paramComboBoxBase, paramComboBoxBaseBehavior);
/*     */   }
/*     */ 
/*     */   protected abstract Node getPopupContent();
/*     */ 
/*     */   protected PopupControl getPopup()
/*     */   {
/*  58 */     if (this.popup == null) {
/*  59 */       createPopup();
/*     */     }
/*  61 */     return this.popup;
/*     */   }
/*     */ 
/*     */   public void show() {
/*  65 */     if (getSkinnable() == null) {
/*  66 */       throw new IllegalStateException("ComboBox is null");
/*     */     }
/*     */ 
/*  69 */     Node localNode = getPopupContent();
/*  70 */     if (localNode == null) {
/*  71 */       throw new IllegalStateException("Popup node is null");
/*     */     }
/*     */ 
/*  74 */     if (getPopup().isShowing()) return;
/*     */ 
/*  76 */     positionAndShowPopup();
/*     */   }
/*     */ 
/*     */   public void hide() {
/*  80 */     if ((this.popup != null) && (this.popup.isShowing()))
/*  81 */       this.popup.hide();
/*     */   }
/*     */ 
/*     */   private Point2D getPrefPopupPosition()
/*     */   {
/*  86 */     return Utils.pointRelativeTo(getSkinnable(), getPopupContent(), HPos.CENTER, VPos.BOTTOM, -7.0D, -10.0D, false);
/*     */   }
/*     */ 
/*     */   private void positionAndShowPopup() {
/*  90 */     if (getPopup().getSkin() == null) {
/*  91 */       getScene().getRoot().impl_processCSS(true);
/*     */     }
/*     */ 
/*  94 */     Point2D localPoint2D = getPrefPopupPosition();
/*  95 */     getPopup().show(((ComboBoxBase)getSkinnable()).getScene().getWindow(), localPoint2D.getX(), localPoint2D.getY());
/*     */   }
/*     */ 
/*     */   private void createPopup() {
/*  99 */     this.popup = new PopupControl()
/*     */     {
/*     */     };
/* 108 */     this.popup.getStyleClass().add("combo-box-popup");
/* 109 */     this.popup.setAutoHide(true);
/* 110 */     this.popup.setAutoFix(true);
/* 111 */     this.popup.setHideOnEscape(true);
/* 112 */     this.popup.setOnAutoHide(new EventHandler() {
/*     */       public void handle(Event paramAnonymousEvent) {
/* 114 */         ((ComboBoxBaseBehavior)ComboBoxPopupControl.this.getBehavior()).onAutoHide();
/*     */       }
/*     */     });
/* 117 */     this.popup.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler()
/*     */     {
/*     */       public void handle(MouseEvent paramAnonymousMouseEvent)
/*     */       {
/* 123 */         ((ComboBoxBaseBehavior)ComboBoxPopupControl.this.getBehavior()).onAutoHide();
/*     */       }
/*     */     });
/* 128 */     InvalidationListener local4 = new InvalidationListener() {
/*     */       public void invalidated(Observable paramAnonymousObservable) {
/* 130 */         ComboBoxPopupControl.this.reconfigurePopup();
/*     */       }
/*     */     };
/* 133 */     ((ComboBoxBase)getSkinnable()).layoutXProperty().addListener(local4);
/* 134 */     ((ComboBoxBase)getSkinnable()).layoutYProperty().addListener(local4);
/* 135 */     ((ComboBoxBase)getSkinnable()).widthProperty().addListener(local4);
/*     */   }
/*     */ 
/*     */   void reconfigurePopup() {
/* 139 */     if (!getPopup().isShowing()) return;
/*     */ 
/* 141 */     Point2D localPoint2D = getPrefPopupPosition();
/* 142 */     reconfigurePopup(localPoint2D.getX(), localPoint2D.getY(), getPopupContent().prefWidth(1.0D), getPopupContent().prefHeight(1.0D));
/*     */   }
/*     */ 
/*     */   void reconfigurePopup(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*     */   {
/* 147 */     if (!getPopup().isShowing()) return;
/*     */ 
/* 149 */     if (paramDouble1 > -1.0D) getPopup().setX(paramDouble1);
/* 150 */     if (paramDouble2 > -1.0D) getPopup().setY(paramDouble2);
/* 151 */     if (paramDouble3 > -1.0D) getPopup().setMinWidth(paramDouble3);
/* 152 */     if (paramDouble4 > -1.0D) getPopup().setMinHeight(paramDouble4);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.skin.ComboBoxPopupControl
 * JD-Core Version:    0.6.2
 */