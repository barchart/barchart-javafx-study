/*     */ package com.sun.javafx.geom;
/*     */ 
/*     */ public class Rectangle
/*     */ {
/*     */   public int x;
/*     */   public int y;
/*     */   public int width;
/*     */   public int height;
/*     */ 
/*     */   public Rectangle()
/*     */   {
/* 144 */     this(0, 0, 0, 0);
/*     */   }
/*     */ 
/*     */   public Rectangle(BaseBounds paramBaseBounds)
/*     */   {
/* 155 */     setBounds(paramBaseBounds);
/*     */   }
/*     */ 
/*     */   public Rectangle(Rectangle paramRectangle)
/*     */   {
/* 169 */     this(paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height);
/*     */   }
/*     */ 
/*     */   public Rectangle(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */   {
/* 184 */     this.x = paramInt1;
/* 185 */     this.y = paramInt2;
/* 186 */     this.width = paramInt3;
/* 187 */     this.height = paramInt4;
/*     */   }
/*     */ 
/*     */   public Rectangle(int paramInt1, int paramInt2)
/*     */   {
/* 198 */     this(0, 0, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   public void setBounds(Rectangle paramRectangle)
/*     */   {
/* 213 */     setBounds(paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height);
/*     */   }
/*     */ 
/*     */   public void setBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */   {
/* 235 */     reshape(paramInt1, paramInt2, paramInt3, paramInt4);
/*     */   }
/*     */ 
/*     */   public void setBounds(BaseBounds paramBaseBounds) {
/* 239 */     this.x = ((int)Math.floor(paramBaseBounds.getMinX()));
/* 240 */     this.y = ((int)Math.floor(paramBaseBounds.getMinY()));
/* 241 */     int i = (int)Math.ceil(paramBaseBounds.getMaxX());
/* 242 */     int j = (int)Math.ceil(paramBaseBounds.getMaxY());
/* 243 */     this.width = (i - this.x);
/* 244 */     this.height = (j - this.y);
/*     */   }
/*     */ 
/*     */   public boolean contains(int paramInt1, int paramInt2)
/*     */   {
/* 259 */     int i = this.width;
/* 260 */     int j = this.height;
/* 261 */     if ((i | j) < 0)
/*     */     {
/* 263 */       return false;
/*     */     }
/*     */ 
/* 266 */     int k = this.x;
/* 267 */     int m = this.y;
/* 268 */     if ((paramInt1 < k) || (paramInt2 < m)) {
/* 269 */       return false;
/*     */     }
/* 271 */     i += k;
/* 272 */     j += m;
/*     */ 
/* 274 */     return ((i < k) || (i > paramInt1)) && ((j < m) || (j > paramInt2));
/*     */   }
/*     */ 
/*     */   public boolean contains(Rectangle paramRectangle)
/*     */   {
/* 288 */     return contains(paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height);
/*     */   }
/*     */ 
/*     */   public boolean contains(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */   {
/* 306 */     int i = this.width;
/* 307 */     int j = this.height;
/* 308 */     if ((i | j | paramInt3 | paramInt4) < 0)
/*     */     {
/* 310 */       return false;
/*     */     }
/*     */ 
/* 313 */     int k = this.x;
/* 314 */     int m = this.y;
/* 315 */     if ((paramInt1 < k) || (paramInt2 < m)) {
/* 316 */       return false;
/*     */     }
/* 318 */     i += k;
/* 319 */     paramInt3 += paramInt1;
/* 320 */     if (paramInt3 <= paramInt1)
/*     */     {
/* 325 */       if ((i >= k) || (paramInt3 > i)) return false;
/*     */ 
/*     */     }
/* 330 */     else if ((i >= k) && (paramInt3 > i)) return false;
/*     */ 
/* 332 */     j += m;
/* 333 */     paramInt4 += paramInt2;
/* 334 */     if (paramInt4 <= paramInt2) {
/* 335 */       if ((j >= m) || (paramInt4 > j)) return false;
/*     */     }
/* 337 */     else if ((j >= m) && (paramInt4 > j)) return false;
/*     */ 
/* 339 */     return true;
/*     */   }
/*     */ 
/*     */   public Rectangle intersection(Rectangle paramRectangle) {
/* 343 */     Rectangle localRectangle = new Rectangle(this);
/* 344 */     localRectangle.intersectWith(paramRectangle);
/* 345 */     return localRectangle;
/*     */   }
/*     */ 
/*     */   public void intersectWith(Rectangle paramRectangle) {
/* 349 */     if (paramRectangle == null) {
/* 350 */       return;
/*     */     }
/* 352 */     int i = this.x;
/* 353 */     int j = this.y;
/* 354 */     int k = paramRectangle.x;
/* 355 */     int m = paramRectangle.y;
/* 356 */     long l1 = i; l1 += this.width;
/* 357 */     long l2 = j; l2 += this.height;
/* 358 */     long l3 = k; l3 += paramRectangle.width;
/* 359 */     long l4 = m; l4 += paramRectangle.height;
/* 360 */     if (i < k) i = k;
/* 361 */     if (j < m) j = m;
/* 362 */     if (l1 > l3) l1 = l3;
/* 363 */     if (l2 > l4) l2 = l4;
/* 364 */     l1 -= i;
/* 365 */     l2 -= j;
/*     */ 
/* 369 */     if (l1 < -2147483648L) l1 = -2147483648L;
/* 370 */     if (l2 < -2147483648L) l2 = -2147483648L;
/* 371 */     setBounds(i, j, (int)l1, (int)l2);
/*     */   }
/*     */ 
/*     */   public void translate(int paramInt1, int paramInt2)
/*     */   {
/* 386 */     int i = this.x;
/* 387 */     int j = i + paramInt1;
/* 388 */     if (paramInt1 < 0)
/*     */     {
/* 390 */       if (j > i)
/*     */       {
/* 393 */         if (this.width >= 0)
/*     */         {
/* 401 */           this.width += j - -2147483648;
/*     */         }
/*     */ 
/* 407 */         j = -2147483648;
/*     */       }
/*     */ 
/*     */     }
/* 411 */     else if (j < i)
/*     */     {
/* 413 */       if (this.width >= 0)
/*     */       {
/* 416 */         this.width += j - 2147483647;
/*     */ 
/* 419 */         if (this.width < 0) this.width = 2147483647;
/*     */       }
/* 421 */       j = 2147483647;
/*     */     }
/*     */ 
/* 424 */     this.x = j;
/*     */ 
/* 426 */     i = this.y;
/* 427 */     j = i + paramInt2;
/* 428 */     if (paramInt2 < 0)
/*     */     {
/* 430 */       if (j > i)
/*     */       {
/* 432 */         if (this.height >= 0) {
/* 433 */           this.height += j - -2147483648;
/*     */         }
/*     */ 
/* 436 */         j = -2147483648;
/*     */       }
/*     */ 
/*     */     }
/* 440 */     else if (j < i)
/*     */     {
/* 442 */       if (this.height >= 0) {
/* 443 */         this.height += j - 2147483647;
/* 444 */         if (this.height < 0) this.height = 2147483647;
/*     */       }
/* 446 */       j = 2147483647;
/*     */     }
/*     */ 
/* 449 */     this.y = j;
/*     */   }
/*     */ 
/*     */   public RectBounds toRectBounds() {
/* 453 */     return new RectBounds(this.x, this.y, this.x + this.width, this.y + this.height);
/*     */   }
/*     */ 
/*     */   public void add(int paramInt1, int paramInt2)
/*     */   {
/* 484 */     if ((this.width | this.height) < 0) {
/* 485 */       this.x = paramInt1;
/* 486 */       this.y = paramInt2;
/* 487 */       this.width = (this.height = 0);
/* 488 */       return;
/*     */     }
/* 490 */     int i = this.x;
/* 491 */     int j = this.y;
/* 492 */     long l1 = this.width;
/* 493 */     long l2 = this.height;
/* 494 */     l1 += i;
/* 495 */     l2 += j;
/* 496 */     if (i > paramInt1) i = paramInt1;
/* 497 */     if (j > paramInt2) j = paramInt2;
/* 498 */     if (l1 < paramInt1) l1 = paramInt1;
/* 499 */     if (l2 < paramInt2) l2 = paramInt2;
/* 500 */     l1 -= i;
/* 501 */     l2 -= j;
/* 502 */     if (l1 > 2147483647L) l1 = 2147483647L;
/* 503 */     if (l2 > 2147483647L) l2 = 2147483647L;
/* 504 */     reshape(i, j, (int)l1, (int)l2);
/*     */   }
/*     */ 
/*     */   public void add(Rectangle paramRectangle)
/*     */   {
/* 531 */     long l1 = this.width;
/* 532 */     long l2 = this.height;
/* 533 */     if ((l1 | l2) < 0L) {
/* 534 */       reshape(paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height);
/*     */     }
/* 536 */     long l3 = paramRectangle.width;
/* 537 */     long l4 = paramRectangle.height;
/* 538 */     if ((l3 | l4) < 0L) {
/* 539 */       return;
/*     */     }
/* 541 */     int i = this.x;
/* 542 */     int j = this.y;
/* 543 */     l1 += i;
/* 544 */     l2 += j;
/* 545 */     int k = paramRectangle.x;
/* 546 */     int m = paramRectangle.y;
/* 547 */     l3 += k;
/* 548 */     l4 += m;
/* 549 */     if (i > k) i = k;
/* 550 */     if (j > m) j = m;
/* 551 */     if (l1 < l3) l1 = l3;
/* 552 */     if (l2 < l4) l2 = l4;
/* 553 */     l1 -= i;
/* 554 */     l2 -= j;
/*     */ 
/* 558 */     if (l1 > 2147483647L) l1 = 2147483647L;
/* 559 */     if (l2 > 2147483647L) l2 = 2147483647L;
/* 560 */     reshape(i, j, (int)l1, (int)l2);
/*     */   }
/*     */ 
/*     */   public void grow(int paramInt1, int paramInt2)
/*     */   {
/* 587 */     long l1 = this.x;
/* 588 */     long l2 = this.y;
/* 589 */     long l3 = this.width;
/* 590 */     long l4 = this.height;
/* 591 */     l3 += l1;
/* 592 */     l4 += l2;
/*     */ 
/* 594 */     l1 -= paramInt1;
/* 595 */     l2 -= paramInt2;
/* 596 */     l3 += paramInt1;
/* 597 */     l4 += paramInt2;
/*     */ 
/* 599 */     if (l3 < l1)
/*     */     {
/* 604 */       l3 -= l1;
/* 605 */       if (l3 < -2147483648L) l3 = -2147483648L;
/* 606 */       if (l1 < -2147483648L) l1 = -2147483648L;
/* 607 */       else if (l1 > 2147483647L) l1 = 2147483647L;
/*     */     }
/*     */     else
/*     */     {
/* 611 */       if (l1 < -2147483648L) l1 = -2147483648L;
/* 612 */       else if (l1 > 2147483647L) l1 = 2147483647L;
/* 613 */       l3 -= l1;
/*     */ 
/* 618 */       if (l3 < -2147483648L) l3 = -2147483648L;
/* 619 */       else if (l3 > 2147483647L) l3 = 2147483647L;
/*     */     }
/*     */ 
/* 622 */     if (l4 < l2)
/*     */     {
/* 624 */       l4 -= l2;
/* 625 */       if (l4 < -2147483648L) l4 = -2147483648L;
/* 626 */       if (l2 < -2147483648L) l2 = -2147483648L;
/* 627 */       else if (l2 > 2147483647L) l2 = 2147483647L; 
/*     */     }
/* 629 */     else { if (l2 < -2147483648L) l2 = -2147483648L;
/* 630 */       else if (l2 > 2147483647L) l2 = 2147483647L;
/* 631 */       l4 -= l2;
/* 632 */       if (l4 < -2147483648L) l4 = -2147483648L;
/* 633 */       else if (l4 > 2147483647L) l4 = 2147483647L;
/*     */     }
/*     */ 
/* 636 */     reshape((int)l1, (int)l2, (int)l3, (int)l4);
/*     */   }
/*     */ 
/*     */   private void reshape(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 640 */     this.x = paramInt1;
/* 641 */     this.y = paramInt2;
/* 642 */     this.width = paramInt3;
/* 643 */     this.height = paramInt4;
/*     */   }
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/* 651 */     return (this.width <= 0) || (this.height <= 0);
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 668 */     if ((paramObject instanceof Rectangle)) {
/* 669 */       Rectangle localRectangle = (Rectangle)paramObject;
/* 670 */       return (this.x == localRectangle.x) && (this.y == localRectangle.y) && (this.width == localRectangle.width) && (this.height == localRectangle.height);
/*     */     }
/*     */ 
/* 675 */     return super.equals(paramObject);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 686 */     return getClass().getName() + "[x=" + this.x + ",y=" + this.y + ",width=" + this.width + ",height=" + this.height + "]";
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.geom.Rectangle
 * JD-Core Version:    0.6.2
 */