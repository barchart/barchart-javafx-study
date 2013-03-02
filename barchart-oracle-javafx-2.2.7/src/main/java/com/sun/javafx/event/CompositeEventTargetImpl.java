/*    */ package com.sun.javafx.event;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.Collections;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import javafx.event.EventDispatchChain;
/*    */ import javafx.event.EventTarget;
/*    */ 
/*    */ public class CompositeEventTargetImpl
/*    */   implements CompositeEventTarget
/*    */ {
/*    */   private final Set<EventTarget> eventTargets;
/*    */ 
/*    */   public CompositeEventTargetImpl(EventTarget[] paramArrayOfEventTarget)
/*    */   {
/* 18 */     HashSet localHashSet = new HashSet(paramArrayOfEventTarget.length);
/*    */ 
/* 20 */     localHashSet.addAll(Arrays.asList(paramArrayOfEventTarget));
/*    */ 
/* 22 */     this.eventTargets = Collections.unmodifiableSet(localHashSet);
/*    */   }
/*    */ 
/*    */   public Set<EventTarget> getTargets()
/*    */   {
/* 27 */     return this.eventTargets;
/*    */   }
/*    */ 
/*    */   public boolean containsTarget(EventTarget paramEventTarget)
/*    */   {
/* 32 */     return this.eventTargets.contains(paramEventTarget);
/*    */   }
/*    */ 
/*    */   public EventDispatchChain buildEventDispatchChain(EventDispatchChain paramEventDispatchChain)
/*    */   {
/* 38 */     EventDispatchTree localEventDispatchTree1 = (EventDispatchTree)paramEventDispatchChain;
/*    */ 
/* 40 */     for (EventTarget localEventTarget : this.eventTargets) {
/* 41 */       EventDispatchTree localEventDispatchTree2 = localEventDispatchTree1.createTree();
/*    */ 
/* 43 */       localEventDispatchTree1 = localEventDispatchTree1.mergeTree((EventDispatchTree)localEventTarget.buildEventDispatchChain(localEventDispatchTree2));
/*    */     }
/*    */ 
/* 48 */     return localEventDispatchTree1;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.event.CompositeEventTargetImpl
 * JD-Core Version:    0.6.2
 */