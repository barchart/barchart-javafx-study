/*     */ package com.sun.deploy.uitoolkit.impl.fx.ui;
/*     */ 
/*     */ import com.sun.javafx.css.StyleManager;
/*     */ import java.net.URL;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.Observable;
/*     */ import javafx.beans.property.BooleanProperty;
/*     */ import javafx.beans.property.ReadOnlyBooleanProperty;
/*     */ import javafx.beans.property.StringProperty;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.event.Event;
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.event.EventType;
/*     */ import javafx.geometry.Point2D;
/*     */ import javafx.geometry.Rectangle2D;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.Scene;
/*     */ import javafx.scene.control.Button;
/*     */ import javafx.scene.control.Label;
/*     */ import javafx.scene.control.ToolBar;
/*     */ import javafx.scene.input.MouseEvent;
/*     */ import javafx.scene.layout.BorderPane;
/*     */ import javafx.scene.layout.HBox;
/*     */ import javafx.scene.layout.Pane;
/*     */ import javafx.scene.layout.Priority;
/*     */ import javafx.scene.layout.Region;
/*     */ import javafx.scene.layout.StackPane;
/*     */ import javafx.scene.paint.Color;
/*     */ import javafx.scene.shape.Rectangle;
/*     */ import javafx.stage.Modality;
/*     */ import javafx.stage.Screen;
/*     */ import javafx.stage.Stage;
/*     */ import javafx.stage.StageStyle;
/*     */ import javafx.stage.Window;
/*     */ 
/*     */ class FXDialog extends Stage
/*     */ {
/*     */   private BorderPane root;
/*     */   private RootPane decoratedRoot;
/*     */   private ToolBar toolBar;
/*     */   private HBox windowBtns;
/*     */   private Button minButton;
/*     */   private Button maxButton;
/*     */   private Rectangle resizeCorner;
/*  45 */   private double mouseDragOffsetX = 0.0D;
/*  46 */   private double mouseDragOffsetY = 0.0D;
/*     */   protected Label titleLabel;
/*     */   private static final int HEADER_HEIGHT = 28;
/*     */ 
/*     */   FXDialog(String title)
/*     */   {
/*  52 */     this(title, null, false);
/*     */   }
/*     */ 
/*     */   FXDialog(String title, Window owner, boolean modal) {
/*  56 */     this(title, owner, modal, StageStyle.TRANSPARENT); } 
/*     */   FXDialog(String title, Window owner, boolean modal, StageStyle stageStyle) { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload 4
/*     */     //   3: invokespecial 9	javafx/stage/Stage:<init>	(Ljavafx/stage/StageStyle;)V
/*     */     //   6: aload_0
/*     */     //   7: dconst_0
/*     */     //   8: putfield 2	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog:mouseDragOffsetX	D
/*     */     //   11: aload_0
/*     */     //   12: dconst_0
/*     */     //   13: putfield 1	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog:mouseDragOffsetY	D
/*     */     //   16: aload_0
/*     */     //   17: aload_1
/*     */     //   18: invokevirtual 10	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog:setTitle	(Ljava/lang/String;)V
/*     */     //   21: aload_2
/*     */     //   22: ifnull +8 -> 30
/*     */     //   25: aload_0
/*     */     //   26: aload_2
/*     */     //   27: invokevirtual 11	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog:initOwner	(Ljavafx/stage/Window;)V
/*     */     //   30: iload_3
/*     */     //   31: ifeq +10 -> 41
/*     */     //   34: aload_0
/*     */     //   35: getstatic 12	javafx/stage/Modality:WINDOW_MODAL	Ljavafx/stage/Modality;
/*     */     //   38: invokevirtual 13	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog:initModality	(Ljavafx/stage/Modality;)V
/*     */     //   41: aload_0
/*     */     //   42: invokevirtual 14	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog:resizableProperty	()Ljavafx/beans/property/BooleanProperty;
/*     */     //   45: new 15	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog$1
/*     */     //   48: dup
/*     */     //   49: aload_0
/*     */     //   50: invokespecial 16	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog$1:<init>	(Lcom/sun/deploy/uitoolkit/impl/fx/ui/FXDialog;)V
/*     */     //   53: invokevirtual 17	javafx/beans/property/BooleanProperty:addListener	(Ljavafx/beans/InvalidationListener;)V
/*     */     //   56: aload_0
/*     */     //   57: new 18	javafx/scene/layout/BorderPane
/*     */     //   60: dup
/*     */     //   61: invokespecial 19	javafx/scene/layout/BorderPane:<init>	()V
/*     */     //   64: putfield 20	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog:root	Ljavafx/scene/layout/BorderPane;
/*     */     //   67: aload 4
/*     */     //   69: getstatic 21	javafx/stage/StageStyle:DECORATED	Ljavafx/stage/StageStyle;
/*     */     //   72: if_acmpne +52 -> 124
/*     */     //   75: new 22	javafx/scene/Scene
/*     */     //   78: dup
/*     */     //   79: aload_0
/*     */     //   80: getfield 20	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog:root	Ljavafx/scene/layout/BorderPane;
/*     */     //   83: invokespecial 23	javafx/scene/Scene:<init>	(Ljavafx/scene/Parent;)V
/*     */     //   86: astore 5
/*     */     //   88: aload 5
/*     */     //   90: invokevirtual 24	javafx/scene/Scene:getStylesheets	()Ljavafx/collections/ObservableList;
/*     */     //   93: iconst_1
/*     */     //   94: anewarray 25	java/lang/String
/*     */     //   97: dup
/*     */     //   98: iconst_0
/*     */     //   99: ldc_w 26
/*     */     //   102: ldc 27
/*     */     //   104: invokevirtual 28	java/lang/Class:getResource	(Ljava/lang/String;)Ljava/net/URL;
/*     */     //   107: invokevirtual 29	java/net/URL:toExternalForm	()Ljava/lang/String;
/*     */     //   110: aastore
/*     */     //   111: invokeinterface 30 2 0
/*     */     //   116: pop
/*     */     //   117: aload_0
/*     */     //   118: aload 5
/*     */     //   120: invokevirtual 31	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog:setScene	(Ljavafx/scene/Scene;)V
/*     */     //   123: return
/*     */     //   124: aload_0
/*     */     //   125: new 32	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog$2
/*     */     //   128: dup
/*     */     //   129: aload_0
/*     */     //   130: invokespecial 33	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog$2:<init>	(Lcom/sun/deploy/uitoolkit/impl/fx/ui/FXDialog;)V
/*     */     //   133: putfield 3	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog:decoratedRoot	Lcom/sun/deploy/uitoolkit/impl/fx/ui/FXDialog$RootPane;
/*     */     //   136: aload_0
/*     */     //   137: getfield 3	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog:decoratedRoot	Lcom/sun/deploy/uitoolkit/impl/fx/ui/FXDialog$RootPane;
/*     */     //   140: invokevirtual 34	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog$RootPane:getChildren	()Ljavafx/collections/ObservableList;
/*     */     //   143: aload_0
/*     */     //   144: getfield 20	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog:root	Ljavafx/scene/layout/BorderPane;
/*     */     //   147: invokeinterface 35 2 0
/*     */     //   152: pop
/*     */     //   153: new 22	javafx/scene/Scene
/*     */     //   156: dup
/*     */     //   157: aload_0
/*     */     //   158: getfield 3	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog:decoratedRoot	Lcom/sun/deploy/uitoolkit/impl/fx/ui/FXDialog$RootPane;
/*     */     //   161: invokespecial 23	javafx/scene/Scene:<init>	(Ljavafx/scene/Parent;)V
/*     */     //   164: astore 5
/*     */     //   166: new 36	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog$3
/*     */     //   169: dup
/*     */     //   170: aload_0
/*     */     //   171: invokespecial 37	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog$3:<init>	(Lcom/sun/deploy/uitoolkit/impl/fx/ui/FXDialog;)V
/*     */     //   174: invokestatic 38	java/security/AccessController:doPrivileged	(Ljava/security/PrivilegedAction;)Ljava/lang/Object;
/*     */     //   177: checkcast 25	java/lang/String
/*     */     //   180: astore 6
/*     */     //   182: aload 5
/*     */     //   184: invokevirtual 24	javafx/scene/Scene:getStylesheets	()Ljavafx/collections/ObservableList;
/*     */     //   187: iconst_1
/*     */     //   188: anewarray 25	java/lang/String
/*     */     //   191: dup
/*     */     //   192: iconst_0
/*     */     //   193: aload 6
/*     */     //   195: aastore
/*     */     //   196: invokeinterface 30 2 0
/*     */     //   201: pop
/*     */     //   202: aload 5
/*     */     //   204: getstatic 39	javafx/scene/paint/Color:TRANSPARENT	Ljavafx/scene/paint/Color;
/*     */     //   207: invokevirtual 40	javafx/scene/Scene:setFill	(Ljavafx/scene/paint/Paint;)V
/*     */     //   210: aload_0
/*     */     //   211: aload 5
/*     */     //   213: invokevirtual 31	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog:setScene	(Ljavafx/scene/Scene;)V
/*     */     //   216: aload_0
/*     */     //   217: getfield 3	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog:decoratedRoot	Lcom/sun/deploy/uitoolkit/impl/fx/ui/FXDialog$RootPane;
/*     */     //   220: invokevirtual 41	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog$RootPane:getStyleClass	()Ljavafx/collections/ObservableList;
/*     */     //   223: ldc 42
/*     */     //   225: invokeinterface 35 2 0
/*     */     //   230: pop
/*     */     //   231: aload_0
/*     */     //   232: invokevirtual 43	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog:focusedProperty	()Ljavafx/beans/property/ReadOnlyBooleanProperty;
/*     */     //   235: new 44	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog$4
/*     */     //   238: dup
/*     */     //   239: aload_0
/*     */     //   240: invokespecial 45	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog$4:<init>	(Lcom/sun/deploy/uitoolkit/impl/fx/ui/FXDialog;)V
/*     */     //   243: invokevirtual 46	javafx/beans/property/ReadOnlyBooleanProperty:addListener	(Ljavafx/beans/InvalidationListener;)V
/*     */     //   246: aload_0
/*     */     //   247: new 47	javafx/scene/control/ToolBar
/*     */     //   250: dup
/*     */     //   251: invokespecial 48	javafx/scene/control/ToolBar:<init>	()V
/*     */     //   254: putfield 49	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog:toolBar	Ljavafx/scene/control/ToolBar;
/*     */     //   257: aload_0
/*     */     //   258: getfield 49	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog:toolBar	Ljavafx/scene/control/ToolBar;
/*     */     //   261: ldc 50
/*     */     //   263: invokevirtual 51	javafx/scene/control/ToolBar:setId	(Ljava/lang/String;)V
/*     */     //   266: aload_0
/*     */     //   267: getfield 49	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog:toolBar	Ljavafx/scene/control/ToolBar;
/*     */     //   270: ldc2_w 52
/*     */     //   273: invokevirtual 54	javafx/scene/control/ToolBar:setPrefHeight	(D)V
/*     */     //   276: aload_0
/*     */     //   277: getfield 49	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog:toolBar	Ljavafx/scene/control/ToolBar;
/*     */     //   280: ldc2_w 52
/*     */     //   283: invokevirtual 55	javafx/scene/control/ToolBar:setMinHeight	(D)V
/*     */     //   286: aload_0
/*     */     //   287: getfield 49	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog:toolBar	Ljavafx/scene/control/ToolBar;
/*     */     //   290: ldc2_w 52
/*     */     //   293: invokevirtual 56	javafx/scene/control/ToolBar:setMaxHeight	(D)V
/*     */     //   296: aload_0
/*     */     //   297: aload_0
/*     */     //   298: getfield 49	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog:toolBar	Ljavafx/scene/control/ToolBar;
/*     */     //   301: invokespecial 57	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog:addDragHandlers	(Ljavafx/scene/Node;)V
/*     */     //   304: aload_0
/*     */     //   305: new 58	javafx/scene/control/Label
/*     */     //   308: dup
/*     */     //   309: invokespecial 59	javafx/scene/control/Label:<init>	()V
/*     */     //   312: putfield 60	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog:titleLabel	Ljavafx/scene/control/Label;
/*     */     //   315: aload_0
/*     */     //   316: getfield 60	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog:titleLabel	Ljavafx/scene/control/Label;
/*     */     //   319: ldc 61
/*     */     //   321: invokevirtual 62	javafx/scene/control/Label:setId	(Ljava/lang/String;)V
/*     */     //   324: aload_0
/*     */     //   325: getfield 60	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog:titleLabel	Ljavafx/scene/control/Label;
/*     */     //   328: aload_0
/*     */     //   329: invokevirtual 63	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog:getTitle	()Ljava/lang/String;
/*     */     //   332: invokevirtual 64	javafx/scene/control/Label:setText	(Ljava/lang/String;)V
/*     */     //   335: aload_0
/*     */     //   336: invokevirtual 65	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog:titleProperty	()Ljavafx/beans/property/StringProperty;
/*     */     //   339: new 66	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog$5
/*     */     //   342: dup
/*     */     //   343: aload_0
/*     */     //   344: invokespecial 67	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog$5:<init>	(Lcom/sun/deploy/uitoolkit/impl/fx/ui/FXDialog;)V
/*     */     //   347: invokevirtual 68	javafx/beans/property/StringProperty:addListener	(Ljavafx/beans/InvalidationListener;)V
/*     */     //   350: new 69	javafx/scene/layout/Region
/*     */     //   353: dup
/*     */     //   354: invokespecial 70	javafx/scene/layout/Region:<init>	()V
/*     */     //   357: astore 7
/*     */     //   359: aload 7
/*     */     //   361: getstatic 71	javafx/scene/layout/Priority:ALWAYS	Ljavafx/scene/layout/Priority;
/*     */     //   364: invokestatic 72	javafx/scene/layout/HBox:setHgrow	(Ljavafx/scene/Node;Ljavafx/scene/layout/Priority;)V
/*     */     //   367: new 73	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog$WindowButton
/*     */     //   370: dup
/*     */     //   371: ldc 74
/*     */     //   373: invokespecial 75	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog$WindowButton:<init>	(Ljava/lang/String;)V
/*     */     //   376: astore 8
/*     */     //   378: aload 8
/*     */     //   380: new 76	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog$6
/*     */     //   383: dup
/*     */     //   384: aload_0
/*     */     //   385: invokespecial 77	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog$6:<init>	(Lcom/sun/deploy/uitoolkit/impl/fx/ui/FXDialog;)V
/*     */     //   388: invokevirtual 78	javafx/scene/control/Button:setOnAction	(Ljavafx/event/EventHandler;)V
/*     */     //   391: aload_0
/*     */     //   392: new 73	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog$WindowButton
/*     */     //   395: dup
/*     */     //   396: ldc 79
/*     */     //   398: invokespecial 75	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog$WindowButton:<init>	(Ljava/lang/String;)V
/*     */     //   401: putfield 80	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog:minButton	Ljavafx/scene/control/Button;
/*     */     //   404: aload_0
/*     */     //   405: getfield 80	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog:minButton	Ljavafx/scene/control/Button;
/*     */     //   408: new 81	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog$7
/*     */     //   411: dup
/*     */     //   412: aload_0
/*     */     //   413: invokespecial 82	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog$7:<init>	(Lcom/sun/deploy/uitoolkit/impl/fx/ui/FXDialog;)V
/*     */     //   416: invokevirtual 78	javafx/scene/control/Button:setOnAction	(Ljavafx/event/EventHandler;)V
/*     */     //   419: aload_0
/*     */     //   420: new 73	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog$WindowButton
/*     */     //   423: dup
/*     */     //   424: ldc 83
/*     */     //   426: invokespecial 75	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog$WindowButton:<init>	(Ljava/lang/String;)V
/*     */     //   429: putfield 4	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog:maxButton	Ljavafx/scene/control/Button;
/*     */     //   432: aload_0
/*     */     //   433: getfield 4	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog:maxButton	Ljavafx/scene/control/Button;
/*     */     //   436: new 84	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog$8
/*     */     //   439: dup
/*     */     //   440: aload_0
/*     */     //   441: invokespecial 85	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog$8:<init>	(Lcom/sun/deploy/uitoolkit/impl/fx/ui/FXDialog;)V
/*     */     //   444: invokevirtual 78	javafx/scene/control/Button:setOnAction	(Ljavafx/event/EventHandler;)V
/*     */     //   447: aload_0
/*     */     //   448: new 86	javafx/scene/layout/HBox
/*     */     //   451: dup
/*     */     //   452: ldc2_w 87
/*     */     //   455: invokespecial 89	javafx/scene/layout/HBox:<init>	(D)V
/*     */     //   458: putfield 90	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog:windowBtns	Ljavafx/scene/layout/HBox;
/*     */     //   461: aload_0
/*     */     //   462: getfield 90	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog:windowBtns	Ljavafx/scene/layout/HBox;
/*     */     //   465: invokevirtual 91	javafx/scene/layout/HBox:getChildren	()Ljavafx/collections/ObservableList;
/*     */     //   468: iconst_3
/*     */     //   469: anewarray 92	javafx/scene/Node
/*     */     //   472: dup
/*     */     //   473: iconst_0
/*     */     //   474: aload_0
/*     */     //   475: getfield 80	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog:minButton	Ljavafx/scene/control/Button;
/*     */     //   478: aastore
/*     */     //   479: dup
/*     */     //   480: iconst_1
/*     */     //   481: aload_0
/*     */     //   482: getfield 4	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog:maxButton	Ljavafx/scene/control/Button;
/*     */     //   485: aastore
/*     */     //   486: dup
/*     */     //   487: iconst_2
/*     */     //   488: aload 8
/*     */     //   490: aastore
/*     */     //   491: invokeinterface 30 2 0
/*     */     //   496: pop
/*     */     //   497: aload_0
/*     */     //   498: getfield 49	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog:toolBar	Ljavafx/scene/control/ToolBar;
/*     */     //   501: invokevirtual 93	javafx/scene/control/ToolBar:getItems	()Ljavafx/collections/ObservableList;
/*     */     //   504: iconst_3
/*     */     //   505: anewarray 92	javafx/scene/Node
/*     */     //   508: dup
/*     */     //   509: iconst_0
/*     */     //   510: aload_0
/*     */     //   511: getfield 60	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog:titleLabel	Ljavafx/scene/control/Label;
/*     */     //   514: aastore
/*     */     //   515: dup
/*     */     //   516: iconst_1
/*     */     //   517: aload 7
/*     */     //   519: aastore
/*     */     //   520: dup
/*     */     //   521: iconst_2
/*     */     //   522: aload_0
/*     */     //   523: getfield 90	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog:windowBtns	Ljavafx/scene/layout/HBox;
/*     */     //   526: aastore
/*     */     //   527: invokeinterface 30 2 0
/*     */     //   532: pop
/*     */     //   533: aload_0
/*     */     //   534: getfield 20	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog:root	Ljavafx/scene/layout/BorderPane;
/*     */     //   537: aload_0
/*     */     //   538: getfield 49	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog:toolBar	Ljavafx/scene/control/ToolBar;
/*     */     //   541: invokevirtual 94	javafx/scene/layout/BorderPane:setTop	(Ljavafx/scene/Node;)V
/*     */     //   544: aload_0
/*     */     //   545: new 95	javafx/scene/shape/Rectangle
/*     */     //   548: dup
/*     */     //   549: ldc2_w 96
/*     */     //   552: ldc2_w 96
/*     */     //   555: invokespecial 98	javafx/scene/shape/Rectangle:<init>	(DD)V
/*     */     //   558: putfield 5	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog:resizeCorner	Ljavafx/scene/shape/Rectangle;
/*     */     //   561: aload_0
/*     */     //   562: getfield 5	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog:resizeCorner	Ljavafx/scene/shape/Rectangle;
/*     */     //   565: ldc 99
/*     */     //   567: invokevirtual 100	javafx/scene/shape/Rectangle:setId	(Ljava/lang/String;)V
/*     */     //   570: new 101	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog$9
/*     */     //   573: dup
/*     */     //   574: aload_0
/*     */     //   575: invokespecial 102	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog$9:<init>	(Lcom/sun/deploy/uitoolkit/impl/fx/ui/FXDialog;)V
/*     */     //   578: astore 9
/*     */     //   580: aload_0
/*     */     //   581: getfield 5	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog:resizeCorner	Ljavafx/scene/shape/Rectangle;
/*     */     //   584: aload 9
/*     */     //   586: invokevirtual 103	javafx/scene/shape/Rectangle:setOnMousePressed	(Ljavafx/event/EventHandler;)V
/*     */     //   589: aload_0
/*     */     //   590: getfield 5	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog:resizeCorner	Ljavafx/scene/shape/Rectangle;
/*     */     //   593: aload 9
/*     */     //   595: invokevirtual 104	javafx/scene/shape/Rectangle:setOnMouseDragged	(Ljavafx/event/EventHandler;)V
/*     */     //   598: aload_0
/*     */     //   599: getfield 5	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog:resizeCorner	Ljavafx/scene/shape/Rectangle;
/*     */     //   602: iconst_0
/*     */     //   603: invokevirtual 105	javafx/scene/shape/Rectangle:setManaged	(Z)V
/*     */     //   606: aload_0
/*     */     //   607: getfield 3	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog:decoratedRoot	Lcom/sun/deploy/uitoolkit/impl/fx/ui/FXDialog$RootPane;
/*     */     //   610: invokevirtual 34	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog$RootPane:getChildren	()Ljavafx/collections/ObservableList;
/*     */     //   613: aload_0
/*     */     //   614: getfield 5	com/sun/deploy/uitoolkit/impl/fx/ui/FXDialog:resizeCorner	Ljavafx/scene/shape/Rectangle;
/*     */     //   617: invokeinterface 35 2 0
/*     */     //   622: pop
/*     */     //   623: return } 
/* 226 */   void setContentPane(Pane pane) { if (pane.getId() == null) {
/* 227 */       pane.setId("content-pane");
/*     */     }
/* 229 */     this.root.setCenter(pane);
/*     */   }
/*     */ 
/*     */   public void setIconifiable(boolean iconifiable)
/*     */   {
/* 234 */     this.minButton.setVisible(iconifiable);
/*     */   }
/*     */ 
/*     */   public void hideWindowTitle()
/*     */   {
/* 241 */     if (this.toolBar != null) {
/* 242 */       this.root.setTop(null);
/* 243 */       sizeToScene();
/* 244 */       addDragHandlers(this.root);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void addDragHandlers(Node node)
/*     */   {
/* 252 */     node.setOnMousePressed(new EventHandler() {
/*     */       public void handle(MouseEvent event) {
/* 254 */         FXDialog.this.mouseDragOffsetX = event.getSceneX();
/* 255 */         FXDialog.this.mouseDragOffsetY = event.getSceneY();
/*     */       }
/*     */     });
/* 258 */     node.setOnMouseDragged(new EventHandler() {
/*     */       public void handle(MouseEvent event) {
/* 260 */         FXDialog.this.setX(event.getScreenX() - FXDialog.this.mouseDragOffsetX);
/* 261 */         FXDialog.this.setY(event.getScreenY() - FXDialog.this.mouseDragOffsetY);
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public static Button createCloseButton()
/*     */   {
/* 270 */     return new WindowButton("black-close");
/*     */   }
/*     */ 
/*     */   private static class RootPane extends StackPane
/*     */   {
/*     */     private static final String PSEUDO_CLASS_ACTIVE = "active";
/* 293 */     private static final long PSEUDO_CLASS_ACTIVE_MASK = StyleManager.getInstance().getPseudoclassMask("active");
/*     */ 
/*     */     public long impl_getPseudoClassState() {
/* 296 */       long mask = super.impl_getPseudoClassState();
/* 297 */       if (getScene().getWindow().isFocused()) {
/* 298 */         mask |= PSEUDO_CLASS_ACTIVE_MASK;
/*     */       }
/* 300 */       return mask;
/*     */     }
/*     */ 
/*     */     private void pseudoClassStateChanged(String pseudoClass) {
/* 304 */       impl_pseudoClassStateChanged(pseudoClass);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class WindowButton extends Button
/*     */   {
/*     */     WindowButton(String name)
/*     */     {
/* 275 */       getStyleClass().setAll(new String[] { "window-button" });
/* 276 */       setId("window-" + name + "-button");
/* 277 */       StackPane graphic = new StackPane();
/* 278 */       graphic.getStyleClass().setAll(new String[] { "graphic" });
/* 279 */       setGraphic(graphic);
/* 280 */       setMinSize(17.0D, 17.0D);
/* 281 */       setPrefSize(17.0D, 17.0D);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.deploy.uitoolkit.impl.fx.ui.FXDialog
 * JD-Core Version:    0.6.2
 */