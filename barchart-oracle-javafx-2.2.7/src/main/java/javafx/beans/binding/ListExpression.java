/*     */ package javafx.beans.binding;
/*     */ 
/*     */ import com.sun.javafx.binding.StringFormatter;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import javafx.beans.Observable;
/*     */ import javafx.beans.property.ReadOnlyBooleanProperty;
/*     */ import javafx.beans.property.ReadOnlyIntegerProperty;
/*     */ import javafx.beans.value.ObservableIntegerValue;
/*     */ import javafx.beans.value.ObservableListValue;
/*     */ import javafx.collections.FXCollections;
/*     */ import javafx.collections.ObservableList;
/*     */ 
/*     */ public abstract class ListExpression<E>
/*     */   implements ObservableListValue<E>
/*     */ {
/*  78 */   private static final ObservableList EMPTY_LIST = FXCollections.emptyObservableList();
/*     */ 
/*     */   public ObservableList<E> getValue()
/*     */   {
/*  82 */     return (ObservableList)get();
/*     */   }
/*     */ 
/*     */   public static <E> ListExpression<E> listExpression(ObservableListValue<E> paramObservableListValue)
/*     */   {
/* 101 */     if (paramObservableListValue == null) {
/* 102 */       throw new NullPointerException("List must be specified.");
/*     */     }
/* 104 */     return (paramObservableListValue instanceof ListExpression) ? (ListExpression)paramObservableListValue : new ListBinding()
/*     */     {
/*     */       public void dispose()
/*     */       {
/* 112 */         super.unbind(new Observable[] { this.val$value });
/*     */       }
/*     */ 
/*     */       protected ObservableList<E> computeValue()
/*     */       {
/* 117 */         return (ObservableList)this.val$value.get();
/*     */       }
/*     */ 
/*     */       public ObservableList<ObservableListValue<E>> getDependencies()
/*     */       {
/* 123 */         return FXCollections.singletonObservableList(this.val$value);
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public int getSize()
/*     */   {
/* 132 */     return size();
/*     */   }
/*     */ 
/*     */   public abstract ReadOnlyIntegerProperty sizeProperty();
/*     */ 
/*     */   public abstract ReadOnlyBooleanProperty emptyProperty();
/*     */ 
/*     */   public ObjectBinding<E> valueAt(int paramInt)
/*     */   {
/* 150 */     return Bindings.valueAt(this, paramInt);
/*     */   }
/*     */ 
/*     */   public ObjectBinding<E> valueAt(ObservableIntegerValue paramObservableIntegerValue)
/*     */   {
/* 162 */     return Bindings.valueAt(this, paramObservableIntegerValue);
/*     */   }
/*     */ 
/*     */   public BooleanBinding isEqualTo(ObservableList<?> paramObservableList)
/*     */   {
/* 176 */     return Bindings.equal(this, paramObservableList);
/*     */   }
/*     */ 
/*     */   public BooleanBinding isNotEqualTo(ObservableList<?> paramObservableList)
/*     */   {
/* 190 */     return Bindings.notEqual(this, paramObservableList);
/*     */   }
/*     */ 
/*     */   public BooleanBinding isNull()
/*     */   {
/* 199 */     return Bindings.isNull(this);
/*     */   }
/*     */ 
/*     */   public BooleanBinding isNotNull()
/*     */   {
/* 208 */     return Bindings.isNotNull(this);
/*     */   }
/*     */ 
/*     */   public StringBinding asString()
/*     */   {
/* 220 */     return (StringBinding)StringFormatter.convert(this);
/*     */   }
/*     */ 
/*     */   public int size()
/*     */   {
/* 225 */     ObservableList localObservableList = (ObservableList)get();
/* 226 */     return localObservableList == null ? EMPTY_LIST.size() : localObservableList.size();
/*     */   }
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/* 231 */     ObservableList localObservableList = (ObservableList)get();
/* 232 */     return localObservableList == null ? EMPTY_LIST.isEmpty() : localObservableList.isEmpty();
/*     */   }
/*     */ 
/*     */   public boolean contains(Object paramObject)
/*     */   {
/* 237 */     ObservableList localObservableList = (ObservableList)get();
/* 238 */     return localObservableList == null ? EMPTY_LIST.contains(paramObject) : localObservableList.contains(paramObject);
/*     */   }
/*     */ 
/*     */   public Iterator<E> iterator()
/*     */   {
/* 243 */     ObservableList localObservableList = (ObservableList)get();
/* 244 */     return localObservableList == null ? EMPTY_LIST.iterator() : localObservableList.iterator();
/*     */   }
/*     */ 
/*     */   public Object[] toArray()
/*     */   {
/* 249 */     ObservableList localObservableList = (ObservableList)get();
/* 250 */     return localObservableList == null ? EMPTY_LIST.toArray() : localObservableList.toArray();
/*     */   }
/*     */ 
/*     */   public <T> T[] toArray(T[] paramArrayOfT)
/*     */   {
/* 255 */     ObservableList localObservableList = (ObservableList)get();
/* 256 */     return localObservableList == null ? (Object[])EMPTY_LIST.toArray(paramArrayOfT) : localObservableList.toArray(paramArrayOfT);
/*     */   }
/*     */ 
/*     */   public boolean add(E paramE)
/*     */   {
/* 261 */     ObservableList localObservableList = (ObservableList)get();
/* 262 */     return localObservableList == null ? EMPTY_LIST.add(paramE) : localObservableList.add(paramE);
/*     */   }
/*     */ 
/*     */   public boolean remove(Object paramObject)
/*     */   {
/* 267 */     ObservableList localObservableList = (ObservableList)get();
/* 268 */     return localObservableList == null ? EMPTY_LIST.remove(paramObject) : localObservableList.remove(paramObject);
/*     */   }
/*     */ 
/*     */   public boolean containsAll(Collection<?> paramCollection)
/*     */   {
/* 273 */     ObservableList localObservableList = (ObservableList)get();
/* 274 */     return localObservableList == null ? EMPTY_LIST.contains(paramCollection) : localObservableList.contains(paramCollection);
/*     */   }
/*     */ 
/*     */   public boolean addAll(Collection<? extends E> paramCollection)
/*     */   {
/* 279 */     ObservableList localObservableList = (ObservableList)get();
/* 280 */     return localObservableList == null ? EMPTY_LIST.addAll(paramCollection) : localObservableList.addAll(paramCollection);
/*     */   }
/*     */ 
/*     */   public boolean addAll(int paramInt, Collection<? extends E> paramCollection)
/*     */   {
/* 285 */     ObservableList localObservableList = (ObservableList)get();
/* 286 */     return localObservableList == null ? EMPTY_LIST.addAll(paramInt, paramCollection) : localObservableList.addAll(paramInt, paramCollection);
/*     */   }
/*     */ 
/*     */   public boolean removeAll(Collection<?> paramCollection)
/*     */   {
/* 291 */     ObservableList localObservableList = (ObservableList)get();
/* 292 */     return localObservableList == null ? EMPTY_LIST.removeAll(paramCollection) : localObservableList.removeAll(paramCollection);
/*     */   }
/*     */ 
/*     */   public boolean retainAll(Collection<?> paramCollection)
/*     */   {
/* 297 */     ObservableList localObservableList = (ObservableList)get();
/* 298 */     return localObservableList == null ? EMPTY_LIST.retainAll(paramCollection) : localObservableList.retainAll(paramCollection);
/*     */   }
/*     */ 
/*     */   public void clear()
/*     */   {
/* 303 */     ObservableList localObservableList = (ObservableList)get();
/* 304 */     if (localObservableList == null)
/* 305 */       EMPTY_LIST.clear();
/*     */     else
/* 307 */       localObservableList.clear();
/*     */   }
/*     */ 
/*     */   public E get(int paramInt)
/*     */   {
/* 313 */     ObservableList localObservableList = (ObservableList)get();
/* 314 */     return localObservableList == null ? EMPTY_LIST.get(paramInt) : localObservableList.get(paramInt);
/*     */   }
/*     */ 
/*     */   public E set(int paramInt, E paramE)
/*     */   {
/* 319 */     ObservableList localObservableList = (ObservableList)get();
/* 320 */     return localObservableList == null ? EMPTY_LIST.set(paramInt, paramE) : localObservableList.set(paramInt, paramE);
/*     */   }
/*     */ 
/*     */   public void add(int paramInt, E paramE)
/*     */   {
/* 325 */     ObservableList localObservableList = (ObservableList)get();
/* 326 */     if (localObservableList == null)
/* 327 */       EMPTY_LIST.add(paramInt, paramE);
/*     */     else
/* 329 */       localObservableList.add(paramInt, paramE);
/*     */   }
/*     */ 
/*     */   public E remove(int paramInt)
/*     */   {
/* 335 */     ObservableList localObservableList = (ObservableList)get();
/* 336 */     return localObservableList == null ? EMPTY_LIST.remove(paramInt) : localObservableList.remove(paramInt);
/*     */   }
/*     */ 
/*     */   public int indexOf(Object paramObject)
/*     */   {
/* 341 */     ObservableList localObservableList = (ObservableList)get();
/* 342 */     return localObservableList == null ? EMPTY_LIST.indexOf(paramObject) : localObservableList.indexOf(paramObject);
/*     */   }
/*     */ 
/*     */   public int lastIndexOf(Object paramObject)
/*     */   {
/* 347 */     ObservableList localObservableList = (ObservableList)get();
/* 348 */     return localObservableList == null ? EMPTY_LIST.lastIndexOf(paramObject) : localObservableList.lastIndexOf(paramObject);
/*     */   }
/*     */ 
/*     */   public ListIterator<E> listIterator()
/*     */   {
/* 353 */     ObservableList localObservableList = (ObservableList)get();
/* 354 */     return localObservableList == null ? EMPTY_LIST.listIterator() : localObservableList.listIterator();
/*     */   }
/*     */ 
/*     */   public ListIterator<E> listIterator(int paramInt)
/*     */   {
/* 359 */     ObservableList localObservableList = (ObservableList)get();
/* 360 */     return localObservableList == null ? EMPTY_LIST.listIterator(paramInt) : localObservableList.listIterator(paramInt);
/*     */   }
/*     */ 
/*     */   public List<E> subList(int paramInt1, int paramInt2)
/*     */   {
/* 365 */     ObservableList localObservableList = (ObservableList)get();
/* 366 */     return localObservableList == null ? EMPTY_LIST.subList(paramInt1, paramInt2) : localObservableList.subList(paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   public boolean addAll(E[] paramArrayOfE)
/*     */   {
/* 371 */     ObservableList localObservableList = (ObservableList)get();
/* 372 */     return localObservableList == null ? EMPTY_LIST.addAll(paramArrayOfE) : localObservableList.addAll(paramArrayOfE);
/*     */   }
/*     */ 
/*     */   public boolean setAll(E[] paramArrayOfE)
/*     */   {
/* 377 */     ObservableList localObservableList = (ObservableList)get();
/* 378 */     return localObservableList == null ? EMPTY_LIST.setAll(paramArrayOfE) : localObservableList.setAll(paramArrayOfE);
/*     */   }
/*     */ 
/*     */   public boolean setAll(Collection<? extends E> paramCollection)
/*     */   {
/* 383 */     ObservableList localObservableList = (ObservableList)get();
/* 384 */     return localObservableList == null ? EMPTY_LIST.setAll(paramCollection) : localObservableList.setAll(paramCollection);
/*     */   }
/*     */ 
/*     */   public boolean removeAll(E[] paramArrayOfE)
/*     */   {
/* 389 */     ObservableList localObservableList = (ObservableList)get();
/* 390 */     return localObservableList == null ? EMPTY_LIST.removeAll(paramArrayOfE) : localObservableList.removeAll(paramArrayOfE);
/*     */   }
/*     */ 
/*     */   public boolean retainAll(E[] paramArrayOfE)
/*     */   {
/* 395 */     ObservableList localObservableList = (ObservableList)get();
/* 396 */     return localObservableList == null ? EMPTY_LIST.retainAll(paramArrayOfE) : localObservableList.retainAll(paramArrayOfE);
/*     */   }
/*     */ 
/*     */   public void remove(int paramInt1, int paramInt2)
/*     */   {
/* 401 */     ObservableList localObservableList = (ObservableList)get();
/* 402 */     if (localObservableList == null)
/* 403 */       EMPTY_LIST.remove(paramInt1, paramInt2);
/*     */     else
/* 405 */       localObservableList.remove(paramInt1, paramInt2);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.binding.ListExpression
 * JD-Core Version:    0.6.2
 */