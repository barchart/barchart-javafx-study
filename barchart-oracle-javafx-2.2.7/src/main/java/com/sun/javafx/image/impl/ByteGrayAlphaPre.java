/*    */ package com.sun.javafx.image.impl;
/*    */ 
/*    */ import com.sun.javafx.image.BytePixelAccessor;
/*    */ import com.sun.javafx.image.BytePixelGetter;
/*    */ import com.sun.javafx.image.BytePixelSetter;
/*    */ import com.sun.javafx.image.ByteToBytePixelConverter;
/*    */ 
/*    */ public class ByteGrayAlphaPre
/*    */ {
/* 34 */   public static final BytePixelGetter getter = ByteGrayAlpha.Accessor.premul;
/* 35 */   public static final BytePixelSetter setter = ByteGrayAlpha.Accessor.premul;
/* 36 */   public static final BytePixelAccessor accessor = ByteGrayAlpha.Accessor.premul;
/*    */ 
/* 38 */   public static final ByteToBytePixelConverter ToByteBgraPre = ByteGrayAlpha.ToByteBgraSameConv.premul;
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.image.impl.ByteGrayAlphaPre
 * JD-Core Version:    0.6.2
 */