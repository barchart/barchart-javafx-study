/*      */ package javafx.scene.canvas;
/*      */ 
/*      */ import com.sun.javafx.geom.Arc2D;
/*      */ import com.sun.javafx.geom.Path2D;
/*      */ import com.sun.javafx.geom.PathIterator;
/*      */ import com.sun.javafx.geom.transform.Affine2D;
/*      */ import com.sun.javafx.geom.transform.NoninvertibleTransformException;
/*      */ import com.sun.javafx.image.BytePixelGetter;
/*      */ import com.sun.javafx.image.BytePixelSetter;
/*      */ import com.sun.javafx.image.ByteToBytePixelConverter;
/*      */ import com.sun.javafx.image.IntPixelGetter;
/*      */ import com.sun.javafx.image.IntToBytePixelConverter;
/*      */ import com.sun.javafx.image.PixelConverter;
/*      */ import com.sun.javafx.image.PixelGetter;
/*      */ import com.sun.javafx.image.PixelUtils;
/*      */ import com.sun.javafx.image.impl.ByteBgraPre;
/*      */ import com.sun.javafx.sg.GrowableDataBuffer;
/*      */ import java.nio.Buffer;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.IntBuffer;
/*      */ import java.util.LinkedList;
/*      */ import javafx.geometry.VPos;
/*      */ import javafx.scene.effect.BlendMode;
/*      */ import javafx.scene.effect.Effect;
/*      */ import javafx.scene.image.Image;
/*      */ import javafx.scene.image.PixelFormat;
/*      */ import javafx.scene.image.PixelFormat.Type;
/*      */ import javafx.scene.image.PixelReader;
/*      */ import javafx.scene.image.PixelWriter;
/*      */ import javafx.scene.paint.Color;
/*      */ import javafx.scene.paint.Paint;
/*      */ import javafx.scene.shape.ArcType;
/*      */ import javafx.scene.shape.FillRule;
/*      */ import javafx.scene.shape.StrokeLineCap;
/*      */ import javafx.scene.shape.StrokeLineJoin;
/*      */ import javafx.scene.text.Font;
/*      */ import javafx.scene.text.TextAlignment;
/*      */ import javafx.scene.transform.Affine;
/*      */ 
/*      */ public final class GraphicsContext
/*      */ {
/*      */   Canvas theCanvas;
/*      */   Path2D path;
/*      */   boolean pathDirty;
/*      */   State curState;
/*      */   LinkedList<State> stateStack;
/*      */   LinkedList<Path2D> clipStack;
/*  221 */   private static float[] coords = new float[6];
/*  222 */   private static final byte[] pgtype = { 41, 42, 43, 44, 45 };
/*      */ 
/*  229 */   private static final int[] numsegs = { 2, 2, 4, 6, 0 };
/*      */ 
/*  302 */   private float[] polybuf = new float[512];
/*      */   private boolean txdirty;
/*  504 */   private static Affine2D scratchTX = new Affine2D();
/*      */ 
/*  638 */   private static javafx.scene.effect.Blend TMP_BLEND = new javafx.scene.effect.Blend(BlendMode.SRC_OVER);
/*      */ 
/* 1240 */   private static final Arc2D TEMP_ARC = new Arc2D();
/*      */   private PixelWriter writer;
/*      */ 
/*      */   GraphicsContext(Canvas paramCanvas)
/*      */   {
/*  117 */     this.theCanvas = paramCanvas;
/*  118 */     this.path = new Path2D();
/*  119 */     this.pathDirty = true;
/*      */ 
/*  121 */     this.curState = new State();
/*  122 */     this.stateStack = new LinkedList();
/*  123 */     this.clipStack = new LinkedList();
/*      */   }
/*      */ 
/*      */   private GrowableDataBuffer getBuffer()
/*      */   {
/*  218 */     return this.theCanvas.getBuffer();
/*      */   }
/*      */ 
/*      */   private void markPathDirty()
/*      */   {
/*  232 */     this.pathDirty = true;
/*      */   }
/*      */ 
/*      */   private void writePath(byte paramByte) {
/*  236 */     updateTransform();
/*  237 */     GrowableDataBuffer localGrowableDataBuffer = getBuffer();
/*  238 */     if (this.pathDirty) {
/*  239 */       localGrowableDataBuffer.putByte((byte)40);
/*  240 */       PathIterator localPathIterator = this.path.getPathIterator(null);
/*  241 */       while (!localPathIterator.isDone()) {
/*  242 */         int i = localPathIterator.currentSegment(coords);
/*  243 */         localGrowableDataBuffer.putByte(pgtype[i]);
/*  244 */         for (int j = 0; j < numsegs[i]; j++) {
/*  245 */           localGrowableDataBuffer.putFloat(coords[j]);
/*      */         }
/*  247 */         localPathIterator.next();
/*      */       }
/*  249 */       localGrowableDataBuffer.putByte((byte)46);
/*  250 */       this.pathDirty = false;
/*      */     }
/*  252 */     localGrowableDataBuffer.putByte(paramByte);
/*      */   }
/*      */ 
/*      */   private void writePaint(Paint paramPaint, byte paramByte) {
/*  256 */     GrowableDataBuffer localGrowableDataBuffer = getBuffer();
/*  257 */     localGrowableDataBuffer.putByte(paramByte);
/*  258 */     localGrowableDataBuffer.putObject(paramPaint.impl_getPlatformPaint());
/*      */   }
/*      */ 
/*      */   private void writeArcType(ArcType paramArcType)
/*      */   {
/*      */     byte b;
/*  263 */     switch (2.$SwitchMap$javafx$scene$shape$ArcType[paramArcType.ordinal()]) { case 1:
/*  264 */       b = 0; break;
/*      */     case 2:
/*  265 */       b = 1; break;
/*      */     case 3:
/*  266 */       b = 2; break;
/*      */     default:
/*  267 */       return;
/*      */     }
/*  269 */     writeParam(b, (byte)15);
/*      */   }
/*      */ 
/*      */   private void writeRectParams(GrowableDataBuffer paramGrowableDataBuffer, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, byte paramByte)
/*      */   {
/*  276 */     paramGrowableDataBuffer.putByte(paramByte);
/*  277 */     paramGrowableDataBuffer.putFloat((float)paramDouble1);
/*  278 */     paramGrowableDataBuffer.putFloat((float)paramDouble2);
/*  279 */     paramGrowableDataBuffer.putFloat((float)paramDouble3);
/*  280 */     paramGrowableDataBuffer.putFloat((float)paramDouble4);
/*      */   }
/*      */ 
/*      */   private void writeOp4(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, byte paramByte) {
/*  284 */     updateTransform();
/*  285 */     writeRectParams(getBuffer(), paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramByte);
/*      */   }
/*      */ 
/*      */   private void writeOp6(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, byte paramByte)
/*      */   {
/*  291 */     updateTransform();
/*  292 */     GrowableDataBuffer localGrowableDataBuffer = getBuffer();
/*  293 */     localGrowableDataBuffer.putByte(paramByte);
/*  294 */     localGrowableDataBuffer.putFloat((float)paramDouble1);
/*  295 */     localGrowableDataBuffer.putFloat((float)paramDouble2);
/*  296 */     localGrowableDataBuffer.putFloat((float)paramDouble3);
/*  297 */     localGrowableDataBuffer.putFloat((float)paramDouble4);
/*  298 */     localGrowableDataBuffer.putFloat((float)paramDouble5);
/*  299 */     localGrowableDataBuffer.putFloat((float)paramDouble6);
/*      */   }
/*      */ 
/*      */   private void flushPolyBuf(GrowableDataBuffer paramGrowableDataBuffer, float[] paramArrayOfFloat, int paramInt, byte paramByte)
/*      */   {
/*  306 */     this.curState.transform.deltaTransform(paramArrayOfFloat, 0, paramArrayOfFloat, 0, paramInt / 2);
/*  307 */     for (int i = 0; i < paramInt; i += 2) {
/*  308 */       paramGrowableDataBuffer.putByte(paramByte);
/*  309 */       paramGrowableDataBuffer.putFloat(paramArrayOfFloat[i]);
/*  310 */       paramGrowableDataBuffer.putFloat(paramArrayOfFloat[(i + 1)]);
/*  311 */       paramByte = 42;
/*      */     }
/*      */   }
/*      */ 
/*      */   private void writePoly(double[] paramArrayOfDouble1, double[] paramArrayOfDouble2, int paramInt, boolean paramBoolean, byte paramByte)
/*      */   {
/*  317 */     GrowableDataBuffer localGrowableDataBuffer = getBuffer();
/*  318 */     localGrowableDataBuffer.putByte((byte)40);
/*  319 */     int i = 0;
/*  320 */     byte b = 41;
/*  321 */     for (int j = 0; j < paramInt; j++) {
/*  322 */       if (i >= this.polybuf.length) {
/*  323 */         flushPolyBuf(localGrowableDataBuffer, this.polybuf, i, b);
/*  324 */         b = 42;
/*      */       }
/*  326 */       this.polybuf[(i++)] = ((float)paramArrayOfDouble1[j]);
/*  327 */       this.polybuf[(i++)] = ((float)paramArrayOfDouble2[j]);
/*      */     }
/*  329 */     flushPolyBuf(localGrowableDataBuffer, this.polybuf, i, b);
/*  330 */     if (paramBoolean) {
/*  331 */       localGrowableDataBuffer.putByte((byte)45);
/*      */     }
/*  333 */     localGrowableDataBuffer.putByte(paramByte);
/*      */ 
/*  335 */     markPathDirty();
/*      */   }
/*      */ 
/*      */   private void writeImage(Image paramImage, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*      */   {
/*  341 */     if (paramImage.getProgress() < 1.0D) return;
/*  342 */     Object localObject = paramImage.impl_getPlatformImage();
/*  343 */     if (localObject == null) return;
/*  344 */     updateTransform();
/*  345 */     GrowableDataBuffer localGrowableDataBuffer = getBuffer();
/*  346 */     writeRectParams(localGrowableDataBuffer, paramDouble1, paramDouble2, paramDouble3, paramDouble4, (byte)50);
/*  347 */     localGrowableDataBuffer.putObject(localObject);
/*      */   }
/*      */ 
/*      */   private void writeImage(Image paramImage, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8)
/*      */   {
/*  354 */     if (paramImage.getProgress() < 1.0D) return;
/*  355 */     Object localObject = paramImage.impl_getPlatformImage();
/*  356 */     if (localObject == null) return;
/*  357 */     updateTransform();
/*  358 */     GrowableDataBuffer localGrowableDataBuffer = getBuffer();
/*  359 */     writeRectParams(localGrowableDataBuffer, paramDouble1, paramDouble2, paramDouble3, paramDouble4, (byte)51);
/*  360 */     localGrowableDataBuffer.putFloat((float)paramDouble5);
/*  361 */     localGrowableDataBuffer.putFloat((float)paramDouble6);
/*  362 */     localGrowableDataBuffer.putFloat((float)paramDouble7);
/*  363 */     localGrowableDataBuffer.putFloat((float)paramDouble8);
/*  364 */     localGrowableDataBuffer.putObject(localObject);
/*      */   }
/*      */ 
/*      */   private void writeText(String paramString, double paramDouble1, double paramDouble2, double paramDouble3, byte paramByte)
/*      */   {
/*  370 */     updateTransform();
/*  371 */     GrowableDataBuffer localGrowableDataBuffer = getBuffer();
/*  372 */     localGrowableDataBuffer.putByte(paramByte);
/*  373 */     localGrowableDataBuffer.putFloat((float)paramDouble1);
/*  374 */     localGrowableDataBuffer.putFloat((float)paramDouble2);
/*  375 */     localGrowableDataBuffer.putFloat((float)paramDouble3);
/*  376 */     localGrowableDataBuffer.putObject(paramString);
/*      */   }
/*      */ 
/*      */   private void writeParam(double paramDouble, byte paramByte) {
/*  380 */     GrowableDataBuffer localGrowableDataBuffer = getBuffer();
/*  381 */     localGrowableDataBuffer.putByte(paramByte);
/*  382 */     localGrowableDataBuffer.putFloat((float)paramDouble);
/*      */   }
/*      */ 
/*      */   private void writeParam(byte paramByte1, byte paramByte2) {
/*  386 */     GrowableDataBuffer localGrowableDataBuffer = getBuffer();
/*  387 */     localGrowableDataBuffer.putByte(paramByte2);
/*  388 */     localGrowableDataBuffer.putByte(paramByte1);
/*      */   }
/*      */ 
/*      */   private void updateTransform()
/*      */   {
/*  393 */     if (this.txdirty) {
/*  394 */       this.txdirty = false;
/*  395 */       GrowableDataBuffer localGrowableDataBuffer = getBuffer();
/*  396 */       localGrowableDataBuffer.putByte((byte)11);
/*  397 */       localGrowableDataBuffer.putDouble(this.curState.transform.getMxx());
/*  398 */       localGrowableDataBuffer.putDouble(this.curState.transform.getMxy());
/*  399 */       localGrowableDataBuffer.putDouble(this.curState.transform.getMxt());
/*  400 */       localGrowableDataBuffer.putDouble(this.curState.transform.getMyx());
/*  401 */       localGrowableDataBuffer.putDouble(this.curState.transform.getMyy());
/*  402 */       localGrowableDataBuffer.putDouble(this.curState.transform.getMyt());
/*      */     }
/*      */   }
/*      */ 
/*      */   public Canvas getCanvas()
/*      */   {
/*  415 */     return this.theCanvas;
/*      */   }
/*      */ 
/*      */   public void save()
/*      */   {
/*  441 */     this.stateStack.push(this.curState.copy());
/*      */   }
/*      */ 
/*      */   public void restore()
/*      */   {
/*  468 */     if (!this.stateStack.isEmpty()) {
/*  469 */       State localState = (State)this.stateStack.pop();
/*  470 */       localState.restore(this);
/*  471 */       this.txdirty = true;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void translate(double paramDouble1, double paramDouble2)
/*      */   {
/*  481 */     this.curState.transform.translate(paramDouble1, paramDouble2);
/*  482 */     this.txdirty = true;
/*      */   }
/*      */ 
/*      */   public void scale(double paramDouble1, double paramDouble2)
/*      */   {
/*  491 */     this.curState.transform.scale(paramDouble1, paramDouble2);
/*  492 */     this.txdirty = true;
/*      */   }
/*      */ 
/*      */   public void rotate(double paramDouble)
/*      */   {
/*  500 */     this.curState.transform.rotate(Math.toRadians(paramDouble));
/*  501 */     this.txdirty = true;
/*      */   }
/*      */ 
/*      */   public void transform(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6)
/*      */   {
/*  519 */     scratchTX.setTransform(paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6);
/*      */ 
/*  522 */     this.curState.transform.concatenate(scratchTX);
/*  523 */     this.txdirty = true;
/*      */   }
/*      */ 
/*      */   public void transform(Affine paramAffine)
/*      */   {
/*  534 */     scratchTX.setTransform(paramAffine.getMxx(), paramAffine.getMyx(), paramAffine.getMxy(), paramAffine.getMyy(), paramAffine.getTx(), paramAffine.getTy());
/*      */ 
/*  537 */     this.curState.transform.concatenate(scratchTX);
/*  538 */     this.txdirty = true;
/*      */   }
/*      */ 
/*      */   public void setTransform(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6)
/*      */   {
/*  554 */     this.curState.transform.setTransform(paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6);
/*      */ 
/*  557 */     this.txdirty = true;
/*      */   }
/*      */ 
/*      */   public void setTransform(Affine paramAffine)
/*      */   {
/*  568 */     this.curState.transform.setTransform(paramAffine.getMxx(), paramAffine.getMyx(), paramAffine.getMxy(), paramAffine.getMyy(), paramAffine.getTx(), paramAffine.getTy());
/*      */ 
/*  571 */     this.txdirty = true;
/*      */   }
/*      */ 
/*      */   public Affine getTransform(Affine paramAffine)
/*      */   {
/*  586 */     if (paramAffine == null) {
/*  587 */       paramAffine = new Affine();
/*      */     }
/*      */ 
/*  590 */     paramAffine.setMxx(this.curState.transform.getMxx());
/*  591 */     paramAffine.setMxy(this.curState.transform.getMxy());
/*  592 */     paramAffine.setMxz(0.0D);
/*  593 */     paramAffine.setTx(this.curState.transform.getMxt());
/*  594 */     paramAffine.setMyx(this.curState.transform.getMyx());
/*  595 */     paramAffine.setMyy(this.curState.transform.getMyy());
/*  596 */     paramAffine.setMyz(0.0D);
/*  597 */     paramAffine.setTy(this.curState.transform.getMyt());
/*  598 */     paramAffine.setMzx(0.0D);
/*  599 */     paramAffine.setMzy(0.0D);
/*  600 */     paramAffine.setMzz(1.0D);
/*  601 */     paramAffine.setTz(0.0D);
/*      */ 
/*  603 */     return paramAffine;
/*      */   }
/*      */ 
/*      */   public Affine getTransform()
/*      */   {
/*  612 */     return getTransform(null);
/*      */   }
/*      */ 
/*      */   public void setGlobalAlpha(double paramDouble)
/*      */   {
/*  622 */     if (this.curState.globalAlpha != paramDouble) {
/*  623 */       this.curState.globalAlpha = paramDouble;
/*  624 */       paramDouble = paramDouble < 0.0D ? 0.0D : paramDouble > 1.0D ? 1.0D : paramDouble;
/*  625 */       writeParam(paramDouble, (byte)0);
/*      */     }
/*      */   }
/*      */ 
/*      */   public double getGlobalAlpha()
/*      */   {
/*  635 */     return this.curState.globalAlpha;
/*      */   }
/*      */ 
/*      */   public void setGlobalBlendMode(BlendMode paramBlendMode)
/*      */   {
/*  645 */     if ((paramBlendMode != null) && (paramBlendMode != this.curState.blendop)) {
/*  646 */       GrowableDataBuffer localGrowableDataBuffer = getBuffer();
/*  647 */       this.curState.blendop = paramBlendMode;
/*  648 */       TMP_BLEND.setMode(paramBlendMode);
/*  649 */       TMP_BLEND.impl_sync();
/*  650 */       localGrowableDataBuffer.putByte((byte)1);
/*  651 */       localGrowableDataBuffer.putObject(((com.sun.scenario.effect.Blend)TMP_BLEND.impl_getImpl()).getMode());
/*      */     }
/*      */   }
/*      */ 
/*      */   public BlendMode getGlobalBlendMode()
/*      */   {
/*  661 */     return this.curState.blendop;
/*      */   }
/*      */ 
/*      */   public void setFill(Paint paramPaint)
/*      */   {
/*  671 */     if (this.curState.fill != paramPaint) {
/*  672 */       this.curState.fill = paramPaint;
/*  673 */       writePaint(paramPaint, (byte)2);
/*      */     }
/*      */   }
/*      */ 
/*      */   public Paint getFill()
/*      */   {
/*  684 */     return this.curState.fill;
/*      */   }
/*      */ 
/*      */   public void setStroke(Paint paramPaint)
/*      */   {
/*  693 */     if (this.curState.stroke != paramPaint) {
/*  694 */       this.curState.stroke = paramPaint;
/*  695 */       writePaint(paramPaint, (byte)3);
/*      */     }
/*      */   }
/*      */ 
/*      */   public Paint getStroke()
/*      */   {
/*  705 */     return this.curState.stroke;
/*      */   }
/*      */ 
/*      */   public void setLineWidth(double paramDouble)
/*      */   {
/*  717 */     if ((paramDouble > 0.0D) && (paramDouble < (1.0D / 0.0D)) && 
/*  718 */       (this.curState.linewidth != paramDouble)) {
/*  719 */       this.curState.linewidth = paramDouble;
/*  720 */       writeParam(paramDouble, (byte)4);
/*      */     }
/*      */   }
/*      */ 
/*      */   public double getLineWidth()
/*      */   {
/*  731 */     return this.curState.linewidth;
/*      */   }
/*      */ 
/*      */   public void setLineCap(StrokeLineCap paramStrokeLineCap)
/*      */   {
/*  740 */     if (this.curState.linecap != paramStrokeLineCap)
/*      */     {
/*      */       byte b;
/*  742 */       switch (2.$SwitchMap$javafx$scene$shape$StrokeLineCap[paramStrokeLineCap.ordinal()]) { case 1:
/*  743 */         b = 0; break;
/*      */       case 2:
/*  744 */         b = 1; break;
/*      */       case 3:
/*  745 */         b = 2; break;
/*      */       default:
/*  746 */         return;
/*      */       }
/*  748 */       this.curState.linecap = paramStrokeLineCap;
/*  749 */       writeParam(b, (byte)5);
/*      */     }
/*      */   }
/*      */ 
/*      */   public StrokeLineCap getLineCap()
/*      */   {
/*  759 */     return this.curState.linecap;
/*      */   }
/*      */ 
/*      */   public void setLineJoin(StrokeLineJoin paramStrokeLineJoin)
/*      */   {
/*  768 */     if (this.curState.linejoin != paramStrokeLineJoin)
/*      */     {
/*      */       byte b;
/*  770 */       switch (2.$SwitchMap$javafx$scene$shape$StrokeLineJoin[paramStrokeLineJoin.ordinal()]) { case 1:
/*  771 */         b = 0; break;
/*      */       case 2:
/*  772 */         b = 2; break;
/*      */       case 3:
/*  773 */         b = 1; break;
/*      */       default:
/*  774 */         return;
/*      */       }
/*  776 */       this.curState.linejoin = paramStrokeLineJoin;
/*  777 */       writeParam(b, (byte)6);
/*      */     }
/*      */   }
/*      */ 
/*      */   public StrokeLineJoin getLineJoin()
/*      */   {
/*  787 */     return this.curState.linejoin;
/*      */   }
/*      */ 
/*      */   public void setMiterLimit(double paramDouble)
/*      */   {
/*  799 */     if ((paramDouble > 0.0D) && (paramDouble < (1.0D / 0.0D)) && 
/*  800 */       (this.curState.miterlimit != paramDouble)) {
/*  801 */       this.curState.miterlimit = paramDouble;
/*  802 */       writeParam(paramDouble, (byte)7);
/*      */     }
/*      */   }
/*      */ 
/*      */   public double getMiterLimit()
/*      */   {
/*  813 */     return this.curState.miterlimit;
/*      */   }
/*      */ 
/*      */   public void setFont(Font paramFont)
/*      */   {
/*  822 */     if (this.curState.font != paramFont) {
/*  823 */       this.curState.font = paramFont;
/*  824 */       GrowableDataBuffer localGrowableDataBuffer = getBuffer();
/*  825 */       localGrowableDataBuffer.putByte((byte)8);
/*  826 */       localGrowableDataBuffer.putObject(paramFont.impl_getNativeFont());
/*      */     }
/*      */   }
/*      */ 
/*      */   public Font getFont()
/*      */   {
/*  836 */     return this.curState.font;
/*      */   }
/*      */ 
/*      */   public void setTextAlign(TextAlignment paramTextAlignment)
/*      */   {
/*  861 */     if (this.curState.textalign != paramTextAlignment)
/*      */     {
/*      */       byte b;
/*  863 */       switch (2.$SwitchMap$javafx$scene$text$TextAlignment[paramTextAlignment.ordinal()]) { case 1:
/*  864 */         b = 0; break;
/*      */       case 2:
/*  865 */         b = 1; break;
/*      */       case 3:
/*  866 */         b = 2; break;
/*      */       case 4:
/*  867 */         b = 3; break;
/*      */       default:
/*  868 */         return;
/*      */       }
/*  870 */       this.curState.textalign = paramTextAlignment;
/*  871 */       writeParam(b, (byte)9);
/*      */     }
/*      */   }
/*      */ 
/*      */   public TextAlignment getTextAlign()
/*      */   {
/*  882 */     return this.curState.textalign;
/*      */   }
/*      */ 
/*      */   public void setTextBaseline(VPos paramVPos)
/*      */   {
/*  891 */     if (this.curState.textbaseline != paramVPos)
/*      */     {
/*      */       byte b;
/*  893 */       switch (2.$SwitchMap$javafx$geometry$VPos[paramVPos.ordinal()]) { case 1:
/*  894 */         b = 0; break;
/*      */       case 2:
/*  895 */         b = 1; break;
/*      */       case 3:
/*  896 */         b = 2; break;
/*      */       case 4:
/*  897 */         b = 3; break;
/*      */       default:
/*  898 */         return;
/*      */       }
/*  900 */       this.curState.textbaseline = paramVPos;
/*  901 */       writeParam(b, (byte)10);
/*      */     }
/*      */   }
/*      */ 
/*      */   public VPos getTextBaseline()
/*      */   {
/*  911 */     return this.curState.textbaseline;
/*      */   }
/*      */ 
/*      */   public void fillText(String paramString, double paramDouble1, double paramDouble2)
/*      */   {
/*  923 */     writeText(paramString, paramDouble1, paramDouble2, 0.0D, (byte)30);
/*      */   }
/*      */ 
/*      */   public void strokeText(String paramString, double paramDouble1, double paramDouble2)
/*      */   {
/*  935 */     writeText(paramString, paramDouble1, paramDouble2, 0.0D, (byte)31);
/*      */   }
/*      */ 
/*      */   public void fillText(String paramString, double paramDouble1, double paramDouble2, double paramDouble3)
/*      */   {
/*  950 */     if (paramDouble3 <= 0.0D) return;
/*  951 */     writeText(paramString, paramDouble1, paramDouble2, paramDouble3, (byte)30);
/*      */   }
/*      */ 
/*      */   public void strokeText(String paramString, double paramDouble1, double paramDouble2, double paramDouble3)
/*      */   {
/*  966 */     if (paramDouble3 <= 0.0D) return;
/*  967 */     writeText(paramString, paramDouble1, paramDouble2, paramDouble3, (byte)31);
/*      */   }
/*      */ 
/*      */   public void setFillRule(FillRule paramFillRule)
/*      */   {
/*  980 */     if (this.curState.fillRule != paramFillRule)
/*      */     {
/*      */       byte b;
/*  982 */       if (paramFillRule == FillRule.EVEN_ODD)
/*  983 */         b = 1;
/*      */       else {
/*  985 */         b = 0;
/*      */       }
/*  987 */       this.curState.fillRule = paramFillRule;
/*  988 */       writeParam(b, (byte)16);
/*      */     }
/*      */   }
/*      */ 
/*      */   public FillRule getFillRule()
/*      */   {
/*  999 */     return this.curState.fillRule;
/*      */   }
/*      */ 
/*      */   public void beginPath()
/*      */   {
/* 1006 */     this.path.reset();
/* 1007 */     markPathDirty();
/*      */   }
/*      */ 
/*      */   public void moveTo(double paramDouble1, double paramDouble2)
/*      */   {
/* 1017 */     coords[0] = ((float)paramDouble1);
/* 1018 */     coords[1] = ((float)paramDouble2);
/* 1019 */     this.curState.transform.transform(coords, 0, coords, 0, 1);
/* 1020 */     this.path.moveTo(coords[0], coords[1]);
/* 1021 */     markPathDirty();
/*      */   }
/*      */ 
/*      */   public void lineTo(double paramDouble1, double paramDouble2)
/*      */   {
/* 1032 */     coords[0] = ((float)paramDouble1);
/* 1033 */     coords[1] = ((float)paramDouble2);
/* 1034 */     this.curState.transform.transform(coords, 0, coords, 0, 1);
/* 1035 */     this.path.lineTo(coords[0], coords[1]);
/* 1036 */     markPathDirty();
/*      */   }
/*      */ 
/*      */   public void quadraticCurveTo(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*      */   {
/* 1048 */     coords[0] = ((float)paramDouble1);
/* 1049 */     coords[1] = ((float)paramDouble2);
/* 1050 */     coords[2] = ((float)paramDouble3);
/* 1051 */     coords[3] = ((float)paramDouble4);
/* 1052 */     this.curState.transform.transform(coords, 0, coords, 0, 2);
/* 1053 */     this.path.quadTo(coords[0], coords[1], coords[2], coords[3]);
/* 1054 */     markPathDirty();
/*      */   }
/*      */ 
/*      */   public void bezierCurveTo(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6)
/*      */   {
/* 1068 */     coords[0] = ((float)paramDouble1);
/* 1069 */     coords[1] = ((float)paramDouble2);
/* 1070 */     coords[2] = ((float)paramDouble3);
/* 1071 */     coords[3] = ((float)paramDouble4);
/* 1072 */     coords[4] = ((float)paramDouble5);
/* 1073 */     coords[5] = ((float)paramDouble6);
/* 1074 */     this.curState.transform.transform(coords, 0, coords, 0, 3);
/* 1075 */     this.path.curveTo(coords[0], coords[1], coords[2], coords[3], coords[4], coords[5]);
/* 1076 */     markPathDirty();
/*      */   }
/*      */ 
/*      */   public void arcTo(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5)
/*      */   {
/* 1089 */     if (this.path.getNumCommands() == 0) {
/* 1090 */       moveTo(paramDouble1, paramDouble2);
/* 1091 */       lineTo(paramDouble1, paramDouble2);
/* 1092 */     } else if (!tryArcTo((float)paramDouble1, (float)paramDouble2, (float)paramDouble3, (float)paramDouble4, (float)paramDouble5))
/*      */     {
/* 1095 */       lineTo(paramDouble1, paramDouble2);
/*      */     }
/*      */   }
/*      */ 
/*      */   private static double lenSq(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/* 1100 */     paramDouble3 -= paramDouble1;
/* 1101 */     paramDouble4 -= paramDouble2;
/* 1102 */     return paramDouble3 * paramDouble3 + paramDouble4 * paramDouble4;
/*      */   }
/*      */ 
/*      */   private boolean tryArcTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5)
/*      */   {
/*      */     float f1;
/*      */     float f2;
/* 1107 */     if (this.curState.transform.isTranslateOrIdentity()) {
/* 1108 */       f1 = (float)(this.path.getCurrentX() - this.curState.transform.getMxt());
/* 1109 */       f2 = (float)(this.path.getCurrentY() - this.curState.transform.getMyt());
/*      */     } else {
/* 1111 */       coords[0] = this.path.getCurrentX();
/* 1112 */       coords[1] = this.path.getCurrentY();
/*      */       try {
/* 1114 */         this.curState.transform.inverseTransform(coords, 0, coords, 0, 1);
/*      */       } catch (NoninvertibleTransformException localNoninvertibleTransformException) {
/* 1116 */         return false;
/*      */       }
/* 1118 */       f1 = coords[0];
/* 1119 */       f2 = coords[1];
/*      */     }
/*      */ 
/* 1137 */     double d1 = lenSq(f1, f2, paramFloat1, paramFloat2);
/* 1138 */     double d2 = lenSq(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
/* 1139 */     double d3 = lenSq(f1, f2, paramFloat3, paramFloat4);
/* 1140 */     double d4 = Math.sqrt(d1);
/* 1141 */     double d5 = Math.sqrt(d2);
/* 1142 */     double d6 = d1 + d2 - d3;
/* 1143 */     double d7 = 2.0D * d4 * d5;
/* 1144 */     if ((d7 == 0.0D) || (paramFloat5 <= 0.0F)) {
/* 1145 */       return false;
/*      */     }
/* 1147 */     double d8 = d6 / d7;
/* 1148 */     double d9 = 1.0D + d8;
/* 1149 */     if (d9 == 0.0D) {
/* 1150 */       return false;
/*      */     }
/* 1152 */     double d10 = (1.0D - d8) / d9;
/* 1153 */     double d11 = paramFloat5 / Math.sqrt(d10);
/* 1154 */     double d12 = paramFloat1 + d11 / d4 * (f1 - paramFloat1);
/* 1155 */     double d13 = paramFloat2 + d11 / d4 * (f2 - paramFloat2);
/* 1156 */     double d14 = paramFloat1 + d11 / d5 * (paramFloat3 - paramFloat1);
/* 1157 */     double d15 = paramFloat2 + d11 / d5 * (paramFloat4 - paramFloat2);
/*      */ 
/* 1159 */     double d16 = (d12 + d14) / 2.0D;
/* 1160 */     double d17 = (d13 + d15) / 2.0D;
/*      */ 
/* 1166 */     double d18 = lenSq(d16, d17, paramFloat1, paramFloat2);
/* 1167 */     if (d18 == 0.0D) {
/* 1168 */       return false;
/*      */     }
/* 1170 */     double d19 = lenSq(d16, d17, d12, d13) / d18;
/* 1171 */     double d20 = d16 + (d16 - paramFloat1) * d19;
/* 1172 */     double d21 = d17 + (d17 - paramFloat2) * d19;
/* 1173 */     if ((d20 != d20) || (d21 != d21)) {
/* 1174 */       return false;
/*      */     }
/*      */ 
/* 1178 */     if ((d12 != f1) || (d13 != f2)) {
/* 1179 */       lineTo(d12, d13);
/*      */     }
/*      */ 
/* 1194 */     double d22 = Math.sqrt((1.0D - d8) / 2.0D);
/* 1195 */     int i = (d13 - d21) * (d14 - d20) > (d15 - d21) * (d12 - d20) ? 1 : 0;
/*      */     double d23;
/*      */     double d24;
/*      */     double d25;
/*      */     double d26;
/*      */     double d27;
/*      */     double d28;
/* 1203 */     if (d8 <= 0.0D)
/*      */     {
/* 1205 */       d23 = Math.sqrt((1.0D + d8) / 2.0D);
/* 1206 */       d24 = 1.333333333333333D * d23 / (1.0D + d22);
/* 1207 */       if (i != 0) d24 = -d24;
/* 1208 */       d25 = d12 - d24 * (d13 - d21);
/* 1209 */       d26 = d13 + d24 * (d12 - d20);
/* 1210 */       d27 = d14 + d24 * (d15 - d21);
/* 1211 */       d28 = d15 - d24 * (d14 - d20);
/* 1212 */       bezierCurveTo(d25, d26, d27, d28, d14, d15);
/*      */     }
/*      */     else
/*      */     {
/* 1219 */       d23 = Math.sqrt((1.0D - d22) / 2.0D);
/* 1220 */       d24 = Math.sqrt((1.0D + d22) / 2.0D);
/* 1221 */       d25 = 1.333333333333333D * d23 / (1.0D + d24);
/* 1222 */       if (i != 0) d25 = -d25;
/* 1223 */       d26 = paramFloat5 / Math.sqrt(d18);
/* 1224 */       d27 = d20 + (paramFloat1 - d16) * d26;
/* 1225 */       d28 = d21 + (paramFloat2 - d17) * d26;
/* 1226 */       double d29 = d12 - d25 * (d13 - d21);
/* 1227 */       double d30 = d13 + d25 * (d12 - d20);
/* 1228 */       double d31 = d27 + d25 * (d28 - d21);
/* 1229 */       double d32 = d28 - d25 * (d27 - d20);
/* 1230 */       bezierCurveTo(d29, d30, d31, d32, d27, d28);
/* 1231 */       d29 = d27 - d25 * (d28 - d21);
/* 1232 */       d30 = d28 + d25 * (d27 - d20);
/* 1233 */       d31 = d14 + d25 * (d15 - d21);
/* 1234 */       d32 = d15 - d25 * (d14 - d20);
/* 1235 */       bezierCurveTo(d29, d30, d31, d32, d14, d15);
/*      */     }
/* 1237 */     return true;
/*      */   }
/*      */ 
/*      */   public void arc(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6)
/*      */   {
/* 1257 */     TEMP_ARC.setArc((float)(paramDouble1 - paramDouble3), (float)(paramDouble2 - paramDouble4), (float)(paramDouble3 * 2.0D), (float)(paramDouble4 * 2.0D), (float)paramDouble5, (float)paramDouble6, 0);
/*      */ 
/* 1264 */     this.path.append(TEMP_ARC.getPathIterator(this.curState.transform), true);
/* 1265 */     markPathDirty();
/*      */   }
/*      */ 
/*      */   public void rect(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*      */   {
/* 1277 */     coords[0] = ((float)paramDouble1);
/* 1278 */     coords[1] = ((float)paramDouble2);
/* 1279 */     coords[2] = ((float)paramDouble3);
/* 1280 */     coords[3] = 0.0F;
/* 1281 */     coords[4] = 0.0F;
/* 1282 */     coords[5] = ((float)paramDouble4);
/* 1283 */     this.curState.transform.deltaTransform(coords, 0, coords, 0, 3);
/* 1284 */     float f1 = coords[0] + (float)this.curState.transform.getMxt();
/* 1285 */     float f2 = coords[1] + (float)this.curState.transform.getMyt();
/* 1286 */     float f3 = coords[2];
/* 1287 */     float f4 = coords[3];
/* 1288 */     float f5 = coords[4];
/* 1289 */     float f6 = coords[5];
/* 1290 */     this.path.moveTo(f1, f2);
/* 1291 */     this.path.lineTo(f1 + f3, f2 + f4);
/* 1292 */     this.path.lineTo(f1 + f3 + f5, f2 + f4 + f6);
/* 1293 */     this.path.lineTo(f1 + f5, f2 + f6);
/* 1294 */     this.path.closePath();
/* 1295 */     markPathDirty();
/*      */   }
/*      */ 
/*      */   public void appendSVGPath(String paramString)
/*      */   {
/* 1306 */     int i = 1;
/* 1307 */     int j = 1;
/* 1308 */     for (int k = 0; k < paramString.length(); k++) {
/* 1309 */       switch (paramString.charAt(k)) {
/*      */       case '\t':
/*      */       case '\n':
/*      */       case '\r':
/*      */       case ' ':
/* 1314 */         break;
/*      */       case 'M':
/* 1316 */         i = j = 0;
/* 1317 */         break;
/*      */       case 'm':
/* 1319 */         if (this.path.getNumCommands() == 0)
/*      */         {
/* 1321 */           i = 0;
/*      */         }
/*      */ 
/* 1327 */         j = 0;
/*      */       }
/*      */ 
/* 1330 */       break;
/*      */     }
/* 1332 */     Path2D localPath2D = new Path2D();
/* 1333 */     if ((i != 0) && (this.path.getNumCommands() > 0))
/*      */     {
/*      */       float f1;
/*      */       float f2;
/* 1335 */       if (this.curState.transform.isTranslateOrIdentity()) {
/* 1336 */         f1 = (float)(this.path.getCurrentX() - this.curState.transform.getMxt());
/* 1337 */         f2 = (float)(this.path.getCurrentY() - this.curState.transform.getMyt());
/*      */       } else {
/* 1339 */         coords[0] = this.path.getCurrentX();
/* 1340 */         coords[1] = this.path.getCurrentY();
/*      */         try {
/* 1342 */           this.curState.transform.inverseTransform(coords, 0, coords, 0, 1);
/*      */         } catch (NoninvertibleTransformException localNoninvertibleTransformException) {
/*      */         }
/* 1345 */         f1 = coords[0];
/* 1346 */         f2 = coords[1];
/*      */       }
/* 1348 */       localPath2D.moveTo(f1, f2);
/*      */     } else {
/* 1350 */       j = 0;
/*      */     }
/* 1352 */     localPath2D.appendSVGPath(paramString);
/* 1353 */     PathIterator localPathIterator = localPath2D.getPathIterator(this.curState.transform);
/* 1354 */     if (j != 0)
/*      */     {
/* 1357 */       localPathIterator.next();
/*      */     }
/* 1359 */     this.path.append(localPathIterator, false);
/*      */   }
/*      */ 
/*      */   public void closePath()
/*      */   {
/* 1366 */     this.path.closePath();
/* 1367 */     markPathDirty();
/*      */   }
/*      */ 
/*      */   public void fill()
/*      */   {
/* 1374 */     writePath((byte)47);
/*      */   }
/*      */ 
/*      */   public void stroke()
/*      */   {
/* 1381 */     writePath((byte)48);
/*      */   }
/*      */ 
/*      */   public void clip()
/*      */   {
/* 1388 */     Path2D localPath2D = new Path2D(this.path);
/* 1389 */     this.clipStack.addLast(localPath2D);
/* 1390 */     this.curState.numClipPaths += 1;
/* 1391 */     GrowableDataBuffer localGrowableDataBuffer = getBuffer();
/* 1392 */     localGrowableDataBuffer.putByte((byte)13);
/* 1393 */     localGrowableDataBuffer.putObject(localPath2D);
/*      */   }
/*      */ 
/*      */   public boolean isPointInPath(double paramDouble1, double paramDouble2)
/*      */   {
/* 1407 */     return this.path.contains((float)paramDouble1, (float)paramDouble2);
/*      */   }
/*      */ 
/*      */   public void clearRect(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*      */   {
/* 1419 */     if ((paramDouble3 != 0.0D) && (paramDouble4 != 0.0D))
/* 1420 */       writeOp4(paramDouble1, paramDouble2, paramDouble3, paramDouble4, (byte)22);
/*      */   }
/*      */ 
/*      */   public void fillRect(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*      */   {
/* 1433 */     if ((paramDouble3 != 0.0D) && (paramDouble4 != 0.0D))
/* 1434 */       writeOp4(paramDouble1, paramDouble2, paramDouble3, paramDouble4, (byte)20);
/*      */   }
/*      */ 
/*      */   public void strokeRect(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*      */   {
/* 1447 */     if ((paramDouble3 != 0.0D) || (paramDouble4 != 0.0D))
/* 1448 */       writeOp4(paramDouble1, paramDouble2, paramDouble3, paramDouble4, (byte)21);
/*      */   }
/*      */ 
/*      */   public void fillOval(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*      */   {
/* 1461 */     if ((paramDouble3 != 0.0D) && (paramDouble4 != 0.0D))
/* 1462 */       writeOp4(paramDouble1, paramDouble2, paramDouble3, paramDouble4, (byte)24);
/*      */   }
/*      */ 
/*      */   public void strokeOval(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*      */   {
/* 1475 */     if ((paramDouble3 != 0.0D) || (paramDouble4 != 0.0D))
/* 1476 */       writeOp4(paramDouble1, paramDouble2, paramDouble3, paramDouble4, (byte)25);
/*      */   }
/*      */ 
/*      */   public void fillArc(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, ArcType paramArcType)
/*      */   {
/* 1494 */     if ((paramDouble3 != 0.0D) && (paramDouble4 != 0.0D)) {
/* 1495 */       writeArcType(paramArcType);
/* 1496 */       writeOp6(paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6, (byte)28);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void strokeArc(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, ArcType paramArcType)
/*      */   {
/* 1514 */     if ((paramDouble3 != 0.0D) && (paramDouble4 != 0.0D)) {
/* 1515 */       writeArcType(paramArcType);
/* 1516 */       writeOp6(paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6, (byte)29);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void fillRoundRect(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6)
/*      */   {
/* 1533 */     if ((paramDouble3 != 0.0D) && (paramDouble4 != 0.0D))
/* 1534 */       writeOp6(paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6, (byte)26);
/*      */   }
/*      */ 
/*      */   public void strokeRoundRect(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6)
/*      */   {
/* 1551 */     if ((paramDouble3 != 0.0D) && (paramDouble4 != 0.0D))
/* 1552 */       writeOp6(paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6, (byte)27);
/*      */   }
/*      */ 
/*      */   public void strokeLine(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*      */   {
/* 1565 */     writeOp4(paramDouble1, paramDouble2, paramDouble3, paramDouble4, (byte)23);
/*      */   }
/*      */ 
/*      */   public void fillPolygon(double[] paramArrayOfDouble1, double[] paramArrayOfDouble2, int paramInt)
/*      */   {
/* 1576 */     if (paramInt >= 3)
/* 1577 */       writePoly(paramArrayOfDouble1, paramArrayOfDouble2, paramInt, true, (byte)47);
/*      */   }
/*      */ 
/*      */   public void strokePolygon(double[] paramArrayOfDouble1, double[] paramArrayOfDouble2, int paramInt)
/*      */   {
/* 1589 */     if (paramInt >= 2)
/* 1590 */       writePoly(paramArrayOfDouble1, paramArrayOfDouble2, paramInt, true, (byte)48);
/*      */   }
/*      */ 
/*      */   public void strokePolyline(double[] paramArrayOfDouble1, double[] paramArrayOfDouble2, int paramInt)
/*      */   {
/* 1603 */     if (paramInt >= 2)
/* 1604 */       writePoly(paramArrayOfDouble1, paramArrayOfDouble2, paramInt, false, (byte)48);
/*      */   }
/*      */ 
/*      */   public void drawImage(Image paramImage, double paramDouble1, double paramDouble2)
/*      */   {
/* 1617 */     double d1 = paramImage.getWidth();
/* 1618 */     double d2 = paramImage.getHeight();
/* 1619 */     writeImage(paramImage, paramDouble1, paramDouble2, d1, d2);
/*      */   }
/*      */ 
/*      */   public void drawImage(Image paramImage, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*      */   {
/* 1633 */     writeImage(paramImage, paramDouble1, paramDouble2, paramDouble3, paramDouble4);
/*      */   }
/*      */ 
/*      */   public void drawImage(Image paramImage, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8)
/*      */   {
/* 1654 */     writeImage(paramImage, paramDouble5, paramDouble6, paramDouble7, paramDouble8, paramDouble1, paramDouble2, paramDouble3, paramDouble4);
/*      */   }
/*      */ 
/*      */   public PixelWriter getPixelWriter()
/*      */   {
/* 1669 */     if (this.writer == null) {
/* 1670 */       this.writer = new PixelWriter()
/*      */       {
/*      */         public PixelFormat getPixelFormat() {
/* 1673 */           return PixelFormat.getByteBgraPreInstance();
/*      */         }
/*      */ 
/*      */         private BytePixelSetter getSetter() {
/* 1677 */           return ByteBgraPre.setter;
/*      */         }
/*      */ 
/*      */         public void setArgb(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
/*      */         {
/* 1682 */           GrowableDataBuffer localGrowableDataBuffer = GraphicsContext.this.getBuffer();
/* 1683 */           localGrowableDataBuffer.putByte((byte)52);
/* 1684 */           localGrowableDataBuffer.putInt(paramAnonymousInt1);
/* 1685 */           localGrowableDataBuffer.putInt(paramAnonymousInt2);
/* 1686 */           localGrowableDataBuffer.putInt(paramAnonymousInt3);
/*      */         }
/*      */ 
/*      */         public void setColor(int paramAnonymousInt1, int paramAnonymousInt2, Color paramAnonymousColor)
/*      */         {
/* 1691 */           int i = (int)Math.round(paramAnonymousColor.getOpacity() * 255.0D);
/* 1692 */           int j = (int)Math.round(paramAnonymousColor.getRed() * 255.0D);
/* 1693 */           int k = (int)Math.round(paramAnonymousColor.getGreen() * 255.0D);
/* 1694 */           int m = (int)Math.round(paramAnonymousColor.getBlue() * 255.0D);
/* 1695 */           setArgb(paramAnonymousInt1, paramAnonymousInt2, i << 24 | j << 16 | k << 8 | m);
/*      */         }
/*      */ 
/*      */         private void writePixelBuffer(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4, byte[] paramAnonymousArrayOfByte)
/*      */         {
/* 1701 */           GrowableDataBuffer localGrowableDataBuffer = GraphicsContext.this.getBuffer();
/* 1702 */           localGrowableDataBuffer.putByte((byte)53);
/* 1703 */           localGrowableDataBuffer.putInt(paramAnonymousInt1);
/* 1704 */           localGrowableDataBuffer.putInt(paramAnonymousInt2);
/* 1705 */           localGrowableDataBuffer.putInt(paramAnonymousInt3);
/* 1706 */           localGrowableDataBuffer.putInt(paramAnonymousInt4);
/* 1707 */           localGrowableDataBuffer.putObject(paramAnonymousArrayOfByte);
/*      */         }
/*      */ 
/*      */         private int[] checkBounds(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4, PixelFormat paramAnonymousPixelFormat, int paramAnonymousInt5)
/*      */         {
/* 1714 */           int i = (int)GraphicsContext.this.theCanvas.getWidth();
/* 1715 */           int j = (int)GraphicsContext.this.theCanvas.getHeight();
/* 1716 */           if ((paramAnonymousInt1 >= 0) && (paramAnonymousInt2 >= 0) && (paramAnonymousInt1 + paramAnonymousInt3 <= i) && (paramAnonymousInt2 + paramAnonymousInt4 <= j)) {
/* 1717 */             return null;
/*      */           }
/* 1719 */           int k = 0;
/* 1720 */           if (paramAnonymousInt1 < 0) {
/* 1721 */             paramAnonymousInt3 += paramAnonymousInt1;
/* 1722 */             if (paramAnonymousInt3 < 0) return null;
/* 1723 */             if (paramAnonymousPixelFormat != null) {
/* 1724 */               switch (GraphicsContext.2.$SwitchMap$javafx$scene$image$PixelFormat$Type[paramAnonymousPixelFormat.getType().ordinal()]) {
/*      */               case 1:
/*      */               case 2:
/* 1727 */                 k -= paramAnonymousInt1 * 4;
/* 1728 */                 break;
/*      */               case 3:
/* 1730 */                 k -= paramAnonymousInt1 * 3;
/* 1731 */                 break;
/*      */               case 4:
/*      */               case 5:
/*      */               case 6:
/* 1735 */                 k -= paramAnonymousInt1;
/* 1736 */                 break;
/*      */               default:
/* 1738 */                 throw new InternalError("unknown Pixel Format");
/*      */               }
/*      */             }
/* 1741 */             paramAnonymousInt1 = 0;
/*      */           }
/* 1743 */           if (paramAnonymousInt2 < 0) {
/* 1744 */             paramAnonymousInt4 += paramAnonymousInt2;
/* 1745 */             if (paramAnonymousInt4 < 0) return null;
/* 1746 */             k -= paramAnonymousInt2 * paramAnonymousInt5;
/* 1747 */             paramAnonymousInt2 = 0;
/*      */           }
/* 1749 */           if (paramAnonymousInt1 + paramAnonymousInt3 > i) {
/* 1750 */             paramAnonymousInt3 = i - paramAnonymousInt1;
/* 1751 */             if (paramAnonymousInt3 < 0) return null;
/*      */           }
/* 1753 */           if (paramAnonymousInt2 + paramAnonymousInt4 > j) {
/* 1754 */             paramAnonymousInt4 = j - paramAnonymousInt2;
/* 1755 */             if (paramAnonymousInt4 < 0) return null;
/*      */           }
/* 1757 */           return new int[] { paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3, paramAnonymousInt4, k };
/*      */         }
/*      */ 
/*      */         public <T extends Buffer> void setPixels(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4, PixelFormat<T> paramAnonymousPixelFormat, T paramAnonymousT, int paramAnonymousInt5)
/*      */         {
/* 1768 */           if ((paramAnonymousInt3 <= 0) || (paramAnonymousInt4 <= 0)) return;
/* 1769 */           int i = paramAnonymousT.position();
/* 1770 */           int[] arrayOfInt = checkBounds(paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3, paramAnonymousInt4, paramAnonymousPixelFormat, paramAnonymousInt5);
/*      */ 
/* 1772 */           if (arrayOfInt != null) {
/* 1773 */             paramAnonymousInt1 = arrayOfInt[0];
/* 1774 */             paramAnonymousInt2 = arrayOfInt[1];
/* 1775 */             paramAnonymousInt3 = arrayOfInt[2];
/* 1776 */             paramAnonymousInt4 = arrayOfInt[3];
/* 1777 */             i += arrayOfInt[4];
/*      */           }
/*      */ 
/* 1780 */           byte[] arrayOfByte = new byte[paramAnonymousInt3 * paramAnonymousInt4 * 4];
/* 1781 */           ByteBuffer localByteBuffer = ByteBuffer.wrap(arrayOfByte);
/*      */ 
/* 1783 */           PixelGetter localPixelGetter = PixelUtils.getGetter(paramAnonymousPixelFormat);
/* 1784 */           PixelConverter localPixelConverter = PixelUtils.getConverter(localPixelGetter, getSetter());
/*      */ 
/* 1786 */           localPixelConverter.convert(paramAnonymousT, i, paramAnonymousInt5, localByteBuffer, 0, paramAnonymousInt3 * 4, paramAnonymousInt3, paramAnonymousInt4);
/*      */ 
/* 1789 */           writePixelBuffer(paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3, paramAnonymousInt4, arrayOfByte);
/*      */         }
/*      */ 
/*      */         public void setPixels(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4, PixelFormat<ByteBuffer> paramAnonymousPixelFormat, byte[] paramAnonymousArrayOfByte, int paramAnonymousInt5, int paramAnonymousInt6)
/*      */         {
/* 1797 */           if ((paramAnonymousInt3 <= 0) || (paramAnonymousInt4 <= 0)) return;
/* 1798 */           int[] arrayOfInt = checkBounds(paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3, paramAnonymousInt4, paramAnonymousPixelFormat, paramAnonymousInt6);
/*      */ 
/* 1800 */           if (arrayOfInt != null) {
/* 1801 */             paramAnonymousInt1 = arrayOfInt[0];
/* 1802 */             paramAnonymousInt2 = arrayOfInt[1];
/* 1803 */             paramAnonymousInt3 = arrayOfInt[2];
/* 1804 */             paramAnonymousInt4 = arrayOfInt[3];
/* 1805 */             paramAnonymousInt5 += arrayOfInt[4];
/*      */           }
/*      */ 
/* 1808 */           byte[] arrayOfByte = new byte[paramAnonymousInt3 * paramAnonymousInt4 * 4];
/*      */ 
/* 1810 */           BytePixelGetter localBytePixelGetter = PixelUtils.getByteGetter(paramAnonymousPixelFormat);
/* 1811 */           ByteToBytePixelConverter localByteToBytePixelConverter = PixelUtils.getB2BConverter(localBytePixelGetter, getSetter());
/*      */ 
/* 1813 */           localByteToBytePixelConverter.convert(paramAnonymousArrayOfByte, paramAnonymousInt5, paramAnonymousInt6, arrayOfByte, 0, paramAnonymousInt3 * 4, paramAnonymousInt3, paramAnonymousInt4);
/*      */ 
/* 1816 */           writePixelBuffer(paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3, paramAnonymousInt4, arrayOfByte);
/*      */         }
/*      */ 
/*      */         public void setPixels(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4, PixelFormat<IntBuffer> paramAnonymousPixelFormat, int[] paramAnonymousArrayOfInt, int paramAnonymousInt5, int paramAnonymousInt6)
/*      */         {
/* 1824 */           if ((paramAnonymousInt3 <= 0) || (paramAnonymousInt4 <= 0)) return;
/* 1825 */           int[] arrayOfInt = checkBounds(paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3, paramAnonymousInt4, paramAnonymousPixelFormat, paramAnonymousInt6);
/*      */ 
/* 1827 */           if (arrayOfInt != null) {
/* 1828 */             paramAnonymousInt1 = arrayOfInt[0];
/* 1829 */             paramAnonymousInt2 = arrayOfInt[1];
/* 1830 */             paramAnonymousInt3 = arrayOfInt[2];
/* 1831 */             paramAnonymousInt4 = arrayOfInt[3];
/* 1832 */             paramAnonymousInt5 += arrayOfInt[4];
/*      */           }
/*      */ 
/* 1835 */           byte[] arrayOfByte = new byte[paramAnonymousInt3 * paramAnonymousInt4 * 4];
/*      */ 
/* 1837 */           IntPixelGetter localIntPixelGetter = PixelUtils.getIntGetter(paramAnonymousPixelFormat);
/* 1838 */           IntToBytePixelConverter localIntToBytePixelConverter = PixelUtils.getI2BConverter(localIntPixelGetter, getSetter());
/*      */ 
/* 1840 */           localIntToBytePixelConverter.convert(paramAnonymousArrayOfInt, paramAnonymousInt5, paramAnonymousInt6, arrayOfByte, 0, paramAnonymousInt3 * 4, paramAnonymousInt3, paramAnonymousInt4);
/*      */ 
/* 1843 */           writePixelBuffer(paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3, paramAnonymousInt4, arrayOfByte);
/*      */         }
/*      */ 
/*      */         public void setPixels(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4, PixelReader paramAnonymousPixelReader, int paramAnonymousInt5, int paramAnonymousInt6)
/*      */         {
/* 1850 */           if ((paramAnonymousInt3 <= 0) || (paramAnonymousInt4 <= 0)) return;
/* 1851 */           int[] arrayOfInt = checkBounds(paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3, paramAnonymousInt4, null, 0);
/* 1852 */           if (arrayOfInt != null) {
/* 1853 */             int i = arrayOfInt[0];
/* 1854 */             int j = arrayOfInt[1];
/* 1855 */             paramAnonymousInt5 += i - paramAnonymousInt1;
/* 1856 */             paramAnonymousInt6 += j - paramAnonymousInt2;
/* 1857 */             paramAnonymousInt1 = i;
/* 1858 */             paramAnonymousInt2 = j;
/* 1859 */             paramAnonymousInt3 = arrayOfInt[2];
/* 1860 */             paramAnonymousInt4 = arrayOfInt[3];
/*      */           }
/*      */ 
/* 1863 */           byte[] arrayOfByte = new byte[paramAnonymousInt3 * paramAnonymousInt4 * 4];
/* 1864 */           paramAnonymousPixelReader.getPixels(paramAnonymousInt5, paramAnonymousInt6, paramAnonymousInt3, paramAnonymousInt4, PixelFormat.getByteBgraPreInstance(), arrayOfByte, 0, paramAnonymousInt3 * 4);
/*      */ 
/* 1867 */           writePixelBuffer(paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3, paramAnonymousInt4, arrayOfByte);
/*      */         }
/*      */       };
/*      */     }
/* 1871 */     return this.writer;
/*      */   }
/*      */ 
/*      */   public void setEffect(Effect paramEffect)
/*      */   {
/* 1880 */     GrowableDataBuffer localGrowableDataBuffer = getBuffer();
/* 1881 */     localGrowableDataBuffer.putByte((byte)12);
/* 1882 */     if (paramEffect == null) {
/* 1883 */       this.curState.effect = null;
/* 1884 */       localGrowableDataBuffer.putObject(null);
/*      */     } else {
/* 1886 */       this.curState.effect = paramEffect.impl_copy();
/* 1887 */       this.curState.effect.impl_sync();
/* 1888 */       localGrowableDataBuffer.putObject(this.curState.effect.impl_getImpl());
/*      */     }
/*      */   }
/*      */ 
/*      */   public Effect getEffect(Effect paramEffect)
/*      */   {
/* 1902 */     return this.curState.effect == null ? null : this.curState.effect.impl_copy();
/*      */   }
/*      */ 
/*      */   public void applyEffect(Effect paramEffect)
/*      */   {
/* 1910 */     GrowableDataBuffer localGrowableDataBuffer = getBuffer();
/* 1911 */     localGrowableDataBuffer.putByte((byte)60);
/* 1912 */     Effect localEffect = paramEffect.impl_copy();
/* 1913 */     localEffect.impl_sync();
/* 1914 */     localGrowableDataBuffer.putObject(localEffect.impl_getImpl());
/*      */   }
/*      */ 
/*      */   static class State
/*      */   {
/*      */     double globalAlpha;
/*      */     BlendMode blendop;
/*      */     Affine2D transform;
/*      */     Paint fill;
/*      */     Paint stroke;
/*      */     double linewidth;
/*      */     StrokeLineCap linecap;
/*      */     StrokeLineJoin linejoin;
/*      */     double miterlimit;
/*      */     int numClipPaths;
/*      */     Font font;
/*      */     TextAlignment textalign;
/*      */     VPos textbaseline;
/*      */     Effect effect;
/*      */     FillRule fillRule;
/*      */ 
/*      */     State()
/*      */     {
/*  144 */       this(1.0D, BlendMode.SRC_OVER, new Affine2D(), Color.BLACK, Color.BLACK, 1.0D, StrokeLineCap.SQUARE, StrokeLineJoin.MITER, 10.0D, 0, Font.getDefault(), TextAlignment.LEFT, VPos.BASELINE, null, FillRule.NON_ZERO);
/*      */     }
/*      */ 
/*      */     State(State paramState)
/*      */     {
/*  153 */       this(paramState.globalAlpha, paramState.blendop, new Affine2D(paramState.transform), paramState.fill, paramState.stroke, paramState.linewidth, paramState.linecap, paramState.linejoin, paramState.miterlimit, paramState.numClipPaths, paramState.font, paramState.textalign, paramState.textbaseline, paramState.effect, paramState.fillRule);
/*      */     }
/*      */ 
/*      */     State(double paramDouble1, BlendMode paramBlendMode, Affine2D paramAffine2D, Paint paramPaint1, Paint paramPaint2, double paramDouble2, StrokeLineCap paramStrokeLineCap, StrokeLineJoin paramStrokeLineJoin, double paramDouble3, int paramInt, Font paramFont, TextAlignment paramTextAlignment, VPos paramVPos, Effect paramEffect, FillRule paramFillRule)
/*      */     {
/*  170 */       this.globalAlpha = paramDouble1;
/*  171 */       this.blendop = paramBlendMode;
/*  172 */       this.transform = paramAffine2D;
/*  173 */       this.fill = paramPaint1;
/*  174 */       this.stroke = paramPaint2;
/*  175 */       this.linewidth = paramDouble2;
/*  176 */       this.linecap = paramStrokeLineCap;
/*  177 */       this.linejoin = paramStrokeLineJoin;
/*  178 */       this.miterlimit = paramDouble3;
/*  179 */       this.numClipPaths = paramInt;
/*  180 */       this.font = paramFont;
/*  181 */       this.textalign = paramTextAlignment;
/*  182 */       this.textbaseline = paramVPos;
/*  183 */       this.effect = paramEffect;
/*  184 */       this.fillRule = paramFillRule;
/*      */     }
/*      */ 
/*      */     State copy() {
/*  188 */       return new State(this);
/*      */     }
/*      */ 
/*      */     void restore(GraphicsContext paramGraphicsContext) {
/*  192 */       paramGraphicsContext.setGlobalAlpha(this.globalAlpha);
/*  193 */       paramGraphicsContext.setGlobalBlendMode(this.blendop);
/*  194 */       paramGraphicsContext.setTransform(this.transform.getMxx(), this.transform.getMyx(), this.transform.getMxy(), this.transform.getMyy(), this.transform.getMxt(), this.transform.getMyt());
/*      */ 
/*  197 */       paramGraphicsContext.setFill(this.fill);
/*  198 */       paramGraphicsContext.setStroke(this.stroke);
/*  199 */       paramGraphicsContext.setLineWidth(this.linewidth);
/*  200 */       paramGraphicsContext.setLineCap(this.linecap);
/*  201 */       paramGraphicsContext.setLineJoin(this.linejoin);
/*  202 */       paramGraphicsContext.setMiterLimit(this.miterlimit);
/*  203 */       GrowableDataBuffer localGrowableDataBuffer = paramGraphicsContext.getBuffer();
/*  204 */       while (paramGraphicsContext.curState.numClipPaths > this.numClipPaths) {
/*  205 */         paramGraphicsContext.curState.numClipPaths -= 1;
/*  206 */         paramGraphicsContext.clipStack.removeLast();
/*  207 */         localGrowableDataBuffer.putByte((byte)14);
/*      */       }
/*  209 */       paramGraphicsContext.setFillRule(this.fillRule);
/*  210 */       paramGraphicsContext.setFont(this.font);
/*  211 */       paramGraphicsContext.setTextAlign(this.textalign);
/*  212 */       paramGraphicsContext.setTextBaseline(this.textbaseline);
/*  213 */       paramGraphicsContext.setEffect(this.effect);
/*      */     }
/*      */   }
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.canvas.GraphicsContext
 * JD-Core Version:    0.6.2
 */