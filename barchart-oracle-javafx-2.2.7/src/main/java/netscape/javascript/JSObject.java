package netscape.javascript;

public abstract class JSObject
{
  public abstract Object call(String paramString, Object[] paramArrayOfObject)
    throws JSException;

  public abstract Object eval(String paramString)
    throws JSException;

  public abstract Object getMember(String paramString)
    throws JSException;

  public abstract void setMember(String paramString, Object paramObject)
    throws JSException;

  public abstract void removeMember(String paramString)
    throws JSException;

  public abstract Object getSlot(int paramInt)
    throws JSException;

  public abstract void setSlot(int paramInt, Object paramObject)
    throws JSException;
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     netscape.javascript.JSObject
 * JD-Core Version:    0.6.2
 */