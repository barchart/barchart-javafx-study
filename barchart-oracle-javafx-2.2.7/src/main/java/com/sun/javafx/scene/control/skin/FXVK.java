/*     */ package com.sun.javafx.scene.control.skin;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.ObjectPropertyBase;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.collections.ObservableMap;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.control.Control;
/*     */ 
/*     */ public class FXVK extends Control
/*     */ {
/*  54 */   public static final String[] VK_TYPE_NAMES = { "text", "numeric", "url", "email" };
/*     */   public static final String VK_TYPE_PROP_KEY = "vkType";
/*     */   String[] chars;
/*     */   private ObjectProperty<Node> attachedNode;
/*     */   int vkType;
/*     */   static FXVK vk;
/*  85 */   private static HashMap<Integer, FXVK> vkMap = new HashMap();
/*     */   private static final String DEFAULT_STYLE_CLASS = "fxvk";
/*     */ 
/*     */   FXVK()
/*     */   {
/*  60 */     getStyleClass().setAll(new String[] { "fxvk" });
/*     */   }
/*     */ 
/*     */   final ObjectProperty<Node> attachedNodeProperty()
/*     */   {
/*  65 */     if (this.attachedNode == null) {
/*  66 */       this.attachedNode = new ObjectPropertyBase() {
/*     */         public Object getBean() {
/*  68 */           return FXVK.this;
/*     */         }
/*     */ 
/*     */         public String getName() {
/*  72 */           return "attachedNode";
/*     */         }
/*     */       };
/*     */     }
/*  76 */     return this.attachedNode;
/*     */   }
/*     */   final void setAttachedNode(Node paramNode) {
/*  79 */     attachedNodeProperty().setValue(paramNode); } 
/*  80 */   final Node getAttachedNode() { return this.attachedNode == null ? null : (Node)this.attachedNode.getValue(); }
/*     */ 
/*     */ 
/*     */   public static void attach(Node paramNode)
/*     */   {
/*  88 */     int i = 0;
/*  89 */     Object localObject1 = paramNode.getProperties().get("vkType");
/*  90 */     if ((localObject1 instanceof String)) {
/*  91 */       localObject2 = ((String)localObject1).toLowerCase();
/*  92 */       for (int j = 0; j < VK_TYPE_NAMES.length; j++) {
/*  93 */         if (((String)localObject2).equals(VK_TYPE_NAMES[j])) {
/*  94 */           i = j;
/*  95 */           break;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 100 */     vk = (FXVK)vkMap.get(Integer.valueOf(i));
/* 101 */     if (vk == null) {
/* 102 */       vk = new FXVK();
/* 103 */       vk.vkType = i;
/* 104 */       vk.setSkin(new FXVKSkin(vk));
/* 105 */       vkMap.put(Integer.valueOf(i), vk);
/*     */     }
/*     */ 
/* 108 */     for (Object localObject2 = vkMap.values().iterator(); ((Iterator)localObject2).hasNext(); ) { FXVK localFXVK = (FXVK)((Iterator)localObject2).next();
/* 109 */       if (localFXVK != vk) {
/* 110 */         localFXVK.setAttachedNode(null);
/*     */       }
/*     */     }
/* 113 */     vk.setAttachedNode(paramNode);
/*     */   }
/*     */ 
/*     */   public static void detach() {
/* 117 */     for (FXVK localFXVK : vkMap.values())
/* 118 */       localFXVK.setAttachedNode(null);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.skin.FXVK
 * JD-Core Version:    0.6.2
 */