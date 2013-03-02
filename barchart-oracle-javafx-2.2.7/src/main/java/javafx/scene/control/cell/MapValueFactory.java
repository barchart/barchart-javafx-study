/*     */ package javafx.scene.control.cell;
/*     */ 
/*     */ import java.util.Map;
/*     */ import javafx.beans.property.ReadOnlyBooleanWrapper;
/*     */ import javafx.beans.property.ReadOnlyDoubleWrapper;
/*     */ import javafx.beans.property.ReadOnlyFloatWrapper;
/*     */ import javafx.beans.property.ReadOnlyIntegerWrapper;
/*     */ import javafx.beans.property.ReadOnlyLongWrapper;
/*     */ import javafx.beans.property.ReadOnlyObjectWrapper;
/*     */ import javafx.beans.property.ReadOnlyStringWrapper;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ import javafx.scene.control.TableColumn.CellDataFeatures;
/*     */ import javafx.util.Callback;
/*     */ 
/*     */ public class MapValueFactory<T>
/*     */   implements Callback<TableColumn.CellDataFeatures<Map, T>, ObservableValue<T>>
/*     */ {
/*     */   private final Object key;
/*     */ 
/*     */   public MapValueFactory(Object paramObject)
/*     */   {
/*  85 */     this.key = paramObject;
/*     */   }
/*     */ 
/*     */   public ObservableValue<T> call(TableColumn.CellDataFeatures<Map, T> paramCellDataFeatures) {
/*  89 */     Map localMap = (Map)paramCellDataFeatures.getValue();
/*  90 */     Object localObject = localMap.get(this.key);
/*     */ 
/*  94 */     if ((localObject instanceof ObservableValue)) {
/*  95 */       return (ObservableValue)localObject;
/*     */     }
/*     */ 
/* 110 */     if ((localObject instanceof Boolean))
/* 111 */       return new ReadOnlyBooleanWrapper(((Boolean)localObject).booleanValue());
/* 112 */     if ((localObject instanceof Integer))
/* 113 */       return new ReadOnlyIntegerWrapper(((Integer)localObject).intValue());
/* 114 */     if ((localObject instanceof Float))
/* 115 */       return new ReadOnlyFloatWrapper(((Float)localObject).floatValue());
/* 116 */     if ((localObject instanceof Long))
/* 117 */       return new ReadOnlyLongWrapper(((Long)localObject).longValue());
/* 118 */     if ((localObject instanceof Double))
/* 119 */       return new ReadOnlyDoubleWrapper(((Double)localObject).doubleValue());
/* 120 */     if ((localObject instanceof String)) {
/* 121 */       return new ReadOnlyStringWrapper((String)localObject);
/*     */     }
/*     */ 
/* 125 */     return new ReadOnlyObjectWrapper(localObject);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.cell.MapValueFactory
 * JD-Core Version:    0.6.2
 */