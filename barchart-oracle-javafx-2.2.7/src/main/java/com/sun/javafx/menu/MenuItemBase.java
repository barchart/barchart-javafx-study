package com.sun.javafx.menu;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.KeyCombination;

public abstract interface MenuItemBase
{
  public abstract void setId(String paramString);

  public abstract String getId();

  public abstract StringProperty idProperty();

  public abstract void setText(String paramString);

  public abstract String getText();

  public abstract StringProperty textProperty();

  public abstract void setGraphic(Node paramNode);

  public abstract Node getGraphic();

  public abstract ObjectProperty<Node> graphicProperty();

  public abstract void setOnAction(EventHandler<ActionEvent> paramEventHandler);

  public abstract EventHandler<ActionEvent> getOnAction();

  public abstract ObjectProperty<EventHandler<ActionEvent>> onActionProperty();

  public abstract void setDisable(boolean paramBoolean);

  public abstract boolean isDisable();

  public abstract BooleanProperty disableProperty();

  public abstract void setVisible(boolean paramBoolean);

  public abstract boolean isVisible();

  public abstract BooleanProperty visibleProperty();

  public abstract void setAccelerator(KeyCombination paramKeyCombination);

  public abstract KeyCombination getAccelerator();

  public abstract ObjectProperty<KeyCombination> acceleratorProperty();

  public abstract void setMnemonicParsing(boolean paramBoolean);

  public abstract boolean isMnemonicParsing();

  public abstract BooleanProperty mnemonicParsingProperty();

  public abstract void fire();

  public abstract void fireValidation();
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.menu.MenuItemBase
 * JD-Core Version:    0.6.2
 */