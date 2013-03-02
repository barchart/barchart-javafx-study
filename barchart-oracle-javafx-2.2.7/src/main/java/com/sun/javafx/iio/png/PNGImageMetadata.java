/*     */ package com.sun.javafx.iio.png;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class PNGImageMetadata
/*     */ {
/*     */   public boolean IHDR_present;
/*     */   public int IHDR_width;
/*     */   public int IHDR_height;
/*     */   public int IHDR_bitDepth;
/*     */   public int IHDR_colorType;
/*     */   public int IHDR_compressionMethod;
/*     */   public int IHDR_filterMethod;
/*     */   public int IHDR_interlaceMethod;
/*     */   public boolean PLTE_present;
/*     */   public byte[] PLTE_red;
/*     */   public byte[] PLTE_green;
/*     */   public byte[] PLTE_blue;
/*  39 */   public int[] PLTE_order = null;
/*     */   public boolean bKGD_present;
/*     */   public int bKGD_colorType;
/*     */   public int bKGD_index;
/*     */   public int bKGD_gray;
/*     */   public int bKGD_red;
/*     */   public int bKGD_green;
/*     */   public int bKGD_blue;
/*     */   public boolean cHRM_present;
/*     */   public int cHRM_whitePointX;
/*     */   public int cHRM_whitePointY;
/*     */   public int cHRM_redX;
/*     */   public int cHRM_redY;
/*     */   public int cHRM_greenX;
/*     */   public int cHRM_greenY;
/*     */   public int cHRM_blueX;
/*     */   public int cHRM_blueY;
/*     */   public boolean gAMA_present;
/*     */   public int gAMA_gamma;
/*     */   public boolean hIST_present;
/*     */   public char[] hIST_histogram;
/*     */   public boolean iCCP_present;
/*     */   public String iCCP_profileName;
/*     */   public int iCCP_compressionMethod;
/*     */   public byte[] iCCP_compressedProfile;
/*  78 */   public ArrayList<String> iTXt_keyword = new ArrayList();
/*  79 */   public ArrayList<Boolean> iTXt_compressionFlag = new ArrayList();
/*  80 */   public ArrayList<Integer> iTXt_compressionMethod = new ArrayList();
/*  81 */   public ArrayList<String> iTXt_languageTag = new ArrayList();
/*  82 */   public ArrayList<String> iTXt_translatedKeyword = new ArrayList();
/*  83 */   public ArrayList<String> iTXt_text = new ArrayList();
/*     */   public boolean pHYs_present;
/*     */   public int pHYs_pixelsPerUnitXAxis;
/*     */   public int pHYs_pixelsPerUnitYAxis;
/*     */   public int pHYs_unitSpecifier;
/*     */   public boolean sBIT_present;
/*     */   public int sBIT_colorType;
/*     */   public int sBIT_grayBits;
/*     */   public int sBIT_redBits;
/*     */   public int sBIT_greenBits;
/*     */   public int sBIT_blueBits;
/*     */   public int sBIT_alphaBits;
/*     */   public boolean sPLT_present;
/*     */   public String sPLT_paletteName;
/*     */   public int sPLT_sampleDepth;
/*     */   public int[] sPLT_red;
/*     */   public int[] sPLT_green;
/*     */   public int[] sPLT_blue;
/*     */   public int[] sPLT_alpha;
/*     */   public int[] sPLT_frequency;
/*     */   public boolean sRGB_present;
/*     */   public int sRGB_renderingIntent;
/* 115 */   public ArrayList<String> tEXt_keyword = new ArrayList();
/* 116 */   public ArrayList<String> tEXt_text = new ArrayList();
/*     */   public boolean tIME_present;
/*     */   public int tIME_year;
/*     */   public int tIME_month;
/*     */   public int tIME_day;
/*     */   public int tIME_hour;
/*     */   public int tIME_minute;
/*     */   public int tIME_second;
/*     */   public boolean tRNS_present;
/*     */   public int tRNS_colorType;
/*     */   public byte[] tRNS_alpha;
/*     */   public int tRNS_gray;
/*     */   public int tRNS_red;
/*     */   public int tRNS_green;
/*     */   public int tRNS_blue;
/* 139 */   public ArrayList<String> zTXt_keyword = new ArrayList();
/* 140 */   public ArrayList<Integer> zTXt_compressionMethod = new ArrayList();
/* 141 */   public ArrayList<String> zTXt_text = new ArrayList();
/*     */ 
/* 144 */   public ArrayList<String> unknownChunkType = new ArrayList();
/* 145 */   public ArrayList<byte[]> unknownChunkData = new ArrayList();
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.iio.png.PNGImageMetadata
 * JD-Core Version:    0.6.2
 */