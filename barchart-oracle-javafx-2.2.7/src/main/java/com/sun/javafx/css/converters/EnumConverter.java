/*    */ package com.sun.javafx.css.converters;
/*    */ 
/*    */ import com.sun.javafx.css.ParsedValue;
/*    */ import com.sun.javafx.css.StringStore;
/*    */ import com.sun.javafx.css.StyleConverter;
/*    */ import java.io.DataInputStream;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.PrintStream;
/*    */ import javafx.scene.text.Font;
/*    */ 
/*    */ public final class EnumConverter<T extends Enum<T>> extends StyleConverter<String, T>
/*    */ {
/*    */   private final Class enumClass;
/*    */ 
/*    */   public EnumConverter(Class paramClass)
/*    */   {
/* 42 */     this.enumClass = paramClass;
/*    */   }
/*    */ 
/*    */   public T convert(ParsedValue<String, T> paramParsedValue, Font paramFont)
/*    */   {
/* 47 */     if (this.enumClass == null) {
/* 48 */       return null;
/*    */     }
/* 50 */     String str = (String)paramParsedValue.getValue();
/* 51 */     int i = str.lastIndexOf(46);
/* 52 */     if (i > -1)
/* 53 */       str = str.substring(i + 1);
/*    */     try
/*    */     {
/* 56 */       str = str.replace('-', '_');
/* 57 */       return Enum.valueOf(this.enumClass, str.toUpperCase());
/*    */     } catch (IllegalArgumentException localIllegalArgumentException) {
/*    */     }
/* 60 */     return Enum.valueOf(this.enumClass, str);
/*    */   }
/*    */ 
/*    */   public void writeBinary(DataOutputStream paramDataOutputStream, StringStore paramStringStore)
/*    */     throws IOException
/*    */   {
/* 66 */     super.writeBinary(paramDataOutputStream, paramStringStore);
/* 67 */     int i = paramStringStore.addString(this.enumClass.getName());
/* 68 */     paramDataOutputStream.writeShort(i);
/*    */   }
/*    */ 
/*    */   public EnumConverter(DataInputStream paramDataInputStream, String[] paramArrayOfString) {
/* 72 */     Class localClass = null;
/*    */     try {
/* 74 */       int i = paramDataInputStream.readShort();
/* 75 */       String str = paramArrayOfString[i];
/* 76 */       localClass = Class.forName(str);
/*    */     } catch (IOException localIOException) {
/* 78 */       System.err.println("EnumConveter caught: " + localIOException);
/*    */     } catch (ClassNotFoundException localClassNotFoundException) {
/* 80 */       System.err.println("EnumConveter caught: " + localClassNotFoundException.toString());
/*    */     }
/* 82 */     this.enumClass = localClass;
/*    */   }
/*    */ 
/*    */   public boolean equals(Object paramObject)
/*    */   {
/* 87 */     if (paramObject == this) return true;
/* 88 */     if ((paramObject == null) || (!(paramObject instanceof EnumConverter))) return false;
/* 89 */     return this.enumClass.equals(((EnumConverter)paramObject).enumClass);
/*    */   }
/*    */ 
/*    */   public int hashCode()
/*    */   {
/* 94 */     return this.enumClass.hashCode();
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 99 */     return "EnumConveter[" + this.enumClass.getName() + "]";
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.css.converters.EnumConverter
 * JD-Core Version:    0.6.2
 */