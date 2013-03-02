/*      */ package javafx.scene.input;
/*      */ 
/*      */ import java.util.HashMap;
/*      */ import java.util.Map;
/*      */ 
/*      */ public enum KeyCode
/*      */ {
/*   39 */   ENTER(10, "Enter", 128), 
/*      */ 
/*   44 */   BACK_SPACE(8, "Backspace"), 
/*      */ 
/*   49 */   TAB(9, "Tab", 128), 
/*      */ 
/*   54 */   CANCEL(3, "Cancel"), 
/*      */ 
/*   59 */   CLEAR(12, "Clear"), 
/*      */ 
/*   64 */   SHIFT(16, "Shift", 8), 
/*      */ 
/*   69 */   CONTROL(17, "Ctrl", 8), 
/*      */ 
/*   74 */   ALT(18, "Alt", 8), 
/*      */ 
/*   79 */   PAUSE(19, "Pause"), 
/*      */ 
/*   84 */   CAPS(20, "Caps Lock"), 
/*      */ 
/*   89 */   ESCAPE(27, "Esc"), 
/*      */ 
/*   94 */   SPACE(32, "Space", 128), 
/*      */ 
/*   99 */   PAGE_UP(33, "Page Up", 2), 
/*      */ 
/*  104 */   PAGE_DOWN(34, "Page Down", 2), 
/*      */ 
/*  109 */   END(35, "End", 2), 
/*      */ 
/*  114 */   HOME(36, "Home", 2), 
/*      */ 
/*  119 */   LEFT(37, "Left", 6), 
/*      */ 
/*  124 */   UP(38, "Up", 6), 
/*      */ 
/*  129 */   RIGHT(39, "Right", 6), 
/*      */ 
/*  134 */   DOWN(40, "Down", 6), 
/*      */ 
/*  139 */   COMMA(44, "Comma"), 
/*      */ 
/*  144 */   MINUS(45, "Minus"), 
/*      */ 
/*  149 */   PERIOD(46, "Period"), 
/*      */ 
/*  154 */   SLASH(47, "Slash"), 
/*      */ 
/*  159 */   DIGIT0(48, "0", 32), 
/*      */ 
/*  164 */   DIGIT1(49, "1", 32), 
/*      */ 
/*  169 */   DIGIT2(50, "2", 32), 
/*      */ 
/*  174 */   DIGIT3(51, "3", 32), 
/*      */ 
/*  179 */   DIGIT4(52, "4", 32), 
/*      */ 
/*  184 */   DIGIT5(53, "5", 32), 
/*      */ 
/*  189 */   DIGIT6(54, "6", 32), 
/*      */ 
/*  194 */   DIGIT7(55, "7", 32), 
/*      */ 
/*  199 */   DIGIT8(56, "8", 32), 
/*      */ 
/*  204 */   DIGIT9(57, "9", 32), 
/*      */ 
/*  209 */   SEMICOLON(59, "Semicolon"), 
/*      */ 
/*  214 */   EQUALS(61, "Equals"), 
/*      */ 
/*  219 */   A(65, "A", 16), 
/*      */ 
/*  224 */   B(66, "B", 16), 
/*      */ 
/*  229 */   C(67, "C", 16), 
/*      */ 
/*  234 */   D(68, "D", 16), 
/*      */ 
/*  239 */   E(69, "E", 16), 
/*      */ 
/*  244 */   F(70, "F", 16), 
/*      */ 
/*  249 */   G(71, "G", 16), 
/*      */ 
/*  254 */   H(72, "H", 16), 
/*      */ 
/*  259 */   I(73, "I", 16), 
/*      */ 
/*  264 */   J(74, "J", 16), 
/*      */ 
/*  269 */   K(75, "K", 16), 
/*      */ 
/*  274 */   L(76, "L", 16), 
/*      */ 
/*  279 */   M(77, "M", 16), 
/*      */ 
/*  284 */   N(78, "N", 16), 
/*      */ 
/*  289 */   O(79, "O", 16), 
/*      */ 
/*  294 */   P(80, "P", 16), 
/*      */ 
/*  299 */   Q(81, "Q", 16), 
/*      */ 
/*  304 */   R(82, "R", 16), 
/*      */ 
/*  309 */   S(83, "S", 16), 
/*      */ 
/*  314 */   T(84, "T", 16), 
/*      */ 
/*  319 */   U(85, "U", 16), 
/*      */ 
/*  324 */   V(86, "V", 16), 
/*      */ 
/*  329 */   W(87, "W", 16), 
/*      */ 
/*  334 */   X(88, "X", 16), 
/*      */ 
/*  339 */   Y(89, "Y", 16), 
/*      */ 
/*  344 */   Z(90, "Z", 16), 
/*      */ 
/*  349 */   OPEN_BRACKET(91, "Open Bracket"), 
/*      */ 
/*  354 */   BACK_SLASH(92, "Back Slash"), 
/*      */ 
/*  359 */   CLOSE_BRACKET(93, "Close Bracket"), 
/*      */ 
/*  364 */   NUMPAD0(96, "Numpad 0", 96), 
/*      */ 
/*  369 */   NUMPAD1(97, "Numpad 1", 96), 
/*      */ 
/*  374 */   NUMPAD2(98, "Numpad 2", 96), 
/*      */ 
/*  379 */   NUMPAD3(99, "Numpad 3", 96), 
/*      */ 
/*  384 */   NUMPAD4(100, "Numpad 4", 96), 
/*      */ 
/*  389 */   NUMPAD5(101, "Numpad 5", 96), 
/*      */ 
/*  394 */   NUMPAD6(102, "Numpad 6", 96), 
/*      */ 
/*  399 */   NUMPAD7(103, "Numpad 7", 96), 
/*      */ 
/*  404 */   NUMPAD8(104, "Numpad 8", 96), 
/*      */ 
/*  409 */   NUMPAD9(105, "Numpad 9", 96), 
/*      */ 
/*  414 */   MULTIPLY(106, "Multiply"), 
/*      */ 
/*  419 */   ADD(107, "Add"), 
/*      */ 
/*  424 */   SEPARATOR(108, "Separator"), 
/*      */ 
/*  429 */   SUBTRACT(109, "Subtract"), 
/*      */ 
/*  434 */   DECIMAL(110, "Decimal"), 
/*      */ 
/*  439 */   DIVIDE(111, "Divide"), 
/*      */ 
/*  444 */   DELETE(127, "Delete"), 
/*      */ 
/*  449 */   NUM_LOCK(144, "Num Lock"), 
/*      */ 
/*  454 */   SCROLL_LOCK(145, "Scroll Lock"), 
/*      */ 
/*  459 */   F1(112, "F1", 1), 
/*      */ 
/*  464 */   F2(113, "F2", 1), 
/*      */ 
/*  469 */   F3(114, "F3", 1), 
/*      */ 
/*  474 */   F4(115, "F4", 1), 
/*      */ 
/*  479 */   F5(116, "F5", 1), 
/*      */ 
/*  484 */   F6(117, "F6", 1), 
/*      */ 
/*  489 */   F7(118, "F7", 1), 
/*      */ 
/*  494 */   F8(119, "F8", 1), 
/*      */ 
/*  499 */   F9(120, "F9", 1), 
/*      */ 
/*  504 */   F10(121, "F10", 1), 
/*      */ 
/*  509 */   F11(122, "F11", 1), 
/*      */ 
/*  514 */   F12(123, "F12", 1), 
/*      */ 
/*  519 */   F13(61440, "F13", 1), 
/*      */ 
/*  524 */   F14(61441, "F14", 1), 
/*      */ 
/*  529 */   F15(61442, "F15", 1), 
/*      */ 
/*  534 */   F16(61443, "F16", 1), 
/*      */ 
/*  539 */   F17(61444, "F17", 1), 
/*      */ 
/*  544 */   F18(61445, "F18", 1), 
/*      */ 
/*  549 */   F19(61446, "F19", 1), 
/*      */ 
/*  554 */   F20(61447, "F20", 1), 
/*      */ 
/*  559 */   F21(61448, "F21", 1), 
/*      */ 
/*  564 */   F22(61449, "F22", 1), 
/*      */ 
/*  569 */   F23(61450, "F23", 1), 
/*      */ 
/*  574 */   F24(61451, "F24", 1), 
/*      */ 
/*  579 */   PRINTSCREEN(154, "Print Screen"), 
/*      */ 
/*  584 */   INSERT(155, "Insert"), 
/*      */ 
/*  589 */   HELP(156, "Help"), 
/*      */ 
/*  594 */   META(157, "Meta", 8), 
/*      */ 
/*  599 */   BACK_QUOTE(192, "Back Quote"), 
/*      */ 
/*  604 */   QUOTE(222, "Quote"), 
/*      */ 
/*  609 */   KP_UP(224, "Numpad Up", 70), 
/*      */ 
/*  614 */   KP_DOWN(225, "Numpad Down", 70), 
/*      */ 
/*  619 */   KP_LEFT(226, "Numpad Left", 70), 
/*      */ 
/*  624 */   KP_RIGHT(227, "Numpad Right", 70), 
/*      */ 
/*  629 */   DEAD_GRAVE(128, "Dead Grave"), 
/*      */ 
/*  634 */   DEAD_ACUTE(129, "Dead Acute"), 
/*      */ 
/*  639 */   DEAD_CIRCUMFLEX(130, "Circumflex"), 
/*      */ 
/*  644 */   DEAD_TILDE(131, "Dead Tilde"), 
/*      */ 
/*  649 */   DEAD_MACRON(132, "Dead Macron"), 
/*      */ 
/*  654 */   DEAD_BREVE(133, "Dead Breve"), 
/*      */ 
/*  659 */   DEAD_ABOVEDOT(134, "Dead Abovedot"), 
/*      */ 
/*  664 */   DEAD_DIAERESIS(135, "Dead Diaeresis"), 
/*      */ 
/*  669 */   DEAD_ABOVERING(136, "Dead Abovering"), 
/*      */ 
/*  674 */   DEAD_DOUBLEACUTE(137, "Dead Doubleacute"), 
/*      */ 
/*  679 */   DEAD_CARON(138, "Dead Caron"), 
/*      */ 
/*  684 */   DEAD_CEDILLA(139, "Dead Cedilla"), 
/*      */ 
/*  689 */   DEAD_OGONEK(140, "Dead Ogonek"), 
/*      */ 
/*  694 */   DEAD_IOTA(141, "Dead Iota"), 
/*      */ 
/*  699 */   DEAD_VOICED_SOUND(142, "Dead Voiced Sound"), 
/*      */ 
/*  704 */   DEAD_SEMIVOICED_SOUND(143, "Dead Semivoiced Sound"), 
/*      */ 
/*  709 */   AMPERSAND(150, "Ampersand"), 
/*      */ 
/*  714 */   ASTERISK(151, "Asterisk"), 
/*      */ 
/*  719 */   QUOTEDBL(152, "Double Quote"), 
/*      */ 
/*  724 */   LESS(153, "Less"), 
/*      */ 
/*  729 */   GREATER(160, "Greater"), 
/*      */ 
/*  734 */   BRACELEFT(161, "Left Brace"), 
/*      */ 
/*  739 */   BRACERIGHT(162, "Right Brace"), 
/*      */ 
/*  744 */   AT(512, "At"), 
/*      */ 
/*  749 */   COLON(513, "Colon"), 
/*      */ 
/*  754 */   CIRCUMFLEX(514, "Circumflex"), 
/*      */ 
/*  759 */   DOLLAR(515, "Dollar"), 
/*      */ 
/*  764 */   EURO_SIGN(516, "Euro Sign"), 
/*      */ 
/*  769 */   EXCLAMATION_MARK(517, "Exclamation Mark"), 
/*      */ 
/*  774 */   INVERTED_EXCLAMATION_MARK(518, "Inverted Exclamation Mark"), 
/*      */ 
/*  779 */   LEFT_PARENTHESIS(519, "Left Parenthesis"), 
/*      */ 
/*  784 */   NUMBER_SIGN(520, "Number Sign"), 
/*      */ 
/*  789 */   PLUS(521, "Plus"), 
/*      */ 
/*  794 */   RIGHT_PARENTHESIS(522, "Right Parenthesis"), 
/*      */ 
/*  799 */   UNDERSCORE(523, "Underscore"), 
/*      */ 
/*  805 */   WINDOWS(524, "Windows", 8), 
/*      */ 
/*  810 */   CONTEXT_MENU(525, "Context Menu"), 
/*      */ 
/*  815 */   FINAL(24, "Final"), 
/*      */ 
/*  820 */   CONVERT(28, "Convert"), 
/*      */ 
/*  825 */   NONCONVERT(29, "Nonconvert"), 
/*      */ 
/*  830 */   ACCEPT(30, "Accept"), 
/*      */ 
/*  835 */   MODECHANGE(31, "Mode Change"), 
/*      */ 
/*  839 */   KANA(21, "Kana"), 
/*      */ 
/*  843 */   KANJI(25, "Kanji"), 
/*      */ 
/*  848 */   ALPHANUMERIC(240, "Alphanumeric"), 
/*      */ 
/*  853 */   KATAKANA(241, "Katakana"), 
/*      */ 
/*  858 */   HIRAGANA(242, "Hiragana"), 
/*      */ 
/*  863 */   FULL_WIDTH(243, "Full Width"), 
/*      */ 
/*  868 */   HALF_WIDTH(244, "Half Width"), 
/*      */ 
/*  873 */   ROMAN_CHARACTERS(245, "Roman Characters"), 
/*      */ 
/*  878 */   ALL_CANDIDATES(256, "All Candidates"), 
/*      */ 
/*  883 */   PREVIOUS_CANDIDATE(257, "Previous Candidate"), 
/*      */ 
/*  888 */   CODE_INPUT(258, "Code Input"), 
/*      */ 
/*  894 */   JAPANESE_KATAKANA(259, "Japanese Katakana"), 
/*      */ 
/*  900 */   JAPANESE_HIRAGANA(260, "Japanese Hiragana"), 
/*      */ 
/*  906 */   JAPANESE_ROMAN(261, "Japanese Roman"), 
/*      */ 
/*  912 */   KANA_LOCK(262, "Kana Lock"), 
/*      */ 
/*  917 */   INPUT_METHOD_ON_OFF(263, "Input Method On/Off"), 
/*      */ 
/*  922 */   CUT(65489, "Cut"), 
/*      */ 
/*  927 */   COPY(65485, "Copy"), 
/*      */ 
/*  932 */   PASTE(65487, "Paste"), 
/*      */ 
/*  937 */   UNDO(65483, "Undo"), 
/*      */ 
/*  942 */   AGAIN(65481, "Again"), 
/*      */ 
/*  947 */   FIND(65488, "Find"), 
/*      */ 
/*  952 */   PROPS(65482, "Properties"), 
/*      */ 
/*  957 */   STOP(65480, "Stop"), 
/*      */ 
/*  962 */   COMPOSE(65312, "Compose"), 
/*      */ 
/*  967 */   ALT_GRAPH(65406, "Alt Graph", 8), 
/*      */ 
/*  972 */   BEGIN(65368, "Begin"), 
/*      */ 
/*  979 */   UNDEFINED(0, "Undefined"), 
/*      */ 
/*  991 */   SOFTKEY_0(4096, "Softkey 0"), 
/*      */ 
/*  996 */   SOFTKEY_1(4097, "Softkey 1"), 
/*      */ 
/* 1001 */   SOFTKEY_2(4098, "Softkey 2"), 
/*      */ 
/* 1006 */   SOFTKEY_3(4099, "Softkey 3"), 
/*      */ 
/* 1011 */   SOFTKEY_4(4100, "Softkey 4"), 
/*      */ 
/* 1016 */   SOFTKEY_5(4101, "Softkey 5"), 
/*      */ 
/* 1021 */   SOFTKEY_6(4102, "Softkey 6"), 
/*      */ 
/* 1026 */   SOFTKEY_7(4103, "Softkey 7"), 
/*      */ 
/* 1031 */   SOFTKEY_8(4104, "Softkey 8"), 
/*      */ 
/* 1036 */   SOFTKEY_9(4105, "Softkey 9"), 
/*      */ 
/* 1041 */   GAME_A(4106, "Game A"), 
/*      */ 
/* 1046 */   GAME_B(4107, "Game B"), 
/*      */ 
/* 1051 */   GAME_C(4108, "Game C"), 
/*      */ 
/* 1056 */   GAME_D(4109, "Game D"), 
/*      */ 
/* 1061 */   STAR(4110, "Star"), 
/*      */ 
/* 1066 */   POUND(4111, "Pound"), 
/*      */ 
/* 1077 */   POWER(409, "Power"), 
/*      */ 
/* 1082 */   INFO(457, "Info"), 
/*      */ 
/* 1087 */   COLORED_KEY_0(403, "Colored Key 0"), 
/*      */ 
/* 1092 */   COLORED_KEY_1(404, "Colored Key 1"), 
/*      */ 
/* 1097 */   COLORED_KEY_2(405, "Colored Key 2"), 
/*      */ 
/* 1102 */   COLORED_KEY_3(406, "Colored Key 3"), 
/*      */ 
/* 1107 */   EJECT_TOGGLE(414, "Eject", 256), 
/*      */ 
/* 1112 */   PLAY(415, "Play", 256), 
/*      */ 
/* 1117 */   RECORD(416, "Record", 256), 
/*      */ 
/* 1122 */   FAST_FWD(417, "Fast Forward", 256), 
/*      */ 
/* 1127 */   REWIND(412, "Rewind", 256), 
/*      */ 
/* 1132 */   TRACK_PREV(424, "Previous Track", 256), 
/*      */ 
/* 1137 */   TRACK_NEXT(425, "Next Track", 256), 
/*      */ 
/* 1142 */   CHANNEL_UP(427, "Channel Up", 256), 
/*      */ 
/* 1147 */   CHANNEL_DOWN(428, "Channel Down", 256), 
/*      */ 
/* 1152 */   VOLUME_UP(447, "Volume Up", 256), 
/*      */ 
/* 1157 */   VOLUME_DOWN(448, "Volume Down", 256), 
/*      */ 
/* 1162 */   MUTE(449, "Mute", 256), 
/*      */ 
/* 1167 */   COMMAND(768, "Command", 8), 
/*      */ 
/* 1172 */   SHORTCUT(-1, "Shortcut");
/*      */ 
/*      */   final int code;
/*      */   final String ch;
/*      */   final String name;
/*      */   private int mask;
/*      */   private static final Map<Integer, KeyCode> charMap;
/*      */   private static final Map<String, KeyCode> nameMap;
/*      */ 
/*      */   private KeyCode(int paramInt1, String paramString, int paramInt2)
/*      */   {
/* 1195 */     this.code = paramInt1;
/* 1196 */     this.name = paramString;
/* 1197 */     this.mask = paramInt2;
/*      */ 
/* 1199 */     this.ch = String.valueOf((char)paramInt1);
/*      */   }
/*      */ 
/*      */   private KeyCode(int paramInt, String paramString) {
/* 1203 */     this(paramInt, paramString, 0);
/*      */   }
/*      */ 
/*      */   public final boolean isFunctionKey()
/*      */   {
/* 1212 */     return (this.mask & 0x1) != 0;
/*      */   }
/*      */ 
/*      */   public final boolean isNavigationKey()
/*      */   {
/* 1222 */     return (this.mask & 0x2) != 0;
/*      */   }
/*      */ 
/*      */   public final boolean isArrowKey()
/*      */   {
/* 1231 */     return (this.mask & 0x4) != 0;
/*      */   }
/*      */ 
/*      */   public final boolean isModifierKey()
/*      */   {
/* 1240 */     return (this.mask & 0x8) != 0;
/*      */   }
/*      */ 
/*      */   public final boolean isLetterKey()
/*      */   {
/* 1249 */     return (this.mask & 0x10) != 0;
/*      */   }
/*      */ 
/*      */   public final boolean isDigitKey()
/*      */   {
/* 1258 */     return (this.mask & 0x20) != 0;
/*      */   }
/*      */ 
/*      */   public final boolean isKeypadKey()
/*      */   {
/* 1267 */     return (this.mask & 0x40) != 0;
/*      */   }
/*      */ 
/*      */   public final boolean isWhitespaceKey()
/*      */   {
/* 1276 */     return (this.mask & 0x80) != 0;
/*      */   }
/*      */ 
/*      */   public final boolean isMediaKey()
/*      */   {
/* 1285 */     return (this.mask & 0x100) != 0;
/*      */   }
/*      */ 
/*      */   public final String getName()
/*      */   {
/* 1293 */     return this.name;
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public String impl_getChar()
/*      */   {
/* 1302 */     return this.ch;
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public int impl_getCode()
/*      */   {
/* 1312 */     return this.code;
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   static KeyCode impl_valueOf(int paramInt)
/*      */   {
/* 1336 */     return (KeyCode)charMap.get(Integer.valueOf(paramInt));
/*      */   }
/*      */ 
/*      */   public static KeyCode getKeyCode(String paramString)
/*      */   {
/* 1346 */     return (KeyCode)nameMap.get(paramString);
/*      */   }
/*      */ 
/*      */   static
/*      */   {
/* 1318 */     charMap = new HashMap(values().length);
/* 1319 */     nameMap = new HashMap(values().length);
/* 1320 */     for (KeyCode localKeyCode : values()) {
/* 1321 */       charMap.put(Integer.valueOf(localKeyCode.code), localKeyCode);
/* 1322 */       nameMap.put(localKeyCode.name, localKeyCode);
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class KeyCodeClass
/*      */   {
/*      */     private static final int FUNCTION = 1;
/*      */     private static final int NAVIGATION = 2;
/*      */     private static final int ARROW = 4;
/*      */     private static final int MODIFIER = 8;
/*      */     private static final int LETTER = 16;
/*      */     private static final int DIGIT = 32;
/*      */     private static final int KEYPAD = 64;
/*      */     private static final int WHITESPACE = 128;
/*      */     private static final int MEDIA = 256;
/*      */   }
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.input.KeyCode
 * JD-Core Version:    0.6.2
 */