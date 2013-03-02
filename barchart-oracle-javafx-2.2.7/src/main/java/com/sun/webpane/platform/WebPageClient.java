package com.sun.webpane.platform;

import com.sun.webpane.platform.graphics.WCPageBackBuffer;
import com.sun.webpane.platform.graphics.WCPoint;
import com.sun.webpane.platform.graphics.WCRectangle;

public abstract interface WebPageClient<T>
{
  public abstract void repaint(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean1, boolean paramBoolean2);

  public abstract void setCursor(long paramLong);

  public abstract void setFocus(boolean paramBoolean);

  public abstract void transferFocus(boolean paramBoolean);

  public abstract void setTooltip(String paramString);

  public abstract WCRectangle getScreenBounds(boolean paramBoolean);

  public abstract int getScreenDepth();

  public abstract WCPoint getLocationOnScreen();

  public abstract T getContainer();

  public abstract WCPoint screenToWindow(WCPoint paramWCPoint);

  public abstract WCPoint windowToScreen(WCPoint paramWCPoint);

  public abstract WCPageBackBuffer createBackBuffer();

  public abstract boolean isBackBufferSupported();
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.platform.WebPageClient
 * JD-Core Version:    0.6.2
 */