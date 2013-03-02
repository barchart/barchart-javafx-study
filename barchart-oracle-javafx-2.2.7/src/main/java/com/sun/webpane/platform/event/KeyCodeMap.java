/*     */ package com.sun.webpane.platform.event;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javafx.scene.input.KeyCode;
/*     */ 
/*     */ public class KeyCodeMap
/*     */ {
/* 201 */   private static final Map<KeyCode, Entry> MAP = Collections.unmodifiableMap(localHashMap);
/*     */ 
/*     */   private static void put(Map<KeyCode, Entry> paramMap, KeyCode paramKeyCode, int paramInt, String paramString)
/*     */   {
/* 208 */     paramMap.put(paramKeyCode, new Entry(paramInt, paramString, null));
/*     */   }
/*     */ 
/*     */   private static void put(Map<KeyCode, Entry> paramMap, KeyCode paramKeyCode, int paramInt)
/*     */   {
/* 214 */     put(paramMap, paramKeyCode, paramInt, null);
/*     */   }
/*     */ 
/*     */   public static Entry lookup(KeyCode paramKeyCode)
/*     */   {
/* 223 */     Entry localEntry = (Entry)MAP.get(paramKeyCode);
/* 224 */     if ((localEntry == null) || (localEntry.getKeyIdentifier() == null)) {
/* 225 */       int i = localEntry != null ? localEntry.getWindowsVirtualKeyCode() : 0;
/*     */ 
/* 227 */       String str = String.format("U+%04X", new Object[] { Integer.valueOf(i) });
/*     */ 
/* 229 */       localEntry = new Entry(i, str, null);
/*     */     }
/* 231 */     return localEntry;
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  47 */     HashMap localHashMap = new HashMap();
/*     */ 
/*  49 */     put(localHashMap, KeyCode.ENTER, 13, "Enter");
/*  50 */     put(localHashMap, KeyCode.BACK_SPACE, 8);
/*  51 */     put(localHashMap, KeyCode.TAB, 9);
/*  52 */     put(localHashMap, KeyCode.CANCEL, 3);
/*  53 */     put(localHashMap, KeyCode.CLEAR, 12, "Clear");
/*  54 */     put(localHashMap, KeyCode.SHIFT, 16, "Shift");
/*  55 */     put(localHashMap, KeyCode.CONTROL, 17, "Control");
/*  56 */     put(localHashMap, KeyCode.ALT, 18, "Alt");
/*  57 */     put(localHashMap, KeyCode.PAUSE, 19, "Pause");
/*  58 */     put(localHashMap, KeyCode.CAPS, 20, "CapsLock");
/*  59 */     put(localHashMap, KeyCode.ESCAPE, 27);
/*  60 */     put(localHashMap, KeyCode.SPACE, 32);
/*  61 */     put(localHashMap, KeyCode.PAGE_UP, 33, "PageUp");
/*  62 */     put(localHashMap, KeyCode.PAGE_DOWN, 34, "PageDown");
/*  63 */     put(localHashMap, KeyCode.END, 35, "End");
/*  64 */     put(localHashMap, KeyCode.HOME, 36, "Home");
/*  65 */     put(localHashMap, KeyCode.LEFT, 37, "Left");
/*  66 */     put(localHashMap, KeyCode.UP, 38, "Up");
/*  67 */     put(localHashMap, KeyCode.RIGHT, 39, "Right");
/*  68 */     put(localHashMap, KeyCode.DOWN, 40, "Down");
/*  69 */     put(localHashMap, KeyCode.COMMA, 188);
/*  70 */     put(localHashMap, KeyCode.MINUS, 189);
/*  71 */     put(localHashMap, KeyCode.PERIOD, 190);
/*  72 */     put(localHashMap, KeyCode.SLASH, 191);
/*  73 */     put(localHashMap, KeyCode.DIGIT0, 48);
/*  74 */     put(localHashMap, KeyCode.DIGIT1, 49);
/*  75 */     put(localHashMap, KeyCode.DIGIT2, 50);
/*  76 */     put(localHashMap, KeyCode.DIGIT3, 51);
/*  77 */     put(localHashMap, KeyCode.DIGIT4, 52);
/*  78 */     put(localHashMap, KeyCode.DIGIT5, 53);
/*  79 */     put(localHashMap, KeyCode.DIGIT6, 54);
/*  80 */     put(localHashMap, KeyCode.DIGIT7, 55);
/*  81 */     put(localHashMap, KeyCode.DIGIT8, 56);
/*  82 */     put(localHashMap, KeyCode.DIGIT9, 57);
/*  83 */     put(localHashMap, KeyCode.SEMICOLON, 186);
/*  84 */     put(localHashMap, KeyCode.EQUALS, 187);
/*  85 */     put(localHashMap, KeyCode.A, 65);
/*  86 */     put(localHashMap, KeyCode.B, 66);
/*  87 */     put(localHashMap, KeyCode.C, 67);
/*  88 */     put(localHashMap, KeyCode.D, 68);
/*  89 */     put(localHashMap, KeyCode.E, 69);
/*  90 */     put(localHashMap, KeyCode.F, 70);
/*  91 */     put(localHashMap, KeyCode.G, 71);
/*  92 */     put(localHashMap, KeyCode.H, 72);
/*  93 */     put(localHashMap, KeyCode.I, 73);
/*  94 */     put(localHashMap, KeyCode.J, 74);
/*  95 */     put(localHashMap, KeyCode.K, 75);
/*  96 */     put(localHashMap, KeyCode.L, 76);
/*  97 */     put(localHashMap, KeyCode.M, 77);
/*  98 */     put(localHashMap, KeyCode.N, 78);
/*  99 */     put(localHashMap, KeyCode.O, 79);
/* 100 */     put(localHashMap, KeyCode.P, 80);
/* 101 */     put(localHashMap, KeyCode.Q, 81);
/* 102 */     put(localHashMap, KeyCode.R, 82);
/* 103 */     put(localHashMap, KeyCode.S, 83);
/* 104 */     put(localHashMap, KeyCode.T, 84);
/* 105 */     put(localHashMap, KeyCode.U, 85);
/* 106 */     put(localHashMap, KeyCode.V, 86);
/* 107 */     put(localHashMap, KeyCode.W, 87);
/* 108 */     put(localHashMap, KeyCode.X, 88);
/* 109 */     put(localHashMap, KeyCode.Y, 89);
/* 110 */     put(localHashMap, KeyCode.Z, 90);
/* 111 */     put(localHashMap, KeyCode.OPEN_BRACKET, 219);
/* 112 */     put(localHashMap, KeyCode.BACK_SLASH, 220);
/* 113 */     put(localHashMap, KeyCode.CLOSE_BRACKET, 221);
/* 114 */     put(localHashMap, KeyCode.NUMPAD0, 96);
/* 115 */     put(localHashMap, KeyCode.NUMPAD1, 97);
/* 116 */     put(localHashMap, KeyCode.NUMPAD2, 98);
/* 117 */     put(localHashMap, KeyCode.NUMPAD3, 99);
/* 118 */     put(localHashMap, KeyCode.NUMPAD4, 100);
/* 119 */     put(localHashMap, KeyCode.NUMPAD5, 101);
/* 120 */     put(localHashMap, KeyCode.NUMPAD6, 102);
/* 121 */     put(localHashMap, KeyCode.NUMPAD7, 103);
/* 122 */     put(localHashMap, KeyCode.NUMPAD8, 104);
/* 123 */     put(localHashMap, KeyCode.NUMPAD9, 105);
/* 124 */     put(localHashMap, KeyCode.MULTIPLY, 106);
/* 125 */     put(localHashMap, KeyCode.ADD, 107);
/* 126 */     put(localHashMap, KeyCode.SEPARATOR, 108);
/* 127 */     put(localHashMap, KeyCode.SUBTRACT, 109);
/* 128 */     put(localHashMap, KeyCode.DECIMAL, 110);
/* 129 */     put(localHashMap, KeyCode.DIVIDE, 111);
/* 130 */     put(localHashMap, KeyCode.DELETE, 46, "U+007F");
/* 131 */     put(localHashMap, KeyCode.NUM_LOCK, 144);
/* 132 */     put(localHashMap, KeyCode.SCROLL_LOCK, 145, "Scroll");
/* 133 */     put(localHashMap, KeyCode.F1, 112, "F1");
/* 134 */     put(localHashMap, KeyCode.F2, 113, "F2");
/* 135 */     put(localHashMap, KeyCode.F3, 114, "F3");
/* 136 */     put(localHashMap, KeyCode.F4, 115, "F4");
/* 137 */     put(localHashMap, KeyCode.F5, 116, "F5");
/* 138 */     put(localHashMap, KeyCode.F6, 117, "F6");
/* 139 */     put(localHashMap, KeyCode.F7, 118, "F7");
/* 140 */     put(localHashMap, KeyCode.F8, 119, "F8");
/* 141 */     put(localHashMap, KeyCode.F9, 120, "F9");
/* 142 */     put(localHashMap, KeyCode.F10, 121, "F10");
/* 143 */     put(localHashMap, KeyCode.F11, 122, "F11");
/* 144 */     put(localHashMap, KeyCode.F12, 123, "F12");
/* 145 */     put(localHashMap, KeyCode.F13, 124, "F13");
/* 146 */     put(localHashMap, KeyCode.F14, 125, "F14");
/* 147 */     put(localHashMap, KeyCode.F15, 126, "F15");
/* 148 */     put(localHashMap, KeyCode.F16, 127, "F16");
/* 149 */     put(localHashMap, KeyCode.F17, 128, "F17");
/* 150 */     put(localHashMap, KeyCode.F18, 129, "F18");
/* 151 */     put(localHashMap, KeyCode.F19, 130, "F19");
/* 152 */     put(localHashMap, KeyCode.F20, 131, "F20");
/* 153 */     put(localHashMap, KeyCode.F21, 132, "F21");
/* 154 */     put(localHashMap, KeyCode.F22, 133, "F22");
/* 155 */     put(localHashMap, KeyCode.F23, 134, "F23");
/* 156 */     put(localHashMap, KeyCode.F24, 135, "F24");
/* 157 */     put(localHashMap, KeyCode.PRINTSCREEN, 44, "PrintScreen");
/* 158 */     put(localHashMap, KeyCode.INSERT, 45, "Insert");
/* 159 */     put(localHashMap, KeyCode.HELP, 47, "Help");
/* 160 */     put(localHashMap, KeyCode.META, 0, "Meta");
/* 161 */     put(localHashMap, KeyCode.BACK_QUOTE, 192);
/* 162 */     put(localHashMap, KeyCode.QUOTE, 222);
/* 163 */     put(localHashMap, KeyCode.KP_UP, 38, "Up");
/* 164 */     put(localHashMap, KeyCode.KP_DOWN, 40, "Down");
/* 165 */     put(localHashMap, KeyCode.KP_LEFT, 37, "Left");
/* 166 */     put(localHashMap, KeyCode.KP_RIGHT, 39, "Right");
/* 167 */     put(localHashMap, KeyCode.AMPERSAND, 55);
/* 168 */     put(localHashMap, KeyCode.ASTERISK, 56);
/* 169 */     put(localHashMap, KeyCode.QUOTEDBL, 222);
/* 170 */     put(localHashMap, KeyCode.LESS, 188);
/* 171 */     put(localHashMap, KeyCode.GREATER, 190);
/* 172 */     put(localHashMap, KeyCode.BRACELEFT, 219);
/* 173 */     put(localHashMap, KeyCode.BRACERIGHT, 221);
/* 174 */     put(localHashMap, KeyCode.AT, 50);
/* 175 */     put(localHashMap, KeyCode.COLON, 186);
/* 176 */     put(localHashMap, KeyCode.CIRCUMFLEX, 54);
/* 177 */     put(localHashMap, KeyCode.DOLLAR, 52);
/* 178 */     put(localHashMap, KeyCode.EXCLAMATION_MARK, 49);
/* 179 */     put(localHashMap, KeyCode.LEFT_PARENTHESIS, 57);
/* 180 */     put(localHashMap, KeyCode.NUMBER_SIGN, 51);
/* 181 */     put(localHashMap, KeyCode.PLUS, 187);
/* 182 */     put(localHashMap, KeyCode.RIGHT_PARENTHESIS, 48);
/* 183 */     put(localHashMap, KeyCode.UNDERSCORE, 189);
/* 184 */     put(localHashMap, KeyCode.WINDOWS, 91, "Win");
/* 185 */     put(localHashMap, KeyCode.CONTEXT_MENU, 93);
/* 186 */     put(localHashMap, KeyCode.FINAL, 24);
/* 187 */     put(localHashMap, KeyCode.CONVERT, 28);
/* 188 */     put(localHashMap, KeyCode.NONCONVERT, 29);
/* 189 */     put(localHashMap, KeyCode.ACCEPT, 30);
/* 190 */     put(localHashMap, KeyCode.MODECHANGE, 31);
/* 191 */     put(localHashMap, KeyCode.KANA, 21);
/* 192 */     put(localHashMap, KeyCode.KANJI, 25);
/* 193 */     put(localHashMap, KeyCode.ALT_GRAPH, 165);
/* 194 */     put(localHashMap, KeyCode.PLAY, 250);
/* 195 */     put(localHashMap, KeyCode.TRACK_PREV, 177);
/* 196 */     put(localHashMap, KeyCode.TRACK_NEXT, 176);
/* 197 */     put(localHashMap, KeyCode.VOLUME_UP, 175);
/* 198 */     put(localHashMap, KeyCode.VOLUME_DOWN, 174);
/* 199 */     put(localHashMap, KeyCode.MUTE, 173);
/*     */   }
/*     */ 
/*     */   public static final class Entry
/*     */   {
/*     */     private final int windowsVirtualKeyCode;
/*     */     private final String keyIdentifier;
/*     */ 
/*     */     private Entry(int paramInt, String paramString)
/*     */     {
/*  31 */       this.windowsVirtualKeyCode = paramInt;
/*  32 */       this.keyIdentifier = paramString;
/*     */     }
/*     */ 
/*     */     public int getWindowsVirtualKeyCode() {
/*  36 */       return this.windowsVirtualKeyCode;
/*     */     }
/*     */ 
/*     */     public String getKeyIdentifier() {
/*  40 */       return this.keyIdentifier;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.platform.event.KeyCodeMap
 * JD-Core Version:    0.6.2
 */