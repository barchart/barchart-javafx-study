package com.sun.javafx.tk;

import java.util.Set;
import javafx.scene.input.DataFormat;
import javafx.scene.input.TransferMode;
import javafx.util.Pair;

public abstract interface TKClipboard
{
  public abstract Set<DataFormat> getContentTypes();

  public abstract boolean putContent(Pair<DataFormat, Object>[] paramArrayOfPair);

  public abstract Object getContent(DataFormat paramDataFormat);

  public abstract boolean hasContent(DataFormat paramDataFormat);

  public abstract Set<TransferMode> getTransferModes();

  public abstract void initSecurityContext();
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.TKClipboard
 * JD-Core Version:    0.6.2
 */