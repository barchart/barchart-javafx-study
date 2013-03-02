/*     */ package com.sun.javafx.scene.control.behavior;
/*     */ 
/*     */ import com.sun.javafx.scene.control.skin.PaginationSkin;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javafx.scene.control.Pagination;
/*     */ import javafx.scene.input.KeyCode;
/*     */ import javafx.scene.input.MouseEvent;
/*     */ 
/*     */ public class PaginationBehavior extends BehaviorBase<Pagination>
/*     */ {
/*     */   private static final String LEFT = "Left";
/*     */   private static final String RIGHT = "Right";
/*  62 */   protected static final List<KeyBinding> PAGINATION_BINDINGS = new ArrayList();
/*     */ 
/*     */   protected List<KeyBinding> createKeyBindings()
/*     */   {
/*  70 */     return PAGINATION_BINDINGS;
/*     */   }
/*     */ 
/*     */   protected void callAction(String paramString)
/*     */   {
/*     */     PaginationSkin localPaginationSkin;
/*  74 */     if ("Left".equals(paramString)) {
/*  75 */       localPaginationSkin = (PaginationSkin)((Pagination)getControl()).getSkin();
/*  76 */       localPaginationSkin.selectPrevious();
/*  77 */     } else if ("Right".equals(paramString)) {
/*  78 */       localPaginationSkin = (PaginationSkin)((Pagination)getControl()).getSkin();
/*  79 */       localPaginationSkin.selectNext();
/*     */     } else {
/*  81 */       super.callAction(paramString);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void mousePressed(MouseEvent paramMouseEvent)
/*     */   {
/*  92 */     super.mousePressed(paramMouseEvent);
/*  93 */     Pagination localPagination = (Pagination)getControl();
/*  94 */     localPagination.requestFocus();
/*     */   }
/*     */ 
/*     */   public PaginationBehavior(Pagination paramPagination)
/*     */   {
/* 102 */     super(paramPagination);
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  64 */     PAGINATION_BINDINGS.add(new KeyBinding(KeyCode.LEFT, "Left"));
/*  65 */     PAGINATION_BINDINGS.add(new KeyBinding(KeyCode.RIGHT, "Right"));
/*  66 */     PAGINATION_BINDINGS.addAll(TRAVERSAL_BINDINGS);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.behavior.PaginationBehavior
 * JD-Core Version:    0.6.2
 */