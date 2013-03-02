/*     */ package com.sun.javafx.css;
/*     */ 
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import javafx.scene.paint.Color;
/*     */ import javafx.scene.text.Font;
/*     */ 
/*     */ public class ParsedValue<V, T>
/*     */ {
/*     */   private final V value;
/*     */   private final StyleConverter<V, T> converter;
/*     */   private final boolean lookup;
/*     */   private final boolean containsLookups;
/*     */   private final boolean needsFont;
/*     */   ParsedValue resolved;
/* 270 */   private static final String newline = (String)AccessController.doPrivileged(new PrivilegedAction()
/*     */   {
/*     */     public String run()
/*     */     {
/* 274 */       return System.getProperty("line.separator");
/*     */     }
/*     */   });
/*     */ 
/* 279 */   private static int indent = 0;
/*     */ 
/* 458 */   private int hc = -1;
/*     */   private static final byte NULL_VALUE = 0;
/*     */   private static final byte VALUE = 1;
/*     */   private static final byte VALUE_ARRAY = 2;
/*     */   private static final byte ARRAY_OF_VALUE_ARRAY = 3;
/*     */   private static final byte STRING = 4;
/*     */   private static final byte COLOR = 5;
/*     */   private static final byte ENUM = 6;
/*     */   private static final byte BOOLEAN = 7;
/*     */   private static final byte URL = 8;
/*     */   private static final byte SIZE = 9;
/*     */ 
/*     */   public final V getValue()
/*     */   {
/*  57 */     return this.value;
/*     */   }
/*     */ 
/*     */   public final StyleConverter<V, T> getConverter()
/*     */   {
/*  64 */     return this.converter;
/*     */   }
/*     */ 
/*     */   public final boolean isLookup()
/*     */   {
/*  71 */     return this.lookup;
/*     */   }
/*     */ 
/*     */   public final boolean isContainsLookups()
/*     */   {
/*  80 */     return this.containsLookups;
/*     */   }
/*     */ 
/*     */   private static boolean getContainsLookupsFlag(Object paramObject)
/*     */   {
/*  85 */     boolean bool = false;
/*     */ 
/*  87 */     if ((paramObject instanceof Size)) {
/*  88 */       bool = false;
/*     */     }
/*     */     else
/*     */     {
/*     */       Object localObject;
/*  91 */       if ((paramObject instanceof ParsedValue)) {
/*  92 */         localObject = (ParsedValue)paramObject;
/*  93 */         bool = (((ParsedValue)localObject).lookup) || (((ParsedValue)localObject).containsLookups);
/*     */       }
/*     */       else
/*     */       {
/*     */         int i;
/*  96 */         if ((paramObject instanceof ParsedValue[])) {
/*  97 */           localObject = (ParsedValue[])paramObject;
/*  98 */           for (i = 0; 
/* 101 */             (i < localObject.length) && (!bool); 
/* 102 */             i++)
/*     */           {
/* 104 */             if (localObject[i] != null) {
/* 105 */               bool = (bool) || (localObject[i].lookup) || (localObject[i].containsLookups);
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/*     */         }
/* 112 */         else if ((paramObject instanceof ParsedValue[][])) {
/* 113 */           localObject = (ParsedValue[][])paramObject;
/* 114 */           for (i = 0; 
/* 115 */             (i < localObject.length) && (!bool); 
/* 116 */             i++)
/*     */           {
/* 118 */             if (localObject[i] != null) {
/* 119 */               for (int j = 0; 
/* 120 */                 (j < localObject[i].length) && (!bool); 
/* 121 */                 j++)
/*     */               {
/* 123 */                 if (localObject[i][j] != null) {
/* 124 */                   bool = (bool) || (localObject[i][j].lookup) || (localObject[i][j].containsLookups);
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 134 */     return bool;
/*     */   }
/*     */ 
/*     */   public boolean isNeedsFont()
/*     */   {
/* 139 */     if ((this.resolved != null) && (this.resolved != this)) {
/* 140 */       return this.resolved.needsFont;
/*     */     }
/* 142 */     return this.needsFont;
/*     */   }
/*     */ 
/*     */   private static boolean getNeedsFontFlag(Object paramObject)
/*     */   {
/* 148 */     boolean bool = false;
/*     */ 
/* 150 */     if ((paramObject instanceof Size)) {
/* 151 */       bool = !((Size)paramObject).isAbsolute();
/*     */     }
/*     */     else
/*     */     {
/*     */       Object localObject;
/* 154 */       if ((paramObject instanceof ParsedValue)) {
/* 155 */         localObject = (ParsedValue)paramObject;
/* 156 */         bool = ((ParsedValue)localObject).needsFont;
/*     */       }
/*     */       else
/*     */       {
/*     */         int i;
/* 159 */         if ((paramObject instanceof ParsedValue[])) {
/* 160 */           localObject = (ParsedValue[])paramObject;
/* 161 */           for (i = 0; 
/* 162 */             (i < localObject.length) && (!bool); 
/* 163 */             i++)
/*     */           {
/* 165 */             if (localObject[i] != null)
/* 166 */               bool = localObject[i].needsFont;
/*     */           }
/*     */         }
/* 169 */         else if ((paramObject instanceof ParsedValue[][])) {
/* 170 */           localObject = (ParsedValue[][])paramObject;
/* 171 */           for (i = 0; 
/* 172 */             (i < localObject.length) && (!bool); 
/* 173 */             i++)
/*     */           {
/* 175 */             if (localObject[i] != null)
/* 176 */               for (int j = 0; 
/* 177 */                 (j < localObject[i].length) && (!bool); 
/* 178 */                 j++)
/*     */               {
/* 180 */                 if (localObject[i][j] != null)
/* 181 */                   bool = localObject[i][j].needsFont; 
/*     */               }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 186 */     return bool;
/*     */   }
/*     */ 
/*     */   public ParsedValue(V paramV, StyleConverter<V, T> paramStyleConverter, boolean paramBoolean)
/*     */   {
/* 197 */     this.value = paramV;
/* 198 */     this.converter = paramStyleConverter;
/* 199 */     this.lookup = paramBoolean;
/* 200 */     this.containsLookups = ((paramBoolean) || (getContainsLookupsFlag(paramV)));
/* 201 */     this.needsFont = getNeedsFontFlag(paramV);
/*     */   }
/*     */ 
/*     */   public ParsedValue(V paramV, StyleConverter<V, T> paramStyleConverter)
/*     */   {
/* 211 */     this(paramV, paramStyleConverter, false);
/*     */   }
/*     */ 
/*     */   private ParsedValue()
/*     */   {
/* 216 */     this(null, null);
/*     */   }
/*     */ 
/*     */   void nullResolved()
/*     */   {
/* 234 */     if ((this.resolved == this) || (this.resolved == null)) return;
/*     */ 
/* 236 */     Object localObject1 = this.resolved.getValue();
/*     */     Object localObject2;
/*     */     int i;
/* 237 */     if ((localObject1 instanceof ParsedValue[])) {
/* 238 */       localObject2 = (ParsedValue[])localObject1;
/* 239 */       for (i = 0; i < localObject2.length; i++) {
/* 240 */         if (localObject2[i] != null)
/* 241 */           localObject2[i].nullResolved();
/*     */       }
/*     */     }
/* 244 */     else if ((localObject1 instanceof ParsedValue[][])) {
/* 245 */       localObject2 = (ParsedValue[][])localObject1;
/* 246 */       for (i = 0; i < localObject2.length; i++) {
/* 247 */         if (localObject2[i] != null) {
/* 248 */           for (int j = 0; j < localObject2[i].length; j++)
/*     */           {
/* 250 */             if (localObject2[i][j] != null)
/* 251 */               localObject2[i][j].nullResolved();
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 256 */     this.resolved = null;
/*     */   }
/*     */ 
/*     */   public T convert(Font paramFont)
/*     */   {
/* 262 */     if ((this.resolved != null) && (this.resolved != this)) {
/* 263 */       return this.resolved.convert(paramFont);
/*     */     }
/* 265 */     return this.converter != null ? this.converter.convert(this, paramFont) : this.value;
/*     */   }
/*     */ 
/*     */   private static String spaces()
/*     */   {
/* 282 */     return new String(new char[indent]).replace('\000', ' ');
/*     */   }
/*     */ 
/*     */   private static void indent() {
/* 286 */     indent += 2;
/*     */   }
/*     */ 
/*     */   private static void outdent() {
/* 290 */     indent = Math.max(0, indent - 2);
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 294 */     StringBuilder localStringBuilder = new StringBuilder();
/* 295 */     localStringBuilder.append(spaces()).append(this.lookup ? "<Value lookup=\"true\">" : "<Value>").append(newline);
/*     */ 
/* 298 */     indent();
/* 299 */     appendValue(localStringBuilder, this.value, "value");
/* 300 */     if ((this.resolved != null) && (this.resolved != this)) {
/* 301 */       appendValue(localStringBuilder, this.resolved, "resolved");
/*     */     }
/* 303 */     localStringBuilder.append(spaces()).append("<converter>").append(this.converter).append("</converter>").append(newline);
/*     */ 
/* 308 */     outdent();
/* 309 */     localStringBuilder.append(spaces()).append("</Value>").append(newline);
/* 310 */     return localStringBuilder.toString();
/*     */   }
/*     */ 
/*     */   private void appendValue(StringBuilder paramStringBuilder, Object paramObject, String paramString)
/*     */   {
/*     */     Object localObject1;
/*     */     Object localObject3;
/* 314 */     if ((paramObject instanceof ParsedValue[][])) {
/* 315 */       localObject1 = (ParsedValue[][])paramObject;
/* 316 */       paramStringBuilder.append(spaces()).append('<').append(paramString).append(" layers=\"").append(localObject1.length).append("\">").append(newline);
/*     */ 
/* 323 */       indent();
/* 324 */       for (localObject3 : localObject1) {
/* 325 */         paramStringBuilder.append(spaces()).append("<layer>").append(newline);
/*     */ 
/* 328 */         indent();
/* 329 */         for (Object localObject5 : localObject3) {
/* 330 */           if (localObject5 == null)
/* 331 */             paramStringBuilder.append(spaces()).append("null").append(newline);
/*     */           else {
/* 333 */             paramStringBuilder.append(localObject5);
/*     */           }
/*     */         }
/* 336 */         outdent();
/* 337 */         paramStringBuilder.append(spaces()).append("</layer>").append(newline);
/*     */       }
/*     */ 
/* 341 */       outdent();
/* 342 */       paramStringBuilder.append(spaces()).append("</").append(paramString).append('>').append(newline);
/*     */     }
/* 344 */     else if ((paramObject instanceof ParsedValue[])) {
/* 345 */       localObject1 = (ParsedValue[])paramObject;
/* 346 */       paramStringBuilder.append(spaces()).append('<').append(paramString).append(" values=\"").append(localObject1.length).append("\">").append(newline);
/*     */ 
/* 353 */       indent();
/* 354 */       for (localObject3 : localObject1) {
/* 355 */         if (localObject3 == null)
/* 356 */           paramStringBuilder.append(spaces()).append("null").append(newline);
/*     */         else {
/* 358 */           paramStringBuilder.append(localObject3);
/*     */         }
/*     */       }
/* 361 */       outdent();
/* 362 */       paramStringBuilder.append(spaces()).append("</").append(paramString).append('>').append(newline);
/* 363 */     } else if ((paramObject instanceof ParsedValue)) {
/* 364 */       paramStringBuilder.append(spaces()).append('<').append(paramString).append('>').append(newline);
/* 365 */       indent();
/* 366 */       paramStringBuilder.append(paramObject);
/* 367 */       outdent();
/* 368 */       paramStringBuilder.append(spaces()).append("</").append(paramString).append('>').append(newline);
/*     */     } else {
/* 370 */       paramStringBuilder.append(spaces()).append('<').append(paramString).append('>');
/* 371 */       paramStringBuilder.append(paramObject);
/* 372 */       paramStringBuilder.append("</").append(paramString).append('>').append(newline);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 378 */     if (paramObject == this) return true;
/*     */ 
/* 380 */     if ((paramObject instanceof ParsedValue))
/*     */     {
/* 382 */       ParsedValue localParsedValue = (ParsedValue)paramObject;
/*     */       Object localObject1;
/*     */       Object localObject2;
/*     */       int i;
/*     */       Object localObject4;
/* 383 */       if ((this.value instanceof ParsedValue[][]))
/*     */       {
/* 385 */         if (!(localParsedValue.value instanceof ParsedValue[][])) return false;
/*     */ 
/* 387 */         localObject1 = (ParsedValue[][])this.value;
/* 388 */         localObject2 = (ParsedValue[][])localParsedValue.value;
/*     */ 
/* 392 */         if (localObject1.length != localObject2.length) return false;
/*     */ 
/* 394 */         for (i = 0; i < localObject1.length; i++)
/*     */         {
/* 399 */           if ((localObject1[i] != null) || (localObject2[i] != null)) {
/* 400 */             if ((localObject1[i] == null) || (localObject2[i] == null)) return false;
/*     */ 
/* 402 */             if (localObject1[i].length != localObject2[i].length) return false;
/*     */ 
/* 404 */             for (int j = 0; j < localObject1[i].length; j++)
/*     */             {
/* 406 */               localObject4 = localObject1[i][j];
/* 407 */               Object localObject5 = localObject2[i][j];
/*     */ 
/* 409 */               if (localObject4 != null ? !localObject4.equals(localObject5) : localObject5 != null)
/*     */               {
/* 412 */                 return false;
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/* 415 */         return true;
/*     */       }
/* 417 */       if ((this.value instanceof ParsedValue[]))
/*     */       {
/* 419 */         if (!(localParsedValue.value instanceof ParsedValue[])) return false;
/*     */ 
/* 421 */         localObject1 = (ParsedValue[])this.value;
/* 422 */         localObject2 = (ParsedValue[])localParsedValue.value;
/*     */ 
/* 426 */         if (localObject1.length != localObject2.length) return false;
/*     */ 
/* 428 */         for (i = 0; i < localObject1.length; i++)
/*     */         {
/* 430 */           Object localObject3 = localObject1[i];
/* 431 */           localObject4 = localObject2[i];
/*     */ 
/* 433 */           if (localObject3 != null ? !localObject3.equals(localObject4) : localObject4 != null)
/*     */           {
/* 436 */             return false;
/*     */           }
/*     */         }
/* 438 */         return true;
/*     */       }
/*     */ 
/* 442 */       if (((localParsedValue.value instanceof ParsedValue[][])) || ((localParsedValue.value instanceof ParsedValue[]))) {
/* 443 */         return false;
/*     */       }
/*     */ 
/* 446 */       return localParsedValue.value == null ? true : this.value != null ? this.value.equals(localParsedValue.value) : false;
/*     */     }
/*     */ 
/* 455 */     return false;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 460 */     if (this.hc == -1) {
/* 461 */       this.hc = 17;
/*     */       Object localObject1;
/*     */       int i;
/* 462 */       if ((this.value instanceof ParsedValue[][])) {
/* 463 */         localObject1 = (ParsedValue[][])this.value;
/* 464 */         for (i = 0; i < localObject1.length; i++)
/* 465 */           for (int j = 0; j < localObject1[i].length; j++) {
/* 466 */             Object localObject3 = localObject1[i][j];
/* 467 */             this.hc = (37 * this.hc + ((localObject3 != null) && (localObject3.value != null) ? localObject3.value.hashCode() : 0));
/*     */           }
/*     */       }
/* 470 */       else if ((this.value instanceof ParsedValue[])) {
/* 471 */         localObject1 = (ParsedValue[])this.value;
/* 472 */         for (i = 0; i < localObject1.length; i++)
/* 473 */           if ((localObject1[i] != null) && (localObject1[i].value != null)) {
/* 474 */             Object localObject2 = localObject1[i];
/* 475 */             this.hc = (37 * this.hc + ((localObject2 != null) && (localObject2.value != null) ? localObject2.value.hashCode() : 0));
/*     */           }
/*     */       } else {
/* 478 */         this.hc = (37 * this.hc + (this.value != null ? this.value.hashCode() : 0));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 486 */     return this.hc;
/*     */   }
/*     */ 
/*     */   public void writeBinary(DataOutputStream paramDataOutputStream, StringStore paramStringStore)
/*     */     throws IOException
/*     */   {
/* 505 */     paramDataOutputStream.writeBoolean(this.lookup);
/* 506 */     paramDataOutputStream.writeBoolean(this.converter != null);
/*     */ 
/* 508 */     if (this.converter != null) this.converter.writeBinary(paramDataOutputStream, paramStringStore);
/*     */ 
/* 510 */     if ((this.value instanceof ParsedValue)) {
/* 511 */       paramDataOutputStream.writeByte(1);
/* 512 */       ((ParsedValue)this.value).writeBinary(paramDataOutputStream, paramStringStore);
/*     */     }
/*     */     else
/*     */     {
/*     */       Object localObject1;
/*     */       int j;
/* 514 */       if ((this.value instanceof ParsedValue[])) {
/* 515 */         paramDataOutputStream.writeByte(2);
/* 516 */         localObject1 = (ParsedValue[])this.value;
/* 517 */         paramDataOutputStream.writeInt(localObject1.length);
/* 518 */         for (j = 0; j < localObject1.length; j++) {
/* 519 */           if (localObject1[j] != null) {
/* 520 */             paramDataOutputStream.writeByte(1);
/* 521 */             localObject1[j].writeBinary(paramDataOutputStream, paramStringStore);
/*     */           } else {
/* 523 */             paramDataOutputStream.writeByte(0);
/*     */           }
/*     */         }
/*     */       }
/* 527 */       else if ((this.value instanceof ParsedValue[][])) {
/* 528 */         paramDataOutputStream.writeByte(3);
/* 529 */         localObject1 = (ParsedValue[][])this.value;
/* 530 */         paramDataOutputStream.writeInt(localObject1.length);
/* 531 */         for (j = 0; j < localObject1.length; j++) {
/* 532 */           Object localObject2 = localObject1[j];
/* 533 */           paramDataOutputStream.writeInt(localObject2.length);
/* 534 */           for (int m = 0; m < localObject2.length; m++) {
/* 535 */             if (localObject2[m] != null) {
/* 536 */               paramDataOutputStream.writeByte(1);
/* 537 */               localObject2[m].writeBinary(paramDataOutputStream, paramStringStore);
/*     */             } else {
/* 539 */               paramDataOutputStream.writeByte(0);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 544 */       else if ((this.value instanceof Color)) {
/* 545 */         localObject1 = (Color)this.value;
/* 546 */         paramDataOutputStream.writeByte(5);
/* 547 */         paramDataOutputStream.writeLong(Double.doubleToLongBits(((Color)localObject1).getRed()));
/* 548 */         paramDataOutputStream.writeLong(Double.doubleToLongBits(((Color)localObject1).getGreen()));
/* 549 */         paramDataOutputStream.writeLong(Double.doubleToLongBits(((Color)localObject1).getBlue()));
/* 550 */         paramDataOutputStream.writeLong(Double.doubleToLongBits(((Color)localObject1).getOpacity()));
/*     */       }
/* 552 */       else if ((this.value instanceof Enum)) {
/* 553 */         localObject1 = (Enum)this.value;
/* 554 */         j = paramStringStore.addString(((Enum)localObject1).name());
/* 555 */         int k = paramStringStore.addString(((Object)localObject1).getClass().getName());
/* 556 */         paramDataOutputStream.writeByte(6);
/* 557 */         paramDataOutputStream.writeShort(j);
/* 558 */         paramDataOutputStream.writeShort(k);
/*     */       }
/* 560 */       else if ((this.value instanceof Boolean)) {
/* 561 */         localObject1 = (Boolean)this.value;
/* 562 */         paramDataOutputStream.writeByte(7);
/* 563 */         paramDataOutputStream.writeBoolean(((Boolean)localObject1).booleanValue());
/*     */       }
/* 565 */       else if ((this.value instanceof Size)) {
/* 566 */         localObject1 = (Size)this.value;
/* 567 */         paramDataOutputStream.writeByte(9);
/*     */ 
/* 569 */         double d = ((Size)localObject1).getValue();
/* 570 */         long l = Double.doubleToLongBits(d);
/* 571 */         paramDataOutputStream.writeLong(l);
/*     */ 
/* 573 */         int n = paramStringStore.addString(((Size)localObject1).getUnits().name());
/* 574 */         paramDataOutputStream.writeShort(n);
/*     */       }
/*     */       else
/*     */       {
/*     */         int i;
/* 576 */         if ((this.value instanceof String)) {
/* 577 */           paramDataOutputStream.writeByte(4);
/* 578 */           i = paramStringStore.addString((String)this.value);
/* 579 */           paramDataOutputStream.writeShort(i);
/*     */         }
/* 581 */         else if ((this.value instanceof URL)) {
/* 582 */           paramDataOutputStream.writeByte(8);
/* 583 */           i = paramStringStore.addString(((URL)this.value).toString());
/* 584 */           paramDataOutputStream.writeShort(i);
/*     */         }
/* 586 */         else if (this.value == null) {
/* 587 */           paramDataOutputStream.writeByte(0);
/*     */         }
/*     */         else {
/* 590 */           throw new InternalError(new StringBuilder().append("cannot writeBinary ").append(this).toString());
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static ParsedValue readBinary(DataInputStream paramDataInputStream, String[] paramArrayOfString) throws IOException {
/* 597 */     boolean bool1 = paramDataInputStream.readBoolean();
/* 598 */     boolean bool2 = paramDataInputStream.readBoolean();
/*     */ 
/* 600 */     StyleConverter localStyleConverter = bool2 ? StyleConverter.readBinary(paramDataInputStream, paramArrayOfString) : null;
/*     */ 
/* 602 */     int i = paramDataInputStream.readByte();
/*     */ 
/* 604 */     if (i == 1) {
/* 605 */       ParsedValue localParsedValue1 = readBinary(paramDataInputStream, paramArrayOfString);
/* 606 */       return new ParsedValue(localParsedValue1, localStyleConverter, bool1);
/*     */     }
/*     */     int j;
/*     */     Object localObject1;
/*     */     int n;
/*     */     int i1;
/* 608 */     if (i == 2) {
/* 609 */       j = paramDataInputStream.readInt();
/* 610 */       localObject1 = new ParsedValue[j];
/* 611 */       for (n = 0; n < j; n++) {
/* 612 */         i1 = paramDataInputStream.readByte();
/* 613 */         if (i1 == 1)
/* 614 */           localObject1[n] = readBinary(paramDataInputStream, paramArrayOfString);
/*     */         else {
/* 616 */           localObject1[n] = null;
/*     */         }
/*     */       }
/* 619 */       return new ParsedValue(localObject1, localStyleConverter, bool1);
/*     */     }
/* 621 */     if (i == 3) {
/* 622 */       j = paramDataInputStream.readInt();
/* 623 */       localObject1 = new ParsedValue[j][0];
/* 624 */       for (n = 0; n < j; n++) {
/* 625 */         i1 = paramDataInputStream.readInt();
/* 626 */         localObject1[n] = new ParsedValue[i1];
/* 627 */         for (int i2 = 0; i2 < i1; i2++) {
/* 628 */           int i3 = paramDataInputStream.readByte();
/* 629 */           if (i3 == 1)
/* 630 */             localObject1[n][i2] = readBinary(paramDataInputStream, paramArrayOfString);
/*     */           else {
/* 632 */             localObject1[n][i2] = null;
/*     */           }
/*     */         }
/*     */       }
/* 636 */       return new ParsedValue(localObject1, localStyleConverter, bool1);
/*     */     }
/* 638 */     if (i == 5) {
/* 639 */       double d1 = Double.longBitsToDouble(paramDataInputStream.readLong());
/* 640 */       double d3 = Double.longBitsToDouble(paramDataInputStream.readLong());
/* 641 */       double d4 = Double.longBitsToDouble(paramDataInputStream.readLong());
/* 642 */       double d5 = Double.longBitsToDouble(paramDataInputStream.readLong());
/* 643 */       return new ParsedValue(Color.color(d1, d3, d4, d5), localStyleConverter, bool1);
/*     */     }
/*     */     Object localObject2;
/*     */     String str2;
/* 645 */     if (i == 6) {
/* 646 */       int k = paramDataInputStream.readShort();
/* 647 */       int m = paramDataInputStream.readShort();
/* 648 */       localObject2 = paramArrayOfString[k];
/* 649 */       str2 = paramArrayOfString[m];
/* 650 */       ParsedValue localParsedValue2 = null;
/*     */       try {
/* 652 */         Class localClass = Class.forName(str2);
/* 653 */         localParsedValue2 = new ParsedValue(Enum.valueOf(localClass, (String)localObject2), localStyleConverter, bool1);
/*     */       } catch (ClassNotFoundException localClassNotFoundException) {
/* 655 */         System.err.println(localClassNotFoundException.toString());
/*     */       } catch (IllegalArgumentException localIllegalArgumentException2) {
/* 657 */         System.err.println(localIllegalArgumentException2.toString());
/*     */       } catch (NullPointerException localNullPointerException2) {
/* 659 */         System.err.println(localNullPointerException2.toString());
/*     */       }
/* 661 */       return localParsedValue2;
/*     */     }
/* 663 */     if (i == 7) {
/* 664 */       Boolean localBoolean = Boolean.valueOf(paramDataInputStream.readBoolean());
/* 665 */       return new ParsedValue(localBoolean, localStyleConverter, bool1);
/*     */     }
/* 667 */     if (i == 9) {
/* 668 */       double d2 = Double.longBitsToDouble(paramDataInputStream.readLong());
/* 669 */       localObject2 = SizeUnits.PX;
/* 670 */       str2 = paramArrayOfString[paramDataInputStream.readShort()];
/*     */       try {
/* 672 */         localObject2 = (SizeUnits)Enum.valueOf(SizeUnits.class, str2);
/*     */       } catch (IllegalArgumentException localIllegalArgumentException1) {
/* 674 */         System.err.println(localIllegalArgumentException1.toString());
/*     */       } catch (NullPointerException localNullPointerException1) {
/* 676 */         System.err.println(localNullPointerException1.toString());
/*     */       }
/* 678 */       return new ParsedValue(new Size(d2, (SizeUnits)localObject2), localStyleConverter, bool1);
/*     */     }
/*     */     String str1;
/* 680 */     if (i == 4) {
/* 681 */       str1 = paramArrayOfString[paramDataInputStream.readShort()];
/* 682 */       return new ParsedValue(str1, localStyleConverter, bool1);
/*     */     }
/* 684 */     if (i == 8) {
/* 685 */       str1 = paramArrayOfString[paramDataInputStream.readShort()];
/*     */       try {
/* 687 */         URL localURL = new URL(str1);
/* 688 */         return new ParsedValue(localURL, localStyleConverter, bool1);
/*     */       } catch (MalformedURLException localMalformedURLException) {
/* 690 */         throw new InternalError(new StringBuilder().append("Excpeption in Value.readBinary: ").append(localMalformedURLException).toString());
/*     */       }
/*     */     }
/* 693 */     if (i == 0) {
/* 694 */       return new ParsedValue(null, localStyleConverter, bool1);
/*     */     }
/*     */ 
/* 697 */     throw new InternalError(new StringBuilder().append("unknown type: ").append(i).toString());
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.css.ParsedValue
 * JD-Core Version:    0.6.2
 */