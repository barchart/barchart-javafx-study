/*     */ package javafx.embed.swing;
/*     */ 
/*     */ import com.sun.javafx.embed.EmbeddedSceneDragSourceInterface;
/*     */ import com.sun.javafx.tk.Toolkit;
/*     */ import java.awt.datatransfer.Transferable;
/*     */ import java.awt.dnd.DropTargetDragEvent;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javafx.scene.input.TransferMode;
/*     */ 
/*     */ final class SwingDragSource
/*     */   implements EmbeddedSceneDragSourceInterface
/*     */ {
/*  24 */   private Map<String, Object> mimeType2Data = Collections.EMPTY_MAP;
/*     */   private int sourceActions;
/*     */   private Set<TransferMode> cachedTransferModes;
/*     */ 
/*     */   SwingDragSource(DropTargetDragEvent paramDropTargetDragEvent)
/*     */   {
/*  29 */     setContents(paramDropTargetDragEvent);
/*     */   }
/*     */ 
/*     */   void updateContents(DropTargetDragEvent paramDropTargetDragEvent) {
/*  33 */     updateSourceActions(paramDropTargetDragEvent.getSourceActions());
/*  34 */     updateData(paramDropTargetDragEvent.getTransferable());
/*     */   }
/*     */ 
/*     */   private void setContents(DropTargetDragEvent paramDropTargetDragEvent) {
/*  38 */     this.sourceActions = 0;
/*  39 */     this.cachedTransferModes = null;
/*  40 */     this.mimeType2Data = Collections.EMPTY_MAP;
/*  41 */     updateContents(paramDropTargetDragEvent);
/*     */   }
/*     */ 
/*     */   private void updateSourceActions(int paramInt) {
/*  45 */     if (paramInt != this.sourceActions) {
/*  46 */       this.sourceActions = paramInt;
/*  47 */       this.cachedTransferModes = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void updateData(Transferable paramTransferable) {
/*  52 */     Map localMap = DataFlavorUtils.adjustSwingDataFlavors(paramTransferable.getTransferDataFlavors());
/*     */ 
/*  55 */     if (localMap.keySet().equals(this.mimeType2Data.keySet()))
/*     */     {
/*  58 */       return;
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/*  91 */       this.mimeType2Data = DataFlavorUtils.readAllData(paramTransferable, localMap);
/*     */     } catch (Exception localException) {
/*  93 */       this.mimeType2Data = Collections.EMPTY_MAP;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Set<TransferMode> getSupportedActions()
/*     */   {
/*  99 */     assert (Toolkit.getToolkit().isFxUserThread());
/* 100 */     if (this.cachedTransferModes == null) {
/* 101 */       this.cachedTransferModes = SwingDnD.dropActionsToTransferModes(this.sourceActions);
/*     */     }
/*     */ 
/* 104 */     return this.cachedTransferModes;
/*     */   }
/*     */ 
/*     */   public Object getData(String paramString)
/*     */   {
/* 109 */     assert (Toolkit.getToolkit().isFxUserThread());
/* 110 */     return this.mimeType2Data.get(paramString);
/*     */   }
/*     */ 
/*     */   public String[] getMimeTypes()
/*     */   {
/* 115 */     assert (Toolkit.getToolkit().isFxUserThread());
/* 116 */     return (String[])this.mimeType2Data.keySet().toArray(new String[0]);
/*     */   }
/*     */ 
/*     */   public boolean isMimeTypeAvailable(String paramString)
/*     */   {
/* 121 */     assert (Toolkit.getToolkit().isFxUserThread());
/* 122 */     return Arrays.asList(getMimeTypes()).contains(paramString);
/*     */   }
/*     */ 
/*     */   public void dragDropEnd(TransferMode paramTransferMode)
/*     */   {
/* 127 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.embed.swing.SwingDragSource
 * JD-Core Version:    0.6.2
 */