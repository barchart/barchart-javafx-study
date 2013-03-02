/*      */ package com.sun.javafx.css;
/*      */ 
/*      */ import com.sun.javafx.Logging;
/*      */ import com.sun.javafx.css.parser.CSSParser;
/*      */ import com.sun.javafx.logging.PlatformLogger;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.FilePermission;
/*      */ import java.io.IOException;
/*      */ import java.io.PrintStream;
/*      */ import java.lang.ref.Reference;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.net.MalformedURLException;
/*      */ import java.net.URI;
/*      */ import java.net.URISyntaxException;
/*      */ import java.net.URL;
/*      */ import java.security.AccessControlContext;
/*      */ import java.security.AccessControlException;
/*      */ import java.security.AccessController;
/*      */ import java.security.CodeSource;
/*      */ import java.security.PermissionCollection;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.security.PrivilegedActionException;
/*      */ import java.security.PrivilegedExceptionAction;
/*      */ import java.security.ProtectionDomain;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Map.Entry;
/*      */ import java.util.Set;
/*      */ import java.util.jar.JarEntry;
/*      */ import java.util.jar.JarFile;
/*      */ import javafx.collections.FXCollections;
/*      */ import javafx.collections.ListChangeListener.Change;
/*      */ import javafx.collections.ObservableList;
/*      */ import javafx.collections.ObservableMap;
/*      */ import javafx.scene.Node;
/*      */ import javafx.scene.Parent;
/*      */ import javafx.scene.Scene;
/*      */ import javafx.stage.PopupWindow;
/*      */ import javafx.stage.Window;
/*      */ 
/*      */ public class StyleManager
/*      */ {
/*      */   private Stylesheet defaultUserAgentStylesheet;
/*  101 */   private ObservableMap<String, Stylesheet> authorStylesheetMap = FXCollections.observableHashMap();
/*      */ 
/*  176 */   private static Map<String, ParentStylesheetContainer> parentStylesheetMap = new HashMap();
/*      */   private Map<WeakReference<Scene>, StylesheetContainer> containerMap;
/*      */   private StylesheetContainer defaultContainer;
/*  634 */   private Map<String, Stylesheet> userAgentStylesheetMap = new HashMap();
/*      */ 
/*  882 */   private final Map<String, Long> pseudoclassMasks = new HashMap();
/*      */ 
/* 1027 */   static int hits = 0;
/* 1028 */   static int misses = 0;
/* 1029 */   static int puts = 0;
/* 1030 */   static int tries = 0;
/*      */ 
/* 1394 */   private ObservableList<CssError> errors = null;
/*      */ 
/*      */   public static StyleManager getInstance()
/*      */   {
/*   80 */     return Holder.INSTANCE;
/*      */   }
/*      */ 
/*      */   private static PlatformLogger logger() {
/*   84 */     return Holder.LOGGER;
/*      */   }
/*      */ 
/*      */   private void addAuthorStylesheet(String paramString, Stylesheet paramStylesheet)
/*      */   {
/*  104 */     if (paramStylesheet != null) {
/*  105 */       paramStylesheet.setOrigin(Stylesheet.Origin.AUTHOR);
/*  106 */       this.authorStylesheetMap.put(paramString, paramStylesheet);
/*      */     } else {
/*  108 */       this.authorStylesheetMap.remove(paramString);
/*      */     }
/*      */   }
/*      */ 
/*      */   private Stylesheet getAuthorStylesheet(String paramString)
/*      */   {
/*  114 */     return (Stylesheet)this.authorStylesheetMap.get(paramString);
/*      */   }
/*      */ 
/*      */   public void parentStylesheetsChanged(Parent paramParent, ListChangeListener.Change<String> paramChange)
/*      */   {
/*  186 */     Scene localScene = paramParent.getScene();
/*  187 */     if (localScene == null) return;
/*      */ 
/*  189 */     int i = 0;
/*      */ 
/*  191 */     while (paramChange.next())
/*      */     {
/*  205 */       if (paramChange.wasRemoved())
/*      */       {
/*  207 */         List localList = paramChange.getRemoved();
/*  208 */         int j = localList != null ? localList.size() : 0;
/*  209 */         for (int k = 0; k < j; k++) {
/*  210 */           String str = (String)localList.get(k);
/*      */ 
/*  214 */           ParentStylesheetContainer localParentStylesheetContainer = (ParentStylesheetContainer)parentStylesheetMap.remove(str);
/*  215 */           if (localParentStylesheetContainer != null) {
/*  216 */             clearParentCache(localParentStylesheetContainer);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  227 */     paramChange.reset();
/*      */   }
/*      */ 
/*      */   private void clearParentCache(ParentStylesheetContainer paramParentStylesheetContainer)
/*      */   {
/*  234 */     List localList1 = ParentStylesheetContainer.access$400(paramParentStylesheetContainer).list;
/*  235 */     List localList2 = ParentStylesheetContainer.access$600(paramParentStylesheetContainer).list;
/*  236 */     for (int i = localList1.size() - 1; 0 <= i; i--)
/*      */     {
/*  238 */       Reference localReference = (Reference)localList1.get(i);
/*  239 */       Parent localParent = (Parent)localReference.get();
/*  240 */       if (localParent != null)
/*      */       {
/*  242 */         Scene localScene = localParent.getScene();
/*  243 */         if (localScene != null)
/*      */         {
/*  245 */           StylesheetContainer localStylesheetContainer = null;
/*  246 */           if ((this.containerMap != null) && ((localStylesheetContainer = get(this.containerMap, localScene)) != null))
/*      */           {
/*  248 */             clearParentCache(localStylesheetContainer, localList2);
/*      */           }
/*      */ 
/*  251 */           if (this.defaultContainer != null) {
/*  252 */             clearParentCache(this.defaultContainer, localList2);
/*      */           }
/*      */ 
/*  256 */           localParent.impl_reapplyCSS();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void clearParentCache(StylesheetContainer paramStylesheetContainer, List<Reference<Key>> paramList) {
/*  263 */     if (paramStylesheetContainer.cacheMap.isEmpty()) return;
/*      */ 
/*  265 */     for (int i = paramList.size() - 1; 0 <= i; i--)
/*      */     {
/*  267 */       Reference localReference = (Reference)paramList.get(i);
/*  268 */       Key localKey = (Key)localReference.get();
/*  269 */       if (localKey != null)
/*      */       {
/*  271 */         Cache localCache = (Cache)paramStylesheetContainer.cacheMap.remove(localKey);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void expunge(Map<WeakReference<Scene>, StylesheetContainer> paramMap)
/*      */   {
/*  289 */     ArrayList localArrayList = null;
/*      */ 
/*  291 */     for (Iterator localIterator = paramMap.keySet().iterator(); localIterator.hasNext(); ) { localObject = (WeakReference)localIterator.next();
/*  292 */       if (((WeakReference)localObject).get() == null) {
/*  293 */         if (localArrayList == null) localArrayList = new ArrayList();
/*      */ 
/*  295 */         localArrayList.add(localObject);
/*      */       }
/*      */     }
/*      */     Object localObject;
/*  299 */     if (localArrayList != null)
/*  300 */       for (int i = localArrayList.size() - 1; 0 <= i; i--) {
/*  301 */         localObject = (StylesheetContainer)paramMap.remove(localArrayList.remove(i));
/*      */ 
/*  303 */         ((StylesheetContainer)localObject).destroy();
/*      */       }
/*      */   }
/*      */ 
/*      */   private void put(Map<WeakReference<Scene>, StylesheetContainer> paramMap, Scene paramScene, StylesheetContainer paramStylesheetContainer)
/*      */   {
/*  319 */     expunge(paramMap);
/*  320 */     paramMap.put(new WeakReference(paramScene), paramStylesheetContainer);
/*      */   }
/*      */ 
/*      */   private StylesheetContainer get(Map<WeakReference<Scene>, StylesheetContainer> paramMap, Scene paramScene)
/*      */   {
/*  335 */     expunge(paramMap);
/*      */ 
/*  337 */     for (Map.Entry localEntry : paramMap.entrySet()) {
/*  338 */       if (paramScene.equals(((WeakReference)localEntry.getKey()).get())) {
/*  339 */         return (StylesheetContainer)localEntry.getValue();
/*      */       }
/*      */     }
/*  342 */     return null;
/*      */   }
/*      */ 
/*      */   private StylesheetContainer remove(Map<WeakReference<Scene>, StylesheetContainer> paramMap, Scene paramScene)
/*      */   {
/*  357 */     expunge(paramMap);
/*      */ 
/*  360 */     Object localObject1 = null;
/*  361 */     for (Object localObject2 = paramMap.keySet().iterator(); ((Iterator)localObject2).hasNext(); ) { WeakReference localWeakReference = (WeakReference)((Iterator)localObject2).next();
/*  362 */       if (paramScene.equals(localWeakReference.get())) {
/*  363 */         localObject1 = localWeakReference;
/*  364 */         break;
/*      */       }
/*      */     }
/*      */ 
/*  368 */     localObject2 = null;
/*      */ 
/*  370 */     if (localObject1 != null) {
/*  371 */       localObject2 = (StylesheetContainer)paramMap.remove(localObject1);
/*      */ 
/*  377 */       localObject1.clear();
/*      */     }
/*      */ 
/*  380 */     return localObject2;
/*      */   }
/*      */ 
/*      */   private static URL getURL(String paramString)
/*      */   {
/*      */     try
/*      */     {
/*  395 */       return new URL(paramString);
/*      */     }
/*      */     catch (MalformedURLException localMalformedURLException)
/*      */     {
/*  399 */       ClassLoader localClassLoader = Thread.currentThread().getContextClassLoader();
/*  400 */       if (localClassLoader != null) {
/*  401 */         String str = (paramString != null) && (paramString.startsWith("/")) ? paramString.substring(1) : paramString;
/*      */ 
/*  403 */         return localClassLoader.getResource(str);
/*      */       }
/*      */     }
/*  405 */     return null;
/*      */   }
/*      */ 
/*      */   private Stylesheet loadStylesheet(final String paramString)
/*      */   {
/*      */     try {
/*  411 */       return loadStylesheetUnPrivileged(paramString);
/*      */     } catch (AccessControlException localAccessControlException) {
/*  413 */       if (logger().isLoggable(800)) {
/*  414 */         logger().info("Could not load the stylesheet, trying with FilePermissions : " + paramString);
/*      */       }
/*      */ 
/*  427 */       if ((paramString.length() < 7) && (paramString.indexOf("!/") < paramString.length() - 7)) {
/*  428 */         return null;
/*      */       }
/*      */ 
/*      */       try
/*      */       {
/*  437 */         URI localURI1 = new URI(paramString);
/*      */ 
/*  442 */         if ("jar".equals(localURI1.getScheme()))
/*      */         {
/*  447 */           URI localURI2 = (URI)AccessController.doPrivileged(new PrivilegedExceptionAction() {
/*      */             public URI run() throws URISyntaxException, PrivilegedActionException {
/*  449 */               return StyleManager.class.getProtectionDomain().getCodeSource().getLocation().toURI();
/*      */             }
/*      */           });
/*  453 */           final String str1 = localURI2.getSchemeSpecificPart();
/*  454 */           String str2 = localURI1.getSchemeSpecificPart();
/*  455 */           String str3 = str2.substring(str2.indexOf(47), str2.indexOf("!/"));
/*      */ 
/*  460 */           if (str1.equals(str3.toString()))
/*      */           {
/*  465 */             String str4 = paramString.substring(paramString.indexOf("!/") + 2);
/*      */ 
/*  469 */             if ((paramString.endsWith(".css")) || (paramString.endsWith(".bss")))
/*      */             {
/*  473 */               FilePermission localFilePermission = new FilePermission(str1, "read");
/*      */ 
/*  475 */               PermissionCollection localPermissionCollection = localFilePermission.newPermissionCollection();
/*  476 */               localPermissionCollection.add(localFilePermission);
/*  477 */               AccessControlContext localAccessControlContext = new AccessControlContext(new ProtectionDomain[] { new ProtectionDomain(null, localPermissionCollection) });
/*      */ 
/*  485 */               JarFile localJarFile = null;
/*      */               try {
/*  487 */                 localJarFile = (JarFile)AccessController.doPrivileged(new PrivilegedExceptionAction() {
/*      */                   public JarFile run() throws FileNotFoundException, IOException {
/*  489 */                     return new JarFile(str1);
/*      */                   }
/*      */                 }
/*      */                 , localAccessControlContext);
/*      */               }
/*      */               catch (PrivilegedActionException localPrivilegedActionException2)
/*      */               {
/*  498 */                 return null;
/*      */               }
/*  500 */               if (localJarFile != null)
/*      */               {
/*  504 */                 JarEntry localJarEntry = localJarFile.getJarEntry(str4);
/*  505 */                 if (localJarEntry != null)
/*      */                 {
/*  509 */                   return (Stylesheet)AccessController.doPrivileged(new PrivilegedAction()
/*      */                   {
/*      */                     public Stylesheet run() {
/*  512 */                       return StyleManager.this.loadStylesheetUnPrivileged(paramString);
/*      */                     }
/*      */                   }
/*      */                   , localAccessControlContext);
/*      */                 }
/*      */ 
/*      */               }
/*      */ 
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  524 */         return null;
/*      */       }
/*      */       catch (URISyntaxException localURISyntaxException)
/*      */       {
/*  532 */         return null; } catch (PrivilegedActionException localPrivilegedActionException1) {
/*      */       }
/*      */     }
/*  535 */     return null;
/*      */   }
/*      */ 
/*      */   private Stylesheet loadStylesheetUnPrivileged(final String paramString)
/*      */   {
/*  543 */     Boolean localBoolean = (Boolean)AccessController.doPrivileged(new PrivilegedAction()
/*      */     {
/*      */       public Boolean run() {
/*  546 */         String str = System.getProperty("binary.css");
/*      */ 
/*  550 */         return Boolean.valueOf((!paramString.endsWith(".bss")) && (str != null) ? false : !Boolean.valueOf(str).booleanValue() ? true : Boolean.FALSE.booleanValue());
/*      */       }
/*      */ 
/*      */     });
/*      */     try
/*      */     {
/*  556 */       String str = localBoolean.booleanValue() ? ".css" : ".bss";
/*      */ 
/*  558 */       localObject = (paramString.endsWith(".css")) || (paramString.endsWith(".bss")) ? paramString.substring(0, paramString.length() - 4) : paramString;
/*      */ 
/*  562 */       URL localURL = getURL((String)localObject + str);
/*  563 */       if (localURL == null) if ((localBoolean = Boolean.valueOf(!localBoolean.booleanValue())).booleanValue())
/*      */         {
/*  567 */           localURL = getURL((String)localObject + ".css");
/*      */         }
/*      */ 
/*  570 */       Stylesheet localStylesheet = null;
/*  571 */       if ((localURL != null) && (!localBoolean.booleanValue())) {
/*  572 */         localStylesheet = Stylesheet.loadBinary(localURL);
/*      */ 
/*  574 */         if (localStylesheet == null) if ((localBoolean = Boolean.valueOf(!localBoolean.booleanValue())).booleanValue())
/*      */           {
/*  578 */             localURL = getURL((String)localObject + ".css");
/*      */           }
/*      */ 
/*      */ 
/*      */       }
/*      */ 
/*  584 */       if ((localURL != null) && (localBoolean.booleanValue())) {
/*  585 */         localStylesheet = CSSParser.getInstance().parse(localURL);
/*      */       }
/*      */ 
/*  588 */       if (localStylesheet == null) {
/*  589 */         if (this.errors != null) {
/*  590 */           CssError localCssError = new CssError("Resource \"" + paramString + "\" not found.");
/*      */ 
/*  594 */           this.errors.add(localCssError);
/*      */         }
/*  596 */         if (logger().isLoggable(900)) {
/*  597 */           logger().warning(String.format("Resource \"%s\" not found.", new Object[] { paramString }));
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  602 */       return localStylesheet;
/*      */     }
/*      */     catch (FileNotFoundException localFileNotFoundException) {
/*  605 */       if (this.errors != null) {
/*  606 */         localObject = new CssError("Stylesheet \"" + paramString + "\" not found.");
/*      */ 
/*  610 */         this.errors.add(localObject);
/*      */       }
/*  612 */       if (logger().isLoggable(800))
/*  613 */         logger().info("Could not find stylesheet: " + paramString);
/*      */     }
/*      */     catch (IOException localIOException)
/*      */     {
/*      */       Object localObject;
/*  616 */       if (this.errors != null) {
/*  617 */         localObject = new CssError("Could not load stylesheet: " + paramString);
/*      */ 
/*  621 */         this.errors.add(localObject);
/*      */       }
/*  623 */       if (logger().isLoggable(800)) {
/*  624 */         logger().info("Could not load stylesheet: " + paramString);
/*      */       }
/*      */     }
/*  627 */     return null;
/*      */   }
/*      */ 
/*      */   public void addUserAgentStylesheet(String paramString)
/*      */   {
/*  644 */     addUserAgentStylesheet(null, paramString);
/*      */   }
/*      */ 
/*      */   public void addUserAgentStylesheet(Scene paramScene, String paramString)
/*      */   {
/*  657 */     if ((paramString == null) || (paramString.trim().isEmpty())) return;
/*      */ 
/*  659 */     if (!this.userAgentStylesheetMap.containsKey(paramString))
/*      */     {
/*  662 */       CssError.setCurrentScene(paramScene);
/*      */ 
/*  664 */       Stylesheet localStylesheet = loadStylesheet(paramString);
/*  665 */       localStylesheet.setOrigin(Stylesheet.Origin.USER_AGENT);
/*  666 */       this.userAgentStylesheetMap.put(paramString, localStylesheet);
/*      */ 
/*  668 */       if (localStylesheet != null) {
/*  669 */         userAgentStylesheetsChanged();
/*      */       }
/*      */ 
/*  673 */       CssError.setCurrentScene(null);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setDefaultUserAgentStylesheet(String paramString)
/*      */   {
/*  684 */     setDefaultUserAgentStylesheet(null, paramString);
/*      */   }
/*      */ 
/*      */   public void setDefaultUserAgentStylesheet(Scene paramScene, String paramString)
/*      */   {
/*  695 */     if ((paramString == null) || (paramString.trim().isEmpty())) {
/*  696 */       throw new IllegalArgumentException("null arg fname");
/*      */     }
/*      */ 
/*  699 */     CssError.setCurrentScene(paramScene);
/*      */ 
/*  701 */     Stylesheet localStylesheet = loadStylesheet(paramString);
/*  702 */     if (localStylesheet != null) {
/*  703 */       localStylesheet.setOrigin(Stylesheet.Origin.USER_AGENT);
/*  704 */       setDefaultUserAgentStylesheet(localStylesheet);
/*      */     }
/*      */ 
/*  708 */     CssError.setCurrentScene(null);
/*      */   }
/*      */ 
/*      */   public void setDefaultUserAgentStylesheet(Stylesheet paramStylesheet)
/*      */   {
/*  717 */     this.defaultUserAgentStylesheet = paramStylesheet;
/*  718 */     if (this.defaultUserAgentStylesheet != null) {
/*  719 */       this.defaultUserAgentStylesheet.setOrigin(Stylesheet.Origin.USER_AGENT);
/*      */     }
/*  721 */     userAgentStylesheetsChanged();
/*      */   }
/*      */ 
/*      */   private void userAgentStylesheetsChanged() {
/*  725 */     if (this.defaultContainer != null)
/*      */     {
/*  732 */       this.defaultContainer.destroy();
/*  733 */       this.defaultContainer = null;
/*      */     }
/*      */     Object localObject;
/*  735 */     if (this.containerMap != null) {
/*  736 */       localIterator = this.containerMap.values().iterator();
/*  737 */       while (localIterator.hasNext()) {
/*  738 */         localObject = (StylesheetContainer)localIterator.next();
/*  739 */         ((StylesheetContainer)localObject).destroy();
/*      */       }
/*  741 */       this.containerMap.clear();
/*      */     }
/*      */ 
/*  744 */     for (Iterator localIterator = Window.impl_getWindows(); localIterator.hasNext(); ) {
/*  745 */       localObject = ((Window)localIterator.next()).getScene();
/*  746 */       if (localObject != null) {
/*  747 */         updateStylesheets((Scene)localObject);
/*  748 */         ((Scene)localObject).getRoot().impl_reapplyCSS();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void reloadStylesheets(Scene paramScene)
/*      */   {
/*  766 */     this.authorStylesheetMap.clear();
/*  767 */     updateStylesheets(paramScene);
/*  768 */     paramScene.getRoot().impl_reapplyCSS();
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public void replaceStylesheet(Scene paramScene, Stylesheet paramStylesheet)
/*      */   {
/*  779 */     StylesheetContainer localStylesheetContainer = this.containerMap != null ? get(this.containerMap, paramScene) : null;
/*      */ 
/*  782 */     if (localStylesheetContainer != null) {
/*  783 */       int i = localStylesheetContainer.stylesheets.indexOf(paramStylesheet);
/*  784 */       if (i > -1)
/*  785 */         localStylesheetContainer.stylesheets.set(i, paramStylesheet);
/*      */       else {
/*  787 */         localStylesheetContainer.stylesheets.add(paramStylesheet);
/*      */       }
/*  789 */       localStylesheetContainer.clearCaches();
/*      */     } else {
/*  791 */       if (this.containerMap == null) this.containerMap = new HashMap();
/*      */ 
/*  794 */       localStylesheetContainer = new StylesheetContainer(null, null);
/*  795 */       localStylesheetContainer.stylesheets.add(paramStylesheet);
/*  796 */       put(this.containerMap, paramScene, localStylesheetContainer);
/*      */     }
/*      */ 
/*  799 */     paramScene.getRoot().impl_reapplyCSS();
/*      */   }
/*      */ 
/*      */   public void updateStylesheets(Scene paramScene)
/*      */   {
/*  823 */     if (paramScene.getWindow() == null) return;
/*      */ 
/*  835 */     if (this.containerMap != null)
/*      */     {
/*  837 */       localObject = remove(this.containerMap, paramScene);
/*      */ 
/*  840 */       if (localObject != null) {
/*  841 */         ((StylesheetContainer)localObject).destroy();
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  846 */     if (paramScene.getStylesheets().size() == 0) return;
/*      */ 
/*  849 */     CssError.setCurrentScene(paramScene);
/*      */ 
/*  852 */     Object localObject = new ArrayList();
/*  853 */     for (int i = 0; i < paramScene.getStylesheets().size(); i++) {
/*  854 */       String str = (String)paramScene.getStylesheets().get(i);
/*  855 */       str = str.trim();
/*      */       try
/*      */       {
/*  858 */         Stylesheet localStylesheet = loadStylesheet(str);
/*  859 */         addAuthorStylesheet(str, localStylesheet);
/*  860 */         if (localStylesheet != null) ((Collection)localObject).add(localStylesheet);
/*      */ 
/*      */       }
/*      */       catch (Exception localException)
/*      */       {
/*  866 */         System.err.printf("Cannot add stylesheet. %s\n", new Object[] { localException.getLocalizedMessage() });
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  871 */     CssError.setCurrentScene(paramScene);
/*      */ 
/*  875 */     if (this.containerMap == null) this.containerMap = new HashMap();
/*      */ 
/*  878 */     StylesheetContainer localStylesheetContainer = new StylesheetContainer((Collection)localObject, null);
/*  879 */     put(this.containerMap, paramScene, localStylesheetContainer);
/*      */   }
/*      */ 
/*      */   public long getPseudoclassMask(String paramString)
/*      */   {
/*  885 */     Long localLong = (Long)this.pseudoclassMasks.get(paramString);
/*  886 */     if (localLong == null) {
/*  887 */       int i = this.pseudoclassMasks.size();
/*  888 */       localLong = Long.valueOf(1L << i);
/*  889 */       this.pseudoclassMasks.put(paramString, localLong);
/*      */     }
/*  891 */     return localLong.longValue();
/*      */   }
/*      */ 
/*      */   long getPseudoclassMask(List<String> paramList) {
/*  895 */     long l1 = 0L;
/*      */ 
/*  897 */     int i = paramList != null ? paramList.size() : -1;
/*  898 */     for (int j = 0; j < i; j++) {
/*  899 */       long l2 = getPseudoclassMask((String)paramList.get(j));
/*  900 */       l1 |= l2;
/*      */     }
/*  902 */     return l1;
/*      */   }
/*      */ 
/*      */   public List<String> getPseudoclassStrings(long paramLong) {
/*  906 */     if (paramLong == 0L) return Collections.EMPTY_LIST;
/*      */ 
/*  908 */     HashMap localHashMap = new HashMap();
/*  909 */     for (Object localObject = this.pseudoclassMasks.entrySet().iterator(); ((Iterator)localObject).hasNext(); ) { Map.Entry localEntry = (Map.Entry)((Iterator)localObject).next();
/*  910 */       localHashMap.put(localEntry.getValue(), localEntry.getKey());
/*      */     }
/*  912 */     localObject = new ArrayList();
/*  913 */     for (long l1 = 0L; l1 < 64L; l1 += 1L) {
/*  914 */       long l2 = 1L << (int)l1 & paramLong;
/*  915 */       if (l2 != 0L) {
/*  916 */         String str = (String)localHashMap.get(Long.valueOf(l2));
/*  917 */         if (str != null) ((List)localObject).add(str);
/*      */       }
/*      */     }
/*  920 */     return localObject;
/*      */   }
/*      */ 
/*      */   public StyleHelper getStyleHelper(Node paramNode)
/*      */   {
/*  929 */     assert ((paramNode != null) && (paramNode.getScene() != null));
/*      */ 
/*  933 */     int i = this.containerMap == null ? 1 : 0;
/*      */ 
/*  940 */     StylesheetContainer localStylesheetContainer = null;
/*  941 */     if (i == 0) {
/*  942 */       Window localWindow = null;
/*      */       Object localObject;
/*  943 */       if ((paramNode != null) && (paramNode.getScene() != null)) {
/*  944 */         localWindow = paramNode.getScene().getWindow();
/*  945 */         while ((localWindow instanceof PopupWindow)) {
/*  946 */           localObject = (PopupWindow)localWindow;
/*  947 */           localWindow = ((PopupWindow)localObject).getOwnerWindow();
/*      */         }
/*      */       }
/*      */ 
/*  951 */       if (localWindow == null)
/*  952 */         localObject = paramNode.getScene();
/*      */       else {
/*  954 */         localObject = localWindow.getScene();
/*      */       }
/*      */ 
/*  957 */       if (localObject != null)
/*      */       {
/*  960 */         localStylesheetContainer = get(this.containerMap, (Scene)localObject);
/*      */       }
/*      */ 
/*  963 */       i = localStylesheetContainer == null ? 1 : 0;
/*      */     }
/*      */ 
/*  966 */     if (i != 0) {
/*  967 */       if (this.defaultContainer == null) this.defaultContainer = new StylesheetContainer(null, null);
/*  968 */       return this.defaultContainer.getStyleHelper(paramNode);
/*      */     }
/*      */ 
/*  974 */     return localStylesheetContainer.getStyleHelper(paramNode);
/*      */   }
/*      */ 
/*      */   public ObservableList<CssError> errorsProperty()
/*      */   {
/* 1401 */     if (this.errors == null) {
/* 1402 */       this.errors = FXCollections.observableArrayList();
/*      */     }
/* 1404 */     return this.errors;
/*      */   }
/*      */ 
/*      */   public ObservableList<CssError> getErrors()
/*      */   {
/* 1416 */     return this.errors;
/*      */   }
/*      */ 
/*      */   private static class Key
/*      */   {
/*      */     String className;
/*      */     String id;
/*      */     List<String> styleClass;
/*      */     int[] indices;
/*      */ 
/*      */     public boolean equals(Object paramObject)
/*      */     {
/* 1604 */       if (this == paramObject) return true;
/* 1605 */       if ((paramObject instanceof Key)) {
/* 1606 */         Key localKey = (Key)paramObject;
/* 1607 */         boolean bool = (this.className.equals(localKey.className)) && (((this.indices == null) && (localKey.indices == null)) || ((this.indices != null) && (localKey.indices != null) && (((this.id == null) && (localKey.id == null)) || ((this.id != null) && (this.id.equals(localKey.id)) && (((this.styleClass == null) && (localKey.styleClass == null)) || ((this.styleClass != null) && (this.styleClass.containsAll(localKey.styleClass))))))));
/*      */ 
/* 1619 */         if ((bool) && (this.indices != null)) {
/* 1620 */           int i = Math.min(this.indices.length, localKey.indices.length);
/* 1621 */           for (int j = 0; (bool) && (j < i); j++) {
/* 1622 */             bool = this.indices[j] == localKey.indices[j];
/*      */           }
/* 1624 */           if (bool)
/*      */           {
/* 1626 */             for (j = i; (bool) && (j < this.indices.length); j++) {
/* 1627 */               bool = this.indices[j] == -1;
/*      */             }
/* 1629 */             for (j = i; (bool) && (j < localKey.indices.length); j++) {
/* 1630 */               bool = localKey.indices[j] == -1;
/*      */             }
/*      */           }
/*      */         }
/* 1634 */         return bool;
/*      */       }
/* 1636 */       return false;
/*      */     }
/*      */ 
/*      */     public int hashCode()
/*      */     {
/* 1641 */       int i = this.className.hashCode();
/* 1642 */       i = 31 * (i + ((this.id == null) || (this.id.isEmpty()) ? 1231 : this.id.hashCode()));
/* 1643 */       i = 31 * (i + ((this.styleClass == null) || (this.styleClass.isEmpty()) ? 1237 : this.styleClass.hashCode()));
/* 1644 */       if (this.indices != null) i = 31 * (i + Arrays.hashCode(this.indices));
/* 1645 */       return i;
/*      */     }
/*      */ 
/*      */     public String toString()
/*      */     {
/* 1650 */       return "Key [" + this.className + ", " + String.valueOf(this.id) + ", " + String.valueOf(this.styleClass) + "]";
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class Cache
/*      */   {
/*      */     private final List<Rule> rules;
/*      */     private final long pseudoclassStateMask;
/*      */     private final boolean impactsChildren;
/*      */     private final Map<Long, StyleManager.StyleMap> cache;
/*      */ 
/*      */     Cache(List<Rule> paramList, long paramLong, boolean paramBoolean)
/*      */     {
/* 1447 */       this.rules = paramList;
/* 1448 */       this.pseudoclassStateMask = paramLong;
/* 1449 */       this.impactsChildren = paramBoolean;
/* 1450 */       this.cache = new HashMap();
/*      */     }
/*      */ 
/*      */     private StyleManager.StyleMap getStyleMap(StyleManager.StylesheetContainer paramStylesheetContainer, Node paramNode)
/*      */     {
/* 1457 */       if (!this.impactsChildren)
/*      */       {
/* 1463 */         String str1 = paramNode.getStyle();
/* 1464 */         int i = (str1 != null) && (!str1.isEmpty()) ? 1 : 0;
/*      */ 
/* 1466 */         if ((this.rules.isEmpty()) && (this.pseudoclassStateMask == 0L) && (i == 0)) {
/* 1467 */           int j = 0;
/*      */ 
/* 1469 */           List localList = paramNode.impl_getStyleableProperties();
/* 1470 */           k = localList != null ? localList.size() : 0;
/* 1471 */           for (m = 0; m < k; m++) {
/* 1472 */             if (((StyleableProperty)localList.get(m)).isInherits()) {
/* 1473 */               j = 1;
/* 1474 */               break;
/*      */             }
/*      */           }
/*      */ 
/* 1478 */           if (j == 0) return null;
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1485 */       long l1 = 0L;
/* 1486 */       long l2 = 1L;
/* 1487 */       int k = 0; for (int m = this.rules.size(); k < m; k++) {
/* 1488 */         localObject2 = (Rule)this.rules.get(k);
/* 1489 */         if (((Rule)localObject2).applies(paramNode)) {
/* 1490 */           l1 |= l2;
/*      */         }
/* 1492 */         l2 <<= 1;
/*      */       }
/*      */ 
/* 1495 */       Long localLong = Long.valueOf(l1);
/* 1496 */       if (this.cache.containsKey(localLong)) {
/* 1497 */         localObject1 = (StyleManager.StyleMap)this.cache.get(localLong);
/* 1498 */         return localObject1;
/*      */       }
/*      */ 
/* 1503 */       Object localObject1 = getStyles(paramNode);
/* 1504 */       Object localObject2 = new HashMap();
/*      */ 
/* 1506 */       int n = localObject1 != null ? ((List)localObject1).size() : 0;
/* 1507 */       for (int i1 = 0; i1 < n; i1++) {
/* 1508 */         CascadingStyle localCascadingStyle = (CascadingStyle)((List)localObject1).get(i1);
/* 1509 */         String str2 = localCascadingStyle.getProperty();
/*      */ 
/* 1511 */         Object localObject3 = (List)((Map)localObject2).get(str2);
/* 1512 */         if (localObject3 == null) {
/* 1513 */           localObject3 = new ArrayList(5);
/* 1514 */           ((Map)localObject2).put(str2, localObject3);
/*      */         }
/* 1516 */         ((List)localObject3).add(localCascadingStyle);
/*      */       }
/*      */ 
/* 1519 */       StyleManager.StyleMap localStyleMap = new StyleManager.StyleMap(StyleManager.StylesheetContainer.access$2400(paramStylesheetContainer), (Map)localObject2, null);
/* 1520 */       this.cache.put(localLong, localStyleMap);
/* 1521 */       return localStyleMap;
/*      */     }
/*      */ 
/*      */     private List<CascadingStyle> getStyles(Node paramNode)
/*      */     {
/* 1539 */       if ((this.rules == null) || (this.rules.isEmpty())) return null;
/*      */ 
/* 1541 */       ArrayList localArrayList = new ArrayList(this.rules.size());
/*      */ 
/* 1543 */       int i = 0;
/*      */ 
/* 1546 */       int j = 0; for (int k = this.rules.size(); j < k; j++) {
/* 1547 */         Rule localRule = (Rule)this.rules.get(j);
/* 1548 */         List localList = localRule.matches(paramNode);
/* 1549 */         int m = 0; for (int n = localList.size(); m < n; m++) {
/* 1550 */           Match localMatch = (Match)localList.get(m);
/* 1551 */           if (localMatch != null) {
/* 1552 */             int i1 = 0; for (int i2 = localRule.declarations.size(); i1 < i2; i1++) {
/* 1553 */               Declaration localDeclaration = (Declaration)localRule.declarations.get(i1);
/*      */ 
/* 1555 */               CascadingStyle localCascadingStyle = new CascadingStyle(new Style(localMatch.selector, localDeclaration), localMatch.pseudoclasses, localMatch.specificity, i++);
/*      */ 
/* 1565 */               localArrayList.add(localCascadingStyle);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/* 1571 */       Collections.sort(localArrayList);
/* 1572 */       return localArrayList;
/*      */     }
/*      */   }
/*      */ 
/*      */   static class StyleMap
/*      */   {
/*      */     final long uniqueId;
/*      */     final Map<String, List<CascadingStyle>> map;
/*      */ 
/*      */     private StyleMap(long paramLong, Map<String, List<CascadingStyle>> paramMap)
/*      */     {
/* 1428 */       this.uniqueId = paramLong;
/* 1429 */       this.map = paramMap;
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class StylesheetContainer
/*      */   {
/*      */     private final List<Stylesheet> stylesheets;
/*      */     private final Map<StyleManager.Key, StyleManager.Cache> cacheMap;
/*      */     private final StyleManager.Key key;
/*      */     private final Map<StyleHelper.StyleCacheKey, StyleHelper.StyleCacheBucket> styleCache;
/*      */     private int smapCount;
/*      */ 
/*      */     private StylesheetContainer(Collection<Stylesheet> paramCollection)
/*      */     {
/* 1057 */       this.cacheMap = new HashMap();
/* 1058 */       this.key = new StyleManager.Key(null);
/*      */ 
/* 1060 */       this.stylesheets = new ArrayList();
/*      */ 
/* 1062 */       if (StyleManager.getInstance().defaultUserAgentStylesheet != null) {
/* 1063 */         this.stylesheets.add(StyleManager.getInstance().defaultUserAgentStylesheet);
/*      */       }
/*      */ 
/* 1066 */       if ((StyleManager.getInstance().userAgentStylesheetMap != null) && (!StyleManager.getInstance().userAgentStylesheetMap.isEmpty()))
/*      */       {
/* 1068 */         this.stylesheets.addAll(StyleManager.getInstance().userAgentStylesheetMap.values());
/*      */       }
/*      */ 
/* 1071 */       if (paramCollection != null) this.stylesheets.addAll(paramCollection);
/*      */ 
/* 1073 */       this.styleCache = new HashMap();
/* 1074 */       this.smapCount = 0;
/*      */     }
/*      */ 
/*      */     private void destroy() {
/* 1078 */       this.stylesheets.clear();
/* 1079 */       clearCaches();
/*      */     }
/*      */ 
/*      */     private void clearCaches() {
/* 1083 */       this.styleCache.clear();
/* 1084 */       this.cacheMap.clear();
/* 1085 */       this.smapCount = 0;
/*      */     }
/*      */ 
/*      */     private long nextSmapId() {
/* 1089 */       return ++this.smapCount;
/*      */     }
/*      */ 
/*      */     private static int[] getIndicesOfParentsWithStylesheets(Parent paramParent, int paramInt)
/*      */     {
/* 1099 */       if (paramParent == null) return new int[paramInt];
/* 1100 */       int[] arrayOfInt = getIndicesOfParentsWithStylesheets(paramParent.getParent(), ++paramInt);
/*      */ 
/* 1103 */       if (!paramParent.getStylesheets().isEmpty())
/* 1104 */         arrayOfInt[(arrayOfInt.length - paramInt)] = paramParent.hashCode();
/*      */       else {
/* 1106 */         arrayOfInt[(arrayOfInt.length - paramInt)] = -1;
/*      */       }
/* 1108 */       return arrayOfInt;
/*      */     }
/*      */ 
/*      */     private List<StyleManager.ParentStylesheetContainer> gatherParentStylesheets(Parent paramParent)
/*      */     {
/* 1119 */       if (paramParent == null) return null;
/*      */ 
/* 1121 */       List localList = paramParent.impl_getAllParentStylesheets();
/*      */ 
/* 1123 */       if ((localList == null) || (localList.isEmpty())) return null;
/*      */ 
/* 1125 */       ArrayList localArrayList = new ArrayList();
/*      */ 
/* 1128 */       CssError.setCurrentScene(paramParent.getScene());
/*      */ 
/* 1130 */       int i = 0; for (int j = localList.size(); i < j; i++) {
/* 1131 */         String str = (String)localList.get(i);
/* 1132 */         StyleManager.ParentStylesheetContainer localParentStylesheetContainer = null;
/* 1133 */         if (StyleManager.parentStylesheetMap.containsKey(str)) {
/* 1134 */           localParentStylesheetContainer = (StyleManager.ParentStylesheetContainer)StyleManager.parentStylesheetMap.get(str);
/*      */ 
/* 1138 */           StyleManager.RefList.access$1800(StyleManager.ParentStylesheetContainer.access$400(localParentStylesheetContainer), paramParent);
/*      */         } else {
/* 1140 */           Stylesheet localStylesheet = StyleManager.getInstance().loadStylesheet(str);
/*      */ 
/* 1146 */           localParentStylesheetContainer = new StyleManager.ParentStylesheetContainer(str, localStylesheet, null);
/*      */ 
/* 1151 */           StyleManager.RefList.access$1800(StyleManager.ParentStylesheetContainer.access$400(localParentStylesheetContainer), paramParent);
/* 1152 */           StyleManager.parentStylesheetMap.put(str, localParentStylesheetContainer);
/*      */         }
/* 1154 */         if (localParentStylesheetContainer != null) localArrayList.add(localParentStylesheetContainer);
/*      */       }
/*      */ 
/* 1157 */       CssError.setCurrentScene(null);
/*      */ 
/* 1159 */       return localArrayList;
/*      */     }
/*      */ 
/*      */     private StyleHelper getStyleHelper(Node paramNode)
/*      */     {
/* 1170 */       String str1 = paramNode.getClass().getName();
/* 1171 */       String str2 = paramNode.getId();
/* 1172 */       ObservableList localObservableList = paramNode.getStyleClass();
/*      */ 
/* 1174 */       int[] arrayOfInt = getIndicesOfParentsWithStylesheets((paramNode instanceof Parent) ? (Parent)paramNode : paramNode.getParent(), 0);
/*      */ 
/* 1184 */       int i = 0;
/* 1185 */       for (int j = 0; j < arrayOfInt.length; j++)
/*      */       {
/* 1187 */         if ((i = arrayOfInt[j] != -1 ? 1 : 0) != 0)
/*      */           break;
/*      */       }
/* 1190 */       this.key.className = str1;
/* 1191 */       this.key.id = str2;
/* 1192 */       this.key.styleClass = localObservableList;
/* 1193 */       this.key.indices = (i != 0 ? arrayOfInt : null);
/*      */ 
/* 1195 */       StyleManager.Cache localCache = (StyleManager.Cache)this.cacheMap.get(this.key);
/*      */ 
/* 1200 */       this.key.styleClass = null;
/*      */ 
/* 1204 */       if (localCache == null)
/*      */       {
/* 1206 */         localObject1 = new ArrayList();
/*      */ 
/* 1214 */         long l = 0L;
/*      */ 
/* 1218 */         boolean bool1 = paramNode instanceof Parent;
/*      */ 
/* 1223 */         boolean bool2 = false;
/*      */ 
/* 1225 */         Object localObject2 = i != 0 ? gatherParentStylesheets((paramNode instanceof Parent) ? (Parent)paramNode : paramNode.getParent()) : null;
/*      */ 
/* 1233 */         Object localObject3 = null;
/*      */         Object localObject4;
/* 1235 */         if ((localObject2 == null) || (localObject2.isEmpty())) {
/* 1236 */           localObject3 = this.stylesheets;
/*      */         }
/*      */         else
/*      */         {
/* 1247 */           localObject3 = new ArrayList(localObject2.size());
/* 1248 */           k = 0; for (m = localObject2.size(); k < m; k++) {
/* 1249 */             localObject4 = (StyleManager.ParentStylesheetContainer)localObject2.get(k);
/* 1250 */             ((List)localObject3).add(StyleManager.ParentStylesheetContainer.access$2100((StyleManager.ParentStylesheetContainer)localObject4));
/*      */           }
/* 1252 */           ((List)localObject3).addAll(0, this.stylesheets);
/*      */         }
/*      */ 
/* 1255 */         int k = 0; for (int m = ((List)localObject3).size(); k < m; k++) {
/* 1256 */           localObject4 = (Stylesheet)((List)localObject3).get(k);
/*      */ 
/* 1258 */           Object localObject5 = localObject4 != null ? ((Stylesheet)localObject4).getRules() : null;
/* 1259 */           if ((localObject5 != null) && (!localObject5.isEmpty()))
/*      */           {
/* 1261 */             int i2 = 0; for (int i3 = localObject5.size(); i2 < i3; i2++) {
/* 1262 */               Rule localRule = (Rule)localObject5.get(i2);
/* 1263 */               boolean bool3 = localRule.mightApply(str1, str2, localObservableList);
/* 1264 */               if (bool3) {
/* 1265 */                 ((List)localObject1).add(localRule);
/*      */               }
/*      */ 
/* 1293 */               if ((bool3) || (bool1))
/*      */               {
/* 1298 */                 int i4 = localRule.selectors != null ? localRule.selectors.size() : 0;
/* 1299 */                 for (int i5 = 0; i5 < i4; i5++) {
/* 1300 */                   Selector localSelector = (Selector)localRule.selectors.get(i5);
/*      */                   Object localObject6;
/* 1301 */                   if ((localSelector instanceof CompoundSelector))
/*      */                   {
/* 1303 */                     localObject6 = (CompoundSelector)localSelector;
/* 1304 */                     List localList = ((CompoundSelector)localObject6).getSelectors();
/*      */ 
/* 1309 */                     if (bool3) {
/* 1310 */                       SimpleSelector localSimpleSelector1 = (SimpleSelector)localList.get(localList.size() - 1);
/*      */ 
/* 1312 */                       l |= StyleManager.getInstance().getPseudoclassMask(localSimpleSelector1.getPseudoclasses());
/*      */                     }
/* 1322 */                     else if (bool1)
/*      */                     {
/* 1327 */                       int i6 = 0; for (int i7 = localList.size() - 1; i6 < i7; i6++) {
/* 1328 */                         SimpleSelector localSimpleSelector2 = (SimpleSelector)localList.get(i6);
/* 1329 */                         if (localSimpleSelector2.mightApply(str1, str2, localObservableList)) {
/* 1330 */                           l |= StyleManager.getInstance().getPseudoclassMask(localSimpleSelector2.getPseudoclasses());
/*      */ 
/* 1332 */                           bool2 = true;
/*      */                         }
/*      */                       }
/*      */ 
/*      */                     }
/*      */ 
/*      */                   }
/* 1339 */                   else if (bool3) {
/* 1340 */                     localObject6 = (SimpleSelector)localSelector;
/* 1341 */                     l |= StyleManager.getInstance().getPseudoclassMask(((SimpleSelector)localObject6).getPseudoclasses());
/*      */                   }
/*      */                 }
/*      */               }
/*      */ 
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 1352 */         StyleManager.Key localKey = new StyleManager.Key(null);
/* 1353 */         localKey.className = str1;
/* 1354 */         localKey.id = str2;
/*      */ 
/* 1358 */         m = localObservableList.size();
/* 1359 */         localKey.styleClass = new ArrayList(m);
/* 1360 */         for (int n = 0; n < m; n++) localKey.styleClass.add(localObservableList.get(n));
/* 1361 */         localKey.indices = (i != 0 ? arrayOfInt : null);
/*      */ 
/* 1363 */         localCache = new StyleManager.Cache((List)localObject1, l, bool2);
/* 1364 */         this.cacheMap.put(localKey, localCache);
/*      */ 
/* 1369 */         n = localObject2 != null ? localObject2.size() : 0;
/* 1370 */         for (int i1 = 0; i1 < n; i1++) {
/* 1371 */           StyleManager.ParentStylesheetContainer localParentStylesheetContainer = (StyleManager.ParentStylesheetContainer)localObject2.get(i1);
/* 1372 */           StyleManager.RefList.access$1800(StyleManager.ParentStylesheetContainer.access$600(localParentStylesheetContainer), localKey);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1379 */       Object localObject1 = localCache.getStyleMap(this, paramNode);
/* 1380 */       if (localObject1 == null) return null;
/*      */ 
/* 1382 */       StyleHelper localStyleHelper = StyleHelper.create(paramNode, ((StyleManager.StyleMap)localObject1).map, this.styleCache, localCache.pseudoclassStateMask, ((StyleManager.StyleMap)localObject1).uniqueId);
/*      */ 
/* 1390 */       return localStyleHelper;
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class RefList<K>
/*      */   {
/*  149 */     private final List<Reference<K>> list = new ArrayList();
/*      */ 
/*      */     private void add(K paramK)
/*      */     {
/*  153 */       for (int i = this.list.size() - 1; 0 <= i; i--) {
/*  154 */         Reference localReference = (Reference)this.list.get(i);
/*  155 */         Object localObject = localReference.get();
/*  156 */         if (localObject == null)
/*      */         {
/*  158 */           this.list.remove(i);
/*      */         }
/*  161 */         else if (localObject == paramK) return;
/*      */ 
/*      */       }
/*      */ 
/*  165 */       this.list.add(new WeakReference(paramK));
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class ParentStylesheetContainer
/*      */   {
/*      */     private final String fname;
/*      */     private final Stylesheet stylesheet;
/*      */     private final StyleManager.RefList<Parent> parents;
/*      */     private final StyleManager.RefList<StyleManager.Key> keys;
/*      */ 
/*      */     private ParentStylesheetContainer(String paramString, Stylesheet paramStylesheet)
/*      */     {
/*  140 */       this.fname = paramString;
/*  141 */       this.stylesheet = paramStylesheet;
/*  142 */       this.parents = new StyleManager.RefList(null);
/*  143 */       this.keys = new StyleManager.RefList(null);
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class Holder
/*      */   {
/*   75 */     private static StyleManager INSTANCE = new StyleManager(null);
/*   76 */     private static PlatformLogger LOGGER = Logging.getCSSLogger();
/*      */   }
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.css.StyleManager
 * JD-Core Version:    0.6.2
 */