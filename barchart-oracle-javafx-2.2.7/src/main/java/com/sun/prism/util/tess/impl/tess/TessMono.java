/*     */ package com.sun.prism.util.tess.impl.tess;
/*     */ 
/*     */ class TessMono
/*     */ {
/*     */   private static final int MARKED_FOR_DELETION = 2147483647;
/*     */ 
/*     */   static boolean __gl_meshTessellateMonoRegion(GLUface paramGLUface, boolean paramBoolean)
/*     */   {
/*  91 */     GLUhalfEdge localGLUhalfEdge1 = paramGLUface.anEdge;
/*  92 */     assert ((localGLUhalfEdge1.Lnext != localGLUhalfEdge1) && (localGLUhalfEdge1.Lnext.Lnext != localGLUhalfEdge1));
/*     */ 
/*  94 */     while (Geom.VertLeq(localGLUhalfEdge1.Sym.Org, localGLUhalfEdge1.Org)) localGLUhalfEdge1 = localGLUhalfEdge1.Onext.Sym;
/*     */ 
/*  96 */     while (Geom.VertLeq(localGLUhalfEdge1.Org, localGLUhalfEdge1.Sym.Org)) localGLUhalfEdge1 = localGLUhalfEdge1.Lnext;
/*     */ 
/*  98 */     GLUhalfEdge localGLUhalfEdge2 = localGLUhalfEdge1.Onext.Sym;
/*     */ 
/* 100 */     int i = 0;
/*     */     GLUhalfEdge localGLUhalfEdge3;
/* 102 */     while (localGLUhalfEdge1.Lnext != localGLUhalfEdge2) {
/* 103 */       if ((paramBoolean) && (i == 0))
/*     */       {
/* 111 */         if (Geom.EdgeCos(localGLUhalfEdge2.Lnext.Org, localGLUhalfEdge2.Org, localGLUhalfEdge2.Lnext.Lnext.Org) <= -0.9999900000000001D)
/*     */           do
/*     */           {
/* 114 */             localGLUhalfEdge2 = localGLUhalfEdge2.Onext.Sym;
/* 115 */             i = 1;
/*     */ 
/* 117 */             if (localGLUhalfEdge1.Lnext == localGLUhalfEdge2) break;  } while (Geom.EdgeCos(localGLUhalfEdge2.Lnext.Org, localGLUhalfEdge2.Org, localGLUhalfEdge2.Lnext.Lnext.Org) <= -0.9999900000000001D);
/* 118 */         else if (Geom.EdgeCos(localGLUhalfEdge1.Onext.Sym.Org, localGLUhalfEdge1.Org, localGLUhalfEdge1.Onext.Sym.Onext.Sym.Org) <= -0.9999900000000001D) {
/*     */           do
/*     */           {
/* 121 */             localGLUhalfEdge1 = localGLUhalfEdge1.Lnext;
/* 122 */             i = 1;
/*     */           }
/* 124 */           while ((localGLUhalfEdge1.Lnext != localGLUhalfEdge2) && (Geom.EdgeCos(localGLUhalfEdge1.Onext.Sym.Org, localGLUhalfEdge1.Org, localGLUhalfEdge1.Onext.Sym.Onext.Sym.Org) <= -0.9999900000000001D));
/*     */         }
/*     */ 
/* 127 */         if (localGLUhalfEdge1.Lnext == localGLUhalfEdge2) {
/*     */           break;
/*     */         }
/*     */       }
/* 131 */       if (Geom.VertLeq(localGLUhalfEdge1.Sym.Org, localGLUhalfEdge2.Org))
/*     */       {
/* 136 */         while ((localGLUhalfEdge2.Lnext != localGLUhalfEdge1) && ((Geom.EdgeGoesLeft(localGLUhalfEdge2.Lnext)) || (Geom.EdgeSign(localGLUhalfEdge2.Org, localGLUhalfEdge2.Sym.Org, localGLUhalfEdge2.Lnext.Sym.Org) <= 0.0D)))
/*     */         {
/* 138 */           localGLUhalfEdge3 = Mesh.__gl_meshConnect(localGLUhalfEdge2.Lnext, localGLUhalfEdge2);
/* 139 */           i = 0;
/* 140 */           if (localGLUhalfEdge3 == null) return false;
/* 141 */           localGLUhalfEdge2 = localGLUhalfEdge3.Sym;
/*     */         }
/* 143 */         localGLUhalfEdge2 = localGLUhalfEdge2.Onext.Sym;
/*     */       }
/*     */       else {
/* 146 */         while ((localGLUhalfEdge2.Lnext != localGLUhalfEdge1) && ((Geom.EdgeGoesRight(localGLUhalfEdge1.Onext.Sym)) || (Geom.EdgeSign(localGLUhalfEdge1.Sym.Org, localGLUhalfEdge1.Org, localGLUhalfEdge1.Onext.Sym.Org) >= 0.0D)))
/*     */         {
/* 148 */           localGLUhalfEdge3 = Mesh.__gl_meshConnect(localGLUhalfEdge1, localGLUhalfEdge1.Onext.Sym);
/* 149 */           i = 0;
/* 150 */           if (localGLUhalfEdge3 == null) return false;
/* 151 */           localGLUhalfEdge1 = localGLUhalfEdge3.Sym;
/*     */         }
/* 153 */         localGLUhalfEdge1 = localGLUhalfEdge1.Lnext;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 160 */     assert (localGLUhalfEdge2.Lnext != localGLUhalfEdge1);
/* 161 */     while (localGLUhalfEdge2.Lnext.Lnext != localGLUhalfEdge1) {
/* 162 */       localGLUhalfEdge3 = Mesh.__gl_meshConnect(localGLUhalfEdge2.Lnext, localGLUhalfEdge2);
/* 163 */       if (localGLUhalfEdge3 == null) return false;
/* 164 */       localGLUhalfEdge2 = localGLUhalfEdge3.Sym;
/*     */     }
/*     */ 
/* 167 */     return true;
/*     */   }
/*     */ 
/*     */   public static boolean __gl_meshTessellateInterior(GLUmesh paramGLUmesh, boolean paramBoolean)
/*     */   {
/*     */     GLUface localGLUface;
/* 179 */     for (Object localObject = paramGLUmesh.fHead.next; localObject != paramGLUmesh.fHead; localObject = localGLUface)
/*     */     {
/* 181 */       localGLUface = ((GLUface)localObject).next;
/* 182 */       if ((((GLUface)localObject).inside) && 
/* 183 */         (!__gl_meshTessellateMonoRegion((GLUface)localObject, paramBoolean))) return false;
/*     */ 
/*     */     }
/*     */ 
/* 187 */     return true;
/*     */   }
/*     */ 
/*     */   public static void __gl_meshDiscardExterior(GLUmesh paramGLUmesh)
/*     */   {
/*     */     GLUface localGLUface;
/* 200 */     for (Object localObject = paramGLUmesh.fHead.next; localObject != paramGLUmesh.fHead; localObject = localGLUface)
/*     */     {
/* 202 */       localGLUface = ((GLUface)localObject).next;
/* 203 */       if (!((GLUface)localObject).inside)
/* 204 */         Mesh.__gl_meshZapFace((GLUface)localObject);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static boolean __gl_meshSetWindingNumber(GLUmesh paramGLUmesh, int paramInt, boolean paramBoolean)
/*     */   {
/*     */     GLUhalfEdge localGLUhalfEdge;
/* 222 */     for (Object localObject = paramGLUmesh.eHead.next; localObject != paramGLUmesh.eHead; localObject = localGLUhalfEdge) {
/* 223 */       localGLUhalfEdge = ((GLUhalfEdge)localObject).next;
/* 224 */       if (((GLUhalfEdge)localObject).Sym.Lface.inside != ((GLUhalfEdge)localObject).Lface.inside)
/*     */       {
/* 227 */         ((GLUhalfEdge)localObject).winding = (((GLUhalfEdge)localObject).Lface.inside ? paramInt : -paramInt);
/*     */       }
/* 231 */       else if (!paramBoolean) {
/* 232 */         ((GLUhalfEdge)localObject).winding = 0;
/*     */       }
/* 234 */       else if (!Mesh.__gl_meshDelete((GLUhalfEdge)localObject)) return false;
/*     */ 
/*     */     }
/*     */ 
/* 238 */     return true;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.util.tess.impl.tess.TessMono
 * JD-Core Version:    0.6.2
 */