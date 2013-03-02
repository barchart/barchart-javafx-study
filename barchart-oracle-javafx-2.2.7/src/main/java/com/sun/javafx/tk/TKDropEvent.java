package com.sun.javafx.tk;

import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

public abstract class TKDropEvent
{
  public abstract Dragboard getDragboard();

  public abstract void accept(TransferMode paramTransferMode);

  public abstract void reject();

  public abstract void dropComplete(boolean paramBoolean);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.TKDropEvent
 * JD-Core Version:    0.6.2
 */