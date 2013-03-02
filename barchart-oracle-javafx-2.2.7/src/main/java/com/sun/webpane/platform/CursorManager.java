/*    */ package com.sun.webpane.platform;
/*    */ 
/*    */ import com.sun.webpane.platform.graphics.WCImage;
/*    */ import com.sun.webpane.platform.graphics.WCImageFrame;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public abstract class CursorManager<T>
/*    */ {
/*    */   public static final int POINTER = 0;
/*    */   public static final int CROSS = 1;
/*    */   public static final int HAND = 2;
/*    */   public static final int MOVE = 3;
/*    */   public static final int TEXT = 4;
/*    */   public static final int WAIT = 5;
/*    */   public static final int HELP = 6;
/*    */   public static final int EAST_RESIZE = 7;
/*    */   public static final int NORTH_RESIZE = 8;
/*    */   public static final int NORTH_EAST_RESIZE = 9;
/*    */   public static final int NORTH_WEST_RESIZE = 10;
/*    */   public static final int SOUTH_RESIZE = 11;
/*    */   public static final int SOUTH_EAST_RESIZE = 12;
/*    */   public static final int SOUTH_WEST_RESIZE = 13;
/*    */   public static final int WEST_RESIZE = 14;
/*    */   public static final int NORTH_SOUTH_RESIZE = 15;
/*    */   public static final int EAST_WEST_RESIZE = 16;
/*    */   public static final int NORTH_EAST_SOUTH_WEST_RESIZE = 17;
/*    */   public static final int NORTH_WEST_SOUTH_EAST_RESIZE = 18;
/*    */   public static final int COLUMN_RESIZE = 19;
/*    */   public static final int ROW_RESIZE = 20;
/*    */   public static final int MIDDLE_PANNING = 21;
/*    */   public static final int EAST_PANNING = 22;
/*    */   public static final int NORTH_PANNING = 23;
/*    */   public static final int NORTH_EAST_PANNING = 24;
/*    */   public static final int NORTH_WEST_PANNING = 25;
/*    */   public static final int SOUTH_PANNING = 26;
/*    */   public static final int SOUTH_EAST_PANNING = 27;
/*    */   public static final int SOUTH_WEST_PANNING = 28;
/*    */   public static final int WEST_PANNING = 29;
/*    */   public static final int VERTICAL_TEXT = 30;
/*    */   public static final int CELL = 31;
/*    */   public static final int CONTEXT_MENU = 32;
/*    */   public static final int NO_DROP = 33;
/*    */   public static final int NOT_ALLOWED = 34;
/*    */   public static final int PROGRESS = 35;
/*    */   public static final int ALIAS = 36;
/*    */   public static final int ZOOM_IN = 37;
/*    */   public static final int ZOOM_OUT = 38;
/*    */   public static final int COPY = 39;
/*    */   public static final int NONE = 40;
/*    */   public static final int GRAB = 41;
/*    */   public static final int GRABBING = 42;
/*    */   private static CursorManager instance;
/* 68 */   private final Map<Long, T> map = new HashMap();
/*    */ 
/*    */   public static void setCursorManager(CursorManager paramCursorManager)
/*    */   {
/* 61 */     instance = paramCursorManager;
/*    */   }
/*    */ 
/*    */   public static CursorManager getCursorManager() {
/* 65 */     return instance;
/*    */   }
/*    */ 
/*    */   protected abstract T getCustomCursor(WCImage paramWCImage, int paramInt1, int paramInt2);
/*    */ 
/*    */   protected abstract T getPredefinedCursor(int paramInt);
/*    */ 
/*    */   public final long getCustomCursorID(WCImageFrame paramWCImageFrame, int paramInt1, int paramInt2)
/*    */   {
/* 75 */     return putCursor(getCustomCursor(paramWCImageFrame.getFrame(), paramInt1, paramInt2));
/*    */   }
/*    */ 
/*    */   public final long getPredefinedCursorID(int paramInt) {
/* 79 */     return putCursor(getPredefinedCursor(paramInt));
/*    */   }
/*    */ 
/*    */   public final T getCursor(long paramLong) {
/* 83 */     return this.map.get(Long.valueOf(paramLong));
/*    */   }
/*    */ 
/*    */   private long putCursor(T paramT) {
/* 87 */     long l = paramT.hashCode();
/* 88 */     this.map.put(Long.valueOf(l), paramT);
/* 89 */     return l;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.platform.CursorManager
 * JD-Core Version:    0.6.2
 */