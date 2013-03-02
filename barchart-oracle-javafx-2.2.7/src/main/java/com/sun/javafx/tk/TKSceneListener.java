package com.sun.javafx.tk;

import javafx.event.EventType;
import javafx.scene.input.RotateEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.SwipeEvent;
import javafx.scene.input.TouchPoint.State;
import javafx.scene.input.ZoomEvent;

public abstract interface TKSceneListener
{
  public abstract void changedLocation(float paramFloat1, float paramFloat2);

  public abstract void changedSize(float paramFloat1, float paramFloat2);

  public abstract void mouseEvent(Object paramObject);

  public abstract void keyEvent(Object paramObject);

  public abstract void inputMethodEvent(Object paramObject);

  public abstract void scrollEvent(EventType<ScrollEvent> paramEventType, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, double paramDouble7, double paramDouble8, double paramDouble9, double paramDouble10, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, boolean paramBoolean6);

  public abstract void menuEvent(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, boolean paramBoolean);

  public abstract void zoomEvent(EventType<ZoomEvent> paramEventType, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, boolean paramBoolean6);

  public abstract void rotateEvent(EventType<RotateEvent> paramEventType, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, boolean paramBoolean6);

  public abstract void swipeEvent(EventType<SwipeEvent> paramEventType, int paramInt, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5);

  public abstract void touchEventBegin(long paramLong, int paramInt, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5);

  public abstract void touchEventNext(TouchPoint.State paramState, long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public abstract void touchEventEnd();
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.TKSceneListener
 * JD-Core Version:    0.6.2
 */