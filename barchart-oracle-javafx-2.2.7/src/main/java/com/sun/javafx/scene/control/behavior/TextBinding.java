/*     */ package com.sun.javafx.scene.control.behavior;
/*     */ 
/*     */ import java.util.StringTokenizer;
/*     */ import javafx.scene.input.KeyCode;
/*     */ 
/*     */ public class TextBinding
/*     */ {
/*  89 */   private String MNEMONIC_SYMBOL = "_";
/*     */ 
/*  94 */   private String text = null;
/*     */ 
/* 108 */   private KeyCode mnemonic = KeyCode.UNDEFINED;
/*     */ 
/* 126 */   private int mnemonicIndex = -1;
/*     */ 
/* 144 */   private String extendedMnemonicText = null;
/*     */ 
/* 160 */   private KeyBinding accelerator = null;
/*     */ 
/* 176 */   private String acceleratorText = null;
/*     */ 
/* 193 */   private boolean ctrl = false;
/*     */ 
/* 201 */   private boolean shift = false;
/*     */ 
/* 209 */   private boolean alt = false;
/*     */ 
/* 217 */   private boolean meta = false;
/*     */ 
/*     */   public String getText()
/*     */   {
/* 102 */     return this.text;
/*     */   }
/*     */ 
/*     */   public KeyCode getMnemonic()
/*     */   {
/* 118 */     return this.mnemonic;
/*     */   }
/*     */ 
/*     */   public int getMnemonicIndex()
/*     */   {
/* 137 */     return this.mnemonicIndex;
/*     */   }
/*     */ 
/*     */   public String getExtendedMnemonicText()
/*     */   {
/* 153 */     return this.extendedMnemonicText;
/*     */   }
/*     */ 
/*     */   public KeyBinding getAccelerator()
/*     */   {
/* 170 */     return this.accelerator;
/*     */   }
/*     */ 
/*     */   public String getAcceleratorText()
/*     */   {
/* 186 */     return this.acceleratorText;
/*     */   }
/*     */ 
/*     */   public boolean getCtrl()
/*     */   {
/* 195 */     return this.ctrl;
/*     */   }
/*     */ 
/*     */   public boolean getShift()
/*     */   {
/* 203 */     return this.shift;
/*     */   }
/*     */ 
/*     */   public boolean getAlt()
/*     */   {
/* 211 */     return this.shift;
/*     */   }
/*     */ 
/*     */   public boolean getMeta()
/*     */   {
/* 219 */     return this.shift;
/*     */   }
/*     */ 
/*     */   public TextBinding(String paramString)
/*     */   {
/* 235 */     parseAndSplit(paramString);
/*     */   }
/*     */ 
/*     */   private void parseAndSplit(String paramString)
/*     */   {
/* 242 */     if ((paramString == null) || (paramString.length() == 0)) {
/* 243 */       this.text = paramString;
/* 244 */       return;
/*     */     }
/*     */ 
/* 250 */     StringBuffer localStringBuffer = new StringBuffer(paramString);
/*     */ 
/* 254 */     int i = localStringBuffer.indexOf("@");
/* 255 */     while ((i >= 0) && (i < localStringBuffer.length() - 1)) {
/* 256 */       if (localStringBuffer.charAt(i + 1) == '@') {
/* 257 */         localStringBuffer.delete(i, i + 1);
/*     */       }
/*     */       else
/*     */       {
/* 262 */         this.acceleratorText = localStringBuffer.substring(i + 1);
/* 263 */         this.accelerator = parseAcceleratorText(this.acceleratorText);
/* 264 */         if (this.accelerator != null) {
/* 265 */           localStringBuffer.delete(i, localStringBuffer.length());
/* 266 */           break;
/*     */         }
/* 268 */         this.acceleratorText = null;
/*     */       }
/*     */ 
/* 271 */       i = localStringBuffer.indexOf("@", i + 1);
/*     */     }
/*     */ 
/* 276 */     i = localStringBuffer.indexOf(this.MNEMONIC_SYMBOL);
/* 277 */     while ((i >= 0) && (i < localStringBuffer.length() - 1))
/*     */     {
/* 279 */       if (this.MNEMONIC_SYMBOL.equals(Character.valueOf(localStringBuffer.charAt(i + 1)))) {
/* 280 */         localStringBuffer.delete(i, i + 1); } else {
/* 281 */         if ((localStringBuffer.charAt(i + 1) != '(') || (i == localStringBuffer.length() - 2))
/*     */         {
/* 283 */           String str1 = localStringBuffer.substring(i + 1, i + 2);
/* 284 */           this.mnemonic = KeyCode.getKeyCode(str1.toUpperCase());
/* 285 */           if (this.mnemonic != null) {
/* 286 */             this.mnemonicIndex = i;
/*     */           }
/* 288 */           localStringBuffer.delete(i, i + 1);
/* 289 */           break;
/*     */         }
/* 291 */         int j = localStringBuffer.indexOf(")", i + 3);
/*     */         String str2;
/* 292 */         if (j == -1) {
/* 293 */           str2 = localStringBuffer.substring(i + 1, i + 2);
/* 294 */           this.mnemonic = KeyCode.getKeyCode(str2.toUpperCase());
/* 295 */           if (this.mnemonic != null) {
/* 296 */             this.mnemonicIndex = i;
/*     */           }
/* 298 */           localStringBuffer.delete(i, i + 1);
/* 299 */           break;
/* 300 */         }if (j == i + 3) {
/* 301 */           str2 = localStringBuffer.substring(i + 2, i + 3);
/* 302 */           this.mnemonic = KeyCode.getKeyCode(str2.toUpperCase());
/* 303 */           this.extendedMnemonicText = localStringBuffer.substring(i + 1, i + 4);
/* 304 */           localStringBuffer.delete(i, j + 3);
/* 305 */           break;
/*     */         }
/*     */       }
/* 308 */       i = localStringBuffer.indexOf(this.MNEMONIC_SYMBOL, i + 1);
/*     */     }
/* 310 */     this.text = localStringBuffer.toString();
/*     */   }
/*     */ 
/*     */   private KeyBinding parseAcceleratorText(String paramString)
/*     */   {
/* 321 */     KeyBinding localKeyBinding = null;
/* 322 */     Object localObject1 = null;
/* 323 */     int i = 0;
/* 324 */     int j = 0;
/* 325 */     int k = 0;
/* 326 */     int m = 0;
/* 327 */     int n = 0;
/* 328 */     StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "+");
/*     */     Object localObject2;
/* 329 */     while ((n == 0) && (localStringTokenizer.hasMoreTokens())) {
/* 330 */       localObject2 = localStringTokenizer.nextToken();
/* 331 */       if (!localStringTokenizer.hasMoreTokens()) {
/* 332 */         localObject1 = localObject2;
/*     */       } else {
/* 334 */         KeyCode localKeyCode = KeyCode.getKeyCode(((String)localObject2).toUpperCase());
/* 335 */         if (localKeyCode != null) {
/* 336 */           switch (1.$SwitchMap$javafx$scene$input$KeyCode[localKeyCode.ordinal()]) {
/*     */           case 1:
/* 338 */             i = 1;
/* 339 */             break;
/*     */           case 2:
/* 341 */             j = 1;
/* 342 */             break;
/*     */           case 3:
/* 344 */             k = 1;
/* 345 */             break;
/*     */           case 4:
/* 347 */             m = 1;
/* 348 */             break;
/*     */           default:
/* 350 */             localObject1 = null;
/* 351 */             n = 1;
/* 352 */             break;
/*     */           }
/*     */         } else {
/* 355 */           localObject1 = null;
/* 356 */           n = 1;
/*     */         }
/*     */       }
/*     */     }
/* 360 */     if (localObject1 != null) {
/* 361 */       localObject2 = KeyCode.getKeyCode(localObject1.toUpperCase());
/* 362 */       if ((localObject2 != null) && 
/* 363 */         (localObject2 != KeyCode.UNDEFINED)) {
/* 364 */         localKeyBinding = new KeyBinding((KeyCode)localObject2, null);
/* 365 */         if (i != 0) {
/* 366 */           this.ctrl = true;
/* 367 */           localKeyBinding.ctrl();
/*     */         }
/* 369 */         if (j != 0) {
/* 370 */           this.alt = true;
/* 371 */           localKeyBinding.alt();
/*     */         }
/* 373 */         if (k != 0) {
/* 374 */           this.shift = true;
/* 375 */           localKeyBinding.shift();
/*     */         }
/*     */ 
/* 378 */         if (m != 0) {
/* 379 */           this.meta = true;
/* 380 */           localKeyBinding.meta();
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 385 */     return localKeyBinding;
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 389 */     return "TextBinding [text=" + getText() + ",mnemonic=" + getMnemonic() + ", mnemonicIndex=" + getMnemonicIndex() + ", extendedMnemonicText=" + getExtendedMnemonicText() + ", accelerator=" + getAccelerator() + ", acceleratorText=" + getAcceleratorText() + "]";
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.behavior.TextBinding
 * JD-Core Version:    0.6.2
 */