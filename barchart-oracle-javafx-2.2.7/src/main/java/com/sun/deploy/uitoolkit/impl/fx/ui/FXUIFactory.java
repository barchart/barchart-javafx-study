/*      */ package com.sun.deploy.uitoolkit.impl.fx.ui;
/*      */ 
/*      */ import com.sun.deploy.security.CredentialInfo;
/*      */ import com.sun.deploy.trace.Trace;
/*      */ import com.sun.deploy.ui.AppInfo;
/*      */ import com.sun.deploy.uitoolkit.impl.fx.FXPluginToolkit;
/*      */ import com.sun.deploy.uitoolkit.ui.ConsoleController;
/*      */ import com.sun.deploy.uitoolkit.ui.ConsoleWindow;
/*      */ import com.sun.deploy.uitoolkit.ui.DialogHook;
/*      */ import com.sun.deploy.uitoolkit.ui.ModalityHelper;
/*      */ import com.sun.deploy.uitoolkit.ui.NativeMixedCodeDialog;
/*      */ import com.sun.deploy.uitoolkit.ui.PluginUIFactory;
/*      */ import com.sun.deploy.util.DeploySysAction;
/*      */ import com.sun.deploy.util.DeploySysRun;
/*      */ import java.io.File;
/*      */ import java.io.PrintStream;
/*      */ import java.net.URL;
/*      */ import java.security.cert.Certificate;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.List;
/*      */ import java.util.TreeMap;
/*      */ import java.util.Vector;
/*      */ import java.util.concurrent.Callable;
/*      */ import javafx.application.Platform;
/*      */ import javafx.collections.FXCollections;
/*      */ import javafx.collections.ObservableList;
/*      */ import javafx.geometry.Pos;
/*      */ import javafx.scene.control.Label;
/*      */ import javafx.scene.control.ListView;
/*      */ import javafx.scene.control.MultipleSelectionModel;
/*      */ import javafx.scene.control.ScrollPane;
/*      */ import javafx.scene.control.SelectionMode;
/*      */ import javafx.scene.control.Tab;
/*      */ import javafx.scene.control.TabPane;
/*      */ import javafx.scene.layout.BorderPane;
/*      */ import javafx.scene.layout.HBox;
/*      */ import javafx.scene.layout.Pane;
/*      */ import javafx.stage.FileChooser;
/*      */ import javafx.stage.FileChooser.ExtensionFilter;
/*      */ import javafx.stage.Stage;
/*      */ import javafx.stage.Window;
/*      */ 
/*      */ public class FXUIFactory extends PluginUIFactory
/*      */ {
/*      */   public int showMessageDialog(Object owner, AppInfo ainfo, int messageType, String title, String masthead, String message, String MoreInfo, String btnString1, String btnString2, String btnString3)
/*      */   {
/*   49 */     switch (messageType) { case -1:
/*      */     case 6:
/*      */     default:
/*   52 */       return showContentDialog(owner, ainfo, title, message, true, btnString1, btnString2);
/*      */     case 0:
/*   54 */       if (MoreInfo != null) {
/*   55 */         return showErrorDialog(owner, ainfo, title, message, null, btnString1, btnString2, null, getDetailPanel(MoreInfo), null);
/*      */       }
/*      */ 
/*   59 */       return showErrorDialog(owner, ainfo, title, masthead, message, btnString1, btnString2, btnString3);
/*      */     case 1:
/*   63 */       showInformationDialog(owner, title, masthead, message);
/*   64 */       return -1;
/*      */     case 2:
/*   66 */       return showWarningDialog(owner, ainfo, title, masthead, message, btnString1, btnString2);
/*      */     case 3:
/*   69 */       return showConfirmDialog(owner, ainfo, title, masthead, MoreInfo, btnString1, btnString2, true);
/*      */     case 5:
/*   72 */       return showIntegrationDialog(owner, ainfo);
/*      */     case 7:
/*   74 */       return showApiDialog(null, ainfo, title, message, masthead, btnString1, btnString2, false);
/*      */     case 4:
/*      */     }
/*   77 */     return showMixedCodeDialog(owner, ainfo, title, masthead, message, MoreInfo, btnString1, btnString2, true);
/*      */   }
/*      */ 
/*      */   public void showExceptionDialog(Object owner, AppInfo appInfo, Throwable throwable, String title, String masthead, String message, Certificate[] certs)
/*      */   {
/*   88 */     if (certs == null)
/*   89 */       showExceptionDialog(owner, throwable, masthead, message, title);
/*      */     else
/*   91 */       showCertificateExceptionDialog(owner, appInfo, throwable, message, title, certs);
/*      */   }
/*      */ 
/*      */   public CredentialInfo showPasswordDialog(Object owner, String title, String notes, boolean showUsername, boolean showDomain, CredentialInfo info, boolean saveEnabled, String scheme)
/*      */   {
/*   99 */     return showPasswordDialog0(owner, title, notes, showUsername, showDomain, info, saveEnabled, scheme);
/*      */   }
/*      */ 
/*      */   public int showSecurityDialog(AppInfo ainfo, String title, String topText, String publisher, URL appFrom, boolean showAlways, boolean checkAlways, String okBtnStr, String cancelBtnStr, String[] securityAlerts, String[] securityInfo, boolean showMoreInfo, Certificate[] certs, int start, int end, boolean majorWarning)
/*      */   {
/*  124 */     return showSecurityDialog0(ainfo, title, topText, publisher, appFrom, showAlways, checkAlways, okBtnStr, cancelBtnStr, securityAlerts, securityInfo, showMoreInfo, certs, start, end, majorWarning, false);
/*      */   }
/*      */ 
/*      */   public int showSecurityDialog(AppInfo ainfo, String title, String topText, String publisher, URL appFrom, boolean showAlways, boolean checkAlways, String okBtnStr, String cancelBtnStr, String[] securityAlerts, String[] securityInfo, boolean showMoreInfo, Certificate[] certs, int start, int end, boolean majorWarning, boolean httpsDialog)
/*      */   {
/*  139 */     return showSecurityDialog0(ainfo, title, topText, publisher, appFrom, showAlways, checkAlways, okBtnStr, cancelBtnStr, securityAlerts, securityInfo, showMoreInfo, certs, start, end, majorWarning, httpsDialog);
/*      */   }
/*      */ 
/*      */   public void showAboutJavaDialog()
/*      */   {
/*  148 */     Platform.runLater(new Runnable() {
/*      */       public void run() {
/*  150 */         FXAboutDialog.showAboutJavaDialog();
/*      */       }
/*      */     });
/*      */   }
/*      */ 
/*      */   public int showListDialog(Object owner, String title, String message, String label, boolean details, Vector certs, TreeMap clientAuthCertsMap)
/*      */   {
/*  158 */     ListView certList = new ListView();
/*  159 */     certList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
/*  160 */     certList.setItems(FXCollections.observableList(certs));
/*  161 */     if (certs.size() > 0) {
/*  162 */       certList.getSelectionModel().select(0);
/*      */     }
/*      */ 
/*  165 */     return showListDialog0(owner, title, message, label, details, certList, clientAuthCertsMap);
/*      */   }
/*      */ 
/*      */   public int showUpdateCheckDialog()
/*      */   {
/*  170 */     return showUpdateCheckDialog0();
/*      */   }
/*      */ 
/*      */   public ConsoleWindow getConsole(ConsoleController cc)
/*      */   {
/*  175 */     return new FXConsole(cc);
/*      */   }
/*      */ 
/*      */   public void setDialogHook(DialogHook dh)
/*      */   {
/*      */   }
/*      */ 
/*      */   public ModalityHelper getModalityHelper()
/*      */   {
/*  185 */     return new FXModalityHelper();
/*      */   }
/*      */ 
/*      */   public static int showErrorDialog(Object owner, AppInfo ainfo, final String title, final String masthead, final String message, final String okBtnStr, final String detailBtnStr, final Throwable throwable, final Object detailPanel, final Certificate[] certs)
/*      */   {
/*  206 */     final Stage fOwner = beforeDialog((Stage)owner);
/*      */     try
/*      */     {
/*  209 */       int rval = ((Integer)FXPluginToolkit.callAndWait(new Callable() {
/*      */         public Integer call() {
/*  211 */           DialogTemplate template = new DialogTemplate(this.val$ainfo, fOwner, title, masthead);
/*      */ 
/*  214 */           template.setErrorContent(message, okBtnStr, detailBtnStr, throwable, detailPanel, certs, false);
/*      */ 
/*  217 */           FXUIFactory.placeWindow(template.getDialog());
/*  218 */           template.setVisible(true);
/*      */ 
/*  220 */           int userAnswer = template.getUserAnswer();
/*  221 */           return new Integer(userAnswer);
/*      */         }
/*      */       })).intValue();
/*      */ 
/*  226 */       System.err.println("FXUIFactory.showErrorDialog: shutting down the FX toolkit");
/*      */       try {
/*      */       }
/*      */       catch (Exception ex) {
/*  230 */         ex.printStackTrace();
/*      */       }
/*      */ 
/*  234 */       return rval;
/*      */     }
/*      */     catch (Throwable e) {
/*  237 */       return -1;
/*      */     } finally {
/*  239 */       afterDialog();
/*      */     }
/*      */   }
/*      */ 
/*      */   public static int showErrorDialog(Object owner, AppInfo ainfo, String titleStr, final String masthead, final String message, String btnOneKey, final String btnTwoKey, final String btnThreeKey)
/*      */   {
/*  259 */     final String btnString1 = btnOneKey == null ? com.sun.deploy.uitoolkit.impl.fx.ui.resources.ResourceManager.getString("common.ok_btn") : btnOneKey;
/*      */ 
/*  261 */     String title = titleStr == null ? com.sun.deploy.uitoolkit.impl.fx.ui.resources.ResourceManager.getString("error.default.title") : titleStr;
/*      */ 
/*  264 */     final Stage fOwner = beforeDialog((Stage)owner);
/*      */     try
/*      */     {
/*  268 */       return ((Integer)FXPluginToolkit.callAndWait(new Callable() {
/*      */         public Integer call() {
/*  270 */           String title = com.sun.deploy.uitoolkit.impl.fx.ui.resources.ResourceManager.getString("error.default.title");
/*      */ 
/*  272 */           DialogTemplate template = new DialogTemplate(this.val$ainfo, fOwner, title, masthead);
/*      */ 
/*  275 */           template.setMultiButtonErrorContent(message, btnString1, btnTwoKey, btnThreeKey);
/*      */ 
/*  278 */           FXUIFactory.placeWindow(template.getDialog());
/*      */ 
/*  280 */           template.setVisible(true);
/*      */ 
/*  282 */           int userAnswer = template.getUserAnswer();
/*  283 */           return new Integer(userAnswer);
/*      */         }
/*      */       })).intValue();
/*      */     }
/*      */     catch (Throwable e)
/*      */     {
/*  288 */       return -1;
/*      */     } finally {
/*  290 */       afterDialog();
/*      */     }
/*      */   }
/*      */ 
/*      */   public static int showContentDialog(Object owner, AppInfo ainfo, final String title, final String content, final boolean scroll, final String okBtnStr, final String cancelBtnStr)
/*      */   {
/*  313 */     final Stage fOwner = beforeDialog((Stage)owner);
/*      */     try
/*      */     {
/*  317 */       return ((Integer)FXPluginToolkit.callAndWait(new Callable() {
/*      */         public Integer call() {
/*  319 */           DialogTemplate template = new DialogTemplate(this.val$ainfo, fOwner, title, null);
/*      */ 
/*  321 */           template.setSimpleContent(content, scroll, null, okBtnStr, cancelBtnStr, false, false);
/*      */ 
/*  323 */           FXUIFactory.placeWindow(template.getDialog());
/*  324 */           template.setVisible(true);
/*  325 */           int userAnswer = template.getUserAnswer();
/*  326 */           return new Integer(userAnswer);
/*      */         }
/*      */       })).intValue();
/*      */     }
/*      */     catch (Throwable e)
/*      */     {
/*  331 */       return -1;
/*      */     } finally {
/*  333 */       afterDialog();
/*      */     }
/*      */   }
/*      */ 
/*      */   public static void showInformationDialog(final Object parent, final String masthead, final String text, final String title)
/*      */   {
/*  348 */     final String okBtnStr = com.sun.deploy.uitoolkit.impl.fx.ui.resources.ResourceManager.getString("common.ok_btn");
/*  349 */     AppInfo ainfo = new AppInfo();
/*      */     try
/*      */     {
/*  352 */       invokeLater(new Runnable() {
/*      */         public void run() {
/*  354 */           DialogTemplate template = new DialogTemplate(this.val$ainfo, (Stage)parent, title, masthead);
/*      */ 
/*  357 */           template.setInfoContent(text, okBtnStr);
/*      */ 
/*  359 */           FXUIFactory.placeWindow(template.getDialog());
/*  360 */           template.setVisible(true);
/*      */         }
/*      */       }
/*      */       , null);
/*      */     }
/*      */     catch (Throwable e)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public static int showWarningDialog(Object owner, AppInfo appInfo, final String title, final String masthead, final String message, String okBtnString, String cancelBtnString)
/*      */   {
/*  387 */     AppInfo ainfo = appInfo == null ? new AppInfo() : appInfo;
/*  388 */     final String okBtnStr = okBtnString == null ? com.sun.deploy.uitoolkit.impl.fx.ui.resources.ResourceManager.getString("common.ok_btn") : okBtnString;
/*      */ 
/*  390 */     final String cancelBtnStr = cancelBtnString == null ? com.sun.deploy.uitoolkit.impl.fx.ui.resources.ResourceManager.getString("common.cancel_btn") : cancelBtnString;
/*      */ 
/*  393 */     final Stage fOwner = beforeDialog((Stage)owner);
/*      */     try
/*      */     {
/*  397 */       return ((Integer)FXPluginToolkit.callAndWait(new Callable() {
/*      */         public Integer call() {
/*  399 */           DialogTemplate template = new DialogTemplate(this.val$ainfo, fOwner, title, masthead);
/*      */ 
/*  402 */           template.setSimpleContent(message, false, null, okBtnStr, cancelBtnStr, true, true);
/*      */ 
/*  404 */           FXUIFactory.placeWindow(template.getDialog());
/*  405 */           template.setVisible(true);
/*      */ 
/*  407 */           int userAnswer = template.getUserAnswer();
/*  408 */           return new Integer(userAnswer);
/*      */         }
/*      */       })).intValue();
/*      */     }
/*      */     catch (Throwable e)
/*      */     {
/*  413 */       return -1;
/*      */     } finally {
/*  415 */       afterDialog();
/*      */     }
/*      */   }
/*      */ 
/*      */   public static int showConfirmDialog(Object owner, AppInfo appInfo, final String message, final String info, final String title, final String okBtnStr, final String cancelBtnStr, final boolean useWarning)
/*      */   {
/*  436 */     AppInfo ainfo = appInfo == null ? new AppInfo() : appInfo;
/*      */ 
/*  438 */     final Stage fOwner = beforeDialog((Stage)owner);
/*      */     try
/*      */     {
/*  442 */       return ((Integer)FXPluginToolkit.callAndWait(new Callable() {
/*      */         public Integer call() {
/*  444 */           DialogTemplate template = new DialogTemplate(this.val$ainfo, fOwner, title, message);
/*      */ 
/*  447 */           template.setSimpleContent(null, false, info, okBtnStr, cancelBtnStr, true, useWarning);
/*      */ 
/*  450 */           FXUIFactory.placeWindow(template.getDialog());
/*      */ 
/*  452 */           template.setVisible(true);
/*      */ 
/*  454 */           int userAnswer = template.getUserAnswer();
/*  455 */           return new Integer(userAnswer);
/*      */         }
/*      */       })).intValue();
/*      */     }
/*      */     catch (Throwable e)
/*      */     {
/*  460 */       return -1;
/*      */     } finally {
/*  462 */       afterDialog();
/*      */     }
/*      */   }
/*      */ 
/*      */   public static int showMixedCodeDialog(Object owner, AppInfo appInfo, String title, String masthead, String message, String info, String okBtnStr, String cancelBtnStr, boolean useWarning)
/*      */   {
/*      */     try
/*      */     {
/*  474 */       if (NativeMixedCodeDialog.isSupported()) {
/*  475 */         String helpBtnStr = com.sun.deploy.resources.ResourceManager.getString("dialog.template.more.info");
/*  476 */         String closeBtnStr = com.sun.deploy.resources.ResourceManager.getString("common.close_btn");
/*  477 */         String helpTitle = com.sun.deploy.resources.ResourceManager.getString("security.more.info.title");
/*  478 */         String helpMessage = com.sun.deploy.resources.ResourceManager.getString("security.dialog.mixcode.info1") + "\n\n" + com.sun.deploy.resources.ResourceManager.getString("security.dialog.mixcode.info2") + "\n\n" + com.sun.deploy.resources.ResourceManager.getString("security.dialog.mixcode.info3");
/*      */ 
/*  482 */         String appLabelStr = com.sun.deploy.resources.ResourceManager.getString("dialog.template.name");
/*      */ 
/*  484 */         appInfo = appInfo == null ? new AppInfo() : appInfo;
/*      */ 
/*  486 */         return NativeMixedCodeDialog.show(title, masthead, message, info, okBtnStr, cancelBtnStr, helpBtnStr, closeBtnStr, helpTitle, helpMessage, appLabelStr, appInfo.getTitle());
/*      */       }
/*      */ 
/*  491 */       return MixedCodeInSwing.show(owner, appInfo, title, masthead, message, info, okBtnStr, cancelBtnStr, useWarning);
/*      */     }
/*      */     catch (Throwable t)
/*      */     {
/*  495 */       Trace.ignored(t);
/*  496 */     }return -1;
/*      */   }
/*      */ 
/*      */   public static int showSecurityDialog0(final AppInfo ainfo, final String title, final String topText, final String publisher, final URL appFrom, final boolean showAlways, final boolean checkAlways, final String okBtnStr, final String cancelBtnStr, final String[] securityAlerts, String[] securityInfo, final boolean showMoreInfo, final Certificate[] certs, final int start, final int end, final boolean majorWarning, final boolean httpsDialog)
/*      */   {
/*  536 */     final Stage parent = beforeDialog((Stage)null);
/*      */     int result;
/*      */     try
/*      */     {
/*  540 */       result = ((Integer)FXPluginToolkit.callAndWait(new Callable()
/*      */       {
/*      */         public Integer call() {
/*  543 */           String[] info = new String[0];
/*  544 */           if (this.val$securityInfo != null) {
/*  545 */             info = this.val$securityInfo;
/*      */           }
/*      */ 
/*  548 */           int securityInfoCount = info.length;
/*  549 */           info = FXUIFactory.addDetail(info, ainfo, true, true);
/*  550 */           ainfo.setVendor(publisher);
/*  551 */           ainfo.setFrom(appFrom);
/*      */ 
/*  553 */           DialogTemplate template = new DialogTemplate(ainfo, parent, title, topText);
/*      */ 
/*  557 */           if ((ainfo.getType() == 3) && (securityAlerts == null)) {
/*  558 */             template.setSecurityContent(false, checkAlways, okBtnStr, cancelBtnStr, securityAlerts, info, securityInfoCount, showMoreInfo, certs, start, end, majorWarning);
/*      */           }
/*  564 */           else if (!httpsDialog) {
/*  565 */             template.setNewSecurityContent(showAlways, checkAlways, okBtnStr, cancelBtnStr, securityAlerts, info, securityInfoCount, showMoreInfo, certs, start, end, majorWarning);
/*      */           }
/*      */           else
/*      */           {
/*  571 */             template.setSecurityContent(showAlways, checkAlways, okBtnStr, cancelBtnStr, securityAlerts, info, securityInfoCount, showMoreInfo, certs, start, end, majorWarning);
/*      */           }
/*      */ 
/*  579 */           FXUIFactory.placeWindow(template.getDialog());
/*  580 */           template.setVisible(true);
/*      */ 
/*  588 */           int userAnswer = template.getUserAnswer();
/*  589 */           return new Integer(userAnswer);
/*      */         }
/*      */       })).intValue();
/*      */     }
/*      */     catch (Throwable e)
/*      */     {
/*  594 */       return -1;
/*      */     } finally {
/*  596 */       afterDialog();
/*      */     }
/*  598 */     return result;
/*      */   }
/*      */ 
/*      */   public static int showIntegrationDialog(Object owner, AppInfo ainfo)
/*      */   {
/*  610 */     final Stage fOwner = beforeDialog((Stage)owner);
/*      */     try
/*      */     {
/*  613 */       return ((Integer)FXPluginToolkit.callAndWait(new Callable() {
/*      */         public Integer call() {
/*  615 */           String title = com.sun.deploy.uitoolkit.impl.fx.ui.resources.ResourceManager.getString("integration.title");
/*  616 */           boolean doShortcut = (this.val$ainfo.getDesktopHint()) || (this.val$ainfo.getMenuHint());
/*      */ 
/*  621 */           String key = "integration.text.shortcut";
/*      */ 
/*  623 */           if (doShortcut)
/*  624 */             key = "integration.text.both";
/*      */           else {
/*  626 */             key = "integration.text.association";
/*      */           }
/*      */ 
/*  629 */           String topText = com.sun.deploy.uitoolkit.impl.fx.ui.resources.ResourceManager.getString(key);
/*      */ 
/*  631 */           String[] security = new String[0];
/*      */ 
/*  634 */           String[] alerts = new String[0];
/*  635 */           alerts = FXUIFactory.addDetail(alerts, this.val$ainfo, false, true);
/*      */ 
/*  637 */           String[] info = new String[0];
/*      */ 
/*  639 */           info = FXUIFactory.addDetail(info, this.val$ainfo, true, false);
/*      */ 
/*  641 */           boolean showMoreInfo = alerts.length + info.length > 1;
/*      */ 
/*  643 */           String okBtnStr = com.sun.deploy.uitoolkit.impl.fx.ui.resources.ResourceManager.getString("common.ok_btn");
/*  644 */           String cancelBtnStr = com.sun.deploy.uitoolkit.impl.fx.ui.resources.ResourceManager.getString("integration.skip.button");
/*      */ 
/*  646 */           DialogTemplate template = new DialogTemplate(this.val$ainfo, fOwner, title, topText);
/*      */ 
/*  649 */           template.setSecurityContent(false, false, okBtnStr, cancelBtnStr, alerts, info, 0, showMoreInfo, null, 0, 0, false);
/*      */ 
/*  653 */           FXUIFactory.placeWindow(template.getDialog());
/*      */ 
/*  655 */           template.setVisible(true);
/*      */ 
/*  657 */           int userAnswer = template.getUserAnswer();
/*  658 */           return new Integer(userAnswer);
/*      */         }
/*      */       })).intValue();
/*      */     }
/*      */     catch (Throwable e)
/*      */     {
/*  663 */       return -1;
/*      */     } finally {
/*  665 */       afterDialog();
/*      */     }
/*      */   }
/*      */ 
/*      */   public static int showUpdateCheckDialog0()
/*      */   {
/*  677 */     String yesBtnKey = "autoupdatecheck.buttonYes";
/*  678 */     String noBtnKey = "autoupdatecheck.buttonNo";
/*  679 */     String askBtnKey = "autoupdatecheck.buttonAskLater";
/*      */ 
/*  681 */     final String title = com.sun.deploy.uitoolkit.impl.fx.ui.resources.ResourceManager.getMessage("autoupdatecheck.caption");
/*  682 */     final String infoStr = com.sun.deploy.uitoolkit.impl.fx.ui.resources.ResourceManager.getMessage("autoupdatecheck.message");
/*  683 */     final String masthead = com.sun.deploy.uitoolkit.impl.fx.ui.resources.ResourceManager.getMessage("autoupdatecheck.masthead");
/*      */ 
/*  685 */     AppInfo ainfo = new AppInfo();
/*      */ 
/*  687 */     final Stage owner = beforeDialog((Stage)null);
/*      */     try
/*      */     {
/*  691 */       return ((Integer)FXPluginToolkit.callAndWait(new Callable() {
/*      */         public Integer call() {
/*  693 */           DialogTemplate template = new DialogTemplate(this.val$ainfo, owner, title, masthead);
/*      */ 
/*  697 */           template.setUpdateCheckContent(infoStr, "autoupdatecheck.buttonYes", "autoupdatecheck.buttonNo", "autoupdatecheck.buttonAskLater");
/*      */ 
/*  700 */           FXUIFactory.placeWindow(template.getDialog());
/*      */ 
/*  702 */           template.setVisible(true);
/*      */ 
/*  704 */           int userAnswer = template.getUserAnswer();
/*  705 */           return new Integer(userAnswer);
/*      */         }
/*      */       })).intValue();
/*      */     }
/*      */     catch (Throwable e)
/*      */     {
/*  710 */       return 3;
/*      */     } finally {
/*  712 */       afterDialog();
/*      */     }
/*      */   }
/*      */ 
/*      */   public static int showListDialog0(Object owner, final String title, final String message, final String label, final boolean details, final ListView scrollList, final TreeMap clientAuthCertsMap)
/*      */   {
/*  742 */     final String okBtnStr = com.sun.deploy.uitoolkit.impl.fx.ui.resources.ResourceManager.getString("common.ok_btn");
/*  743 */     final String cancelBtnStr = com.sun.deploy.uitoolkit.impl.fx.ui.resources.ResourceManager.getString("common.cancel_btn");
/*      */ 
/*  745 */     Stage fOwner = beforeDialog((Stage)owner);
/*      */     try
/*      */     {
/*  749 */       return ((Integer)FXPluginToolkit.callAndWait(new Callable() {
/*      */         public Integer call() {
/*  751 */           DialogTemplate template = new DialogTemplate(new AppInfo(), this.val$fOwner, title, message);
/*      */ 
/*  754 */           template.setListContent(label, scrollList, details, okBtnStr, cancelBtnStr, clientAuthCertsMap);
/*      */ 
/*  757 */           FXUIFactory.placeWindow(template.getDialog());
/*  758 */           template.setVisible(true);
/*      */ 
/*  760 */           int userAnswer = template.getUserAnswer();
/*  761 */           return new Integer(userAnswer);
/*      */         }
/*      */       })).intValue();
/*      */     }
/*      */     catch (Throwable e)
/*      */     {
/*  766 */       return -1;
/*      */     } finally {
/*  768 */       afterDialog();
/*      */     }
/*      */   }
/*      */ 
/*      */   public static int showApiDialog(Object owner, AppInfo appInfo, final String title, final String message, final String label, final String files, final String always, final boolean checked)
/*      */   {
/*  789 */     final String okBtnStr = com.sun.deploy.uitoolkit.impl.fx.ui.resources.ResourceManager.getString("common.ok_btn");
/*  790 */     final String cancelBtnStr = com.sun.deploy.uitoolkit.impl.fx.ui.resources.ResourceManager.getString("common.cancel_btn");
/*  791 */     AppInfo ainfo = appInfo == null ? new AppInfo() : appInfo;
/*      */ 
/*  793 */     final Stage fOwner = beforeDialog((Stage)owner);
/*      */     try
/*      */     {
/*  797 */       return ((Integer)FXPluginToolkit.callAndWait(new Callable() {
/*      */         public Integer call() {
/*  799 */           DialogTemplate template = new DialogTemplate(this.val$ainfo, fOwner, title, message);
/*      */ 
/*  802 */           template.setApiContent(files, label, always, checked, okBtnStr, cancelBtnStr);
/*      */ 
/*  805 */           FXUIFactory.placeWindow(template.getDialog());
/*  806 */           template.setVisible(true);
/*      */ 
/*  808 */           int userAnswer = template.getUserAnswer();
/*  809 */           return new Integer(userAnswer);
/*      */         }
/*      */       })).intValue();
/*      */     }
/*      */     catch (Throwable e)
/*      */     {
/*  814 */       return -1;
/*      */     } finally {
/*  816 */       afterDialog();
/*      */     }
/*      */   }
/*      */ 
/*      */   public static void showExceptionDialog(Object owner, Throwable throwable, String masthead, String msg, String title)
/*      */   {
/*  828 */     String okBtnStr = com.sun.deploy.uitoolkit.impl.fx.ui.resources.ResourceManager.getString("common.ok_btn");
/*  829 */     String detailBtnStr = com.sun.deploy.uitoolkit.impl.fx.ui.resources.ResourceManager.getString("common.detail.button");
/*  830 */     if (msg == null) {
/*  831 */       msg = throwable.toString();
/*      */     }
/*  833 */     if (title == null) {
/*  834 */       title = com.sun.deploy.uitoolkit.impl.fx.ui.resources.ResourceManager.getString("error.default.title");
/*      */     }
/*  836 */     showErrorDialog(owner, new AppInfo(), title, masthead, msg, okBtnStr, detailBtnStr, throwable, null, null);
/*      */   }
/*      */ 
/*      */   public static void showCertificateExceptionDialog(Object owner, AppInfo ainfo, Throwable throwable, String msg, String title, Certificate[] certs)
/*      */   {
/*  847 */     String okBtnStr = com.sun.deploy.uitoolkit.impl.fx.ui.resources.ResourceManager.getString("common.ok_btn");
/*  848 */     String detailBtnStr = com.sun.deploy.uitoolkit.impl.fx.ui.resources.ResourceManager.getString("common.detail.button");
/*  849 */     if (msg == null) {
/*  850 */       msg = throwable.toString();
/*      */     }
/*  852 */     if (title == null) {
/*  853 */       title = com.sun.deploy.uitoolkit.impl.fx.ui.resources.ResourceManager.getString("error.default.title");
/*      */     }
/*  855 */     showErrorDialog(owner, ainfo, title, msg, null, okBtnStr, detailBtnStr, throwable, null, certs);
/*      */   }
/*      */ 
/*      */   public static CredentialInfo showPasswordDialog0(Object parent, final String title, final String notes, final boolean showUsername, final boolean showDomain, CredentialInfo info, final boolean saveEnabled, final String scheme)
/*      */   {
/*  883 */     final Stage fParent = beforeDialog((Stage)parent);
/*      */     try
/*      */     {
/*  887 */       return (CredentialInfo)FXPluginToolkit.callAndWait(new Callable() {
/*      */         public CredentialInfo call() {
/*  889 */           CredentialInfo result = null;
/*  890 */           CredentialInfo input = this.val$info;
/*      */ 
/*  892 */           DialogTemplate template = new DialogTemplate(new AppInfo(), fParent, title, "");
/*      */ 
/*  895 */           if (input == null) {
/*  896 */             input = new CredentialInfo();
/*      */           }
/*      */ 
/*  899 */           template.setPasswordContent(notes, showUsername, showDomain, input.getUserName(), input.getDomain(), saveEnabled, input.getPassword(), scheme);
/*      */ 
/*  902 */           FXUIFactory.placeWindow(template.getDialog());
/*  903 */           template.setVisible(true);
/*      */ 
/*  908 */           int answer = template.getUserAnswer();
/*      */ 
/*  910 */           if ((answer == 0) || (answer == 2)) {
/*  911 */             result = new CredentialInfo();
/*  912 */             result.setUserName(template.getUserName());
/*  913 */             result.setDomain(template.getDomain());
/*  914 */             result.setPassword(template.getPassword());
/*  915 */             result.setPasswordSaveApproval(template.isPasswordSaved());
/*      */           }
/*      */ 
/*  918 */           return result;
/*      */         }
/*      */       });
/*      */     }
/*      */     catch (Throwable e)
/*      */     {
/*  924 */       return null;
/*      */     } finally {
/*  926 */       afterDialog();
/*      */     }
/*      */   }
/*      */ 
/*      */   public int showSSVDialog(Object owner, AppInfo ainfo, String title, String masthead, String message, String moreInfoText, URL moreInfoURL, String choiceText, String choice1Label, String choice2Label, String btnOneLabel, String btnTwoLabel)
/*      */   {
/*  947 */     return showSSVDialog0(owner, ainfo, title, masthead, message, moreInfoText, moreInfoURL, choiceText, choice1Label, choice2Label, btnOneLabel, btnTwoLabel);
/*      */   }
/*      */ 
/*      */   public File[] showFileChooser(String initDir, String[] extensions, int mode, boolean showMultiple)
/*      */   {
/*  958 */     FileChooser fileChooser = new FileChooser();
/*  959 */     File f = initDir == null ? null : new File(initDir);
/*  960 */     if ((f != null) && (!f.isDirectory()))
/*      */     {
/*  963 */       f = f.getParentFile();
/*      */     }
/*      */ 
/*  969 */     fileChooser.setInitialDirectory(f);
/*      */ 
/*  971 */     if (extensions != null)
/*      */     {
/*  973 */       fileChooser.getExtensionFilters().setAll(new FileChooser.ExtensionFilter[] { new FileChooser.ExtensionFilter(Arrays.toString(extensions), Arrays.asList(extensions)) });
/*      */     }
/*      */ 
/*  979 */     if (showMultiple)
/*  980 */       return (File[])fileChooser.showOpenMultipleDialog(null).toArray(new File[0]);
/*  981 */     if (mode == 8) {
/*  982 */       return new File[] { fileChooser.showOpenDialog(null) };
/*      */     }
/*  984 */     return new File[] { fileChooser.showSaveDialog(null) };
/*      */   }
/*      */ 
/*      */   public static Pane getDetailPanel(String MoreInfo)
/*      */   {
/*  992 */     BorderPane detailPanel = new BorderPane()
/*      */     {
/*      */     };
/* 1001 */     TabPane tabPane = new TabPane();
/* 1002 */     tabPane.setId("detail-panel-tab-pane");
/* 1003 */     tabPane.getStyleClass().add("floating");
/* 1004 */     detailPanel.setCenter(tabPane);
/*      */ 
/* 1006 */     HBox intro = new HBox();
/* 1007 */     intro.setId("detail-panel-top-pane");
/* 1008 */     intro.setAlignment(Pos.BASELINE_LEFT);
/* 1009 */     Label label = new Label(com.sun.deploy.uitoolkit.impl.fx.ui.resources.ResourceManager.getString("launcherrordialog.error.label"));
/* 1010 */     label.setId("error-dialog-error-label");
/* 1011 */     label.setMinWidth((-1.0D / 0.0D));
/* 1012 */     intro.getChildren().add(label);
/*      */ 
/* 1014 */     String[] msg = MoreInfo.split("<split>");
/*      */ 
/* 1016 */     UITextArea ta = new UITextArea(msg[0]);
/* 1017 */     ta.setId("detail-panel-msg0");
/* 1018 */     ta.setPrefWidth(-1.0D);
/* 1019 */     intro.getChildren().add(ta);
/*      */ 
/* 1021 */     detailPanel.setTop(intro);
/*      */ 
/* 1026 */     int i = 1;
/* 1027 */     while (i + 1 < msg.length) {
/* 1028 */       Label tp12 = new Label();
/* 1029 */       tp12.getStyleClass().add("multiline-text");
/* 1030 */       tp12.setWrapText(true);
/* 1031 */       tp12.setText(msg[(i + 1)]);
/* 1032 */       Tab tab = new Tab();
/* 1033 */       tab.setText(msg[i]);
/* 1034 */       ScrollPane scrollPane = new ScrollPane();
/* 1035 */       scrollPane.setContent(tp12);
/* 1036 */       scrollPane.setFitToWidth(true);
/* 1037 */       tab.setContent(scrollPane);
/* 1038 */       tabPane.getTabs().add(tab);
/* 1039 */       i += 2;
/*      */     }
/*      */ 
/* 1043 */     return detailPanel;
/*      */   }
/*      */ 
/*      */   private static Stage beforeDialog(Stage component)
/*      */   {
/* 1050 */     return component;
/*      */   }
/*      */ 
/*      */   private static void afterDialog()
/*      */   {
/*      */   }
/*      */ 
/*      */   public static String[] addDetail(String[] list, AppInfo ainfo, boolean doShortcut, boolean doAssociation)
/*      */   {
/* 1064 */     String title = ainfo.getTitle();
/* 1065 */     if (title == null) {
/* 1066 */       title = "";
/*      */     }
/*      */ 
/* 1069 */     ArrayList al = new ArrayList();
/* 1070 */     for (int i = 0; i < list.length; i++) {
/* 1071 */       al.add(list[i]);
/*      */     }
/*      */ 
/* 1086 */     if (doShortcut) {
/* 1087 */       String message = null;
/* 1088 */       if ((ainfo.getDesktopHint()) && (ainfo.getMenuHint()))
/*      */       {
/* 1090 */         message = com.sun.deploy.uitoolkit.impl.fx.ui.resources.ResourceManager.getString("install.windows.both.message");
/*      */       }
/* 1095 */       else if (ainfo.getDesktopHint())
/* 1096 */         message = com.sun.deploy.uitoolkit.impl.fx.ui.resources.ResourceManager.getString("install.desktop.message");
/* 1097 */       else if (ainfo.getMenuHint())
/*      */       {
/* 1099 */         message = com.sun.deploy.uitoolkit.impl.fx.ui.resources.ResourceManager.getString("install.windows.menu.message");
/*      */       }
/*      */ 
/* 1105 */       if (message != null) {
/* 1106 */         al.add(message);
/*      */       }
/*      */     }
/* 1109 */     return (String[])al.toArray(list);
/*      */   }
/*      */ 
/*      */   public static int showSSVDialog0(Object owner, AppInfo ainfo, final String title, final String masthead, final String message, final String moreInfoText, final URL moreInfoURL, final String choiceText, final String choice1Label, final String choice2Label, final String btnOneLabel, final String btnTwoLabel)
/*      */   {
/* 1126 */     final Stage fOwner = beforeDialog((Stage)owner);
/*      */     try
/*      */     {
/* 1130 */       return ((Integer)FXPluginToolkit.callAndWait(new Callable() {
/*      */         public Integer call() {
/* 1132 */           DialogTemplate template = new DialogTemplate(this.val$ainfo, fOwner, title, masthead);
/*      */ 
/* 1135 */           template.setSSVContent(message, moreInfoText, moreInfoURL, choiceText, choice1Label, choice2Label, btnOneLabel, btnTwoLabel);
/*      */ 
/* 1139 */           FXUIFactory.placeWindow(template.getDialog());
/* 1140 */           template.setVisible(true);
/*      */ 
/* 1142 */           int userAnswer = template.getUserAnswer();
/* 1143 */           return new Integer(userAnswer);
/*      */         }
/*      */       })).intValue();
/*      */     }
/*      */     catch (Throwable e)
/*      */     {
/* 1148 */       return -1;
/*      */     } finally {
/* 1150 */       afterDialog();
/*      */     }
/*      */   }
/*      */ 
/*      */   public static void placeWindow(Window window)
/*      */   {
/* 1163 */     window.centerOnScreen();
/*      */   }
/*      */ 
/*      */   private static Object invokeLater(Runnable r, Integer dummy)
/*      */     throws Exception
/*      */   {
/* 1224 */     if (r != null)
/*      */     {
/* 1228 */       return (Integer)DeploySysRun.executePrivileged(new DeploySysAction()
/*      */       {
/*      */         public Object execute() {
/* 1231 */           Platform.runLater(this.val$r);
/* 1232 */           return null;
/*      */         }
/*      */       }
/*      */       , new Integer(-1));
/*      */     }
/*      */ 
/* 1236 */     return Integer.valueOf(-1);
/*      */   }
/*      */ 
/*      */   public int showSSV3Dialog(final Object owner, final AppInfo ainfo, final int messageType, final String title, final String masthead, final String mainText, final String location, final String prompt, final String multiPrompt, final String multiText, final String run, final String update, final String cancel, final String always, final URL updateURL)
/*      */   {
/* 1256 */     Stage fOwner = beforeDialog((Stage)owner);
/*      */     try
/*      */     {
/* 1260 */       return ((Integer)FXPluginToolkit.callAndWait(new Callable() {
/*      */         public Integer call() {
/* 1262 */           return Integer.valueOf(FXSSV3Dialog.showSSV3Dialog(owner, ainfo, messageType, title, masthead, mainText, location, prompt, multiPrompt, multiText, run, update, cancel, always, updateURL));
/*      */         }
/*      */       })).intValue();
/*      */     }
/*      */     catch (Throwable e)
/*      */     {
/* 1270 */       return -1;
/*      */     } finally {
/* 1272 */       afterDialog();
/*      */     }
/*      */   }
/*      */ 
/*      */   public int showPublisherInfo(Object owner, final AppInfo ainfo, final String title, final String masthead, final String message, final String okBtnStr, final String detailBtnStr, final String moreInfo)
/*      */   {
/* 1301 */     final Stage fOwner = beforeDialog((Stage)owner);
/*      */     try
/*      */     {
/* 1305 */       return ((Integer)FXPluginToolkit.callAndWait(new Callable() {
/*      */         public Integer call() {
/* 1307 */           DialogTemplate template = new DialogTemplate(ainfo, fOwner, title, masthead);
/*      */ 
/* 1309 */           Pane detailPanel = null;
/* 1310 */           if (moreInfo != null) {
/* 1311 */             detailPanel = FXUIFactory.getDetailPanel(moreInfo);
/*      */           }
/* 1313 */           template.setPublisherInfo(message, okBtnStr, detailBtnStr, detailPanel, false);
/*      */ 
/* 1316 */           FXUIFactory.placeWindow(template.getDialog());
/* 1317 */           template.setVisible(true);
/*      */ 
/* 1319 */           int userAnswer = template.getUserAnswer();
/* 1320 */           return new Integer(userAnswer);
/*      */         }
/*      */       })).intValue();
/*      */     }
/*      */     catch (Throwable e)
/*      */     {
/* 1325 */       return -1;
/*      */     } finally {
/* 1327 */       afterDialog();
/*      */     }
/*      */   }
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.deploy.uitoolkit.impl.fx.ui.FXUIFactory
 * JD-Core Version:    0.6.2
 */