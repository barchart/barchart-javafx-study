package com.sun.webpane.platform;

import com.sun.webpane.platform.graphics.WCImageFrame;

public abstract interface Pasteboard
{
  public abstract String getPlainText();

  public abstract String getHtml();

  public abstract void writePlainText(String paramString);

  public abstract void writeSelection(boolean paramBoolean, String paramString1, String paramString2);

  public abstract void writeImage(WCImageFrame paramWCImageFrame);

  public abstract void writeUrl(String paramString1, String paramString2);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.platform.Pasteboard
 * JD-Core Version:    0.6.2
 */