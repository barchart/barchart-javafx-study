/*     */ package com.sun.prism.impl.shape;
/*     */ 
/*     */ import com.sun.javafx.geom.Path2D;
/*     */ import com.sun.javafx.geom.PathConsumer2D;
/*     */ import com.sun.javafx.geom.PathIterator;
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.Shape;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.openpisces.Dasher;
/*     */ import com.sun.openpisces.Renderer;
/*     */ import com.sun.openpisces.Stroker;
/*     */ import com.sun.openpisces.TransformingPathConsumer2D.FilterSet;
/*     */ import com.sun.prism.BasicStroke;
/*     */ 
/*     */ public class OpenPiscesPrismUtils
/*     */ {
/*  20 */   private static final Renderer savedRenderer = new Renderer(3, 3);
/*  21 */   private static final Stroker savedStroker = new Stroker(savedRenderer);
/*  22 */   private static final Dasher savedDasher = new Dasher(savedStroker);
/*     */ 
/*  24 */   private static TransformingPathConsumer2D.FilterSet transformer = new TransformingPathConsumer2D.FilterSet();
/*     */ 
/*     */   private static PathConsumer2D initRenderer(BasicStroke paramBasicStroke, BaseTransform paramBaseTransform, Rectangle paramRectangle, int paramInt)
/*     */   {
/*  32 */     int i = (paramBasicStroke == null) && (paramInt == 0) ? 0 : 1;
/*     */ 
/*  34 */     savedRenderer.reset(paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height, i);
/*  35 */     Object localObject = transformer.getConsumer(savedRenderer, paramBaseTransform);
/*  36 */     if (paramBasicStroke != null) {
/*  37 */       savedStroker.reset(paramBasicStroke.getLineWidth(), paramBasicStroke.getEndCap(), paramBasicStroke.getLineJoin(), paramBasicStroke.getMiterLimit());
/*     */ 
/*  39 */       savedStroker.setConsumer((PathConsumer2D)localObject);
/*  40 */       localObject = savedStroker;
/*  41 */       float[] arrayOfFloat = paramBasicStroke.getDashArray();
/*  42 */       if (arrayOfFloat != null) {
/*  43 */         savedDasher.reset(arrayOfFloat, paramBasicStroke.getDashPhase());
/*  44 */         localObject = savedDasher;
/*     */       }
/*     */     }
/*  47 */     return localObject;
/*     */   }
/*     */ 
/*     */   public static void feedConsumer(PathIterator paramPathIterator, PathConsumer2D paramPathConsumer2D) {
/*  51 */     float[] arrayOfFloat = new float[6];
/*  52 */     while (!paramPathIterator.isDone()) {
/*  53 */       int i = paramPathIterator.currentSegment(arrayOfFloat);
/*  54 */       switch (i) {
/*     */       case 0:
/*  56 */         paramPathConsumer2D.moveTo(arrayOfFloat[0], arrayOfFloat[1]);
/*  57 */         break;
/*     */       case 1:
/*  59 */         paramPathConsumer2D.lineTo(arrayOfFloat[0], arrayOfFloat[1]);
/*  60 */         break;
/*     */       case 2:
/*  62 */         paramPathConsumer2D.quadTo(arrayOfFloat[0], arrayOfFloat[1], arrayOfFloat[2], arrayOfFloat[3]);
/*     */ 
/*  64 */         break;
/*     */       case 3:
/*  66 */         paramPathConsumer2D.curveTo(arrayOfFloat[0], arrayOfFloat[1], arrayOfFloat[2], arrayOfFloat[3], arrayOfFloat[4], arrayOfFloat[5]);
/*     */ 
/*  69 */         break;
/*     */       case 4:
/*  71 */         paramPathConsumer2D.closePath();
/*     */       }
/*     */ 
/*  74 */       paramPathIterator.next();
/*     */     }
/*  76 */     paramPathConsumer2D.pathDone();
/*     */   }
/*     */ 
/*     */   public static Renderer setupRenderer(Shape paramShape, BasicStroke paramBasicStroke, BaseTransform paramBaseTransform, Rectangle paramRectangle)
/*     */   {
/*  84 */     PathIterator localPathIterator = paramShape.getPathIterator(null);
/*  85 */     feedConsumer(localPathIterator, initRenderer(paramBasicStroke, paramBaseTransform, paramRectangle, localPathIterator.getWindingRule()));
/*  86 */     return savedRenderer;
/*     */   }
/*     */ 
/*     */   public static Renderer setupRenderer(Path2D paramPath2D, BasicStroke paramBasicStroke, BaseTransform paramBaseTransform, Rectangle paramRectangle)
/*     */   {
/*  94 */     PathConsumer2D localPathConsumer2D = initRenderer(paramBasicStroke, paramBaseTransform, paramRectangle, paramPath2D.getWindingRule());
/*     */ 
/*  96 */     float[] arrayOfFloat = paramPath2D.getFloatCoordsNoClone();
/*  97 */     byte[] arrayOfByte = paramPath2D.getCommandsNoClone();
/*  98 */     int i = paramPath2D.getNumCommands();
/*  99 */     int j = 0;
/* 100 */     for (int k = 0; k < i; k++) {
/* 101 */       switch (arrayOfByte[k]) {
/*     */       case 0:
/* 103 */         localPathConsumer2D.moveTo(arrayOfFloat[(j + 0)], arrayOfFloat[(j + 1)]);
/* 104 */         j += 2;
/* 105 */         break;
/*     */       case 1:
/* 107 */         localPathConsumer2D.lineTo(arrayOfFloat[(j + 0)], arrayOfFloat[(j + 1)]);
/* 108 */         j += 2;
/* 109 */         break;
/*     */       case 2:
/* 111 */         localPathConsumer2D.quadTo(arrayOfFloat[(j + 0)], arrayOfFloat[(j + 1)], arrayOfFloat[(j + 2)], arrayOfFloat[(j + 3)]);
/*     */ 
/* 113 */         j += 4;
/* 114 */         break;
/*     */       case 3:
/* 116 */         localPathConsumer2D.curveTo(arrayOfFloat[(j + 0)], arrayOfFloat[(j + 1)], arrayOfFloat[(j + 2)], arrayOfFloat[(j + 3)], arrayOfFloat[(j + 4)], arrayOfFloat[(j + 5)]);
/*     */ 
/* 119 */         j += 6;
/* 120 */         break;
/*     */       case 4:
/* 122 */         localPathConsumer2D.closePath();
/*     */       }
/*     */     }
/*     */ 
/* 126 */     localPathConsumer2D.pathDone();
/* 127 */     return savedRenderer;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.impl.shape.OpenPiscesPrismUtils
 * JD-Core Version:    0.6.2
 */