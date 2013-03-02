/*     */ package com.sun.javafx.tk.quantum;
/*     */ 
/*     */ import com.sun.glass.ui.Application;
/*     */ import com.sun.glass.ui.Menu;
/*     */ import com.sun.glass.ui.Menu.EventHandler;
/*     */ import com.sun.glass.ui.MenuBar;
/*     */ import com.sun.glass.ui.MenuItem;
/*     */ import com.sun.glass.ui.MenuItem.Callback;
/*     */ import com.sun.glass.ui.Pixels;
/*     */ import com.sun.javafx.PlatformUtil;
/*     */ import com.sun.javafx.menu.CheckMenuItemBase;
/*     */ import com.sun.javafx.menu.MenuBase;
/*     */ import com.sun.javafx.menu.MenuItemBase;
/*     */ import com.sun.javafx.menu.RadioMenuItemBase;
/*     */ import com.sun.javafx.menu.SeparatorMenuItemBase;
/*     */ import com.sun.javafx.tk.TKSystemMenu;
/*     */ import java.util.List;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.Observable;
/*     */ import javafx.beans.property.BooleanProperty;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.StringProperty;
/*     */ import javafx.collections.ListChangeListener;
/*     */ import javafx.collections.ListChangeListener.Change;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.image.ImageView;
/*     */ import javafx.scene.input.KeyCharacterCombination;
/*     */ import javafx.scene.input.KeyCode;
/*     */ import javafx.scene.input.KeyCodeCombination;
/*     */ import javafx.scene.input.KeyCombination;
/*     */ import javafx.scene.input.KeyCombination.ModifierValue;
/*     */ 
/*     */ public class GlassSystemMenu
/*     */   implements TKSystemMenu
/*     */ {
/*  43 */   private List<MenuBase> systemMenus = null;
/*  44 */   private MenuBar glassSystemMenuBar = null;
/*     */ 
/*  46 */   private InvalidationListener visibilityListener = new InvalidationListener() {
/*     */     public void invalidated(Observable paramAnonymousObservable) {
/*  48 */       if (GlassSystemMenu.this.systemMenus != null)
/*  49 */         GlassSystemMenu.this.setMenus(GlassSystemMenu.this.systemMenus);
/*     */     }
/*  46 */   };
/*     */ 
/*     */   protected void createMenuBar()
/*     */   {
/*  55 */     if (this.glassSystemMenuBar == null) {
/*  56 */       Application localApplication = Application.GetApplication();
/*  57 */       this.glassSystemMenuBar = localApplication.createMenuBar();
/*  58 */       localApplication.installDefaultMenus(this.glassSystemMenuBar);
/*     */ 
/*  60 */       if (this.systemMenus != null)
/*  61 */         setMenus(this.systemMenus);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected MenuBar getMenuBar()
/*     */   {
/*  67 */     return this.glassSystemMenuBar;
/*     */   }
/*     */ 
/*     */   public boolean isSupported() {
/*  71 */     return PlatformUtil.isMac();
/*     */   }
/*     */ 
/*     */   public void setMenus(List<MenuBase> paramList) {
/*  75 */     Application localApplication = Application.GetApplication();
/*  76 */     this.systemMenus = paramList;
/*  77 */     if (this.glassSystemMenuBar != null)
/*     */     {
/*  82 */       List localList = this.glassSystemMenuBar.getMenus();
/*  83 */       int i = localList.size();
/*     */ 
/*  88 */       for (int j = i - 1; j >= 1; j--) {
/*  89 */         this.glassSystemMenuBar.remove(j);
/*     */       }
/*     */ 
/*  92 */       for (MenuBase localMenuBase : paramList)
/*  93 */         addMenu(null, localMenuBase);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void addMenu(Menu paramMenu, MenuBase paramMenuBase)
/*     */   {
/*  99 */     if (paramMenu != null)
/* 100 */       insertMenu(paramMenu, paramMenuBase, paramMenu.getItems().size());
/*     */     else
/* 102 */       insertMenu(paramMenu, paramMenuBase, this.glassSystemMenuBar.getMenus().size());
/*     */   }
/*     */ 
/*     */   private void insertMenu(Menu paramMenu, MenuBase paramMenuBase, int paramInt)
/*     */   {
/* 107 */     Application localApplication = Application.GetApplication();
/* 108 */     final Menu localMenu = localApplication.createMenu(parseText(paramMenuBase), !paramMenuBase.isDisable());
/* 109 */     localMenu.setEventHandler(new GlassMenuEventHandler(paramMenuBase));
/*     */ 
/* 112 */     paramMenuBase.visibleProperty().removeListener(this.visibilityListener);
/* 113 */     paramMenuBase.visibleProperty().addListener(this.visibilityListener);
/*     */ 
/* 115 */     if (!paramMenuBase.isVisible()) {
/* 116 */       return;
/*     */     }
/*     */ 
/* 119 */     ObservableList localObservableList = paramMenuBase.getItemsBase();
/*     */ 
/* 121 */     localObservableList.addListener(new ListChangeListener() {
/*     */       public void onChanged(ListChangeListener.Change<? extends MenuItemBase> paramAnonymousChange) {
/* 123 */         while (paramAnonymousChange.next()) {
/* 124 */           int i = paramAnonymousChange.getFrom();
/* 125 */           int j = paramAnonymousChange.getTo();
/* 126 */           List localList = paramAnonymousChange.getRemoved();
/* 127 */           for (int k = i + localList.size() - 1; k >= i; k--) {
/* 128 */             localMenu.remove(k);
/*     */           }
/* 130 */           for (k = i; k < j; k++) {
/* 131 */             MenuItemBase localMenuItemBase = (MenuItemBase)paramAnonymousChange.getList().get(k);
/* 132 */             if ((localMenuItemBase instanceof MenuBase))
/* 133 */               GlassSystemMenu.this.insertMenu(localMenu, (MenuBase)localMenuItemBase, k);
/*     */             else
/* 135 */               GlassSystemMenu.this.insertMenuItem(localMenu, localMenuItemBase, k);
/*     */           }
/*     */         }
/*     */       }
/*     */     });
/* 142 */     for (MenuItemBase localMenuItemBase : localObservableList) {
/* 143 */       if ((localMenuItemBase instanceof MenuBase))
/*     */       {
/* 145 */         addMenu(localMenu, (MenuBase)localMenuItemBase);
/*     */       }
/*     */       else {
/* 148 */         addMenuItem(localMenu, localMenuItemBase);
/*     */       }
/*     */     }
/* 151 */     localMenu.setPixels(getPixels(paramMenuBase));
/*     */ 
/* 153 */     setMenuBindings(localMenu, paramMenuBase);
/*     */ 
/* 155 */     if (paramMenu != null)
/* 156 */       paramMenu.insert(localMenu, paramInt);
/*     */     else
/* 158 */       this.glassSystemMenuBar.insert(localMenu, paramInt);
/*     */   }
/*     */ 
/*     */   private void setMenuBindings(final Menu paramMenu, final MenuBase paramMenuBase)
/*     */   {
/* 163 */     paramMenuBase.textProperty().addListener(new InvalidationListener() {
/*     */       public void invalidated(Observable paramAnonymousObservable) {
/* 165 */         paramMenu.setTitle(GlassSystemMenu.this.parseText(paramMenuBase));
/*     */       }
/*     */     });
/* 168 */     paramMenuBase.disableProperty().addListener(new InvalidationListener() {
/*     */       public void invalidated(Observable paramAnonymousObservable) {
/* 170 */         paramMenu.setEnabled(!paramMenuBase.isDisable());
/*     */       }
/*     */     });
/* 173 */     paramMenuBase.mnemonicParsingProperty().addListener(new InvalidationListener() {
/*     */       public void invalidated(Observable paramAnonymousObservable) {
/* 175 */         paramMenu.setTitle(GlassSystemMenu.this.parseText(paramMenuBase));
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   private void addMenuItem(Menu paramMenu, MenuItemBase paramMenuItemBase) {
/* 181 */     insertMenuItem(paramMenu, paramMenuItemBase, paramMenu.getItems().size());
/*     */   }
/*     */ 
/*     */   private void insertMenuItem(final Menu paramMenu, final MenuItemBase paramMenuItemBase, int paramInt) {
/* 185 */     Node localNode = paramMenuItemBase.getGraphic();
/* 186 */     Application localApplication = Application.GetApplication();
/*     */ 
/* 190 */     paramMenuItemBase.visibleProperty().removeListener(this.visibilityListener);
/* 191 */     paramMenuItemBase.visibleProperty().addListener(this.visibilityListener);
/*     */ 
/* 193 */     if (!paramMenuItemBase.isVisible()) {
/* 194 */       return;
/*     */     }
/*     */ 
/* 197 */     if ((paramMenuItemBase instanceof SeparatorMenuItemBase)) {
/* 198 */       if (paramMenuItemBase.isVisible())
/* 199 */         paramMenu.insert(MenuItem.Separator, paramInt);
/*     */     }
/*     */     else {
/* 202 */       MenuItem.Callback local6 = new MenuItem.Callback()
/*     */       {
/*     */         public void action()
/*     */         {
/*     */           Object localObject;
/* 205 */           if ((paramMenuItemBase instanceof CheckMenuItemBase)) {
/* 206 */             localObject = (CheckMenuItemBase)paramMenuItemBase;
/* 207 */             ((CheckMenuItemBase)localObject).setSelected(!((CheckMenuItemBase)localObject).isSelected());
/* 208 */           } else if ((paramMenuItemBase instanceof RadioMenuItemBase))
/*     */           {
/* 213 */             localObject = (RadioMenuItemBase)paramMenuItemBase;
/*     */ 
/* 217 */             ((RadioMenuItemBase)localObject).setSelected(true);
/*     */           }
/* 219 */           paramMenuItemBase.fire();
/*     */         }
/*     */         public void validate() {
/* 222 */           Menu.EventHandler localEventHandler = paramMenu.getEventHandler();
/* 223 */           GlassMenuEventHandler localGlassMenuEventHandler = (GlassMenuEventHandler)localEventHandler;
/*     */ 
/* 225 */           if (localGlassMenuEventHandler.isMenuOpen())
/*     */           {
/* 227 */             return;
/*     */           }
/* 229 */           paramMenuItemBase.fireValidation();
/*     */         }
/*     */       };
/* 233 */       final MenuItem localMenuItem = localApplication.createMenuItem(parseText(paramMenuItemBase), local6);
/*     */ 
/* 235 */       paramMenuItemBase.textProperty().addListener(new InvalidationListener() {
/*     */         public void invalidated(Observable paramAnonymousObservable) {
/* 237 */           localMenuItem.setTitle(GlassSystemMenu.this.parseText(paramMenuItemBase));
/*     */         }
/*     */       });
/* 241 */       localMenuItem.setPixels(getPixels(paramMenuItemBase));
/* 242 */       paramMenuItemBase.graphicProperty().addListener(new InvalidationListener() {
/*     */         public void invalidated(Observable paramAnonymousObservable) {
/* 244 */           localMenuItem.setPixels(GlassSystemMenu.this.getPixels(paramMenuItemBase));
/*     */         }
/*     */       });
/* 248 */       localMenuItem.setEnabled(!paramMenuItemBase.isDisable());
/* 249 */       paramMenuItemBase.disableProperty().addListener(new InvalidationListener() {
/*     */         public void invalidated(Observable paramAnonymousObservable) {
/* 251 */           localMenuItem.setEnabled(!paramMenuItemBase.isDisable());
/*     */         }
/*     */       });
/* 255 */       setShortcut(localMenuItem, paramMenuItemBase);
/* 256 */       paramMenuItemBase.acceleratorProperty().addListener(new InvalidationListener() {
/*     */         public void invalidated(Observable paramAnonymousObservable) {
/* 258 */           GlassSystemMenu.this.setShortcut(localMenuItem, paramMenuItemBase);
/*     */         }
/*     */       });
/* 262 */       paramMenuItemBase.mnemonicParsingProperty().addListener(new InvalidationListener() {
/*     */         public void invalidated(Observable paramAnonymousObservable) {
/* 264 */           localMenuItem.setTitle(GlassSystemMenu.this.parseText(paramMenuItemBase));
/*     */         }
/*     */       });
/*     */       Object localObject;
/* 268 */       if ((paramMenuItemBase instanceof CheckMenuItemBase)) {
/* 269 */         localObject = (CheckMenuItemBase)paramMenuItemBase;
/* 270 */         localMenuItem.setChecked(((CheckMenuItemBase)localObject).isSelected());
/* 271 */         ((CheckMenuItemBase)localObject).selectedProperty().addListener(new InvalidationListener() {
/*     */           public void invalidated(Observable paramAnonymousObservable) {
/* 273 */             localMenuItem.setChecked(this.val$checkItem.isSelected());
/*     */           } } );
/*     */       }
/* 276 */       else if ((paramMenuItemBase instanceof RadioMenuItemBase)) {
/* 277 */         localObject = (RadioMenuItemBase)paramMenuItemBase;
/* 278 */         localMenuItem.setChecked(((RadioMenuItemBase)localObject).isSelected());
/* 279 */         ((RadioMenuItemBase)localObject).selectedProperty().addListener(new InvalidationListener() {
/*     */           public void invalidated(Observable paramAnonymousObservable) {
/* 281 */             localMenuItem.setChecked(this.val$radioItem.isSelected());
/*     */           }
/*     */         });
/*     */       }
/*     */ 
/* 286 */       paramMenu.insert(localMenuItem, paramInt);
/*     */     }
/*     */   }
/*     */ 
/*     */   private String parseText(MenuItemBase paramMenuItemBase) {
/* 291 */     String str = paramMenuItemBase.getText();
/* 292 */     if (str == null)
/*     */     {
/* 294 */       return "";
/* 295 */     }if ((!str.isEmpty()) && (paramMenuItemBase.isMnemonicParsing()))
/*     */     {
/* 298 */       return str.replaceFirst("_([^_])", "$1");
/*     */     }
/* 300 */     return str;
/*     */   }
/*     */ 
/*     */   private Pixels getPixels(MenuItemBase paramMenuItemBase)
/*     */   {
/* 305 */     if ((paramMenuItemBase.getGraphic() instanceof ImageView)) {
/* 306 */       ImageView localImageView = (ImageView)paramMenuItemBase.getGraphic();
/* 307 */       javafx.scene.image.Image localImage = localImageView.getImage();
/* 308 */       String str = localImage.impl_getUrl();
/*     */ 
/* 310 */       if ((str == null) || (PixelUtils.supportedFormatType(str))) {
/* 311 */         com.sun.prism.Image localImage1 = (com.sun.prism.Image)localImage.impl_getPlatformImage();
/*     */ 
/* 313 */         return localImage1 == null ? null : PixelUtils.imageToPixels(localImage1);
/*     */       }
/*     */     }
/* 316 */     return null;
/*     */   }
/*     */ 
/*     */   private void setShortcut(MenuItem paramMenuItem, MenuItemBase paramMenuItemBase) {
/* 320 */     KeyCombination localKeyCombination = paramMenuItemBase.getAccelerator();
/*     */ 
/* 322 */     if (localKeyCombination == null)
/*     */     {
/* 324 */       paramMenuItem.setShortcut(0, 0);
/*     */     }
/*     */     else
/*     */     {
/*     */       Object localObject1;
/*     */       Object localObject2;
/* 325 */       if ((localKeyCombination instanceof KeyCodeCombination)) {
/* 326 */         localObject1 = (KeyCodeCombination)localKeyCombination;
/* 327 */         localObject2 = ((KeyCodeCombination)localObject1).getCode();
/*     */ 
/* 329 */         assert ((PlatformUtil.isMac()) || (PlatformUtil.isLinux()));
/*     */ 
/* 331 */         int i = glassModifiers((KeyCombination)localObject1);
/* 332 */         String str = ((KeyCode)localObject2).impl_getChar().toLowerCase();
/*     */ 
/* 334 */         if (PlatformUtil.isMac())
/* 335 */           paramMenuItem.setShortcut(str.charAt(0), i);
/* 336 */         else if (PlatformUtil.isLinux()) {
/* 337 */           if ((i & 0x4) != 0)
/* 338 */             paramMenuItem.setShortcut(str.charAt(0), i);
/*     */           else
/* 340 */             paramMenuItem.setShortcut(0, 0);
/*     */         }
/*     */         else
/* 343 */           paramMenuItem.setShortcut(0, 0);
/*     */       }
/* 345 */       else if ((localKeyCombination instanceof KeyCharacterCombination)) {
/* 346 */         localObject1 = (KeyCharacterCombination)localKeyCombination;
/* 347 */         localObject2 = ((KeyCharacterCombination)localObject1).getCharacter();
/* 348 */         paramMenuItem.setShortcut(((String)localObject2).charAt(0), glassModifiers((KeyCombination)localObject1));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/* 353 */   private int glassModifiers(KeyCombination paramKeyCombination) { int i = 0;
/* 354 */     if (paramKeyCombination.getShift() == KeyCombination.ModifierValue.DOWN) {
/* 355 */       i++;
/*     */     }
/* 357 */     if (paramKeyCombination.getControl() == KeyCombination.ModifierValue.DOWN) {
/* 358 */       i += 4;
/*     */     }
/* 360 */     if (paramKeyCombination.getAlt() == KeyCombination.ModifierValue.DOWN) {
/* 361 */       i += 8;
/*     */     }
/* 363 */     if (paramKeyCombination.getShortcut() == KeyCombination.ModifierValue.DOWN) {
/* 364 */       if (PlatformUtil.isLinux())
/* 365 */         i += 4;
/* 366 */       else if (PlatformUtil.isMac()) {
/* 367 */         i += 16;
/*     */       }
/*     */     }
/* 370 */     if (paramKeyCombination.getMeta() == KeyCombination.ModifierValue.DOWN) {
/* 371 */       if (PlatformUtil.isLinux())
/* 372 */         i += 16;
/* 373 */       else if (PlatformUtil.isMac()) {
/* 374 */         i += 16;
/*     */       }
/*     */     }
/*     */ 
/* 378 */     if ((paramKeyCombination instanceof KeyCodeCombination)) {
/* 379 */       KeyCode localKeyCode = ((KeyCodeCombination)paramKeyCombination).getCode();
/* 380 */       int j = localKeyCode.impl_getCode();
/*     */ 
/* 382 */       if (((j >= KeyCode.F1.impl_getCode()) && (j <= KeyCode.F12.impl_getCode())) || ((j >= KeyCode.F13.impl_getCode()) && (j <= KeyCode.F24.impl_getCode())))
/*     */       {
/* 384 */         i += 2;
/*     */       }
/*     */     }
/*     */ 
/* 388 */     return i;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.quantum.GlassSystemMenu
 * JD-Core Version:    0.6.2
 */