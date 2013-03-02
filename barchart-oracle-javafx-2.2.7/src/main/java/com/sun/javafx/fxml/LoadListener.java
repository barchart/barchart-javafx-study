package com.sun.javafx.fxml;

public abstract interface LoadListener
{
  public abstract void readImportProcessingInstruction(String paramString);

  public abstract void readLanguageProcessingInstruction(String paramString);

  public abstract void readComment(String paramString);

  public abstract void beginInstanceDeclarationElement(Class<?> paramClass);

  public abstract void beginUnknownTypeElement(String paramString);

  public abstract void beginIncludeElement();

  public abstract void beginReferenceElement();

  public abstract void beginCopyElement();

  public abstract void beginRootElement();

  public abstract void beginPropertyElement(String paramString, Class<?> paramClass);

  public abstract void beginUnknownStaticPropertyElement(String paramString);

  public abstract void beginScriptElement();

  public abstract void beginDefineElement();

  public abstract void readInternalAttribute(String paramString1, String paramString2);

  public abstract void readPropertyAttribute(String paramString1, Class<?> paramClass, String paramString2);

  public abstract void readUnknownStaticPropertyAttribute(String paramString1, String paramString2);

  public abstract void readEventHandlerAttribute(String paramString1, String paramString2);

  public abstract void endElement(Object paramObject);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.fxml.LoadListener
 * JD-Core Version:    0.6.2
 */