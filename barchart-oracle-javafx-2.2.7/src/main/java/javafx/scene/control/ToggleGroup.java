/*     */ package javafx.scene.control;
/*     */ 
/*     */ import com.sun.javafx.collections.VetoableObservableList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javafx.beans.property.BooleanProperty;
/*     */ import javafx.beans.property.ReadOnlyObjectProperty;
/*     */ import javafx.beans.property.ReadOnlyObjectWrapper;
/*     */ import javafx.collections.ListChangeListener.Change;
/*     */ import javafx.collections.ObservableList;
/*     */ 
/*     */ public class ToggleGroup
/*     */ {
/*  55 */   private final ObservableList<Toggle> toggles = new VetoableObservableList() {
/*     */     protected void onProposedChange(List<Toggle> paramAnonymousList, int[] paramAnonymousArrayOfInt) {
/*  57 */       for (Toggle localToggle : paramAnonymousList) {
/*  58 */         if ((paramAnonymousArrayOfInt[0] == 0) && (paramAnonymousArrayOfInt[1] == size()))
/*     */         {
/*     */           break;
/*     */         }
/*  62 */         if (ToggleGroup.this.toggles.contains(localToggle))
/*  63 */           throw new IllegalArgumentException("Duplicate toggles are not allow in a ToggleGroup.");
/*     */       }
/*     */     }
/*     */ 
/*     */     protected void onChanged(ListChangeListener.Change<Toggle> paramAnonymousChange)
/*     */     {
/*     */       Iterator localIterator;
/*     */       Toggle localToggle;
/*  69 */       while (paramAnonymousChange.next())
/*     */       {
/*  73 */         for (localIterator = paramAnonymousChange.getRemoved().iterator(); localIterator.hasNext(); ) { localToggle = (Toggle)localIterator.next();
/*  74 */           if (localToggle.isSelected()) {
/*  75 */             ToggleGroup.this.selectToggle(null);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*  82 */         for (localIterator = paramAnonymousChange.getAddedSubList().iterator(); localIterator.hasNext(); ) { localToggle = (Toggle)localIterator.next();
/*  83 */           if (!ToggleGroup.this.equals(localToggle.getToggleGroup())) {
/*  84 */             if (localToggle.getToggleGroup() != null) {
/*  85 */               localToggle.getToggleGroup().getToggles().remove(localToggle);
/*     */             }
/*  87 */             localToggle.setToggleGroup(ToggleGroup.this);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*  94 */         for (localIterator = paramAnonymousChange.getAddedSubList().iterator(); localIterator.hasNext(); ) { localToggle = (Toggle)localIterator.next();
/*  95 */           if (localToggle.isSelected()) {
/*  96 */             ToggleGroup.this.selectToggle(localToggle);
/*  97 */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*  55 */   };
/*     */ 
/* 104 */   private final ReadOnlyObjectWrapper<Toggle> selectedToggle = new ReadOnlyObjectWrapper()
/*     */   {
/*     */     public void set(Toggle paramAnonymousToggle)
/*     */     {
/* 109 */       if (isBound()) {
/* 110 */         throw new RuntimeException("A bound value cannot be set.");
/*     */       }
/* 112 */       Toggle localToggle = (Toggle)get();
/* 113 */       if ((ToggleGroup.this.setSelected(paramAnonymousToggle, true)) || ((paramAnonymousToggle != null) && (paramAnonymousToggle.getToggleGroup() == ToggleGroup.this)) || (paramAnonymousToggle == null))
/*     */       {
/* 116 */         if ((localToggle == null) || (localToggle.getToggleGroup() == ToggleGroup.this) || (!localToggle.isSelected())) {
/* 117 */           ToggleGroup.this.setSelected(localToggle, false);
/*     */         }
/* 119 */         super.set(paramAnonymousToggle);
/*     */       }
/*     */     }
/* 104 */   };
/*     */ 
/*     */   public final ObservableList<Toggle> getToggles()
/*     */   {
/*  52 */     return this.toggles;
/*     */   }
/*     */ 
/*     */   public final void selectToggle(Toggle paramToggle)
/*     */   {
/* 131 */     this.selectedToggle.set(paramToggle);
/*     */   }
/*     */ 
/*     */   public final Toggle getSelectedToggle()
/*     */   {
/* 137 */     return (Toggle)this.selectedToggle.get();
/*     */   }
/*     */ 
/*     */   public final ReadOnlyObjectProperty<Toggle> selectedToggleProperty()
/*     */   {
/* 142 */     return this.selectedToggle.getReadOnlyProperty();
/*     */   }
/*     */   private boolean setSelected(Toggle paramToggle, boolean paramBoolean) {
/* 145 */     if ((paramToggle != null) && (paramToggle.getToggleGroup() == this) && (!paramToggle.selectedProperty().isBound()))
/*     */     {
/* 148 */       paramToggle.setSelected(paramBoolean);
/* 149 */       return true;
/*     */     }
/* 151 */     return false;
/*     */   }
/*     */ 
/*     */   final void clearSelectedToggle()
/*     */   {
/* 156 */     if (!((Toggle)this.selectedToggle.getValue()).isSelected()) {
/* 157 */       for (Toggle localToggle : getToggles()) {
/* 158 */         if (localToggle.isSelected()) {
/* 159 */           return;
/*     */         }
/*     */       }
/*     */     }
/* 163 */     this.selectedToggle.set(null);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.ToggleGroup
 * JD-Core Version:    0.6.2
 */