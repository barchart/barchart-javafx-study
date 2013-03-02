/*     */ package javafx.scene.input;
/*     */ 
/*     */ import com.sun.javafx.scene.input.InputEventUtils;
/*     */ import javafx.event.EventTarget;
/*     */ import javafx.geometry.Point2D;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.Scene;
/*     */ 
/*     */ public final class TouchPoint
/*     */ {
/*     */   private EventTarget target;
/*     */   private Object source;
/* 135 */   private EventTarget grabbed = null;
/*     */   private int id;
/*     */   private State state;
/*     */   private double x;
/*     */   private double y;
/*     */   private double screenX;
/*     */   private double screenY;
/*     */   private double sceneX;
/*     */   private double sceneY;
/*     */ 
/*     */   private TouchPoint(int paramInt, State paramState, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*     */   {
/*  60 */     this.target = null;
/*  61 */     this.id = paramInt;
/*  62 */     this.state = paramState;
/*  63 */     this.x = paramDouble1;
/*  64 */     this.y = paramDouble2;
/*  65 */     this.sceneX = paramDouble1;
/*  66 */     this.sceneY = paramDouble2;
/*  67 */     this.screenX = paramDouble3;
/*  68 */     this.screenY = paramDouble4;
/*     */   }
/*     */ 
/*     */   void recomputeToSource(Object paramObject1, Object paramObject2)
/*     */   {
/*  79 */     Point2D localPoint2D = InputEventUtils.recomputeCoordinates(new Point2D(this.sceneX, this.sceneY), null, paramObject2);
/*     */ 
/*  82 */     this.x = localPoint2D.getX();
/*  83 */     this.y = localPoint2D.getY();
/*     */ 
/*  85 */     this.source = paramObject2;
/*     */   }
/*     */ 
/*     */   public boolean belongsTo(EventTarget paramEventTarget)
/*     */   {
/*  99 */     if ((this.target instanceof Node)) {
/* 100 */       Object localObject = (Node)this.target;
/*     */ 
/* 102 */       if ((paramEventTarget instanceof Scene)) {
/* 103 */         return ((Node)localObject).getScene() == paramEventTarget;
/*     */       }
/* 105 */       while (localObject != null) {
/* 106 */         if (localObject == paramEventTarget) {
/* 107 */           return true;
/*     */         }
/* 109 */         localObject = ((Node)localObject).getParent();
/*     */       }
/*     */     }
/*     */ 
/* 113 */     return paramEventTarget == this.target;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void impl_setTarget(EventTarget paramEventTarget)
/*     */   {
/* 122 */     this.target = paramEventTarget;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void impl_reset()
/*     */   {
/* 131 */     this.x = this.sceneX;
/* 132 */     this.y = this.sceneY;
/*     */   }
/*     */ 
/*     */   public EventTarget getGrabbed()
/*     */   {
/* 142 */     return this.grabbed;
/*     */   }
/*     */ 
/*     */   public void grab()
/*     */   {
/* 151 */     if ((this.source instanceof EventTarget))
/* 152 */       this.grabbed = ((EventTarget)this.source);
/*     */     else
/* 154 */       throw new IllegalStateException("Cannot grab touch point, source is not an instance of EventTarget: " + this.source);
/*     */   }
/*     */ 
/*     */   public void grab(EventTarget paramEventTarget)
/*     */   {
/* 165 */     this.grabbed = paramEventTarget;
/*     */   }
/*     */ 
/*     */   public void ungrab() {
/* 169 */     this.grabbed = null;
/*     */   }
/*     */ 
/*     */   public final int getId()
/*     */   {
/* 183 */     return this.id;
/*     */   }
/*     */ 
/*     */   public final State getState()
/*     */   {
/* 193 */     return this.state;
/*     */   }
/*     */ 
/*     */   public final double getX()
/*     */   {
/* 207 */     return this.x;
/*     */   }
/*     */ 
/*     */   public final double getY()
/*     */   {
/* 220 */     return this.y;
/*     */   }
/*     */ 
/*     */   public final double getScreenX()
/*     */   {
/* 230 */     return this.screenX;
/*     */   }
/*     */ 
/*     */   public final double getScreenY()
/*     */   {
/* 240 */     return this.screenY;
/*     */   }
/*     */ 
/*     */   public final double getSceneX()
/*     */   {
/* 255 */     return this.sceneX;
/*     */   }
/*     */ 
/*     */   public final double getSceneY()
/*     */   {
/* 270 */     return this.sceneY;
/*     */   }
/*     */ 
/*     */   public EventTarget getTarget()
/*     */   {
/* 279 */     return this.target;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 287 */     StringBuilder localStringBuilder = new StringBuilder("TouchPoint [");
/*     */ 
/* 289 */     localStringBuilder.append("state = ").append(getState());
/* 290 */     localStringBuilder.append(", id = ").append(getId());
/* 291 */     localStringBuilder.append(", target = ").append(getTarget());
/* 292 */     localStringBuilder.append(", x = ").append(getX()).append(", y = ").append(getY());
/*     */ 
/* 294 */     return "]";
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public static TouchPoint impl_touchPoint(int paramInt, State paramState, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*     */   {
/* 304 */     return new TouchPoint(paramInt, paramState, paramDouble1, paramDouble2, paramDouble3, paramDouble4);
/*     */   }
/*     */ 
/*     */   public static enum State
/*     */   {
/* 316 */     PRESSED, 
/*     */ 
/* 320 */     MOVED, 
/*     */ 
/* 324 */     STATIONARY, 
/*     */ 
/* 328 */     RELEASED;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.input.TouchPoint
 * JD-Core Version:    0.6.2
 */