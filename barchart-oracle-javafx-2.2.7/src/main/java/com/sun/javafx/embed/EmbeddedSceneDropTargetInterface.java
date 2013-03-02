package com.sun.javafx.embed;

import javafx.scene.input.TransferMode;

public abstract interface EmbeddedSceneDropTargetInterface
{
  public abstract TransferMode handleDragEnter(int paramInt1, int paramInt2, int paramInt3, int paramInt4, TransferMode paramTransferMode, EmbeddedSceneDragSourceInterface paramEmbeddedSceneDragSourceInterface);

  public abstract void handleDragLeave();

  public abstract TransferMode handleDragDrop(int paramInt1, int paramInt2, int paramInt3, int paramInt4, TransferMode paramTransferMode);

  public abstract TransferMode handleDragOver(int paramInt1, int paramInt2, int paramInt3, int paramInt4, TransferMode paramTransferMode);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.embed.EmbeddedSceneDropTargetInterface
 * JD-Core Version:    0.6.2
 */