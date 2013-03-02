package com.sun.javafx.tk;

import com.sun.javafx.geom.CameraImpl;
import com.sun.javafx.geom.PickRay;
import com.sun.javafx.sg.PGNode;
import java.security.AccessControlContext;
import javafx.scene.input.Dragboard;

public abstract interface TKScene
{
  public abstract void setSecurityContext(AccessControlContext paramAccessControlContext);

  public abstract void setTKSceneListener(TKSceneListener paramTKSceneListener);

  public abstract void setTKScenePaintListener(TKScenePaintListener paramTKScenePaintListener);

  public abstract void setScene(Object paramObject);

  public abstract void setRoot(PGNode paramPGNode);

  public abstract void markDirty();

  public abstract void setCamera(CameraImpl paramCameraImpl);

  public abstract PickRay computePickRay(float paramFloat1, float paramFloat2, PickRay paramPickRay);

  public abstract void setFillPaint(Object paramObject);

  public abstract void setCursor(Object paramObject);

  public abstract void requestFocus();

  public abstract void enableInputMethodEvents(boolean paramBoolean);

  public abstract void entireSceneNeedsRepaint();

  public abstract Dragboard createDragboard();
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.TKScene
 * JD-Core Version:    0.6.2
 */