package javafx.collections;

import java.util.Map;
import javafx.beans.Observable;

public abstract interface ObservableMap<K, V> extends Map<K, V>, Observable
{
  public abstract void addListener(MapChangeListener<? super K, ? super V> paramMapChangeListener);

  public abstract void removeListener(MapChangeListener<? super K, ? super V> paramMapChangeListener);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.collections.ObservableMap
 * JD-Core Version:    0.6.2
 */