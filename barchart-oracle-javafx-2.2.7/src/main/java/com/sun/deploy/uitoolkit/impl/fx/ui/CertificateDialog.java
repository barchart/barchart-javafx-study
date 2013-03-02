/*     */ package com.sun.deploy.uitoolkit.impl.fx.ui;
/*     */ 
/*     */ import com.sun.deploy.uitoolkit.impl.fx.ui.resources.ResourceManager;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.Principal;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.text.MessageFormat;
/*     */ import javafx.beans.property.ReadOnlyObjectWrapper;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ import javafx.collections.FXCollections;
/*     */ import javafx.collections.ListChangeListener;
/*     */ import javafx.collections.ListChangeListener.Change;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.event.ActionEvent;
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.geometry.Orientation;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.control.Button;
/*     */ import javafx.scene.control.Label;
/*     */ import javafx.scene.control.MultipleSelectionModel;
/*     */ import javafx.scene.control.ScrollPane;
/*     */ import javafx.scene.control.SelectionMode;
/*     */ import javafx.scene.control.SplitPane;
/*     */ import javafx.scene.control.TableColumn;
/*     */ import javafx.scene.control.TableColumn.CellDataFeatures;
/*     */ import javafx.scene.control.TableView;
/*     */ import javafx.scene.control.TableView.TableViewSelectionModel;
/*     */ import javafx.scene.control.TextArea;
/*     */ import javafx.scene.control.TreeItem;
/*     */ import javafx.scene.control.TreeView;
/*     */ import javafx.scene.layout.BorderPane;
/*     */ import javafx.scene.layout.FlowPane;
/*     */ import javafx.stage.Stage;
/*     */ import javafx.util.Callback;
/*     */ import sun.misc.HexDumpEncoder;
/*     */ import sun.security.x509.SerialNumber;
/*     */ 
/*     */ public class CertificateDialog
/*     */ {
/*     */   public static void showCertificates(Stage parent, Certificate[] certs, int start, int end)
/*     */   {
/*  35 */     FXDialog details = new FXDialog(ResourceManager.getMessage("cert.dialog.caption"), parent, true);
/*     */ 
/*  37 */     details.setWidth(800.0D);
/*  38 */     details.setHeight(600.0D);
/*     */ 
/*  40 */     BorderPane borderPane = new BorderPane();
/*  41 */     details.setContentPane(borderPane);
/*     */ 
/*  43 */     borderPane.setCenter(getComponents(parent, certs, start, end));
/*     */ 
/*  45 */     FlowPane buttonPanel = new FlowPane();
/*  46 */     buttonPanel.getStyleClass().add("button-bar");
/*     */ 
/*  48 */     Button closeButton = new Button(ResourceManager.getMessage("cert.dialog.close"));
/*  49 */     closeButton.setDefaultButton(true);
/*  50 */     closeButton.setOnAction(new EventHandler() {
/*     */       public void handle(ActionEvent e) {
/*  52 */         this.val$details.hide();
/*     */       }
/*     */     });
/*  55 */     buttonPanel.getChildren().add(closeButton);
/*     */ 
/*  57 */     borderPane.setBottom(buttonPanel);
/*     */ 
/*  61 */     details.show();
/*     */   }
/*     */ 
/*     */   private static Node getComponents(Stage parent, Certificate[] certs, int start, int end)
/*     */   {
/*  67 */     SplitPane panel = new SplitPane();
/*     */ 
/*  69 */     if ((certs.length > start) && ((certs[start] instanceof X509Certificate))) {
/*  70 */       TreeView certChainTree = buildCertChainTree(certs, start, end);
/*  71 */       final TableView certInfoTable = new TableView();
/*  72 */       final TextArea textArea = new TextArea();
/*  73 */       textArea.setEditable(false);
/*     */ 
/*  75 */       MultipleSelectionModel treeSM = certChainTree.getSelectionModel();
/*  76 */       treeSM.getSelectedItems().addListener(new ListChangeListener() {
/*     */         public void onChanged(ListChangeListener.Change<? extends TreeItem<CertificateDialog.CertificateInfo>> change) {
/*  78 */           ObservableList selectedItems = this.val$treeSM.getSelectedItems();
/*  79 */           if ((selectedItems != null) && (selectedItems.size() == 1)) {
/*  80 */             TreeItem node = (TreeItem)selectedItems.get(0);
/*  81 */             CertificateDialog.CertificateInfo certInfo = (CertificateDialog.CertificateInfo)node.getValue();
/*  82 */             CertificateDialog.showCertificateInfo(certInfo.getCertificate(), certInfoTable, textArea);
/*     */           }
/*     */         }
/*     */       });
/*  88 */       TableColumn fieldColumn = new TableColumn();
/*  89 */       fieldColumn.setText(ResourceManager.getMessage("cert.dialog.field"));
/*  90 */       fieldColumn.setCellValueFactory(new Callback() {
/*     */         public ObservableValue<Object> call(TableColumn.CellDataFeatures<CertificateDialog.Row, Object> p) {
/*  92 */           return new ReadOnlyObjectWrapper(((CertificateDialog.Row)p.getValue()).field);
/*     */         }
/*     */       });
/*  95 */       TableColumn valueColumn = new TableColumn();
/*  96 */       valueColumn.setText(ResourceManager.getMessage("cert.dialog.value"));
/*  97 */       valueColumn.setCellValueFactory(new Callback() {
/*     */         public ObservableValue<Object> call(TableColumn.CellDataFeatures<CertificateDialog.Row, Object> p) {
/*  99 */           return new ReadOnlyObjectWrapper(((CertificateDialog.Row)p.getValue()).value);
/*     */         }
/*     */       });
/* 102 */       certInfoTable.getColumns().addAll(new TableColumn[] { fieldColumn, valueColumn });
/*     */ 
/* 104 */       TableView.TableViewSelectionModel tableSM = certInfoTable.getSelectionModel();
/* 105 */       tableSM.setSelectionMode(SelectionMode.SINGLE);
/* 106 */       tableSM.getSelectedItems().addListener(new ListChangeListener() {
/*     */         public void onChanged(ListChangeListener.Change<? extends String> change) {
/* 108 */           ObservableList selectedItems = this.val$tableSM.getSelectedItems();
/* 109 */           if ((selectedItems != null) && (selectedItems.size() == 1)) {
/* 110 */             String value = ((CertificateDialog.Row)selectedItems.get(0)).value;
/*     */ 
/* 112 */             textArea.setText(value);
/*     */           }
/*     */         }
/*     */       });
/* 118 */       certChainTree.setMinWidth((-1.0D / 0.0D));
/* 119 */       certChainTree.setMinHeight((-1.0D / 0.0D));
/* 120 */       ScrollPane scrollPane = makeScrollPane(certChainTree);
/* 121 */       scrollPane.setFitToWidth(true);
/* 122 */       scrollPane.setFitToHeight(true);
/* 123 */       panel.getItems().add(scrollPane);
/*     */ 
/* 126 */       SplitPane panelInfo = new SplitPane();
/* 127 */       panelInfo.setOrientation(Orientation.VERTICAL);
/* 128 */       panelInfo.getItems().add(certInfoTable);
/*     */ 
/* 130 */       textArea.setPrefWidth(320.0D);
/* 131 */       textArea.setPrefHeight(120.0D);
/* 132 */       panelInfo.getItems().add(textArea);
/* 133 */       panelInfo.setDividerPosition(0, 0.8D);
/*     */ 
/* 135 */       panel.getItems().add(panelInfo);
/* 136 */       panel.setDividerPosition(0, 0.4D);
/*     */ 
/* 139 */       treeSM.select(0);
/*     */     }
/* 141 */     return panel;
/*     */   }
/*     */ 
/*     */   private static ScrollPane makeScrollPane(Node node) {
/* 145 */     ScrollPane scrollPane = new ScrollPane();
/* 146 */     scrollPane.setContent(node);
/* 147 */     if ((node instanceof Label)) {
/* 148 */       scrollPane.setFitToWidth(true);
/*     */     }
/* 150 */     return scrollPane;
/*     */   }
/*     */ 
/*     */   private static TreeView buildCertChainTree(Certificate[] cert, int start, int end)
/*     */   {
/* 158 */     TreeItem root = null;
/*     */ 
/* 160 */     TreeItem currentNode = null;
/*     */ 
/* 162 */     for (int i = start; (i < cert.length) && (i < end); i++) {
/* 163 */       TreeItem childNode = new TreeItem(new CertificateInfo((X509Certificate)cert[i]));
/*     */ 
/* 165 */       if (root == null)
/* 166 */         root = childNode;
/*     */       else {
/* 168 */         currentNode.getChildren().add(childNode);
/*     */       }
/* 170 */       currentNode = childNode;
/*     */     }
/*     */ 
/* 173 */     TreeView tree = new TreeView();
/* 174 */     tree.setShowRoot(true);
/* 175 */     tree.setRoot(root);
/*     */ 
/* 178 */     tree.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
/*     */ 
/* 180 */     return tree;
/*     */   }
/*     */ 
/*     */   private static void showCertificateInfo(X509Certificate cert, TableView certInfoTable, TextArea textArea)
/*     */   {
/* 199 */     String certVersion = "V" + cert.getVersion();
/* 200 */     String certSerialNumber = "[xxxxx-xxxxx]";
/* 201 */     String md5 = null;
/* 202 */     String sha1 = null;
/*     */     try
/*     */     {
/* 205 */       SerialNumber serial = new SerialNumber(cert.getSerialNumber());
/*     */ 
/* 210 */       certSerialNumber = "[" + serial.getNumber() + "]";
/*     */ 
/* 212 */       md5 = getCertFingerPrint("MD5", cert);
/* 213 */       sha1 = getCertFingerPrint("SHA1", cert);
/*     */     }
/*     */     catch (Throwable e) {
/*     */     }
/* 217 */     String certSigAlg = "[" + cert.getSigAlgName() + "]";
/* 218 */     String certIssuer = formatDNString(cert.getIssuerDN().toString());
/* 219 */     String certValidity = "[From: " + cert.getNotBefore() + ",\n To: " + cert.getNotAfter() + "]";
/*     */ 
/* 221 */     String certSubject = formatDNString(cert.getSubjectDN().toString());
/*     */ 
/* 223 */     HexDumpEncoder encoder = new HexDumpEncoder();
/* 224 */     String certSig = encoder.encodeBuffer(cert.getSignature());
/*     */ 
/* 226 */     ObservableList data = FXCollections.observableArrayList(new Row[] { new Row(ResourceManager.getMessage("cert.dialog.field.Version"), certVersion), new Row(ResourceManager.getMessage("cert.dialog.field.SerialNumber"), certSerialNumber), new Row(ResourceManager.getMessage("cert.dialog.field.SignatureAlg"), certSigAlg), new Row(ResourceManager.getMessage("cert.dialog.field.Issuer"), certIssuer), new Row(ResourceManager.getMessage("cert.dialog.field.Validity"), certValidity), new Row(ResourceManager.getMessage("cert.dialog.field.Subject"), certSubject), new Row(ResourceManager.getMessage("cert.dialog.field.Signature"), certSig), new Row(ResourceManager.getMessage("cert.dialog.field.md5Fingerprint"), md5), new Row(ResourceManager.getMessage("cert.dialog.field.sha1Fingerprint"), sha1) });
/*     */ 
/* 238 */     certInfoTable.setItems(data);
/*     */ 
/* 241 */     certInfoTable.getSelectionModel().select(8, null);
/*     */   }
/*     */ 
/*     */   public static String formatDNString(String dnString)
/*     */   {
/* 248 */     int len = dnString.length();
/* 249 */     int last = 0;
/* 250 */     boolean inQuote = false;
/*     */ 
/* 252 */     StringBuffer buffer = new StringBuffer();
/*     */ 
/* 254 */     for (int i = 0; i < len; i++) {
/* 255 */       char ch = dnString.charAt(i);
/*     */ 
/* 258 */       if ((ch == '"') || (ch == '\'')) {
/* 259 */         inQuote = !inQuote;
/*     */       }
/*     */ 
/* 262 */       if ((ch == ',') && (!inQuote))
/* 263 */         buffer.append(",\n");
/*     */       else {
/* 265 */         buffer.append(ch);
/*     */       }
/*     */     }
/*     */ 
/* 269 */     return buffer.toString();
/*     */   }
/*     */ 
/*     */   public static String getCertFingerPrint(String mdAlg, X509Certificate cert)
/*     */     throws Exception
/*     */   {
/* 277 */     byte[] encCertInfo = cert.getEncoded();
/* 278 */     MessageDigest md = MessageDigest.getInstance(mdAlg);
/* 279 */     byte[] digest = md.digest(encCertInfo);
/*     */ 
/* 281 */     return toHexString(digest);
/*     */   }
/*     */ 
/*     */   private static void byte2hex(byte b, StringBuffer buf)
/*     */   {
/* 289 */     char[] hexChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
/*     */ 
/* 291 */     int high = (b & 0xF0) >> 4;
/* 292 */     int low = b & 0xF;
/* 293 */     buf.append(hexChars[high]);
/* 294 */     buf.append(hexChars[low]);
/*     */   }
/*     */ 
/*     */   private static String toHexString(byte[] block)
/*     */   {
/* 301 */     StringBuffer buf = new StringBuffer();
/* 302 */     int len = block.length;
/* 303 */     for (int i = 0; i < len; i++) {
/* 304 */       byte2hex(block[i], buf);
/* 305 */       if (i < len - 1) {
/* 306 */         buf.append(":");
/*     */       }
/*     */     }
/* 309 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   public static class CertificateInfo
/*     */   {
/*     */     X509Certificate cert;
/*     */ 
/*     */     public CertificateInfo(X509Certificate cert) {
/* 317 */       this.cert = cert;
/*     */     }
/*     */ 
/*     */     public X509Certificate getCertificate() {
/* 321 */       return this.cert;
/*     */     }
/*     */ 
/*     */     private String extractAliasName(X509Certificate cert)
/*     */     {
/* 331 */       String subjectName = ResourceManager.getMessage("security.dialog.unknown.subject");
/* 332 */       String issuerName = ResourceManager.getMessage("security.dialog.unknown.issuer");
/*     */       try
/*     */       {
/* 336 */         Principal principal = cert.getSubjectDN();
/* 337 */         Principal principalIssuer = cert.getIssuerDN();
/*     */ 
/* 340 */         String subjectDNName = principal.getName();
/* 341 */         String issuerDNName = principalIssuer.getName();
/*     */ 
/* 344 */         subjectName = extractFromQuote(subjectDNName, "CN=");
/*     */ 
/* 346 */         if (subjectName == null) {
/* 347 */           subjectName = extractFromQuote(subjectDNName, "O=");
/*     */         }
/* 349 */         if (subjectName == null) {
/* 350 */           subjectName = ResourceManager.getMessage("security.dialog.unknown.subject");
/*     */         }
/*     */ 
/* 353 */         issuerName = extractFromQuote(issuerDNName, "CN=");
/*     */ 
/* 355 */         if (issuerName == null) {
/* 356 */           issuerName = extractFromQuote(issuerDNName, "O=");
/*     */         }
/* 358 */         if (issuerName == null) {
/* 359 */           issuerName = ResourceManager.getMessage("security.dialog.unknown.issuer");
/*     */         }
/*     */ 
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/*     */       }
/*     */ 
/* 367 */       MessageFormat mf = new MessageFormat(ResourceManager.getMessage("security.dialog.certShowName"));
/* 368 */       Object[] args = { subjectName, issuerName };
/* 369 */       return mf.format(args);
/*     */     }
/*     */ 
/*     */     private String extractFromQuote(String s, String prefix)
/*     */     {
/* 376 */       if (s == null) {
/* 377 */         return null;
/*     */       }
/*     */ 
/* 382 */       int x = s.indexOf(prefix);
/* 383 */       int y = 0;
/*     */ 
/* 385 */       if (x >= 0) {
/* 386 */         x += prefix.length();
/*     */ 
/* 389 */         if (s.charAt(x) == '"')
/*     */         {
/* 393 */           x += 1;
/*     */ 
/* 395 */           y = s.indexOf('"', x);
/*     */         }
/*     */         else {
/* 398 */           y = s.indexOf(',', x);
/*     */         }
/*     */ 
/* 401 */         if (y < 0) {
/* 402 */           return s.substring(x);
/*     */         }
/* 404 */         return s.substring(x, y);
/*     */       }
/*     */ 
/* 408 */       return null;
/*     */     }
/*     */ 
/*     */     public String toString()
/*     */     {
/* 413 */       return extractAliasName(this.cert);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class Row
/*     */   {
/*     */     public String field;
/*     */     public String value;
/*     */ 
/*     */     Row(String field, String value)
/*     */     {
/* 188 */       this.field = field;
/* 189 */       this.value = value;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.deploy.uitoolkit.impl.fx.ui.CertificateDialog
 * JD-Core Version:    0.6.2
 */