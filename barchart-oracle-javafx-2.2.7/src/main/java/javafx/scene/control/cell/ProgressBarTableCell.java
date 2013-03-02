/*     */ package javafx.scene.control.cell;
/*     */ 
/*     */ import javafx.beans.property.DoubleProperty;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.scene.control.ProgressBar;
/*     */ import javafx.scene.control.TableCell;
/*     */ import javafx.scene.control.TableColumn;
/*     */ import javafx.util.Callback;
/*     */ 
/*     */ public class ProgressBarTableCell<S> extends TableCell<S, Double>
/*     */ {
/*     */   private final ProgressBar progressBar;
/*     */   private ObservableValue observable;
/*     */ 
/*     */   public static <S> Callback<TableColumn<S, Double>, TableCell<S, Double>> forTableColumn()
/*     */   {
/*  61 */     return new Callback() {
/*     */       public TableCell<S, Double> call(TableColumn<S, Double> paramAnonymousTableColumn) {
/*  63 */         return new ProgressBarTableCell();
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public ProgressBarTableCell()
/*     */   {
/*  92 */     getStyleClass().add("progress-bar-table-cell");
/*     */ 
/*  94 */     this.progressBar = new ProgressBar();
/*  95 */     setGraphic(this.progressBar);
/*     */   }
/*     */ 
/*     */   public void updateItem(Double paramDouble, boolean paramBoolean)
/*     */   {
/* 108 */     super.updateItem(paramDouble, paramBoolean);
/*     */ 
/* 110 */     if (paramBoolean) {
/* 111 */       setGraphic(null);
/*     */     } else {
/* 113 */       this.progressBar.progressProperty().unbind();
/*     */ 
/* 115 */       this.observable = getTableColumn().getCellObservableValue(getIndex());
/* 116 */       if (this.observable != null)
/* 117 */         this.progressBar.progressProperty().bind(this.observable);
/*     */       else {
/* 119 */         this.progressBar.setProgress(paramDouble.doubleValue());
/*     */       }
/*     */ 
/* 122 */       setGraphic(this.progressBar);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.cell.ProgressBarTableCell
 * JD-Core Version:    0.6.2
 */