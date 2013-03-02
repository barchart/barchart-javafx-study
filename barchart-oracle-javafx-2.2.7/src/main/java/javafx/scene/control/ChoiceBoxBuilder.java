/*    */ package javafx.scene.control;
/*    */ 
/*    */ import javafx.collections.ObservableList;
/*    */ import javafx.util.Builder;
/*    */ import javafx.util.StringConverter;
/*    */ 
/*    */ public class ChoiceBoxBuilder<T, B extends ChoiceBoxBuilder<T, B>> extends ControlBuilder<B>
/*    */   implements Builder<ChoiceBox<T>>
/*    */ {
/*    */   private int __set;
/*    */   private StringConverter<T> converter;
/*    */   private ObservableList<T> items;
/*    */   private SingleSelectionModel<T> selectionModel;
/*    */   private T value;
/*    */ 
/*    */   public static <T> ChoiceBoxBuilder<T, ?> create()
/*    */   {
/* 15 */     return new ChoiceBoxBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(ChoiceBox<T> paramChoiceBox)
/*    */   {
/* 20 */     super.applyTo(paramChoiceBox);
/* 21 */     int i = this.__set;
/* 22 */     if ((i & 0x1) != 0) paramChoiceBox.setConverter(this.converter);
/* 23 */     if ((i & 0x2) != 0) paramChoiceBox.setItems(this.items);
/* 24 */     if ((i & 0x4) != 0) paramChoiceBox.setSelectionModel(this.selectionModel);
/* 25 */     if ((i & 0x8) != 0) paramChoiceBox.setValue(this.value);
/*    */   }
/*    */ 
/*    */   public B converter(StringConverter<T> paramStringConverter)
/*    */   {
/* 34 */     this.converter = paramStringConverter;
/* 35 */     this.__set |= 1;
/* 36 */     return this;
/*    */   }
/*    */ 
/*    */   public B items(ObservableList<T> paramObservableList)
/*    */   {
/* 45 */     this.items = paramObservableList;
/* 46 */     this.__set |= 2;
/* 47 */     return this;
/*    */   }
/*    */ 
/*    */   public B selectionModel(SingleSelectionModel<T> paramSingleSelectionModel)
/*    */   {
/* 56 */     this.selectionModel = paramSingleSelectionModel;
/* 57 */     this.__set |= 4;
/* 58 */     return this;
/*    */   }
/*    */ 
/*    */   public B value(T paramT)
/*    */   {
/* 67 */     this.value = paramT;
/* 68 */     this.__set |= 8;
/* 69 */     return this;
/*    */   }
/*    */ 
/*    */   public ChoiceBox<T> build()
/*    */   {
/* 76 */     ChoiceBox localChoiceBox = new ChoiceBox();
/* 77 */     applyTo(localChoiceBox);
/* 78 */     return localChoiceBox;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.ChoiceBoxBuilder
 * JD-Core Version:    0.6.2
 */