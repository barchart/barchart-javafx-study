/*     */ package com.sun.javafx.css.parser;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Reader;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ final class CSSLexer
/*     */ {
/*     */   static final int STRING = 10;
/*     */   static final int IDENT = 11;
/*     */   static final int FUNCTION = 12;
/*     */   static final int NUMBER = 13;
/*     */   static final int CM = 14;
/*     */   static final int EMS = 15;
/*     */   static final int EXS = 16;
/*     */   static final int IN = 17;
/*     */   static final int MM = 18;
/*     */   static final int PC = 19;
/*     */   static final int PT = 20;
/*     */   static final int PX = 21;
/*     */   static final int PERCENTAGE = 22;
/*     */   static final int DEG = 23;
/*     */   static final int GRAD = 24;
/*     */   static final int RAD = 25;
/*     */   static final int TURN = 26;
/*     */   static final int GREATER = 27;
/*     */   static final int LBRACE = 28;
/*     */   static final int RBRACE = 29;
/*     */   static final int SEMI = 30;
/*     */   static final int COLON = 31;
/*     */   static final int SOLIDUS = 32;
/*     */   static final int STAR = 33;
/*     */   static final int LPAREN = 34;
/*     */   static final int RPAREN = 35;
/*     */   static final int COMMA = 36;
/*     */   static final int HASH = 37;
/*     */   static final int DOT = 38;
/*     */   static final int IMPORTANT_SYM = 39;
/*     */   static final int WS = 40;
/*     */   static final int NL = 41;
/*  77 */   private final Recognizer A = new SimpleRecognizer(97, new int[] { 65 });
/*  78 */   private final Recognizer B = new SimpleRecognizer(98, new int[] { 66 });
/*  79 */   private final Recognizer C = new SimpleRecognizer(99, new int[] { 67 });
/*  80 */   private final Recognizer D = new SimpleRecognizer(100, new int[] { 68 });
/*  81 */   private final Recognizer E = new SimpleRecognizer(101, new int[] { 69 });
/*  82 */   private final Recognizer F = new SimpleRecognizer(102, new int[] { 70 });
/*  83 */   private final Recognizer G = new SimpleRecognizer(103, new int[] { 71 });
/*  84 */   private final Recognizer H = new SimpleRecognizer(104, new int[] { 72 });
/*  85 */   private final Recognizer I = new SimpleRecognizer(105, new int[] { 73 });
/*  86 */   private final Recognizer J = new SimpleRecognizer(106, new int[] { 74 });
/*  87 */   private final Recognizer K = new SimpleRecognizer(107, new int[] { 75 });
/*  88 */   private final Recognizer L = new SimpleRecognizer(108, new int[] { 76 });
/*  89 */   private final Recognizer M = new SimpleRecognizer(109, new int[] { 77 });
/*  90 */   private final Recognizer N = new SimpleRecognizer(110, new int[] { 78 });
/*  91 */   private final Recognizer O = new SimpleRecognizer(111, new int[] { 79 });
/*  92 */   private final Recognizer P = new SimpleRecognizer(112, new int[] { 80 });
/*  93 */   private final Recognizer Q = new SimpleRecognizer(113, new int[] { 81 });
/*  94 */   private final Recognizer R = new SimpleRecognizer(114, new int[] { 82 });
/*  95 */   private final Recognizer S = new SimpleRecognizer(115, new int[] { 83 });
/*  96 */   private final Recognizer T = new SimpleRecognizer(116, new int[] { 84 });
/*  97 */   private final Recognizer U = new SimpleRecognizer(117, new int[] { 85 });
/*  98 */   private final Recognizer V = new SimpleRecognizer(118, new int[] { 86 });
/*  99 */   private final Recognizer W = new SimpleRecognizer(119, new int[] { 87 });
/* 100 */   private final Recognizer X = new SimpleRecognizer(120, new int[] { 88 });
/* 101 */   private final Recognizer Y = new SimpleRecognizer(121, new int[] { 89 });
/* 102 */   private final Recognizer Z = new SimpleRecognizer(122, new int[] { 90 });
/* 103 */   private final Recognizer ALPHA = new Recognizer() {
/*     */     public boolean recognize(int paramAnonymousInt) {
/* 105 */       return ((97 <= paramAnonymousInt) && (paramAnonymousInt <= 122)) || ((65 <= paramAnonymousInt) && (paramAnonymousInt <= 90));
/*     */     }
/* 103 */   };
/*     */ 
/* 110 */   private final Recognizer NON_ASCII = new Recognizer() {
/*     */     public boolean recognize(int paramAnonymousInt) {
/* 112 */       return (128 <= paramAnonymousInt) && (paramAnonymousInt <= 65535);
/*     */     }
/* 110 */   };
/*     */ 
/* 116 */   private final Recognizer DOT_CHAR = new SimpleRecognizer(46, new int[0]);
/* 117 */   private final Recognizer GREATER_CHAR = new SimpleRecognizer(62, new int[0]);
/* 118 */   private final Recognizer LBRACE_CHAR = new SimpleRecognizer(123, new int[0]);
/* 119 */   private final Recognizer RBRACE_CHAR = new SimpleRecognizer(125, new int[0]);
/* 120 */   private final Recognizer SEMI_CHAR = new SimpleRecognizer(59, new int[0]);
/* 121 */   private final Recognizer COLON_CHAR = new SimpleRecognizer(58, new int[0]);
/* 122 */   private final Recognizer SOLIDUS_CHAR = new SimpleRecognizer(47, new int[0]);
/* 123 */   private final Recognizer MINUS_CHAR = new SimpleRecognizer(45, new int[0]);
/* 124 */   private final Recognizer PLUS_CHAR = new SimpleRecognizer(43, new int[0]);
/* 125 */   private final Recognizer STAR_CHAR = new SimpleRecognizer(42, new int[0]);
/* 126 */   private final Recognizer LPAREN_CHAR = new SimpleRecognizer(40, new int[0]);
/* 127 */   private final Recognizer RPAREN_CHAR = new SimpleRecognizer(41, new int[0]);
/* 128 */   private final Recognizer COMMA_CHAR = new SimpleRecognizer(44, new int[0]);
/* 129 */   private final Recognizer UNDERSCORE_CHAR = new SimpleRecognizer(95, new int[0]);
/* 130 */   private final Recognizer HASH_CHAR = new SimpleRecognizer(35, new int[0]);
/*     */ 
/* 132 */   private final Recognizer WS_CHARS = new Recognizer() {
/*     */     public boolean recognize(int paramAnonymousInt) {
/* 134 */       return (paramAnonymousInt == 32) || (paramAnonymousInt == 9) || (paramAnonymousInt == 13) || (paramAnonymousInt == 10) || (paramAnonymousInt == 12);
/*     */     }
/* 132 */   };
/*     */ 
/* 141 */   private final Recognizer NL_CHARS = new Recognizer() {
/*     */     public boolean recognize(int paramAnonymousInt) {
/* 143 */       return (paramAnonymousInt == 13) || (paramAnonymousInt == 10);
/*     */     }
/* 141 */   };
/*     */ 
/* 147 */   private final Recognizer DIGIT = new SimpleRecognizer(48, new int[] { 49, 50, 51, 52, 53, 54, 55, 56, 57 });
/*     */ 
/* 151 */   private final Recognizer HEX_DIGIT = new Recognizer() {
/*     */     public boolean recognize(int paramAnonymousInt) {
/* 153 */       return ((48 <= paramAnonymousInt) && (paramAnonymousInt <= 57)) || ((97 <= paramAnonymousInt) && (paramAnonymousInt <= 102)) || ((65 <= paramAnonymousInt) && (paramAnonymousInt <= 70));
/*     */     }
/* 151 */   };
/*     */ 
/* 160 */   final LexerState initState = new LexerState("initState", null, new Recognizer[0]) {
/* 161 */     public boolean accepts(int paramAnonymousInt) { return true; }
/*     */ 
/* 160 */   };
/*     */ 
/* 164 */   final LexerState hashState = new LexerState("hashState", this.HASH_CHAR, new Recognizer[0]);
/*     */ 
/* 168 */   final LexerState minusState = new LexerState("minusState", this.MINUS_CHAR, new Recognizer[0]);
/*     */ 
/* 172 */   final LexerState plusState = new LexerState("plusState", this.PLUS_CHAR, new Recognizer[0]);
/*     */ 
/* 177 */   final LexerState dotState = new LexerState(38, "dotState", this.DOT_CHAR, new Recognizer[0]);
/*     */ 
/* 182 */   final LexerState nmStartState = new LexerState(11, "nmStartState", this.UNDERSCORE_CHAR, new Recognizer[] { this.ALPHA });
/*     */ 
/* 187 */   final LexerState nmCharState = new LexerState(11, "nmCharState", this.UNDERSCORE_CHAR, new Recognizer[] { this.ALPHA, this.DIGIT, this.MINUS_CHAR });
/*     */ 
/* 193 */   final LexerState hashNameCharState = new LexerState(37, "hashNameCharState", this.UNDERSCORE_CHAR, new Recognizer[] { this.ALPHA, this.DIGIT, this.MINUS_CHAR });
/*     */ 
/* 198 */   final LexerState lparenState = new LexerState(12, "lparenState", this.LPAREN_CHAR, new Recognizer[0]);
/*     */ 
/* 203 */   final LexerState leadingDigitsState = new LexerState(13, "leadingDigitsState", this.DIGIT, new Recognizer[0]);
/*     */ 
/* 209 */   final LexerState decimalMarkState = new LexerState("decimalMarkState", this.DOT_CHAR, new Recognizer[0]);
/*     */ 
/* 214 */   final LexerState trailingDigitsState = new LexerState(13, "trailingDigitsState", this.DIGIT, new Recognizer[0]);
/*     */ 
/* 219 */   final LexerState unitsState = new UnitsState();
/*     */ 
/* 562 */   private int pos = 0;
/* 563 */   private int offset = 0;
/* 564 */   private int line = 1;
/* 565 */   private int lastc = -1;
/*     */   private int ch;
/*     */   private Reader reader;
/*     */   private Token token;
/*     */   private final Map<LexerState, LexerState[]> stateMap;
/*     */   private LexerState currentState;
/*     */   private final StringBuilder text;
/*     */ 
/*     */   public static CSSLexer getInstance()
/*     */   {
/*  41 */     return InstanceHolder.INSTANCE;
/*     */   }
/*     */ 
/*     */   private Map<LexerState, LexerState[]> createStateMap()
/*     */   {
/* 223 */     HashMap localHashMap = new HashMap();
/*     */ 
/* 232 */     localHashMap.put(this.initState, new LexerState[] { this.hashState, this.minusState, this.nmStartState, this.plusState, this.minusState, this.leadingDigitsState, this.dotState });
/*     */ 
/* 249 */     localHashMap.put(this.minusState, new LexerState[] { this.nmStartState, this.leadingDigitsState, this.decimalMarkState });
/*     */ 
/* 264 */     localHashMap.put(this.hashState, new LexerState[] { this.hashNameCharState });
/*     */ 
/* 271 */     localHashMap.put(this.hashNameCharState, new LexerState[] { this.hashNameCharState });
/*     */ 
/* 286 */     localHashMap.put(this.nmStartState, new LexerState[] { this.nmCharState });
/*     */ 
/* 293 */     localHashMap.put(this.nmCharState, new LexerState[] { this.nmCharState, this.lparenState });
/*     */ 
/* 302 */     localHashMap.put(this.plusState, new LexerState[] { this.leadingDigitsState, this.decimalMarkState });
/*     */ 
/* 312 */     localHashMap.put(this.leadingDigitsState, new LexerState[] { this.leadingDigitsState, this.decimalMarkState, this.unitsState });
/*     */ 
/* 325 */     localHashMap.put(this.dotState, new LexerState[] { this.trailingDigitsState });
/*     */ 
/* 332 */     localHashMap.put(this.decimalMarkState, new LexerState[] { this.trailingDigitsState });
/*     */ 
/* 340 */     localHashMap.put(this.trailingDigitsState, new LexerState[] { this.trailingDigitsState, this.unitsState });
/*     */ 
/* 349 */     localHashMap.put(this.unitsState, new LexerState[] { this.unitsState });
/*     */ 
/* 356 */     return localHashMap;
/*     */   }
/*     */ 
/*     */   private CSSLexer() {
/* 360 */     this.stateMap = createStateMap();
/* 361 */     this.text = new StringBuilder(64);
/* 362 */     this.currentState = this.initState;
/*     */   }
/*     */ 
/*     */   public void setReader(Reader paramReader) {
/* 366 */     this.reader = paramReader;
/* 367 */     this.lastc = -1;
/* 368 */     this.pos = (this.offset = 0);
/* 369 */     this.line = 1;
/* 370 */     this.currentState = this.initState;
/* 371 */     this.token = null;
/*     */     try {
/* 373 */       this.ch = readChar();
/*     */     } catch (IOException localIOException) {
/* 375 */       this.token = Token.EOF_TOKEN;
/*     */     }
/*     */   }
/*     */ 
/*     */   private Token scanImportant()
/*     */     throws IOException
/*     */   {
/* 382 */     Recognizer[] arrayOfRecognizer = { this.I, this.M, this.P, this.O, this.R, this.T, this.A, this.N, this.T };
/*     */ 
/* 384 */     int i = 0;
/*     */ 
/* 386 */     this.text.append((char)this.ch);
/*     */ 
/* 389 */     this.ch = readChar();
/*     */     while (true)
/*     */     {
/*     */       int j;
/* 393 */       switch (this.ch)
/*     */       {
/*     */       case -1:
/* 396 */         this.token = Token.EOF_TOKEN;
/* 397 */         return this.token;
/*     */       case 47:
/* 400 */         this.ch = readChar();
/* 401 */         if (this.ch == 42) { skipComment();
/*     */         } else {
/* 403 */           this.text.append('/').append((char)this.ch);
/* 404 */           j = this.offset;
/* 405 */           this.offset = this.pos;
/* 406 */           return new Token(0, this.text.toString(), this.line, j);
/*     */         }
/*     */ 
/*     */         break;
/*     */       case 9:
/*     */       case 10:
/*     */       case 12:
/*     */       case 13:
/*     */       case 32:
/* 415 */         this.ch = readChar();
/* 416 */         break;
/*     */       default:
/* 419 */         j = 1;
/*     */         boolean bool;
/* 420 */         while ((j != 0) && (i < arrayOfRecognizer.length)) {
/* 421 */           bool = arrayOfRecognizer[(i++)].recognize(this.ch);
/* 422 */           this.text.append((char)this.ch);
/* 423 */           this.ch = readChar();
/*     */         }
/*     */         int k;
/* 425 */         if (bool) {
/* 426 */           k = this.offset;
/* 427 */           this.offset = (this.pos - 1);
/* 428 */           return new Token(39, "!important", this.line, k);
/*     */         }
/*     */ 
/* 432 */         while ((this.ch != 59) && (this.ch != 125) && (this.ch != -1)) {
/* 433 */           this.ch = readChar();
/*     */         }
/* 435 */         if (this.ch != -1) {
/* 436 */           k = this.offset;
/* 437 */           this.offset = (this.pos - 1);
/* 438 */           return new Token(1, this.text.toString(), this.line, k);
/*     */         }
/* 440 */         return Token.EOF_TOKEN;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void skipComment()
/*     */     throws IOException
/*     */   {
/* 548 */     while (this.ch != -1)
/* 549 */       if (this.ch == 42) {
/* 550 */         this.ch = readChar();
/* 551 */         if (this.ch == 47) {
/* 552 */           this.offset = this.pos;
/* 553 */           this.ch = readChar();
/* 554 */           break;
/*     */         }
/*     */       } else {
/* 557 */         this.ch = readChar();
/*     */       }
/*     */   }
/*     */ 
/*     */   private int readChar()
/*     */     throws IOException
/*     */   {
/* 569 */     int i = this.reader.read();
/*     */ 
/* 573 */     if ((this.lastc == 10) || ((this.lastc == 13) && (i != 10)))
/*     */     {
/* 575 */       this.pos = 1;
/* 576 */       this.offset = 0;
/* 577 */       this.line += 1;
/*     */     } else {
/* 579 */       this.pos += 1;
/*     */     }
/*     */ 
/* 582 */     this.lastc = i;
/* 583 */     return i;
/*     */   }
/*     */ 
/*     */   public Token nextToken()
/*     */   {
/* 588 */     Token localToken = null;
/* 589 */     if (this.token != null) {
/* 590 */       localToken = this.token;
/* 591 */       if (this.token.getType() != -1) this.token = null; 
/*     */     }
/*     */     else { do
/* 594 */         localToken = getToken();
/* 595 */       while ((localToken != null) && (Token.SKIP_TOKEN.equals(localToken)));
/*     */     }
/*     */ 
/* 601 */     this.text.delete(0, this.text.length());
/* 602 */     this.currentState = this.initState;
/*     */ 
/* 604 */     return localToken;
/*     */   }
/*     */ 
/*     */   private Token getToken() {
/*     */     try {
/*     */       Object localObject3;
/*     */       do {
/*     */         while (true) {
/* 612 */           Object localObject1 = this.currentState != null ? (LexerState[])this.stateMap.get(this.currentState) : null;
/*     */ 
/* 615 */           int i = localObject1 != null ? localObject1.length : 0;
/*     */ 
/* 617 */           Object localObject2 = null;
/* 618 */           for (j = 0; (j < i) && (localObject2 == null); j++) {
/* 619 */             localObject3 = localObject1[j];
/* 620 */             if (((LexerState)localObject3).accepts(this.ch)) {
/* 621 */               localObject2 = localObject3;
/*     */             }
/*     */           }
/*     */ 
/* 625 */           if (localObject2 == null)
/*     */           {
/*     */             break;
/*     */           }
/* 629 */           this.currentState = localObject2;
/* 630 */           this.text.append((char)this.ch);
/* 631 */           this.ch = readChar();
/*     */         }
/*     */ 
/* 639 */         int j = this.currentState.getType();
/*     */ 
/* 646 */         if ((j != 0) || (!this.currentState.equals(this.initState)))
/*     */         {
/* 649 */           localObject3 = this.text.toString();
/* 650 */           Token localToken2 = new Token(j, (String)localObject3, this.line, this.offset);
/*     */ 
/* 653 */           this.offset = (this.pos - 1);
/*     */ 
/* 656 */           return localToken2;
/*     */         }
/*     */ 
/* 662 */         switch (this.ch)
/*     */         {
/*     */         case -1:
/* 665 */           this.token = Token.EOF_TOKEN;
/* 666 */           return this.token;
/*     */         case 34:
/*     */         case 39:
/* 671 */           this.text.append((char)this.ch);
/* 672 */           j = this.ch;
/* 673 */           while ((this.ch = readChar()) != -1) {
/* 674 */             this.text.append((char)this.ch);
/* 675 */             if (this.ch == j) break;
/*     */           }
/*     */ 
/* 678 */           if (this.ch != -1) {
/* 679 */             this.token = new Token(10, this.text.toString(), this.line, this.offset);
/* 680 */             this.offset = this.pos;
/*     */           } else {
/* 682 */             this.token = new Token(0, this.text.toString(), this.line, this.offset);
/* 683 */             this.offset = this.pos;
/*     */           }
/* 685 */           break;
/*     */         case 47:
/* 688 */           this.ch = readChar();
/* 689 */           if (this.ch != 42) break label566;
/* 690 */           skipComment();
/*     */         case 62:
/*     */         case 123:
/*     */         case 125:
/*     */         case 59:
/*     */         case 58:
/*     */         case 42:
/*     */         case 40:
/*     */         case 41:
/*     */         case 44:
/*     */         case 46:
/*     */         case 9:
/*     */         case 12:
/*     */         case 32:
/*     */         case 13:
/*     */         case 10:
/*     */         case 33:
/* 691 */         case 64: }  } while (this.ch != -1);
/*     */ 
/* 694 */       this.token = Token.EOF_TOKEN;
/* 695 */       return this.token;
/*     */ 
/* 699 */       label566: this.token = new Token(32, "/", this.line, this.offset);
/* 700 */       this.offset = this.pos;
/*     */ 
/* 702 */       break label1205;
/*     */ 
/* 706 */       this.token = new Token(27, ">", this.line, this.offset);
/* 707 */       this.offset = this.pos;
/* 708 */       break label1205;
/*     */ 
/* 711 */       this.token = new Token(28, "{", this.line, this.offset);
/* 712 */       this.offset = this.pos;
/* 713 */       break label1205;
/*     */ 
/* 716 */       this.token = new Token(29, "}", this.line, this.offset);
/* 717 */       this.offset = this.pos;
/* 718 */       break label1205;
/*     */ 
/* 721 */       this.token = new Token(30, ";", this.line, this.offset);
/* 722 */       this.offset = this.pos;
/* 723 */       break label1205;
/*     */ 
/* 726 */       this.token = new Token(31, ":", this.line, this.offset);
/* 727 */       this.offset = this.pos;
/* 728 */       break label1205;
/*     */ 
/* 731 */       this.token = new Token(33, "*", this.line, this.offset);
/* 732 */       this.offset = this.pos;
/* 733 */       break label1205;
/*     */ 
/* 736 */       this.token = new Token(34, "(", this.line, this.offset);
/* 737 */       this.offset = this.pos;
/* 738 */       break label1205;
/*     */ 
/* 741 */       this.token = new Token(35, ")", this.line, this.offset);
/* 742 */       this.offset = this.pos;
/* 743 */       break label1205;
/*     */ 
/* 746 */       this.token = new Token(36, ",", this.line, this.offset);
/* 747 */       this.offset = this.pos;
/* 748 */       break label1205;
/*     */ 
/* 751 */       this.token = new Token(38, ".", this.line, this.offset);
/* 752 */       this.offset = this.pos;
/* 753 */       break label1205;
/*     */ 
/* 758 */       this.token = new Token(40, Character.toString((char)this.ch), this.line, this.offset);
/* 759 */       this.offset = this.pos;
/* 760 */       break label1205;
/*     */ 
/* 764 */       this.token = new Token(41, "\\r", this.line, this.offset);
/*     */ 
/* 767 */       this.ch = readChar();
/* 768 */       if (this.ch == 10) {
/* 769 */         this.token = new Token(41, "\\r\\n", this.line, this.offset);
/*     */       }
/*     */       else
/*     */       {
/* 775 */         localObject3 = this.token;
/* 776 */         this.token = (this.ch == -1 ? Token.EOF_TOKEN : null);
/* 777 */         return localObject3;
/*     */ 
/* 782 */         this.token = new Token(41, "\\n", this.line, this.offset);
/*     */ 
/* 784 */         break label1205;
/*     */ 
/* 787 */         return scanImportant();
/*     */         do
/*     */         {
/* 793 */           this.ch = readChar();
/*     */         }
/* 795 */         while ((this.ch != 59) && (this.ch != -1));
/* 796 */         if (this.ch == 59) {
/* 797 */           this.ch = readChar();
/* 798 */           this.token = Token.SKIP_TOKEN;
/* 799 */           this.offset = this.pos; break label1205;
/*     */ 
/* 805 */           this.token = new Token(0, Character.toString((char)this.ch), this.line, this.offset);
/* 806 */           this.offset = this.pos;
/*     */         }
/*     */       }
/*     */ 
/* 810 */       label1205: if (this.token == null)
/*     */       {
/* 812 */         this.token = new Token(0, null, this.line, this.offset);
/* 813 */         this.offset = this.pos;
/* 814 */       } else if (this.token.getType() == -1) {
/* 815 */         return this.token;
/*     */       }
/*     */ 
/* 818 */       if (this.ch != -1) this.ch = readChar();
/*     */ 
/* 820 */       Token localToken1 = this.token;
/* 821 */       this.token = null;
/* 822 */       return localToken1;
/*     */     }
/*     */     catch (IOException localIOException) {
/* 825 */       this.token = Token.EOF_TOKEN;
/* 826 */     }return this.token;
/*     */   }
/*     */ 
/*     */   private class UnitsState extends LexerState
/*     */   {
/* 449 */     private Recognizer[][] units = { { CSSLexer.this.C, CSSLexer.this.M }, { CSSLexer.this.D, CSSLexer.this.E, CSSLexer.this.G }, { CSSLexer.this.E, CSSLexer.this.M }, { CSSLexer.this.E, CSSLexer.this.X }, { CSSLexer.this.G, CSSLexer.this.R, CSSLexer.this.A, CSSLexer.this.D }, { CSSLexer.this.I, CSSLexer.this.N }, { CSSLexer.this.M, CSSLexer.this.M }, { CSSLexer.this.P, CSSLexer.this.C }, { CSSLexer.this.P, CSSLexer.this.T }, { CSSLexer.this.P, CSSLexer.this.X }, { CSSLexer.this.R, CSSLexer.this.A, CSSLexer.this.D }, { CSSLexer.this.T, CSSLexer.this.U, CSSLexer.this.R, CSSLexer.this.N }, { new SimpleRecognizer(37, new int[0]) } };
/*     */ 
/* 470 */     private int unitsMask = 8191;
/*     */ 
/* 473 */     private int index = -1;
/*     */ 
/*     */     UnitsState() {
/* 476 */       super("UnitsState", null, new Recognizer[0]);
/*     */     }
/*     */ 
/*     */     public int getType()
/*     */     {
/* 482 */       int i = 0;
/*     */ 
/* 486 */       switch (this.unitsMask) { case 1:
/* 487 */         i = 14; break;
/*     */       case 2:
/* 488 */         i = 23; break;
/*     */       case 4:
/* 489 */         i = 15; break;
/*     */       case 8:
/* 490 */         i = 16; break;
/*     */       case 16:
/* 491 */         i = 24; break;
/*     */       case 32:
/* 492 */         i = 17; break;
/*     */       case 64:
/* 493 */         i = 18; break;
/*     */       case 128:
/* 494 */         i = 19; break;
/*     */       case 256:
/* 495 */         i = 20; break;
/*     */       case 512:
/* 496 */         i = 21; break;
/*     */       case 1024:
/* 497 */         i = 25; break;
/*     */       case 2048:
/* 498 */         i = 26; break;
/*     */       case 4096:
/* 499 */         i = 22; break;
/*     */       default:
/* 500 */         i = 0;
/*     */       }
/*     */ 
/* 504 */       this.unitsMask = 8191;
/* 505 */       this.index = -1;
/*     */ 
/* 507 */       return i;
/*     */     }
/*     */ 
/*     */     public boolean accepts(int paramInt)
/*     */     {
/* 516 */       if ((!CSSLexer.this.ALPHA.recognize(paramInt)) && (paramInt != 37)) {
/* 517 */         return false;
/*     */       }
/*     */ 
/* 523 */       if (this.unitsMask == 0) return true;
/*     */ 
/* 525 */       this.index += 1;
/*     */ 
/* 527 */       for (int i = 0; i < this.units.length; i++)
/*     */       {
/* 529 */         int j = 1 << i;
/*     */ 
/* 532 */         if ((this.unitsMask & j) != 0)
/*     */         {
/* 534 */           if ((this.index >= this.units[i].length) || (!this.units[i][this.index].recognize(paramInt)))
/*     */           {
/* 536 */             this.unitsMask &= (j ^ 0xFFFFFFFF);
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 542 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class InstanceHolder
/*     */   {
/*  37 */     static final CSSLexer INSTANCE = new CSSLexer(null);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.css.parser.CSSLexer
 * JD-Core Version:    0.6.2
 */