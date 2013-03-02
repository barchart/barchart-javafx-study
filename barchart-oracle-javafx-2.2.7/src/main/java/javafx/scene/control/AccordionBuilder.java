/*    */ package javafx.scene.control;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import javafx.collections.ObservableList;
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class AccordionBuilder<B extends AccordionBuilder<B>> extends ControlBuilder<B>
/*    */   implements Builder<Accordion>
/*    */ {
/*    */   private int __set;
/*    */   private TitledPane expandedPane;
/*    */   private Collection<? extends TitledPane> panes;
/*    */ 
/*    */   public static AccordionBuilder<?> create()
/*    */   {
/* 15 */     return new AccordionBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(Accordion paramAccordion)
/*    */   {
/* 20 */     super.applyTo(paramAccordion);
/* 21 */     int i = this.__set;
/* 22 */     if ((i & 0x1) != 0) paramAccordion.setExpandedPane(this.expandedPane);
/* 23 */     if ((i & 0x2) != 0) paramAccordion.getPanes().addAll(this.panes);
/*    */   }
/*    */ 
/*    */   public B expandedPane(TitledPane paramTitledPane)
/*    */   {
/* 32 */     this.expandedPane = paramTitledPane;
/* 33 */     this.__set |= 1;
/* 34 */     return this;
/*    */   }
/*    */ 
/*    */   public B panes(Collection<? extends TitledPane> paramCollection)
/*    */   {
/* 43 */     this.panes = paramCollection;
/* 44 */     this.__set |= 2;
/* 45 */     return this;
/*    */   }
/*    */ 
/*    */   public B panes(TitledPane[] paramArrayOfTitledPane)
/*    */   {
/* 52 */     return panes(Arrays.asList(paramArrayOfTitledPane));
/*    */   }
/*    */ 
/*    */   public Accordion build()
/*    */   {
/* 59 */     Accordion localAccordion = new Accordion();
/* 60 */     applyTo(localAccordion);
/* 61 */     return localAccordion;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.AccordionBuilder
 * JD-Core Version:    0.6.2
 */