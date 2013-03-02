/*     */ package javafx.scene.control;
/*     */ 
/*     */ import com.sun.javafx.css.StyleManager;
/*     */ import com.sun.javafx.css.StyleableProperty;
/*     */ import javafx.beans.property.BooleanProperty;
/*     */ import javafx.beans.property.BooleanPropertyBase;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.event.ActionEvent;
/*     */ import javafx.scene.Cursor;
/*     */ import javafx.scene.Node;
/*     */ 
/*     */ public class Hyperlink extends ButtonBase
/*     */ {
/*     */   private BooleanProperty visited;
/*     */   private static final String DEFAULT_STYLE_CLASS = "hyperlink";
/*     */   private static final String PSEUDO_CLASS_VISITED = "visited";
/* 157 */   private static final long VISITED_PSEUDOCLASS_STATE = StyleManager.getInstance().getPseudoclassMask("visited");
/*     */ 
/*     */   public Hyperlink()
/*     */   {
/*  59 */     initialize();
/*     */   }
/*     */ 
/*     */   public Hyperlink(String paramString)
/*     */   {
/*  68 */     super(paramString);
/*  69 */     initialize();
/*     */   }
/*     */ 
/*     */   public Hyperlink(String paramString, Node paramNode)
/*     */   {
/*  79 */     super(paramString, paramNode);
/*  80 */     initialize();
/*     */   }
/*     */ 
/*     */   private void initialize()
/*     */   {
/*  85 */     getStyleClass().setAll(new String[] { "hyperlink" });
/*     */ 
/*  90 */     StyleableProperty localStyleableProperty = StyleableProperty.getStyleableProperty(cursorProperty());
/*  91 */     localStyleableProperty.set(this, Cursor.HAND);
/*     */   }
/*     */ 
/*     */   public final BooleanProperty visitedProperty()
/*     */   {
/* 103 */     if (this.visited == null) {
/* 104 */       this.visited = new BooleanPropertyBase() {
/*     */         protected void invalidated() {
/* 106 */           Hyperlink.this.impl_pseudoClassStateChanged("visited");
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 111 */           return Hyperlink.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 116 */           return "visited";
/*     */         }
/*     */       };
/*     */     }
/* 120 */     return this.visited;
/*     */   }
/*     */ 
/*     */   public final void setVisited(boolean paramBoolean) {
/* 124 */     visitedProperty().set(paramBoolean);
/*     */   }
/*     */   public final boolean isVisited() {
/* 127 */     return this.visited == null ? false : this.visited.get();
/*     */   }
/*     */ 
/*     */   public void fire()
/*     */   {
/* 142 */     if ((this.visited == null) || (!this.visited.isBound())) {
/* 143 */       setVisited(true);
/*     */     }
/* 145 */     fireEvent(new ActionEvent());
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public long impl_getPseudoClassState()
/*     */   {
/* 166 */     long l = super.impl_getPseudoClassState();
/* 167 */     if (isVisited()) l |= VISITED_PSEUDOCLASS_STATE;
/* 168 */     return l;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   protected Cursor impl_cssGetCursorInitialValue()
/*     */   {
/* 178 */     return Cursor.HAND;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.Hyperlink
 * JD-Core Version:    0.6.2
 */