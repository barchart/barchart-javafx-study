/*     */ package com.sun.webpane.sg.theme;
/*     */ 
/*     */ import com.sun.javafx.scene.control.behavior.BehaviorBase;
/*     */ import com.sun.javafx.scene.control.skin.SkinBase;
/*     */ import com.sun.webpane.platform.LoadListenerClient;
/*     */ import com.sun.webpane.platform.WebPage;
/*     */ import com.sun.webpane.platform.graphics.Ref;
/*     */ import com.sun.webpane.platform.graphics.RenderTheme;
/*     */ import com.sun.webpane.platform.graphics.WCGraphicsContext;
/*     */ import com.sun.webpane.sg.Accessor;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.Observable;
/*     */ import javafx.collections.FXCollections;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.geometry.Orientation;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.control.Button;
/*     */ import javafx.scene.control.CheckBox;
/*     */ import javafx.scene.control.ChoiceBox;
/*     */ import javafx.scene.control.Control;
/*     */ import javafx.scene.control.ProgressBar;
/*     */ import javafx.scene.control.RadioButton;
/*     */ import javafx.scene.control.Slider;
/*     */ import javafx.scene.control.TextField;
/*     */ import javafx.scene.shape.Polygon;
/*     */ 
/*     */ public class RenderThemeImpl extends RenderTheme
/*     */ {
/*  40 */   private static final Logger log = Logger.getLogger(RenderThemeImpl.class.getName());
/*     */   private Accessor accessor;
/*     */   private boolean isDefault;
/*     */   private Pool<FormControl> pool;
/*     */ 
/*     */   public RenderThemeImpl(final Accessor paramAccessor)
/*     */   {
/* 207 */     this.accessor = paramAccessor;
/* 208 */     this.pool = new Pool(new RenderThemeImpl.Pool.Notifier()
/*     */     {
/*     */       public void notifyRemoved(RenderThemeImpl.FormControl paramAnonymousFormControl) {
/* 211 */         paramAccessor.removeChild(paramAnonymousFormControl.asControl());
/*     */       }
/*     */     }
/*     */     , FormControl.class);
/*     */ 
/* 214 */     paramAccessor.addViewListener(new ViewListener(this.pool, paramAccessor));
/*     */   }
/*     */ 
/*     */   public RenderThemeImpl() {
/* 218 */     this.isDefault = true;
/*     */   }
/*     */ 
/*     */   private void ensureNotDefault() {
/* 222 */     if (this.isDefault)
/* 223 */       throw new IllegalStateException("the method should not be called in this context");
/*     */   }
/*     */ 
/*     */   public Ref createWidget(long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, ByteBuffer paramByteBuffer)
/*     */   {
/* 236 */     ensureNotDefault();
/*     */ 
/* 238 */     Object localObject1 = (FormControl)this.pool.get(paramLong);
/* 239 */     WidgetType localWidgetType = WidgetType.convert(paramInt1);
/*     */ 
/* 241 */     if ((localObject1 == null) || (((FormControl)localObject1).getType() != localWidgetType)) {
/* 242 */       if (localObject1 != null)
/*     */       {
/* 244 */         this.accessor.removeChild(((FormControl)localObject1).asControl());
/*     */       }
/* 246 */       switch (2.$SwitchMap$com$sun$webpane$sg$theme$RenderThemeImpl$WidgetType[localWidgetType.ordinal()]) {
/*     */       case 1:
/* 248 */         localObject1 = new FormTextField();
/* 249 */         break;
/*     */       case 2:
/* 251 */         localObject1 = new FormButton();
/* 252 */         break;
/*     */       case 3:
/* 254 */         localObject1 = new FormCheckBox();
/* 255 */         break;
/*     */       case 4:
/* 257 */         localObject1 = new FormRadioButton();
/* 258 */         break;
/*     */       case 5:
/* 260 */         localObject1 = new FormMenuList();
/* 261 */         break;
/*     */       case 6:
/* 263 */         localObject1 = new FormMenuListButton();
/* 264 */         break;
/*     */       case 7:
/* 266 */         localObject1 = new FormSlider();
/* 267 */         break;
/*     */       case 8:
/* 269 */         localObject1 = new FormProgressBar(WidgetType.PROGRESSBAR);
/* 270 */         break;
/*     */       case 9:
/* 272 */         localObject1 = new FormProgressBar(WidgetType.METER);
/* 273 */         break;
/*     */       default:
/* 275 */         log.log(Level.ALL, "unknown widget index: {0}", Integer.valueOf(paramInt1));
/* 276 */         return null;
/*     */       }
/* 278 */       ((FormControl)localObject1).asControl().setFocusTraversable(false);
/* 279 */       this.pool.put(paramLong, (Widget)localObject1);
/* 280 */       this.accessor.addChild(((FormControl)localObject1).asControl());
/*     */     }
/*     */ 
/* 283 */     ((FormControl)localObject1).setState(paramInt2);
/* 284 */     Control localControl = ((FormControl)localObject1).asControl();
/* 285 */     if ((localControl.getWidth() != paramInt3) || (localControl.getHeight() != paramInt4)) {
/* 286 */       localControl.resize(paramInt3, paramInt4);
/*     */     }
/* 288 */     if (localControl.isManaged())
/* 289 */       localControl.setManaged(false);
/*     */     Object localObject2;
/* 291 */     if (localWidgetType == WidgetType.SLIDER) {
/* 292 */       localObject2 = (Slider)localControl;
/* 293 */       paramByteBuffer.order(ByteOrder.nativeOrder());
/* 294 */       ((Slider)localObject2).setOrientation(paramByteBuffer.getInt() == 0 ? Orientation.HORIZONTAL : Orientation.VERTICAL);
/*     */ 
/* 297 */       ((Slider)localObject2).setMax(paramByteBuffer.getFloat());
/* 298 */       ((Slider)localObject2).setMin(paramByteBuffer.getFloat());
/* 299 */       ((Slider)localObject2).setValue(paramByteBuffer.getFloat());
/* 300 */     } else if (localWidgetType == WidgetType.PROGRESSBAR) {
/* 301 */       localObject2 = (ProgressBar)localControl;
/* 302 */       paramByteBuffer.order(ByteOrder.nativeOrder());
/* 303 */       ((ProgressBar)localObject2).setProgress(paramByteBuffer.getInt() == 1 ? paramByteBuffer.getFloat() : -1.0D);
/*     */     }
/* 306 */     else if (localWidgetType == WidgetType.METER) {
/* 307 */       localObject2 = (ProgressBar)localControl;
/* 308 */       paramByteBuffer.order(ByteOrder.nativeOrder());
/* 309 */       ((ProgressBar)localObject2).setProgress(paramByteBuffer.getFloat());
/* 310 */       ((ProgressBar)localObject2).setStyle(getMeterStyle(paramByteBuffer.getInt()));
/*     */     }
/* 312 */     return new FormControlRef((FormControl)localObject1);
/*     */   }
/*     */ 
/*     */   private String getMeterStyle(int paramInt)
/*     */   {
/* 317 */     switch (paramInt) {
/*     */     case 1:
/* 319 */       return "-fx-accent: yellow";
/*     */     case 2:
/* 321 */       return "-fx-accent: red";
/*     */     }
/* 323 */     return "-fx-accent: green";
/*     */   }
/*     */ 
/*     */   public void drawWidget(WCGraphicsContext paramWCGraphicsContext, Ref paramRef, int paramInt1, int paramInt2)
/*     */   {
/* 333 */     ensureNotDefault();
/*     */ 
/* 335 */     FormControl localFormControl = ((FormControlRef)paramRef).asFormControl();
/* 336 */     if (localFormControl != null) {
/* 337 */       Control localControl = localFormControl.asControl();
/* 338 */       if (localControl != null) {
/* 339 */         paramWCGraphicsContext.saveState();
/* 340 */         paramWCGraphicsContext.translate(paramInt1, paramInt2);
/* 341 */         Renderer.getRenderer().render(localControl, paramWCGraphicsContext);
/* 342 */         paramWCGraphicsContext.restoreState();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getRadioButtonSize()
/*     */   {
/* 350 */     return 15;
/*     */   }
/*     */ 
/*     */   public int getSelectionColor(int paramInt)
/*     */   {
/* 356 */     switch (paramInt) { case 0:
/* 357 */       return -16739329;
/*     */     case 1:
/* 358 */       return -1; }
/* 359 */     return 0;
/*     */   }
/*     */ 
/*     */   protected static boolean hasState(int paramInt1, int paramInt2)
/*     */   {
/* 364 */     return (paramInt1 & paramInt2) != 0;
/*     */   }
/*     */ 
/*     */   class FormMenuListButton extends Control
/*     */     implements RenderThemeImpl.FormControl
/*     */   {
/*     */     public Control asControl()
/*     */     {
/* 523 */       return this;
/*     */     }
/*     */     public void setState(int paramInt) {
/*     */     }
/* 527 */     FormMenuListButton() { setSkin(new Skin((float)getWidth())); }
/*     */ 
/*     */ 
/*     */     public RenderThemeImpl.WidgetType getType()
/*     */     {
/* 552 */       return RenderThemeImpl.WidgetType.MENULISTBUTTON;
/*     */     }
/*     */ 
/*     */     class Skin extends SkinBase
/*     */     {
/*     */       private float width;
/*     */ 
/*     */       Skin(float arg2)
/*     */       {
/* 535 */         super(new BehaviorBase(RenderThemeImpl.FormMenuListButton.this));
/*     */ 
/* 537 */         Polygon localPolygon = new Polygon(new double[] { -3.5D, -2.0D, 3.5D, -2.0D, 0.0D, 2.0D });
/*     */         Object localObject;
/* 538 */         localPolygon.setTranslateX(localObject - 8.0F);
/* 539 */         localPolygon.setTranslateY(localObject / 2.0F);
/* 540 */         getChildren().add(localPolygon);
/*     */       }
/*     */ 
/*     */       public boolean contains(double paramDouble1, double paramDouble2) {
/* 544 */         return getNode().contains(paramDouble1, paramDouble2);
/*     */       }
/*     */ 
/*     */       public boolean intersects(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/* 548 */         return getNode().intersects(paramDouble1, paramDouble2, paramDouble3, paramDouble4);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   class FormMenuList extends ChoiceBox
/*     */     implements RenderThemeImpl.FormControl
/*     */   {
/*     */     private int state;
/*     */ 
/*     */     public FormMenuList()
/*     */     {
/* 504 */       ArrayList localArrayList = new ArrayList();
/* 505 */       localArrayList.add("");
/* 506 */       setItems(FXCollections.observableList(localArrayList));
/*     */     }
/*     */     public Control asControl() {
/* 509 */       return this;
/*     */     }
/*     */     public void setState(int paramInt) {
/* 512 */       if (this.state == paramInt) return;
/* 513 */       this.state = paramInt;
/* 514 */       setDisabled(!RenderThemeImpl.hasState(paramInt, 4));
/* 515 */       setFocused(RenderThemeImpl.hasState(paramInt, 8));
/* 516 */       setHover(RenderThemeImpl.hasState(paramInt, 32));
/*     */     }
/*     */     public RenderThemeImpl.WidgetType getType() {
/* 519 */       return RenderThemeImpl.WidgetType.MENULIST;
/*     */     }
/*     */   }
/*     */ 
/*     */   class FormProgressBar extends ProgressBar
/*     */     implements RenderThemeImpl.FormControl
/*     */   {
/*     */     private final RenderThemeImpl.WidgetType type;
/*     */     private int state;
/*     */ 
/*     */     FormProgressBar(RenderThemeImpl.WidgetType arg2)
/*     */     {
/*     */       Object localObject;
/* 483 */       this.type = localObject;
/*     */     }
/*     */     public Control asControl() {
/* 486 */       return this;
/*     */     }
/*     */     public void setState(int paramInt) {
/* 489 */       if (this.state == paramInt) return;
/* 490 */       this.state = paramInt;
/* 491 */       setDisabled(!RenderThemeImpl.hasState(paramInt, 4));
/* 492 */       setFocused(RenderThemeImpl.hasState(paramInt, 8));
/* 493 */       setHover(RenderThemeImpl.hasState(paramInt, 32));
/*     */     }
/*     */     public RenderThemeImpl.WidgetType getType() {
/* 496 */       return this.type;
/*     */     }
/*     */   }
/*     */ 
/*     */   class FormSlider extends Slider
/*     */     implements RenderThemeImpl.FormControl
/*     */   {
/*     */     private int state;
/*     */ 
/*     */     FormSlider()
/*     */     {
/*     */     }
/*     */ 
/*     */     public Control asControl()
/*     */     {
/* 465 */       return this;
/*     */     }
/*     */     public void setState(int paramInt) {
/* 468 */       if (this.state == paramInt) return;
/* 469 */       this.state = paramInt;
/* 470 */       setDisabled(!RenderThemeImpl.hasState(paramInt, 4));
/* 471 */       setFocused(RenderThemeImpl.hasState(paramInt, 8));
/* 472 */       setHover(RenderThemeImpl.hasState(paramInt, 32));
/*     */     }
/*     */     public RenderThemeImpl.WidgetType getType() {
/* 475 */       return RenderThemeImpl.WidgetType.SLIDER;
/*     */     }
/*     */   }
/*     */ 
/*     */   class FormRadioButton extends RadioButton
/*     */     implements RenderThemeImpl.FormControl
/*     */   {
/*     */     private int state;
/*     */ 
/*     */     FormRadioButton()
/*     */     {
/*     */     }
/*     */ 
/*     */     public Control asControl()
/*     */     {
/* 448 */       return this;
/*     */     }
/*     */     public void setState(int paramInt) {
/* 451 */       if (this.state == paramInt) return;
/* 452 */       this.state = paramInt;
/* 453 */       setDisabled(!RenderThemeImpl.hasState(paramInt, 4));
/* 454 */       setFocused(RenderThemeImpl.hasState(paramInt, 8));
/* 455 */       setHover(RenderThemeImpl.hasState(paramInt, 32));
/* 456 */       setSelected(RenderThemeImpl.hasState(paramInt, 1));
/*     */     }
/*     */     public RenderThemeImpl.WidgetType getType() {
/* 459 */       return RenderThemeImpl.WidgetType.RADIOBUTTON;
/*     */     }
/*     */   }
/*     */ 
/*     */   class FormCheckBox extends CheckBox
/*     */     implements RenderThemeImpl.FormControl
/*     */   {
/*     */     private int state;
/*     */ 
/*     */     FormCheckBox()
/*     */     {
/*     */     }
/*     */ 
/*     */     public Control asControl()
/*     */     {
/* 431 */       return this;
/*     */     }
/*     */     public void setState(int paramInt) {
/* 434 */       if (this.state == paramInt) return;
/* 435 */       this.state = paramInt;
/* 436 */       setDisabled(!RenderThemeImpl.hasState(paramInt, 4));
/* 437 */       setFocused(RenderThemeImpl.hasState(paramInt, 8));
/* 438 */       setHover(RenderThemeImpl.hasState(paramInt, 32));
/* 439 */       setSelected(RenderThemeImpl.hasState(paramInt, 1));
/*     */     }
/*     */     public RenderThemeImpl.WidgetType getType() {
/* 442 */       return RenderThemeImpl.WidgetType.CHECKBOX;
/*     */     }
/*     */   }
/*     */ 
/*     */   class FormTextField extends TextField
/*     */     implements RenderThemeImpl.FormControl
/*     */   {
/*     */     private int state;
/*     */ 
/*     */     public FormTextField()
/*     */     {
/* 410 */       setStyle("-fx-display-caret: false");
/*     */     }
/*     */ 
/*     */     public Control asControl() {
/* 414 */       return this;
/*     */     }
/*     */     public void setState(int paramInt) {
/* 417 */       if (this.state == paramInt) return;
/* 418 */       this.state = paramInt;
/* 419 */       setDisabled(!RenderThemeImpl.hasState(paramInt, 4));
/* 420 */       setEditable(RenderThemeImpl.hasState(paramInt, 64));
/* 421 */       setFocused(RenderThemeImpl.hasState(paramInt, 8));
/* 422 */       setHover(RenderThemeImpl.hasState(paramInt, 32));
/*     */     }
/*     */     public RenderThemeImpl.WidgetType getType() {
/* 425 */       return RenderThemeImpl.WidgetType.TEXTFIELD;
/*     */     }
/*     */   }
/*     */ 
/*     */   class FormButton extends Button
/*     */     implements RenderThemeImpl.FormControl
/*     */   {
/*     */     private int state;
/*     */ 
/*     */     FormButton()
/*     */     {
/*     */     }
/*     */ 
/*     */     public Control asControl()
/*     */     {
/* 391 */       return this;
/*     */     }
/*     */     public void setState(int paramInt) {
/* 394 */       if (this.state == paramInt) return;
/* 395 */       this.state = paramInt;
/* 396 */       setDisabled(!RenderThemeImpl.hasState(paramInt, 4));
/* 397 */       setFocused(RenderThemeImpl.hasState(paramInt, 8));
/* 398 */       setHover(RenderThemeImpl.hasState(paramInt, 32));
/* 399 */       setPressed(RenderThemeImpl.hasState(paramInt, 16));
/* 400 */       if (isPressed()) arm(); else disarm(); 
/*     */     }
/*     */ 
/* 403 */     public RenderThemeImpl.WidgetType getType() { return RenderThemeImpl.WidgetType.BUTTON; }
/*     */ 
/*     */   }
/*     */ 
/*     */   static abstract interface FormControl extends RenderThemeImpl.Widget
/*     */   {
/*     */     public abstract Control asControl();
/*     */ 
/*     */     public abstract void setState(int paramInt);
/*     */   }
/*     */ 
/*     */   static abstract interface Widget
/*     */   {
/*     */     public abstract RenderThemeImpl.WidgetType getType();
/*     */   }
/*     */ 
/*     */   class FormControlRef extends Ref
/*     */   {
/*     */     private WeakReference<RenderThemeImpl.FormControl> fcRef;
/*     */ 
/*     */     public FormControlRef(RenderThemeImpl.FormControl arg2)
/*     */     {
/*     */       Object localObject;
/* 371 */       this.fcRef = new WeakReference(localObject);
/*     */     }
/*     */ 
/*     */     public RenderThemeImpl.FormControl asFormControl() {
/* 375 */       return (RenderThemeImpl.FormControl)this.fcRef.get();
/*     */     }
/*     */   }
/*     */ 
/*     */   static class ViewListener
/*     */     implements InvalidationListener
/*     */   {
/*     */     RenderThemeImpl.Pool pool;
/*     */     Accessor accessor;
/*     */     LoadListenerClient loadListener;
/*     */ 
/*     */     public ViewListener(RenderThemeImpl.Pool paramPool, Accessor paramAccessor)
/*     */     {
/* 176 */       this.pool = paramPool;
/* 177 */       this.accessor = paramAccessor;
/*     */     }
/*     */ 
/*     */     public void invalidated(Observable paramObservable) {
/* 181 */       this.pool.clear();
/*     */ 
/* 184 */       if ((this.accessor.getPage() != null) && (this.loadListener == null)) {
/* 185 */         this.loadListener = new LoadListenerClient()
/*     */         {
/*     */           public void dispatchLoadEvent(long paramAnonymousLong, int paramAnonymousInt1, String paramAnonymousString1, String paramAnonymousString2, double paramAnonymousDouble, int paramAnonymousInt2)
/*     */           {
/* 190 */             if (paramAnonymousInt1 == 0)
/*     */             {
/* 193 */               RenderThemeImpl.ViewListener.this.pool.clear();
/*     */             }
/*     */           }
/*     */ 
/*     */           public void dispatchResourceLoadEvent(long paramAnonymousLong, int paramAnonymousInt1, String paramAnonymousString1, String paramAnonymousString2, double paramAnonymousDouble, int paramAnonymousInt2)
/*     */           {
/*     */           }
/*     */         };
/* 201 */         this.accessor.getPage().addLoadListenerClient(this.loadListener);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   static class Pool<T extends RenderThemeImpl.Widget>
/*     */   {
/*     */     private static final int MAX_SIZE = 100;
/*     */     private static final int SORT_BARRIER = 50;
/*  88 */     private LinkedList<Long> ids = new LinkedList();
/*     */ 
/*  92 */     private Map<Long, WeakReference<T>> pool = new HashMap();
/*     */     private Notifier<T> notifier;
/*     */     private String type;
/*     */ 
/*     */     public Pool(Notifier<T> paramNotifier, Class<T> paramClass)
/*     */     {
/* 107 */       this.notifier = paramNotifier;
/* 108 */       this.type = paramClass.getSimpleName();
/*     */     }
/*     */ 
/*     */     public T get(long paramLong) {
/* 112 */       if (RenderThemeImpl.log.isLoggable(Level.FINE)) {
/* 113 */         RenderThemeImpl.log.log(Level.FINE, "type: {0}, size: {1}, id: 0x{2}", new Object[] { this.type, Integer.valueOf(this.pool.size()), Long.toHexString(paramLong) });
/*     */       }
/*     */ 
/* 116 */       assert (this.ids.size() == this.pool.size());
/*     */ 
/* 118 */       WeakReference localWeakReference = (WeakReference)this.pool.get(Long.valueOf(paramLong));
/* 119 */       if (localWeakReference == null) {
/* 120 */         return null;
/*     */       }
/*     */ 
/* 123 */       RenderThemeImpl.Widget localWidget = (RenderThemeImpl.Widget)localWeakReference.get();
/* 124 */       if (localWidget == null) {
/* 125 */         return null;
/*     */       }
/*     */ 
/* 128 */       if (this.ids.size() > 50)
/*     */       {
/* 130 */         this.ids.remove(Long.valueOf(paramLong));
/* 131 */         this.ids.addFirst(Long.valueOf(paramLong));
/*     */       }
/* 133 */       return localWidget;
/*     */     }
/*     */ 
/*     */     public void put(long paramLong, T paramT) {
/* 137 */       if (RenderThemeImpl.log.isLoggable(Level.FINEST)) {
/* 138 */         RenderThemeImpl.log.log(Level.FINEST, "size: {0}, id: 0x{1}, control: {2}", new Object[] { Integer.valueOf(this.pool.size()), Long.toHexString(paramLong), paramT.getType() });
/*     */       }
/*     */ 
/* 141 */       if (this.ids.size() >= 100)
/*     */       {
/* 143 */         RenderThemeImpl.Widget localWidget = (RenderThemeImpl.Widget)((WeakReference)this.pool.remove(this.ids.removeLast())).get();
/* 144 */         if (localWidget != null) {
/* 145 */           this.notifier.notifyRemoved(localWidget);
/*     */         }
/*     */       }
/* 148 */       this.ids.addFirst(Long.valueOf(paramLong));
/* 149 */       this.pool.put(Long.valueOf(paramLong), new WeakReference(paramT));
/*     */     }
/*     */ 
/*     */     public void clear() {
/* 153 */       if (RenderThemeImpl.log.isLoggable(Level.FINE)) {
/* 154 */         RenderThemeImpl.log.fine("size: " + this.pool.size() + ", controls: " + this.pool.values());
/*     */       }
/* 156 */       if (this.pool.size() == 0) {
/* 157 */         return;
/*     */       }
/* 159 */       this.ids.clear();
/* 160 */       for (WeakReference localWeakReference : this.pool.values()) {
/* 161 */         RenderThemeImpl.Widget localWidget = (RenderThemeImpl.Widget)localWeakReference.get();
/* 162 */         if (localWidget != null) {
/* 163 */           this.notifier.notifyRemoved(localWidget);
/*     */         }
/*     */       }
/* 166 */       this.pool.clear();
/*     */     }
/*     */ 
/*     */     public static abstract interface Notifier<T>
/*     */     {
/*     */       public abstract void notifyRemoved(T paramT);
/*     */     }
/*     */   }
/*     */ 
/*     */   static enum WidgetType
/*     */   {
/*  43 */     TEXTFIELD(0), 
/*  44 */     BUTTON(1), 
/*  45 */     CHECKBOX(2), 
/*  46 */     RADIOBUTTON(3), 
/*  47 */     MENULIST(4), 
/*  48 */     MENULISTBUTTON(5), 
/*  49 */     SLIDER(6), 
/*  50 */     PROGRESSBAR(7), 
/*  51 */     METER(8), 
/*  52 */     SCROLLBAR(9);
/*     */ 
/*     */     private static final HashMap<Integer, WidgetType> map;
/*     */     private int value;
/*     */ 
/*  57 */     private WidgetType(int paramInt) { this.value = paramInt; }
/*     */ 
/*     */     public static WidgetType convert(int paramInt)
/*     */     {
/*  61 */       return (WidgetType)map.get(Integer.valueOf(paramInt));
/*     */     }
/*     */ 
/*     */     static
/*     */     {
/*  54 */       map = new HashMap();
/*     */ 
/*  59 */       for (WidgetType localWidgetType : values()) map.put(Integer.valueOf(localWidgetType.value), localWidgetType);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.sg.theme.RenderThemeImpl
 * JD-Core Version:    0.6.2
 */