/*     */ package javafx.scene.control;
/*     */ 
/*     */ import com.sun.javafx.collections.TrackableObservableList;
/*     */ import com.sun.javafx.css.StyleableProperty;
/*     */ import java.util.List;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.SimpleObjectProperty;
/*     */ import javafx.collections.ListChangeListener.Change;
/*     */ import javafx.collections.ObservableList;
/*     */ 
/*     */ public class Accordion extends Control
/*     */ {
/*  91 */   private final ObservableList<TitledPane> panes = new TrackableObservableList()
/*     */   {
/*     */     protected void onChanged(ListChangeListener.Change<TitledPane> paramAnonymousChange)
/*     */     {
/*  97 */       while (paramAnonymousChange.next())
/*  98 */         if ((paramAnonymousChange.wasRemoved()) && (!Accordion.this.expandedPane.isBound()))
/*  99 */           for (TitledPane localTitledPane : paramAnonymousChange.getRemoved())
/* 100 */             if ((!paramAnonymousChange.getAddedSubList().contains(localTitledPane)) && (Accordion.this.getExpandedPane() == localTitledPane)) {
/* 101 */               Accordion.this.setExpandedPane(null);
/* 102 */               break;
/*     */             }
/*     */     }
/*  91 */   };
/*     */ 
/* 117 */   private ObjectProperty<TitledPane> expandedPane = new SimpleObjectProperty(this, "expandedPane") {
/*     */     public void set(TitledPane paramAnonymousTitledPane) {
/* 119 */       if (isBound()) {
/* 120 */         throw new RuntimeException("A bound value cannot be set.");
/*     */       }
/* 122 */       if (paramAnonymousTitledPane != null) {
/* 123 */         paramAnonymousTitledPane.setExpanded(true);
/*     */       } else {
/* 125 */         TitledPane localTitledPane = (TitledPane)get();
/* 126 */         if (localTitledPane != null) {
/* 127 */           localTitledPane.setExpanded(false);
/*     */         }
/*     */       }
/* 130 */       super.set(paramAnonymousTitledPane);
/*     */     }
/* 117 */   };
/*     */   private static final String DEFAULT_STYLE_CLASS = "accordion";
/*     */ 
/*     */   public Accordion()
/*     */   {
/*  75 */     getStyleClass().setAll(new String[] { "accordion" });
/*     */ 
/*  80 */     StyleableProperty localStyleableProperty = StyleableProperty.getStyleableProperty(focusTraversableProperty());
/*  81 */     localStyleableProperty.set(this, Boolean.FALSE);
/*     */   }
/*     */ 
/*     */   public final void setExpandedPane(TitledPane paramTitledPane)
/*     */   {
/* 143 */     expandedPaneProperty().set(paramTitledPane);
/*     */   }
/*     */ 
/*     */   public final TitledPane getExpandedPane()
/*     */   {
/* 151 */     return (TitledPane)this.expandedPane.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<TitledPane> expandedPaneProperty()
/*     */   {
/* 158 */     return this.expandedPane;
/*     */   }
/*     */ 
/*     */   public final ObservableList<TitledPane> getPanes()
/*     */   {
/* 173 */     return this.panes;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   protected Boolean impl_cssGetFocusTraversableInitialValue()
/*     */   {
/* 192 */     return Boolean.FALSE;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.Accordion
 * JD-Core Version:    0.6.2
 */