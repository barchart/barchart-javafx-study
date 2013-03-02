/*    */ package com.sun.javafx.fxml;
/*    */ 
/*    */ import javafx.collections.ObservableMap;
/*    */ import javafx.event.Event;
/*    */ import javafx.event.EventType;
/*    */ 
/*    */ public class ObservableMapChangeEvent<K, V> extends Event
/*    */ {
/*    */   private static final long serialVersionUID = 0L;
/*    */   private EventType<ObservableMapChangeEvent<?, ?>> type;
/*    */   private K key;
/*    */   private V removed;
/* 23 */   public static final EventType<ObservableMapChangeEvent<?, ?>> ADD = new EventType();
/*    */ 
/* 25 */   public static final EventType<ObservableMapChangeEvent<?, ?>> UPDATE = new EventType();
/*    */ 
/* 27 */   public static final EventType<ObservableMapChangeEvent<?, ?>> REMOVE = new EventType();
/*    */ 
/*    */   public ObservableMapChangeEvent(ObservableMap<K, V> paramObservableMap, EventType<ObservableMapChangeEvent<?, ?>> paramEventType, K paramK, V paramV)
/*    */   {
/* 32 */     super(paramObservableMap, null, paramEventType);
/*    */ 
/* 34 */     this.type = paramEventType;
/* 35 */     this.key = paramK;
/* 36 */     this.removed = paramV;
/*    */   }
/*    */ 
/*    */   public K getKey()
/*    */   {
/* 43 */     return this.key;
/*    */   }
/*    */ 
/*    */   public V getRemoved()
/*    */   {
/* 51 */     return this.removed;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 56 */     return new StringBuilder().append(getClass().getName()).append(" ").append(this.type).append(": ").append(this.key).append(" ").append(this.removed == null ? "" : this.removed).toString();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.fxml.ObservableMapChangeEvent
 * JD-Core Version:    0.6.2
 */