/*      */ package com.sun.javafx.geom.transform;
/*      */ 
/*      */ import com.sun.javafx.geom.BaseBounds;
/*      */ import com.sun.javafx.geom.Path2D;
/*      */ import com.sun.javafx.geom.Point2D;
/*      */ import com.sun.javafx.geom.RectBounds;
/*      */ import com.sun.javafx.geom.Rectangle;
/*      */ import com.sun.javafx.geom.Shape;
/*      */ import com.sun.javafx.geom.Vec3d;
/*      */ 
/*      */ public abstract class AffineBase extends BaseTransform
/*      */ {
/*      */   protected static final int APPLY_IDENTITY = 0;
/*      */   protected static final int APPLY_TRANSLATE = 1;
/*      */   protected static final int APPLY_SCALE = 2;
/*      */   protected static final int APPLY_SHEAR = 4;
/*      */   protected static final int APPLY_3D = 8;
/*      */   protected static final int APPLY_2D_MASK = 7;
/*      */   protected static final int APPLY_2D_DELTA_MASK = 6;
/*      */   protected static final int HI_SHIFT = 4;
/*      */   protected static final int HI_IDENTITY = 0;
/*      */   protected static final int HI_TRANSLATE = 16;
/*      */   protected static final int HI_SCALE = 32;
/*      */   protected static final int HI_SHEAR = 64;
/*      */   protected static final int HI_3D = 128;
/*      */   protected double mxx;
/*      */   protected double myx;
/*      */   protected double mxy;
/*      */   protected double myy;
/*      */   protected double mxt;
/*      */   protected double myt;
/*      */   protected transient int state;
/*      */   protected transient int type;
/* 2275 */   private static final int[] rot90conversion = { 4, 5, 4, 5, 2, 3, 6, 7 };
/*      */ 
/*      */   protected static void stateError()
/*      */   {
/*  205 */     throw new InternalError("missing case in transform state switch");
/*      */   }
/*      */ 
/*      */   protected void updateState()
/*      */   {
/*  231 */     updateState2D();
/*      */   }
/*      */ 
/*      */   protected void updateState2D()
/*      */   {
/*  239 */     if ((this.mxy == 0.0D) && (this.myx == 0.0D)) {
/*  240 */       if ((this.mxx == 1.0D) && (this.myy == 1.0D)) {
/*  241 */         if ((this.mxt == 0.0D) && (this.myt == 0.0D)) {
/*  242 */           this.state = 0;
/*  243 */           this.type = 0;
/*      */         } else {
/*  245 */           this.state = 1;
/*  246 */           this.type = 1;
/*      */         }
/*      */       } else {
/*  249 */         if ((this.mxt == 0.0D) && (this.myt == 0.0D))
/*  250 */           this.state = 2;
/*      */         else {
/*  252 */           this.state = 3;
/*      */         }
/*  254 */         this.type = -1;
/*      */       }
/*      */     } else {
/*  257 */       if ((this.mxx == 0.0D) && (this.myy == 0.0D)) {
/*  258 */         if ((this.mxt == 0.0D) && (this.myt == 0.0D))
/*  259 */           this.state = 4;
/*      */         else {
/*  261 */           this.state = 5;
/*      */         }
/*      */       }
/*  264 */       else if ((this.mxt == 0.0D) && (this.myt == 0.0D))
/*  265 */         this.state = 6;
/*      */       else {
/*  267 */         this.state = 7;
/*      */       }
/*      */ 
/*  270 */       this.type = -1;
/*      */     }
/*      */   }
/*      */ 
/*      */   public int getType() {
/*  275 */     if (this.type == -1) {
/*  276 */       updateState();
/*  277 */       if (this.type == -1) {
/*  278 */         this.type = calculateType();
/*      */       }
/*      */     }
/*  281 */     return this.type;
/*      */   }
/*      */ 
/*      */   protected int calculateType() {
/*  285 */     int i = (this.state & 0x8) == 0 ? 0 : 128;
/*      */     int j;
/*      */     int k;
/*  287 */     switch (this.state & 0x7) {
/*      */     default:
/*  289 */       stateError();
/*      */     case 7:
/*  292 */       i |= 1;
/*      */     case 6:
/*  295 */       if (this.mxx * this.mxy + this.myx * this.myy != 0.0D)
/*      */       {
/*  297 */         i |= 32;
/*      */       }
/*      */       else {
/*  300 */         j = this.mxx >= 0.0D ? 1 : 0;
/*  301 */         k = this.myy >= 0.0D ? 1 : 0;
/*  302 */         if (j == k)
/*      */         {
/*  305 */           if ((this.mxx != this.myy) || (this.mxy != -this.myx))
/*  306 */             i |= 20;
/*  307 */           else if (this.mxx * this.myy - this.mxy * this.myx != 1.0D)
/*  308 */             i |= 18;
/*      */           else {
/*  310 */             i |= 16;
/*      */           }
/*      */ 
/*      */         }
/*  315 */         else if ((this.mxx != -this.myy) || (this.mxy != this.myx)) {
/*  316 */           i |= 84;
/*      */         }
/*  319 */         else if (this.mxx * this.myy - this.mxy * this.myx != 1.0D) {
/*  320 */           i |= 82;
/*      */         }
/*      */         else
/*      */         {
/*  324 */           i |= 80;
/*      */         }
/*      */       }
/*  327 */       break;
/*      */     case 5:
/*  329 */       i |= 1;
/*      */     case 4:
/*  332 */       j = this.mxy >= 0.0D ? 1 : 0;
/*  333 */       k = this.myx >= 0.0D ? 1 : 0;
/*  334 */       if (j != k)
/*      */       {
/*  336 */         if (this.mxy != -this.myx)
/*  337 */           i |= 12;
/*  338 */         else if ((this.mxy != 1.0D) && (this.myx != -1.0D))
/*  339 */           i |= 10;
/*      */         else {
/*  341 */           i |= 8;
/*      */         }
/*      */ 
/*      */       }
/*  345 */       else if (this.mxy == this.myx) {
/*  346 */         i |= 74;
/*      */       }
/*      */       else
/*      */       {
/*  350 */         i |= 76;
/*      */       }
/*      */ 
/*  355 */       break;
/*      */     case 3:
/*  357 */       i |= 1;
/*      */     case 2:
/*  360 */       j = this.mxx >= 0.0D ? 1 : 0;
/*  361 */       k = this.myy >= 0.0D ? 1 : 0;
/*  362 */       if (j == k) {
/*  363 */         if (j != 0)
/*      */         {
/*  366 */           if (this.mxx == this.myy)
/*  367 */             i |= 2;
/*      */           else {
/*  369 */             i |= 4;
/*      */           }
/*      */ 
/*      */         }
/*  373 */         else if (this.mxx != this.myy)
/*  374 */           i |= 12;
/*  375 */         else if (this.mxx != -1.0D)
/*  376 */           i |= 10;
/*      */         else {
/*  378 */           i |= 8;
/*      */         }
/*      */ 
/*      */       }
/*  383 */       else if (this.mxx == -this.myy) {
/*  384 */         if ((this.mxx == 1.0D) || (this.mxx == -1.0D))
/*  385 */           i |= 64;
/*      */         else
/*  387 */           i |= 66;
/*      */       }
/*      */       else {
/*  390 */         i |= 68;
/*      */       }
/*      */ 
/*  393 */       break;
/*      */     case 1:
/*  395 */       i |= 1;
/*  396 */       break;
/*      */     case 0:
/*      */     }
/*      */ 
/*  400 */     return i;
/*      */   }
/*      */ 
/*      */   public double getMxx()
/*      */   {
/*  413 */     return this.mxx;
/*      */   }
/*      */ 
/*      */   public double getMyy()
/*      */   {
/*  426 */     return this.myy;
/*      */   }
/*      */ 
/*      */   public double getMxy()
/*      */   {
/*  439 */     return this.mxy;
/*      */   }
/*      */ 
/*      */   public double getMyx()
/*      */   {
/*  452 */     return this.myx;
/*      */   }
/*      */ 
/*      */   public double getMxt()
/*      */   {
/*  465 */     return this.mxt;
/*      */   }
/*      */ 
/*      */   public double getMyt()
/*      */   {
/*  478 */     return this.myt;
/*      */   }
/*      */ 
/*      */   public boolean isIdentity()
/*      */   {
/*  489 */     return (this.state == 0) || (getType() == 0);
/*      */   }
/*      */ 
/*      */   public boolean isTranslateOrIdentity()
/*      */   {
/*  494 */     return (this.state <= 1) || (getType() <= 1);
/*      */   }
/*      */ 
/*      */   public boolean is2D()
/*      */   {
/*  499 */     return (this.state < 8) || (getType() <= 127);
/*      */   }
/*      */ 
/*      */   public double getDeterminant()
/*      */   {
/*  545 */     switch (this.state) {
/*      */     default:
/*  547 */       stateError();
/*      */     case 6:
/*      */     case 7:
/*  551 */       return this.mxx * this.myy - this.mxy * this.myx;
/*      */     case 4:
/*      */     case 5:
/*  554 */       return -(this.mxy * this.myx);
/*      */     case 2:
/*      */     case 3:
/*  557 */       return this.mxx * this.myy;
/*      */     case 0:
/*      */     case 1:
/*  560 */     }return 1.0D;
/*      */   }
/*      */ 
/*      */   protected abstract void reset3Delements();
/*      */ 
/*      */   public void setToIdentity()
/*      */   {
/*  577 */     this.mxx = (this.myy = 1.0D);
/*  578 */     this.myx = (this.mxy = this.mxt = this.myt = 0.0D);
/*  579 */     reset3Delements();
/*  580 */     this.state = 0;
/*  581 */     this.type = 0;
/*      */   }
/*      */ 
/*      */   public void setTransform(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6)
/*      */   {
/*  599 */     this.mxx = paramDouble1;
/*  600 */     this.myx = paramDouble2;
/*  601 */     this.mxy = paramDouble3;
/*  602 */     this.myy = paramDouble4;
/*  603 */     this.mxt = paramDouble5;
/*  604 */     this.myt = paramDouble6;
/*  605 */     reset3Delements();
/*  606 */     updateState2D();
/*      */   }
/*      */ 
/*      */   public void setToShear(double paramDouble1, double paramDouble2)
/*      */   {
/*  624 */     this.mxx = 1.0D;
/*  625 */     this.mxy = paramDouble1;
/*  626 */     this.myx = paramDouble2;
/*  627 */     this.myy = 1.0D;
/*  628 */     this.mxt = 0.0D;
/*  629 */     this.myt = 0.0D;
/*  630 */     reset3Delements();
/*  631 */     if ((paramDouble1 != 0.0D) || (paramDouble2 != 0.0D)) {
/*  632 */       this.state = 6;
/*  633 */       this.type = -1;
/*      */     } else {
/*  635 */       this.state = 0;
/*  636 */       this.type = 0;
/*      */     }
/*      */   }
/*      */ 
/*      */   public Point2D transform(Point2D paramPoint2D) {
/*  641 */     return transform(paramPoint2D, paramPoint2D);
/*      */   }
/*      */ 
/*      */   public Point2D transform(Point2D paramPoint2D1, Point2D paramPoint2D2)
/*      */   {
/*  663 */     if (paramPoint2D2 == null) {
/*  664 */       paramPoint2D2 = new Point2D();
/*      */     }
/*      */ 
/*  667 */     double d1 = paramPoint2D1.x;
/*  668 */     double d2 = paramPoint2D1.y;
/*      */ 
/*  674 */     switch (this.state & 0x7) {
/*      */     default:
/*  676 */       stateError();
/*      */     case 7:
/*  679 */       paramPoint2D2.setLocation((float)(d1 * this.mxx + d2 * this.mxy + this.mxt), (float)(d1 * this.myx + d2 * this.myy + this.myt));
/*      */ 
/*  681 */       return paramPoint2D2;
/*      */     case 6:
/*  683 */       paramPoint2D2.setLocation((float)(d1 * this.mxx + d2 * this.mxy), (float)(d1 * this.myx + d2 * this.myy));
/*      */ 
/*  685 */       return paramPoint2D2;
/*      */     case 5:
/*  687 */       paramPoint2D2.setLocation((float)(d2 * this.mxy + this.mxt), (float)(d1 * this.myx + this.myt));
/*      */ 
/*  689 */       return paramPoint2D2;
/*      */     case 4:
/*  691 */       paramPoint2D2.setLocation((float)(d2 * this.mxy), (float)(d1 * this.myx));
/*  692 */       return paramPoint2D2;
/*      */     case 3:
/*  694 */       paramPoint2D2.setLocation((float)(d1 * this.mxx + this.mxt), (float)(d2 * this.myy + this.myt));
/*  695 */       return paramPoint2D2;
/*      */     case 2:
/*  697 */       paramPoint2D2.setLocation((float)(d1 * this.mxx), (float)(d2 * this.myy));
/*  698 */       return paramPoint2D2;
/*      */     case 1:
/*  700 */       paramPoint2D2.setLocation((float)(d1 + this.mxt), (float)(d2 + this.myt));
/*  701 */       return paramPoint2D2;
/*      */     case 0:
/*  703 */     }paramPoint2D2.setLocation((float)d1, (float)d2);
/*  704 */     return paramPoint2D2;
/*      */   }
/*      */ 
/*      */   public Vec3d transform(Vec3d paramVec3d1, Vec3d paramVec3d2)
/*      */   {
/*  711 */     if (paramVec3d2 == null) {
/*  712 */       paramVec3d2 = new Vec3d();
/*      */     }
/*      */ 
/*  715 */     double d1 = paramVec3d1.x;
/*  716 */     double d2 = paramVec3d1.y;
/*  717 */     double d3 = paramVec3d1.z;
/*      */ 
/*  719 */     switch (this.state) {
/*      */     default:
/*  721 */       stateError();
/*      */     case 7:
/*  724 */       paramVec3d2.x = (d1 * this.mxx + d2 * this.mxy + this.mxt);
/*  725 */       paramVec3d2.y = (d1 * this.myx + d2 * this.myy + this.myt);
/*  726 */       paramVec3d2.z = d3;
/*  727 */       return paramVec3d2;
/*      */     case 6:
/*  729 */       paramVec3d2.x = (d1 * this.mxx + d2 * this.mxy);
/*  730 */       paramVec3d2.y = (d1 * this.myx + d2 * this.myy);
/*  731 */       paramVec3d2.z = d3;
/*  732 */       return paramVec3d2;
/*      */     case 5:
/*  734 */       paramVec3d2.x = (d2 * this.mxy + this.mxt);
/*  735 */       paramVec3d2.y = (d1 * this.myx + this.myt);
/*  736 */       paramVec3d2.z = d3;
/*  737 */       return paramVec3d2;
/*      */     case 4:
/*  739 */       paramVec3d2.x = (d2 * this.mxy);
/*  740 */       paramVec3d2.y = (d1 * this.myx);
/*  741 */       paramVec3d2.z = d3;
/*  742 */       return paramVec3d2;
/*      */     case 3:
/*  744 */       paramVec3d2.x = (d1 * this.mxx + this.mxt);
/*  745 */       paramVec3d2.y = (d2 * this.myy + this.myt);
/*  746 */       paramVec3d2.z = d3;
/*  747 */       return paramVec3d2;
/*      */     case 2:
/*  749 */       paramVec3d2.x = (d1 * this.mxx);
/*  750 */       paramVec3d2.y = (d2 * this.myy);
/*  751 */       paramVec3d2.z = d3;
/*  752 */       return paramVec3d2;
/*      */     case 1:
/*  754 */       paramVec3d2.x = (d1 + this.mxt);
/*  755 */       paramVec3d2.y = (d2 + this.myt);
/*  756 */       paramVec3d2.z = d3;
/*  757 */       return paramVec3d2;
/*      */     case 0:
/*  759 */     }paramVec3d2.x = d1;
/*  760 */     paramVec3d2.y = d2;
/*  761 */     paramVec3d2.z = d3;
/*  762 */     return paramVec3d2;
/*      */   }
/*      */ 
/*      */   private BaseBounds transform2DBounds(RectBounds paramRectBounds1, RectBounds paramRectBounds2)
/*      */   {
/*  769 */     switch (this.state & 0x7) {
/*      */     default:
/*  771 */       stateError();
/*      */     case 6:
/*      */     case 7:
/*  776 */       double d1 = paramRectBounds1.getMinX();
/*  777 */       double d2 = paramRectBounds1.getMinY();
/*  778 */       double d3 = paramRectBounds1.getMaxX();
/*  779 */       double d4 = paramRectBounds1.getMaxY();
/*  780 */       paramRectBounds2.setBoundsAndSort((float)(d1 * this.mxx + d2 * this.mxy), (float)(d1 * this.myx + d2 * this.myy), (float)(d3 * this.mxx + d4 * this.mxy), (float)(d3 * this.myx + d4 * this.myy));
/*      */ 
/*  784 */       paramRectBounds2.add((float)(d1 * this.mxx + d4 * this.mxy), (float)(d1 * this.myx + d4 * this.myy));
/*      */ 
/*  786 */       paramRectBounds2.add((float)(d3 * this.mxx + d2 * this.mxy), (float)(d3 * this.myx + d2 * this.myy));
/*      */ 
/*  788 */       paramRectBounds2.setBounds((float)(paramRectBounds2.getMinX() + this.mxt), (float)(paramRectBounds2.getMinY() + this.myt), (float)(paramRectBounds2.getMaxX() + this.mxt), (float)(paramRectBounds2.getMaxY() + this.myt));
/*      */ 
/*  792 */       break;
/*      */     case 5:
/*  794 */       paramRectBounds2.setBoundsAndSort((float)(paramRectBounds1.getMinY() * this.mxy + this.mxt), (float)(paramRectBounds1.getMinX() * this.myx + this.myt), (float)(paramRectBounds1.getMaxY() * this.mxy + this.mxt), (float)(paramRectBounds1.getMaxX() * this.myx + this.myt));
/*      */ 
/*  798 */       break;
/*      */     case 4:
/*  800 */       paramRectBounds2.setBoundsAndSort((float)(paramRectBounds1.getMinY() * this.mxy), (float)(paramRectBounds1.getMinX() * this.myx), (float)(paramRectBounds1.getMaxY() * this.mxy), (float)(paramRectBounds1.getMaxX() * this.myx));
/*      */ 
/*  804 */       break;
/*      */     case 3:
/*  806 */       paramRectBounds2.setBoundsAndSort((float)(paramRectBounds1.getMinX() * this.mxx + this.mxt), (float)(paramRectBounds1.getMinY() * this.myy + this.myt), (float)(paramRectBounds1.getMaxX() * this.mxx + this.mxt), (float)(paramRectBounds1.getMaxY() * this.myy + this.myt));
/*      */ 
/*  810 */       break;
/*      */     case 2:
/*  812 */       paramRectBounds2.setBoundsAndSort((float)(paramRectBounds1.getMinX() * this.mxx), (float)(paramRectBounds1.getMinY() * this.myy), (float)(paramRectBounds1.getMaxX() * this.mxx), (float)(paramRectBounds1.getMaxY() * this.myy));
/*      */ 
/*  816 */       break;
/*      */     case 1:
/*  818 */       paramRectBounds2.setBounds((float)(paramRectBounds1.getMinX() + this.mxt), (float)(paramRectBounds1.getMinY() + this.myt), (float)(paramRectBounds1.getMaxX() + this.mxt), (float)(paramRectBounds1.getMaxY() + this.myt));
/*      */ 
/*  822 */       break;
/*      */     case 0:
/*  824 */       if (paramRectBounds1 != paramRectBounds2) {
/*  825 */         paramRectBounds2.setBounds(paramRectBounds1);
/*      */       }
/*      */       break;
/*      */     }
/*  829 */     return paramRectBounds2;
/*      */   }
/*      */ 
/*      */   private BaseBounds transform3DBounds(BaseBounds paramBaseBounds1, BaseBounds paramBaseBounds2)
/*      */   {
/*  834 */     switch (this.state & 0x7) {
/*      */     default:
/*  836 */       stateError();
/*      */     case 6:
/*      */     case 7:
/*  842 */       double d1 = paramBaseBounds1.getMinX();
/*  843 */       double d2 = paramBaseBounds1.getMinY();
/*  844 */       double d3 = paramBaseBounds1.getMinZ();
/*  845 */       double d4 = paramBaseBounds1.getMaxX();
/*  846 */       double d5 = paramBaseBounds1.getMaxY();
/*  847 */       double d6 = paramBaseBounds1.getMaxZ();
/*  848 */       paramBaseBounds2.setBoundsAndSort((float)(d1 * this.mxx + d2 * this.mxy), (float)(d1 * this.myx + d2 * this.myy), (float)d3, (float)(d4 * this.mxx + d5 * this.mxy), (float)(d4 * this.myx + d5 * this.myy), (float)d6);
/*      */ 
/*  854 */       paramBaseBounds2.add((float)(d1 * this.mxx + d5 * this.mxy), (float)(d1 * this.myx + d5 * this.myy), 0.0F);
/*      */ 
/*  856 */       paramBaseBounds2.add((float)(d4 * this.mxx + d2 * this.mxy), (float)(d4 * this.myx + d2 * this.myy), 0.0F);
/*      */ 
/*  858 */       paramBaseBounds2.deriveWithNewBounds((float)(paramBaseBounds2.getMinX() + this.mxt), (float)(paramBaseBounds2.getMinY() + this.myt), paramBaseBounds2.getMinZ(), (float)(paramBaseBounds2.getMaxX() + this.mxt), (float)(paramBaseBounds2.getMaxY() + this.myt), paramBaseBounds2.getMaxZ());
/*      */ 
/*  864 */       break;
/*      */     case 5:
/*  866 */       paramBaseBounds2 = paramBaseBounds2.deriveWithNewBoundsAndSort((float)(paramBaseBounds1.getMinY() * this.mxy + this.mxt), (float)(paramBaseBounds1.getMinX() * this.myx + this.myt), paramBaseBounds1.getMinZ(), (float)(paramBaseBounds1.getMaxY() * this.mxy + this.mxt), (float)(paramBaseBounds1.getMaxX() * this.myx + this.myt), paramBaseBounds1.getMaxZ());
/*      */ 
/*  872 */       break;
/*      */     case 4:
/*  874 */       paramBaseBounds2 = paramBaseBounds2.deriveWithNewBoundsAndSort((float)(paramBaseBounds1.getMinY() * this.mxy), (float)(paramBaseBounds1.getMinX() * this.myx), paramBaseBounds1.getMinZ(), (float)(paramBaseBounds1.getMaxY() * this.mxy), (float)(paramBaseBounds1.getMaxX() * this.myx), paramBaseBounds1.getMaxZ());
/*      */ 
/*  880 */       break;
/*      */     case 3:
/*  882 */       paramBaseBounds2 = paramBaseBounds2.deriveWithNewBoundsAndSort((float)(paramBaseBounds1.getMinX() * this.mxx + this.mxt), (float)(paramBaseBounds1.getMinY() * this.myy + this.myt), paramBaseBounds1.getMinZ(), (float)(paramBaseBounds1.getMaxX() * this.mxx + this.mxt), (float)(paramBaseBounds1.getMaxY() * this.myy + this.myt), paramBaseBounds1.getMaxZ());
/*      */ 
/*  888 */       break;
/*      */     case 2:
/*  890 */       paramBaseBounds2 = paramBaseBounds2.deriveWithNewBoundsAndSort((float)(paramBaseBounds1.getMinX() * this.mxx), (float)(paramBaseBounds1.getMinY() * this.myy), paramBaseBounds1.getMinZ(), (float)(paramBaseBounds1.getMaxX() * this.mxx), (float)(paramBaseBounds1.getMaxY() * this.myy), paramBaseBounds1.getMaxZ());
/*      */ 
/*  896 */       break;
/*      */     case 1:
/*  898 */       paramBaseBounds2 = paramBaseBounds2.deriveWithNewBounds((float)(paramBaseBounds1.getMinX() + this.mxt), (float)(paramBaseBounds1.getMinY() + this.myt), paramBaseBounds1.getMinZ(), (float)(paramBaseBounds1.getMaxX() + this.mxt), (float)(paramBaseBounds1.getMaxY() + this.myt), paramBaseBounds1.getMaxZ());
/*      */ 
/*  904 */       break;
/*      */     case 0:
/*  906 */       if (paramBaseBounds1 != paramBaseBounds2) {
/*  907 */         paramBaseBounds2 = paramBaseBounds2.deriveWithNewBounds(paramBaseBounds1);
/*      */       }
/*      */       break;
/*      */     }
/*  911 */     return paramBaseBounds2;
/*      */   }
/*      */ 
/*      */   public BaseBounds transform(BaseBounds paramBaseBounds1, BaseBounds paramBaseBounds2)
/*      */   {
/*  916 */     if ((!paramBaseBounds1.is2D()) || (!paramBaseBounds2.is2D())) {
/*  917 */       return transform3DBounds(paramBaseBounds1, paramBaseBounds2);
/*      */     }
/*  919 */     return transform2DBounds((RectBounds)paramBaseBounds1, (RectBounds)paramBaseBounds2);
/*      */   }
/*      */ 
/*      */   public void transform(Rectangle paramRectangle1, Rectangle paramRectangle2)
/*      */   {
/*  924 */     switch (this.state & 0x7) {
/*      */     default:
/*  926 */       stateError();
/*      */     case 2:
/*      */     case 3:
/*      */     case 4:
/*      */     case 5:
/*      */     case 6:
/*      */     case 7:
/*  934 */       RectBounds localRectBounds = new RectBounds(paramRectangle1);
/*      */ 
/*  936 */       localRectBounds = (RectBounds)transform(localRectBounds, localRectBounds);
/*  937 */       paramRectangle2.setBounds(localRectBounds);
/*  938 */       return;
/*      */     case 1:
/*  940 */       Translate2D.transform(paramRectangle1, paramRectangle2, this.mxt, this.myt);
/*  941 */       return;
/*      */     case 0:
/*  943 */     }if (paramRectangle2 != paramRectangle1)
/*  944 */       paramRectangle2.setBounds(paramRectangle1);
/*      */   }
/*      */ 
/*      */   public void transform(float[] paramArrayOfFloat1, int paramInt1, float[] paramArrayOfFloat2, int paramInt2, int paramInt3)
/*      */   {
/*  975 */     doTransform(paramArrayOfFloat1, paramInt1, paramArrayOfFloat2, paramInt2, paramInt3, this.state & 0x7);
/*      */   }
/*      */ 
/*      */   public void deltaTransform(float[] paramArrayOfFloat1, int paramInt1, float[] paramArrayOfFloat2, int paramInt2, int paramInt3)
/*      */   {
/* 1014 */     doTransform(paramArrayOfFloat1, paramInt1, paramArrayOfFloat2, paramInt2, paramInt3, this.state & 0x6);
/*      */   }
/*      */ 
/*      */   private void doTransform(float[] paramArrayOfFloat1, int paramInt1, float[] paramArrayOfFloat2, int paramInt2, int paramInt3, int paramInt4)
/*      */   {
/* 1023 */     if ((paramArrayOfFloat2 == paramArrayOfFloat1) && (paramInt2 > paramInt1) && (paramInt2 < paramInt1 + paramInt3 * 2))
/*      */     {
/* 1034 */       System.arraycopy(paramArrayOfFloat1, paramInt1, paramArrayOfFloat2, paramInt2, paramInt3 * 2);
/*      */ 
/* 1036 */       paramInt1 = paramInt2;
/*      */     }
/*      */     double d1;
/*      */     double d2;
/*      */     double d3;
/*      */     double d4;
/*      */     double d5;
/*      */     double d6;
/*      */     double d7;
/*      */     double d8;
/* 1042 */     switch (paramInt4) {
/*      */     default:
/* 1044 */       stateError();
/*      */     case 7:
/* 1047 */       d1 = this.mxx; d2 = this.mxy; d3 = this.mxt;
/* 1048 */       d4 = this.myx; d5 = this.myy; d6 = this.myt;
/*      */       while (true) { paramInt3--; if (paramInt3 < 0) break;
/* 1050 */         d7 = paramArrayOfFloat1[(paramInt1++)];
/* 1051 */         d8 = paramArrayOfFloat1[(paramInt1++)];
/* 1052 */         paramArrayOfFloat2[(paramInt2++)] = ((float)(d1 * d7 + d2 * d8 + d3));
/* 1053 */         paramArrayOfFloat2[(paramInt2++)] = ((float)(d4 * d7 + d5 * d8 + d6));
/*      */       }
/* 1055 */       return;
/*      */     case 6:
/* 1057 */       d1 = this.mxx; d2 = this.mxy;
/* 1058 */       d4 = this.myx; d5 = this.myy;
/*      */       while (true) { paramInt3--; if (paramInt3 < 0) break;
/* 1060 */         d7 = paramArrayOfFloat1[(paramInt1++)];
/* 1061 */         d8 = paramArrayOfFloat1[(paramInt1++)];
/* 1062 */         paramArrayOfFloat2[(paramInt2++)] = ((float)(d1 * d7 + d2 * d8));
/* 1063 */         paramArrayOfFloat2[(paramInt2++)] = ((float)(d4 * d7 + d5 * d8));
/*      */       }
/* 1065 */       return;
/*      */     case 5:
/* 1067 */       d2 = this.mxy; d3 = this.mxt;
/* 1068 */       d4 = this.myx; d6 = this.myt;
/*      */       while (true) { paramInt3--; if (paramInt3 < 0) break;
/* 1070 */         d7 = paramArrayOfFloat1[(paramInt1++)];
/* 1071 */         paramArrayOfFloat2[(paramInt2++)] = ((float)(d2 * paramArrayOfFloat1[(paramInt1++)] + d3));
/* 1072 */         paramArrayOfFloat2[(paramInt2++)] = ((float)(d4 * d7 + d6));
/*      */       }
/* 1074 */       return;
/*      */     case 4:
/* 1076 */       d2 = this.mxy; d4 = this.myx;
/*      */       while (true) { paramInt3--; if (paramInt3 < 0) break;
/* 1078 */         d7 = paramArrayOfFloat1[(paramInt1++)];
/* 1079 */         paramArrayOfFloat2[(paramInt2++)] = ((float)(d2 * paramArrayOfFloat1[(paramInt1++)]));
/* 1080 */         paramArrayOfFloat2[(paramInt2++)] = ((float)(d4 * d7));
/*      */       }
/* 1082 */       return;
/*      */     case 3:
/* 1084 */       d1 = this.mxx; d3 = this.mxt;
/* 1085 */       d5 = this.myy; d6 = this.myt;
/*      */       while (true) { paramInt3--; if (paramInt3 < 0) break;
/* 1087 */         paramArrayOfFloat2[(paramInt2++)] = ((float)(d1 * paramArrayOfFloat1[(paramInt1++)] + d3));
/* 1088 */         paramArrayOfFloat2[(paramInt2++)] = ((float)(d5 * paramArrayOfFloat1[(paramInt1++)] + d6));
/*      */       }
/* 1090 */       return;
/*      */     case 2:
/* 1092 */       d1 = this.mxx; d5 = this.myy;
/*      */       while (true) { paramInt3--; if (paramInt3 < 0) break;
/* 1094 */         paramArrayOfFloat2[(paramInt2++)] = ((float)(d1 * paramArrayOfFloat1[(paramInt1++)]));
/* 1095 */         paramArrayOfFloat2[(paramInt2++)] = ((float)(d5 * paramArrayOfFloat1[(paramInt1++)]));
/*      */       }
/* 1097 */       return;
/*      */     case 1:
/* 1099 */       d3 = this.mxt; d6 = this.myt;
/*      */       while (true) { paramInt3--; if (paramInt3 < 0) break;
/* 1101 */         paramArrayOfFloat2[(paramInt2++)] = ((float)(paramArrayOfFloat1[(paramInt1++)] + d3));
/* 1102 */         paramArrayOfFloat2[(paramInt2++)] = ((float)(paramArrayOfFloat1[(paramInt1++)] + d6));
/*      */       }
/* 1104 */       return;
/*      */     case 0:
/* 1106 */     }if ((paramArrayOfFloat1 != paramArrayOfFloat2) || (paramInt1 != paramInt2))
/* 1107 */       System.arraycopy(paramArrayOfFloat1, paramInt1, paramArrayOfFloat2, paramInt2, paramInt3 * 2);
/*      */   }
/*      */ 
/*      */   public void transform(double[] paramArrayOfDouble1, int paramInt1, double[] paramArrayOfDouble2, int paramInt2, int paramInt3)
/*      */   {
/* 1141 */     doTransform(paramArrayOfDouble1, paramInt1, paramArrayOfDouble2, paramInt2, paramInt3, this.state & 0x7);
/*      */   }
/*      */ 
/*      */   public void deltaTransform(double[] paramArrayOfDouble1, int paramInt1, double[] paramArrayOfDouble2, int paramInt2, int paramInt3)
/*      */   {
/* 1180 */     doTransform(paramArrayOfDouble1, paramInt1, paramArrayOfDouble2, paramInt2, paramInt3, this.state & 0x6);
/*      */   }
/*      */ 
/*      */   private void doTransform(double[] paramArrayOfDouble1, int paramInt1, double[] paramArrayOfDouble2, int paramInt2, int paramInt3, int paramInt4)
/*      */   {
/* 1189 */     if ((paramArrayOfDouble2 == paramArrayOfDouble1) && (paramInt2 > paramInt1) && (paramInt2 < paramInt1 + paramInt3 * 2))
/*      */     {
/* 1200 */       System.arraycopy(paramArrayOfDouble1, paramInt1, paramArrayOfDouble2, paramInt2, paramInt3 * 2);
/*      */ 
/* 1202 */       paramInt1 = paramInt2;
/*      */     }
/*      */     double d1;
/*      */     double d2;
/*      */     double d3;
/*      */     double d4;
/*      */     double d5;
/*      */     double d6;
/*      */     double d7;
/*      */     double d8;
/* 1208 */     switch (paramInt4) {
/*      */     default:
/* 1210 */       stateError();
/*      */     case 7:
/* 1213 */       d1 = this.mxx; d2 = this.mxy; d3 = this.mxt;
/* 1214 */       d4 = this.myx; d5 = this.myy; d6 = this.myt;
/*      */       while (true) { paramInt3--; if (paramInt3 < 0) break;
/* 1216 */         d7 = paramArrayOfDouble1[(paramInt1++)];
/* 1217 */         d8 = paramArrayOfDouble1[(paramInt1++)];
/* 1218 */         paramArrayOfDouble2[(paramInt2++)] = (d1 * d7 + d2 * d8 + d3);
/* 1219 */         paramArrayOfDouble2[(paramInt2++)] = (d4 * d7 + d5 * d8 + d6);
/*      */       }
/* 1221 */       return;
/*      */     case 6:
/* 1223 */       d1 = this.mxx; d2 = this.mxy;
/* 1224 */       d4 = this.myx; d5 = this.myy;
/*      */       while (true) { paramInt3--; if (paramInt3 < 0) break;
/* 1226 */         d7 = paramArrayOfDouble1[(paramInt1++)];
/* 1227 */         d8 = paramArrayOfDouble1[(paramInt1++)];
/* 1228 */         paramArrayOfDouble2[(paramInt2++)] = (d1 * d7 + d2 * d8);
/* 1229 */         paramArrayOfDouble2[(paramInt2++)] = (d4 * d7 + d5 * d8);
/*      */       }
/* 1231 */       return;
/*      */     case 5:
/* 1233 */       d2 = this.mxy; d3 = this.mxt;
/* 1234 */       d4 = this.myx; d6 = this.myt;
/*      */       while (true) { paramInt3--; if (paramInt3 < 0) break;
/* 1236 */         d7 = paramArrayOfDouble1[(paramInt1++)];
/* 1237 */         paramArrayOfDouble2[(paramInt2++)] = (d2 * paramArrayOfDouble1[(paramInt1++)] + d3);
/* 1238 */         paramArrayOfDouble2[(paramInt2++)] = (d4 * d7 + d6);
/*      */       }
/* 1240 */       return;
/*      */     case 4:
/* 1242 */       d2 = this.mxy; d4 = this.myx;
/*      */       while (true) { paramInt3--; if (paramInt3 < 0) break;
/* 1244 */         d7 = paramArrayOfDouble1[(paramInt1++)];
/* 1245 */         paramArrayOfDouble2[(paramInt2++)] = (d2 * paramArrayOfDouble1[(paramInt1++)]);
/* 1246 */         paramArrayOfDouble2[(paramInt2++)] = (d4 * d7);
/*      */       }
/* 1248 */       return;
/*      */     case 3:
/* 1250 */       d1 = this.mxx; d3 = this.mxt;
/* 1251 */       d5 = this.myy; d6 = this.myt;
/*      */       while (true) { paramInt3--; if (paramInt3 < 0) break;
/* 1253 */         paramArrayOfDouble2[(paramInt2++)] = (d1 * paramArrayOfDouble1[(paramInt1++)] + d3);
/* 1254 */         paramArrayOfDouble2[(paramInt2++)] = (d5 * paramArrayOfDouble1[(paramInt1++)] + d6);
/*      */       }
/* 1256 */       return;
/*      */     case 2:
/* 1258 */       d1 = this.mxx; d5 = this.myy;
/*      */       while (true) { paramInt3--; if (paramInt3 < 0) break;
/* 1260 */         paramArrayOfDouble2[(paramInt2++)] = (d1 * paramArrayOfDouble1[(paramInt1++)]);
/* 1261 */         paramArrayOfDouble2[(paramInt2++)] = (d5 * paramArrayOfDouble1[(paramInt1++)]);
/*      */       }
/* 1263 */       return;
/*      */     case 1:
/* 1265 */       d3 = this.mxt; d6 = this.myt;
/*      */       while (true) { paramInt3--; if (paramInt3 < 0) break;
/* 1267 */         paramArrayOfDouble1[(paramInt1++)] += d3;
/* 1268 */         paramArrayOfDouble1[(paramInt1++)] += d6;
/*      */       }
/* 1270 */       return;
/*      */     case 0:
/* 1272 */     }if ((paramArrayOfDouble1 != paramArrayOfDouble2) || (paramInt1 != paramInt2))
/* 1273 */       System.arraycopy(paramArrayOfDouble1, paramInt1, paramArrayOfDouble2, paramInt2, paramInt3 * 2);
/*      */   }
/*      */ 
/*      */   public void transform(float[] paramArrayOfFloat, int paramInt1, double[] paramArrayOfDouble, int paramInt2, int paramInt3)
/*      */   {
/*      */     double d1;
/*      */     double d2;
/*      */     double d3;
/*      */     double d4;
/*      */     double d5;
/*      */     double d6;
/*      */     double d7;
/*      */     double d8;
/* 1307 */     switch (this.state & 0x7) {
/*      */     default:
/* 1309 */       stateError();
/*      */     case 7:
/* 1312 */       d1 = this.mxx; d2 = this.mxy; d3 = this.mxt;
/* 1313 */       d4 = this.myx; d5 = this.myy; d6 = this.myt;
/*      */       while (true) { paramInt3--; if (paramInt3 < 0) break;
/* 1315 */         d7 = paramArrayOfFloat[(paramInt1++)];
/* 1316 */         d8 = paramArrayOfFloat[(paramInt1++)];
/* 1317 */         paramArrayOfDouble[(paramInt2++)] = (d1 * d7 + d2 * d8 + d3);
/* 1318 */         paramArrayOfDouble[(paramInt2++)] = (d4 * d7 + d5 * d8 + d6);
/*      */       }
/* 1320 */       return;
/*      */     case 6:
/* 1322 */       d1 = this.mxx; d2 = this.mxy;
/* 1323 */       d4 = this.myx; d5 = this.myy;
/*      */       while (true) { paramInt3--; if (paramInt3 < 0) break;
/* 1325 */         d7 = paramArrayOfFloat[(paramInt1++)];
/* 1326 */         d8 = paramArrayOfFloat[(paramInt1++)];
/* 1327 */         paramArrayOfDouble[(paramInt2++)] = (d1 * d7 + d2 * d8);
/* 1328 */         paramArrayOfDouble[(paramInt2++)] = (d4 * d7 + d5 * d8);
/*      */       }
/* 1330 */       return;
/*      */     case 5:
/* 1332 */       d2 = this.mxy; d3 = this.mxt;
/* 1333 */       d4 = this.myx; d6 = this.myt;
/*      */       while (true) { paramInt3--; if (paramInt3 < 0) break;
/* 1335 */         d7 = paramArrayOfFloat[(paramInt1++)];
/* 1336 */         paramArrayOfDouble[(paramInt2++)] = (d2 * paramArrayOfFloat[(paramInt1++)] + d3);
/* 1337 */         paramArrayOfDouble[(paramInt2++)] = (d4 * d7 + d6);
/*      */       }
/* 1339 */       return;
/*      */     case 4:
/* 1341 */       d2 = this.mxy; d4 = this.myx;
/*      */       while (true) { paramInt3--; if (paramInt3 < 0) break;
/* 1343 */         d7 = paramArrayOfFloat[(paramInt1++)];
/* 1344 */         paramArrayOfDouble[(paramInt2++)] = (d2 * paramArrayOfFloat[(paramInt1++)]);
/* 1345 */         paramArrayOfDouble[(paramInt2++)] = (d4 * d7);
/*      */       }
/* 1347 */       return;
/*      */     case 3:
/* 1349 */       d1 = this.mxx; d3 = this.mxt;
/* 1350 */       d5 = this.myy; d6 = this.myt;
/*      */       while (true) { paramInt3--; if (paramInt3 < 0) break;
/* 1352 */         paramArrayOfDouble[(paramInt2++)] = (d1 * paramArrayOfFloat[(paramInt1++)] + d3);
/* 1353 */         paramArrayOfDouble[(paramInt2++)] = (d5 * paramArrayOfFloat[(paramInt1++)] + d6);
/*      */       }
/* 1355 */       return;
/*      */     case 2:
/* 1357 */       d1 = this.mxx; d5 = this.myy;
/*      */       while (true) { paramInt3--; if (paramInt3 < 0) break;
/* 1359 */         paramArrayOfDouble[(paramInt2++)] = (d1 * paramArrayOfFloat[(paramInt1++)]);
/* 1360 */         paramArrayOfDouble[(paramInt2++)] = (d5 * paramArrayOfFloat[(paramInt1++)]);
/*      */       }
/* 1362 */       return;
/*      */     case 1:
/* 1364 */       d3 = this.mxt; d6 = this.myt;
/*      */       while (true) { paramInt3--; if (paramInt3 < 0) break;
/* 1366 */         paramArrayOfDouble[(paramInt2++)] = (paramArrayOfFloat[(paramInt1++)] + d3);
/* 1367 */         paramArrayOfDouble[(paramInt2++)] = (paramArrayOfFloat[(paramInt1++)] + d6); } return;
/*      */     case 0:
/*      */     }
/*      */     while (true) {
/* 1371 */       paramInt3--; if (paramInt3 < 0) break;
/* 1372 */       paramArrayOfDouble[(paramInt2++)] = paramArrayOfFloat[(paramInt1++)];
/* 1373 */       paramArrayOfDouble[(paramInt2++)] = paramArrayOfFloat[(paramInt1++)];
/*      */     }
/*      */   }
/*      */ 
/*      */   public void transform(double[] paramArrayOfDouble, int paramInt1, float[] paramArrayOfFloat, int paramInt2, int paramInt3)
/*      */   {
/*      */     double d1;
/*      */     double d2;
/*      */     double d3;
/*      */     double d4;
/*      */     double d5;
/*      */     double d6;
/*      */     double d7;
/*      */     double d8;
/* 1406 */     switch (this.state & 0x7) {
/*      */     default:
/* 1408 */       stateError();
/*      */     case 7:
/* 1411 */       d1 = this.mxx; d2 = this.mxy; d3 = this.mxt;
/* 1412 */       d4 = this.myx; d5 = this.myy; d6 = this.myt;
/*      */       while (true) { paramInt3--; if (paramInt3 < 0) break;
/* 1414 */         d7 = paramArrayOfDouble[(paramInt1++)];
/* 1415 */         d8 = paramArrayOfDouble[(paramInt1++)];
/* 1416 */         paramArrayOfFloat[(paramInt2++)] = ((float)(d1 * d7 + d2 * d8 + d3));
/* 1417 */         paramArrayOfFloat[(paramInt2++)] = ((float)(d4 * d7 + d5 * d8 + d6));
/*      */       }
/* 1419 */       return;
/*      */     case 6:
/* 1421 */       d1 = this.mxx; d2 = this.mxy;
/* 1422 */       d4 = this.myx; d5 = this.myy;
/*      */       while (true) { paramInt3--; if (paramInt3 < 0) break;
/* 1424 */         d7 = paramArrayOfDouble[(paramInt1++)];
/* 1425 */         d8 = paramArrayOfDouble[(paramInt1++)];
/* 1426 */         paramArrayOfFloat[(paramInt2++)] = ((float)(d1 * d7 + d2 * d8));
/* 1427 */         paramArrayOfFloat[(paramInt2++)] = ((float)(d4 * d7 + d5 * d8));
/*      */       }
/* 1429 */       return;
/*      */     case 5:
/* 1431 */       d2 = this.mxy; d3 = this.mxt;
/* 1432 */       d4 = this.myx; d6 = this.myt;
/*      */       while (true) { paramInt3--; if (paramInt3 < 0) break;
/* 1434 */         d7 = paramArrayOfDouble[(paramInt1++)];
/* 1435 */         paramArrayOfFloat[(paramInt2++)] = ((float)(d2 * paramArrayOfDouble[(paramInt1++)] + d3));
/* 1436 */         paramArrayOfFloat[(paramInt2++)] = ((float)(d4 * d7 + d6));
/*      */       }
/* 1438 */       return;
/*      */     case 4:
/* 1440 */       d2 = this.mxy; d4 = this.myx;
/*      */       while (true) { paramInt3--; if (paramInt3 < 0) break;
/* 1442 */         d7 = paramArrayOfDouble[(paramInt1++)];
/* 1443 */         paramArrayOfFloat[(paramInt2++)] = ((float)(d2 * paramArrayOfDouble[(paramInt1++)]));
/* 1444 */         paramArrayOfFloat[(paramInt2++)] = ((float)(d4 * d7));
/*      */       }
/* 1446 */       return;
/*      */     case 3:
/* 1448 */       d1 = this.mxx; d3 = this.mxt;
/* 1449 */       d5 = this.myy; d6 = this.myt;
/*      */       while (true) { paramInt3--; if (paramInt3 < 0) break;
/* 1451 */         paramArrayOfFloat[(paramInt2++)] = ((float)(d1 * paramArrayOfDouble[(paramInt1++)] + d3));
/* 1452 */         paramArrayOfFloat[(paramInt2++)] = ((float)(d5 * paramArrayOfDouble[(paramInt1++)] + d6));
/*      */       }
/* 1454 */       return;
/*      */     case 2:
/* 1456 */       d1 = this.mxx; d5 = this.myy;
/*      */       while (true) { paramInt3--; if (paramInt3 < 0) break;
/* 1458 */         paramArrayOfFloat[(paramInt2++)] = ((float)(d1 * paramArrayOfDouble[(paramInt1++)]));
/* 1459 */         paramArrayOfFloat[(paramInt2++)] = ((float)(d5 * paramArrayOfDouble[(paramInt1++)]));
/*      */       }
/* 1461 */       return;
/*      */     case 1:
/* 1463 */       d3 = this.mxt; d6 = this.myt;
/*      */       while (true) { paramInt3--; if (paramInt3 < 0) break;
/* 1465 */         paramArrayOfFloat[(paramInt2++)] = ((float)(paramArrayOfDouble[(paramInt1++)] + d3));
/* 1466 */         paramArrayOfFloat[(paramInt2++)] = ((float)(paramArrayOfDouble[(paramInt1++)] + d6)); } return;
/*      */     case 0:
/*      */     }
/*      */     while (true) {
/* 1470 */       paramInt3--; if (paramInt3 < 0) break;
/* 1471 */       paramArrayOfFloat[(paramInt2++)] = ((float)paramArrayOfDouble[(paramInt1++)]);
/* 1472 */       paramArrayOfFloat[(paramInt2++)] = ((float)paramArrayOfDouble[(paramInt1++)]);
/*      */     }
/*      */   }
/*      */ 
/*      */   public Point2D inverseTransform(Point2D paramPoint2D1, Point2D paramPoint2D2)
/*      */     throws NoninvertibleTransformException
/*      */   {
/* 1502 */     if (paramPoint2D2 == null) {
/* 1503 */       paramPoint2D2 = new Point2D();
/*      */     }
/*      */ 
/* 1506 */     double d1 = paramPoint2D1.x;
/* 1507 */     double d2 = paramPoint2D1.y;
/*      */ 
/* 1509 */     switch (this.state) {
/*      */     default:
/* 1511 */       stateError();
/*      */     case 7:
/* 1514 */       d1 -= this.mxt;
/* 1515 */       d2 -= this.myt;
/*      */     case 6:
/* 1518 */       double d3 = this.mxx * this.myy - this.mxy * this.myx;
/* 1519 */       if ((d3 == 0.0D) || (Math.abs(d3) <= 4.9E-324D)) {
/* 1520 */         throw new NoninvertibleTransformException("Determinant is " + d3);
/*      */       }
/*      */ 
/* 1523 */       paramPoint2D2.setLocation((float)((d1 * this.myy - d2 * this.mxy) / d3), (float)((d2 * this.mxx - d1 * this.myx) / d3));
/*      */ 
/* 1525 */       return paramPoint2D2;
/*      */     case 5:
/* 1527 */       d1 -= this.mxt;
/* 1528 */       d2 -= this.myt;
/*      */     case 4:
/* 1531 */       if ((this.mxy == 0.0D) || (this.myx == 0.0D)) {
/* 1532 */         throw new NoninvertibleTransformException("Determinant is 0");
/*      */       }
/* 1534 */       paramPoint2D2.setLocation((float)(d2 / this.myx), (float)(d1 / this.mxy));
/* 1535 */       return paramPoint2D2;
/*      */     case 3:
/* 1537 */       d1 -= this.mxt;
/* 1538 */       d2 -= this.myt;
/*      */     case 2:
/* 1541 */       if ((this.mxx == 0.0D) || (this.myy == 0.0D)) {
/* 1542 */         throw new NoninvertibleTransformException("Determinant is 0");
/*      */       }
/* 1544 */       paramPoint2D2.setLocation((float)(d1 / this.mxx), (float)(d2 / this.myy));
/* 1545 */       return paramPoint2D2;
/*      */     case 1:
/* 1547 */       paramPoint2D2.setLocation((float)(d1 - this.mxt), (float)(d2 - this.myt));
/* 1548 */       return paramPoint2D2;
/*      */     case 0:
/* 1550 */     }paramPoint2D2.setLocation((float)d1, (float)d2);
/* 1551 */     return paramPoint2D2;
/*      */   }
/*      */ 
/*      */   public Vec3d inverseTransform(Vec3d paramVec3d1, Vec3d paramVec3d2)
/*      */     throws NoninvertibleTransformException
/*      */   {
/* 1578 */     if (paramVec3d2 == null) {
/* 1579 */       paramVec3d2 = new Vec3d();
/*      */     }
/*      */ 
/* 1582 */     double d1 = paramVec3d1.x;
/* 1583 */     double d2 = paramVec3d1.y;
/* 1584 */     double d3 = paramVec3d1.z;
/*      */ 
/* 1586 */     switch (this.state) {
/*      */     default:
/* 1588 */       stateError();
/*      */     case 7:
/* 1591 */       d1 -= this.mxt;
/* 1592 */       d2 -= this.myt;
/*      */     case 6:
/* 1595 */       double d4 = this.mxx * this.myy - this.mxy * this.myx;
/* 1596 */       if ((d4 == 0.0D) || (Math.abs(d4) <= 4.9E-324D)) {
/* 1597 */         throw new NoninvertibleTransformException("Determinant is " + d4);
/*      */       }
/*      */ 
/* 1600 */       paramVec3d2.set((d1 * this.myy - d2 * this.mxy) / d4, (d2 * this.mxx - d1 * this.myx) / d4, d3);
/* 1601 */       return paramVec3d2;
/*      */     case 5:
/* 1603 */       d1 -= this.mxt;
/* 1604 */       d2 -= this.myt;
/*      */     case 4:
/* 1607 */       if ((this.mxy == 0.0D) || (this.myx == 0.0D)) {
/* 1608 */         throw new NoninvertibleTransformException("Determinant is 0");
/*      */       }
/* 1610 */       paramVec3d2.set(d2 / this.myx, d1 / this.mxy, d3);
/* 1611 */       return paramVec3d2;
/*      */     case 3:
/* 1613 */       d1 -= this.mxt;
/* 1614 */       d2 -= this.myt;
/*      */     case 2:
/* 1617 */       if ((this.mxx == 0.0D) || (this.myy == 0.0D)) {
/* 1618 */         throw new NoninvertibleTransformException("Determinant is 0");
/*      */       }
/* 1620 */       paramVec3d2.set(d1 / this.mxx, d2 / this.myy, d3);
/* 1621 */       return paramVec3d2;
/*      */     case 1:
/* 1623 */       paramVec3d2.set(d1 - this.mxt, d2 - this.myt, d3);
/* 1624 */       return paramVec3d2;
/*      */     case 0:
/* 1626 */     }paramVec3d2.set(d1, d2, d3);
/* 1627 */     return paramVec3d2;
/*      */   }
/*      */ 
/*      */   private BaseBounds inversTransform2DBounds(RectBounds paramRectBounds1, RectBounds paramRectBounds2)
/*      */     throws NoninvertibleTransformException
/*      */   {
/* 1636 */     switch (this.state) {
/*      */     default:
/* 1638 */       stateError();
/*      */     case 6:
/*      */     case 7:
/* 1643 */       double d1 = this.mxx * this.myy - this.mxy * this.myx;
/* 1644 */       if ((d1 == 0.0D) || (Math.abs(d1) <= 4.9E-324D)) {
/* 1645 */         throw new NoninvertibleTransformException("Determinant is " + d1);
/*      */       }
/*      */ 
/* 1648 */       double d2 = paramRectBounds1.getMinX() - this.mxt;
/* 1649 */       double d3 = paramRectBounds1.getMinY() - this.myt;
/* 1650 */       double d4 = paramRectBounds1.getMaxX() - this.mxt;
/* 1651 */       double d5 = paramRectBounds1.getMaxY() - this.myt;
/* 1652 */       paramRectBounds2.setBoundsAndSort((float)((d2 * this.myy - d3 * this.mxy) / d1), (float)((d3 * this.mxx - d2 * this.myx) / d1), (float)((d4 * this.myy - d5 * this.mxy) / d1), (float)((d5 * this.mxx - d4 * this.myx) / d1));
/*      */ 
/* 1656 */       paramRectBounds2.add((float)((d4 * this.myy - d3 * this.mxy) / d1), (float)((d3 * this.mxx - d4 * this.myx) / d1));
/*      */ 
/* 1658 */       paramRectBounds2.add((float)((d2 * this.myy - d5 * this.mxy) / d1), (float)((d5 * this.mxx - d2 * this.myx) / d1));
/*      */ 
/* 1660 */       return paramRectBounds2;
/*      */     case 5:
/* 1662 */       if ((this.mxy == 0.0D) || (this.myx == 0.0D)) {
/* 1663 */         throw new NoninvertibleTransformException("Determinant is 0");
/*      */       }
/* 1665 */       paramRectBounds2.setBoundsAndSort((float)((paramRectBounds1.getMinY() - this.myt) / this.myx), (float)((paramRectBounds1.getMinX() - this.mxt) / this.mxy), (float)((paramRectBounds1.getMaxY() - this.myt) / this.myx), (float)((paramRectBounds1.getMaxX() - this.mxt) / this.mxy));
/*      */ 
/* 1669 */       break;
/*      */     case 4:
/* 1671 */       if ((this.mxy == 0.0D) || (this.myx == 0.0D)) {
/* 1672 */         throw new NoninvertibleTransformException("Determinant is 0");
/*      */       }
/* 1674 */       paramRectBounds2.setBoundsAndSort((float)(paramRectBounds1.getMinY() / this.myx), (float)(paramRectBounds1.getMinX() / this.mxy), (float)(paramRectBounds1.getMaxY() / this.myx), (float)(paramRectBounds1.getMaxX() / this.mxy));
/*      */ 
/* 1678 */       break;
/*      */     case 3:
/* 1680 */       if ((this.mxx == 0.0D) || (this.myy == 0.0D)) {
/* 1681 */         throw new NoninvertibleTransformException("Determinant is 0");
/*      */       }
/* 1683 */       paramRectBounds2.setBoundsAndSort((float)((paramRectBounds1.getMinX() - this.mxt) / this.mxx), (float)((paramRectBounds1.getMinY() - this.myt) / this.myy), (float)((paramRectBounds1.getMaxX() - this.mxt) / this.mxx), (float)((paramRectBounds1.getMaxY() - this.myt) / this.myy));
/*      */ 
/* 1687 */       break;
/*      */     case 2:
/* 1689 */       if ((this.mxx == 0.0D) || (this.myy == 0.0D)) {
/* 1690 */         throw new NoninvertibleTransformException("Determinant is 0");
/*      */       }
/* 1692 */       paramRectBounds2.setBoundsAndSort((float)(paramRectBounds1.getMinX() / this.mxx), (float)(paramRectBounds1.getMinY() / this.myy), (float)(paramRectBounds1.getMaxX() / this.mxx), (float)(paramRectBounds1.getMaxY() / this.myy));
/*      */ 
/* 1696 */       break;
/*      */     case 1:
/* 1698 */       paramRectBounds2.setBounds((float)(paramRectBounds1.getMinX() - this.mxt), (float)(paramRectBounds1.getMinY() - this.myt), (float)(paramRectBounds1.getMaxX() - this.mxt), (float)(paramRectBounds1.getMaxY() - this.myt));
/*      */ 
/* 1702 */       break;
/*      */     case 0:
/* 1704 */       if (paramRectBounds2 != paramRectBounds1) {
/* 1705 */         paramRectBounds2.setBounds(paramRectBounds1);
/*      */       }
/*      */       break;
/*      */     }
/* 1709 */     return paramRectBounds2;
/*      */   }
/*      */ 
/*      */   private BaseBounds inversTransform3DBounds(BaseBounds paramBaseBounds1, BaseBounds paramBaseBounds2)
/*      */     throws NoninvertibleTransformException
/*      */   {
/* 1716 */     switch (this.state) {
/*      */     default:
/* 1718 */       stateError();
/*      */     case 4:
/*      */     case 5:
/*      */     case 6:
/*      */     case 7:
/* 1727 */       double d1 = this.mxx * this.myy - this.mxy * this.myx;
/* 1728 */       if ((d1 == 0.0D) || (Math.abs(d1) <= 4.9E-324D)) {
/* 1729 */         throw new NoninvertibleTransformException("Determinant is " + d1);
/*      */       }
/*      */ 
/* 1732 */       double d2 = paramBaseBounds1.getMinX() - this.mxt;
/* 1733 */       double d3 = paramBaseBounds1.getMinY() - this.myt;
/* 1734 */       double d4 = paramBaseBounds1.getMinZ();
/* 1735 */       double d5 = paramBaseBounds1.getMaxX() - this.mxt;
/* 1736 */       double d6 = paramBaseBounds1.getMaxY() - this.myt;
/* 1737 */       double d7 = paramBaseBounds1.getMaxZ();
/* 1738 */       paramBaseBounds2 = paramBaseBounds2.deriveWithNewBoundsAndSort((float)((d2 * this.myy - d3 * this.mxy) / d1), (float)((d3 * this.mxx - d2 * this.myx) / d1), (float)(d4 / d1), (float)((d5 * this.myy - d6 * this.mxy) / d1), (float)((d6 * this.mxx - d5 * this.myx) / d1), (float)(d7 / d1));
/*      */ 
/* 1745 */       paramBaseBounds2.add((float)((d5 * this.myy - d3 * this.mxy) / d1), (float)((d3 * this.mxx - d5 * this.myx) / d1), 0.0F);
/*      */ 
/* 1747 */       paramBaseBounds2.add((float)((d2 * this.myy - d6 * this.mxy) / d1), (float)((d6 * this.mxx - d2 * this.myx) / d1), 0.0F);
/*      */ 
/* 1749 */       return paramBaseBounds2;
/*      */     case 3:
/* 1751 */       if ((this.mxx == 0.0D) || (this.myy == 0.0D)) {
/* 1752 */         throw new NoninvertibleTransformException("Determinant is 0");
/*      */       }
/* 1754 */       paramBaseBounds2 = paramBaseBounds2.deriveWithNewBoundsAndSort((float)((paramBaseBounds1.getMinX() - this.mxt) / this.mxx), (float)((paramBaseBounds1.getMinY() - this.myt) / this.myy), paramBaseBounds1.getMinZ(), (float)((paramBaseBounds1.getMaxX() - this.mxt) / this.mxx), (float)((paramBaseBounds1.getMaxY() - this.myt) / this.myy), paramBaseBounds1.getMaxZ());
/*      */ 
/* 1760 */       break;
/*      */     case 2:
/* 1762 */       if ((this.mxx == 0.0D) || (this.myy == 0.0D)) {
/* 1763 */         throw new NoninvertibleTransformException("Determinant is 0");
/*      */       }
/* 1765 */       paramBaseBounds2 = paramBaseBounds2.deriveWithNewBoundsAndSort((float)(paramBaseBounds1.getMinX() / this.mxx), (float)(paramBaseBounds1.getMinY() / this.myy), paramBaseBounds1.getMinZ(), (float)(paramBaseBounds1.getMaxX() / this.mxx), (float)(paramBaseBounds1.getMaxY() / this.myy), paramBaseBounds1.getMaxZ());
/*      */ 
/* 1771 */       break;
/*      */     case 1:
/* 1773 */       paramBaseBounds2 = paramBaseBounds2.deriveWithNewBounds((float)(paramBaseBounds1.getMinX() - this.mxt), (float)(paramBaseBounds1.getMinY() - this.myt), paramBaseBounds1.getMinZ(), (float)(paramBaseBounds1.getMaxX() - this.mxt), (float)(paramBaseBounds1.getMaxY() - this.myt), paramBaseBounds1.getMaxZ());
/*      */ 
/* 1779 */       break;
/*      */     case 0:
/* 1781 */       if (paramBaseBounds2 != paramBaseBounds1) {
/* 1782 */         paramBaseBounds2 = paramBaseBounds2.deriveWithNewBounds(paramBaseBounds1);
/*      */       }
/*      */       break;
/*      */     }
/* 1786 */     return paramBaseBounds2;
/*      */   }
/*      */ 
/*      */   public BaseBounds inverseTransform(BaseBounds paramBaseBounds1, BaseBounds paramBaseBounds2)
/*      */     throws NoninvertibleTransformException
/*      */   {
/* 1793 */     if ((!paramBaseBounds1.is2D()) || (!paramBaseBounds2.is2D())) {
/* 1794 */       return inversTransform3DBounds(paramBaseBounds1, paramBaseBounds2);
/*      */     }
/* 1796 */     return inversTransform2DBounds((RectBounds)paramBaseBounds1, (RectBounds)paramBaseBounds2);
/*      */   }
/*      */ 
/*      */   public void inverseTransform(Rectangle paramRectangle1, Rectangle paramRectangle2)
/*      */     throws NoninvertibleTransformException
/*      */   {
/* 1803 */     switch (this.state) {
/*      */     default:
/* 1805 */       stateError();
/*      */     case 2:
/*      */     case 3:
/*      */     case 4:
/*      */     case 5:
/*      */     case 6:
/*      */     case 7:
/* 1813 */       RectBounds localRectBounds = new RectBounds(paramRectangle1);
/*      */ 
/* 1815 */       localRectBounds = (RectBounds)inverseTransform(localRectBounds, localRectBounds);
/* 1816 */       paramRectangle2.setBounds(localRectBounds);
/* 1817 */       return;
/*      */     case 1:
/* 1819 */       Translate2D.transform(paramRectangle1, paramRectangle2, -this.mxt, -this.myt);
/* 1820 */       return;
/*      */     case 0:
/* 1822 */     }if (paramRectangle2 != paramRectangle1)
/* 1823 */       paramRectangle2.setBounds(paramRectangle1);
/*      */   }
/*      */ 
/*      */   public void inverseTransform(float[] paramArrayOfFloat1, int paramInt1, float[] paramArrayOfFloat2, int paramInt2, int paramInt3)
/*      */     throws NoninvertibleTransformException
/*      */   {
/* 1858 */     doInverseTransform(paramArrayOfFloat1, paramInt1, paramArrayOfFloat2, paramInt2, paramInt3, this.state);
/*      */   }
/*      */ 
/*      */   public void inverseDeltaTransform(float[] paramArrayOfFloat1, int paramInt1, float[] paramArrayOfFloat2, int paramInt2, int paramInt3)
/*      */     throws NoninvertibleTransformException
/*      */   {
/* 1890 */     doInverseTransform(paramArrayOfFloat1, paramInt1, paramArrayOfFloat2, paramInt2, paramInt3, this.state & 0xFFFFFFFE);
/*      */   }
/*      */ 
/*      */   private void doInverseTransform(float[] paramArrayOfFloat1, int paramInt1, float[] paramArrayOfFloat2, int paramInt2, int paramInt3, int paramInt4)
/*      */     throws NoninvertibleTransformException
/*      */   {
/* 1906 */     if ((paramArrayOfFloat2 == paramArrayOfFloat1) && (paramInt2 > paramInt1) && (paramInt2 < paramInt1 + paramInt3 * 2))
/*      */     {
/* 1917 */       System.arraycopy(paramArrayOfFloat1, paramInt1, paramArrayOfFloat2, paramInt2, paramInt3 * 2);
/*      */ 
/* 1919 */       paramInt1 = paramInt2;
/*      */     }
/*      */     double d1;
/*      */     double d2;
/*      */     double d3;
/*      */     double d4;
/*      */     double d5;
/*      */     double d6;
/*      */     double d7;
/*      */     double d8;
/*      */     double d9;
/* 1922 */     switch (paramInt4) {
/*      */     default:
/* 1924 */       stateError();
/*      */     case 7:
/* 1927 */       d1 = this.mxx; d2 = this.mxy; d3 = this.mxt;
/* 1928 */       d4 = this.myx; d5 = this.myy; d6 = this.myt;
/* 1929 */       d7 = d1 * d5 - d2 * d4;
/* 1930 */       if ((d7 == 0.0D) || (Math.abs(d7) <= 4.9E-324D))
/* 1931 */         throw new NoninvertibleTransformException("Determinant is " + d7);
/*      */       while (true)
/*      */       {
/* 1934 */         paramInt3--; if (paramInt3 < 0) break;
/* 1935 */         d8 = paramArrayOfFloat1[(paramInt1++)] - d3;
/* 1936 */         d9 = paramArrayOfFloat1[(paramInt1++)] - d6;
/* 1937 */         paramArrayOfFloat2[(paramInt2++)] = ((float)((d8 * d5 - d9 * d2) / d7));
/* 1938 */         paramArrayOfFloat2[(paramInt2++)] = ((float)((d9 * d1 - d8 * d4) / d7));
/*      */       }
/* 1940 */       return;
/*      */     case 6:
/* 1942 */       d1 = this.mxx; d2 = this.mxy;
/* 1943 */       d4 = this.myx; d5 = this.myy;
/* 1944 */       d7 = d1 * d5 - d2 * d4;
/* 1945 */       if ((d7 == 0.0D) || (Math.abs(d7) <= 4.9E-324D))
/* 1946 */         throw new NoninvertibleTransformException("Determinant is " + d7);
/*      */       while (true)
/*      */       {
/* 1949 */         paramInt3--; if (paramInt3 < 0) break;
/* 1950 */         d8 = paramArrayOfFloat1[(paramInt1++)];
/* 1951 */         d9 = paramArrayOfFloat1[(paramInt1++)];
/* 1952 */         paramArrayOfFloat2[(paramInt2++)] = ((float)((d8 * d5 - d9 * d2) / d7));
/* 1953 */         paramArrayOfFloat2[(paramInt2++)] = ((float)((d9 * d1 - d8 * d4) / d7));
/*      */       }
/* 1955 */       return;
/*      */     case 5:
/* 1957 */       d2 = this.mxy; d3 = this.mxt;
/* 1958 */       d4 = this.myx; d6 = this.myt;
/* 1959 */       if ((d2 == 0.0D) || (d4 == 0.0D))
/* 1960 */         throw new NoninvertibleTransformException("Determinant is 0");
/*      */       while (true) {
/* 1962 */         paramInt3--; if (paramInt3 < 0) break;
/* 1963 */         d8 = paramArrayOfFloat1[(paramInt1++)] - d3;
/* 1964 */         paramArrayOfFloat2[(paramInt2++)] = ((float)((paramArrayOfFloat1[(paramInt1++)] - d6) / d4));
/* 1965 */         paramArrayOfFloat2[(paramInt2++)] = ((float)(d8 / d2));
/*      */       }
/* 1967 */       return;
/*      */     case 4:
/* 1969 */       d2 = this.mxy; d4 = this.myx;
/* 1970 */       if ((d2 == 0.0D) || (d4 == 0.0D))
/* 1971 */         throw new NoninvertibleTransformException("Determinant is 0");
/*      */       while (true) {
/* 1973 */         paramInt3--; if (paramInt3 < 0) break;
/* 1974 */         d8 = paramArrayOfFloat1[(paramInt1++)];
/* 1975 */         paramArrayOfFloat2[(paramInt2++)] = ((float)(paramArrayOfFloat1[(paramInt1++)] / d4));
/* 1976 */         paramArrayOfFloat2[(paramInt2++)] = ((float)(d8 / d2));
/*      */       }
/* 1978 */       return;
/*      */     case 3:
/* 1980 */       d1 = this.mxx; d3 = this.mxt;
/* 1981 */       d5 = this.myy; d6 = this.myt;
/* 1982 */       if ((d1 == 0.0D) || (d5 == 0.0D))
/* 1983 */         throw new NoninvertibleTransformException("Determinant is 0");
/*      */       while (true) {
/* 1985 */         paramInt3--; if (paramInt3 < 0) break;
/* 1986 */         paramArrayOfFloat2[(paramInt2++)] = ((float)((paramArrayOfFloat1[(paramInt1++)] - d3) / d1));
/* 1987 */         paramArrayOfFloat2[(paramInt2++)] = ((float)((paramArrayOfFloat1[(paramInt1++)] - d6) / d5));
/*      */       }
/* 1989 */       return;
/*      */     case 2:
/* 1991 */       d1 = this.mxx; d5 = this.myy;
/* 1992 */       if ((d1 == 0.0D) || (d5 == 0.0D))
/* 1993 */         throw new NoninvertibleTransformException("Determinant is 0");
/*      */       while (true) {
/* 1995 */         paramInt3--; if (paramInt3 < 0) break;
/* 1996 */         paramArrayOfFloat2[(paramInt2++)] = ((float)(paramArrayOfFloat1[(paramInt1++)] / d1));
/* 1997 */         paramArrayOfFloat2[(paramInt2++)] = ((float)(paramArrayOfFloat1[(paramInt1++)] / d5));
/*      */       }
/* 1999 */       return;
/*      */     case 1:
/* 2001 */       d3 = this.mxt; d6 = this.myt;
/*      */       while (true) { paramInt3--; if (paramInt3 < 0) break;
/* 2003 */         paramArrayOfFloat2[(paramInt2++)] = ((float)(paramArrayOfFloat1[(paramInt1++)] - d3));
/* 2004 */         paramArrayOfFloat2[(paramInt2++)] = ((float)(paramArrayOfFloat1[(paramInt1++)] - d6));
/*      */       }
/* 2006 */       return;
/*      */     case 0:
/* 2008 */     }if ((paramArrayOfFloat1 != paramArrayOfFloat2) || (paramInt1 != paramInt2))
/* 2009 */       System.arraycopy(paramArrayOfFloat1, paramInt1, paramArrayOfFloat2, paramInt2, paramInt3 * 2);
/*      */   }
/*      */ 
/*      */   public void inverseTransform(double[] paramArrayOfDouble1, int paramInt1, double[] paramArrayOfDouble2, int paramInt2, int paramInt3)
/*      */     throws NoninvertibleTransformException
/*      */   {
/* 2049 */     if ((paramArrayOfDouble2 == paramArrayOfDouble1) && (paramInt2 > paramInt1) && (paramInt2 < paramInt1 + paramInt3 * 2))
/*      */     {
/* 2060 */       System.arraycopy(paramArrayOfDouble1, paramInt1, paramArrayOfDouble2, paramInt2, paramInt3 * 2);
/*      */ 
/* 2062 */       paramInt1 = paramInt2;
/*      */     }
/*      */     double d1;
/*      */     double d2;
/*      */     double d3;
/*      */     double d4;
/*      */     double d5;
/*      */     double d6;
/*      */     double d7;
/*      */     double d8;
/*      */     double d9;
/* 2065 */     switch (this.state) {
/*      */     default:
/* 2067 */       stateError();
/*      */     case 7:
/* 2070 */       d1 = this.mxx; d2 = this.mxy; d3 = this.mxt;
/* 2071 */       d4 = this.myx; d5 = this.myy; d6 = this.myt;
/* 2072 */       d7 = d1 * d5 - d2 * d4;
/* 2073 */       if ((d7 == 0.0D) || (Math.abs(d7) <= 4.9E-324D))
/* 2074 */         throw new NoninvertibleTransformException("Determinant is " + d7);
/*      */       while (true)
/*      */       {
/* 2077 */         paramInt3--; if (paramInt3 < 0) break;
/* 2078 */         d8 = paramArrayOfDouble1[(paramInt1++)] - d3;
/* 2079 */         d9 = paramArrayOfDouble1[(paramInt1++)] - d6;
/* 2080 */         paramArrayOfDouble2[(paramInt2++)] = ((d8 * d5 - d9 * d2) / d7);
/* 2081 */         paramArrayOfDouble2[(paramInt2++)] = ((d9 * d1 - d8 * d4) / d7);
/*      */       }
/* 2083 */       return;
/*      */     case 6:
/* 2085 */       d1 = this.mxx; d2 = this.mxy;
/* 2086 */       d4 = this.myx; d5 = this.myy;
/* 2087 */       d7 = d1 * d5 - d2 * d4;
/* 2088 */       if ((d7 == 0.0D) || (Math.abs(d7) <= 4.9E-324D))
/* 2089 */         throw new NoninvertibleTransformException("Determinant is " + d7);
/*      */       while (true)
/*      */       {
/* 2092 */         paramInt3--; if (paramInt3 < 0) break;
/* 2093 */         d8 = paramArrayOfDouble1[(paramInt1++)];
/* 2094 */         d9 = paramArrayOfDouble1[(paramInt1++)];
/* 2095 */         paramArrayOfDouble2[(paramInt2++)] = ((d8 * d5 - d9 * d2) / d7);
/* 2096 */         paramArrayOfDouble2[(paramInt2++)] = ((d9 * d1 - d8 * d4) / d7);
/*      */       }
/* 2098 */       return;
/*      */     case 5:
/* 2100 */       d2 = this.mxy; d3 = this.mxt;
/* 2101 */       d4 = this.myx; d6 = this.myt;
/* 2102 */       if ((d2 == 0.0D) || (d4 == 0.0D))
/* 2103 */         throw new NoninvertibleTransformException("Determinant is 0");
/*      */       while (true) {
/* 2105 */         paramInt3--; if (paramInt3 < 0) break;
/* 2106 */         d8 = paramArrayOfDouble1[(paramInt1++)] - d3;
/* 2107 */         paramArrayOfDouble2[(paramInt2++)] = ((paramArrayOfDouble1[(paramInt1++)] - d6) / d4);
/* 2108 */         paramArrayOfDouble2[(paramInt2++)] = (d8 / d2);
/*      */       }
/* 2110 */       return;
/*      */     case 4:
/* 2112 */       d2 = this.mxy; d4 = this.myx;
/* 2113 */       if ((d2 == 0.0D) || (d4 == 0.0D))
/* 2114 */         throw new NoninvertibleTransformException("Determinant is 0");
/*      */       while (true) {
/* 2116 */         paramInt3--; if (paramInt3 < 0) break;
/* 2117 */         d8 = paramArrayOfDouble1[(paramInt1++)];
/* 2118 */         paramArrayOfDouble1[(paramInt1++)] /= d4;
/* 2119 */         paramArrayOfDouble2[(paramInt2++)] = (d8 / d2);
/*      */       }
/* 2121 */       return;
/*      */     case 3:
/* 2123 */       d1 = this.mxx; d3 = this.mxt;
/* 2124 */       d5 = this.myy; d6 = this.myt;
/* 2125 */       if ((d1 == 0.0D) || (d5 == 0.0D))
/* 2126 */         throw new NoninvertibleTransformException("Determinant is 0");
/*      */       while (true) {
/* 2128 */         paramInt3--; if (paramInt3 < 0) break;
/* 2129 */         paramArrayOfDouble2[(paramInt2++)] = ((paramArrayOfDouble1[(paramInt1++)] - d3) / d1);
/* 2130 */         paramArrayOfDouble2[(paramInt2++)] = ((paramArrayOfDouble1[(paramInt1++)] - d6) / d5);
/*      */       }
/* 2132 */       return;
/*      */     case 2:
/* 2134 */       d1 = this.mxx; d5 = this.myy;
/* 2135 */       if ((d1 == 0.0D) || (d5 == 0.0D))
/* 2136 */         throw new NoninvertibleTransformException("Determinant is 0");
/*      */       while (true) {
/* 2138 */         paramInt3--; if (paramInt3 < 0) break;
/* 2139 */         paramArrayOfDouble1[(paramInt1++)] /= d1;
/* 2140 */         paramArrayOfDouble1[(paramInt1++)] /= d5;
/*      */       }
/* 2142 */       return;
/*      */     case 1:
/* 2144 */       d3 = this.mxt; d6 = this.myt;
/*      */       while (true) { paramInt3--; if (paramInt3 < 0) break;
/* 2146 */         paramArrayOfDouble1[(paramInt1++)] -= d3;
/* 2147 */         paramArrayOfDouble1[(paramInt1++)] -= d6;
/*      */       }
/* 2149 */       return;
/*      */     case 0:
/* 2151 */     }if ((paramArrayOfDouble1 != paramArrayOfDouble2) || (paramInt1 != paramInt2))
/* 2152 */       System.arraycopy(paramArrayOfDouble1, paramInt1, paramArrayOfDouble2, paramInt2, paramInt3 * 2);
/*      */   }
/*      */ 
/*      */   public Shape createTransformedShape(Shape paramShape)
/*      */   {
/* 2172 */     if (paramShape == null) {
/* 2173 */       return null;
/*      */     }
/* 2175 */     return new Path2D(paramShape, this);
/*      */   }
/*      */ 
/*      */   public void translate(double paramDouble1, double paramDouble2)
/*      */   {
/* 2195 */     switch (this.state) {
/*      */     default:
/* 2197 */       stateError();
/*      */     case 7:
/* 2200 */       this.mxt = (paramDouble1 * this.mxx + paramDouble2 * this.mxy + this.mxt);
/* 2201 */       this.myt = (paramDouble1 * this.myx + paramDouble2 * this.myy + this.myt);
/* 2202 */       if ((this.mxt == 0.0D) && (this.myt == 0.0D)) {
/* 2203 */         this.state = 6;
/* 2204 */         if (this.type != -1) {
/* 2205 */           this.type &= -2;
/*      */         }
/*      */       }
/* 2208 */       return;
/*      */     case 6:
/* 2210 */       this.mxt = (paramDouble1 * this.mxx + paramDouble2 * this.mxy);
/* 2211 */       this.myt = (paramDouble1 * this.myx + paramDouble2 * this.myy);
/* 2212 */       if ((this.mxt != 0.0D) || (this.myt != 0.0D)) {
/* 2213 */         this.state = 7;
/* 2214 */         this.type |= 1;
/*      */       }
/* 2216 */       return;
/*      */     case 5:
/* 2218 */       this.mxt = (paramDouble2 * this.mxy + this.mxt);
/* 2219 */       this.myt = (paramDouble1 * this.myx + this.myt);
/* 2220 */       if ((this.mxt == 0.0D) && (this.myt == 0.0D)) {
/* 2221 */         this.state = 4;
/* 2222 */         if (this.type != -1) {
/* 2223 */           this.type &= -2;
/*      */         }
/*      */       }
/* 2226 */       return;
/*      */     case 4:
/* 2228 */       this.mxt = (paramDouble2 * this.mxy);
/* 2229 */       this.myt = (paramDouble1 * this.myx);
/* 2230 */       if ((this.mxt != 0.0D) || (this.myt != 0.0D)) {
/* 2231 */         this.state = 5;
/* 2232 */         this.type |= 1;
/*      */       }
/* 2234 */       return;
/*      */     case 3:
/* 2236 */       this.mxt = (paramDouble1 * this.mxx + this.mxt);
/* 2237 */       this.myt = (paramDouble2 * this.myy + this.myt);
/* 2238 */       if ((this.mxt == 0.0D) && (this.myt == 0.0D)) {
/* 2239 */         this.state = 2;
/* 2240 */         if (this.type != -1) {
/* 2241 */           this.type &= -2;
/*      */         }
/*      */       }
/* 2244 */       return;
/*      */     case 2:
/* 2246 */       this.mxt = (paramDouble1 * this.mxx);
/* 2247 */       this.myt = (paramDouble2 * this.myy);
/* 2248 */       if ((this.mxt != 0.0D) || (this.myt != 0.0D)) {
/* 2249 */         this.state = 3;
/* 2250 */         this.type |= 1;
/*      */       }
/* 2252 */       return;
/*      */     case 1:
/* 2254 */       this.mxt = (paramDouble1 + this.mxt);
/* 2255 */       this.myt = (paramDouble2 + this.myt);
/* 2256 */       if ((this.mxt == 0.0D) && (this.myt == 0.0D)) {
/* 2257 */         this.state = 0;
/* 2258 */         this.type = 0;
/*      */       }
/* 2260 */       return;
/*      */     case 0:
/* 2262 */     }this.mxt = paramDouble1;
/* 2263 */     this.myt = paramDouble2;
/* 2264 */     if ((paramDouble1 != 0.0D) || (paramDouble2 != 0.0D)) {
/* 2265 */       this.state = 1;
/* 2266 */       this.type = 1;
/*      */     }
/*      */   }
/*      */ 
/*      */   protected final void rotate90()
/*      */   {
/* 2286 */     double d = this.mxx;
/* 2287 */     this.mxx = this.mxy;
/* 2288 */     this.mxy = (-d);
/* 2289 */     d = this.myx;
/* 2290 */     this.myx = this.myy;
/* 2291 */     this.myy = (-d);
/* 2292 */     int i = rot90conversion[this.state];
/* 2293 */     if (((i & 0x6) == 2) && (this.mxx == 1.0D) && (this.myy == 1.0D))
/*      */     {
/* 2296 */       i -= 2;
/*      */     }
/* 2298 */     this.state = i;
/* 2299 */     this.type = -1;
/*      */   }
/*      */   protected final void rotate180() {
/* 2302 */     this.mxx = (-this.mxx);
/* 2303 */     this.myy = (-this.myy);
/* 2304 */     int i = this.state;
/* 2305 */     if ((i & 0x4) != 0)
/*      */     {
/* 2308 */       this.mxy = (-this.mxy);
/* 2309 */       this.myx = (-this.myx);
/*      */     }
/* 2313 */     else if ((this.mxx == 1.0D) && (this.myy == 1.0D)) {
/* 2314 */       this.state = (i & 0xFFFFFFFD);
/*      */     } else {
/* 2316 */       this.state = (i | 0x2);
/*      */     }
/*      */ 
/* 2319 */     this.type = -1;
/*      */   }
/*      */   protected final void rotate270() {
/* 2322 */     double d = this.mxx;
/* 2323 */     this.mxx = (-this.mxy);
/* 2324 */     this.mxy = d;
/* 2325 */     d = this.myx;
/* 2326 */     this.myx = (-this.myy);
/* 2327 */     this.myy = d;
/* 2328 */     int i = rot90conversion[this.state];
/* 2329 */     if (((i & 0x6) == 2) && (this.mxx == 1.0D) && (this.myy == 1.0D))
/*      */     {
/* 2332 */       i -= 2;
/*      */     }
/* 2334 */     this.state = i;
/* 2335 */     this.type = -1;
/*      */   }
/*      */ 
/*      */   public void rotate(double paramDouble)
/*      */   {
/* 2357 */     double d1 = Math.sin(paramDouble);
/* 2358 */     if (d1 == 1.0D) {
/* 2359 */       rotate90();
/* 2360 */     } else if (d1 == -1.0D) {
/* 2361 */       rotate270();
/*      */     } else {
/* 2363 */       double d2 = Math.cos(paramDouble);
/* 2364 */       if (d2 == -1.0D) {
/* 2365 */         rotate180();
/* 2366 */       } else if (d2 != 1.0D)
/*      */       {
/* 2368 */         double d3 = this.mxx;
/* 2369 */         double d4 = this.mxy;
/* 2370 */         this.mxx = (d2 * d3 + d1 * d4);
/* 2371 */         this.mxy = (-d1 * d3 + d2 * d4);
/* 2372 */         d3 = this.myx;
/* 2373 */         d4 = this.myy;
/* 2374 */         this.myx = (d2 * d3 + d1 * d4);
/* 2375 */         this.myy = (-d1 * d3 + d2 * d4);
/* 2376 */         updateState2D();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void scale(double paramDouble1, double paramDouble2)
/*      */   {
/* 2397 */     int i = this.state;
/*      */ 
/* 2399 */     switch (i) {
/*      */     default:
/* 2401 */       stateError();
/*      */     case 6:
/*      */     case 7:
/* 2405 */       this.mxx *= paramDouble1;
/* 2406 */       this.myy *= paramDouble2;
/*      */     case 4:
/*      */     case 5:
/* 2410 */       this.mxy *= paramDouble2;
/* 2411 */       this.myx *= paramDouble1;
/* 2412 */       if ((this.mxy == 0.0D) && (this.myx == 0.0D)) {
/* 2413 */         i &= 1;
/* 2414 */         if ((this.mxx == 1.0D) && (this.myy == 1.0D)) {
/* 2415 */           this.type = (i == 0 ? 0 : 1);
/*      */         }
/*      */         else
/*      */         {
/* 2419 */           i |= 2;
/* 2420 */           this.type = -1;
/*      */         }
/* 2422 */         this.state = i;
/*      */       }
/* 2424 */       return;
/*      */     case 2:
/*      */     case 3:
/* 2427 */       this.mxx *= paramDouble1;
/* 2428 */       this.myy *= paramDouble2;
/* 2429 */       if ((this.mxx == 1.0D) && (this.myy == 1.0D)) {
/* 2430 */         this.state = (i &= 1);
/* 2431 */         this.type = (i == 0 ? 0 : 1);
/*      */       }
/*      */       else
/*      */       {
/* 2435 */         this.type = -1;
/*      */       }
/* 2437 */       return;
/*      */     case 0:
/*      */     case 1:
/* 2440 */     }this.mxx = paramDouble1;
/* 2441 */     this.myy = paramDouble2;
/* 2442 */     if ((paramDouble1 != 1.0D) || (paramDouble2 != 1.0D)) {
/* 2443 */       this.state = (i | 0x2);
/* 2444 */       this.type = -1;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void shear(double paramDouble1, double paramDouble2)
/*      */   {
/* 2466 */     int i = this.state;
/*      */ 
/* 2468 */     switch (i) {
/*      */     default:
/* 2470 */       stateError();
/*      */     case 6:
/*      */     case 7:
/* 2475 */       double d1 = this.mxx;
/* 2476 */       double d2 = this.mxy;
/* 2477 */       this.mxx = (d1 + d2 * paramDouble2);
/* 2478 */       this.mxy = (d1 * paramDouble1 + d2);
/*      */ 
/* 2480 */       d1 = this.myx;
/* 2481 */       d2 = this.myy;
/* 2482 */       this.myx = (d1 + d2 * paramDouble2);
/* 2483 */       this.myy = (d1 * paramDouble1 + d2);
/* 2484 */       updateState2D();
/* 2485 */       return;
/*      */     case 4:
/*      */     case 5:
/* 2488 */       this.mxx = (this.mxy * paramDouble2);
/* 2489 */       this.myy = (this.myx * paramDouble1);
/* 2490 */       if ((this.mxx != 0.0D) || (this.myy != 0.0D)) {
/* 2491 */         this.state = (i | 0x2);
/*      */       }
/* 2493 */       this.type = -1;
/* 2494 */       return;
/*      */     case 2:
/*      */     case 3:
/* 2497 */       this.mxy = (this.mxx * paramDouble1);
/* 2498 */       this.myx = (this.myy * paramDouble2);
/* 2499 */       if ((this.mxy != 0.0D) || (this.myx != 0.0D)) {
/* 2500 */         this.state = (i | 0x4);
/*      */       }
/* 2502 */       this.type = -1;
/* 2503 */       return;
/*      */     case 0:
/*      */     case 1:
/* 2506 */     }this.mxy = paramDouble1;
/* 2507 */     this.myx = paramDouble2;
/* 2508 */     if ((this.mxy != 0.0D) || (this.myx != 0.0D)) {
/* 2509 */       this.state = (i | 0x2 | 0x4);
/* 2510 */       this.type = -1;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void concatenate(BaseTransform paramBaseTransform)
/*      */   {
/* 2538 */     switch (1.$SwitchMap$com$sun$javafx$geom$transform$BaseTransform$Degree[paramBaseTransform.getDegree().ordinal()]) {
/*      */     case 1:
/* 2540 */       return;
/*      */     case 2:
/* 2542 */       translate(paramBaseTransform.getMxt(), paramBaseTransform.getMyt());
/* 2543 */       return;
/*      */     case 3:
/* 2545 */       break;
/*      */     default:
/* 2547 */       if (!paramBaseTransform.is2D()) {
/* 2548 */         degreeError(BaseTransform.Degree.AFFINE_2D);
/*      */       }
/*      */ 
/* 2555 */       if (!(paramBaseTransform instanceof AffineBase)) {
/* 2556 */         paramBaseTransform = new Affine2D(paramBaseTransform);
/*      */       }
/*      */ 
/*      */       break;
/*      */     }
/*      */ 
/* 2563 */     int i = this.state;
/* 2564 */     AffineBase localAffineBase = (AffineBase)paramBaseTransform;
/* 2565 */     int j = localAffineBase.state;
/*      */     double d1;
/* 2566 */     switch (j << 4 | i)
/*      */     {
/*      */     case 0:
/*      */     case 1:
/*      */     case 2:
/*      */     case 3:
/*      */     case 4:
/*      */     case 5:
/*      */     case 6:
/*      */     case 7:
/* 2577 */       return;
/*      */     case 112:
/* 2581 */       this.mxy = localAffineBase.mxy;
/* 2582 */       this.myx = localAffineBase.myx;
/*      */     case 48:
/* 2585 */       this.mxx = localAffineBase.mxx;
/* 2586 */       this.myy = localAffineBase.myy;
/*      */     case 16:
/* 2589 */       this.mxt = localAffineBase.mxt;
/* 2590 */       this.myt = localAffineBase.myt;
/* 2591 */       this.state = j;
/* 2592 */       this.type = localAffineBase.type;
/* 2593 */       return;
/*      */     case 96:
/* 2595 */       this.mxy = localAffineBase.mxy;
/* 2596 */       this.myx = localAffineBase.myx;
/*      */     case 32:
/* 2599 */       this.mxx = localAffineBase.mxx;
/* 2600 */       this.myy = localAffineBase.myy;
/* 2601 */       this.state = j;
/* 2602 */       this.type = localAffineBase.type;
/* 2603 */       return;
/*      */     case 80:
/* 2605 */       this.mxt = localAffineBase.mxt;
/* 2606 */       this.myt = localAffineBase.myt;
/*      */     case 64:
/* 2609 */       this.mxy = localAffineBase.mxy;
/* 2610 */       this.myx = localAffineBase.myx;
/* 2611 */       this.mxx = (this.myy = 0.0D);
/* 2612 */       this.state = j;
/* 2613 */       this.type = localAffineBase.type;
/* 2614 */       return;
/*      */     case 17:
/*      */     case 18:
/*      */     case 19:
/*      */     case 20:
/*      */     case 21:
/*      */     case 22:
/*      */     case 23:
/* 2624 */       translate(localAffineBase.mxt, localAffineBase.myt);
/* 2625 */       return;
/*      */     case 33:
/*      */     case 34:
/*      */     case 35:
/*      */     case 36:
/*      */     case 37:
/*      */     case 38:
/*      */     case 39:
/* 2635 */       scale(localAffineBase.mxx, localAffineBase.myy);
/* 2636 */       return;
/*      */     case 70:
/*      */     case 71:
/* 2641 */       d4 = localAffineBase.mxy; d5 = localAffineBase.myx;
/* 2642 */       d1 = this.mxx;
/* 2643 */       this.mxx = (this.mxy * d5);
/* 2644 */       this.mxy = (d1 * d4);
/* 2645 */       d1 = this.myx;
/* 2646 */       this.myx = (this.myy * d5);
/* 2647 */       this.myy = (d1 * d4);
/* 2648 */       this.type = -1;
/* 2649 */       return;
/*      */     case 68:
/*      */     case 69:
/* 2652 */       this.mxx = (this.mxy * localAffineBase.myx);
/* 2653 */       this.mxy = 0.0D;
/* 2654 */       this.myy = (this.myx * localAffineBase.mxy);
/* 2655 */       this.myx = 0.0D;
/* 2656 */       this.state = (i ^ 0x6);
/* 2657 */       this.type = -1;
/* 2658 */       return;
/*      */     case 66:
/*      */     case 67:
/* 2661 */       this.mxy = (this.mxx * localAffineBase.mxy);
/* 2662 */       this.mxx = 0.0D;
/* 2663 */       this.myx = (this.myy * localAffineBase.myx);
/* 2664 */       this.myy = 0.0D;
/* 2665 */       this.state = (i ^ 0x6);
/* 2666 */       this.type = -1;
/* 2667 */       return;
/*      */     case 65:
/* 2669 */       this.mxx = 0.0D;
/* 2670 */       this.mxy = localAffineBase.mxy;
/* 2671 */       this.myx = localAffineBase.myx;
/* 2672 */       this.myy = 0.0D;
/* 2673 */       this.state = 5;
/* 2674 */       this.type = -1;
/* 2675 */       return;
/*      */     case 8:
/*      */     case 9:
/*      */     case 10:
/*      */     case 11:
/*      */     case 12:
/*      */     case 13:
/*      */     case 14:
/*      */     case 15:
/*      */     case 24:
/*      */     case 25:
/*      */     case 26:
/*      */     case 27:
/*      */     case 28:
/*      */     case 29:
/*      */     case 30:
/*      */     case 31:
/*      */     case 40:
/*      */     case 41:
/*      */     case 42:
/*      */     case 43:
/*      */     case 44:
/*      */     case 45:
/*      */     case 46:
/*      */     case 47:
/*      */     case 49:
/*      */     case 50:
/*      */     case 51:
/*      */     case 52:
/*      */     case 53:
/*      */     case 54:
/*      */     case 55:
/*      */     case 56:
/*      */     case 57:
/*      */     case 58:
/*      */     case 59:
/*      */     case 60:
/*      */     case 61:
/*      */     case 62:
/*      */     case 63:
/*      */     case 72:
/*      */     case 73:
/*      */     case 74:
/*      */     case 75:
/*      */     case 76:
/*      */     case 77:
/*      */     case 78:
/*      */     case 79:
/*      */     case 81:
/*      */     case 82:
/*      */     case 83:
/*      */     case 84:
/*      */     case 85:
/*      */     case 86:
/*      */     case 87:
/*      */     case 88:
/*      */     case 89:
/*      */     case 90:
/*      */     case 91:
/*      */     case 92:
/*      */     case 93:
/*      */     case 94:
/*      */     case 95:
/*      */     case 97:
/*      */     case 98:
/*      */     case 99:
/*      */     case 100:
/*      */     case 101:
/*      */     case 102:
/*      */     case 103:
/*      */     case 104:
/*      */     case 105:
/*      */     case 106:
/*      */     case 107:
/*      */     case 108:
/*      */     case 109:
/*      */     case 110:
/* 2679 */     case 111: } double d3 = localAffineBase.mxx; double d4 = localAffineBase.mxy; double d7 = localAffineBase.mxt;
/* 2680 */     double d5 = localAffineBase.myx; double d6 = localAffineBase.myy; double d8 = localAffineBase.myt;
/* 2681 */     switch (i) {
/*      */     default:
/* 2683 */       stateError();
/*      */     case 6:
/* 2686 */       this.state = (i | j);
/*      */     case 7:
/* 2689 */       d1 = this.mxx;
/* 2690 */       double d2 = this.mxy;
/* 2691 */       this.mxx = (d3 * d1 + d5 * d2);
/* 2692 */       this.mxy = (d4 * d1 + d6 * d2);
/* 2693 */       this.mxt += d7 * d1 + d8 * d2;
/*      */ 
/* 2695 */       d1 = this.myx;
/* 2696 */       d2 = this.myy;
/* 2697 */       this.myx = (d3 * d1 + d5 * d2);
/* 2698 */       this.myy = (d4 * d1 + d6 * d2);
/* 2699 */       this.myt += d7 * d1 + d8 * d2;
/* 2700 */       this.type = -1;
/* 2701 */       return;
/*      */     case 4:
/*      */     case 5:
/* 2705 */       d1 = this.mxy;
/* 2706 */       this.mxx = (d5 * d1);
/* 2707 */       this.mxy = (d6 * d1);
/* 2708 */       this.mxt += d8 * d1;
/*      */ 
/* 2710 */       d1 = this.myx;
/* 2711 */       this.myx = (d3 * d1);
/* 2712 */       this.myy = (d4 * d1);
/* 2713 */       this.myt += d7 * d1;
/* 2714 */       break;
/*      */     case 2:
/*      */     case 3:
/* 2718 */       d1 = this.mxx;
/* 2719 */       this.mxx = (d3 * d1);
/* 2720 */       this.mxy = (d4 * d1);
/* 2721 */       this.mxt += d7 * d1;
/*      */ 
/* 2723 */       d1 = this.myy;
/* 2724 */       this.myx = (d5 * d1);
/* 2725 */       this.myy = (d6 * d1);
/* 2726 */       this.myt += d8 * d1;
/* 2727 */       break;
/*      */     case 1:
/* 2730 */       this.mxx = d3;
/* 2731 */       this.mxy = d4;
/* 2732 */       this.mxt += d7;
/*      */ 
/* 2734 */       this.myx = d5;
/* 2735 */       this.myy = d6;
/* 2736 */       this.myt += d8;
/* 2737 */       this.state = (j | 0x1);
/* 2738 */       this.type = -1;
/* 2739 */       return;
/*      */     }
/* 2741 */     updateState2D();
/*      */   }
/*      */ 
/*      */   public void invert()
/*      */     throws NoninvertibleTransformException
/*      */   {
/*      */     double d1;
/*      */     double d2;
/*      */     double d3;
/*      */     double d4;
/*      */     double d5;
/*      */     double d6;
/*      */     double d7;
/* 2770 */     switch (this.state) {
/*      */     default:
/* 2772 */       stateError();
/*      */     case 7:
/* 2775 */       d1 = this.mxx; d2 = this.mxy; d3 = this.mxt;
/* 2776 */       d4 = this.myx; d5 = this.myy; d6 = this.myt;
/* 2777 */       d7 = d1 * d5 - d2 * d4;
/* 2778 */       if ((d7 == 0.0D) || (Math.abs(d7) <= 4.9E-324D)) {
/* 2779 */         throw new NoninvertibleTransformException("Determinant is " + d7);
/*      */       }
/*      */ 
/* 2782 */       this.mxx = (d5 / d7);
/* 2783 */       this.myx = (-d4 / d7);
/* 2784 */       this.mxy = (-d2 / d7);
/* 2785 */       this.myy = (d1 / d7);
/* 2786 */       this.mxt = ((d2 * d6 - d5 * d3) / d7);
/* 2787 */       this.myt = ((d4 * d3 - d1 * d6) / d7);
/* 2788 */       break;
/*      */     case 6:
/* 2790 */       d1 = this.mxx; d2 = this.mxy;
/* 2791 */       d4 = this.myx; d5 = this.myy;
/* 2792 */       d7 = d1 * d5 - d2 * d4;
/* 2793 */       if ((d7 == 0.0D) || (Math.abs(d7) <= 4.9E-324D)) {
/* 2794 */         throw new NoninvertibleTransformException("Determinant is " + d7);
/*      */       }
/*      */ 
/* 2797 */       this.mxx = (d5 / d7);
/* 2798 */       this.myx = (-d4 / d7);
/* 2799 */       this.mxy = (-d2 / d7);
/* 2800 */       this.myy = (d1 / d7);
/*      */ 
/* 2803 */       break;
/*      */     case 5:
/* 2805 */       d2 = this.mxy; d3 = this.mxt;
/* 2806 */       d4 = this.myx; d6 = this.myt;
/* 2807 */       if ((d2 == 0.0D) || (d4 == 0.0D)) {
/* 2808 */         throw new NoninvertibleTransformException("Determinant is 0");
/*      */       }
/*      */ 
/* 2811 */       this.myx = (1.0D / d2);
/* 2812 */       this.mxy = (1.0D / d4);
/*      */ 
/* 2814 */       this.mxt = (-d6 / d4);
/* 2815 */       this.myt = (-d3 / d2);
/* 2816 */       break;
/*      */     case 4:
/* 2818 */       d2 = this.mxy;
/* 2819 */       d4 = this.myx;
/* 2820 */       if ((d2 == 0.0D) || (d4 == 0.0D)) {
/* 2821 */         throw new NoninvertibleTransformException("Determinant is 0");
/*      */       }
/*      */ 
/* 2824 */       this.myx = (1.0D / d2);
/* 2825 */       this.mxy = (1.0D / d4);
/*      */ 
/* 2829 */       break;
/*      */     case 3:
/* 2831 */       d1 = this.mxx; d3 = this.mxt;
/* 2832 */       d5 = this.myy; d6 = this.myt;
/* 2833 */       if ((d1 == 0.0D) || (d5 == 0.0D)) {
/* 2834 */         throw new NoninvertibleTransformException("Determinant is 0");
/*      */       }
/* 2836 */       this.mxx = (1.0D / d1);
/*      */ 
/* 2839 */       this.myy = (1.0D / d5);
/* 2840 */       this.mxt = (-d3 / d1);
/* 2841 */       this.myt = (-d6 / d5);
/* 2842 */       break;
/*      */     case 2:
/* 2844 */       d1 = this.mxx;
/* 2845 */       d5 = this.myy;
/* 2846 */       if ((d1 == 0.0D) || (d5 == 0.0D)) {
/* 2847 */         throw new NoninvertibleTransformException("Determinant is 0");
/*      */       }
/* 2849 */       this.mxx = (1.0D / d1);
/*      */ 
/* 2852 */       this.myy = (1.0D / d5);
/*      */ 
/* 2855 */       break;
/*      */     case 1:
/* 2861 */       this.mxt = (-this.mxt);
/* 2862 */       this.myt = (-this.myt);
/* 2863 */       break;
/*      */     case 0:
/*      */     }
/*      */   }
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.geom.transform.AffineBase
 * JD-Core Version:    0.6.2
 */