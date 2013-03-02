package com.sun.webpane.platform;

import java.net.URL;

public abstract interface PolicyClient
{
  public abstract boolean permitNavigateAction(long paramLong, URL paramURL);

  public abstract boolean permitRedirectAction(long paramLong, URL paramURL);

  public abstract boolean permitAcceptResourceAction(long paramLong, URL paramURL);

  public abstract boolean permitSubmitDataAction(long paramLong, URL paramURL, String paramString);

  public abstract boolean permitResubmitDataAction(long paramLong, URL paramURL, String paramString);

  public abstract boolean permitEnableScriptsAction(long paramLong, URL paramURL);

  public abstract boolean permitNewPageAction(long paramLong, URL paramURL);

  public abstract boolean permitClosePageAction(long paramLong);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.platform.PolicyClient
 * JD-Core Version:    0.6.2
 */