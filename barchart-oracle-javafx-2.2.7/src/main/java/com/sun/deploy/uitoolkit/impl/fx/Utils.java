/*    */ package com.sun.deploy.uitoolkit.impl.fx;
/*    */ 
/*    */ import com.sun.applet2.Applet2Context;
/*    */ import com.sun.applet2.AppletParameters;
/*    */ import java.lang.reflect.Field;
/*    */ import java.util.HashMap;
/*    */ import java.util.HashSet;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class Utils
/*    */ {
/* 15 */   static final Set<String> bannedNames = new HashSet();
/* 16 */   static Object unnamedKey = null;
/*    */ 
/*    */   public static Map<String, String> getNamedParameters(Applet2Context a2c)
/*    */   {
/* 37 */     Map p = new HashMap();
/* 38 */     Map all = a2c.getParameters().rawMap();
/*    */     Iterator i$;
/* 39 */     if (all != null) {
/* 40 */       for (i$ = all.keySet().iterator(); i$.hasNext(); ) { Object k = i$.next();
/* 41 */         if (((k instanceof String)) && (!bannedNames.contains((String)k))) {
/* 42 */           p.put((String)k, (String)all.get(k));
/*    */         }
/*    */       }
/*    */     }
/*    */ 
/* 47 */     return p;
/*    */   }
/*    */ 
/*    */   public static String[] getUnnamed(Applet2Context a2c) {
/* 51 */     Map all = a2c.getParameters();
/* 52 */     return (String[])all.get(unnamedKey);
/*    */   }
/*    */ 
/*    */   static
/*    */   {
/*    */     try
/*    */     {
/* 20 */       Class c = Class.forName("sun.plugin2.util.ParameterNames", true, null);
/* 21 */       for (Field f : c.getDeclaredFields())
/* 22 */         if (f.getType() == String.class) {
/* 23 */           String nm = (String)f.get(null);
/* 24 */           bannedNames.add(nm);
/*    */         }
/* 26 */         else if ("ARGUMENTS".equals(f.getName())) {
/* 27 */           unnamedKey = f.get(null);
/*    */         }
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 32 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.deploy.uitoolkit.impl.fx.Utils
 * JD-Core Version:    0.6.2
 */