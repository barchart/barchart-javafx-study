package javafx.scene.control;

import javafx.scene.Node;

public abstract interface Skin<C extends Skinnable>
{
  public abstract C getSkinnable();

  public abstract Node getNode();

  public abstract void dispose();
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.Skin
 * JD-Core Version:    0.6.2
 */