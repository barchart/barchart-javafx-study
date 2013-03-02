/*     */ package com.sun.javafx.scene;
/*     */ 
/*     */ import com.sun.javafx.PlatformUtil;
/*     */ import com.sun.javafx.collections.ObservableListWrapper;
/*     */ import com.sun.javafx.collections.ObservableMapWrapper;
/*     */ import com.sun.javafx.event.BasicEventDispatcher;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.collections.ObservableMap;
/*     */ import javafx.event.Event;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.input.KeyCode;
/*     */ import javafx.scene.input.KeyCombination;
/*     */ import javafx.scene.input.KeyEvent;
/*     */ import javafx.scene.input.Mnemonic;
/*     */ 
/*     */ public final class KeyboardShortcutsHandler extends BasicEventDispatcher
/*     */ {
/*     */   private ObservableMap<KeyCombination, Runnable> accelerators;
/*     */   private ObservableMap<KeyCombination, ObservableList<Mnemonic>> mnemonics;
/*  52 */   private boolean mnemonicsLatch = false;
/*     */ 
/* 323 */   private boolean mnemonicsDisplayEnabled = false;
/*     */ 
/*     */   public void addMnemonic(Mnemonic paramMnemonic)
/*     */   {
/*  55 */     Object localObject = (ObservableList)getMnemonics().get(paramMnemonic.getKeyCombination());
/*  56 */     if (localObject == null) {
/*  57 */       localObject = new ObservableListWrapper(new ArrayList());
/*  58 */       getMnemonics().put(paramMnemonic.getKeyCombination(), localObject);
/*     */     }
/*  60 */     int i = 0;
/*  61 */     for (int j = 0; j < ((ObservableList)localObject).size(); j++) {
/*  62 */       if (((ObservableList)localObject).get(j) == paramMnemonic) {
/*  63 */         i = 1;
/*     */       }
/*     */     }
/*  66 */     if (i == 0)
/*  67 */       ((ObservableList)localObject).add(paramMnemonic);
/*     */   }
/*     */ 
/*     */   public void removeMnemonic(Mnemonic paramMnemonic)
/*     */   {
/*  72 */     ObservableList localObservableList = (ObservableList)getMnemonics().get(paramMnemonic.getKeyCombination());
/*  73 */     if (localObservableList != null)
/*  74 */       for (int i = 0; i < localObservableList.size(); i++)
/*  75 */         if (((Mnemonic)localObservableList.get(i)).getNode() == paramMnemonic.getNode())
/*  76 */           localObservableList.remove(i);
/*     */   }
/*     */ 
/*     */   public ObservableMap<KeyCombination, ObservableList<Mnemonic>> getMnemonics()
/*     */   {
/*  83 */     if (this.mnemonics == null) {
/*  84 */       this.mnemonics = new ObservableMapWrapper(new HashMap());
/*     */     }
/*  86 */     return this.mnemonics;
/*     */   }
/*     */ 
/*     */   public ObservableMap<KeyCombination, Runnable> getAccelerators() {
/*  90 */     if (this.accelerators == null) {
/*  91 */       this.accelerators = new ObservableMapWrapper(new HashMap());
/*     */     }
/*  93 */     return this.accelerators;
/*     */   }
/*     */ 
/*     */   public Event dispatchBubblingEvent(Event paramEvent)
/*     */   {
/* 107 */     if (paramEvent.getEventType() == KeyEvent.KEY_PRESSED) {
/* 108 */       if (PlatformUtil.isMac()) {
/* 109 */         if (((KeyEvent)paramEvent).isMetaDown()) {
/* 110 */           processMnemonics((KeyEvent)paramEvent);
/*     */         }
/*     */       }
/* 113 */       else if ((((KeyEvent)paramEvent).isAltDown()) || (isMnemonicsDisplayEnabled())) {
/* 114 */         processMnemonics((KeyEvent)paramEvent);
/*     */       }
/*     */ 
/* 118 */       if (!paramEvent.isConsumed()) {
/* 119 */         processAccelerators((KeyEvent)paramEvent);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 127 */     if (!PlatformUtil.isMac()) {
/* 128 */       if ((paramEvent.getEventType() == KeyEvent.KEY_PRESSED) && 
/* 129 */         (((KeyEvent)paramEvent).isAltDown()) && (!paramEvent.isConsumed()))
/*     */       {
/* 133 */         if (!isMnemonicsDisplayEnabled()) {
/* 134 */           setMnemonicsDisplayEnabled(true);
/*     */         }
/* 136 */         if (PlatformUtil.isWindows()) {
/* 137 */           this.mnemonicsLatch = (!this.mnemonicsLatch);
/*     */         }
/*     */       }
/*     */ 
/* 141 */       if ((paramEvent.getEventType() == KeyEvent.KEY_RELEASED) && 
/* 142 */         (!((KeyEvent)paramEvent).isAltDown()) && 
/* 143 */         (this.mnemonicsLatch != true)) {
/* 144 */         setMnemonicsDisplayEnabled(false);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 149 */     return paramEvent;
/*     */   }
/*     */ 
/*     */   private void processMnemonics(KeyEvent paramKeyEvent) {
/* 153 */     if (this.mnemonics != null)
/*     */     {
/* 155 */       ObservableList localObservableList = null;
/*     */ 
/* 158 */       for (Iterator localIterator = this.mnemonics.entrySet().iterator(); localIterator.hasNext(); ) { localObject1 = (Map.Entry)localIterator.next();
/*     */ 
/* 160 */         if (!isMnemonicsDisplayEnabled()) {
/* 161 */           if (((KeyCombination)((Map.Entry)localObject1).getKey()).match(paramKeyEvent)) {
/* 162 */             localObservableList = (ObservableList)((Map.Entry)localObject1).getValue();
/* 163 */             break;
/*     */           }
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/* 172 */           localObject2 = KeyEvent.impl_keyEvent(paramKeyEvent.getTarget(), paramKeyEvent.getCharacter(), paramKeyEvent.getText(), paramKeyEvent.getCode().impl_getCode(), paramKeyEvent.isShiftDown(), paramKeyEvent.isControlDown(), true, paramKeyEvent.isMetaDown(), KeyEvent.KEY_PRESSED);
/*     */ 
/* 183 */           if (((KeyCombination)((Map.Entry)localObject1).getKey()).match((KeyEvent)localObject2)) {
/* 184 */             localObservableList = (ObservableList)((Map.Entry)localObject1).getValue();
/* 185 */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */       Object localObject1;
/*     */       Object localObject2;
/* 190 */       if (localObservableList != null)
/*     */       {
/* 199 */         int i = 0;
/* 200 */         localObject1 = null;
/* 201 */         localObject2 = null;
/* 202 */         int j = -1;
/* 203 */         int k = -1;
/*     */ 
/* 208 */         for (int m = 0; m < localObservableList.size(); m++) {
/* 209 */           if ((localObservableList.get(m) instanceof Mnemonic)) {
/* 210 */             Node localNode = ((Mnemonic)localObservableList.get(m)).getNode();
/*     */ 
/* 212 */             if ((localObject2 == null) && (localNode.impl_isTreeVisible())) {
/* 213 */               localObject2 = (Mnemonic)localObservableList.get(m);
/*     */             }
/*     */ 
/* 216 */             if ((localNode.impl_isTreeVisible()) && (localNode.isFocusTraversable())) {
/* 217 */               if (localObject1 == null) {
/* 218 */                 localObject1 = localNode;
/*     */               }
/*     */               else
/*     */               {
/* 224 */                 i = 1;
/* 225 */                 if ((j != -1) && 
/* 226 */                   (k == -1)) {
/* 227 */                   k = m;
/*     */                 }
/*     */ 
/*     */               }
/*     */ 
/*     */             }
/*     */ 
/* 235 */             if (localNode.isFocused()) {
/* 236 */               j = m;
/*     */             }
/*     */           }
/*     */         }
/*     */ 
/* 241 */         if (localObject1 != null) {
/* 242 */           if ((i == 0 ? 1 : 0) == 1)
/*     */           {
/* 246 */             ((Node)localObject1).requestFocus();
/* 247 */             paramKeyEvent.consume();
/*     */           }
/* 255 */           else if (j == -1) {
/* 256 */             ((Node)localObject1).requestFocus();
/* 257 */             paramKeyEvent.consume();
/*     */           }
/* 260 */           else if (j >= localObservableList.size()) {
/* 261 */             ((Node)localObject1).requestFocus();
/* 262 */             paramKeyEvent.consume();
/*     */           }
/*     */           else {
/* 265 */             if (k != -1) {
/* 266 */               ((Mnemonic)localObservableList.get(k)).getNode().requestFocus();
/*     */             }
/*     */             else {
/* 269 */               ((Node)localObject1).requestFocus();
/*     */             }
/* 271 */             paramKeyEvent.consume();
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 276 */         if ((i == 0) && (localObject2 != null))
/* 277 */           ((Mnemonic)localObject2).fire();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void processAccelerators(KeyEvent paramKeyEvent)
/*     */   {
/* 284 */     if (this.accelerators != null)
/*     */     {
/* 286 */       for (Map.Entry localEntry : this.accelerators.entrySet())
/*     */       {
/* 288 */         if (((KeyCombination)localEntry.getKey()).match(paramKeyEvent)) {
/* 289 */           Runnable localRunnable = (Runnable)localEntry.getValue();
/* 290 */           if (localRunnable != null)
/*     */           {
/* 296 */             localRunnable.run();
/* 297 */             paramKeyEvent.consume();
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void processMnemonicsKeyDisplay() {
/* 305 */     ObservableList localObservableList = null;
/* 306 */     if (this.mnemonics != null)
/* 307 */       for (Map.Entry localEntry : this.mnemonics.entrySet()) {
/* 308 */         localObservableList = (ObservableList)localEntry.getValue();
/*     */ 
/* 310 */         if (localObservableList != null)
/* 311 */           for (int i = 0; i < localObservableList.size(); i++) {
/* 312 */             Node localNode = ((Mnemonic)localObservableList.get(i)).getNode();
/* 313 */             localNode.impl_setShowMnemonics(this.mnemonicsDisplayEnabled);
/*     */           }
/*     */       }
/*     */   }
/*     */ 
/*     */   public boolean isMnemonicsDisplayEnabled()
/*     */   {
/* 326 */     return this.mnemonicsDisplayEnabled;
/*     */   }
/*     */   public void setMnemonicsDisplayEnabled(boolean paramBoolean) {
/* 329 */     if (paramBoolean != this.mnemonicsDisplayEnabled) {
/* 330 */       this.mnemonicsDisplayEnabled = paramBoolean;
/* 331 */       processMnemonicsKeyDisplay();
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.KeyboardShortcutsHandler
 * JD-Core Version:    0.6.2
 */