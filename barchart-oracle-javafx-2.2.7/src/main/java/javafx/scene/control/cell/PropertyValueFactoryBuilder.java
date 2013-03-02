/*    */ package javafx.scene.control.cell;
/*    */ 
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class PropertyValueFactoryBuilder<S, T, B extends PropertyValueFactoryBuilder<S, T, B>>
/*    */   implements Builder<PropertyValueFactory<S, T>>
/*    */ {
/*    */   private String property;
/*    */ 
/*    */   public static <S, T> PropertyValueFactoryBuilder<S, T, ?> create()
/*    */   {
/* 15 */     return new PropertyValueFactoryBuilder();
/*    */   }
/*    */ 
/*    */   public B property(String paramString)
/*    */   {
/* 24 */     this.property = paramString;
/* 25 */     return this;
/*    */   }
/*    */ 
/*    */   public PropertyValueFactory<S, T> build()
/*    */   {
/* 32 */     PropertyValueFactory localPropertyValueFactory = new PropertyValueFactory(this.property);
/* 33 */     return localPropertyValueFactory;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.cell.PropertyValueFactoryBuilder
 * JD-Core Version:    0.6.2
 */