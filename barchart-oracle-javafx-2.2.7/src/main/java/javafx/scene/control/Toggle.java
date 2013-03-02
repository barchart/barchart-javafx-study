package javafx.scene.control;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableMap;

public abstract interface Toggle
{
  public abstract ToggleGroup getToggleGroup();

  public abstract void setToggleGroup(ToggleGroup paramToggleGroup);

  public abstract ObjectProperty<ToggleGroup> toggleGroupProperty();

  public abstract boolean isSelected();

  public abstract void setSelected(boolean paramBoolean);

  public abstract BooleanProperty selectedProperty();

  public abstract Object getUserData();

  public abstract void setUserData(Object paramObject);

  public abstract ObservableMap<Object, Object> getProperties();
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.Toggle
 * JD-Core Version:    0.6.2
 */