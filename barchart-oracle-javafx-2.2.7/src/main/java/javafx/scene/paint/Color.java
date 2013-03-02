/*      */ package javafx.scene.paint;
/*      */ 
/*      */ import com.sun.javafx.Utils;
/*      */ import com.sun.javafx.tk.Toolkit;
/*      */ import java.util.HashMap;
/*      */ import java.util.Map;
/*      */ import javafx.animation.Interpolatable;
/*      */ 
/*      */ public class Color extends Paint
/*      */   implements Interpolatable<Color>
/*      */ {
/*      */   private static final double DARKER_BRIGHTER_FACTOR = 0.7D;
/*      */   private static final double SATURATE_DESATURATE_FACTOR = 0.7D;
/*      */   private static final int PARSE_COMPONENT = 0;
/*      */   private static final int PARSE_PERCENT = 1;
/*      */   private static final int PARSE_ANGLE = 2;
/*      */   private static final int PARSE_ALPHA = 3;
/*  771 */   public static final Color TRANSPARENT = new Color(0.0D, 0.0D, 0.0D, 0.0D);
/*      */ 
/*  776 */   public static final Color ALICEBLUE = new Color(0.9411765F, 0.972549F, 1.0F);
/*      */ 
/*  781 */   public static final Color ANTIQUEWHITE = new Color(0.9803922F, 0.9215686F, 0.8431373F);
/*      */ 
/*  786 */   public static final Color AQUA = new Color(0.0F, 1.0F, 1.0F);
/*      */ 
/*  791 */   public static final Color AQUAMARINE = new Color(0.4980392F, 1.0F, 0.8313726F);
/*      */ 
/*  796 */   public static final Color AZURE = new Color(0.9411765F, 1.0F, 1.0F);
/*      */ 
/*  801 */   public static final Color BEIGE = new Color(0.9607843F, 0.9607843F, 0.8627451F);
/*      */ 
/*  806 */   public static final Color BISQUE = new Color(1.0F, 0.8941177F, 0.7686275F);
/*      */ 
/*  811 */   public static final Color BLACK = new Color(0.0F, 0.0F, 0.0F);
/*      */ 
/*  816 */   public static final Color BLANCHEDALMOND = new Color(1.0F, 0.9215686F, 0.8039216F);
/*      */ 
/*  821 */   public static final Color BLUE = new Color(0.0F, 0.0F, 1.0F);
/*      */ 
/*  826 */   public static final Color BLUEVIOLET = new Color(0.541177F, 0.1686275F, 0.8862745F);
/*      */ 
/*  831 */   public static final Color BROWN = new Color(0.6470588F, 0.1647059F, 0.1647059F);
/*      */ 
/*  836 */   public static final Color BURLYWOOD = new Color(0.8705882F, 0.7215686F, 0.5294118F);
/*      */ 
/*  841 */   public static final Color CADETBLUE = new Color(0.372549F, 0.6196079F, 0.627451F);
/*      */ 
/*  846 */   public static final Color CHARTREUSE = new Color(0.4980392F, 1.0F, 0.0F);
/*      */ 
/*  851 */   public static final Color CHOCOLATE = new Color(0.8235294F, 0.4117647F, 0.1176471F);
/*      */ 
/*  856 */   public static final Color CORAL = new Color(1.0F, 0.4980392F, 0.313726F);
/*      */ 
/*  861 */   public static final Color CORNFLOWERBLUE = new Color(0.3921569F, 0.5843138F, 0.9294118F);
/*      */ 
/*  866 */   public static final Color CORNSILK = new Color(1.0F, 0.972549F, 0.8627451F);
/*      */ 
/*  871 */   public static final Color CRIMSON = new Color(0.8627451F, 0.07843138F, 0.2352941F);
/*      */ 
/*  876 */   public static final Color CYAN = new Color(0.0F, 1.0F, 1.0F);
/*      */ 
/*  881 */   public static final Color DARKBLUE = new Color(0.0F, 0.0F, 0.5450981F);
/*      */ 
/*  886 */   public static final Color DARKCYAN = new Color(0.0F, 0.5450981F, 0.5450981F);
/*      */ 
/*  891 */   public static final Color DARKGOLDENROD = new Color(0.7215686F, 0.5254902F, 0.04313726F);
/*      */ 
/*  896 */   public static final Color DARKGRAY = new Color(0.6627451F, 0.6627451F, 0.6627451F);
/*      */ 
/*  901 */   public static final Color DARKGREEN = new Color(0.0F, 0.3921569F, 0.0F);
/*      */ 
/*  906 */   public static final Color DARKGREY = DARKGRAY;
/*      */ 
/*  911 */   public static final Color DARKKHAKI = new Color(0.7411765F, 0.7176471F, 0.4196079F);
/*      */ 
/*  916 */   public static final Color DARKMAGENTA = new Color(0.5450981F, 0.0F, 0.5450981F);
/*      */ 
/*  921 */   public static final Color DARKOLIVEGREEN = new Color(0.3333333F, 0.4196079F, 0.1843137F);
/*      */ 
/*  926 */   public static final Color DARKORANGE = new Color(1.0F, 0.5490196F, 0.0F);
/*      */ 
/*  931 */   public static final Color DARKORCHID = new Color(0.6F, 0.1960784F, 0.8F);
/*      */ 
/*  936 */   public static final Color DARKRED = new Color(0.5450981F, 0.0F, 0.0F);
/*      */ 
/*  941 */   public static final Color DARKSALMON = new Color(0.913726F, 0.5882353F, 0.4784314F);
/*      */ 
/*  946 */   public static final Color DARKSEAGREEN = new Color(0.5607843F, 0.7372549F, 0.5607843F);
/*      */ 
/*  951 */   public static final Color DARKSLATEBLUE = new Color(0.282353F, 0.2392157F, 0.5450981F);
/*      */ 
/*  956 */   public static final Color DARKSLATEGRAY = new Color(0.1843137F, 0.3098039F, 0.3098039F);
/*      */ 
/*  961 */   public static final Color DARKSLATEGREY = DARKSLATEGRAY;
/*      */ 
/*  966 */   public static final Color DARKTURQUOISE = new Color(0.0F, 0.8078432F, 0.8196079F);
/*      */ 
/*  971 */   public static final Color DARKVIOLET = new Color(0.5803922F, 0.0F, 0.827451F);
/*      */ 
/*  976 */   public static final Color DEEPPINK = new Color(1.0F, 0.07843138F, 0.5764706F);
/*      */ 
/*  981 */   public static final Color DEEPSKYBLUE = new Color(0.0F, 0.7490196F, 1.0F);
/*      */ 
/*  986 */   public static final Color DIMGRAY = new Color(0.4117647F, 0.4117647F, 0.4117647F);
/*      */ 
/*  991 */   public static final Color DIMGREY = DIMGRAY;
/*      */ 
/*  996 */   public static final Color DODGERBLUE = new Color(0.1176471F, 0.5647059F, 1.0F);
/*      */ 
/* 1001 */   public static final Color FIREBRICK = new Color(0.6980392F, 0.1333333F, 0.1333333F);
/*      */ 
/* 1006 */   public static final Color FLORALWHITE = new Color(1.0F, 0.9803922F, 0.9411765F);
/*      */ 
/* 1011 */   public static final Color FORESTGREEN = new Color(0.1333333F, 0.5450981F, 0.1333333F);
/*      */ 
/* 1016 */   public static final Color FUCHSIA = new Color(1.0F, 0.0F, 1.0F);
/*      */ 
/* 1021 */   public static final Color GAINSBORO = new Color(0.8627451F, 0.8627451F, 0.8627451F);
/*      */ 
/* 1026 */   public static final Color GHOSTWHITE = new Color(0.972549F, 0.972549F, 1.0F);
/*      */ 
/* 1031 */   public static final Color GOLD = new Color(1.0F, 0.8431373F, 0.0F);
/*      */ 
/* 1036 */   public static final Color GOLDENROD = new Color(0.854902F, 0.6470588F, 0.12549F);
/*      */ 
/* 1041 */   public static final Color GRAY = new Color(0.5019608F, 0.5019608F, 0.5019608F);
/*      */ 
/* 1046 */   public static final Color GREEN = new Color(0.0F, 0.5019608F, 0.0F);
/*      */ 
/* 1051 */   public static final Color GREENYELLOW = new Color(0.6784314F, 1.0F, 0.1843137F);
/*      */ 
/* 1056 */   public static final Color GREY = GRAY;
/*      */ 
/* 1061 */   public static final Color HONEYDEW = new Color(0.9411765F, 1.0F, 0.9411765F);
/*      */ 
/* 1066 */   public static final Color HOTPINK = new Color(1.0F, 0.4117647F, 0.7058824F);
/*      */ 
/* 1071 */   public static final Color INDIANRED = new Color(0.8039216F, 0.3607843F, 0.3607843F);
/*      */ 
/* 1076 */   public static final Color INDIGO = new Color(0.2941177F, 0.0F, 0.509804F);
/*      */ 
/* 1081 */   public static final Color IVORY = new Color(1.0F, 1.0F, 0.9411765F);
/*      */ 
/* 1086 */   public static final Color KHAKI = new Color(0.9411765F, 0.9019608F, 0.5490196F);
/*      */ 
/* 1091 */   public static final Color LAVENDER = new Color(0.9019608F, 0.9019608F, 0.9803922F);
/*      */ 
/* 1096 */   public static final Color LAVENDERBLUSH = new Color(1.0F, 0.9411765F, 0.9607843F);
/*      */ 
/* 1101 */   public static final Color LAWNGREEN = new Color(0.4862745F, 0.9882353F, 0.0F);
/*      */ 
/* 1106 */   public static final Color LEMONCHIFFON = new Color(1.0F, 0.9803922F, 0.8039216F);
/*      */ 
/* 1111 */   public static final Color LIGHTBLUE = new Color(0.6784314F, 0.8470588F, 0.9019608F);
/*      */ 
/* 1116 */   public static final Color LIGHTCORAL = new Color(0.9411765F, 0.5019608F, 0.5019608F);
/*      */ 
/* 1121 */   public static final Color LIGHTCYAN = new Color(0.8784314F, 1.0F, 1.0F);
/*      */ 
/* 1126 */   public static final Color LIGHTGOLDENRODYELLOW = new Color(0.9803922F, 0.9803922F, 0.8235294F);
/*      */ 
/* 1131 */   public static final Color LIGHTGRAY = new Color(0.827451F, 0.827451F, 0.827451F);
/*      */ 
/* 1136 */   public static final Color LIGHTGREEN = new Color(0.5647059F, 0.9333333F, 0.5647059F);
/*      */ 
/* 1141 */   public static final Color LIGHTGREY = LIGHTGRAY;
/*      */ 
/* 1146 */   public static final Color LIGHTPINK = new Color(1.0F, 0.7137255F, 0.7568628F);
/*      */ 
/* 1151 */   public static final Color LIGHTSALMON = new Color(1.0F, 0.627451F, 0.4784314F);
/*      */ 
/* 1156 */   public static final Color LIGHTSEAGREEN = new Color(0.12549F, 0.6980392F, 0.6666667F);
/*      */ 
/* 1161 */   public static final Color LIGHTSKYBLUE = new Color(0.5294118F, 0.8078432F, 0.9803922F);
/*      */ 
/* 1166 */   public static final Color LIGHTSLATEGRAY = new Color(0.4666667F, 0.5333334F, 0.6F);
/*      */ 
/* 1171 */   public static final Color LIGHTSLATEGREY = LIGHTSLATEGRAY;
/*      */ 
/* 1176 */   public static final Color LIGHTSTEELBLUE = new Color(0.690196F, 0.7686275F, 0.8705882F);
/*      */ 
/* 1181 */   public static final Color LIGHTYELLOW = new Color(1.0F, 1.0F, 0.8784314F);
/*      */ 
/* 1186 */   public static final Color LIME = new Color(0.0F, 1.0F, 0.0F);
/*      */ 
/* 1191 */   public static final Color LIMEGREEN = new Color(0.1960784F, 0.8039216F, 0.1960784F);
/*      */ 
/* 1196 */   public static final Color LINEN = new Color(0.9803922F, 0.9411765F, 0.9019608F);
/*      */ 
/* 1201 */   public static final Color MAGENTA = new Color(1.0F, 0.0F, 1.0F);
/*      */ 
/* 1206 */   public static final Color MAROON = new Color(0.5019608F, 0.0F, 0.0F);
/*      */ 
/* 1211 */   public static final Color MEDIUMAQUAMARINE = new Color(0.4F, 0.8039216F, 0.6666667F);
/*      */ 
/* 1216 */   public static final Color MEDIUMBLUE = new Color(0.0F, 0.0F, 0.8039216F);
/*      */ 
/* 1221 */   public static final Color MEDIUMORCHID = new Color(0.7294118F, 0.3333333F, 0.827451F);
/*      */ 
/* 1226 */   public static final Color MEDIUMPURPLE = new Color(0.5764706F, 0.4392157F, 0.8588235F);
/*      */ 
/* 1231 */   public static final Color MEDIUMSEAGREEN = new Color(0.2352941F, 0.701961F, 0.4431373F);
/*      */ 
/* 1236 */   public static final Color MEDIUMSLATEBLUE = new Color(0.4823529F, 0.4078431F, 0.9333333F);
/*      */ 
/* 1241 */   public static final Color MEDIUMSPRINGGREEN = new Color(0.0F, 0.9803922F, 0.6039216F);
/*      */ 
/* 1246 */   public static final Color MEDIUMTURQUOISE = new Color(0.282353F, 0.8196079F, 0.8F);
/*      */ 
/* 1251 */   public static final Color MEDIUMVIOLETRED = new Color(0.7803922F, 0.08235294F, 0.5215687F);
/*      */ 
/* 1256 */   public static final Color MIDNIGHTBLUE = new Color(0.09803922F, 0.09803922F, 0.4392157F);
/*      */ 
/* 1261 */   public static final Color MINTCREAM = new Color(0.9607843F, 1.0F, 0.9803922F);
/*      */ 
/* 1266 */   public static final Color MISTYROSE = new Color(1.0F, 0.8941177F, 0.882353F);
/*      */ 
/* 1271 */   public static final Color MOCCASIN = new Color(1.0F, 0.8941177F, 0.7098039F);
/*      */ 
/* 1276 */   public static final Color NAVAJOWHITE = new Color(1.0F, 0.8705882F, 0.6784314F);
/*      */ 
/* 1281 */   public static final Color NAVY = new Color(0.0F, 0.0F, 0.5019608F);
/*      */ 
/* 1286 */   public static final Color OLDLACE = new Color(0.9921569F, 0.9607843F, 0.9019608F);
/*      */ 
/* 1291 */   public static final Color OLIVE = new Color(0.5019608F, 0.5019608F, 0.0F);
/*      */ 
/* 1296 */   public static final Color OLIVEDRAB = new Color(0.4196079F, 0.5568628F, 0.1372549F);
/*      */ 
/* 1301 */   public static final Color ORANGE = new Color(1.0F, 0.6470588F, 0.0F);
/*      */ 
/* 1306 */   public static final Color ORANGERED = new Color(1.0F, 0.2705883F, 0.0F);
/*      */ 
/* 1311 */   public static final Color ORCHID = new Color(0.854902F, 0.4392157F, 0.839216F);
/*      */ 
/* 1316 */   public static final Color PALEGOLDENROD = new Color(0.9333333F, 0.9098039F, 0.6666667F);
/*      */ 
/* 1321 */   public static final Color PALEGREEN = new Color(0.5960785F, 0.9843137F, 0.5960785F);
/*      */ 
/* 1326 */   public static final Color PALETURQUOISE = new Color(0.6862745F, 0.9333333F, 0.9333333F);
/*      */ 
/* 1331 */   public static final Color PALEVIOLETRED = new Color(0.8588235F, 0.4392157F, 0.5764706F);
/*      */ 
/* 1336 */   public static final Color PAPAYAWHIP = new Color(1.0F, 0.9372549F, 0.8352941F);
/*      */ 
/* 1341 */   public static final Color PEACHPUFF = new Color(1.0F, 0.854902F, 0.7254902F);
/*      */ 
/* 1346 */   public static final Color PERU = new Color(0.8039216F, 0.5215687F, 0.2470588F);
/*      */ 
/* 1351 */   public static final Color PINK = new Color(1.0F, 0.7529412F, 0.7960784F);
/*      */ 
/* 1356 */   public static final Color PLUM = new Color(0.8666667F, 0.627451F, 0.8666667F);
/*      */ 
/* 1361 */   public static final Color POWDERBLUE = new Color(0.690196F, 0.8784314F, 0.9019608F);
/*      */ 
/* 1366 */   public static final Color PURPLE = new Color(0.5019608F, 0.0F, 0.5019608F);
/*      */ 
/* 1371 */   public static final Color RED = new Color(1.0F, 0.0F, 0.0F);
/*      */ 
/* 1376 */   public static final Color ROSYBROWN = new Color(0.7372549F, 0.5607843F, 0.5607843F);
/*      */ 
/* 1381 */   public static final Color ROYALBLUE = new Color(0.254902F, 0.4117647F, 0.882353F);
/*      */ 
/* 1386 */   public static final Color SADDLEBROWN = new Color(0.5450981F, 0.2705883F, 0.07450981F);
/*      */ 
/* 1391 */   public static final Color SALMON = new Color(0.9803922F, 0.5019608F, 0.4470588F);
/*      */ 
/* 1396 */   public static final Color SANDYBROWN = new Color(0.9568628F, 0.6431373F, 0.376471F);
/*      */ 
/* 1401 */   public static final Color SEAGREEN = new Color(0.1803922F, 0.5450981F, 0.3411765F);
/*      */ 
/* 1406 */   public static final Color SEASHELL = new Color(1.0F, 0.9607843F, 0.9333333F);
/*      */ 
/* 1411 */   public static final Color SIENNA = new Color(0.627451F, 0.3215686F, 0.1764706F);
/*      */ 
/* 1416 */   public static final Color SILVER = new Color(0.7529412F, 0.7529412F, 0.7529412F);
/*      */ 
/* 1421 */   public static final Color SKYBLUE = new Color(0.5294118F, 0.8078432F, 0.9215686F);
/*      */ 
/* 1426 */   public static final Color SLATEBLUE = new Color(0.4156863F, 0.3529412F, 0.8039216F);
/*      */ 
/* 1431 */   public static final Color SLATEGRAY = new Color(0.4392157F, 0.5019608F, 0.5647059F);
/*      */ 
/* 1436 */   public static final Color SLATEGREY = SLATEGRAY;
/*      */ 
/* 1441 */   public static final Color SNOW = new Color(1.0F, 0.9803922F, 0.9803922F);
/*      */ 
/* 1446 */   public static final Color SPRINGGREEN = new Color(0.0F, 1.0F, 0.4980392F);
/*      */ 
/* 1451 */   public static final Color STEELBLUE = new Color(0.2745098F, 0.509804F, 0.7058824F);
/*      */ 
/* 1456 */   public static final Color TAN = new Color(0.8235294F, 0.7058824F, 0.5490196F);
/*      */ 
/* 1461 */   public static final Color TEAL = new Color(0.0F, 0.5019608F, 0.5019608F);
/*      */ 
/* 1466 */   public static final Color THISTLE = new Color(0.8470588F, 0.7490196F, 0.8470588F);
/*      */ 
/* 1471 */   public static final Color TOMATO = new Color(1.0F, 0.388235F, 0.2784314F);
/*      */ 
/* 1476 */   public static final Color TURQUOISE = new Color(0.2509804F, 0.8784314F, 0.8156863F);
/*      */ 
/* 1481 */   public static final Color VIOLET = new Color(0.9333333F, 0.509804F, 0.9333333F);
/*      */ 
/* 1486 */   public static final Color WHEAT = new Color(0.9607843F, 0.8705882F, 0.701961F);
/*      */ 
/* 1491 */   public static final Color WHITE = new Color(1.0F, 1.0F, 1.0F);
/*      */ 
/* 1496 */   public static final Color WHITESMOKE = new Color(0.9607843F, 0.9607843F, 0.9607843F);
/*      */ 
/* 1501 */   public static final Color YELLOW = new Color(1.0F, 1.0F, 0.0F);
/*      */ 
/* 1506 */   public static final Color YELLOWGREEN = new Color(0.6039216F, 0.8039216F, 0.1960784F);
/*      */   private float red;
/*      */   private float green;
/*      */   private float blue;
/* 1709 */   private float opacity = 1.0F;
/*      */   private Object platformPaint;
/*      */ 
/*      */   public static Color color(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*      */   {
/*  131 */     return new Color(paramDouble1, paramDouble2, paramDouble3, paramDouble4);
/*      */   }
/*      */ 
/*      */   public static Color color(double paramDouble1, double paramDouble2, double paramDouble3)
/*      */   {
/*  145 */     return new Color(paramDouble1, paramDouble2, paramDouble3, 1.0D);
/*      */   }
/*      */ 
/*      */   public static Color rgb(int paramInt1, int paramInt2, int paramInt3, double paramDouble)
/*      */   {
/*  160 */     checkRGB(paramInt1, paramInt2, paramInt3);
/*  161 */     return new Color(paramInt1 / 255.0D, paramInt2 / 255.0D, paramInt3 / 255.0D, paramDouble);
/*      */   }
/*      */ 
/*      */   public static Color rgb(int paramInt1, int paramInt2, int paramInt3)
/*      */   {
/*  178 */     checkRGB(paramInt1, paramInt2, paramInt3);
/*  179 */     return new Color(paramInt1 / 255.0D, paramInt2 / 255.0D, paramInt3 / 255.0D, 1.0D);
/*      */   }
/*      */ 
/*      */   public static Color grayRgb(int paramInt)
/*      */   {
/*  191 */     return rgb(paramInt, paramInt, paramInt);
/*      */   }
/*      */ 
/*      */   public static Color grayRgb(int paramInt, double paramDouble)
/*      */   {
/*  198 */     return rgb(paramInt, paramInt, paramInt, paramDouble);
/*      */   }
/*      */ 
/*      */   public static Color gray(double paramDouble1, double paramDouble2)
/*      */   {
/*  210 */     return new Color(paramDouble1, paramDouble1, paramDouble1, paramDouble2);
/*      */   }
/*      */ 
/*      */   public static Color gray(double paramDouble)
/*      */   {
/*  221 */     return gray(paramDouble, 1.0D);
/*      */   }
/*      */ 
/*      */   private static void checkRGB(int paramInt1, int paramInt2, int paramInt3) {
/*  225 */     if ((paramInt1 < 0) || (paramInt1 > 255)) {
/*  226 */       throw new IllegalArgumentException("Color.rgb's red parameter (" + paramInt1 + ") expects color values 0-255");
/*      */     }
/*  228 */     if ((paramInt2 < 0) || (paramInt2 > 255)) {
/*  229 */       throw new IllegalArgumentException("Color.rgb's green parameter (" + paramInt2 + ") expects color values 0-255");
/*      */     }
/*  231 */     if ((paramInt3 < 0) || (paramInt3 > 255))
/*  232 */       throw new IllegalArgumentException("Color.rgb's blue parameter (" + paramInt3 + ") expects color values 0-255");
/*      */   }
/*      */ 
/*      */   public static Color hsb(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*      */   {
/*  249 */     checkSB(paramDouble2, paramDouble3);
/*  250 */     double[] arrayOfDouble = Utils.HSBtoRGB(paramDouble1, paramDouble2, paramDouble3);
/*  251 */     Color localColor = new Color(arrayOfDouble[0], arrayOfDouble[1], arrayOfDouble[2], paramDouble4);
/*  252 */     return localColor;
/*      */   }
/*      */ 
/*      */   public static Color hsb(double paramDouble1, double paramDouble2, double paramDouble3)
/*      */   {
/*  266 */     return hsb(paramDouble1, paramDouble2, paramDouble3, 1.0D);
/*      */   }
/*      */ 
/*      */   private static void checkSB(double paramDouble1, double paramDouble2) {
/*  270 */     if ((paramDouble1 < 0.0D) || (paramDouble1 > 1.0D)) {
/*  271 */       throw new IllegalArgumentException("Color.hsb's saturation parameter (" + paramDouble1 + ") expects values 0.0-1.0");
/*      */     }
/*  273 */     if ((paramDouble2 < 0.0D) || (paramDouble2 > 1.0D))
/*  274 */       throw new IllegalArgumentException("Color.hsb's brightness parameter (" + paramDouble2 + ") expects values 0.0-1.0");
/*      */   }
/*      */ 
/*      */   public static Color web(String paramString, double paramDouble)
/*      */   {
/*  384 */     if (paramString == null) {
/*  385 */       throw new NullPointerException("The color components or name must be specified");
/*      */     }
/*      */ 
/*  388 */     if (paramString.isEmpty()) {
/*  389 */       throw new IllegalArgumentException("Invalid color specification");
/*      */     }
/*      */ 
/*  392 */     String str = paramString.toLowerCase();
/*      */ 
/*  394 */     if (str.startsWith("#")) {
/*  395 */       str = str.substring(1);
/*  396 */     } else if (str.startsWith("0x")) {
/*  397 */       str = str.substring(2);
/*  398 */     } else if (str.startsWith("rgb")) {
/*  399 */       if (str.startsWith("(", 3))
/*  400 */         return parseRGBColor(str, 4, false, paramDouble);
/*  401 */       if (str.startsWith("a(", 3))
/*  402 */         return parseRGBColor(str, 5, true, paramDouble);
/*      */     }
/*  404 */     else if (str.startsWith("hsl")) {
/*  405 */       if (str.startsWith("(", 3))
/*  406 */         return parseHSLColor(str, 4, false, paramDouble);
/*  407 */       if (str.startsWith("a(", 3))
/*  408 */         return parseHSLColor(str, 5, true, paramDouble);
/*      */     }
/*      */     else {
/*  411 */       Color localColor = NamedColors.get(str);
/*  412 */       if (localColor != null) {
/*  413 */         if (paramDouble == 1.0D) {
/*  414 */           return localColor;
/*      */         }
/*  416 */         return color(localColor.red, localColor.green, localColor.blue, paramDouble);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  421 */     int i = str.length();
/*      */     try
/*      */     {
/*      */       int j;
/*      */       int k;
/*      */       int m;
/*  429 */       if (i == 3) {
/*  430 */         j = Integer.parseInt(str.substring(0, 1), 16);
/*  431 */         k = Integer.parseInt(str.substring(1, 2), 16);
/*  432 */         m = Integer.parseInt(str.substring(2, 3), 16);
/*  433 */         return color(j / 15.0D, k / 15.0D, m / 15.0D, paramDouble);
/*      */       }
/*      */       int n;
/*  434 */       if (i == 4) {
/*  435 */         j = Integer.parseInt(str.substring(0, 1), 16);
/*  436 */         k = Integer.parseInt(str.substring(1, 2), 16);
/*  437 */         m = Integer.parseInt(str.substring(2, 3), 16);
/*  438 */         n = Integer.parseInt(str.substring(3, 4), 16);
/*  439 */         return color(j / 15.0D, k / 15.0D, m / 15.0D, paramDouble * n / 15.0D);
/*      */       }
/*  441 */       if (i == 6) {
/*  442 */         j = Integer.parseInt(str.substring(0, 2), 16);
/*  443 */         k = Integer.parseInt(str.substring(2, 4), 16);
/*  444 */         m = Integer.parseInt(str.substring(4, 6), 16);
/*  445 */         return rgb(j, k, m, paramDouble);
/*  446 */       }if (i == 8) {
/*  447 */         j = Integer.parseInt(str.substring(0, 2), 16);
/*  448 */         k = Integer.parseInt(str.substring(2, 4), 16);
/*  449 */         m = Integer.parseInt(str.substring(4, 6), 16);
/*  450 */         n = Integer.parseInt(str.substring(6, 8), 16);
/*  451 */         return rgb(j, k, m, paramDouble * n / 255.0D);
/*      */       }
/*      */     } catch (NumberFormatException localNumberFormatException) {
/*      */     }
/*  455 */     throw new IllegalArgumentException("Invalid color specification");
/*      */   }
/*      */ 
/*      */   private static Color parseRGBColor(String paramString, int paramInt, boolean paramBoolean, double paramDouble)
/*      */   {
/*      */     try
/*      */     {
/*  462 */       int i = paramString.indexOf(',', paramInt);
/*  463 */       int j = i < 0 ? -1 : paramString.indexOf(',', i + 1);
/*  464 */       int k = j < 0 ? -1 : paramString.indexOf(paramBoolean ? 44 : ')', j + 1);
/*  465 */       int m = paramBoolean ? paramString.indexOf(')', k + 1) : k < 0 ? -1 : k;
/*  466 */       if (m >= 0) {
/*  467 */         double d1 = parseComponent(paramString, paramInt, i, 0);
/*  468 */         double d2 = parseComponent(paramString, i + 1, j, 0);
/*  469 */         double d3 = parseComponent(paramString, j + 1, k, 0);
/*  470 */         if (paramBoolean) {
/*  471 */           paramDouble *= parseComponent(paramString, k + 1, m, 3);
/*      */         }
/*  473 */         return new Color(d1, d2, d3, paramDouble);
/*      */       }
/*      */     } catch (NumberFormatException localNumberFormatException) {
/*      */     }
/*  477 */     throw new IllegalArgumentException("Invalid color specification");
/*      */   }
/*      */ 
/*      */   private static Color parseHSLColor(String paramString, int paramInt, boolean paramBoolean, double paramDouble)
/*      */   {
/*      */     try
/*      */     {
/*  484 */       int i = paramString.indexOf(',', paramInt);
/*  485 */       int j = i < 0 ? -1 : paramString.indexOf(',', i + 1);
/*  486 */       int k = j < 0 ? -1 : paramString.indexOf(paramBoolean ? 44 : ')', j + 1);
/*  487 */       int m = paramBoolean ? paramString.indexOf(')', k + 1) : k < 0 ? -1 : k;
/*  488 */       if (m >= 0) {
/*  489 */         double d1 = parseComponent(paramString, paramInt, i, 2);
/*  490 */         double d2 = parseComponent(paramString, i + 1, j, 1);
/*  491 */         double d3 = parseComponent(paramString, j + 1, k, 1);
/*  492 */         if (paramBoolean) {
/*  493 */           paramDouble *= parseComponent(paramString, k + 1, m, 3);
/*      */         }
/*  495 */         return hsb(d1, d2, d3, paramDouble);
/*      */       }
/*      */     } catch (NumberFormatException localNumberFormatException) {
/*      */     }
/*  499 */     throw new IllegalArgumentException("Invalid color specification");
/*      */   }
/*      */ 
/*      */   private static double parseComponent(String paramString, int paramInt1, int paramInt2, int paramInt3)
/*      */   {
/*  507 */     paramString = paramString.substring(paramInt1, paramInt2).trim();
/*  508 */     if (paramString.endsWith("%")) {
/*  509 */       if (paramInt3 > 1) {
/*  510 */         throw new IllegalArgumentException("Invalid color specification");
/*      */       }
/*  512 */       paramInt3 = 1;
/*  513 */       paramString = paramString.substring(0, paramString.length() - 1).trim();
/*  514 */     } else if (paramInt3 == 1) {
/*  515 */       throw new IllegalArgumentException("Invalid color specification");
/*      */     }
/*  517 */     double d = paramInt3 == 0 ? Integer.parseInt(paramString) : Double.parseDouble(paramString);
/*      */ 
/*  520 */     switch (paramInt3) {
/*      */     case 3:
/*  522 */       return d > 1.0D ? 1.0D : d < 0.0D ? 0.0D : d;
/*      */     case 1:
/*  524 */       return d >= 100.0D ? 1.0D : d <= 0.0D ? 0.0D : d / 100.0D;
/*      */     case 0:
/*  526 */       return d >= 255.0D ? 1.0D : d <= 0.0D ? 0.0D : d / 255.0D;
/*      */     case 2:
/*  528 */       return d > 360.0D ? d % 360.0D : d < 0.0D ? d % 360.0D + 360.0D : d;
/*      */     }
/*      */ 
/*  535 */     throw new IllegalArgumentException("Invalid color specification");
/*      */   }
/*      */ 
/*      */   public static Color web(String paramString)
/*      */   {
/*  636 */     return web(paramString, 1.0D);
/*      */   }
/*      */ 
/*      */   public static Color valueOf(String paramString)
/*      */   {
/*  652 */     if (paramString == null) {
/*  653 */       throw new NullPointerException("color must be specified");
/*      */     }
/*      */ 
/*  656 */     return web(paramString);
/*      */   }
/*      */ 
/*      */   private static int to32BitInteger(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  660 */     int i = paramInt1;
/*  661 */     i <<= 8;
/*  662 */     i |= paramInt2;
/*  663 */     i <<= 8;
/*  664 */     i |= paramInt3;
/*  665 */     i <<= 8;
/*  666 */     i |= paramInt4;
/*  667 */     return i;
/*      */   }
/*      */ 
/*      */   public double getHue()
/*      */   {
/*  675 */     return Utils.RGBtoHSB(this.red, this.green, this.blue)[0];
/*      */   }
/*      */ 
/*      */   public double getSaturation()
/*      */   {
/*  683 */     return Utils.RGBtoHSB(this.red, this.green, this.blue)[1];
/*      */   }
/*      */ 
/*      */   public double getBrightness()
/*      */   {
/*  691 */     return Utils.RGBtoHSB(this.red, this.green, this.blue)[2];
/*      */   }
/*      */ 
/*      */   public Color deriveColor(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*      */   {
/*  707 */     double[] arrayOfDouble = Utils.RGBtoHSB(this.red, this.green, this.blue);
/*      */ 
/*  710 */     double d1 = arrayOfDouble[2];
/*  711 */     if ((d1 == 0.0D) && (paramDouble3 > 1.0D)) {
/*  712 */       d1 = 0.05D;
/*      */     }
/*      */ 
/*  716 */     double d2 = ((arrayOfDouble[0] + paramDouble1) % 360.0D + 360.0D) % 360.0D;
/*  717 */     double d3 = Math.max(Math.min(arrayOfDouble[1] * paramDouble2, 1.0D), 0.0D);
/*  718 */     d1 = Math.max(Math.min(d1 * paramDouble3, 1.0D), 0.0D);
/*  719 */     double d4 = Math.max(Math.min(this.opacity * paramDouble4, 1.0D), 0.0D);
/*  720 */     return hsb(d2, d3, d1, d4);
/*      */   }
/*      */ 
/*      */   public Color brighter()
/*      */   {
/*  727 */     return deriveColor(0.0D, 1.0D, 1.428571428571429D, 1.0D);
/*      */   }
/*      */ 
/*      */   public Color darker()
/*      */   {
/*  734 */     return deriveColor(0.0D, 1.0D, 0.7D, 1.0D);
/*      */   }
/*      */ 
/*      */   public Color saturate()
/*      */   {
/*  741 */     return deriveColor(0.0D, 1.428571428571429D, 1.0D, 1.0D);
/*      */   }
/*      */ 
/*      */   public Color desaturate()
/*      */   {
/*  748 */     return deriveColor(0.0D, 0.7D, 1.0D, 1.0D);
/*      */   }
/*      */ 
/*      */   public Color grayscale()
/*      */   {
/*  756 */     double d = 0.21D * this.red + 0.71D * this.green + 0.07000000000000001D * this.blue;
/*  757 */     return color(d, d, d, this.opacity);
/*      */   }
/*      */ 
/*      */   public Color invert()
/*      */   {
/*  765 */     return color(1.0D - this.red, 1.0D - this.green, 1.0D - this.blue, this.opacity);
/*      */   }
/*      */ 
/*      */   public final double getRed()
/*      */   {
/* 1684 */     return this.red;
/*      */   }
/*      */ 
/*      */   public final double getGreen()
/*      */   {
/* 1692 */     return this.green;
/*      */   }
/*      */ 
/*      */   public final double getBlue()
/*      */   {
/* 1700 */     return this.blue;
/*      */   }
/*      */ 
/*      */   public final double getOpacity()
/*      */   {
/* 1708 */     return this.opacity;
/*      */   }
/*      */ 
/*      */   public Color(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*      */   {
/* 1721 */     if ((paramDouble1 < 0.0D) || (paramDouble1 > 1.0D)) {
/* 1722 */       throw new IllegalArgumentException("Color's red value (" + paramDouble1 + ") must be in the range 0.0-1.0");
/*      */     }
/* 1724 */     if ((paramDouble2 < 0.0D) || (paramDouble2 > 1.0D)) {
/* 1725 */       throw new IllegalArgumentException("Color's green value (" + paramDouble2 + ") must be in the range 0.0-1.0");
/*      */     }
/* 1727 */     if ((paramDouble3 < 0.0D) || (paramDouble3 > 1.0D)) {
/* 1728 */       throw new IllegalArgumentException("Color's blue value (" + paramDouble3 + ") must be in the range 0.0-1.0");
/*      */     }
/* 1730 */     if ((paramDouble4 < 0.0D) || (paramDouble4 > 1.0D)) {
/* 1731 */       throw new IllegalArgumentException("Color's opacity value (" + paramDouble4 + ") must be in the range 0.0-1.0");
/*      */     }
/*      */ 
/* 1734 */     this.red = ((float)paramDouble1);
/* 1735 */     this.green = ((float)paramDouble2);
/* 1736 */     this.blue = ((float)paramDouble3);
/* 1737 */     this.opacity = ((float)paramDouble4);
/*      */   }
/*      */ 
/*      */   private Color(float paramFloat1, float paramFloat2, float paramFloat3)
/*      */   {
/* 1751 */     this.red = paramFloat1;
/* 1752 */     this.green = paramFloat2;
/* 1753 */     this.blue = paramFloat3;
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public Object impl_getPlatformPaint()
/*      */   {
/* 1762 */     if (this.platformPaint == null) {
/* 1763 */       this.platformPaint = Toolkit.getToolkit().getPaint(this);
/*      */     }
/* 1765 */     return this.platformPaint;
/*      */   }
/*      */ 
/*      */   public Color interpolate(Color paramColor, double paramDouble)
/*      */   {
/* 1772 */     if (paramDouble <= 0.0D) return this;
/* 1773 */     if (paramDouble >= 1.0D) return paramColor;
/* 1774 */     float f = (float)paramDouble;
/* 1775 */     return new Color(this.red + (paramColor.red - this.red) * f, this.green + (paramColor.green - this.green) * f, this.blue + (paramColor.blue - this.blue) * f, this.opacity + (paramColor.opacity - this.opacity) * f);
/*      */   }
/*      */ 
/*      */   public boolean equals(Object paramObject)
/*      */   {
/* 1789 */     if (paramObject == this) return true;
/* 1790 */     if ((paramObject instanceof Color)) {
/* 1791 */       Color localColor = (Color)paramObject;
/* 1792 */       return (this.red == localColor.red) && (this.green == localColor.green) && (this.blue == localColor.blue) && (this.opacity == localColor.opacity);
/*      */     }
/*      */ 
/* 1796 */     return false;
/*      */   }
/*      */ 
/*      */   public int hashCode()
/*      */   {
/* 1805 */     int i = (int)Math.round(this.red * 255.0D);
/* 1806 */     int j = (int)Math.round(this.green * 255.0D);
/* 1807 */     int k = (int)Math.round(this.blue * 255.0D);
/* 1808 */     int m = (int)Math.round(this.opacity * 255.0D);
/* 1809 */     return to32BitInteger(i, j, k, m);
/*      */   }
/*      */ 
/*      */   public String toString()
/*      */   {
/* 1821 */     int i = (int)Math.round(this.red * 255.0D);
/* 1822 */     int j = (int)Math.round(this.green * 255.0D);
/* 1823 */     int k = (int)Math.round(this.blue * 255.0D);
/* 1824 */     int m = (int)Math.round(this.opacity * 255.0D);
/* 1825 */     return String.format("0x%02x%02x%02x%02x", new Object[] { Integer.valueOf(i), Integer.valueOf(j), Integer.valueOf(k), Integer.valueOf(m) });
/*      */   }
/*      */ 
/*      */   private static final class NamedColors
/*      */   {
/* 1513 */     private static final Map<String, Color> namedColors = createNamedColors();
/*      */ 
/*      */     private static Color get(String paramString)
/*      */     {
/* 1520 */       return (Color)namedColors.get(paramString);
/*      */     }
/*      */ 
/*      */     private static Map<String, Color> createNamedColors() {
/* 1524 */       HashMap localHashMap = new HashMap(256);
/*      */ 
/* 1526 */       localHashMap.put("aliceblue", Color.ALICEBLUE);
/* 1527 */       localHashMap.put("antiquewhite", Color.ANTIQUEWHITE);
/* 1528 */       localHashMap.put("aqua", Color.AQUA);
/* 1529 */       localHashMap.put("aquamarine", Color.AQUAMARINE);
/* 1530 */       localHashMap.put("azure", Color.AZURE);
/* 1531 */       localHashMap.put("beige", Color.BEIGE);
/* 1532 */       localHashMap.put("bisque", Color.BISQUE);
/* 1533 */       localHashMap.put("black", Color.BLACK);
/* 1534 */       localHashMap.put("blanchedalmond", Color.BLANCHEDALMOND);
/* 1535 */       localHashMap.put("blue", Color.BLUE);
/* 1536 */       localHashMap.put("blueviolet", Color.BLUEVIOLET);
/* 1537 */       localHashMap.put("brown", Color.BROWN);
/* 1538 */       localHashMap.put("burlywood", Color.BURLYWOOD);
/* 1539 */       localHashMap.put("cadetblue", Color.CADETBLUE);
/* 1540 */       localHashMap.put("chartreuse", Color.CHARTREUSE);
/* 1541 */       localHashMap.put("chocolate", Color.CHOCOLATE);
/* 1542 */       localHashMap.put("coral", Color.CORAL);
/* 1543 */       localHashMap.put("cornflowerblue", Color.CORNFLOWERBLUE);
/* 1544 */       localHashMap.put("cornsilk", Color.CORNSILK);
/* 1545 */       localHashMap.put("crimson", Color.CRIMSON);
/* 1546 */       localHashMap.put("cyan", Color.CYAN);
/* 1547 */       localHashMap.put("darkblue", Color.DARKBLUE);
/* 1548 */       localHashMap.put("darkcyan", Color.DARKCYAN);
/* 1549 */       localHashMap.put("darkgoldenrod", Color.DARKGOLDENROD);
/* 1550 */       localHashMap.put("darkgray", Color.DARKGRAY);
/* 1551 */       localHashMap.put("darkgreen", Color.DARKGREEN);
/* 1552 */       localHashMap.put("darkgrey", Color.DARKGREY);
/* 1553 */       localHashMap.put("darkkhaki", Color.DARKKHAKI);
/* 1554 */       localHashMap.put("darkmagenta", Color.DARKMAGENTA);
/* 1555 */       localHashMap.put("darkolivegreen", Color.DARKOLIVEGREEN);
/* 1556 */       localHashMap.put("darkorange", Color.DARKORANGE);
/* 1557 */       localHashMap.put("darkorchid", Color.DARKORCHID);
/* 1558 */       localHashMap.put("darkred", Color.DARKRED);
/* 1559 */       localHashMap.put("darksalmon", Color.DARKSALMON);
/* 1560 */       localHashMap.put("darkseagreen", Color.DARKSEAGREEN);
/* 1561 */       localHashMap.put("darkslateblue", Color.DARKSLATEBLUE);
/* 1562 */       localHashMap.put("darkslategray", Color.DARKSLATEGRAY);
/* 1563 */       localHashMap.put("darkslategrey", Color.DARKSLATEGREY);
/* 1564 */       localHashMap.put("darkturquoise", Color.DARKTURQUOISE);
/* 1565 */       localHashMap.put("darkviolet", Color.DARKVIOLET);
/* 1566 */       localHashMap.put("deeppink", Color.DEEPPINK);
/* 1567 */       localHashMap.put("deepskyblue", Color.DEEPSKYBLUE);
/* 1568 */       localHashMap.put("dimgray", Color.DIMGRAY);
/* 1569 */       localHashMap.put("dimgrey", Color.DIMGREY);
/* 1570 */       localHashMap.put("dodgerblue", Color.DODGERBLUE);
/* 1571 */       localHashMap.put("firebrick", Color.FIREBRICK);
/* 1572 */       localHashMap.put("floralwhite", Color.FLORALWHITE);
/* 1573 */       localHashMap.put("forestgreen", Color.FORESTGREEN);
/* 1574 */       localHashMap.put("fuchsia", Color.FUCHSIA);
/* 1575 */       localHashMap.put("gainsboro", Color.GAINSBORO);
/* 1576 */       localHashMap.put("ghostwhite", Color.GHOSTWHITE);
/* 1577 */       localHashMap.put("gold", Color.GOLD);
/* 1578 */       localHashMap.put("goldenrod", Color.GOLDENROD);
/* 1579 */       localHashMap.put("gray", Color.GRAY);
/* 1580 */       localHashMap.put("green", Color.GREEN);
/* 1581 */       localHashMap.put("greenyellow", Color.GREENYELLOW);
/* 1582 */       localHashMap.put("grey", Color.GREY);
/* 1583 */       localHashMap.put("honeydew", Color.HONEYDEW);
/* 1584 */       localHashMap.put("hotpink", Color.HOTPINK);
/* 1585 */       localHashMap.put("indianred", Color.INDIANRED);
/* 1586 */       localHashMap.put("indigo", Color.INDIGO);
/* 1587 */       localHashMap.put("ivory", Color.IVORY);
/* 1588 */       localHashMap.put("khaki", Color.KHAKI);
/* 1589 */       localHashMap.put("lavender", Color.LAVENDER);
/* 1590 */       localHashMap.put("lavenderblush", Color.LAVENDERBLUSH);
/* 1591 */       localHashMap.put("lawngreen", Color.LAWNGREEN);
/* 1592 */       localHashMap.put("lemonchiffon", Color.LEMONCHIFFON);
/* 1593 */       localHashMap.put("lightblue", Color.LIGHTBLUE);
/* 1594 */       localHashMap.put("lightcoral", Color.LIGHTCORAL);
/* 1595 */       localHashMap.put("lightcyan", Color.LIGHTCYAN);
/* 1596 */       localHashMap.put("lightgoldenrodyellow", Color.LIGHTGOLDENRODYELLOW);
/* 1597 */       localHashMap.put("lightgray", Color.LIGHTGRAY);
/* 1598 */       localHashMap.put("lightgreen", Color.LIGHTGREEN);
/* 1599 */       localHashMap.put("lightgrey", Color.LIGHTGREY);
/* 1600 */       localHashMap.put("lightpink", Color.LIGHTPINK);
/* 1601 */       localHashMap.put("lightsalmon", Color.LIGHTSALMON);
/* 1602 */       localHashMap.put("lightseagreen", Color.LIGHTSEAGREEN);
/* 1603 */       localHashMap.put("lightskyblue", Color.LIGHTSKYBLUE);
/* 1604 */       localHashMap.put("lightslategray", Color.LIGHTSLATEGRAY);
/* 1605 */       localHashMap.put("lightslategrey", Color.LIGHTSLATEGREY);
/* 1606 */       localHashMap.put("lightsteelblue", Color.LIGHTSTEELBLUE);
/* 1607 */       localHashMap.put("lightyellow", Color.LIGHTYELLOW);
/* 1608 */       localHashMap.put("lime", Color.LIME);
/* 1609 */       localHashMap.put("limegreen", Color.LIMEGREEN);
/* 1610 */       localHashMap.put("linen", Color.LINEN);
/* 1611 */       localHashMap.put("magenta", Color.MAGENTA);
/* 1612 */       localHashMap.put("maroon", Color.MAROON);
/* 1613 */       localHashMap.put("mediumaquamarine", Color.MEDIUMAQUAMARINE);
/* 1614 */       localHashMap.put("mediumblue", Color.MEDIUMBLUE);
/* 1615 */       localHashMap.put("mediumorchid", Color.MEDIUMORCHID);
/* 1616 */       localHashMap.put("mediumpurple", Color.MEDIUMPURPLE);
/* 1617 */       localHashMap.put("mediumseagreen", Color.MEDIUMSEAGREEN);
/* 1618 */       localHashMap.put("mediumslateblue", Color.MEDIUMSLATEBLUE);
/* 1619 */       localHashMap.put("mediumspringgreen", Color.MEDIUMSPRINGGREEN);
/* 1620 */       localHashMap.put("mediumturquoise", Color.MEDIUMTURQUOISE);
/* 1621 */       localHashMap.put("mediumvioletred", Color.MEDIUMVIOLETRED);
/* 1622 */       localHashMap.put("midnightblue", Color.MIDNIGHTBLUE);
/* 1623 */       localHashMap.put("mintcream", Color.MINTCREAM);
/* 1624 */       localHashMap.put("mistyrose", Color.MISTYROSE);
/* 1625 */       localHashMap.put("moccasin", Color.MOCCASIN);
/* 1626 */       localHashMap.put("navajowhite", Color.NAVAJOWHITE);
/* 1627 */       localHashMap.put("navy", Color.NAVY);
/* 1628 */       localHashMap.put("oldlace", Color.OLDLACE);
/* 1629 */       localHashMap.put("olive", Color.OLIVE);
/* 1630 */       localHashMap.put("olivedrab", Color.OLIVEDRAB);
/* 1631 */       localHashMap.put("orange", Color.ORANGE);
/* 1632 */       localHashMap.put("orangered", Color.ORANGERED);
/* 1633 */       localHashMap.put("orchid", Color.ORCHID);
/* 1634 */       localHashMap.put("palegoldenrod", Color.PALEGOLDENROD);
/* 1635 */       localHashMap.put("palegreen", Color.PALEGREEN);
/* 1636 */       localHashMap.put("paleturquoise", Color.PALETURQUOISE);
/* 1637 */       localHashMap.put("palevioletred", Color.PALEVIOLETRED);
/* 1638 */       localHashMap.put("papayawhip", Color.PAPAYAWHIP);
/* 1639 */       localHashMap.put("peachpuff", Color.PEACHPUFF);
/* 1640 */       localHashMap.put("peru", Color.PERU);
/* 1641 */       localHashMap.put("pink", Color.PINK);
/* 1642 */       localHashMap.put("plum", Color.PLUM);
/* 1643 */       localHashMap.put("powderblue", Color.POWDERBLUE);
/* 1644 */       localHashMap.put("purple", Color.PURPLE);
/* 1645 */       localHashMap.put("red", Color.RED);
/* 1646 */       localHashMap.put("rosybrown", Color.ROSYBROWN);
/* 1647 */       localHashMap.put("royalblue", Color.ROYALBLUE);
/* 1648 */       localHashMap.put("saddlebrown", Color.SADDLEBROWN);
/* 1649 */       localHashMap.put("salmon", Color.SALMON);
/* 1650 */       localHashMap.put("sandybrown", Color.SANDYBROWN);
/* 1651 */       localHashMap.put("seagreen", Color.SEAGREEN);
/* 1652 */       localHashMap.put("seashell", Color.SEASHELL);
/* 1653 */       localHashMap.put("sienna", Color.SIENNA);
/* 1654 */       localHashMap.put("silver", Color.SILVER);
/* 1655 */       localHashMap.put("skyblue", Color.SKYBLUE);
/* 1656 */       localHashMap.put("slateblue", Color.SLATEBLUE);
/* 1657 */       localHashMap.put("slategray", Color.SLATEGRAY);
/* 1658 */       localHashMap.put("slategrey", Color.SLATEGREY);
/* 1659 */       localHashMap.put("snow", Color.SNOW);
/* 1660 */       localHashMap.put("springgreen", Color.SPRINGGREEN);
/* 1661 */       localHashMap.put("steelblue", Color.STEELBLUE);
/* 1662 */       localHashMap.put("tan", Color.TAN);
/* 1663 */       localHashMap.put("teal", Color.TEAL);
/* 1664 */       localHashMap.put("thistle", Color.THISTLE);
/* 1665 */       localHashMap.put("tomato", Color.TOMATO);
/* 1666 */       localHashMap.put("transparent", Color.TRANSPARENT);
/* 1667 */       localHashMap.put("turquoise", Color.TURQUOISE);
/* 1668 */       localHashMap.put("violet", Color.VIOLET);
/* 1669 */       localHashMap.put("wheat", Color.WHEAT);
/* 1670 */       localHashMap.put("white", Color.WHITE);
/* 1671 */       localHashMap.put("whitesmoke", Color.WHITESMOKE);
/* 1672 */       localHashMap.put("yellow", Color.YELLOW);
/* 1673 */       localHashMap.put("yellowgreen", Color.YELLOWGREEN);
/*      */ 
/* 1675 */       return localHashMap;
/*      */     }
/*      */   }
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.paint.Color
 * JD-Core Version:    0.6.2
 */