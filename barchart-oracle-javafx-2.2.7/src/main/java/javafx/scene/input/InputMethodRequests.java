package javafx.scene.input;

import javafx.geometry.Point2D;

public abstract interface InputMethodRequests
{
  public abstract Point2D getTextLocation(int paramInt);

  public abstract int getLocationOffset(int paramInt1, int paramInt2);

  public abstract void cancelLatestCommittedText();

  public abstract String getSelectedText();
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.input.InputMethodRequests
 * JD-Core Version:    0.6.2
 */