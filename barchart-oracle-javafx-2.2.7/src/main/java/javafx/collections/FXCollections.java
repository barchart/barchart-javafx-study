/*      */ package javafx.collections;
/*      */ 
/*      */ import com.sun.javafx.collections.ObservableListWrapper;
/*      */ import com.sun.javafx.collections.ObservableMapWrapper;
/*      */ import com.sun.javafx.collections.ObservableSetWrapper;
/*      */ import com.sun.javafx.collections.SortableList;
/*      */ import com.sun.javafx.collections.UnmodifiableObservableMap;
/*      */ import java.lang.reflect.Array;
/*      */ import java.util.AbstractList;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.ListIterator;
/*      */ import java.util.Map;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Random;
/*      */ import java.util.Set;
/*      */ import javafx.beans.InvalidationListener;
/*      */ import javafx.beans.Observable;
/*      */ import javafx.util.Callback;
/*      */ 
/*      */ public class FXCollections
/*      */ {
/*  295 */   private static ObservableList EMPTY_OBSERVABLE_LIST = new EmptyObservableList();
/*      */   private static Random r;
/*      */ 
/*      */   public static <E> ObservableList<E> observableList(List<E> paramList)
/*      */   {
/*   83 */     if (paramList == null) {
/*   84 */       throw new NullPointerException();
/*      */     }
/*   86 */     return new ObservableListWrapper(paramList);
/*      */   }
/*      */ 
/*      */   public static <E> ObservableList<E> observableList(List<E> paramList, Callback<E, Observable[]> paramCallback)
/*      */   {
/*  107 */     if ((paramList == null) || (paramCallback == null)) {
/*  108 */       throw new NullPointerException();
/*      */     }
/*  110 */     return new ObservableListWrapper(paramList, paramCallback);
/*      */   }
/*      */ 
/*      */   public static <K, V> ObservableMap<K, V> observableMap(Map<K, V> paramMap)
/*      */   {
/*  123 */     if (paramMap == null) {
/*  124 */       throw new NullPointerException();
/*      */     }
/*  126 */     return new ObservableMapWrapper(paramMap);
/*      */   }
/*      */ 
/*      */   public static <E> ObservableSet<E> observableSet(Set<E> paramSet)
/*      */   {
/*  139 */     if (paramSet == null) {
/*  140 */       throw new NullPointerException();
/*      */     }
/*  142 */     return new ObservableSetWrapper(paramSet);
/*      */   }
/*      */ 
/*      */   public static <E> ObservableSet<E> observableSet(E[] paramArrayOfE)
/*      */   {
/*  151 */     if (paramArrayOfE == null) {
/*  152 */       throw new NullPointerException();
/*      */     }
/*  154 */     HashSet localHashSet = new HashSet(paramArrayOfE.length);
/*  155 */     Collections.addAll(localHashSet, paramArrayOfE);
/*  156 */     return new ObservableSetWrapper(localHashSet);
/*      */   }
/*      */ 
/*      */   public static <K, V> ObservableMap<K, V> unmodifiableObservableMap(ObservableMap<K, V> paramObservableMap)
/*      */   {
/*  169 */     if (paramObservableMap == null) {
/*  170 */       throw new NullPointerException();
/*      */     }
/*  172 */     return new UnmodifiableObservableMap(paramObservableMap);
/*      */   }
/*      */ 
/*      */   public static <E> ObservableList<E> observableArrayList()
/*      */   {
/*  182 */     return observableList(new ArrayList());
/*      */   }
/*      */ 
/*      */   public static <E> ObservableList<E> observableArrayList(Callback<E, Observable[]> paramCallback)
/*      */   {
/*  195 */     return observableList(new ArrayList(), paramCallback);
/*      */   }
/*      */ 
/*      */   public static <E> ObservableList<E> observableArrayList(E[] paramArrayOfE)
/*      */   {
/*  205 */     ObservableList localObservableList = observableArrayList();
/*  206 */     localObservableList.addAll(paramArrayOfE);
/*  207 */     return localObservableList;
/*      */   }
/*      */ 
/*      */   public static <E> ObservableList<E> observableArrayList(Collection<? extends E> paramCollection)
/*      */   {
/*  217 */     ObservableList localObservableList = observableArrayList();
/*  218 */     localObservableList.addAll(paramCollection);
/*  219 */     return localObservableList;
/*      */   }
/*      */ 
/*      */   public static <K, V> ObservableMap<K, V> observableHashMap()
/*      */   {
/*  229 */     return observableMap(new HashMap());
/*      */   }
/*      */ 
/*      */   public static <E> ObservableList<E> concat(ObservableList<E>[] paramArrayOfObservableList)
/*      */   {
/*  239 */     if (paramArrayOfObservableList.length == 0) {
/*  240 */       return observableArrayList();
/*      */     }
/*  242 */     if (paramArrayOfObservableList.length == 1) {
/*  243 */       return observableArrayList(paramArrayOfObservableList[0]);
/*      */     }
/*  245 */     ArrayList localArrayList = new ArrayList();
/*  246 */     for (ObservableList<E> localObservableList : paramArrayOfObservableList) {
/*  247 */       localArrayList.addAll(localObservableList);
/*      */     }
/*      */ 
/*  250 */     return observableList(localArrayList);
/*      */   }
/*      */ 
/*      */   public static <E> ObservableList<E> unmodifiableObservableList(ObservableList<E> paramObservableList)
/*      */   {
/*  262 */     if (paramObservableList == null) {
/*  263 */       throw new NullPointerException();
/*      */     }
/*  265 */     return new UnmodifiableObservableListImpl(paramObservableList);
/*      */   }
/*      */ 
/*      */   public static <E> ObservableList<E> checkedObservableList(ObservableList<E> paramObservableList, Class<E> paramClass)
/*      */   {
/*  276 */     if (paramObservableList == null) {
/*  277 */       throw new NullPointerException();
/*      */     }
/*  279 */     return new CheckedObservableList(paramObservableList, paramClass);
/*      */   }
/*      */ 
/*      */   public static <E> ObservableList<E> synchronizedObservableList(ObservableList<E> paramObservableList)
/*      */   {
/*  289 */     if (paramObservableList == null) {
/*  290 */       throw new NullPointerException();
/*      */     }
/*  292 */     return new SynchronizedObservableList(paramObservableList);
/*      */   }
/*      */ 
/*      */   public static <E> ObservableList<E> emptyObservableList()
/*      */   {
/*  306 */     return EMPTY_OBSERVABLE_LIST;
/*      */   }
/*      */ 
/*      */   public static <E> ObservableList<E> singletonObservableList(E paramE)
/*      */   {
/*  317 */     return new SingletonObservableList(paramE);
/*      */   }
/*      */ 
/*      */   public static <T> void copy(ObservableList<? super T> paramObservableList, List<? extends T> paramList)
/*      */   {
/*  328 */     int i = paramList.size();
/*  329 */     if (i > paramObservableList.size()) {
/*  330 */       throw new IndexOutOfBoundsException("Source does not fit in dest");
/*      */     }
/*  332 */     Object[] arrayOfObject = (Object[])paramObservableList.toArray();
/*  333 */     System.arraycopy(paramList.toArray(), 0, arrayOfObject, 0, i);
/*  334 */     paramObservableList.setAll(arrayOfObject);
/*      */   }
/*      */ 
/*      */   public static <T> void fill(ObservableList<? super T> paramObservableList, T paramT)
/*      */   {
/*  345 */     Object[] arrayOfObject = (Object[])new Object[paramObservableList.size()];
/*  346 */     Arrays.fill(arrayOfObject, paramT);
/*  347 */     paramObservableList.setAll(arrayOfObject);
/*      */   }
/*      */ 
/*      */   public static <T> boolean replaceAll(ObservableList<T> paramObservableList, T paramT1, T paramT2)
/*      */   {
/*  361 */     Object[] arrayOfObject = (Object[])paramObservableList.toArray();
/*  362 */     boolean bool = false;
/*  363 */     for (int i = 0; i < arrayOfObject.length; i++) {
/*  364 */       if (arrayOfObject[i].equals(paramT1)) {
/*  365 */         arrayOfObject[i] = paramT2;
/*  366 */         bool = true;
/*      */       }
/*      */     }
/*  369 */     if (bool) {
/*  370 */       paramObservableList.setAll(arrayOfObject);
/*      */     }
/*  372 */     return bool;
/*      */   }
/*      */ 
/*      */   public static void reverse(ObservableList paramObservableList)
/*      */   {
/*  383 */     Object[] arrayOfObject = paramObservableList.toArray();
/*  384 */     for (int i = 0; i < arrayOfObject.length / 2; i++) {
/*  385 */       Object localObject = arrayOfObject[i];
/*  386 */       arrayOfObject[i] = arrayOfObject[(arrayOfObject.length - i - 1)];
/*  387 */       arrayOfObject[(arrayOfObject.length - i - 1)] = localObject;
/*      */     }
/*  389 */     paramObservableList.setAll(arrayOfObject);
/*      */   }
/*      */ 
/*      */   public static void rotate(ObservableList paramObservableList, int paramInt)
/*      */   {
/*  401 */     Object[] arrayOfObject = paramObservableList.toArray();
/*      */ 
/*  403 */     int i = paramObservableList.size();
/*  404 */     paramInt %= i;
/*  405 */     if (paramInt < 0)
/*  406 */       paramInt += i;
/*  407 */     if (paramInt == 0) {
/*  408 */       return;
/*      */     }
/*  410 */     int j = 0; for (int k = 0; k != i; j++) {
/*  411 */       Object localObject1 = arrayOfObject[j];
/*      */ 
/*  413 */       int m = j;
/*      */       do {
/*  415 */         m += paramInt;
/*  416 */         if (m >= i)
/*  417 */           m -= i;
/*  418 */         Object localObject2 = arrayOfObject[m];
/*  419 */         arrayOfObject[m] = localObject1;
/*  420 */         localObject1 = localObject2;
/*  421 */         k++;
/*  422 */       }while (m != j);
/*      */     }
/*  424 */     paramObservableList.setAll(arrayOfObject);
/*      */   }
/*      */ 
/*      */   public static void shuffle(ObservableList<?> paramObservableList)
/*      */   {
/*  434 */     if (r == null) {
/*  435 */       r = new Random();
/*      */     }
/*  437 */     shuffle(paramObservableList, r);
/*      */   }
/*      */ 
/*      */   public static void shuffle(ObservableList paramObservableList, Random paramRandom)
/*      */   {
/*  450 */     Object[] arrayOfObject = paramObservableList.toArray();
/*      */ 
/*  452 */     for (int i = paramObservableList.size(); i > 1; i--) {
/*  453 */       swap(arrayOfObject, i - 1, paramRandom.nextInt(i));
/*      */     }
/*      */ 
/*  456 */     paramObservableList.setAll(arrayOfObject);
/*      */   }
/*      */ 
/*      */   private static void swap(Object[] paramArrayOfObject, int paramInt1, int paramInt2) {
/*  460 */     Object localObject = paramArrayOfObject[paramInt1];
/*  461 */     paramArrayOfObject[paramInt1] = paramArrayOfObject[paramInt2];
/*  462 */     paramArrayOfObject[paramInt2] = localObject;
/*      */   }
/*      */ 
/*      */   public static <T extends Comparable<? super T>> void sort(ObservableList<T> paramObservableList)
/*      */   {
/*  472 */     if ((paramObservableList instanceof SortableList)) {
/*  473 */       ((SortableList)paramObservableList).sort();
/*      */     } else {
/*  475 */       ArrayList localArrayList = new ArrayList(paramObservableList);
/*  476 */       Collections.sort(localArrayList);
/*  477 */       paramObservableList.setAll(localArrayList);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static <T> void sort(ObservableList<T> paramObservableList, Comparator<? super T> paramComparator)
/*      */   {
/*  490 */     if ((paramObservableList instanceof SortableList)) {
/*  491 */       ((SortableList)paramObservableList).sort(paramComparator);
/*      */     } else {
/*  493 */       ArrayList localArrayList = new ArrayList(paramObservableList);
/*  494 */       Collections.sort(localArrayList, paramComparator);
/*  495 */       paramObservableList.setAll(localArrayList);
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class CheckedObservableList<T>
/*      */     implements ObservableList<T>
/*      */   {
/*      */     final ObservableList<T> list;
/*      */     final Class<T> type;
/*      */ 
/*      */     CheckedObservableList(ObservableList<T> paramObservableList, Class<T> paramClass)
/*      */     {
/* 1070 */       if ((paramObservableList == null) || (paramClass == null)) {
/* 1071 */         throw new NullPointerException();
/*      */       }
/* 1073 */       this.list = paramObservableList;
/* 1074 */       this.type = paramClass;
/*      */     }
/*      */ 
/*      */     void typeCheck(Object paramObject) {
/* 1078 */       if (!this.type.isInstance(paramObject))
/* 1079 */         throw new ClassCastException("Attempt to insert " + paramObject.getClass() + " element into collection with element type " + this.type);
/*      */     }
/*      */ 
/*      */     public int size()
/*      */     {
/* 1087 */       return this.list.size();
/*      */     }
/*      */ 
/*      */     public boolean isEmpty()
/*      */     {
/* 1092 */       return this.list.isEmpty();
/*      */     }
/*      */ 
/*      */     public boolean contains(Object paramObject)
/*      */     {
/* 1097 */       return this.list.contains(paramObject);
/*      */     }
/*      */ 
/*      */     public Object[] toArray()
/*      */     {
/* 1102 */       return this.list.toArray();
/*      */     }
/*      */ 
/*      */     public <T> T[] toArray(T[] paramArrayOfT)
/*      */     {
/* 1107 */       return this.list.toArray(paramArrayOfT);
/*      */     }
/*      */ 
/*      */     public String toString()
/*      */     {
/* 1112 */       return this.list.toString();
/*      */     }
/*      */ 
/*      */     public boolean remove(Object paramObject)
/*      */     {
/* 1117 */       return this.list.remove(paramObject);
/*      */     }
/*      */ 
/*      */     public boolean containsAll(Collection<?> paramCollection)
/*      */     {
/* 1122 */       return this.list.containsAll(paramCollection);
/*      */     }
/*      */ 
/*      */     public boolean removeAll(Collection<?> paramCollection)
/*      */     {
/* 1127 */       return this.list.removeAll(paramCollection);
/*      */     }
/*      */ 
/*      */     public boolean retainAll(Collection<?> paramCollection)
/*      */     {
/* 1132 */       return this.list.retainAll(paramCollection);
/*      */     }
/*      */ 
/*      */     public boolean removeAll(T[] paramArrayOfT)
/*      */     {
/* 1137 */       return this.list.removeAll(paramArrayOfT);
/*      */     }
/*      */ 
/*      */     public boolean retainAll(T[] paramArrayOfT)
/*      */     {
/* 1142 */       return this.list.retainAll(paramArrayOfT);
/*      */     }
/*      */ 
/*      */     public void remove(int paramInt1, int paramInt2)
/*      */     {
/* 1147 */       this.list.remove(paramInt1, paramInt2);
/*      */     }
/*      */ 
/*      */     public void clear()
/*      */     {
/* 1152 */       this.list.clear();
/*      */     }
/*      */ 
/*      */     public boolean equals(Object paramObject)
/*      */     {
/* 1157 */       return (paramObject == this) || (this.list.equals(paramObject));
/*      */     }
/*      */ 
/*      */     public int hashCode()
/*      */     {
/* 1162 */       return this.list.hashCode();
/*      */     }
/*      */ 
/*      */     public T get(int paramInt)
/*      */     {
/* 1167 */       return this.list.get(paramInt);
/*      */     }
/*      */ 
/*      */     public T remove(int paramInt)
/*      */     {
/* 1172 */       return this.list.remove(paramInt);
/*      */     }
/*      */ 
/*      */     public int indexOf(Object paramObject)
/*      */     {
/* 1177 */       return this.list.indexOf(paramObject);
/*      */     }
/*      */ 
/*      */     public int lastIndexOf(Object paramObject)
/*      */     {
/* 1182 */       return this.list.lastIndexOf(paramObject);
/*      */     }
/*      */ 
/*      */     public T set(int paramInt, T paramT)
/*      */     {
/* 1187 */       typeCheck(paramT);
/* 1188 */       return this.list.set(paramInt, paramT);
/*      */     }
/*      */ 
/*      */     public void add(int paramInt, T paramT)
/*      */     {
/* 1193 */       typeCheck(paramT);
/* 1194 */       this.list.add(paramInt, paramT);
/*      */     }
/*      */ 
/*      */     public boolean addAll(int paramInt, Collection<? extends T> paramCollection)
/*      */     {
/* 1200 */       Object[] arrayOfObject = null;
/*      */       try {
/* 1202 */         arrayOfObject = paramCollection.toArray((Object[])Array.newInstance(this.type, 0));
/*      */       } catch (ArrayStoreException localArrayStoreException) {
/* 1204 */         throw new ClassCastException();
/*      */       }
/*      */ 
/* 1207 */       return this.list.addAll(paramInt, Arrays.asList(arrayOfObject));
/*      */     }
/*      */ 
/*      */     public boolean addAll(Collection<? extends T> paramCollection)
/*      */     {
/* 1213 */       Object[] arrayOfObject = null;
/*      */       try {
/* 1215 */         arrayOfObject = paramCollection.toArray((Object[])Array.newInstance(this.type, 0));
/*      */       } catch (ArrayStoreException localArrayStoreException) {
/* 1217 */         throw new ClassCastException();
/*      */       }
/*      */ 
/* 1220 */       return this.list.addAll(Arrays.asList(arrayOfObject));
/*      */     }
/*      */ 
/*      */     public ListIterator<T> listIterator()
/*      */     {
/* 1225 */       return listIterator(0);
/*      */     }
/*      */ 
/*      */     public ListIterator<T> listIterator(final int paramInt)
/*      */     {
/* 1230 */       return new ListIterator()
/*      */       {
/* 1232 */         ListIterator<T> i = FXCollections.CheckedObservableList.this.list.listIterator(paramInt);
/*      */ 
/*      */         public boolean hasNext()
/*      */         {
/* 1236 */           return this.i.hasNext();
/*      */         }
/*      */ 
/*      */         public T next()
/*      */         {
/* 1241 */           return this.i.next();
/*      */         }
/*      */ 
/*      */         public boolean hasPrevious()
/*      */         {
/* 1246 */           return this.i.hasPrevious();
/*      */         }
/*      */ 
/*      */         public T previous()
/*      */         {
/* 1251 */           return this.i.previous();
/*      */         }
/*      */ 
/*      */         public int nextIndex()
/*      */         {
/* 1256 */           return this.i.nextIndex();
/*      */         }
/*      */ 
/*      */         public int previousIndex()
/*      */         {
/* 1261 */           return this.i.previousIndex();
/*      */         }
/*      */ 
/*      */         public void remove()
/*      */         {
/* 1266 */           this.i.remove();
/*      */         }
/*      */ 
/*      */         public void set(T paramAnonymousT)
/*      */         {
/* 1271 */           FXCollections.CheckedObservableList.this.typeCheck(paramAnonymousT);
/* 1272 */           this.i.set(paramAnonymousT);
/*      */         }
/*      */ 
/*      */         public void add(T paramAnonymousT)
/*      */         {
/* 1277 */           FXCollections.CheckedObservableList.this.typeCheck(paramAnonymousT);
/* 1278 */           this.i.add(paramAnonymousT);
/*      */         }
/*      */       };
/*      */     }
/*      */ 
/*      */     public Iterator<T> iterator()
/*      */     {
/* 1285 */       return new Iterator()
/*      */       {
/* 1287 */         private final Iterator<T> it = FXCollections.CheckedObservableList.this.list.iterator();
/*      */ 
/*      */         public boolean hasNext()
/*      */         {
/* 1291 */           return this.it.hasNext();
/*      */         }
/*      */ 
/*      */         public T next()
/*      */         {
/* 1296 */           return this.it.next();
/*      */         }
/*      */ 
/*      */         public void remove()
/*      */         {
/* 1301 */           this.it.remove();
/*      */         }
/*      */       };
/*      */     }
/*      */ 
/*      */     public boolean add(T paramT)
/*      */     {
/* 1308 */       typeCheck(paramT);
/* 1309 */       return this.list.add(paramT);
/*      */     }
/*      */ 
/*      */     public List<T> subList(int paramInt1, int paramInt2)
/*      */     {
/* 1314 */       return Collections.checkedList(this.list.subList(paramInt1, paramInt2), this.type);
/*      */     }
/*      */ 
/*      */     public boolean addAll(T[] paramArrayOfT)
/*      */     {
/*      */       try
/*      */       {
/* 1321 */         Object[] arrayOfObject = (Object[])Array.newInstance(this.type, paramArrayOfT.length);
/* 1322 */         System.arraycopy(paramArrayOfT, 0, arrayOfObject, 0, paramArrayOfT.length);
/* 1323 */         return this.list.addAll(arrayOfObject); } catch (ArrayStoreException localArrayStoreException) {
/*      */       }
/* 1325 */       throw new ClassCastException();
/*      */     }
/*      */ 
/*      */     public boolean setAll(T[] paramArrayOfT)
/*      */     {
/*      */       try
/*      */       {
/* 1333 */         Object[] arrayOfObject = (Object[])Array.newInstance(this.type, paramArrayOfT.length);
/* 1334 */         System.arraycopy(paramArrayOfT, 0, arrayOfObject, 0, paramArrayOfT.length);
/* 1335 */         return this.list.setAll(arrayOfObject); } catch (ArrayStoreException localArrayStoreException) {
/*      */       }
/* 1337 */       throw new ClassCastException();
/*      */     }
/*      */ 
/*      */     public boolean setAll(Collection<? extends T> paramCollection)
/*      */     {
/* 1344 */       Object[] arrayOfObject = null;
/*      */       try {
/* 1346 */         arrayOfObject = paramCollection.toArray((Object[])Array.newInstance(this.type, 0));
/*      */       } catch (ArrayStoreException localArrayStoreException) {
/* 1348 */         throw new ClassCastException();
/*      */       }
/*      */ 
/* 1351 */       return this.list.setAll(Arrays.asList(arrayOfObject));
/*      */     }
/*      */ 
/*      */     public final void addListener(InvalidationListener paramInvalidationListener)
/*      */     {
/* 1356 */       this.list.addListener(paramInvalidationListener);
/*      */     }
/*      */ 
/*      */     public final void removeListener(InvalidationListener paramInvalidationListener)
/*      */     {
/* 1361 */       this.list.removeListener(paramInvalidationListener);
/*      */     }
/*      */ 
/*      */     public void addListener(ListChangeListener<? super T> paramListChangeListener)
/*      */     {
/* 1366 */       this.list.addListener(paramListChangeListener);
/*      */     }
/*      */ 
/*      */     public void removeListener(ListChangeListener<? super T> paramListChangeListener)
/*      */     {
/* 1371 */       this.list.removeListener(paramListChangeListener);
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class SynchronizedObservableList<T> extends FXCollections.SynchronizedList<T>
/*      */     implements ObservableList<T>
/*      */   {
/*      */     private final ObservableList<T> backingList;
/*      */ 
/*      */     SynchronizedObservableList(ObservableList<T> paramObservableList, Object paramObject)
/*      */     {
/*  991 */       super(paramObject);
/*  992 */       this.backingList = paramObservableList;
/*      */     }
/*      */ 
/*      */     SynchronizedObservableList(ObservableList<T> paramObservableList) {
/*  996 */       this(paramObservableList, new Object());
/*      */     }
/*      */ 
/*      */     public boolean addAll(T[] paramArrayOfT)
/*      */     {
/* 1001 */       synchronized (this.mutex) {
/* 1002 */         return this.backingList.addAll(paramArrayOfT);
/*      */       }
/*      */     }
/*      */ 
/*      */     public boolean setAll(T[] paramArrayOfT)
/*      */     {
/* 1008 */       synchronized (this.mutex) {
/* 1009 */         return this.backingList.setAll(paramArrayOfT);
/*      */       }
/*      */     }
/*      */ 
/*      */     public boolean removeAll(T[] paramArrayOfT)
/*      */     {
/* 1015 */       synchronized (this.mutex) {
/* 1016 */         return this.backingList.removeAll(paramArrayOfT);
/*      */       }
/*      */     }
/*      */ 
/*      */     public boolean retainAll(T[] paramArrayOfT)
/*      */     {
/* 1022 */       synchronized (this.mutex) {
/* 1023 */         return this.backingList.retainAll(paramArrayOfT);
/*      */       }
/*      */     }
/*      */ 
/*      */     public void remove(int paramInt1, int paramInt2)
/*      */     {
/* 1029 */       synchronized (this.mutex) {
/* 1030 */         this.backingList.remove(paramInt1, paramInt2);
/*      */       }
/*      */     }
/*      */ 
/*      */     public boolean setAll(Collection<? extends T> paramCollection)
/*      */     {
/* 1036 */       synchronized (this.mutex) {
/* 1037 */         return this.backingList.setAll(paramCollection);
/*      */       }
/*      */     }
/*      */ 
/*      */     public final void addListener(InvalidationListener paramInvalidationListener)
/*      */     {
/* 1043 */       this.backingList.addListener(paramInvalidationListener);
/*      */     }
/*      */ 
/*      */     public final void removeListener(InvalidationListener paramInvalidationListener)
/*      */     {
/* 1048 */       this.backingList.removeListener(paramInvalidationListener);
/*      */     }
/*      */ 
/*      */     public void addListener(ListChangeListener<? super T> paramListChangeListener)
/*      */     {
/* 1053 */       this.backingList.addListener(paramListChangeListener);
/*      */     }
/*      */ 
/*      */     public void removeListener(ListChangeListener<? super T> paramListChangeListener)
/*      */     {
/* 1058 */       this.backingList.removeListener(paramListChangeListener);
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class SynchronizedList<T>
/*      */     implements List<T>
/*      */   {
/*      */     final Object mutex;
/*      */     private final List<T> backingList;
/*      */ 
/*      */     SynchronizedList(List<T> paramList, Object paramObject)
/*      */     {
/*  816 */       this.backingList = paramList;
/*  817 */       this.mutex = paramObject;
/*      */     }
/*      */ 
/*      */     SynchronizedList(List<T> paramList) {
/*  821 */       this(paramList, new Object());
/*      */     }
/*      */ 
/*      */     public int size()
/*      */     {
/*  827 */       synchronized (this.mutex) {
/*  828 */         return this.backingList.size();
/*      */       }
/*      */     }
/*      */ 
/*      */     public boolean isEmpty()
/*      */     {
/*  834 */       synchronized (this.mutex) {
/*  835 */         return this.backingList.isEmpty();
/*      */       }
/*      */     }
/*      */ 
/*      */     public boolean contains(Object paramObject)
/*      */     {
/*  841 */       synchronized (this.mutex) {
/*  842 */         return this.backingList.contains(paramObject);
/*      */       }
/*      */     }
/*      */ 
/*      */     public Iterator<T> iterator()
/*      */     {
/*  848 */       return this.backingList.iterator();
/*      */     }
/*      */ 
/*      */     public Object[] toArray()
/*      */     {
/*  853 */       synchronized (this.mutex) {
/*  854 */         return this.backingList.toArray();
/*      */       }
/*      */     }
/*      */ 
/*      */     public <T> T[] toArray(T[] paramArrayOfT)
/*      */     {
/*  860 */       synchronized (this.mutex) {
/*  861 */         return this.backingList.toArray(paramArrayOfT);
/*      */       }
/*      */     }
/*      */ 
/*      */     public boolean add(T paramT)
/*      */     {
/*  867 */       synchronized (this.mutex) {
/*  868 */         return this.backingList.add(paramT);
/*      */       }
/*      */     }
/*      */ 
/*      */     public boolean remove(Object paramObject)
/*      */     {
/*  874 */       synchronized (this.mutex) {
/*  875 */         return this.backingList.remove(paramObject);
/*      */       }
/*      */     }
/*      */ 
/*      */     public boolean containsAll(Collection<?> paramCollection)
/*      */     {
/*  881 */       synchronized (this.mutex) {
/*  882 */         return this.backingList.containsAll(paramCollection);
/*      */       }
/*      */     }
/*      */ 
/*      */     public boolean addAll(Collection<? extends T> paramCollection)
/*      */     {
/*  888 */       synchronized (this.mutex) {
/*  889 */         return this.backingList.addAll(paramCollection);
/*      */       }
/*      */     }
/*      */ 
/*      */     public boolean addAll(int paramInt, Collection<? extends T> paramCollection)
/*      */     {
/*  895 */       synchronized (this.mutex) {
/*  896 */         return this.backingList.addAll(paramInt, paramCollection);
/*      */       }
/*      */     }
/*      */ 
/*      */     public boolean removeAll(Collection<?> paramCollection)
/*      */     {
/*  903 */       synchronized (this.mutex) {
/*  904 */         return this.backingList.removeAll(paramCollection);
/*      */       }
/*      */     }
/*      */ 
/*      */     public boolean retainAll(Collection<?> paramCollection)
/*      */     {
/*  910 */       synchronized (this.mutex) {
/*  911 */         return this.backingList.retainAll(paramCollection);
/*      */       }
/*      */     }
/*      */ 
/*      */     public void clear()
/*      */     {
/*  917 */       synchronized (this.mutex) {
/*  918 */         this.backingList.clear();
/*      */       }
/*      */     }
/*      */ 
/*      */     public T get(int paramInt)
/*      */     {
/*  924 */       synchronized (this.mutex) {
/*  925 */         return this.backingList.get(paramInt);
/*      */       }
/*      */     }
/*      */ 
/*      */     public T set(int paramInt, T paramT)
/*      */     {
/*  931 */       synchronized (this.mutex) {
/*  932 */         return this.backingList.set(paramInt, paramT);
/*      */       }
/*      */     }
/*      */ 
/*      */     public void add(int paramInt, T paramT)
/*      */     {
/*  938 */       synchronized (this.mutex) {
/*  939 */         this.backingList.add(paramInt, paramT);
/*      */       }
/*      */     }
/*      */ 
/*      */     public T remove(int paramInt)
/*      */     {
/*  945 */       synchronized (this.mutex) {
/*  946 */         return this.backingList.remove(paramInt);
/*      */       }
/*      */     }
/*      */ 
/*      */     public int indexOf(Object paramObject)
/*      */     {
/*  952 */       synchronized (this.mutex) {
/*  953 */         return this.backingList.indexOf(paramObject);
/*      */       }
/*      */     }
/*      */ 
/*      */     public int lastIndexOf(Object paramObject)
/*      */     {
/*  959 */       synchronized (this.mutex) {
/*  960 */         return this.backingList.lastIndexOf(paramObject);
/*      */       }
/*      */     }
/*      */ 
/*      */     public ListIterator<T> listIterator()
/*      */     {
/*  966 */       return this.backingList.listIterator();
/*      */     }
/*      */ 
/*      */     public ListIterator<T> listIterator(int paramInt)
/*      */     {
/*  971 */       synchronized (this.mutex) {
/*  972 */         return this.backingList.listIterator(paramInt);
/*      */       }
/*      */     }
/*      */ 
/*      */     public List<T> subList(int paramInt1, int paramInt2)
/*      */     {
/*  978 */       synchronized (this.mutex) {
/*  979 */         return new SynchronizedList(this.backingList.subList(paramInt1, paramInt2), this.mutex);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class UnmodifiableObservableListImpl<T> extends AbstractList<T>
/*      */     implements ObservableList<T>
/*      */   {
/*      */     private final ObservableList<T> backingList;
/*      */ 
/*      */     public UnmodifiableObservableListImpl(ObservableList<T> paramObservableList)
/*      */     {
/*  746 */       this.backingList = paramObservableList;
/*      */     }
/*      */ 
/*      */     public T get(int paramInt)
/*      */     {
/*  751 */       return this.backingList.get(paramInt);
/*      */     }
/*      */ 
/*      */     public int size()
/*      */     {
/*  756 */       return this.backingList.size();
/*      */     }
/*      */ 
/*      */     public final void addListener(InvalidationListener paramInvalidationListener)
/*      */     {
/*  761 */       this.backingList.addListener(paramInvalidationListener);
/*      */     }
/*      */ 
/*      */     public final void removeListener(InvalidationListener paramInvalidationListener)
/*      */     {
/*  766 */       this.backingList.removeListener(paramInvalidationListener);
/*      */     }
/*      */ 
/*      */     public void addListener(ListChangeListener<? super T> paramListChangeListener)
/*      */     {
/*  771 */       this.backingList.addListener(paramListChangeListener);
/*      */     }
/*      */ 
/*      */     public void removeListener(ListChangeListener<? super T> paramListChangeListener)
/*      */     {
/*  776 */       this.backingList.removeListener(paramListChangeListener);
/*      */     }
/*      */ 
/*      */     public boolean addAll(T[] paramArrayOfT)
/*      */     {
/*  781 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public boolean setAll(T[] paramArrayOfT)
/*      */     {
/*  786 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public boolean setAll(Collection<? extends T> paramCollection)
/*      */     {
/*  791 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public boolean removeAll(T[] paramArrayOfT)
/*      */     {
/*  796 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public boolean retainAll(T[] paramArrayOfT)
/*      */     {
/*  801 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public void remove(int paramInt1, int paramInt2)
/*      */     {
/*  806 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class SingletonObservableList<E> extends AbstractList<E>
/*      */     implements ObservableList<E>
/*      */   {
/*      */     private final E element;
/*      */ 
/*      */     public SingletonObservableList(E paramE)
/*      */     {
/*  664 */       if (paramE == null) {
/*  665 */         throw new NullPointerException();
/*      */       }
/*  667 */       this.element = paramE;
/*      */     }
/*      */ 
/*      */     public boolean addAll(E[] paramArrayOfE)
/*      */     {
/*  672 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public boolean setAll(E[] paramArrayOfE)
/*      */     {
/*  677 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public boolean setAll(Collection<? extends E> paramCollection)
/*      */     {
/*  682 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public boolean removeAll(E[] paramArrayOfE)
/*      */     {
/*  687 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public boolean retainAll(E[] paramArrayOfE)
/*      */     {
/*  692 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public void remove(int paramInt1, int paramInt2)
/*      */     {
/*  697 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public void addListener(InvalidationListener paramInvalidationListener)
/*      */     {
/*      */     }
/*      */ 
/*      */     public void removeListener(InvalidationListener paramInvalidationListener)
/*      */     {
/*      */     }
/*      */ 
/*      */     public void addListener(ListChangeListener<? super E> paramListChangeListener)
/*      */     {
/*      */     }
/*      */ 
/*      */     public void removeListener(ListChangeListener<? super E> paramListChangeListener)
/*      */     {
/*      */     }
/*      */ 
/*      */     public int size()
/*      */     {
/*  718 */       return 1;
/*      */     }
/*      */ 
/*      */     public boolean isEmpty()
/*      */     {
/*  723 */       return false;
/*      */     }
/*      */ 
/*      */     public boolean contains(Object paramObject)
/*      */     {
/*  728 */       return this.element.equals(paramObject);
/*      */     }
/*      */ 
/*      */     public E get(int paramInt)
/*      */     {
/*  733 */       if (paramInt != 0) {
/*  734 */         throw new IndexOutOfBoundsException();
/*      */       }
/*  736 */       return this.element;
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class EmptyObservableList<E> extends AbstractList<E>
/*      */     implements ObservableList<E>
/*      */   {
/*  501 */     private static final ListIterator iterator = new ListIterator()
/*      */     {
/*      */       public boolean hasNext()
/*      */       {
/*  505 */         return false;
/*      */       }
/*      */ 
/*      */       public Object next()
/*      */       {
/*  510 */         throw new NoSuchElementException();
/*      */       }
/*      */ 
/*      */       public void remove()
/*      */       {
/*  515 */         throw new UnsupportedOperationException();
/*      */       }
/*      */ 
/*      */       public boolean hasPrevious()
/*      */       {
/*  520 */         return false;
/*      */       }
/*      */ 
/*      */       public Object previous()
/*      */       {
/*  525 */         throw new NoSuchElementException();
/*      */       }
/*      */ 
/*      */       public int nextIndex()
/*      */       {
/*  530 */         return 0;
/*      */       }
/*      */ 
/*      */       public int previousIndex()
/*      */       {
/*  535 */         return -1;
/*      */       }
/*      */ 
/*      */       public void set(Object paramAnonymousObject)
/*      */       {
/*  540 */         throw new UnsupportedOperationException();
/*      */       }
/*      */ 
/*      */       public void add(Object paramAnonymousObject)
/*      */       {
/*  545 */         throw new UnsupportedOperationException();
/*      */       }
/*  501 */     };
/*      */ 
/*      */     public final void addListener(InvalidationListener paramInvalidationListener)
/*      */     {
/*      */     }
/*      */ 
/*      */     public final void removeListener(InvalidationListener paramInvalidationListener)
/*      */     {
/*      */     }
/*      */ 
/*      */     public void addListener(ListChangeListener<? super E> paramListChangeListener)
/*      */     {
/*      */     }
/*      */ 
/*      */     public void removeListener(ListChangeListener<? super E> paramListChangeListener)
/*      */     {
/*      */     }
/*      */ 
/*      */     public int size()
/*      */     {
/*  571 */       return 0;
/*      */     }
/*      */ 
/*      */     public boolean contains(Object paramObject)
/*      */     {
/*  576 */       return false;
/*      */     }
/*      */ 
/*      */     public Iterator<E> iterator()
/*      */     {
/*  582 */       return iterator;
/*      */     }
/*      */ 
/*      */     public boolean containsAll(Collection<?> paramCollection)
/*      */     {
/*  587 */       return paramCollection.isEmpty();
/*      */     }
/*      */ 
/*      */     public E get(int paramInt)
/*      */     {
/*  592 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */ 
/*      */     public int indexOf(Object paramObject)
/*      */     {
/*  597 */       return -1;
/*      */     }
/*      */ 
/*      */     public int lastIndexOf(Object paramObject)
/*      */     {
/*  602 */       return -1;
/*      */     }
/*      */ 
/*      */     public ListIterator<E> listIterator()
/*      */     {
/*  608 */       return iterator;
/*      */     }
/*      */ 
/*      */     public ListIterator<E> listIterator(int paramInt)
/*      */     {
/*  614 */       if (paramInt != 0) {
/*  615 */         throw new IndexOutOfBoundsException();
/*      */       }
/*  617 */       return iterator;
/*      */     }
/*      */ 
/*      */     public List<E> subList(int paramInt1, int paramInt2)
/*      */     {
/*  622 */       if ((paramInt1 != 0) || (paramInt2 != 0)) {
/*  623 */         throw new IndexOutOfBoundsException();
/*      */       }
/*  625 */       return this;
/*      */     }
/*      */ 
/*      */     public boolean addAll(E[] paramArrayOfE)
/*      */     {
/*  630 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public boolean setAll(E[] paramArrayOfE)
/*      */     {
/*  635 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public boolean setAll(Collection<? extends E> paramCollection)
/*      */     {
/*  640 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public boolean removeAll(E[] paramArrayOfE)
/*      */     {
/*  645 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public boolean retainAll(E[] paramArrayOfE)
/*      */     {
/*  650 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public void remove(int paramInt1, int paramInt2)
/*      */     {
/*  655 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.collections.FXCollections
 * JD-Core Version:    0.6.2
 */