/*     */ package com.sun.scenario.effect;
/*     */ 
/*     */ import com.sun.scenario.effect.impl.Renderer;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class FloatMap
/*     */ {
/*     */   private final int width;
/*     */   private final int height;
/*     */   private final FloatBuffer buf;
/*     */   private boolean cacheValid;
/*     */   private Map<FilterContext, Entry> cache;
/*     */ 
/*     */   public FloatMap(int paramInt1, int paramInt2)
/*     */   {
/*  53 */     if ((paramInt1 <= 0) || (paramInt1 > 4096) || (paramInt2 <= 0) || (paramInt2 > 4096)) {
/*  54 */       throw new IllegalArgumentException("Width and height must be in the range [1, 4096]");
/*     */     }
/*  56 */     this.width = paramInt1;
/*  57 */     this.height = paramInt2;
/*  58 */     int i = paramInt1 * paramInt2 * 4;
/*     */ 
/*  67 */     this.buf = FloatBuffer.wrap(new float[i]);
/*     */   }
/*     */ 
/*     */   public int getWidth()
/*     */   {
/*  76 */     return this.width;
/*     */   }
/*     */ 
/*     */   public int getHeight()
/*     */   {
/*  85 */     return this.height;
/*     */   }
/*     */ 
/*     */   public float[] getData()
/*     */   {
/*  91 */     return this.buf.array();
/*     */   }
/*     */ 
/*     */   public FloatBuffer getBuffer() {
/*  95 */     return this.buf;
/*     */   }
/*     */ 
/*     */   public float getSample(int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/* 107 */     return this.buf.get((paramInt1 + paramInt2 * this.width) * 4 + paramInt3);
/*     */   }
/*     */ 
/*     */   public void setSample(int paramInt1, int paramInt2, int paramInt3, float paramFloat)
/*     */   {
/* 119 */     this.buf.put((paramInt1 + paramInt2 * this.width) * 4 + paramInt3, paramFloat);
/* 120 */     this.cacheValid = false;
/*     */   }
/*     */ 
/*     */   public void setSamples(int paramInt1, int paramInt2, float paramFloat)
/*     */   {
/* 131 */     int i = (paramInt1 + paramInt2 * this.width) * 4;
/* 132 */     this.buf.put(i + 0, paramFloat);
/* 133 */     this.cacheValid = false;
/*     */   }
/*     */ 
/*     */   public void setSamples(int paramInt1, int paramInt2, float paramFloat1, float paramFloat2)
/*     */   {
/* 145 */     int i = (paramInt1 + paramInt2 * this.width) * 4;
/* 146 */     this.buf.put(i + 0, paramFloat1);
/* 147 */     this.buf.put(i + 1, paramFloat2);
/* 148 */     this.cacheValid = false;
/*     */   }
/*     */ 
/*     */   public void setSamples(int paramInt1, int paramInt2, float paramFloat1, float paramFloat2, float paramFloat3)
/*     */   {
/* 161 */     int i = (paramInt1 + paramInt2 * this.width) * 4;
/* 162 */     this.buf.put(i + 0, paramFloat1);
/* 163 */     this.buf.put(i + 1, paramFloat2);
/* 164 */     this.buf.put(i + 2, paramFloat3);
/* 165 */     this.cacheValid = false;
/*     */   }
/*     */ 
/*     */   public void setSamples(int paramInt1, int paramInt2, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/* 179 */     int i = (paramInt1 + paramInt2 * this.width) * 4;
/* 180 */     this.buf.put(i + 0, paramFloat1);
/* 181 */     this.buf.put(i + 1, paramFloat2);
/* 182 */     this.buf.put(i + 2, paramFloat3);
/* 183 */     this.buf.put(i + 3, paramFloat4);
/* 184 */     this.cacheValid = false;
/*     */   }
/*     */ 
/*     */   public void put(float[] paramArrayOfFloat) {
/* 188 */     this.buf.rewind();
/* 189 */     this.buf.put(paramArrayOfFloat);
/* 190 */     this.buf.rewind();
/* 191 */     this.cacheValid = false;
/*     */   }
/*     */ 
/*     */   public Object getAccelData(FilterContext paramFilterContext)
/*     */   {
/* 197 */     if (this.cache == null) {
/* 198 */       this.cache = new HashMap();
/* 199 */     } else if (!this.cacheValid) {
/* 200 */       for (localObject1 = this.cache.values().iterator(); ((Iterator)localObject1).hasNext(); ) { localEntry = (Entry)((Iterator)localObject1).next();
/* 201 */         localEntry.valid = false;
/*     */       }
/* 203 */       this.cacheValid = true;
/*     */     }
/*     */ 
/* 209 */     Object localObject1 = Renderer.getRenderer(paramFilterContext);
/* 210 */     Entry localEntry = (Entry)this.cache.get(paramFilterContext);
/* 211 */     if (localEntry == null) {
/* 212 */       Object localObject2 = ((Renderer)localObject1).createFloatTexture(this.width, this.height);
/* 213 */       localEntry = new Entry(localObject2);
/* 214 */       this.cache.put(paramFilterContext, localEntry);
/*     */     }
/* 216 */     if (!localEntry.valid) {
/* 217 */       ((Renderer)localObject1).updateFloatTexture(localEntry.texture, this);
/* 218 */       localEntry.valid = true;
/*     */     }
/*     */ 
/* 221 */     return localEntry.texture;
/*     */   }
/*     */   private static class Entry {
/*     */     Object texture;
/*     */     boolean valid;
/*     */ 
/* 228 */     Entry(Object paramObject) { this.texture = paramObject; }
/*     */ 
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.FloatMap
 * JD-Core Version:    0.6.2
 */