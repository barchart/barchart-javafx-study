/*     */ package com.sun.deploy.uitoolkit.impl.fx.ui;
/*     */ 
/*     */ import com.sun.deploy.uitoolkit.impl.fx.ui.resources.ResourceManager;
/*     */ import com.sun.deploy.uitoolkit.ui.ConsoleController;
/*     */ import com.sun.deploy.uitoolkit.ui.ConsoleHelper;
/*     */ import com.sun.deploy.uitoolkit.ui.ConsoleWindow;
/*     */ import com.sun.deploy.util.DeploySysAction;
/*     */ import com.sun.deploy.util.DeploySysRun;
/*     */ import java.io.PrintStream;
/*     */ import java.text.MessageFormat;
/*     */ import javafx.application.Platform;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.event.ActionEvent;
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.geometry.Orientation;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.Parent;
/*     */ import javafx.scene.Scene;
/*     */ import javafx.scene.control.Button;
/*     */ import javafx.scene.control.IndexRange;
/*     */ import javafx.scene.control.Label;
/*     */ import javafx.scene.control.ScrollBar;
/*     */ import javafx.scene.control.ScrollPane;
/*     */ import javafx.scene.control.TextArea;
/*     */ import javafx.scene.input.Clipboard;
/*     */ import javafx.scene.input.ClipboardContent;
/*     */ import javafx.scene.input.KeyEvent;
/*     */ import javafx.scene.layout.BorderPane;
/*     */ import javafx.scene.layout.FlowPane;
/*     */ import javafx.stage.StageStyle;
/*     */ 
/*     */ public final class FXConsole
/*     */   implements ConsoleWindow
/*     */ {
/*  42 */   private final boolean USE_TEXT_AREA = true;
/*     */   private final ConsoleController controller;
/*     */   private FXDialog dialog;
/*     */   private ScrollPane sp;
/*     */   private TextArea textArea;
/*     */   private Label textAreaLabel;
/*     */ 
/*     */   public static FXConsole create(ConsoleController controller)
/*     */     throws Exception
/*     */   {
/*  55 */     return (FXConsole)DeploySysRun.execute(new DeploySysAction()
/*     */     {
/*     */       public Object execute() {
/*  58 */         return new FXConsole(this.val$controller);
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public FXConsole(ConsoleController controller)
/*     */   {
/*  68 */     this.controller = controller;
/*     */ 
/*  70 */     invokeLater(new Runnable() {
/*     */       public void run() {
/*  72 */         FXConsole.this.dialog = new FXDialog(ResourceManager.getMessage("console.caption"), null, false, StageStyle.DECORATED);
/*  73 */         FXConsole.this.dialog.setResizable(true);
/*  74 */         FXConsole.this.dialog.impl_setImportant(false);
/*     */ 
/*  80 */         BorderPane contentPane = new BorderPane();
/*     */ 
/*  82 */         FXConsole.this.dialog.setContentPane(contentPane);
/*  83 */         FXConsole.this.dialog.setWidth(470.0D);
/*  84 */         FXConsole.this.dialog.setHeight(430.0D);
/*     */ 
/*  87 */         FXConsole.this.textArea = new TextArea();
/*  88 */         FXConsole.this.textArea.setEditable(false);
/*  89 */         FXConsole.this.textArea.setWrapText(true);
/*  90 */         FXConsole.this.textArea.getStyleClass().add("multiline-text");
/*  91 */         contentPane.setCenter(FXConsole.this.textArea);
/*     */ 
/* 105 */         EventHandler keyHandler = new EventHandler() {
/*     */           public void handle(KeyEvent ev) {
/* 107 */             String character = ev.getCharacter();
/*     */ 
/* 109 */             if ((character != null) && (character.length() == 1))
/* 110 */               switch (character.charAt(0)) { case 'j':
/* 111 */                 FXConsole.this.dumpJCovData(); break;
/*     */               case 'v':
/* 112 */                 FXConsole.this.dumpThreadStack(); break;
/*     */               case 'p':
/* 113 */                 FXConsole.this.reloadProxyConfig(); break;
/*     */               case 'r':
/* 114 */                 FXConsole.this.reloadPolicyConfig(); break;
/*     */               case 'x':
/* 115 */                 FXConsole.this.clearClassLoaderCache(); break;
/*     */               case 'l':
/* 116 */                 FXConsole.this.showClassLoaderCache(); break;
/*     */               case 'o':
/* 117 */                 FXConsole.this.logging(); break;
/*     */               case 't':
/* 118 */                 FXConsole.this.showThreads(); break;
/*     */               case 's':
/* 119 */                 FXConsole.this.showSystemProperties(); break;
/*     */               case 'h':
/* 120 */                 FXConsole.this.showHelp(); break;
/*     */               case 'm':
/* 121 */                 FXConsole.this.showMemory(); break;
/*     */               case 'c':
/* 122 */                 FXConsole.this.clearConsole(); break;
/*     */               case 'g':
/* 123 */                 FXConsole.this.runGC(); break;
/*     */               case 'f':
/* 124 */                 FXConsole.this.runFinalize(); break;
/*     */               case 'q':
/* 125 */                 FXConsole.this.closeConsole(); break;
/*     */               case '0':
/* 126 */                 FXConsole.this.traceLevel0(); break;
/*     */               case '1':
/* 127 */                 FXConsole.this.traceLevel1(); break;
/*     */               case '2':
/* 128 */                 FXConsole.this.traceLevel2(); break;
/*     */               case '3':
/* 129 */                 FXConsole.this.traceLevel3(); break;
/*     */               case '4':
/* 130 */                 FXConsole.this.traceLevel4(); break;
/*     */               case '5':
/* 131 */                 FXConsole.this.traceLevel5();
/*     */               case '6':
/*     */               case '7':
/*     */               case '8':
/*     */               case '9':
/*     */               case ':':
/*     */               case ';':
/*     */               case '<':
/*     */               case '=':
/*     */               case '>':
/*     */               case '?':
/*     */               case '@':
/*     */               case 'A':
/*     */               case 'B':
/*     */               case 'C':
/*     */               case 'D':
/*     */               case 'E':
/*     */               case 'F':
/*     */               case 'G':
/*     */               case 'H':
/*     */               case 'I':
/*     */               case 'J':
/*     */               case 'K':
/*     */               case 'L':
/*     */               case 'M':
/*     */               case 'N':
/*     */               case 'O':
/*     */               case 'P':
/*     */               case 'Q':
/*     */               case 'R':
/*     */               case 'S':
/*     */               case 'T':
/*     */               case 'U':
/*     */               case 'V':
/*     */               case 'W':
/*     */               case 'X':
/*     */               case 'Y':
/*     */               case 'Z':
/*     */               case '[':
/*     */               case '\\':
/*     */               case ']':
/*     */               case '^':
/*     */               case '_':
/*     */               case '`':
/*     */               case 'a':
/*     */               case 'b':
/*     */               case 'd':
/*     */               case 'e':
/*     */               case 'i':
/*     */               case 'k':
/*     */               case 'n':
/*     */               case 'u':
/*     */               case 'w':
/*     */               }
/*     */           }
/*     */         };
/* 137 */         FXConsole.this.dialog.getScene().setOnKeyTyped(keyHandler);
/*     */ 
/* 139 */         FXConsole.this.textArea.setOnKeyTyped(keyHandler);
/*     */ 
/* 148 */         Button clear = new Button(ResourceManager.getMessage("console.clear"));
/*     */ 
/* 150 */         Button copy = new Button(ResourceManager.getMessage("console.copy"));
/*     */ 
/* 152 */         Button close = new Button(ResourceManager.getMessage("console.close"));
/*     */ 
/* 154 */         FlowPane panel = new FlowPane();
/* 155 */         panel.getStyleClass().add("button-bar");
/* 156 */         panel.setId("console-dialog-button-bar");
/*     */ 
/* 158 */         panel.getChildren().add(clear);
/* 159 */         panel.getChildren().add(new Label("    "));
/* 160 */         panel.getChildren().add(copy);
/* 161 */         panel.getChildren().add(new Label("    "));
/* 162 */         panel.getChildren().add(close);
/*     */ 
/* 164 */         contentPane.setBottom(panel);
/*     */ 
/* 179 */         clear.setOnAction(new EventHandler() {
/*     */           public void handle(ActionEvent e) {
/* 181 */             FXConsole.this.clearConsole();
/*     */           }
/*     */         });
/* 185 */         copy.setOnAction(new EventHandler() {
/*     */           public void handle(ActionEvent e) {
/* 187 */             FXConsole.this.copyConsole();
/*     */           }
/*     */         });
/* 191 */         close.setOnAction(new EventHandler() {
/*     */           public void handle(ActionEvent e) {
/* 193 */             FXConsole.this.closeConsole();
/*     */           }
/*     */         });
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   private void dumpJCovData()
/*     */   {
/* 206 */     if (this.controller.isJCovSupported())
/* 207 */       if (this.controller.dumpJCovData())
/* 208 */         System.out.println(ResourceManager.getMessage("console.jcov.info"));
/*     */       else
/* 210 */         System.out.println(ResourceManager.getMessage("console.jcov.error"));
/*     */   }
/*     */ 
/*     */   private void dumpThreadStack()
/*     */   {
/* 217 */     if (this.controller.isDumpStackSupported()) {
/* 218 */       System.out.print(ResourceManager.getMessage("console.dump.stack"));
/* 219 */       System.out.print(ResourceManager.getMessage("console.menu.text.top"));
/* 220 */       ConsoleHelper.dumpAllStacks(this.controller);
/* 221 */       System.out.print(ResourceManager.getMessage("console.menu.text.tail"));
/* 222 */       System.out.print(ResourceManager.getMessage("console.done"));
/*     */     }
/*     */   }
/*     */ 
/*     */   private void showThreads() {
/* 227 */     System.out.print(ResourceManager.getMessage("console.dump.thread"));
/*     */ 
/* 229 */     ThreadGroup tg = this.controller.getMainThreadGroup();
/* 230 */     ConsoleHelper.dumpThreadGroup(tg);
/*     */ 
/* 232 */     System.out.println(ResourceManager.getMessage("console.done"));
/*     */   }
/*     */ 
/*     */   private void reloadPolicyConfig() {
/* 236 */     if (this.controller.isSecurityPolicyReloadSupported()) {
/* 237 */       System.out.print(ResourceManager.getMessage("console.reload.policy"));
/* 238 */       this.controller.reloadSecurityPolicy();
/* 239 */       System.out.println(ResourceManager.getMessage("console.completed"));
/*     */     }
/*     */   }
/*     */ 
/*     */   private void reloadProxyConfig() {
/* 244 */     if (this.controller.isProxyConfigReloadSupported()) {
/* 245 */       System.out.println(ResourceManager.getMessage("console.reload.proxy"));
/* 246 */       this.controller.reloadProxyConfig();
/* 247 */       System.out.println(ResourceManager.getMessage("console.done"));
/*     */     }
/*     */   }
/*     */ 
/*     */   private void showSystemProperties() {
/* 252 */     ConsoleHelper.displaySystemProperties();
/*     */   }
/*     */ 
/*     */   private void showHelp() {
/* 256 */     ConsoleHelper.displayHelp(this.controller, this);
/*     */   }
/*     */ 
/*     */   private void showClassLoaderCache() {
/* 260 */     if (this.controller.isDumpClassLoaderSupported())
/* 261 */       System.out.println(this.controller.dumpClassLoaders());
/*     */   }
/*     */ 
/*     */   private void clearClassLoaderCache()
/*     */   {
/* 266 */     if (this.controller.isClearClassLoaderSupported()) {
/* 267 */       this.controller.clearClassLoaders();
/* 268 */       System.out.println(ResourceManager.getMessage("console.clear.classloader"));
/*     */     }
/*     */   }
/*     */ 
/*     */   private void clearConsole()
/*     */   {
/* 276 */     clear();
/*     */   }
/*     */ 
/*     */   private void copyConsole()
/*     */   {
/* 281 */     int selectionStart = this.textArea.getSelection().getStart();
/* 282 */     int selectionEnd = this.textArea.getSelection().getEnd();
/*     */ 
/* 284 */     if (selectionEnd - selectionStart <= 0) {
/* 285 */       ClipboardContent content = new ClipboardContent();
/* 286 */       content.putString(this.textArea.getText());
/* 287 */       Clipboard.getSystemClipboard().setContent(content);
/*     */     } else {
/* 289 */       this.textArea.copy();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void closeConsole()
/*     */   {
/* 303 */     if (this.controller.isIconifiedOnClose())
/*     */     {
/* 306 */       this.dialog.setIconified(true);
/*     */     }
/* 308 */     else this.dialog.hide();
/*     */ 
/* 310 */     this.controller.notifyConsoleClosed();
/*     */   }
/*     */ 
/*     */   private void showMemory() {
/* 314 */     long freeMemory = Runtime.getRuntime().freeMemory() / 1024L;
/* 315 */     long totalMemory = Runtime.getRuntime().totalMemory() / 1024L;
/* 316 */     long percentFree = ()(100.0D / (totalMemory / freeMemory));
/*     */ 
/* 318 */     MessageFormat mf = new MessageFormat(ResourceManager.getMessage("console.memory"));
/*     */ 
/* 320 */     Object[] args = { new Long(totalMemory), new Long(freeMemory), new Long(percentFree) };
/*     */ 
/* 326 */     System.out.print(mf.format(args));
/* 327 */     System.out.println(ResourceManager.getMessage("console.completed"));
/*     */   }
/*     */ 
/*     */   private void runFinalize() {
/* 331 */     System.out.print(ResourceManager.getMessage("console.finalize"));
/* 332 */     System.runFinalization();
/* 333 */     System.out.println(ResourceManager.getMessage("console.completed"));
/*     */ 
/* 336 */     showMemory();
/*     */   }
/*     */ 
/*     */   private void runGC() {
/* 340 */     System.out.print(ResourceManager.getMessage("console.gc"));
/* 341 */     System.gc();
/* 342 */     System.out.println(ResourceManager.getMessage("console.completed"));
/*     */ 
/* 344 */     showMemory();
/*     */   }
/*     */ 
/*     */   private void traceLevel0() {
/* 348 */     ConsoleHelper.setTraceLevel(0);
/*     */   }
/*     */ 
/*     */   private void traceLevel1() {
/* 352 */     ConsoleHelper.setTraceLevel(1);
/*     */   }
/*     */ 
/*     */   private void traceLevel2() {
/* 356 */     ConsoleHelper.setTraceLevel(2);
/*     */   }
/*     */ 
/*     */   private void traceLevel3() {
/* 360 */     ConsoleHelper.setTraceLevel(3);
/*     */   }
/*     */ 
/*     */   private void traceLevel4() {
/* 364 */     ConsoleHelper.setTraceLevel(4);
/*     */   }
/*     */ 
/*     */   private void traceLevel5() {
/* 368 */     ConsoleHelper.setTraceLevel(5);
/*     */   }
/*     */ 
/*     */   private void logging() {
/* 372 */     if (this.controller.isLoggingSupported())
/* 373 */       System.out.println(ResourceManager.getMessage("console.log") + this.controller.toggleLogging() + ResourceManager.getMessage("console.completed"));
/*     */   }
/*     */ 
/*     */   public void clear()
/*     */   {
/* 386 */     invokeLater(new Runnable()
/*     */     {
/*     */       public void run() {
/* 389 */         FXConsole.this.textArea.setText("");
/*     */ 
/* 394 */         ConsoleHelper.displayVersion(FXConsole.this.controller, FXConsole.this);
/* 395 */         FXConsole.this.append("\n");
/* 396 */         ConsoleHelper.displayHelp(FXConsole.this.controller, FXConsole.this);
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   private static void invokeLater(Runnable r) {
/* 402 */     Platform.runLater(r);
/*     */   }
/*     */ 
/*     */   public void append(final String text)
/*     */   {
/* 413 */     invokeLater(new Runnable()
/*     */     {
/*     */       public void run()
/*     */       {
/* 417 */         ScrollBar vsb = FXConsole.this.getVerticalScrollBar();
/* 418 */         boolean scrollToEnd = (vsb == null) || (!vsb.isVisible()) || (vsb.getValue() == vsb.getMax());
/*     */ 
/* 421 */         int len = FXConsole.this.textArea.getText().length();
/* 422 */         if (len > 1)
/* 423 */           FXConsole.this.textArea.insertText(len, text);
/*     */         else {
/* 425 */           FXConsole.this.textArea.setText(text);
/*     */         }
/*     */ 
/* 433 */         if (scrollToEnd)
/* 434 */           FXConsole.this.setScrollPosition();
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   private void setScrollPosition()
/*     */   {
/* 446 */     ScrollBar vsb = getVerticalScrollBar();
/* 447 */     if (vsb != null) {
/* 448 */       double max = vsb.getMax();
/* 449 */       double value = vsb.getValue();
/* 450 */       if (value < max)
/* 451 */         vsb.setValue(max);
/*     */     }
/*     */   }
/*     */ 
/*     */   private ScrollBar getVerticalScrollBar()
/*     */   {
/* 459 */     return findScrollBar(this.textArea, true);
/*     */   }
/*     */ 
/*     */   private ScrollBar findScrollBar(Parent parent, boolean vertical)
/*     */   {
/* 466 */     if ((parent instanceof ScrollBar)) {
/* 467 */       ScrollBar sb = (ScrollBar)parent;
/* 468 */       if ((sb.getOrientation() == Orientation.VERTICAL) == vertical) {
/* 469 */         return (ScrollBar)parent;
/*     */       }
/* 471 */       return null;
/*     */     }
/*     */ 
/* 475 */     for (Node child : parent.getChildrenUnmodifiable()) {
/* 476 */       if ((child instanceof Parent)) {
/* 477 */         ScrollBar sb = findScrollBar((Parent)child, vertical);
/* 478 */         if (sb != null) {
/* 479 */           return sb;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 484 */     return null;
/*     */   }
/*     */ 
/*     */   public void setVisible(final boolean visible)
/*     */   {
/* 489 */     invokeLater(new Runnable() {
/*     */       public void run() {
/* 491 */         FXConsole.this.setVisibleImpl(visible);
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   private void setVisibleImpl(boolean visible)
/*     */   {
/* 501 */     if (this.controller.isIconifiedOnClose())
/*     */     {
/* 505 */       this.dialog.setIconified(!visible);
/* 506 */       this.dialog.show();
/*     */     }
/*     */     else
/*     */     {
/* 513 */       if (isVisible() != visible) {
/* 514 */         if (visible)
/* 515 */           this.dialog.show();
/*     */         else {
/* 517 */           this.dialog.hide();
/*     */         }
/*     */       }
/*     */ 
/* 521 */       if (visible)
/* 522 */         this.dialog.toFront();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setTitle(final String string)
/*     */   {
/* 528 */     invokeLater(new Runnable() {
/*     */       public void run() {
/* 530 */         FXConsole.this.setTitleImpl(string);
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   private void setTitleImpl(String string) {
/* 536 */     this.dialog.setTitle(string);
/*     */   }
/*     */ 
/*     */   public String getRecentLog() {
/* 540 */     return "Not supported yet.";
/*     */   }
/*     */ 
/*     */   public boolean isVisible()
/*     */   {
/* 547 */     if (this.controller.isIconifiedOnClose())
/*     */     {
/* 550 */       return !this.dialog.isIconified();
/*     */     }
/*     */ 
/* 554 */     return this.dialog.isShowing();
/*     */   }
/*     */ 
/*     */   public void dispose()
/*     */   {
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.deploy.uitoolkit.impl.fx.ui.FXConsole
 * JD-Core Version:    0.6.2
 */