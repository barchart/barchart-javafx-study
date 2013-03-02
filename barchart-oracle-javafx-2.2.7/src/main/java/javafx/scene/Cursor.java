/*     */ package javafx.scene;
/*     */ 
/*     */ import com.sun.javafx.cursor.CursorFrame;
/*     */ import com.sun.javafx.cursor.CursorType;
/*     */ import com.sun.javafx.cursor.StandardCursorFrame;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import javafx.scene.image.Image;
/*     */ 
/*     */ public abstract class Cursor
/*     */ {
/*  42 */   public static final Cursor DEFAULT = new StandardCursor("DEFAULT", CursorType.DEFAULT);
/*     */ 
/*  48 */   public static final Cursor CROSSHAIR = new StandardCursor("CROSSHAIR", CursorType.CROSSHAIR);
/*     */ 
/*  54 */   public static final Cursor TEXT = new StandardCursor("TEXT", CursorType.TEXT);
/*     */ 
/*  60 */   public static final Cursor WAIT = new StandardCursor("WAIT", CursorType.WAIT);
/*     */ 
/*  66 */   public static final Cursor SW_RESIZE = new StandardCursor("SW_RESIZE", CursorType.SW_RESIZE);
/*     */ 
/*  72 */   public static final Cursor SE_RESIZE = new StandardCursor("SE_RESIZE", CursorType.SE_RESIZE);
/*     */ 
/*  78 */   public static final Cursor NW_RESIZE = new StandardCursor("NW_RESIZE", CursorType.NW_RESIZE);
/*     */ 
/*  84 */   public static final Cursor NE_RESIZE = new StandardCursor("NE_RESIZE", CursorType.NE_RESIZE);
/*     */ 
/*  90 */   public static final Cursor N_RESIZE = new StandardCursor("N_RESIZE", CursorType.N_RESIZE);
/*     */ 
/*  96 */   public static final Cursor S_RESIZE = new StandardCursor("S_RESIZE", CursorType.S_RESIZE);
/*     */ 
/* 102 */   public static final Cursor W_RESIZE = new StandardCursor("W_RESIZE", CursorType.W_RESIZE);
/*     */ 
/* 108 */   public static final Cursor E_RESIZE = new StandardCursor("E_RESIZE", CursorType.E_RESIZE);
/*     */ 
/* 114 */   public static final Cursor OPEN_HAND = new StandardCursor("OPEN_HAND", CursorType.OPEN_HAND);
/*     */ 
/* 121 */   public static final Cursor CLOSED_HAND = new StandardCursor("CLOSED_HAND", CursorType.CLOSED_HAND);
/*     */ 
/* 129 */   public static final Cursor HAND = new StandardCursor("HAND", CursorType.HAND);
/*     */ 
/* 135 */   public static final Cursor MOVE = new StandardCursor("MOVE", CursorType.MOVE);
/*     */ 
/* 144 */   public static final Cursor DISAPPEAR = new StandardCursor("DISAPPEAR", CursorType.DISAPPEAR);
/*     */ 
/* 150 */   public static final Cursor H_RESIZE = new StandardCursor("H_RESIZE", CursorType.H_RESIZE);
/*     */ 
/* 156 */   public static final Cursor V_RESIZE = new StandardCursor("V_RESIZE", CursorType.V_RESIZE);
/*     */ 
/* 163 */   public static final Cursor NONE = new StandardCursor("NONE", CursorType.NONE);
/*     */ 
/* 166 */   private String name = "CUSTOM";
/*     */ 
/*     */   Cursor() {
/*     */   }
/* 170 */   Cursor(String paramString) { this.name = paramString; }
/*     */ 
/*     */ 
/*     */   abstract CursorFrame getCurrentFrame();
/*     */ 
/*     */   void activate()
/*     */   {
/*     */   }
/*     */ 
/*     */   void deactivate()
/*     */   {
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 195 */     return this.name;
/*     */   }
/*     */ 
/*     */   public static Cursor cursor(String paramString)
/*     */   {
/* 212 */     if (paramString == null) {
/* 213 */       throw new NullPointerException("The cursor identifier must not be null");
/*     */     }
/*     */ 
/* 217 */     if (isUrl(paramString)) {
/* 218 */       return new ImageCursor(new Image(paramString));
/*     */     }
/*     */ 
/* 221 */     String str = paramString.toUpperCase();
/* 222 */     if (str.equals(DEFAULT.name))
/* 223 */       return DEFAULT;
/* 224 */     if (str.equals(CROSSHAIR.name))
/* 225 */       return CROSSHAIR;
/* 226 */     if (str.equals(TEXT.name))
/* 227 */       return TEXT;
/* 228 */     if (str.equals(WAIT.name))
/* 229 */       return WAIT;
/* 230 */     if (str.equals(MOVE.name))
/* 231 */       return MOVE;
/* 232 */     if (str.equals(SW_RESIZE.name))
/* 233 */       return SW_RESIZE;
/* 234 */     if (str.equals(SE_RESIZE.name))
/* 235 */       return SE_RESIZE;
/* 236 */     if (str.equals(NW_RESIZE.name))
/* 237 */       return NW_RESIZE;
/* 238 */     if (str.equals(NE_RESIZE.name))
/* 239 */       return NE_RESIZE;
/* 240 */     if (str.equals(N_RESIZE.name))
/* 241 */       return N_RESIZE;
/* 242 */     if (str.equals(S_RESIZE.name))
/* 243 */       return S_RESIZE;
/* 244 */     if (str.equals(W_RESIZE.name))
/* 245 */       return W_RESIZE;
/* 246 */     if (str.equals(E_RESIZE.name))
/* 247 */       return E_RESIZE;
/* 248 */     if (str.equals(OPEN_HAND.name))
/* 249 */       return OPEN_HAND;
/* 250 */     if (str.equals(CLOSED_HAND.name))
/* 251 */       return CLOSED_HAND;
/* 252 */     if (str.equals(HAND.name))
/* 253 */       return HAND;
/* 254 */     if (str.equals(H_RESIZE.name))
/* 255 */       return H_RESIZE;
/* 256 */     if (str.equals(V_RESIZE.name))
/* 257 */       return V_RESIZE;
/* 258 */     if (str.equals(DISAPPEAR.name))
/* 259 */       return DISAPPEAR;
/* 260 */     if (str.equals(NONE.name)) {
/* 261 */       return NONE;
/*     */     }
/*     */ 
/* 264 */     throw new IllegalArgumentException("Invalid cursor specification");
/*     */   }
/*     */ 
/*     */   private static boolean isUrl(String paramString) {
/*     */     try {
/* 269 */       new URL(paramString);
/*     */     } catch (MalformedURLException localMalformedURLException) {
/* 271 */       return false;
/*     */     }
/*     */ 
/* 274 */     return true;
/*     */   }
/*     */ 
/*     */   private static final class StandardCursor extends Cursor {
/*     */     private final CursorFrame singleFrame;
/*     */ 
/*     */     public StandardCursor(String paramString, CursorType paramCursorType) {
/* 281 */       super();
/* 282 */       this.singleFrame = new StandardCursorFrame(paramCursorType);
/*     */     }
/*     */ 
/*     */     CursorFrame getCurrentFrame()
/*     */     {
/* 287 */       return this.singleFrame;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.Cursor
 * JD-Core Version:    0.6.2
 */