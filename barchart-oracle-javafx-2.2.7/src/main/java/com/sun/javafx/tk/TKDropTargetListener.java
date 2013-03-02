package com.sun.javafx.tk;

import javafx.scene.input.TransferMode;

public abstract interface TKDropTargetListener
{
  public abstract TransferMode dragEnter(Object paramObject);

  public abstract TransferMode dragOver(Object paramObject);

  public abstract void dropActionChanged(Object paramObject);

  public abstract void dragExit(Object paramObject);

  public abstract TransferMode drop(Object paramObject);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.TKDropTargetListener
 * JD-Core Version:    0.6.2
 */