/*     */ package javafx.scene.control;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.event.ActionEvent;
/*     */ import javafx.event.Event;
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.input.KeyCombination;
/*     */ import javafx.util.Builder;
/*     */ 
/*     */ public class MenuItemBuilder<B extends MenuItemBuilder<B>>
/*     */   implements Builder<MenuItem>
/*     */ {
/*     */   private int __set;
/*     */   private KeyCombination accelerator;
/*     */   private boolean disable;
/*     */   private Node graphic;
/*     */   private String id;
/*     */   private boolean mnemonicParsing;
/*     */   private EventHandler<ActionEvent> onAction;
/*     */   private EventHandler<Event> onMenuValidation;
/*     */   private String style;
/*     */   private Collection<? extends String> styleClass;
/*     */   private String text;
/*     */   private Object userData;
/*     */   private boolean visible;
/*     */ 
/*     */   public static MenuItemBuilder<?> create()
/*     */   {
/*  15 */     return new MenuItemBuilder();
/*     */   }
/*     */ 
/*     */   private void __set(int paramInt)
/*     */   {
/*  20 */     this.__set |= 1 << paramInt;
/*     */   }
/*     */   public void applyTo(MenuItem paramMenuItem) {
/*  23 */     int i = this.__set;
/*  24 */     while (i != 0) {
/*  25 */       int j = Integer.numberOfTrailingZeros(i);
/*  26 */       i &= (1 << j ^ 0xFFFFFFFF);
/*  27 */       switch (j) { case 0:
/*  28 */         paramMenuItem.setAccelerator(this.accelerator); break;
/*     */       case 1:
/*  29 */         paramMenuItem.setDisable(this.disable); break;
/*     */       case 2:
/*  30 */         paramMenuItem.setGraphic(this.graphic); break;
/*     */       case 3:
/*  31 */         paramMenuItem.setId(this.id); break;
/*     */       case 4:
/*  32 */         paramMenuItem.setMnemonicParsing(this.mnemonicParsing); break;
/*     */       case 5:
/*  33 */         paramMenuItem.setOnAction(this.onAction); break;
/*     */       case 6:
/*  34 */         paramMenuItem.setOnMenuValidation(this.onMenuValidation); break;
/*     */       case 7:
/*  35 */         paramMenuItem.setStyle(this.style); break;
/*     */       case 8:
/*  36 */         paramMenuItem.getStyleClass().addAll(this.styleClass); break;
/*     */       case 9:
/*  37 */         paramMenuItem.setText(this.text); break;
/*     */       case 10:
/*  38 */         paramMenuItem.setUserData(this.userData); break;
/*     */       case 11:
/*  39 */         paramMenuItem.setVisible(this.visible);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public B accelerator(KeyCombination paramKeyCombination)
/*     */   {
/*  50 */     this.accelerator = paramKeyCombination;
/*  51 */     __set(0);
/*  52 */     return this;
/*     */   }
/*     */ 
/*     */   public B disable(boolean paramBoolean)
/*     */   {
/*  61 */     this.disable = paramBoolean;
/*  62 */     __set(1);
/*  63 */     return this;
/*     */   }
/*     */ 
/*     */   public B graphic(Node paramNode)
/*     */   {
/*  72 */     this.graphic = paramNode;
/*  73 */     __set(2);
/*  74 */     return this;
/*     */   }
/*     */ 
/*     */   public B id(String paramString)
/*     */   {
/*  83 */     this.id = paramString;
/*  84 */     __set(3);
/*  85 */     return this;
/*     */   }
/*     */ 
/*     */   public B mnemonicParsing(boolean paramBoolean)
/*     */   {
/*  94 */     this.mnemonicParsing = paramBoolean;
/*  95 */     __set(4);
/*  96 */     return this;
/*     */   }
/*     */ 
/*     */   public B onAction(EventHandler<ActionEvent> paramEventHandler)
/*     */   {
/* 105 */     this.onAction = paramEventHandler;
/* 106 */     __set(5);
/* 107 */     return this;
/*     */   }
/*     */ 
/*     */   public B onMenuValidation(EventHandler<Event> paramEventHandler)
/*     */   {
/* 116 */     this.onMenuValidation = paramEventHandler;
/* 117 */     __set(6);
/* 118 */     return this;
/*     */   }
/*     */ 
/*     */   public B style(String paramString)
/*     */   {
/* 127 */     this.style = paramString;
/* 128 */     __set(7);
/* 129 */     return this;
/*     */   }
/*     */ 
/*     */   public B styleClass(Collection<? extends String> paramCollection)
/*     */   {
/* 138 */     this.styleClass = paramCollection;
/* 139 */     __set(8);
/* 140 */     return this;
/*     */   }
/*     */ 
/*     */   public B styleClass(String[] paramArrayOfString)
/*     */   {
/* 147 */     return styleClass(Arrays.asList(paramArrayOfString));
/*     */   }
/*     */ 
/*     */   public B text(String paramString)
/*     */   {
/* 156 */     this.text = paramString;
/* 157 */     __set(9);
/* 158 */     return this;
/*     */   }
/*     */ 
/*     */   public B userData(Object paramObject)
/*     */   {
/* 167 */     this.userData = paramObject;
/* 168 */     __set(10);
/* 169 */     return this;
/*     */   }
/*     */ 
/*     */   public B visible(boolean paramBoolean)
/*     */   {
/* 178 */     this.visible = paramBoolean;
/* 179 */     __set(11);
/* 180 */     return this;
/*     */   }
/*     */ 
/*     */   public MenuItem build()
/*     */   {
/* 187 */     MenuItem localMenuItem = new MenuItem();
/* 188 */     applyTo(localMenuItem);
/* 189 */     return localMenuItem;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.MenuItemBuilder
 * JD-Core Version:    0.6.2
 */