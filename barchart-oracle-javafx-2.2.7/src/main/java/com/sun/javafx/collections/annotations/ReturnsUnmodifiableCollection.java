package com.sun.javafx.collections.annotations;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.METHOD})
@Retention(RetentionPolicy.SOURCE)
public @interface ReturnsUnmodifiableCollection
{
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.collections.annotations.ReturnsUnmodifiableCollection
 * JD-Core Version:    0.6.2
 */