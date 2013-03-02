package com.sun.browser.plugin;

import java.net.URL;

public abstract interface PluginHandler
{
  public abstract String getName();

  public abstract String getFileName();

  public abstract String getDescription();

  public abstract String[] supportedMIMETypes();

  public abstract boolean isSupportedMIMEType(String paramString);

  public abstract boolean isSupportedPlatform();

  public abstract Plugin createPlugin(URL paramURL, String paramString, String[] paramArrayOfString1, String[] paramArrayOfString2);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.browser.plugin.PluginHandler
 * JD-Core Version:    0.6.2
 */