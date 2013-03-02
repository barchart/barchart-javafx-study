/*    */ package com.sun.javafx.fxml;
/*    */ 
/*    */ import java.util.List;
/*    */ import javafx.collections.ObservableList;
/*    */ import javafx.event.Event;
/*    */ import javafx.event.EventType;
/*    */ 
/*    */ public class ObservableListChangeEvent<E> extends Event
/*    */ {
/*    */   private static final long serialVersionUID = 0L;
/*    */   private EventType<ObservableListChangeEvent<?>> type;
/*    */   private int from;
/*    */   private int to;
/*    */   private List<E> removed;
/* 26 */   public static final EventType<ObservableListChangeEvent<?>> ADD = new EventType();
/*    */ 
/* 28 */   public static final EventType<ObservableListChangeEvent<?>> UPDATE = new EventType();
/*    */ 
/* 30 */   public static final EventType<ObservableListChangeEvent<?>> REMOVE = new EventType();
/*    */ 
/*    */   public ObservableListChangeEvent(ObservableList<E> paramObservableList, EventType<ObservableListChangeEvent<?>> paramEventType, int paramInt1, int paramInt2, List<E> paramList)
/*    */   {
/* 35 */     super(paramObservableList, null, paramEventType);
/*    */ 
/* 37 */     this.from = paramInt1;
/* 38 */     this.to = paramInt2;
/* 39 */     this.removed = paramList;
/*    */   }
/*    */ 
/*    */   public int getFrom()
/*    */   {
/* 46 */     return this.from;
/*    */   }
/*    */ 
/*    */   public int getTo()
/*    */   {
/* 54 */     return this.to;
/*    */   }
/*    */ 
/*    */   public List<E> getRemoved()
/*    */   {
/* 63 */     return this.removed;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 68 */     return new StringBuilder().append(getClass().getName()).append(" ").append(this.type).append(": [").append(this.from).append("..").append(this.to).append(") ").append(this.removed == null ? "" : this.removed).toString();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.fxml.ObservableListChangeEvent
 * JD-Core Version:    0.6.2
 */