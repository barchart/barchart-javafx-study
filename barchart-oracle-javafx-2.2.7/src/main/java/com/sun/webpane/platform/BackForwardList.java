/*     */ package com.sun.webpane.platform;
/*     */ 
/*     */ import com.sun.webpane.platform.event.WCChangeEvent;
/*     */ import com.sun.webpane.platform.event.WCChangeListener;
/*     */ import com.sun.webpane.platform.graphics.WCImage;
/*     */ import com.sun.webpane.webkit.network.URLs;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.util.Date;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class BackForwardList
/*     */ {
/*     */   private final WebPage page;
/* 130 */   private final List<WCChangeListener> listenerList = new LinkedList();
/*     */ 
/*     */   BackForwardList(WebPage paramWebPage)
/*     */   {
/* 134 */     this.page = paramWebPage;
/*     */ 
/* 138 */     paramWebPage.addLoadListenerClient(new LoadListenerClient()
/*     */     {
/*     */       public void dispatchLoadEvent(long paramAnonymousLong, int paramAnonymousInt1, String paramAnonymousString1, String paramAnonymousString2, double paramAnonymousDouble, int paramAnonymousInt2)
/*     */       {
/* 146 */         if (paramAnonymousInt1 == 14) {
/* 147 */           BackForwardList.Entry localEntry = BackForwardList.this.getCurrentEntry();
/* 148 */           if (localEntry != null)
/* 149 */             localEntry.updateLastVisitedDate();
/*     */         }
/*     */       }
/*     */ 
/*     */       public void dispatchResourceLoadEvent(long paramAnonymousLong, int paramAnonymousInt1, String paramAnonymousString1, String paramAnonymousString2, double paramAnonymousDouble, int paramAnonymousInt2)
/*     */       {
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public int size()
/*     */   {
/* 165 */     return bflSize(this.page.getPage());
/*     */   }
/*     */ 
/*     */   public int getMaximumSize() {
/* 169 */     return bflGetMaximumSize(this.page.getPage());
/*     */   }
/*     */ 
/*     */   public void setMaximumSize(int paramInt) {
/* 173 */     bflSetMaximumSize(this.page.getPage(), paramInt);
/*     */   }
/*     */ 
/*     */   public int getCurrentIndex() {
/* 177 */     return bflGetCurrentIndex(this.page.getPage());
/*     */   }
/*     */ 
/*     */   public boolean isEmpty() {
/* 181 */     return size() == 0;
/*     */   }
/*     */ 
/*     */   public void setEnabled(boolean paramBoolean) {
/* 185 */     bflSetEnabled(this.page.getPage(), paramBoolean);
/*     */   }
/*     */ 
/*     */   public boolean isEnabled() {
/* 189 */     return bflIsEnabled(this.page.getPage());
/*     */   }
/*     */ 
/*     */   public Entry get(int paramInt) {
/* 193 */     Entry localEntry = (Entry)bflGet(this.page.getPage(), paramInt);
/* 194 */     return localEntry;
/*     */   }
/*     */ 
/*     */   public Entry getCurrentEntry() {
/* 198 */     return get(getCurrentIndex());
/*     */   }
/*     */ 
/*     */   public int indexOf(Entry paramEntry) {
/* 202 */     return bflIndexOf(this.page.getPage(), paramEntry.pitem, false);
/*     */   }
/*     */ 
/*     */   public boolean contains(Entry paramEntry) {
/* 206 */     return indexOf(paramEntry) >= 0;
/*     */   }
/*     */ 
/*     */   public Entry[] toArray() {
/* 210 */     int i = size();
/* 211 */     Entry[] arrayOfEntry = new Entry[i];
/* 212 */     for (int j = 0; j < i; j++) {
/* 213 */       arrayOfEntry[j] = get(j);
/*     */     }
/* 215 */     return arrayOfEntry;
/*     */   }
/*     */ 
/*     */   public void setCurrentIndex(int paramInt) {
/* 219 */     if (bflSetCurrentIndex(this.page.getPage(), paramInt) < 0)
/* 220 */       throw new IllegalArgumentException("invalid index: " + paramInt);
/*     */   }
/*     */ 
/*     */   private boolean canGoBack(int paramInt)
/*     */   {
/* 225 */     return paramInt > 0;
/*     */   }
/*     */ 
/*     */   public boolean canGoBack() {
/* 229 */     return canGoBack(getCurrentIndex());
/*     */   }
/*     */ 
/*     */   public boolean goBack() {
/* 233 */     int i = getCurrentIndex();
/* 234 */     if (canGoBack(i)) {
/* 235 */       setCurrentIndex(i - 1);
/* 236 */       return true;
/*     */     }
/* 238 */     return false;
/*     */   }
/*     */ 
/*     */   private boolean canGoForward(int paramInt) {
/* 242 */     return paramInt < size() - 1;
/*     */   }
/*     */ 
/*     */   public boolean canGoForward() {
/* 246 */     return canGoForward(getCurrentIndex());
/*     */   }
/*     */ 
/*     */   public boolean goForward() {
/* 250 */     int i = getCurrentIndex();
/* 251 */     if (canGoForward(i)) {
/* 252 */       setCurrentIndex(i + 1);
/* 253 */       return true;
/*     */     }
/* 255 */     return false;
/*     */   }
/*     */ 
/*     */   public void addChangeListener(WCChangeListener paramWCChangeListener) {
/* 259 */     if (paramWCChangeListener == null) {
/* 260 */       return;
/*     */     }
/* 262 */     if (this.listenerList.size() == 0) {
/* 263 */       bflSetHostObject(this.page.getPage(), this);
/*     */     }
/* 265 */     this.listenerList.add(paramWCChangeListener);
/*     */   }
/*     */ 
/*     */   public void removeChangeListener(WCChangeListener paramWCChangeListener) {
/* 269 */     if (paramWCChangeListener == null) {
/* 270 */       return;
/*     */     }
/* 272 */     this.listenerList.remove(paramWCChangeListener);
/* 273 */     if (this.listenerList.size() == 0)
/* 274 */       bflSetHostObject(this.page.getPage(), null);
/*     */   }
/*     */ 
/*     */   public WCChangeListener[] getChangeListeners()
/*     */   {
/* 279 */     return (WCChangeListener[])this.listenerList.toArray();
/*     */   }
/*     */ 
/*     */   private void notifyChanged()
/*     */   {
/* 284 */     for (WCChangeListener localWCChangeListener : this.listenerList)
/* 285 */       localWCChangeListener.stateChanged(new WCChangeEvent(this));
/*     */   }
/*     */ 
/*     */   private static native String bflItemGetURL(long paramLong);
/*     */ 
/*     */   private static native String bflItemGetTitle(long paramLong);
/*     */ 
/*     */   private static native WCImage bflItemGetIcon(long paramLong);
/*     */ 
/*     */   private static native long bflItemGetLastVisitedDate(long paramLong);
/*     */ 
/*     */   private static native boolean bflItemIsTargetItem(long paramLong);
/*     */ 
/*     */   private static native Entry[] bflItemGetChildren(long paramLong1, long paramLong2);
/*     */ 
/*     */   private static native String bflItemGetTarget(long paramLong);
/*     */ 
/*     */   private static native int bflSize(long paramLong);
/*     */ 
/*     */   private static native int bflGetMaximumSize(long paramLong);
/*     */ 
/*     */   private static native void bflSetMaximumSize(long paramLong, int paramInt);
/*     */ 
/*     */   private static native int bflGetCurrentIndex(long paramLong);
/*     */ 
/*     */   private static native int bflIndexOf(long paramLong1, long paramLong2, boolean paramBoolean);
/*     */ 
/*     */   private static native void bflSetEnabled(long paramLong, boolean paramBoolean);
/*     */ 
/*     */   private static native boolean bflIsEnabled(long paramLong);
/*     */ 
/*     */   private static native Object bflGet(long paramLong, int paramInt);
/*     */ 
/*     */   private static native int bflSetCurrentIndex(long paramLong, int paramInt);
/*     */ 
/*     */   private static native void bflSetHostObject(long paramLong, Object paramObject);
/*     */ 
/*     */   public static class Entry
/*     */   {
/*  25 */     private long pitem = 0L;
/*     */ 
/*  28 */     private long ppage = 0L;
/*     */     private Entry[] children;
/*     */     private URL url;
/*     */     private String title;
/*     */     private Date lastVisitedDate;
/*     */     private WCImage icon;
/*     */     private String target;
/*     */     private boolean isTargetItem;
/* 113 */     private final List<WCChangeListener> listenerList = new LinkedList();
/*     */ 
/*     */     private Entry(long paramLong1, long paramLong2)
/*     */     {
/*  40 */       this.pitem = paramLong1;
/*  41 */       this.ppage = paramLong2;
/*     */ 
/*  47 */       getURL();
/*  48 */       getTitle();
/*  49 */       getLastVisitedDate();
/*  50 */       getIcon();
/*  51 */       getTarget();
/*  52 */       isTargetItem();
/*  53 */       getChildren();
/*     */     }
/*     */ 
/*     */     private void notifyItemDestroyed()
/*     */     {
/*  58 */       this.pitem = 0L;
/*     */     }
/*     */ 
/*     */     private void notifyItemChanged()
/*     */     {
/*  63 */       for (WCChangeListener localWCChangeListener : this.listenerList)
/*  64 */         localWCChangeListener.stateChanged(new WCChangeEvent(this));
/*     */     }
/*     */ 
/*     */     public URL getURL()
/*     */     {
/*     */       try {
/*  70 */         return this.url = URLs.newURL(BackForwardList.bflItemGetURL(this.pitem)); } catch (MalformedURLException localMalformedURLException) {
/*     */       }
/*  72 */       return this.url = null;
/*     */     }
/*     */ 
/*     */     public String getTitle()
/*     */     {
/*  77 */       return this.title = BackForwardList.bflItemGetTitle(this.pitem);
/*     */     }
/*     */ 
/*     */     public WCImage getIcon() {
/*  81 */       return this.icon = BackForwardList.bflItemGetIcon(this.pitem);
/*     */     }
/*     */ 
/*     */     public String getTarget() {
/*  85 */       return this.target = BackForwardList.bflItemGetTarget(this.pitem);
/*     */     }
/*     */ 
/*     */     public Date getLastVisitedDate() {
/*  89 */       return this.lastVisitedDate == null ? null : (Date)this.lastVisitedDate.clone();
/*     */     }
/*     */ 
/*     */     void updateLastVisitedDate() {
/*  93 */       this.lastVisitedDate = new Date(System.currentTimeMillis());
/*  94 */       notifyItemChanged();
/*     */     }
/*     */ 
/*     */     public boolean isTargetItem() {
/*  98 */       return this.isTargetItem = BackForwardList.bflItemIsTargetItem(this.pitem);
/*     */     }
/*     */ 
/*     */     public Entry[] getChildren() {
/* 102 */       return this.children = BackForwardList.bflItemGetChildren(this.pitem, this.ppage);
/*     */     }
/*     */ 
/*     */     public String toString()
/*     */     {
/* 107 */       return "url=" + getURL() + ",title=" + getTitle() + ",date=" + getLastVisitedDate();
/*     */     }
/*     */ 
/*     */     public void addChangeListener(WCChangeListener paramWCChangeListener)
/*     */     {
/* 117 */       if (paramWCChangeListener == null)
/* 118 */         return;
/* 119 */       this.listenerList.add(paramWCChangeListener);
/*     */     }
/*     */ 
/*     */     public void removeChangeListener(WCChangeListener paramWCChangeListener) {
/* 123 */       if (paramWCChangeListener == null)
/* 124 */         return;
/* 125 */       this.listenerList.remove(paramWCChangeListener);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.platform.BackForwardList
 * JD-Core Version:    0.6.2
 */