/*      */ package com.sun.javafx.geom.transform;
/*      */ 
/*      */ import com.sun.javafx.geom.Point2D;
/*      */ import java.io.PrintStream;
/*      */ 
/*      */ public class Affine2D extends AffineBase
/*      */ {
/* 1424 */   private static final long BASE_HASH = l;
/*      */ 
/*      */   private Affine2D(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, int paramInt)
/*      */   {
/*  121 */     this.mxx = paramDouble1;
/*  122 */     this.myx = paramDouble2;
/*  123 */     this.mxy = paramDouble3;
/*  124 */     this.myy = paramDouble4;
/*  125 */     this.mxt = paramDouble5;
/*  126 */     this.myt = paramDouble6;
/*  127 */     this.state = paramInt;
/*  128 */     this.type = -1;
/*      */   }
/*      */ 
/*      */   public Affine2D()
/*      */   {
/*  137 */     this.mxx = (this.myy = 1.0D);
/*      */   }
/*      */ 
/*      */   public Affine2D(BaseTransform paramBaseTransform)
/*      */   {
/*  150 */     setTransform(paramBaseTransform);
/*      */   }
/*      */ 
/*      */   public Affine2D(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
/*      */   {
/*  170 */     this.mxx = paramFloat1;
/*  171 */     this.myx = paramFloat2;
/*  172 */     this.mxy = paramFloat3;
/*  173 */     this.myy = paramFloat4;
/*  174 */     this.mxt = paramFloat5;
/*  175 */     this.myt = paramFloat6;
/*  176 */     updateState2D();
/*      */   }
/*      */ 
/*      */   public Affine2D(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6)
/*      */   {
/*  196 */     this.mxx = paramDouble1;
/*  197 */     this.myx = paramDouble2;
/*  198 */     this.mxy = paramDouble3;
/*  199 */     this.myy = paramDouble4;
/*  200 */     this.mxt = paramDouble5;
/*  201 */     this.myt = paramDouble6;
/*  202 */     updateState2D();
/*      */   }
/*      */ 
/*      */   public BaseTransform.Degree getDegree()
/*      */   {
/*  207 */     return BaseTransform.Degree.AFFINE_2D;
/*      */   }
/*      */ 
/*      */   protected void reset3Delements()
/*      */   {
/*      */   }
/*      */ 
/*      */   public void rotate(double paramDouble1, double paramDouble2, double paramDouble3)
/*      */   {
/*  241 */     translate(paramDouble2, paramDouble3);
/*  242 */     rotate(paramDouble1);
/*  243 */     translate(-paramDouble2, -paramDouble3);
/*      */   }
/*      */ 
/*      */   public void rotate(double paramDouble1, double paramDouble2)
/*      */   {
/*  265 */     if (paramDouble2 == 0.0D) {
/*  266 */       if (paramDouble1 < 0.0D) {
/*  267 */         rotate180();
/*      */       }
/*      */ 
/*      */     }
/*  271 */     else if (paramDouble1 == 0.0D) {
/*  272 */       if (paramDouble2 > 0.0D)
/*  273 */         rotate90();
/*      */       else
/*  275 */         rotate270();
/*      */     }
/*      */     else {
/*  278 */       double d1 = Math.sqrt(paramDouble1 * paramDouble1 + paramDouble2 * paramDouble2);
/*  279 */       double d2 = paramDouble2 / d1;
/*  280 */       double d3 = paramDouble1 / d1;
/*      */ 
/*  282 */       double d4 = this.mxx;
/*  283 */       double d5 = this.mxy;
/*  284 */       this.mxx = (d3 * d4 + d2 * d5);
/*  285 */       this.mxy = (-d2 * d4 + d3 * d5);
/*  286 */       d4 = this.myx;
/*  287 */       d5 = this.myy;
/*  288 */       this.myx = (d3 * d4 + d2 * d5);
/*  289 */       this.myy = (-d2 * d4 + d3 * d5);
/*  290 */       updateState2D();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void rotate(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*      */   {
/*  320 */     translate(paramDouble3, paramDouble4);
/*  321 */     rotate(paramDouble1, paramDouble2);
/*  322 */     translate(-paramDouble3, -paramDouble4);
/*      */   }
/*      */ 
/*      */   public void quadrantRotate(int paramInt)
/*      */   {
/*  338 */     switch (paramInt & 0x3) {
/*      */     case 0:
/*  340 */       break;
/*      */     case 1:
/*  342 */       rotate90();
/*  343 */       break;
/*      */     case 2:
/*  345 */       rotate180();
/*  346 */       break;
/*      */     case 3:
/*  348 */       rotate270();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void quadrantRotate(int paramInt, double paramDouble1, double paramDouble2)
/*      */   {
/*  372 */     switch (paramInt & 0x3) {
/*      */     case 0:
/*  374 */       return;
/*      */     case 1:
/*  376 */       this.mxt += paramDouble1 * (this.mxx - this.mxy) + paramDouble2 * (this.mxy + this.mxx);
/*  377 */       this.myt += paramDouble1 * (this.myx - this.myy) + paramDouble2 * (this.myy + this.myx);
/*  378 */       rotate90();
/*  379 */       break;
/*      */     case 2:
/*  381 */       this.mxt += paramDouble1 * (this.mxx + this.mxx) + paramDouble2 * (this.mxy + this.mxy);
/*  382 */       this.myt += paramDouble1 * (this.myx + this.myx) + paramDouble2 * (this.myy + this.myy);
/*  383 */       rotate180();
/*  384 */       break;
/*      */     case 3:
/*  386 */       this.mxt += paramDouble1 * (this.mxx + this.mxy) + paramDouble2 * (this.mxy - this.mxx);
/*  387 */       this.myt += paramDouble1 * (this.myx + this.myy) + paramDouble2 * (this.myy - this.myx);
/*  388 */       rotate270();
/*      */     }
/*      */ 
/*  391 */     if ((this.mxt == 0.0D) && (this.myt == 0.0D)) {
/*  392 */       this.state &= -2;
/*  393 */       if (this.type != -1)
/*  394 */         this.type &= -2;
/*      */     }
/*      */     else {
/*  397 */       this.state |= 1;
/*  398 */       this.type |= 1;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setToTranslation(double paramDouble1, double paramDouble2)
/*      */   {
/*  417 */     this.mxx = 1.0D;
/*  418 */     this.myx = 0.0D;
/*  419 */     this.mxy = 0.0D;
/*  420 */     this.myy = 1.0D;
/*  421 */     this.mxt = paramDouble1;
/*  422 */     this.myt = paramDouble2;
/*  423 */     if ((paramDouble1 != 0.0D) || (paramDouble2 != 0.0D)) {
/*  424 */       this.state = 1;
/*  425 */       this.type = 1;
/*      */     } else {
/*  427 */       this.state = 0;
/*  428 */       this.type = 0;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setToRotation(double paramDouble)
/*      */   {
/*  449 */     double d1 = Math.sin(paramDouble);
/*      */     double d2;
/*  451 */     if ((d1 == 1.0D) || (d1 == -1.0D)) {
/*  452 */       d2 = 0.0D;
/*  453 */       this.state = 4;
/*  454 */       this.type = 8;
/*      */     } else {
/*  456 */       d2 = Math.cos(paramDouble);
/*  457 */       if (d2 == -1.0D) {
/*  458 */         d1 = 0.0D;
/*  459 */         this.state = 2;
/*  460 */         this.type = 8;
/*  461 */       } else if (d2 == 1.0D) {
/*  462 */         d1 = 0.0D;
/*  463 */         this.state = 0;
/*  464 */         this.type = 0;
/*      */       } else {
/*  466 */         this.state = 6;
/*  467 */         this.type = 16;
/*      */       }
/*      */     }
/*  470 */     this.mxx = d2;
/*  471 */     this.myx = d1;
/*  472 */     this.mxy = (-d1);
/*  473 */     this.myy = d2;
/*  474 */     this.mxt = 0.0D;
/*  475 */     this.myt = 0.0D;
/*      */   }
/*      */ 
/*      */   public void setToRotation(double paramDouble1, double paramDouble2, double paramDouble3)
/*      */   {
/*  510 */     setToRotation(paramDouble1);
/*  511 */     double d1 = this.myx;
/*  512 */     double d2 = 1.0D - this.mxx;
/*  513 */     this.mxt = (paramDouble2 * d2 + paramDouble3 * d1);
/*  514 */     this.myt = (paramDouble3 * d2 - paramDouble2 * d1);
/*  515 */     if ((this.mxt != 0.0D) || (this.myt != 0.0D)) {
/*  516 */       this.state |= 1;
/*  517 */       this.type |= 1;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setToRotation(double paramDouble1, double paramDouble2)
/*      */   {
/*      */     double d1;
/*      */     double d2;
/*  541 */     if (paramDouble2 == 0.0D) {
/*  542 */       d1 = 0.0D;
/*  543 */       if (paramDouble1 < 0.0D) {
/*  544 */         d2 = -1.0D;
/*  545 */         this.state = 2;
/*  546 */         this.type = 8;
/*      */       } else {
/*  548 */         d2 = 1.0D;
/*  549 */         this.state = 0;
/*  550 */         this.type = 0;
/*      */       }
/*  552 */     } else if (paramDouble1 == 0.0D) {
/*  553 */       d2 = 0.0D;
/*  554 */       d1 = paramDouble2 > 0.0D ? 1.0D : -1.0D;
/*  555 */       this.state = 4;
/*  556 */       this.type = 8;
/*      */     } else {
/*  558 */       double d3 = Math.sqrt(paramDouble1 * paramDouble1 + paramDouble2 * paramDouble2);
/*  559 */       d2 = paramDouble1 / d3;
/*  560 */       d1 = paramDouble2 / d3;
/*  561 */       this.state = 6;
/*  562 */       this.type = 16;
/*      */     }
/*  564 */     this.mxx = d2;
/*  565 */     this.myx = d1;
/*  566 */     this.mxy = (-d1);
/*  567 */     this.myy = d2;
/*  568 */     this.mxt = 0.0D;
/*  569 */     this.myt = 0.0D;
/*      */   }
/*      */ 
/*      */   public void setToRotation(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*      */   {
/*  597 */     setToRotation(paramDouble1, paramDouble2);
/*  598 */     double d1 = this.myx;
/*  599 */     double d2 = 1.0D - this.mxx;
/*  600 */     this.mxt = (paramDouble3 * d2 + paramDouble4 * d1);
/*  601 */     this.myt = (paramDouble4 * d2 - paramDouble3 * d1);
/*  602 */     if ((this.mxt != 0.0D) || (this.myt != 0.0D)) {
/*  603 */       this.state |= 1;
/*  604 */       this.type |= 1;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setToQuadrantRotation(int paramInt)
/*      */   {
/*  621 */     switch (paramInt & 0x3) {
/*      */     case 0:
/*  623 */       this.mxx = 1.0D;
/*  624 */       this.myx = 0.0D;
/*  625 */       this.mxy = 0.0D;
/*  626 */       this.myy = 1.0D;
/*  627 */       this.mxt = 0.0D;
/*  628 */       this.myt = 0.0D;
/*  629 */       this.state = 0;
/*  630 */       this.type = 0;
/*  631 */       break;
/*      */     case 1:
/*  633 */       this.mxx = 0.0D;
/*  634 */       this.myx = 1.0D;
/*  635 */       this.mxy = -1.0D;
/*  636 */       this.myy = 0.0D;
/*  637 */       this.mxt = 0.0D;
/*  638 */       this.myt = 0.0D;
/*  639 */       this.state = 4;
/*  640 */       this.type = 8;
/*  641 */       break;
/*      */     case 2:
/*  643 */       this.mxx = -1.0D;
/*  644 */       this.myx = 0.0D;
/*  645 */       this.mxy = 0.0D;
/*  646 */       this.myy = -1.0D;
/*  647 */       this.mxt = 0.0D;
/*  648 */       this.myt = 0.0D;
/*  649 */       this.state = 2;
/*  650 */       this.type = 8;
/*  651 */       break;
/*      */     case 3:
/*  653 */       this.mxx = 0.0D;
/*  654 */       this.myx = -1.0D;
/*  655 */       this.mxy = 1.0D;
/*  656 */       this.myy = 0.0D;
/*  657 */       this.mxt = 0.0D;
/*  658 */       this.myt = 0.0D;
/*  659 */       this.state = 4;
/*  660 */       this.type = 8;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setToQuadrantRotation(int paramInt, double paramDouble1, double paramDouble2)
/*      */   {
/*  684 */     switch (paramInt & 0x3) {
/*      */     case 0:
/*  686 */       this.mxx = 1.0D;
/*  687 */       this.myx = 0.0D;
/*  688 */       this.mxy = 0.0D;
/*  689 */       this.myy = 1.0D;
/*  690 */       this.mxt = 0.0D;
/*  691 */       this.myt = 0.0D;
/*  692 */       this.state = 0;
/*  693 */       this.type = 0;
/*  694 */       break;
/*      */     case 1:
/*  696 */       this.mxx = 0.0D;
/*  697 */       this.myx = 1.0D;
/*  698 */       this.mxy = -1.0D;
/*  699 */       this.myy = 0.0D;
/*  700 */       this.mxt = (paramDouble1 + paramDouble2);
/*  701 */       this.myt = (paramDouble2 - paramDouble1);
/*  702 */       if ((this.mxt == 0.0D) && (this.myt == 0.0D)) {
/*  703 */         this.state = 4;
/*  704 */         this.type = 8;
/*      */       } else {
/*  706 */         this.state = 5;
/*  707 */         this.type = 9;
/*      */       }
/*  709 */       break;
/*      */     case 2:
/*  711 */       this.mxx = -1.0D;
/*  712 */       this.myx = 0.0D;
/*  713 */       this.mxy = 0.0D;
/*  714 */       this.myy = -1.0D;
/*  715 */       this.mxt = (paramDouble1 + paramDouble1);
/*  716 */       this.myt = (paramDouble2 + paramDouble2);
/*  717 */       if ((this.mxt == 0.0D) && (this.myt == 0.0D)) {
/*  718 */         this.state = 2;
/*  719 */         this.type = 8;
/*      */       } else {
/*  721 */         this.state = 3;
/*  722 */         this.type = 9;
/*      */       }
/*  724 */       break;
/*      */     case 3:
/*  726 */       this.mxx = 0.0D;
/*  727 */       this.myx = -1.0D;
/*  728 */       this.mxy = 1.0D;
/*  729 */       this.myy = 0.0D;
/*  730 */       this.mxt = (paramDouble1 - paramDouble2);
/*  731 */       this.myt = (paramDouble2 + paramDouble1);
/*  732 */       if ((this.mxt == 0.0D) && (this.myt == 0.0D)) {
/*  733 */         this.state = 4;
/*  734 */         this.type = 8;
/*      */       } else {
/*  736 */         this.state = 5;
/*  737 */         this.type = 9;
/*      */       }
/*      */       break;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setToScale(double paramDouble1, double paramDouble2)
/*      */   {
/*  758 */     this.mxx = paramDouble1;
/*  759 */     this.myx = 0.0D;
/*  760 */     this.mxy = 0.0D;
/*  761 */     this.myy = paramDouble2;
/*  762 */     this.mxt = 0.0D;
/*  763 */     this.myt = 0.0D;
/*  764 */     if ((paramDouble1 != 1.0D) || (paramDouble2 != 1.0D)) {
/*  765 */       this.state = 2;
/*  766 */       this.type = -1;
/*      */     } else {
/*  768 */       this.state = 0;
/*  769 */       this.type = 0;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setTransform(BaseTransform paramBaseTransform)
/*      */   {
/*  781 */     switch (1.$SwitchMap$com$sun$javafx$geom$transform$BaseTransform$Degree[paramBaseTransform.getDegree().ordinal()]) {
/*      */     case 1:
/*  783 */       setToIdentity();
/*  784 */       break;
/*      */     case 2:
/*  786 */       setToTranslation(paramBaseTransform.getMxt(), paramBaseTransform.getMyt());
/*  787 */       break;
/*      */     default:
/*  789 */       if (paramBaseTransform.getType() > 127) {
/*  790 */         System.out.println(paramBaseTransform + " is " + paramBaseTransform.getType());
/*  791 */         System.out.print("  " + paramBaseTransform.getMxx());
/*  792 */         System.out.print(", " + paramBaseTransform.getMxy());
/*  793 */         System.out.print(", " + paramBaseTransform.getMxz());
/*  794 */         System.out.print(", " + paramBaseTransform.getMxt());
/*  795 */         System.out.println();
/*  796 */         System.out.print("  " + paramBaseTransform.getMyx());
/*  797 */         System.out.print(", " + paramBaseTransform.getMyy());
/*  798 */         System.out.print(", " + paramBaseTransform.getMyz());
/*  799 */         System.out.print(", " + paramBaseTransform.getMyt());
/*  800 */         System.out.println();
/*  801 */         System.out.print("  " + paramBaseTransform.getMzx());
/*  802 */         System.out.print(", " + paramBaseTransform.getMzy());
/*  803 */         System.out.print(", " + paramBaseTransform.getMzz());
/*  804 */         System.out.print(", " + paramBaseTransform.getMzt());
/*  805 */         System.out.println();
/*      */ 
/*  807 */         degreeError(BaseTransform.Degree.AFFINE_2D);
/*      */       }break;
/*      */     case 3:
/*      */     }
/*  811 */     this.mxx = paramBaseTransform.getMxx();
/*  812 */     this.myx = paramBaseTransform.getMyx();
/*  813 */     this.mxy = paramBaseTransform.getMxy();
/*  814 */     this.myy = paramBaseTransform.getMyy();
/*  815 */     this.mxt = paramBaseTransform.getMxt();
/*  816 */     this.myt = paramBaseTransform.getMyt();
/*  817 */     if ((paramBaseTransform instanceof AffineBase)) {
/*  818 */       this.state = ((AffineBase)paramBaseTransform).state;
/*  819 */       this.type = ((AffineBase)paramBaseTransform).type;
/*      */     } else {
/*  821 */       updateState2D();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void preConcatenate(BaseTransform paramBaseTransform)
/*      */   {
/*  852 */     switch (1.$SwitchMap$com$sun$javafx$geom$transform$BaseTransform$Degree[paramBaseTransform.getDegree().ordinal()]) {
/*      */     case 1:
/*  854 */       return;
/*      */     case 2:
/*  856 */       translate(paramBaseTransform.getMxt(), paramBaseTransform.getMyt());
/*  857 */       return;
/*      */     case 3:
/*  859 */       break;
/*      */     default:
/*  861 */       degreeError(BaseTransform.Degree.AFFINE_2D);
/*      */     }
/*      */ 
/*  866 */     int i = this.state;
/*  867 */     Affine2D localAffine2D = (Affine2D)paramBaseTransform;
/*  868 */     int j = localAffine2D.state;
/*      */     double d1;
/*  869 */     switch (j << 4 | i)
/*      */     {
/*      */     case 0:
/*      */     case 1:
/*      */     case 2:
/*      */     case 3:
/*      */     case 4:
/*      */     case 5:
/*      */     case 6:
/*      */     case 7:
/*  879 */       return;
/*      */     case 16:
/*      */     case 18:
/*      */     case 20:
/*      */     case 22:
/*  886 */       this.mxt = localAffine2D.mxt;
/*  887 */       this.myt = localAffine2D.myt;
/*  888 */       this.state = (i | 0x1);
/*  889 */       this.type |= 1;
/*  890 */       return;
/*      */     case 17:
/*      */     case 19:
/*      */     case 21:
/*      */     case 23:
/*  897 */       this.mxt += localAffine2D.mxt;
/*  898 */       this.myt += localAffine2D.myt;
/*  899 */       return;
/*      */     case 32:
/*      */     case 33:
/*  904 */       this.state = (i | 0x2);
/*      */     case 34:
/*      */     case 35:
/*      */     case 36:
/*      */     case 37:
/*      */     case 38:
/*      */     case 39:
/*  913 */       d3 = localAffine2D.mxx;
/*  914 */       d6 = localAffine2D.myy;
/*  915 */       if ((i & 0x4) != 0) {
/*  916 */         this.mxy *= d3;
/*  917 */         this.myx *= d6;
/*  918 */         if ((i & 0x2) != 0) {
/*  919 */           this.mxx *= d3;
/*  920 */           this.myy *= d6;
/*      */         }
/*      */       } else {
/*  923 */         this.mxx *= d3;
/*  924 */         this.myy *= d6;
/*      */       }
/*  926 */       if ((i & 0x1) != 0) {
/*  927 */         this.mxt *= d3;
/*  928 */         this.myt *= d6;
/*      */       }
/*  930 */       this.type = -1;
/*  931 */       return;
/*      */     case 68:
/*      */     case 69:
/*  934 */       i |= 2;
/*      */     case 64:
/*      */     case 65:
/*      */     case 66:
/*      */     case 67:
/*  940 */       this.state = (i ^ 0x4);
/*      */     case 70:
/*      */     case 71:
/*  945 */       d4 = localAffine2D.mxy;
/*  946 */       d5 = localAffine2D.myx;
/*      */ 
/*  948 */       d1 = this.mxx;
/*  949 */       this.mxx = (this.myx * d4);
/*  950 */       this.myx = (d1 * d5);
/*      */ 
/*  952 */       d1 = this.mxy;
/*  953 */       this.mxy = (this.myy * d4);
/*  954 */       this.myy = (d1 * d5);
/*      */ 
/*  956 */       d1 = this.mxt;
/*  957 */       this.mxt = (this.myt * d4);
/*  958 */       this.myt = (d1 * d5);
/*  959 */       this.type = -1;
/*  960 */       return;
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
/*      */     case 48:
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
/*  964 */     case 63: } double d3 = localAffine2D.mxx; double d4 = localAffine2D.mxy; double d7 = localAffine2D.mxt;
/*  965 */     double d5 = localAffine2D.myx; double d6 = localAffine2D.myy; double d8 = localAffine2D.myt;
/*      */     double d2;
/*  966 */     switch (i) {
/*      */     default:
/*  968 */       stateError();
/*      */     case 7:
/*  971 */       d1 = this.mxt;
/*  972 */       d2 = this.myt;
/*  973 */       d7 += d1 * d3 + d2 * d4;
/*  974 */       d8 += d1 * d5 + d2 * d6;
/*      */     case 6:
/*  978 */       this.mxt = d7;
/*  979 */       this.myt = d8;
/*      */ 
/*  981 */       d1 = this.mxx;
/*  982 */       d2 = this.myx;
/*  983 */       this.mxx = (d1 * d3 + d2 * d4);
/*  984 */       this.myx = (d1 * d5 + d2 * d6);
/*      */ 
/*  986 */       d1 = this.mxy;
/*  987 */       d2 = this.myy;
/*  988 */       this.mxy = (d1 * d3 + d2 * d4);
/*  989 */       this.myy = (d1 * d5 + d2 * d6);
/*  990 */       break;
/*      */     case 5:
/*  993 */       d1 = this.mxt;
/*  994 */       d2 = this.myt;
/*  995 */       d7 += d1 * d3 + d2 * d4;
/*  996 */       d8 += d1 * d5 + d2 * d6;
/*      */     case 4:
/* 1000 */       this.mxt = d7;
/* 1001 */       this.myt = d8;
/*      */ 
/* 1003 */       d1 = this.myx;
/* 1004 */       this.mxx = (d1 * d4);
/* 1005 */       this.myx = (d1 * d6);
/*      */ 
/* 1007 */       d1 = this.mxy;
/* 1008 */       this.mxy = (d1 * d3);
/* 1009 */       this.myy = (d1 * d5);
/* 1010 */       break;
/*      */     case 3:
/* 1013 */       d1 = this.mxt;
/* 1014 */       d2 = this.myt;
/* 1015 */       d7 += d1 * d3 + d2 * d4;
/* 1016 */       d8 += d1 * d5 + d2 * d6;
/*      */     case 2:
/* 1020 */       this.mxt = d7;
/* 1021 */       this.myt = d8;
/*      */ 
/* 1023 */       d1 = this.mxx;
/* 1024 */       this.mxx = (d1 * d3);
/* 1025 */       this.myx = (d1 * d5);
/*      */ 
/* 1027 */       d1 = this.myy;
/* 1028 */       this.mxy = (d1 * d4);
/* 1029 */       this.myy = (d1 * d6);
/* 1030 */       break;
/*      */     case 1:
/* 1033 */       d1 = this.mxt;
/* 1034 */       d2 = this.myt;
/* 1035 */       d7 += d1 * d3 + d2 * d4;
/* 1036 */       d8 += d1 * d5 + d2 * d6;
/*      */     case 0:
/* 1040 */       this.mxt = d7;
/* 1041 */       this.myt = d8;
/*      */ 
/* 1043 */       this.mxx = d3;
/* 1044 */       this.myx = d5;
/*      */ 
/* 1046 */       this.mxy = d4;
/* 1047 */       this.myy = d6;
/*      */ 
/* 1049 */       this.state = (i | j);
/* 1050 */       this.type = -1;
/* 1051 */       return;
/*      */     }
/* 1053 */     updateState2D();
/*      */   }
/*      */ 
/*      */   public Affine2D createInverse()
/*      */     throws NoninvertibleTransformException
/*      */   {
/*      */     double d;
/* 1082 */     switch (this.state) {
/*      */     default:
/* 1084 */       stateError();
/*      */     case 7:
/* 1087 */       d = this.mxx * this.myy - this.mxy * this.myx;
/* 1088 */       if ((d == 0.0D) || (Math.abs(d) <= 4.9E-324D)) {
/* 1089 */         throw new NoninvertibleTransformException("Determinant is " + d);
/*      */       }
/*      */ 
/* 1092 */       return new Affine2D(this.myy / d, -this.myx / d, -this.mxy / d, this.mxx / d, (this.mxy * this.myt - this.myy * this.mxt) / d, (this.myx * this.mxt - this.mxx * this.myt) / d, 7);
/*      */     case 6:
/* 1100 */       d = this.mxx * this.myy - this.mxy * this.myx;
/* 1101 */       if ((d == 0.0D) || (Math.abs(d) <= 4.9E-324D)) {
/* 1102 */         throw new NoninvertibleTransformException("Determinant is " + d);
/*      */       }
/*      */ 
/* 1105 */       return new Affine2D(this.myy / d, -this.myx / d, -this.mxy / d, this.mxx / d, 0.0D, 0.0D, 6);
/*      */     case 5:
/* 1110 */       if ((this.mxy == 0.0D) || (this.myx == 0.0D)) {
/* 1111 */         throw new NoninvertibleTransformException("Determinant is 0");
/*      */       }
/* 1113 */       return new Affine2D(0.0D, 1.0D / this.mxy, 1.0D / this.myx, 0.0D, -this.myt / this.myx, -this.mxt / this.mxy, 5);
/*      */     case 4:
/* 1118 */       if ((this.mxy == 0.0D) || (this.myx == 0.0D)) {
/* 1119 */         throw new NoninvertibleTransformException("Determinant is 0");
/*      */       }
/* 1121 */       return new Affine2D(0.0D, 1.0D / this.mxy, 1.0D / this.myx, 0.0D, 0.0D, 0.0D, 4);
/*      */     case 3:
/* 1126 */       if ((this.mxx == 0.0D) || (this.myy == 0.0D)) {
/* 1127 */         throw new NoninvertibleTransformException("Determinant is 0");
/*      */       }
/* 1129 */       return new Affine2D(1.0D / this.mxx, 0.0D, 0.0D, 1.0D / this.myy, -this.mxt / this.mxx, -this.myt / this.myy, 3);
/*      */     case 2:
/* 1134 */       if ((this.mxx == 0.0D) || (this.myy == 0.0D)) {
/* 1135 */         throw new NoninvertibleTransformException("Determinant is 0");
/*      */       }
/* 1137 */       return new Affine2D(1.0D / this.mxx, 0.0D, 0.0D, 1.0D / this.myy, 0.0D, 0.0D, 2);
/*      */     case 1:
/* 1142 */       return new Affine2D(1.0D, 0.0D, 0.0D, 1.0D, -this.mxt, -this.myt, 1);
/*      */     case 0:
/*      */     }
/*      */ 
/* 1147 */     return new Affine2D();
/*      */   }
/*      */ 
/*      */   public void transform(Point2D[] paramArrayOfPoint2D1, int paramInt1, Point2D[] paramArrayOfPoint2D2, int paramInt2, int paramInt3)
/*      */   {
/* 1187 */     int i = this.state;
/*      */     while (true) { paramInt3--; if (paramInt3 < 0)
/*      */         break;
/* 1190 */       Point2D localPoint2D1 = paramArrayOfPoint2D1[(paramInt1++)];
/* 1191 */       double d1 = localPoint2D1.x;
/* 1192 */       double d2 = localPoint2D1.y;
/* 1193 */       Point2D localPoint2D2 = paramArrayOfPoint2D2[(paramInt2++)];
/* 1194 */       if (localPoint2D2 == null) {
/* 1195 */         localPoint2D2 = new Point2D();
/* 1196 */         paramArrayOfPoint2D2[(paramInt2 - 1)] = localPoint2D2;
/*      */       }
/* 1198 */       switch (i) {
/*      */       default:
/* 1200 */         stateError();
/*      */       case 7:
/* 1203 */         localPoint2D2.setLocation((float)(d1 * this.mxx + d2 * this.mxy + this.mxt), (float)(d1 * this.myx + d2 * this.myy + this.myt));
/*      */ 
/* 1205 */         break;
/*      */       case 6:
/* 1207 */         localPoint2D2.setLocation((float)(d1 * this.mxx + d2 * this.mxy), (float)(d1 * this.myx + d2 * this.myy));
/*      */ 
/* 1209 */         break;
/*      */       case 5:
/* 1211 */         localPoint2D2.setLocation((float)(d2 * this.mxy + this.mxt), (float)(d1 * this.myx + this.myt));
/*      */ 
/* 1213 */         break;
/*      */       case 4:
/* 1215 */         localPoint2D2.setLocation((float)(d2 * this.mxy), (float)(d1 * this.myx));
/* 1216 */         break;
/*      */       case 3:
/* 1218 */         localPoint2D2.setLocation((float)(d1 * this.mxx + this.mxt), (float)(d2 * this.myy + this.myt));
/* 1219 */         break;
/*      */       case 2:
/* 1221 */         localPoint2D2.setLocation((float)(d1 * this.mxx), (float)(d2 * this.myy));
/* 1222 */         break;
/*      */       case 1:
/* 1224 */         localPoint2D2.setLocation((float)(d1 + this.mxt), (float)(d2 + this.myt));
/* 1225 */         break;
/*      */       case 0:
/* 1227 */         localPoint2D2.setLocation((float)d1, (float)d2);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public Point2D deltaTransform(Point2D paramPoint2D1, Point2D paramPoint2D2)
/*      */   {
/* 1261 */     if (paramPoint2D2 == null) {
/* 1262 */       paramPoint2D2 = new Point2D();
/*      */     }
/*      */ 
/* 1265 */     double d1 = paramPoint2D1.x;
/* 1266 */     double d2 = paramPoint2D1.y;
/* 1267 */     switch (this.state) {
/*      */     default:
/* 1269 */       stateError();
/*      */     case 6:
/*      */     case 7:
/* 1273 */       paramPoint2D2.setLocation((float)(d1 * this.mxx + d2 * this.mxy), (float)(d1 * this.myx + d2 * this.myy));
/* 1274 */       return paramPoint2D2;
/*      */     case 4:
/*      */     case 5:
/* 1277 */       paramPoint2D2.setLocation((float)(d2 * this.mxy), (float)(d1 * this.myx));
/* 1278 */       return paramPoint2D2;
/*      */     case 2:
/*      */     case 3:
/* 1281 */       paramPoint2D2.setLocation((float)(d1 * this.mxx), (float)(d2 * this.myy));
/* 1282 */       return paramPoint2D2;
/*      */     case 0:
/*      */     case 1:
/* 1285 */     }paramPoint2D2.setLocation((float)d1, (float)d2);
/* 1286 */     return paramPoint2D2;
/*      */   }
/*      */ 
/*      */   private static double _matround(double paramDouble)
/*      */   {
/* 1295 */     return Math.rint(paramDouble * 1000000000000000.0D) / 1000000000000000.0D;
/*      */   }
/*      */ 
/*      */   public String toString()
/*      */   {
/* 1307 */     return "Affine2D[[" + _matround(this.mxx) + ", " + _matround(this.mxy) + ", " + _matround(this.mxt) + "], [" + _matround(this.myx) + ", " + _matround(this.myy) + ", " + _matround(this.myt) + "]]";
/*      */   }
/*      */ 
/*      */   public boolean is2D()
/*      */   {
/* 1318 */     return true;
/*      */   }
/*      */ 
/*      */   public void restoreTransform(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6)
/*      */   {
/* 1326 */     setTransform(paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6);
/*      */   }
/*      */ 
/*      */   public void restoreTransform(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, double paramDouble9, double paramDouble10, double paramDouble11, double paramDouble12)
/*      */   {
/* 1334 */     if ((paramDouble3 != 0.0D) || (paramDouble7 != 0.0D) || (paramDouble9 != 0.0D) || (paramDouble10 != 0.0D) || (paramDouble11 != 1.0D) || (paramDouble12 != 0.0D))
/*      */     {
/* 1338 */       degreeError(BaseTransform.Degree.AFFINE_2D);
/*      */     }
/* 1340 */     setTransform(paramDouble1, paramDouble5, paramDouble2, paramDouble6, paramDouble4, paramDouble8);
/*      */   }
/*      */ 
/*      */   public BaseTransform deriveWithTranslation(double paramDouble1, double paramDouble2)
/*      */   {
/* 1345 */     translate(paramDouble1, paramDouble2);
/* 1346 */     return this;
/*      */   }
/*      */ 
/*      */   public BaseTransform deriveWithPreTranslation(double paramDouble1, double paramDouble2)
/*      */   {
/* 1351 */     this.mxt += paramDouble1;
/* 1352 */     this.myt += paramDouble2;
/* 1353 */     if ((this.mxt != 0.0D) || (this.myt != 0.0D)) {
/* 1354 */       this.state |= 1;
/*      */ 
/* 1356 */       this.type |= 1;
/*      */     }
/*      */     else {
/* 1359 */       this.state &= -2;
/* 1360 */       if (this.type != -1) {
/* 1361 */         this.type &= -2;
/*      */       }
/*      */     }
/* 1364 */     return this;
/*      */   }
/*      */ 
/*      */   public BaseTransform deriveWithConcatenation(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6)
/*      */   {
/* 1373 */     BaseTransform localBaseTransform = getInstance(paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6);
/*      */ 
/* 1376 */     concatenate(localBaseTransform);
/* 1377 */     return this;
/*      */   }
/*      */ 
/*      */   public BaseTransform deriveWithConcatenation(BaseTransform paramBaseTransform)
/*      */   {
/* 1382 */     if (paramBaseTransform.is2D()) {
/* 1383 */       concatenate(paramBaseTransform);
/* 1384 */       return this;
/*      */     }
/* 1386 */     Affine3D localAffine3D = new Affine3D(this);
/* 1387 */     localAffine3D.concatenate(paramBaseTransform);
/* 1388 */     return localAffine3D;
/*      */   }
/*      */ 
/*      */   public BaseTransform deriveWithPreConcatenation(BaseTransform paramBaseTransform)
/*      */   {
/* 1393 */     if (paramBaseTransform.is2D()) {
/* 1394 */       preConcatenate(paramBaseTransform);
/* 1395 */       return this;
/*      */     }
/* 1397 */     Affine3D localAffine3D = new Affine3D(this);
/* 1398 */     localAffine3D.preConcatenate(paramBaseTransform);
/* 1399 */     return localAffine3D;
/*      */   }
/*      */ 
/*      */   public BaseTransform deriveWithNewTransform(BaseTransform paramBaseTransform)
/*      */   {
/* 1404 */     if (paramBaseTransform.is2D()) {
/* 1405 */       setTransform(paramBaseTransform);
/* 1406 */       return this;
/*      */     }
/* 1408 */     return getInstance(paramBaseTransform);
/*      */   }
/*      */ 
/*      */   public BaseTransform copy()
/*      */   {
/* 1413 */     return new Affine2D(this);
/*      */   }
/*      */ 
/*      */   public int hashCode()
/*      */   {
/* 1438 */     if (isIdentity()) return 0;
/* 1439 */     long l = BASE_HASH;
/* 1440 */     l = l * 31L + Double.doubleToLongBits(getMyy());
/* 1441 */     l = l * 31L + Double.doubleToLongBits(getMyx());
/* 1442 */     l = l * 31L + Double.doubleToLongBits(getMxy());
/* 1443 */     l = l * 31L + Double.doubleToLongBits(getMxx());
/* 1444 */     l = l * 31L + Double.doubleToLongBits(0.0D);
/* 1445 */     l = l * 31L + Double.doubleToLongBits(getMyt());
/* 1446 */     l = l * 31L + Double.doubleToLongBits(getMxt());
/* 1447 */     return (int)l ^ (int)(l >> 32);
/*      */   }
/*      */ 
/*      */   public boolean equals(Object paramObject)
/*      */   {
/* 1462 */     if ((paramObject instanceof BaseTransform)) {
/* 1463 */       BaseTransform localBaseTransform = (BaseTransform)paramObject;
/* 1464 */       return (localBaseTransform.getType() <= 127) && (localBaseTransform.getMxx() == this.mxx) && (localBaseTransform.getMxy() == this.mxy) && (localBaseTransform.getMxt() == this.mxt) && (localBaseTransform.getMyx() == this.myx) && (localBaseTransform.getMyy() == this.myy) && (localBaseTransform.getMyt() == this.myt);
/*      */     }
/*      */ 
/* 1472 */     return false;
/*      */   }
/*      */ 
/*      */   static
/*      */   {
/* 1418 */     long l = 0L;
/* 1419 */     l = l * 31L + Double.doubleToLongBits(IDENTITY_TRANSFORM.getMzz());
/* 1420 */     l = l * 31L + Double.doubleToLongBits(IDENTITY_TRANSFORM.getMzy());
/* 1421 */     l = l * 31L + Double.doubleToLongBits(IDENTITY_TRANSFORM.getMzx());
/* 1422 */     l = l * 31L + Double.doubleToLongBits(IDENTITY_TRANSFORM.getMyz());
/* 1423 */     l = l * 31L + Double.doubleToLongBits(IDENTITY_TRANSFORM.getMxz());
/*      */   }
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.geom.transform.Affine2D
 * JD-Core Version:    0.6.2
 */