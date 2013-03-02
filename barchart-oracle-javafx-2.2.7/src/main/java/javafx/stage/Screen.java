/*     */ package javafx.stage;
/*     */ 
/*     */ import com.sun.javafx.tk.ScreenConfigurationAccessor;
/*     */ import com.sun.javafx.tk.TKScreenConfigurationListener;
/*     */ import com.sun.javafx.tk.Toolkit;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import javafx.collections.FXCollections;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.geometry.Rectangle2D;
/*     */ 
/*     */ public class Screen
/*     */ {
/*  80 */   private static AtomicBoolean configurationDirty = new AtomicBoolean(true);
/*     */ 
/*  88 */   private static ScreenConfigurationAccessor accessor = Toolkit.getToolkit().setScreenConfigurationListener(new TKScreenConfigurationListener() {
/*     */     public void screenConfigurationChanged() {
/*  90 */       Screen.access$000(); }  } );
/*     */   private static Screen primary;
/*  85 */   private static ObservableList<Screen> screens = FXCollections.observableArrayList();
/*     */ 
/* 228 */   private Rectangle2D bounds = Rectangle2D.EMPTY;
/*     */ 
/* 243 */   private Rectangle2D visualBounds = Rectangle2D.EMPTY;
/*     */   private double dpi;
/*     */ 
/*  96 */   private static void checkDirty() { if (configurationDirty.compareAndSet(true, false))
/*  97 */       updateConfiguration();
/*     */   }
/*     */ 
/*     */   private static void updateConfiguration()
/*     */   {
/* 102 */     Object localObject1 = Toolkit.getToolkit().getPrimaryScreen();
/* 103 */     Screen localScreen1 = nativeToScreen(localObject1, primary);
/* 104 */     if (localScreen1 != null) {
/* 105 */       primary = localScreen1;
/*     */     }
/*     */ 
/* 108 */     List localList = Toolkit.getToolkit().getScreens();
/*     */ 
/* 112 */     ObservableList localObservableList = FXCollections.observableArrayList();
/*     */ 
/* 115 */     int i = screens.size() == localList.size() ? 1 : 0;
/* 116 */     for (int j = 0; j < localList.size(); j++) {
/* 117 */       Object localObject2 = localList.get(j);
/* 118 */       Screen localScreen2 = null;
/* 119 */       if (i != 0) {
/* 120 */         localScreen2 = (Screen)screens.get(j);
/*     */       }
/* 122 */       Screen localScreen3 = nativeToScreen(localObject2, localScreen2);
/* 123 */       if (localScreen3 != null) {
/* 124 */         if (i != 0) {
/* 125 */           i = 0;
/* 126 */           localObservableList.clear();
/* 127 */           localObservableList.addAll(screens.subList(0, j));
/*     */         }
/* 129 */         localObservableList.add(localScreen3);
/*     */       }
/*     */     }
/* 132 */     if (i == 0) {
/* 133 */       screens.clear();
/* 134 */       screens.addAll(localObservableList);
/*     */     }
/*     */ 
/* 137 */     configurationDirty.set(false);
/*     */   }
/*     */ 
/*     */   private static Screen nativeToScreen(Object paramObject, Screen paramScreen)
/*     */   {
/* 142 */     int i = accessor.getMinX(paramObject);
/* 143 */     int j = accessor.getMinY(paramObject);
/* 144 */     int k = accessor.getWidth(paramObject);
/* 145 */     int m = accessor.getHeight(paramObject);
/* 146 */     int n = accessor.getVisualMinX(paramObject);
/* 147 */     int i1 = accessor.getVisualMinY(paramObject);
/* 148 */     int i2 = accessor.getVisualWidth(paramObject);
/* 149 */     int i3 = accessor.getVisualHeight(paramObject);
/* 150 */     double d = accessor.getDPI(paramObject);
/* 151 */     if ((paramScreen == null) || (paramScreen.bounds.getMinX() != i) || (paramScreen.bounds.getMinY() != j) || (paramScreen.bounds.getWidth() != k) || (paramScreen.bounds.getHeight() != m) || (paramScreen.visualBounds.getMinX() != n) || (paramScreen.visualBounds.getMinY() != i1) || (paramScreen.visualBounds.getWidth() != i2) || (paramScreen.visualBounds.getHeight() != i3) || (paramScreen.dpi != d))
/*     */     {
/* 162 */       Screen localScreen = new Screen();
/* 163 */       localScreen.bounds = new Rectangle2D(i, j, k, m);
/* 164 */       localScreen.visualBounds = new Rectangle2D(n, i1, i2, i3);
/* 165 */       localScreen.dpi = d;
/* 166 */       return localScreen;
/*     */     }
/* 168 */     return null;
/*     */   }
/*     */ 
/*     */   public static Screen getPrimary()
/*     */   {
/* 176 */     checkDirty();
/* 177 */     return primary;
/*     */   }
/*     */ 
/*     */   public static ObservableList<Screen> getScreens()
/*     */   {
/* 184 */     checkDirty();
/* 185 */     return screens;
/*     */   }
/*     */ 
/*     */   public static ObservableList<Screen> getScreensForRectangle(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*     */   {
/* 203 */     checkDirty();
/* 204 */     ObservableList localObservableList = FXCollections.observableArrayList();
/* 205 */     for (Screen localScreen : screens) {
/* 206 */       if (localScreen.bounds.intersects(paramDouble1, paramDouble2, paramDouble3, paramDouble4)) {
/* 207 */         localObservableList.add(localScreen);
/*     */       }
/*     */     }
/* 210 */     return localObservableList;
/*     */   }
/*     */ 
/*     */   public static ObservableList<Screen> getScreensForRectangle(Rectangle2D paramRectangle2D)
/*     */   {
/* 221 */     checkDirty();
/* 222 */     return getScreensForRectangle(paramRectangle2D.getMinX(), paramRectangle2D.getMinY(), paramRectangle2D.getWidth(), paramRectangle2D.getHeight());
/*     */   }
/*     */ 
/*     */   public Rectangle2D getBounds()
/*     */   {
/* 234 */     return this.bounds;
/*     */   }
/*     */ 
/*     */   public final Rectangle2D getVisualBounds()
/*     */   {
/* 252 */     return this.visualBounds;
/*     */   }
/*     */ 
/*     */   public final double getDpi()
/*     */   {
/* 264 */     return this.dpi;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 272 */     long l = 7L;
/* 273 */     l = 37L * l + this.bounds.hashCode();
/* 274 */     l = 37L * l + this.visualBounds.hashCode();
/* 275 */     l = 37L * l + Double.doubleToLongBits(this.dpi);
/* 276 */     return (int)(l ^ l >> 32);
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 285 */     if (paramObject == this) return true;
/* 286 */     if ((paramObject instanceof Screen)) {
/* 287 */       Screen localScreen = (Screen)paramObject;
/* 288 */       return (this.bounds == null ? localScreen.bounds == null : this.bounds.equals(localScreen.bounds)) && (this.visualBounds == null ? localScreen.visualBounds == null : this.visualBounds.equals(localScreen.visualBounds)) && (localScreen.dpi == this.dpi);
/*     */     }
/*     */ 
/* 291 */     return false;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 299 */     return super.toString() + "bounds:" + this.bounds + " visualBounds:" + this.visualBounds + " dpi:" + this.dpi;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.stage.Screen
 * JD-Core Version:    0.6.2
 */