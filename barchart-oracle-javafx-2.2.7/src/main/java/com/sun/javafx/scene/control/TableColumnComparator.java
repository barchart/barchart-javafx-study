/*    */ package com.sun.javafx.scene.control;
/*    */ 
/*    */ import java.util.Comparator;
/*    */ import javafx.collections.FXCollections;
/*    */ import javafx.collections.ObservableList;
/*    */ import javafx.scene.control.TableColumn;
/*    */ 
/*    */ public class TableColumnComparator
/*    */   implements Comparator<Object>
/*    */ {
/*    */   private final ObservableList<TableColumn<?, ?>> columns;
/*    */ 
/*    */   public TableColumnComparator()
/*    */   {
/* 44 */     this.columns = FXCollections.observableArrayList();
/*    */   }
/*    */ 
/*    */   public ObservableList<TableColumn<?, ?>> getColumns() {
/* 48 */     return this.columns;
/*    */   }
/*    */ 
/*    */   public int compare(Object paramObject1, Object paramObject2)
/*    */   {
/* 53 */     for (TableColumn localTableColumn : this.columns) {
/* 54 */       Comparator localComparator = localTableColumn.getComparator();
/*    */ 
/* 56 */       Object localObject1 = localTableColumn.getCellData(paramObject1);
/* 57 */       Object localObject2 = localTableColumn.getCellData(paramObject2);
/*    */ 
/* 59 */       int i = 0;
/* 60 */       switch (1.$SwitchMap$javafx$scene$control$TableColumn$SortType[localTableColumn.getSortType().ordinal()]) { case 1:
/* 61 */         i = localComparator.compare(localObject1, localObject2); break;
/*    */       case 2:
/* 62 */         i = localComparator.compare(localObject2, localObject1);
/*    */       }
/*    */ 
/* 65 */       if (i != 0) {
/* 66 */         return i;
/*    */       }
/*    */     }
/* 69 */     return 0;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.TableColumnComparator
 * JD-Core Version:    0.6.2
 */