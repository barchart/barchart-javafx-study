/*    */ package com.sun.javafx.css;
/*    */ 
/*    */ import java.io.DataInputStream;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class StringStore
/*    */ {
/* 59 */   private Map<String, Integer> stringMap = new HashMap();
/* 60 */   public List<String> strings = new ArrayList();
/*    */ 
/*    */   public int addString(String paramString)
/*    */   {
/* 64 */     Integer localInteger = (Integer)this.stringMap.get(paramString);
/* 65 */     if (localInteger == null) {
/* 66 */       localInteger = Integer.valueOf(this.strings.size());
/* 67 */       this.strings.add(paramString);
/* 68 */       this.stringMap.put(paramString, localInteger);
/*    */     }
/* 70 */     return localInteger.intValue();
/*    */   }
/*    */ 
/*    */   public void writeBinary(DataOutputStream paramDataOutputStream) throws IOException {
/* 74 */     paramDataOutputStream.writeShort(this.strings.size());
/* 75 */     if (this.stringMap.containsKey(null)) {
/* 76 */       Integer localInteger = (Integer)this.stringMap.get(null);
/* 77 */       paramDataOutputStream.writeShort(localInteger.intValue());
/*    */     } else {
/* 79 */       paramDataOutputStream.writeShort(-1);
/*    */     }
/* 81 */     for (int i = 0; i < this.strings.size(); i++) {
/* 82 */       String str = (String)this.strings.get(i);
/* 83 */       if (str != null)
/* 84 */         paramDataOutputStream.writeUTF(str);
/*    */     }
/*    */   }
/*    */ 
/*    */   static String[] readBinary(DataInputStream paramDataInputStream) throws IOException
/*    */   {
/* 90 */     int i = paramDataInputStream.readShort();
/* 91 */     int j = paramDataInputStream.readShort();
/* 92 */     String[] arrayOfString = new String[i];
/* 93 */     Arrays.fill(arrayOfString, null);
/* 94 */     for (int k = 0; k < i; k++) {
/* 95 */       if (k != j)
/* 96 */         arrayOfString[k] = paramDataInputStream.readUTF();
/*    */     }
/* 98 */     return arrayOfString;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.css.StringStore
 * JD-Core Version:    0.6.2
 */