/*     */ package javafx.animation;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.CopyOnWriteArraySet;
/*     */ import javafx.event.ActionEvent;
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.util.Duration;
/*     */ 
/*     */ public final class KeyFrame
/*     */ {
/*  56 */   private static final EventHandler<ActionEvent> DEFAULT_ON_FINISHED = null;
/*  57 */   private static final String DEFAULT_NAME = null;
/*     */   private final Duration time;
/*     */   private final Set<KeyValue> values;
/*     */   private final EventHandler<ActionEvent> onFinished;
/*     */   private final String name;
/*     */ 
/*     */   public Duration getTime()
/*     */   {
/*  77 */     return this.time;
/*     */   }
/*     */ 
/*     */   public Set<KeyValue> getValues()
/*     */   {
/*  88 */     return this.values;
/*     */   }
/*     */ 
/*     */   public EventHandler<ActionEvent> getOnFinished()
/*     */   {
/* 102 */     return this.onFinished;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 115 */     return this.name;
/*     */   }
/*     */ 
/*     */   public KeyFrame(Duration paramDuration, String paramString, EventHandler<ActionEvent> paramEventHandler, Collection<KeyValue> paramCollection)
/*     */   {
/* 141 */     if (paramDuration == null) {
/* 142 */       throw new NullPointerException("The time has to be specified");
/*     */     }
/* 144 */     if ((paramDuration.lessThan(Duration.ZERO)) || (paramDuration.equals(Duration.UNKNOWN))) {
/* 145 */       throw new IllegalArgumentException("The time is invalid.");
/*     */     }
/* 147 */     this.time = paramDuration;
/* 148 */     this.name = paramString;
/* 149 */     if (paramCollection != null) {
/* 150 */       CopyOnWriteArraySet localCopyOnWriteArraySet = new CopyOnWriteArraySet(paramCollection);
/* 151 */       localCopyOnWriteArraySet.remove(null);
/* 152 */       this.values = (localCopyOnWriteArraySet.size() == 1 ? Collections.singleton(localCopyOnWriteArraySet.iterator().next()) : localCopyOnWriteArraySet.size() == 0 ? Collections.emptySet() : Collections.unmodifiableSet(localCopyOnWriteArraySet));
/*     */     }
/*     */     else
/*     */     {
/* 157 */       this.values = Collections.emptySet();
/*     */     }
/* 159 */     this.onFinished = paramEventHandler;
/*     */   }
/*     */ 
/*     */   public KeyFrame(Duration paramDuration, String paramString, EventHandler<ActionEvent> paramEventHandler, KeyValue[] paramArrayOfKeyValue)
/*     */   {
/* 183 */     if (paramDuration == null) {
/* 184 */       throw new NullPointerException("The time has to be specified");
/*     */     }
/* 186 */     if ((paramDuration.lessThan(Duration.ZERO)) || (paramDuration.equals(Duration.UNKNOWN))) {
/* 187 */       throw new IllegalArgumentException("The time is invalid.");
/*     */     }
/* 189 */     this.time = paramDuration;
/* 190 */     this.name = paramString;
/* 191 */     if (paramArrayOfKeyValue != null) {
/* 192 */       CopyOnWriteArraySet localCopyOnWriteArraySet = new CopyOnWriteArraySet();
/* 193 */       for (KeyValue localKeyValue : paramArrayOfKeyValue) {
/* 194 */         if (localKeyValue != null) {
/* 195 */           localCopyOnWriteArraySet.add(localKeyValue);
/*     */         }
/*     */       }
/* 198 */       this.values = (localCopyOnWriteArraySet.size() == 1 ? Collections.singleton(localCopyOnWriteArraySet.iterator().next()) : localCopyOnWriteArraySet.size() == 0 ? Collections.emptySet() : Collections.unmodifiableSet(localCopyOnWriteArraySet));
/*     */     }
/*     */     else
/*     */     {
/* 203 */       this.values = Collections.emptySet();
/*     */     }
/* 205 */     this.onFinished = paramEventHandler;
/*     */   }
/*     */ 
/*     */   public KeyFrame(Duration paramDuration, EventHandler<ActionEvent> paramEventHandler, KeyValue[] paramArrayOfKeyValue)
/*     */   {
/* 224 */     this(paramDuration, DEFAULT_NAME, paramEventHandler, paramArrayOfKeyValue);
/*     */   }
/*     */ 
/*     */   public KeyFrame(Duration paramDuration, String paramString, KeyValue[] paramArrayOfKeyValue)
/*     */   {
/* 242 */     this(paramDuration, paramString, DEFAULT_ON_FINISHED, paramArrayOfKeyValue);
/*     */   }
/*     */ 
/*     */   public KeyFrame(Duration paramDuration, KeyValue[] paramArrayOfKeyValue)
/*     */   {
/* 258 */     this(paramDuration, DEFAULT_NAME, DEFAULT_ON_FINISHED, paramArrayOfKeyValue);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 267 */     return "KeyFrame [time=" + this.time + ", values=" + this.values + ", onFinished=" + this.onFinished + ", name=" + this.name + "]";
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 277 */     assert ((this.time != null) && (this.values != null));
/*     */ 
/* 279 */     int i = 1;
/* 280 */     i = 31 * i + this.time.hashCode();
/* 281 */     i = 31 * i + (this.name == null ? 0 : this.name.hashCode());
/* 282 */     i = 31 * i + (this.onFinished == null ? 0 : this.onFinished.hashCode());
/*     */ 
/* 284 */     i = 31 * i + this.values.hashCode();
/* 285 */     return i;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 296 */     if (this == paramObject) {
/* 297 */       return true;
/*     */     }
/* 299 */     if ((paramObject instanceof KeyFrame)) {
/* 300 */       KeyFrame localKeyFrame = (KeyFrame)paramObject;
/* 301 */       assert ((this.time != null) && (this.values != null) && (localKeyFrame.time != null) && (localKeyFrame.values != null));
/*     */ 
/* 303 */       return (this.time.equals(localKeyFrame.time)) && (this.name == null ? localKeyFrame.name == null : this.name.equals(localKeyFrame.name)) && (this.onFinished == null ? localKeyFrame.onFinished == null : this.onFinished.equals(localKeyFrame.onFinished)) && (this.values.equals(localKeyFrame.values));
/*     */     }
/*     */ 
/* 309 */     return false;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.animation.KeyFrame
 * JD-Core Version:    0.6.2
 */