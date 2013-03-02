/*     */ package com.sun.javafx.scene.control.skin;
/*     */ 
/*     */ import com.sun.javafx.css.StyleableProperty;
/*     */ import com.sun.javafx.scene.control.WeakListChangeListener;
/*     */ import com.sun.javafx.scene.control.behavior.BehaviorBase;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ import javafx.beans.value.WeakChangeListener;
/*     */ import javafx.collections.ListChangeListener;
/*     */ import javafx.collections.ListChangeListener.Change;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.event.EventType;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.Parent;
/*     */ import javafx.scene.control.ContextMenu;
/*     */ import javafx.scene.control.Control;
/*     */ import javafx.scene.control.Skin;
/*     */ import javafx.scene.input.ContextMenuEvent;
/*     */ import javafx.scene.input.MouseEvent;
/*     */ import javafx.scene.layout.StackPane;
/*     */ 
/*     */ public abstract class SkinBase<C extends Control, B extends BehaviorBase<C>> extends StackPane
/*     */   implements Skin<C>
/*     */ {
/*     */   private C control;
/*     */   private B behavior;
/*  86 */   private static final EventHandler<MouseEvent> mouseEventConsumer = new EventHandler()
/*     */   {
/*     */     public void handle(MouseEvent paramAnonymousMouseEvent)
/*     */     {
/*  93 */       paramAnonymousMouseEvent.consume();
/*     */     }
/*  86 */   };
/*     */ 
/* 262 */   private final ListChangeListener styleClassChangeListener = new ListChangeListener()
/*     */   {
/*     */     public void onChanged(ListChangeListener.Change<? extends String> paramAnonymousChange)
/*     */     {
/* 267 */       while (paramAnonymousChange.next())
/*     */       {
/* 270 */         if (paramAnonymousChange.wasRemoved()) {
/* 271 */           SkinBase.this.getStyleClass().removeAll(paramAnonymousChange.getRemoved());
/*     */         }
/* 273 */         if (paramAnonymousChange.wasAdded())
/* 274 */           SkinBase.this.getStyleClass().addAll(paramAnonymousChange.getAddedSubList());
/*     */       }
/*     */     }
/* 262 */   };
/*     */ 
/* 280 */   private final ChangeListener controlPropertyChangedListener = new ChangeListener() {
/*     */     public void changed(ObservableValue paramAnonymousObservableValue, Object paramAnonymousObject1, Object paramAnonymousObject2) {
/* 282 */       SkinBase.this.handleControlPropertyChanged((String)SkinBase.this.propertyReferenceMap.get(paramAnonymousObservableValue));
/*     */     }
/* 280 */   };
/*     */ 
/* 291 */   private Map<ObservableValue, String> propertyReferenceMap = new HashMap();
/*     */   private static final String STYLE_PROPERTY_REF = "STYLE_PROPERTY_REF";
/*     */   private static final String ID_PROPERTY_REF = "ID_PROPERTY_REF";
/* 328 */   private final EventHandler<MouseEvent> mouseHandler = new EventHandler()
/*     */   {
/*     */     public void handle(MouseEvent paramAnonymousMouseEvent) {
/* 331 */       EventType localEventType = paramAnonymousMouseEvent.getEventType();
/*     */ 
/* 333 */       if (localEventType == MouseEvent.MOUSE_ENTERED) SkinBase.this.behavior.mouseEntered(paramAnonymousMouseEvent);
/* 334 */       else if (localEventType == MouseEvent.MOUSE_EXITED) SkinBase.this.behavior.mouseExited(paramAnonymousMouseEvent);
/* 335 */       else if (localEventType == MouseEvent.MOUSE_PRESSED) SkinBase.this.behavior.mousePressed(paramAnonymousMouseEvent);
/* 336 */       else if (localEventType == MouseEvent.MOUSE_RELEASED) SkinBase.this.behavior.mouseReleased(paramAnonymousMouseEvent);
/* 337 */       else if (localEventType == MouseEvent.MOUSE_DRAGGED) SkinBase.this.behavior.mouseDragged(paramAnonymousMouseEvent);
/*     */       else
/* 339 */         throw new AssertionError("Unsupported event type received");
/*     */     }
/* 328 */   };
/*     */ 
/* 350 */   private final EventHandler<ContextMenuEvent> contextMenuHandler = new EventHandler()
/*     */   {
/*     */     public void handle(ContextMenuEvent paramAnonymousContextMenuEvent) {
/* 353 */       if (SkinBase.this.showContextMenu(SkinBase.this.getContextMenu(), paramAnonymousContextMenuEvent.getScreenX(), paramAnonymousContextMenuEvent.getScreenY(), paramAnonymousContextMenuEvent.isKeyboardTrigger()))
/* 354 */         paramAnonymousContextMenuEvent.consume();
/*     */     }
/* 350 */   };
/*     */ 
/*     */   protected final void consumeMouseEvents(boolean paramBoolean)
/*     */   {
/* 101 */     if (paramBoolean) {
/* 102 */       if (this.control != null) {
/* 103 */         this.control.addEventHandler(MouseEvent.ANY, mouseEventConsumer);
/*     */       }
/*     */     }
/* 106 */     else if (this.control != null)
/* 107 */       this.control.removeEventHandler(MouseEvent.ANY, mouseEventConsumer);
/*     */   }
/*     */ 
/*     */   public C getSkinnable()
/*     */   {
/* 117 */     return this.control;
/*     */   }
/*     */ 
/*     */   public final Node getNode() {
/* 121 */     return this;
/*     */   }
/*     */ 
/*     */   public void dispose()
/*     */   {
/* 129 */     for (ObservableValue localObservableValue : this.propertyReferenceMap.keySet()) {
/* 130 */       localObservableValue.removeListener(this.controlPropertyChangedListener);
/*     */     }
/*     */ 
/* 133 */     removeEventHandler(MouseEvent.MOUSE_ENTERED, this.mouseHandler);
/* 134 */     removeEventHandler(MouseEvent.MOUSE_EXITED, this.mouseHandler);
/* 135 */     removeEventHandler(MouseEvent.MOUSE_PRESSED, this.mouseHandler);
/* 136 */     removeEventHandler(MouseEvent.MOUSE_RELEASED, this.mouseHandler);
/* 137 */     removeEventHandler(MouseEvent.MOUSE_DRAGGED, this.mouseHandler);
/*     */ 
/* 139 */     this.control.removeEventHandler(ContextMenuEvent.CONTEXT_MENU_REQUESTED, this.contextMenuHandler);
/*     */ 
/* 141 */     this.control = null;
/* 142 */     this.behavior = null;
/*     */   }
/*     */ 
/*     */   public SkinBase(C paramC, B paramB) {
/* 146 */     if ((paramC == null) || (paramB == null)) {
/* 147 */       throw new IllegalArgumentException("Cannot pass null for control or behavior");
/*     */     }
/*     */ 
/* 151 */     this.control = paramC;
/* 152 */     this.behavior = paramB;
/*     */ 
/* 155 */     getStyleClass().setAll(paramC.getStyleClass());
/* 156 */     setStyle(paramC.getStyle());
/* 157 */     setId(paramC.getId());
/*     */ 
/* 160 */     paramC.getStyleClass().addListener(new WeakListChangeListener(this.styleClassChangeListener));
/*     */ 
/* 163 */     registerChangeListener(paramC.styleProperty(), "STYLE_PROPERTY_REF");
/*     */ 
/* 166 */     registerChangeListener(paramC.idProperty(), "ID_PROPERTY_REF");
/*     */ 
/* 170 */     addEventHandler(MouseEvent.MOUSE_ENTERED, this.mouseHandler);
/* 171 */     addEventHandler(MouseEvent.MOUSE_EXITED, this.mouseHandler);
/* 172 */     addEventHandler(MouseEvent.MOUSE_PRESSED, this.mouseHandler);
/* 173 */     addEventHandler(MouseEvent.MOUSE_RELEASED, this.mouseHandler);
/* 174 */     addEventHandler(MouseEvent.MOUSE_DRAGGED, this.mouseHandler);
/*     */ 
/* 178 */     paramC.addEventHandler(ContextMenuEvent.CONTEXT_MENU_REQUESTED, this.contextMenuHandler);
/*     */ 
/* 181 */     consumeMouseEvents(true);
/*     */   }
/*     */ 
/*     */   public B getBehavior() {
/* 185 */     return this.behavior;
/*     */   }
/*     */ 
/*     */   public ContextMenu getContextMenu() {
/* 189 */     return getSkinnable().getContextMenu();
/*     */   }
/*     */ 
/*     */   public boolean showContextMenu(ContextMenu paramContextMenu, double paramDouble1, double paramDouble2, boolean paramBoolean)
/*     */   {
/* 198 */     if (paramContextMenu != null) {
/* 199 */       paramContextMenu.show(this.control, paramDouble1, paramDouble2);
/* 200 */       return true;
/*     */     }
/* 202 */     return false;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public long impl_getPseudoClassState()
/*     */   {
/* 220 */     return getSkinnable().impl_getPseudoClassState();
/*     */   }
/*     */ 
/*     */   public static List<StyleableProperty> impl_CSS_STYLEABLES()
/*     */   {
/* 246 */     return StyleableProperties.STYLEABLES;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public List<StyleableProperty> impl_getStyleableProperties()
/*     */   {
/* 256 */     return impl_CSS_STYLEABLES();
/*     */   }
/*     */ 
/*     */   protected final void registerChangeListener(ObservableValue paramObservableValue, String paramString)
/*     */   {
/* 302 */     if (!this.propertyReferenceMap.containsKey(paramObservableValue)) {
/* 303 */       this.propertyReferenceMap.put(paramObservableValue, paramString);
/* 304 */       paramObservableValue.addListener(new WeakChangeListener(this.controlPropertyChangedListener));
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void handleControlPropertyChanged(String paramString)
/*     */   {
/* 315 */     if ("STYLE_PROPERTY_REF".equals(paramString))
/* 316 */       setStyle(getSkinnable().getStyle());
/* 317 */     else if ("ID_PROPERTY_REF".equals(paramString))
/* 318 */       setId(getSkinnable().getId());
/*     */   }
/*     */ 
/*     */   private static class StyleableProperties
/*     */   {
/* 240 */     private static final List<StyleableProperty> STYLEABLES = Collections.unmodifiableList(localArrayList);
/*     */ 
/*     */     static
/*     */     {
/* 232 */       List localList1 = StackPane.impl_CSS_STYLEABLES();
/* 233 */       List localList2 = Parent.impl_CSS_STYLEABLES();
/* 234 */       ArrayList localArrayList = new ArrayList();
/*     */ 
/* 236 */       int i = localList2.size();
/* 237 */       int j = 0; for (int k = localList1.size() - i; j < k; j++)
/* 238 */         localArrayList.add(localList1.get(j + i));
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.skin.SkinBase
 * JD-Core Version:    0.6.2
 */