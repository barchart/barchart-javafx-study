package com.sun.javafx.css;

abstract interface Property<T>
{
  public abstract void applyStyle(Stylesheet.Origin paramOrigin, T paramT);

  public abstract Stylesheet.Origin getOrigin();

  public abstract StyleableProperty getStyleableProperty();
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.css.Property
 * JD-Core Version:    0.6.2
 */