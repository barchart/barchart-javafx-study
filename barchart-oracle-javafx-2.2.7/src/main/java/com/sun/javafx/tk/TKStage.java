package com.sun.javafx.tk;

import java.security.AccessControlContext;
import java.util.List;

public abstract interface TKStage
{
  public abstract void setSecurityContext(AccessControlContext paramAccessControlContext);

  public abstract void setTKStageListener(TKStageListener paramTKStageListener);

  public abstract TKScene createTKScene(boolean paramBoolean);

  public abstract void setScene(TKScene paramTKScene);

  public abstract void setBounds(float paramFloat1, float paramFloat2, boolean paramBoolean1, boolean paramBoolean2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8);

  public abstract void setIcons(List paramList);

  public abstract void setTitle(String paramString);

  public abstract void setVisible(boolean paramBoolean);

  public abstract void setOpacity(float paramFloat);

  public abstract void setIconified(boolean paramBoolean);

  public abstract void setResizable(boolean paramBoolean);

  public abstract void setImportant(boolean paramBoolean);

  public abstract void setMinimumSize(int paramInt1, int paramInt2);

  public abstract void setMaximumSize(int paramInt1, int paramInt2);

  public abstract void setFullScreen(boolean paramBoolean);

  public abstract void requestFocus();

  public abstract void toBack();

  public abstract void toFront();

  public abstract void close();

  public abstract void requestFocus(FocusCause paramFocusCause);

  public abstract boolean grabFocus();

  public abstract void ungrabFocus();
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.TKStage
 * JD-Core Version:    0.6.2
 */