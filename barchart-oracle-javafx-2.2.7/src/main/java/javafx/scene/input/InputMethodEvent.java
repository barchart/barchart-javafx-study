/*     */ package javafx.scene.input;
/*     */ 
/*     */ import javafx.collections.FXCollections;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.event.EventTarget;
/*     */ import javafx.event.EventType;
/*     */ 
/*     */ public class InputMethodEvent extends InputEvent
/*     */ {
/*  73 */   public static final EventType<InputMethodEvent> INPUT_METHOD_TEXT_CHANGED = new EventType(InputEvent.ANY, "INPUT_METHOD_TEXT_CHANGED");
/*     */   private ObservableList<InputMethodTextRun> composed;
/* 140 */   private String committed = "";
/*     */   private int caretPosition;
/*     */ 
/*     */   private InputMethodEvent(EventType<? extends InputMethodEvent> paramEventType)
/*     */   {
/*  77 */     super(paramEventType);
/*     */   }
/*     */ 
/*     */   private InputMethodEvent(Object paramObject, EventTarget paramEventTarget, EventType<? extends InputMethodEvent> paramEventType)
/*     */   {
/*  83 */     super(paramObject, paramEventTarget, paramEventType);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public static InputMethodEvent impl_copy(EventTarget paramEventTarget, InputMethodEvent paramInputMethodEvent)
/*     */   {
/*  93 */     return (InputMethodEvent)paramInputMethodEvent.copyFor(paramInputMethodEvent.source, paramEventTarget);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public static InputMethodEvent impl_inputMethodEvent(EventTarget paramEventTarget, ObservableList<InputMethodTextRun> paramObservableList, String paramString, int paramInt, EventType<? extends InputMethodEvent> paramEventType)
/*     */   {
/* 104 */     InputMethodEvent localInputMethodEvent = new InputMethodEvent(null, paramEventTarget, paramEventType);
/* 105 */     localInputMethodEvent.getComposed().addAll(paramObservableList);
/* 106 */     localInputMethodEvent.committed = paramString;
/* 107 */     localInputMethodEvent.caretPosition = paramInt;
/* 108 */     return localInputMethodEvent;
/*     */   }
/*     */ 
/*     */   public final ObservableList<InputMethodTextRun> getComposed()
/*     */   {
/* 128 */     if (this.composed == null) {
/* 129 */       this.composed = FXCollections.observableArrayList();
/*     */     }
/* 131 */     return this.composed;
/*     */   }
/*     */ 
/*     */   public final String getCommitted()
/*     */   {
/* 149 */     return this.committed;
/*     */   }
/*     */ 
/*     */   public final int getCaretPosition()
/*     */   {
/* 167 */     return this.caretPosition;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 175 */     StringBuilder localStringBuilder = new StringBuilder("InputMethodEvent [");
/*     */ 
/* 177 */     localStringBuilder.append("source = ").append(getSource());
/* 178 */     localStringBuilder.append(", target = ").append(getTarget());
/* 179 */     localStringBuilder.append(", eventType = ").append(getEventType());
/* 180 */     localStringBuilder.append(", consumed = ").append(isConsumed());
/*     */ 
/* 182 */     localStringBuilder.append(", composed = ").append(getComposed());
/* 183 */     localStringBuilder.append(", committed = ").append(getCommitted());
/* 184 */     localStringBuilder.append(", caretPosition = ").append(getCaretPosition());
/*     */ 
/* 186 */     return "]";
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.input.InputMethodEvent
 * JD-Core Version:    0.6.2
 */