package com.sun.webpane.webkit.network;

import java.net.URI;

public abstract interface CookieFilter
{
  public abstract boolean shouldAccept(Cookie paramCookie, URI paramURI);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.network.CookieFilter
 * JD-Core Version:    0.6.2
 */