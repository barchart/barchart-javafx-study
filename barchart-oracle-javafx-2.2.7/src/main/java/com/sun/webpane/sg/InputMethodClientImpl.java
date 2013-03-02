/*     */ package com.sun.webpane.sg;
/*     */ 
/*     */ import com.sun.webpane.platform.InputMethodClient;
/*     */ import com.sun.webpane.platform.WebPage;
/*     */ import com.sun.webpane.platform.WebPageClient;
/*     */ import com.sun.webpane.platform.event.WCInputMethodEvent;
/*     */ import com.sun.webpane.platform.graphics.WCPoint;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.geometry.Point2D;
/*     */ import javafx.scene.Scene;
/*     */ import javafx.scene.input.InputMethodEvent;
/*     */ import javafx.scene.input.InputMethodHighlight;
/*     */ import javafx.scene.input.InputMethodRequests;
/*     */ import javafx.scene.input.InputMethodTextRun;
/*     */ import javafx.scene.web.WebView;
/*     */ 
/*     */ public class InputMethodClientImpl
/*     */   implements InputMethodClient, InputMethodRequests
/*     */ {
/*     */   private final WeakReference<WebView> wvRef;
/*     */   private WebPage webPage;
/*     */   private boolean state;
/*     */ 
/*     */   public InputMethodClientImpl(WebView paramWebView, WebPage paramWebPage)
/*     */   {
/*  31 */     this.wvRef = new WeakReference(paramWebView);
/*  32 */     setPage(paramWebPage);
/*     */   }
/*     */ 
/*     */   public final void setPage(WebPage paramWebPage) {
/*  36 */     this.webPage = paramWebPage;
/*  37 */     if (paramWebPage != null) paramWebPage.setInputMethodClient(this); 
/*     */   }
/*     */ 
/*     */   public void activateInputMethods(boolean paramBoolean)
/*     */   {
/*  41 */     WebView localWebView = (WebView)this.wvRef.get();
/*  42 */     if ((localWebView != null) && (localWebView.getScene() != null)) {
/*  43 */       localWebView.getScene().impl_enableInputMethodEvents(paramBoolean);
/*     */     }
/*  45 */     this.state = paramBoolean;
/*     */   }
/*     */ 
/*     */   public boolean getInputMethodState() {
/*  49 */     return this.state;
/*     */   }
/*     */ 
/*     */   public static WCInputMethodEvent convertToWCInputMethodEvent(InputMethodEvent paramInputMethodEvent)
/*     */   {
/*  56 */     ArrayList localArrayList = new ArrayList();
/*  57 */     StringBuilder localStringBuilder = new StringBuilder();
/*  58 */     int i = 0;
/*     */ 
/*  61 */     for (Iterator localIterator = paramInputMethodEvent.getComposed().iterator(); localIterator.hasNext(); ) { localObject = (InputMethodTextRun)localIterator.next();
/*  62 */       String str = ((InputMethodTextRun)localObject).getText();
/*     */ 
/*  66 */       InputMethodHighlight localInputMethodHighlight = ((InputMethodTextRun)localObject).getHighlight();
/*  67 */       localArrayList.add(Integer.valueOf(i));
/*  68 */       localArrayList.add(Integer.valueOf(i + str.length()));
/*     */ 
/*  72 */       localArrayList.add(Integer.valueOf((localInputMethodHighlight == InputMethodHighlight.SELECTED_CONVERTED) || (localInputMethodHighlight == InputMethodHighlight.SELECTED_RAW) ? 1 : 0));
/*     */ 
/*  74 */       i += str.length();
/*  75 */       localStringBuilder.append(str);
/*     */     }
/*     */ 
/*  78 */     int j = localArrayList.size();
/*     */ 
/*  81 */     if (j == 0) {
/*  82 */       localArrayList.add(Integer.valueOf(0));
/*  83 */       localArrayList.add(Integer.valueOf(i));
/*  84 */       localArrayList.add(Integer.valueOf(0));
/*  85 */       j = localArrayList.size();
/*     */     }
/*  87 */     Object localObject = new int[j];
/*  88 */     for (int k = 0; k < j; k++) {
/*  89 */       localObject[k] = ((Integer)localArrayList.get(k)).intValue();
/*     */     }
/*     */ 
/*  92 */     return new WCInputMethodEvent(paramInputMethodEvent.getCommitted(), localStringBuilder.toString(), (int[])localObject, paramInputMethodEvent.getCaretPosition());
/*     */   }
/*     */ 
/*     */   public Point2D getTextLocation(int paramInt)
/*     */   {
/*  98 */     int[] arrayOfInt = this.webPage.getClientTextLocation(paramInt);
/*  99 */     WCPoint localWCPoint = this.webPage.getPageClient().windowToScreen(new WCPoint(arrayOfInt[0], arrayOfInt[1]));
/* 100 */     return new Point2D(localWCPoint.getIntX(), localWCPoint.getIntY());
/*     */   }
/*     */ 
/*     */   public int getLocationOffset(int paramInt1, int paramInt2) {
/* 104 */     WCPoint localWCPoint = this.webPage.getPageClient().windowToScreen(new WCPoint(0.0F, 0.0F));
/* 105 */     return this.webPage.getClientLocationOffset(paramInt1 - localWCPoint.getIntX(), paramInt2 - localWCPoint.getIntY());
/*     */   }
/*     */ 
/*     */   public void cancelLatestCommittedText()
/*     */   {
/*     */   }
/*     */ 
/*     */   public String getSelectedText() {
/* 113 */     return this.webPage.getClientSelectedText();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.sg.InputMethodClientImpl
 * JD-Core Version:    0.6.2
 */