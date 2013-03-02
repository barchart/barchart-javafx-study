/*      */ package com.sun.prism.util.tess.impl.tess;
/*      */ 
/*      */ class Sweep
/*      */ {
/*      */   private static final boolean TOLERANCE_NONZERO = false;
/*      */   private static final double SENTINEL_COORD = 4.0E+150D;
/*      */ 
/*      */   private static void DebugEvent(TessellatorImpl paramTessellatorImpl)
/*      */   {
/*      */   }
/*      */ 
/*      */   private static void AddWinding(GLUhalfEdge paramGLUhalfEdge1, GLUhalfEdge paramGLUhalfEdge2)
/*      */   {
/*  103 */     paramGLUhalfEdge1.winding += paramGLUhalfEdge2.winding;
/*  104 */     paramGLUhalfEdge1.Sym.winding += paramGLUhalfEdge2.Sym.winding;
/*      */   }
/*      */ 
/*      */   private static ActiveRegion RegionBelow(ActiveRegion paramActiveRegion)
/*      */   {
/*  109 */     return (ActiveRegion)Dict.dictKey(Dict.dictPred(paramActiveRegion.nodeUp));
/*      */   }
/*      */ 
/*      */   private static ActiveRegion RegionAbove(ActiveRegion paramActiveRegion) {
/*  113 */     return (ActiveRegion)Dict.dictKey(Dict.dictSucc(paramActiveRegion.nodeUp));
/*      */   }
/*      */ 
/*      */   static boolean EdgeLeq(TessellatorImpl paramTessellatorImpl, ActiveRegion paramActiveRegion1, ActiveRegion paramActiveRegion2)
/*      */   {
/*  128 */     GLUvertex localGLUvertex = paramTessellatorImpl.event;
/*      */ 
/*  132 */     GLUhalfEdge localGLUhalfEdge1 = paramActiveRegion1.eUp;
/*  133 */     GLUhalfEdge localGLUhalfEdge2 = paramActiveRegion2.eUp;
/*      */ 
/*  135 */     if (localGLUhalfEdge1.Sym.Org == localGLUvertex) {
/*  136 */       if (localGLUhalfEdge2.Sym.Org == localGLUvertex)
/*      */       {
/*  140 */         if (Geom.VertLeq(localGLUhalfEdge1.Org, localGLUhalfEdge2.Org)) {
/*  141 */           return Geom.EdgeSign(localGLUhalfEdge2.Sym.Org, localGLUhalfEdge1.Org, localGLUhalfEdge2.Org) <= 0.0D;
/*      */         }
/*  143 */         return Geom.EdgeSign(localGLUhalfEdge1.Sym.Org, localGLUhalfEdge2.Org, localGLUhalfEdge1.Org) >= 0.0D;
/*      */       }
/*  145 */       return Geom.EdgeSign(localGLUhalfEdge2.Sym.Org, localGLUvertex, localGLUhalfEdge2.Org) <= 0.0D;
/*      */     }
/*  147 */     if (localGLUhalfEdge2.Sym.Org == localGLUvertex) {
/*  148 */       return Geom.EdgeSign(localGLUhalfEdge1.Sym.Org, localGLUvertex, localGLUhalfEdge1.Org) >= 0.0D;
/*      */     }
/*      */ 
/*  152 */     double d1 = Geom.EdgeEval(localGLUhalfEdge1.Sym.Org, localGLUvertex, localGLUhalfEdge1.Org);
/*  153 */     double d2 = Geom.EdgeEval(localGLUhalfEdge2.Sym.Org, localGLUvertex, localGLUhalfEdge2.Org);
/*  154 */     return d1 >= d2;
/*      */   }
/*      */ 
/*      */   static void DeleteRegion(TessellatorImpl paramTessellatorImpl, ActiveRegion paramActiveRegion)
/*      */   {
/*  159 */     if (paramActiveRegion.fixUpperEdge)
/*      */     {
/*  164 */       assert (paramActiveRegion.eUp.winding == 0);
/*      */     }
/*  166 */     paramActiveRegion.eUp.activeRegion = null;
/*  167 */     Dict.dictDelete(paramTessellatorImpl.dict, paramActiveRegion.nodeUp);
/*      */   }
/*      */ 
/*      */   static boolean FixUpperEdge(ActiveRegion paramActiveRegion, GLUhalfEdge paramGLUhalfEdge)
/*      */   {
/*  175 */     assert (paramActiveRegion.fixUpperEdge);
/*  176 */     if (!Mesh.__gl_meshDelete(paramActiveRegion.eUp)) return false;
/*  177 */     paramActiveRegion.fixUpperEdge = false;
/*  178 */     paramActiveRegion.eUp = paramGLUhalfEdge;
/*  179 */     paramGLUhalfEdge.activeRegion = paramActiveRegion;
/*      */ 
/*  181 */     return true;
/*      */   }
/*      */ 
/*      */   static ActiveRegion TopLeftRegion(ActiveRegion paramActiveRegion) {
/*  185 */     GLUvertex localGLUvertex = paramActiveRegion.eUp.Org;
/*      */     do
/*      */     {
/*  190 */       paramActiveRegion = RegionAbove(paramActiveRegion);
/*  191 */     }while (paramActiveRegion.eUp.Org == localGLUvertex);
/*      */ 
/*  196 */     if (paramActiveRegion.fixUpperEdge) {
/*  197 */       GLUhalfEdge localGLUhalfEdge = Mesh.__gl_meshConnect(RegionBelow(paramActiveRegion).eUp.Sym, paramActiveRegion.eUp.Lnext);
/*  198 */       if (localGLUhalfEdge == null) return null;
/*  199 */       if (!FixUpperEdge(paramActiveRegion, localGLUhalfEdge)) return null;
/*  200 */       paramActiveRegion = RegionAbove(paramActiveRegion);
/*      */     }
/*  202 */     return paramActiveRegion;
/*      */   }
/*      */ 
/*      */   static ActiveRegion TopRightRegion(ActiveRegion paramActiveRegion) {
/*  206 */     GLUvertex localGLUvertex = paramActiveRegion.eUp.Sym.Org;
/*      */     do
/*      */     {
/*  210 */       paramActiveRegion = RegionAbove(paramActiveRegion);
/*  211 */     }while (paramActiveRegion.eUp.Sym.Org == localGLUvertex);
/*  212 */     return paramActiveRegion;
/*      */   }
/*      */ 
/*      */   static ActiveRegion AddRegionBelow(TessellatorImpl paramTessellatorImpl, ActiveRegion paramActiveRegion, GLUhalfEdge paramGLUhalfEdge)
/*      */   {
/*  224 */     ActiveRegion localActiveRegion = new ActiveRegion();
/*  225 */     if (localActiveRegion == null) throw new RuntimeException();
/*      */ 
/*  227 */     localActiveRegion.eUp = paramGLUhalfEdge;
/*      */ 
/*  229 */     localActiveRegion.nodeUp = Dict.dictInsertBefore(paramTessellatorImpl.dict, paramActiveRegion.nodeUp, localActiveRegion);
/*  230 */     if (localActiveRegion.nodeUp == null) throw new RuntimeException();
/*  231 */     localActiveRegion.fixUpperEdge = false;
/*  232 */     localActiveRegion.sentinel = false;
/*  233 */     localActiveRegion.dirty = false;
/*      */ 
/*  235 */     paramGLUhalfEdge.activeRegion = localActiveRegion;
/*  236 */     return localActiveRegion;
/*      */   }
/*      */ 
/*      */   static boolean IsWindingInside(TessellatorImpl paramTessellatorImpl, int paramInt) {
/*  240 */     switch (paramTessellatorImpl.windingRule) {
/*      */     case 100130:
/*  242 */       return (paramInt & 0x1) != 0;
/*      */     case 100131:
/*  244 */       return paramInt != 0;
/*      */     case 100132:
/*  246 */       return paramInt > 0;
/*      */     case 100133:
/*  248 */       return paramInt < 0;
/*      */     case 100134:
/*  250 */       return (paramInt >= 2) || (paramInt <= -2);
/*      */     }
/*      */ 
/*  254 */     throw new InternalError();
/*      */   }
/*      */ 
/*      */   static void ComputeWinding(TessellatorImpl paramTessellatorImpl, ActiveRegion paramActiveRegion)
/*      */   {
/*  260 */     paramActiveRegion.windingNumber = (RegionAbove(paramActiveRegion).windingNumber + paramActiveRegion.eUp.winding);
/*  261 */     paramActiveRegion.inside = IsWindingInside(paramTessellatorImpl, paramActiveRegion.windingNumber);
/*      */   }
/*      */ 
/*      */   static void FinishRegion(TessellatorImpl paramTessellatorImpl, ActiveRegion paramActiveRegion)
/*      */   {
/*  273 */     GLUhalfEdge localGLUhalfEdge = paramActiveRegion.eUp;
/*  274 */     GLUface localGLUface = localGLUhalfEdge.Lface;
/*      */ 
/*  276 */     localGLUface.inside = paramActiveRegion.inside;
/*  277 */     localGLUface.anEdge = localGLUhalfEdge;
/*  278 */     DeleteRegion(paramTessellatorImpl, paramActiveRegion);
/*      */   }
/*      */ 
/*      */   static GLUhalfEdge FinishLeftRegions(TessellatorImpl paramTessellatorImpl, ActiveRegion paramActiveRegion1, ActiveRegion paramActiveRegion2)
/*      */   {
/*  299 */     Object localObject = paramActiveRegion1;
/*  300 */     GLUhalfEdge localGLUhalfEdge2 = paramActiveRegion1.eUp;
/*  301 */     while (localObject != paramActiveRegion2) {
/*  302 */       ((ActiveRegion)localObject).fixUpperEdge = false;
/*  303 */       ActiveRegion localActiveRegion = RegionBelow((ActiveRegion)localObject);
/*  304 */       GLUhalfEdge localGLUhalfEdge1 = localActiveRegion.eUp;
/*  305 */       if (localGLUhalfEdge1.Org != localGLUhalfEdge2.Org) {
/*  306 */         if (!localActiveRegion.fixUpperEdge)
/*      */         {
/*  313 */           FinishRegion(paramTessellatorImpl, (ActiveRegion)localObject);
/*  314 */           break;
/*      */         }
/*      */ 
/*  319 */         localGLUhalfEdge1 = Mesh.__gl_meshConnect(localGLUhalfEdge2.Onext.Sym, localGLUhalfEdge1.Sym);
/*  320 */         if (localGLUhalfEdge1 == null) throw new RuntimeException();
/*  321 */         if (!FixUpperEdge(localActiveRegion, localGLUhalfEdge1)) throw new RuntimeException();
/*      */ 
/*      */       }
/*      */ 
/*  325 */       if (localGLUhalfEdge2.Onext != localGLUhalfEdge1) {
/*  326 */         if (!Mesh.__gl_meshSplice(localGLUhalfEdge1.Sym.Lnext, localGLUhalfEdge1)) throw new RuntimeException();
/*  327 */         if (!Mesh.__gl_meshSplice(localGLUhalfEdge2, localGLUhalfEdge1)) throw new RuntimeException();
/*      */       }
/*  329 */       FinishRegion(paramTessellatorImpl, (ActiveRegion)localObject);
/*  330 */       localGLUhalfEdge2 = localActiveRegion.eUp;
/*  331 */       localObject = localActiveRegion;
/*      */     }
/*  333 */     return localGLUhalfEdge2;
/*      */   }
/*      */ 
/*      */   static void AddRightEdges(TessellatorImpl paramTessellatorImpl, ActiveRegion paramActiveRegion, GLUhalfEdge paramGLUhalfEdge1, GLUhalfEdge paramGLUhalfEdge2, GLUhalfEdge paramGLUhalfEdge3, boolean paramBoolean)
/*      */   {
/*  352 */     int i = 1;
/*      */ 
/*  355 */     GLUhalfEdge localGLUhalfEdge1 = paramGLUhalfEdge1;
/*      */     do {
/*  357 */       assert (Geom.VertLeq(localGLUhalfEdge1.Org, localGLUhalfEdge1.Sym.Org));
/*  358 */       AddRegionBelow(paramTessellatorImpl, paramActiveRegion, localGLUhalfEdge1.Sym);
/*  359 */       localGLUhalfEdge1 = localGLUhalfEdge1.Onext;
/*  360 */     }while (localGLUhalfEdge1 != paramGLUhalfEdge2);
/*      */ 
/*  366 */     if (paramGLUhalfEdge3 == null) {
/*  367 */       paramGLUhalfEdge3 = RegionBelow(paramActiveRegion).eUp.Sym.Onext; } Object localObject = paramActiveRegion;
/*  370 */     GLUhalfEdge localGLUhalfEdge2 = paramGLUhalfEdge3;
/*      */     ActiveRegion localActiveRegion;
/*      */     while (true) { localActiveRegion = RegionBelow((ActiveRegion)localObject);
/*  373 */       localGLUhalfEdge1 = localActiveRegion.eUp.Sym;
/*  374 */       if (localGLUhalfEdge1.Org != localGLUhalfEdge2.Org)
/*      */         break;
/*  376 */       if (localGLUhalfEdge1.Onext != localGLUhalfEdge2)
/*      */       {
/*  378 */         if (!Mesh.__gl_meshSplice(localGLUhalfEdge1.Sym.Lnext, localGLUhalfEdge1)) throw new RuntimeException();
/*  379 */         if (!Mesh.__gl_meshSplice(localGLUhalfEdge2.Sym.Lnext, localGLUhalfEdge1)) throw new RuntimeException();
/*      */       }
/*      */ 
/*  382 */       localActiveRegion.windingNumber = (((ActiveRegion)localObject).windingNumber - localGLUhalfEdge1.winding);
/*  383 */       localActiveRegion.inside = IsWindingInside(paramTessellatorImpl, localActiveRegion.windingNumber);
/*      */ 
/*  388 */       ((ActiveRegion)localObject).dirty = true;
/*  389 */       if ((i == 0) && (CheckForRightSplice(paramTessellatorImpl, (ActiveRegion)localObject))) {
/*  390 */         AddWinding(localGLUhalfEdge1, localGLUhalfEdge2);
/*  391 */         DeleteRegion(paramTessellatorImpl, (ActiveRegion)localObject);
/*  392 */         if (!Mesh.__gl_meshDelete(localGLUhalfEdge2)) throw new RuntimeException();
/*      */       }
/*  394 */       i = 0;
/*  395 */       localObject = localActiveRegion;
/*  396 */       localGLUhalfEdge2 = localGLUhalfEdge1;
/*      */     }
/*  398 */     ((ActiveRegion)localObject).dirty = true;
/*  399 */     assert (((ActiveRegion)localObject).windingNumber - localGLUhalfEdge1.winding == localActiveRegion.windingNumber);
/*      */ 
/*  401 */     if (paramBoolean)
/*      */     {
/*  403 */       WalkDirtyRegions(paramTessellatorImpl, (ActiveRegion)localObject);
/*      */     }
/*      */   }
/*      */ 
/*      */   static void CallCombine(TessellatorImpl paramTessellatorImpl, GLUvertex paramGLUvertex, Object[] paramArrayOfObject, float[] paramArrayOfFloat, boolean paramBoolean)
/*      */   {
/*  410 */     double[] arrayOfDouble = new double[3];
/*      */ 
/*  413 */     arrayOfDouble[0] = paramGLUvertex.coords[0];
/*  414 */     arrayOfDouble[1] = paramGLUvertex.coords[1];
/*  415 */     arrayOfDouble[2] = paramGLUvertex.coords[2];
/*      */ 
/*  417 */     Object[] arrayOfObject = new Object[1];
/*  418 */     paramTessellatorImpl.callCombineOrCombineData(arrayOfDouble, paramArrayOfObject, paramArrayOfFloat, arrayOfObject);
/*  419 */     paramGLUvertex.data = arrayOfObject[0];
/*  420 */     if (paramGLUvertex.data == null)
/*  421 */       if (!paramBoolean) {
/*  422 */         paramGLUvertex.data = paramArrayOfObject[0];
/*  423 */       } else if (!paramTessellatorImpl.fatalError)
/*      */       {
/*  428 */         paramTessellatorImpl.callErrorOrErrorData(100156);
/*  429 */         paramTessellatorImpl.fatalError = true;
/*      */       }
/*      */   }
/*      */ 
/*      */   static void SpliceMergeVertices(TessellatorImpl paramTessellatorImpl, GLUhalfEdge paramGLUhalfEdge1, GLUhalfEdge paramGLUhalfEdge2)
/*      */   {
/*  440 */     Object[] arrayOfObject = new Object[4];
/*  441 */     float[] arrayOfFloat = { 0.5F, 0.5F, 0.0F, 0.0F };
/*      */ 
/*  443 */     arrayOfObject[0] = paramGLUhalfEdge1.Org.data;
/*  444 */     arrayOfObject[1] = paramGLUhalfEdge2.Org.data;
/*  445 */     CallCombine(paramTessellatorImpl, paramGLUhalfEdge1.Org, arrayOfObject, arrayOfFloat, false);
/*  446 */     if (!Mesh.__gl_meshSplice(paramGLUhalfEdge1, paramGLUhalfEdge2)) throw new RuntimeException();
/*      */   }
/*      */ 
/*      */   static void VertexWeights(GLUvertex paramGLUvertex1, GLUvertex paramGLUvertex2, GLUvertex paramGLUvertex3, float[] paramArrayOfFloat)
/*      */   {
/*  458 */     double d1 = Geom.VertL1dist(paramGLUvertex2, paramGLUvertex1);
/*  459 */     double d2 = Geom.VertL1dist(paramGLUvertex3, paramGLUvertex1);
/*      */ 
/*  461 */     paramArrayOfFloat[0] = ((float)(0.5D * d2 / (d1 + d2)));
/*  462 */     paramArrayOfFloat[1] = ((float)(0.5D * d1 / (d1 + d2)));
/*  463 */     paramGLUvertex1.coords[0] += paramArrayOfFloat[0] * paramGLUvertex2.coords[0] + paramArrayOfFloat[1] * paramGLUvertex3.coords[0];
/*  464 */     paramGLUvertex1.coords[1] += paramArrayOfFloat[0] * paramGLUvertex2.coords[1] + paramArrayOfFloat[1] * paramGLUvertex3.coords[1];
/*  465 */     paramGLUvertex1.coords[2] += paramArrayOfFloat[0] * paramGLUvertex2.coords[2] + paramArrayOfFloat[1] * paramGLUvertex3.coords[2];
/*      */   }
/*      */ 
/*      */   static void GetIntersectData(TessellatorImpl paramTessellatorImpl, GLUvertex paramGLUvertex1, GLUvertex paramGLUvertex2, GLUvertex paramGLUvertex3, GLUvertex paramGLUvertex4, GLUvertex paramGLUvertex5)
/*      */   {
/*  477 */     Object[] arrayOfObject = new Object[4];
/*  478 */     float[] arrayOfFloat1 = new float[4];
/*  479 */     float[] arrayOfFloat2 = new float[2];
/*  480 */     float[] arrayOfFloat3 = new float[2];
/*      */ 
/*  482 */     arrayOfObject[0] = paramGLUvertex2.data;
/*  483 */     arrayOfObject[1] = paramGLUvertex3.data;
/*  484 */     arrayOfObject[2] = paramGLUvertex4.data;
/*  485 */     arrayOfObject[3] = paramGLUvertex5.data;
/*      */     double tmp73_72 = (paramGLUvertex1.coords[2] = 0.0D); paramGLUvertex1.coords[1] = tmp73_72; paramGLUvertex1.coords[0] = tmp73_72;
/*  488 */     VertexWeights(paramGLUvertex1, paramGLUvertex2, paramGLUvertex3, arrayOfFloat2);
/*  489 */     VertexWeights(paramGLUvertex1, paramGLUvertex4, paramGLUvertex5, arrayOfFloat3);
/*  490 */     System.arraycopy(arrayOfFloat2, 0, arrayOfFloat1, 0, 2);
/*  491 */     System.arraycopy(arrayOfFloat3, 0, arrayOfFloat1, 2, 2);
/*      */ 
/*  493 */     CallCombine(paramTessellatorImpl, paramGLUvertex1, arrayOfObject, arrayOfFloat1, true);
/*      */   }
/*      */ 
/*      */   static boolean CheckForRightSplice(TessellatorImpl paramTessellatorImpl, ActiveRegion paramActiveRegion)
/*      */   {
/*  522 */     ActiveRegion localActiveRegion = RegionBelow(paramActiveRegion);
/*  523 */     GLUhalfEdge localGLUhalfEdge1 = paramActiveRegion.eUp;
/*  524 */     GLUhalfEdge localGLUhalfEdge2 = localActiveRegion.eUp;
/*      */ 
/*  526 */     if (Geom.VertLeq(localGLUhalfEdge1.Org, localGLUhalfEdge2.Org)) {
/*  527 */       if (Geom.EdgeSign(localGLUhalfEdge2.Sym.Org, localGLUhalfEdge1.Org, localGLUhalfEdge2.Org) > 0.0D) return false;
/*      */ 
/*  530 */       if (!Geom.VertEq(localGLUhalfEdge1.Org, localGLUhalfEdge2.Org))
/*      */       {
/*  532 */         if (Mesh.__gl_meshSplitEdge(localGLUhalfEdge2.Sym) == null) throw new RuntimeException();
/*  533 */         if (!Mesh.__gl_meshSplice(localGLUhalfEdge1, localGLUhalfEdge2.Sym.Lnext)) throw new RuntimeException();
/*  534 */         paramActiveRegion.dirty = (localActiveRegion.dirty = 1);
/*      */       }
/*  536 */       else if (localGLUhalfEdge1.Org != localGLUhalfEdge2.Org)
/*      */       {
/*  538 */         paramTessellatorImpl.pq.pqDelete(localGLUhalfEdge1.Org.pqHandle);
/*  539 */         SpliceMergeVertices(paramTessellatorImpl, localGLUhalfEdge2.Sym.Lnext, localGLUhalfEdge1);
/*      */       }
/*      */     } else {
/*  542 */       if (Geom.EdgeSign(localGLUhalfEdge1.Sym.Org, localGLUhalfEdge2.Org, localGLUhalfEdge1.Org) < 0.0D) return false;
/*      */ 
/*  545 */       RegionAbove(paramActiveRegion).dirty = (paramActiveRegion.dirty = 1);
/*  546 */       if (Mesh.__gl_meshSplitEdge(localGLUhalfEdge1.Sym) == null) throw new RuntimeException();
/*  547 */       if (!Mesh.__gl_meshSplice(localGLUhalfEdge2.Sym.Lnext, localGLUhalfEdge1)) throw new RuntimeException();
/*      */     }
/*  549 */     return true;
/*      */   }
/*      */ 
/*      */   static boolean CheckForLeftSplice(TessellatorImpl paramTessellatorImpl, ActiveRegion paramActiveRegion)
/*      */   {
/*  571 */     ActiveRegion localActiveRegion = RegionBelow(paramActiveRegion);
/*  572 */     GLUhalfEdge localGLUhalfEdge1 = paramActiveRegion.eUp;
/*  573 */     GLUhalfEdge localGLUhalfEdge2 = localActiveRegion.eUp;
/*      */ 
/*  576 */     assert (!Geom.VertEq(localGLUhalfEdge1.Sym.Org, localGLUhalfEdge2.Sym.Org));
/*      */     GLUhalfEdge localGLUhalfEdge3;
/*  578 */     if (Geom.VertLeq(localGLUhalfEdge1.Sym.Org, localGLUhalfEdge2.Sym.Org)) {
/*  579 */       if (Geom.EdgeSign(localGLUhalfEdge1.Sym.Org, localGLUhalfEdge2.Sym.Org, localGLUhalfEdge1.Org) < 0.0D) return false;
/*      */ 
/*  582 */       RegionAbove(paramActiveRegion).dirty = (paramActiveRegion.dirty = 1);
/*  583 */       localGLUhalfEdge3 = Mesh.__gl_meshSplitEdge(localGLUhalfEdge1);
/*  584 */       if (localGLUhalfEdge3 == null) throw new RuntimeException();
/*  585 */       if (!Mesh.__gl_meshSplice(localGLUhalfEdge2.Sym, localGLUhalfEdge3)) throw new RuntimeException();
/*  586 */       localGLUhalfEdge3.Lface.inside = paramActiveRegion.inside;
/*      */     } else {
/*  588 */       if (Geom.EdgeSign(localGLUhalfEdge2.Sym.Org, localGLUhalfEdge1.Sym.Org, localGLUhalfEdge2.Org) > 0.0D) return false;
/*      */ 
/*  591 */       paramActiveRegion.dirty = (localActiveRegion.dirty = 1);
/*  592 */       localGLUhalfEdge3 = Mesh.__gl_meshSplitEdge(localGLUhalfEdge2);
/*  593 */       if (localGLUhalfEdge3 == null) throw new RuntimeException();
/*  594 */       if (!Mesh.__gl_meshSplice(localGLUhalfEdge1.Lnext, localGLUhalfEdge2.Sym)) throw new RuntimeException();
/*  595 */       localGLUhalfEdge3.Sym.Lface.inside = paramActiveRegion.inside;
/*      */     }
/*  597 */     return true;
/*      */   }
/*      */ 
/*      */   static boolean CheckForIntersect(TessellatorImpl paramTessellatorImpl, ActiveRegion paramActiveRegion)
/*      */   {
/*  611 */     ActiveRegion localActiveRegion = RegionBelow(paramActiveRegion);
/*  612 */     GLUhalfEdge localGLUhalfEdge1 = paramActiveRegion.eUp;
/*  613 */     GLUhalfEdge localGLUhalfEdge2 = localActiveRegion.eUp;
/*  614 */     GLUvertex localGLUvertex1 = localGLUhalfEdge1.Org;
/*  615 */     GLUvertex localGLUvertex2 = localGLUhalfEdge2.Org;
/*  616 */     GLUvertex localGLUvertex3 = localGLUhalfEdge1.Sym.Org;
/*  617 */     GLUvertex localGLUvertex4 = localGLUhalfEdge2.Sym.Org;
/*      */ 
/*  619 */     GLUvertex localGLUvertex5 = new GLUvertex();
/*      */ 
/*  623 */     assert (!Geom.VertEq(localGLUvertex4, localGLUvertex3));
/*  624 */     assert (Geom.EdgeSign(localGLUvertex3, paramTessellatorImpl.event, localGLUvertex1) <= 0.0D);
/*  625 */     assert (Geom.EdgeSign(localGLUvertex4, paramTessellatorImpl.event, localGLUvertex2) >= 0.0D);
/*  626 */     assert ((localGLUvertex1 != paramTessellatorImpl.event) && (localGLUvertex2 != paramTessellatorImpl.event));
/*  627 */     assert ((!paramActiveRegion.fixUpperEdge) && (!localActiveRegion.fixUpperEdge));
/*      */ 
/*  629 */     if (localGLUvertex1 == localGLUvertex2) return false;
/*      */ 
/*  631 */     double d1 = Math.min(localGLUvertex1.t, localGLUvertex3.t);
/*  632 */     double d2 = Math.max(localGLUvertex2.t, localGLUvertex4.t);
/*  633 */     if (d1 > d2) return false;
/*      */ 
/*  635 */     if (Geom.VertLeq(localGLUvertex1, localGLUvertex2)) {
/*  636 */       if (Geom.EdgeSign(localGLUvertex4, localGLUvertex1, localGLUvertex2) > 0.0D) return false;
/*      */     }
/*  638 */     else if (Geom.EdgeSign(localGLUvertex3, localGLUvertex2, localGLUvertex1) < 0.0D) return false;
/*      */ 
/*  642 */     DebugEvent(paramTessellatorImpl);
/*      */ 
/*  644 */     Geom.EdgeIntersect(localGLUvertex3, localGLUvertex1, localGLUvertex4, localGLUvertex2, localGLUvertex5);
/*      */ 
/*  646 */     assert (Math.min(localGLUvertex1.t, localGLUvertex3.t) <= localGLUvertex5.t);
/*  647 */     assert (localGLUvertex5.t <= Math.max(localGLUvertex2.t, localGLUvertex4.t));
/*  648 */     assert (Math.min(localGLUvertex4.s, localGLUvertex3.s) <= localGLUvertex5.s);
/*  649 */     assert (localGLUvertex5.s <= Math.max(localGLUvertex2.s, localGLUvertex1.s));
/*      */ 
/*  651 */     if (Geom.VertLeq(localGLUvertex5, paramTessellatorImpl.event))
/*      */     {
/*  658 */       localGLUvertex5.s = paramTessellatorImpl.event.s;
/*  659 */       localGLUvertex5.t = paramTessellatorImpl.event.t;
/*      */     }
/*      */ 
/*  667 */     GLUvertex localGLUvertex6 = Geom.VertLeq(localGLUvertex1, localGLUvertex2) ? localGLUvertex1 : localGLUvertex2;
/*  668 */     if (Geom.VertLeq(localGLUvertex6, localGLUvertex5)) {
/*  669 */       localGLUvertex5.s = localGLUvertex6.s;
/*  670 */       localGLUvertex5.t = localGLUvertex6.t;
/*      */     }
/*      */ 
/*  673 */     if ((Geom.VertEq(localGLUvertex5, localGLUvertex1)) || (Geom.VertEq(localGLUvertex5, localGLUvertex2)))
/*      */     {
/*  675 */       CheckForRightSplice(paramTessellatorImpl, paramActiveRegion);
/*  676 */       return false;
/*      */     }
/*      */ 
/*  679 */     if (((!Geom.VertEq(localGLUvertex3, paramTessellatorImpl.event)) && (Geom.EdgeSign(localGLUvertex3, paramTessellatorImpl.event, localGLUvertex5) >= 0.0D)) || ((!Geom.VertEq(localGLUvertex4, paramTessellatorImpl.event)) && (Geom.EdgeSign(localGLUvertex4, paramTessellatorImpl.event, localGLUvertex5) <= 0.0D)))
/*      */     {
/*  687 */       if (localGLUvertex4 == paramTessellatorImpl.event)
/*      */       {
/*  689 */         if (Mesh.__gl_meshSplitEdge(localGLUhalfEdge1.Sym) == null) throw new RuntimeException();
/*  690 */         if (!Mesh.__gl_meshSplice(localGLUhalfEdge2.Sym, localGLUhalfEdge1)) throw new RuntimeException();
/*  691 */         paramActiveRegion = TopLeftRegion(paramActiveRegion);
/*  692 */         if (paramActiveRegion == null) throw new RuntimeException();
/*  693 */         localGLUhalfEdge1 = RegionBelow(paramActiveRegion).eUp;
/*  694 */         FinishLeftRegions(paramTessellatorImpl, RegionBelow(paramActiveRegion), localActiveRegion);
/*  695 */         AddRightEdges(paramTessellatorImpl, paramActiveRegion, localGLUhalfEdge1.Sym.Lnext, localGLUhalfEdge1, localGLUhalfEdge1, true);
/*  696 */         return true;
/*      */       }
/*  698 */       if (localGLUvertex3 == paramTessellatorImpl.event)
/*      */       {
/*  700 */         if (Mesh.__gl_meshSplitEdge(localGLUhalfEdge2.Sym) == null) throw new RuntimeException();
/*  701 */         if (!Mesh.__gl_meshSplice(localGLUhalfEdge1.Lnext, localGLUhalfEdge2.Sym.Lnext)) throw new RuntimeException();
/*  702 */         localActiveRegion = paramActiveRegion;
/*  703 */         paramActiveRegion = TopRightRegion(paramActiveRegion);
/*  704 */         GLUhalfEdge localGLUhalfEdge3 = RegionBelow(paramActiveRegion).eUp.Sym.Onext;
/*  705 */         localActiveRegion.eUp = localGLUhalfEdge2.Sym.Lnext;
/*  706 */         localGLUhalfEdge2 = FinishLeftRegions(paramTessellatorImpl, localActiveRegion, null);
/*  707 */         AddRightEdges(paramTessellatorImpl, paramActiveRegion, localGLUhalfEdge2.Onext, localGLUhalfEdge1.Sym.Onext, localGLUhalfEdge3, true);
/*  708 */         return true;
/*      */       }
/*      */ 
/*  714 */       if (Geom.EdgeSign(localGLUvertex3, paramTessellatorImpl.event, localGLUvertex5) >= 0.0D) {
/*  715 */         RegionAbove(paramActiveRegion).dirty = (paramActiveRegion.dirty = 1);
/*  716 */         if (Mesh.__gl_meshSplitEdge(localGLUhalfEdge1.Sym) == null) throw new RuntimeException();
/*  717 */         localGLUhalfEdge1.Org.s = paramTessellatorImpl.event.s;
/*  718 */         localGLUhalfEdge1.Org.t = paramTessellatorImpl.event.t;
/*      */       }
/*  720 */       if (Geom.EdgeSign(localGLUvertex4, paramTessellatorImpl.event, localGLUvertex5) <= 0.0D) {
/*  721 */         paramActiveRegion.dirty = (localActiveRegion.dirty = 1);
/*  722 */         if (Mesh.__gl_meshSplitEdge(localGLUhalfEdge2.Sym) == null) throw new RuntimeException();
/*  723 */         localGLUhalfEdge2.Org.s = paramTessellatorImpl.event.s;
/*  724 */         localGLUhalfEdge2.Org.t = paramTessellatorImpl.event.t;
/*      */       }
/*      */ 
/*  727 */       return false;
/*      */     }
/*      */ 
/*  738 */     if (Mesh.__gl_meshSplitEdge(localGLUhalfEdge1.Sym) == null) throw new RuntimeException();
/*  739 */     if (Mesh.__gl_meshSplitEdge(localGLUhalfEdge2.Sym) == null) throw new RuntimeException();
/*  740 */     if (!Mesh.__gl_meshSplice(localGLUhalfEdge2.Sym.Lnext, localGLUhalfEdge1)) throw new RuntimeException();
/*  741 */     localGLUhalfEdge1.Org.s = localGLUvertex5.s;
/*  742 */     localGLUhalfEdge1.Org.t = localGLUvertex5.t;
/*  743 */     localGLUhalfEdge1.Org.pqHandle = paramTessellatorImpl.pq.pqInsert(localGLUhalfEdge1.Org);
/*  744 */     if (localGLUhalfEdge1.Org.pqHandle == 9223372036854775807L) {
/*  745 */       paramTessellatorImpl.pq.pqDeletePriorityQ();
/*  746 */       paramTessellatorImpl.pq = null;
/*  747 */       throw new RuntimeException();
/*      */     }
/*  749 */     GetIntersectData(paramTessellatorImpl, localGLUhalfEdge1.Org, localGLUvertex1, localGLUvertex3, localGLUvertex2, localGLUvertex4);
/*  750 */     RegionAbove(paramActiveRegion).dirty = (paramActiveRegion.dirty = localActiveRegion.dirty = 1);
/*  751 */     return false;
/*      */   }
/*      */ 
/*      */   static void WalkDirtyRegions(TessellatorImpl paramTessellatorImpl, ActiveRegion paramActiveRegion)
/*      */   {
/*  763 */     ActiveRegion localActiveRegion = RegionBelow(paramActiveRegion);
/*      */     while (true)
/*      */     {
/*  768 */       if (localActiveRegion.dirty) {
/*  769 */         paramActiveRegion = localActiveRegion;
/*  770 */         localActiveRegion = RegionBelow(localActiveRegion);
/*      */       } else {
/*  772 */         if (!paramActiveRegion.dirty) {
/*  773 */           localActiveRegion = paramActiveRegion;
/*  774 */           paramActiveRegion = RegionAbove(paramActiveRegion);
/*  775 */           if ((paramActiveRegion == null) || (!paramActiveRegion.dirty))
/*      */           {
/*  777 */             return;
/*      */           }
/*      */         }
/*  780 */         paramActiveRegion.dirty = false;
/*  781 */         GLUhalfEdge localGLUhalfEdge1 = paramActiveRegion.eUp;
/*  782 */         GLUhalfEdge localGLUhalfEdge2 = localActiveRegion.eUp;
/*      */ 
/*  784 */         if (localGLUhalfEdge1.Sym.Org != localGLUhalfEdge2.Sym.Org)
/*      */         {
/*  786 */           if (CheckForLeftSplice(paramTessellatorImpl, paramActiveRegion))
/*      */           {
/*  792 */             if (localActiveRegion.fixUpperEdge) {
/*  793 */               DeleteRegion(paramTessellatorImpl, localActiveRegion);
/*  794 */               if (!Mesh.__gl_meshDelete(localGLUhalfEdge2)) throw new RuntimeException();
/*  795 */               localActiveRegion = RegionBelow(paramActiveRegion);
/*  796 */               localGLUhalfEdge2 = localActiveRegion.eUp;
/*  797 */             } else if (paramActiveRegion.fixUpperEdge) {
/*  798 */               DeleteRegion(paramTessellatorImpl, paramActiveRegion);
/*  799 */               if (!Mesh.__gl_meshDelete(localGLUhalfEdge1)) throw new RuntimeException();
/*  800 */               paramActiveRegion = RegionAbove(localActiveRegion);
/*  801 */               localGLUhalfEdge1 = paramActiveRegion.eUp;
/*      */             }
/*      */           }
/*      */         }
/*  805 */         if (localGLUhalfEdge1.Org != localGLUhalfEdge2.Org) {
/*  806 */           if ((localGLUhalfEdge1.Sym.Org != localGLUhalfEdge2.Sym.Org) && (!paramActiveRegion.fixUpperEdge) && (!localActiveRegion.fixUpperEdge) && ((localGLUhalfEdge1.Sym.Org == paramTessellatorImpl.event) || (localGLUhalfEdge2.Sym.Org == paramTessellatorImpl.event)))
/*      */           {
/*  817 */             if (!CheckForIntersect(paramTessellatorImpl, paramActiveRegion));
/*      */           }
/*      */           else
/*      */           {
/*  825 */             CheckForRightSplice(paramTessellatorImpl, paramActiveRegion);
/*      */           }
/*      */         }
/*  828 */         if ((localGLUhalfEdge1.Org == localGLUhalfEdge2.Org) && (localGLUhalfEdge1.Sym.Org == localGLUhalfEdge2.Sym.Org))
/*      */         {
/*  830 */           AddWinding(localGLUhalfEdge2, localGLUhalfEdge1);
/*  831 */           DeleteRegion(paramTessellatorImpl, paramActiveRegion);
/*  832 */           if (!Mesh.__gl_meshDelete(localGLUhalfEdge1)) throw new RuntimeException();
/*  833 */           paramActiveRegion = RegionAbove(localActiveRegion);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   static void ConnectRightVertex(TessellatorImpl paramTessellatorImpl, ActiveRegion paramActiveRegion, GLUhalfEdge paramGLUhalfEdge)
/*      */   {
/*  873 */     GLUhalfEdge localGLUhalfEdge2 = paramGLUhalfEdge.Onext;
/*  874 */     ActiveRegion localActiveRegion = RegionBelow(paramActiveRegion);
/*  875 */     GLUhalfEdge localGLUhalfEdge3 = paramActiveRegion.eUp;
/*  876 */     GLUhalfEdge localGLUhalfEdge4 = localActiveRegion.eUp;
/*  877 */     int i = 0;
/*      */ 
/*  879 */     if (localGLUhalfEdge3.Sym.Org != localGLUhalfEdge4.Sym.Org) {
/*  880 */       CheckForIntersect(paramTessellatorImpl, paramActiveRegion);
/*      */     }
/*      */ 
/*  886 */     if (Geom.VertEq(localGLUhalfEdge3.Org, paramTessellatorImpl.event)) {
/*  887 */       if (!Mesh.__gl_meshSplice(localGLUhalfEdge2.Sym.Lnext, localGLUhalfEdge3)) throw new RuntimeException();
/*  888 */       paramActiveRegion = TopLeftRegion(paramActiveRegion);
/*  889 */       if (paramActiveRegion == null) throw new RuntimeException();
/*  890 */       localGLUhalfEdge2 = RegionBelow(paramActiveRegion).eUp;
/*  891 */       FinishLeftRegions(paramTessellatorImpl, RegionBelow(paramActiveRegion), localActiveRegion);
/*  892 */       i = 1;
/*      */     }
/*  894 */     if (Geom.VertEq(localGLUhalfEdge4.Org, paramTessellatorImpl.event)) {
/*  895 */       if (!Mesh.__gl_meshSplice(paramGLUhalfEdge, localGLUhalfEdge4.Sym.Lnext)) throw new RuntimeException();
/*  896 */       paramGLUhalfEdge = FinishLeftRegions(paramTessellatorImpl, localActiveRegion, null);
/*  897 */       i = 1;
/*      */     }
/*  899 */     if (i != 0) {
/*  900 */       AddRightEdges(paramTessellatorImpl, paramActiveRegion, paramGLUhalfEdge.Onext, localGLUhalfEdge2, localGLUhalfEdge2, true);
/*  901 */       return;
/*      */     }
/*      */ 
/*  907 */     if (Geom.VertLeq(localGLUhalfEdge4.Org, localGLUhalfEdge3.Org))
/*  908 */       localGLUhalfEdge1 = localGLUhalfEdge4.Sym.Lnext;
/*      */     else {
/*  910 */       localGLUhalfEdge1 = localGLUhalfEdge3;
/*      */     }
/*  912 */     GLUhalfEdge localGLUhalfEdge1 = Mesh.__gl_meshConnect(paramGLUhalfEdge.Onext.Sym, localGLUhalfEdge1);
/*  913 */     if (localGLUhalfEdge1 == null) throw new RuntimeException();
/*      */ 
/*  918 */     AddRightEdges(paramTessellatorImpl, paramActiveRegion, localGLUhalfEdge1, localGLUhalfEdge1.Onext, localGLUhalfEdge1.Onext, false);
/*  919 */     localGLUhalfEdge1.Sym.activeRegion.fixUpperEdge = true;
/*  920 */     WalkDirtyRegions(paramTessellatorImpl, paramActiveRegion);
/*      */   }
/*      */ 
/*      */   static void ConnectLeftDegenerate(TessellatorImpl paramTessellatorImpl, ActiveRegion paramActiveRegion, GLUvertex paramGLUvertex)
/*      */   {
/*  942 */     GLUhalfEdge localGLUhalfEdge1 = paramActiveRegion.eUp;
/*  943 */     if (Geom.VertEq(localGLUhalfEdge1.Org, paramGLUvertex))
/*      */     {
/*  947 */       if (!$assertionsDisabled) throw new AssertionError();
/*  948 */       SpliceMergeVertices(paramTessellatorImpl, localGLUhalfEdge1, paramGLUvertex.anEdge);
/*  949 */       return;
/*      */     }
/*      */ 
/*  952 */     if (!Geom.VertEq(localGLUhalfEdge1.Sym.Org, paramGLUvertex))
/*      */     {
/*  954 */       if (Mesh.__gl_meshSplitEdge(localGLUhalfEdge1.Sym) == null) throw new RuntimeException();
/*  955 */       if (paramActiveRegion.fixUpperEdge)
/*      */       {
/*  957 */         if (!Mesh.__gl_meshDelete(localGLUhalfEdge1.Onext)) throw new RuntimeException();
/*  958 */         paramActiveRegion.fixUpperEdge = false;
/*      */       }
/*  960 */       if (!Mesh.__gl_meshSplice(paramGLUvertex.anEdge, localGLUhalfEdge1)) throw new RuntimeException();
/*  961 */       SweepEvent(paramTessellatorImpl, paramGLUvertex);
/*  962 */       return;
/*      */     }
/*      */ 
/*  968 */     if (!$assertionsDisabled) throw new AssertionError();
/*  969 */     paramActiveRegion = TopRightRegion(paramActiveRegion);
/*  970 */     ActiveRegion localActiveRegion = RegionBelow(paramActiveRegion);
/*  971 */     GLUhalfEdge localGLUhalfEdge3 = localActiveRegion.eUp.Sym;
/*      */     GLUhalfEdge localGLUhalfEdge4;
/*  972 */     GLUhalfEdge localGLUhalfEdge2 = localGLUhalfEdge4 = localGLUhalfEdge3.Onext;
/*  973 */     if (localActiveRegion.fixUpperEdge)
/*      */     {
/*  977 */       assert (localGLUhalfEdge2 != localGLUhalfEdge3);
/*  978 */       DeleteRegion(paramTessellatorImpl, localActiveRegion);
/*  979 */       if (!Mesh.__gl_meshDelete(localGLUhalfEdge3)) throw new RuntimeException();
/*  980 */       localGLUhalfEdge3 = localGLUhalfEdge2.Sym.Lnext;
/*      */     }
/*  982 */     if (!Mesh.__gl_meshSplice(paramGLUvertex.anEdge, localGLUhalfEdge3)) throw new RuntimeException();
/*  983 */     if (!Geom.EdgeGoesLeft(localGLUhalfEdge2))
/*      */     {
/*  985 */       localGLUhalfEdge2 = null;
/*      */     }
/*  987 */     AddRightEdges(paramTessellatorImpl, paramActiveRegion, localGLUhalfEdge3.Onext, localGLUhalfEdge4, localGLUhalfEdge2, true);
/*      */   }
/*      */ 
/*      */   static void ConnectLeftVertex(TessellatorImpl paramTessellatorImpl, GLUvertex paramGLUvertex)
/*      */   {
/* 1009 */     ActiveRegion localActiveRegion4 = new ActiveRegion();
/*      */ 
/* 1014 */     localActiveRegion4.eUp = paramGLUvertex.anEdge.Sym;
/*      */ 
/* 1016 */     ActiveRegion localActiveRegion1 = (ActiveRegion)Dict.dictKey(Dict.dictSearch(paramTessellatorImpl.dict, localActiveRegion4));
/* 1017 */     ActiveRegion localActiveRegion2 = RegionBelow(localActiveRegion1);
/* 1018 */     GLUhalfEdge localGLUhalfEdge1 = localActiveRegion1.eUp;
/* 1019 */     GLUhalfEdge localGLUhalfEdge2 = localActiveRegion2.eUp;
/*      */ 
/* 1022 */     if (Geom.EdgeSign(localGLUhalfEdge1.Sym.Org, paramGLUvertex, localGLUhalfEdge1.Org) == 0.0D) {
/* 1023 */       ConnectLeftDegenerate(paramTessellatorImpl, localActiveRegion1, paramGLUvertex);
/* 1024 */       return;
/*      */     }
/*      */ 
/* 1030 */     ActiveRegion localActiveRegion3 = Geom.VertLeq(localGLUhalfEdge2.Sym.Org, localGLUhalfEdge1.Sym.Org) ? localActiveRegion1 : localActiveRegion2;
/*      */ 
/* 1032 */     if ((localActiveRegion1.inside) || (localActiveRegion3.fixUpperEdge))
/*      */     {
/*      */       GLUhalfEdge localGLUhalfEdge3;
/* 1033 */       if (localActiveRegion3 == localActiveRegion1) {
/* 1034 */         localGLUhalfEdge3 = Mesh.__gl_meshConnect(paramGLUvertex.anEdge.Sym, localGLUhalfEdge1.Lnext);
/* 1035 */         if (localGLUhalfEdge3 == null) throw new RuntimeException(); 
/*      */       }
/* 1037 */       else { GLUhalfEdge localGLUhalfEdge4 = Mesh.__gl_meshConnect(localGLUhalfEdge2.Sym.Onext.Sym, paramGLUvertex.anEdge);
/* 1038 */         if (localGLUhalfEdge4 == null) throw new RuntimeException();
/*      */ 
/* 1040 */         localGLUhalfEdge3 = localGLUhalfEdge4.Sym;
/*      */       }
/* 1042 */       if (localActiveRegion3.fixUpperEdge) {
/* 1043 */         if (!FixUpperEdge(localActiveRegion3, localGLUhalfEdge3)) throw new RuntimeException(); 
/*      */       }
/*      */       else {
/* 1045 */         ComputeWinding(paramTessellatorImpl, AddRegionBelow(paramTessellatorImpl, localActiveRegion1, localGLUhalfEdge3));
/*      */       }
/* 1047 */       SweepEvent(paramTessellatorImpl, paramGLUvertex);
/*      */     }
/*      */     else
/*      */     {
/* 1052 */       AddRightEdges(paramTessellatorImpl, localActiveRegion1, paramGLUvertex.anEdge, paramGLUvertex.anEdge, null, true);
/*      */     }
/*      */   }
/*      */ 
/*      */   static void SweepEvent(TessellatorImpl paramTessellatorImpl, GLUvertex paramGLUvertex)
/*      */   {
/* 1065 */     paramTessellatorImpl.event = paramGLUvertex;
/* 1066 */     DebugEvent(paramTessellatorImpl);
/*      */ 
/* 1072 */     GLUhalfEdge localGLUhalfEdge1 = paramGLUvertex.anEdge;
/* 1073 */     while (localGLUhalfEdge1.activeRegion == null) {
/* 1074 */       localGLUhalfEdge1 = localGLUhalfEdge1.Onext;
/* 1075 */       if (localGLUhalfEdge1 == paramGLUvertex.anEdge)
/*      */       {
/* 1077 */         ConnectLeftVertex(paramTessellatorImpl, paramGLUvertex);
/* 1078 */         return;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1089 */     ActiveRegion localActiveRegion1 = TopLeftRegion(localGLUhalfEdge1.activeRegion);
/* 1090 */     if (localActiveRegion1 == null) throw new RuntimeException();
/* 1091 */     ActiveRegion localActiveRegion2 = RegionBelow(localActiveRegion1);
/* 1092 */     GLUhalfEdge localGLUhalfEdge2 = localActiveRegion2.eUp;
/* 1093 */     GLUhalfEdge localGLUhalfEdge3 = FinishLeftRegions(paramTessellatorImpl, localActiveRegion2, null);
/*      */ 
/* 1100 */     if (localGLUhalfEdge3.Onext == localGLUhalfEdge2)
/*      */     {
/* 1102 */       ConnectRightVertex(paramTessellatorImpl, localActiveRegion1, localGLUhalfEdge3);
/*      */     }
/* 1104 */     else AddRightEdges(paramTessellatorImpl, localActiveRegion1, localGLUhalfEdge3.Onext, localGLUhalfEdge2, localGLUhalfEdge2, true);
/*      */   }
/*      */ 
/*      */   static void AddSentinel(TessellatorImpl paramTessellatorImpl, double paramDouble)
/*      */   {
/* 1122 */     ActiveRegion localActiveRegion = new ActiveRegion();
/* 1123 */     if (localActiveRegion == null) throw new RuntimeException();
/*      */ 
/* 1125 */     GLUhalfEdge localGLUhalfEdge = Mesh.__gl_meshMakeEdge(paramTessellatorImpl.mesh);
/* 1126 */     if (localGLUhalfEdge == null) throw new RuntimeException();
/*      */ 
/* 1128 */     localGLUhalfEdge.Org.s = 4.0E+150D;
/* 1129 */     localGLUhalfEdge.Org.t = paramDouble;
/* 1130 */     localGLUhalfEdge.Sym.Org.s = -4.0E+150D;
/* 1131 */     localGLUhalfEdge.Sym.Org.t = paramDouble;
/* 1132 */     paramTessellatorImpl.event = localGLUhalfEdge.Sym.Org;
/*      */ 
/* 1134 */     localActiveRegion.eUp = localGLUhalfEdge;
/* 1135 */     localActiveRegion.windingNumber = 0;
/* 1136 */     localActiveRegion.inside = false;
/* 1137 */     localActiveRegion.fixUpperEdge = false;
/* 1138 */     localActiveRegion.sentinel = true;
/* 1139 */     localActiveRegion.dirty = false;
/* 1140 */     localActiveRegion.nodeUp = Dict.dictInsert(paramTessellatorImpl.dict, localActiveRegion);
/* 1141 */     if (localActiveRegion.nodeUp == null) throw new RuntimeException();
/*      */   }
/*      */ 
/*      */   static void InitEdgeDict(TessellatorImpl paramTessellatorImpl)
/*      */   {
/* 1151 */     paramTessellatorImpl.dict = Dict.dictNewDict(paramTessellatorImpl, new Dict.DictLeq() {
/*      */       public boolean leq(Object paramAnonymousObject1, Object paramAnonymousObject2, Object paramAnonymousObject3) {
/* 1153 */         return Sweep.EdgeLeq(this.val$tess, (ActiveRegion)paramAnonymousObject2, (ActiveRegion)paramAnonymousObject3);
/*      */       }
/*      */     });
/* 1156 */     if (paramTessellatorImpl.dict == null) throw new RuntimeException();
/*      */ 
/* 1158 */     AddSentinel(paramTessellatorImpl, -4.0E+150D);
/* 1159 */     AddSentinel(paramTessellatorImpl, 4.0E+150D);
/*      */   }
/*      */ 
/*      */   static void DoneEdgeDict(TessellatorImpl paramTessellatorImpl)
/*      */   {
/* 1165 */     int i = 0;
/*      */     ActiveRegion localActiveRegion;
/* 1168 */     while ((localActiveRegion = (ActiveRegion)Dict.dictKey(Dict.dictMin(paramTessellatorImpl.dict))) != null)
/*      */     {
/* 1174 */       if (!localActiveRegion.sentinel) {
/* 1175 */         assert (localActiveRegion.fixUpperEdge);
/* 1176 */         if (!$assertionsDisabled) { i++; if (i != 1) throw new AssertionError();  }
/*      */ 
/*      */       }
/* 1178 */       assert (localActiveRegion.windingNumber == 0);
/* 1179 */       DeleteRegion(paramTessellatorImpl, localActiveRegion);
/*      */     }
/*      */ 
/* 1182 */     Dict.dictDeleteDict(paramTessellatorImpl.dict);
/*      */   }
/*      */ 
/*      */   static void RemoveDegenerateEdges(TessellatorImpl paramTessellatorImpl)
/*      */   {
/* 1191 */     GLUhalfEdge localGLUhalfEdge3 = paramTessellatorImpl.mesh.eHead;
/*      */     GLUhalfEdge localGLUhalfEdge1;
/* 1194 */     for (Object localObject = localGLUhalfEdge3.next; localObject != localGLUhalfEdge3; localObject = localGLUhalfEdge1) {
/* 1195 */       localGLUhalfEdge1 = ((GLUhalfEdge)localObject).next;
/* 1196 */       GLUhalfEdge localGLUhalfEdge2 = ((GLUhalfEdge)localObject).Lnext;
/*      */ 
/* 1198 */       if ((Geom.VertEq(((GLUhalfEdge)localObject).Org, ((GLUhalfEdge)localObject).Sym.Org)) && (((GLUhalfEdge)localObject).Lnext.Lnext != localObject))
/*      */       {
/* 1201 */         SpliceMergeVertices(paramTessellatorImpl, localGLUhalfEdge2, (GLUhalfEdge)localObject);
/* 1202 */         if (!Mesh.__gl_meshDelete((GLUhalfEdge)localObject)) throw new RuntimeException();
/* 1203 */         localObject = localGLUhalfEdge2;
/* 1204 */         localGLUhalfEdge2 = ((GLUhalfEdge)localObject).Lnext;
/*      */       }
/* 1206 */       if (localGLUhalfEdge2.Lnext == localObject)
/*      */       {
/* 1209 */         if (localGLUhalfEdge2 != localObject) {
/* 1210 */           if ((localGLUhalfEdge2 == localGLUhalfEdge1) || (localGLUhalfEdge2 == localGLUhalfEdge1.Sym)) {
/* 1211 */             localGLUhalfEdge1 = localGLUhalfEdge1.next;
/*      */           }
/* 1213 */           if (!Mesh.__gl_meshDelete(localGLUhalfEdge2)) throw new RuntimeException();
/*      */         }
/* 1215 */         if ((localObject == localGLUhalfEdge1) || (localObject == localGLUhalfEdge1.Sym)) {
/* 1216 */           localGLUhalfEdge1 = localGLUhalfEdge1.next;
/*      */         }
/* 1218 */         if (!Mesh.__gl_meshDelete((GLUhalfEdge)localObject)) throw new RuntimeException();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   static boolean InitPriorityQ(TessellatorImpl paramTessellatorImpl)
/*      */   {
/* 1232 */     PriorityQ localPriorityQ = paramTessellatorImpl.pq = PriorityQ.pqNewPriorityQ(new PriorityQ.Leq() {
/*      */       public boolean leq(Object paramAnonymousObject1, Object paramAnonymousObject2) {
/* 1234 */         return Geom.VertLeq((GLUvertex)paramAnonymousObject1, (GLUvertex)paramAnonymousObject2);
/*      */       }
/*      */     });
/* 1237 */     if (localPriorityQ == null) return false;
/*      */ 
/* 1239 */     GLUvertex localGLUvertex2 = paramTessellatorImpl.mesh.vHead;
/* 1240 */     for (GLUvertex localGLUvertex1 = localGLUvertex2.next; localGLUvertex1 != localGLUvertex2; localGLUvertex1 = localGLUvertex1.next) {
/* 1241 */       localGLUvertex1.pqHandle = localPriorityQ.pqInsert(localGLUvertex1);
/* 1242 */       if (localGLUvertex1.pqHandle == 9223372036854775807L) break;
/*      */     }
/* 1244 */     if ((localGLUvertex1 != localGLUvertex2) || (!localPriorityQ.pqInit())) {
/* 1245 */       paramTessellatorImpl.pq.pqDeletePriorityQ();
/* 1246 */       paramTessellatorImpl.pq = null;
/* 1247 */       return false;
/*      */     }
/*      */ 
/* 1250 */     return true;
/*      */   }
/*      */ 
/*      */   static void DonePriorityQ(TessellatorImpl paramTessellatorImpl)
/*      */   {
/* 1255 */     paramTessellatorImpl.pq.pqDeletePriorityQ();
/*      */   }
/*      */ 
/*      */   static boolean RemoveDegenerateFaces(GLUmesh paramGLUmesh)
/*      */   {
/*      */     GLUface localGLUface;
/* 1278 */     for (Object localObject = paramGLUmesh.fHead.next; localObject != paramGLUmesh.fHead; localObject = localGLUface) {
/* 1279 */       localGLUface = ((GLUface)localObject).next;
/* 1280 */       GLUhalfEdge localGLUhalfEdge = ((GLUface)localObject).anEdge;
/* 1281 */       assert (localGLUhalfEdge.Lnext != localGLUhalfEdge);
/*      */ 
/* 1283 */       if (localGLUhalfEdge.Lnext.Lnext == localGLUhalfEdge)
/*      */       {
/* 1285 */         AddWinding(localGLUhalfEdge.Onext, localGLUhalfEdge);
/* 1286 */         if (!Mesh.__gl_meshDelete(localGLUhalfEdge)) return false;
/*      */       }
/*      */     }
/* 1289 */     return true;
/*      */   }
/*      */ 
/*      */   public static boolean __gl_computeInterior(TessellatorImpl paramTessellatorImpl)
/*      */   {
/* 1302 */     paramTessellatorImpl.fatalError = false;
/*      */ 
/* 1310 */     RemoveDegenerateEdges(paramTessellatorImpl);
/* 1311 */     if (!InitPriorityQ(paramTessellatorImpl)) return false;
/* 1312 */     InitEdgeDict(paramTessellatorImpl);
/*      */     GLUvertex localGLUvertex1;
/* 1315 */     while ((localGLUvertex1 = (GLUvertex)paramTessellatorImpl.pq.pqExtractMin()) != null) {
/*      */       while (true) {
/* 1317 */         GLUvertex localGLUvertex2 = (GLUvertex)paramTessellatorImpl.pq.pqMinimum();
/* 1318 */         if ((localGLUvertex2 == null) || (!Geom.VertEq(localGLUvertex2, localGLUvertex1)))
/*      */         {
/*      */           break;
/*      */         }
/*      */ 
/* 1334 */         localGLUvertex2 = (GLUvertex)paramTessellatorImpl.pq.pqExtractMin();
/* 1335 */         SpliceMergeVertices(paramTessellatorImpl, localGLUvertex1.anEdge, localGLUvertex2.anEdge);
/*      */       }
/* 1337 */       SweepEvent(paramTessellatorImpl, localGLUvertex1);
/*      */     }
/*      */ 
/* 1342 */     paramTessellatorImpl.event = ((ActiveRegion)Dict.dictKey(Dict.dictMin(paramTessellatorImpl.dict))).eUp.Org;
/* 1343 */     DebugEvent(paramTessellatorImpl);
/* 1344 */     DoneEdgeDict(paramTessellatorImpl);
/* 1345 */     DonePriorityQ(paramTessellatorImpl);
/*      */ 
/* 1347 */     if (!RemoveDegenerateFaces(paramTessellatorImpl.mesh)) return false;
/* 1348 */     Mesh.__gl_meshCheckMesh(paramTessellatorImpl.mesh);
/*      */ 
/* 1350 */     return true;
/*      */   }
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.util.tess.impl.tess.Sweep
 * JD-Core Version:    0.6.2
 */