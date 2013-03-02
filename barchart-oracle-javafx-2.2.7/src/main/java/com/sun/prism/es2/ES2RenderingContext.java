/*    */ package com.sun.prism.es2;
/*    */ 
/*    */ import com.sun.glass.ui.View;
/*    */ import com.sun.glass.ui.Window;
/*    */ import com.sun.prism.es2.gl.GLDrawable;
/*    */ import com.sun.prism.es2.gl.GLFactory;
/*    */ import com.sun.prism.impl.BaseRenderingContext;
/*    */ 
/*    */ public class ES2RenderingContext extends BaseRenderingContext
/*    */ {
/*    */   private final ES2Context context;
/*    */   private final GLDrawable drawable;
/*    */ 
/*    */   ES2RenderingContext(ES2Context paramES2Context, View paramView)
/*    */   {
/* 21 */     this.context = paramES2Context;
/* 22 */     if (paramView != null) {
/* 23 */       long l = paramView.getWindow().getNativeWindow();
/* 24 */       this.drawable = ES2Pipeline.glFactory.createGLDrawable(l, paramES2Context.getPixelFormat());
/*    */     }
/*    */     else {
/* 27 */       this.drawable = paramES2Context.getDummyDrawable();
/*    */     }
/*    */   }
/*    */ 
/*    */   public void begin()
/*    */   {
/* 36 */     this.context.setCurrentRenderingContext(this, this.drawable);
/*    */   }
/*    */ 
/*    */   public void end()
/*    */   {
/* 45 */     this.context.setCurrentRenderingContext(null, this.context.getDummyDrawable());
/*    */   }
/*    */ 
/*    */   GLDrawable getDrawable() {
/* 49 */     return this.drawable;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.es2.ES2RenderingContext
 * JD-Core Version:    0.6.2
 */