/*     */ package com.sun.prism.util.tess.impl.tess;
/*     */ 
/*     */ class Mesh
/*     */ {
/*     */   static GLUhalfEdge MakeEdge(GLUhalfEdge paramGLUhalfEdge)
/*     */   {
/*  74 */     GLUhalfEdge localGLUhalfEdge1 = new GLUhalfEdge(true);
/*     */ 
/*  76 */     GLUhalfEdge localGLUhalfEdge2 = new GLUhalfEdge(false);
/*     */ 
/*  80 */     if (!paramGLUhalfEdge.first) {
/*  81 */       paramGLUhalfEdge = paramGLUhalfEdge.Sym;
/*     */     }
/*     */ 
/*  87 */     GLUhalfEdge localGLUhalfEdge3 = paramGLUhalfEdge.Sym.next;
/*  88 */     localGLUhalfEdge2.next = localGLUhalfEdge3;
/*  89 */     localGLUhalfEdge3.Sym.next = localGLUhalfEdge1;
/*  90 */     localGLUhalfEdge1.next = paramGLUhalfEdge;
/*  91 */     paramGLUhalfEdge.Sym.next = localGLUhalfEdge2;
/*     */ 
/*  93 */     localGLUhalfEdge1.Sym = localGLUhalfEdge2;
/*  94 */     localGLUhalfEdge1.Onext = localGLUhalfEdge1;
/*  95 */     localGLUhalfEdge1.Lnext = localGLUhalfEdge2;
/*  96 */     localGLUhalfEdge1.Org = null;
/*  97 */     localGLUhalfEdge1.Lface = null;
/*  98 */     localGLUhalfEdge1.winding = 0;
/*  99 */     localGLUhalfEdge1.activeRegion = null;
/*     */ 
/* 101 */     localGLUhalfEdge2.Sym = localGLUhalfEdge1;
/* 102 */     localGLUhalfEdge2.Onext = localGLUhalfEdge2;
/* 103 */     localGLUhalfEdge2.Lnext = localGLUhalfEdge1;
/* 104 */     localGLUhalfEdge2.Org = null;
/* 105 */     localGLUhalfEdge2.Lface = null;
/* 106 */     localGLUhalfEdge2.winding = 0;
/* 107 */     localGLUhalfEdge2.activeRegion = null;
/*     */ 
/* 109 */     return localGLUhalfEdge1;
/*     */   }
/*     */ 
/*     */   static void Splice(GLUhalfEdge paramGLUhalfEdge1, GLUhalfEdge paramGLUhalfEdge2)
/*     */   {
/* 119 */     GLUhalfEdge localGLUhalfEdge1 = paramGLUhalfEdge1.Onext;
/* 120 */     GLUhalfEdge localGLUhalfEdge2 = paramGLUhalfEdge2.Onext;
/*     */ 
/* 122 */     localGLUhalfEdge1.Sym.Lnext = paramGLUhalfEdge2;
/* 123 */     localGLUhalfEdge2.Sym.Lnext = paramGLUhalfEdge1;
/* 124 */     paramGLUhalfEdge1.Onext = localGLUhalfEdge2;
/* 125 */     paramGLUhalfEdge2.Onext = localGLUhalfEdge1;
/*     */   }
/*     */ 
/*     */   static void MakeVertex(GLUvertex paramGLUvertex1, GLUhalfEdge paramGLUhalfEdge, GLUvertex paramGLUvertex2)
/*     */   {
/* 138 */     GLUvertex localGLUvertex2 = paramGLUvertex1;
/*     */ 
/* 140 */     assert (localGLUvertex2 != null);
/*     */ 
/* 143 */     GLUvertex localGLUvertex1 = paramGLUvertex2.prev;
/* 144 */     localGLUvertex2.prev = localGLUvertex1;
/* 145 */     localGLUvertex1.next = localGLUvertex2;
/* 146 */     localGLUvertex2.next = paramGLUvertex2;
/* 147 */     paramGLUvertex2.prev = localGLUvertex2;
/*     */ 
/* 149 */     localGLUvertex2.anEdge = paramGLUhalfEdge;
/* 150 */     localGLUvertex2.data = null;
/*     */ 
/* 154 */     GLUhalfEdge localGLUhalfEdge = paramGLUhalfEdge;
/*     */     do {
/* 156 */       localGLUhalfEdge.Org = localGLUvertex2;
/* 157 */       localGLUhalfEdge = localGLUhalfEdge.Onext;
/* 158 */     }while (localGLUhalfEdge != paramGLUhalfEdge);
/*     */   }
/*     */ 
/*     */   static void MakeFace(GLUface paramGLUface1, GLUhalfEdge paramGLUhalfEdge, GLUface paramGLUface2)
/*     */   {
/* 170 */     GLUface localGLUface2 = paramGLUface1;
/*     */ 
/* 172 */     assert (localGLUface2 != null);
/*     */ 
/* 175 */     GLUface localGLUface1 = paramGLUface2.prev;
/* 176 */     localGLUface2.prev = localGLUface1;
/* 177 */     localGLUface1.next = localGLUface2;
/* 178 */     localGLUface2.next = paramGLUface2;
/* 179 */     paramGLUface2.prev = localGLUface2;
/*     */ 
/* 181 */     localGLUface2.anEdge = paramGLUhalfEdge;
/* 182 */     localGLUface2.data = null;
/* 183 */     localGLUface2.trail = null;
/* 184 */     localGLUface2.marked = false;
/*     */ 
/* 189 */     localGLUface2.inside = paramGLUface2.inside;
/*     */ 
/* 192 */     GLUhalfEdge localGLUhalfEdge = paramGLUhalfEdge;
/*     */     do {
/* 194 */       localGLUhalfEdge.Lface = localGLUface2;
/* 195 */       localGLUhalfEdge = localGLUhalfEdge.Lnext;
/* 196 */     }while (localGLUhalfEdge != paramGLUhalfEdge);
/*     */   }
/*     */ 
/*     */   static void KillEdge(GLUhalfEdge paramGLUhalfEdge)
/*     */   {
/* 206 */     if (!paramGLUhalfEdge.first) {
/* 207 */       paramGLUhalfEdge = paramGLUhalfEdge.Sym;
/*     */     }
/*     */ 
/* 211 */     GLUhalfEdge localGLUhalfEdge2 = paramGLUhalfEdge.next;
/* 212 */     GLUhalfEdge localGLUhalfEdge1 = paramGLUhalfEdge.Sym.next;
/* 213 */     localGLUhalfEdge2.Sym.next = localGLUhalfEdge1;
/* 214 */     localGLUhalfEdge1.Sym.next = localGLUhalfEdge2;
/*     */   }
/*     */ 
/*     */   static void KillVertex(GLUvertex paramGLUvertex1, GLUvertex paramGLUvertex2)
/*     */   {
/* 222 */     GLUhalfEdge localGLUhalfEdge2 = paramGLUvertex1.anEdge;
/*     */ 
/* 226 */     GLUhalfEdge localGLUhalfEdge1 = localGLUhalfEdge2;
/*     */     do {
/* 228 */       localGLUhalfEdge1.Org = paramGLUvertex2;
/* 229 */       localGLUhalfEdge1 = localGLUhalfEdge1.Onext;
/* 230 */     }while (localGLUhalfEdge1 != localGLUhalfEdge2);
/*     */ 
/* 233 */     GLUvertex localGLUvertex1 = paramGLUvertex1.prev;
/* 234 */     GLUvertex localGLUvertex2 = paramGLUvertex1.next;
/* 235 */     localGLUvertex2.prev = localGLUvertex1;
/* 236 */     localGLUvertex1.next = localGLUvertex2;
/*     */   }
/*     */ 
/*     */   static void KillFace(GLUface paramGLUface1, GLUface paramGLUface2)
/*     */   {
/* 243 */     GLUhalfEdge localGLUhalfEdge2 = paramGLUface1.anEdge;
/*     */ 
/* 247 */     GLUhalfEdge localGLUhalfEdge1 = localGLUhalfEdge2;
/*     */     do {
/* 249 */       localGLUhalfEdge1.Lface = paramGLUface2;
/* 250 */       localGLUhalfEdge1 = localGLUhalfEdge1.Lnext;
/* 251 */     }while (localGLUhalfEdge1 != localGLUhalfEdge2);
/*     */ 
/* 254 */     GLUface localGLUface1 = paramGLUface1.prev;
/* 255 */     GLUface localGLUface2 = paramGLUface1.next;
/* 256 */     localGLUface2.prev = localGLUface1;
/* 257 */     localGLUface1.next = localGLUface2;
/*     */   }
/*     */ 
/*     */   public static GLUhalfEdge __gl_meshMakeEdge(GLUmesh paramGLUmesh)
/*     */   {
/* 267 */     GLUvertex localGLUvertex1 = new GLUvertex();
/* 268 */     GLUvertex localGLUvertex2 = new GLUvertex();
/* 269 */     GLUface localGLUface = new GLUface();
/*     */ 
/* 272 */     GLUhalfEdge localGLUhalfEdge = MakeEdge(paramGLUmesh.eHead);
/* 273 */     if (localGLUhalfEdge == null) return null;
/*     */ 
/* 275 */     MakeVertex(localGLUvertex1, localGLUhalfEdge, paramGLUmesh.vHead);
/* 276 */     MakeVertex(localGLUvertex2, localGLUhalfEdge.Sym, paramGLUmesh.vHead);
/* 277 */     MakeFace(localGLUface, localGLUhalfEdge, paramGLUmesh.fHead);
/* 278 */     return localGLUhalfEdge;
/*     */   }
/*     */ 
/*     */   public static boolean __gl_meshSplice(GLUhalfEdge paramGLUhalfEdge1, GLUhalfEdge paramGLUhalfEdge2)
/*     */   {
/* 306 */     int i = 0;
/* 307 */     int j = 0;
/*     */ 
/* 309 */     if (paramGLUhalfEdge1 == paramGLUhalfEdge2) return true;
/*     */ 
/* 311 */     if (paramGLUhalfEdge2.Org != paramGLUhalfEdge1.Org)
/*     */     {
/* 313 */       j = 1;
/* 314 */       KillVertex(paramGLUhalfEdge2.Org, paramGLUhalfEdge1.Org);
/*     */     }
/* 316 */     if (paramGLUhalfEdge2.Lface != paramGLUhalfEdge1.Lface)
/*     */     {
/* 318 */       i = 1;
/* 319 */       KillFace(paramGLUhalfEdge2.Lface, paramGLUhalfEdge1.Lface);
/*     */     }
/*     */ 
/* 323 */     Splice(paramGLUhalfEdge2, paramGLUhalfEdge1);
/*     */     Object localObject;
/* 325 */     if (j == 0) {
/* 326 */       localObject = new GLUvertex();
/*     */ 
/* 331 */       MakeVertex((GLUvertex)localObject, paramGLUhalfEdge2, paramGLUhalfEdge1.Org);
/* 332 */       paramGLUhalfEdge1.Org.anEdge = paramGLUhalfEdge1;
/*     */     }
/* 334 */     if (i == 0) {
/* 335 */       localObject = new GLUface();
/*     */ 
/* 340 */       MakeFace((GLUface)localObject, paramGLUhalfEdge2, paramGLUhalfEdge1.Lface);
/* 341 */       paramGLUhalfEdge1.Lface.anEdge = paramGLUhalfEdge1;
/*     */     }
/*     */ 
/* 344 */     return true;
/*     */   }
/*     */ 
/*     */   static boolean __gl_meshDelete(GLUhalfEdge paramGLUhalfEdge)
/*     */   {
/* 359 */     GLUhalfEdge localGLUhalfEdge = paramGLUhalfEdge.Sym;
/* 360 */     int i = 0;
/*     */ 
/* 365 */     if (paramGLUhalfEdge.Lface != paramGLUhalfEdge.Sym.Lface)
/*     */     {
/* 367 */       i = 1;
/* 368 */       KillFace(paramGLUhalfEdge.Lface, paramGLUhalfEdge.Sym.Lface);
/*     */     }
/*     */ 
/* 371 */     if (paramGLUhalfEdge.Onext == paramGLUhalfEdge) {
/* 372 */       KillVertex(paramGLUhalfEdge.Org, null);
/*     */     }
/*     */     else {
/* 375 */       paramGLUhalfEdge.Sym.Lface.anEdge = paramGLUhalfEdge.Sym.Lnext;
/* 376 */       paramGLUhalfEdge.Org.anEdge = paramGLUhalfEdge.Onext;
/*     */ 
/* 378 */       Splice(paramGLUhalfEdge, paramGLUhalfEdge.Sym.Lnext);
/* 379 */       if (i == 0) {
/* 380 */         GLUface localGLUface = new GLUface();
/*     */ 
/* 383 */         MakeFace(localGLUface, paramGLUhalfEdge, paramGLUhalfEdge.Lface);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 390 */     if (localGLUhalfEdge.Onext == localGLUhalfEdge) {
/* 391 */       KillVertex(localGLUhalfEdge.Org, null);
/* 392 */       KillFace(localGLUhalfEdge.Lface, null);
/*     */     }
/*     */     else {
/* 395 */       paramGLUhalfEdge.Lface.anEdge = localGLUhalfEdge.Sym.Lnext;
/* 396 */       localGLUhalfEdge.Org.anEdge = localGLUhalfEdge.Onext;
/* 397 */       Splice(localGLUhalfEdge, localGLUhalfEdge.Sym.Lnext);
/*     */     }
/*     */ 
/* 401 */     KillEdge(paramGLUhalfEdge);
/*     */ 
/* 403 */     return true;
/*     */   }
/*     */ 
/*     */   static GLUhalfEdge __gl_meshAddEdgeVertex(GLUhalfEdge paramGLUhalfEdge)
/*     */   {
/* 420 */     GLUhalfEdge localGLUhalfEdge2 = MakeEdge(paramGLUhalfEdge);
/*     */ 
/* 422 */     GLUhalfEdge localGLUhalfEdge1 = localGLUhalfEdge2.Sym;
/*     */ 
/* 425 */     Splice(localGLUhalfEdge2, paramGLUhalfEdge.Lnext);
/*     */ 
/* 428 */     localGLUhalfEdge2.Org = paramGLUhalfEdge.Sym.Org;
/*     */ 
/* 430 */     GLUvertex localGLUvertex = new GLUvertex();
/*     */ 
/* 432 */     MakeVertex(localGLUvertex, localGLUhalfEdge1, localGLUhalfEdge2.Org);
/*     */ 
/* 434 */     localGLUhalfEdge2.Lface = (localGLUhalfEdge1.Lface = paramGLUhalfEdge.Lface);
/*     */ 
/* 436 */     return localGLUhalfEdge2;
/*     */   }
/*     */ 
/*     */   public static GLUhalfEdge __gl_meshSplitEdge(GLUhalfEdge paramGLUhalfEdge)
/*     */   {
/* 446 */     GLUhalfEdge localGLUhalfEdge2 = __gl_meshAddEdgeVertex(paramGLUhalfEdge);
/*     */ 
/* 448 */     GLUhalfEdge localGLUhalfEdge1 = localGLUhalfEdge2.Sym;
/*     */ 
/* 451 */     Splice(paramGLUhalfEdge.Sym, paramGLUhalfEdge.Sym.Sym.Lnext);
/* 452 */     Splice(paramGLUhalfEdge.Sym, localGLUhalfEdge1);
/*     */ 
/* 455 */     paramGLUhalfEdge.Sym.Org = localGLUhalfEdge1.Org;
/* 456 */     localGLUhalfEdge1.Sym.Org.anEdge = localGLUhalfEdge1.Sym;
/* 457 */     localGLUhalfEdge1.Sym.Lface = paramGLUhalfEdge.Sym.Lface;
/* 458 */     localGLUhalfEdge1.winding = paramGLUhalfEdge.winding;
/* 459 */     localGLUhalfEdge1.Sym.winding = paramGLUhalfEdge.Sym.winding;
/*     */ 
/* 461 */     return localGLUhalfEdge1;
/*     */   }
/*     */ 
/*     */   static GLUhalfEdge __gl_meshConnect(GLUhalfEdge paramGLUhalfEdge1, GLUhalfEdge paramGLUhalfEdge2)
/*     */   {
/* 477 */     int i = 0;
/* 478 */     GLUhalfEdge localGLUhalfEdge2 = MakeEdge(paramGLUhalfEdge1);
/*     */ 
/* 480 */     GLUhalfEdge localGLUhalfEdge1 = localGLUhalfEdge2.Sym;
/*     */ 
/* 482 */     if (paramGLUhalfEdge2.Lface != paramGLUhalfEdge1.Lface)
/*     */     {
/* 484 */       i = 1;
/* 485 */       KillFace(paramGLUhalfEdge2.Lface, paramGLUhalfEdge1.Lface);
/*     */     }
/*     */ 
/* 489 */     Splice(localGLUhalfEdge2, paramGLUhalfEdge1.Lnext);
/* 490 */     Splice(localGLUhalfEdge1, paramGLUhalfEdge2);
/*     */ 
/* 493 */     localGLUhalfEdge2.Org = paramGLUhalfEdge1.Sym.Org;
/* 494 */     localGLUhalfEdge1.Org = paramGLUhalfEdge2.Org;
/* 495 */     localGLUhalfEdge2.Lface = (localGLUhalfEdge1.Lface = paramGLUhalfEdge1.Lface);
/*     */ 
/* 498 */     paramGLUhalfEdge1.Lface.anEdge = localGLUhalfEdge1;
/*     */ 
/* 500 */     if (i == 0) {
/* 501 */       GLUface localGLUface = new GLUface();
/*     */ 
/* 504 */       MakeFace(localGLUface, localGLUhalfEdge2, paramGLUhalfEdge1.Lface);
/*     */     }
/* 506 */     return localGLUhalfEdge2;
/*     */   }
/*     */ 
/*     */   static void __gl_meshZapFace(GLUface paramGLUface)
/*     */   {
/* 520 */     GLUhalfEdge localGLUhalfEdge1 = paramGLUface.anEdge;
/*     */ 
/* 525 */     GLUhalfEdge localGLUhalfEdge3 = localGLUhalfEdge1.Lnext;
/*     */     GLUhalfEdge localGLUhalfEdge2;
/*     */     do
/*     */     {
/* 527 */       localGLUhalfEdge2 = localGLUhalfEdge3;
/* 528 */       localGLUhalfEdge3 = localGLUhalfEdge2.Lnext;
/*     */ 
/* 530 */       localGLUhalfEdge2.Lface = null;
/* 531 */       if (localGLUhalfEdge2.Sym.Lface == null)
/*     */       {
/* 534 */         if (localGLUhalfEdge2.Onext == localGLUhalfEdge2) {
/* 535 */           KillVertex(localGLUhalfEdge2.Org, null);
/*     */         }
/*     */         else {
/* 538 */           localGLUhalfEdge2.Org.anEdge = localGLUhalfEdge2.Onext;
/* 539 */           Splice(localGLUhalfEdge2, localGLUhalfEdge2.Sym.Lnext);
/*     */         }
/* 541 */         GLUhalfEdge localGLUhalfEdge4 = localGLUhalfEdge2.Sym;
/* 542 */         if (localGLUhalfEdge4.Onext == localGLUhalfEdge4) {
/* 543 */           KillVertex(localGLUhalfEdge4.Org, null);
/*     */         }
/*     */         else {
/* 546 */           localGLUhalfEdge4.Org.anEdge = localGLUhalfEdge4.Onext;
/* 547 */           Splice(localGLUhalfEdge4, localGLUhalfEdge4.Sym.Lnext);
/*     */         }
/* 549 */         KillEdge(localGLUhalfEdge2);
/*     */       }
/*     */     }
/* 551 */     while (localGLUhalfEdge2 != localGLUhalfEdge1);
/*     */ 
/* 554 */     GLUface localGLUface1 = paramGLUface.prev;
/* 555 */     GLUface localGLUface2 = paramGLUface.next;
/* 556 */     localGLUface2.prev = localGLUface1;
/* 557 */     localGLUface1.next = localGLUface2;
/*     */   }
/*     */ 
/*     */   public static GLUmesh __gl_meshNewMesh()
/*     */   {
/* 569 */     GLUmesh localGLUmesh = new GLUmesh();
/*     */ 
/* 571 */     GLUvertex localGLUvertex = localGLUmesh.vHead;
/* 572 */     GLUface localGLUface = localGLUmesh.fHead;
/* 573 */     GLUhalfEdge localGLUhalfEdge1 = localGLUmesh.eHead;
/* 574 */     GLUhalfEdge localGLUhalfEdge2 = localGLUmesh.eHeadSym;
/*     */ 
/* 576 */     localGLUvertex.prev = localGLUvertex; localGLUvertex.next = localGLUvertex;
/* 577 */     localGLUvertex.anEdge = null;
/* 578 */     localGLUvertex.data = null;
/*     */ 
/* 580 */     localGLUface.next = (localGLUface.prev = localGLUface);
/* 581 */     localGLUface.anEdge = null;
/* 582 */     localGLUface.data = null;
/* 583 */     localGLUface.trail = null;
/* 584 */     localGLUface.marked = false;
/* 585 */     localGLUface.inside = false;
/*     */ 
/* 587 */     localGLUhalfEdge1.next = localGLUhalfEdge1;
/* 588 */     localGLUhalfEdge1.Sym = localGLUhalfEdge2;
/* 589 */     localGLUhalfEdge1.Onext = null;
/* 590 */     localGLUhalfEdge1.Lnext = null;
/* 591 */     localGLUhalfEdge1.Org = null;
/* 592 */     localGLUhalfEdge1.Lface = null;
/* 593 */     localGLUhalfEdge1.winding = 0;
/* 594 */     localGLUhalfEdge1.activeRegion = null;
/*     */ 
/* 596 */     localGLUhalfEdge2.next = localGLUhalfEdge2;
/* 597 */     localGLUhalfEdge2.Sym = localGLUhalfEdge1;
/* 598 */     localGLUhalfEdge2.Onext = null;
/* 599 */     localGLUhalfEdge2.Lnext = null;
/* 600 */     localGLUhalfEdge2.Org = null;
/* 601 */     localGLUhalfEdge2.Lface = null;
/* 602 */     localGLUhalfEdge2.winding = 0;
/* 603 */     localGLUhalfEdge2.activeRegion = null;
/*     */ 
/* 605 */     return localGLUmesh;
/*     */   }
/*     */ 
/*     */   static GLUmesh __gl_meshUnion(GLUmesh paramGLUmesh1, GLUmesh paramGLUmesh2)
/*     */   {
/* 613 */     GLUface localGLUface1 = paramGLUmesh1.fHead;
/* 614 */     GLUvertex localGLUvertex1 = paramGLUmesh1.vHead;
/* 615 */     GLUhalfEdge localGLUhalfEdge1 = paramGLUmesh1.eHead;
/* 616 */     GLUface localGLUface2 = paramGLUmesh2.fHead;
/* 617 */     GLUvertex localGLUvertex2 = paramGLUmesh2.vHead;
/* 618 */     GLUhalfEdge localGLUhalfEdge2 = paramGLUmesh2.eHead;
/*     */ 
/* 621 */     if (localGLUface2.next != localGLUface2) {
/* 622 */       localGLUface1.prev.next = localGLUface2.next;
/* 623 */       localGLUface2.next.prev = localGLUface1.prev;
/* 624 */       localGLUface2.prev.next = localGLUface1;
/* 625 */       localGLUface1.prev = localGLUface2.prev;
/*     */     }
/*     */ 
/* 628 */     if (localGLUvertex2.next != localGLUvertex2) {
/* 629 */       localGLUvertex1.prev.next = localGLUvertex2.next;
/* 630 */       localGLUvertex2.next.prev = localGLUvertex1.prev;
/* 631 */       localGLUvertex2.prev.next = localGLUvertex1;
/* 632 */       localGLUvertex1.prev = localGLUvertex2.prev;
/*     */     }
/*     */ 
/* 635 */     if (localGLUhalfEdge2.next != localGLUhalfEdge2) {
/* 636 */       localGLUhalfEdge1.Sym.next.Sym.next = localGLUhalfEdge2.next;
/* 637 */       localGLUhalfEdge2.next.Sym.next = localGLUhalfEdge1.Sym.next;
/* 638 */       localGLUhalfEdge2.Sym.next.Sym.next = localGLUhalfEdge1;
/* 639 */       localGLUhalfEdge1.Sym.next = localGLUhalfEdge2.Sym.next;
/*     */     }
/*     */ 
/* 642 */     return paramGLUmesh1;
/*     */   }
/*     */ 
/*     */   static void __gl_meshDeleteMeshZap(GLUmesh paramGLUmesh)
/*     */   {
/* 649 */     GLUface localGLUface = paramGLUmesh.fHead;
/*     */ 
/* 651 */     while (localGLUface.next != localGLUface) {
/* 652 */       __gl_meshZapFace(localGLUface.next);
/*     */     }
/* 654 */     assert (paramGLUmesh.vHead.next == paramGLUmesh.vHead);
/*     */   }
/*     */ 
/*     */   public static void __gl_meshDeleteMesh(GLUmesh paramGLUmesh)
/*     */   {
/*     */     GLUface localGLUface;
/* 664 */     for (Object localObject1 = paramGLUmesh.fHead.next; localObject1 != paramGLUmesh.fHead; localObject1 = localGLUface)
/* 665 */       localGLUface = ((GLUface)localObject1).next;
/*     */     GLUvertex localGLUvertex;
/* 668 */     for (Object localObject2 = paramGLUmesh.vHead.next; localObject2 != paramGLUmesh.vHead; localObject2 = localGLUvertex)
/* 669 */       localGLUvertex = ((GLUvertex)localObject2).next;
/*     */     GLUhalfEdge localGLUhalfEdge;
/* 672 */     for (Object localObject3 = paramGLUmesh.eHead.next; localObject3 != paramGLUmesh.eHead; localObject3 = localGLUhalfEdge)
/*     */     {
/* 674 */       localGLUhalfEdge = ((GLUhalfEdge)localObject3).next;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void __gl_meshCheckMesh(GLUmesh paramGLUmesh)
/*     */   {
/* 681 */     GLUface localGLUface1 = paramGLUmesh.fHead;
/* 682 */     GLUvertex localGLUvertex1 = paramGLUmesh.vHead;
/* 683 */     GLUhalfEdge localGLUhalfEdge1 = paramGLUmesh.eHead;
/*     */ 
/* 688 */     Object localObject1 = localGLUface1;
/*     */     GLUface localGLUface2;
/*     */     GLUhalfEdge localGLUhalfEdge2;
/* 689 */     for (localObject1 = localGLUface1; (localGLUface2 = ((GLUface)localObject1).next) != localGLUface1; localObject1 = localGLUface2) {
/* 690 */       assert (localGLUface2.prev == localObject1);
/* 691 */       localGLUhalfEdge2 = localGLUface2.anEdge;
/*     */       do {
/* 693 */         assert (localGLUhalfEdge2.Sym != localGLUhalfEdge2);
/* 694 */         assert (localGLUhalfEdge2.Sym.Sym == localGLUhalfEdge2);
/* 695 */         assert (localGLUhalfEdge2.Lnext.Onext.Sym == localGLUhalfEdge2);
/* 696 */         assert (localGLUhalfEdge2.Onext.Sym.Lnext == localGLUhalfEdge2);
/* 697 */         assert (localGLUhalfEdge2.Lface == localGLUface2);
/* 698 */         localGLUhalfEdge2 = localGLUhalfEdge2.Lnext;
/* 699 */       }while (localGLUhalfEdge2 != localGLUface2.anEdge);
/*     */     }
/* 701 */     assert ((localGLUface2.prev == localObject1) && (localGLUface2.anEdge == null) && (localGLUface2.data == null));
/*     */ 
/* 703 */     Object localObject2 = localGLUvertex1;
/*     */     GLUvertex localGLUvertex2;
/* 704 */     for (localObject2 = localGLUvertex1; (localGLUvertex2 = ((GLUvertex)localObject2).next) != localGLUvertex1; localObject2 = localGLUvertex2) {
/* 705 */       assert (localGLUvertex2.prev == localObject2);
/* 706 */       localGLUhalfEdge2 = localGLUvertex2.anEdge;
/*     */       do {
/* 708 */         assert (localGLUhalfEdge2.Sym != localGLUhalfEdge2);
/* 709 */         assert (localGLUhalfEdge2.Sym.Sym == localGLUhalfEdge2);
/* 710 */         assert (localGLUhalfEdge2.Lnext.Onext.Sym == localGLUhalfEdge2);
/* 711 */         assert (localGLUhalfEdge2.Onext.Sym.Lnext == localGLUhalfEdge2);
/* 712 */         assert (localGLUhalfEdge2.Org == localGLUvertex2);
/* 713 */         localGLUhalfEdge2 = localGLUhalfEdge2.Onext;
/* 714 */       }while (localGLUhalfEdge2 != localGLUvertex2.anEdge);
/*     */     }
/* 716 */     assert ((localGLUvertex2.prev == localObject2) && (localGLUvertex2.anEdge == null) && (localGLUvertex2.data == null));
/*     */ 
/* 718 */     GLUhalfEdge localGLUhalfEdge3 = localGLUhalfEdge1;
/* 719 */     for (localGLUhalfEdge3 = localGLUhalfEdge1; (localGLUhalfEdge2 = localGLUhalfEdge3.next) != localGLUhalfEdge1; localGLUhalfEdge3 = localGLUhalfEdge2) {
/* 720 */       assert (localGLUhalfEdge2.Sym.next == localGLUhalfEdge3.Sym);
/* 721 */       assert (localGLUhalfEdge2.Sym != localGLUhalfEdge2);
/* 722 */       assert (localGLUhalfEdge2.Sym.Sym == localGLUhalfEdge2);
/* 723 */       assert (localGLUhalfEdge2.Org != null);
/* 724 */       assert (localGLUhalfEdge2.Sym.Org != null);
/* 725 */       assert (localGLUhalfEdge2.Lnext.Onext.Sym == localGLUhalfEdge2);
/* 726 */       assert (localGLUhalfEdge2.Onext.Sym.Lnext == localGLUhalfEdge2);
/*     */     }
/* 728 */     assert ((localGLUhalfEdge2.Sym.next == localGLUhalfEdge3.Sym) && (localGLUhalfEdge2.Sym == paramGLUmesh.eHeadSym) && (localGLUhalfEdge2.Sym.Sym == localGLUhalfEdge2) && (localGLUhalfEdge2.Org == null) && (localGLUhalfEdge2.Sym.Org == null) && (localGLUhalfEdge2.Lface == null) && (localGLUhalfEdge2.Sym.Lface == null));
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.util.tess.impl.tess.Mesh
 * JD-Core Version:    0.6.2
 */