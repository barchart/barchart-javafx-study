package com.sun.browser.plugin;

import com.sun.webpane.platform.graphics.WCGraphicsContext;
import java.io.IOException;

public abstract interface Plugin
{
  public static final int EVENT_BEFOREACTIVATE = -4;
  public static final int EVENT_FOCUSCHANGE = -1;

  public abstract void requestFocus();

  public abstract void setNativeContainerBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public abstract void activate(Object paramObject, PluginListener paramPluginListener);

  public abstract void destroy();

  public abstract void setVisible(boolean paramBoolean);

  public abstract void setEnabled(boolean paramBoolean);

  public abstract void setBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public abstract Object invoke(String paramString1, String paramString2, Object[] paramArrayOfObject)
    throws IOException;

  public abstract void paint(WCGraphicsContext paramWCGraphicsContext, int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public abstract boolean handleMouseEvent(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, long paramLong);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.browser.plugin.Plugin
 * JD-Core Version:    0.6.2
 */