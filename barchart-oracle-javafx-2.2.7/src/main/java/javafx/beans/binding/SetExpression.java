/*     */ package javafx.beans.binding;
/*     */ 
/*     */ import com.sun.javafx.binding.StringFormatter;
/*     */ import java.util.AbstractSet;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.Observable;
/*     */ import javafx.beans.property.ReadOnlyBooleanProperty;
/*     */ import javafx.beans.property.ReadOnlyIntegerProperty;
/*     */ import javafx.beans.value.ObservableSetValue;
/*     */ import javafx.collections.FXCollections;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.collections.ObservableSet;
/*     */ import javafx.collections.SetChangeListener;
/*     */ 
/*     */ public abstract class SetExpression<E>
/*     */   implements ObservableSetValue<E>
/*     */ {
/*  80 */   private static final ObservableSet EMPTY_SET = new EmptyObservableSet(null);
/*     */ 
/*     */   public ObservableSet<E> getValue()
/*     */   {
/* 135 */     return (ObservableSet)get();
/*     */   }
/*     */ 
/*     */   public static <E> SetExpression<E> setExpression(ObservableSetValue<E> paramObservableSetValue)
/*     */   {
/* 154 */     if (paramObservableSetValue == null) {
/* 155 */       throw new NullPointerException("Set must be specified.");
/*     */     }
/* 157 */     return (paramObservableSetValue instanceof SetExpression) ? (SetExpression)paramObservableSetValue : new SetBinding()
/*     */     {
/*     */       public void dispose()
/*     */       {
/* 165 */         super.unbind(new Observable[] { this.val$value });
/*     */       }
/*     */ 
/*     */       protected ObservableSet<E> computeValue()
/*     */       {
/* 170 */         return (ObservableSet)this.val$value.get();
/*     */       }
/*     */ 
/*     */       public ObservableList<?> getDependencies()
/*     */       {
/* 176 */         return FXCollections.singletonObservableList(this.val$value);
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public int getSize()
/*     */   {
/* 185 */     return size();
/*     */   }
/*     */ 
/*     */   public abstract ReadOnlyIntegerProperty sizeProperty();
/*     */ 
/*     */   public abstract ReadOnlyBooleanProperty emptyProperty();
/*     */ 
/*     */   public BooleanBinding isEqualTo(ObservableSet<?> paramObservableSet)
/*     */   {
/* 206 */     return Bindings.equal(this, paramObservableSet);
/*     */   }
/*     */ 
/*     */   public BooleanBinding isNotEqualTo(ObservableSet<?> paramObservableSet)
/*     */   {
/* 220 */     return Bindings.notEqual(this, paramObservableSet);
/*     */   }
/*     */ 
/*     */   public BooleanBinding isNull()
/*     */   {
/* 229 */     return Bindings.isNull(this);
/*     */   }
/*     */ 
/*     */   public BooleanBinding isNotNull()
/*     */   {
/* 238 */     return Bindings.isNotNull(this);
/*     */   }
/*     */ 
/*     */   public StringBinding asString()
/*     */   {
/* 250 */     return (StringBinding)StringFormatter.convert(this);
/*     */   }
/*     */ 
/*     */   public int size()
/*     */   {
/* 255 */     ObservableSet localObservableSet = (ObservableSet)get();
/* 256 */     return localObservableSet == null ? EMPTY_SET.size() : localObservableSet.size();
/*     */   }
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/* 261 */     ObservableSet localObservableSet = (ObservableSet)get();
/* 262 */     return localObservableSet == null ? EMPTY_SET.isEmpty() : localObservableSet.isEmpty();
/*     */   }
/*     */ 
/*     */   public boolean contains(Object paramObject)
/*     */   {
/* 267 */     ObservableSet localObservableSet = (ObservableSet)get();
/* 268 */     return localObservableSet == null ? EMPTY_SET.contains(paramObject) : localObservableSet.contains(paramObject);
/*     */   }
/*     */ 
/*     */   public Iterator<E> iterator()
/*     */   {
/* 273 */     ObservableSet localObservableSet = (ObservableSet)get();
/* 274 */     return localObservableSet == null ? EMPTY_SET.iterator() : localObservableSet.iterator();
/*     */   }
/*     */ 
/*     */   public Object[] toArray()
/*     */   {
/* 279 */     ObservableSet localObservableSet = (ObservableSet)get();
/* 280 */     return localObservableSet == null ? EMPTY_SET.toArray() : localObservableSet.toArray();
/*     */   }
/*     */ 
/*     */   public <T> T[] toArray(T[] paramArrayOfT)
/*     */   {
/* 285 */     ObservableSet localObservableSet = (ObservableSet)get();
/* 286 */     return localObservableSet == null ? (Object[])EMPTY_SET.toArray(paramArrayOfT) : localObservableSet.toArray(paramArrayOfT);
/*     */   }
/*     */ 
/*     */   public boolean add(E paramE)
/*     */   {
/* 291 */     ObservableSet localObservableSet = (ObservableSet)get();
/* 292 */     return localObservableSet == null ? EMPTY_SET.add(paramE) : localObservableSet.add(paramE);
/*     */   }
/*     */ 
/*     */   public boolean remove(Object paramObject)
/*     */   {
/* 297 */     ObservableSet localObservableSet = (ObservableSet)get();
/* 298 */     return localObservableSet == null ? EMPTY_SET.remove(paramObject) : localObservableSet.remove(paramObject);
/*     */   }
/*     */ 
/*     */   public boolean containsAll(Collection<?> paramCollection)
/*     */   {
/* 303 */     ObservableSet localObservableSet = (ObservableSet)get();
/* 304 */     return localObservableSet == null ? EMPTY_SET.contains(paramCollection) : localObservableSet.contains(paramCollection);
/*     */   }
/*     */ 
/*     */   public boolean addAll(Collection<? extends E> paramCollection)
/*     */   {
/* 309 */     ObservableSet localObservableSet = (ObservableSet)get();
/* 310 */     return localObservableSet == null ? EMPTY_SET.addAll(paramCollection) : localObservableSet.addAll(paramCollection);
/*     */   }
/*     */ 
/*     */   public boolean removeAll(Collection<?> paramCollection)
/*     */   {
/* 315 */     ObservableSet localObservableSet = (ObservableSet)get();
/* 316 */     return localObservableSet == null ? EMPTY_SET.removeAll(paramCollection) : localObservableSet.removeAll(paramCollection);
/*     */   }
/*     */ 
/*     */   public boolean retainAll(Collection<?> paramCollection)
/*     */   {
/* 321 */     ObservableSet localObservableSet = (ObservableSet)get();
/* 322 */     return localObservableSet == null ? EMPTY_SET.retainAll(paramCollection) : localObservableSet.retainAll(paramCollection);
/*     */   }
/*     */ 
/*     */   public void clear()
/*     */   {
/* 327 */     ObservableSet localObservableSet = (ObservableSet)get();
/* 328 */     if (localObservableSet == null)
/* 329 */       EMPTY_SET.clear();
/*     */     else
/* 331 */       localObservableSet.clear();
/*     */   }
/*     */ 
/*     */   private static class EmptyObservableSet<E> extends AbstractSet<E>
/*     */     implements ObservableSet<E>
/*     */   {
/*  84 */     private static final Iterator iterator = new Iterator()
/*     */     {
/*     */       public boolean hasNext() {
/*  87 */         return false;
/*     */       }
/*     */ 
/*     */       public Object next()
/*     */       {
/*  92 */         throw new NoSuchElementException();
/*     */       }
/*     */ 
/*     */       public void remove()
/*     */       {
/*  97 */         throw new UnsupportedOperationException();
/*     */       }
/*  84 */     };
/*     */ 
/*     */     public Iterator<E> iterator()
/*     */     {
/* 104 */       return iterator;
/*     */     }
/*     */ 
/*     */     public int size()
/*     */     {
/* 109 */       return 0;
/*     */     }
/*     */ 
/*     */     public void addListener(SetChangeListener<? super E> paramSetChangeListener)
/*     */     {
/*     */     }
/*     */ 
/*     */     public void removeListener(SetChangeListener<? super E> paramSetChangeListener)
/*     */     {
/*     */     }
/*     */ 
/*     */     public void addListener(InvalidationListener paramInvalidationListener)
/*     */     {
/*     */     }
/*     */ 
/*     */     public void removeListener(InvalidationListener paramInvalidationListener)
/*     */     {
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.binding.SetExpression
 * JD-Core Version:    0.6.2
 */