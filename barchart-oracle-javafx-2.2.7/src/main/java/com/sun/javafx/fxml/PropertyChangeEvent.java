/*    */ package com.sun.javafx.fxml;
/*    */ 
/*    */ import javafx.event.Event;
/*    */ import javafx.event.EventType;
/*    */ 
/*    */ public class PropertyChangeEvent<V> extends Event
/*    */ {
/*    */   private static final long serialVersionUID = 0L;
/*    */   private V previousValue;
/* 20 */   public static final EventType<PropertyChangeEvent<?>> PROPERTY_CHANGE = new EventType();
/*    */ 
/*    */   public PropertyChangeEvent(Object paramObject, V paramV)
/*    */   {
/* 24 */     super(paramObject, null, PROPERTY_CHANGE);
/*    */ 
/* 26 */     this.previousValue = paramV;
/*    */   }
/*    */ 
/*    */   public V getPreviousValue()
/*    */   {
/* 33 */     return this.previousValue;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.fxml.PropertyChangeEvent
 * JD-Core Version:    0.6.2
 */