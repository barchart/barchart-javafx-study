/*    */ package javafx.scene.control;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import javafx.collections.ObservableList;
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class MenuBarBuilder<B extends MenuBarBuilder<B>> extends ControlBuilder<B>
/*    */   implements Builder<MenuBar>
/*    */ {
/*    */   private int __set;
/*    */   private Collection<? extends Menu> menus;
/*    */   private boolean useSystemMenuBar;
/*    */ 
/*    */   public static MenuBarBuilder<?> create()
/*    */   {
/* 15 */     return new MenuBarBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(MenuBar paramMenuBar)
/*    */   {
/* 20 */     super.applyTo(paramMenuBar);
/* 21 */     int i = this.__set;
/* 22 */     if ((i & 0x1) != 0) paramMenuBar.getMenus().addAll(this.menus);
/* 23 */     if ((i & 0x2) != 0) paramMenuBar.setUseSystemMenuBar(this.useSystemMenuBar);
/*    */   }
/*    */ 
/*    */   public B menus(Collection<? extends Menu> paramCollection)
/*    */   {
/* 32 */     this.menus = paramCollection;
/* 33 */     this.__set |= 1;
/* 34 */     return this;
/*    */   }
/*    */ 
/*    */   public B menus(Menu[] paramArrayOfMenu)
/*    */   {
/* 41 */     return menus(Arrays.asList(paramArrayOfMenu));
/*    */   }
/*    */ 
/*    */   public B useSystemMenuBar(boolean paramBoolean)
/*    */   {
/* 50 */     this.useSystemMenuBar = paramBoolean;
/* 51 */     this.__set |= 2;
/* 52 */     return this;
/*    */   }
/*    */ 
/*    */   public MenuBar build()
/*    */   {
/* 59 */     MenuBar localMenuBar = new MenuBar();
/* 60 */     applyTo(localMenuBar);
/* 61 */     return localMenuBar;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.MenuBarBuilder
 * JD-Core Version:    0.6.2
 */