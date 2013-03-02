/*      */ package com.sun.deploy.uitoolkit.impl.fx.ui;
/*      */ 
/*      */ import com.sun.deploy.trace.Trace;
/*      */ import com.sun.deploy.ui.AppInfo;
/*      */ import com.sun.deploy.uitoolkit.impl.fx.ui.resources.ResourceManager;
/*      */ import com.sun.javafx.applet.HostServicesImpl;
/*      */ import com.sun.javafx.application.HostServicesDelegate;
/*      */ import java.net.URL;
/*      */ import java.security.cert.Certificate;
/*      */ import java.security.cert.X509Certificate;
/*      */ import java.text.MessageFormat;
/*      */ import java.util.Collection;
/*      */ import java.util.Iterator;
/*      */ import java.util.TreeMap;
/*      */ import javafx.collections.ObservableList;
/*      */ import javafx.event.ActionEvent;
/*      */ import javafx.event.Event;
/*      */ import javafx.event.EventHandler;
/*      */ import javafx.geometry.HPos;
/*      */ import javafx.geometry.Insets;
/*      */ import javafx.geometry.Pos;
/*      */ import javafx.scene.Cursor;
/*      */ import javafx.scene.Node;
/*      */ import javafx.scene.control.Button;
/*      */ import javafx.scene.control.CheckBox;
/*      */ import javafx.scene.control.Hyperlink;
/*      */ import javafx.scene.control.Label;
/*      */ import javafx.scene.control.ListView;
/*      */ import javafx.scene.control.MultipleSelectionModel;
/*      */ import javafx.scene.control.PasswordField;
/*      */ import javafx.scene.control.ProgressBar;
/*      */ import javafx.scene.control.RadioButton;
/*      */ import javafx.scene.control.ScrollPane;
/*      */ import javafx.scene.control.TextField;
/*      */ import javafx.scene.control.ToggleGroup;
/*      */ import javafx.scene.control.Tooltip;
/*      */ import javafx.scene.image.Image;
/*      */ import javafx.scene.image.ImageView;
/*      */ import javafx.scene.layout.BorderPane;
/*      */ import javafx.scene.layout.FlowPane;
/*      */ import javafx.scene.layout.GridPane;
/*      */ import javafx.scene.layout.HBox;
/*      */ import javafx.scene.layout.Pane;
/*      */ import javafx.scene.layout.Priority;
/*      */ import javafx.scene.layout.StackPane;
/*      */ import javafx.scene.layout.VBox;
/*      */ import javafx.scene.paint.Color;
/*      */ import javafx.stage.Modality;
/*      */ import javafx.stage.Stage;
/*      */ 
/*      */ public class DialogTemplate
/*      */ {
/*   54 */   int theAnswer = -1;
/*   55 */   final Object responseLock = new Object();
/*      */   private static final int RISK_LABEL_WIDTH = 40;
/*      */   private static final int RISK_TEXT_WIDTH = 490;
/* 1686 */   private EventHandler<ActionEvent> okHandler = new EventHandler() {
/*      */     public void handle(ActionEvent ae) {
/* 1688 */       DialogTemplate.this.userAnswer = 0;
/*      */ 
/* 1690 */       if ((DialogTemplate.this.always != null) && (DialogTemplate.this.always.isSelected())) {
/* 1691 */         DialogTemplate.this.userAnswer = 2;
/*      */       }
/* 1693 */       if (DialogTemplate.this.stayAliveOnOk)
/*      */       {
/* 1696 */         return;
/*      */       }
/* 1698 */       if (DialogTemplate.this.password != null) {
/* 1699 */         DialogTemplate.this.pwd = DialogTemplate.this.password.getText().toCharArray();
/*      */       }
/* 1701 */       if (DialogTemplate.this.pwdName != null) {
/* 1702 */         DialogTemplate.this.userName = DialogTemplate.this.pwdName.getText();
/*      */       }
/* 1704 */       if (DialogTemplate.this.pwdDomain != null) {
/* 1705 */         DialogTemplate.this.domain = DialogTemplate.this.pwdDomain.getText();
/*      */       }
/* 1707 */       if (DialogTemplate.this.scrollList != null) {
/* 1708 */         DialogTemplate.this.userAnswer = DialogTemplate.this.scrollList.getSelectionModel().getSelectedIndex();
/*      */       }
/*      */ 
/* 1714 */       DialogTemplate.this.setVisible(false);
/*      */     }
/* 1686 */   };
/*      */ 
/* 1718 */   private EventHandler<ActionEvent> cancelHandler = new EventHandler()
/*      */   {
/*      */     public void handle(ActionEvent ae) {
/* 1721 */       if ((DialogTemplate.this.throwable != null) || (DialogTemplate.this.detailPanel != null)) {
/* 1722 */         DialogTemplate.this.showMoreInfo();
/* 1723 */         return;
/*      */       }
/* 1725 */       DialogTemplate.this.userAnswer = 1;
/*      */ 
/* 1730 */       if (DialogTemplate.this.scrollList != null) {
/* 1731 */         DialogTemplate.this.userAnswer = -1;
/*      */       }
/*      */ 
/* 1737 */       DialogTemplate.this.setVisible(false);
/*      */     }
/* 1718 */   };
/*      */ 
/* 1741 */   EventHandler<ActionEvent> acceptRiskHandler = new EventHandler() {
/*      */     public void handle(ActionEvent ae) {
/* 1743 */       boolean accepted = DialogTemplate.this.acceptRisk.isSelected();
/* 1744 */       DialogTemplate.this.okBtn.setDisable(!accepted);
/* 1745 */       DialogTemplate.this.okBtn.setDefaultButton(accepted);
/* 1746 */       DialogTemplate.this.cancelBtn.setDefaultButton(!accepted);
/* 1747 */       if (DialogTemplate.this.always != null) {
/* 1748 */         DialogTemplate.this.always.setSelected(false);
/* 1749 */         DialogTemplate.this.always.setDisable(!accepted);
/*      */       }
/*      */     }
/* 1741 */   };
/*      */ 
/* 1754 */   EventHandler<ActionEvent> expandHandler = new EventHandler() {
/*      */     public void handle(ActionEvent ae) {
/* 1756 */       if (ae.getSource() == DialogTemplate.this.expandBtn) {
/* 1757 */         DialogTemplate.this.expandPanel.setTop(DialogTemplate.this.collapseBtn);
/* 1758 */         DialogTemplate.this.expandPanel.setBottom(DialogTemplate.this.always);
/* 1759 */         if (DialogTemplate.this.acceptRisk != null)
/* 1760 */           DialogTemplate.this.always.setDisable(!DialogTemplate.this.acceptRisk.isSelected());
/*      */       }
/* 1762 */       else if (ae.getSource() == DialogTemplate.this.collapseBtn) {
/* 1763 */         DialogTemplate.this.expandPanel.setTop(DialogTemplate.this.expandBtn);
/* 1764 */         DialogTemplate.this.expandPanel.setBottom(null);
/*      */       }
/* 1766 */       DialogTemplate.this.dialog.sizeToScene();
/*      */     }
/* 1754 */   };
/*      */ 
/* 1770 */   EventHandler moreInfoHandler = new EventHandler() {
/*      */     public void handle(Event e) {
/* 1772 */       DialogTemplate.this.showMoreInfo();
/*      */     }
/* 1770 */   };
/*      */ 
/* 1776 */   EventHandler closeHandler = new EventHandler() {
/*      */     public void handle(Event event) {
/* 1778 */       DialogTemplate.this.dialog.hide();
/*      */     }
/* 1776 */   };
/*      */ 
/* 1879 */   private FXDialog dialog = null;
/* 1880 */   private VBox contentPane = null;
/* 1881 */   private AppInfo ainfo = null;
/* 1882 */   private String topText = null;
/* 1883 */   private String appTitle = null;
/* 1884 */   private String appPublisher = null;
/* 1885 */   private URL appURL = null;
/* 1886 */   private boolean useErrorIcon = false;
/* 1887 */   private boolean useWarningIcon = false;
/* 1888 */   private boolean useInfoIcon = false;
/* 1889 */   private boolean useMixcodeIcon = false;
/*      */ 
/* 1891 */   private Label progressStatusLabel = null;
/*      */   private BorderPane topPanel;
/*      */   private Pane centerPanel;
/*      */   private BorderPane expandPanel;
/*      */   private ImageView topIcon;
/*      */   private ImageView securityIcon;
/*      */   private Label nameInfo;
/*      */   private Label publisherInfo;
/*      */   private Label urlInfo;
/*      */   private Button okBtn;
/*      */   private Button cancelBtn;
/*      */   private Button expandBtn;
/*      */   private Button collapseBtn;
/*      */   private CheckBox always;
/*      */   private CheckBox acceptRisk;
/*      */   private Label mixedCodeLabel;
/* 1902 */   private UITextArea masthead1 = null;
/* 1903 */   private UITextArea masthead2 = null;
/*      */   private static final int ICON_SIZE = 48;
/* 1909 */   private int userAnswer = -1;
/*      */   static final int DIALOG_WIDTH = 516;
/* 1913 */   private final int MAX_LARGE_SCROLL_WIDTH = 600;
/*      */ 
/* 1917 */   private final String SECURITY_ALERT_HIGH = "security.alert.high.image";
/* 1918 */   private final String SECURITY_ALERT_LOW = "security.alert.low.image";
/*      */ 
/* 1922 */   private static int MAIN_TEXT_WIDTH = 400;
/*      */ 
/* 1924 */   private final String OK_ACTION = "OK";
/*      */ 
/* 1926 */   private final int MAX_BUTTONS = 2;
/*      */   private int start;
/*      */   private int end;
/*      */   private Certificate[] certs;
/*      */   private String[] alertStrs;
/*      */   private String[] infoStrs;
/*      */   private int securityInfoCount;
/*      */   private Color originalColor;
/* 1938 */   private Cursor originalCursor = null;
/*      */ 
/* 1940 */   protected ProgressBar progressBar = null;
/* 1941 */   private boolean stayAliveOnOk = false;
/* 1942 */   private String contentString = null;
/* 1943 */   private String cacheUpgradeContentString = null;
/* 1944 */   private String contentLabel = null;
/* 1945 */   private String alwaysString = null;
/* 1946 */   private String mixedCodeString = null;
/* 1947 */   private boolean contentScroll = false;
/* 1948 */   private boolean includeMasthead = true;
/* 1949 */   private boolean includeAppInfo = true;
/* 1950 */   private boolean largeScroll = false;
/* 1951 */   private Throwable throwable = null;
/* 1952 */   private Pane detailPanel = null;
/* 1953 */   private char[] pwd = new char[0];
/*      */   private String userName;
/*      */   private String domain;
/*      */   private TextField pwdName;
/*      */   private TextField pwdDomain;
/*      */   private PasswordField password;
/*      */   private ListView scrollList;
/* 1959 */   private boolean showDetails = false;
/*      */   TreeMap clientAuthCertsMap;
/* 1961 */   private boolean majorWarning = false;
/*      */   private String okBtnStr;
/*      */   private String cancelBtnStr;
/*      */ 
/*      */   DialogTemplate(AppInfo ainfo, Stage owner, String title, String topText)
/*      */   {
/*   67 */     Stage parent = owner;
/*   68 */     this.dialog = new FXDialog(title, parent, false);
/*   69 */     this.contentPane = new VBox() {
/*      */       protected double computePrefHeight(double width) {
/*   71 */         double h = super.computePrefHeight(width);
/*      */ 
/*   73 */         return h;
/*      */       }
/*      */     };
/*   77 */     this.dialog.setContentPane(this.contentPane);
/*      */ 
/*   79 */     this.ainfo = ainfo;
/*   80 */     this.topText = topText;
/*   81 */     this.appTitle = ainfo.getTitle();
/*   82 */     this.appPublisher = ainfo.getVendor();
/*   83 */     this.appURL = ainfo.getFrom();
/*      */   }
/*      */ 
/*      */   void setNewSecurityContent(boolean showAlways, boolean checkAlways, String okBtnStr, String cancelBtnStr, String[] alerts, String[] info, int securityInfoCount, boolean showMoreInfo, Certificate[] certs, int start, int end, boolean majorWarning)
/*      */   {
/*   99 */     this.certs = certs;
/*  100 */     this.start = start;
/*  101 */     this.end = end;
/*  102 */     this.alertStrs = alerts;
/*  103 */     this.infoStrs = info;
/*  104 */     this.securityInfoCount = securityInfoCount;
/*  105 */     this.majorWarning = majorWarning;
/*  106 */     this.okBtnStr = okBtnStr;
/*  107 */     this.cancelBtnStr = cancelBtnStr;
/*      */ 
/*  109 */     if ((alerts != null) && (alerts.length > 0)) {
/*  110 */       this.useWarningIcon = true;
/*      */     }
/*      */     try
/*      */     {
/*  114 */       this.contentPane.setId("security-content-panel");
/*      */ 
/*  117 */       this.dialog.initModality(Modality.APPLICATION_MODAL);
/*      */ 
/*  119 */       this.contentPane.getChildren().add(createSecurityTopPanel());
/*  120 */       this.contentPane.getChildren().add(createSecurityCenterPanel());
/*  121 */       this.contentPane.getChildren().add(createSecurityBottomPanel());
/*      */ 
/*  123 */       this.dialog.setResizable(false);
/*  124 */       this.dialog.setIconifiable(false);
/*      */ 
/*  126 */       if (this.alertStrs == null)
/*  127 */         this.dialog.hideWindowTitle();
/*      */     }
/*      */     catch (Throwable t) {
/*  130 */       Trace.ignored(t);
/*      */     }
/*      */   }
/*      */ 
/*      */   private Pane createSecurityTopPanel() {
/*  135 */     BorderPane panel = new BorderPane();
/*      */ 
/*  137 */     panel.setTop(createSecurityTopMastheadPanel());
/*  138 */     panel.setBottom(createSecurityTopIconLabelsPanel());
/*      */ 
/*  140 */     return panel;
/*      */   }
/*      */ 
/*      */   private Pane createSecurityTopMastheadPanel()
/*      */   {
/*  147 */     BorderPane panel = new BorderPane();
/*      */ 
/*  149 */     this.masthead1 = new UITextArea(MAIN_TEXT_WIDTH);
/*  150 */     this.masthead1.setId("security-masthead-label");
/*  151 */     this.masthead1.setText(this.topText.trim());
/*      */ 
/*  153 */     panel.setLeft(this.masthead1);
/*  154 */     if (this.alertStrs == null) {
/*  155 */       Button closeButton = FXDialog.createCloseButton();
/*  156 */       closeButton.setOnAction(this.closeHandler);
/*  157 */       panel.setRight(closeButton);
/*      */     }
/*      */ 
/*  160 */     return panel;
/*      */   }
/*      */ 
/*      */   private Pane createSecurityTopIconLabelsPanel()
/*      */   {
/*  167 */     BorderPane panel = new BorderPane();
/*  168 */     if (this.alertStrs != null)
/*  169 */       this.topIcon = ResourceManager.getIcon("warning48s.image");
/*      */     else {
/*  171 */       this.topIcon = ResourceManager.getIcon("java48s.image");
/*      */     }
/*      */ 
/*  174 */     Label iconLabel = new Label(null, this.topIcon);
/*  175 */     iconLabel.setId("security-top-icon-label");
/*  176 */     panel.setLeft(iconLabel);
/*      */ 
/*  178 */     GridPane labelsPanel = new GridPane();
/*  179 */     labelsPanel.setId("security-top-labels-grid");
/*      */ 
/*  181 */     String nameStr = ResourceManager.getMessage("dialog.template.name");
/*  182 */     Label nameLbl = new Label(nameStr);
/*  183 */     nameLbl.setId("security-name-label");
/*      */ 
/*  185 */     String publisherStr = ResourceManager.getMessage("dialog.template.publisher");
/*  186 */     Label publisherLbl = new Label(publisherStr);
/*  187 */     publisherLbl.setId("security-publisher-label");
/*      */ 
/*  189 */     String fromStr = ResourceManager.getMessage("dialog.template.from");
/*  190 */     Label fromLbl = new Label(fromStr);
/*  191 */     fromLbl.setId("security-from-label");
/*      */ 
/*  193 */     this.nameInfo = new Label();
/*  194 */     this.nameInfo.setId("security-name-value");
/*      */ 
/*  196 */     this.publisherInfo = new Label();
/*      */ 
/*  198 */     this.urlInfo = new Label();
/*      */ 
/*  200 */     if (this.appTitle != null) {
/*  201 */       GridPane.setConstraints(nameLbl, 0, 0);
/*  202 */       GridPane.setHalignment(nameLbl, HPos.RIGHT);
/*  203 */       labelsPanel.getChildren().add(nameLbl);
/*  204 */       GridPane.setConstraints(this.nameInfo, 1, 0);
/*  205 */       labelsPanel.getChildren().add(this.nameInfo);
/*      */     }
/*  207 */     if (this.appPublisher != null) {
/*  208 */       GridPane.setConstraints(publisherLbl, 0, 1);
/*  209 */       GridPane.setHalignment(publisherLbl, HPos.RIGHT);
/*  210 */       labelsPanel.getChildren().add(publisherLbl);
/*  211 */       GridPane.setConstraints(this.publisherInfo, 1, 1);
/*  212 */       labelsPanel.getChildren().add(this.publisherInfo);
/*      */     }
/*  214 */     if ((this.appTitle != null) && (this.appURL != null)) {
/*  215 */       GridPane.setConstraints(fromLbl, 0, 2);
/*  216 */       GridPane.setHalignment(fromLbl, HPos.RIGHT);
/*  217 */       labelsPanel.getChildren().add(fromLbl);
/*  218 */       GridPane.setConstraints(this.urlInfo, 1, 2);
/*  219 */       labelsPanel.getChildren().add(this.urlInfo);
/*      */     }
/*      */ 
/*  222 */     setInfo(this.appTitle, this.appPublisher, this.appURL);
/*  223 */     panel.setCenter(labelsPanel);
/*      */ 
/*  225 */     return panel;
/*      */   }
/*      */ 
/*      */   private Pane createSecurityCenterPanel() {
/*  229 */     BorderPane panel = new BorderPane();
/*  230 */     panel.setTop(createSecurityRiskPanel());
/*  231 */     if (this.majorWarning)
/*  232 */       panel.setBottom(createSecurityAcceptRiskPanel());
/*      */     else {
/*  234 */       panel.setBottom(createSecurityAlwaysPanel());
/*      */     }
/*  236 */     return panel;
/*      */   }
/*      */ 
/*      */   private Pane createSecurityRiskPanel()
/*      */   {
/*  247 */     BorderPane panel = new BorderPane();
/*  248 */     panel.setId("security-risk-panel");
/*      */ 
/*  250 */     if (this.majorWarning) {
/*  251 */       String riskMsg = this.alertStrs[0];
/*      */ 
/*  253 */       int index = riskMsg.indexOf(" ");
/*  254 */       String riskLabelText = riskMsg.substring(0, index);
/*  255 */       String riskText = riskMsg.substring(index + 1);
/*      */ 
/*  257 */       UITextArea riskLabelArea = new UITextArea(40.0D);
/*  258 */       riskLabelArea.setText(riskLabelText);
/*  259 */       riskLabelArea.setId("security-risk-label");
/*  260 */       panel.setLeft(riskLabelArea);
/*      */ 
/*  262 */       BorderPane riskPanelRight = new BorderPane();
/*  263 */       UITextArea riskTextArea = new UITextArea(490.0D);
/*  264 */       riskTextArea.setId("security-risk-value");
/*  265 */       riskTextArea.setText(riskText);
/*      */ 
/*  267 */       riskPanelRight.setTop(riskTextArea);
/*  268 */       riskPanelRight.setBottom(createMoreInfoHyperlink());
/*  269 */       panel.setRight(riskPanelRight);
/*      */     } else {
/*  271 */       String riskMsg = ResourceManager.getMessage("security.dialog.valid.signed.risk");
/*      */ 
/*  273 */       UITextArea riskTextArea = new UITextArea(490.0D);
/*  274 */       riskTextArea.setId("security-risk-value");
/*  275 */       riskTextArea.setText(riskMsg);
/*  276 */       panel.setLeft(riskTextArea);
/*      */     }
/*      */ 
/*  279 */     return panel;
/*      */   }
/*      */ 
/*      */   private Hyperlink createMoreInfoHyperlink()
/*      */   {
/*  286 */     String moreInfoStr = ResourceManager.getMessage("dialog.template.more.info2");
/*  287 */     Hyperlink moreInfoLink = new Hyperlink(moreInfoStr);
/*  288 */     moreInfoLink.setMnemonicParsing(true);
/*  289 */     moreInfoLink.setId("security-more-info-link");
/*  290 */     moreInfoLink.setOnAction(this.moreInfoHandler);
/*  291 */     return moreInfoLink;
/*      */   }
/*      */ 
/*      */   private Pane createSecurityAcceptRiskPanel()
/*      */   {
/*  298 */     BorderPane panel = new BorderPane();
/*      */ 
/*  300 */     String acceptTitle = ResourceManager.getMessage("security.dialog.accept.title");
/*  301 */     String acceptText = ResourceManager.getMessage("security.dialog.accept.text");
/*      */ 
/*  303 */     UITextArea acceptLabel = new UITextArea(MAIN_TEXT_WIDTH);
/*  304 */     acceptLabel.setText(acceptTitle);
/*      */ 
/*  306 */     panel.setTop(acceptLabel);
/*      */ 
/*  308 */     BorderPane bottomPanel = new BorderPane();
/*  309 */     bottomPanel.setId("security-accept-risk-panel");
/*      */ 
/*  311 */     this.acceptRisk = new CheckBox(acceptText);
/*  312 */     this.acceptRisk.setSelected(false);
/*  313 */     this.acceptRisk.setOnAction(this.acceptRiskHandler);
/*      */ 
/*  315 */     bottomPanel.setLeft(this.acceptRisk);
/*  316 */     bottomPanel.setRight(createSecurityOkCancelButtons());
/*      */ 
/*  318 */     panel.setBottom(bottomPanel);
/*      */ 
/*  320 */     return panel;
/*      */   }
/*      */ 
/*      */   private Pane createSecurityOkCancelButtons()
/*      */   {
/*  327 */     HBox buttonsPanel = new HBox();
/*  328 */     buttonsPanel.getStyleClass().add("security-button-bar");
/*  329 */     this.okBtn = new Button(this.okBtnStr);
/*  330 */     this.okBtn.setOnAction(this.okHandler);
/*  331 */     buttonsPanel.getChildren().add(this.okBtn);
/*      */ 
/*  333 */     this.cancelBtn = new Button(this.cancelBtnStr);
/*  334 */     this.cancelBtn.setOnAction(this.cancelHandler);
/*  335 */     buttonsPanel.getChildren().add(this.cancelBtn);
/*      */ 
/*  337 */     if (this.majorWarning) {
/*  338 */       this.okBtn.setDisable(true);
/*  339 */       this.cancelBtn.setDefaultButton(true);
/*      */     } else {
/*  341 */       this.okBtn.setDefaultButton(true);
/*      */     }
/*      */ 
/*  344 */     return buttonsPanel;
/*      */   }
/*      */ 
/*      */   private Pane createSecurityAlwaysPanel()
/*      */   {
/*  351 */     BorderPane panel = new BorderPane();
/*  352 */     panel.setLeft(createSecurityAlwaysCheckbox());
/*  353 */     return panel;
/*      */   }
/*      */ 
/*      */   private CheckBox createSecurityAlwaysCheckbox()
/*      */   {
/*  360 */     this.alwaysString = ResourceManager.getMessage("security.dialog.always");
/*  361 */     this.always = new CheckBox(this.alwaysString);
/*  362 */     if (this.majorWarning) {
/*  363 */       this.always.setId("security-always-trust-checkbox");
/*      */     }
/*  365 */     this.always.setSelected(false);
/*  366 */     this.always.setVisible(true);
/*  367 */     return this.always;
/*      */   }
/*      */ 
/*      */   private Pane createSecurityBottomPanel() {
/*  371 */     if (this.majorWarning) {
/*  372 */       return createSecurityBottomExpandPanel();
/*      */     }
/*  374 */     return createSecurityBottomMoreInfoPanel();
/*      */   }
/*      */ 
/*      */   private Pane createSecurityBottomExpandPanel()
/*      */   {
/*  383 */     this.expandPanel = new BorderPane();
/*  384 */     this.expandPanel.setId("security-bottom-panel");
/*      */ 
/*  386 */     this.always = createSecurityAlwaysCheckbox();
/*      */ 
/*  388 */     ImageView downIcon = ResourceManager.getIcon("toggle_down2.image");
/*  389 */     String showStr = ResourceManager.getMessage("security.dialog.show.options");
/*  390 */     this.expandBtn = new Button(showStr, downIcon);
/*  391 */     this.expandBtn.setId("security-expand-button");
/*  392 */     this.expandBtn.setOnAction(this.expandHandler);
/*      */ 
/*  394 */     ImageView upIcon = ResourceManager.getIcon("toggle_up2.image");
/*  395 */     String hideStr = ResourceManager.getMessage("security.dialog.hide.options");
/*  396 */     this.collapseBtn = new Button(hideStr, upIcon);
/*  397 */     this.collapseBtn.setId("security-expand-button");
/*  398 */     this.collapseBtn.setOnAction(this.expandHandler);
/*      */ 
/*  400 */     this.expandPanel.setTop(this.expandBtn);
/*      */ 
/*  402 */     return this.expandPanel;
/*      */   }
/*      */ 
/*      */   private Pane createSecurityBottomMoreInfoPanel()
/*      */   {
/*  409 */     BorderPane panel = new BorderPane();
/*  410 */     panel.setId("security-bottom-panel");
/*      */ 
/*  412 */     HBox iconLinkPanel = new HBox();
/*  413 */     if (this.alertStrs != null)
/*  414 */       this.securityIcon = ResourceManager.getIcon("yellowShield.image");
/*      */     else {
/*  416 */       this.securityIcon = ResourceManager.getIcon("blueShield.image");
/*      */     }
/*  418 */     iconLinkPanel.getChildren().add(this.securityIcon);
/*      */ 
/*  420 */     BorderPane moreInfoPane = new BorderPane();
/*  421 */     moreInfoPane.setId("security-more-info-panel");
/*      */ 
/*  423 */     if (this.alertStrs != null) {
/*  424 */       moreInfoPane.setBottom(createMoreInfoHyperlink());
/*  425 */       String expiredStr = ResourceManager.getMessage("security.dialog.expired.signed.label");
/*  426 */       Label expiredLbl = new Label(expiredStr);
/*  427 */       moreInfoPane.setTop(expiredLbl);
/*      */     } else {
/*  429 */       moreInfoPane.setCenter(createMoreInfoHyperlink());
/*      */     }
/*  431 */     iconLinkPanel.getChildren().add(moreInfoPane);
/*      */ 
/*  433 */     panel.setTop(createSecurityOkCancelButtons());
/*  434 */     panel.setBottom(iconLinkPanel);
/*      */ 
/*  436 */     return panel;
/*      */   }
/*      */ 
/*      */   void setSecurityContent(boolean showAlways, boolean checkAlways, String okBtnStr, String cancelBtnStr, String[] alerts, String[] info, int securityInfoCount, boolean showMoreInfo, Certificate[] certs, int start, int end, boolean majorWarning)
/*      */   {
/*  445 */     this.certs = certs;
/*  446 */     this.start = start;
/*  447 */     this.end = end;
/*  448 */     this.alertStrs = alerts;
/*  449 */     this.infoStrs = info;
/*  450 */     this.securityInfoCount = securityInfoCount;
/*  451 */     this.majorWarning = majorWarning;
/*      */ 
/*  453 */     if ((alerts != null) && (alerts.length > 0)) {
/*  454 */       this.useWarningIcon = true;
/*      */     }
/*      */     try
/*      */     {
/*  458 */       this.contentPane.getChildren().add(createTopPanel(false));
/*      */ 
/*  461 */       this.dialog.initModality(Modality.APPLICATION_MODAL);
/*      */ 
/*  463 */       if (showAlways) {
/*  464 */         this.alwaysString = ResourceManager.getMessage("security.dialog.always");
/*      */       }
/*  466 */       this.contentPane.getChildren().add(createCenterPanel(checkAlways, okBtnStr, cancelBtnStr, -1));
/*  467 */       this.contentPane.getChildren().add(createBottomPanel(showMoreInfo));
/*      */ 
/*  469 */       this.dialog.setResizable(false);
/*  470 */       this.dialog.setIconifiable(false);
/*      */     } catch (Throwable t) {
/*  472 */       t.printStackTrace();
/*      */     }
/*      */   }
/*      */ 
/*      */   void setSSVContent(String message, String moreInfoText, URL moreInfoURL, String choiceText, String choice1Label, String choice2Label, String btnOneLabel, String btnTwoLabel)
/*      */   {
/*      */     try
/*      */     {
/*  487 */       BorderPane content = new BorderPane();
/*  488 */       content.setId("ssv-content-panel");
/*      */ 
/*  492 */       this.dialog.initModality(Modality.APPLICATION_MODAL);
/*      */ 
/*  494 */       this.contentPane.getChildren().add(content);
/*  495 */       content.setTop(createSSVTopPanel(this.topText, this.appTitle, "ainfo.getDisplayFrom()"));
/*      */ 
/*  498 */       BorderPane riskPanel = createSSVRiskPanel(message, moreInfoText, moreInfoURL);
/*      */ 
/*  500 */       final SSVChoicePanel choicePanel = new SSVChoicePanel(choiceText, choice1Label, choice2Label);
/*      */ 
/*  502 */       BorderPane centerPanel = new BorderPane();
/*  503 */       centerPanel.setTop(riskPanel);
/*  504 */       centerPanel.setBottom(choicePanel);
/*      */ 
/*  506 */       content.setCenter(centerPanel);
/*      */ 
/*  508 */       FlowPane buttonsPanel = new FlowPane(6.0D, 0.0D);
/*  509 */       buttonsPanel.getStyleClass().add("button-bar");
/*      */ 
/*  512 */       this.okBtn = new Button(btnOneLabel);
/*  513 */       this.okBtn.setOnAction(new EventHandler() {
/*      */         public void handle(ActionEvent ae) {
/*  515 */           if (choicePanel.getSelection() == 0)
/*  516 */             DialogTemplate.this.setUserAnswer(2);
/*      */           else {
/*  518 */             DialogTemplate.this.setUserAnswer(0);
/*      */           }
/*  520 */           DialogTemplate.this.setVisible(false);
/*      */         }
/*      */       });
/*  523 */       buttonsPanel.getChildren().add(this.okBtn);
/*      */ 
/*  525 */       this.cancelBtn = new Button(btnTwoLabel);
/*  526 */       this.cancelBtn.setOnAction(new EventHandler() {
/*      */         public void handle(ActionEvent ae) {
/*  528 */           DialogTemplate.this.cancelAction();
/*      */         }
/*      */       });
/*  531 */       this.cancelBtn.setCancelButton(true);
/*      */ 
/*  533 */       buttonsPanel.getChildren().add(this.cancelBtn);
/*  534 */       this.okBtn.setDefaultButton(true);
/*      */ 
/*  536 */       content.setBottom(buttonsPanel);
/*  537 */       this.dialog.setResizable(false);
/*  538 */       this.dialog.setIconifiable(false);
/*      */     }
/*      */     catch (Throwable t)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   void setSimpleContent(String contentString, boolean contentScroll, String infoString, String okBtnStr, String cancelBtnStr, boolean includeTop, boolean useWarningIcon)
/*      */   {
/*  549 */     this.contentString = contentString;
/*  550 */     this.contentScroll = contentScroll;
/*      */ 
/*  552 */     this.includeMasthead = includeTop;
/*  553 */     this.includeAppInfo = includeTop;
/*  554 */     this.largeScroll = (!includeTop);
/*      */ 
/*  556 */     this.useWarningIcon = useWarningIcon;
/*  557 */     if (infoString != null) {
/*  558 */       String[] strs = { infoString };
/*  559 */       if (useWarningIcon)
/*  560 */         this.alertStrs = strs;
/*      */       else {
/*  562 */         this.infoStrs = strs;
/*      */       }
/*      */     }
/*      */     try
/*      */     {
/*  567 */       this.contentPane.getChildren().add(createTopPanel(false));
/*  568 */       this.contentPane.getChildren().add(createCenterPanel(false, okBtnStr, cancelBtnStr, -1));
/*  569 */       this.contentPane.getChildren().add(createBottomPanel(false));
/*      */ 
/*  571 */       this.dialog.setResizable(contentScroll);
/*      */     }
/*      */     catch (Throwable t)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   void setMixedCodeContent(String contentString, boolean contentScroll, String infoString, String bottomString, String okBtnStr, String cancelBtnStr, boolean includeTop, boolean useWarningIcon)
/*      */   {
/*  582 */     this.contentString = contentString;
/*  583 */     this.contentScroll = contentScroll;
/*      */ 
/*  585 */     this.includeMasthead = includeTop;
/*  586 */     this.includeAppInfo = includeTop;
/*  587 */     this.largeScroll = (!includeTop);
/*      */ 
/*  589 */     this.useMixcodeIcon = true;
/*      */ 
/*  591 */     this.alertStrs = new String[1];
/*  592 */     String[] botStr = { bottomString };
/*  593 */     this.alertStrs = botStr;
/*      */ 
/*  595 */     this.infoStrs = new String[3];
/*  596 */     String aStr = ResourceManager.getString("security.dialog.mixcode.info1");
/*  597 */     String bStr = ResourceManager.getString("security.dialog.mixcode.info2");
/*  598 */     String cStr = ResourceManager.getString("security.dialog.mixcode.info3");
/*      */ 
/*  600 */     String[] strs = { aStr, bStr, cStr };
/*  601 */     this.infoStrs = strs;
/*      */     try
/*      */     {
/*  604 */       this.contentPane.getChildren().add(createTopPanel(false));
/*  605 */       this.mixedCodeString = infoString;
/*  606 */       this.contentPane.getChildren().add(createCenterPanel(false, okBtnStr, cancelBtnStr, -1));
/*  607 */       this.contentPane.getChildren().add(createBottomPanel(false));
/*      */ 
/*  610 */       this.okBtn.requestFocus();
/*  611 */       boolean isResizable = contentScroll;
/*  612 */       this.dialog.setResizable(isResizable);
/*      */     }
/*      */     catch (Throwable t)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   void setListContent(String label, ListView scrollList, boolean showDetails, String okBtnStr, String cancelBtnStr, TreeMap clientAuthCertsMap)
/*      */   {
/*  629 */     this.useWarningIcon = true;
/*      */ 
/*  632 */     this.includeAppInfo = false;
/*      */ 
/*  636 */     this.clientAuthCertsMap = clientAuthCertsMap;
/*      */ 
/*  639 */     this.contentLabel = label;
/*  640 */     this.contentScroll = true;
/*  641 */     this.scrollList = scrollList;
/*  642 */     this.showDetails = showDetails;
/*      */     try
/*      */     {
/*  645 */       this.contentPane.getChildren().add(createTopPanel(false));
/*  646 */       this.contentPane.getChildren().add(createCenterPanel(false, okBtnStr, cancelBtnStr, -1));
/*  647 */       this.contentPane.getChildren().add(createBottomPanel(false));
/*      */     }
/*      */     catch (Throwable t)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   void setApiContent(String contentString, String contentLabel, String alwaysString, boolean alwaysChecked, String okBtnStr, String cancelBtnStr)
/*      */   {
/*  660 */     this.contentString = contentString;
/*  661 */     this.contentLabel = contentLabel;
/*  662 */     this.contentScroll = (contentString != null);
/*  663 */     this.alwaysString = alwaysString;
/*      */ 
/*  665 */     if ((contentLabel == null) && (contentString != null)) {
/*  666 */       this.infoStrs = new String[1];
/*  667 */       this.infoStrs[0] = contentString;
/*  668 */       this.contentString = null;
/*      */     }
/*      */ 
/*  671 */     this.includeMasthead = true;
/*  672 */     this.includeAppInfo = (this.contentString == null);
/*  673 */     this.largeScroll = false;
/*      */     try
/*      */     {
/*  676 */       this.contentPane.getChildren().add(createTopPanel(false));
/*  677 */       this.contentPane.getChildren().add(createCenterPanel(false, okBtnStr, cancelBtnStr, -1));
/*  678 */       this.contentPane.getChildren().add(createBottomPanel(false));
/*      */ 
/*  680 */       this.dialog.setResizable(this.contentScroll);
/*      */     }
/*      */     catch (Throwable t)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   void setErrorContent(String contentString, String okBtnStr, String cancelBtnStr, Throwable throwable, Object detailPanelObject, Certificate[] certs, boolean hideLabel)
/*      */   {
/*  692 */     Pane detailPanel = (Pane)detailPanelObject;
/*  693 */     this.contentString = contentString;
/*  694 */     this.throwable = throwable;
/*  695 */     this.detailPanel = detailPanel;
/*  696 */     this.certs = certs;
/*      */ 
/*  698 */     if (hideLabel) {
/*  699 */       this.includeAppInfo = false;
/*      */     }
/*      */ 
/*  702 */     this.useErrorIcon = true;
/*      */     try
/*      */     {
/*  705 */       this.contentPane.getChildren().add(createTopPanel(false));
/*  706 */       this.contentPane.getChildren().add(createCenterPanel(false, okBtnStr, cancelBtnStr, -1));
/*  707 */       Pane bottomPanel = createBottomPanel(false);
/*  708 */       if (bottomPanel.getChildren().size() > 0) {
/*  709 */         this.contentPane.getChildren().add(bottomPanel);
/*      */       }
/*      */ 
/*  712 */       this.dialog.setResizable(false);
/*      */     }
/*      */     catch (Throwable t)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   void setMultiButtonErrorContent(String message, String btnOneKey, String btnTwoKey, String btnThreeKey) {
/*  720 */     this.useErrorIcon = true;
/*      */     try
/*      */     {
/*  723 */       this.contentPane.getChildren().add(createTopPanel(false));
/*  724 */       BorderPane errorPanel = new BorderPane();
/*  725 */       errorPanel.setId("error-panel");
/*  726 */       errorPanel.setTop(createInfoPanel(message));
/*  727 */       errorPanel.setBottom(createThreeButtonsPanel(btnOneKey, btnTwoKey, btnThreeKey, false));
/*  728 */       this.contentPane.getChildren().add(errorPanel);
/*      */ 
/*  730 */       this.dialog.setResizable(false);
/*      */     }
/*      */     catch (Throwable t)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   void setInfoContent(String contentString, String okBtnStr) {
/*  738 */     this.useInfoIcon = true;
/*  739 */     this.contentString = contentString;
/*      */     try
/*      */     {
/*  742 */       this.contentPane.getChildren().add(createTopPanel(false));
/*  743 */       this.contentPane.getChildren().add(createCenterPanel(false, okBtnStr, null, -1));
/*      */ 
/*  745 */       this.dialog.setResizable(false);
/*      */     }
/*      */     catch (Throwable t)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   void setPasswordContent(String details, boolean showUserName, boolean showDomain, String userName, String domain, boolean save, char[] password, String scheme)
/*      */   {
/*      */     try
/*      */     {
/*  756 */       this.contentPane.getChildren().add(createPasswordPanel(details, showUserName, showDomain, userName, domain, save, password, scheme));
/*      */ 
/*  760 */       this.dialog.initModality(Modality.APPLICATION_MODAL);
/*      */ 
/*  762 */       this.dialog.setResizable(false);
/*  763 */       this.dialog.setIconifiable(false);
/*      */     }
/*      */     catch (Throwable t)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   void setUpdateCheckContent(String infoStr, String yesBtnKey, String noBtnKey, String askLaterBtnKey)
/*      */   {
/*      */     try
/*      */     {
/*  774 */       this.contentPane.getChildren().add(createTopPanel(false));
/*  775 */       this.contentPane.getChildren().add(createInfoPanel(infoStr));
/*  776 */       this.contentPane.getChildren().add(createThreeButtonsPanel(yesBtnKey, noBtnKey, askLaterBtnKey, true));
/*      */ 
/*  778 */       this.dialog.setResizable(false);
/*      */     }
/*      */     catch (Throwable t)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   void setProgressContent(String okBtnStr, String detailBtnStr, String contentStr, boolean showOkBtn, int percent)
/*      */   {
/*      */     try
/*      */     {
/*  791 */       this.cacheUpgradeContentString = contentStr;
/*      */ 
/*  793 */       this.contentPane.getChildren().add(createTopPanel(false));
/*  794 */       this.contentPane.getChildren().add(createCenterPanel(false, okBtnStr, detailBtnStr, percent));
/*  795 */       if (this.cacheUpgradeContentString == null) {
/*  796 */         this.contentPane.getChildren().add(createBottomPanel(false));
/*      */       }
/*      */ 
/*  799 */       this.dialog.setResizable(false);
/*      */     }
/*      */     catch (Throwable t)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   private Pane createInfoPanel(String infoStr)
/*      */   {
/*  813 */     StackPane infoPanel = new StackPane();
/*  814 */     infoPanel.setId("info-panel");
/*      */ 
/*  816 */     UITextArea infoText = new UITextArea(484.0D);
/*      */ 
/*  819 */     infoText.setId("info-panel-text");
/*  820 */     infoText.setText(infoStr);
/*      */ 
/*  822 */     infoPanel.getChildren().add(infoText);
/*  823 */     return infoPanel;
/*      */   }
/*      */ 
/*      */   private Pane createThreeButtonsPanel(String btnOneKey, String btnTwoKey, String btnThreeKey, boolean isUpdateSpecific)
/*      */   {
/*  832 */     FlowPane buttonsPanel = new FlowPane(6.0D, 0.0D);
/*  833 */     buttonsPanel.getStyleClass().add("button-bar");
/*      */ 
/*  836 */     Button oneBtn = new Button(ResourceManager.getMessage(btnOneKey));
/*      */ 
/*  838 */     oneBtn.setOnAction(new EventHandler() {
/*      */       public void handle(ActionEvent e) {
/*  840 */         DialogTemplate.this.setUserAnswer(0);
/*  841 */         DialogTemplate.this.setVisible(false);
/*      */       }
/*      */     });
/*  844 */     buttonsPanel.getChildren().add(oneBtn);
/*      */ 
/*  847 */     Button twoBtn = new Button(ResourceManager.getMessage(btnTwoKey));
/*      */ 
/*  849 */     twoBtn.setOnAction(new EventHandler() {
/*      */       public void handle(ActionEvent e) {
/*  851 */         DialogTemplate.this.setVisible(false);
/*  852 */         DialogTemplate.this.setUserAnswer(1);
/*      */       }
/*      */     });
/*  855 */     buttonsPanel.getChildren().add(twoBtn);
/*      */ 
/*  858 */     Button threeBtn = null;
/*  859 */     if (btnThreeKey != null) {
/*  860 */       threeBtn = new Button(ResourceManager.getMessage(btnThreeKey));
/*      */ 
/*  862 */       threeBtn.setOnAction(new EventHandler() {
/*      */         public void handle(ActionEvent e) {
/*  864 */           DialogTemplate.this.setVisible(false);
/*  865 */           DialogTemplate.this.setUserAnswer(3);
/*      */         }
/*      */       });
/*  868 */       buttonsPanel.getChildren().add(threeBtn);
/*      */     }
/*      */ 
/*  872 */     if (isUpdateSpecific) {
/*  873 */       threeBtn.setTooltip(new Tooltip(ResourceManager.getMessage("autoupdatecheck.masthead")));
/*      */     }
/*      */ 
/*  876 */     if (threeBtn != null)
/*  877 */       resizeButtons(new Button[] { oneBtn, twoBtn, threeBtn });
/*      */     else {
/*  879 */       resizeButtons(new Button[] { oneBtn, twoBtn });
/*      */     }
/*      */ 
/*  882 */     return buttonsPanel;
/*      */   }
/*      */ 
/*      */   private Pane createTopPanel(boolean useMastheadImage)
/*      */   {
/*  894 */     this.topPanel = new BorderPane();
/*  895 */     this.topPanel.setId("top-panel");
/*      */ 
/*  897 */     if (this.includeMasthead)
/*      */     {
/*  902 */       this.masthead1 = new UITextArea(MAIN_TEXT_WIDTH);
/*  903 */       this.masthead1.setId("masthead-label-1");
/*      */ 
/*  907 */       String str1 = this.topText;
/*  908 */       String str2 = null;
/*  909 */       for (String questionKey : new String[] { "security.dialog.caption.run.question", "security.dialog.caption.continue.question" })
/*      */       {
/*  913 */         String question = ResourceManager.getMessage(questionKey);
/*  914 */         if ((question != null) && (str1.endsWith(question))) {
/*  915 */           str2 = str1.substring(0, str1.indexOf(question)).trim();
/*  916 */           str1 = question;
/*  917 */           break;
/*      */         }
/*      */       }
/*      */ 
/*  921 */       VBox mastheadVBox = new VBox();
/*  922 */       mastheadVBox.setAlignment(Pos.CENTER_LEFT);
/*  923 */       this.masthead1.setText(str1);
/*  924 */       this.masthead1.setAlignment(Pos.CENTER_LEFT);
/*  925 */       mastheadVBox.getChildren().add(this.masthead1);
/*  926 */       if (str2 != null) {
/*  927 */         this.masthead2 = new UITextArea(MAIN_TEXT_WIDTH);
/*  928 */         this.masthead2.setId("masthead-label-2");
/*  929 */         this.masthead2.setText(str2);
/*  930 */         this.masthead2.setAlignment(Pos.CENTER_LEFT);
/*  931 */         mastheadVBox.getChildren().add(this.masthead2);
/*      */       }
/*  933 */       this.topPanel.setLeft(mastheadVBox);
/*  934 */       BorderPane.setAlignment(mastheadVBox, Pos.CENTER_LEFT);
/*      */       ImageView icon;
/*  935 */       if (useMastheadImage) {
/*  936 */         icon = ResourceManager.getIcon("progress.background.image");
/*      */       }
/*      */       else
/*      */       {
/*  940 */         this.topIcon = ResourceManager.getIcon("java48.image");
/*  941 */         if (this.useErrorIcon) {
/*  942 */           this.topIcon = ResourceManager.getIcon("error48.image");
/*      */         }
/*  944 */         if (this.useInfoIcon) {
/*  945 */           this.topIcon = ResourceManager.getIcon("info48.image");
/*      */         }
/*  947 */         if (this.useMixcodeIcon) {
/*  948 */           this.topIcon = ResourceManager.getIcon("mixcode.image");
/*      */         }
/*      */ 
/*  951 */         if (this.useWarningIcon) {
/*  952 */           if (this.majorWarning)
/*  953 */             this.topIcon = ResourceManager.getIcon("major-warning48.image");
/*      */           else
/*  955 */             this.topIcon = ResourceManager.getIcon("warning48.image");
/*      */         }
/*  957 */         else if (this.ainfo.getIconRef() != null)
/*      */         {
/*  959 */           this.topIcon = ResourceManager.getIcon(this.ainfo.getIconRef());
/*      */         }
/*  961 */         this.topPanel.setRight(this.topIcon);
/*      */       }
/*      */     }
/*      */ 
/*  965 */     return this.topPanel;
/*      */   }
/*      */ 
/*      */   private Pane createCenterPanel(boolean checkAlways, String okBtnStr, String cancelBtnStr, int progress)
/*      */   {
/*  981 */     this.centerPanel = new VBox();
/*  982 */     this.centerPanel.setId("center-panel");
/*      */ 
/*  984 */     GridPane labelsPanel = new GridPane();
/*  985 */     labelsPanel.setId("center-panel-grid");
/*      */ 
/*  987 */     Label nameLbl = new Label(ResourceManager.getMessage("dialog.template.name"));
/*  988 */     nameLbl.setId("dialog-name-label");
/*      */ 
/*  990 */     Label publisherLbl = new Label(ResourceManager.getMessage("dialog.template.publisher"));
/*  991 */     publisherLbl.setId("dialog-publisher-label");
/*      */ 
/*  993 */     Label fromLbl = new Label(ResourceManager.getMessage("dialog.template.from"));
/*  994 */     fromLbl.setId("dialog-from-label");
/*      */ 
/*  996 */     this.nameInfo = new Label();
/*  997 */     this.nameInfo.setId("dialog-name-value");
/*      */ 
/*  999 */     this.publisherInfo = new Label();
/* 1000 */     this.publisherInfo.setId("dialog-publisher-value");
/*      */ 
/* 1002 */     this.urlInfo = new Label();
/* 1003 */     this.urlInfo.setId("dialog-from-value");
/*      */ 
/* 1007 */     if (this.appTitle != null) {
/* 1008 */       GridPane.setConstraints(nameLbl, 0, 0);
/* 1009 */       GridPane.setHalignment(nameLbl, HPos.RIGHT);
/* 1010 */       labelsPanel.getChildren().add(nameLbl);
/* 1011 */       GridPane.setConstraints(this.nameInfo, 1, 0);
/* 1012 */       labelsPanel.getChildren().add(this.nameInfo);
/*      */     }
/* 1014 */     if (this.appPublisher != null) {
/* 1015 */       GridPane.setConstraints(publisherLbl, 0, 1);
/* 1016 */       GridPane.setHalignment(publisherLbl, HPos.RIGHT);
/* 1017 */       labelsPanel.getChildren().add(publisherLbl);
/* 1018 */       GridPane.setConstraints(this.publisherInfo, 1, 1);
/* 1019 */       labelsPanel.getChildren().add(this.publisherInfo);
/*      */     }
/*      */ 
/* 1022 */     if ((this.appTitle != null) && (this.appURL != null)) {
/* 1023 */       GridPane.setConstraints(fromLbl, 0, 2);
/* 1024 */       GridPane.setHalignment(fromLbl, HPos.RIGHT);
/* 1025 */       labelsPanel.getChildren().add(fromLbl);
/* 1026 */       GridPane.setConstraints(this.urlInfo, 1, 2);
/* 1027 */       labelsPanel.getChildren().add(this.urlInfo);
/*      */     }
/*      */ 
/* 1030 */     setInfo(this.appTitle, this.appPublisher, this.appURL);
/*      */ 
/* 1032 */     FlowPane checkboxPanel = new FlowPane();
/* 1033 */     checkboxPanel.setId("center-checkbox-panel");
/*      */ 
/* 1035 */     BorderPane mixedcodePanel = new BorderPane();
/* 1036 */     mixedcodePanel.setId("mixed-code-panel");
/*      */ 
/* 1038 */     BorderPane contentPanel = new BorderPane();
/* 1039 */     contentPanel.setId("center-content-panel");
/* 1040 */     VBox.setVgrow(contentPanel, Priority.ALWAYS);
/*      */ 
/* 1045 */     if (this.alwaysString != null) {
/* 1046 */       String key = "security.dialog.always";
/* 1047 */       this.always = new CheckBox(this.alwaysString);
/*      */ 
/* 1049 */       this.always.setSelected(checkAlways);
/*      */ 
/* 1051 */       checkboxPanel.getChildren().add(this.always);
/*      */     }
/*      */ 
/* 1056 */     if (this.mixedCodeString != null) {
/* 1057 */       this.mixedCodeLabel = new Label(this.mixedCodeString);
/*      */ 
/* 1060 */       BorderPane moreinfoPanel = new BorderPane();
/* 1061 */       moreinfoPanel.setId("center-more-info-panel");
/*      */ 
/* 1063 */       Hyperlink moreInfoButton = new Hyperlink(ResourceManager.getMessage("dialog.template.more.info"));
/* 1064 */       moreInfoButton.setMnemonicParsing(true);
/* 1065 */       moreInfoButton.setOnAction(new EventHandler() {
/*      */         public void handle(ActionEvent e) {
/* 1067 */           DialogTemplate.this.showMixedcodeMoreInfo();
/*      */         }
/*      */       });
/* 1071 */       moreinfoPanel.setLeft(moreInfoButton);
/* 1072 */       mixedcodePanel.setTop(this.mixedCodeLabel);
/* 1073 */       mixedcodePanel.setBottom(moreinfoPanel);
/*      */     }
/*      */ 
/* 1076 */     boolean showProgress = progress >= 0;
/* 1077 */     if (showProgress)
/*      */     {
/* 1081 */       this.progressBar = new ProgressBar();
/* 1082 */       this.progressBar.setVisible(progress <= 100);
/*      */     }
/*      */ 
/* 1085 */     if (this.contentString != null) {
/* 1086 */       if (this.contentLabel != null) {
/* 1087 */         BorderPane top = new BorderPane();
/* 1088 */         top.setLeft(new Label(this.contentLabel));
/* 1089 */         contentPanel.setTop(top);
/*      */       }
/* 1091 */       if (this.contentScroll)
/*      */       {
/* 1093 */         boolean limitWidth = this.largeScroll;
/*      */         Label text;
/* 1094 */         if (this.largeScroll) {
/* 1095 */           Label text = new Label(this.contentString);
/* 1096 */           text.setPrefWidth(640.0D);
/* 1097 */           text.setPrefHeight(240.0D);
/*      */         } else {
/* 1099 */           text = new Label(this.contentString);
/* 1100 */           text.setPrefWidth(320.0D);
/* 1101 */           text.setPrefHeight(48.0D);
/*      */         }
/* 1103 */         ScrollPane sp = new ScrollPane();
/* 1104 */         sp.setContent(text);
/* 1105 */         sp.setFitToWidth(true);
/* 1106 */         VBox.setVgrow(sp, Priority.ALWAYS);
/* 1107 */         if (limitWidth) {
/* 1108 */           sp.setMaxWidth(600.0D);
/*      */         }
/*      */ 
/* 1111 */         contentPanel.setCenter(sp);
/*      */       }
/*      */       else {
/* 1114 */         UITextArea ta = new UITextArea(this.contentString);
/* 1115 */         ta.setId("center-content-area");
/* 1116 */         ta.setAlignment(Pos.TOP_LEFT);
/* 1117 */         contentPanel.setCenter(ta);
/*      */       }
/* 1119 */       contentPanel.setPadding(new Insets(0.0D, 0.0D, 12.0D, 0.0D));
/*      */     }
/*      */ 
/* 1122 */     if (this.scrollList != null) {
/* 1123 */       if (this.contentLabel != null) {
/* 1124 */         BorderPane top = new BorderPane();
/* 1125 */         top.setLeft(new Label(this.contentLabel));
/* 1126 */         contentPanel.setTop(top);
/*      */       }
/* 1128 */       if (this.contentScroll) {
/* 1129 */         ScrollPane sp = new ScrollPane();
/* 1130 */         sp.setContent(this.scrollList);
/* 1131 */         VBox.setVgrow(sp, Priority.ALWAYS);
/* 1132 */         contentPanel.setCenter(sp);
/*      */       }
/*      */ 
/* 1137 */       if (this.showDetails) {
/* 1138 */         Hyperlink certDetails = new Hyperlink(ResourceManager.getMessage("security.more.info.details"));
/* 1139 */         certDetails.setMnemonicParsing(true);
/* 1140 */         certDetails.setOnAction(new EventHandler() {
/*      */           public void handle(ActionEvent e) {
/* 1142 */             DialogTemplate.this.showCertificateDetails();
/*      */           }
/*      */         });
/* 1146 */         FlowPane certDetailsPanel = new FlowPane();
/* 1147 */         certDetailsPanel.setPadding(new Insets(12.0D, 0.0D, 12.0D, 0.0D));
/* 1148 */         certDetailsPanel.setAlignment(Pos.TOP_LEFT);
/* 1149 */         certDetailsPanel.getChildren().add(certDetails);
/* 1150 */         contentPanel.setBottom(certDetailsPanel);
/*      */       }
/*      */     }
/*      */ 
/* 1154 */     FlowPane buttonsPanel = new FlowPane(6.0D, 0.0D);
/* 1155 */     buttonsPanel.getStyleClass().add("button-bar");
/* 1156 */     buttonsPanel.setId("center-bottom-button-bar");
/*      */ 
/* 1159 */     this.okBtn = new Button(okBtnStr == null ? "" : ResourceManager.getMessage(okBtnStr));
/* 1160 */     this.okBtn.setOnAction(this.okHandler);
/* 1161 */     buttonsPanel.getChildren().add(this.okBtn);
/* 1162 */     this.okBtn.setVisible(okBtnStr != null);
/*      */ 
/* 1164 */     this.cancelBtn = new Button(cancelBtnStr == null ? "" : ResourceManager.getMessage(cancelBtnStr));
/*      */ 
/* 1166 */     this.cancelBtn.setOnAction(this.cancelHandler);
/* 1167 */     buttonsPanel.getChildren().add(this.cancelBtn);
/* 1168 */     this.cancelBtn.setVisible(cancelBtnStr != null);
/*      */ 
/* 1173 */     if (this.okBtn.isVisible())
/* 1174 */       this.okBtn.setDefaultButton(true);
/*      */     else {
/* 1176 */       this.cancelBtn.setCancelButton(true);
/*      */     }
/*      */ 
/* 1180 */     resizeButtons(new Button[] { this.okBtn, this.cancelBtn });
/*      */ 
/* 1182 */     if (this.cacheUpgradeContentString != null) {
/* 1183 */       UITextArea cacheUpgradeContentTa = new UITextArea(this.cacheUpgradeContentString);
/*      */ 
/* 1185 */       cacheUpgradeContentTa.setId("cache-upgrade-content");
/* 1186 */       contentPanel.setTop(cacheUpgradeContentTa);
/*      */     } else {
/* 1188 */       if (this.includeAppInfo) {
/* 1189 */         this.centerPanel.getChildren().add(labelsPanel);
/*      */       }
/* 1191 */       if (this.alwaysString != null) {
/* 1192 */         this.centerPanel.getChildren().add(checkboxPanel);
/*      */       }
/* 1194 */       if (this.mixedCodeString != null) {
/* 1195 */         this.centerPanel.getChildren().add(mixedcodePanel);
/*      */       }
/*      */     }
/* 1198 */     if (contentPanel.getChildren().size() > 0) {
/* 1199 */       this.centerPanel.getChildren().add(contentPanel);
/*      */     }
/*      */ 
/* 1202 */     BorderPane bottomPanel = new BorderPane();
/* 1203 */     bottomPanel.setId("center-bottom-panel");
/* 1204 */     if (showProgress) {
/* 1205 */       this.progressStatusLabel = new Label(" ");
/* 1206 */       this.progressStatusLabel.getStyleClass().add("progress-label");
/* 1207 */       BorderPane progressStatusPanel = new BorderPane();
/* 1208 */       progressStatusPanel.setId("center-progress-status-panel");
/* 1209 */       this.centerPanel.getChildren().add(progressStatusPanel);
/* 1210 */       progressStatusPanel.setLeft(this.progressStatusLabel);
/* 1211 */       progressStatusPanel.setPadding(new Insets(2.0D, 0.0D, 2.0D, 0.0D));
/* 1212 */       bottomPanel.setCenter(this.progressBar);
/*      */     }
/* 1214 */     bottomPanel.setRight(buttonsPanel);
/* 1215 */     this.centerPanel.getChildren().add(bottomPanel);
/*      */ 
/* 1217 */     return this.centerPanel;
/*      */   }
/*      */ 
/*      */   private Pane createBottomPanel(boolean showMoreInfo)
/*      */   {
/* 1230 */     HBox bottomPanel = new HBox();
/* 1231 */     bottomPanel.setId("bottom-panel");
/*      */ 
/* 1233 */     if ((this.alertStrs != null) || (this.infoStrs != null))
/*      */     {
/* 1239 */       String imageFile = "security.alert.high.image";
/*      */ 
/* 1241 */       if ((this.alertStrs == null) || (this.alertStrs.length == 0)) {
/* 1242 */         imageFile = "security.alert.low.image";
/*      */ 
/* 1245 */         if (this.always != null)
/* 1246 */           this.always.setSelected(true);
/*      */       }
/* 1248 */       else if (this.mixedCodeString == null)
/*      */       {
/* 1251 */         this.okBtn.setDefaultButton(false);
/* 1252 */         this.cancelBtn.setCancelButton(true);
/*      */       }
/* 1254 */       this.securityIcon = ResourceManager.getIcon(imageFile);
/*      */ 
/* 1258 */       bottomPanel.getChildren().add(this.securityIcon);
/*      */ 
/* 1265 */       int moreInfoLength = 0;
/* 1266 */       Hyperlink moreInfoLbl = null;
/* 1267 */       if (showMoreInfo) {
/* 1268 */         moreInfoLbl = new Hyperlink(ResourceManager.getMessage("dialog.template.more.info"));
/* 1269 */         moreInfoLbl.setMnemonicParsing(true);
/* 1270 */         moreInfoLbl.setId("bottom-more-info-link");
/* 1271 */         moreInfoLbl.setOnAction(new EventHandler() {
/*      */           public void handle(ActionEvent e) {
/* 1273 */             DialogTemplate.this.showMoreInfo();
/*      */           }
/*      */ 
/*      */         });
/*      */       }
/*      */ 
/* 1291 */       int textAreaWidth = 333;
/* 1292 */       UITextArea bulletText = new UITextArea(textAreaWidth);
/* 1293 */       bulletText.setId("bottom-text");
/*      */ 
/* 1295 */       if (((this.alertStrs == null) || (this.alertStrs.length == 0)) && (this.infoStrs != null) && (this.infoStrs.length != 0))
/*      */       {
/* 1298 */         bulletText.setText(this.infoStrs[0] != null ? this.infoStrs[0] : " ");
/* 1299 */       } else if ((this.alertStrs != null) && (this.alertStrs.length != 0))
/*      */       {
/* 1301 */         bulletText.setText(this.alertStrs[0] != null ? this.alertStrs[0] : " ");
/*      */       }
/*      */ 
/* 1304 */       bottomPanel.getChildren().add(bulletText);
/*      */ 
/* 1306 */       if (moreInfoLbl != null) {
/* 1307 */         bottomPanel.getChildren().add(moreInfoLbl);
/*      */       }
/*      */     }
/*      */ 
/* 1311 */     return bottomPanel;
/*      */   }
/*      */ 
/*      */   private BorderPane createSSVTopPanel(String message, String name, String url)
/*      */   {
/* 1316 */     BorderPane top = new BorderPane();
/* 1317 */     top.setPadding(new Insets(16.0D, 0.0D, 16.0D, 16.0D));
/*      */ 
/* 1319 */     Label messageLabel = new Label(message);
/*      */ 
/* 1321 */     messageLabel.getStyleClass().add("ssv-big-bold-label");
/*      */ 
/* 1323 */     top.setTop(messageLabel);
/*      */ 
/* 1325 */     Label nameLabel = new Label(ResourceManager.getMessage("dialog.template.name"));
/* 1326 */     nameLabel.getStyleClass().add("ssv-small-bold-label");
/* 1327 */     nameLabel.setId("ssv-top-panel-name-label");
/*      */ 
/* 1329 */     Label fromLabel = new Label(ResourceManager.getMessage("dialog.template.from"));
/* 1330 */     fromLabel.getStyleClass().add("ssv-small-bold-label");
/* 1331 */     fromLabel.setId("ssv-top-panel-from-label");
/*      */ 
/* 1333 */     this.nameInfo = new Label(name);
/* 1334 */     this.nameInfo.getStyleClass().add("ssv-big-bold-label");
/*      */ 
/* 1336 */     Label fromInfo = new Label(url);
/* 1337 */     fromInfo.getStyleClass().add("ssv-small-label");
/*      */ 
/* 1339 */     BorderPane[] parts = new BorderPane[4];
/* 1340 */     for (int i = 0; i < 4; i++) {
/* 1341 */       parts[i] = new BorderPane();
/*      */     }
/* 1343 */     ImageView warningIcon = ResourceManager.getIcon("warning48.image");
/*      */ 
/* 1345 */     parts[2].setTop(nameLabel);
/* 1346 */     parts[2].setBottom(fromLabel);
/*      */ 
/* 1348 */     parts[2].setPadding(new Insets(2.0D, 0.0D, 0.0D, 0.0D));
/* 1349 */     parts[3].setTop(this.nameInfo);
/* 1350 */     parts[3].setBottom(fromInfo);
/*      */ 
/* 1352 */     parts[1].setLeft(parts[2]);
/* 1353 */     parts[1].setCenter(parts[3]);
/*      */ 
/* 1355 */     parts[1].setPadding(new Insets(12.0D, 0.0D, 12.0D, 0.0D));
/* 1356 */     parts[0].setLeft(warningIcon);
/* 1357 */     parts[0].setRight(parts[1]);
/* 1358 */     parts[0].setPadding(new Insets(8.0D, 0.0D, 0.0D, 32.0D));
/*      */ 
/* 1360 */     top.setBottom(parts[0]);
/*      */ 
/* 1362 */     return top;
/*      */   }
/*      */ 
/*      */   private BorderPane createSSVRiskPanel(String message, String moreInfoText, final URL moreInfoURL)
/*      */   {
/* 1368 */     BorderPane risk = new BorderPane();
/* 1369 */     risk.setPadding(new Insets(8.0D, 8.0D, 0.0D, 8.0D));
/*      */ 
/* 1371 */     int index = message.indexOf(" ");
/* 1372 */     if (index < message.length() - 2) {
/* 1373 */       String riskLabelText = message.substring(0, index);
/* 1374 */       String riskText = message.substring(index + 1);
/* 1375 */       BorderPane left = new BorderPane();
/* 1376 */       Label riskLabel = new Label(riskLabelText);
/* 1377 */       riskLabel.getStyleClass().add("ssv-small-bold-label");
/* 1378 */       left.setTop(riskLabel);
/* 1379 */       left.setPadding(new Insets(0.0D, 8.0D, 0.0D, 8.0D));
/*      */ 
/* 1381 */       BorderPane right = new BorderPane();
/* 1382 */       Label riskTextLabel = new Label(riskText);
/* 1383 */       right.setLeft(riskTextLabel);
/* 1384 */       riskTextLabel.getStyleClass().add("ssv-small-label");
/* 1385 */       Hyperlink link = new Hyperlink(moreInfoText);
/* 1386 */       link.setOnAction(new EventHandler() {
/*      */         public void handle(ActionEvent e) {
/* 1388 */           HostServicesDelegate services = HostServicesImpl.getInstance(null);
/* 1389 */           if ((services != null) && (moreInfoURL != null))
/* 1390 */             services.showDocument(moreInfoURL.toExternalForm());
/*      */         }
/*      */       });
/* 1394 */       right.setRight(link);
/*      */ 
/* 1396 */       risk.setLeft(left);
/* 1397 */       risk.setCenter(right);
/*      */     }
/* 1399 */     return risk;
/*      */   }
/*      */ 
/*      */   private Pane createPasswordPanel(String details, boolean showUserName, boolean showDomain, String userName, String domain, boolean saveEnabled, char[] sugPass, String scheme)
/*      */   {
/* 1464 */     Label uNameLbl = new Label();
/* 1465 */     Label domainLbl = new Label();
/*      */ 
/* 1467 */     ImageView banner = ResourceManager.getIcon("pwd-masthead.png");
/*      */ 
/* 1470 */     if (showUserName) {
/* 1471 */       String userNameKey = "password.dialog.username";
/* 1472 */       uNameLbl.setText(ResourceManager.getMessage(userNameKey));
/* 1473 */       uNameLbl.setMnemonicParsing(true);
/* 1474 */       this.pwdName = new TextField();
/* 1475 */       this.pwdName.setId("user-name-field");
/*      */ 
/* 1477 */       this.pwdName.setText(userName);
/* 1478 */       uNameLbl.setLabelFor(this.pwdName);
/* 1479 */       uNameLbl.setId("user-name-label");
/*      */     }
/*      */ 
/* 1483 */     String passwordKey = "password.dialog.password";
/* 1484 */     Label passwordLbl = new Label(ResourceManager.getMessage(passwordKey));
/* 1485 */     this.password = new PasswordField();
/*      */ 
/* 1487 */     this.password.setText(String.valueOf(sugPass));
/* 1488 */     passwordLbl.setLabelFor(this.password);
/* 1489 */     passwordLbl.setMnemonicParsing(true);
/* 1490 */     passwordLbl.setId("password-label");
/*      */ 
/* 1492 */     if (showDomain) {
/* 1493 */       String domainKey = "password.dialog.domain";
/* 1494 */       domainLbl.setText(ResourceManager.getMessage(domainKey));
/* 1495 */       this.pwdDomain = new TextField();
/*      */ 
/* 1497 */       this.pwdDomain.setText(domain);
/* 1498 */       domainLbl.setLabelFor(this.pwdDomain);
/* 1499 */       domainLbl.setMnemonicParsing(true);
/* 1500 */       domainLbl.setId("password-domain-label");
/*      */     }
/*      */ 
/* 1503 */     VBox elementsPanel = new VBox();
/* 1504 */     elementsPanel.setMaxWidth(banner.getImage().getWidth());
/* 1505 */     elementsPanel.getChildren().add(banner);
/*      */ 
/* 1507 */     VBox passwordContentPanel = new VBox(10.0D);
/* 1508 */     passwordContentPanel.setId("password-panel");
/*      */ 
/* 1515 */     Label detailsText = new Label();
/* 1516 */     detailsText.setId("password-details");
/* 1517 */     detailsText.setWrapText(true);
/*      */ 
/* 1520 */     detailsText.setText(details);
/*      */ 
/* 1522 */     passwordContentPanel.getChildren().add(detailsText);
/*      */ 
/* 1524 */     GridPane gridPane = new GridPane();
/* 1525 */     gridPane.setId("password-panel-grid");
/* 1526 */     int row = 0;
/*      */ 
/* 1529 */     if (showUserName) {
/* 1530 */       GridPane.setConstraints(uNameLbl, 0, row);
/* 1531 */       GridPane.setHalignment(uNameLbl, HPos.RIGHT);
/* 1532 */       gridPane.getChildren().add(uNameLbl);
/* 1533 */       GridPane.setConstraints(this.pwdName, 1, row++);
/* 1534 */       gridPane.getChildren().add(this.pwdName);
/*      */     }
/*      */ 
/* 1539 */     GridPane.setConstraints(passwordLbl, 0, row);
/* 1540 */     GridPane.setHalignment(passwordLbl, HPos.RIGHT);
/* 1541 */     gridPane.getChildren().add(passwordLbl);
/* 1542 */     GridPane.setConstraints(this.password, 1, row++);
/* 1543 */     gridPane.getChildren().add(this.password);
/*      */ 
/* 1547 */     if (showDomain) {
/* 1548 */       GridPane.setConstraints(domainLbl, 0, row);
/* 1549 */       GridPane.setHalignment(domainLbl, HPos.RIGHT);
/* 1550 */       gridPane.getChildren().add(domainLbl);
/* 1551 */       GridPane.setConstraints(this.pwdDomain, 1, row++);
/* 1552 */       gridPane.getChildren().add(this.pwdDomain);
/*      */     }
/*      */ 
/* 1557 */     if (saveEnabled) {
/* 1558 */       this.always = new CheckBox(ResourceManager.getMessage("password.dialog.save"));
/* 1559 */       this.always.setId("password-always-checkbox");
/*      */ 
/* 1561 */       this.always.setSelected(sugPass.length > 0);
/* 1562 */       GridPane.setConstraints(this.always, 1, row++);
/* 1563 */       gridPane.getChildren().add(this.always);
/*      */     }
/*      */ 
/* 1566 */     passwordContentPanel.getChildren().add(gridPane);
/*      */ 
/* 1568 */     FlowPane btnsPanel = new FlowPane(6.0D, 0.0D);
/* 1569 */     btnsPanel.setPrefWrapLength(300.0D);
/* 1570 */     btnsPanel.getStyleClass().add("button-bar");
/* 1571 */     btnsPanel.setId("password-button-bar");
/*      */ 
/* 1575 */     this.okBtn = new Button(ResourceManager.getMessage("common.ok_btn"));
/* 1576 */     this.okBtn.setOnAction(this.okHandler);
/* 1577 */     this.okBtn.setDefaultButton(true);
/*      */ 
/* 1579 */     this.cancelBtn = new Button(ResourceManager.getMessage("common.cancel_btn"));
/* 1580 */     this.cancelBtn.setOnAction(this.cancelHandler);
/*      */ 
/* 1584 */     resizeButtons(new Button[] { this.okBtn, this.cancelBtn });
/* 1585 */     btnsPanel.getChildren().addAll(new Node[] { this.okBtn, this.cancelBtn });
/* 1586 */     passwordContentPanel.getChildren().add(btnsPanel);
/*      */ 
/* 1589 */     if (scheme != null) {
/* 1590 */       MessageFormat mf = new MessageFormat(ResourceManager.getMessage("password.dialog.scheme"));
/* 1591 */       Object[] args = { scheme };
/* 1592 */       Label schemeLabel = new Label(mf.format(args));
/* 1593 */       passwordContentPanel.getChildren().add(schemeLabel);
/*      */     }
/*      */ 
/* 1596 */     elementsPanel.getChildren().add(passwordContentPanel);
/*      */ 
/* 1598 */     return elementsPanel;
/*      */   }
/*      */ 
/*      */   void showMoreInfo()
/*      */   {
/*      */     MoreInfoDialog info;
/*      */     MoreInfoDialog info;
/* 1603 */     if ((this.throwable == null) && (this.detailPanel == null)) {
/* 1604 */       info = new MoreInfoDialog(this.dialog, this.alertStrs, this.infoStrs, this.securityInfoCount, this.certs, this.start, this.end);
/*      */     }
/*      */     else {
/* 1607 */       info = new MoreInfoDialog(this.dialog, this.detailPanel, this.throwable, this.certs);
/*      */     }
/* 1609 */     info.show();
/*      */   }
/*      */ 
/*      */   void showMixedcodeMoreInfo()
/*      */   {
/* 1614 */     MoreInfoDialog info = new MoreInfoDialog(this.dialog, null, this.infoStrs, 0, null, 0, 0);
/* 1615 */     info.show();
/*      */   }
/*      */ 
/*      */   void showCertificateDetails()
/*      */   {
/* 1621 */     long selectedIndex = this.scrollList.getSelectionModel().getSelectedIndex();
/*      */ 
/* 1623 */     X509Certificate[] selectCert = null;
/* 1624 */     Iterator iter = this.clientAuthCertsMap.values().iterator();
/*      */ 
/* 1626 */     for (; (selectedIndex >= 0L) && (iter.hasNext()); 
/* 1626 */       selectedIndex -= 1L)
/*      */     {
/* 1628 */       selectCert = (X509Certificate[])iter.next();
/*      */     }
/*      */ 
/* 1632 */     if (selectCert != null)
/* 1633 */       CertificateDialog.showCertificates(this.dialog, selectCert, 0, selectCert.length);
/*      */   }
/*      */ 
/*      */   public void setVisible(boolean visible)
/*      */   {
/* 1639 */     if (visible)
/*      */     {
/* 1644 */       final FXDialog dlg = this.dialog;
/*      */ 
/* 1646 */       Runnable runner = new Runnable()
/*      */       {
/*      */         public void run()
/*      */         {
/* 1651 */           dlg.showAndWait();
/*      */         }
/*      */       };
/* 1655 */       runner.run();
/*      */     } else {
/* 1657 */       this.dialog.hide();
/*      */     }
/*      */   }
/*      */ 
/*      */   public static void resizeButtons(Button[] buttons)
/*      */   {
/* 1668 */     int len = buttons.length;
/*      */ 
/* 1671 */     double widest = 50.0D;
/* 1672 */     for (int i = 0; i < len; i++)
/* 1673 */       if (buttons[i].prefWidth(-1.0D) > widest)
/* 1674 */         widest = buttons[i].prefWidth(-1.0D);
/*      */   }
/*      */ 
/*      */   public void cancelAction()
/*      */   {
/* 1784 */     this.userAnswer = 1;
/* 1785 */     setVisible(false);
/*      */   }
/*      */ 
/*      */   int getUserAnswer()
/*      */   {
/* 1796 */     return this.userAnswer;
/*      */   }
/*      */ 
/*      */   void setUserAnswer(int answer) {
/* 1800 */     this.userAnswer = answer;
/*      */   }
/*      */ 
/*      */   char[] getPassword() {
/* 1804 */     return this.pwd;
/*      */   }
/*      */ 
/*      */   String getUserName() {
/* 1808 */     return this.userName;
/*      */   }
/*      */ 
/*      */   String getDomain() {
/* 1812 */     return this.domain;
/*      */   }
/*      */ 
/*      */   public boolean isPasswordSaved() {
/* 1816 */     return (this.always != null) && (this.always.isSelected());
/*      */   }
/*      */ 
/*      */   public void progress(int progress) {
/* 1820 */     if (this.progressBar != null)
/* 1821 */       if (progress <= 100) {
/* 1822 */         this.progressBar.setProgress(progress / 100.0D);
/* 1823 */         this.progressBar.setVisible(true);
/*      */       } else {
/* 1825 */         this.progressBar.setVisible(false);
/*      */       }
/*      */   }
/*      */ 
/*      */   public FXDialog getDialog()
/*      */   {
/* 1831 */     return this.dialog;
/*      */   }
/*      */ 
/*      */   public void setInfo(String name, String publisher, URL urlFrom) {
/* 1835 */     if (this.nameInfo != null) {
/* 1836 */       this.nameInfo.setText(name);
/*      */     }
/* 1838 */     if (this.publisherInfo != null) {
/* 1839 */       this.appPublisher = publisher;
/* 1840 */       this.publisherInfo.setText(publisher);
/*      */     }
/* 1842 */     if (this.urlInfo != null) {
/* 1843 */       this.appURL = urlFrom;
/* 1844 */       String from = " ";
/* 1845 */       String tooltip = "";
/*      */ 
/* 1848 */       if (urlFrom != null) {
/* 1849 */         from = urlFrom.getProtocol() + "://" + urlFrom.getHost();
/* 1850 */         int port = urlFrom.getPort();
/* 1851 */         if (port != -1) {
/* 1852 */           from = from + ":" + Integer.toString(port);
/*      */         }
/* 1854 */         tooltip = urlFrom.toString();
/*      */       }
/* 1856 */       this.urlInfo.setText(from);
/* 1857 */       this.urlInfo.setTooltip(new Tooltip(tooltip));
/*      */     }
/*      */   }
/*      */ 
/*      */   void showOk(boolean show) {
/* 1862 */     resizeButtons(new Button[] { this.okBtn, this.cancelBtn });
/* 1863 */     this.okBtn.setVisible(show);
/*      */   }
/*      */ 
/*      */   void stayAlive() {
/* 1867 */     this.stayAliveOnOk = true;
/*      */   }
/*      */ 
/*      */   public void setProgressStatusText(String text) {
/* 1871 */     if (this.progressStatusLabel != null) {
/* 1872 */       if ((text == null) || (text.length() == 0)) {
/* 1873 */         text = " ";
/*      */       }
/* 1875 */       this.progressStatusLabel.setText(text);
/*      */     }
/*      */   }
/*      */ 
/*      */   void setPublisherInfo(String contentString, String okBtnStr, String cancelBtnStr, Object detailPanel, boolean hideLabel)
/*      */   {
/* 1981 */     this.detailPanel = ((Pane)detailPanel);
/* 1982 */     this.contentString = contentString;
/* 1983 */     this.useInfoIcon = true;
/*      */ 
/* 1985 */     if (detailPanel == null) {
/* 1986 */       cancelBtnStr = null;
/*      */     }
/*      */ 
/* 1989 */     if (hideLabel) {
/* 1990 */       this.includeAppInfo = false;
/*      */     }
/*      */ 
/* 1993 */     this.okBtnStr = okBtnStr;
/* 1994 */     this.cancelBtnStr = cancelBtnStr;
/*      */     try
/*      */     {
/* 1997 */       this.contentPane.getChildren().add(createTopPanel(true));
/* 1998 */       this.contentPane.getChildren().add(createCenterPanel(false, okBtnStr, cancelBtnStr, -1));
/*      */ 
/* 2000 */       this.dialog.setResizable(false);
/* 2001 */       this.dialog.setIconifiable(false);
/*      */     } catch (Throwable t) {
/* 2003 */       Trace.ignored(t);
/*      */     }
/*      */   }
/*      */ 
/*      */   private class SSVChoicePanel extends BorderPane
/*      */   {
/*      */     ToggleGroup group;
/*      */     RadioButton button1;
/*      */     RadioButton button2;
/*      */ 
/*      */     public SSVChoicePanel(String choiceText, String choice1Label, String choice2Label)
/*      */     {
/* 1410 */       setPadding(new Insets(8.0D, 16.0D, 0.0D, 16.0D));
/*      */ 
/* 1412 */       BorderPane top = new BorderPane();
/* 1413 */       VBox bot = new VBox();
/* 1414 */       bot.setSpacing(4.0D);
/*      */ 
/* 1416 */       Label textLabel = new Label(choiceText);
/* 1417 */       textLabel.getStyleClass().add("ssv-small-bold-label");
/* 1418 */       top.setCenter(textLabel);
/*      */ 
/* 1420 */       this.button1 = new RadioButton(choice1Label);
/* 1421 */       this.button1.getStyleClass().add("ssv-small-label");
/* 1422 */       this.button2 = new RadioButton(choice2Label);
/* 1423 */       this.button2.getStyleClass().add("ssv-small-label");
/*      */ 
/* 1425 */       this.group = new ToggleGroup();
/* 1426 */       this.button1.setToggleGroup(this.group);
/* 1427 */       this.button2.setToggleGroup(this.group);
/*      */ 
/* 1429 */       this.button1.setSelected(true);
/*      */ 
/* 1431 */       bot.getChildren().addAll(new Node[] { this.button1, this.button2 });
/* 1432 */       bot.setPadding(new Insets(0.0D, 16.0D, 0.0D, 32.0D));
/*      */ 
/* 1434 */       setTop(top);
/* 1435 */       setBottom(bot);
/*      */ 
/* 1437 */       this.button1.requestFocus();
/*      */     }
/*      */ 
/*      */     public int getSelection() {
/* 1441 */       if (this.button2.isSelected()) {
/* 1442 */         return 1;
/*      */       }
/* 1444 */       return 0;
/*      */     }
/*      */   }
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.deploy.uitoolkit.impl.fx.ui.DialogTemplate
 * JD-Core Version:    0.6.2
 */