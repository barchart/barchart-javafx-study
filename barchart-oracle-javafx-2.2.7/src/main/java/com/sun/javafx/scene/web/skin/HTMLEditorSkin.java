/*      */ package com.sun.javafx.scene.web.skin;
/*      */ 
/*      */ import com.sun.javafx.PlatformUtil;
/*      */ import com.sun.javafx.scene.control.skin.FXVK;
/*      */ import com.sun.javafx.scene.control.skin.SkinBase;
/*      */ import com.sun.javafx.scene.traversal.TraversalEngine;
/*      */ import com.sun.javafx.scene.traversal.TraverseListener;
/*      */ import com.sun.javafx.scene.web.behavior.HTMLEditorBehavior;
/*      */ import com.sun.webpane.platform.WebPage;
/*      */ import com.sun.webpane.platform.event.WCFocusEvent;
/*      */ import com.sun.webpane.sg.Accessor;
/*      */ import java.net.URL;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.HashMap;
/*      */ import java.util.Map;
/*      */ import java.util.ResourceBundle;
/*      */ import javafx.application.Platform;
/*      */ import javafx.beans.property.BooleanProperty;
/*      */ import javafx.beans.property.ObjectProperty;
/*      */ import javafx.beans.property.ReadOnlyBooleanProperty;
/*      */ import javafx.beans.property.ReadOnlyDoubleProperty;
/*      */ import javafx.beans.value.ChangeListener;
/*      */ import javafx.beans.value.ObservableValue;
/*      */ import javafx.collections.FXCollections;
/*      */ import javafx.collections.ObservableList;
/*      */ import javafx.concurrent.Worker;
/*      */ import javafx.event.ActionEvent;
/*      */ import javafx.event.EventHandler;
/*      */ import javafx.geometry.Bounds;
/*      */ import javafx.scene.Group;
/*      */ import javafx.scene.Node;
/*      */ import javafx.scene.Scene;
/*      */ import javafx.scene.control.Button;
/*      */ import javafx.scene.control.ComboBox;
/*      */ import javafx.scene.control.ListCell;
/*      */ import javafx.scene.control.ListView;
/*      */ import javafx.scene.control.Separator;
/*      */ import javafx.scene.control.TextInputControl;
/*      */ import javafx.scene.control.Toggle;
/*      */ import javafx.scene.control.ToggleButton;
/*      */ import javafx.scene.control.ToggleGroup;
/*      */ import javafx.scene.control.ToolBar;
/*      */ import javafx.scene.control.Tooltip;
/*      */ import javafx.scene.image.Image;
/*      */ import javafx.scene.image.ImageView;
/*      */ import javafx.scene.input.KeyCode;
/*      */ import javafx.scene.input.KeyEvent;
/*      */ import javafx.scene.input.MouseEvent;
/*      */ import javafx.scene.layout.ColumnConstraints;
/*      */ import javafx.scene.layout.GridPane;
/*      */ import javafx.scene.layout.Priority;
/*      */ import javafx.scene.paint.Color;
/*      */ import javafx.scene.paint.Paint;
/*      */ import javafx.scene.shape.Rectangle;
/*      */ import javafx.scene.text.Font;
/*      */ import javafx.scene.web.HTMLEditor;
/*      */ import javafx.scene.web.WebEngine;
/*      */ import javafx.scene.web.WebView;
/*      */ import javafx.stage.Window;
/*      */ import javafx.util.Callback;
/*      */ import org.w3c.dom.NodeList;
/*      */ import org.w3c.dom.html.HTMLDocument;
/*      */ import org.w3c.dom.html.HTMLElement;
/*      */ 
/*      */ public class HTMLEditorSkin extends SkinBase<HTMLEditor, HTMLEditorBehavior>
/*      */   implements TraverseListener
/*      */ {
/*      */   private GridPane gridPane;
/*      */   private ToolBar toolbar1;
/*      */   private ToolBar toolbar2;
/*      */   private Button cutButton;
/*      */   private Button copyButton;
/*      */   private Button pasteButton;
/*      */   private Button insertHorizontalRuleButton;
/*      */   private ToggleGroup alignmentToggleGroup;
/*      */   private ToggleButton alignLeftButton;
/*      */   private ToggleButton alignCenterButton;
/*      */   private ToggleButton alignRightButton;
/*      */   private ToggleButton alignJustifyButton;
/*      */   private ToggleButton bulletsButton;
/*      */   private ToggleButton numbersButton;
/*      */   private Button indentButton;
/*      */   private Button outdentButton;
/*      */   private ComboBox formatComboBox;
/*      */   private Map<String, String> formatStyleMap;
/*      */   private Map<String, String> styleFormatMap;
/*      */   private ComboBox fontFamilyComboBox;
/*      */   private ComboBox fontSizeComboBox;
/*      */   private Map<String, String> fontSizeMap;
/*      */   private Map<String, String> sizeFontMap;
/*      */   private ToggleButton boldButton;
/*      */   private ToggleButton italicButton;
/*      */   private ToggleButton underlineButton;
/*      */   private ToggleButton strikethroughButton;
/*      */   private PopupButton fgColorButton;
/*      */   private PopupButton bgColorButton;
/*      */   private ColorPicker fgColorPicker;
/*      */   private ColorPicker bgColorPicker;
/*      */   private Rectangle fgColorRect;
/*      */   private Rectangle bgColorRect;
/*      */   private WebView webView;
/*      */   private WebPage webPage;
/*      */   public static final String CUT_COMMAND = "cut";
/*      */   public static final String COPY_COMMAND = "copy";
/*      */   public static final String PASTE_COMMAND = "paste";
/*      */   public static final String UNDO_COMMAND = "undo";
/*      */   public static final String REDO_COMMAND = "redo";
/*      */   public static final String INSERT_HORIZONTAL_RULE_COMMAND = "inserthorizontalrule";
/*      */   public static final String ALIGN_LEFT_COMMAND = "justifyleft";
/*      */   public static final String ALIGN_CENTER_COMMAND = "justifycenter";
/*      */   public static final String ALIGN_RIGHT_COMMAND = "justifyright";
/*      */   public static final String ALIGN_JUSTIFY_COMMAND = "justifyfull";
/*      */   public static final String BULLETS_COMMAND = "insertUnorderedList";
/*      */   public static final String NUMBERS_COMMAND = "insertOrderedList";
/*      */   public static final String INDENT_COMMAND = "indent";
/*      */   public static final String OUTDENT_COMMAND = "outdent";
/*      */   public static final String FORMAT_COMMAND = "formatblock";
/*      */   public static final String FONT_FAMILY_COMMAND = "fontname";
/*      */   public static final String FONT_SIZE_COMMAND = "fontsize";
/*      */   public static final String BOLD_COMMAND = "bold";
/*      */   public static final String ITALIC_COMMAND = "italic";
/*      */   public static final String UNDERLINE_COMMAND = "underline";
/*      */   public static final String STRIKETHROUGH_COMMAND = "strikethrough";
/*      */   public static final String FOREGROUND_COLOR_COMMAND = "forecolor";
/*      */   public static final String BACKGROUND_COLOR_COMMAND = "backcolor";
/*  154 */   private static final Color DEFAULT_BG_COLOR = Color.WHITE;
/*  155 */   private static final Color DEFAULT_FG_COLOR = Color.BLACK;
/*      */   private static final String FORMAT_PARAGRAPH = "<p>";
/*      */   private static final String FORMAT_HEADING_1 = "<h1>";
/*      */   private static final String FORMAT_HEADING_2 = "<h2>";
/*      */   private static final String FORMAT_HEADING_3 = "<h3>";
/*      */   private static final String FORMAT_HEADING_4 = "<h4>";
/*      */   private static final String FORMAT_HEADING_5 = "<h5>";
/*      */   private static final String FORMAT_HEADING_6 = "<h6>";
/*      */   private static final String SIZE_XX_SMALL = "1";
/*      */   private static final String SIZE_X_SMALL = "2";
/*      */   private static final String SIZE_SMALL = "3";
/*      */   private static final String SIZE_MEDIUM = "4";
/*      */   private static final String SIZE_LARGE = "5";
/*      */   private static final String SIZE_X_LARGE = "6";
/*      */   private static final String SIZE_XX_LARGE = "7";
/*      */   private static final String INSERT_NEW_LINE_COMMAND = "insertnewline";
/*      */   private static final String INSERT_TAB_COMMAND = "inserttab";
/*  177 */   private static final String[][] DEFAULT_FORMAT_MAPPINGS = { { "<p>", "", "3" }, { "<h1>", "bold", "6" }, { "<h2>", "bold", "5" }, { "<h3>", "bold", "4" }, { "<h4>", "bold", "3" }, { "<h5>", "bold", "2" }, { "<h6>", "bold", "1" } };
/*      */ 
/*  188 */   private static final String[] DEFAULT_WINDOWS_7_MAPPINGS = { "Windows 7", "Segoe UI", "12px", "", "120" };
/*      */ 
/*  191 */   private static final String[][] DEFAULT_OS_MAPPINGS = { { "Windows XP", "Tahoma", "12px", "", "96" }, { "Windows Vista", "Segoe UI", "12px", "", "96" }, DEFAULT_WINDOWS_7_MAPPINGS, { "Mac OS X", "Lucida Grande", "12px", "", "72" }, { "Linux", "Lucida Sans", "12px", "", "96" } };
/*      */ 
/*  199 */   private static final String DEFAULT_OS_FONT = getOSMappings()[1];
/*      */   private TraversalEngine engine;
/*  214 */   private boolean resetToolbarState = false;
/*  215 */   private boolean formatChanged = false;
/*  216 */   private String cachedHTMLText = "<html><body></body></html>";
/*      */   ResourceBundle resources;
/*      */   private static final int FORMAT_MENUBUTTON_WIDTH = 100;
/*      */   private static final int FONT_FAMILY_MENUBUTTON_WIDTH = 150;
/*      */   private static final int FONT_FAMILY_MENU_WIDTH = 100;
/*      */   private static final int FONT_FAMILY_MENUBUTTON_HEIGHT = 21;
/*      */   private static final int FONT_SIZE_MENUBUTTON_WIDTH = 80;
/*      */ 
/*      */   private static String[] getOSMappings()
/*      */   {
/*  202 */     String str = System.getProperty("os.name");
/*  203 */     for (int i = 0; i < DEFAULT_OS_MAPPINGS.length; i++) {
/*  204 */       if (str.equals(DEFAULT_OS_MAPPINGS[i][0])) {
/*  205 */         return DEFAULT_OS_MAPPINGS[i];
/*      */       }
/*      */     }
/*      */ 
/*  209 */     return DEFAULT_WINDOWS_7_MAPPINGS;
/*      */   }
/*      */ 
/*      */   public HTMLEditorSkin(HTMLEditor paramHTMLEditor)
/*      */   {
/*  219 */     super(paramHTMLEditor, new HTMLEditorBehavior(paramHTMLEditor));
/*      */ 
/*  221 */     getChildren().clear();
/*      */ 
/*  223 */     this.gridPane = new GridPane();
/*  224 */     this.gridPane.getStyleClass().add("html-editor");
/*  225 */     getChildren().addAll(new Node[] { this.gridPane });
/*      */ 
/*  227 */     this.toolbar1 = new ToolBar();
/*  228 */     this.toolbar1.getStyleClass().add("top-toolbar");
/*  229 */     this.gridPane.add(this.toolbar1, 0, 0);
/*      */ 
/*  231 */     this.toolbar2 = new ToolBar();
/*  232 */     this.toolbar2.getStyleClass().add("bottom-toolbar");
/*  233 */     this.gridPane.add(this.toolbar2, 0, 1);
/*      */ 
/*  235 */     populateToolbars();
/*      */ 
/*  237 */     this.webView = new WebView();
/*  238 */     this.gridPane.add(this.webView, 0, 2);
/*      */ 
/*  240 */     ColumnConstraints localColumnConstraints = new ColumnConstraints();
/*  241 */     localColumnConstraints.setHgrow(Priority.ALWAYS);
/*  242 */     this.gridPane.getColumnConstraints().add(localColumnConstraints);
/*      */ 
/*  244 */     this.webPage = Accessor.getPageFor(this.webView.getEngine());
/*      */ 
/*  246 */     this.webView.addEventHandler(MouseEvent.ANY, new EventHandler() {
/*      */       public void handle(MouseEvent paramAnonymousMouseEvent) {
/*  248 */         Platform.runLater(new Runnable() {
/*      */           public void run() {
/*  250 */             HTMLEditorSkin.this.updateToolbarState(true);
/*      */           }
/*      */         });
/*      */       }
/*      */     });
/*  257 */     this.webView.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler() {
/*      */       public void handle(final KeyEvent paramAnonymousKeyEvent) {
/*  259 */         HTMLEditorSkin.this.applyTextFormatting();
/*  260 */         if ((paramAnonymousKeyEvent.getCode() == KeyCode.CONTROL) || (paramAnonymousKeyEvent.getCode() == KeyCode.META)) {
/*  261 */           return;
/*      */         }
/*  263 */         if ((paramAnonymousKeyEvent.getCode() == KeyCode.TAB) && (!paramAnonymousKeyEvent.isControlDown())) {
/*  264 */           if (!paramAnonymousKeyEvent.isShiftDown())
/*      */           {
/*  269 */             if ((HTMLEditorSkin.this.getCommandState("insertUnorderedList")) || (HTMLEditorSkin.this.getCommandState("insertOrderedList"))) {
/*  270 */               HTMLEditorSkin.this.executeCommand("indent", null);
/*      */             }
/*      */             else {
/*  273 */               HTMLEditorSkin.this.executeCommand("inserttab", null);
/*      */             }
/*      */ 
/*      */           }
/*  281 */           else if ((HTMLEditorSkin.this.getCommandState("insertUnorderedList")) || (HTMLEditorSkin.this.getCommandState("insertOrderedList"))) {
/*  282 */             HTMLEditorSkin.this.executeCommand("outdent", null);
/*      */           }
/*      */ 
/*  285 */           return;
/*      */         }
/*      */ 
/*  288 */         if (((HTMLEditorSkin.this.fgColorButton != null) && (HTMLEditorSkin.this.fgColorButton.isShowing())) || ((HTMLEditorSkin.this.bgColorButton != null) && (HTMLEditorSkin.this.bgColorButton.isShowing())))
/*      */         {
/*  290 */           return;
/*      */         }
/*  292 */         Platform.runLater(new Runnable() {
/*      */           public void run() {
/*  294 */             if (HTMLEditorSkin.this.webPage.getClientSelectedText().isEmpty()) {
/*  295 */               if ((paramAnonymousKeyEvent.getCode() == KeyCode.UP) || (paramAnonymousKeyEvent.getCode() == KeyCode.DOWN) || (paramAnonymousKeyEvent.getCode() == KeyCode.LEFT) || (paramAnonymousKeyEvent.getCode() == KeyCode.RIGHT) || (paramAnonymousKeyEvent.getCode() == KeyCode.HOME) || (paramAnonymousKeyEvent.getCode() == KeyCode.END))
/*      */               {
/*  298 */                 HTMLEditorSkin.this.updateToolbarState(true);
/*  299 */               } else if ((paramAnonymousKeyEvent.isControlDown()) || (paramAnonymousKeyEvent.isMetaDown())) {
/*  300 */                 if (paramAnonymousKeyEvent.getCode() == KeyCode.B)
/*  301 */                   HTMLEditorSkin.this.keyboardShortcuts("bold");
/*  302 */                 else if (paramAnonymousKeyEvent.getCode() == KeyCode.I)
/*  303 */                   HTMLEditorSkin.this.keyboardShortcuts("italic");
/*  304 */                 else if (paramAnonymousKeyEvent.getCode() == KeyCode.U) {
/*  305 */                   HTMLEditorSkin.this.keyboardShortcuts("underline");
/*      */                 }
/*  307 */                 HTMLEditorSkin.this.updateToolbarState(true);
/*      */               } else {
/*  309 */                 HTMLEditorSkin.this.resetToolbarState = (paramAnonymousKeyEvent.getCode() == KeyCode.ENTER);
/*  310 */                 if ((HTMLEditorSkin.this.resetToolbarState) && 
/*  311 */                   (HTMLEditorSkin.this.getCommandState("bold") != HTMLEditorSkin.this.boldButton.selectedProperty().getValue().booleanValue())) {
/*  312 */                   HTMLEditorSkin.this.executeCommand("bold", HTMLEditorSkin.this.boldButton.selectedProperty().getValue().toString());
/*      */                 }
/*      */ 
/*  315 */                 HTMLEditorSkin.this.updateToolbarState(false);
/*      */               }
/*  317 */               HTMLEditorSkin.this.resetToolbarState = false;
/*      */             }
/*  319 */             else if ((paramAnonymousKeyEvent.isShiftDown()) && ((paramAnonymousKeyEvent.getCode() == KeyCode.UP) || (paramAnonymousKeyEvent.getCode() == KeyCode.DOWN) || (paramAnonymousKeyEvent.getCode() == KeyCode.LEFT) || (paramAnonymousKeyEvent.getCode() == KeyCode.RIGHT)))
/*      */             {
/*  322 */               HTMLEditorSkin.this.updateToolbarState(true);
/*      */             }
/*      */           }
/*      */         });
/*      */       }
/*      */     });
/*  329 */     this.webView.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler() {
/*      */       public void handle(final KeyEvent paramAnonymousKeyEvent) {
/*  331 */         if ((paramAnonymousKeyEvent.getCode() == KeyCode.CONTROL) || (paramAnonymousKeyEvent.getCode() == KeyCode.META)) {
/*  332 */           return;
/*      */         }
/*      */ 
/*  335 */         if (((HTMLEditorSkin.this.fgColorButton != null) && (HTMLEditorSkin.this.fgColorButton.isShowing())) || ((HTMLEditorSkin.this.bgColorButton != null) && (HTMLEditorSkin.this.bgColorButton.isShowing())))
/*      */         {
/*  337 */           return;
/*      */         }
/*  339 */         Platform.runLater(new Runnable() {
/*      */           public void run() {
/*  341 */             if (HTMLEditorSkin.this.webPage.getClientSelectedText().isEmpty()) {
/*  342 */               if ((paramAnonymousKeyEvent.getCode() == KeyCode.UP) || (paramAnonymousKeyEvent.getCode() == KeyCode.DOWN) || (paramAnonymousKeyEvent.getCode() == KeyCode.LEFT) || (paramAnonymousKeyEvent.getCode() == KeyCode.RIGHT) || (paramAnonymousKeyEvent.getCode() == KeyCode.HOME) || (paramAnonymousKeyEvent.getCode() == KeyCode.END))
/*      */               {
/*  345 */                 HTMLEditorSkin.this.updateToolbarState(true);
/*  346 */               } else if ((paramAnonymousKeyEvent.isControlDown()) || (paramAnonymousKeyEvent.isMetaDown())) {
/*  347 */                 if (paramAnonymousKeyEvent.getCode() == KeyCode.B)
/*  348 */                   HTMLEditorSkin.this.keyboardShortcuts("bold");
/*  349 */                 else if (paramAnonymousKeyEvent.getCode() == KeyCode.I)
/*  350 */                   HTMLEditorSkin.this.keyboardShortcuts("italic");
/*  351 */                 else if (paramAnonymousKeyEvent.getCode() == KeyCode.U) {
/*  352 */                   HTMLEditorSkin.this.keyboardShortcuts("underline");
/*      */                 }
/*  354 */                 HTMLEditorSkin.this.updateToolbarState(true);
/*      */               } else {
/*  356 */                 HTMLEditorSkin.this.resetToolbarState = (paramAnonymousKeyEvent.getCode() == KeyCode.ENTER);
/*  357 */                 if (!HTMLEditorSkin.this.resetToolbarState) {
/*  358 */                   HTMLEditorSkin.this.updateToolbarState(false);
/*      */                 }
/*      */               }
/*  361 */               HTMLEditorSkin.this.resetToolbarState = false;
/*      */             }
/*      */           }
/*      */         });
/*      */       }
/*      */     });
/*  368 */     ((HTMLEditor)getSkinnable()).focusedProperty().addListener(new ChangeListener()
/*      */     {
/*      */       public void changed(ObservableValue<? extends Boolean> paramAnonymousObservableValue, Boolean paramAnonymousBoolean1, final Boolean paramAnonymousBoolean2) {
/*  371 */         Platform.runLater(new Runnable() {
/*      */           public void run() {
/*  373 */             if (paramAnonymousBoolean2.booleanValue())
/*  374 */               HTMLEditorSkin.this.webView.requestFocus();
/*      */           }
/*      */         });
/*      */       }
/*      */     });
/*  381 */     this.webView.focusedProperty().addListener(new ChangeListener()
/*      */     {
/*      */       public void changed(ObservableValue<? extends Boolean> paramAnonymousObservableValue, Boolean paramAnonymousBoolean1, final Boolean paramAnonymousBoolean2) {
/*  384 */         if (paramAnonymousBoolean2.booleanValue()) {
/*  385 */           HTMLEditorSkin.this.webPage.dispatchFocusEvent(new WCFocusEvent(2, 0));
/*  386 */           HTMLEditorSkin.this.enableToolbar(true);
/*      */         } else {
/*  388 */           HTMLEditorSkin.this.webPage.dispatchFocusEvent(new WCFocusEvent(3, 0));
/*  389 */           HTMLEditorSkin.this.enableToolbar(false);
/*      */         }
/*  391 */         Platform.runLater(new Runnable() {
/*      */           public void run() {
/*  393 */             HTMLEditorSkin.this.updateToolbarState(true);
/*      */           }
/*      */         });
/*  397 */         if (PlatformUtil.isEmbedded())
/*  398 */           Platform.runLater(new Runnable() {
/*      */             public void run() {
/*  400 */               if (paramAnonymousBoolean2.booleanValue())
/*  401 */                 FXVK.attach(HTMLEditorSkin.this.webView);
/*  402 */               else if ((HTMLEditorSkin.this.getScene() == null) || (HTMLEditorSkin.this.getScene().getWindow() == null) || (!HTMLEditorSkin.this.getScene().getWindow().isFocused()) || (!(HTMLEditorSkin.this.getScene().getFocusOwner() instanceof TextInputControl)))
/*      */               {
/*  407 */                 FXVK.detach();
/*      */               }
/*      */             }
/*      */           });
/*      */       }
/*      */     });
/*  415 */     this.webView.getEngine().getLoadWorker().workDoneProperty().addListener(new ChangeListener()
/*      */     {
/*      */       public void changed(ObservableValue<? extends Number> paramAnonymousObservableValue, Number paramAnonymousNumber1, Number paramAnonymousNumber2) {
/*  418 */         Platform.runLater(new Runnable() {
/*      */           public void run() {
/*  420 */             HTMLEditorSkin.this.webView.requestLayout();
/*      */           }
/*      */         });
/*  424 */         double d = HTMLEditorSkin.this.webView.getEngine().getLoadWorker().getTotalWork();
/*  425 */         if (paramAnonymousNumber2.doubleValue() == d) {
/*  426 */           HTMLEditorSkin.this.cachedHTMLText = null;
/*  427 */           Platform.runLater(new Runnable() {
/*      */             public void run() {
/*  429 */               HTMLEditorSkin.this.setContentEditable(true);
/*  430 */               HTMLEditorSkin.this.updateToolbarState(true);
/*      */             }
/*      */           });
/*      */         }
/*      */       }
/*      */     });
/*  437 */     enableToolbar(true);
/*  438 */     setHTMLText(this.cachedHTMLText);
/*      */ 
/*  440 */     this.engine = new TraversalEngine(this, false);
/*  441 */     this.engine.addTraverseListener(this);
/*  442 */     this.engine.reg(this.toolbar1);
/*  443 */     setImpl_traversalEngine(this.engine);
/*  444 */     this.webView.setFocusTraversable(true);
/*      */   }
/*      */ 
/*      */   public final String getHTMLText()
/*      */   {
/*  450 */     return this.cachedHTMLText != null ? this.cachedHTMLText : this.webPage.getHtml(this.webPage.getMainFrame());
/*      */   }
/*      */ 
/*      */   public final void setHTMLText(String paramString) {
/*  454 */     this.cachedHTMLText = paramString;
/*  455 */     this.webPage.load(this.webPage.getMainFrame(), paramString, "text/html");
/*      */ 
/*  457 */     Platform.runLater(new Runnable() {
/*      */       public void run() {
/*  459 */         HTMLEditorSkin.this.updateToolbarState(true);
/*      */       }
/*      */     });
/*      */   }
/*      */ 
/*      */   private void populateToolbars()
/*      */   {
/*  467 */     this.resources = ResourceBundle.getBundle(getClass().getName());
/*      */ 
/*  470 */     this.cutButton = addButton(this.toolbar1, this.resources.getString("cutIcon"), this.resources.getString("cut"), "cut");
/*  471 */     this.copyButton = addButton(this.toolbar1, this.resources.getString("copyIcon"), this.resources.getString("copy"), "copy");
/*  472 */     this.pasteButton = addButton(this.toolbar1, this.resources.getString("pasteIcon"), this.resources.getString("paste"), "paste");
/*      */ 
/*  474 */     this.toolbar1.getItems().add(new Separator());
/*      */ 
/*  480 */     this.alignmentToggleGroup = new ToggleGroup();
/*  481 */     this.alignLeftButton = addToggleButton(this.toolbar1, this.alignmentToggleGroup, this.resources.getString("alignLeftIcon"), this.resources.getString("alignLeft"), "justifyleft");
/*      */ 
/*  483 */     this.alignCenterButton = addToggleButton(this.toolbar1, this.alignmentToggleGroup, this.resources.getString("alignCenterIcon"), this.resources.getString("alignCenter"), "justifycenter");
/*      */ 
/*  485 */     this.alignRightButton = addToggleButton(this.toolbar1, this.alignmentToggleGroup, this.resources.getString("alignRightIcon"), this.resources.getString("alignRight"), "justifyright");
/*      */ 
/*  487 */     this.alignJustifyButton = addToggleButton(this.toolbar1, this.alignmentToggleGroup, this.resources.getString("alignJustifyIcon"), this.resources.getString("alignJustify"), "justifyfull");
/*      */ 
/*  490 */     this.toolbar1.getItems().add(new Separator());
/*      */ 
/*  492 */     this.outdentButton = addButton(this.toolbar1, this.resources.getString("outdentIcon"), this.resources.getString("outdent"), "outdent");
/*  493 */     this.indentButton = addButton(this.toolbar1, this.resources.getString("indentIcon"), this.resources.getString("indent"), "indent");
/*      */ 
/*  495 */     this.toolbar1.getItems().add(new Separator());
/*      */ 
/*  497 */     ToggleGroup localToggleGroup = new ToggleGroup();
/*  498 */     this.bulletsButton = addToggleButton(this.toolbar1, localToggleGroup, this.resources.getString("bulletsIcon"), this.resources.getString("bullets"), "insertUnorderedList");
/*      */ 
/*  500 */     this.numbersButton = addToggleButton(this.toolbar1, localToggleGroup, this.resources.getString("numbersIcon"), this.resources.getString("numbers"), "insertOrderedList");
/*      */ 
/*  503 */     this.toolbar1.getItems().add(new Separator());
/*      */ 
/*  508 */     this.formatComboBox = new ComboBox();
/*  509 */     this.formatComboBox.getStyleClass().add("font-menu-button");
/*  510 */     this.formatComboBox.setMinWidth(100.0D);
/*  511 */     this.formatComboBox.setPrefWidth(100.0D);
/*  512 */     this.formatComboBox.setMaxWidth(100.0D);
/*  513 */     this.formatComboBox.setFocusTraversable(false);
/*  514 */     this.toolbar2.getItems().add(this.formatComboBox);
/*      */ 
/*  516 */     this.formatStyleMap = new HashMap();
/*  517 */     this.styleFormatMap = new HashMap();
/*      */ 
/*  519 */     createFormatMenuItem("<p>", this.resources.getString("paragraph"));
/*  520 */     Platform.runLater(new Runnable()
/*      */     {
/*      */       public void run() {
/*  523 */         HTMLEditorSkin.this.formatComboBox.setValue(HTMLEditorSkin.this.resources.getString("paragraph"));
/*      */       }
/*      */     });
/*  526 */     createFormatMenuItem("<h1>", this.resources.getString("heading1"));
/*  527 */     createFormatMenuItem("<h2>", this.resources.getString("heading2"));
/*  528 */     createFormatMenuItem("<h3>", this.resources.getString("heading3"));
/*  529 */     createFormatMenuItem("<h4>", this.resources.getString("heading4"));
/*  530 */     createFormatMenuItem("<h5>", this.resources.getString("heading5"));
/*  531 */     createFormatMenuItem("<h6>", this.resources.getString("heading6"));
/*      */ 
/*  533 */     this.formatComboBox.setCellFactory(new Callback() {
/*      */       public ListCell<String> call(ListView<String> paramAnonymousListView) {
/*  535 */         ListCell local1 = new ListCell() {
/*      */           public void updateItem(String paramAnonymous2String, boolean paramAnonymous2Boolean) {
/*  537 */             super.updateItem(paramAnonymous2String, paramAnonymous2Boolean);
/*  538 */             if (paramAnonymous2String != null)
/*  539 */               setText(paramAnonymous2String);
/*      */           }
/*      */         };
/*  543 */         return local1;
/*      */       }
/*      */     });
/*  547 */     this.formatComboBox.setTooltip(new Tooltip(this.resources.getString("format")));
/*      */ 
/*  549 */     this.formatComboBox.valueProperty().addListener(new ChangeListener()
/*      */     {
/*      */       public void changed(ObservableValue<? extends String> paramAnonymousObservableValue, String paramAnonymousString1, String paramAnonymousString2) {
/*  552 */         if (paramAnonymousString2 == null) {
/*  553 */           HTMLEditorSkin.this.formatComboBox.setValue(null);
/*      */         }
/*      */         else {
/*  556 */           HTMLEditorSkin.this.formatChanged = true;
/*      */ 
/*  558 */           String str = (String)HTMLEditorSkin.this.formatStyleMap.get(paramAnonymousString2);
/*  559 */           HTMLEditorSkin.this.executeCommand("formatblock", str);
/*  560 */           HTMLEditorSkin.this.updateToolbarState(false);
/*      */ 
/*  563 */           for (int i = 0; i < HTMLEditorSkin.DEFAULT_FORMAT_MAPPINGS.length; i++) {
/*  564 */             String[] arrayOfString = HTMLEditorSkin.DEFAULT_FORMAT_MAPPINGS[i];
/*  565 */             if (arrayOfString[0].equalsIgnoreCase(str)) {
/*  566 */               HTMLEditorSkin.this.executeCommand("fontsize", arrayOfString[2]);
/*  567 */               HTMLEditorSkin.this.updateToolbarState(false);
/*  568 */               break;
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     });
/*  575 */     this.fontFamilyComboBox = new ComboBox();
/*  576 */     this.fontFamilyComboBox.getStyleClass().add("font-menu-button");
/*  577 */     this.fontFamilyComboBox.setMinWidth(150.0D);
/*  578 */     this.fontFamilyComboBox.setPrefWidth(150.0D);
/*  579 */     this.fontFamilyComboBox.setMaxWidth(150.0D);
/*  580 */     this.fontFamilyComboBox.setMaxHeight(21.0D);
/*  581 */     this.fontFamilyComboBox.setPrefHeight(21.0D);
/*  582 */     this.fontFamilyComboBox.setMinHeight(21.0D);
/*  583 */     this.fontFamilyComboBox.setFocusTraversable(false);
/*  584 */     this.fontFamilyComboBox.setTooltip(new Tooltip(this.resources.getString("fontFamily")));
/*  585 */     this.toolbar2.getItems().add(this.fontFamilyComboBox);
/*      */ 
/*  587 */     this.fontFamilyComboBox.setCellFactory(new Callback() {
/*      */       public ListCell<String> call(ListView<String> paramAnonymousListView) {
/*  589 */         ListCell local1 = new ListCell() {
/*      */           public void updateItem(String paramAnonymous2String, boolean paramAnonymous2Boolean) {
/*  591 */             super.updateItem(paramAnonymous2String, paramAnonymous2Boolean);
/*  592 */             if (paramAnonymous2String != null)
/*  593 */               setText(paramAnonymous2String);
/*      */           }
/*      */         };
/*  599 */         local1.setMinWidth(100.0D);
/*  600 */         local1.setPrefWidth(100.0D);
/*  601 */         local1.setMaxWidth(100.0D);
/*  602 */         return local1;
/*      */       }
/*      */     });
/*  606 */     Platform.runLater(new Runnable() {
/*      */       public void run() {
/*  608 */         ObservableList localObservableList = FXCollections.observableArrayList(Font.getFamilies());
/*  609 */         for (String str : localObservableList) {
/*  610 */           if (HTMLEditorSkin.DEFAULT_OS_FONT.equals(str)) {
/*  611 */             HTMLEditorSkin.this.fontFamilyComboBox.setValue(str);
/*      */           }
/*  613 */           HTMLEditorSkin.this.fontFamilyComboBox.setItems(localObservableList);
/*      */         }
/*      */       }
/*      */     });
/*  618 */     this.fontFamilyComboBox.valueProperty().addListener(new ChangeListener()
/*      */     {
/*      */       public void changed(ObservableValue<? extends String> paramAnonymousObservableValue, String paramAnonymousString1, String paramAnonymousString2) {
/*  621 */         HTMLEditorSkin.this.executeCommand("fontname", paramAnonymousString2);
/*      */       }
/*      */     });
/*  625 */     this.fontSizeComboBox = new ComboBox();
/*  626 */     this.fontSizeComboBox.getStyleClass().add("font-menu-button");
/*  627 */     this.fontSizeComboBox.setMinWidth(80.0D);
/*  628 */     this.fontSizeComboBox.setPrefWidth(80.0D);
/*  629 */     this.fontSizeComboBox.setMaxWidth(80.0D);
/*  630 */     this.fontSizeComboBox.setFocusTraversable(false);
/*  631 */     this.toolbar2.getItems().add(this.fontSizeComboBox);
/*      */ 
/*  633 */     this.fontSizeMap = new HashMap();
/*  634 */     this.sizeFontMap = new HashMap();
/*  635 */     ObservableList localObservableList = this.fontSizeComboBox.getItems();
/*      */ 
/*  637 */     createFontSizeMenuItem("1", this.resources.getString("extraExtraSmall"));
/*  638 */     createFontSizeMenuItem("2", this.resources.getString("extraSmall"));
/*  639 */     createFontSizeMenuItem("3", this.resources.getString("small"));
/*  640 */     Platform.runLater(new Runnable()
/*      */     {
/*      */       public void run() {
/*  643 */         HTMLEditorSkin.this.fontSizeComboBox.setValue(HTMLEditorSkin.this.resources.getString("small"));
/*      */       }
/*      */     });
/*  646 */     createFontSizeMenuItem("4", this.resources.getString("medium"));
/*  647 */     createFontSizeMenuItem("5", this.resources.getString("large"));
/*  648 */     createFontSizeMenuItem("6", this.resources.getString("extraLarge"));
/*  649 */     createFontSizeMenuItem("7", this.resources.getString("extraExtraLarge"));
/*  650 */     this.fontSizeComboBox.setTooltip(new Tooltip(this.resources.getString("fontSize")));
/*      */ 
/*  652 */     this.fontSizeComboBox.setCellFactory(new Callback() {
/*      */       public ListCell<String> call(ListView<String> paramAnonymousListView) {
/*  654 */         ListCell local1 = new ListCell() {
/*      */           public void updateItem(String paramAnonymous2String, boolean paramAnonymous2Boolean) {
/*  656 */             super.updateItem(paramAnonymous2String, paramAnonymous2Boolean);
/*  657 */             if (paramAnonymous2String != null)
/*  658 */               setText(paramAnonymous2String);
/*      */           }
/*      */         };
/*  664 */         return local1;
/*      */       }
/*      */     });
/*  669 */     this.fontSizeComboBox.valueProperty().addListener(new Object()
/*      */     {
/*      */       public void changed(ObservableValue<? extends String> paramAnonymousObservableValue, String paramAnonymousString1, String paramAnonymousString2) {
/*  672 */         String str = HTMLEditorSkin.this.getCommandValue("fontsize");
/*  673 */         if (!paramAnonymousString2.equals(str))
/*  674 */           HTMLEditorSkin.this.executeCommand("fontsize", (String)HTMLEditorSkin.this.fontSizeMap.get(paramAnonymousString2));
/*      */       }
/*      */     });
/*  679 */     this.toolbar2.getItems().add(new Separator());
/*      */ 
/*  681 */     this.boldButton = addToggleButton(this.toolbar2, null, this.resources.getString("boldIcon"), this.resources.getString("bold"), "bold");
/*      */ 
/*  683 */     this.boldButton.setOnAction(new EventHandler()
/*      */     {
/*      */       public void handle(ActionEvent paramAnonymousActionEvent)
/*      */       {
/*  689 */         if ("<p>".equals(HTMLEditorSkin.this.formatStyleMap.get(HTMLEditorSkin.this.formatComboBox.getValue())))
/*  690 */           HTMLEditorSkin.this.executeCommand("bold", HTMLEditorSkin.this.boldButton.selectedProperty().getValue().toString());
/*      */       }
/*      */     });
/*  694 */     this.italicButton = addToggleButton(this.toolbar2, null, this.resources.getString("italicIcon"), this.resources.getString("italic"), "italic");
/*      */ 
/*  696 */     this.underlineButton = addToggleButton(this.toolbar2, null, this.resources.getString("underlineIcon"), this.resources.getString("underline"), "underline");
/*      */ 
/*  698 */     this.strikethroughButton = addToggleButton(this.toolbar2, null, this.resources.getString("strikethroughIcon"), this.resources.getString("strikethrough"), "strikethrough");
/*      */ 
/*  701 */     this.toolbar2.getItems().add(new Separator());
/*      */ 
/*  703 */     this.insertHorizontalRuleButton = addButton(this.toolbar2, this.resources.getString("insertHorizontalRuleIcon"), this.resources.getString("insertHorizontalRule"), "inserthorizontalrule");
/*      */ 
/*  706 */     this.insertHorizontalRuleButton.setOnAction(new EventHandler()
/*      */     {
/*      */       public void handle(ActionEvent paramAnonymousActionEvent) {
/*  709 */         HTMLEditorSkin.this.executeCommand("insertnewline", null);
/*  710 */         HTMLEditorSkin.this.executeCommand("inserthorizontalrule", null);
/*  711 */         HTMLEditorSkin.this.updateToolbarState(false);
/*      */       }
/*      */     });
/*  715 */     this.fgColorButton = new PopupButton();
/*  716 */     this.fgColorButton.setFocusTraversable(false);
/*  717 */     this.toolbar1.getItems().add(this.fgColorButton);
/*  718 */     Image localImage1 = (Image)AccessController.doPrivileged(new PrivilegedAction() {
/*      */       public Image run() {
/*  720 */         return new Image(HTMLEditorSkin.class.getResource(HTMLEditorSkin.this.resources.getString("foregroundColorIcon")).toString());
/*      */       }
/*      */     });
/*  723 */     this.fgColorRect = new Rectangle(0.0D, 10.0D, 16.0D, 5.0D);
/*  724 */     Group localGroup1 = new Group();
/*  725 */     localGroup1.getChildren().addAll(new Node[] { this.fgColorRect, new ImageView(localImage1) });
/*  726 */     this.fgColorButton.setGraphic(localGroup1);
/*  727 */     this.fgColorPicker = new ColorPicker();
/*  728 */     this.fgColorPicker.setColor(DEFAULT_FG_COLOR);
/*  729 */     this.fgColorButton.setContent(this.fgColorPicker);
/*  730 */     this.fgColorButton.setTooltip(new Tooltip(this.resources.getString("foregroundColor")));
/*      */ 
/*  732 */     this.fgColorPicker.setOnAction(new EventHandler() {
/*      */       public void handle(ActionEvent paramAnonymousActionEvent) {
/*  734 */         Color localColor = HTMLEditorSkin.this.fgColorPicker.getColor();
/*  735 */         if (localColor != null) {
/*  736 */           HTMLEditorSkin.this.executeCommand("forecolor", ColorPicker.colorValueToHex(localColor));
/*  737 */           HTMLEditorSkin.this.fgColorButton.hide();
/*  738 */           HTMLEditorSkin.this.fgColorRect.setFill(localColor);
/*      */         }
/*      */       }
/*      */     });
/*  743 */     this.bgColorButton = new PopupButton();
/*  744 */     this.bgColorButton.setFocusTraversable(false);
/*  745 */     this.toolbar1.getItems().add(this.bgColorButton);
/*  746 */     Image localImage2 = (Image)AccessController.doPrivileged(new PrivilegedAction() {
/*      */       public Image run() {
/*  748 */         return new Image(HTMLEditorSkin.class.getResource(HTMLEditorSkin.this.resources.getString("backgroundColorIcon")).toString());
/*      */       }
/*      */     });
/*  751 */     this.bgColorRect = new Rectangle(0.0D, 10.0D, 16.0D, 5.0D);
/*  752 */     this.bgColorRect.setFill(DEFAULT_BG_COLOR);
/*  753 */     Group localGroup2 = new Group();
/*  754 */     localGroup2.getChildren().addAll(new Node[] { this.bgColorRect, new ImageView(localImage2) });
/*  755 */     this.bgColorButton.setGraphic(localGroup2);
/*  756 */     this.bgColorPicker = new ColorPicker();
/*  757 */     this.bgColorPicker.setColor(DEFAULT_BG_COLOR);
/*  758 */     this.bgColorButton.setContent(this.bgColorPicker);
/*  759 */     this.bgColorButton.setTooltip(new Tooltip(this.resources.getString("backgroundColor")));
/*      */ 
/*  761 */     this.bgColorPicker.setOnAction(new EventHandler() {
/*      */       public void handle(ActionEvent paramAnonymousActionEvent) {
/*  763 */         Color localColor = HTMLEditorSkin.this.bgColorPicker.getColor();
/*  764 */         if (localColor != null) {
/*  765 */           HTMLEditorSkin.this.executeCommand("backcolor", ColorPicker.colorValueToHex(localColor));
/*  766 */           HTMLEditorSkin.this.bgColorButton.hide();
/*  767 */           HTMLEditorSkin.this.bgColorRect.setFill(localColor);
/*      */         }
/*      */       }
/*      */     });
/*      */   }
/*      */ 
/*      */   private Button addButton(ToolBar paramToolBar, final String paramString1, String paramString2, final String paramString3)
/*      */   {
/*  775 */     Button localButton = new Button();
/*  776 */     localButton.setFocusTraversable(false);
/*  777 */     paramToolBar.getItems().add(localButton);
/*      */ 
/*  779 */     Image localImage = (Image)AccessController.doPrivileged(new PrivilegedAction() {
/*      */       public Image run() {
/*  781 */         return new Image(HTMLEditorSkin.class.getResource(paramString1).toString());
/*      */       }
/*      */     });
/*  784 */     localButton.setGraphic(new ImageView(localImage));
/*  785 */     localButton.setTooltip(new Tooltip(paramString2));
/*      */ 
/*  787 */     localButton.setOnAction(new EventHandler()
/*      */     {
/*      */       public void handle(ActionEvent paramAnonymousActionEvent) {
/*  790 */         HTMLEditorSkin.this.executeCommand(paramString3, null);
/*  791 */         HTMLEditorSkin.this.updateToolbarState(false);
/*      */       }
/*      */     });
/*  795 */     return localButton;
/*      */   }
/*      */ 
/*      */   private ToggleButton addToggleButton(ToolBar paramToolBar, ToggleGroup paramToggleGroup, final String paramString1, String paramString2, final String paramString3)
/*      */   {
/*  800 */     ToggleButton localToggleButton = new ToggleButton();
/*  801 */     localToggleButton.setUserData(paramString3);
/*  802 */     localToggleButton.setFocusTraversable(false);
/*  803 */     paramToolBar.getItems().add(localToggleButton);
/*  804 */     if (paramToggleGroup != null) {
/*  805 */       localToggleButton.setToggleGroup(paramToggleGroup);
/*      */     }
/*      */ 
/*  808 */     Image localImage = (Image)AccessController.doPrivileged(new PrivilegedAction() {
/*      */       public Image run() {
/*  810 */         return new Image(HTMLEditorSkin.class.getResource(paramString1).toString());
/*      */       }
/*      */     });
/*  813 */     localToggleButton.setGraphic(new ImageView(localImage));
/*  814 */     localToggleButton.setTooltip(new Tooltip(paramString2));
/*      */ 
/*  816 */     if (!"bold".equals(paramString3)) {
/*  817 */       localToggleButton.selectedProperty().addListener(new ChangeListener()
/*      */       {
/*      */         public void changed(ObservableValue<? extends Boolean> paramAnonymousObservableValue, Boolean paramAnonymousBoolean1, Boolean paramAnonymousBoolean2) {
/*  820 */           if (HTMLEditorSkin.this.getCommandState(paramString3) != paramAnonymousBoolean2.booleanValue()) {
/*  821 */             HTMLEditorSkin.this.executeCommand(paramString3, null);
/*      */           }
/*      */         }
/*      */       });
/*      */     }
/*  826 */     return localToggleButton;
/*      */   }
/*      */ 
/*      */   private void createFormatMenuItem(String paramString1, String paramString2) {
/*  830 */     this.formatComboBox.getItems().add(paramString2);
/*  831 */     this.formatStyleMap.put(paramString2, paramString1);
/*  832 */     this.styleFormatMap.put(paramString1, paramString2);
/*      */   }
/*      */ 
/*      */   private void createFontSizeMenuItem(String paramString1, String paramString2) {
/*  836 */     this.fontSizeComboBox.getItems().add(paramString2);
/*  837 */     this.fontSizeMap.put(paramString2, paramString1);
/*  838 */     this.sizeFontMap.put(paramString1, paramString2);
/*      */   }
/*      */ 
/*      */   private void updateToolbarState(boolean paramBoolean) {
/*  842 */     if (!this.webView.isFocused()) {
/*  843 */       return;
/*      */     }
/*      */ 
/*  847 */     this.copyButton.setDisable(!isCommandEnabled("cut"));
/*  848 */     this.cutButton.setDisable(!isCommandEnabled("copy"));
/*  849 */     this.pasteButton.setDisable(!isCommandEnabled("paste"));
/*      */ 
/*  857 */     this.insertHorizontalRuleButton.setDisable(!isCommandEnabled("inserthorizontalrule"));
/*      */ 
/*  859 */     if (paramBoolean) {
/*  860 */       this.alignLeftButton.setDisable(!isCommandEnabled("justifyleft"));
/*  861 */       this.alignLeftButton.setSelected(getCommandState("justifyleft"));
/*  862 */       this.alignCenterButton.setDisable(!isCommandEnabled("justifycenter"));
/*  863 */       this.alignCenterButton.setSelected(getCommandState("justifycenter"));
/*  864 */       this.alignRightButton.setDisable(!isCommandEnabled("justifyright"));
/*  865 */       this.alignRightButton.setSelected(getCommandState("justifyright"));
/*  866 */       this.alignJustifyButton.setDisable(!isCommandEnabled("justifyfull"));
/*  867 */       this.alignJustifyButton.setSelected(getCommandState("justifyfull"));
/*      */     }
/*  869 */     else if (this.alignmentToggleGroup.getSelectedToggle() != null) {
/*  870 */       str1 = this.alignmentToggleGroup.getSelectedToggle().getUserData().toString();
/*  871 */       if (!getCommandState(str1)) {
/*  872 */         executeCommand(str1, null);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  877 */     if (this.alignmentToggleGroup.getSelectedToggle() == null) {
/*  878 */       this.alignmentToggleGroup.selectToggle(this.alignLeftButton);
/*      */     }
/*      */ 
/*  881 */     this.bulletsButton.setDisable(!isCommandEnabled("insertUnorderedList"));
/*  882 */     this.bulletsButton.setSelected(getCommandState("insertUnorderedList"));
/*  883 */     this.numbersButton.setDisable(!isCommandEnabled("insertOrderedList"));
/*  884 */     this.numbersButton.setSelected(getCommandState("insertOrderedList"));
/*      */ 
/*  886 */     this.indentButton.setDisable(!isCommandEnabled("indent"));
/*  887 */     this.outdentButton.setDisable(!isCommandEnabled("outdent"));
/*      */ 
/*  889 */     this.formatComboBox.setDisable(!isCommandEnabled("formatblock"));
/*      */ 
/*  892 */     String str1 = getCommandValue("formatblock");
/*  893 */     if ((str1 instanceof String)) {
/*  894 */       str2 = "<" + str1 + ">";
/*  895 */       str3 = (String)this.styleFormatMap.get(str2);
/*      */ 
/*  899 */       if ((this.resetToolbarState) || (str2.equals("<>")) || (str2.equalsIgnoreCase("<div>"))) {
/*  900 */         this.formatComboBox.setValue(this.resources.getString("paragraph"));
/*      */       }
/*  902 */       else if (!((String)this.formatComboBox.getValue()).equalsIgnoreCase(str3)) {
/*  903 */         this.formatComboBox.setValue(str3);
/*      */       }
/*      */     }
/*      */ 
/*  907 */     this.fontFamilyComboBox.setDisable(!isCommandEnabled("fontname"));
/*  908 */     String str2 = getCommandValue("fontname");
/*      */     Object localObject3;
/*  909 */     if ((str2 instanceof String)) {
/*  910 */       str3 = (String)str2;
/*      */ 
/*  914 */       if (str3.startsWith("'")) {
/*  915 */         str3 = str3.substring(1);
/*      */       }
/*  917 */       if (str3.endsWith("'")) {
/*  918 */         str3 = str3.substring(0, str3.length() - 1);
/*      */       }
/*      */ 
/*  921 */       localObject1 = this.fontFamilyComboBox.getValue();
/*  922 */       if (((localObject1 instanceof String)) && 
/*  923 */         (!((Object)localObject1).equals(str3)))
/*      */       {
/*  925 */         localObject2 = this.fontFamilyComboBox.getItems();
/*  926 */         localObject3 = null;
/*  927 */         for (String str4 : (ObservableList)localObject2)
/*      */         {
/*  929 */           if (str4.equals(str3)) {
/*  930 */             localObject3 = str4;
/*  931 */             break;
/*      */           }
/*      */ 
/*  935 */           if ((str4.equals(DEFAULT_OS_FONT)) && (str3.equals("Dialog"))) {
/*  936 */             localObject3 = str4;
/*  937 */             break;
/*      */           }
/*      */         }
/*      */ 
/*  941 */         if (localObject3 != null) {
/*  942 */           this.fontFamilyComboBox.setValue(localObject3);
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  948 */     this.fontSizeComboBox.setDisable(!isCommandEnabled("fontsize"));
/*  949 */     String str3 = getCommandValue("fontsize");
/*      */ 
/*  951 */     if (this.resetToolbarState) {
/*  952 */       this.fontSizeComboBox.setValue(this.sizeFontMap.get("3"));
/*      */     }
/*  955 */     else if ((str3 instanceof String)) {
/*  956 */       if (!this.fontSizeComboBox.getValue().equals(this.sizeFontMap.get(str3))) {
/*  957 */         this.fontSizeComboBox.setValue(this.sizeFontMap.get(str3));
/*      */       }
/*      */ 
/*      */     }
/*  965 */     else if (!this.fontSizeComboBox.getValue().equals(this.sizeFontMap.get("3"))) {
/*  966 */       this.fontSizeComboBox.setValue(this.sizeFontMap.get("3"));
/*      */     }
/*      */ 
/*  971 */     this.boldButton.setDisable(!isCommandEnabled("bold"));
/*  972 */     if (((str1 instanceof String)) && 
/*  973 */       (!this.resetToolbarState) && (
/*  973 */       ("p".equals(str1)) || ("div".equals(str1)))) {
/*  974 */       this.boldButton.setSelected(getCommandState("bold"));
/*      */     }
/*      */ 
/*  977 */     this.italicButton.setDisable(!isCommandEnabled("italic"));
/*  978 */     this.italicButton.setSelected(getCommandState("italic"));
/*  979 */     this.underlineButton.setDisable(!isCommandEnabled("underline"));
/*  980 */     this.underlineButton.setSelected(getCommandState("underline"));
/*  981 */     this.strikethroughButton.setDisable(!isCommandEnabled("strikethrough"));
/*  982 */     this.strikethroughButton.setSelected(getCommandState("strikethrough"));
/*      */ 
/*  984 */     this.fgColorButton.setDisable(!isCommandEnabled("forecolor"));
/*  985 */     Object localObject1 = getCommandValue("forecolor");
/*  986 */     if ((localObject1 instanceof String)) {
/*  987 */       localObject2 = Color.web(rgbToHex((String)localObject1));
/*  988 */       this.fgColorPicker.setColor((Color)localObject2);
/*  989 */       this.fgColorRect.setFill((Paint)localObject2);
/*      */     }
/*      */ 
/*  992 */     this.bgColorButton.setDisable(!isCommandEnabled("backcolor"));
/*  993 */     Object localObject2 = getCommandValue("backcolor");
/*  994 */     if ((localObject2 instanceof String)) {
/*  995 */       localObject3 = Color.web(rgbToHex((String)localObject2));
/*  996 */       this.bgColorPicker.setColor((Color)localObject3);
/*  997 */       this.bgColorRect.setFill((Paint)localObject3);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void enableToolbar(final boolean paramBoolean) {
/* 1002 */     Platform.runLater(new Runnable()
/*      */     {
/*      */       public void run()
/*      */       {
/* 1009 */         if (paramBoolean) {
/* 1010 */           HTMLEditorSkin.this.copyButton.setDisable(!HTMLEditorSkin.this.isCommandEnabled("copy"));
/* 1011 */           HTMLEditorSkin.this.cutButton.setDisable(!HTMLEditorSkin.this.isCommandEnabled("cut"));
/* 1012 */           HTMLEditorSkin.this.pasteButton.setDisable(!HTMLEditorSkin.this.isCommandEnabled("paste"));
/*      */         }
/*      */         else {
/* 1015 */           HTMLEditorSkin.this.copyButton.setDisable(true);
/* 1016 */           HTMLEditorSkin.this.cutButton.setDisable(true);
/* 1017 */           HTMLEditorSkin.this.pasteButton.setDisable(true);
/*      */         }
/*      */ 
/* 1022 */         HTMLEditorSkin.this.insertHorizontalRuleButton.setDisable(!paramBoolean);
/* 1023 */         HTMLEditorSkin.this.alignLeftButton.setDisable(!paramBoolean);
/* 1024 */         HTMLEditorSkin.this.alignCenterButton.setDisable(!paramBoolean);
/* 1025 */         HTMLEditorSkin.this.alignRightButton.setDisable(!paramBoolean);
/* 1026 */         HTMLEditorSkin.this.alignJustifyButton.setDisable(!paramBoolean);
/* 1027 */         HTMLEditorSkin.this.bulletsButton.setDisable(!paramBoolean);
/* 1028 */         HTMLEditorSkin.this.numbersButton.setDisable(!paramBoolean);
/* 1029 */         HTMLEditorSkin.this.indentButton.setDisable(!paramBoolean);
/* 1030 */         HTMLEditorSkin.this.outdentButton.setDisable(!paramBoolean);
/* 1031 */         HTMLEditorSkin.this.formatComboBox.setDisable(!paramBoolean);
/* 1032 */         HTMLEditorSkin.this.fontFamilyComboBox.setDisable(!paramBoolean);
/* 1033 */         HTMLEditorSkin.this.fontSizeComboBox.setDisable(!paramBoolean);
/* 1034 */         HTMLEditorSkin.this.boldButton.setDisable(!paramBoolean);
/* 1035 */         HTMLEditorSkin.this.italicButton.setDisable(!paramBoolean);
/* 1036 */         HTMLEditorSkin.this.underlineButton.setDisable(!paramBoolean);
/* 1037 */         HTMLEditorSkin.this.strikethroughButton.setDisable(!paramBoolean);
/* 1038 */         HTMLEditorSkin.this.fgColorButton.setDisable(!paramBoolean);
/* 1039 */         HTMLEditorSkin.this.bgColorButton.setDisable(!paramBoolean);
/*      */       }
/*      */     });
/*      */   }
/*      */ 
/*      */   private boolean executeCommand(String paramString1, String paramString2) {
/* 1045 */     return this.webPage.executeCommand(paramString1, paramString2);
/*      */   }
/*      */ 
/*      */   private boolean isCommandEnabled(String paramString) {
/* 1049 */     return this.webPage.queryCommandEnabled(paramString);
/*      */   }
/*      */ 
/*      */   private void setContentEditable(boolean paramBoolean) {
/* 1053 */     HTMLDocument localHTMLDocument = (HTMLDocument)this.webPage.getDocument(this.webPage.getMainFrame());
/* 1054 */     HTMLElement localHTMLElement1 = (HTMLElement)localHTMLDocument.getDocumentElement();
/* 1055 */     HTMLElement localHTMLElement2 = (HTMLElement)localHTMLElement1.getElementsByTagName("body").item(0);
/* 1056 */     localHTMLElement2.setAttribute("contenteditable", Boolean.toString(paramBoolean));
/*      */   }
/*      */ 
/*      */   private boolean getCommandState(String paramString) {
/* 1060 */     return this.webPage.queryCommandState(paramString);
/*      */   }
/*      */ 
/*      */   private String getCommandValue(String paramString) {
/* 1064 */     return this.webPage.queryCommandValue(paramString);
/*      */   }
/*      */ 
/*      */   private static String rgbToHex(String paramString)
/*      */   {
/*      */     String[] arrayOfString;
/* 1068 */     if (paramString.startsWith("rgba")) {
/* 1069 */       arrayOfString = paramString.substring(paramString.indexOf(40) + 1, paramString.lastIndexOf(41)).split(",");
/* 1070 */       paramString = String.format("#%02X%02X%02X%02X", new Object[] { Integer.valueOf(Integer.parseInt(arrayOfString[0].trim())), Integer.valueOf(Integer.parseInt(arrayOfString[1].trim())), Integer.valueOf(Integer.parseInt(arrayOfString[2].trim())), Integer.valueOf(Integer.parseInt(arrayOfString[3].trim())) });
/*      */ 
/* 1078 */       if ("#00000000".equals(paramString))
/* 1079 */         return "#FFFFFFFF";
/*      */     }
/* 1081 */     else if (paramString.startsWith("rgb")) {
/* 1082 */       arrayOfString = paramString.substring(paramString.indexOf(40) + 1, paramString.lastIndexOf(41)).split(",");
/* 1083 */       paramString = String.format("#%02X%02X%02X", new Object[] { Integer.valueOf(Integer.parseInt(arrayOfString[0].trim())), Integer.valueOf(Integer.parseInt(arrayOfString[1].trim())), Integer.valueOf(Integer.parseInt(arrayOfString[2].trim())) });
/*      */     }
/*      */ 
/* 1089 */     return paramString;
/*      */   }
/*      */ 
/*      */   private void applyTextFormatting() {
/* 1093 */     if ((getCommandState("insertUnorderedList")) || (getCommandState("insertOrderedList"))) {
/* 1094 */       return;
/*      */     }
/*      */ 
/* 1097 */     if (this.webPage.getClientCommittedTextLength() == 0) {
/* 1098 */       String str1 = (String)this.formatStyleMap.get(this.formatComboBox.getValue());
/* 1099 */       String str2 = this.fontFamilyComboBox.getValue().toString();
/*      */ 
/* 1101 */       executeCommand("formatblock", str1);
/* 1102 */       executeCommand("fontname", str2);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void keyboardShortcuts(String paramString) {
/* 1107 */     if ("bold".equals(paramString))
/* 1108 */       this.boldButton.fire();
/* 1109 */     else if ("italic".equals(paramString))
/* 1110 */       this.italicButton.setSelected(!this.italicButton.isSelected());
/* 1111 */     else if ("underline".equals(paramString))
/* 1112 */       this.underlineButton.setSelected(!this.underlineButton.isSelected());
/*      */   }
/*      */ 
/*      */   public void onTraverse(Node paramNode, Bounds paramBounds)
/*      */   {
/* 1118 */     this.cutButton.requestFocus();
/*      */   }
/*      */ 
/*      */   protected void layoutChildren()
/*      */   {
/* 1123 */     super.layoutChildren();
/* 1124 */     double d = Math.max(this.toolbar1.prefWidth(-1.0D), this.toolbar2.prefWidth(-1.0D));
/* 1125 */     this.toolbar1.setMinWidth(d);
/* 1126 */     this.toolbar1.setPrefWidth(d);
/* 1127 */     this.toolbar2.setMinWidth(d);
/* 1128 */     this.toolbar2.setPrefWidth(d);
/*      */   }
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.web.skin.HTMLEditorSkin
 * JD-Core Version:    0.6.2
 */