/*      */ package com.sun.javafx;
/*      */ 
/*      */ import com.sun.javafx.stage.StageHelper;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import javafx.collections.ObservableList;
/*      */ import javafx.geometry.BoundingBox;
/*      */ import javafx.geometry.Bounds;
/*      */ import javafx.geometry.HPos;
/*      */ import javafx.geometry.Point2D;
/*      */ import javafx.geometry.Rectangle2D;
/*      */ import javafx.geometry.VPos;
/*      */ import javafx.scene.Node;
/*      */ import javafx.scene.Scene;
/*      */ import javafx.scene.paint.Color;
/*      */ import javafx.scene.paint.Stop;
/*      */ import javafx.stage.Screen;
/*      */ import javafx.stage.Stage;
/*      */ import javafx.stage.Window;
/*      */ 
/*      */ public class Utils
/*      */ {
/* 1034 */   private static UnicodeProcessor unicodeProcessor = new UnicodeProcessor(null);
/*      */ 
/*      */   public static float clamp(float paramFloat1, float paramFloat2, float paramFloat3)
/*      */   {
/*   65 */     if (paramFloat2 < paramFloat1) return paramFloat1;
/*   66 */     if (paramFloat2 > paramFloat3) return paramFloat3;
/*   67 */     return paramFloat2;
/*      */   }
/*      */ 
/*      */   public static int clamp(int paramInt1, int paramInt2, int paramInt3)
/*      */   {
/*   75 */     if (paramInt2 < paramInt1) return paramInt1;
/*   76 */     if (paramInt2 > paramInt3) return paramInt3;
/*   77 */     return paramInt2;
/*      */   }
/*      */ 
/*      */   public static double clamp(double paramDouble1, double paramDouble2, double paramDouble3)
/*      */   {
/*   85 */     if (paramDouble2 < paramDouble1) return paramDouble1;
/*   86 */     if (paramDouble2 > paramDouble3) return paramDouble3;
/*   87 */     return paramDouble2;
/*      */   }
/*      */ 
/*      */   public static double clampMin(double paramDouble1, double paramDouble2)
/*      */   {
/*   95 */     if (paramDouble1 < paramDouble2) return paramDouble2;
/*   96 */     return paramDouble1;
/*      */   }
/*      */ 
/*      */   public static int clampMin(int paramInt1, int paramInt2)
/*      */   {
/*  104 */     if (paramInt1 < paramInt2) return paramInt2;
/*  105 */     return paramInt1;
/*      */   }
/*      */ 
/*      */   public static float clampMax(float paramFloat1, float paramFloat2)
/*      */   {
/*  113 */     if (paramFloat1 > paramFloat2) return paramFloat2;
/*  114 */     return paramFloat1;
/*      */   }
/*      */ 
/*      */   public static int clampMax(int paramInt1, int paramInt2)
/*      */   {
/*  122 */     if (paramInt1 > paramInt2) return paramInt2;
/*  123 */     return paramInt1;
/*      */   }
/*      */ 
/*      */   public static double nearest(double paramDouble1, double paramDouble2, double paramDouble3)
/*      */   {
/*  132 */     double d1 = paramDouble2 - paramDouble1;
/*  133 */     double d2 = paramDouble3 - paramDouble2;
/*  134 */     if (d1 < d2) return paramDouble1;
/*  135 */     return paramDouble3;
/*      */   }
/*      */ 
/*      */   public static String stripNewlines(String paramString)
/*      */   {
/*  151 */     if (paramString == null) return null;
/*  152 */     return paramString.replace('\n', ' ');
/*      */   }
/*      */ 
/*      */   public static String stripQuotes(String paramString)
/*      */   {
/*  160 */     if (paramString == null) return paramString;
/*  161 */     if (paramString.length() == 0) return paramString;
/*      */ 
/*  163 */     int i = 0;
/*  164 */     int j = paramString.charAt(i);
/*  165 */     if ((j == 34) || (j == 39)) i++;
/*      */ 
/*  167 */     int k = paramString.length();
/*  168 */     int m = paramString.charAt(k - 1);
/*  169 */     if ((m == 34) || (m == 39)) k--;
/*      */ 
/*  171 */     if (k - i < 0) return paramString;
/*      */ 
/*  175 */     return paramString.substring(i, k);
/*      */   }
/*      */ 
/*      */   public static String[] split(String paramString1, String paramString2)
/*      */   {
/*  183 */     if ((paramString1 == null) || (paramString1.length() == 0)) return new String[0];
/*  184 */     if ((paramString2 == null) || (paramString2.length() == 0)) return new String[0];
/*  185 */     if (paramString2.length() > paramString1.length()) return new String[0];
/*      */ 
/*  187 */     ArrayList localArrayList = new ArrayList();
/*      */ 
/*  189 */     int i = paramString1.indexOf(paramString2);
/*  190 */     while (i >= 0) {
/*  191 */       String str = paramString1.substring(0, i);
/*  192 */       if ((str != null) && (str.length() > 0)) {
/*  193 */         localArrayList.add(str);
/*      */       }
/*  195 */       paramString1 = paramString1.substring(i + paramString2.length());
/*  196 */       i = paramString1.indexOf(paramString2);
/*      */     }
/*      */ 
/*  199 */     if ((paramString1 != null) && (paramString1.length() > 0)) {
/*  200 */       localArrayList.add(paramString1);
/*      */     }
/*      */ 
/*  203 */     return (String[])localArrayList.toArray(new String[0]);
/*      */   }
/*      */ 
/*      */   public static boolean contains(String paramString1, String paramString2)
/*      */   {
/*  211 */     if ((paramString1 == null) || (paramString1.length() == 0)) return false;
/*  212 */     if ((paramString2 == null) || (paramString2.length() == 0)) return false;
/*  213 */     if (paramString2.length() > paramString1.length()) return false;
/*      */ 
/*  215 */     return paramString1.indexOf(paramString2) > -1;
/*      */   }
/*      */ 
/*      */   public static double calculateBrightness(Color paramColor)
/*      */   {
/*  228 */     return 0.3D * paramColor.getRed() + 0.59D * paramColor.getGreen() + 0.11D * paramColor.getBlue();
/*      */   }
/*      */ 
/*      */   public static Color deriveColor(Color paramColor, double paramDouble)
/*      */   {
/*  239 */     double d1 = calculateBrightness(paramColor);
/*  240 */     double d2 = paramDouble;
/*      */ 
/*  242 */     if (paramDouble > 0.0D) {
/*  243 */       if (d1 > 0.85D)
/*  244 */         d2 *= 1.6D;
/*  245 */       else if (d1 <= 0.6D)
/*      */       {
/*  247 */         if (d1 > 0.5D)
/*  248 */           d2 *= 0.9D;
/*  249 */         else if (d1 > 0.4D)
/*  250 */           d2 *= 0.8D;
/*  251 */         else if (d1 > 0.3D)
/*  252 */           d2 *= 0.7D;
/*      */         else
/*  254 */           d2 *= 0.6D;
/*      */       }
/*      */     }
/*  257 */     else if (d1 < 0.2D) {
/*  258 */       d2 *= 0.6D;
/*      */     }
/*      */ 
/*  262 */     if (d2 < -1.0D) d2 = -1.0D; else if (d2 > 1.0D) d2 = 1.0D;
/*      */ 
/*  264 */     double[] arrayOfDouble = RGBtoHSB(paramColor.getRed(), paramColor.getGreen(), paramColor.getBlue());
/*      */ 
/*  266 */     if (d2 > 0.0D) {
/*  267 */       arrayOfDouble[1] *= (1.0D - d2);
/*  268 */       arrayOfDouble[2] += (1.0D - arrayOfDouble[2]) * d2;
/*      */     } else {
/*  270 */       arrayOfDouble[2] *= (d2 + 1.0D);
/*      */     }
/*      */ 
/*  273 */     if (arrayOfDouble[1] < 0.0D) arrayOfDouble[1] = 0.0D; else if (arrayOfDouble[1] > 1.0D) arrayOfDouble[1] = 1.0D;
/*  274 */     if (arrayOfDouble[2] < 0.0D) arrayOfDouble[2] = 0.0D; else if (arrayOfDouble[2] > 1.0D) arrayOfDouble[2] = 1.0D;
/*      */ 
/*  276 */     Color localColor = Color.hsb((int)arrayOfDouble[0], arrayOfDouble[1], arrayOfDouble[2], paramColor.getOpacity());
/*  277 */     return Color.hsb((int)arrayOfDouble[0], arrayOfDouble[1], arrayOfDouble[2], paramColor.getOpacity());
/*      */   }
/*      */ 
/*      */   private static Color interpolateLinear(double paramDouble, Color paramColor1, Color paramColor2)
/*      */   {
/*  305 */     Color localColor1 = convertSRGBtoLinearRGB(paramColor1);
/*  306 */     Color localColor2 = convertSRGBtoLinearRGB(paramColor2);
/*  307 */     return convertLinearRGBtoSRGB(Color.color(localColor1.getRed() + (localColor2.getRed() - localColor1.getRed()) * paramDouble, localColor1.getGreen() + (localColor2.getGreen() - localColor1.getGreen()) * paramDouble, localColor1.getBlue() + (localColor2.getBlue() - localColor1.getBlue()) * paramDouble, localColor1.getOpacity() + (localColor2.getOpacity() - localColor1.getOpacity()) * paramDouble));
/*      */   }
/*      */ 
/*      */   private static Color ladder(double paramDouble, Stop[] paramArrayOfStop)
/*      */   {
/*  319 */     Object localObject = null;
/*  320 */     for (int i = 0; i < paramArrayOfStop.length; i++) {
/*  321 */       Stop localStop = paramArrayOfStop[i];
/*  322 */       if (paramDouble <= localStop.getOffset()) {
/*  323 */         if (localObject == null) {
/*  324 */           return localStop.getColor();
/*      */         }
/*  326 */         return interpolateLinear((paramDouble - localObject.getOffset()) / (localStop.getOffset() - localObject.getOffset()), localObject.getColor(), localStop.getColor());
/*      */       }
/*      */ 
/*  329 */       localObject = localStop;
/*      */     }
/*      */ 
/*  332 */     return localObject.getColor();
/*      */   }
/*      */ 
/*      */   public static Color ladder(Color paramColor, Stop[] paramArrayOfStop)
/*      */   {
/*  339 */     return ladder(calculateBrightness(paramColor), paramArrayOfStop);
/*      */   }
/*      */ 
/*      */   public static double[] HSBtoRGB(double paramDouble1, double paramDouble2, double paramDouble3)
/*      */   {
/*  344 */     double d1 = (paramDouble1 % 360.0D + 360.0D) % 360.0D;
/*  345 */     paramDouble1 = d1 / 360.0D;
/*      */ 
/*  347 */     double d2 = 0.0D; double d3 = 0.0D; double d4 = 0.0D;
/*  348 */     if (paramDouble2 == 0.0D) {
/*  349 */       d2 = d3 = d4 = paramDouble3;
/*      */     } else {
/*  351 */       double d5 = (paramDouble1 - Math.floor(paramDouble1)) * 6.0D;
/*  352 */       double d6 = d5 - Math.floor(d5);
/*  353 */       double d7 = paramDouble3 * (1.0D - paramDouble2);
/*  354 */       double d8 = paramDouble3 * (1.0D - paramDouble2 * d6);
/*  355 */       double d9 = paramDouble3 * (1.0D - paramDouble2 * (1.0D - d6));
/*  356 */       switch ((int)d5) {
/*      */       case 0:
/*  358 */         d2 = paramDouble3;
/*  359 */         d3 = d9;
/*  360 */         d4 = d7;
/*  361 */         break;
/*      */       case 1:
/*  363 */         d2 = d8;
/*  364 */         d3 = paramDouble3;
/*  365 */         d4 = d7;
/*  366 */         break;
/*      */       case 2:
/*  368 */         d2 = d7;
/*  369 */         d3 = paramDouble3;
/*  370 */         d4 = d9;
/*  371 */         break;
/*      */       case 3:
/*  373 */         d2 = d7;
/*  374 */         d3 = d8;
/*  375 */         d4 = paramDouble3;
/*  376 */         break;
/*      */       case 4:
/*  378 */         d2 = d9;
/*  379 */         d3 = d7;
/*  380 */         d4 = paramDouble3;
/*  381 */         break;
/*      */       case 5:
/*  383 */         d2 = paramDouble3;
/*  384 */         d3 = d7;
/*  385 */         d4 = d8;
/*      */       }
/*      */     }
/*      */ 
/*  389 */     double[] arrayOfDouble = new double[3];
/*  390 */     arrayOfDouble[0] = d2;
/*  391 */     arrayOfDouble[1] = d3;
/*  392 */     arrayOfDouble[2] = d4;
/*  393 */     return arrayOfDouble;
/*      */   }
/*      */ 
/*      */   public static double[] RGBtoHSB(double paramDouble1, double paramDouble2, double paramDouble3)
/*      */   {
/*  398 */     double[] arrayOfDouble = new double[3];
/*  399 */     double d4 = paramDouble1 > paramDouble2 ? paramDouble1 : paramDouble2;
/*  400 */     if (paramDouble3 > d4) d4 = paramDouble3;
/*  401 */     double d5 = paramDouble1 < paramDouble2 ? paramDouble1 : paramDouble2;
/*  402 */     if (paramDouble3 < d5) d5 = paramDouble3;
/*      */ 
/*  404 */     double d3 = d4;
/*      */     double d2;
/*  405 */     if (d4 != 0.0D)
/*  406 */       d2 = (d4 - d5) / d4;
/*      */     else
/*  408 */       d2 = 0.0D;
/*      */     double d1;
/*  410 */     if (d2 == 0.0D) {
/*  411 */       d1 = 0.0D;
/*      */     } else {
/*  413 */       double d6 = (d4 - paramDouble1) / (d4 - d5);
/*  414 */       double d7 = (d4 - paramDouble2) / (d4 - d5);
/*  415 */       double d8 = (d4 - paramDouble3) / (d4 - d5);
/*  416 */       if (paramDouble1 == d4)
/*  417 */         d1 = d8 - d7;
/*  418 */       else if (paramDouble2 == d4)
/*  419 */         d1 = 2.0D + d6 - d8;
/*      */       else
/*  421 */         d1 = 4.0D + d7 - d6;
/*  422 */       d1 /= 6.0D;
/*  423 */       if (d1 < 0.0D)
/*  424 */         d1 += 1.0D;
/*      */     }
/*  426 */     arrayOfDouble[0] = (d1 * 360.0D);
/*  427 */     arrayOfDouble[1] = d2;
/*  428 */     arrayOfDouble[2] = d3;
/*  429 */     return arrayOfDouble;
/*      */   }
/*      */ 
/*      */   public static Color convertSRGBtoLinearRGB(Color paramColor)
/*      */   {
/*  436 */     double[] arrayOfDouble = { paramColor.getRed(), paramColor.getGreen(), paramColor.getBlue() };
/*  437 */     for (int i = 0; i < arrayOfDouble.length; i++) {
/*  438 */       if (arrayOfDouble[i] <= 0.04045D)
/*  439 */         arrayOfDouble[i] /= 12.92D;
/*      */       else {
/*  441 */         arrayOfDouble[i] = Math.pow((arrayOfDouble[i] + 0.055D) / 1.055D, 2.4D);
/*      */       }
/*      */     }
/*  444 */     return Color.color(arrayOfDouble[0], arrayOfDouble[1], arrayOfDouble[2], paramColor.getOpacity());
/*      */   }
/*      */ 
/*      */   public static Color convertLinearRGBtoSRGB(Color paramColor)
/*      */   {
/*  451 */     double[] arrayOfDouble = { paramColor.getRed(), paramColor.getGreen(), paramColor.getBlue() };
/*  452 */     for (int i = 0; i < arrayOfDouble.length; i++) {
/*  453 */       if (arrayOfDouble[i] <= 0.0031308D)
/*  454 */         arrayOfDouble[i] *= 12.92D;
/*      */       else {
/*  456 */         arrayOfDouble[i] = (1.055D * Math.pow(arrayOfDouble[i], 0.4166666666666667D) - 0.055D);
/*      */       }
/*      */     }
/*  459 */     return Color.color(arrayOfDouble[0], arrayOfDouble[1], arrayOfDouble[2], paramColor.getOpacity());
/*      */   }
/*      */ 
/*      */   public static <E extends Node> List<E> getManaged(List<E> paramList) {
/*  463 */     ArrayList localArrayList = new ArrayList();
/*  464 */     for (Node localNode : paramList) {
/*  465 */       if ((localNode != null) && (localNode.isManaged())) {
/*  466 */         localArrayList.add(localNode);
/*      */       }
/*      */     }
/*  469 */     return localArrayList;
/*      */   }
/*      */ 
/*      */   public static double sum(double[] paramArrayOfDouble)
/*      */   {
/*  474 */     double d1 = 0.0D;
/*  475 */     for (double d2 : paramArrayOfDouble) d1 += d2;
/*  476 */     return d1 / paramArrayOfDouble.length;
/*      */   }
/*      */ 
/*      */   public static Point2D pointRelativeTo(Node paramNode1, Node paramNode2, HPos paramHPos, VPos paramVPos, boolean paramBoolean)
/*      */   {
/*  491 */     double d1 = paramNode2.getLayoutBounds().getWidth();
/*  492 */     double d2 = paramNode2.getLayoutBounds().getHeight();
/*  493 */     return pointRelativeTo(paramNode1, d1, d2, paramHPos, paramVPos, 0.0D, 0.0D, paramBoolean);
/*      */   }
/*      */ 
/*      */   public static Point2D pointRelativeTo(Node paramNode, double paramDouble1, double paramDouble2, HPos paramHPos, VPos paramVPos, boolean paramBoolean)
/*      */   {
/*  499 */     return pointRelativeTo(paramNode, paramDouble1, paramDouble2, paramHPos, paramVPos, 0.0D, 0.0D, paramBoolean);
/*      */   }
/*      */ 
/*      */   public static Point2D pointRelativeTo(Node paramNode1, Node paramNode2, HPos paramHPos, VPos paramVPos, double paramDouble1, double paramDouble2, boolean paramBoolean)
/*      */   {
/*  505 */     double d1 = paramNode2.getLayoutBounds().getWidth();
/*  506 */     double d2 = paramNode2.getLayoutBounds().getHeight();
/*  507 */     return pointRelativeTo(paramNode1, d1, d2, paramHPos, paramVPos, paramDouble1, paramDouble2, paramBoolean);
/*      */   }
/*      */ 
/*      */   public static Point2D pointRelativeTo(Node paramNode, double paramDouble1, double paramDouble2, HPos paramHPos, VPos paramVPos, double paramDouble3, double paramDouble4, boolean paramBoolean)
/*      */   {
/*  514 */     double d1 = getOffsetX(paramNode);
/*  515 */     double d2 = getOffsetY(paramNode);
/*  516 */     Bounds localBounds = getBounds(paramNode);
/*      */ 
/*  518 */     double d3 = positionX(d1, localBounds, paramDouble1, paramHPos) + paramDouble3;
/*  519 */     double d4 = positionY(d2, localBounds, paramDouble2, paramVPos) + paramDouble4;
/*      */ 
/*  521 */     if (paramBoolean) {
/*  522 */       return pointRelativeTo(paramNode, paramDouble1, paramDouble2, d3, d4, paramHPos, paramVPos);
/*      */     }
/*  524 */     return new Point2D(d3, d4);
/*      */   }
/*      */ 
/*      */   public static Point2D pointRelativeTo(Node paramNode1, Node paramNode2, double paramDouble1, double paramDouble2, boolean paramBoolean)
/*      */   {
/*  540 */     Bounds localBounds = paramNode1.localToScene(paramNode1.getBoundsInLocal());
/*  541 */     double d1 = paramDouble1 + localBounds.getMinX() + paramNode1.getScene().getX() + paramNode1.getScene().getWindow().getX();
/*  542 */     double d2 = paramDouble2 + localBounds.getMinY() + paramNode1.getScene().getY() + paramNode1.getScene().getWindow().getY();
/*      */ 
/*  544 */     if (paramBoolean) {
/*  545 */       return pointRelativeTo(paramNode1, paramNode2, d1, d2, null, null);
/*      */     }
/*  547 */     return new Point2D(d1, d2);
/*      */   }
/*      */ 
/*      */   public static Point2D pointRelativeTo(Node paramNode1, Node paramNode2, double paramDouble1, double paramDouble2, HPos paramHPos, VPos paramVPos)
/*      */   {
/*  571 */     double d1 = paramNode2.getLayoutBounds().getWidth();
/*  572 */     double d2 = paramNode2.getLayoutBounds().getHeight();
/*      */ 
/*  574 */     return pointRelativeTo(paramNode1, d1, d2, paramDouble1, paramDouble2, paramHPos, paramVPos);
/*      */   }
/*      */ 
/*      */   public static Point2D pointRelativeTo(Object paramObject, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, HPos paramHPos, VPos paramVPos)
/*      */   {
/*  593 */     double d1 = paramDouble3;
/*  594 */     double d2 = paramDouble4;
/*  595 */     double d3 = getOffsetX(paramObject);
/*  596 */     double d4 = getOffsetY(paramObject);
/*  597 */     Bounds localBounds = getBounds(paramObject);
/*      */ 
/*  600 */     Screen localScreen = getScreen(paramObject);
/*  601 */     Rectangle2D localRectangle2D = hasFullScreenStage(localScreen) ? localScreen.getBounds() : localScreen.getVisualBounds();
/*      */ 
/*  610 */     if (paramHPos != null)
/*      */     {
/*  612 */       if (d1 + paramDouble1 > localRectangle2D.getMaxX()) {
/*  613 */         d1 = positionX(d3, localBounds, paramDouble1, getHPosOpposite(paramHPos, paramVPos));
/*      */       }
/*      */ 
/*  617 */       if (d1 < localRectangle2D.getMinX()) {
/*  618 */         d1 = positionX(d3, localBounds, paramDouble1, getHPosOpposite(paramHPos, paramVPos));
/*      */       }
/*      */     }
/*      */ 
/*  622 */     if (paramVPos != null)
/*      */     {
/*  624 */       if (d2 + paramDouble2 > localRectangle2D.getMaxY()) {
/*  625 */         d2 = positionY(d4, localBounds, paramDouble2, getVPosOpposite(paramHPos, paramVPos));
/*      */       }
/*      */ 
/*  629 */       if (d2 < localRectangle2D.getMinY()) {
/*  630 */         d2 = positionY(d4, localBounds, paramDouble2, getVPosOpposite(paramHPos, paramVPos));
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  638 */     if (d1 + paramDouble1 > localRectangle2D.getMaxX()) {
/*  639 */       d1 -= d1 + paramDouble1 - localRectangle2D.getMaxX();
/*      */     }
/*  641 */     if (d1 < localRectangle2D.getMinX()) {
/*  642 */       d1 = localRectangle2D.getMinX();
/*      */     }
/*  644 */     if (d2 + paramDouble2 > localRectangle2D.getMaxY()) {
/*  645 */       d2 -= d2 + paramDouble2 - localRectangle2D.getMaxY();
/*      */     }
/*  647 */     if (d2 < localRectangle2D.getMinY()) {
/*  648 */       d2 = localRectangle2D.getMinY();
/*      */     }
/*      */ 
/*  651 */     return new Point2D(d1, d2);
/*      */   }
/*      */ 
/*      */   public static Point2D pointRelativeTo(Window paramWindow, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*      */   {
/*  664 */     return pointRelativeTo(paramWindow, paramDouble1, paramDouble2, paramDouble3, paramDouble4, null, null);
/*      */   }
/*      */ 
/*      */   private static double getOffsetX(Object paramObject)
/*      */   {
/*  674 */     if ((paramObject instanceof Node)) {
/*  675 */       Scene localScene = ((Node)paramObject).getScene();
/*  676 */       if ((localScene == null) || (localScene.getWindow() == null)) {
/*  677 */         return 0.0D;
/*      */       }
/*  679 */       return localScene.getX() + localScene.getWindow().getX();
/*  680 */     }if ((paramObject instanceof Window)) {
/*  681 */       return ((Window)paramObject).getX();
/*      */     }
/*  683 */     return 0.0D;
/*      */   }
/*      */ 
/*      */   private static double getOffsetY(Object paramObject)
/*      */   {
/*  694 */     if ((paramObject instanceof Node)) {
/*  695 */       Scene localScene = ((Node)paramObject).getScene();
/*  696 */       if ((localScene == null) || (localScene.getWindow() == null)) {
/*  697 */         return 0.0D;
/*      */       }
/*  699 */       return localScene.getY() + localScene.getWindow().getY();
/*  700 */     }if ((paramObject instanceof Window)) {
/*  701 */       return ((Window)paramObject).getY();
/*      */     }
/*  703 */     return 0.0D;
/*      */   }
/*      */ 
/*      */   private static double positionX(double paramDouble1, Bounds paramBounds, double paramDouble2, HPos paramHPos)
/*      */   {
/*  713 */     if (paramHPos == HPos.CENTER)
/*      */     {
/*  715 */       return paramDouble1 + paramBounds.getMinX();
/*  716 */     }if (paramHPos == HPos.RIGHT)
/*  717 */       return paramDouble1 + paramBounds.getMaxX();
/*  718 */     if (paramHPos == HPos.LEFT) {
/*  719 */       return paramDouble1 + paramBounds.getMinX() - paramDouble2;
/*      */     }
/*  721 */     return 0.0D;
/*      */   }
/*      */ 
/*      */   private static double positionY(double paramDouble1, Bounds paramBounds, double paramDouble2, VPos paramVPos)
/*      */   {
/*  733 */     if (paramVPos == VPos.BOTTOM)
/*  734 */       return paramDouble1 + paramBounds.getMaxY();
/*  735 */     if (paramVPos == VPos.CENTER)
/*  736 */       return paramDouble1 + paramBounds.getMinY();
/*  737 */     if (paramVPos == VPos.TOP) {
/*  738 */       return paramDouble1 + paramBounds.getMinY() - paramDouble2;
/*      */     }
/*  740 */     return 0.0D;
/*      */   }
/*      */ 
/*      */   private static Bounds getBounds(Object paramObject)
/*      */   {
/*  750 */     if ((paramObject instanceof Node))
/*  751 */       return ((Node)paramObject).localToScene(((Node)paramObject).getBoundsInLocal());
/*  752 */     if ((paramObject instanceof Window)) {
/*  753 */       Window localWindow = (Window)paramObject;
/*  754 */       return new BoundingBox(0.0D, 0.0D, localWindow.getWidth(), localWindow.getHeight());
/*      */     }
/*  756 */     return new BoundingBox(0.0D, 0.0D, 0.0D, 0.0D);
/*      */   }
/*      */ 
/*      */   private static HPos getHPosOpposite(HPos paramHPos, VPos paramVPos)
/*      */   {
/*  765 */     if (paramVPos == VPos.CENTER) {
/*  766 */       if (paramHPos == HPos.LEFT)
/*  767 */         return HPos.RIGHT;
/*  768 */       if (paramHPos == HPos.RIGHT)
/*  769 */         return HPos.LEFT;
/*  770 */       if (paramHPos == HPos.CENTER) {
/*  771 */         return HPos.CENTER;
/*      */       }
/*      */ 
/*  774 */       return HPos.CENTER;
/*      */     }
/*      */ 
/*  777 */     return HPos.CENTER;
/*      */   }
/*      */ 
/*      */   private static VPos getVPosOpposite(HPos paramHPos, VPos paramVPos)
/*      */   {
/*  786 */     if (paramHPos == HPos.CENTER) {
/*  787 */       if (paramVPos == VPos.BASELINE)
/*  788 */         return VPos.BASELINE;
/*  789 */       if (paramVPos == VPos.BOTTOM)
/*  790 */         return VPos.TOP;
/*  791 */       if (paramVPos == VPos.CENTER)
/*  792 */         return VPos.CENTER;
/*  793 */       if (paramVPos == VPos.TOP) {
/*  794 */         return VPos.BOTTOM;
/*      */       }
/*      */ 
/*  797 */       return VPos.CENTER;
/*      */     }
/*      */ 
/*  800 */     return VPos.CENTER;
/*      */   }
/*      */ 
/*      */   public static boolean hasFullScreenStage(Screen paramScreen)
/*      */   {
/*  805 */     ObservableList localObservableList = StageHelper.getStages();
/*      */ 
/*  807 */     for (Stage localStage : localObservableList) {
/*  808 */       if ((localStage.isFullScreen()) && (getScreen(localStage) == paramScreen)) {
/*  809 */         return true;
/*      */       }
/*      */     }
/*      */ 
/*  813 */     return false;
/*      */   }
/*      */ 
/*      */   public static boolean isVGAScreen()
/*      */   {
/*  820 */     Rectangle2D localRectangle2D = Screen.getPrimary().getBounds();
/*  821 */     return ((localRectangle2D.getWidth() == 640.0D) && (localRectangle2D.getHeight() == 480.0D)) || ((localRectangle2D.getWidth() == 480.0D) && (localRectangle2D.getHeight() == 640.0D));
/*      */   }
/*      */ 
/*      */   public static boolean isQVGAScreen()
/*      */   {
/*  829 */     Rectangle2D localRectangle2D = Screen.getPrimary().getBounds();
/*  830 */     return ((localRectangle2D.getWidth() == 320.0D) && (localRectangle2D.getHeight() == 240.0D)) || ((localRectangle2D.getWidth() == 240.0D) && (localRectangle2D.getHeight() == 320.0D));
/*      */   }
/*      */ 
/*      */   public static Screen getScreen(Object paramObject)
/*      */   {
/*  843 */     double d1 = getOffsetX(paramObject);
/*  844 */     double d2 = getOffsetY(paramObject);
/*  845 */     Bounds localBounds = getBounds(paramObject);
/*      */ 
/*  847 */     Rectangle2D localRectangle2D = new Rectangle2D(d1 + localBounds.getMinX(), d2 + localBounds.getMinY(), localBounds.getWidth(), localBounds.getHeight());
/*      */ 
/*  853 */     return getScreenForRectangle(localRectangle2D);
/*      */   }
/*      */ 
/*      */   public static Screen getScreenForRectangle(Rectangle2D paramRectangle2D) {
/*  857 */     ObservableList localObservableList = Screen.getScreens();
/*      */ 
/*  859 */     double d1 = paramRectangle2D.getMinX();
/*  860 */     double d2 = paramRectangle2D.getMaxX();
/*  861 */     double d3 = paramRectangle2D.getMinY();
/*  862 */     double d4 = paramRectangle2D.getMaxY();
/*      */ 
/*  866 */     Object localObject1 = null;
/*  867 */     double d5 = 0.0D;
/*  868 */     for (Screen localScreen1 : localObservableList) {
/*  869 */       localObject2 = localScreen1.getBounds();
/*  870 */       double d7 = getIntersectionLength(d1, d2, ((Rectangle2D)localObject2).getMinX(), ((Rectangle2D)localObject2).getMaxX()) * getIntersectionLength(d3, d4, ((Rectangle2D)localObject2).getMinY(), ((Rectangle2D)localObject2).getMaxY());
/*      */ 
/*  878 */       if (d5 < d7) {
/*  879 */         d5 = d7;
/*  880 */         localObject1 = localScreen1;
/*      */       }
/*      */     }
/*      */ 
/*  884 */     if (localObject1 != null) {
/*  885 */       return localObject1;
/*      */     }
/*      */ 
/*  888 */     localObject1 = Screen.getPrimary();
/*  889 */     double d6 = 1.7976931348623157E+308D;
/*  890 */     for (Object localObject2 = localObservableList.iterator(); ((Iterator)localObject2).hasNext(); ) { Screen localScreen2 = (Screen)((Iterator)localObject2).next();
/*  891 */       Rectangle2D localRectangle2D = localScreen2.getBounds();
/*  892 */       double d8 = getOuterDistance(d1, d2, localRectangle2D.getMinX(), localRectangle2D.getMaxX());
/*      */ 
/*  895 */       double d9 = getOuterDistance(d3, d4, localRectangle2D.getMinY(), localRectangle2D.getMaxY());
/*      */ 
/*  898 */       double d10 = d8 * d8 + d9 * d9;
/*      */ 
/*  900 */       if (d6 > d10) {
/*  901 */         d6 = d10;
/*  902 */         localObject1 = localScreen2;
/*      */       }
/*      */     }
/*      */ 
/*  906 */     return localObject1;
/*      */   }
/*      */ 
/*      */   public static Screen getScreenForPoint(double paramDouble1, double paramDouble2) {
/*  910 */     ObservableList localObservableList = Screen.getScreens();
/*      */ 
/*  913 */     for (Object localObject = localObservableList.iterator(); ((Iterator)localObject).hasNext(); ) { Screen localScreen1 = (Screen)((Iterator)localObject).next();
/*      */ 
/*  916 */       Rectangle2D localRectangle2D1 = localScreen1.getBounds();
/*  917 */       if ((paramDouble1 >= localRectangle2D1.getMinX()) && (paramDouble1 < localRectangle2D1.getMaxX()) && (paramDouble2 >= localRectangle2D1.getMinY()) && (paramDouble2 < localRectangle2D1.getMaxY()))
/*      */       {
/*  921 */         return localScreen1;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  926 */     localObject = Screen.getPrimary();
/*  927 */     double d1 = 1.7976931348623157E+308D;
/*  928 */     for (Screen localScreen2 : localObservableList) {
/*  929 */       Rectangle2D localRectangle2D2 = localScreen2.getBounds();
/*  930 */       double d2 = getOuterDistance(localRectangle2D2.getMinX(), localRectangle2D2.getMaxX(), paramDouble1);
/*      */ 
/*  933 */       double d3 = getOuterDistance(localRectangle2D2.getMinY(), localRectangle2D2.getMaxY(), paramDouble2);
/*      */ 
/*  936 */       double d4 = d2 * d2 + d3 * d3;
/*  937 */       if (d1 >= d4) {
/*  938 */         d1 = d4;
/*  939 */         localObject = localScreen2;
/*      */       }
/*      */     }
/*      */ 
/*  943 */     return localObject;
/*      */   }
/*      */ 
/*      */   private static double getIntersectionLength(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*      */   {
/*  950 */     return paramDouble1 <= paramDouble3 ? getIntersectionLengthImpl(paramDouble3, paramDouble4, paramDouble2) : getIntersectionLengthImpl(paramDouble1, paramDouble2, paramDouble4);
/*      */   }
/*      */ 
/*      */   private static double getIntersectionLengthImpl(double paramDouble1, double paramDouble2, double paramDouble3)
/*      */   {
/*  957 */     if (paramDouble3 <= paramDouble1) {
/*  958 */       return 0.0D;
/*      */     }
/*      */ 
/*  961 */     return paramDouble3 <= paramDouble2 ? paramDouble3 - paramDouble1 : paramDouble2 - paramDouble1;
/*      */   }
/*      */ 
/*      */   private static double getOuterDistance(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*      */   {
/*  968 */     if (paramDouble2 <= paramDouble3) {
/*  969 */       return paramDouble3 - paramDouble2;
/*      */     }
/*      */ 
/*  972 */     if (paramDouble4 <= paramDouble1) {
/*  973 */       return paramDouble4 - paramDouble1;
/*      */     }
/*      */ 
/*  976 */     return 0.0D;
/*      */   }
/*      */ 
/*      */   private static double getOuterDistance(double paramDouble1, double paramDouble2, double paramDouble3)
/*      */   {
/*  983 */     if (paramDouble3 <= paramDouble1) {
/*  984 */       return paramDouble1 - paramDouble3;
/*      */     }
/*      */ 
/*  987 */     if (paramDouble3 >= paramDouble2) {
/*  988 */       return paramDouble3 - paramDouble2;
/*      */     }
/*      */ 
/*  991 */     return 0.0D;
/*      */   }
/*      */ 
/*      */   public static boolean assertionEnabled()
/*      */   {
/* 1001 */     boolean bool = false;
/* 1002 */     assert ((bool = 1) != 0);
/*      */ 
/* 1004 */     return bool;
/*      */   }
/*      */ 
/*      */   public static boolean isWindows()
/*      */   {
/* 1011 */     return PlatformUtil.isWindows();
/*      */   }
/*      */ 
/*      */   public static boolean isMac()
/*      */   {
/* 1018 */     return PlatformUtil.isMac();
/*      */   }
/*      */ 
/*      */   public static boolean isUnix()
/*      */   {
/* 1025 */     return PlatformUtil.isUnix();
/*      */   }
/*      */ 
/*      */   public static String convertUnicode(String paramString)
/*      */   {
/* 1036 */     return unicodeProcessor.process(paramString);
/*      */   }
/*      */ 
/*      */   private static final class UnicodeProcessor
/*      */   {
/*      */     private char[] buf;
/*      */     private int bp;
/*      */     private int buflen;
/*      */     private char ch;
/* 1053 */     private int unicodeConversionBp = -1;
/*      */ 
/*      */     public String process(String paramString) {
/* 1056 */       this.buf = paramString.toCharArray();
/* 1057 */       this.buflen = this.buf.length;
/* 1058 */       this.bp = -1;
/*      */ 
/* 1060 */       char[] arrayOfChar = new char[this.buflen];
/* 1061 */       int i = 0;
/*      */ 
/* 1063 */       while (this.bp < this.buflen - 1) {
/* 1064 */         this.ch = this.buf[(++this.bp)];
/* 1065 */         if (this.ch == '\\') {
/* 1066 */           convertUnicode();
/*      */         }
/* 1068 */         arrayOfChar[(i++)] = this.ch;
/*      */       }
/*      */ 
/* 1071 */       return new String(arrayOfChar, 0, i);
/*      */     }
/*      */ 
/*      */     private void convertUnicode()
/*      */     {
/* 1078 */       if ((this.ch == '\\') && (this.unicodeConversionBp != this.bp)) {
/* 1079 */         this.bp += 1; this.ch = this.buf[this.bp];
/* 1080 */         if (this.ch == 'u') {
/*      */           do {
/* 1082 */             this.bp += 1; this.ch = this.buf[this.bp];
/* 1083 */           }while (this.ch == 'u');
/* 1084 */           int i = this.bp + 3;
/* 1085 */           if (i < this.buflen) {
/* 1086 */             int j = digit(16);
/* 1087 */             int k = j;
/* 1088 */             while ((this.bp < i) && (j >= 0)) {
/* 1089 */               this.bp += 1; this.ch = this.buf[this.bp];
/* 1090 */               j = digit(16);
/* 1091 */               k = (k << 4) + j;
/*      */             }
/* 1093 */             if (j >= 0) {
/* 1094 */               this.ch = ((char)k);
/* 1095 */               this.unicodeConversionBp = this.bp;
/* 1096 */               return;
/*      */             }
/*      */           }
/*      */         }
/*      */         else {
/* 1101 */           this.bp -= 1;
/* 1102 */           this.ch = '\\';
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*      */     private int digit(int paramInt)
/*      */     {
/* 1111 */       char c = this.ch;
/* 1112 */       int i = Character.digit(c, paramInt);
/* 1113 */       if ((i >= 0) && (c > ''))
/*      */       {
/* 1115 */         this.ch = "0123456789abcdef".charAt(i);
/*      */       }
/* 1117 */       return i;
/*      */     }
/*      */   }
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.Utils
 * JD-Core Version:    0.6.2
 */