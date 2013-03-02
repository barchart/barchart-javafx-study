/*    */ package com.sun.javafx.menu;
/*    */ 
/*    */ import javafx.beans.property.ObjectProperty;
/*    */ import javafx.beans.property.ReadOnlyBooleanProperty;
/*    */ import javafx.collections.ObservableList;
/*    */ import javafx.event.Event;
/*    */ import javafx.event.EventHandler;
/*    */ import javafx.event.EventType;
/*    */ 
/*    */ public abstract interface MenuBase extends MenuItemBase
/*    */ {
/* 42 */   public static final EventType<Event> ON_SHOWING = new EventType(Event.ANY, "ON_SHOWING");
/*    */ 
/* 49 */   public static final EventType<Event> ON_SHOWN = new EventType(Event.ANY, "ON_SHOWN");
/*    */ 
/* 56 */   public static final EventType<Event> ON_HIDING = new EventType(Event.ANY, "ON_HIDING");
/*    */ 
/* 63 */   public static final EventType<Event> ON_HIDDEN = new EventType(Event.ANY, "ON_HIDDEN");
/*    */ 
/*    */   public abstract boolean isShowing();
/*    */ 
/*    */   public abstract ReadOnlyBooleanProperty showingProperty();
/*    */ 
/*    */   public abstract ObjectProperty<EventHandler<Event>> onShowingProperty();
/*    */ 
/*    */   public abstract void setOnShowing(EventHandler<Event> paramEventHandler);
/*    */ 
/*    */   public abstract EventHandler<Event> getOnShowing();
/*    */ 
/*    */   public abstract ObjectProperty<EventHandler<Event>> onShownProperty();
/*    */ 
/*    */   public abstract void setOnShown(EventHandler<Event> paramEventHandler);
/*    */ 
/*    */   public abstract EventHandler<Event> getOnShown();
/*    */ 
/*    */   public abstract ObjectProperty<EventHandler<Event>> onHidingProperty();
/*    */ 
/*    */   public abstract void setOnHiding(EventHandler<Event> paramEventHandler);
/*    */ 
/*    */   public abstract EventHandler<Event> getOnHiding();
/*    */ 
/*    */   public abstract ObjectProperty<EventHandler<Event>> onHiddenProperty();
/*    */ 
/*    */   public abstract void setOnHidden(EventHandler<Event> paramEventHandler);
/*    */ 
/*    */   public abstract EventHandler<Event> getOnHidden();
/*    */ 
/*    */   public abstract ObservableList<MenuItemBase> getItemsBase();
/*    */ 
/*    */   public abstract void show();
/*    */ 
/*    */   public abstract void hide();
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.menu.MenuBase
 * JD-Core Version:    0.6.2
 */