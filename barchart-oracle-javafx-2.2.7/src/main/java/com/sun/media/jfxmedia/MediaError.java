/*     */ package com.sun.media.jfxmedia;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.MissingResourceException;
/*     */ import java.util.ResourceBundle;
/*     */ 
/*     */ public enum MediaError
/*     */ {
/*  75 */   ERROR_BASE_MEDIA(256), 
/*  76 */   ERROR_BASE_MANAGER(512), 
/*  77 */   ERROR_BASE_PIPELINE(768), 
/*  78 */   ERROR_BASE_FACTORY(1024), 
/*  79 */   ERROR_BASE_LOCATOR(1280), 
/*  80 */   ERROR_BASE_REGISTRY(1536), 
/*  81 */   ERROR_BASE_GSTREAMER(2048), 
/*  82 */   ERROR_BASE_SYSTEM(2560), 
/*  83 */   ERROR_BASE_FUNCTION(2816), 
/*  84 */   ERROR_BASE_JNI(3072), 
/*     */ 
/*  86 */   WARNING_BASE_JFXMEDIA(1048576), 
/*  87 */   WARNING_BASE_GSTREAMER(8388608), 
/*  88 */   WARNING_BASE_GLIB(9437184), 
/*     */ 
/*  90 */   ERROR_MASK_BASE(3840), 
/*  91 */   WARNING_MASK_BASE(15728640), 
/*     */ 
/*  93 */   ERROR_NONE(0), 
/*     */ 
/*  95 */   ERROR_MANAGER_NULL(ERROR_BASE_MANAGER.code() + 1), 
/*  96 */   ERROR_MANAGER_CREATION(ERROR_BASE_MANAGER.code() + 2), 
/*  97 */   ERROR_MANAGER_ENGINEINIT_FAIL(ERROR_BASE_MANAGER.code() + 3), 
/*  98 */   ERROR_MANAGER_RUNLOOP_FAIL(ERROR_BASE_MANAGER.code() + 4), 
/*     */ 
/* 100 */   ERROR_MEDIA_NULL(ERROR_BASE_MEDIA.code() + 1), 
/* 101 */   ERROR_MEDIA_CREATION(ERROR_BASE_MEDIA.code() + 2), 
/* 102 */   ERROR_MEDIA_UNKNOWN_PIXEL_FORMAT(ERROR_BASE_MEDIA.code() + 3), 
/* 103 */   ERROR_MEDIA_INVALID(ERROR_BASE_MEDIA.code() + 4), 
/* 104 */   ERROR_MEDIA_MARKER_NAME_NULL(ERROR_BASE_MEDIA.code() + 5), 
/* 105 */   ERROR_MEDIA_MARKER_TIME_NEGATIVE(ERROR_BASE_MEDIA.code() + 6), 
/* 106 */   ERROR_MEDIA_MARKER_MAP_NULL(ERROR_BASE_MEDIA.code() + 7), 
/* 107 */   ERROR_MEDIA_VIDEO_FORMAT_UNSUPPORTED(ERROR_BASE_MEDIA.code() + 8), 
/* 108 */   ERROR_MEDIA_AUDIO_FORMAT_UNSUPPORTED(ERROR_BASE_MEDIA.code() + 9), 
/* 109 */   ERROR_MEDIA_MP3_FORMAT_UNSUPPORTED(ERROR_BASE_MEDIA.code() + 10), 
/* 110 */   ERROR_MEDIA_AAC_FORMAT_UNSUPPORTED(ERROR_BASE_MEDIA.code() + 11), 
/* 111 */   ERROR_MEDIA_H264_FORMAT_UNSUPPORTED(ERROR_BASE_MEDIA.code() + 12), 
/* 112 */   ERROR_MEDIA_HLS_FORMAT_UNSUPPORTED(ERROR_BASE_MEDIA.code() + 13), 
/* 113 */   ERROR_MEDIA_CORRUPTED(ERROR_BASE_MEDIA.code() + 14), 
/*     */ 
/* 115 */   ERROR_PIPELINE_NULL(ERROR_BASE_PIPELINE.code() + 1), 
/* 116 */   ERROR_PIPELINE_CREATION(ERROR_BASE_PIPELINE.code() + 2), 
/* 117 */   ERROR_PIPELINE_NO_FRAME_QUEUE(ERROR_BASE_PIPELINE.code() + 3), 
/*     */ 
/* 119 */   ERROR_FACTORY_NULL(ERROR_BASE_FACTORY.code() + 1), 
/* 120 */   ERROR_FACTORY_CONTAINER_CREATION(ERROR_BASE_FACTORY.code() + 2), 
/* 121 */   ERROR_FACTORY_INVALID_URI(ERROR_BASE_FACTORY.code() + 3), 
/*     */ 
/* 123 */   ERROR_LOCATOR_NULL(ERROR_BASE_LOCATOR.code() + 1), 
/* 124 */   ERROR_LOCATOR_UNSUPPORTED_TYPE(ERROR_BASE_LOCATOR.code() + 2), 
/* 125 */   ERROR_LOCATOR_UNSUPPORTED_MEDIA_FORMAT(ERROR_BASE_LOCATOR.code() + 3), 
/* 126 */   ERROR_LOCATOR_CONNECTION_LOST(ERROR_BASE_LOCATOR.code() + 4), 
/* 127 */   ERROR_LOCATOR_CONTENT_TYPE_NULL(ERROR_BASE_LOCATOR.code() + 5), 
/*     */ 
/* 129 */   ERROR_REGISTRY_NULL(ERROR_BASE_REGISTRY.code() + 1), 
/* 130 */   ERROR_REGISTRY_PLUGIN_ALREADY_EXIST(ERROR_BASE_REGISTRY.code() + 2), 
/* 131 */   ERROR_REGISTRY_PLUGIN_PATH(ERROR_BASE_REGISTRY.code() + 3), 
/* 132 */   ERROR_REGISTRY_NO_MATCHING_RECIPE(ERROR_BASE_REGISTRY.code() + 4), 
/*     */ 
/* 134 */   ERROR_GSTREAMER_ERROR(ERROR_BASE_GSTREAMER.code() + 1), 
/* 135 */   ERROR_GSTREAMER_PIPELINE_CREATION(ERROR_BASE_GSTREAMER.code() + 2), 
/* 136 */   ERROR_GSTREAMER_AUDIO_DECODER_SINK_PAD(ERROR_BASE_GSTREAMER.code() + 3), 
/* 137 */   ERROR_GSTREAMER_AUDIO_DECODER_SRC_PAD(ERROR_BASE_GSTREAMER.code() + 4), 
/* 138 */   ERROR_GSTREAMER_AUDIO_SINK_SINK_PAD(ERROR_BASE_GSTREAMER.code() + 5), 
/* 139 */   ERROR_GSTREAMER_VIDEO_DECODER_SINK_PAD(ERROR_BASE_GSTREAMER.code() + 6), 
/* 140 */   ERROR_GSTREAMER_PIPELINE_STATE_CHANGE(ERROR_BASE_GSTREAMER.code() + 7), 
/* 141 */   ERROR_GSTREAMER_PIPELINE_SEEK(ERROR_BASE_GSTREAMER.code() + 8), 
/* 142 */   ERROR_GSTREAMER_PIPELINE_QUERY_LENGTH(ERROR_BASE_GSTREAMER.code() + 9), 
/* 143 */   ERROR_GSTREAMER_PIPELINE_QUERY_POS(ERROR_BASE_GSTREAMER.code() + 10), 
/* 144 */   ERROR_GSTREAMER_PIPELINE_METADATA_TYPE(ERROR_BASE_GSTREAMER.code() + 11), 
/* 145 */   ERROR_GSTREAMER_AUDIO_SINK_CREATE(ERROR_BASE_GSTREAMER.code() + 12), 
/* 146 */   ERROR_GSTREAMER_GET_BUFFER_SRC_PAD(ERROR_BASE_GSTREAMER.code() + 13), 
/* 147 */   ERROR_GSTREAMER_CREATE_GHOST_PAD(ERROR_BASE_GSTREAMER.code() + 14), 
/* 148 */   ERROR_GSTREAMER_ELEMENT_ADD_PAD(ERROR_BASE_GSTREAMER.code() + 15), 
/* 149 */   ERROR_GSTREAMER_UNSUPPORTED_PROTOCOL(ERROR_BASE_GSTREAMER.code() + 16), 
/* 150 */   ERROR_GSTREAMER_SOURCEFILE_NONEXISTENT(ERROR_BASE_GSTREAMER.code() + 32), 
/* 151 */   ERROR_GSTREAMER_SOURCEFILE_NONREGULAR(ERROR_BASE_GSTREAMER.code() + 48), 
/* 152 */   ERROR_GSTREAMER_ELEMENT_LINK(ERROR_BASE_GSTREAMER.code() + 64), 
/* 153 */   ERROR_GSTREAMER_ELEMENT_LINK_AUDIO_BIN(ERROR_BASE_GSTREAMER.code() + 80), 
/* 154 */   ERROR_GSTREAMER_ELEMENT_LINK_VIDEO_BIN(ERROR_BASE_GSTREAMER.code() + 96), 
/* 155 */   ERROR_GSTREAMER_ELEMENT_CREATE(ERROR_BASE_GSTREAMER.code() + 112), 
/* 156 */   ERROR_GSTREAMER_VIDEO_SINK_CREATE(ERROR_BASE_GSTREAMER.code() + 128), 
/* 157 */   ERROR_GSTREAMER_BIN_CREATE(ERROR_BASE_GSTREAMER.code() + 144), 
/* 158 */   ERROR_GSTREAMER_BIN_ADD_ELEMENT(ERROR_BASE_GSTREAMER.code() + 160), 
/* 159 */   ERROR_GSTREAMER_ELEMENT_GET_PAD(ERROR_BASE_GSTREAMER.code() + 176), 
/* 160 */   ERROR_GSTREAMER_MAIN_LOOP_CREATE(ERROR_BASE_GSTREAMER.code() + 192), 
/* 161 */   ERROR_GSTREAMER_BUS_SOURCE_ATTACH(ERROR_BASE_GSTREAMER.code() + 193), 
/* 162 */   ERROR_GSTREAMER_PIPELINE_SET_RATE_ZERO(ERROR_BASE_GSTREAMER.code() + 208), 
/* 163 */   ERROR_GSTREAMER_VIDEO_SINK_SINK_PAD(ERROR_BASE_GSTREAMER.code() + 224), 
/*     */ 
/* 165 */   ERROR_NOT_IMPLEMENTED(ERROR_BASE_SYSTEM.code() + 1), 
/* 166 */   ERROR_MEMORY_ALLOCATION(ERROR_BASE_SYSTEM.code() + 2), 
/* 167 */   ERROR_OS_UNSUPPORTED(ERROR_BASE_SYSTEM.code() + 3), 
/* 168 */   ERROR_PLATFORM_UNSUPPORTED(ERROR_BASE_SYSTEM.code() + 4), 
/*     */ 
/* 170 */   ERROR_FUNCTION_PARAM(ERROR_BASE_FUNCTION.code() + 1), 
/* 171 */   ERROR_FUNCTION_PARAM_NULL(ERROR_BASE_FUNCTION.code() + 2), 
/*     */ 
/* 173 */   ERROR_JNI_SEND_PLAYER_MEDIA_ERROR_EVENT(ERROR_BASE_JNI.code() + 1), 
/* 174 */   ERROR_JNI_SEND_PLAYER_HALT_EVENT(ERROR_BASE_JNI.code() + 2), 
/* 175 */   ERROR_JNI_SEND_PLAYER_STATE_EVENT(ERROR_BASE_JNI.code() + 3), 
/* 176 */   ERROR_JNI_SEND_NEW_FRAME_EVENT(ERROR_BASE_JNI.code() + 4), 
/* 177 */   ERROR_JNI_SEND_FRAME_SIZE_CHANGED_EVENT(ERROR_BASE_JNI.code() + 5), 
/* 178 */   ERROR_JNI_SEND_END_OF_MEDIA_EVENT(ERROR_BASE_JNI.code() + 6), 
/* 179 */   ERROR_JNI_SEND_AUDIO_TRACK_EVENT(ERROR_BASE_JNI.code() + 7), 
/* 180 */   ERROR_JNI_SEND_VIDEO_TRACK_EVENT(ERROR_BASE_JNI.code() + 8), 
/* 181 */   ERROR_JNI_SEND_METADATA_EVENT(ERROR_BASE_JNI.code() + 9), 
/* 182 */   ERROR_JNI_SEND_MARKER_EVENT(ERROR_BASE_JNI.code() + 10), 
/* 183 */   ERROR_JNI_SEND_BUFFER_PROGRESS_EVENT(ERROR_BASE_JNI.code() + 11), 
/* 184 */   ERROR_JNI_SEND_STOP_REACHED_EVENT(ERROR_BASE_JNI.code() + 12), 
/* 185 */   ERROR_JNI_SEND_DURATION_UPDATE_EVENT(ERROR_BASE_JNI.code() + 13), 
/* 186 */   ERROR_JNI_SEND_AUDIO_SPECTRUM_EVENT(ERROR_BASE_JNI.code() + 14), 
/*     */ 
/* 188 */   WARNING_GSTREAMER_WARNING(WARNING_BASE_GSTREAMER.code() + 1), 
/* 189 */   WARNING_GSTREAMER_PIPELINE_ERROR(WARNING_BASE_GSTREAMER.code() + 2), 
/* 190 */   WARNING_GSTREAMER_PIPELINE_WARNING(WARNING_BASE_GSTREAMER.code() + 3), 
/* 191 */   WARNING_GSTREAMER_PIPELINE_STATE_EVENT(WARNING_BASE_GSTREAMER.code() + 4), 
/* 192 */   WARNING_GSTREAMER_PIPELINE_FRAME_SIZE(WARNING_BASE_GSTREAMER.code() + 5), 
/* 193 */   WARNING_GSTREAMER_INVALID_FRAME(WARNING_BASE_GSTREAMER.code() + 6), 
/* 194 */   WARNING_GSTREAMER_PIPELINE_INFO_ERROR(WARNING_BASE_GSTREAMER.code() + 7), 
/* 195 */   WARNING_GSTREAMER_AUDIO_BUFFER_FIELD(WARNING_BASE_GSTREAMER.code() + 8);
/*     */ 
/*     */   private static ResourceBundle bundle;
/*     */   private static final Map<Integer, MediaError> map;
/*     */   private int code;
/*     */   private String description;
/*     */ 
/*     */   private MediaError(int code)
/*     */   {
/* 216 */     this.code = code;
/*     */   }
/*     */ 
/*     */   public int code() {
/* 220 */     return this.code;
/*     */   }
/*     */ 
/*     */   public String description() {
/* 224 */     if (this.description == null) {
/* 225 */       String errorName = name();
/* 226 */       if (bundle != null)
/*     */         try {
/* 228 */           this.description = bundle.getString(errorName);
/*     */         } catch (MissingResourceException e) {
/* 230 */           this.description = errorName;
/*     */         }
/*     */       else {
/* 233 */         this.description = errorName;
/*     */       }
/*     */     }
/* 236 */     return this.description;
/*     */   }
/*     */ 
/*     */   public static MediaError getFromCode(int code) {
/* 240 */     return (MediaError)map.get(Integer.valueOf(code));
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/* 198 */     map = new HashMap();
/*     */     try
/*     */     {
/* 202 */       bundle = ResourceBundle.getBundle("MediaErrors", Locale.getDefault());
/*     */     } catch (MissingResourceException e) {
/* 204 */       bundle = null;
/*     */     }
/*     */ 
/* 207 */     for (MediaError error : values())
/* 208 */       map.put(Integer.valueOf(error.code()), error);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.media.jfxmedia.MediaError
 * JD-Core Version:    0.6.2
 */