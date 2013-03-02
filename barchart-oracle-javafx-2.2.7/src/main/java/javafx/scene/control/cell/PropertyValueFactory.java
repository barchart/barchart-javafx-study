/*     */ package javafx.scene.control.cell;
/*     */ 
/*     */ import com.sun.javafx.logging.PlatformLogger;
/*     */ import com.sun.javafx.property.PropertyReference;
/*     */ import com.sun.javafx.scene.control.Logging;
/*     */ import javafx.beans.property.ReadOnlyObjectWrapper;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ import javafx.scene.control.TableColumn.CellDataFeatures;
/*     */ import javafx.util.Callback;
/*     */ 
/*     */ public class PropertyValueFactory<S, T>
/*     */   implements Callback<TableColumn.CellDataFeatures<S, T>, ObservableValue<T>>
/*     */ {
/*     */   private final String property;
/*     */   private Class columnClass;
/*     */   private String previousProperty;
/*     */   private PropertyReference<T> propertyRef;
/*     */ 
/*     */   public PropertyValueFactory(String paramString)
/*     */   {
/* 109 */     this.property = paramString;
/*     */   }
/*     */ 
/*     */   public ObservableValue<T> call(TableColumn.CellDataFeatures<S, T> paramCellDataFeatures)
/*     */   {
/* 114 */     return getCellDataReflectively(paramCellDataFeatures.getValue());
/*     */   }
/*     */ 
/*     */   public final String getProperty()
/*     */   {
/* 120 */     return this.property;
/*     */   }
/*     */   private ObservableValue<T> getCellDataReflectively(T paramT) {
/* 123 */     if ((getProperty() == null) || (getProperty().isEmpty()) || (paramT == null)) return null;
/*     */ 
/*     */     try
/*     */     {
/* 129 */       if ((this.columnClass == null) || (this.previousProperty == null) || (!this.columnClass.equals(paramT.getClass())) || (!this.previousProperty.equals(getProperty())))
/*     */       {
/* 134 */         this.columnClass = paramT.getClass();
/* 135 */         this.previousProperty = getProperty();
/* 136 */         this.propertyRef = new PropertyReference(paramT.getClass(), getProperty());
/*     */       }
/*     */ 
/* 139 */       return this.propertyRef.getProperty(paramT);
/*     */     }
/*     */     catch (IllegalStateException localIllegalStateException1) {
/*     */       try {
/* 143 */         Object localObject = this.propertyRef.get(paramT);
/* 144 */         return new ReadOnlyObjectWrapper(localObject);
/*     */       }
/*     */       catch (IllegalStateException localIllegalStateException2)
/*     */       {
/* 150 */         PlatformLogger localPlatformLogger = Logging.getControlsLogger();
/* 151 */         if (localPlatformLogger.isLoggable(900)) {
/* 152 */           localPlatformLogger.finest("Can not retrieve property '" + getProperty() + "' in PropertyBindingFactory: " + this + " with provided class type: " + paramT.getClass(), localIllegalStateException1);
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 158 */     return null;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.cell.PropertyValueFactory
 * JD-Core Version:    0.6.2
 */