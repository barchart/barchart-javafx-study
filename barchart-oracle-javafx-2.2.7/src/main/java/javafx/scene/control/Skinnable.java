package javafx.scene.control;

import javafx.beans.property.ObjectProperty;

public abstract interface Skinnable
{
  public abstract ObjectProperty<Skin<?>> skinProperty();

  public abstract void setSkin(Skin<?> paramSkin);

  public abstract Skin<?> getSkin();
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.Skinnable
 * JD-Core Version:    0.6.2
 */