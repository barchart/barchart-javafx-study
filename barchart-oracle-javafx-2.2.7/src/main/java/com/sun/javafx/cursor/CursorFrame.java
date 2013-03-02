/*    */ package com.sun.javafx.cursor;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public abstract class CursorFrame
/*    */ {
/*    */   private Class<?> firstPlatformCursorClass;
/*    */   private Object firstPlatformCursor;
/*    */   private Map<Class<?>, Object> otherPlatformCursors;
/*    */ 
/*    */   public abstract CursorType getCursorType();
/*    */ 
/*    */   public <T> T getPlatformCursor(Class<T> paramClass)
/*    */   {
/* 45 */     if (this.firstPlatformCursorClass == paramClass) {
/* 46 */       return this.firstPlatformCursor;
/*    */     }
/*    */ 
/* 49 */     if (this.otherPlatformCursors != null) {
/* 50 */       return this.otherPlatformCursors.get(paramClass);
/*    */     }
/*    */ 
/* 53 */     return null;
/*    */   }
/*    */ 
/*    */   public <T> void setPlatforCursor(Class<T> paramClass, T paramT)
/*    */   {
/* 59 */     if ((this.firstPlatformCursorClass == null) || (this.firstPlatformCursorClass == paramClass))
/*    */     {
/* 62 */       this.firstPlatformCursorClass = paramClass;
/* 63 */       this.firstPlatformCursor = paramT;
/* 64 */       return;
/*    */     }
/*    */ 
/* 67 */     if (this.otherPlatformCursors == null) {
/* 68 */       this.otherPlatformCursors = new HashMap();
/*    */     }
/*    */ 
/* 71 */     this.otherPlatformCursors.put(paramClass, paramT);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.cursor.CursorFrame
 * JD-Core Version:    0.6.2
 */