/*     */ package javafx.scene.input;
/*     */ 
/*     */ import com.sun.javafx.tk.Toolkit;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public abstract class KeyCombination
/*     */ {
/*  51 */   public static final Modifier SHIFT_DOWN = new Modifier(KeyCode.SHIFT, ModifierValue.DOWN, null);
/*     */ 
/*  57 */   public static final Modifier SHIFT_ANY = new Modifier(KeyCode.SHIFT, ModifierValue.ANY, null);
/*     */ 
/*  60 */   public static final Modifier CONTROL_DOWN = new Modifier(KeyCode.CONTROL, ModifierValue.DOWN, null);
/*     */ 
/*  66 */   public static final Modifier CONTROL_ANY = new Modifier(KeyCode.CONTROL, ModifierValue.ANY, null);
/*     */ 
/*  69 */   public static final Modifier ALT_DOWN = new Modifier(KeyCode.ALT, ModifierValue.DOWN, null);
/*     */ 
/*  75 */   public static final Modifier ALT_ANY = new Modifier(KeyCode.ALT, ModifierValue.ANY, null);
/*     */ 
/*  78 */   public static final Modifier META_DOWN = new Modifier(KeyCode.META, ModifierValue.DOWN, null);
/*     */ 
/*  84 */   public static final Modifier META_ANY = new Modifier(KeyCode.META, ModifierValue.ANY, null);
/*     */ 
/*  87 */   public static final Modifier SHORTCUT_DOWN = new Modifier(KeyCode.SHORTCUT, ModifierValue.DOWN, null);
/*     */ 
/*  93 */   public static final Modifier SHORTCUT_ANY = new Modifier(KeyCode.SHORTCUT, ModifierValue.ANY, null);
/*     */ 
/*  96 */   private static final Modifier[] POSSIBLE_MODIFIERS = { SHIFT_DOWN, SHIFT_ANY, CONTROL_DOWN, CONTROL_ANY, ALT_DOWN, ALT_ANY, META_DOWN, META_ANY, SHORTCUT_DOWN, SHORTCUT_ANY };
/*     */   private final ModifierValue shift;
/*     */   private final ModifierValue control;
/*     */   private final ModifierValue alt;
/*     */   private final ModifierValue meta;
/*     */   private final ModifierValue shortcut;
/*     */ 
/*     */   public final ModifierValue getShift()
/*     */   {
/* 111 */     return this.shift;
/*     */   }
/*     */ 
/*     */   public final ModifierValue getControl()
/*     */   {
/* 121 */     return this.control;
/*     */   }
/*     */ 
/*     */   public final ModifierValue getAlt()
/*     */   {
/* 131 */     return this.alt;
/*     */   }
/*     */ 
/*     */   public final ModifierValue getMeta()
/*     */   {
/* 141 */     return this.meta;
/*     */   }
/*     */ 
/*     */   public final ModifierValue getShortcut()
/*     */   {
/* 152 */     return this.shortcut;
/*     */   }
/*     */ 
/*     */   protected KeyCombination(ModifierValue paramModifierValue1, ModifierValue paramModifierValue2, ModifierValue paramModifierValue3, ModifierValue paramModifierValue4, ModifierValue paramModifierValue5)
/*     */   {
/* 171 */     if ((paramModifierValue1 == null) || (paramModifierValue2 == null) || (paramModifierValue3 == null) || (paramModifierValue4 == null) || (paramModifierValue5 == null))
/*     */     {
/* 176 */       throw new NullPointerException("Modifier value must not be null!");
/*     */     }
/*     */ 
/* 179 */     this.shift = paramModifierValue1;
/* 180 */     this.control = paramModifierValue2;
/* 181 */     this.alt = paramModifierValue3;
/* 182 */     this.meta = paramModifierValue4;
/* 183 */     this.shortcut = paramModifierValue5;
/*     */   }
/*     */ 
/*     */   protected KeyCombination(Modifier[] paramArrayOfModifier)
/*     */   {
/* 197 */     this(getModifierValue(paramArrayOfModifier, KeyCode.SHIFT), getModifierValue(paramArrayOfModifier, KeyCode.CONTROL), getModifierValue(paramArrayOfModifier, KeyCode.ALT), getModifierValue(paramArrayOfModifier, KeyCode.META), getModifierValue(paramArrayOfModifier, KeyCode.SHORTCUT));
/*     */   }
/*     */ 
/*     */   public boolean match(KeyEvent paramKeyEvent)
/*     */   {
/* 217 */     KeyCode localKeyCode = Toolkit.getToolkit().getPlatformShortcutKey();
/*     */ 
/* 219 */     return (test(KeyCode.SHIFT, this.shift, localKeyCode, this.shortcut, paramKeyEvent.isShiftDown())) && (test(KeyCode.CONTROL, this.control, localKeyCode, this.shortcut, paramKeyEvent.isControlDown())) && (test(KeyCode.ALT, this.alt, localKeyCode, this.shortcut, paramKeyEvent.isAltDown())) && (test(KeyCode.META, this.meta, localKeyCode, this.shortcut, paramKeyEvent.isMetaDown()));
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 258 */     StringBuilder localStringBuilder = new StringBuilder();
/* 259 */     addModifiersIntoString(localStringBuilder);
/*     */ 
/* 261 */     return localStringBuilder.toString();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 272 */     if (!(paramObject instanceof KeyCombination)) {
/* 273 */       return false;
/*     */     }
/*     */ 
/* 276 */     KeyCombination localKeyCombination = (KeyCombination)paramObject;
/* 277 */     return (this.shift == localKeyCombination.shift) && (this.control == localKeyCombination.control) && (this.alt == localKeyCombination.alt) && (this.meta == localKeyCombination.meta) && (this.shortcut == localKeyCombination.shortcut);
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 291 */     int i = 7;
/*     */ 
/* 293 */     i = 23 * i + this.shift.hashCode();
/* 294 */     i = 23 * i + this.control.hashCode();
/* 295 */     i = 23 * i + this.alt.hashCode();
/* 296 */     i = 23 * i + this.meta.hashCode();
/* 297 */     i = 23 * i + this.shortcut.hashCode();
/*     */ 
/* 299 */     return i;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 310 */     return getName();
/*     */   }
/*     */ 
/*     */   public static KeyCombination valueOf(String paramString)
/*     */   {
/* 328 */     ArrayList localArrayList = new ArrayList(4);
/*     */ 
/* 330 */     String[] arrayOfString = splitName(paramString);
/*     */ 
/* 332 */     KeyCode localKeyCode = null;
/* 333 */     Object localObject1 = null;
/* 334 */     for (String str1 : arrayOfString)
/*     */     {
/* 336 */       if ((str1.length() > 2) && (str1.charAt(0) == '\'') && (str1.charAt(str1.length() - 1) == '\''))
/*     */       {
/* 339 */         if ((localKeyCode != null) || (localObject1 != null)) {
/* 340 */           throw new IllegalArgumentException(new StringBuilder().append("Cannot parse key binding ").append(paramString).toString());
/*     */         }
/*     */ 
/* 344 */         localObject1 = str1.substring(1, str1.length() - 1).replace("\\'", "'");
/*     */       }
/*     */       else
/*     */       {
/* 349 */         String str2 = normalizeToken(str1);
/*     */ 
/* 351 */         Modifier localModifier = getModifier(str2);
/* 352 */         if (localModifier != null) {
/* 353 */           localArrayList.add(localModifier);
/*     */         }
/*     */         else
/*     */         {
/* 357 */           if ((localKeyCode != null) || (localObject1 != null)) {
/* 358 */             throw new IllegalArgumentException(new StringBuilder().append("Cannot parse key binding ").append(paramString).toString());
/*     */           }
/*     */ 
/* 362 */           localKeyCode = KeyCode.getKeyCode(str2);
/* 363 */           if (localKeyCode == null)
/* 364 */             localObject1 = str1;
/*     */         }
/*     */       }
/*     */     }
/* 368 */     if ((localKeyCode == null) && (localObject1 == null)) {
/* 369 */       throw new IllegalArgumentException(new StringBuilder().append("Cannot parse key binding ").append(paramString).toString());
/*     */     }
/*     */ 
/* 373 */     ??? = (Modifier[])localArrayList.toArray(new Modifier[localArrayList.size()]);
/*     */ 
/* 375 */     return localKeyCode != null ? new KeyCodeCombination(localKeyCode, (Modifier[])???) : new KeyCharacterCombination((String)localObject1, (Modifier[])???);
/*     */   }
/*     */ 
/*     */   public static KeyCombination keyCombination(String paramString)
/*     */   {
/* 390 */     return valueOf(paramString);
/*     */   }
/*     */ 
/*     */   private void addModifiersIntoString(StringBuilder paramStringBuilder)
/*     */   {
/* 449 */     addModifierIntoString(paramStringBuilder, KeyCode.SHIFT, this.shift);
/* 450 */     addModifierIntoString(paramStringBuilder, KeyCode.CONTROL, this.control);
/* 451 */     addModifierIntoString(paramStringBuilder, KeyCode.ALT, this.alt);
/* 452 */     addModifierIntoString(paramStringBuilder, KeyCode.META, this.meta);
/* 453 */     addModifierIntoString(paramStringBuilder, KeyCode.SHORTCUT, this.shortcut);
/*     */   }
/*     */ 
/*     */   private static void addModifierIntoString(StringBuilder paramStringBuilder, KeyCode paramKeyCode, ModifierValue paramModifierValue)
/*     */   {
/* 461 */     if (paramModifierValue == ModifierValue.UP) {
/* 462 */       return;
/*     */     }
/*     */ 
/* 465 */     if (paramStringBuilder.length() > 0) {
/* 466 */       paramStringBuilder.append("+");
/*     */     }
/*     */ 
/* 469 */     if (paramModifierValue == ModifierValue.ANY) {
/* 470 */       paramStringBuilder.append("Ignore ");
/*     */     }
/*     */ 
/* 473 */     paramStringBuilder.append(paramKeyCode.getName());
/*     */   }
/*     */ 
/*     */   private static boolean test(KeyCode paramKeyCode1, ModifierValue paramModifierValue1, KeyCode paramKeyCode2, ModifierValue paramModifierValue2, boolean paramBoolean)
/*     */   {
/* 481 */     ModifierValue localModifierValue = paramKeyCode1 == paramKeyCode2 ? resolveModifierValue(paramModifierValue1, paramModifierValue2) : paramModifierValue1;
/*     */ 
/* 487 */     return test(localModifierValue, paramBoolean);
/*     */   }
/*     */ 
/*     */   private static boolean test(ModifierValue paramModifierValue, boolean paramBoolean)
/*     */   {
/* 493 */     switch (1.$SwitchMap$javafx$scene$input$KeyCombination$ModifierValue[paramModifierValue.ordinal()]) {
/*     */     case 1:
/* 495 */       return paramBoolean;
/*     */     case 2:
/* 498 */       return !paramBoolean;
/*     */     case 3:
/*     */     }
/*     */ 
/* 502 */     return true;
/*     */   }
/*     */ 
/*     */   private static ModifierValue resolveModifierValue(ModifierValue paramModifierValue1, ModifierValue paramModifierValue2)
/*     */   {
/* 509 */     if ((paramModifierValue1 == ModifierValue.DOWN) || (paramModifierValue2 == ModifierValue.DOWN))
/*     */     {
/* 511 */       return ModifierValue.DOWN;
/*     */     }
/*     */ 
/* 514 */     if ((paramModifierValue1 == ModifierValue.ANY) || (paramModifierValue2 == ModifierValue.ANY))
/*     */     {
/* 516 */       return ModifierValue.ANY;
/*     */     }
/*     */ 
/* 519 */     return ModifierValue.UP;
/*     */   }
/*     */ 
/*     */   static Modifier getModifier(String paramString) {
/* 523 */     for (Modifier localModifier : POSSIBLE_MODIFIERS) {
/* 524 */       if (localModifier.toString().equals(paramString)) {
/* 525 */         return localModifier;
/*     */       }
/*     */     }
/*     */ 
/* 529 */     return null;
/*     */   }
/*     */ 
/*     */   private static ModifierValue getModifierValue(Modifier[] paramArrayOfModifier, KeyCode paramKeyCode)
/*     */   {
/* 535 */     ModifierValue localModifierValue = ModifierValue.UP;
/* 536 */     for (Modifier localModifier : paramArrayOfModifier) {
/* 537 */       if (localModifier == null) {
/* 538 */         throw new NullPointerException("Modifier must not be null!");
/*     */       }
/*     */ 
/* 541 */       if (localModifier.getKey() == paramKeyCode) {
/* 542 */         if (localModifierValue != ModifierValue.UP) {
/* 543 */           throw new IllegalArgumentException(localModifier.getValue() != localModifierValue ? "Conflicting modifiers specified!" : "Duplicate modifiers specified!");
/*     */         }
/*     */ 
/* 549 */         localModifierValue = localModifier.getValue();
/*     */       }
/*     */     }
/*     */ 
/* 553 */     return localModifierValue;
/*     */   }
/*     */ 
/*     */   private static String normalizeToken(String paramString) {
/* 557 */     String[] arrayOfString1 = paramString.split("\\s+");
/* 558 */     StringBuilder localStringBuilder = new StringBuilder();
/*     */ 
/* 560 */     for (String str : arrayOfString1) {
/* 561 */       if (localStringBuilder.length() > 0) {
/* 562 */         localStringBuilder.append(' ');
/*     */       }
/*     */ 
/* 565 */       localStringBuilder.append(str.substring(0, 1).toUpperCase());
/* 566 */       localStringBuilder.append(str.substring(1).toLowerCase());
/*     */     }
/*     */ 
/* 569 */     return localStringBuilder.toString();
/*     */   }
/*     */ 
/*     */   private static String[] splitName(String paramString) {
/* 573 */     ArrayList localArrayList = new ArrayList();
/* 574 */     char[] arrayOfChar = paramString.trim().toCharArray();
/*     */ 
/* 581 */     int i = 0;
/* 582 */     int j = 0;
/* 583 */     int k = -1;
/*     */ 
/* 585 */     for (int m = 0; m < arrayOfChar.length; m++) {
/* 586 */       int n = arrayOfChar[m];
/* 587 */       switch (i) {
/*     */       case 0:
/* 589 */         switch (n) {
/*     */         case 9:
/*     */         case 10:
/*     */         case 11:
/*     */         case 12:
/*     */         case 13:
/*     */         case 32:
/* 596 */           k = m;
/* 597 */           i = 1;
/* 598 */           break;
/*     */         case 43:
/* 600 */           k = m;
/* 601 */           i = 2;
/* 602 */           break;
/*     */         case 39:
/* 604 */           if ((m == 0) || (arrayOfChar[(m - 1)] != '\\')) {
/* 605 */             i = 3;
/*     */           }
/*     */           break;
/*     */         }
/* 609 */         break;
/*     */       case 1:
/* 613 */         switch (n) {
/*     */         case 9:
/*     */         case 10:
/*     */         case 11:
/*     */         case 12:
/*     */         case 13:
/*     */         case 32:
/* 620 */           break;
/*     */         case 43:
/* 622 */           i = 2;
/* 623 */           break;
/*     */         case 39:
/* 625 */           i = 3;
/* 626 */           k = -1;
/* 627 */           break;
/*     */         default:
/* 629 */           i = 0;
/* 630 */           k = -1;
/* 631 */         }break;
/*     */       case 2:
/* 635 */         switch (n) {
/*     */         case 9:
/*     */         case 10:
/*     */         case 11:
/*     */         case 12:
/*     */         case 13:
/*     */         case 32:
/* 642 */           break;
/*     */         case 43:
/* 644 */           throw new IllegalArgumentException(new StringBuilder().append("Cannot parse key binding ").append(paramString).toString());
/*     */         default:
/* 647 */           if (k <= j) {
/* 648 */             throw new IllegalArgumentException(new StringBuilder().append("Cannot parse key binding ").append(paramString).toString());
/*     */           }
/*     */ 
/* 651 */           localArrayList.add(new String(arrayOfChar, j, k - j));
/*     */ 
/* 653 */           j = m;
/* 654 */           k = -1;
/* 655 */           i = n == 39 ? 3 : 0;
/* 656 */         }break;
/*     */       case 3:
/* 660 */         if ((n == 39) && (arrayOfChar[(m - 1)] != '\\')) {
/* 661 */           i = 0;
/*     */         }
/*     */         break;
/*     */       }
/*     */     }
/*     */ 
/* 667 */     switch (i) {
/*     */     case 0:
/*     */     case 1:
/* 670 */       localArrayList.add(new String(arrayOfChar, j, arrayOfChar.length - j));
/*     */ 
/* 672 */       break;
/*     */     case 2:
/*     */     case 3:
/* 675 */       throw new IllegalArgumentException(new StringBuilder().append("Cannot parse key binding ").append(paramString).toString());
/*     */     }
/*     */ 
/* 679 */     return (String[])localArrayList.toArray(new String[localArrayList.size()]);
/*     */   }
/*     */ 
/*     */   public static enum ModifierValue
/*     */   {
/* 438 */     DOWN, 
/*     */ 
/* 440 */     UP, 
/*     */ 
/* 445 */     ANY;
/*     */   }
/*     */ 
/*     */   public static final class Modifier
/*     */   {
/*     */     private final KeyCode key;
/*     */     private final KeyCombination.ModifierValue value;
/*     */ 
/*     */     private Modifier(KeyCode paramKeyCode, KeyCombination.ModifierValue paramModifierValue)
/*     */     {
/* 402 */       this.key = paramKeyCode;
/* 403 */       this.value = paramModifierValue;
/*     */     }
/*     */ 
/*     */     public KeyCode getKey()
/*     */     {
/* 412 */       return this.key;
/*     */     }
/*     */ 
/*     */     public KeyCombination.ModifierValue getValue()
/*     */     {
/* 421 */       return this.value;
/*     */     }
/*     */ 
/*     */     public String toString()
/*     */     {
/* 430 */       return new StringBuilder().append(this.value == KeyCombination.ModifierValue.ANY ? "Ignore " : "").append(this.key.getName()).toString();
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.input.KeyCombination
 * JD-Core Version:    0.6.2
 */