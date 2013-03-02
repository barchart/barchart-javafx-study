/*     */ package com.sun.javafx.tk.quantum;
/*     */ 
/*     */ public class GlassEventUtils
/*     */ {
/*     */   public static String getMouseEventString(int paramInt)
/*     */   {
/*  20 */     switch (paramInt) {
/*     */     case 211:
/*  22 */       return "BUTTON_NONE";
/*     */     case 212:
/*  24 */       return "BUTTON_LEFT";
/*     */     case 213:
/*  26 */       return "BUTTON_RIGHT";
/*     */     case 214:
/*  28 */       return "BUTTON_OTHER";
/*     */     case 221:
/*  30 */       return "DOWN";
/*     */     case 222:
/*  32 */       return "UP";
/*     */     case 223:
/*  34 */       return "DRAG";
/*     */     case 224:
/*  36 */       return "MOVE";
/*     */     case 225:
/*  38 */       return "ENTER";
/*     */     case 226:
/*  40 */       return "EXIT";
/*     */     case 227:
/*  42 */       return "CLICK";
/*     */     case 228:
/*  44 */       return "WHEEL";
/*     */     case 215:
/*     */     case 216:
/*     */     case 217:
/*     */     case 218:
/*     */     case 219:
/*  46 */     case 220: } return "UNKNOWN";
/*     */   }
/*     */ 
/*     */   public static String getViewEventString(int paramInt)
/*     */   {
/*  51 */     switch (paramInt) {
/*     */     case 411:
/*  53 */       return "LOCKED";
/*     */     case 412:
/*  55 */       return "UNLOCKED";
/*     */     case 421:
/*  57 */       return "ADD";
/*     */     case 422:
/*  59 */       return "REMOVE";
/*     */     case 431:
/*  61 */       return "REPAINT";
/*     */     case 432:
/*  63 */       return "RESIZE";
/*     */     case 433:
/*  65 */       return "MOVE";
/*     */     case 441:
/*  67 */       return "FULLSCREEN_ENTER";
/*     */     case 442:
/*  69 */       return "FULLSCREEN_EXIT";
/*     */     case 413:
/*     */     case 414:
/*     */     case 415:
/*     */     case 416:
/*     */     case 417:
/*     */     case 418:
/*     */     case 419:
/*     */     case 420:
/*     */     case 423:
/*     */     case 424:
/*     */     case 425:
/*     */     case 426:
/*     */     case 427:
/*     */     case 428:
/*     */     case 429:
/*     */     case 430:
/*     */     case 434:
/*     */     case 435:
/*     */     case 436:
/*     */     case 437:
/*     */     case 438:
/*     */     case 439:
/*  71 */     case 440: } return "UNKNOWN";
/*     */   }
/*     */ 
/*     */   public static String getWindowEventString(int paramInt)
/*     */   {
/*  76 */     switch (paramInt) {
/*     */     case 511:
/*  78 */       return "RESIZE";
/*     */     case 512:
/*  80 */       return "MOVE";
/*     */     case 521:
/*  82 */       return "CLOSE";
/*     */     case 522:
/*  84 */       return "DESTROY";
/*     */     case 531:
/*  86 */       return "MINIMIZE";
/*     */     case 532:
/*  88 */       return "MAXIMIZE";
/*     */     case 533:
/*  90 */       return "RESTORE";
/*     */     case 541:
/*  92 */       return "FOCUS_LOST";
/*     */     case 542:
/*  94 */       return "FOCUS_GAINED";
/*     */     case 543:
/*  96 */       return "FOCUS_GAINED_FORWARD";
/*     */     case 544:
/*  98 */       return "FOCUS_GAINED_BACKWARD";
/*     */     case 545:
/* 100 */       return "FOCUS_DISABLED";
/*     */     case 546:
/* 102 */       return "FOCUS_UNGRAB";
/*     */     case 513:
/*     */     case 514:
/*     */     case 515:
/*     */     case 516:
/*     */     case 517:
/*     */     case 518:
/*     */     case 519:
/*     */     case 520:
/*     */     case 523:
/*     */     case 524:
/*     */     case 525:
/*     */     case 526:
/*     */     case 527:
/*     */     case 528:
/*     */     case 529:
/*     */     case 530:
/*     */     case 534:
/*     */     case 535:
/*     */     case 536:
/*     */     case 537:
/*     */     case 538:
/*     */     case 539:
/* 104 */     case 540: } return "UNKNOWN";
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.quantum.GlassEventUtils
 * JD-Core Version:    0.6.2
 */