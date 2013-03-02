/*     */ package javafx.scene.control;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.event.Event;
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.scene.Node;
/*     */ import javafx.util.Builder;
/*     */ 
/*     */ public class TabBuilder<B extends TabBuilder<B>>
/*     */   implements Builder<Tab>
/*     */ {
/*     */   private int __set;
/*     */   private boolean closable;
/*     */   private Node content;
/*     */   private ContextMenu contextMenu;
/*     */   private boolean disable;
/*     */   private Node graphic;
/*     */   private String id;
/*     */   private EventHandler<Event> onClosed;
/*     */   private EventHandler<Event> onSelectionChanged;
/*     */   private String style;
/*     */   private Collection<? extends String> styleClass;
/*     */   private String text;
/*     */   private Tooltip tooltip;
/*     */   private Object userData;
/*     */ 
/*     */   public static TabBuilder<?> create()
/*     */   {
/*  15 */     return new TabBuilder();
/*     */   }
/*     */ 
/*     */   private void __set(int paramInt)
/*     */   {
/*  20 */     this.__set |= 1 << paramInt;
/*     */   }
/*     */   public void applyTo(Tab paramTab) {
/*  23 */     int i = this.__set;
/*  24 */     while (i != 0) {
/*  25 */       int j = Integer.numberOfTrailingZeros(i);
/*  26 */       i &= (1 << j ^ 0xFFFFFFFF);
/*  27 */       switch (j) { case 0:
/*  28 */         paramTab.setClosable(this.closable); break;
/*     */       case 1:
/*  29 */         paramTab.setContent(this.content); break;
/*     */       case 2:
/*  30 */         paramTab.setContextMenu(this.contextMenu); break;
/*     */       case 3:
/*  31 */         paramTab.setDisable(this.disable); break;
/*     */       case 4:
/*  32 */         paramTab.setGraphic(this.graphic); break;
/*     */       case 5:
/*  33 */         paramTab.setId(this.id); break;
/*     */       case 6:
/*  34 */         paramTab.setOnClosed(this.onClosed); break;
/*     */       case 7:
/*  35 */         paramTab.setOnSelectionChanged(this.onSelectionChanged); break;
/*     */       case 8:
/*  36 */         paramTab.setStyle(this.style); break;
/*     */       case 9:
/*  37 */         paramTab.getStyleClass().addAll(this.styleClass); break;
/*     */       case 10:
/*  38 */         paramTab.setText(this.text); break;
/*     */       case 11:
/*  39 */         paramTab.setTooltip(this.tooltip); break;
/*     */       case 12:
/*  40 */         paramTab.setUserData(this.userData);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public B closable(boolean paramBoolean)
/*     */   {
/*  51 */     this.closable = paramBoolean;
/*  52 */     __set(0);
/*  53 */     return this;
/*     */   }
/*     */ 
/*     */   public B content(Node paramNode)
/*     */   {
/*  62 */     this.content = paramNode;
/*  63 */     __set(1);
/*  64 */     return this;
/*     */   }
/*     */ 
/*     */   public B contextMenu(ContextMenu paramContextMenu)
/*     */   {
/*  73 */     this.contextMenu = paramContextMenu;
/*  74 */     __set(2);
/*  75 */     return this;
/*     */   }
/*     */ 
/*     */   public B disable(boolean paramBoolean)
/*     */   {
/*  84 */     this.disable = paramBoolean;
/*  85 */     __set(3);
/*  86 */     return this;
/*     */   }
/*     */ 
/*     */   public B graphic(Node paramNode)
/*     */   {
/*  95 */     this.graphic = paramNode;
/*  96 */     __set(4);
/*  97 */     return this;
/*     */   }
/*     */ 
/*     */   public B id(String paramString)
/*     */   {
/* 106 */     this.id = paramString;
/* 107 */     __set(5);
/* 108 */     return this;
/*     */   }
/*     */ 
/*     */   public B onClosed(EventHandler<Event> paramEventHandler)
/*     */   {
/* 117 */     this.onClosed = paramEventHandler;
/* 118 */     __set(6);
/* 119 */     return this;
/*     */   }
/*     */ 
/*     */   public B onSelectionChanged(EventHandler<Event> paramEventHandler)
/*     */   {
/* 128 */     this.onSelectionChanged = paramEventHandler;
/* 129 */     __set(7);
/* 130 */     return this;
/*     */   }
/*     */ 
/*     */   public B style(String paramString)
/*     */   {
/* 139 */     this.style = paramString;
/* 140 */     __set(8);
/* 141 */     return this;
/*     */   }
/*     */ 
/*     */   public B styleClass(Collection<? extends String> paramCollection)
/*     */   {
/* 150 */     this.styleClass = paramCollection;
/* 151 */     __set(9);
/* 152 */     return this;
/*     */   }
/*     */ 
/*     */   public B styleClass(String[] paramArrayOfString)
/*     */   {
/* 159 */     return styleClass(Arrays.asList(paramArrayOfString));
/*     */   }
/*     */ 
/*     */   public B text(String paramString)
/*     */   {
/* 168 */     this.text = paramString;
/* 169 */     __set(10);
/* 170 */     return this;
/*     */   }
/*     */ 
/*     */   public B tooltip(Tooltip paramTooltip)
/*     */   {
/* 179 */     this.tooltip = paramTooltip;
/* 180 */     __set(11);
/* 181 */     return this;
/*     */   }
/*     */ 
/*     */   public B userData(Object paramObject)
/*     */   {
/* 190 */     this.userData = paramObject;
/* 191 */     __set(12);
/* 192 */     return this;
/*     */   }
/*     */ 
/*     */   public Tab build()
/*     */   {
/* 199 */     Tab localTab = new Tab();
/* 200 */     applyTo(localTab);
/* 201 */     return localTab;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.TabBuilder
 * JD-Core Version:    0.6.2
 */