/*      */ package javafx.fxml;
/*      */ 
/*      */ import com.sun.javafx.beans.IDProperty;
/*      */ import com.sun.javafx.fxml.BeanAdapter;
/*      */ import com.sun.javafx.fxml.LoadListener;
/*      */ import com.sun.javafx.fxml.ObservableListChangeEvent;
/*      */ import com.sun.javafx.fxml.ObservableMapChangeEvent;
/*      */ import com.sun.javafx.fxml.PropertyChangeEvent;
/*      */ import com.sun.javafx.fxml.PropertyNotFoundException;
/*      */ import com.sun.javafx.fxml.expression.Expression;
/*      */ import com.sun.javafx.fxml.expression.VariableExpression;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.PrintStream;
/*      */ import java.io.Reader;
/*      */ import java.lang.reflect.Array;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.lang.reflect.Field;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.lang.reflect.Method;
/*      */ import java.lang.reflect.ParameterizedType;
/*      */ import java.lang.reflect.Type;
/*      */ import java.net.URL;
/*      */ import java.nio.charset.Charset;
/*      */ import java.util.AbstractMap;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.Map.Entry;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.Set;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ import javafx.beans.DefaultProperty;
/*      */ import javafx.beans.property.Property;
/*      */ import javafx.beans.property.ReadOnlyObjectProperty;
/*      */ import javafx.beans.value.ChangeListener;
/*      */ import javafx.beans.value.ObservableValue;
/*      */ import javafx.collections.FXCollections;
/*      */ import javafx.collections.ListChangeListener;
/*      */ import javafx.collections.ListChangeListener.Change;
/*      */ import javafx.collections.MapChangeListener;
/*      */ import javafx.collections.MapChangeListener.Change;
/*      */ import javafx.collections.ObservableList;
/*      */ import javafx.collections.ObservableMap;
/*      */ import javafx.event.Event;
/*      */ import javafx.event.EventHandler;
/*      */ import javafx.event.EventType;
/*      */ import javafx.util.Builder;
/*      */ import javafx.util.BuilderFactory;
/*      */ import javafx.util.Callback;
/*      */ import javax.script.Bindings;
/*      */ import javax.script.ScriptEngine;
/*      */ import javax.script.ScriptEngineManager;
/*      */ import javax.script.ScriptException;
/*      */ import javax.script.SimpleBindings;
/*      */ import javax.xml.stream.Location;
/*      */ import javax.xml.stream.XMLInputFactory;
/*      */ import javax.xml.stream.XMLStreamException;
/*      */ import javax.xml.stream.XMLStreamReader;
/*      */ import javax.xml.stream.util.StreamReaderDelegate;
/*      */ import sun.reflect.misc.ConstructorUtil;
/*      */ import sun.reflect.misc.FieldUtil;
/*      */ import sun.reflect.misc.MethodUtil;
/*      */ import sun.reflect.misc.ReflectUtil;
/*      */ 
/*      */ public class FXMLLoader
/*      */ {
/* 1588 */   private ObservableMap<String, Object> namespace = FXCollections.observableHashMap();
/*      */   private URL location;
/*      */   private ResourceBundle resources;
/*      */   private BuilderFactory builderFactory;
/*      */   private Callback<Class<?>, Object> controllerFactory;
/*      */   private Charset charset;
/*      */   private LinkedList<FXMLLoader> loaders;
/* 1598 */   private ClassLoader classLoader = defaultClassLoader;
/* 1599 */   private boolean staticLoad = false;
/* 1600 */   private LoadListener loadListener = null;
/*      */ 
/* 1602 */   private XMLStreamReader xmlStreamReader = null;
/* 1603 */   private Element current = null;
/* 1604 */   private Object root = null;
/*      */ 
/* 1606 */   private Object controller = null;
/* 1607 */   private ScriptEngine scriptEngine = null;
/*      */   private URL exceptionLocation;
/*      */   private int exceptionLineNumber;
/* 1612 */   private LinkedList<String> packages = new LinkedList();
/* 1613 */   private HashMap<String, Class<?>> classes = new HashMap();
/*      */ 
/* 1615 */   private ScriptEngineManager scriptEngineManager = null;
/*      */ 
/* 1617 */   private HashMap<String, Field> controllerFields = null;
/* 1618 */   private HashMap<String, Method> controllerMethods = null;
/*      */   private static ClassLoader defaultClassLoader;
/* 1622 */   private static boolean enableBidirectionalBinding = false;
/*      */ 
/* 1624 */   private static final Pattern extraneousWhitespacePattern = Pattern.compile("\\s+");
/*      */   public static final String DEFAULT_CHARSET_NAME = "UTF-8";
/*      */   public static final String LANGUAGE_PROCESSING_INSTRUCTION = "language";
/*      */   public static final String IMPORT_PROCESSING_INSTRUCTION = "import";
/*      */   public static final String FX_NAMESPACE_PREFIX = "fx";
/*      */   public static final String FX_CONTROLLER_ATTRIBUTE = "controller";
/*      */   public static final String FX_ID_ATTRIBUTE = "id";
/*      */   public static final String FX_VALUE_ATTRIBUTE = "value";
/*      */   public static final String FX_CONSTANT_ATTRIBUTE = "constant";
/*      */   public static final String FX_FACTORY_ATTRIBUTE = "factory";
/*      */   public static final String INCLUDE_TAG = "include";
/*      */   public static final String INCLUDE_SOURCE_ATTRIBUTE = "source";
/*      */   public static final String INCLUDE_RESOURCES_ATTRIBUTE = "resources";
/*      */   public static final String INCLUDE_CHARSET_ATTRIBUTE = "charset";
/*      */   public static final String SCRIPT_TAG = "script";
/*      */   public static final String SCRIPT_SOURCE_ATTRIBUTE = "source";
/*      */   public static final String SCRIPT_CHARSET_ATTRIBUTE = "charset";
/*      */   public static final String DEFINE_TAG = "define";
/*      */   public static final String REFERENCE_TAG = "reference";
/*      */   public static final String REFERENCE_SOURCE_ATTRIBUTE = "source";
/*      */   public static final String ROOT_TAG = "root";
/*      */   public static final String ROOT_TYPE_ATTRIBUTE = "type";
/*      */   public static final String COPY_TAG = "copy";
/*      */   public static final String COPY_SOURCE_ATTRIBUTE = "source";
/*      */   public static final String EVENT_HANDLER_PREFIX = "on";
/*      */   public static final String EVENT_KEY = "event";
/*      */   public static final String CHANGE_EVENT_HANDLER_SUFFIX = "Change";
/*      */   public static final String NULL_KEYWORD = "null";
/*      */   public static final String ESCAPE_PREFIX = "\\";
/*      */   public static final String RELATIVE_PATH_PREFIX = "@";
/*      */   public static final String RESOURCE_KEY_PREFIX = "%";
/*      */   public static final String EXPRESSION_PREFIX = "$";
/*      */   public static final String BINDING_EXPRESSION_PREFIX = "{";
/*      */   public static final String BINDING_EXPRESSION_SUFFIX = "}";
/*      */   public static final String BI_DIRECTIONAL_BINDING_PREFIX = "#{";
/*      */   public static final String BI_DIRECTIONAL_BINDING_SUFFIX = "}";
/*      */   public static final String ARRAY_COMPONENT_DELIMITER = ",";
/*      */   public static final String LOCATION_KEY = "location";
/*      */   public static final String RESOURCES_KEY = "resources";
/*      */   public static final String CONTROLLER_METHOD_PREFIX = "#";
/*      */   public static final String CONTROLLER_KEYWORD = "controller";
/*      */   public static final String CONTROLLER_SUFFIX = "Controller";
/*      */   public static final String INITIALIZE_METHOD_NAME = "initialize";
/*      */ 
/*      */   public FXMLLoader()
/*      */   {
/* 1706 */     this((URL)null);
/*      */   }
/*      */ 
/*      */   public FXMLLoader(URL paramURL)
/*      */   {
/* 1715 */     this(paramURL, null);
/*      */   }
/*      */ 
/*      */   public FXMLLoader(URL paramURL, ResourceBundle paramResourceBundle)
/*      */   {
/* 1725 */     this(paramURL, paramResourceBundle, new JavaFXBuilderFactory());
/*      */   }
/*      */ 
/*      */   public FXMLLoader(URL paramURL, ResourceBundle paramResourceBundle, BuilderFactory paramBuilderFactory)
/*      */   {
/* 1736 */     this(paramURL, paramResourceBundle, paramBuilderFactory, null);
/*      */   }
/*      */ 
/*      */   public FXMLLoader(URL paramURL, ResourceBundle paramResourceBundle, BuilderFactory paramBuilderFactory, Callback<Class<?>, Object> paramCallback)
/*      */   {
/* 1749 */     this(paramURL, paramResourceBundle, paramBuilderFactory, paramCallback, Charset.forName("UTF-8"));
/*      */   }
/*      */ 
/*      */   public FXMLLoader(Charset paramCharset)
/*      */   {
/* 1758 */     this(null, null, null, null, paramCharset);
/*      */   }
/*      */ 
/*      */   public FXMLLoader(URL paramURL, ResourceBundle paramResourceBundle, BuilderFactory paramBuilderFactory, Callback<Class<?>, Object> paramCallback, Charset paramCharset)
/*      */   {
/* 1772 */     this(paramURL, paramResourceBundle, paramBuilderFactory, paramCallback, paramCharset, new LinkedList());
/*      */   }
/*      */ 
/*      */   public FXMLLoader(URL paramURL, ResourceBundle paramResourceBundle, BuilderFactory paramBuilderFactory, Callback<Class<?>, Object> paramCallback, Charset paramCharset, LinkedList<FXMLLoader> paramLinkedList)
/*      */   {
/* 1789 */     setLocation(paramURL);
/* 1790 */     setResources(paramResourceBundle);
/* 1791 */     setBuilderFactory(paramBuilderFactory);
/* 1792 */     setControllerFactory(paramCallback);
/* 1793 */     setCharset(paramCharset);
/*      */ 
/* 1795 */     this.loaders = paramLinkedList;
/*      */   }
/*      */ 
/*      */   public ObservableMap<String, Object> getNamespace()
/*      */   {
/* 1802 */     return this.namespace;
/*      */   }
/*      */ 
/*      */   public <T> T getRoot()
/*      */   {
/* 1810 */     return this.root;
/*      */   }
/*      */ 
/*      */   public void setRoot(Object paramObject)
/*      */   {
/* 1823 */     this.root = paramObject;
/*      */   }
/*      */ 
/*      */   public <T> T getController()
/*      */   {
/* 1837 */     return this.controller;
/*      */   }
/*      */ 
/*      */   public void setController(Object paramObject)
/*      */   {
/* 1847 */     this.controller = paramObject;
/*      */ 
/* 1849 */     if (paramObject == null)
/* 1850 */       this.namespace.remove("controller");
/*      */     else {
/* 1852 */       this.namespace.put("controller", paramObject);
/*      */     }
/*      */ 
/* 1855 */     this.controllerFields = null;
/* 1856 */     this.controllerMethods = null;
/*      */   }
/*      */ 
/*      */   public URL getLocation()
/*      */   {
/* 1863 */     return this.location;
/*      */   }
/*      */ 
/*      */   public void setLocation(URL paramURL)
/*      */   {
/* 1872 */     this.location = paramURL;
/*      */   }
/*      */ 
/*      */   public ResourceBundle getResources()
/*      */   {
/* 1879 */     return this.resources;
/*      */   }
/*      */ 
/*      */   public void setResources(ResourceBundle paramResourceBundle)
/*      */   {
/* 1888 */     this.resources = paramResourceBundle;
/*      */   }
/*      */ 
/*      */   public BuilderFactory getBuilderFactory()
/*      */   {
/* 1895 */     return this.builderFactory;
/*      */   }
/*      */ 
/*      */   public void setBuilderFactory(BuilderFactory paramBuilderFactory)
/*      */   {
/* 1904 */     this.builderFactory = paramBuilderFactory;
/*      */   }
/*      */ 
/*      */   public Callback<Class<?>, Object> getControllerFactory()
/*      */   {
/* 1911 */     return this.controllerFactory;
/*      */   }
/*      */ 
/*      */   public void setControllerFactory(Callback<Class<?>, Object> paramCallback)
/*      */   {
/* 1920 */     this.controllerFactory = paramCallback;
/*      */   }
/*      */ 
/*      */   public Charset getCharset()
/*      */   {
/* 1927 */     return this.charset;
/*      */   }
/*      */ 
/*      */   public void setCharset(Charset paramCharset)
/*      */   {
/* 1936 */     if (paramCharset == null) {
/* 1937 */       throw new NullPointerException("charset is null.");
/*      */     }
/*      */ 
/* 1940 */     this.charset = paramCharset;
/*      */   }
/*      */ 
/*      */   public ClassLoader getClassLoader()
/*      */   {
/* 1947 */     return this.classLoader;
/*      */   }
/*      */ 
/*      */   public void setClassLoader(ClassLoader paramClassLoader)
/*      */   {
/* 1956 */     if (paramClassLoader == null) {
/* 1957 */       throw new IllegalArgumentException();
/*      */     }
/*      */ 
/* 1960 */     this.classLoader = paramClassLoader;
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public boolean isStaticLoad()
/*      */   {
/* 1971 */     return this.staticLoad;
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public void setStaticLoad(boolean paramBoolean)
/*      */   {
/* 1984 */     this.staticLoad = paramBoolean;
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public LoadListener getLoadListener()
/*      */   {
/* 1995 */     return this.loadListener;
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public void setLoadListener(LoadListener paramLoadListener)
/*      */   {
/* 2008 */     this.loadListener = paramLoadListener;
/*      */   }
/*      */ 
/*      */   public Object load()
/*      */     throws IOException
/*      */   {
/* 2020 */     if (this.location == null) {
/* 2021 */       throw new IllegalStateException("Location is not set.");
/* 2024 */     }
/*      */ InputStream localInputStream = null;
/*      */     Object localObject1;
/*      */     try {
/* 2027 */       localInputStream = this.location.openStream();
/* 2028 */       localObject1 = load(localInputStream);
/*      */     } catch (IOException localIOException) {
/* 2030 */       logException(localIOException);
/* 2031 */       throw localIOException;
/*      */     } catch (RuntimeException localRuntimeException) {
/* 2033 */       logException(localRuntimeException);
/* 2034 */       throw localRuntimeException;
/*      */     } finally {
/* 2036 */       if (localInputStream != null) {
/* 2037 */         localInputStream.close();
/*      */       }
/*      */     }
/*      */ 
/* 2041 */     return localObject1;
/*      */   }
/*      */ 
/*      */   public Object load(InputStream paramInputStream)
/*      */     throws IOException
/*      */   {
/* 2055 */     if (paramInputStream == null) {
/* 2056 */       throw new NullPointerException("inputStream is null.");
/* 2060 */     }
/*      */ this.namespace.put("location", this.location);
/* 2061 */     this.namespace.put("resources", this.resources);
/*      */ 
/* 2064 */     this.packages.clear();
/* 2065 */     this.classes.clear();
/*      */ 
/* 2068 */     this.scriptEngine = null;
/*      */ 
/* 2071 */     this.exceptionLocation = null;
/* 2072 */     this.exceptionLineNumber = -1;
/*      */     Object localObject;
/*      */     try { XMLInputFactory localXMLInputFactory = XMLInputFactory.newInstance();
/* 2077 */       localXMLInputFactory.setProperty("javax.xml.stream.isCoalescing", Boolean.valueOf(true));
/*      */ 
/* 2081 */       localObject = new InputStreamReader(paramInputStream, this.charset);
/* 2082 */       this.xmlStreamReader = new StreamReaderDelegate(localXMLInputFactory.createXMLStreamReader((Reader)localObject))
/*      */       {
/*      */         public String getPrefix() {
/* 2085 */           String str = super.getPrefix();
/*      */ 
/* 2087 */           if ((str != null) && (str.length() == 0))
/*      */           {
/* 2089 */             str = null;
/*      */           }
/*      */ 
/* 2092 */           return str;
/*      */         }
/*      */ 
/*      */         public String getAttributePrefix(int paramAnonymousInt)
/*      */         {
/* 2097 */           String str = super.getAttributePrefix(paramAnonymousInt);
/*      */ 
/* 2099 */           if ((str != null) && (str.length() == 0))
/*      */           {
/* 2101 */             str = null;
/*      */           }
/*      */ 
/* 2104 */           return str;
/*      */         }
/*      */       };
/*      */     } catch (XMLStreamException localXMLStreamException1) {
/* 2108 */       throw new LoadException(localXMLStreamException1);
/*      */     }
/*      */ 
/* 2112 */     this.loaders.push(this);
/*      */     try
/*      */     {
/* 2116 */       while (this.xmlStreamReader.hasNext()) {
/* 2117 */         int i = this.xmlStreamReader.next();
/*      */ 
/* 2119 */         switch (i) {
/*      */         case 3:
/* 2121 */           processProcessingInstruction();
/* 2122 */           break;
/*      */         case 5:
/* 2126 */           processComment();
/* 2127 */           break;
/*      */         case 1:
/* 2131 */           processStartElement();
/* 2132 */           break;
/*      */         case 2:
/* 2136 */           processEndElement();
/* 2137 */           break;
/*      */         case 4:
/* 2141 */           processCharacters();
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (XMLStreamException localXMLStreamException2)
/*      */     {
/* 2147 */       throw new LoadException(localXMLStreamException2);
/*      */     }
/*      */ 
/* 2150 */     if (this.controller != null) {
/* 2151 */       if ((this.controller instanceof Initializable)) {
/* 2152 */         ((Initializable)this.controller).initialize(this.location, this.resources);
/*      */       }
/*      */       else {
/* 2155 */         HashMap localHashMap = getControllerFields();
/*      */ 
/* 2157 */         localObject = (Field)localHashMap.get("location");
/* 2158 */         if (localObject != null) {
/*      */           try {
/* 2160 */             ((Field)localObject).set(this.controller, this.location);
/*      */           }
/*      */           catch (IllegalAccessException localIllegalAccessException1)
/*      */           {
/*      */           }
/*      */         }
/*      */ 
/* 2167 */         Field localField = (Field)localHashMap.get("resources");
/* 2168 */         if (localField != null) {
/*      */           try {
/* 2170 */             localField.set(this.controller, this.resources);
/*      */           }
/*      */           catch (IllegalAccessException localIllegalAccessException2)
/*      */           {
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 2178 */         Method localMethod = (Method)getControllerMethods().get("initialize");
/*      */ 
/* 2180 */         if (localMethod != null) {
/*      */           try {
/* 2182 */             MethodUtil.invoke(localMethod, this.controller, new Object[0]);
/*      */           } catch (IllegalAccessException localIllegalAccessException3) {
/* 2184 */             throw new LoadException(localIllegalAccessException3);
/*      */           } catch (InvocationTargetException localInvocationTargetException) {
/* 2186 */             throw new LoadException(localInvocationTargetException);
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2193 */     this.loaders.pop();
/*      */ 
/* 2196 */     this.xmlStreamReader = null;
/*      */ 
/* 2198 */     return this.root;
/*      */   }
/*      */ 
/*      */   private void logException(Exception paramException) {
/* 2202 */     String str = paramException.getMessage();
/* 2203 */     if (str == null) {
/* 2204 */       str = paramException.getClass().getName();
/*      */     }
/*      */ 
/* 2207 */     StringBuilder localStringBuilder = new StringBuilder(str);
/* 2208 */     localStringBuilder.append("\n");
/*      */ 
/* 2210 */     for (Object localObject = this.loaders.iterator(); ((Iterator)localObject).hasNext(); ) { FXMLLoader localFXMLLoader = (FXMLLoader)((Iterator)localObject).next();
/* 2211 */       localStringBuilder.append(localFXMLLoader.location.getPath());
/*      */ 
/* 2213 */       if (localFXMLLoader.current != null) {
/* 2214 */         localStringBuilder.append(":");
/* 2215 */         localStringBuilder.append(localFXMLLoader.current.lineNumber);
/*      */       }
/*      */ 
/* 2218 */       localStringBuilder.append("\n");
/*      */     }
/*      */ 
/* 2221 */     localObject = paramException.getStackTrace();
/* 2222 */     if (localObject != null) {
/* 2223 */       for (int i = 0; i < localObject.length; i++) {
/* 2224 */         localStringBuilder.append("  at ");
/* 2225 */         localStringBuilder.append(localObject[i].toString());
/* 2226 */         localStringBuilder.append("\n");
/*      */       }
/*      */     }
/*      */ 
/* 2230 */     System.err.println(localStringBuilder.toString());
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public int getLineNumber()
/*      */   {
/* 2240 */     return this.xmlStreamReader.getLocation().getLineNumber();
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public ParseTraceElement[] getParseTrace()
/*      */   {
/* 2250 */     ParseTraceElement[] arrayOfParseTraceElement = new ParseTraceElement[this.loaders.size()];
/*      */ 
/* 2252 */     int i = 0;
/* 2253 */     for (FXMLLoader localFXMLLoader : this.loaders) {
/* 2254 */       arrayOfParseTraceElement[(i++)] = new ParseTraceElement(localFXMLLoader.location, localFXMLLoader.current != null ? localFXMLLoader.current.lineNumber : -1);
/*      */     }
/*      */ 
/* 2258 */     return arrayOfParseTraceElement;
/*      */   }
/*      */ 
/*      */   private void processProcessingInstruction() throws LoadException {
/* 2262 */     String str = this.xmlStreamReader.getPITarget().trim();
/*      */ 
/* 2264 */     if (str.equals("language"))
/* 2265 */       processLanguage();
/* 2266 */     else if (str.equals("import"))
/* 2267 */       processImport();
/*      */   }
/*      */ 
/*      */   private void processLanguage() throws LoadException
/*      */   {
/* 2272 */     if (this.scriptEngine != null) {
/* 2273 */       throw new LoadException("Page language already set.");
/*      */     }
/*      */ 
/* 2276 */     String str = this.xmlStreamReader.getPIData();
/*      */ 
/* 2278 */     if (this.loadListener != null) {
/* 2279 */       this.loadListener.readLanguageProcessingInstruction(str);
/*      */     }
/*      */ 
/* 2282 */     if (!this.staticLoad) {
/* 2283 */       ScriptEngineManager localScriptEngineManager = getScriptEngineManager();
/* 2284 */       this.scriptEngine = localScriptEngineManager.getEngineByName(str);
/* 2285 */       this.scriptEngine.setBindings(localScriptEngineManager.getBindings(), 100);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void processImport() throws LoadException {
/* 2290 */     String str = this.xmlStreamReader.getPIData().trim();
/*      */ 
/* 2292 */     if (this.loadListener != null) {
/* 2293 */       this.loadListener.readImportProcessingInstruction(str);
/*      */     }
/*      */ 
/* 2296 */     if (str.endsWith(".*"))
/* 2297 */       importPackage(str.substring(0, str.length() - 2));
/*      */     else
/* 2299 */       importClass(str);
/*      */   }
/*      */ 
/*      */   private void processComment() throws LoadException
/*      */   {
/* 2304 */     if (this.loadListener != null)
/* 2305 */       this.loadListener.readComment(this.xmlStreamReader.getText());
/*      */   }
/*      */ 
/*      */   private void processStartElement()
/*      */     throws IOException
/*      */   {
/* 2311 */     createElement();
/*      */ 
/* 2314 */     this.current.processStartElement();
/*      */ 
/* 2317 */     if (this.root == null)
/* 2318 */       this.root = this.current.value;
/*      */   }
/*      */ 
/*      */   private void createElement() throws IOException
/*      */   {
/* 2323 */     String str1 = this.xmlStreamReader.getPrefix();
/* 2324 */     String str2 = this.xmlStreamReader.getLocalName();
/*      */ 
/* 2326 */     if (str1 == null) {
/* 2327 */       int i = str2.lastIndexOf(46);
/*      */       Object localObject;
/* 2329 */       if (Character.isLowerCase(str2.charAt(i + 1))) {
/* 2330 */         localObject = str2.substring(i + 1);
/*      */ 
/* 2332 */         if (i == -1)
/*      */         {
/* 2334 */           if (this.loadListener != null) {
/* 2335 */             this.loadListener.beginPropertyElement((String)localObject, null);
/*      */           }
/*      */ 
/* 2338 */           this.current = new PropertyElement((String)localObject, null);
/*      */         }
/*      */         else {
/* 2341 */           Class localClass = getType(str2.substring(0, i));
/*      */ 
/* 2343 */           if (localClass != null) {
/* 2344 */             if (this.loadListener != null) {
/* 2345 */               this.loadListener.beginPropertyElement((String)localObject, localClass);
/*      */             }
/*      */ 
/* 2348 */             this.current = new PropertyElement((String)localObject, localClass);
/* 2349 */           } else if (this.staticLoad)
/*      */           {
/* 2351 */             if (this.loadListener != null) {
/* 2352 */               this.loadListener.beginUnknownStaticPropertyElement(str2);
/*      */             }
/*      */ 
/* 2355 */             this.current = new UnknownStaticPropertyElement(str2);
/*      */           } else {
/* 2357 */             throw new LoadException(new StringBuilder().append(str2).append(" is not a valid property.").toString());
/*      */           }
/*      */         }
/*      */       } else {
/* 2361 */         if ((this.current == null) && (this.root != null)) {
/* 2362 */           throw new LoadException("Root value already specified.");
/*      */         }
/*      */ 
/* 2365 */         localObject = getType(str2);
/*      */ 
/* 2367 */         if (localObject != null) {
/* 2368 */           if (this.loadListener != null) {
/* 2369 */             this.loadListener.beginInstanceDeclarationElement((Class)localObject);
/*      */           }
/*      */ 
/* 2372 */           this.current = new InstanceDeclarationElement((Class)localObject);
/* 2373 */         } else if (this.staticLoad)
/*      */         {
/* 2375 */           if (this.loadListener != null) {
/* 2376 */             this.loadListener.beginUnknownTypeElement(str2);
/*      */           }
/*      */ 
/* 2379 */           this.current = new UnknownTypeElement(str2);
/*      */         } else {
/* 2381 */           throw new LoadException(new StringBuilder().append(str2).append(" is not a valid type.").toString());
/*      */         }
/*      */       }
/* 2384 */     } else if (str1.equals("fx")) {
/* 2385 */       if (str2.equals("include")) {
/* 2386 */         if (this.loadListener != null) {
/* 2387 */           this.loadListener.beginIncludeElement();
/*      */         }
/*      */ 
/* 2390 */         this.current = new IncludeElement(null);
/* 2391 */       } else if (str2.equals("reference")) {
/* 2392 */         if (this.loadListener != null) {
/* 2393 */           this.loadListener.beginReferenceElement();
/*      */         }
/*      */ 
/* 2396 */         this.current = new ReferenceElement(null);
/* 2397 */       } else if (str2.equals("copy")) {
/* 2398 */         if (this.loadListener != null) {
/* 2399 */           this.loadListener.beginCopyElement();
/*      */         }
/*      */ 
/* 2402 */         this.current = new CopyElement(null);
/* 2403 */       } else if (str2.equals("root")) {
/* 2404 */         if (this.loadListener != null) {
/* 2405 */           this.loadListener.beginRootElement();
/*      */         }
/*      */ 
/* 2408 */         this.current = new RootElement(null);
/* 2409 */       } else if (str2.equals("script")) {
/* 2410 */         if (this.loadListener != null) {
/* 2411 */           this.loadListener.beginScriptElement();
/*      */         }
/*      */ 
/* 2414 */         this.current = new ScriptElement(null);
/* 2415 */       } else if (str2.equals("define")) {
/* 2416 */         if (this.loadListener != null) {
/* 2417 */           this.loadListener.beginDefineElement();
/*      */         }
/*      */ 
/* 2420 */         this.current = new DefineElement(null);
/*      */       } else {
/* 2422 */         throw new LoadException(new StringBuilder().append(str1).append(":").append(str2).append(" is not a valid element.").toString());
/*      */       }
/*      */     } else {
/* 2425 */       throw new LoadException(new StringBuilder().append("Unexpected namespace prefix: ").append(str1).append(".").toString());
/*      */     }
/*      */   }
/*      */ 
/*      */   private void processEndElement() throws IOException {
/* 2430 */     this.current.processEndElement();
/*      */ 
/* 2432 */     if (this.loadListener != null) {
/* 2433 */       this.loadListener.endElement(this.current.value);
/*      */     }
/*      */ 
/* 2437 */     this.current = this.current.parent;
/*      */   }
/*      */ 
/*      */   private void processCharacters() throws IOException
/*      */   {
/* 2442 */     if (!this.xmlStreamReader.isWhiteSpace())
/* 2443 */       this.current.processCharacters();
/*      */   }
/*      */ 
/*      */   private void importPackage(String paramString) throws LoadException
/*      */   {
/* 2448 */     this.packages.add(paramString);
/*      */   }
/*      */ 
/*      */   private void importClass(String paramString) throws LoadException {
/*      */     try {
/* 2453 */       loadType(paramString, true);
/*      */     } catch (ClassNotFoundException localClassNotFoundException) {
/* 2455 */       throw new LoadException(localClassNotFoundException);
/*      */     }
/*      */   }
/*      */ 
/*      */   private Class<?> getType(String paramString) throws LoadException {
/* 2460 */     Class localClass = null;
/*      */ 
/* 2462 */     if (Character.isLowerCase(paramString.charAt(0)))
/*      */     {
/*      */       try {
/* 2465 */         localClass = loadType(paramString, false);
/*      */       }
/*      */       catch (ClassNotFoundException localClassNotFoundException1) {
/*      */       }
/*      */     }
/*      */     else {
/* 2471 */       localClass = (Class)this.classes.get(paramString);
/*      */ 
/* 2473 */       if (localClass == null)
/*      */       {
/* 2475 */         for (String str : this.packages) {
/*      */           try {
/* 2477 */             localClass = loadTypeForPackage(str, paramString);
/*      */           }
/*      */           catch (ClassNotFoundException localClassNotFoundException2)
/*      */           {
/*      */           }
/* 2482 */           if (localClass != null)
/*      */           {
/*      */             break;
/*      */           }
/*      */         }
/* 2487 */         if (localClass != null) {
/* 2488 */           this.classes.put(paramString, localClass);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 2493 */     return localClass;
/*      */   }
/*      */ 
/*      */   private Class<?> loadType(String paramString, boolean paramBoolean) throws ClassNotFoundException {
/* 2497 */     int i = paramString.indexOf(46);
/* 2498 */     int j = paramString.length();
/*      */ 
/* 2501 */     while ((i != -1) && (i < j) && (Character.isLowerCase(paramString.charAt(i + 1)))) {
/* 2502 */       i = paramString.indexOf(46, i + 1);
/*      */     }
/*      */ 
/* 2505 */     if ((i == -1) || (i == j)) {
/* 2506 */       throw new ClassNotFoundException();
/*      */     }
/*      */ 
/* 2509 */     String str1 = paramString.substring(0, i);
/* 2510 */     String str2 = paramString.substring(i + 1);
/*      */ 
/* 2512 */     Class localClass = loadTypeForPackage(str1, str2);
/*      */ 
/* 2514 */     if (paramBoolean) {
/* 2515 */       this.classes.put(str2, localClass);
/*      */     }
/*      */ 
/* 2518 */     return localClass;
/*      */   }
/*      */ 
/*      */   private Class<?> loadTypeForPackage(String paramString1, String paramString2) throws ClassNotFoundException
/*      */   {
/* 2523 */     return this.classLoader.loadClass(new StringBuilder().append(paramString1).append(".").append(paramString2.replace('.', '$')).toString());
/*      */   }
/*      */ 
/*      */   private ScriptEngineManager getScriptEngineManager() {
/* 2527 */     if (this.scriptEngineManager == null) {
/* 2528 */       this.scriptEngineManager = new ScriptEngineManager();
/* 2529 */       this.scriptEngineManager.setBindings(new SimpleBindings(this.namespace));
/*      */     }
/*      */ 
/* 2532 */     return this.scriptEngineManager;
/*      */   }
/*      */ 
/*      */   private HashMap<String, Field> getControllerFields() throws LoadException {
/* 2536 */     if (this.controllerFields == null) {
/* 2537 */       this.controllerFields = new HashMap();
/*      */ 
/* 2539 */       Class localClass1 = this.controller.getClass();
/* 2540 */       Class localClass2 = localClass1;
/*      */ 
/* 2542 */       while (localClass2 != Object.class) {
/* 2543 */         Field[] arrayOfField = FieldUtil.getDeclaredFields(localClass2);
/*      */ 
/* 2545 */         for (int i = 0; i < arrayOfField.length; i++) {
/* 2546 */           Field localField = arrayOfField[i];
/* 2547 */           int j = localField.getModifiers();
/*      */ 
/* 2550 */           if ((localClass2 == localClass1) || ((j & 0x2) == 0))
/*      */           {
/* 2553 */             int k = localField.getModifiers();
/*      */ 
/* 2555 */             if (((k & 0x1) == 0) && (localField.getAnnotation(FXML.class) != null)) {
/*      */               try
/*      */               {
/* 2558 */                 localField.setAccessible(true);
/*      */               } catch (SecurityException localSecurityException) {
/* 2560 */                 throw new LoadException(localSecurityException);
/*      */               }
/*      */             }
/*      */ 
/* 2564 */             this.controllerFields.put(localField.getName(), localField);
/*      */           }
/*      */         }
/*      */ 
/* 2568 */         localClass2 = localClass2.getSuperclass();
/*      */       }
/*      */     }
/*      */ 
/* 2572 */     return this.controllerFields;
/*      */   }
/*      */ 
/*      */   private HashMap<String, Method> getControllerMethods() throws LoadException {
/* 2576 */     if (this.controllerMethods == null) {
/* 2577 */       this.controllerMethods = new HashMap();
/*      */ 
/* 2579 */       Class localClass1 = this.controller.getClass();
/* 2580 */       Class localClass2 = localClass1;
/*      */ 
/* 2582 */       while (localClass2 != Object.class) {
/* 2583 */         ReflectUtil.checkPackageAccess(localClass2);
/* 2584 */         Method[] arrayOfMethod = localClass2.getDeclaredMethods();
/*      */ 
/* 2586 */         for (int i = 0; i < arrayOfMethod.length; i++) {
/* 2587 */           Method localMethod = arrayOfMethod[i];
/* 2588 */           int j = localMethod.getModifiers();
/*      */ 
/* 2591 */           if ((localClass2 == localClass1) || ((j & 0x2) == 0))
/*      */           {
/* 2594 */             int k = localMethod.getModifiers();
/*      */ 
/* 2596 */             if (((k & 0x1) == 0) && (localMethod.getAnnotation(FXML.class) != null)) {
/*      */               try
/*      */               {
/* 2599 */                 localMethod.setAccessible(true);
/*      */               } catch (SecurityException localSecurityException) {
/* 2601 */                 throw new LoadException(localSecurityException);
/*      */               }
/*      */ 
/*      */             }
/*      */ 
/* 2610 */             String str = localMethod.getName();
/* 2611 */             Class[] arrayOfClass = localMethod.getParameterTypes();
/*      */ 
/* 2613 */             if (str.equals("initialize")) {
/* 2614 */               if (arrayOfClass.length == 0)
/* 2615 */                 this.controllerMethods.put(localMethod.getName(), localMethod);
/*      */             }
/* 2617 */             else if (((arrayOfClass.length == 1) && (Event.class.isAssignableFrom(arrayOfClass[0]))) || ((arrayOfClass.length == 0) && (!this.controllerMethods.containsKey(str))))
/*      */             {
/* 2619 */               this.controllerMethods.put(localMethod.getName(), localMethod);
/*      */             }
/*      */           }
/*      */         }
/*      */ 
/* 2624 */         localClass2 = localClass2.getSuperclass();
/*      */       }
/*      */     }
/*      */ 
/* 2628 */     return this.controllerMethods;
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public static Class<?> loadType(String paramString1, String paramString2)
/*      */     throws ClassNotFoundException
/*      */   {
/* 2641 */     return loadType(new StringBuilder().append(paramString1).append(".").append(paramString2.replace('.', '$')).toString());
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public static Class<?> loadType(String paramString)
/*      */     throws ClassNotFoundException
/*      */   {
/* 2653 */     ReflectUtil.checkPackageAccess(paramString);
/* 2654 */     return Class.forName(paramString, true, defaultClassLoader);
/*      */   }
/*      */ 
/*      */   public static ClassLoader getDefaultClassLoader()
/*      */   {
/* 2661 */     return defaultClassLoader;
/*      */   }
/*      */ 
/*      */   public static void setDefaultClassLoader(ClassLoader paramClassLoader)
/*      */   {
/* 2671 */     if (paramClassLoader == null) {
/* 2672 */       throw new NullPointerException();
/*      */     }
/*      */ 
/* 2675 */     defaultClassLoader = paramClassLoader;
/*      */   }
/*      */ 
/*      */   public static <T> T load(URL paramURL)
/*      */     throws IOException
/*      */   {
/* 2685 */     return load(paramURL, null);
/*      */   }
/*      */ 
/*      */   public static <T> T load(URL paramURL, ResourceBundle paramResourceBundle)
/*      */     throws IOException
/*      */   {
/* 2696 */     return load(paramURL, paramResourceBundle, new JavaFXBuilderFactory());
/*      */   }
/*      */ 
/*      */   public static <T> T load(URL paramURL, ResourceBundle paramResourceBundle, BuilderFactory paramBuilderFactory)
/*      */     throws IOException
/*      */   {
/* 2709 */     return load(paramURL, paramResourceBundle, paramBuilderFactory, null);
/*      */   }
/*      */ 
/*      */   public static <T> T load(URL paramURL, ResourceBundle paramResourceBundle, BuilderFactory paramBuilderFactory, Callback<Class<?>, Object> paramCallback)
/*      */     throws IOException
/*      */   {
/* 2723 */     return load(paramURL, paramResourceBundle, paramBuilderFactory, paramCallback, Charset.forName("UTF-8"));
/*      */   }
/*      */ 
/*      */   public static <T> T load(URL paramURL, ResourceBundle paramResourceBundle, BuilderFactory paramBuilderFactory, Callback<Class<?>, Object> paramCallback, Charset paramCharset)
/*      */     throws IOException
/*      */   {
/* 2738 */     if (paramURL == null) {
/* 2739 */       throw new NullPointerException("Location is required.");
/*      */     }
/*      */ 
/* 2742 */     FXMLLoader localFXMLLoader = new FXMLLoader(paramURL, paramResourceBundle, paramBuilderFactory, paramCallback, paramCharset);
/*      */ 
/* 2744 */     return localFXMLLoader.load();
/*      */   }
/*      */ 
/*      */   static
/*      */   {
/* 1686 */     defaultClassLoader = Thread.currentThread().getContextClassLoader();
/*      */ 
/* 1688 */     if (defaultClassLoader == null) {
/* 1689 */       throw new NullPointerException();
/*      */     }
/*      */     try
/*      */     {
/* 1693 */       String str = System.getProperty(new StringBuilder().append(FXMLLoader.class.getName()).append(".enableBidirectionalBinding").toString());
/*      */ 
/* 1695 */       enableBidirectionalBinding = (str != null) && (Boolean.parseBoolean(str));
/*      */     }
/*      */     catch (SecurityException localSecurityException)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class ExpressionTargetMapping
/*      */     implements ChangeListener<Object>
/*      */   {
/*      */     public final Expression source;
/*      */     public final Object target;
/*      */     public final List<String> path;
/*      */ 
/*      */     public ExpressionTargetMapping(Expression paramExpression, Object paramObject, List<String> paramList)
/*      */     {
/* 1575 */       this.source = paramExpression;
/* 1576 */       this.target = paramObject;
/* 1577 */       this.path = paramList;
/*      */     }
/*      */ 
/*      */     public void changed(ObservableValue<? extends Object> paramObservableValue, Object paramObject1, Object paramObject2)
/*      */     {
/* 1582 */       if (this.source.isDefined())
/* 1583 */         Expression.set(this.target, this.path, this.source.getValue());
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class PropertyChangeAdapter
/*      */     implements ChangeListener<Object>
/*      */   {
/*      */     public final Object source;
/*      */     public final EventHandler<PropertyChangeEvent<?>> handler;
/*      */ 
/*      */     public PropertyChangeAdapter(Object paramObject, EventHandler<PropertyChangeEvent<?>> paramEventHandler)
/*      */     {
/* 1550 */       if (paramObject == null) {
/* 1551 */         throw new NullPointerException();
/*      */       }
/*      */ 
/* 1554 */       if (paramEventHandler == null) {
/* 1555 */         throw new NullPointerException();
/*      */       }
/*      */ 
/* 1558 */       this.source = paramObject;
/* 1559 */       this.handler = paramEventHandler;
/*      */     }
/*      */ 
/*      */     public void changed(ObservableValue<? extends Object> paramObservableValue, Object paramObject1, Object paramObject2)
/*      */     {
/* 1564 */       this.handler.handle(new PropertyChangeEvent(this.source, paramObject1));
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class ObservableMapChangeAdapter
/*      */     implements MapChangeListener<Object, Object>
/*      */   {
/*      */     public final ObservableMap<Object, Object> source;
/*      */     public final EventHandler<ObservableMapChangeEvent<?, ?>> handler;
/*      */ 
/*      */     public ObservableMapChangeAdapter(ObservableMap<Object, Object> paramObservableMap, EventHandler<ObservableMapChangeEvent<?, ?>> paramEventHandler)
/*      */     {
/* 1522 */       this.source = paramObservableMap;
/* 1523 */       this.handler = paramEventHandler;
/*      */     }
/*      */ 
/*      */     public void onChanged(MapChangeListener.Change<? extends Object, ? extends Object> paramChange)
/*      */     {
/*      */       EventType localEventType;
/* 1529 */       if ((paramChange.wasAdded()) && (paramChange.wasRemoved()))
/* 1530 */         localEventType = ObservableMapChangeEvent.UPDATE;
/* 1531 */       else if (paramChange.wasAdded())
/* 1532 */         localEventType = ObservableMapChangeEvent.ADD;
/* 1533 */       else if (paramChange.wasRemoved())
/* 1534 */         localEventType = ObservableMapChangeEvent.REMOVE;
/*      */       else {
/* 1536 */         throw new UnsupportedOperationException();
/*      */       }
/*      */ 
/* 1539 */       this.handler.handle(new ObservableMapChangeEvent(this.source, localEventType, paramChange.getKey(), paramChange.getValueRemoved()));
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class ObservableListChangeAdapter
/*      */     implements ListChangeListener<Object>
/*      */   {
/*      */     public final ObservableList<Object> source;
/*      */     public final EventHandler<ObservableListChangeEvent<?>> handler;
/*      */ 
/*      */     public ObservableListChangeAdapter(ObservableList<Object> paramObservableList, EventHandler<ObservableListChangeEvent<?>> paramEventHandler)
/*      */     {
/* 1484 */       this.source = paramObservableList;
/* 1485 */       this.handler = paramEventHandler;
/*      */     }
/*      */ 
/*      */     public void onChanged(ListChangeListener.Change<? extends Object> paramChange)
/*      */     {
/* 1491 */       while (paramChange.next())
/*      */       {
/* 1493 */         List localList = paramChange.getRemoved();
/*      */         EventType localEventType;
/* 1495 */         if (paramChange.wasPermutated()) {
/* 1496 */           localEventType = ObservableListChangeEvent.UPDATE;
/* 1497 */           localList = null;
/* 1498 */         } else if ((paramChange.wasAdded()) && (paramChange.wasRemoved())) {
/* 1499 */           localEventType = ObservableListChangeEvent.UPDATE;
/* 1500 */         } else if (paramChange.wasAdded()) {
/* 1501 */           localEventType = ObservableListChangeEvent.ADD;
/* 1502 */         } else if (paramChange.wasRemoved()) {
/* 1503 */           localEventType = ObservableListChangeEvent.REMOVE;
/*      */         } else {
/* 1505 */           throw new UnsupportedOperationException();
/*      */         }
/*      */ 
/* 1508 */         this.handler.handle(new ObservableListChangeEvent(this.source, localEventType, paramChange.getFrom(), paramChange.getTo(), localList));
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class ScriptEventHandler
/*      */     implements EventHandler<Event>
/*      */   {
/*      */     public final String script;
/*      */     public final ScriptEngine scriptEngine;
/*      */ 
/*      */     public ScriptEventHandler(String paramString, ScriptEngine paramScriptEngine)
/*      */     {
/* 1453 */       this.script = paramString;
/* 1454 */       this.scriptEngine = paramScriptEngine;
/*      */     }
/*      */ 
/*      */     public void handle(Event paramEvent)
/*      */     {
/* 1460 */       Bindings localBindings1 = this.scriptEngine.getBindings(100);
/* 1461 */       Bindings localBindings2 = this.scriptEngine.createBindings();
/* 1462 */       localBindings2.put("event", paramEvent);
/* 1463 */       this.scriptEngine.setBindings(localBindings2, 100);
/*      */       try
/*      */       {
/* 1467 */         this.scriptEngine.eval(this.script);
/*      */       } catch (ScriptException localScriptException) {
/* 1469 */         throw new RuntimeException(localScriptException);
/*      */       }
/*      */ 
/* 1473 */       this.scriptEngine.setBindings(localBindings1, 100);
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class ControllerMethodEventHandler
/*      */     implements EventHandler<Event>
/*      */   {
/*      */     public final Object controller;
/*      */     public final Method method;
/*      */     public final boolean typed;
/*      */ 
/*      */     public ControllerMethodEventHandler(Object paramObject, Method paramMethod)
/*      */     {
/* 1426 */       this.controller = paramObject;
/* 1427 */       this.method = paramMethod;
/* 1428 */       this.typed = (paramMethod.getParameterTypes().length == 1);
/*      */     }
/*      */ 
/*      */     public void handle(Event paramEvent)
/*      */     {
/*      */       try {
/* 1434 */         if (this.typed)
/* 1435 */           MethodUtil.invoke(this.method, this.controller, new Object[] { paramEvent });
/*      */         else
/* 1437 */           MethodUtil.invoke(this.method, this.controller, new Object[0]);
/*      */       }
/*      */       catch (InvocationTargetException localInvocationTargetException) {
/* 1440 */         throw new RuntimeException(localInvocationTargetException);
/*      */       } catch (IllegalAccessException localIllegalAccessException) {
/* 1442 */         throw new RuntimeException(localIllegalAccessException);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class Attribute
/*      */   {
/*      */     public final String name;
/*      */     public final Class<?> sourceType;
/*      */     public final String value;
/*      */ 
/*      */     public Attribute(String paramString1, Class<?> paramClass, String paramString2)
/*      */     {
/* 1413 */       this.name = paramString1;
/* 1414 */       this.sourceType = paramClass;
/* 1415 */       this.value = paramString2;
/*      */     }
/*      */   }
/*      */ 
/*      */   private class DefineElement extends FXMLLoader.Element
/*      */   {
/*      */     private DefineElement()
/*      */     {
/* 1388 */       super();
/*      */     }
/*      */     public boolean isCollection() {
/* 1391 */       return true;
/*      */     }
/*      */ 
/*      */     public void add(Object paramObject)
/*      */     {
/*      */     }
/*      */ 
/*      */     public void processAttribute(String paramString1, String paramString2, String paramString3)
/*      */       throws LoadException
/*      */     {
/* 1402 */       throw new LoadException("Element does not support attributes.");
/*      */     }
/*      */   }
/*      */ 
/*      */   private class ScriptElement extends FXMLLoader.Element
/*      */   {
/* 1271 */     public String source = null;
/* 1272 */     public Charset charset = FXMLLoader.this.charset;
/*      */ 
/*      */     private ScriptElement()
/*      */     {
/* 1270 */       super();
/*      */     }
/*      */ 
/*      */     public boolean isCollection()
/*      */     {
/* 1276 */       return false;
/*      */     }
/*      */ 
/*      */     public void processStartElement() throws IOException
/*      */     {
/* 1281 */       super.processStartElement();
/*      */ 
/* 1283 */       if ((this.source != null) && (!FXMLLoader.this.staticLoad)) {
/* 1284 */         int i = this.source.lastIndexOf(".");
/* 1285 */         if (i == -1) {
/* 1286 */           throw new LoadException("Cannot determine type of script \"" + this.source + "\"."); } 
/*      */ String str = this.source.substring(i + 1);
/*      */ 
/* 1292 */         ClassLoader localClassLoader = Thread.currentThread().getContextClassLoader();
/*      */         Object localObject1;
/*      */         ScriptEngine localScriptEngine;
/*      */         try { Thread.currentThread().setContextClassLoader(FXMLLoader.this.classLoader);
/* 1295 */           localObject1 = FXMLLoader.this.getScriptEngineManager();
/* 1296 */           localScriptEngine = ((ScriptEngineManager)localObject1).getEngineByExtension(str);
/*      */         } finally {
/* 1298 */           Thread.currentThread().setContextClassLoader(localClassLoader);
/*      */         }
/*      */ 
/* 1301 */         if (localScriptEngine == null) {
/* 1302 */           throw new LoadException("Unable to locate scripting engine for extension " + str + ".");
/*      */         }
/*      */ 
/* 1306 */         localScriptEngine.setBindings(FXMLLoader.this.scriptEngineManager.getBindings(), 100);
/*      */         try
/*      */         {
/* 1310 */           if (this.source.charAt(0) == '/') {
/* 1311 */             localObject1 = FXMLLoader.this.classLoader.getResource(this.source.substring(1));
/*      */           } else {
/* 1313 */             if (FXMLLoader.this.location == null) {
/* 1314 */               throw new LoadException("Base location is undefined.");
/*      */             }
/*      */ 
/* 1317 */             localObject1 = new URL(FXMLLoader.this.location, this.source);
/*      */           }
/*      */ 
/* 1320 */           InputStreamReader localInputStreamReader = null;
/*      */           try {
/* 1322 */             localInputStreamReader = new InputStreamReader(((URL)localObject1).openStream(), this.charset);
/* 1323 */             localScriptEngine.eval(localInputStreamReader);
/*      */           } catch (ScriptException localScriptException) {
/* 1325 */             localScriptException.printStackTrace();
/*      */           } finally {
/* 1327 */             if (localInputStreamReader != null)
/* 1328 */               localInputStreamReader.close();
/*      */           }
/*      */         }
/*      */         catch (IOException localIOException) {
/* 1332 */           throw new LoadException(localIOException);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*      */     public void processEndElement() throws IOException
/*      */     {
/* 1339 */       super.processEndElement();
/*      */ 
/* 1341 */       if ((this.value != null) && (!FXMLLoader.this.staticLoad))
/*      */         try
/*      */         {
/* 1344 */           FXMLLoader.this.scriptEngine.eval((String)this.value);
/*      */         } catch (ScriptException localScriptException) {
/* 1346 */           System.err.println(localScriptException.getMessage());
/*      */         }
/*      */     }
/*      */ 
/*      */     public void processCharacters()
/*      */       throws LoadException
/*      */     {
/* 1353 */       if (this.source != null) {
/* 1354 */         throw new LoadException("Script source already specified.");
/*      */       }
/*      */ 
/* 1357 */       if ((FXMLLoader.this.scriptEngine == null) && (!FXMLLoader.this.staticLoad)) {
/* 1358 */         throw new LoadException("Page language not specified.");
/*      */       }
/*      */ 
/* 1361 */       updateValue(FXMLLoader.this.xmlStreamReader.getText());
/*      */     }
/*      */ 
/*      */     public void processAttribute(String paramString1, String paramString2, String paramString3)
/*      */       throws IOException
/*      */     {
/* 1367 */       if ((paramString1 == null) && (paramString2.equals("source")))
/*      */       {
/* 1369 */         if (FXMLLoader.this.loadListener != null) {
/* 1370 */           FXMLLoader.this.loadListener.readInternalAttribute(paramString2, paramString3);
/*      */         }
/*      */ 
/* 1373 */         this.source = paramString3;
/* 1374 */       } else if (paramString2.equals("charset")) {
/* 1375 */         if (FXMLLoader.this.loadListener != null) {
/* 1376 */           FXMLLoader.this.loadListener.readInternalAttribute(paramString2, paramString3);
/*      */         }
/*      */ 
/* 1379 */         this.charset = Charset.forName(paramString3);
/*      */       } else {
/* 1381 */         throw new LoadException(paramString1 + ":" + paramString2 + " is not a valid attribute.");
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private class UnknownStaticPropertyElement extends FXMLLoader.Element
/*      */   {
/*      */     public final String name;
/*      */ 
/*      */     public UnknownStaticPropertyElement(String arg2)
/*      */       throws LoadException
/*      */     {
/* 1238 */       super();
/* 1239 */       if (this.parent == null) {
/* 1240 */         throw new LoadException("Invalid root element.");
/*      */       }
/*      */ 
/* 1243 */       if (this.parent.value == null)
/* 1244 */         throw new LoadException("Parent element does not support property elements.");
/*      */       Object localObject;
/* 1247 */       this.name = localObject;
/*      */     }
/*      */ 
/*      */     public boolean isCollection()
/*      */     {
/* 1252 */       return false;
/*      */     }
/*      */ 
/*      */     public void set(Object paramObject)
/*      */     {
/* 1257 */       updateValue(paramObject);
/*      */     }
/*      */ 
/*      */     public void processCharacters() throws IOException
/*      */     {
/* 1262 */       String str = FXMLLoader.this.xmlStreamReader.getText();
/* 1263 */       str = FXMLLoader.extraneousWhitespacePattern.matcher(str).replaceAll(" ");
/*      */ 
/* 1265 */       updateValue(str.trim());
/*      */     }
/*      */   }
/*      */ 
/*      */   private class PropertyElement extends FXMLLoader.Element
/*      */   {
/*      */     public final String name;
/*      */     public final Class<?> sourceType;
/*      */     public final boolean readOnly;
/*      */ 
/*      */     public PropertyElement(Class<?> arg2)
/*      */       throws LoadException
/*      */     {
/* 1123 */       super();
/* 1124 */       if (this.parent == null) {
/* 1125 */         throw new LoadException("Invalid root element.");
/*      */       }
/*      */ 
/* 1128 */       if (this.parent.value == null)
/* 1129 */         throw new LoadException("Parent element does not support property elements.");
/*      */       String str;
/* 1132 */       this.name = str;
/*      */       Object localObject1;
/* 1133 */       this.sourceType = localObject1;
/*      */ 
/* 1135 */       if (localObject1 == null)
/*      */       {
/* 1137 */         if (str.startsWith("on")) {
/* 1138 */           throw new LoadException("\"" + str + "\" is not a valid element name.");
/*      */         }
/*      */ 
/* 1141 */         Map localMap = this.parent.getProperties();
/*      */ 
/* 1143 */         if (this.parent.isTyped()) {
/* 1144 */           this.readOnly = this.parent.getValueAdapter().isReadOnly(str);
/*      */         }
/*      */         else
/*      */         {
/* 1148 */           this.readOnly = localMap.containsKey(str);
/*      */         }
/*      */ 
/* 1151 */         if (this.readOnly) {
/* 1152 */           Object localObject2 = localMap.get(str);
/* 1153 */           if (localObject2 == null) {
/* 1154 */             throw new LoadException("Invalid property.");
/*      */           }
/*      */ 
/* 1157 */           updateValue(localObject2);
/*      */         }
/*      */       }
/*      */       else {
/* 1161 */         this.readOnly = false;
/*      */       }
/*      */     }
/*      */ 
/*      */     public boolean isCollection()
/*      */     {
/* 1167 */       return this.readOnly ? super.isCollection() : false;
/*      */     }
/*      */ 
/*      */     public void add(Object paramObject)
/*      */       throws LoadException
/*      */     {
/* 1173 */       if (this.parent.isTyped()) {
/* 1174 */         Type localType = this.parent.getValueAdapter().getGenericType(this.name);
/* 1175 */         paramObject = BeanAdapter.coerce(paramObject, BeanAdapter.getListItemType(localType));
/*      */       }
/*      */ 
/* 1179 */       super.add(paramObject);
/*      */     }
/*      */ 
/*      */     public void set(Object paramObject)
/*      */       throws LoadException
/*      */     {
/* 1185 */       updateValue(paramObject);
/*      */ 
/* 1187 */       if (this.sourceType == null)
/*      */       {
/* 1189 */         this.parent.getProperties().put(this.name, paramObject);
/*      */       }
/* 1191 */       else if ((this.parent.value instanceof Builder))
/*      */       {
/* 1193 */         this.parent.staticPropertyElements.add(this);
/*      */       }
/*      */       else
/* 1196 */         BeanAdapter.put(this.parent.value, this.sourceType, this.name, paramObject);
/*      */     }
/*      */ 
/*      */     public void processAttribute(String paramString1, String paramString2, String paramString3)
/*      */       throws IOException
/*      */     {
/* 1204 */       if (!this.readOnly) {
/* 1205 */         throw new LoadException("Attributes are not supported for writable property elements.");
/*      */       }
/*      */ 
/* 1208 */       super.processAttribute(paramString1, paramString2, paramString3);
/*      */     }
/*      */ 
/*      */     public void processEndElement() throws IOException
/*      */     {
/* 1213 */       super.processEndElement();
/*      */ 
/* 1215 */       if (this.readOnly) {
/* 1216 */         processInstancePropertyAttributes();
/* 1217 */         processEventHandlerAttributes();
/*      */       }
/*      */     }
/*      */ 
/*      */     public void processCharacters() throws IOException
/*      */     {
/* 1223 */       if (!this.readOnly) {
/* 1224 */         String str = FXMLLoader.this.xmlStreamReader.getText();
/* 1225 */         str = FXMLLoader.extraneousWhitespacePattern.matcher(str).replaceAll(" ");
/*      */ 
/* 1227 */         set(str.trim());
/*      */       } else {
/* 1229 */         super.processCharacters();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private class RootElement extends FXMLLoader.ValueElement
/*      */   {
/* 1063 */     public String type = null;
/*      */ 
/*      */     private RootElement()
/*      */     {
/* 1062 */       super(null);
/*      */     }
/*      */ 
/*      */     public void processAttribute(String paramString1, String paramString2, String paramString3)
/*      */       throws IOException
/*      */     {
/* 1068 */       if (paramString1 == null) {
/* 1069 */         if (paramString2.equals("type")) {
/* 1070 */           if (FXMLLoader.this.loadListener != null) {
/* 1071 */             FXMLLoader.this.loadListener.readInternalAttribute(paramString2, paramString3);
/*      */           }
/*      */ 
/* 1074 */           this.type = paramString3;
/*      */         } else {
/* 1076 */           super.processAttribute(paramString1, paramString2, paramString3);
/*      */         }
/*      */       }
/* 1079 */       else super.processAttribute(paramString1, paramString2, paramString3);
/*      */     }
/*      */ 
/*      */     public Object constructValue()
/*      */       throws LoadException
/*      */     {
/* 1085 */       if (this.type == null) {
/* 1086 */         throw new LoadException("type is required.");
/*      */       }
/*      */ 
/* 1089 */       Class localClass = FXMLLoader.this.getType(this.type);
/*      */       Object localObject;
/* 1092 */       if (FXMLLoader.this.root == null) {
/* 1093 */         localObject = FXMLLoader.this.builderFactory == null ? null : FXMLLoader.this.builderFactory.getBuilder(localClass);
/*      */ 
/* 1095 */         if (localObject == null)
/*      */           try {
/* 1097 */             localObject = ReflectUtil.newInstance(localClass);
/*      */           } catch (InstantiationException localInstantiationException) {
/* 1099 */             throw new LoadException(localInstantiationException);
/*      */           } catch (IllegalAccessException localIllegalAccessException) {
/* 1101 */             throw new LoadException(localIllegalAccessException);
/*      */           }
/*      */       }
/*      */       else {
/* 1105 */         if (!localClass.isAssignableFrom(FXMLLoader.this.root.getClass())) {
/* 1106 */           throw new LoadException("Root is not an instance of " + localClass.getName() + ".");
/*      */         }
/*      */ 
/* 1110 */         localObject = FXMLLoader.this.root;
/*      */       }
/*      */ 
/* 1113 */       return localObject;
/*      */     }
/*      */   }
/*      */ 
/*      */   private class CopyElement extends FXMLLoader.ValueElement
/*      */   {
/* 1000 */     public String source = null;
/*      */ 
/*      */     private CopyElement()
/*      */     {
/*  999 */       super(null);
/*      */     }
/*      */ 
/*      */     public void processAttribute(String paramString1, String paramString2, String paramString3)
/*      */       throws IOException
/*      */     {
/* 1005 */       if (paramString1 == null) {
/* 1006 */         if (paramString2.equals("source")) {
/* 1007 */           if (FXMLLoader.this.loadListener != null) {
/* 1008 */             FXMLLoader.this.loadListener.readInternalAttribute(paramString2, paramString3);
/*      */           }
/*      */ 
/* 1011 */           this.source = paramString3;
/*      */         } else {
/* 1013 */           super.processAttribute(paramString1, paramString2, paramString3);
/*      */         }
/*      */       }
/* 1016 */       else super.processAttribute(paramString1, paramString2, paramString3);
/*      */     }
/*      */ 
/*      */     public Object constructValue()
/*      */       throws LoadException
/*      */     {
/* 1022 */       if (this.source == null) {
/* 1023 */         throw new LoadException("source is required.");
/*      */       }
/*      */ 
/* 1026 */       List localList = Expression.split(this.source);
/* 1027 */       if (!Expression.isDefined(FXMLLoader.this.namespace, localList)) {
/* 1028 */         throw new LoadException("Value \"" + this.source + "\" does not exist.");
/*      */       }
/*      */ 
/* 1031 */       Object localObject1 = Expression.get(FXMLLoader.this.namespace, localList);
/* 1032 */       Class localClass = localObject1.getClass();
/*      */ 
/* 1034 */       Constructor localConstructor = null;
/*      */       try {
/* 1036 */         localConstructor = ConstructorUtil.getConstructor(localClass, new Class[] { localClass });
/*      */       }
/*      */       catch (NoSuchMethodException localNoSuchMethodException)
/*      */       {
/*      */       }
/*      */       Object localObject2;
/* 1042 */       if (localConstructor != null)
/*      */         try {
/* 1044 */           ReflectUtil.checkPackageAccess(localClass);
/* 1045 */           localObject2 = localConstructor.newInstance(new Object[] { localObject1 });
/*      */         } catch (InstantiationException localInstantiationException) {
/* 1047 */           throw new LoadException(localInstantiationException);
/*      */         } catch (IllegalAccessException localIllegalAccessException) {
/* 1049 */           throw new LoadException(localIllegalAccessException);
/*      */         } catch (InvocationTargetException localInvocationTargetException) {
/* 1051 */           throw new LoadException(localInvocationTargetException);
/*      */         }
/*      */       else {
/* 1054 */         throw new LoadException("Can't copy value " + localObject1 + ".");
/*      */       }
/*      */ 
/* 1057 */       return localObject2;
/*      */     }
/*      */   }
/*      */ 
/*      */   private class ReferenceElement extends FXMLLoader.ValueElement
/*      */   {
/*  963 */     public String source = null;
/*      */ 
/*      */     private ReferenceElement()
/*      */     {
/*  962 */       super(null);
/*      */     }
/*      */ 
/*      */     public void processAttribute(String paramString1, String paramString2, String paramString3)
/*      */       throws IOException
/*      */     {
/*  968 */       if (paramString1 == null) {
/*  969 */         if (paramString2.equals("source")) {
/*  970 */           if (FXMLLoader.this.loadListener != null) {
/*  971 */             FXMLLoader.this.loadListener.readInternalAttribute(paramString2, paramString3);
/*      */           }
/*      */ 
/*  974 */           this.source = paramString3;
/*      */         } else {
/*  976 */           super.processAttribute(paramString1, paramString2, paramString3);
/*      */         }
/*      */       }
/*  979 */       else super.processAttribute(paramString1, paramString2, paramString3);
/*      */     }
/*      */ 
/*      */     public Object constructValue()
/*      */       throws LoadException
/*      */     {
/*  985 */       if (this.source == null) {
/*  986 */         throw new LoadException("source is required.");
/*      */       }
/*      */ 
/*  989 */       List localList = Expression.split(this.source);
/*  990 */       if (!Expression.isDefined(FXMLLoader.this.namespace, localList)) {
/*  991 */         throw new LoadException("Value \"" + this.source + "\" does not exist.");
/*      */       }
/*      */ 
/*  994 */       return Expression.get(FXMLLoader.this.namespace, localList);
/*      */     }
/*      */   }
/*      */ 
/*      */   private class IncludeElement extends FXMLLoader.ValueElement
/*      */   {
/*  878 */     public String source = null;
/*  879 */     public ResourceBundle resources = FXMLLoader.this.resources;
/*  880 */     public Charset charset = FXMLLoader.this.charset;
/*      */ 
/*      */     private IncludeElement()
/*      */     {
/*  877 */       super(null);
/*      */     }
/*      */ 
/*      */     public void processAttribute(String paramString1, String paramString2, String paramString3)
/*      */       throws IOException
/*      */     {
/*  885 */       if (paramString1 == null) {
/*  886 */         if (paramString2.equals("source")) {
/*  887 */           if (FXMLLoader.this.loadListener != null) {
/*  888 */             FXMLLoader.this.loadListener.readInternalAttribute(paramString2, paramString3);
/*      */           }
/*      */ 
/*  891 */           this.source = paramString3;
/*  892 */         } else if (paramString2.equals("resources")) {
/*  893 */           if (FXMLLoader.this.loadListener != null) {
/*  894 */             FXMLLoader.this.loadListener.readInternalAttribute(paramString2, paramString3);
/*      */           }
/*      */ 
/*  897 */           this.resources = ResourceBundle.getBundle(paramString3, Locale.getDefault(), FXMLLoader.this.resources.getClass().getClassLoader());
/*      */         }
/*  899 */         else if (paramString2.equals("charset")) {
/*  900 */           if (FXMLLoader.this.loadListener != null) {
/*  901 */             FXMLLoader.this.loadListener.readInternalAttribute(paramString2, paramString3);
/*      */           }
/*      */ 
/*  904 */           this.charset = Charset.forName(paramString3);
/*      */         } else {
/*  906 */           super.processAttribute(paramString1, paramString2, paramString3);
/*      */         }
/*      */       }
/*  909 */       else super.processAttribute(paramString1, paramString2, paramString3);
/*      */     }
/*      */ 
/*      */     public Object constructValue()
/*      */       throws IOException
/*      */     {
/*  915 */       if (this.source == null)
/*  916 */         throw new LoadException("source is required.");
/*      */       URL localURL;
/*  920 */       if (this.source.charAt(0) == '/') {
/*  921 */         localURL = FXMLLoader.this.classLoader.getResource(this.source.substring(1));
/*      */       } else {
/*  923 */         if (FXMLLoader.this.location == null) {
/*  924 */           throw new LoadException("Base location is undefined.");
/*      */         }
/*      */ 
/*  927 */         localURL = new URL(FXMLLoader.this.location, this.source);
/*      */       }
/*      */ 
/*  930 */       FXMLLoader localFXMLLoader = new FXMLLoader(localURL, this.resources, FXMLLoader.this.builderFactory, FXMLLoader.this.controllerFactory, this.charset, FXMLLoader.this.loaders);
/*      */ 
/*  933 */       localFXMLLoader.setClassLoader(FXMLLoader.this.classLoader);
/*  934 */       localFXMLLoader.setStaticLoad(FXMLLoader.this.staticLoad);
/*      */ 
/*  936 */       Object localObject1 = localFXMLLoader.load();
/*      */ 
/*  938 */       if (this.id != null) {
/*  939 */         String str = this.id + "Controller";
/*  940 */         Object localObject2 = localFXMLLoader.getController();
/*      */ 
/*  942 */         FXMLLoader.this.namespace.put(str, localObject2);
/*      */ 
/*  944 */         if (localObject2 != null) {
/*  945 */           Field localField = (Field)FXMLLoader.this.getControllerFields().get(str);
/*      */ 
/*  947 */           if (localField != null) {
/*      */             try {
/*  949 */               localField.set(FXMLLoader.this.controller, localObject2);
/*      */             } catch (IllegalAccessException localIllegalAccessException) {
/*  951 */               throw new LoadException(localIllegalAccessException);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*  957 */       return localObject1;
/*      */     }
/*      */   }
/*      */ 
/*      */   private class UnknownTypeElement extends FXMLLoader.ValueElement
/*      */   {
/*      */     public final String name;
/*      */ 
/*      */     public UnknownTypeElement(String arg2)
/*      */     {
/*  861 */       super(null);
/*      */       Object localObject;
/*  862 */       this.name = localObject;
/*      */     }
/*      */ 
/*      */     public void processEndElement()
/*      */       throws IOException
/*      */     {
/*      */     }
/*      */ 
/*      */     public Object constructValue() throws LoadException
/*      */     {
/*  872 */       return new UnknownValueMap();
/*      */     }
/*      */ 
/*      */     @DefaultProperty("items")
/*      */     public class UnknownValueMap extends AbstractMap<String, Object>
/*      */     {
/*  827 */       private ArrayList<?> items = new ArrayList();
/*  828 */       private HashMap<String, Object> values = new HashMap();
/*      */ 
/*      */       public UnknownValueMap() {
/*      */       }
/*  832 */       public Object get(Object paramObject) { if (paramObject == null) {
/*  833 */           throw new NullPointerException();
/*      */         }
/*      */ 
/*  836 */         return paramObject.equals(((DefaultProperty)getClass().getAnnotation(DefaultProperty.class)).value()) ? this.items : this.values.get(paramObject);
/*      */       }
/*      */ 
/*      */       public Object put(String paramString, Object paramObject)
/*      */       {
/*  842 */         if (paramString == null) {
/*  843 */           throw new NullPointerException();
/*      */         }
/*      */ 
/*  846 */         if (paramString.equals(((DefaultProperty)getClass().getAnnotation(DefaultProperty.class)).value())) {
/*  847 */           throw new IllegalArgumentException();
/*      */         }
/*      */ 
/*  850 */         return this.values.put(paramString, paramObject);
/*      */       }
/*      */ 
/*      */       public Set<Map.Entry<String, Object>> entrySet()
/*      */       {
/*  855 */         return Collections.emptySet();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private class InstanceDeclarationElement extends FXMLLoader.ValueElement
/*      */   {
/*      */     public Class<?> type;
/*  756 */     public String constant = null;
/*  757 */     public String factory = null;
/*      */ 
/*  759 */     public InstanceDeclarationElement() throws LoadException { super(null);
/*      */       Object localObject;
/*  760 */       this.type = localObject;
/*      */     }
/*      */ 
/*      */     public void processAttribute(String paramString1, String paramString2, String paramString3)
/*      */       throws IOException
/*      */     {
/*  766 */       if ((paramString1 != null) && (paramString1.equals("fx")))
/*      */       {
/*  768 */         if (paramString2.equals("value"))
/*  769 */           this.value = paramString3;
/*  770 */         else if (paramString2.equals("constant"))
/*  771 */           this.constant = paramString3;
/*  772 */         else if (paramString2.equals("factory"))
/*  773 */           this.factory = paramString3;
/*      */         else
/*  775 */           super.processAttribute(paramString1, paramString2, paramString3);
/*      */       }
/*      */       else
/*  778 */         super.processAttribute(paramString1, paramString2, paramString3);
/*      */     }
/*      */ 
/*      */     public Object constructValue()
/*      */       throws IOException
/*      */     {
/*      */       Object localObject;
/*  785 */       if (this.value != null) {
/*  786 */         localObject = BeanAdapter.coerce(this.value, this.type);
/*  787 */       } else if (this.constant != null) {
/*  788 */         localObject = BeanAdapter.getConstantValue(this.type, this.constant);
/*  789 */       } else if (this.factory != null) {
/*      */         Method localMethod;
/*      */         try {
/*  792 */           localMethod = MethodUtil.getMethod(this.type, this.factory, new Class[0]);
/*      */         } catch (NoSuchMethodException localNoSuchMethodException) {
/*  794 */           throw new LoadException(localNoSuchMethodException);
/*      */         }
/*      */         try
/*      */         {
/*  798 */           localObject = MethodUtil.invoke(localMethod, null, new Object[0]);
/*      */         } catch (IllegalAccessException localIllegalAccessException2) {
/*  800 */           throw new LoadException(localIllegalAccessException2);
/*      */         } catch (InvocationTargetException localInvocationTargetException) {
/*  802 */           throw new LoadException(localInvocationTargetException);
/*      */         }
/*      */       } else {
/*  805 */         localObject = FXMLLoader.this.builderFactory == null ? null : FXMLLoader.this.builderFactory.getBuilder(this.type);
/*      */ 
/*  807 */         if (localObject == null) {
/*      */           try {
/*  809 */             localObject = ReflectUtil.newInstance(this.type);
/*      */           } catch (InstantiationException localInstantiationException) {
/*  811 */             throw new LoadException(localInstantiationException);
/*      */           } catch (IllegalAccessException localIllegalAccessException1) {
/*  813 */             throw new LoadException(localIllegalAccessException1);
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*  818 */       return localObject;
/*      */     }
/*      */   }
/*      */ 
/*      */   private abstract class ValueElement extends FXMLLoader.Element
/*      */   {
/*  559 */     public String id = null;
/*      */ 
/*      */     private ValueElement()
/*      */     {
/*  558 */       super();
/*      */     }
/*      */ 
/*      */     public void processStartElement() throws IOException
/*      */     {
/*  563 */       super.processStartElement();
/*      */ 
/*  565 */       updateValue(constructValue());
/*      */ 
/*  567 */       if ((this.value instanceof Builder))
/*  568 */         processInstancePropertyAttributes();
/*      */       else
/*  570 */         processValue();
/*      */     }
/*      */ 
/*      */     public void processEndElement()
/*      */       throws IOException
/*      */     {
/*  577 */       super.processEndElement();
/*      */       Object localObject1;
/*  580 */       if ((this.value instanceof Builder)) {
/*  581 */         localObject1 = (Builder)this.value;
/*  582 */         updateValue(((Builder)localObject1).build());
/*      */ 
/*  584 */         processValue();
/*      */       } else {
/*  586 */         processInstancePropertyAttributes();
/*      */       }
/*      */ 
/*  589 */       processEventHandlerAttributes();
/*      */ 
/*  592 */       if (this.staticPropertyAttributes.size() > 0)
/*  593 */         for (localObject1 = this.staticPropertyAttributes.iterator(); ((Iterator)localObject1).hasNext(); ) { localObject2 = (FXMLLoader.Attribute)((Iterator)localObject1).next();
/*  594 */           processPropertyAttribute((FXMLLoader.Attribute)localObject2);
/*      */         }
/*      */       Object localObject2;
/*  599 */       if (this.staticPropertyElements.size() > 0) {
/*  600 */         for (localObject1 = this.staticPropertyElements.iterator(); ((Iterator)localObject1).hasNext(); ) { localObject2 = (FXMLLoader.PropertyElement)((Iterator)localObject1).next();
/*  601 */           BeanAdapter.put(this.value, ((FXMLLoader.PropertyElement)localObject2).sourceType, ((FXMLLoader.PropertyElement)localObject2).name, ((FXMLLoader.PropertyElement)localObject2).value);
/*      */         }
/*      */       }
/*      */ 
/*  605 */       if (this.parent != null)
/*  606 */         if (this.parent.isCollection())
/*  607 */           this.parent.add(this.value);
/*      */         else
/*  609 */           this.parent.set(this.value);
/*      */     }
/*      */ 
/*      */     private Object getListValue(FXMLLoader.Element paramElement, String paramString, Object paramObject)
/*      */     {
/*  616 */       if (paramElement.isTyped()) {
/*  617 */         Type localType1 = paramElement.getValueAdapter().getGenericType(paramString);
/*      */ 
/*  619 */         if (localType1 != null) {
/*  620 */           Type localType2 = BeanAdapter.getGenericListItemType(localType1);
/*      */ 
/*  622 */           if ((localType2 instanceof ParameterizedType)) {
/*  623 */             localType2 = ((ParameterizedType)localType2).getRawType();
/*      */           }
/*      */ 
/*  626 */           paramObject = BeanAdapter.coerce(paramObject, (Class)localType2);
/*      */         }
/*      */       }
/*      */ 
/*  630 */       return paramObject;
/*      */     }
/*      */ 
/*      */     private void processValue() throws LoadException
/*      */     {
/*  635 */       if (this.parent == null) {
/*  636 */         FXMLLoader.this.root = this.value;
/*      */       }
/*      */ 
/*  640 */       if (this.id != null) {
/*  641 */         FXMLLoader.this.namespace.put(this.id, this.value);
/*      */ 
/*  644 */         IDProperty localIDProperty = (IDProperty)this.value.getClass().getAnnotation(IDProperty.class);
/*      */         Object localObject;
/*  646 */         if (localIDProperty != null) {
/*  647 */           localObject = getProperties();
/*  648 */           ((Map)localObject).put(localIDProperty.value(), this.id);
/*      */         }
/*      */ 
/*  652 */         if (FXMLLoader.this.controller != null) {
/*  653 */           localObject = (Field)FXMLLoader.this.getControllerFields().get(this.id);
/*      */ 
/*  655 */           if (localObject != null)
/*      */             try {
/*  657 */               ((Field)localObject).set(FXMLLoader.this.controller, this.value);
/*      */             } catch (IllegalAccessException localIllegalAccessException) {
/*  659 */               throw new RuntimeException(localIllegalAccessException);
/*      */             }
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*      */     public void processCharacters()
/*      */       throws LoadException
/*      */     {
/*  669 */       Class localClass = this.value.getClass();
/*  670 */       DefaultProperty localDefaultProperty = (DefaultProperty)localClass.getAnnotation(DefaultProperty.class);
/*      */ 
/*  674 */       if (localDefaultProperty != null) {
/*  675 */         String str1 = FXMLLoader.this.xmlStreamReader.getText();
/*  676 */         str1 = FXMLLoader.extraneousWhitespacePattern.matcher(str1).replaceAll(" ");
/*      */ 
/*  678 */         String str2 = localDefaultProperty.value();
/*  679 */         BeanAdapter localBeanAdapter = getValueAdapter();
/*      */ 
/*  681 */         if ((localBeanAdapter.isReadOnly(str2)) && (List.class.isAssignableFrom(localBeanAdapter.getType(str2))))
/*      */         {
/*  683 */           List localList = (List)localBeanAdapter.get(str2);
/*  684 */           localList.add(getListValue(this, str2, str1));
/*      */         } else {
/*  686 */           localBeanAdapter.put(str2, str1.trim());
/*      */         }
/*      */       } else {
/*  689 */         throw new LoadException(localClass.getName() + " does not have a default property.");
/*      */       }
/*      */     }
/*      */ 
/*      */     public void processAttribute(String paramString1, String paramString2, String paramString3)
/*      */       throws IOException
/*      */     {
/*  696 */       if ((paramString1 != null) && (paramString1.equals("fx")))
/*      */       {
/*  698 */         if (paramString2.equals("id"))
/*      */         {
/*  700 */           if (paramString3.equals("null")) {
/*  701 */             throw new LoadException("Invalid identifier.");
/*      */           }
/*      */ 
/*  704 */           int i = 0; for (int j = paramString3.length(); i < j; i++) {
/*  705 */             if (!Character.isJavaIdentifierPart(paramString3.charAt(i))) {
/*  706 */               throw new LoadException("Invalid identifier.");
/*      */             }
/*      */           }
/*      */ 
/*  710 */           this.id = paramString3;
/*  711 */         } else if (paramString2.equals("controller")) {
/*  712 */           if (FXMLLoader.this.current.parent != null) {
/*  713 */             throw new LoadException("fx:controller can only be applied to root element.");
/*      */           }
/*      */ 
/*  717 */           if (FXMLLoader.this.controller != null) {
/*  718 */             throw new LoadException("Controller value already specified.");
/*      */           }
/*      */ 
/*  721 */           if (!FXMLLoader.this.staticLoad) {
/*      */             Class localClass;
/*      */             try {
/*  724 */               localClass = FXMLLoader.this.classLoader.loadClass(paramString3);
/*      */             } catch (ClassNotFoundException localClassNotFoundException) {
/*  726 */               throw new LoadException(localClassNotFoundException);
/*      */             }
/*      */             try
/*      */             {
/*  730 */               if (FXMLLoader.this.controllerFactory == null)
/*  731 */                 FXMLLoader.this.setController(ReflectUtil.newInstance(localClass));
/*      */               else
/*  733 */                 FXMLLoader.this.setController(FXMLLoader.this.controllerFactory.call(localClass));
/*      */             }
/*      */             catch (InstantiationException localInstantiationException) {
/*  736 */               throw new LoadException(localInstantiationException);
/*      */             } catch (IllegalAccessException localIllegalAccessException) {
/*  738 */               throw new LoadException(localIllegalAccessException);
/*      */             }
/*      */           }
/*      */         } else {
/*  742 */           throw new LoadException("Invalid attribute.");
/*      */         }
/*      */       }
/*  745 */       else super.processAttribute(paramString1, paramString2, paramString3);
/*      */     }
/*      */ 
/*      */     public abstract Object constructValue()
/*      */       throws IOException;
/*      */   }
/*      */ 
/*      */   private abstract class Element
/*      */   {
/*      */     public final Element parent;
/*      */     public final int lineNumber;
/*   72 */     public Object value = null;
/*   73 */     private BeanAdapter valueAdapter = null;
/*      */ 
/*   75 */     public final LinkedList<FXMLLoader.Attribute> eventHandlerAttributes = new LinkedList();
/*   76 */     public final LinkedList<FXMLLoader.Attribute> instancePropertyAttributes = new LinkedList();
/*   77 */     public final LinkedList<FXMLLoader.Attribute> staticPropertyAttributes = new LinkedList();
/*   78 */     public final LinkedList<FXMLLoader.PropertyElement> staticPropertyElements = new LinkedList();
/*      */ 
/*      */     public Element() {
/*   81 */       this.parent = FXMLLoader.this.current;
/*   82 */       this.lineNumber = FXMLLoader.this.getLineNumber();
/*      */     }
/*      */ 
/*      */     public boolean isCollection()
/*      */     {
/*      */       boolean bool;
/*   89 */       if ((this.value instanceof List)) {
/*   90 */         bool = true;
/*      */       } else {
/*   92 */         Class localClass = this.value.getClass();
/*   93 */         DefaultProperty localDefaultProperty = (DefaultProperty)localClass.getAnnotation(DefaultProperty.class);
/*      */ 
/*   95 */         if (localDefaultProperty != null)
/*   96 */           bool = getProperties().get(localDefaultProperty.value()) instanceof List;
/*      */         else {
/*   98 */           bool = false;
/*      */         }
/*      */       }
/*      */ 
/*  102 */       return bool;
/*      */     }
/*      */ 
/*      */     public void add(Object paramObject)
/*      */       throws LoadException
/*      */     {
/*      */       List localList;
/*  111 */       if ((this.value instanceof List)) {
/*  112 */         localList = (List)this.value;
/*      */       } else {
/*  114 */         Class localClass = this.value.getClass();
/*  115 */         DefaultProperty localDefaultProperty = (DefaultProperty)localClass.getAnnotation(DefaultProperty.class);
/*  116 */         String str = localDefaultProperty.value();
/*      */ 
/*  119 */         localList = (List)getProperties().get(str);
/*      */ 
/*  122 */         if (!Map.class.isAssignableFrom(localClass)) {
/*  123 */           Type localType = getValueAdapter().getGenericType(str);
/*  124 */           paramObject = BeanAdapter.coerce(paramObject, BeanAdapter.getListItemType(localType));
/*      */         }
/*      */       }
/*      */ 
/*  128 */       localList.add(paramObject);
/*      */     }
/*      */ 
/*      */     public void set(Object paramObject) throws LoadException {
/*  132 */       if (this.value == null) {
/*  133 */         throw new LoadException("Cannot set value on this element.");
/*      */       }
/*      */ 
/*  137 */       Class localClass = this.value.getClass();
/*  138 */       DefaultProperty localDefaultProperty = (DefaultProperty)localClass.getAnnotation(DefaultProperty.class);
/*  139 */       if (localDefaultProperty == null) {
/*  140 */         throw new LoadException("Element does not define a default property.");
/*      */       }
/*      */ 
/*  143 */       getProperties().put(localDefaultProperty.value(), paramObject);
/*      */     }
/*      */ 
/*      */     public void updateValue(Object paramObject) {
/*  147 */       this.value = paramObject;
/*  148 */       this.valueAdapter = null;
/*      */     }
/*      */ 
/*      */     public boolean isTyped() {
/*  152 */       return !(this.value instanceof Map);
/*      */     }
/*      */ 
/*      */     public BeanAdapter getValueAdapter() {
/*  156 */       if (this.valueAdapter == null) {
/*  157 */         this.valueAdapter = new BeanAdapter(this.value);
/*      */       }
/*      */ 
/*  160 */       return this.valueAdapter;
/*      */     }
/*      */ 
/*      */     public Map<String, Object> getProperties()
/*      */     {
/*  165 */       return isTyped() ? getValueAdapter() : (Map)this.value;
/*      */     }
/*      */ 
/*      */     public void processStartElement() throws IOException {
/*  169 */       int i = 0; for (int j = FXMLLoader.this.xmlStreamReader.getAttributeCount(); i < j; i++) {
/*  170 */         String str1 = FXMLLoader.this.xmlStreamReader.getAttributePrefix(i);
/*  171 */         String str2 = FXMLLoader.this.xmlStreamReader.getAttributeLocalName(i);
/*  172 */         String str3 = FXMLLoader.this.xmlStreamReader.getAttributeValue(i);
/*      */ 
/*  174 */         if ((FXMLLoader.this.loadListener != null) && (str1 != null) && (str1.equals("fx")))
/*      */         {
/*  177 */           FXMLLoader.this.loadListener.readInternalAttribute(str1 + ":" + str2, str3);
/*      */         }
/*      */ 
/*  180 */         processAttribute(str1, str2, str3);
/*      */       }
/*      */     }
/*      */ 
/*      */     public void processEndElement() throws IOException
/*      */     {
/*      */     }
/*      */ 
/*      */     public void processCharacters() throws IOException {
/*  189 */       throw new LoadException("Unexpected characters in input stream.");
/*      */     }
/*      */ 
/*      */     public void processInstancePropertyAttributes() throws IOException {
/*  193 */       if (this.instancePropertyAttributes.size() > 0)
/*  194 */         for (FXMLLoader.Attribute localAttribute : this.instancePropertyAttributes)
/*  195 */           processPropertyAttribute(localAttribute);
/*      */     }
/*      */ 
/*      */     public void processAttribute(String paramString1, String paramString2, String paramString3)
/*      */       throws IOException
/*      */     {
/*  202 */       if (paramString1 == null)
/*      */       {
/*  204 */         if (paramString2.startsWith("on")) {
/*  205 */           if (FXMLLoader.this.loadListener != null) {
/*  206 */             FXMLLoader.this.loadListener.readEventHandlerAttribute(paramString2, paramString3);
/*      */           }
/*      */ 
/*  209 */           this.eventHandlerAttributes.add(new FXMLLoader.Attribute(paramString2, null, paramString3));
/*      */         } else {
/*  211 */           int i = paramString2.lastIndexOf('.');
/*      */ 
/*  213 */           if (i == -1)
/*      */           {
/*  215 */             if (FXMLLoader.this.loadListener != null) {
/*  216 */               FXMLLoader.this.loadListener.readPropertyAttribute(paramString2, null, paramString3);
/*      */             }
/*      */ 
/*  219 */             this.instancePropertyAttributes.add(new FXMLLoader.Attribute(paramString2, null, paramString3));
/*      */           }
/*      */           else {
/*  222 */             String str = paramString2.substring(i + 1);
/*  223 */             Class localClass = FXMLLoader.this.getType(paramString2.substring(0, i));
/*      */ 
/*  225 */             if (localClass != null) {
/*  226 */               if (FXMLLoader.this.loadListener != null) {
/*  227 */                 FXMLLoader.this.loadListener.readPropertyAttribute(str, localClass, paramString3);
/*      */               }
/*      */ 
/*  230 */               this.staticPropertyAttributes.add(new FXMLLoader.Attribute(str, localClass, paramString3));
/*  231 */             } else if (FXMLLoader.this.staticLoad) {
/*  232 */               if (FXMLLoader.this.loadListener != null)
/*  233 */                 FXMLLoader.this.loadListener.readUnknownStaticPropertyAttribute(paramString2, paramString3);
/*      */             }
/*      */             else {
/*  236 */               throw new LoadException(paramString2 + " is not a valid attribute.");
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*  242 */         throw new LoadException(paramString1 + ":" + paramString2 + " is not a valid attribute.");
/*      */     }
/*      */ 
/*      */     public void processPropertyAttribute(FXMLLoader.Attribute paramAttribute)
/*      */       throws IOException
/*      */     {
/*  249 */       String str1 = paramAttribute.value;
/*      */ 
/*  251 */       if (str1.startsWith("\\")) {
/*  252 */         str1 = str1.substring("\\".length());
/*      */ 
/*  254 */         if ((str1.length() == 0) || ((!str1.startsWith("\\")) && (!str1.startsWith("@")) && (!str1.startsWith("%")) && (!str1.startsWith("$")) && (!str1.startsWith("#{"))))
/*      */         {
/*  260 */           throw new LoadException("Invalid escape sequence.");
/*      */         }
/*      */ 
/*  263 */         applyProperty(paramAttribute.name, paramAttribute.sourceType, str1);
/*      */       }
/*      */       else
/*      */       {
/*      */         Object localObject1;
/*  264 */         if (str1.startsWith("@")) {
/*  265 */           str1 = str1.substring("@".length());
/*      */ 
/*  267 */           if (str1.startsWith("@"))
/*      */           {
/*  269 */             warnDeprecatedEscapeSequence("@");
/*  270 */             applyProperty(paramAttribute.name, paramAttribute.sourceType, str1);
/*      */           } else {
/*  272 */             if (str1.length() == 0) {
/*  273 */               throw new LoadException("Missing relative path.");
/*      */             }
/*      */ 
/*  277 */             if (str1.charAt(0) == '/') {
/*  278 */               localObject1 = FXMLLoader.this.classLoader.getResource(str1.substring(1));
/*      */             } else {
/*  280 */               if (FXMLLoader.this.location == null) {
/*  281 */                 throw new LoadException("Base location is undefined.");
/*      */               }
/*      */ 
/*  284 */               localObject1 = new URL(FXMLLoader.this.location, str1);
/*      */             }
/*      */ 
/*  287 */             applyProperty(paramAttribute.name, paramAttribute.sourceType, localObject1);
/*      */           }
/*  289 */         } else if (str1.startsWith("%")) {
/*  290 */           str1 = str1.substring("%".length());
/*      */ 
/*  292 */           if (str1.startsWith("%"))
/*      */           {
/*  294 */             warnDeprecatedEscapeSequence("%");
/*  295 */             applyProperty(paramAttribute.name, paramAttribute.sourceType, str1);
/*      */           } else {
/*  297 */             if (str1.length() == 0) {
/*  298 */               throw new LoadException("Missing resource key.");
/*      */             }
/*      */ 
/*  302 */             if (FXMLLoader.this.resources == null) {
/*  303 */               throw new LoadException("No resources specified.");
/*      */             }
/*      */ 
/*  306 */             if (!FXMLLoader.this.resources.containsKey(str1)) {
/*  307 */               throw new LoadException("Resource \"" + str1 + "\" not found.");
/*      */             }
/*      */ 
/*  310 */             applyProperty(paramAttribute.name, paramAttribute.sourceType, FXMLLoader.this.resources.getObject(str1));
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/*      */           Object localObject2;
/*  312 */           if (str1.startsWith("$")) {
/*  313 */             str1 = str1.substring("$".length());
/*      */ 
/*  315 */             if (str1.startsWith("$"))
/*      */             {
/*  317 */               warnDeprecatedEscapeSequence("$");
/*  318 */               applyProperty(paramAttribute.name, paramAttribute.sourceType, str1);
/*  319 */             } else if (str1.equals("null"))
/*      */             {
/*  321 */               applyProperty(paramAttribute.name, paramAttribute.sourceType, null);
/*      */             } else {
/*  323 */               if (str1.length() == 0) {
/*  324 */                 throw new LoadException("Missing expression.");
/*      */               }
/*      */ 
/*  329 */               if ((str1.startsWith("{")) && (str1.endsWith("}")))
/*      */               {
/*  331 */                 if (paramAttribute.sourceType != null) {
/*  332 */                   throw new LoadException("Cannot bind to static property.");
/*      */                 }
/*      */ 
/*  335 */                 if ((this.value instanceof Builder)) {
/*  336 */                   throw new LoadException("Cannot bind to builder property.");
/*      */                 }
/*      */ 
/*  339 */                 str1 = str1.substring(1, str1.length() - 1);
/*  340 */                 localObject1 = Expression.valueOf(str1, FXMLLoader.this.namespace);
/*      */ 
/*  343 */                 localObject2 = getProperties();
/*  344 */                 ((Expression)localObject1).valueProperty().addListener(new FXMLLoader.ExpressionTargetMapping((Expression)localObject1, localObject2, Expression.split(paramAttribute.name)));
/*      */               }
/*      */               else {
/*  347 */                 localObject1 = new VariableExpression(Expression.split(str1), FXMLLoader.this.namespace);
/*      */               }
/*      */ 
/*  351 */               if (((Expression)localObject1).isDefined())
/*  352 */                 applyProperty(paramAttribute.name, paramAttribute.sourceType, ((Expression)localObject1).getValue());
/*      */             }
/*      */           }
/*      */           else
/*      */           {
/*      */             Object localObject3;
/*      */             Object localObject4;
/*  355 */             if (str1.startsWith("#{")) {
/*  356 */               if (!FXMLLoader.enableBidirectionalBinding) {
/*  357 */                 throw new LoadException("Bi-directional binding is not enabled.");
/*      */               }
/*      */ 
/*  360 */               if (!str1.endsWith("}")) {
/*  361 */                 throw new LoadException("Unterminated expression.");
/*      */               }
/*      */ 
/*  364 */               str1 = str1.substring("#{".length(), str1.length() - "}".length());
/*      */ 
/*  367 */               if (str1.length() == 0) {
/*  368 */                 throw new LoadException("Missing expression.");
/*      */               }
/*      */ 
/*  371 */               if (paramAttribute.sourceType != null) {
/*  372 */                 throw new LoadException("Cannot bind to static property.");
/*      */               }
/*      */ 
/*  375 */               if ((this.value instanceof Builder)) {
/*  376 */                 throw new LoadException("Cannot bind to builder property.");
/*      */               }
/*      */ 
/*  380 */               localObject1 = Expression.valueOf(str1, FXMLLoader.this.namespace);
/*      */ 
/*  383 */               localObject2 = getProperties();
/*  384 */               localObject3 = Expression.split(paramAttribute.name);
/*  385 */               ((Expression)localObject1).valueProperty().addListener(new FXMLLoader.ExpressionTargetMapping((Expression)localObject1, localObject2, (List)localObject3));
/*      */ 
/*  390 */               if ((localObject1 instanceof VariableExpression)) {
/*  391 */                 localObject4 = new VariableExpression((List)localObject3, localObject2);
/*  392 */                 ((Expression)localObject4).valueProperty().addListener(new FXMLLoader.ExpressionTargetMapping((Expression)localObject4, FXMLLoader.this.namespace, ((VariableExpression)localObject1).getPath()));
/*      */               }
/*      */ 
/*  397 */               if (((Expression)localObject1).isDefined())
/*  398 */                 applyProperty(paramAttribute.name, paramAttribute.sourceType, ((Expression)localObject1).getValue());
/*      */             }
/*      */             else {
/*  401 */               localObject1 = str1;
/*      */ 
/*  403 */               if ((paramAttribute.sourceType == null) && (isTyped())) {
/*  404 */                 localObject2 = getValueAdapter();
/*  405 */                 localObject3 = ((BeanAdapter)localObject2).getType(paramAttribute.name);
/*      */ 
/*  407 */                 if (localObject3 == null)
/*  408 */                   throw new PropertyNotFoundException("Property \"" + paramAttribute.name + "\" does not exist" + " or is read-only.");
/*      */                 Object localObject5;
/*      */                 Object localObject6;
/*  412 */                 if ((List.class.isAssignableFrom((Class)localObject3)) && (((BeanAdapter)localObject2).isReadOnly(paramAttribute.name)))
/*      */                 {
/*  415 */                   localObject4 = (List)((BeanAdapter)localObject2).get(paramAttribute.name);
/*  416 */                   localObject5 = ((BeanAdapter)localObject2).getGenericType(paramAttribute.name);
/*  417 */                   localObject6 = (Class)BeanAdapter.getGenericListItemType((Type)localObject5);
/*      */ 
/*  419 */                   if ((localObject6 instanceof ParameterizedType)) {
/*  420 */                     localObject6 = ((ParameterizedType)localObject6).getRawType();
/*      */                   }
/*      */ 
/*  423 */                   String str2 = str1.toString();
/*  424 */                   if (str2.length() > 0) {
/*  425 */                     String[] arrayOfString = str2.split(",");
/*      */ 
/*  427 */                     for (int j = 0; j < arrayOfString.length; j++) {
/*  428 */                       ((List)localObject4).add(BeanAdapter.coerce(arrayOfString[j].trim(), (Class)localObject6));
/*      */                     }
/*      */                   }
/*      */ 
/*  432 */                   localObject1 = null;
/*  433 */                 } else if (((Class)localObject3).isArray())
/*      */                 {
/*  435 */                   localObject4 = ((Class)localObject3).getComponentType();
/*      */ 
/*  437 */                   localObject5 = str1.toString();
/*  438 */                   if (((String)localObject5).length() > 0) {
/*  439 */                     localObject6 = ((String)localObject5).split(",");
/*  440 */                     localObject1 = Array.newInstance((Class)localObject4, localObject6.length);
/*  441 */                     for (int i = 0; i < localObject6.length; i++)
/*  442 */                       Array.set(localObject1, i, BeanAdapter.coerce(localObject6[i].trim(), ((Class)localObject3).getComponentType()));
/*      */                   }
/*      */                   else
/*      */                   {
/*  446 */                     localObject1 = Array.newInstance((Class)localObject4, 0);
/*      */                   }
/*      */                 }
/*      */               }
/*      */ 
/*  451 */               if (localObject1 != null)
/*  452 */                 applyProperty(paramAttribute.name, paramAttribute.sourceType, localObject1);  } 
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  458 */     public void warnDeprecatedEscapeSequence(String paramString) { System.err.println(paramString + paramString + " is a deprecated escape sequence. " + "Please use \\" + paramString + " instead."); }
/*      */ 
/*      */ 
/*      */     public void applyProperty(String paramString, Class<?> paramClass, Object paramObject)
/*      */     {
/*  463 */       if (paramClass == null)
/*  464 */         getProperties().put(paramString, paramObject);
/*      */       else
/*  466 */         BeanAdapter.put(this.value, paramClass, paramString, paramObject);
/*      */     }
/*      */ 
/*      */     public void processEventHandlerAttributes() throws LoadException
/*      */     {
/*  471 */       if ((this.eventHandlerAttributes.size() > 0) && (!FXMLLoader.this.staticLoad))
/*  472 */         for (FXMLLoader.Attribute localAttribute : this.eventHandlerAttributes) {
/*  473 */           Object localObject = null;
/*      */ 
/*  475 */           String str = localAttribute.value;
/*      */ 
/*  477 */           if (str.startsWith("#")) {
/*  478 */             str = str.substring("#".length());
/*      */ 
/*  480 */             if (!str.startsWith("#")) {
/*  481 */               if (str.length() == 0) {
/*  482 */                 throw new LoadException("Missing controller method.");
/*      */               }
/*      */ 
/*  485 */               if (FXMLLoader.this.controller == null) {
/*  486 */                 throw new LoadException("No controller specified.");
/*      */               }
/*      */ 
/*  489 */               Method localMethod = (Method)FXMLLoader.this.getControllerMethods().get(str);
/*      */ 
/*  491 */               if (localMethod == null) {
/*  492 */                 throw new LoadException("Controller method \"" + str + "\" not found.");
/*      */               }
/*      */ 
/*  495 */               localObject = new FXMLLoader.ControllerMethodEventHandler(FXMLLoader.this.controller, localMethod);
/*      */             }
/*      */           }
/*      */ 
/*  499 */           if (localObject == null) {
/*  500 */             if (str.length() == 0) {
/*  501 */               throw new LoadException("Missing handler script.");
/*      */             }
/*      */ 
/*  504 */             if (FXMLLoader.this.scriptEngine == null) {
/*  505 */               throw new LoadException("Page language not specified.");
/*      */             }
/*      */ 
/*  508 */             localObject = new FXMLLoader.ScriptEventHandler(str, FXMLLoader.this.scriptEngine);
/*      */           }
/*      */ 
/*  512 */           if (localObject != null)
/*  513 */             addEventHandler(localAttribute, (EventHandler)localObject);
/*      */         }
/*      */     }
/*      */ 
/*      */     private void addEventHandler(FXMLLoader.Attribute paramAttribute, EventHandler<? extends Event> paramEventHandler)
/*      */       throws LoadException
/*      */     {
/*  522 */       if (paramAttribute.name.endsWith("Change")) {
/*  523 */         int i = "on".length();
/*  524 */         int j = paramAttribute.name.length() - "Change".length();
/*      */         Object localObject;
/*  526 */         if (i == j) {
/*  527 */           if ((this.value instanceof ObservableList)) {
/*  528 */             localObject = (ObservableList)this.value;
/*  529 */             ((ObservableList)localObject).addListener(new FXMLLoader.ObservableListChangeAdapter((ObservableList)localObject, paramEventHandler));
/*      */           }
/*  531 */           else if ((this.value instanceof ObservableMap)) {
/*  532 */             localObject = (ObservableMap)this.value;
/*  533 */             ((ObservableMap)localObject).addListener(new FXMLLoader.ObservableMapChangeAdapter((ObservableMap)localObject, paramEventHandler));
/*      */           }
/*      */           else {
/*  536 */             throw new LoadException("Invalid event source.");
/*      */           }
/*      */         } else {
/*  539 */           localObject = Character.toLowerCase(paramAttribute.name.charAt(i)) + paramAttribute.name.substring(i + 1, j);
/*      */ 
/*  542 */           Property localProperty = getValueAdapter().getPropertyModel((String)localObject);
/*  543 */           if (localProperty == null) {
/*  544 */             throw new LoadException(this.value.getClass().getName() + " does not define" + " a property model for \"" + (String)localObject + "\".");
/*      */           }
/*      */ 
/*  548 */           localProperty.addListener(new FXMLLoader.PropertyChangeAdapter(this.value, paramEventHandler));
/*      */         }
/*      */       }
/*      */       else {
/*  552 */         getValueAdapter().put(paramAttribute.name, paramEventHandler);
/*      */       }
/*      */     }
/*      */   }
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.fxml.FXMLLoader
 * JD-Core Version:    0.6.2
 */