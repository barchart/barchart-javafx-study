/*    */ package com.sun.prism.impl.packrect;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class Level
/*    */ {
/*    */   private int width;
/*    */   private int height;
/*    */   private int yPos;
/*    */   private LevelSet holder;
/* 50 */   private List rects = new ArrayList();
/*    */   private int nextAddX;
/*    */ 
/*    */   public Level(int paramInt1, int paramInt2, int paramInt3, LevelSet paramLevelSet)
/*    */   {
/* 54 */     this.width = paramInt1;
/* 55 */     this.height = paramInt2;
/* 56 */     this.yPos = paramInt3;
/* 57 */     this.holder = paramLevelSet;
/*    */   }
/*    */   public int w() {
/* 60 */     return this.width; } 
/* 61 */   public int h() { return this.height; } 
/* 62 */   public int yPos() { return this.yPos; } 
/* 63 */   public int nextX() { return this.nextAddX; }
/*    */ 
/*    */ 
/*    */   public boolean add(Rect paramRect)
/*    */   {
/* 68 */     if ((this.nextAddX + paramRect.w() <= this.width) && (paramRect.h() <= this.height)) {
/* 69 */       paramRect.setPosition(this.nextAddX, this.yPos);
/* 70 */       this.rects.add(paramRect);
/* 71 */       this.nextAddX += paramRect.w();
/* 72 */       return true;
/*    */     }
/* 74 */     return false;
/*    */   }
/*    */ 
/*    */   public int size()
/*    */   {
/* 79 */     return this.rects.size();
/*    */   }
/*    */ 
/*    */   public boolean isEmpty()
/*    */   {
/* 84 */     return this.rects.isEmpty();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.impl.packrect.Level
 * JD-Core Version:    0.6.2
 */