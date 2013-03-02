/*     */ package com.sun.javafx.fxml.expression;
/*     */ 
/*     */ import com.sun.javafx.fxml.BeanAdapter;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javafx.collections.ListChangeListener;
/*     */ import javafx.collections.ListChangeListener.Change;
/*     */ import javafx.collections.MapChangeListener;
/*     */ import javafx.collections.MapChangeListener.Change;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.collections.ObservableMap;
/*     */ 
/*     */ public class VariableExpression extends Expression
/*     */ {
/*     */   private List<String> path;
/*     */   private Object namespace;
/* 165 */   private PathMonitor pathMonitor = null;
/*     */ 
/*     */   public VariableExpression(List<String> paramList, Object paramObject) {
/* 168 */     if (paramList == null) {
/* 169 */       throw new NullPointerException();
/*     */     }
/*     */ 
/* 172 */     if (paramObject == null) {
/* 173 */       throw new NullPointerException();
/*     */     }
/*     */ 
/* 176 */     this.path = Collections.unmodifiableList(paramList);
/* 177 */     this.namespace = paramObject;
/*     */   }
/*     */ 
/*     */   public List<String> getPath()
/*     */   {
/* 184 */     return this.path;
/*     */   }
/*     */ 
/*     */   public Object getNamespace()
/*     */   {
/* 192 */     return this.namespace;
/*     */   }
/*     */ 
/*     */   protected Object evaluate()
/*     */   {
/* 197 */     return get(this.namespace, this.path);
/*     */   }
/*     */ 
/*     */   public boolean isDefined()
/*     */   {
/* 202 */     return isDefined(this.namespace, this.path);
/*     */   }
/*     */ 
/*     */   protected void registerChangeListeners()
/*     */   {
/* 207 */     if (this.pathMonitor == null) {
/* 208 */       this.pathMonitor = new PathMonitor(this.path);
/* 209 */       this.pathMonitor.registerChangeListeners();
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void unregisterChangeListeners()
/*     */   {
/* 215 */     if (this.pathMonitor != null) {
/* 216 */       this.pathMonitor.unregisterChangeListeners();
/* 217 */       this.pathMonitor = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 223 */     return this.path.toString();
/*     */   }
/*     */ 
/*     */   private class PathMonitor
/*     */   {
/*     */     public final List<String> path;
/*  25 */     public final LinkedList<Object> collections = new LinkedList();
/*     */ 
/*  27 */     private ListChangeListener<Object> listChangeListener = new ListChangeListener()
/*     */     {
/*     */       public void onChanged(ListChangeListener.Change<? extends Object> paramAnonymousChange) {
/*  30 */         while (paramAnonymousChange.next()) {
/*  31 */           ObservableList localObservableList = paramAnonymousChange.getList();
/*     */ 
/*  33 */           Iterator localIterator1 = VariableExpression.PathMonitor.this.collections.iterator();
/*  34 */           Iterator localIterator2 = VariableExpression.PathMonitor.this.path.iterator();
/*     */ 
/*  36 */           String str = null;
/*  37 */           while (localIterator1.hasNext()) {
/*  38 */             Object localObject = localIterator1.next();
/*  39 */             str = (String)localIterator2.next();
/*     */ 
/*  41 */             if (localObject == localObservableList)
/*     */             {
/*     */               break;
/*     */             }
/*     */           }
/*  46 */           int i = Integer.parseInt(str);
/*     */ 
/*  48 */           if ((i >= paramAnonymousChange.getFrom()) && (i < paramAnonymousChange.getTo())) {
/*  49 */             VariableExpression.this.invalidate();
/*     */ 
/*  51 */             if (localIterator1.hasNext()) {
/*  52 */               VariableExpression.PathMonitor.this.unregisterChangeListeners(localIterator1);
/*     */             }
/*     */ 
/*  55 */             if (localIterator2.hasNext())
/*  56 */               VariableExpression.PathMonitor.this.registerChangeListeners(Expression.get(localObservableList, str), localIterator2);
/*     */           }
/*     */         }
/*     */       }
/*  27 */     };
/*     */ 
/*  63 */     private MapChangeListener<String, Object> mapChangeListener = new MapChangeListener()
/*     */     {
/*     */       public void onChanged(MapChangeListener.Change<? extends String, ? extends Object> paramAnonymousChange) {
/*  66 */         ObservableMap localObservableMap = paramAnonymousChange.getMap();
/*     */ 
/*  68 */         Iterator localIterator1 = VariableExpression.PathMonitor.this.collections.iterator();
/*  69 */         Iterator localIterator2 = VariableExpression.PathMonitor.this.path.iterator();
/*     */ 
/*  71 */         String str = null;
/*  72 */         while (localIterator1.hasNext()) {
/*  73 */           Object localObject = localIterator1.next();
/*  74 */           str = (String)localIterator2.next();
/*     */ 
/*  76 */           if (localObject == localObservableMap)
/*     */           {
/*     */             break;
/*     */           }
/*     */         }
/*  81 */         if (str.equals(paramAnonymousChange.getKey())) {
/*  82 */           VariableExpression.this.invalidate();
/*     */ 
/*  84 */           if (localIterator1.hasNext()) {
/*  85 */             VariableExpression.PathMonitor.this.unregisterChangeListeners(localIterator1);
/*     */           }
/*     */ 
/*  88 */           if (localIterator2.hasNext())
/*  89 */             VariableExpression.PathMonitor.this.registerChangeListeners(Expression.get(localObservableMap, str), localIterator2);
/*     */         }
/*     */       }
/*  63 */     };
/*     */ 
/*     */     public PathMonitor()
/*     */     {
/*     */       Object localObject;
/*  96 */       this.path = localObject;
/*     */     }
/*     */ 
/*     */     public void registerChangeListeners() {
/* 100 */       registerChangeListeners(VariableExpression.this.namespace, this.path.iterator());
/*     */     }
/*     */ 
/*     */     public void registerChangeListeners(Object paramObject, Iterator<String> paramIterator)
/*     */     {
/* 105 */       String str = (String)paramIterator.next();
/*     */       Object localObject2;
/*     */       Object localObject1;
/* 108 */       if ((paramObject instanceof ObservableList)) {
/* 109 */         localObject2 = (ObservableList)paramObject;
/*     */ 
/* 111 */         ((ObservableList)localObject2).addListener(this.listChangeListener);
/*     */ 
/* 113 */         localObject1 = localObject2;
/*     */       }
/*     */       else {
/* 116 */         if ((paramObject instanceof Map))
/* 117 */           localObject2 = (ObservableMap)paramObject;
/*     */         else {
/* 119 */           localObject2 = new BeanAdapter(paramObject);
/*     */         }
/*     */ 
/* 122 */         ((ObservableMap)localObject2).addListener(this.mapChangeListener);
/*     */ 
/* 124 */         localObject1 = localObject2;
/*     */       }
/*     */ 
/* 127 */       this.collections.add(localObject1);
/*     */ 
/* 129 */       if (paramIterator.hasNext()) {
/* 130 */         paramObject = Expression.get(paramObject, str);
/*     */ 
/* 132 */         if (paramObject != null)
/* 133 */           registerChangeListeners(paramObject, paramIterator);
/*     */       }
/*     */     }
/*     */ 
/*     */     public void unregisterChangeListeners()
/*     */     {
/* 139 */       unregisterChangeListeners(this.collections.iterator());
/*     */     }
/*     */ 
/*     */     public void unregisterChangeListeners(Iterator<Object> paramIterator)
/*     */     {
/* 144 */       Object localObject1 = paramIterator.next();
/*     */       Object localObject2;
/* 146 */       if ((localObject1 instanceof List)) {
/* 147 */         localObject2 = (ObservableList)localObject1;
/* 148 */         ((ObservableList)localObject2).removeListener(this.listChangeListener);
/*     */       } else {
/* 150 */         localObject2 = (ObservableMap)localObject1;
/* 151 */         ((ObservableMap)localObject2).removeListener(this.mapChangeListener);
/*     */       }
/*     */ 
/* 154 */       if (paramIterator.hasNext()) {
/* 155 */         unregisterChangeListeners(paramIterator);
/*     */       }
/*     */ 
/* 158 */       this.collections.removeLast();
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.fxml.expression.VariableExpression
 * JD-Core Version:    0.6.2
 */