/*     */ package com.sun.javafx.tk.quantum;
/*     */ 
/*     */ import com.sun.javafx.tk.TKSceneListener;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javafx.event.EventType;
/*     */ import javafx.scene.input.SwipeEvent;
/*     */ 
/*     */ public class SwipeGestureRecognizer
/*     */   implements GestureRecognizer
/*     */ {
/*     */   private static final boolean VERBOSE = false;
/*     */   private static final double DISTANCE_THRESHOLD = 10.0D;
/*     */   private static final double BACKWARD_DISTANCE_THRASHOLD = 5.0D;
/*     */   static final long TIME_STEP = 100000000L;
/*  28 */   private SwipeRecognitionState state = SwipeRecognitionState.IDLE;
/*  29 */   MultiTouchTracker tracker = new MultiTouchTracker(null);
/*     */   private ViewScene scene;
/*     */ 
/*     */   SwipeGestureRecognizer(ViewScene paramViewScene)
/*     */   {
/*  33 */     this.scene = paramViewScene;
/*     */   }
/*     */ 
/*     */   public void notifyBeginTouchEvent(long paramLong, int paramInt1, boolean paramBoolean, int paramInt2)
/*     */   {
/*  39 */     this.tracker.params(paramInt1, paramBoolean);
/*     */   }
/*     */ 
/*     */   public void notifyNextTouchEvent(long paramLong1, int paramInt1, long paramLong2, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
/*     */   {
/*  45 */     switch (paramInt1) {
/*     */     case 811:
/*  47 */       this.tracker.pressed(paramLong2, paramLong1, paramInt2, paramInt3, paramInt4, paramInt5);
/*  48 */       break;
/*     */     case 812:
/*     */     case 814:
/*  52 */       this.tracker.progress(paramLong2, paramLong1, paramInt4, paramInt5);
/*  53 */       break;
/*     */     case 813:
/*  55 */       this.tracker.released(paramLong2, paramLong1, paramInt2, paramInt3, paramInt4, paramInt5);
/*  56 */       break;
/*     */     default:
/*  58 */       throw new RuntimeException("Error in swipe gesture recognition: unknown touch state: " + this.state);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void notifyEndTouchEvent(long paramLong)
/*     */   {
/*     */   }
/*     */ 
/*     */   private EventType<SwipeEvent> calcSwipeType(TouchPointTracker paramTouchPointTracker)
/*     */   {
/*  70 */     double d1 = paramTouchPointTracker.getDistanceX();
/*  71 */     double d2 = paramTouchPointTracker.getDistanceY();
/*  72 */     double d3 = Math.abs(d1);
/*  73 */     double d4 = Math.abs(d2);
/*     */ 
/*  75 */     int i = d3 > d4 ? 1 : 0;
/*     */ 
/*  77 */     double d5 = i != 0 ? d1 : d2;
/*  78 */     double d6 = i != 0 ? d3 : d4;
/*  79 */     double d7 = i != 0 ? d4 : d3;
/*  80 */     double d8 = i != 0 ? paramTouchPointTracker.lengthX : paramTouchPointTracker.lengthY;
/*     */ 
/*  82 */     double d9 = i != 0 ? paramTouchPointTracker.maxDeviationY : paramTouchPointTracker.maxDeviationX;
/*     */ 
/*  84 */     double d10 = i != 0 ? paramTouchPointTracker.lastXMovement : paramTouchPointTracker.lastYMovement;
/*     */ 
/*  87 */     if (d6 <= 10.0D)
/*     */     {
/*  89 */       return null;
/*     */     }
/*     */ 
/*  92 */     if (d7 > d6 * 0.839D)
/*     */     {
/*  94 */       return null;
/*     */     }
/*     */ 
/*  97 */     if (d9 > d8 / (paramTouchPointTracker.getDuration() / 100L))
/*     */     {
/* 100 */       return null;
/*     */     }
/*     */ 
/* 103 */     if (d8 > d6 * 1.5D)
/*     */     {
/* 105 */       return null;
/*     */     }
/*     */ 
/* 108 */     if ((Math.signum(d5) != Math.signum(d10)) && (Math.abs(d10) > 5.0D))
/*     */     {
/* 111 */       return null;
/*     */     }
/*     */ 
/* 114 */     if (i != 0) {
/* 115 */       return paramTouchPointTracker.getDistanceX() < 0.0D ? SwipeEvent.SWIPE_LEFT : SwipeEvent.SWIPE_RIGHT;
/*     */     }
/*     */ 
/* 118 */     return paramTouchPointTracker.getDistanceY() < 0.0D ? SwipeEvent.SWIPE_UP : SwipeEvent.SWIPE_DOWN;
/*     */   }
/*     */ 
/*     */   private void handleSwipeType(final EventType<SwipeEvent> paramEventType, final CenterComputer paramCenterComputer, final int paramInt1, final int paramInt2, final boolean paramBoolean)
/*     */   {
/* 126 */     if (paramEventType == null) {
/* 127 */       return;
/*     */     }
/*     */ 
/* 133 */     AccessController.doPrivileged(new PrivilegedAction()
/*     */     {
/*     */       public Void run() {
/* 136 */         if (SwipeGestureRecognizer.this.scene.sceneListener != null) {
/* 137 */           SwipeGestureRecognizer.this.scene.sceneListener.swipeEvent(paramEventType, paramInt1, paramCenterComputer.getX(), paramCenterComputer.getY(), paramCenterComputer.getAbsX(), paramCenterComputer.getAbsY(), (paramInt2 & 0x1) != 0, (paramInt2 & 0x4) != 0, (paramInt2 & 0x8) != 0, (paramInt2 & 0x10) != 0, paramBoolean);
/*     */         }
/*     */ 
/* 146 */         return null;
/*     */       }
/*     */     }
/*     */     , this.scene.getAccessControlContext());
/*     */   }
/*     */ 
/*     */   private static enum SwipeRecognitionState
/*     */   {
/* 374 */     IDLE, 
/* 375 */     ADDING, 
/* 376 */     REMOVING, 
/* 377 */     FAILURE;
/*     */   }
/*     */ 
/*     */   private class TouchPointTracker
/*     */   {
/*     */     long time;
/*     */     long beginTime;
/*     */     long endTime;
/*     */     double beginX;
/*     */     double beginY;
/*     */     double endX;
/*     */     double endY;
/*     */     double beginAbsX;
/*     */     double beginAbsY;
/*     */     double endAbsX;
/*     */     double endAbsY;
/*     */     double lengthX;
/*     */     double lengthY;
/*     */     double maxDeviationX;
/*     */     double maxDeviationY;
/*     */     double lastXMovement;
/*     */     double lastYMovement;
/*     */     double lastX;
/*     */     double lastY;
/*     */ 
/*     */     private TouchPointTracker()
/*     */     {
/*     */     }
/*     */ 
/*     */     public void start(long paramLong, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*     */     {
/* 311 */       this.beginX = paramDouble1;
/* 312 */       this.beginY = paramDouble2;
/* 313 */       this.beginAbsX = paramDouble3;
/* 314 */       this.beginAbsY = paramDouble4;
/* 315 */       this.lastX = paramDouble3;
/* 316 */       this.lastY = paramDouble4;
/* 317 */       this.beginTime = (paramLong / 1000000L);
/*     */     }
/*     */ 
/*     */     public void end(long paramLong, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/* 321 */       progress(paramLong, paramDouble1, paramDouble2);
/* 322 */       this.endX = paramDouble1;
/* 323 */       this.endY = paramDouble2;
/* 324 */       this.endAbsX = paramDouble3;
/* 325 */       this.endAbsY = paramDouble4;
/* 326 */       this.endTime = (paramLong / 1000000L);
/*     */     }
/*     */ 
/*     */     public void progress(long paramLong, double paramDouble1, double paramDouble2) {
/* 330 */       if (paramLong > this.time + 100000000L) {
/* 331 */         double d1 = paramDouble1 - this.lastX;
/* 332 */         double d2 = paramDouble2 - this.lastY;
/*     */ 
/* 334 */         this.time = paramLong;
/* 335 */         this.lengthX += Math.abs(d1);
/* 336 */         this.lengthY += Math.abs(d2);
/* 337 */         this.lastX = paramDouble1;
/* 338 */         this.lastY = paramDouble2;
/*     */ 
/* 340 */         double d3 = Math.abs(paramDouble1 - this.beginAbsX);
/* 341 */         if (d3 > this.maxDeviationX) this.maxDeviationX = d3;
/*     */ 
/* 343 */         double d4 = Math.abs(paramDouble2 - this.beginAbsY);
/* 344 */         if (d4 > this.maxDeviationY) this.maxDeviationY = d4;
/*     */ 
/* 346 */         if (Math.signum(d1) == Math.signum(this.lastXMovement))
/* 347 */           this.lastXMovement += d1;
/*     */         else {
/* 349 */           this.lastXMovement = d1;
/*     */         }
/*     */ 
/* 352 */         if (Math.signum(d2) == Math.signum(this.lastYMovement))
/* 353 */           this.lastYMovement += d2;
/*     */         else
/* 355 */           this.lastYMovement = d2;
/*     */       }
/*     */     }
/*     */ 
/*     */     public double getDistanceX()
/*     */     {
/* 361 */       return this.endX - this.beginX;
/*     */     }
/*     */ 
/*     */     public double getDistanceY() {
/* 365 */       return this.endY - this.beginY;
/*     */     }
/*     */ 
/*     */     public long getDuration() {
/* 369 */       return this.endTime - this.beginTime;
/*     */     }
/*     */   }
/*     */ 
/*     */   private class MultiTouchTracker
/*     */   {
/* 191 */     SwipeGestureRecognizer.SwipeRecognitionState state = SwipeGestureRecognizer.SwipeRecognitionState.IDLE;
/* 192 */     Map<Long, SwipeGestureRecognizer.TouchPointTracker> trackers = new HashMap();
/*     */ 
/* 194 */     SwipeGestureRecognizer.CenterComputer cc = new SwipeGestureRecognizer.CenterComputer(SwipeGestureRecognizer.this, null);
/*     */     int modifiers;
/*     */     boolean direct;
/*     */     private int touchCount;
/*     */     private int currentTouchCount;
/*     */     private EventType<SwipeEvent> type;
/*     */ 
/*     */     private MultiTouchTracker()
/*     */     {
/*     */     }
/*     */ 
/*     */     public void params(int paramInt, boolean paramBoolean)
/*     */     {
/* 202 */       this.modifiers = paramInt;
/* 203 */       this.direct = paramBoolean;
/*     */     }
/*     */ 
/*     */     public void pressed(long paramLong1, long paramLong2, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 207 */       this.currentTouchCount += 1;
/* 208 */       switch (SwipeGestureRecognizer.2.$SwitchMap$com$sun$javafx$tk$quantum$SwipeGestureRecognizer$SwipeRecognitionState[this.state.ordinal()]) {
/*     */       case 1:
/* 210 */         this.currentTouchCount = 1;
/* 211 */         this.state = SwipeGestureRecognizer.SwipeRecognitionState.ADDING;
/*     */       case 2:
/* 214 */         SwipeGestureRecognizer.TouchPointTracker localTouchPointTracker = new SwipeGestureRecognizer.TouchPointTracker(SwipeGestureRecognizer.this, null);
/* 215 */         localTouchPointTracker.start(paramLong2, paramInt1, paramInt2, paramInt3, paramInt4);
/* 216 */         this.trackers.put(Long.valueOf(paramLong1), localTouchPointTracker);
/* 217 */         break;
/*     */       case 3:
/* 220 */         this.state = SwipeGestureRecognizer.SwipeRecognitionState.FAILURE;
/*     */       }
/*     */     }
/*     */ 
/*     */     public void released(long paramLong1, long paramLong2, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */     {
/* 226 */       if (this.state != SwipeGestureRecognizer.SwipeRecognitionState.FAILURE) {
/* 227 */         SwipeGestureRecognizer.TouchPointTracker localTouchPointTracker = (SwipeGestureRecognizer.TouchPointTracker)this.trackers.get(Long.valueOf(paramLong1));
/*     */ 
/* 229 */         if (localTouchPointTracker == null)
/*     */         {
/* 231 */           this.state = SwipeGestureRecognizer.SwipeRecognitionState.FAILURE;
/* 232 */           throw new RuntimeException("Error in swipe gesture recognition: released unknown touch point");
/*     */         }
/*     */ 
/* 236 */         localTouchPointTracker.end(paramLong2, paramInt1, paramInt2, paramInt3, paramInt4);
/* 237 */         this.cc.add(localTouchPointTracker.beginX, localTouchPointTracker.beginY, localTouchPointTracker.beginAbsX, localTouchPointTracker.beginAbsY);
/*     */ 
/* 239 */         this.cc.add(localTouchPointTracker.endX, localTouchPointTracker.endY, localTouchPointTracker.endAbsX, localTouchPointTracker.endAbsY);
/*     */ 
/* 242 */         EventType localEventType = SwipeGestureRecognizer.this.calcSwipeType(localTouchPointTracker);
/*     */ 
/* 244 */         switch (SwipeGestureRecognizer.2.$SwitchMap$com$sun$javafx$tk$quantum$SwipeGestureRecognizer$SwipeRecognitionState[this.state.ordinal()]) {
/*     */         case 1:
/* 246 */           reset();
/* 247 */           throw new RuntimeException("Error in swipe gesture recognition: released touch point outside of gesture");
/*     */         case 2:
/* 251 */           this.state = SwipeGestureRecognizer.SwipeRecognitionState.REMOVING;
/* 252 */           this.touchCount = this.currentTouchCount;
/* 253 */           this.type = localEventType;
/* 254 */           break;
/*     */         case 3:
/* 256 */           if (this.type != localEventType)
/*     */           {
/* 258 */             this.state = SwipeGestureRecognizer.SwipeRecognitionState.FAILURE;
/*     */           }break;
/*     */         }
/* 261 */         this.trackers.remove(Long.valueOf(paramLong1));
/*     */       }
/*     */ 
/* 264 */       this.currentTouchCount -= 1;
/*     */ 
/* 266 */       if (this.currentTouchCount == 0) {
/* 267 */         if (this.state == SwipeGestureRecognizer.SwipeRecognitionState.REMOVING) {
/* 268 */           SwipeGestureRecognizer.this.handleSwipeType(this.type, this.cc, this.touchCount, this.modifiers, this.direct);
/*     */         }
/*     */ 
/* 271 */         this.state = SwipeGestureRecognizer.SwipeRecognitionState.IDLE;
/* 272 */         reset();
/*     */       }
/*     */     }
/*     */ 
/*     */     public void progress(long paramLong1, long paramLong2, int paramInt1, int paramInt2)
/*     */     {
/* 278 */       if (this.state == SwipeGestureRecognizer.SwipeRecognitionState.FAILURE) {
/* 279 */         return;
/*     */       }
/*     */ 
/* 282 */       SwipeGestureRecognizer.TouchPointTracker localTouchPointTracker = (SwipeGestureRecognizer.TouchPointTracker)this.trackers.get(Long.valueOf(paramLong1));
/*     */ 
/* 284 */       if (localTouchPointTracker == null)
/*     */       {
/* 286 */         this.state = SwipeGestureRecognizer.SwipeRecognitionState.FAILURE;
/* 287 */         throw new RuntimeException("Error in swipe gesture recognition: reported unknown touch point");
/*     */       }
/*     */ 
/* 291 */       localTouchPointTracker.progress(paramLong2, paramInt1, paramInt2);
/*     */     }
/*     */ 
/*     */     void reset() {
/* 295 */       this.trackers.clear();
/* 296 */       this.cc.reset();
/* 297 */       this.state = SwipeGestureRecognizer.SwipeRecognitionState.IDLE;
/*     */     }
/*     */   }
/*     */ 
/*     */   private class CenterComputer
/*     */   {
/* 152 */     double totalAbsX = 0.0D; double totalAbsY = 0.0D;
/* 153 */     double totalX = 0.0D; double totalY = 0.0D;
/* 154 */     int count = 0;
/*     */ 
/*     */     private CenterComputer() {  } 
/* 157 */     public void add(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) { this.totalAbsX += paramDouble3;
/* 158 */       this.totalAbsY += paramDouble4;
/* 159 */       this.totalX += paramDouble1;
/* 160 */       this.totalY += paramDouble2;
/*     */ 
/* 162 */       this.count += 1; }
/*     */ 
/*     */     public double getX()
/*     */     {
/* 166 */       return this.count == 0 ? 0.0D : this.totalX / this.count;
/*     */     }
/*     */ 
/*     */     public double getY() {
/* 170 */       return this.count == 0 ? 0.0D : this.totalY / this.count;
/*     */     }
/*     */ 
/*     */     public double getAbsX() {
/* 174 */       return this.count == 0 ? 0.0D : this.totalAbsX / this.count;
/*     */     }
/*     */ 
/*     */     public double getAbsY() {
/* 178 */       return this.count == 0 ? 0.0D : this.totalAbsY / this.count;
/*     */     }
/*     */ 
/*     */     public void reset() {
/* 182 */       this.totalX = 0.0D;
/* 183 */       this.totalY = 0.0D;
/* 184 */       this.totalAbsX = 0.0D;
/* 185 */       this.totalAbsY = 0.0D;
/* 186 */       this.count = 0;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.quantum.SwipeGestureRecognizer
 * JD-Core Version:    0.6.2
 */