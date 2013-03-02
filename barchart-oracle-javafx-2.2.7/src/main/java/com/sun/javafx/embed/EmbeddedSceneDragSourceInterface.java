package com.sun.javafx.embed;

import java.util.Set;
import javafx.scene.input.TransferMode;

public abstract interface EmbeddedSceneDragSourceInterface
{
  public abstract Set<TransferMode> getSupportedActions();

  public abstract Object getData(String paramString);

  public abstract String[] getMimeTypes();

  public abstract boolean isMimeTypeAvailable(String paramString);

  public abstract void dragDropEnd(TransferMode paramTransferMode);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.embed.EmbeddedSceneDragSourceInterface
 * JD-Core Version:    0.6.2
 */