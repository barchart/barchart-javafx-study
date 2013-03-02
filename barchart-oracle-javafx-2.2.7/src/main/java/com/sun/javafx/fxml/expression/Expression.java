/*      */ package com.sun.javafx.fxml.expression;
/*      */ 
/*      */ import com.sun.javafx.fxml.BeanAdapter;
/*      */ import java.io.IOException;
/*      */ import java.io.PushbackReader;
/*      */ import java.io.Reader;
/*      */ import java.io.StringReader;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import javafx.beans.property.ReadOnlyObjectProperty;
/*      */ import javafx.beans.property.ReadOnlyObjectPropertyBase;
/*      */ import javafx.beans.value.ChangeListener;
/*      */ 
/*      */ public abstract class Expression
/*      */ {
/*  614 */   private Expression parent = null;
/*      */ 
/*  616 */   private ValueProperty valueProperty = new ValueProperty(null);
/*  617 */   private boolean valid = false;
/*      */ 
/*  619 */   private boolean updating = false;
/*      */   public static final String NEGATE = "-";
/*      */   public static final String NOT = "!";
/*      */   public static final String ADD = "+";
/*      */   public static final String SUBTRACT = "-";
/*      */   public static final String MULTIPLY = "*";
/*      */   public static final String DIVIDE = "/";
/*      */   public static final String MODULO = "%";
/*      */   public static final String GREATER_THAN = ">";
/*      */   public static final String GREATER_THAN_OR_EQUAL_TO = ">=";
/*      */   public static final String LESS_THAN = "<";
/*      */   public static final String LESS_THAN_OR_EQUAL_TO = "<=";
/*      */   public static final String EQUAL_TO = "==";
/*      */   public static final String NOT_EQUAL_TO = "!=";
/*      */   public static final String AND = "&&";
/*      */   public static final String OR = "||";
/*      */   public static final String LEFT_PARENTHESIS = "(";
/*      */   public static final String RIGHT_PARENTHESIS = ")";
/*      */   public static final String NULL_KEYWORD = "null";
/*      */   public static final String TRUE_KEYWORD = "true";
/*      */   public static final String FALSE_KEYWORD = "false";
/*      */ 
/*      */   public Expression getParent()
/*      */   {
/*  655 */     return this.parent;
/*      */   }
/*      */ 
/*      */   protected void setParent(Expression paramExpression)
/*      */   {
/*  665 */     this.parent = paramExpression;
/*      */   }
/*      */ 
/*      */   public <T> T getValue()
/*      */   {
/*  676 */     return this.valueProperty.get();
/*      */   }
/*      */ 
/*      */   public ReadOnlyObjectProperty<Object> valueProperty()
/*      */   {
/*  683 */     return this.valueProperty;
/*      */   }
/*      */ 
/*      */   protected abstract Object evaluate();
/*      */ 
/*      */   public void invalidate()
/*      */   {
/*  698 */     this.valueProperty.invalidate();
/*      */ 
/*  700 */     if (this.parent != null)
/*  701 */       this.parent.invalidate();
/*      */   }
/*      */ 
/*      */   public void validate()
/*      */   {
/*  709 */     this.valueProperty.validate();
/*      */   }
/*      */ 
/*      */   public boolean isValid()
/*      */   {
/*  716 */     return this.valid;
/*      */   }
/*      */ 
/*      */   public abstract boolean isDefined();
/*      */ 
/*      */   protected abstract void registerChangeListeners();
/*      */ 
/*      */   protected abstract void unregisterChangeListeners();
/*      */ 
/*      */   public static <T> T get(Object paramObject, List<String> paramList)
/*      */   {
/*  749 */     if (paramList == null) {
/*  750 */       throw new NullPointerException();
/*      */     }
/*      */ 
/*  753 */     return get(paramObject, paramList.iterator());
/*      */   }
/*      */ 
/*      */   public static <T> T get(Object paramObject, Iterator<String> paramIterator)
/*      */   {
/*  767 */     if (paramIterator == null)
/*  768 */       throw new NullPointerException();
/*      */     Object localObject;
/*  772 */     if (paramIterator.hasNext())
/*  773 */       localObject = get(get(paramObject, (String)paramIterator.next()), paramIterator);
/*      */     else {
/*  775 */       localObject = paramObject;
/*      */     }
/*      */ 
/*  778 */     return localObject;
/*      */   }
/*      */ 
/*      */   public static <T> T get(Object paramObject, String paramString)
/*      */   {
/*  792 */     if (paramString == null)
/*  793 */       throw new NullPointerException();
/*      */     Object localObject2;
/*      */     Object localObject1;
/*  797 */     if ((paramObject instanceof List)) {
/*  798 */       localObject2 = (List)paramObject;
/*  799 */       localObject1 = ((List)localObject2).get(Integer.parseInt(paramString));
/*  800 */     } else if (paramObject != null)
/*      */     {
/*  802 */       if ((paramObject instanceof Map))
/*  803 */         localObject2 = (Map)paramObject;
/*      */       else {
/*  805 */         localObject2 = new BeanAdapter(paramObject);
/*      */       }
/*      */ 
/*  808 */       localObject1 = ((Map)localObject2).get(paramString);
/*      */     } else {
/*  810 */       localObject1 = null;
/*      */     }
/*      */ 
/*  813 */     return localObject1;
/*      */   }
/*      */ 
/*      */   public static void set(Object paramObject1, List<String> paramList, Object paramObject2)
/*      */   {
/*  824 */     if (paramList == null) {
/*  825 */       throw new NullPointerException();
/*      */     }
/*      */ 
/*  828 */     set(paramObject1, paramList.iterator(), paramObject2);
/*      */   }
/*      */ 
/*      */   public static void set(Object paramObject1, Iterator<String> paramIterator, Object paramObject2)
/*      */   {
/*  839 */     if (paramIterator == null) {
/*  840 */       throw new NullPointerException();
/*      */     }
/*      */ 
/*  843 */     if (!paramIterator.hasNext()) {
/*  844 */       throw new IllegalArgumentException();
/*      */     }
/*      */ 
/*  847 */     String str = (String)paramIterator.next();
/*      */ 
/*  849 */     if (paramIterator.hasNext())
/*  850 */       set(get(paramObject1, str), paramIterator, paramObject2);
/*      */     else
/*  852 */       set(paramObject1, str, paramObject2);
/*      */   }
/*      */ 
/*      */   public static void set(Object paramObject1, String paramString, Object paramObject2)
/*      */   {
/*  865 */     if (paramString == null)
/*  866 */       throw new NullPointerException();
/*      */     Object localObject;
/*  869 */     if ((paramObject1 instanceof List)) {
/*  870 */       localObject = (List)paramObject1;
/*  871 */       ((List)localObject).set(Integer.parseInt(paramString), paramObject2);
/*  872 */     } else if (paramObject1 != null)
/*      */     {
/*  874 */       if ((paramObject1 instanceof Map))
/*  875 */         localObject = (Map)paramObject1;
/*      */       else {
/*  877 */         localObject = new BeanAdapter(paramObject1);
/*      */       }
/*      */ 
/*  880 */       ((Map)localObject).put(paramString, paramObject2);
/*      */     } else {
/*  882 */       throw new IllegalArgumentException();
/*      */     }
/*      */   }
/*      */ 
/*      */   public static boolean isDefined(Object paramObject, List<String> paramList)
/*      */   {
/*  896 */     if (paramList == null) {
/*  897 */       throw new NullPointerException();
/*      */     }
/*      */ 
/*  900 */     return isDefined(paramObject, paramList.iterator());
/*      */   }
/*      */ 
/*      */   public static boolean isDefined(Object paramObject, Iterator<String> paramIterator)
/*      */   {
/*  913 */     if (paramIterator == null) {
/*  914 */       throw new NullPointerException();
/*      */     }
/*      */ 
/*  917 */     if (!paramIterator.hasNext()) {
/*  918 */       throw new IllegalArgumentException();
/*      */     }
/*      */ 
/*  921 */     String str = (String)paramIterator.next();
/*      */     boolean bool;
/*  924 */     if (paramIterator.hasNext())
/*  925 */       bool = isDefined(get(paramObject, str), paramIterator);
/*      */     else {
/*  927 */       bool = isDefined(paramObject, str);
/*      */     }
/*      */ 
/*  930 */     return bool;
/*      */   }
/*      */ 
/*      */   public static boolean isDefined(Object paramObject, String paramString)
/*      */   {
/*  944 */     if (paramString == null)
/*  945 */       throw new NullPointerException();
/*      */     Object localObject;
/*      */     boolean bool;
/*  949 */     if ((paramObject instanceof List)) {
/*  950 */       localObject = (List)paramObject;
/*  951 */       bool = Integer.parseInt(paramString) < ((List)localObject).size();
/*  952 */     } else if (paramObject != null)
/*      */     {
/*  954 */       if ((paramObject instanceof Map))
/*  955 */         localObject = (Map)paramObject;
/*      */       else {
/*  957 */         localObject = new BeanAdapter(paramObject);
/*      */       }
/*      */ 
/*  960 */       bool = ((Map)localObject).containsKey(paramString);
/*      */     } else {
/*  962 */       bool = false;
/*      */     }
/*      */ 
/*  965 */     return bool;
/*      */   }
/*      */ 
/*      */   public static List<String> split(String paramString)
/*      */   {
/*  974 */     if (paramString == null) {
/*  975 */       throw new NullPointerException();
/*  978 */     }
/*      */ Parser localParser = new Parser(null);
/*      */     List localList;
/*      */     try {
/*  981 */       localList = localParser.readPath(new StringReader(paramString));
/*      */     } catch (IOException localIOException) {
/*  983 */       throw new RuntimeException(localIOException);
/*      */     }
/*      */ 
/*  986 */     return Collections.unmodifiableList(localList);
/*      */   }
/*      */ 
/*      */   public static BinaryExpression add(Expression paramExpression1, Expression paramExpression2)
/*      */   {
/*  996 */     return new BinaryExpression(paramExpression1, paramExpression2)
/*      */     {
/*      */       public String getOperator() {
/*  999 */         return "+";
/*      */       }
/*      */ 
/*      */       protected Object evaluate()
/*      */       {
/* 1004 */         Object localObject1 = getLeft().evaluate();
/* 1005 */         Object localObject2 = getRight().evaluate();
/*      */         Object localObject3;
/* 1008 */         if (((localObject1 instanceof String)) || ((localObject2 instanceof String))) {
/* 1009 */           localObject3 = localObject1.toString().concat(localObject2.toString());
/*      */         } else {
/* 1011 */           Number localNumber1 = (Number)localObject1;
/* 1012 */           Number localNumber2 = (Number)localObject2;
/*      */ 
/* 1014 */           if (((localNumber1 instanceof Double)) || ((localNumber2 instanceof Double)))
/* 1015 */             localObject3 = Double.valueOf(localNumber1.doubleValue() + localNumber2.doubleValue());
/* 1016 */           else if (((localNumber1 instanceof Float)) || ((localNumber2 instanceof Float)))
/* 1017 */             localObject3 = Float.valueOf(localNumber1.floatValue() + localNumber2.floatValue());
/* 1018 */           else if (((localNumber1 instanceof Long)) || ((localNumber2 instanceof Long)))
/* 1019 */             localObject3 = Long.valueOf(localNumber1.longValue() + localNumber2.longValue());
/* 1020 */           else if (((localNumber1 instanceof Integer)) || ((localNumber2 instanceof Integer)))
/* 1021 */             localObject3 = Integer.valueOf(localNumber1.intValue() + localNumber2.intValue());
/* 1022 */           else if (((localNumber1 instanceof Short)) || ((localNumber2 instanceof Short)))
/* 1023 */             localObject3 = Integer.valueOf(localNumber1.shortValue() + localNumber2.shortValue());
/* 1024 */           else if (((localNumber1 instanceof Byte)) || ((localNumber2 instanceof Byte)))
/* 1025 */             localObject3 = Integer.valueOf(localNumber1.byteValue() + localNumber2.byteValue());
/*      */           else {
/* 1027 */             throw new UnsupportedOperationException();
/*      */           }
/*      */         }
/*      */ 
/* 1031 */         return localObject3;
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static BinaryExpression add(Expression paramExpression, Object paramObject)
/*      */   {
/* 1043 */     return add(paramExpression, new LiteralExpression(paramObject));
/*      */   }
/*      */ 
/*      */   public static BinaryExpression add(Object paramObject, Expression paramExpression)
/*      */   {
/* 1053 */     return add(new LiteralExpression(paramObject), paramExpression);
/*      */   }
/*      */ 
/*      */   public static BinaryExpression add(Object paramObject1, Object paramObject2)
/*      */   {
/* 1063 */     return add(new LiteralExpression(paramObject1), new LiteralExpression(paramObject2));
/*      */   }
/*      */ 
/*      */   public static BinaryExpression subtract(Expression paramExpression1, Expression paramExpression2)
/*      */   {
/* 1073 */     return new BinaryExpression(paramExpression1, paramExpression2)
/*      */     {
/*      */       public String getOperator() {
/* 1076 */         return "-";
/*      */       }
/*      */ 
/*      */       protected Object evaluate()
/*      */       {
/* 1081 */         Number localNumber1 = (Number)getLeft().getValue();
/* 1082 */         Number localNumber2 = (Number)getRight().getValue();
/*      */         Object localObject;
/* 1085 */         if (((localNumber1 instanceof Double)) || ((localNumber2 instanceof Double)))
/* 1086 */           localObject = Double.valueOf(localNumber1.doubleValue() - localNumber2.doubleValue());
/* 1087 */         else if (((localNumber1 instanceof Float)) || ((localNumber2 instanceof Float)))
/* 1088 */           localObject = Float.valueOf(localNumber1.floatValue() - localNumber2.floatValue());
/* 1089 */         else if (((localNumber1 instanceof Long)) || ((localNumber2 instanceof Long)))
/* 1090 */           localObject = Long.valueOf(localNumber1.longValue() - localNumber2.longValue());
/* 1091 */         else if (((localNumber1 instanceof Integer)) || ((localNumber2 instanceof Integer)))
/* 1092 */           localObject = Integer.valueOf(localNumber1.intValue() - localNumber2.intValue());
/* 1093 */         else if (((localNumber1 instanceof Short)) || ((localNumber2 instanceof Short)))
/* 1094 */           localObject = Integer.valueOf(localNumber1.shortValue() - localNumber2.shortValue());
/* 1095 */         else if (((localNumber1 instanceof Byte)) || ((localNumber2 instanceof Byte)))
/* 1096 */           localObject = Integer.valueOf(localNumber1.byteValue() - localNumber2.byteValue());
/*      */         else {
/* 1098 */           throw new UnsupportedOperationException();
/*      */         }
/*      */ 
/* 1101 */         return localObject;
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static BinaryExpression subtract(Expression paramExpression, Number paramNumber)
/*      */   {
/* 1113 */     return subtract(paramExpression, new LiteralExpression(paramNumber));
/*      */   }
/*      */ 
/*      */   public static BinaryExpression subtract(Number paramNumber, Expression paramExpression)
/*      */   {
/* 1123 */     return subtract(new LiteralExpression(paramNumber), paramExpression);
/*      */   }
/*      */ 
/*      */   public static BinaryExpression subtract(Number paramNumber1, Number paramNumber2)
/*      */   {
/* 1133 */     return subtract(new LiteralExpression(paramNumber1), new LiteralExpression(paramNumber2));
/*      */   }
/*      */ 
/*      */   public static BinaryExpression multiply(Expression paramExpression1, Expression paramExpression2)
/*      */   {
/* 1143 */     return new BinaryExpression(paramExpression1, paramExpression2)
/*      */     {
/*      */       public String getOperator() {
/* 1146 */         return "*";
/*      */       }
/*      */ 
/*      */       protected Object evaluate()
/*      */       {
/* 1151 */         Number localNumber1 = (Number)getLeft().getValue();
/* 1152 */         Number localNumber2 = (Number)getRight().getValue();
/*      */         Object localObject;
/* 1155 */         if (((localNumber1 instanceof Double)) || ((localNumber2 instanceof Double)))
/* 1156 */           localObject = Double.valueOf(localNumber1.doubleValue() * localNumber2.doubleValue());
/* 1157 */         else if (((localNumber1 instanceof Float)) || ((localNumber2 instanceof Float)))
/* 1158 */           localObject = Float.valueOf(localNumber1.floatValue() * localNumber2.floatValue());
/* 1159 */         else if (((localNumber1 instanceof Long)) || ((localNumber2 instanceof Long)))
/* 1160 */           localObject = Long.valueOf(localNumber1.longValue() * localNumber2.longValue());
/* 1161 */         else if (((localNumber1 instanceof Integer)) || ((localNumber2 instanceof Integer)))
/* 1162 */           localObject = Integer.valueOf(localNumber1.intValue() * localNumber2.intValue());
/* 1163 */         else if (((localNumber1 instanceof Short)) || ((localNumber2 instanceof Short)))
/* 1164 */           localObject = Integer.valueOf(localNumber1.shortValue() * localNumber2.shortValue());
/* 1165 */         else if (((localNumber1 instanceof Byte)) || ((localNumber2 instanceof Byte)))
/* 1166 */           localObject = Integer.valueOf(localNumber1.byteValue() * localNumber2.byteValue());
/*      */         else {
/* 1168 */           throw new UnsupportedOperationException();
/*      */         }
/*      */ 
/* 1171 */         return localObject;
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static BinaryExpression multiply(Expression paramExpression, Number paramNumber)
/*      */   {
/* 1183 */     return multiply(paramExpression, new LiteralExpression(paramNumber));
/*      */   }
/*      */ 
/*      */   public static BinaryExpression multiply(Number paramNumber, Expression paramExpression)
/*      */   {
/* 1193 */     return multiply(new LiteralExpression(paramNumber), paramExpression);
/*      */   }
/*      */ 
/*      */   public static BinaryExpression multiply(Number paramNumber1, Number paramNumber2)
/*      */   {
/* 1203 */     return multiply(new LiteralExpression(paramNumber1), new LiteralExpression(paramNumber2));
/*      */   }
/*      */ 
/*      */   public static BinaryExpression divide(Expression paramExpression1, Expression paramExpression2)
/*      */   {
/* 1213 */     return new BinaryExpression(paramExpression1, paramExpression2)
/*      */     {
/*      */       public String getOperator() {
/* 1216 */         return "/";
/*      */       }
/*      */ 
/*      */       protected Object evaluate()
/*      */       {
/* 1221 */         Number localNumber1 = (Number)getLeft().getValue();
/* 1222 */         Number localNumber2 = (Number)getRight().getValue();
/*      */         Object localObject;
/* 1225 */         if (((localNumber1 instanceof Double)) || ((localNumber2 instanceof Double)))
/* 1226 */           localObject = Double.valueOf(localNumber1.doubleValue() / localNumber2.doubleValue());
/* 1227 */         else if (((localNumber1 instanceof Float)) || ((localNumber2 instanceof Float)))
/* 1228 */           localObject = Float.valueOf(localNumber1.floatValue() / localNumber2.floatValue());
/* 1229 */         else if (((localNumber1 instanceof Long)) || ((localNumber2 instanceof Long)))
/* 1230 */           localObject = Long.valueOf(localNumber1.longValue() / localNumber2.longValue());
/* 1231 */         else if (((localNumber1 instanceof Integer)) || ((localNumber2 instanceof Integer)))
/* 1232 */           localObject = Integer.valueOf(localNumber1.intValue() / localNumber2.intValue());
/* 1233 */         else if (((localNumber1 instanceof Short)) || ((localNumber2 instanceof Short)))
/* 1234 */           localObject = Integer.valueOf(localNumber1.shortValue() / localNumber2.shortValue());
/* 1235 */         else if (((localNumber1 instanceof Byte)) || ((localNumber2 instanceof Byte)))
/* 1236 */           localObject = Integer.valueOf(localNumber1.byteValue() / localNumber2.byteValue());
/*      */         else {
/* 1238 */           throw new UnsupportedOperationException();
/*      */         }
/*      */ 
/* 1241 */         return localObject;
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static BinaryExpression divide(Expression paramExpression, Number paramNumber)
/*      */   {
/* 1253 */     return divide(paramExpression, new LiteralExpression(paramNumber));
/*      */   }
/*      */ 
/*      */   public static BinaryExpression divide(Number paramNumber, Expression paramExpression)
/*      */   {
/* 1263 */     return divide(new LiteralExpression(paramNumber), paramExpression);
/*      */   }
/*      */ 
/*      */   public static BinaryExpression divide(Number paramNumber1, Number paramNumber2)
/*      */   {
/* 1273 */     return divide(new LiteralExpression(paramNumber1), new LiteralExpression(paramNumber2));
/*      */   }
/*      */ 
/*      */   public static BinaryExpression modulo(Expression paramExpression1, Expression paramExpression2)
/*      */   {
/* 1283 */     return new BinaryExpression(paramExpression1, paramExpression2)
/*      */     {
/*      */       public String getOperator() {
/* 1286 */         return "%";
/*      */       }
/*      */ 
/*      */       protected Object evaluate()
/*      */       {
/* 1291 */         Number localNumber1 = (Number)getLeft().getValue();
/* 1292 */         Number localNumber2 = (Number)getRight().getValue();
/*      */         Object localObject;
/* 1295 */         if (((localNumber1 instanceof Double)) || ((localNumber2 instanceof Double)))
/* 1296 */           localObject = Double.valueOf(localNumber1.doubleValue() % localNumber2.doubleValue());
/* 1297 */         else if (((localNumber1 instanceof Float)) || ((localNumber2 instanceof Float)))
/* 1298 */           localObject = Float.valueOf(localNumber1.floatValue() % localNumber2.floatValue());
/* 1299 */         else if (((localNumber1 instanceof Long)) || ((localNumber2 instanceof Long)))
/* 1300 */           localObject = Long.valueOf(localNumber1.longValue() % localNumber2.longValue());
/* 1301 */         else if (((localNumber1 instanceof Integer)) || ((localNumber2 instanceof Integer)))
/* 1302 */           localObject = Integer.valueOf(localNumber1.intValue() % localNumber2.intValue());
/* 1303 */         else if (((localNumber1 instanceof Short)) || ((localNumber2 instanceof Short)))
/* 1304 */           localObject = Integer.valueOf(localNumber1.shortValue() % localNumber2.shortValue());
/* 1305 */         else if (((localNumber1 instanceof Byte)) || ((localNumber2 instanceof Byte)))
/* 1306 */           localObject = Integer.valueOf(localNumber1.byteValue() % localNumber2.byteValue());
/*      */         else {
/* 1308 */           throw new UnsupportedOperationException();
/*      */         }
/*      */ 
/* 1311 */         return localObject;
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static BinaryExpression modulo(Expression paramExpression, Number paramNumber)
/*      */   {
/* 1323 */     return modulo(paramExpression, new LiteralExpression(paramNumber));
/*      */   }
/*      */ 
/*      */   public static BinaryExpression modulo(Number paramNumber, Expression paramExpression)
/*      */   {
/* 1333 */     return modulo(new LiteralExpression(paramNumber), paramExpression);
/*      */   }
/*      */ 
/*      */   public static BinaryExpression modulo(Number paramNumber1, Number paramNumber2)
/*      */   {
/* 1343 */     return modulo(new LiteralExpression(paramNumber1), new LiteralExpression(paramNumber2));
/*      */   }
/*      */ 
/*      */   public static BinaryExpression equalTo(Expression paramExpression1, Expression paramExpression2)
/*      */   {
/* 1353 */     return new BinaryExpression(paramExpression1, paramExpression2)
/*      */     {
/*      */       public String getOperator() {
/* 1356 */         return "==";
/*      */       }
/*      */ 
/*      */       protected Object evaluate()
/*      */       {
/* 1362 */         return Boolean.valueOf(((Comparable)getLeft().getValue()).compareTo(getRight().getValue()) == 0);
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static BinaryExpression equalTo(Expression paramExpression, Object paramObject)
/*      */   {
/* 1374 */     return equalTo(paramExpression, new LiteralExpression(paramObject));
/*      */   }
/*      */ 
/*      */   public static BinaryExpression equalTo(Object paramObject, Expression paramExpression)
/*      */   {
/* 1384 */     return equalTo(new LiteralExpression(paramObject), paramExpression);
/*      */   }
/*      */ 
/*      */   public static BinaryExpression equalTo(Object paramObject1, Object paramObject2)
/*      */   {
/* 1394 */     return equalTo(new LiteralExpression(paramObject1), new LiteralExpression(paramObject2));
/*      */   }
/*      */ 
/*      */   public static BinaryExpression notEqualTo(Expression paramExpression1, Expression paramExpression2)
/*      */   {
/* 1404 */     return new BinaryExpression(paramExpression1, paramExpression2)
/*      */     {
/*      */       public String getOperator() {
/* 1407 */         return "!=";
/*      */       }
/*      */ 
/*      */       protected Object evaluate()
/*      */       {
/* 1413 */         return Boolean.valueOf(((Comparable)getLeft().getValue()).compareTo(getRight().getValue()) != 0);
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static BinaryExpression notEqualTo(Expression paramExpression, Object paramObject)
/*      */   {
/* 1425 */     return notEqualTo(paramExpression, new LiteralExpression(paramObject));
/*      */   }
/*      */ 
/*      */   public static BinaryExpression notEqualTo(Object paramObject, Expression paramExpression)
/*      */   {
/* 1435 */     return notEqualTo(new LiteralExpression(paramObject), paramExpression);
/*      */   }
/*      */ 
/*      */   public static BinaryExpression notEqualTo(Object paramObject1, Object paramObject2)
/*      */   {
/* 1445 */     return notEqualTo(new LiteralExpression(paramObject1), new LiteralExpression(paramObject2));
/*      */   }
/*      */ 
/*      */   public static BinaryExpression greaterThan(Expression paramExpression1, Expression paramExpression2)
/*      */   {
/* 1455 */     return new BinaryExpression(paramExpression1, paramExpression2)
/*      */     {
/*      */       public String getOperator() {
/* 1458 */         return ">";
/*      */       }
/*      */ 
/*      */       protected Object evaluate()
/*      */       {
/* 1464 */         return Boolean.valueOf(((Comparable)getLeft().getValue()).compareTo(getRight().getValue()) > 0);
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static BinaryExpression greaterThan(Expression paramExpression, Object paramObject)
/*      */   {
/* 1476 */     return greaterThan(paramExpression, new LiteralExpression(paramObject));
/*      */   }
/*      */ 
/*      */   public static BinaryExpression greaterThan(Object paramObject, Expression paramExpression)
/*      */   {
/* 1486 */     return greaterThan(new LiteralExpression(paramObject), paramExpression);
/*      */   }
/*      */ 
/*      */   public static BinaryExpression greaterThan(Object paramObject1, Object paramObject2)
/*      */   {
/* 1496 */     return greaterThan(new LiteralExpression(paramObject1), new LiteralExpression(paramObject2));
/*      */   }
/*      */ 
/*      */   public static BinaryExpression greaterThanOrEqualTo(Expression paramExpression1, Expression paramExpression2)
/*      */   {
/* 1506 */     return new BinaryExpression(paramExpression1, paramExpression2)
/*      */     {
/*      */       public String getOperator() {
/* 1509 */         return ">=";
/*      */       }
/*      */ 
/*      */       protected Object evaluate()
/*      */       {
/* 1515 */         return Boolean.valueOf(((Comparable)getLeft().getValue()).compareTo(getRight().getValue()) >= 0);
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static BinaryExpression greaterThanOrEqualTo(Expression paramExpression, Object paramObject)
/*      */   {
/* 1527 */     return greaterThanOrEqualTo(paramExpression, new LiteralExpression(paramObject));
/*      */   }
/*      */ 
/*      */   public static BinaryExpression greaterThanOrEqualTo(Object paramObject, Expression paramExpression)
/*      */   {
/* 1537 */     return greaterThanOrEqualTo(new LiteralExpression(paramObject), paramExpression);
/*      */   }
/*      */ 
/*      */   public static BinaryExpression greaterThanOrEqualTo(Object paramObject1, Object paramObject2)
/*      */   {
/* 1547 */     return greaterThanOrEqualTo(new LiteralExpression(paramObject1), new LiteralExpression(paramObject2));
/*      */   }
/*      */ 
/*      */   public static BinaryExpression lessThan(Expression paramExpression1, Expression paramExpression2)
/*      */   {
/* 1557 */     return new BinaryExpression(paramExpression1, paramExpression2)
/*      */     {
/*      */       public String getOperator() {
/* 1560 */         return "<";
/*      */       }
/*      */ 
/*      */       protected Object evaluate()
/*      */       {
/* 1566 */         return Boolean.valueOf(((Comparable)getLeft().getValue()).compareTo(getRight().getValue()) < 0);
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static BinaryExpression lessThan(Expression paramExpression, Object paramObject)
/*      */   {
/* 1578 */     return lessThan(paramExpression, new LiteralExpression(paramObject));
/*      */   }
/*      */ 
/*      */   public static BinaryExpression lessThan(Object paramObject, Expression paramExpression)
/*      */   {
/* 1588 */     return lessThan(new LiteralExpression(paramObject), paramExpression);
/*      */   }
/*      */ 
/*      */   public static BinaryExpression lessThan(Object paramObject1, Object paramObject2)
/*      */   {
/* 1598 */     return lessThan(new LiteralExpression(paramObject1), new LiteralExpression(paramObject2));
/*      */   }
/*      */ 
/*      */   public static BinaryExpression lessThanOrEqualTo(Expression paramExpression1, Expression paramExpression2)
/*      */   {
/* 1608 */     return new BinaryExpression(paramExpression1, paramExpression2)
/*      */     {
/*      */       public String getOperator() {
/* 1611 */         return "<=";
/*      */       }
/*      */ 
/*      */       protected Object evaluate()
/*      */       {
/* 1617 */         return Boolean.valueOf(((Comparable)getLeft().getValue()).compareTo(getRight().getValue()) <= 0);
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static BinaryExpression lessThanOrEqualTo(Expression paramExpression, Object paramObject)
/*      */   {
/* 1629 */     return lessThanOrEqualTo(paramExpression, new LiteralExpression(paramObject));
/*      */   }
/*      */ 
/*      */   public static BinaryExpression lessThanOrEqualTo(Object paramObject, Expression paramExpression)
/*      */   {
/* 1639 */     return lessThanOrEqualTo(new LiteralExpression(paramObject), paramExpression);
/*      */   }
/*      */ 
/*      */   public static BinaryExpression lessThanOrEqualTo(Object paramObject1, Object paramObject2)
/*      */   {
/* 1649 */     return lessThanOrEqualTo(new LiteralExpression(paramObject1), new LiteralExpression(paramObject2));
/*      */   }
/*      */ 
/*      */   public static BinaryExpression and(Expression paramExpression1, Expression paramExpression2)
/*      */   {
/* 1659 */     return new BinaryExpression(paramExpression1, paramExpression2)
/*      */     {
/*      */       public String getOperator() {
/* 1662 */         return "&&";
/*      */       }
/*      */ 
/*      */       protected Object evaluate()
/*      */       {
/* 1667 */         return Boolean.valueOf((((Boolean)getLeft().getValue()).booleanValue()) && (((Boolean)getRight().getValue()).booleanValue()));
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static BinaryExpression and(Expression paramExpression, Boolean paramBoolean)
/*      */   {
/* 1679 */     return and(paramExpression, new LiteralExpression(paramBoolean));
/*      */   }
/*      */ 
/*      */   public static BinaryExpression and(Boolean paramBoolean, Expression paramExpression)
/*      */   {
/* 1689 */     return and(new LiteralExpression(paramBoolean), paramExpression);
/*      */   }
/*      */ 
/*      */   public static BinaryExpression and(Boolean paramBoolean1, Boolean paramBoolean2)
/*      */   {
/* 1699 */     return and(new LiteralExpression(paramBoolean1), new LiteralExpression(paramBoolean2));
/*      */   }
/*      */ 
/*      */   public static BinaryExpression or(Expression paramExpression1, Expression paramExpression2)
/*      */   {
/* 1709 */     return new BinaryExpression(paramExpression1, paramExpression2)
/*      */     {
/*      */       public String getOperator() {
/* 1712 */         return "||";
/*      */       }
/*      */ 
/*      */       protected Object evaluate()
/*      */       {
/* 1717 */         return Boolean.valueOf((((Boolean)getLeft().getValue()).booleanValue()) || (((Boolean)getRight().getValue()).booleanValue()));
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static BinaryExpression or(Expression paramExpression, Boolean paramBoolean)
/*      */   {
/* 1729 */     return or(paramExpression, new LiteralExpression(paramBoolean));
/*      */   }
/*      */ 
/*      */   public static BinaryExpression or(Boolean paramBoolean, Expression paramExpression)
/*      */   {
/* 1739 */     return or(new LiteralExpression(paramBoolean), paramExpression);
/*      */   }
/*      */ 
/*      */   public static BinaryExpression or(Boolean paramBoolean1, Boolean paramBoolean2)
/*      */   {
/* 1749 */     return or(new LiteralExpression(paramBoolean1), new LiteralExpression(paramBoolean2));
/*      */   }
/*      */ 
/*      */   public static UnaryExpression negate(Expression paramExpression)
/*      */   {
/* 1758 */     return new UnaryExpression(paramExpression)
/*      */     {
/*      */       public String getOperator() {
/* 1761 */         return "-";
/*      */       }
/*      */ 
/*      */       protected Object evaluate()
/*      */       {
/* 1766 */         Object localObject = (Number)getOperand().getValue();
/* 1767 */         Class localClass = localObject.getClass();
/*      */ 
/* 1769 */         if (localClass == Byte.class)
/* 1770 */           localObject = Integer.valueOf(-((Number)localObject).byteValue());
/* 1771 */         else if (localClass == Short.class)
/* 1772 */           localObject = Integer.valueOf(-((Number)localObject).shortValue());
/* 1773 */         else if (localClass == Integer.class)
/* 1774 */           localObject = Integer.valueOf(-((Number)localObject).intValue());
/* 1775 */         else if (localClass == Long.class)
/* 1776 */           localObject = Long.valueOf(-((Number)localObject).longValue());
/* 1777 */         else if (localClass == Float.class)
/* 1778 */           localObject = Float.valueOf(-((Number)localObject).floatValue());
/* 1779 */         else if (localClass == Double.class)
/* 1780 */           localObject = Double.valueOf(-((Number)localObject).doubleValue());
/*      */         else {
/* 1782 */           throw new UnsupportedOperationException();
/*      */         }
/*      */ 
/* 1785 */         return localObject;
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public UnaryExpression negate(Number paramNumber)
/*      */   {
/* 1796 */     return negate(new LiteralExpression(paramNumber));
/*      */   }
/*      */ 
/*      */   public static UnaryExpression not(Expression paramExpression)
/*      */   {
/* 1805 */     return new UnaryExpression(paramExpression)
/*      */     {
/*      */       public String getOperator() {
/* 1808 */         return "!";
/*      */       }
/*      */ 
/*      */       protected Object evaluate()
/*      */       {
/* 1813 */         return Boolean.valueOf(!((Boolean)getOperand().getValue()).booleanValue());
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static UnaryExpression not(Boolean paramBoolean)
/*      */   {
/* 1824 */     return not(new LiteralExpression(paramBoolean));
/*      */   }
/*      */ 
/*      */   public static Expression valueOf(String paramString)
/*      */   {
/* 1835 */     return valueOf(paramString, null);
/*      */   }
/*      */ 
/*      */   public static Expression valueOf(String paramString, Object paramObject)
/*      */   {
/* 1849 */     if (paramString == null) {
/* 1850 */       throw new NullPointerException();
/* 1853 */     }
/*      */ Parser localParser = new Parser(paramObject);
/*      */     Expression localExpression;
/*      */     try {
/* 1856 */       localExpression = localParser.parse(new StringReader(paramString));
/*      */     } catch (IOException localIOException) {
/* 1858 */       throw new RuntimeException(localIOException);
/*      */     }
/*      */ 
/* 1861 */     return localExpression;
/*      */   }
/*      */ 
/*      */   private class ValueProperty extends ReadOnlyObjectPropertyBase<Object>
/*      */   {
/*  541 */     private Object value = null;
/*  542 */     private int listenerCount = 0;
/*      */ 
/*      */     private ValueProperty() {
/*      */     }
/*  546 */     public Object getBean() { return Expression.this; }
/*      */ 
/*      */ 
/*      */     public String getName()
/*      */     {
/*  551 */       return "value";
/*      */     }
/*      */ 
/*      */     public Object get()
/*      */     {
/*      */       Object localObject;
/*  557 */       if (this.listenerCount == 0) {
/*  558 */         localObject = Expression.this.evaluate();
/*      */       } else {
/*  560 */         validate();
/*  561 */         localObject = this.value;
/*      */       }
/*      */ 
/*  564 */       return localObject;
/*      */     }
/*      */ 
/*      */     public void invalidate() {
/*  568 */       Expression.this.valid = false;
/*      */ 
/*  570 */       fireValueChangedEvent();
/*      */     }
/*      */ 
/*      */     public void validate() {
/*  574 */       if (!Expression.this.valid) {
/*  575 */         this.value = Expression.this.evaluate();
/*      */       }
/*      */ 
/*  578 */       Expression.this.valid = true;
/*      */     }
/*      */ 
/*      */     public void addListener(ChangeListener<? super Object> paramChangeListener)
/*      */     {
/*  583 */       if (this.listenerCount == 0) {
/*  584 */         Expression.this.registerChangeListeners();
/*      */       }
/*      */ 
/*  587 */       super.addListener(paramChangeListener);
/*  588 */       this.listenerCount += 1;
/*      */     }
/*      */ 
/*      */     public void removeListener(ChangeListener<? super Object> paramChangeListener)
/*      */     {
/*  593 */       super.removeListener(paramChangeListener);
/*  594 */       this.listenerCount -= 1;
/*      */ 
/*  596 */       if (this.listenerCount == 0)
/*  597 */         Expression.this.unregisterChangeListeners();
/*      */     }
/*      */ 
/*      */     protected void fireValueChangedEvent()
/*      */     {
/*  604 */       if (!Expression.this.updating) {
/*  605 */         Expression.this.updating = true;
/*      */ 
/*  607 */         super.fireValueChangedEvent();
/*      */ 
/*  609 */         Expression.this.updating = false;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class Parser
/*      */   {
/*      */     public static final int MAX_PRIORITY = 6;
/*      */     private Object namespace;
/*   59 */     private int c = -1;
/*   60 */     private char[] pushbackBuffer = new char[6];
/*      */     public static final int PUSHBACK_BUFFER_SIZE = 6;
/*      */ 
/*      */     public Parser(Object paramObject)
/*      */     {
/*   65 */       this.namespace = paramObject;
/*      */     }
/*      */ 
/*      */     public Expression parse(Reader paramReader) throws IOException
/*      */     {
/*   70 */       LinkedList localLinkedList1 = tokenize(new PushbackReader(paramReader, 6));
/*      */ 
/*   72 */       LinkedList localLinkedList2 = new LinkedList();
/*      */ 
/*   74 */       for (Token localToken : localLinkedList1)
/*      */       {
/*      */         Object localObject;
/*      */         String str;
/*      */         Expression localExpression1;
/*   76 */         switch (Expression.16.$SwitchMap$com$sun$javafx$fxml$expression$Expression$Parser$TokenType[localToken.type.ordinal()]) {
/*      */         case 1:
/*   78 */           localObject = new LiteralExpression(localToken.value);
/*   79 */           break;
/*      */         case 2:
/*   83 */           localObject = new VariableExpression((List)localToken.value, this.namespace);
/*   84 */           break;
/*      */         case 3:
/*   88 */           str = (String)localToken.value;
/*   89 */           localExpression1 = (Expression)localLinkedList2.pop();
/*      */ 
/*   91 */           if (str.equals("-"))
/*   92 */             localObject = Expression.negate(localExpression1);
/*   93 */           else if (str.equals("!"))
/*   94 */             localObject = Expression.not(localExpression1);
/*      */           else {
/*   96 */             throw new UnsupportedOperationException();
/*      */           }
/*      */ 
/*      */           break;
/*      */         case 4:
/*  103 */           str = (String)localToken.value;
/*  104 */           localExpression1 = (Expression)localLinkedList2.pop();
/*  105 */           Expression localExpression2 = (Expression)localLinkedList2.pop();
/*      */ 
/*  107 */           if (str.equals("+"))
/*  108 */             localObject = Expression.add(localExpression2, localExpression1);
/*  109 */           else if (str.equals("-"))
/*  110 */             localObject = Expression.subtract(localExpression2, localExpression1);
/*  111 */           else if (str.equals("*"))
/*  112 */             localObject = Expression.multiply(localExpression2, localExpression1);
/*  113 */           else if (str.equals("/"))
/*  114 */             localObject = Expression.divide(localExpression2, localExpression1);
/*  115 */           else if (str.equals("%"))
/*  116 */             localObject = Expression.modulo(localExpression2, localExpression1);
/*  117 */           else if (str.equals(">"))
/*  118 */             localObject = Expression.greaterThan(localExpression2, localExpression1);
/*  119 */           else if (str.equals(">="))
/*  120 */             localObject = Expression.greaterThanOrEqualTo(localExpression2, localExpression1);
/*  121 */           else if (str.equals("<"))
/*  122 */             localObject = Expression.lessThan(localExpression2, localExpression1);
/*  123 */           else if (str.equals("<="))
/*  124 */             localObject = Expression.lessThanOrEqualTo(localExpression2, localExpression1);
/*  125 */           else if (str.equals("=="))
/*  126 */             localObject = Expression.equalTo(localExpression2, localExpression1);
/*  127 */           else if (str.equals("!="))
/*  128 */             localObject = Expression.notEqualTo(localExpression2, localExpression1);
/*  129 */           else if (str.equals("&&"))
/*  130 */             localObject = Expression.and(localExpression2, localExpression1);
/*  131 */           else if (str.equals("||"))
/*  132 */             localObject = Expression.or(localExpression2, localExpression1);
/*      */           else {
/*  134 */             throw new UnsupportedOperationException();
/*      */           }
/*      */ 
/*      */           break;
/*      */         default:
/*  141 */           throw new UnsupportedOperationException();
/*      */         }
/*      */ 
/*  145 */         localLinkedList2.push(localObject);
/*      */       }
/*      */ 
/*  148 */       if (localLinkedList2.size() != 1) {
/*  149 */         throw new IllegalArgumentException("Invalid expression.");
/*      */       }
/*      */ 
/*  152 */       return (Expression)localLinkedList2.peek();
/*      */     }
/*      */ 
/*      */     private LinkedList<Token> tokenize(PushbackReader paramPushbackReader) throws IOException
/*      */     {
/*  157 */       LinkedList localLinkedList1 = new LinkedList();
/*  158 */       LinkedList localLinkedList2 = new LinkedList();
/*      */ 
/*  160 */       this.c = paramPushbackReader.read();
/*  161 */       int i = 1;
/*      */ 
/*  163 */       while (this.c != -1)
/*      */       {
/*  165 */         while ((this.c != -1) && (Character.isWhitespace(this.c))) {
/*  166 */           this.c = paramPushbackReader.read();
/*      */         }
/*      */ 
/*  169 */         if (this.c != -1)
/*      */         {
/*      */           Token localToken;
/*  172 */           if (this.c == 110) {
/*  173 */             if (readKeyword(paramPushbackReader, "null"))
/*  174 */               localToken = new Token(TokenType.LITERAL, null);
/*      */             else
/*  176 */               localToken = new Token(TokenType.VARIABLE, readPath(paramPushbackReader));
/*      */           }
/*      */           else
/*      */           {
/*      */             StringBuilder localStringBuilder;
/*      */             int k;
/*      */             Object localObject;
/*  178 */             if ((this.c == 34) || (this.c == 39)) {
/*  179 */               localStringBuilder = new StringBuilder();
/*      */ 
/*  182 */               k = this.c;
/*      */ 
/*  185 */               this.c = paramPushbackReader.read();
/*      */ 
/*  187 */               while ((this.c != -1) && (this.c != k)) {
/*  188 */                 if (!Character.isISOControl(this.c)) {
/*  189 */                   if (this.c == 92) {
/*  190 */                     this.c = paramPushbackReader.read();
/*      */ 
/*  192 */                     if (this.c == 98) {
/*  193 */                       this.c = 8;
/*  194 */                     } else if (this.c == 102) {
/*  195 */                       this.c = 12;
/*  196 */                     } else if (this.c == 110) {
/*  197 */                       this.c = 10;
/*  198 */                     } else if (this.c == 114) {
/*  199 */                       this.c = 13;
/*  200 */                     } else if (this.c == 116) {
/*  201 */                       this.c = 9;
/*  202 */                     } else if (this.c == 117) {
/*  203 */                       localObject = new StringBuilder();
/*  204 */                       while (((StringBuilder)localObject).length() < 4) {
/*  205 */                         this.c = paramPushbackReader.read();
/*  206 */                         ((StringBuilder)localObject).append((char)this.c);
/*      */                       }
/*      */ 
/*  209 */                       String str = ((StringBuilder)localObject).toString();
/*  210 */                       this.c = ((char)Integer.parseInt(str, 16));
/*      */                     }
/*  212 */                     else if ((this.c != 92) && (this.c != 47) && (this.c != 34) && (this.c != 39) && (this.c != k))
/*      */                     {
/*  217 */                       throw new IllegalArgumentException("Unsupported escape sequence.");
/*      */                     }
/*      */ 
/*      */                   }
/*      */ 
/*  222 */                   localStringBuilder.append((char)this.c);
/*      */                 }
/*      */ 
/*  225 */                 this.c = paramPushbackReader.read();
/*      */               }
/*      */ 
/*  228 */               if (this.c != k) {
/*  229 */                 throw new IllegalArgumentException("Unterminated string.");
/*      */               }
/*      */ 
/*  233 */               this.c = paramPushbackReader.read();
/*      */ 
/*  235 */               localToken = new Token(TokenType.LITERAL, localStringBuilder.toString());
/*  236 */             } else if (Character.isDigit(this.c)) {
/*  237 */               localStringBuilder = new StringBuilder();
/*  238 */               k = 1;
/*      */ 
/*  240 */               while ((this.c != -1) && ((Character.isDigit(this.c)) || (this.c == 46) || (this.c == 101) || (this.c == 69)))
/*      */               {
/*  242 */                 localStringBuilder.append((char)this.c);
/*  243 */                 k &= (this.c != 46 ? 1 : 0);
/*  244 */                 this.c = paramPushbackReader.read();
/*      */               }
/*      */ 
/*  248 */               if (k != 0)
/*  249 */                 localObject = Long.valueOf(Long.parseLong(localStringBuilder.toString()));
/*      */               else {
/*  251 */                 localObject = Double.valueOf(Double.parseDouble(localStringBuilder.toString()));
/*      */               }
/*      */ 
/*  254 */               localToken = new Token(TokenType.LITERAL, localObject);
/*  255 */             } else if (this.c == 116) {
/*  256 */               if (readKeyword(paramPushbackReader, "true"))
/*  257 */                 localToken = new Token(TokenType.LITERAL, Boolean.valueOf(true));
/*      */               else
/*  259 */                 localToken = new Token(TokenType.VARIABLE, readPath(paramPushbackReader));
/*      */             }
/*  261 */             else if (this.c == 102) {
/*  262 */               if (readKeyword(paramPushbackReader, "false"))
/*  263 */                 localToken = new Token(TokenType.LITERAL, Boolean.valueOf(false));
/*      */               else
/*  265 */                 localToken = new Token(TokenType.VARIABLE, readPath(paramPushbackReader));
/*      */             }
/*  267 */             else if (Character.isJavaIdentifierStart(this.c)) {
/*  268 */               paramPushbackReader.unread(this.c);
/*  269 */               localToken = new Token(TokenType.VARIABLE, readPath(paramPushbackReader));
/*      */             } else {
/*  271 */               if ((this.c == "-".charAt(0)) && (i != 0)) {
/*  272 */                 localToken = new Token(TokenType.UNARY_OPERATOR, "-");
/*  273 */               } else if ((this.c == "!".charAt(0)) && (i != 0)) {
/*  274 */                 localToken = new Token(TokenType.UNARY_OPERATOR, "!");
/*  275 */               } else if (this.c == "+".charAt(0)) {
/*  276 */                 localToken = new Token(TokenType.BINARY_OPERATOR, "+");
/*  277 */               } else if (this.c == "-".charAt(0)) {
/*  278 */                 localToken = new Token(TokenType.BINARY_OPERATOR, "-");
/*  279 */               } else if (this.c == "*".charAt(0)) {
/*  280 */                 localToken = new Token(TokenType.BINARY_OPERATOR, "*");
/*  281 */               } else if (this.c == "/".charAt(0)) {
/*  282 */                 localToken = new Token(TokenType.BINARY_OPERATOR, "/");
/*  283 */               } else if (this.c == "%".charAt(0)) {
/*  284 */                 localToken = new Token(TokenType.BINARY_OPERATOR, "%");
/*  285 */               } else if (this.c == "==".charAt(0)) {
/*  286 */                 this.c = paramPushbackReader.read();
/*      */ 
/*  288 */                 if (this.c == "==".charAt(1))
/*  289 */                   localToken = new Token(TokenType.BINARY_OPERATOR, "==");
/*      */                 else
/*  291 */                   throw new IllegalArgumentException();
/*      */               }
/*  293 */               else if (this.c == "!=".charAt(0)) {
/*  294 */                 this.c = paramPushbackReader.read();
/*      */ 
/*  296 */                 if (this.c == "!=".charAt(1))
/*  297 */                   localToken = new Token(TokenType.BINARY_OPERATOR, "!=");
/*      */                 else
/*  299 */                   throw new IllegalArgumentException();
/*      */               }
/*  301 */               else if (this.c == ">".charAt(0)) {
/*  302 */                 this.c = paramPushbackReader.read();
/*      */ 
/*  304 */                 if (this.c == ">=".charAt(1))
/*  305 */                   localToken = new Token(TokenType.BINARY_OPERATOR, ">=");
/*      */                 else
/*  307 */                   localToken = new Token(TokenType.BINARY_OPERATOR, ">");
/*      */               }
/*  309 */               else if (this.c == "<".charAt(0)) {
/*  310 */                 this.c = paramPushbackReader.read();
/*      */ 
/*  312 */                 if (this.c == "<=".charAt(1))
/*  313 */                   localToken = new Token(TokenType.BINARY_OPERATOR, "<=");
/*      */                 else
/*  315 */                   localToken = new Token(TokenType.BINARY_OPERATOR, "<");
/*      */               }
/*  317 */               else if (this.c == "&&".charAt(0)) {
/*  318 */                 this.c = paramPushbackReader.read();
/*      */ 
/*  320 */                 if (this.c == "&&".charAt(0))
/*  321 */                   localToken = new Token(TokenType.BINARY_OPERATOR, "&&");
/*      */                 else
/*  323 */                   throw new IllegalArgumentException();
/*      */               }
/*  325 */               else if (this.c == "||".charAt(0)) {
/*  326 */                 this.c = paramPushbackReader.read();
/*      */ 
/*  328 */                 if (this.c == "||".charAt(0))
/*  329 */                   localToken = new Token(TokenType.BINARY_OPERATOR, "||");
/*      */                 else
/*  331 */                   throw new IllegalArgumentException();
/*      */               }
/*  333 */               else if (this.c == 40) {
/*  334 */                 localToken = new Token(TokenType.BEGIN_GROUP, "(");
/*  335 */               } else if (this.c == 41) {
/*  336 */                 localToken = new Token(TokenType.END_GROUP, ")");
/*      */               } else {
/*  338 */                 throw new IllegalArgumentException("Unexpected character in expression.");
/*      */               }
/*      */ 
/*  341 */               this.c = paramPushbackReader.read();
/*      */             }
/*      */           }
/*      */ 
/*  345 */           switch (Expression.16.$SwitchMap$com$sun$javafx$fxml$expression$Expression$Parser$TokenType[localToken.type.ordinal()]) {
/*      */           case 1:
/*      */           case 2:
/*  348 */             localLinkedList1.add(localToken);
/*  349 */             break;
/*      */           case 3:
/*      */           case 4:
/*  354 */             int j = getPriority((String)localToken.value);
/*      */ 
/*  359 */             while ((!localLinkedList2.isEmpty()) && (((Token)localLinkedList2.peek()).type != TokenType.BEGIN_GROUP) && (getPriority((String)((Token)localLinkedList2.peek()).value) >= j) && (getPriority((String)((Token)localLinkedList2.peek()).value) != 6)) {
/*  360 */               localLinkedList1.add(localLinkedList2.removeFirst());
/*      */             }
/*      */ 
/*  363 */             localLinkedList2.addFirst(localToken);
/*  364 */             break;
/*      */           case 5:
/*  368 */             localLinkedList2.push(localToken);
/*  369 */             break;
/*      */           case 6:
/*  373 */             for (localToken = (Token)localLinkedList2.pop(); localToken.type != TokenType.BEGIN_GROUP; localToken = (Token)localLinkedList2.pop()) {
/*  374 */               localLinkedList1.add(localToken);
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*  381 */           i = (localToken.type != TokenType.LITERAL) && (localToken.type != TokenType.VARIABLE) ? 1 : 0;
/*      */         }
/*      */       }
/*      */ 
/*  385 */       while (!localLinkedList2.isEmpty()) {
/*  386 */         localLinkedList1.add(localLinkedList2.pop());
/*      */       }
/*      */ 
/*  389 */       return localLinkedList1;
/*      */     }
/*      */ 
/*      */     private boolean readKeyword(PushbackReader paramPushbackReader, String paramString) throws IOException {
/*  393 */       int i = paramString.length();
/*  394 */       int j = 0;
/*      */ 
/*  396 */       while ((this.c != -1) && (j < i)) {
/*  397 */         this.pushbackBuffer[j] = ((char)this.c);
/*  398 */         if (paramString.charAt(j) != this.c)
/*      */         {
/*      */           break;
/*      */         }
/*  402 */         this.c = paramPushbackReader.read();
/*  403 */         j++;
/*      */       }
/*      */       boolean bool;
/*  407 */       if (j < i) {
/*  408 */         if (this.namespace != null) {
/*  409 */           paramPushbackReader.unread(this.pushbackBuffer, 0, j + 1);
/*  410 */           bool = false;
/*      */         } else {
/*  412 */           throw new IllegalArgumentException();
/*      */         }
/*      */       }
/*  415 */       else bool = true;
/*      */ 
/*  418 */       return bool;
/*      */     }
/*      */ 
/*      */     private List<String> readPath(Reader paramReader) throws IOException {
/*  422 */       ArrayList localArrayList = new ArrayList();
/*      */ 
/*  424 */       this.c = paramReader.read();
/*      */ 
/*  426 */       while ((this.c != -1) && ((Character.isJavaIdentifierStart(this.c)) || (this.c == 91))) {
/*  427 */         StringBuilder localStringBuilder = new StringBuilder();
/*      */ 
/*  429 */         int i = this.c == 91 ? 1 : 0;
/*      */ 
/*  431 */         if (i != 0) {
/*  432 */           this.c = paramReader.read();
/*  433 */           int j = (this.c == 34) || (this.c == 39) ? 1 : 0;
/*      */           int k;
/*  436 */           if (j != 0) {
/*  437 */             k = (char)this.c;
/*  438 */             this.c = paramReader.read();
/*      */           } else {
/*  440 */             k = 0;
/*      */           }
/*      */ 
/*  444 */           while ((this.c != -1) && (i != 0)) {
/*  445 */             if (Character.isISOControl(this.c)) {
/*  446 */               throw new IllegalArgumentException("Illegal identifier character.");
/*      */             }
/*      */ 
/*  449 */             if ((j == 0) && (!Character.isDigit(this.c)))
/*      */             {
/*  451 */               throw new IllegalArgumentException("Illegal character in index value.");
/*      */             }
/*      */ 
/*  454 */             localStringBuilder.append((char)this.c);
/*  455 */             this.c = paramReader.read();
/*      */ 
/*  457 */             if (j != 0) {
/*  458 */               j = this.c != k ? 1 : 0;
/*      */ 
/*  460 */               if (j == 0) {
/*  461 */                 this.c = paramReader.read();
/*      */               }
/*      */             }
/*      */ 
/*  465 */             i = this.c != 93 ? 1 : 0;
/*      */           }
/*      */ 
/*  468 */           if (j != 0) {
/*  469 */             throw new IllegalArgumentException("Unterminated quoted identifier.");
/*      */           }
/*      */ 
/*  472 */           if (i != 0) {
/*  473 */             throw new IllegalArgumentException("Unterminated bracketed identifier.");
/*      */           }
/*      */ 
/*  476 */           this.c = paramReader.read();
/*      */         } else {
/*  478 */           while ((this.c != -1) && (this.c != 46) && (this.c != 91) && (Character.isJavaIdentifierPart(this.c))) {
/*  479 */             localStringBuilder.append((char)this.c);
/*  480 */             this.c = paramReader.read();
/*      */           }
/*      */         }
/*      */ 
/*  484 */         if (this.c == 46) {
/*  485 */           this.c = paramReader.read();
/*      */ 
/*  487 */           if (this.c == -1) {
/*  488 */             throw new IllegalArgumentException("Illegal terminator character.");
/*      */           }
/*      */         }
/*      */ 
/*  492 */         if (localStringBuilder.length() == 0) {
/*  493 */           throw new IllegalArgumentException("Missing identifier.");
/*      */         }
/*      */ 
/*  496 */         localArrayList.add(localStringBuilder.toString());
/*      */       }
/*      */ 
/*  499 */       if (localArrayList.size() == 0) {
/*  500 */         throw new IllegalArgumentException("Invalid path.");
/*      */       }
/*      */ 
/*  503 */       return localArrayList;
/*      */     }
/*      */ 
/*      */     private int getPriority(String paramString)
/*      */     {
/*      */       int i;
/*  509 */       if ((paramString.equals("-")) || (paramString.equals("!")))
/*      */       {
/*  511 */         i = 6;
/*  512 */       } else if ((paramString.equals("*")) || (paramString.equals("/")) || (paramString.equals("%")))
/*      */       {
/*  515 */         i = 5;
/*  516 */       } else if ((paramString.equals("+")) || (paramString.equals("-")))
/*      */       {
/*  518 */         i = 4;
/*  519 */       } else if ((paramString.equals(">")) || (paramString.equals(">=")) || (paramString.equals("<")) || (paramString.equals("<=")))
/*      */       {
/*  523 */         i = 3;
/*  524 */       } else if ((paramString.equals("==")) || (paramString.equals("!=")))
/*      */       {
/*  526 */         i = 2;
/*  527 */       } else if (paramString.equals("&&"))
/*  528 */         i = 1;
/*  529 */       else if (paramString.equals("||"))
/*  530 */         i = 0;
/*      */       else {
/*  532 */         throw new IllegalArgumentException();
/*      */       }
/*      */ 
/*  535 */       return i;
/*      */     }
/*      */ 
/*      */     public static enum TokenType
/*      */     {
/*   47 */       LITERAL, 
/*   48 */       VARIABLE, 
/*   49 */       UNARY_OPERATOR, 
/*   50 */       BINARY_OPERATOR, 
/*   51 */       BEGIN_GROUP, 
/*   52 */       END_GROUP;
/*      */     }
/*      */ 
/*      */     public static class Token
/*      */     {
/*      */       public final Expression.Parser.TokenType type;
/*      */       public final Object value;
/*      */ 
/*      */       public Token(Expression.Parser.TokenType paramTokenType, Object paramObject)
/*      */       {
/*   33 */         this.type = paramTokenType;
/*   34 */         this.value = paramObject;
/*      */       }
/*      */ 
/*      */       public String toString()
/*      */       {
/*   42 */         return this.value.toString();
/*      */       }
/*      */     }
/*      */   }
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.fxml.expression.Expression
 * JD-Core Version:    0.6.2
 */