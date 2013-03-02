/*     */ package com.sun.prism.util.tess.impl.tess;
/*     */ 
/*     */ class Normal
/*     */ {
/*     */   static boolean SLANTED_SWEEP;
/*     */   static double S_UNIT_X;
/*     */   static double S_UNIT_Y;
/*     */   private static final boolean TRUE_PROJECT = false;
/*     */ 
/*     */   private static double Dot(double[] paramArrayOfDouble1, double[] paramArrayOfDouble2)
/*     */   {
/*  86 */     return paramArrayOfDouble1[0] * paramArrayOfDouble2[0] + paramArrayOfDouble1[1] * paramArrayOfDouble2[1] + paramArrayOfDouble1[2] * paramArrayOfDouble2[2];
/*     */   }
/*     */ 
/*     */   static void Normalize(double[] paramArrayOfDouble) {
/*  90 */     double d = paramArrayOfDouble[0] * paramArrayOfDouble[0] + paramArrayOfDouble[1] * paramArrayOfDouble[1] + paramArrayOfDouble[2] * paramArrayOfDouble[2];
/*     */ 
/*  92 */     assert (d > 0.0D);
/*  93 */     d = Math.sqrt(d);
/*  94 */     paramArrayOfDouble[0] /= d;
/*  95 */     paramArrayOfDouble[1] /= d;
/*  96 */     paramArrayOfDouble[2] /= d;
/*     */   }
/*     */ 
/*     */   static int LongAxis(double[] paramArrayOfDouble) {
/* 100 */     int i = 0;
/*     */ 
/* 102 */     if (Math.abs(paramArrayOfDouble[1]) > Math.abs(paramArrayOfDouble[0])) {
/* 103 */       i = 1;
/*     */     }
/* 105 */     if (Math.abs(paramArrayOfDouble[2]) > Math.abs(paramArrayOfDouble[i])) {
/* 106 */       i = 2;
/*     */     }
/* 108 */     return i;
/*     */   }
/*     */ 
/*     */   static void ComputeNormal(TessellatorImpl paramTessellatorImpl, double[] paramArrayOfDouble)
/*     */   {
/* 116 */     GLUvertex localGLUvertex4 = paramTessellatorImpl.mesh.vHead;
/*     */ 
/* 119 */     double[] arrayOfDouble1 = new double[3];
/* 120 */     double[] arrayOfDouble2 = new double[3];
/* 121 */     GLUvertex[] arrayOfGLUvertex2 = new GLUvertex[3];
/* 122 */     GLUvertex[] arrayOfGLUvertex1 = new GLUvertex[3];
/* 123 */     double[] arrayOfDouble3 = new double[3];
/* 124 */     double[] arrayOfDouble4 = new double[3];
/* 125 */     double[] arrayOfDouble5 = new double[3];
/*     */     double tmp60_59 = (arrayOfDouble1[2] = -2.0E+150D); arrayOfDouble1[1] = tmp60_59; arrayOfDouble1[0] = tmp60_59;
/*     */     double tmp77_76 = (arrayOfDouble2[2] = 2.0E+150D); arrayOfDouble2[1] = tmp77_76; arrayOfDouble2[0] = tmp77_76;
/*     */ 
/* 130 */     for (GLUvertex localGLUvertex1 = tmp60_59.next; localGLUvertex1 != tmp60_59; localGLUvertex1 = localGLUvertex1.next) {
/* 131 */       for (i = 0; i < 3; i++) {
/* 132 */         double d1 = localGLUvertex1.coords[i];
/* 133 */         if (d1 < arrayOfDouble2[i]) {
/* 134 */           arrayOfDouble2[i] = d1;
/* 135 */           arrayOfGLUvertex2[i] = localGLUvertex1;
/*     */         }
/* 137 */         if (d1 > arrayOfDouble1[i]) {
/* 138 */           arrayOfDouble1[i] = d1;
/* 139 */           arrayOfGLUvertex1[i] = localGLUvertex1;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 147 */     int i = 0;
/* 148 */     if (arrayOfDouble1[1] - arrayOfDouble2[1] > arrayOfDouble1[0] - arrayOfDouble2[0]) {
/* 149 */       i = 1;
/*     */     }
/* 151 */     if (arrayOfDouble1[2] - arrayOfDouble2[2] > arrayOfDouble1[i] - arrayOfDouble2[i]) {
/* 152 */       i = 2;
/*     */     }
/* 154 */     if (arrayOfDouble2[i] >= arrayOfDouble1[i])
/*     */     {
/* 156 */       paramArrayOfDouble[0] = 0.0D;
/* 157 */       paramArrayOfDouble[1] = 0.0D;
/* 158 */       paramArrayOfDouble[2] = 1.0D;
/* 159 */       return;
/*     */     }
/*     */ 
/* 165 */     double d3 = 0.0D;
/* 166 */     GLUvertex localGLUvertex2 = arrayOfGLUvertex2[i];
/* 167 */     GLUvertex localGLUvertex3 = arrayOfGLUvertex1[i];
/* 168 */     arrayOfDouble3[0] = (localGLUvertex2.coords[0] - localGLUvertex3.coords[0]);
/* 169 */     arrayOfDouble3[1] = (localGLUvertex2.coords[1] - localGLUvertex3.coords[1]);
/* 170 */     arrayOfDouble3[2] = (localGLUvertex2.coords[2] - localGLUvertex3.coords[2]);
/* 171 */     for (localGLUvertex1 = tmp60_59.next; localGLUvertex1 != tmp60_59; localGLUvertex1 = localGLUvertex1.next) {
/* 172 */       arrayOfDouble4[0] = (localGLUvertex1.coords[0] - localGLUvertex3.coords[0]);
/* 173 */       arrayOfDouble4[1] = (localGLUvertex1.coords[1] - localGLUvertex3.coords[1]);
/* 174 */       arrayOfDouble4[2] = (localGLUvertex1.coords[2] - localGLUvertex3.coords[2]);
/* 175 */       arrayOfDouble5[0] = (arrayOfDouble3[1] * arrayOfDouble4[2] - arrayOfDouble3[2] * arrayOfDouble4[1]);
/* 176 */       arrayOfDouble5[1] = (arrayOfDouble3[2] * arrayOfDouble4[0] - arrayOfDouble3[0] * arrayOfDouble4[2]);
/* 177 */       arrayOfDouble5[2] = (arrayOfDouble3[0] * arrayOfDouble4[1] - arrayOfDouble3[1] * arrayOfDouble4[0]);
/* 178 */       double d2 = arrayOfDouble5[0] * arrayOfDouble5[0] + arrayOfDouble5[1] * arrayOfDouble5[1] + arrayOfDouble5[2] * arrayOfDouble5[2];
/* 179 */       if (d2 > d3) {
/* 180 */         d3 = d2;
/* 181 */         paramArrayOfDouble[0] = arrayOfDouble5[0];
/* 182 */         paramArrayOfDouble[1] = arrayOfDouble5[1];
/* 183 */         paramArrayOfDouble[2] = arrayOfDouble5[2];
/*     */       }
/*     */     }
/*     */ 
/* 187 */     if (d3 <= 0.0D)
/*     */     {
/*     */       double tmp547_546 = (paramArrayOfDouble[2] = 0.0D); paramArrayOfDouble[1] = tmp547_546; paramArrayOfDouble[0] = tmp547_546;
/* 190 */       paramArrayOfDouble[LongAxis(arrayOfDouble3)] = 1.0D;
/*     */     }
/*     */   }
/*     */ 
/*     */   static void CheckOrientation(TessellatorImpl paramTessellatorImpl)
/*     */   {
/* 196 */     GLUface localGLUface2 = paramTessellatorImpl.mesh.fHead;
/* 197 */     GLUvertex localGLUvertex2 = paramTessellatorImpl.mesh.vHead;
/*     */ 
/* 203 */     double d = 0.0D;
/* 204 */     for (GLUface localGLUface1 = localGLUface2.next; localGLUface1 != localGLUface2; localGLUface1 = localGLUface1.next) {
/* 205 */       GLUhalfEdge localGLUhalfEdge = localGLUface1.anEdge;
/* 206 */       if (localGLUhalfEdge.winding > 0)
/*     */         do {
/* 208 */           d += (localGLUhalfEdge.Org.s - localGLUhalfEdge.Sym.Org.s) * (localGLUhalfEdge.Org.t + localGLUhalfEdge.Sym.Org.t);
/* 209 */           localGLUhalfEdge = localGLUhalfEdge.Lnext;
/* 210 */         }while (localGLUhalfEdge != localGLUface1.anEdge);
/*     */     }
/* 212 */     if (d < 0.0D)
/*     */     {
/* 214 */       for (GLUvertex localGLUvertex1 = localGLUvertex2.next; localGLUvertex1 != localGLUvertex2; localGLUvertex1 = localGLUvertex1.next) {
/* 215 */         localGLUvertex1.t = (-localGLUvertex1.t);
/*     */       }
/* 217 */       paramTessellatorImpl.tUnit[0] = (-paramTessellatorImpl.tUnit[0]);
/* 218 */       paramTessellatorImpl.tUnit[1] = (-paramTessellatorImpl.tUnit[1]);
/* 219 */       paramTessellatorImpl.tUnit[2] = (-paramTessellatorImpl.tUnit[2]);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void __gl_projectPolygon(TessellatorImpl paramTessellatorImpl)
/*     */   {
/* 227 */     GLUvertex localGLUvertex2 = paramTessellatorImpl.mesh.vHead;
/*     */ 
/* 229 */     double[] arrayOfDouble1 = new double[3];
/*     */ 
/* 232 */     int j = 0;
/*     */ 
/* 234 */     arrayOfDouble1[0] = paramTessellatorImpl.normal[0];
/* 235 */     arrayOfDouble1[1] = paramTessellatorImpl.normal[1];
/* 236 */     arrayOfDouble1[2] = paramTessellatorImpl.normal[2];
/* 237 */     if ((arrayOfDouble1[0] == 0.0D) && (arrayOfDouble1[1] == 0.0D) && (arrayOfDouble1[2] == 0.0D)) {
/* 238 */       ComputeNormal(paramTessellatorImpl, arrayOfDouble1);
/* 239 */       j = 1;
/*     */     }
/* 241 */     double[] arrayOfDouble2 = paramTessellatorImpl.sUnit;
/* 242 */     double[] arrayOfDouble3 = paramTessellatorImpl.tUnit;
/* 243 */     int i = LongAxis(arrayOfDouble1);
/*     */ 
/* 269 */     arrayOfDouble2[i] = 0.0D;
/* 270 */     arrayOfDouble2[((i + 1) % 3)] = S_UNIT_X;
/* 271 */     arrayOfDouble2[((i + 2) % 3)] = S_UNIT_Y;
/*     */ 
/* 273 */     arrayOfDouble3[i] = 0.0D;
/* 274 */     arrayOfDouble3[((i + 1) % 3)] = (arrayOfDouble1[i] > 0.0D ? -S_UNIT_Y : S_UNIT_Y);
/* 275 */     arrayOfDouble3[((i + 2) % 3)] = (arrayOfDouble1[i] > 0.0D ? S_UNIT_X : -S_UNIT_X);
/*     */ 
/* 279 */     for (GLUvertex localGLUvertex1 = localGLUvertex2.next; localGLUvertex1 != localGLUvertex2; localGLUvertex1 = localGLUvertex1.next) {
/* 280 */       localGLUvertex1.s = Dot(localGLUvertex1.coords, arrayOfDouble2);
/* 281 */       localGLUvertex1.t = Dot(localGLUvertex1.coords, arrayOfDouble3);
/*     */     }
/* 283 */     if (j != 0)
/* 284 */       CheckOrientation(paramTessellatorImpl);
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  61 */     SLANTED_SWEEP = false;
/*     */ 
/*  67 */     if (SLANTED_SWEEP)
/*     */     {
/*  77 */       S_UNIT_X = 0.5094153956495539D;
/*  78 */       S_UNIT_Y = 0.8605207462201063D;
/*     */     } else {
/*  80 */       S_UNIT_X = 1.0D;
/*  81 */       S_UNIT_Y = 0.0D;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.util.tess.impl.tess.Normal
 * JD-Core Version:    0.6.2
 */