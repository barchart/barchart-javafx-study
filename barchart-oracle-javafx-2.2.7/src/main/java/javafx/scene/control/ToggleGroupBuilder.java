/*    */ package javafx.scene.control;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import javafx.collections.ObservableList;
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class ToggleGroupBuilder<B extends ToggleGroupBuilder<B>>
/*    */   implements Builder<ToggleGroup>
/*    */ {
/*    */   private boolean __set;
/*    */   private Collection<? extends Toggle> toggles;
/*    */ 
/*    */   public static ToggleGroupBuilder<?> create()
/*    */   {
/* 15 */     return new ToggleGroupBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(ToggleGroup paramToggleGroup)
/*    */   {
/* 20 */     if (this.__set) paramToggleGroup.getToggles().addAll(this.toggles);
/*    */   }
/*    */ 
/*    */   public B toggles(Collection<? extends Toggle> paramCollection)
/*    */   {
/* 29 */     this.toggles = paramCollection;
/* 30 */     this.__set = true;
/* 31 */     return this;
/*    */   }
/*    */ 
/*    */   public B toggles(Toggle[] paramArrayOfToggle)
/*    */   {
/* 38 */     return toggles(Arrays.asList(paramArrayOfToggle));
/*    */   }
/*    */ 
/*    */   public ToggleGroup build()
/*    */   {
/* 45 */     ToggleGroup localToggleGroup = new ToggleGroup();
/* 46 */     applyTo(localToggleGroup);
/* 47 */     return localToggleGroup;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.ToggleGroupBuilder
 * JD-Core Version:    0.6.2
 */