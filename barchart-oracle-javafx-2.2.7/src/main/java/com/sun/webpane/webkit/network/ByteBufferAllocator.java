package com.sun.webpane.webkit.network;

import java.nio.ByteBuffer;

abstract interface ByteBufferAllocator
{
  public abstract ByteBuffer allocate()
    throws InterruptedException;

  public abstract void release(ByteBuffer paramByteBuffer);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.network.ByteBufferAllocator
 * JD-Core Version:    0.6.2
 */