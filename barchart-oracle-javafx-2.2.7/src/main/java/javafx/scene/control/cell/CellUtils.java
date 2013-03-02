/*     */ package javafx.scene.control.cell;
/*     */ 
/*     */ import javafx.beans.property.ReadOnlyObjectProperty;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.scene.control.Cell;
/*     */ import javafx.scene.control.ChoiceBox;
/*     */ import javafx.scene.control.ComboBox;
/*     */ import javafx.scene.control.SingleSelectionModel;
/*     */ import javafx.scene.control.TextField;
/*     */ import javafx.scene.control.TreeItem;
/*     */ import javafx.scene.input.KeyCode;
/*     */ import javafx.scene.input.KeyEvent;
/*     */ import javafx.util.StringConverter;
/*     */ 
/*     */ class CellUtils
/*     */ {
/*  49 */   private static final StringConverter defaultStringConverter = new StringConverter() {
/*     */     public String toString(Object paramAnonymousObject) {
/*  51 */       return paramAnonymousObject == null ? null : paramAnonymousObject.toString();
/*     */     }
/*     */ 
/*     */     public Object fromString(String paramAnonymousString) {
/*  55 */       return paramAnonymousString;
/*     */     }
/*  49 */   };
/*     */ 
/*  59 */   private static final StringConverter defaultTreeItemStringConverter = new StringConverter()
/*     */   {
/*     */     public String toString(TreeItem paramAnonymousTreeItem) {
/*  62 */       return (paramAnonymousTreeItem == null) || (paramAnonymousTreeItem.getValue() == null) ? "" : paramAnonymousTreeItem.getValue().toString();
/*     */     }
/*     */ 
/*     */     public TreeItem fromString(String paramAnonymousString)
/*     */     {
/*  67 */       return new TreeItem(paramAnonymousString);
/*     */     }
/*  59 */   };
/*     */ 
/*     */   static <T> StringConverter<T> defaultStringConverter()
/*     */   {
/*  82 */     return defaultStringConverter;
/*     */   }
/*     */ 
/*     */   static <T> StringConverter<TreeItem<T>> defaultTreeItemStringConverter()
/*     */   {
/*  90 */     return defaultTreeItemStringConverter;
/*     */   }
/*     */ 
/*     */   private static <T> String getItemText(Cell<T> paramCell, StringConverter<T> paramStringConverter) {
/*  94 */     return paramStringConverter == null ? paramCell.getItem().toString() : paramCell.getItem() == null ? "" : paramStringConverter.toString(paramCell.getItem());
/*     */   }
/*     */ 
/*     */   static <T> void updateItem(Cell<T> paramCell, ChoiceBox<T> paramChoiceBox, StringConverter<T> paramStringConverter)
/*     */   {
/* 110 */     if (paramCell.isEmpty()) {
/* 111 */       paramCell.setText(null);
/* 112 */       paramCell.setGraphic(null);
/*     */     }
/* 114 */     else if (paramCell.isEditing()) {
/* 115 */       if (paramChoiceBox != null) {
/* 116 */         paramChoiceBox.getSelectionModel().select(paramCell.getItem());
/*     */       }
/* 118 */       paramCell.setText(null);
/* 119 */       paramCell.setGraphic(paramChoiceBox);
/*     */     } else {
/* 121 */       paramCell.setText(getItemText(paramCell, paramStringConverter));
/* 122 */       paramCell.setGraphic(null);
/*     */     }
/*     */   }
/*     */ 
/*     */   static <T> ChoiceBox<T> createChoiceBox(Cell<T> paramCell, ObservableList<T> paramObservableList)
/*     */   {
/* 130 */     ChoiceBox localChoiceBox = new ChoiceBox(paramObservableList);
/* 131 */     localChoiceBox.setMaxWidth(1.7976931348623157E+308D);
/* 132 */     localChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener()
/*     */     {
/*     */       public void changed(ObservableValue<? extends T> paramAnonymousObservableValue, T paramAnonymousT1, T paramAnonymousT2) {
/* 135 */         if (this.val$cell.isEditing())
/* 136 */           this.val$cell.commitEdit(paramAnonymousT2);
/*     */       }
/*     */     });
/* 140 */     return localChoiceBox;
/*     */   }
/*     */ 
/*     */   static <T> void updateItem(Cell<T> paramCell, TextField paramTextField, StringConverter<T> paramStringConverter)
/*     */   {
/* 152 */     if (paramCell.isEmpty()) {
/* 153 */       paramCell.setText(null);
/* 154 */       paramCell.setGraphic(null);
/*     */     }
/* 156 */     else if (paramCell.isEditing()) {
/* 157 */       if (paramTextField != null) {
/* 158 */         paramTextField.setText(getItemText(paramCell, paramStringConverter));
/*     */       }
/* 160 */       paramCell.setText(null);
/* 161 */       paramCell.setGraphic(paramTextField);
/*     */     } else {
/* 163 */       paramCell.setText(getItemText(paramCell, paramStringConverter));
/* 164 */       paramCell.setGraphic(null);
/*     */     }
/*     */   }
/*     */ 
/*     */   static <T> void startEdit(Cell<T> paramCell, TextField paramTextField, StringConverter<T> paramStringConverter)
/*     */   {
/* 173 */     if (paramTextField == null) {
/* 174 */       paramTextField = createTextField(paramCell, paramStringConverter);
/*     */     }
/* 176 */     paramTextField.setText(getItemText(paramCell, paramStringConverter));
/*     */ 
/* 178 */     paramCell.setText(null);
/* 179 */     paramCell.setGraphic(paramTextField);
/*     */ 
/* 181 */     paramTextField.selectAll();
/*     */   }
/*     */ 
/*     */   static <T> void cancelEdit(Cell<T> paramCell, StringConverter<T> paramStringConverter) {
/* 185 */     paramCell.setText(getItemText(paramCell, paramStringConverter));
/* 186 */     paramCell.setGraphic(null);
/*     */   }
/*     */ 
/*     */   private static <T> TextField createTextField(final Cell<T> paramCell, StringConverter<T> paramStringConverter) {
/* 190 */     final TextField localTextField = new TextField(getItemText(paramCell, paramStringConverter));
/* 191 */     localTextField.setOnKeyReleased(new EventHandler() {
/*     */       public void handle(KeyEvent paramAnonymousKeyEvent) {
/* 193 */         if (paramAnonymousKeyEvent.getCode() == KeyCode.ENTER) {
/* 194 */           if (this.val$converter == null) {
/* 195 */             throw new IllegalStateException("Attempting to convert text input into Object, but provided StringConverter is null. Be sure to set a StringConverter in your cell factory.");
/*     */           }
/*     */ 
/* 200 */           paramCell.commitEdit(this.val$converter.fromString(localTextField.getText()));
/* 201 */         } else if (paramAnonymousKeyEvent.getCode() == KeyCode.ESCAPE) {
/* 202 */           paramCell.cancelEdit();
/*     */         }
/*     */       }
/*     */     });
/* 206 */     return localTextField;
/*     */   }
/*     */ 
/*     */   static <T> void updateItem(Cell<T> paramCell, ComboBox<T> paramComboBox, StringConverter<T> paramStringConverter)
/*     */   {
/* 218 */     if (paramCell.isEmpty()) {
/* 219 */       paramCell.setText(null);
/* 220 */       paramCell.setGraphic(null);
/*     */     }
/* 222 */     else if (paramCell.isEditing()) {
/* 223 */       if (paramComboBox != null) {
/* 224 */         paramComboBox.getSelectionModel().select(paramCell.getItem());
/*     */       }
/* 226 */       paramCell.setText(null);
/* 227 */       paramCell.setGraphic(paramComboBox);
/*     */     } else {
/* 229 */       paramCell.setText(getItemText(paramCell, paramStringConverter));
/* 230 */       paramCell.setGraphic(null);
/*     */     }
/*     */   }
/*     */ 
/*     */   static <T> ComboBox<T> createComboBox(Cell<T> paramCell, ObservableList<T> paramObservableList)
/*     */   {
/* 236 */     ComboBox localComboBox = new ComboBox(paramObservableList);
/* 237 */     localComboBox.setMaxWidth(1.7976931348623157E+308D);
/* 238 */     localComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
/*     */       public void changed(ObservableValue<? extends T> paramAnonymousObservableValue, T paramAnonymousT1, T paramAnonymousT2) {
/* 240 */         if (this.val$cell.isEditing())
/* 241 */           this.val$cell.commitEdit(paramAnonymousT2);
/*     */       }
/*     */     });
/* 245 */     return localComboBox;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.cell.CellUtils
 * JD-Core Version:    0.6.2
 */