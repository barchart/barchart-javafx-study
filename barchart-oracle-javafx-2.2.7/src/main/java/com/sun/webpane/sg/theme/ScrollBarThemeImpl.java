/*     */ package com.sun.webpane.sg.theme;
/*     */ 
/*     */ import com.sun.javafx.scene.control.skin.ScrollBarSkin;
/*     */ import com.sun.webpane.platform.graphics.Ref;
/*     */ import com.sun.webpane.platform.graphics.ScrollBarTheme;
/*     */ import com.sun.webpane.platform.graphics.WCGraphicsContext;
/*     */ import com.sun.webpane.sg.Accessor;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javafx.beans.Observable;
/*     */ import javafx.geometry.Bounds;
/*     */ import javafx.geometry.Orientation;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.control.Control;
/*     */ import javafx.scene.control.ScrollBar;
/*     */ 
/*     */ public class ScrollBarThemeImpl extends ScrollBarTheme
/*     */ {
/*  29 */   private static final Logger log = Logger.getLogger(ScrollBarThemeImpl.class.getName());
/*     */ 
/*  31 */   private WeakReference<ScrollBar> testSBRef = new WeakReference(null);
/*     */ 
/*  34 */   private boolean thicknessInitialized = false;
/*     */   private Accessor accessor;
/*     */   private RenderThemeImpl.Pool<ScrollBarWidget> pool;
/*     */ 
/*     */   public ScrollBarThemeImpl(final Accessor paramAccessor)
/*     */   {
/*  75 */     this.accessor = paramAccessor;
/*  76 */     this.pool = new RenderThemeImpl.Pool(new RenderThemeImpl.Pool.Notifier()
/*     */     {
/*     */       public void notifyRemoved(ScrollBarThemeImpl.ScrollBarWidget paramAnonymousScrollBarWidget) {
/*  79 */         paramAccessor.removeChild(paramAnonymousScrollBarWidget);
/*     */       }
/*     */     }
/*     */     , ScrollBarWidget.class);
/*     */ 
/*  82 */     paramAccessor.addViewListener(new RenderThemeImpl.ViewListener(this.pool, paramAccessor) {
/*     */       public void invalidated(Observable paramAnonymousObservable) {
/*  84 */         super.invalidated(paramAnonymousObservable);
/*  85 */         ScrollBarThemeImpl.ScrollBarWidget localScrollBarWidget = new ScrollBarThemeImpl.ScrollBarWidget(ScrollBarThemeImpl.this);
/*     */ 
/*  87 */         this.accessor.addChild(localScrollBarWidget);
/*  88 */         ScrollBarThemeImpl.this.testSBRef = new WeakReference(localScrollBarWidget);
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   private static Orientation convertOrientation(int paramInt)
/*     */   {
/*  95 */     return paramInt == 1 ? Orientation.VERTICAL : Orientation.HORIZONTAL;
/*     */   }
/*     */ 
/*     */   public void adjustScrollBar(ScrollBar paramScrollBar, int paramInt1, int paramInt2, int paramInt3) {
/*  99 */     Orientation localOrientation = convertOrientation(paramInt3);
/* 100 */     if (localOrientation != paramScrollBar.getOrientation()) {
/* 101 */       paramScrollBar.setOrientation(localOrientation);
/*     */     }
/*     */ 
/* 104 */     if (localOrientation == Orientation.VERTICAL)
/* 105 */       paramInt1 = ScrollBarTheme.getThickness();
/*     */     else {
/* 107 */       paramInt2 = ScrollBarTheme.getThickness();
/*     */     }
/*     */ 
/* 110 */     if ((paramInt1 != paramScrollBar.getWidth()) || (paramInt2 != paramScrollBar.getHeight()))
/* 111 */       paramScrollBar.resize(paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   public void adjustScrollBar(ScrollBar paramScrollBar, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */   {
/* 118 */     adjustScrollBar(paramScrollBar, paramInt1, paramInt2, paramInt3);
/* 119 */     boolean bool = paramInt6 <= paramInt5;
/* 120 */     paramScrollBar.setDisable(bool);
/* 121 */     if (bool) {
/* 122 */       return;
/*     */     }
/* 124 */     if (paramInt4 < 0)
/* 125 */       paramInt4 = 0;
/* 126 */     else if (paramInt4 > paramInt6 - paramInt5) {
/* 127 */       paramInt4 = paramInt6 - paramInt5;
/*     */     }
/*     */ 
/* 130 */     if ((paramScrollBar.getMax() != paramInt6) || (paramScrollBar.getVisibleAmount() != paramInt5)) {
/* 131 */       paramScrollBar.setValue(0.0D);
/* 132 */       paramScrollBar.setMax(paramInt6);
/* 133 */       paramScrollBar.setVisibleAmount(paramInt5);
/*     */     }
/*     */ 
/* 142 */     if (paramInt6 > paramInt5) {
/* 143 */       float f = paramInt6 / (paramInt6 - paramInt5);
/* 144 */       if (paramScrollBar.getValue() != paramInt4 * f)
/* 145 */         paramScrollBar.setValue(paramInt4 * f);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Ref createWidget(long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */   {
/* 153 */     ScrollBarWidget localScrollBarWidget = (ScrollBarWidget)this.pool.get(paramLong);
/* 154 */     if (localScrollBarWidget == null) {
/* 155 */       localScrollBarWidget = new ScrollBarWidget();
/* 156 */       this.pool.put(paramLong, localScrollBarWidget);
/* 157 */       this.accessor.addChild(localScrollBarWidget);
/*     */     }
/* 159 */     adjustScrollBar(localScrollBarWidget, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
/*     */ 
/* 161 */     return new ScrollBarRef(localScrollBarWidget);
/*     */   }
/*     */ 
/*     */   public void paint(WCGraphicsContext paramWCGraphicsContext, Ref paramRef, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */   {
/* 167 */     ScrollBar localScrollBar = (ScrollBar)((ScrollBarRef)paramRef).asControl();
/* 168 */     if (localScrollBar == null) {
/* 169 */       return;
/*     */     }
/*     */ 
/* 172 */     if (log.isLoggable(Level.FINEST)) {
/* 173 */       log.log(Level.FINEST, "[{0}, {1} {2}x{3}], {4}", new Object[] { Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), Double.valueOf(localScrollBar.getWidth()), Double.valueOf(localScrollBar.getHeight()), localScrollBar.getOrientation() == Orientation.VERTICAL ? "VERTICAL" : "HORIZONTAL" });
/*     */     }
/*     */ 
/* 177 */     paramWCGraphicsContext.saveState();
/* 178 */     paramWCGraphicsContext.translate(paramInt1, paramInt2);
/* 179 */     Renderer.getRenderer().render(localScrollBar, paramWCGraphicsContext);
/* 180 */     paramWCGraphicsContext.restoreState();
/*     */   }
/*     */ 
/*     */   public int hitTest(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8)
/*     */   {
/* 187 */     if (log.isLoggable(Level.FINEST)) {
/* 188 */       log.log(Level.FINEST, "[{0}, {1} {2}x{3}], {4}", new Object[] { Integer.valueOf(paramInt7), Integer.valueOf(paramInt8), Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), paramInt3 == 1 ? "VERTICAL" : "HORIZONTAL" });
/*     */     }
/*     */ 
/* 193 */     ScrollBar localScrollBar = (ScrollBar)this.testSBRef.get();
/* 194 */     if (localScrollBar == null) {
/* 195 */       return 0;
/*     */     }
/* 197 */     Node localNode1 = getThumb(localScrollBar);
/* 198 */     Node localNode2 = getTrack(localScrollBar);
/* 199 */     Node localNode3 = getDecButton(localScrollBar);
/* 200 */     Node localNode4 = getIncButton(localScrollBar);
/*     */ 
/* 202 */     adjustScrollBar(localScrollBar, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
/*     */     int n;
/*     */     int k;
/*     */     int i;
/*     */     int j;
/*     */     int i1;
/*     */     int m;
/* 211 */     if (paramInt3 == 1) {
/* 212 */       i = k = n = paramInt7;
/* 213 */       j = paramInt8 - (int)localNode3.getLayoutBounds().getHeight();
/* 214 */       i1 = j - (int)localNode1.getTranslateY();
/* 215 */       m = j - (int)localNode2.getLayoutBounds().getHeight();
/*     */     } else {
/* 217 */       j = m = i1 = paramInt8;
/* 218 */       i = paramInt7 - (int)localNode3.getLayoutBounds().getWidth();
/* 219 */       n = i - (int)localNode1.getTranslateX();
/* 220 */       k = i - (int)localNode2.getLayoutBounds().getWidth();
/*     */     }
/*     */ 
/* 223 */     if (localNode1.contains(n, i1)) {
/* 224 */       log.finer("thumb");
/* 225 */       return 8;
/*     */     }
/* 227 */     if (localNode2.contains(i, j))
/*     */     {
/* 229 */       if (((paramInt3 == 1) && (localNode1.getTranslateY() >= j)) || ((paramInt3 == 0) && (localNode1.getTranslateX() >= i)))
/*     */       {
/* 232 */         log.finer("back track");
/* 233 */         return 4;
/*     */       }
/* 235 */       if (((paramInt3 == 1) && (localNode1.getTranslateY() < j)) || ((paramInt3 == 0) && (localNode1.getTranslateX() < i)))
/*     */       {
/* 238 */         log.finer("forward track");
/* 239 */         return 16;
/*     */       }
/*     */     } else { if (localNode3.contains(paramInt7, paramInt8)) {
/* 242 */         log.finer("back button");
/* 243 */         return 1;
/*     */       }
/* 245 */       if (localNode4.contains(k, m)) {
/* 246 */         log.finer("forward button");
/* 247 */         return 2;
/*     */       }
/*     */     }
/* 250 */     log.finer("no part");
/* 251 */     return 0;
/*     */   }
/*     */ 
/*     */   public int getThumbLength(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */   {
/* 257 */     ScrollBar localScrollBar = (ScrollBar)this.testSBRef.get();
/* 258 */     if (localScrollBar == null) {
/* 259 */       return 0;
/*     */     }
/* 261 */     Node localNode = getThumb(localScrollBar);
/*     */ 
/* 263 */     adjustScrollBar(localScrollBar, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
/*     */ 
/* 265 */     double d = 0.0D;
/* 266 */     if (paramInt3 == 1)
/* 267 */       d = localNode.getLayoutBounds().getHeight();
/*     */     else {
/* 269 */       d = localNode.getLayoutBounds().getWidth();
/*     */     }
/* 271 */     log.log(Level.FINEST, "thumb length: {0}", Double.valueOf(d));
/* 272 */     return (int)d;
/*     */   }
/*     */ 
/*     */   public int getTrackPosition(int paramInt1, int paramInt2, int paramInt3) {
/* 276 */     ScrollBar localScrollBar = (ScrollBar)this.testSBRef.get();
/* 277 */     if (localScrollBar == null) {
/* 278 */       return 0;
/*     */     }
/* 280 */     Node localNode = getDecButton(localScrollBar);
/*     */ 
/* 282 */     adjustScrollBar(localScrollBar, paramInt1, paramInt2, paramInt3);
/*     */ 
/* 284 */     double d = 0.0D;
/* 285 */     if (paramInt3 == 1)
/* 286 */       d = localNode.getLayoutBounds().getHeight();
/*     */     else {
/* 288 */       d = localNode.getLayoutBounds().getWidth();
/*     */     }
/* 290 */     log.log(Level.FINEST, "track position: {0}", Double.valueOf(d));
/* 291 */     return (int)d;
/*     */   }
/*     */ 
/*     */   public int getTrackLength(int paramInt1, int paramInt2, int paramInt3) {
/* 295 */     ScrollBar localScrollBar = (ScrollBar)this.testSBRef.get();
/* 296 */     if (localScrollBar == null) {
/* 297 */       return 0;
/*     */     }
/* 299 */     Node localNode = getTrack(localScrollBar);
/*     */ 
/* 301 */     adjustScrollBar(localScrollBar, paramInt1, paramInt2, paramInt3);
/*     */ 
/* 303 */     double d = 0.0D;
/* 304 */     if (paramInt3 == 1)
/* 305 */       d = localNode.getLayoutBounds().getHeight();
/*     */     else {
/* 307 */       d = localNode.getLayoutBounds().getWidth();
/*     */     }
/* 309 */     log.log(Level.FINEST, "track length: {0}", Double.valueOf(d));
/* 310 */     return (int)d;
/*     */   }
/*     */ 
/*     */   public int getThumbPosition(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */   {
/* 316 */     ScrollBar localScrollBar = (ScrollBar)this.testSBRef.get();
/* 317 */     if (localScrollBar == null) {
/* 318 */       return 0;
/*     */     }
/* 320 */     Node localNode = getThumb(localScrollBar);
/*     */ 
/* 322 */     adjustScrollBar(localScrollBar, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
/*     */ 
/* 324 */     double d = 0.0D;
/* 325 */     if (paramInt3 == 1)
/* 326 */       d = localNode.getTranslateY();
/*     */     else {
/* 328 */       d = localNode.getTranslateX();
/*     */     }
/* 330 */     log.log(Level.FINEST, "thumb position: {0}", Double.valueOf(d));
/* 331 */     return (int)d;
/*     */   }
/*     */ 
/*     */   private void initializeThickness() {
/* 335 */     ScrollBar localScrollBar = (ScrollBar)this.testSBRef.get();
/* 336 */     if (localScrollBar == null) {
/* 337 */       return;
/*     */     }
/* 339 */     ScrollBarSkin localScrollBarSkin = (ScrollBarSkin)localScrollBar.getSkin();
/*     */ 
/* 341 */     int i = (int)localScrollBarSkin.getMinWidth();
/* 342 */     if ((i != 0) && (ScrollBarTheme.getThickness() != i)) {
/* 343 */       ScrollBarTheme.setThickness(i);
/*     */     }
/*     */ 
/* 346 */     this.thicknessInitialized = true;
/*     */   }
/*     */ 
/*     */   private static Node getThumb(ScrollBar paramScrollBar) {
/* 350 */     return (Node)getFieldValue("thumb", ScrollBarSkin.class, paramScrollBar.getSkin());
/*     */   }
/*     */ 
/*     */   private static Node getTrack(ScrollBar paramScrollBar)
/*     */   {
/* 355 */     return (Node)getFieldValue("track", ScrollBarSkin.class, paramScrollBar.getSkin());
/*     */   }
/*     */ 
/*     */   private static Node getIncButton(ScrollBar paramScrollBar)
/*     */   {
/* 360 */     return (Node)getFieldValue("incButton", ScrollBarSkin.class, paramScrollBar.getSkin());
/*     */   }
/*     */ 
/*     */   private static Node getDecButton(ScrollBar paramScrollBar)
/*     */   {
/* 365 */     return (Node)getFieldValue("decButton", ScrollBarSkin.class, paramScrollBar.getSkin());
/*     */   }
/*     */ 
/*     */   private static Object getFieldValue(String paramString, Class paramClass, Object paramObject)
/*     */   {
/*     */     try
/*     */     {
/* 373 */       Field localField = paramClass.getDeclaredField(paramString);
/* 374 */       localField.setAccessible(true);
/* 375 */       return localField.get(paramObject);
/*     */     } catch (Exception localException) {
/* 377 */       localException.printStackTrace();
/*     */     }
/* 379 */     return null;
/*     */   }
/*     */ 
/*     */   private static class ScrollBarRef extends Ref
/*     */   {
/*     */     private WeakReference<ScrollBarThemeImpl.ScrollBarWidget> sbRef;
/*     */ 
/*     */     public ScrollBarRef(ScrollBarThemeImpl.ScrollBarWidget paramScrollBarWidget)
/*     */     {
/*  61 */       this.sbRef = new WeakReference(paramScrollBarWidget);
/*     */     }
/*     */ 
/*     */     public Control asControl() {
/*  65 */       return (Control)this.sbRef.get();
/*     */     }
/*     */   }
/*     */ 
/*     */   private class ScrollBarWidget extends ScrollBar
/*     */     implements RenderThemeImpl.Widget
/*     */   {
/*     */     ScrollBarWidget()
/*     */     {
/*  42 */       setOrientation(Orientation.VERTICAL);
/*  43 */       setMin(0.0D);
/*  44 */       setManaged(false);
/*     */     }
/*     */ 
/*     */     public void impl_updatePG() {
/*  48 */       super.impl_updatePG();
/*  49 */       if (!ScrollBarThemeImpl.this.thicknessInitialized)
/*  50 */         ScrollBarThemeImpl.this.initializeThickness();
/*     */     }
/*     */ 
/*     */     public RenderThemeImpl.WidgetType getType() {
/*  54 */       return RenderThemeImpl.WidgetType.SCROLLBAR;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.sg.theme.ScrollBarThemeImpl
 * JD-Core Version:    0.6.2
 */