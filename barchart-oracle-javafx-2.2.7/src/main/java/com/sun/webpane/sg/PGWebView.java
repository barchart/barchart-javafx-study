package com.sun.webpane.sg;

import com.sun.javafx.sg.PGGroup;
import com.sun.webpane.platform.WebPage;

public abstract interface PGWebView extends PGGroup
{
  public abstract void setPage(WebPage paramWebPage);

  public abstract void resize(float paramFloat1, float paramFloat2);

  public abstract void update();

  public abstract void requestRender();
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.sg.PGWebView
 * JD-Core Version:    0.6.2
 */