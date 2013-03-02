/*     */ package javafx.scene.web;
/*     */ 
/*     */ import com.sun.webpane.platform.BackForwardList;
/*     */ import com.sun.webpane.platform.BackForwardList.Entry;
/*     */ import com.sun.webpane.platform.WebPage;
/*     */ import com.sun.webpane.platform.event.WCChangeEvent;
/*     */ import com.sun.webpane.platform.event.WCChangeListener;
/*     */ import java.net.URL;
/*     */ import java.util.Date;
/*     */ import javafx.beans.property.IntegerProperty;
/*     */ import javafx.beans.property.ReadOnlyIntegerProperty;
/*     */ import javafx.beans.property.ReadOnlyIntegerWrapper;
/*     */ import javafx.beans.property.ReadOnlyObjectProperty;
/*     */ import javafx.beans.property.ReadOnlyObjectWrapper;
/*     */ import javafx.beans.property.SimpleIntegerProperty;
/*     */ import javafx.collections.FXCollections;
/*     */ import javafx.collections.ObservableList;
/*     */ 
/*     */ public final class WebHistory
/*     */ {
/*     */   private BackForwardList bfl;
/*     */   private ObservableList<Entry> list;
/*     */   private ObservableList<Entry> ulist;
/*     */   private ReadOnlyIntegerWrapper currentIndex;
/*     */   private IntegerProperty maxSize;
/*     */ 
/*     */   WebHistory(WebPage paramWebPage)
/*     */   {
/* 135 */     this.list = FXCollections.observableArrayList();
/* 136 */     this.bfl = paramWebPage.createBackForwardList();
/*     */ 
/* 138 */     setMaxSize(getMaxSize());
/*     */ 
/* 140 */     this.bfl.addChangeListener(new WCChangeListener()
/*     */     {
/*     */       public void stateChanged(WCChangeEvent paramAnonymousWCChangeEvent)
/*     */       {
/* 146 */         if (WebHistory.this.bfl.size() > WebHistory.this.list.size()) {
/* 147 */           assert (WebHistory.this.bfl.size() == WebHistory.this.list.size() + 1);
/* 148 */           WebHistory.this.list.add(new WebHistory.Entry(WebHistory.this, WebHistory.this.bfl.getCurrentEntry()));
/*     */ 
/* 150 */           WebHistory.this.setCurrentIndex(WebHistory.this.list.size() - 1);
/* 151 */           return;
/*     */         }
/*     */ 
/* 155 */         if (WebHistory.this.bfl.size() == WebHistory.this.list.size()) {
/* 156 */           if (WebHistory.this.list.size() == 0) {
/* 157 */             return;
/*     */           }
/* 159 */           assert (WebHistory.this.list.size() > 0);
/* 160 */           BackForwardList.Entry localEntry1 = WebHistory.this.bfl.get(WebHistory.this.list.size() - 1);
/* 161 */           BackForwardList.Entry localEntry2 = WebHistory.this.bfl.get(0);
/*     */ 
/* 164 */           if (((WebHistory.Entry)WebHistory.this.list.get(WebHistory.this.list.size() - 1)).isPeer(localEntry1)) {
/* 165 */             WebHistory.this.setCurrentIndex(WebHistory.this.bfl.getCurrentIndex());
/* 166 */             return;
/*     */           }
/*     */ 
/* 171 */           if (!((WebHistory.Entry)WebHistory.this.list.get(0)).isPeer(localEntry2)) {
/* 172 */             WebHistory.this.list.remove(0);
/* 173 */             WebHistory.this.list.add(new WebHistory.Entry(WebHistory.this, localEntry1));
/* 174 */             WebHistory.this.setCurrentIndex(WebHistory.this.bfl.getCurrentIndex());
/* 175 */             return;
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 183 */         assert (WebHistory.this.bfl.size() <= WebHistory.this.list.size());
/* 184 */         WebHistory.this.list.remove(WebHistory.this.bfl.size(), WebHistory.this.list.size());
/* 185 */         int i = WebHistory.this.list.size() - 1;
/* 186 */         if ((i >= 0) && (!((WebHistory.Entry)WebHistory.this.list.get(i)).isPeer(WebHistory.this.bfl.get(i)))) {
/* 187 */           WebHistory.this.list.remove(i);
/* 188 */           WebHistory.this.list.add(new WebHistory.Entry(WebHistory.this, WebHistory.this.bfl.get(i)));
/*     */         }
/* 190 */         WebHistory.this.setCurrentIndex(WebHistory.this.bfl.getCurrentIndex());
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public ReadOnlyIntegerProperty currentIndexProperty()
/*     */   {
/* 203 */     if (this.currentIndex == null) {
/* 204 */       this.currentIndex = new ReadOnlyIntegerWrapper(this, "currentIndex");
/*     */     }
/* 206 */     return this.currentIndex.getReadOnlyProperty();
/*     */   }
/*     */ 
/*     */   public int getCurrentIndex() {
/* 210 */     return currentIndexProperty().get();
/*     */   }
/*     */ 
/*     */   private void setCurrentIndex(int paramInt) {
/* 214 */     currentIndexProperty();
/* 215 */     this.currentIndex.set(paramInt);
/*     */   }
/*     */ 
/*     */   public IntegerProperty maxSizeProperty()
/*     */   {
/* 231 */     if (this.maxSize == null) {
/* 232 */       this.maxSize = new SimpleIntegerProperty(this, "maxSize", 100)
/*     */       {
/*     */         public void set(int paramAnonymousInt) {
/* 235 */           if (paramAnonymousInt < 0) {
/* 236 */             throw new IllegalArgumentException("value cannot be negative.");
/*     */           }
/* 238 */           super.set(paramAnonymousInt);
/*     */         }
/*     */       };
/*     */     }
/* 242 */     return this.maxSize;
/*     */   }
/*     */ 
/*     */   public void setMaxSize(int paramInt) {
/* 246 */     maxSizeProperty().set(paramInt);
/* 247 */     this.bfl.setMaximumSize(paramInt);
/*     */   }
/*     */ 
/*     */   public int getMaxSize() {
/* 251 */     return maxSizeProperty().get();
/*     */   }
/*     */ 
/*     */   public ObservableList<Entry> getEntries()
/*     */   {
/* 260 */     if (this.ulist == null) {
/* 261 */       this.ulist = FXCollections.unmodifiableObservableList(this.list);
/*     */     }
/* 263 */     return this.ulist;
/*     */   }
/*     */ 
/*     */   public void go(int paramInt)
/*     */     throws IndexOutOfBoundsException
/*     */   {
/* 287 */     if (paramInt == 0) {
/* 288 */       return;
/*     */     }
/* 290 */     int i = getCurrentIndex() + paramInt;
/* 291 */     if ((i < 0) || (i >= this.list.size())) {
/* 292 */       throw new IndexOutOfBoundsException("the effective index " + i + " is out of the range [0.." + (this.list.size() - 1) + "]");
/*     */     }
/*     */ 
/* 296 */     this.bfl.setCurrentIndex(i);
/*     */   }
/*     */ 
/*     */   public final class Entry
/*     */   {
/*     */     private URL url;
/*  56 */     private ReadOnlyObjectWrapper<String> title = new ReadOnlyObjectWrapper(this, "title");
/*  57 */     private ReadOnlyObjectWrapper<Date> lastVisitedDate = new ReadOnlyObjectWrapper(this, "lastVisitedDate");
/*     */     private BackForwardList.Entry peer;
/*     */ 
/*     */     Entry(BackForwardList.Entry arg2)
/*     */     {
/*     */       final BackForwardList.Entry localEntry;
/*  61 */       this.url = localEntry.getURL();
/*  62 */       this.title.set(localEntry.getTitle());
/*  63 */       this.lastVisitedDate.set(localEntry.getLastVisitedDate());
/*  64 */       this.peer = localEntry;
/*     */ 
/*  66 */       localEntry.addChangeListener(new WCChangeListener()
/*     */       {
/*     */         public void stateChanged(WCChangeEvent paramAnonymousWCChangeEvent) {
/*  69 */           String str = localEntry.getTitle();
/*     */ 
/*  71 */           if ((str == null) || (!str.equals(WebHistory.Entry.this.getTitle()))) {
/*  72 */             WebHistory.Entry.this.title.set(str);
/*     */           }
/*     */ 
/*  75 */           Date localDate = localEntry.getLastVisitedDate();
/*     */ 
/*  77 */           if ((localDate != null) && (!localDate.equals(WebHistory.Entry.this.getLastVisitedDate())))
/*  78 */             WebHistory.Entry.this.lastVisitedDate.set(localDate);
/*     */         }
/*     */       });
/*     */     }
/*     */ 
/*     */     public String getUrl()
/*     */     {
/*  90 */       assert (this.url != null);
/*  91 */       return this.url.toString();
/*     */     }
/*     */ 
/*     */     public ReadOnlyObjectProperty<String> titleProperty()
/*     */     {
/*  98 */       return this.title.getReadOnlyProperty();
/*     */     }
/*     */ 
/*     */     public String getTitle() {
/* 102 */       return (String)this.title.get();
/*     */     }
/*     */ 
/*     */     public ReadOnlyObjectProperty<Date> lastVisitedDateProperty()
/*     */     {
/* 109 */       return this.lastVisitedDate.getReadOnlyProperty();
/*     */     }
/*     */ 
/*     */     public Date getLastVisitedDate() {
/* 113 */       return (Date)this.lastVisitedDate.get();
/*     */     }
/*     */ 
/*     */     boolean isPeer(BackForwardList.Entry paramEntry) {
/* 117 */       return this.peer == paramEntry;
/*     */     }
/*     */ 
/*     */     public String toString()
/*     */     {
/* 122 */       return "[url: " + getUrl() + ", title: " + getTitle() + ", date: " + getLastVisitedDate() + "]";
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.web.WebHistory
 * JD-Core Version:    0.6.2
 */