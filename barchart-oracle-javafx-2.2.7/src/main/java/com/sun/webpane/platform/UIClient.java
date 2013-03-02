package com.sun.webpane.platform;

import com.sun.webpane.platform.graphics.WCImage;
import com.sun.webpane.platform.graphics.WCRectangle;

public abstract interface UIClient
{
  public abstract WebPage createPage(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4);

  public abstract void closePage();

  public abstract void showView();

  public abstract WCRectangle getViewBounds();

  public abstract void setViewBounds(WCRectangle paramWCRectangle);

  public abstract void setStatusbarText(String paramString);

  public abstract void alert(String paramString);

  public abstract boolean confirm(String paramString);

  public abstract String prompt(String paramString1, String paramString2);

  public abstract String chooseFile(String paramString);

  public abstract void print();

  public abstract void startDrag(WCImage paramWCImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, String[] paramArrayOfString, Object[] paramArrayOfObject);

  public abstract void confirmStartDrag();

  public abstract boolean isDragConfirmed();
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.platform.UIClient
 * JD-Core Version:    0.6.2
 */