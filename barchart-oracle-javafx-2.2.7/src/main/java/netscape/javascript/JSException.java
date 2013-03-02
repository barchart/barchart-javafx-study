/*     */ package netscape.javascript;
/*     */ 
/*     */ public class JSException extends RuntimeException
/*     */ {
/*     */ 
/*     */   /** @deprecated */
/*     */   public static final int EXCEPTION_TYPE_EMPTY = -1;
/*     */ 
/*     */   /** @deprecated */
/*     */   public static final int EXCEPTION_TYPE_VOID = 0;
/*     */ 
/*     */   /** @deprecated */
/*     */   public static final int EXCEPTION_TYPE_OBJECT = 1;
/*     */ 
/*     */   /** @deprecated */
/*     */   public static final int EXCEPTION_TYPE_FUNCTION = 2;
/*     */ 
/*     */   /** @deprecated */
/*     */   public static final int EXCEPTION_TYPE_STRING = 3;
/*     */ 
/*     */   /** @deprecated */
/*     */   public static final int EXCEPTION_TYPE_NUMBER = 4;
/*     */ 
/*     */   /** @deprecated */
/*     */   public static final int EXCEPTION_TYPE_BOOLEAN = 5;
/*     */ 
/*     */   /** @deprecated */
/*     */   public static final int EXCEPTION_TYPE_ERROR = 6;
/*     */ 
/*     */   /** @deprecated */
/*  98 */   protected String message = null;
/*     */ 
/*     */   /** @deprecated */
/* 104 */   protected String filename = null;
/*     */ 
/*     */   /** @deprecated */
/* 110 */   protected int lineno = -1;
/*     */ 
/*     */   /** @deprecated */
/* 116 */   protected String source = null;
/*     */ 
/*     */   /** @deprecated */
/* 122 */   protected int tokenIndex = -1;
/*     */ 
/*     */   /** @deprecated */
/* 128 */   private int wrappedExceptionType = -1;
/*     */ 
/*     */   /** @deprecated */
/* 134 */   private Object wrappedException = null;
/*     */ 
/*     */   public JSException()
/*     */   {
/*  44 */     this(null);
/*     */   }
/*     */ 
/*     */   public JSException(String paramString)
/*     */   {
/*  54 */     this(paramString, null, -1, null, -1);
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public JSException(String paramString1, String paramString2, int paramInt1, String paramString3, int paramInt2)
/*     */   {
/*  71 */     super(paramString1);
/*  72 */     this.message = paramString1;
/*  73 */     this.filename = paramString2;
/*  74 */     this.lineno = paramInt1;
/*  75 */     this.source = paramString3;
/*  76 */     this.tokenIndex = paramInt2;
/*  77 */     this.wrappedExceptionType = -1;
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public JSException(int paramInt, Object paramObject)
/*     */   {
/*  89 */     this();
/*  90 */     this.wrappedExceptionType = paramInt;
/*  91 */     this.wrappedException = paramObject;
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public int getWrappedExceptionType()
/*     */   {
/* 145 */     return this.wrappedExceptionType;
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public Object getWrappedException()
/*     */   {
/* 156 */     return this.wrappedException;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     netscape.javascript.JSException
 * JD-Core Version:    0.6.2
 */