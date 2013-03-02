/*     */ package com.sun.javafx.scene.control.behavior;
/*     */ 
/*     */ import com.sun.javafx.PlatformUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javafx.scene.input.KeyCode;
/*     */ import javafx.scene.input.KeyEvent;
/*     */ 
/*     */ public class TextInputControlBindings
/*     */ {
/*  38 */   protected static final List<KeyBinding> BINDINGS = new ArrayList();
/*     */ 
/*     */   static {
/*  41 */     BINDINGS.add(new KeyBinding(KeyCode.RIGHT, KeyEvent.KEY_PRESSED, "Forward"));
/*  42 */     BINDINGS.add(new KeyBinding(KeyCode.KP_RIGHT, KeyEvent.KEY_PRESSED, "Forward"));
/*  43 */     BINDINGS.add(new KeyBinding(KeyCode.LEFT, KeyEvent.KEY_PRESSED, "Backward"));
/*  44 */     BINDINGS.add(new KeyBinding(KeyCode.KP_LEFT, KeyEvent.KEY_PRESSED, "Backward"));
/*  45 */     BINDINGS.add(new KeyBinding(KeyCode.UP, KeyEvent.KEY_PRESSED, "Home"));
/*  46 */     BINDINGS.add(new KeyBinding(KeyCode.KP_UP, KeyEvent.KEY_PRESSED, "Home"));
/*  47 */     BINDINGS.add(new KeyBinding(KeyCode.HOME, KeyEvent.KEY_PRESSED, "Home"));
/*  48 */     BINDINGS.add(new KeyBinding(KeyCode.DOWN, KeyEvent.KEY_PRESSED, "End"));
/*  49 */     BINDINGS.add(new KeyBinding(KeyCode.KP_DOWN, KeyEvent.KEY_PRESSED, "End"));
/*  50 */     BINDINGS.add(new KeyBinding(KeyCode.END, KeyEvent.KEY_PRESSED, "End"));
/*  51 */     BINDINGS.add(new KeyBinding(KeyCode.ENTER, KeyEvent.KEY_PRESSED, "Fire"));
/*     */ 
/*  53 */     BINDINGS.add(new KeyBinding(KeyCode.BACK_SPACE, KeyEvent.KEY_PRESSED, "DeletePreviousChar"));
/*  54 */     BINDINGS.add(new KeyBinding(KeyCode.DELETE, KeyEvent.KEY_PRESSED, "DeleteNextChar"));
/*     */ 
/*  56 */     BINDINGS.add(new KeyBinding(KeyCode.CUT, KeyEvent.KEY_PRESSED, "Cut"));
/*  57 */     BINDINGS.add(new KeyBinding(KeyCode.DELETE, KeyEvent.KEY_PRESSED, "Cut").shift());
/*  58 */     BINDINGS.add(new KeyBinding(KeyCode.COPY, KeyEvent.KEY_PRESSED, "Copy"));
/*  59 */     BINDINGS.add(new KeyBinding(KeyCode.PASTE, KeyEvent.KEY_PRESSED, "Paste"));
/*  60 */     BINDINGS.add(new KeyBinding(KeyCode.INSERT, KeyEvent.KEY_PRESSED, "Paste").shift());
/*     */ 
/*  62 */     BINDINGS.add(new KeyBinding(KeyCode.RIGHT, KeyEvent.KEY_PRESSED, "SelectForward").shift());
/*  63 */     BINDINGS.add(new KeyBinding(KeyCode.KP_RIGHT, KeyEvent.KEY_PRESSED, "SelectForward").shift());
/*  64 */     BINDINGS.add(new KeyBinding(KeyCode.LEFT, KeyEvent.KEY_PRESSED, "SelectBackward").shift());
/*  65 */     BINDINGS.add(new KeyBinding(KeyCode.KP_LEFT, KeyEvent.KEY_PRESSED, "SelectBackward").shift());
/*  66 */     BINDINGS.add(new KeyBinding(KeyCode.UP, KeyEvent.KEY_PRESSED, "SelectHome").shift());
/*  67 */     BINDINGS.add(new KeyBinding(KeyCode.KP_UP, KeyEvent.KEY_PRESSED, "SelectHome").shift());
/*  68 */     BINDINGS.add(new KeyBinding(KeyCode.DOWN, KeyEvent.KEY_PRESSED, "SelectEnd").shift());
/*  69 */     BINDINGS.add(new KeyBinding(KeyCode.KP_DOWN, KeyEvent.KEY_PRESSED, "SelectEnd").shift());
/*     */ 
/*  71 */     BINDINGS.add(new KeyBinding(KeyCode.BACK_SPACE, KeyEvent.KEY_PRESSED, "DeletePreviousChar").shift());
/*  72 */     BINDINGS.add(new KeyBinding(KeyCode.DELETE, KeyEvent.KEY_PRESSED, "DeleteNextChar").shift());
/*     */ 
/*  75 */     if (PlatformUtil.isMac()) {
/*  76 */       BINDINGS.add(new KeyBinding(KeyCode.HOME, KeyEvent.KEY_PRESSED, "SelectHomeExtend").shift());
/*  77 */       BINDINGS.add(new KeyBinding(KeyCode.END, KeyEvent.KEY_PRESSED, "SelectEndExtend").shift());
/*     */ 
/*  79 */       BINDINGS.add(new KeyBinding(KeyCode.HOME, KeyEvent.KEY_PRESSED, "Home").meta());
/*  80 */       BINDINGS.add(new KeyBinding(KeyCode.END, KeyEvent.KEY_PRESSED, "End").meta());
/*  81 */       BINDINGS.add(new KeyBinding(KeyCode.LEFT, KeyEvent.KEY_PRESSED, "Home").meta());
/*  82 */       BINDINGS.add(new KeyBinding(KeyCode.KP_LEFT, KeyEvent.KEY_PRESSED, "Home").meta());
/*  83 */       BINDINGS.add(new KeyBinding(KeyCode.RIGHT, KeyEvent.KEY_PRESSED, "End").meta());
/*  84 */       BINDINGS.add(new KeyBinding(KeyCode.KP_RIGHT, KeyEvent.KEY_PRESSED, "End").meta());
/*  85 */       BINDINGS.add(new KeyBinding(KeyCode.LEFT, KeyEvent.KEY_PRESSED, "PreviousWord").alt());
/*  86 */       BINDINGS.add(new KeyBinding(KeyCode.KP_LEFT, KeyEvent.KEY_PRESSED, "PreviousWord").alt());
/*  87 */       BINDINGS.add(new KeyBinding(KeyCode.RIGHT, KeyEvent.KEY_PRESSED, "NextWord").alt());
/*  88 */       BINDINGS.add(new KeyBinding(KeyCode.KP_RIGHT, KeyEvent.KEY_PRESSED, "NextWord").alt());
/*  89 */       BINDINGS.add(new KeyBinding(KeyCode.DELETE, KeyEvent.KEY_PRESSED, "DeleteNextWord").meta());
/*  90 */       BINDINGS.add(new KeyBinding(KeyCode.BACK_SPACE, KeyEvent.KEY_PRESSED, "DeletePreviousWord").meta());
/*  91 */       BINDINGS.add(new KeyBinding(KeyCode.X, KeyEvent.KEY_PRESSED, "Cut").meta());
/*  92 */       BINDINGS.add(new KeyBinding(KeyCode.C, KeyEvent.KEY_PRESSED, "Copy").meta());
/*  93 */       BINDINGS.add(new KeyBinding(KeyCode.INSERT, KeyEvent.KEY_PRESSED, "Copy").meta());
/*  94 */       BINDINGS.add(new KeyBinding(KeyCode.V, KeyEvent.KEY_PRESSED, "Paste").meta());
/*  95 */       BINDINGS.add(new KeyBinding(KeyCode.HOME, KeyEvent.KEY_PRESSED, "SelectHome").shift().meta());
/*  96 */       BINDINGS.add(new KeyBinding(KeyCode.END, KeyEvent.KEY_PRESSED, "SelectEnd").shift().meta());
/*  97 */       BINDINGS.add(new KeyBinding(KeyCode.LEFT, KeyEvent.KEY_PRESSED, "SelectHomeExtend").shift().meta());
/*  98 */       BINDINGS.add(new KeyBinding(KeyCode.KP_LEFT, KeyEvent.KEY_PRESSED, "SelectHomeExtend").shift().meta());
/*  99 */       BINDINGS.add(new KeyBinding(KeyCode.RIGHT, KeyEvent.KEY_PRESSED, "SelectEndExtend").shift().meta());
/* 100 */       BINDINGS.add(new KeyBinding(KeyCode.KP_RIGHT, KeyEvent.KEY_PRESSED, "SelectEndExtend").shift().meta());
/* 101 */       BINDINGS.add(new KeyBinding(KeyCode.A, KeyEvent.KEY_PRESSED, "SelectAll").meta());
/* 102 */       BINDINGS.add(new KeyBinding(KeyCode.LEFT, KeyEvent.KEY_PRESSED, "SelectPreviousWord").shift().alt());
/* 103 */       BINDINGS.add(new KeyBinding(KeyCode.KP_LEFT, KeyEvent.KEY_PRESSED, "SelectPreviousWord").shift().alt());
/* 104 */       BINDINGS.add(new KeyBinding(KeyCode.RIGHT, KeyEvent.KEY_PRESSED, "SelectNextWord").shift().alt());
/* 105 */       BINDINGS.add(new KeyBinding(KeyCode.KP_RIGHT, KeyEvent.KEY_PRESSED, "SelectNextWord").shift().alt());
/* 106 */       BINDINGS.add(new KeyBinding(KeyCode.Z, KeyEvent.KEY_PRESSED, "Undo").meta());
/* 107 */       BINDINGS.add(new KeyBinding(KeyCode.Z, KeyEvent.KEY_PRESSED, "Redo").shift().meta());
/*     */     } else {
/* 109 */       BINDINGS.add(new KeyBinding(KeyCode.HOME, KeyEvent.KEY_PRESSED, "SelectHome").shift());
/* 110 */       BINDINGS.add(new KeyBinding(KeyCode.END, KeyEvent.KEY_PRESSED, "SelectEnd").shift());
/*     */ 
/* 112 */       BINDINGS.add(new KeyBinding(KeyCode.HOME, KeyEvent.KEY_PRESSED, "Home").ctrl());
/* 113 */       BINDINGS.add(new KeyBinding(KeyCode.END, KeyEvent.KEY_PRESSED, "End").ctrl());
/* 114 */       BINDINGS.add(new KeyBinding(KeyCode.LEFT, KeyEvent.KEY_PRESSED, "PreviousWord").ctrl());
/* 115 */       BINDINGS.add(new KeyBinding(KeyCode.KP_LEFT, KeyEvent.KEY_PRESSED, "PreviousWord").ctrl());
/* 116 */       BINDINGS.add(new KeyBinding(KeyCode.RIGHT, KeyEvent.KEY_PRESSED, "NextWord").ctrl());
/* 117 */       BINDINGS.add(new KeyBinding(KeyCode.KP_RIGHT, KeyEvent.KEY_PRESSED, "NextWord").ctrl());
/* 118 */       BINDINGS.add(new KeyBinding(KeyCode.H, KeyEvent.KEY_PRESSED, "DeletePreviousChar").ctrl());
/* 119 */       BINDINGS.add(new KeyBinding(KeyCode.DELETE, KeyEvent.KEY_PRESSED, "DeleteNextWord").ctrl());
/* 120 */       BINDINGS.add(new KeyBinding(KeyCode.BACK_SPACE, KeyEvent.KEY_PRESSED, "DeletePreviousWord").ctrl());
/* 121 */       BINDINGS.add(new KeyBinding(KeyCode.X, KeyEvent.KEY_PRESSED, "Cut").ctrl());
/* 122 */       BINDINGS.add(new KeyBinding(KeyCode.C, KeyEvent.KEY_PRESSED, "Copy").ctrl());
/* 123 */       BINDINGS.add(new KeyBinding(KeyCode.INSERT, KeyEvent.KEY_PRESSED, "Copy").ctrl());
/* 124 */       BINDINGS.add(new KeyBinding(KeyCode.V, KeyEvent.KEY_PRESSED, "Paste").ctrl());
/* 125 */       BINDINGS.add(new KeyBinding(KeyCode.HOME, KeyEvent.KEY_PRESSED, "SelectHome").ctrl().shift());
/* 126 */       BINDINGS.add(new KeyBinding(KeyCode.END, KeyEvent.KEY_PRESSED, "SelectEnd").ctrl().shift());
/* 127 */       BINDINGS.add(new KeyBinding(KeyCode.LEFT, KeyEvent.KEY_PRESSED, "SelectPreviousWord").ctrl().shift());
/* 128 */       BINDINGS.add(new KeyBinding(KeyCode.KP_LEFT, KeyEvent.KEY_PRESSED, "SelectPreviousWord").ctrl().shift());
/* 129 */       BINDINGS.add(new KeyBinding(KeyCode.RIGHT, KeyEvent.KEY_PRESSED, "SelectNextWord").ctrl().shift());
/* 130 */       BINDINGS.add(new KeyBinding(KeyCode.KP_RIGHT, KeyEvent.KEY_PRESSED, "SelectNextWord").ctrl().shift());
/* 131 */       BINDINGS.add(new KeyBinding(KeyCode.A, KeyEvent.KEY_PRESSED, "SelectAll").ctrl());
/* 132 */       BINDINGS.add(new KeyBinding(KeyCode.BACK_SLASH, KeyEvent.KEY_PRESSED, "Unselect").ctrl());
/* 133 */       if (PlatformUtil.isLinux()) {
/* 134 */         BINDINGS.add(new KeyBinding(KeyCode.Z, KeyEvent.KEY_PRESSED, "Undo").ctrl());
/* 135 */         BINDINGS.add(new KeyBinding(KeyCode.Z, KeyEvent.KEY_PRESSED, "Redo").ctrl().shift());
/*     */       } else {
/* 137 */         BINDINGS.add(new KeyBinding(KeyCode.Z, KeyEvent.KEY_PRESSED, "Undo").ctrl());
/* 138 */         BINDINGS.add(new KeyBinding(KeyCode.Y, KeyEvent.KEY_PRESSED, "Redo").ctrl());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 143 */     BINDINGS.add(new KeyBinding(null, KeyEvent.KEY_TYPED, "InputCharacter").alt(OptionalBoolean.ANY).shift(OptionalBoolean.ANY).ctrl(OptionalBoolean.ANY).meta(OptionalBoolean.ANY));
/*     */ 
/* 149 */     BINDINGS.add(new KeyBinding(KeyCode.TAB, "TraverseNext"));
/* 150 */     BINDINGS.add(new KeyBinding(KeyCode.TAB, "TraversePrevious").shift());
/*     */ 
/* 152 */     BINDINGS.add(new KeyBinding(KeyCode.ESCAPE, "ToParent"));
/* 153 */     BINDINGS.add(new KeyBinding(KeyCode.F10, "ToParent"));
/*     */ 
/* 156 */     if (PlatformUtil.isEmbedded())
/* 157 */       BINDINGS.add(new KeyBinding(KeyCode.DIGIT9, "UseVK").ctrl().shift());
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.behavior.TextInputControlBindings
 * JD-Core Version:    0.6.2
 */