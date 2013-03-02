/*     */ package com.sun.javafx.scene.control.skin;
/*     */ 
/*     */ import com.sun.javafx.event.EventDispatchChainImpl;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.Observable;
/*     */ import javafx.beans.property.IntegerProperty;
/*     */ import javafx.beans.property.ReadOnlyBooleanProperty;
/*     */ import javafx.beans.property.StringProperty;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.event.EventDispatchChain;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.control.Skin;
/*     */ import javafx.scene.control.TextField;
/*     */ 
/*     */ abstract class InputFieldSkin
/*     */   implements Skin<InputField>
/*     */ {
/*     */   protected InputField control;
/*     */   private InnerTextField textField;
/*     */   private InvalidationListener InputFieldFocusListener;
/*     */   private InvalidationListener InputFieldStyleClassListener;
/*     */ 
/*     */   public InputFieldSkin(final InputField paramInputField)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: invokespecial 2	java/lang/Object:<init>	()V
/*     */     //   4: aload_0
/*     */     //   5: aload_1
/*     */     //   6: putfield 3	com/sun/javafx/scene/control/skin/InputFieldSkin:control	Lcom/sun/javafx/scene/control/skin/InputField;
/*     */     //   9: aload_0
/*     */     //   10: new 4	com/sun/javafx/scene/control/skin/InputFieldSkin$1
/*     */     //   13: dup
/*     */     //   14: aload_0
/*     */     //   15: invokespecial 5	com/sun/javafx/scene/control/skin/InputFieldSkin$1:<init>	(Lcom/sun/javafx/scene/control/skin/InputFieldSkin;)V
/*     */     //   18: putfield 1	com/sun/javafx/scene/control/skin/InputFieldSkin:textField	Lcom/sun/javafx/scene/control/skin/InputFieldSkin$InnerTextField;
/*     */     //   21: aload_0
/*     */     //   22: getfield 1	com/sun/javafx/scene/control/skin/InputFieldSkin:textField	Lcom/sun/javafx/scene/control/skin/InputFieldSkin$InnerTextField;
/*     */     //   25: ldc 6
/*     */     //   27: invokevirtual 7	com/sun/javafx/scene/control/skin/InputFieldSkin$InnerTextField:setId	(Ljava/lang/String;)V
/*     */     //   30: aload_0
/*     */     //   31: getfield 1	com/sun/javafx/scene/control/skin/InputFieldSkin:textField	Lcom/sun/javafx/scene/control/skin/InputFieldSkin$InnerTextField;
/*     */     //   34: invokevirtual 8	com/sun/javafx/scene/control/skin/InputFieldSkin$InnerTextField:getStyleClass	()Ljavafx/collections/ObservableList;
/*     */     //   37: aload_1
/*     */     //   38: invokevirtual 9	com/sun/javafx/scene/control/skin/InputField:getStyleClass	()Ljavafx/collections/ObservableList;
/*     */     //   41: invokeinterface 10 2 0
/*     */     //   46: pop
/*     */     //   47: aload_1
/*     */     //   48: invokevirtual 9	com/sun/javafx/scene/control/skin/InputField:getStyleClass	()Ljavafx/collections/ObservableList;
/*     */     //   51: aload_0
/*     */     //   52: new 11	com/sun/javafx/scene/control/skin/InputFieldSkin$2
/*     */     //   55: dup
/*     */     //   56: aload_0
/*     */     //   57: aload_1
/*     */     //   58: invokespecial 12	com/sun/javafx/scene/control/skin/InputFieldSkin$2:<init>	(Lcom/sun/javafx/scene/control/skin/InputFieldSkin;Lcom/sun/javafx/scene/control/skin/InputField;)V
/*     */     //   61: dup_x1
/*     */     //   62: putfield 13	com/sun/javafx/scene/control/skin/InputFieldSkin:InputFieldStyleClassListener	Ljavafx/beans/InvalidationListener;
/*     */     //   65: invokeinterface 14 2 0
/*     */     //   70: aload_0
/*     */     //   71: getfield 1	com/sun/javafx/scene/control/skin/InputFieldSkin:textField	Lcom/sun/javafx/scene/control/skin/InputFieldSkin$InnerTextField;
/*     */     //   74: invokevirtual 15	com/sun/javafx/scene/control/skin/InputFieldSkin$InnerTextField:promptTextProperty	()Ljavafx/beans/property/StringProperty;
/*     */     //   77: aload_1
/*     */     //   78: invokevirtual 16	com/sun/javafx/scene/control/skin/InputField:promptTextProperty	()Ljavafx/beans/property/StringProperty;
/*     */     //   81: invokevirtual 17	javafx/beans/property/StringProperty:bind	(Ljavafx/beans/value/ObservableValue;)V
/*     */     //   84: aload_0
/*     */     //   85: getfield 1	com/sun/javafx/scene/control/skin/InputFieldSkin:textField	Lcom/sun/javafx/scene/control/skin/InputFieldSkin$InnerTextField;
/*     */     //   88: invokevirtual 18	com/sun/javafx/scene/control/skin/InputFieldSkin$InnerTextField:prefColumnCountProperty	()Ljavafx/beans/property/IntegerProperty;
/*     */     //   91: aload_1
/*     */     //   92: invokevirtual 19	com/sun/javafx/scene/control/skin/InputField:prefColumnCountProperty	()Ljavafx/beans/property/IntegerProperty;
/*     */     //   95: invokevirtual 20	javafx/beans/property/IntegerProperty:bind	(Ljavafx/beans/value/ObservableValue;)V
/*     */     //   98: aload_0
/*     */     //   99: getfield 1	com/sun/javafx/scene/control/skin/InputFieldSkin:textField	Lcom/sun/javafx/scene/control/skin/InputFieldSkin$InnerTextField;
/*     */     //   102: invokevirtual 21	com/sun/javafx/scene/control/skin/InputFieldSkin$InnerTextField:textProperty	()Ljavafx/beans/property/StringProperty;
/*     */     //   105: new 22	com/sun/javafx/scene/control/skin/InputFieldSkin$3
/*     */     //   108: dup
/*     */     //   109: aload_0
/*     */     //   110: invokespecial 23	com/sun/javafx/scene/control/skin/InputFieldSkin$3:<init>	(Lcom/sun/javafx/scene/control/skin/InputFieldSkin;)V
/*     */     //   113: invokevirtual 24	javafx/beans/property/StringProperty:addListener	(Ljavafx/beans/InvalidationListener;)V
/*     */     //   116: aload_1
/*     */     //   117: invokevirtual 25	com/sun/javafx/scene/control/skin/InputField:focusedProperty	()Ljavafx/beans/property/ReadOnlyBooleanProperty;
/*     */     //   120: aload_0
/*     */     //   121: new 26	com/sun/javafx/scene/control/skin/InputFieldSkin$4
/*     */     //   124: dup
/*     */     //   125: aload_0
/*     */     //   126: aload_1
/*     */     //   127: invokespecial 27	com/sun/javafx/scene/control/skin/InputFieldSkin$4:<init>	(Lcom/sun/javafx/scene/control/skin/InputFieldSkin;Lcom/sun/javafx/scene/control/skin/InputField;)V
/*     */     //   130: dup_x1
/*     */     //   131: putfield 28	com/sun/javafx/scene/control/skin/InputFieldSkin:InputFieldFocusListener	Ljavafx/beans/InvalidationListener;
/*     */     //   134: invokevirtual 29	javafx/beans/property/ReadOnlyBooleanProperty:addListener	(Ljavafx/beans/InvalidationListener;)V
/*     */     //   137: aload_0
/*     */     //   138: invokevirtual 30	com/sun/javafx/scene/control/skin/InputFieldSkin:updateText	()V
/*     */     //   141: return
/*     */   }
/*     */ 
/*     */   public InputField getSkinnable()
/*     */   {
/* 126 */     return this.control;
/*     */   }
/*     */ 
/*     */   public Node getNode() {
/* 130 */     return this.textField;
/*     */   }
/*     */ 
/*     */   public void dispose()
/*     */   {
/* 143 */     this.control.getStyleClass().removeListener(this.InputFieldStyleClassListener);
/* 144 */     this.control.focusedProperty().removeListener(this.InputFieldFocusListener);
/* 145 */     this.textField = null;
/*     */   }
/*     */   protected abstract boolean accept(String paramString);
/*     */ 
/*     */   protected abstract void updateText();
/*     */ 
/*     */   protected abstract void updateValue();
/*     */ 
/* 153 */   protected TextField getTextField() { return this.textField; } 
/*     */   private class InnerTextField extends TextField {
/*     */     private InnerTextField() {
/*     */     }
/*     */     public void handleFocus(boolean paramBoolean) {
/* 158 */       setFocused(paramBoolean);
/*     */     }
/*     */ 
/*     */     public EventDispatchChain buildEventDispatchChain(EventDispatchChain paramEventDispatchChain) {
/* 162 */       EventDispatchChainImpl localEventDispatchChainImpl = new EventDispatchChainImpl();
/* 163 */       localEventDispatchChainImpl.append(InputFieldSkin.this.textField.getEventDispatcher());
/* 164 */       return localEventDispatchChainImpl;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.skin.InputFieldSkin
 * JD-Core Version:    0.6.2
 */