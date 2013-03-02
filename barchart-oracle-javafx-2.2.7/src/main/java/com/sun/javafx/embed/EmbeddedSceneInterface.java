package com.sun.javafx.embed;

import com.sun.javafx.scene.traversal.Direction;
import java.nio.IntBuffer;

public abstract interface EmbeddedSceneInterface
{
  public abstract void setSize(int paramInt1, int paramInt2);

  public abstract boolean getPixels(IntBuffer paramIntBuffer, int paramInt1, int paramInt2);

  public abstract void mouseEvent(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, boolean paramBoolean4, boolean paramBoolean5, boolean paramBoolean6, boolean paramBoolean7, int paramInt8, boolean paramBoolean8);

  public abstract void keyEvent(int paramInt1, int paramInt2, char[] paramArrayOfChar, int paramInt3);

  public abstract void menuEvent(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean);

  public abstract boolean traverseOut(Direction paramDirection);

  public abstract void setDragStartListener(EmbeddedSceneDragStartListenerInterface paramEmbeddedSceneDragStartListenerInterface);

  public abstract EmbeddedSceneDropTargetInterface createDropTarget();
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.embed.EmbeddedSceneInterface
 * JD-Core Version:    0.6.2
 */