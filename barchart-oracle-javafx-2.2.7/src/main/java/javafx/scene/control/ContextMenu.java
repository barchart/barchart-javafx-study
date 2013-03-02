/*     */ package javafx.scene.control;
/*     */ 
/*     */ import com.sun.javafx.Utils;
/*     */ import com.sun.javafx.collections.TrackableObservableList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javafx.beans.property.BooleanProperty;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.ObjectPropertyBase;
/*     */ import javafx.beans.property.SimpleBooleanProperty;
/*     */ import javafx.collections.ListChangeListener.Change;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.event.ActionEvent;
/*     */ import javafx.event.Event;
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.geometry.HPos;
/*     */ import javafx.geometry.Point2D;
/*     */ import javafx.geometry.Side;
/*     */ import javafx.geometry.VPos;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.Scene;
/*     */ import javafx.stage.Window;
/*     */ 
/*     */ public class ContextMenu extends PopupControl
/*     */ {
/* 154 */   private ObjectProperty<EventHandler<ActionEvent>> onAction = new ObjectPropertyBase() {
/*     */     protected void invalidated() {
/* 156 */       ContextMenu.this.setEventHandler(ActionEvent.ACTION, (EventHandler)get());
/*     */     }
/*     */ 
/*     */     public Object getBean()
/*     */     {
/* 161 */       return ContextMenu.this;
/*     */     }
/*     */ 
/*     */     public String getName()
/*     */     {
/* 166 */       return "onAction";
/*     */     }
/* 154 */   };
/*     */ 
/* 173 */   private final ObservableList<MenuItem> items = new TrackableObservableList()
/*     */   {
/*     */     protected void onChanged(ListChangeListener.Change<MenuItem> paramAnonymousChange)
/*     */     {
/*     */       Iterator localIterator;
/*     */       MenuItem localMenuItem;
/* 175 */       while (paramAnonymousChange.next()) {
/* 176 */         for (localIterator = paramAnonymousChange.getRemoved().iterator(); localIterator.hasNext(); ) { localMenuItem = (MenuItem)localIterator.next();
/* 177 */           localMenuItem.setParentPopup(null);
/*     */         }
/* 179 */         for (localIterator = paramAnonymousChange.getAddedSubList().iterator(); localIterator.hasNext(); ) { localMenuItem = (MenuItem)localIterator.next();
/* 180 */           localMenuItem.setParentPopup(ContextMenu.this);
/*     */         }
/*     */       }
/*     */     }
/* 173 */   };
/*     */ 
/*     */   @Deprecated
/* 196 */   private final BooleanProperty impl_showRelativeToWindow = new SimpleBooleanProperty(false);
/*     */   private static final String DEFAULT_STYLE_CLASS = "context-menu";
/*     */ 
/*     */   public ContextMenu()
/*     */   {
/* 130 */     getStyleClass().setAll(new String[] { "context-menu" });
/* 131 */     setAutoHide(true);
/*     */   }
/*     */ 
/*     */   public ContextMenu(MenuItem[] paramArrayOfMenuItem)
/*     */   {
/* 138 */     this();
/* 139 */     this.items.addAll(paramArrayOfMenuItem);
/*     */   }
/*     */ 
/*     */   public final void setOnAction(EventHandler<ActionEvent> paramEventHandler)
/*     */   {
/* 169 */     onActionProperty().set(paramEventHandler); } 
/* 170 */   public final EventHandler<ActionEvent> getOnAction() { return (EventHandler)onActionProperty().get(); } 
/* 171 */   public final ObjectProperty<EventHandler<ActionEvent>> onActionProperty() { return this.onAction; }
/*     */ 
/*     */ 
/*     */   public final ObservableList<MenuItem> getItems()
/*     */   {
/* 190 */     return this.items;
/*     */   }
/*     */ 
/*     */   public final boolean isImpl_showRelativeToWindow()
/*     */   {
/* 201 */     return this.impl_showRelativeToWindow.get();
/*     */   }
/*     */ 
/*     */   public final void setImpl_showRelativeToWindow(boolean paramBoolean) {
/* 205 */     this.impl_showRelativeToWindow.set(paramBoolean);
/*     */   }
/*     */ 
/*     */   public final BooleanProperty impl_showRelativeToWindowProperty() {
/* 209 */     return this.impl_showRelativeToWindow;
/*     */   }
/*     */ 
/*     */   public void show(Node paramNode, Side paramSide, double paramDouble1, double paramDouble2)
/*     */   {
/* 237 */     if (paramNode == null) return;
/* 238 */     Event.fireEvent(this, new Event(Menu.ON_SHOWING));
/* 239 */     if (getItems().size() == 0) return;
/*     */ 
/* 244 */     HPos localHPos = paramSide == Side.RIGHT ? HPos.RIGHT : paramSide == Side.LEFT ? HPos.LEFT : HPos.CENTER;
/* 245 */     VPos localVPos = paramSide == Side.BOTTOM ? VPos.BOTTOM : paramSide == Side.TOP ? VPos.TOP : VPos.CENTER;
/*     */ 
/* 248 */     Point2D localPoint2D = Utils.pointRelativeTo(paramNode, prefWidth(-1.0D), prefHeight(-1.0D), localHPos, localVPos, paramDouble1, paramDouble2, true);
/*     */ 
/* 251 */     doShow(paramNode, localPoint2D.getX(), localPoint2D.getY());
/*     */   }
/*     */ 
/*     */   public void show(Node paramNode, double paramDouble1, double paramDouble2)
/*     */   {
/* 262 */     if (paramNode == null) return;
/* 263 */     Event.fireEvent(this, new Event(Menu.ON_SHOWING));
/* 264 */     if (getItems().size() == 0) return;
/*     */ 
/* 266 */     doShow(paramNode, paramDouble1, paramDouble2);
/*     */   }
/*     */ 
/*     */   private void doShow(Node paramNode, double paramDouble1, double paramDouble2) {
/* 270 */     if (isImpl_showRelativeToWindow()) {
/* 271 */       Scene localScene = paramNode == null ? null : paramNode.getScene();
/* 272 */       Window localWindow = localScene == null ? null : localScene.getWindow();
/* 273 */       super.show(localWindow, paramDouble1, paramDouble2);
/*     */     } else {
/* 275 */       super.show(paramNode, paramDouble1, paramDouble2);
/*     */     }
/* 277 */     Event.fireEvent(this, new Event(Menu.ON_SHOWN));
/*     */   }
/*     */ 
/*     */   public void hide()
/*     */   {
/* 287 */     if (!isShowing()) return;
/* 288 */     Event.fireEvent(this, new Event(Menu.ON_HIDING));
/* 289 */     super.hide();
/* 290 */     Event.fireEvent(this, new Event(Menu.ON_HIDDEN));
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.ContextMenu
 * JD-Core Version:    0.6.2
 */