/*     */ package com.sun.webpane.sg.prism;
/*     */ 
/*     */ import com.sun.javafx.geom.Arc2D;
/*     */ import com.sun.javafx.geom.Ellipse2D;
/*     */ import com.sun.javafx.geom.Path2D;
/*     */ import com.sun.javafx.geom.PathIterator;
/*     */ import com.sun.javafx.geom.Point2D;
/*     */ import com.sun.javafx.geom.RectBounds;
/*     */ import com.sun.javafx.geom.RoundRectangle2D;
/*     */ import com.sun.javafx.geom.Shape;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.webpane.platform.graphics.WCPath;
/*     */ import com.sun.webpane.platform.graphics.WCPathIterator;
/*     */ import com.sun.webpane.platform.graphics.WCRectangle;
/*     */ import java.util.Stack;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ class WCPathImpl extends WCPath<Path2D>
/*     */ {
/*     */   private Path2D path;
/*  25 */   private boolean hasCP = false;
/*     */ 
/*  27 */   private static final Logger log = Logger.getLogger(WCPathImpl.class.getName());
/*     */ 
/*     */   public WCPathImpl()
/*     */   {
/*  31 */     if (log.isLoggable(Level.FINE)) {
/*  32 */       log.log(Level.FINE, "Create empty WCPathImpl({0})", Integer.valueOf(getID()));
/*     */     }
/*  34 */     this.path = new Path2D();
/*     */   }
/*     */ 
/*     */   public WCPathImpl(WCPathImpl paramWCPathImpl) {
/*  38 */     if (log.isLoggable(Level.FINE)) {
/*  39 */       log.log(Level.FINE, "Create WCPathImpl({0}) from WCPathImpl({1})", new Object[] { Integer.valueOf(getID()), Integer.valueOf(paramWCPathImpl.getID()) });
/*     */     }
/*     */ 
/*  42 */     this.path = new Path2D(paramWCPathImpl.path);
/*  43 */     this.hasCP = paramWCPathImpl.hasCP;
/*     */   }
/*     */ 
/*     */   public void addRect(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/*  47 */     if (log.isLoggable(Level.FINE)) {
/*  48 */       log.log(Level.FINE, "WCPathImpl({0}).addRect({1},{2},{3},{4})", new Object[] { Integer.valueOf(getID()), Double.valueOf(paramDouble1), Double.valueOf(paramDouble2), Double.valueOf(paramDouble3), Double.valueOf(paramDouble4) });
/*     */     }
/*     */ 
/*  51 */     this.hasCP = true;
/*  52 */     this.path.append(new RoundRectangle2D((float)paramDouble1, (float)paramDouble2, (float)paramDouble3, (int)paramDouble4, 0.0F, 0.0F), false);
/*     */   }
/*     */ 
/*     */   public void addEllipse(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*     */   {
/*  58 */     if (log.isLoggable(Level.FINE)) {
/*  59 */       log.log(Level.FINE, "WCPathImpl({0}).addEllipse({1},{2},{3},{4})", new Object[] { Integer.valueOf(getID()), Double.valueOf(paramDouble1), Double.valueOf(paramDouble2), Double.valueOf(paramDouble3), Double.valueOf(paramDouble4) });
/*     */     }
/*     */ 
/*  62 */     this.hasCP = true;
/*  63 */     this.path.append(new Ellipse2D((float)paramDouble1, (float)paramDouble2, (float)paramDouble3, (float)paramDouble4), false);
/*     */   }
/*     */ 
/*     */   public void addArcTo(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5)
/*     */   {
/*  68 */     if (log.isLoggable(Level.FINE)) {
/*  69 */       log.log(Level.FINE, "WCPathImpl({0}).addArcTo({1},{2},{3},{4})", new Object[] { Integer.valueOf(getID()), Double.valueOf(paramDouble1), Double.valueOf(paramDouble2), Double.valueOf(paramDouble3), Double.valueOf(paramDouble4) });
/*     */     }
/*     */ 
/*  73 */     Arc2D localArc2D = new Arc2D();
/*  74 */     localArc2D.setArcByTangent(this.path.getCurrentPoint(), new Point2D((float)paramDouble1, (float)paramDouble2), new Point2D((float)paramDouble3, (float)paramDouble4), (float)paramDouble5);
/*     */ 
/*  80 */     this.hasCP = true;
/*  81 */     this.path.append(localArc2D, true);
/*     */   }
/*     */ 
/*     */   public void addArc(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, boolean paramBoolean)
/*     */   {
/*  87 */     if (log.isLoggable(Level.FINE)) {
/*  88 */       log.log(Level.FINE, "WCPathImpl({0}).addArc({1},{2},{3},{4},{5},{6})", new Object[] { Integer.valueOf(getID()), Double.valueOf(paramDouble1), Double.valueOf(paramDouble2), Double.valueOf(paramDouble3), Double.valueOf(paramDouble4), Double.valueOf(paramDouble5), Boolean.valueOf(paramBoolean) });
/*     */     }
/*     */ 
/*  91 */     this.hasCP = true;
/*     */ 
/*  94 */     double d1 = 0.001D;
/*     */ 
/*  96 */     if (!paramBoolean)
/*     */     {
/*     */       int i;
/*  97 */       if (paramDouble5 < 0.0D) {
/*  98 */         if (paramDouble5 < -6.283185307179586D - d1) {
/*  99 */           i = (int)(-paramDouble5 / 6.283185307179586D);
/* 100 */           paramDouble5 += i * 2.0D * 3.141592653589793D;
/*     */         }
/*     */ 
/* 103 */         paramDouble5 += 6.283185307179586D;
/*     */       }
/* 105 */       else if (paramDouble5 > 6.283185307179586D + d1) {
/* 106 */         i = (int)(paramDouble5 / 6.283185307179586D);
/* 107 */         paramDouble5 -= i * 2.0D * 3.141592653589793D;
/*     */       }
/*     */ 
/* 111 */       if (paramDouble4 < 0.0D) {
/* 112 */         if (paramDouble4 < -6.283185307179586D - d1) {
/* 113 */           i = (int)(-paramDouble4 / 6.283185307179586D);
/* 114 */           paramDouble4 += i * 2.0D * 3.141592653589793D;
/*     */         }
/*     */ 
/* 117 */         paramDouble4 += 6.283185307179586D;
/*     */       }
/* 119 */       else if (paramDouble4 > 6.283185307179586D + d1) {
/* 120 */         i = (int)(paramDouble4 / 6.283185307179586D);
/* 121 */         paramDouble4 -= i * 2.0D * 3.141592653589793D;
/*     */       }
/*     */ 
/* 125 */       double d2 = paramDouble4 - paramDouble5;
/*     */ 
/* 127 */       if (paramDouble4 < paramDouble5) {
/* 128 */         d2 = Math.abs(d2);
/*     */       }
/*     */ 
/* 131 */       paramDouble5 = (float)(6.283185307179586D - paramDouble5);
/*     */ 
/* 133 */       Arc2D localArc2D = new Arc2D((float)(paramDouble1 - paramDouble3), (float)(paramDouble2 - paramDouble3), (float)(2.0D * paramDouble3), (float)(2.0D * paramDouble3), (float)(paramDouble5 * 180.0D / 3.141592653589793D), (float)(d2 * 180.0D / 3.141592653589793D), 0);
/*     */ 
/* 138 */       PathIterator localPathIterator = localArc2D.getPathIterator(null);
/* 139 */       Stack localStack1 = new Stack();
/* 140 */       Stack localStack2 = new Stack();
/* 141 */       float[] arrayOfFloat = new float[6];
/* 142 */       while (!localPathIterator.isDone()) {
/* 143 */         switch (localPathIterator.currentSegment(arrayOfFloat)) {
/*     */         case 0:
/* 145 */           localStack2.add(Float.valueOf(arrayOfFloat[1]));
/* 146 */           localStack2.add(Float.valueOf(arrayOfFloat[0]));
/* 147 */           break;
/*     */         case 2:
/* 149 */           throw new RuntimeException("Unexpected segment: SEG_QUADTO");
/*     */         case 3:
/* 152 */           localStack2.add(Float.valueOf(arrayOfFloat[1]));
/* 153 */           localStack2.add(Float.valueOf(arrayOfFloat[0]));
/* 154 */           localStack2.add(Float.valueOf(arrayOfFloat[3]));
/* 155 */           localStack2.add(Float.valueOf(arrayOfFloat[2]));
/* 156 */           localStack2.add(Float.valueOf(arrayOfFloat[5]));
/* 157 */           localStack2.add(Float.valueOf(arrayOfFloat[4]));
/* 158 */           localStack1.add(Integer.valueOf(3));
/* 159 */           break;
/*     */         case 4:
/* 161 */           throw new RuntimeException("Unexpected segment: SEG_CLOSE");
/*     */         case 1:
/*     */         }
/*     */ 
/* 165 */         localPathIterator.next();
/*     */       }
/*     */ 
/* 168 */       localStack1.add(Integer.valueOf(0));
/*     */ 
/* 170 */       Path2D localPath2D = new Path2D();
/* 171 */       while (localStack1.size() > 0) {
/* 172 */         switch (((Integer)localStack1.pop()).intValue()) {
/*     */         case 0:
/* 174 */           localPath2D.moveTo(((Float)localStack2.pop()).floatValue(), ((Float)localStack2.pop()).floatValue());
/* 175 */           break;
/*     */         case 1:
/* 177 */           localPath2D.lineTo(((Float)localStack2.pop()).floatValue(), ((Float)localStack2.pop()).floatValue());
/* 178 */           break;
/*     */         case 2:
/* 180 */           localPath2D.quadTo(((Float)localStack2.pop()).floatValue(), ((Float)localStack2.pop()).floatValue(), ((Float)localStack2.pop()).floatValue(), ((Float)localStack2.pop()).floatValue());
/*     */ 
/* 182 */           break;
/*     */         case 3:
/* 184 */           localPath2D.curveTo(((Float)localStack2.pop()).floatValue(), ((Float)localStack2.pop()).floatValue(), ((Float)localStack2.pop()).floatValue(), ((Float)localStack2.pop()).floatValue(), ((Float)localStack2.pop()).floatValue(), ((Float)localStack2.pop()).floatValue());
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 190 */       this.path.append(localPath2D, true);
/*     */     }
/*     */     else
/*     */     {
/*     */       int j;
/* 193 */       if (paramDouble5 < 0.0D) {
/* 194 */         if (paramDouble5 < -6.283185307179586D - d1) {
/* 195 */           j = (int)(-paramDouble5 / 6.283185307179586D);
/* 196 */           paramDouble5 += j * 2.0D * 3.141592653589793D;
/*     */         }
/*     */ 
/* 199 */         paramDouble5 += 6.283185307179586D;
/*     */       }
/* 201 */       else if (paramDouble5 > 6.283185307179586D + d1) {
/* 202 */         j = (int)(paramDouble5 / 6.283185307179586D);
/* 203 */         paramDouble5 -= j * 2.0D * 3.141592653589793D;
/*     */       }
/*     */ 
/* 207 */       if (paramDouble4 < 0.0D) {
/* 208 */         if (paramDouble4 < -6.283185307179586D - d1) {
/* 209 */           j = (int)(-paramDouble4 / 6.283185307179586D);
/* 210 */           paramDouble4 += j * 2.0D * 3.141592653589793D;
/*     */         }
/*     */ 
/* 213 */         paramDouble4 += 6.283185307179586D;
/*     */       }
/* 215 */       else if (paramDouble4 > 6.283185307179586D + d1) {
/* 216 */         j = (int)(paramDouble4 / 6.283185307179586D);
/* 217 */         paramDouble4 -= j * 2.0D * 3.141592653589793D;
/*     */       }
/*     */ 
/* 221 */       double d3 = paramDouble4 - paramDouble5;
/*     */ 
/* 223 */       if (paramDouble4 < paramDouble5) {
/* 224 */         d3 += 6.283185307179586D;
/* 225 */         if (d3 < d1) {
/* 226 */           d3 += 6.283185307179586D;
/*     */         }
/*     */       }
/*     */ 
/* 230 */       if (Math.abs(paramDouble4) > d1) {
/* 231 */         paramDouble4 = (float)(6.283185307179586D - paramDouble4);
/*     */       }
/*     */ 
/* 235 */       this.path.append(new Arc2D((float)(paramDouble1 - paramDouble3), (float)(paramDouble2 - paramDouble3), (float)(2.0D * paramDouble3), (float)(2.0D * paramDouble3), (float)(paramDouble4 * 180.0D / 3.141592653589793D), (float)(d3 * 180.0D / 3.141592653589793D), 0), true);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean contains(int paramInt, double paramDouble1, double paramDouble2)
/*     */   {
/* 244 */     if (log.isLoggable(Level.FINE)) {
/* 245 */       log.log(Level.FINE, "WCPathImpl({0}).contains({1},{2},{3})", new Object[] { Integer.valueOf(getID()), Integer.valueOf(paramInt), Double.valueOf(paramDouble1), Double.valueOf(paramDouble2) });
/*     */     }
/*     */ 
/* 248 */     int i = this.path.getWindingRule();
/* 249 */     this.path.setWindingRule(paramInt);
/* 250 */     boolean bool = this.path.contains((float)paramDouble1, (float)paramDouble2);
/* 251 */     this.path.setWindingRule(i);
/*     */ 
/* 253 */     return bool;
/*     */   }
/*     */ 
/*     */   public WCRectangle getBounds()
/*     */   {
/* 258 */     RectBounds localRectBounds = this.path.getBounds();
/* 259 */     return new WCRectangle(localRectBounds.getMinX(), localRectBounds.getMinY(), localRectBounds.getWidth(), localRectBounds.getHeight());
/*     */   }
/*     */ 
/*     */   public void clear() {
/* 263 */     if (log.isLoggable(Level.FINE)) {
/* 264 */       log.log(Level.FINE, "WCPathImpl({0}).clear()", Integer.valueOf(getID()));
/*     */     }
/* 266 */     this.hasCP = false;
/* 267 */     this.path.reset();
/*     */   }
/*     */ 
/*     */   public void moveTo(double paramDouble1, double paramDouble2) {
/* 271 */     if (log.isLoggable(Level.FINE)) {
/* 272 */       log.log(Level.FINE, "WCPathImpl({0}).moveTo({1},{2})", new Object[] { Integer.valueOf(getID()), Double.valueOf(paramDouble1), Double.valueOf(paramDouble2) });
/*     */     }
/*     */ 
/* 275 */     this.hasCP = true;
/* 276 */     this.path.moveTo((float)paramDouble1, (float)paramDouble2);
/*     */   }
/*     */ 
/*     */   public void addLineTo(double paramDouble1, double paramDouble2) {
/* 280 */     if (log.isLoggable(Level.FINE)) {
/* 281 */       log.log(Level.FINE, "WCPathImpl({0}).addLineTo({1},{2})", new Object[] { Integer.valueOf(getID()), Double.valueOf(paramDouble1), Double.valueOf(paramDouble2) });
/*     */     }
/*     */ 
/* 284 */     this.hasCP = true;
/* 285 */     this.path.lineTo((float)paramDouble1, (float)paramDouble2);
/*     */   }
/*     */ 
/*     */   public void addQuadCurveTo(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/* 289 */     if (log.isLoggable(Level.FINE)) {
/* 290 */       log.log(Level.FINE, "WCPathImpl({0}).addQuadCurveTo({1},{2},{3},{4})", new Object[] { Integer.valueOf(getID()), Double.valueOf(paramDouble1), Double.valueOf(paramDouble2), Double.valueOf(paramDouble3), Double.valueOf(paramDouble4) });
/*     */     }
/*     */ 
/* 293 */     this.hasCP = true;
/* 294 */     this.path.quadTo((float)paramDouble1, (float)paramDouble2, (float)paramDouble3, (float)paramDouble4);
/*     */   }
/*     */ 
/*     */   public void addBezierCurveTo(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6)
/*     */   {
/* 299 */     if (log.isLoggable(Level.FINE)) {
/* 300 */       log.log(Level.FINE, "WCPathImpl({0}).addBezierCurveTo({1},{2},{3},{4},{5},{6})", new Object[] { Integer.valueOf(getID()), Double.valueOf(paramDouble1), Double.valueOf(paramDouble2), Double.valueOf(paramDouble3), Double.valueOf(paramDouble4), Double.valueOf(paramDouble5), Double.valueOf(paramDouble6) });
/*     */     }
/*     */ 
/* 303 */     this.hasCP = true;
/* 304 */     this.path.curveTo((float)paramDouble1, (float)paramDouble2, (float)paramDouble3, (float)paramDouble4, (float)paramDouble5, (float)paramDouble6);
/*     */   }
/*     */ 
/*     */   public void addPath(WCPath paramWCPath)
/*     */   {
/* 309 */     if (log.isLoggable(Level.FINE)) {
/* 310 */       log.log(Level.FINE, "WCPathImpl({0}).addPath({1})", new Object[] { Integer.valueOf(getID()), Integer.valueOf(paramWCPath.getID()) });
/*     */     }
/*     */ 
/* 313 */     this.hasCP = ((this.hasCP) || (((WCPathImpl)paramWCPath).hasCP));
/* 314 */     this.path.append(((WCPathImpl)paramWCPath).path, false);
/*     */   }
/*     */ 
/*     */   public void closeSubpath() {
/* 318 */     if (log.isLoggable(Level.FINE)) {
/* 319 */       log.log(Level.FINE, "WCPathImpl({0}).closeSubpath()", Integer.valueOf(getID()));
/*     */     }
/* 321 */     this.path.closePath();
/*     */   }
/*     */ 
/*     */   public boolean hasCurrentPoint() {
/* 325 */     return this.hasCP;
/*     */   }
/*     */ 
/*     */   public boolean isEmpty() {
/* 329 */     PathIterator localPathIterator = this.path.getPathIterator(null);
/* 330 */     float[] arrayOfFloat = new float[6];
/* 331 */     while (!localPathIterator.isDone()) {
/* 332 */       switch (localPathIterator.currentSegment(arrayOfFloat)) {
/*     */       case 1:
/*     */       case 2:
/*     */       case 3:
/* 336 */         return false;
/*     */       }
/* 338 */       localPathIterator.next();
/*     */     }
/* 340 */     return true;
/*     */   }
/*     */ 
/*     */   public int getWindingRule() {
/* 344 */     return 1 - this.path.getWindingRule();
/*     */   }
/*     */ 
/*     */   public void setWindingRule(int paramInt) {
/* 348 */     this.path.setWindingRule(1 - paramInt);
/*     */   }
/*     */ 
/*     */   public Path2D getPlatformPath() {
/* 352 */     if (log.isLoggable(Level.FINE)) {
/* 353 */       log.log(Level.FINE, "WCPathImpl({0}).getPath() BEGIN=====", Integer.valueOf(getID()));
/* 354 */       PathIterator localPathIterator = this.path.getPathIterator(null);
/* 355 */       float[] arrayOfFloat = new float[6];
/* 356 */       while (!localPathIterator.isDone()) {
/* 357 */         switch (localPathIterator.currentSegment(arrayOfFloat)) {
/*     */         case 0:
/* 359 */           log.log(Level.FINE, "SEG_MOVETO ({0},{1})", new Object[] { Float.valueOf(arrayOfFloat[0]), Float.valueOf(arrayOfFloat[1]) });
/*     */ 
/* 361 */           break;
/*     */         case 1:
/* 363 */           log.log(Level.FINE, "SEG_LINETO ({0},{1})", new Object[] { Float.valueOf(arrayOfFloat[0]), Float.valueOf(arrayOfFloat[1]) });
/*     */ 
/* 365 */           break;
/*     */         case 2:
/* 367 */           log.log(Level.FINE, "SEG_QUADTO ({0},{1},{2},{3})", new Object[] { Float.valueOf(arrayOfFloat[0]), Float.valueOf(arrayOfFloat[1]), Float.valueOf(arrayOfFloat[2]), Float.valueOf(arrayOfFloat[3]) });
/*     */ 
/* 369 */           break;
/*     */         case 3:
/* 371 */           log.log(Level.FINE, "SEG_CUBICTO ({0},{1},{2},{3},{4},{5})", new Object[] { Float.valueOf(arrayOfFloat[0]), Float.valueOf(arrayOfFloat[1]), Float.valueOf(arrayOfFloat[2]), Float.valueOf(arrayOfFloat[3]), Float.valueOf(arrayOfFloat[4]), Float.valueOf(arrayOfFloat[5]) });
/*     */ 
/* 374 */           break;
/*     */         case 4:
/* 376 */           log.fine("SEG_CLOSE");
/*     */         }
/*     */ 
/* 379 */         localPathIterator.next();
/*     */       }
/* 381 */       log.fine("========getPath() END=====");
/*     */     }
/* 383 */     return this.path;
/*     */   }
/*     */ 
/*     */   public WCPathIterator getPathIterator() {
/* 387 */     final PathIterator localPathIterator = this.path.getPathIterator(null);
/* 388 */     return new WCPathIterator() {
/*     */       public int getWindingRule() {
/* 390 */         return localPathIterator.getWindingRule();
/*     */       }
/*     */ 
/*     */       public boolean isDone() {
/* 394 */         return localPathIterator.isDone();
/*     */       }
/*     */ 
/*     */       public void next() {
/* 398 */         localPathIterator.next();
/*     */       }
/*     */ 
/*     */       public int currentSegment(double[] paramAnonymousArrayOfDouble) {
/* 402 */         float[] arrayOfFloat = new float[6];
/* 403 */         int i = localPathIterator.currentSegment(arrayOfFloat);
/* 404 */         for (int j = 0; j < paramAnonymousArrayOfDouble.length; j++) {
/* 405 */           paramAnonymousArrayOfDouble[j] = arrayOfFloat[j];
/*     */         }
/* 407 */         return i;
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public void translate(double paramDouble1, double paramDouble2)
/*     */   {
/* 414 */     if (log.isLoggable(Level.FINE)) {
/* 415 */       log.log(Level.FINE, "WCPathImpl({0}).translate({1}, {2})", new Object[] { Integer.valueOf(getID()), Double.valueOf(paramDouble1), Double.valueOf(paramDouble2) });
/*     */     }
/*     */ 
/* 418 */     this.path.transform(BaseTransform.getTranslateInstance(paramDouble1, paramDouble2));
/*     */   }
/*     */ 
/*     */   public void transform(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6)
/*     */   {
/* 425 */     if (log.isLoggable(Level.FINE)) {
/* 426 */       log.log(Level.FINE, "WCPathImpl({0}).transform({1},{2},{3},{4},{5},{6})", new Object[] { Integer.valueOf(getID()), Double.valueOf(paramDouble1), Double.valueOf(paramDouble2), Double.valueOf(paramDouble3), Double.valueOf(paramDouble4), Double.valueOf(paramDouble5), Double.valueOf(paramDouble6) });
/*     */     }
/*     */ 
/* 429 */     this.path.transform(BaseTransform.getInstance(paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6));
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.sg.prism.WCPathImpl
 * JD-Core Version:    0.6.2
 */