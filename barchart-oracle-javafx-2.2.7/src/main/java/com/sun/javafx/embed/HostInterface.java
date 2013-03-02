package com.sun.javafx.embed;

import com.sun.javafx.cursor.CursorFrame;

public abstract interface HostInterface
{
  public abstract void setEmbeddedStage(EmbeddedStageInterface paramEmbeddedStageInterface);

  public abstract void setEmbeddedScene(EmbeddedSceneInterface paramEmbeddedSceneInterface);

  public abstract boolean requestFocus();

  public abstract boolean traverseFocusOut(boolean paramBoolean);

  public abstract void repaint();

  public abstract void setPreferredSize(int paramInt1, int paramInt2);

  public abstract void setEnabled(boolean paramBoolean);

  public abstract void setCursor(CursorFrame paramCursorFrame);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.embed.HostInterface
 * JD-Core Version:    0.6.2
 */