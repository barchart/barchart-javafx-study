package javafx.beans.binding;

import java.util.Locale;
import javafx.beans.value.ObservableNumberValue;

public abstract interface NumberExpression extends ObservableNumberValue
{
  public abstract NumberBinding negate();

  public abstract NumberBinding add(ObservableNumberValue paramObservableNumberValue);

  public abstract NumberBinding add(double paramDouble);

  public abstract NumberBinding add(float paramFloat);

  public abstract NumberBinding add(long paramLong);

  public abstract NumberBinding add(int paramInt);

  public abstract NumberBinding subtract(ObservableNumberValue paramObservableNumberValue);

  public abstract NumberBinding subtract(double paramDouble);

  public abstract NumberBinding subtract(float paramFloat);

  public abstract NumberBinding subtract(long paramLong);

  public abstract NumberBinding subtract(int paramInt);

  public abstract NumberBinding multiply(ObservableNumberValue paramObservableNumberValue);

  public abstract NumberBinding multiply(double paramDouble);

  public abstract NumberBinding multiply(float paramFloat);

  public abstract NumberBinding multiply(long paramLong);

  public abstract NumberBinding multiply(int paramInt);

  public abstract NumberBinding divide(ObservableNumberValue paramObservableNumberValue);

  public abstract NumberBinding divide(double paramDouble);

  public abstract NumberBinding divide(float paramFloat);

  public abstract NumberBinding divide(long paramLong);

  public abstract NumberBinding divide(int paramInt);

  public abstract BooleanBinding isEqualTo(ObservableNumberValue paramObservableNumberValue);

  public abstract BooleanBinding isEqualTo(ObservableNumberValue paramObservableNumberValue, double paramDouble);

  public abstract BooleanBinding isEqualTo(double paramDouble1, double paramDouble2);

  public abstract BooleanBinding isEqualTo(float paramFloat, double paramDouble);

  public abstract BooleanBinding isEqualTo(long paramLong);

  public abstract BooleanBinding isEqualTo(long paramLong, double paramDouble);

  public abstract BooleanBinding isEqualTo(int paramInt);

  public abstract BooleanBinding isEqualTo(int paramInt, double paramDouble);

  public abstract BooleanBinding isNotEqualTo(ObservableNumberValue paramObservableNumberValue);

  public abstract BooleanBinding isNotEqualTo(ObservableNumberValue paramObservableNumberValue, double paramDouble);

  public abstract BooleanBinding isNotEqualTo(double paramDouble1, double paramDouble2);

  public abstract BooleanBinding isNotEqualTo(float paramFloat, double paramDouble);

  public abstract BooleanBinding isNotEqualTo(long paramLong);

  public abstract BooleanBinding isNotEqualTo(long paramLong, double paramDouble);

  public abstract BooleanBinding isNotEqualTo(int paramInt);

  public abstract BooleanBinding isNotEqualTo(int paramInt, double paramDouble);

  public abstract BooleanBinding greaterThan(ObservableNumberValue paramObservableNumberValue);

  public abstract BooleanBinding greaterThan(double paramDouble);

  public abstract BooleanBinding greaterThan(float paramFloat);

  public abstract BooleanBinding greaterThan(long paramLong);

  public abstract BooleanBinding greaterThan(int paramInt);

  public abstract BooleanBinding lessThan(ObservableNumberValue paramObservableNumberValue);

  public abstract BooleanBinding lessThan(double paramDouble);

  public abstract BooleanBinding lessThan(float paramFloat);

  public abstract BooleanBinding lessThan(long paramLong);

  public abstract BooleanBinding lessThan(int paramInt);

  public abstract BooleanBinding greaterThanOrEqualTo(ObservableNumberValue paramObservableNumberValue);

  public abstract BooleanBinding greaterThanOrEqualTo(double paramDouble);

  public abstract BooleanBinding greaterThanOrEqualTo(float paramFloat);

  public abstract BooleanBinding greaterThanOrEqualTo(long paramLong);

  public abstract BooleanBinding greaterThanOrEqualTo(int paramInt);

  public abstract BooleanBinding lessThanOrEqualTo(ObservableNumberValue paramObservableNumberValue);

  public abstract BooleanBinding lessThanOrEqualTo(double paramDouble);

  public abstract BooleanBinding lessThanOrEqualTo(float paramFloat);

  public abstract BooleanBinding lessThanOrEqualTo(long paramLong);

  public abstract BooleanBinding lessThanOrEqualTo(int paramInt);

  public abstract StringBinding asString();

  public abstract StringBinding asString(String paramString);

  public abstract StringBinding asString(Locale paramLocale, String paramString);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.binding.NumberExpression
 * JD-Core Version:    0.6.2
 */