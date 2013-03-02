/*      */ package com.sun.javafx.css.parser;
/*      */ 
/*      */ import com.sun.javafx.Logging;
/*      */ import com.sun.javafx.Utils;
/*      */ import com.sun.javafx.css.Combinator;
/*      */ import com.sun.javafx.css.CompoundSelector;
/*      */ import com.sun.javafx.css.CssError;
/*      */ import com.sun.javafx.css.CssError.InlineStyleParsingError;
/*      */ import com.sun.javafx.css.CssError.StringParsingError;
/*      */ import com.sun.javafx.css.CssError.StylesheetParsingError;
/*      */ import com.sun.javafx.css.Declaration;
/*      */ import com.sun.javafx.css.FontUnits.Style;
/*      */ import com.sun.javafx.css.FontUnits.Weight;
/*      */ import com.sun.javafx.css.ParsedValue;
/*      */ import com.sun.javafx.css.Rule;
/*      */ import com.sun.javafx.css.Selector;
/*      */ import com.sun.javafx.css.SimpleSelector;
/*      */ import com.sun.javafx.css.Size;
/*      */ import com.sun.javafx.css.SizeUnits;
/*      */ import com.sun.javafx.css.StyleManager;
/*      */ import com.sun.javafx.css.Styleable;
/*      */ import com.sun.javafx.css.Stylesheet;
/*      */ import com.sun.javafx.css.converters.BooleanConverter;
/*      */ import com.sun.javafx.css.converters.EffectConverter.DropShadowConverter;
/*      */ import com.sun.javafx.css.converters.EffectConverter.InnerShadowConverter;
/*      */ import com.sun.javafx.css.converters.EnumConverter;
/*      */ import com.sun.javafx.css.converters.FontConverter;
/*      */ import com.sun.javafx.css.converters.FontConverter.SizeConverter;
/*      */ import com.sun.javafx.css.converters.FontConverter.StyleConverter;
/*      */ import com.sun.javafx.css.converters.FontConverter.WeightConverter;
/*      */ import com.sun.javafx.css.converters.InsetsConverter;
/*      */ import com.sun.javafx.css.converters.InsetsConverter.SequenceConverter;
/*      */ import com.sun.javafx.css.converters.PaintConverter.LinearGradientConverter;
/*      */ import com.sun.javafx.css.converters.PaintConverter.RadialGradientConverter;
/*      */ import com.sun.javafx.css.converters.PaintConverter.SequenceConverter;
/*      */ import com.sun.javafx.css.converters.SizeConverter;
/*      */ import com.sun.javafx.css.converters.SizeConverter.SequenceConverter;
/*      */ import com.sun.javafx.css.converters.StringConverter;
/*      */ import com.sun.javafx.css.converters.URLConverter;
/*      */ import com.sun.javafx.css.converters.URLConverter.SequenceConverter;
/*      */ import com.sun.javafx.logging.PlatformLogger;
/*      */ import com.sun.javafx.scene.layout.region.BackgroundImage.BackgroundPosition;
/*      */ import com.sun.javafx.scene.layout.region.BackgroundImage.BackgroundPositionConverter;
/*      */ import com.sun.javafx.scene.layout.region.BackgroundImage.BackgroundRepeat;
/*      */ import com.sun.javafx.scene.layout.region.BackgroundImage.BackgroundRepeatConverter;
/*      */ import com.sun.javafx.scene.layout.region.BackgroundImage.BackgroundSize;
/*      */ import com.sun.javafx.scene.layout.region.BackgroundImage.BackgroundSizeConverter;
/*      */ import com.sun.javafx.scene.layout.region.BackgroundImage.LayeredBackgroundPositionConverter;
/*      */ import com.sun.javafx.scene.layout.region.BackgroundImage.LayeredBackgroundSizeConverter;
/*      */ import com.sun.javafx.scene.layout.region.BorderImage.BorderImageRepeat;
/*      */ import com.sun.javafx.scene.layout.region.BorderImage.BorderImageSlice;
/*      */ import com.sun.javafx.scene.layout.region.BorderImage.RepeatConverter;
/*      */ import com.sun.javafx.scene.layout.region.BorderImage.SliceConverter;
/*      */ import com.sun.javafx.scene.layout.region.BorderImage.SliceSequenceConverter;
/*      */ import com.sun.javafx.scene.layout.region.BorderStyle;
/*      */ import com.sun.javafx.scene.layout.region.Margins;
/*      */ import com.sun.javafx.scene.layout.region.Margins.Converter;
/*      */ import com.sun.javafx.scene.layout.region.Margins.SequenceConverter;
/*      */ import com.sun.javafx.scene.layout.region.Repeat;
/*      */ import com.sun.javafx.scene.layout.region.StrokeBorder.BorderPaintConverter;
/*      */ import com.sun.javafx.scene.layout.region.StrokeBorder.BorderStyleConverter;
/*      */ import com.sun.javafx.scene.layout.region.StrokeBorder.BorderStyleSequenceConverter;
/*      */ import com.sun.javafx.scene.layout.region.StrokeBorder.LayeredBorderPaintConverter;
/*      */ import com.sun.javafx.scene.layout.region.StrokeBorder.LayeredBorderStyleConverter;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.CharArrayReader;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.Reader;
/*      */ import java.net.URL;
/*      */ import java.text.MessageFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import javafx.collections.ObservableList;
/*      */ import javafx.geometry.Insets;
/*      */ import javafx.scene.effect.BlurType;
/*      */ import javafx.scene.paint.Color;
/*      */ import javafx.scene.paint.CycleMethod;
/*      */ import javafx.scene.paint.Paint;
/*      */ import javafx.scene.paint.Stop;
/*      */ import javafx.scene.shape.StrokeLineCap;
/*      */ import javafx.scene.shape.StrokeLineJoin;
/*      */ import javafx.scene.shape.StrokeType;
/*      */ import javafx.scene.text.Font;
/*      */ import javafx.scene.text.FontPosture;
/*      */ import javafx.scene.text.FontWeight;
/*      */ 
/*      */ public final class CSSParser
/*      */ {
/*   77 */   static boolean EXIT_ON_ERROR = false;
/*      */   private String stylesheetAsText;
/*      */   private URL sourceOfStylesheet;
/*      */   private Styleable sourceOfInlineStyle;
/*  134 */   private static final PlatformLogger LOGGER = Logging.getCSSLogger();
/*      */   private final Map<String, String> properties;
/* 2226 */   private static final ParsedValue<Size, Size> ZERO_PERCENT = new ParsedValue(new Size(0.0D, SizeUnits.PERCENT), null);
/*      */ 
/* 2229 */   private static final ParsedValue<Size, Size> FIFTY_PERCENT = new ParsedValue(new Size(50.0D, SizeUnits.PERCENT), null);
/*      */ 
/* 2232 */   private static final ParsedValue<Size, Size> ONE_HUNDRED_PERCENT = new ParsedValue(new Size(100.0D, SizeUnits.PERCENT), null);
/*      */ 
/* 2910 */   private static final ParsedValue<ParsedValue<?, Size>[], Double[]> DASHED = new ParsedValue(new ParsedValue[] { new ParsedValue(new Size(5.0D, SizeUnits.PX), null), new ParsedValue(new Size(3.0D, SizeUnits.PX), null) }, SizeConverter.SequenceConverter.getInstance());
/*      */ 
/* 2917 */   private static final ParsedValue<ParsedValue<?, Size>[], Double[]> DOTTED = new ParsedValue(new ParsedValue[] { new ParsedValue(new Size(1.0D, SizeUnits.PX), null), new ParsedValue(new Size(3.0D, SizeUnits.PX), null) }, SizeConverter.SequenceConverter.getInstance());
/*      */ 
/* 2924 */   private static final ParsedValue<ParsedValue<?, Size>[], Double[]> SOLID = new ParsedValue(new ParsedValue[0], SizeConverter.SequenceConverter.getInstance());
/*      */ 
/* 2931 */   private static final ParsedValue<ParsedValue<?, Size>[], Double[]> NONE = new ParsedValue(null, SizeConverter.SequenceConverter.getInstance());
/*      */ 
/* 3393 */   Token currentToken = null;
/*      */ 
/*      */   public static CSSParser getInstance()
/*      */   {
/*   85 */     return InstanceHolder.INSTANCE;
/*      */   }
/*      */ 
/*      */   private CSSParser() {
/*   89 */     this.properties = new HashMap();
/*      */   }
/*      */ 
/*      */   private void setInputSource(URL paramURL)
/*      */   {
/*  108 */     setInputSource(paramURL, null);
/*      */   }
/*      */ 
/*      */   private void setInputSource(URL paramURL, String paramString)
/*      */   {
/*  113 */     this.stylesheetAsText = paramString;
/*  114 */     this.sourceOfStylesheet = paramURL;
/*  115 */     this.sourceOfInlineStyle = null;
/*      */   }
/*      */ 
/*      */   private void setInputSource(String paramString)
/*      */   {
/*  120 */     this.stylesheetAsText = paramString;
/*  121 */     this.sourceOfStylesheet = null;
/*  122 */     this.sourceOfInlineStyle = null;
/*      */   }
/*      */ 
/*      */   private void setInputSource(Styleable paramStyleable)
/*      */   {
/*  127 */     this.stylesheetAsText = (paramStyleable != null ? paramStyleable.getStyle() : null);
/*  128 */     this.sourceOfStylesheet = null;
/*  129 */     this.sourceOfInlineStyle = paramStyleable;
/*      */   }
/*      */ 
/*      */   public Stylesheet parse(String paramString)
/*      */   {
/*  176 */     Stylesheet localStylesheet = new Stylesheet();
/*  177 */     if ((paramString != null) && (!paramString.trim().isEmpty())) {
/*  178 */       setInputSource(paramString);
/*  179 */       CharArrayReader localCharArrayReader = new CharArrayReader(paramString.toCharArray());
/*  180 */       parse(localStylesheet, localCharArrayReader);
/*      */     }
/*  182 */     return localStylesheet;
/*      */   }
/*      */ 
/*      */   public Stylesheet parse(String paramString1, String paramString2)
/*      */     throws IOException
/*      */   {
/*  193 */     URL localURL = new URL(paramString1);
/*  194 */     Stylesheet localStylesheet = new Stylesheet(localURL);
/*  195 */     if ((paramString2 != null) && (!paramString2.trim().isEmpty())) {
/*  196 */       setInputSource(localURL, paramString2);
/*  197 */       CharArrayReader localCharArrayReader = new CharArrayReader(paramString2.toCharArray());
/*  198 */       parse(localStylesheet, localCharArrayReader);
/*      */     }
/*  200 */     return localStylesheet;
/*      */   }
/*      */ 
/*      */   public Stylesheet parse(URL paramURL)
/*      */     throws IOException
/*      */   {
/*  213 */     Stylesheet localStylesheet = new Stylesheet(paramURL);
/*  214 */     if (paramURL != null) {
/*  215 */       setInputSource(paramURL);
/*  216 */       BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(paramURL.openStream()));
/*  217 */       parse(localStylesheet, localBufferedReader);
/*      */     }
/*  219 */     return localStylesheet;
/*      */   }
/*      */ 
/*      */   private void parse(Stylesheet paramStylesheet, Reader paramReader)
/*      */   {
/*  225 */     CSSLexer localCSSLexer = CSSLexer.getInstance();
/*  226 */     localCSSLexer.setReader(paramReader);
/*      */     try
/*      */     {
/*  229 */       parse(paramStylesheet, localCSSLexer);
/*  230 */       paramReader.close();
/*      */     }
/*      */     catch (IOException localIOException)
/*      */     {
/*      */     }
/*      */     catch (Exception localException)
/*      */     {
/*  237 */       reportException(localException);
/*      */     }
/*      */   }
/*      */ 
/*      */   public Stylesheet parseInlineStyle(Styleable paramStyleable)
/*      */   {
/*  245 */     Stylesheet localStylesheet = new Stylesheet();
/*      */ 
/*  247 */     Object localObject = paramStyleable != null ? paramStyleable.getStyle() : null;
/*  248 */     if ((localObject != null) && (!localObject.trim().isEmpty())) {
/*  249 */       setInputSource(paramStyleable);
/*  250 */       ArrayList localArrayList = new ArrayList();
/*  251 */       CharArrayReader localCharArrayReader = new CharArrayReader(localObject.toCharArray());
/*  252 */       CSSLexer localCSSLexer = CSSLexer.getInstance();
/*  253 */       localCSSLexer.setReader(localCharArrayReader);
/*      */       try {
/*  255 */         this.currentToken = nextToken(localCSSLexer);
/*  256 */         List localList = declarations(localCSSLexer);
/*  257 */         if ((localList != null) && (!localList.isEmpty())) {
/*  258 */           Selector localSelector = Selector.getUniversalSelector();
/*  259 */           Rule localRule = new Rule(Collections.singletonList(localSelector), localList);
/*      */ 
/*  263 */           localArrayList.add(localRule);
/*      */         }
/*  265 */         localCharArrayReader.close();
/*      */       }
/*      */       catch (IOException localIOException)
/*      */       {
/*      */       }
/*      */       catch (Exception localException)
/*      */       {
/*  272 */         reportException(localException);
/*      */       }
/*  274 */       localStylesheet.getRules().addAll(localArrayList);
/*      */     }
/*      */ 
/*  278 */     setInputSource((Styleable)null);
/*      */ 
/*  280 */     return localStylesheet;
/*      */   }
/*      */ 
/*      */   public ParsedValue parseExpr(String paramString1, String paramString2)
/*      */   {
/*  285 */     ParsedValue localParsedValue = null;
/*      */     try {
/*  287 */       setInputSource(null, new StringBuilder().append(paramString1).append(": ").append(paramString2).toString());
/*  288 */       char[] arrayOfChar = new char[paramString2.length() + 1];
/*  289 */       System.arraycopy(paramString2.toCharArray(), 0, arrayOfChar, 0, paramString2.length());
/*  290 */       arrayOfChar[(arrayOfChar.length - 1)] = ';';
/*      */ 
/*  292 */       CharArrayReader localCharArrayReader = new CharArrayReader(arrayOfChar);
/*  293 */       CSSLexer localCSSLexer = CSSLexer.getInstance();
/*  294 */       localCSSLexer.setReader(localCharArrayReader);
/*      */ 
/*  296 */       this.currentToken = nextToken(localCSSLexer);
/*  297 */       Term localTerm = expr(localCSSLexer);
/*  298 */       localParsedValue = valueFor(paramString1, localTerm);
/*      */ 
/*  300 */       localCharArrayReader.close();
/*      */     } catch (IOException localIOException) {
/*      */     } catch (ParseException localParseException) {
/*  303 */       if (LOGGER.isLoggable(900)) {
/*  304 */         LOGGER.warning(new StringBuilder().append("\"").append(paramString1).append(": ").append(paramString2).append("\" ").append(localParseException.toString()).toString());
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (Exception localException)
/*      */     {
/*  311 */       reportException(localException);
/*      */     }
/*  313 */     return localParsedValue;
/*      */   }
/*      */ 
/*      */   private CssError createError(String paramString)
/*      */   {
/*  405 */     Object localObject = null;
/*  406 */     if (this.sourceOfStylesheet != null)
/*  407 */       localObject = new CssError.StylesheetParsingError(this.sourceOfStylesheet, paramString);
/*  408 */     else if (this.sourceOfInlineStyle != null)
/*  409 */       localObject = new CssError.InlineStyleParsingError(this.sourceOfInlineStyle, paramString);
/*      */     else {
/*  411 */       localObject = new CssError.StringParsingError(this.stylesheetAsText, paramString);
/*      */     }
/*  413 */     return localObject;
/*      */   }
/*      */ 
/*      */   private void reportError(CssError paramCssError) {
/*  417 */     ObservableList localObservableList = null;
/*  418 */     if ((localObservableList = StyleManager.getInstance().getErrors()) != null)
/*  419 */       localObservableList.add(paramCssError);
/*      */   }
/*      */ 
/*      */   private void error(Term paramTerm, String paramString)
/*      */     throws CSSParser.ParseException
/*      */   {
/*  425 */     Token localToken = paramTerm != null ? paramTerm.token : null;
/*  426 */     ParseException localParseException = new ParseException(paramString, localToken, this);
/*  427 */     reportError(createError(localParseException.toString()));
/*  428 */     throw localParseException;
/*      */   }
/*      */ 
/*      */   private void reportException(Exception paramException)
/*      */   {
/*  433 */     if (LOGGER.isLoggable(900)) {
/*  434 */       StackTraceElement[] arrayOfStackTraceElement = paramException.getStackTrace();
/*  435 */       if (arrayOfStackTraceElement.length > 0) {
/*  436 */         StringBuilder localStringBuilder = new StringBuilder("Please report ");
/*      */ 
/*  438 */         localStringBuilder.append(paramException.getClass().getName()).append(" at:");
/*      */ 
/*  440 */         int i = 0;
/*  441 */         while (i < arrayOfStackTraceElement.length)
/*      */         {
/*  443 */           if (!getClass().getName().equals(arrayOfStackTraceElement[i].getClassName())) {
/*      */             break;
/*      */           }
/*  446 */           localStringBuilder.append("\n\t").append(arrayOfStackTraceElement[(i++)].toString());
/*      */         }
/*      */ 
/*  449 */         LOGGER.warning(localStringBuilder.toString());
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private String formatDeprecatedMessage(Term paramTerm, String paramString) {
/*  455 */     StringBuilder localStringBuilder = new StringBuilder("Using deprecated syntax for ");
/*      */ 
/*  457 */     localStringBuilder.append(paramString);
/*  458 */     if (this.sourceOfStylesheet != null) {
/*  459 */       localStringBuilder.append(" at ").append(this.sourceOfStylesheet).append("[").append(paramTerm.token.getLine()).append(',').append(paramTerm.token.getOffset()).append("]");
/*      */     }
/*      */ 
/*  467 */     localStringBuilder.append(". Refer to the CSS Reference Guide.");
/*  468 */     return localStringBuilder.toString();
/*      */   }
/*      */ 
/*      */   private ParsedValue<Color, Color> colorValueOfString(String paramString)
/*      */   {
/*  474 */     if ((paramString.startsWith("#")) || (paramString.startsWith("0x")))
/*      */     {
/*  476 */       double d = 1.0D;
/*  477 */       String str = paramString;
/*  478 */       int i = paramString.startsWith("#") ? 1 : 2;
/*      */ 
/*  480 */       int j = str.length();
/*      */ 
/*  482 */       if (j - i == 4) {
/*  483 */         d = Integer.parseInt(str.substring(j - 1), 16) / 15.0F;
/*  484 */         str = str.substring(0, j - 1);
/*  485 */       } else if (j - i == 8) {
/*  486 */         d = Integer.parseInt(str.substring(j - 2), 16) / 255.0F;
/*  487 */         str = str.substring(0, j - 2);
/*      */       }
/*      */ 
/*  490 */       return new ParsedValue(Color.web(str, d), null);
/*      */     }
/*      */     try
/*      */     {
/*  494 */       return new ParsedValue(Color.web(paramString), null);
/*      */     }
/*      */     catch (IllegalArgumentException localIllegalArgumentException) {
/*      */     }
/*      */     catch (NullPointerException localNullPointerException) {
/*      */     }
/*  500 */     return null;
/*      */   }
/*      */ 
/*      */   private String stripQuotes(String paramString) {
/*  504 */     return Utils.stripQuotes(paramString);
/*      */   }
/*      */ 
/*      */   private double clamp(double paramDouble1, double paramDouble2, double paramDouble3) {
/*  508 */     if (paramDouble2 < paramDouble1) return paramDouble1;
/*  509 */     if (paramDouble3 < paramDouble2) return paramDouble3;
/*  510 */     return paramDouble2;
/*      */   }
/*      */ 
/*      */   private boolean isSize(Token paramToken)
/*      */   {
/*  516 */     int i = paramToken.getType();
/*  517 */     switch (i) {
/*      */     case 13:
/*      */     case 14:
/*      */     case 15:
/*      */     case 16:
/*      */     case 17:
/*      */     case 18:
/*      */     case 19:
/*      */     case 20:
/*      */     case 21:
/*      */     case 22:
/*      */     case 23:
/*      */     case 24:
/*      */     case 25:
/*      */     case 26:
/*  532 */       return true;
/*      */     }
/*  534 */     return paramToken.getType() == 11;
/*      */   }
/*      */ 
/*      */   private Size size(Token paramToken) throws CSSParser.ParseException
/*      */   {
/*  539 */     SizeUnits localSizeUnits = SizeUnits.PX;
/*      */ 
/*  541 */     int i = 2;
/*  542 */     String str = paramToken.getText().trim();
/*  543 */     int j = str.length();
/*  544 */     int k = paramToken.getType();
/*  545 */     switch (k) {
/*      */     case 13:
/*  547 */       localSizeUnits = SizeUnits.PX;
/*  548 */       i = 0;
/*  549 */       break;
/*      */     case 22:
/*  551 */       localSizeUnits = SizeUnits.PERCENT;
/*  552 */       i = 1;
/*  553 */       break;
/*      */     case 15:
/*  555 */       localSizeUnits = SizeUnits.EM;
/*  556 */       break;
/*      */     case 16:
/*  558 */       localSizeUnits = SizeUnits.EX;
/*  559 */       break;
/*      */     case 21:
/*  561 */       localSizeUnits = SizeUnits.PX;
/*  562 */       break;
/*      */     case 14:
/*  564 */       localSizeUnits = SizeUnits.CM;
/*  565 */       break;
/*      */     case 18:
/*  567 */       localSizeUnits = SizeUnits.MM;
/*  568 */       break;
/*      */     case 17:
/*  570 */       localSizeUnits = SizeUnits.IN;
/*  571 */       break;
/*      */     case 20:
/*  573 */       localSizeUnits = SizeUnits.PT;
/*  574 */       break;
/*      */     case 19:
/*  576 */       localSizeUnits = SizeUnits.PC;
/*  577 */       break;
/*      */     case 23:
/*  579 */       localSizeUnits = SizeUnits.DEG;
/*  580 */       i = 3;
/*  581 */       break;
/*      */     case 24:
/*  583 */       localSizeUnits = SizeUnits.GRAD;
/*  584 */       i = 4;
/*  585 */       break;
/*      */     case 25:
/*  587 */       localSizeUnits = SizeUnits.RAD;
/*  588 */       i = 3;
/*  589 */       break;
/*      */     case 26:
/*  591 */       localSizeUnits = SizeUnits.TURN;
/*  592 */       i = 5;
/*  593 */       break;
/*      */     default:
/*  595 */       if (LOGGER.isLoggable(300)) {
/*  596 */         LOGGER.finest("Expected '<number>'");
/*      */       }
/*  598 */       ParseException localParseException = new ParseException("Expected '<number>'", paramToken, this);
/*  599 */       reportError(createError(localParseException.toString()));
/*  600 */       throw localParseException;
/*      */     }
/*      */ 
/*  603 */     return new Size(Double.parseDouble(str.substring(0, j - i)), localSizeUnits);
/*      */   }
/*      */ 
/*      */   private int numberOfTerms(Term paramTerm)
/*      */   {
/*  611 */     if (paramTerm == null) return 0;
/*      */ 
/*  613 */     int i = 0;
/*  614 */     Term localTerm = paramTerm;
/*      */     do {
/*  616 */       i++;
/*  617 */       localTerm = localTerm.nextInSeries;
/*  618 */     }while (localTerm != null);
/*  619 */     return i;
/*      */   }
/*      */ 
/*      */   private int numberOfLayers(Term paramTerm)
/*      */   {
/*  624 */     if (paramTerm == null) return 0;
/*      */ 
/*  626 */     int i = 0;
/*  627 */     Term localTerm = paramTerm;
/*      */     do {
/*  629 */       i++;
/*  630 */       while (localTerm.nextInSeries != null) {
/*  631 */         localTerm = localTerm.nextInSeries;
/*      */       }
/*  633 */       localTerm = localTerm.nextLayer;
/*  634 */     }while (localTerm != null);
/*  635 */     return i;
/*      */   }
/*      */ 
/*      */   private int numberOfArgs(Term paramTerm)
/*      */   {
/*  640 */     if (paramTerm == null) return 0;
/*      */ 
/*  642 */     int i = 0;
/*  643 */     Term localTerm = paramTerm.firstArg;
/*  644 */     while (localTerm != null) {
/*  645 */       i++;
/*  646 */       localTerm = localTerm.nextArg;
/*      */     }
/*  648 */     return i;
/*      */   }
/*      */ 
/*      */   private Term nextLayer(Term paramTerm)
/*      */   {
/*  653 */     if (paramTerm == null) return null;
/*      */ 
/*  655 */     Term localTerm = paramTerm;
/*  656 */     while (localTerm.nextInSeries != null) {
/*  657 */       localTerm = localTerm.nextInSeries;
/*      */     }
/*  659 */     return localTerm.nextLayer;
/*      */   }
/*      */ 
/*      */   ParsedValue valueFor(String paramString, Term paramTerm)
/*      */     throws CSSParser.ParseException
/*      */   {
/*  669 */     String str = paramString.toLowerCase();
/*  670 */     this.properties.put(str, str);
/*  671 */     if ((paramTerm == null) || (paramTerm.token == null)) {
/*  672 */       error(paramTerm, new StringBuilder().append("Expected value for property '").append(str).append("'").toString());
/*      */     }
/*      */ 
/*  675 */     if (paramTerm.token.getType() == 11) {
/*  676 */       if ("inherit".equalsIgnoreCase(paramTerm.token.getText()))
/*  677 */         return new ParsedValue("inherit", null);
/*  678 */       if ("null".equalsIgnoreCase(paramTerm.token.getText())) {
/*  679 */         return new ParsedValue("null", null);
/*      */       }
/*      */     }
/*      */ 
/*  683 */     if ("-fx-background-color".equals(paramString))
/*  684 */       return parsePaintLayers(paramTerm);
/*  685 */     if ("-fx-background-image".equals(str))
/*  686 */       return parseURILayers(paramTerm);
/*  687 */     if ("-fx-background-insets".equals(str))
/*  688 */       return parseInsetsLayers(paramTerm);
/*  689 */     if ("-fx-background-position".equals(str))
/*  690 */       return parseBackgroundPositionLayers(paramTerm);
/*  691 */     if ("-fx-background-radius".equals(str))
/*  692 */       return parseInsetsLayers(paramTerm);
/*  693 */     if ("-fx-background-repeat".equals(str))
/*  694 */       return parseBackgroundRepeatStyleLayers(paramTerm);
/*  695 */     if ("-fx-background-size".equals(str))
/*  696 */       return parseBackgroundSizeLayers(paramTerm);
/*  697 */     if ("-fx-border-color".equals(str))
/*  698 */       return parseBorderPaintLayers(paramTerm);
/*  699 */     if ("-fx-border-insets".equals(str))
/*  700 */       return parseInsetsLayers(paramTerm);
/*  701 */     if ("-fx-border-radius".equals(str))
/*  702 */       return parseMarginsLayers(paramTerm);
/*  703 */     if ("-fx-border-style".equals(str))
/*  704 */       return parseBorderStyleLayers(paramTerm);
/*  705 */     if ("-fx-border-width".equals(str))
/*  706 */       return parseMarginsLayers(paramTerm);
/*  707 */     if ("-fx-border-image-insets".equals(str))
/*  708 */       return parseInsetsLayers(paramTerm);
/*  709 */     if ("-fx-border-image-repeat".equals(str))
/*  710 */       return parseBorderImageRepeatStyleLayers(paramTerm);
/*  711 */     if ("-fx-border-image-slice".equals(str))
/*  712 */       return parseBorderImageSliceLayers(paramTerm);
/*  713 */     if ("-fx-border-image-source".equals(str))
/*  714 */       return parseURILayers(paramTerm);
/*  715 */     if ("-fx-border-image-width".equals(str))
/*  716 */       return parseMarginsLayers(paramTerm);
/*      */     Object localObject1;
/*  717 */     if ("-fx-padding".equals(str)) {
/*  718 */       localObject1 = parseSizeSeries(paramTerm);
/*  719 */       return new ParsedValue(localObject1, InsetsConverter.getInstance());
/*  720 */     }if ("-fx-label-padding".equals(str)) {
/*  721 */       localObject1 = parseSizeSeries(paramTerm);
/*  722 */       return new ParsedValue(localObject1, InsetsConverter.getInstance());
/*  723 */     }if (str.endsWith("font-family"))
/*  724 */       return parseFontFamily(paramTerm);
/*  725 */     if (str.endsWith("font-size")) {
/*  726 */       localObject1 = parseFontSize(paramTerm);
/*  727 */       if (localObject1 == null) error(paramTerm, "Expected '<font-size>'");
/*  728 */       return localObject1;
/*  729 */     }if (str.endsWith("font-style")) {
/*  730 */       localObject1 = parseFontStyle(paramTerm);
/*  731 */       if (localObject1 == null) error(paramTerm, "Expected '<font-style>'");
/*  732 */       return localObject1;
/*  733 */     }if (str.endsWith("font-weight")) {
/*  734 */       localObject1 = parseFontWeight(paramTerm);
/*  735 */       if (localObject1 == null) error(paramTerm, "Expected '<font-style>'");
/*  736 */       return localObject1;
/*  737 */     }if (str.endsWith("font"))
/*  738 */       return parseFont(paramTerm);
/*      */     int i;
/*      */     Object localObject2;
/*  739 */     if ("-fx-stroke-dash-array".equals(str))
/*      */     {
/*  742 */       localObject1 = paramTerm;
/*  743 */       i = numberOfTerms((Term)localObject1);
/*  744 */       localObject2 = new ParsedValue[i];
/*  745 */       int j = 0;
/*  746 */       while (localObject1 != null) {
/*  747 */         localObject2[(j++)] = parseSize((Term)localObject1);
/*  748 */         localObject1 = ((Term)localObject1).nextInSeries;
/*      */       }
/*      */ 
/*  751 */       return new ParsedValue(localObject2, SizeConverter.SequenceConverter.getInstance());
/*      */     }
/*  753 */     if ("-fx-stroke-line-join".equals(str))
/*      */     {
/*  756 */       localObject1 = parseStrokeLineJoin(paramTerm);
/*  757 */       if (localObject1 == null) error(paramTerm, "Expected 'miter', 'bevel' or 'round'");
/*  758 */       return localObject1[0];
/*  759 */     }if ("-fx-stroke-line-cap".equals(str))
/*      */     {
/*  762 */       localObject1 = parseStrokeLineCap(paramTerm);
/*  763 */       if (localObject1 == null) error(paramTerm, "Expected 'square', 'butt' or 'round'");
/*  764 */       return localObject1;
/*  765 */     }if ("-fx-stroke-type".equals(str))
/*      */     {
/*  768 */       localObject1 = parseStrokeType(paramTerm);
/*  769 */       if (localObject1 == null) error(paramTerm, "Expected 'centered', 'inside' or 'outside'");
/*  770 */       return localObject1;
/*  771 */     }if ("-fx-font-smoothing-type".equals(str))
/*      */     {
/*  774 */       localObject1 = null;
/*  775 */       i = -1;
/*  776 */       localObject2 = paramTerm.token;
/*      */ 
/*  778 */       if ((paramTerm.token == null) || (((i = paramTerm.token.getType()) != 10) && (i != 11)) || ((localObject1 = paramTerm.token.getText()) == null) || (((String)localObject1).isEmpty()))
/*      */       {
/*  783 */         error(paramTerm, "Expected STRING or IDENT");
/*      */       }
/*  785 */       return new ParsedValue(stripQuotes((String)localObject1), null, false);
/*      */     }
/*  787 */     return parse(paramTerm);
/*      */   }
/*      */ 
/*      */   private ParsedValue parse(Term paramTerm) throws CSSParser.ParseException
/*      */   {
/*  792 */     if (paramTerm.token == null) error(paramTerm, "Parse error");
/*  793 */     Token localToken = paramTerm.token;
/*  794 */     ParsedValue localParsedValue1 = null;
/*      */ 
/*  796 */     int i = localToken.getType();
/*  797 */     switch (i) {
/*      */     case 13:
/*      */     case 14:
/*      */     case 15:
/*      */     case 16:
/*      */     case 17:
/*      */     case 18:
/*      */     case 19:
/*      */     case 20:
/*      */     case 21:
/*      */     case 22:
/*      */     case 23:
/*      */     case 24:
/*      */     case 25:
/*      */     case 26:
/*  812 */       ParsedValue localParsedValue2 = new ParsedValue(size(localToken), null);
/*  813 */       localParsedValue1 = new ParsedValue(localParsedValue2, SizeConverter.getInstance());
/*  814 */       break;
/*      */     case 10:
/*      */     case 11:
/*  817 */       int j = i == 11 ? 1 : 0;
/*  818 */       String str1 = stripQuotes(localToken.getText());
/*  819 */       String str2 = str1.toLowerCase();
/*  820 */       if ("ladder".equals(str2)) {
/*  821 */         localParsedValue1 = ladder(paramTerm);
/*  822 */       } else if ("linear".equals(str2)) {
/*  823 */         localParsedValue1 = linearGradient(paramTerm);
/*  824 */       } else if ("radial".equals(str2)) {
/*  825 */         localParsedValue1 = radialGradient(paramTerm);
/*  826 */       } else if ("true".equals(str2))
/*      */       {
/*  828 */         localParsedValue1 = new ParsedValue("true", BooleanConverter.getInstance());
/*  829 */       } else if ("false".equals(str2))
/*      */       {
/*  831 */         localParsedValue1 = new ParsedValue("false", BooleanConverter.getInstance());
/*      */       }
/*      */       else {
/*  834 */         int k = (j != 0) && (this.properties.containsKey(str1)) ? 1 : 0;
/*  835 */         if ((k != 0) || ((localParsedValue1 = colorValueOfString(str1)) == null)) {
/*  836 */           localParsedValue1 = new ParsedValue(str1, null, (j != 0) || (k != 0));
/*      */         }
/*      */       }
/*  839 */       break;
/*      */     case 37:
/*  841 */       String str3 = localToken.getText();
/*      */       try {
/*  843 */         localParsedValue1 = new ParsedValue(Color.web(str3), null);
/*      */       } catch (IllegalArgumentException localIllegalArgumentException) {
/*  845 */         error(paramTerm, localIllegalArgumentException.getMessage());
/*      */       }
/*      */ 
/*      */     case 12:
/*  849 */       return parseFunction(paramTerm);
/*      */     case 27:
/*      */     case 28:
/*      */     case 29:
/*      */     case 30:
/*      */     case 31:
/*      */     case 32:
/*      */     case 33:
/*      */     case 34:
/*      */     case 35:
/*      */     case 36:
/*      */     default:
/*  851 */       String str4 = new StringBuilder().append("Unknown token type: '").append(i).append("'").toString();
/*  852 */       error(paramTerm, str4);
/*      */     }
/*  854 */     return localParsedValue1;
/*      */   }
/*      */ 
/*      */   private ParsedValue<?, Size> parseSize(Term paramTerm)
/*      */     throws CSSParser.ParseException
/*      */   {
/*  863 */     if ((paramTerm.token == null) || (!isSize(paramTerm.token))) error(paramTerm, "Expected '<size>'");
/*      */ 
/*  865 */     ParsedValue localParsedValue = null;
/*      */     Object localObject;
/*  867 */     if (paramTerm.token.getType() != 11)
/*      */     {
/*  869 */       localObject = size(paramTerm.token);
/*  870 */       localParsedValue = new ParsedValue(localObject, null);
/*      */     }
/*      */     else
/*      */     {
/*  874 */       localObject = paramTerm.token.getText();
/*  875 */       localParsedValue = new ParsedValue(localObject, null, true);
/*      */     }
/*      */ 
/*  879 */     return localParsedValue;
/*      */   }
/*      */ 
/*      */   private ParsedValue<?, Color> parseColor(Term paramTerm) throws CSSParser.ParseException
/*      */   {
/*  884 */     ParsedValue localParsedValue = null;
/*  885 */     if ((paramTerm.token != null) && ((paramTerm.token.getType() == 11) || (paramTerm.token.getType() == 37) || (paramTerm.token.getType() == 12)))
/*      */     {
/*  890 */       localParsedValue = parse(paramTerm);
/*      */     }
/*      */     else {
/*  893 */       error(paramTerm, "Expected '<color>'");
/*      */     }
/*  895 */     return localParsedValue;
/*      */   }
/*      */ 
/*      */   private ParsedValue rgb(Term paramTerm)
/*      */     throws CSSParser.ParseException
/*      */   {
/*  905 */     String str1 = paramTerm.token != null ? paramTerm.token.getText() : null;
/*  906 */     if ((str1 == null) || (!"rgb".regionMatches(true, 0, str1, 0, 3)))
/*      */     {
/*  908 */       error(paramTerm, "Expected 'rgb' or 'rgba'");
/*      */     }
/*      */ 
/*  911 */     Term localTerm = paramTerm;
/*      */ 
/*  914 */     if ((localTerm = localTerm.firstArg) == null) error(paramTerm, "Expected '<number>' or '<percentage>'");
/*      */     Token localToken1;
/*  915 */     if (((localToken1 = localTerm.token) == null) || ((localToken1.getType() != 13) && (localToken1.getType() != 22)))
/*      */     {
/*  917 */       error(localTerm, "Expected '<number>' or '<percentage>'");
/*      */     }
/*  919 */     paramTerm = localTerm;
/*      */ 
/*  921 */     if ((localTerm = localTerm.nextArg) == null) error(paramTerm, "Expected '<number>' or '<percentage>'");
/*      */     Token localToken2;
/*  922 */     if (((localToken2 = localTerm.token) == null) || ((localToken2.getType() != 13) && (localToken2.getType() != 22)))
/*      */     {
/*  924 */       error(localTerm, "Expected '<number>' or '<percentage>'");
/*      */     }
/*  926 */     paramTerm = localTerm;
/*      */ 
/*  928 */     if ((localTerm = localTerm.nextArg) == null) error(paramTerm, "Expected '<number>' or '<percentage>'");
/*      */     Token localToken3;
/*  929 */     if (((localToken3 = localTerm.token) == null) || ((localToken3.getType() != 13) && (localToken3.getType() != 22)))
/*      */     {
/*  931 */       error(localTerm, "Expected '<number>' or '<percentage>'");
/*      */     }
/*  933 */     paramTerm = localTerm;
/*      */     Token localToken4;
/*  935 */     if ((localTerm = localTerm.nextArg) != null) {
/*  936 */       if (((localToken4 = localTerm.token) == null) || (localToken4.getType() != 13))
/*  937 */         error(localTerm, "Expected '<number>'");
/*      */     }
/*  939 */     else localToken4 = null;
/*      */ 
/*  942 */     int i = localToken1.getType();
/*  943 */     if ((i != localToken2.getType()) || (i != localToken3.getType()) || ((i != 13) && (i != 22)))
/*      */     {
/*  945 */       error(paramTerm, "Argument type mistmatch");
/*      */     }
/*      */ 
/*  948 */     String str2 = localToken1.getText();
/*  949 */     String str3 = localToken2.getText();
/*  950 */     String str4 = localToken3.getText();
/*      */ 
/*  952 */     double d1 = 0.0D;
/*  953 */     double d2 = 0.0D;
/*  954 */     double d3 = 0.0D;
/*  955 */     if (i == 13) {
/*  956 */       d1 = clamp(0.0D, Double.parseDouble(str2) / 255.0D, 1.0D);
/*  957 */       d2 = clamp(0.0D, Double.parseDouble(str3) / 255.0D, 1.0D);
/*  958 */       d3 = clamp(0.0D, Double.parseDouble(str4) / 255.0D, 1.0D);
/*      */     } else {
/*  960 */       d1 = clamp(0.0D, Double.parseDouble(str2.substring(0, str2.length() - 1)) / 100.0D, 1.0D);
/*  961 */       d2 = clamp(0.0D, Double.parseDouble(str3.substring(0, str3.length() - 1)) / 100.0D, 1.0D);
/*  962 */       d3 = clamp(0.0D, Double.parseDouble(str4.substring(0, str4.length() - 1)) / 100.0D, 1.0D);
/*      */     }
/*      */ 
/*  965 */     String str5 = localToken4 != null ? localToken4.getText() : null;
/*  966 */     double d4 = str5 != null ? clamp(0.0D, Double.parseDouble(str5), 1.0D) : 1.0D;
/*      */ 
/*  968 */     return new ParsedValue(Color.color(d1, d2, d3, d4), null);
/*      */   }
/*      */ 
/*      */   private ParsedValue hsb(Term paramTerm)
/*      */     throws CSSParser.ParseException
/*      */   {
/*  977 */     String str = paramTerm.token != null ? paramTerm.token.getText() : null;
/*  978 */     if ((str == null) || (!"hsb".regionMatches(true, 0, str, 0, 3)))
/*      */     {
/*  980 */       error(paramTerm, "Expected 'hsb' or 'hsba'");
/*      */     }
/*      */ 
/*  983 */     Term localTerm = paramTerm;
/*      */ 
/*  986 */     if ((localTerm = localTerm.firstArg) == null) error(paramTerm, "Expected '<number>'");
/*  987 */     Token localToken1;
/*  987 */     if (((localToken1 = localTerm.token) == null) || (localToken1.getType() != 13)) error(localTerm, "Expected '<number>'");
/*      */ 
/*  989 */     paramTerm = localTerm;
/*      */ 
/*  991 */     if ((localTerm = localTerm.nextArg) == null) error(paramTerm, "Expected '<percent>'");
/*  992 */     Token localToken2;
/*  992 */     if (((localToken2 = localTerm.token) == null) || (localToken2.getType() != 22)) error(localTerm, "Expected '<percent>'");
/*      */ 
/*  994 */     paramTerm = localTerm;
/*      */ 
/*  996 */     if ((localTerm = localTerm.nextArg) == null) error(paramTerm, "Expected '<percent>'");
/*  997 */     Token localToken3;
/*  997 */     if (((localToken3 = localTerm.token) == null) || (localToken3.getType() != 22)) error(localTerm, "Expected '<percent>'");
/*      */ 
/*  999 */     paramTerm = localTerm;
/*      */     Token localToken4;
/* 1001 */     if ((localTerm = localTerm.nextArg) != null) {
/* 1002 */       if (((localToken4 = localTerm.token) == null) || (localToken4.getType() != 13)) error(localTerm, "Expected '<number>'"); 
/*      */     }
/*      */     else {
/* 1004 */       localToken4 = null;
/*      */     }
/*      */ 
/* 1007 */     Size localSize1 = size(localToken1);
/* 1008 */     Size localSize2 = size(localToken2);
/* 1009 */     Size localSize3 = size(localToken3);
/*      */ 
/* 1011 */     double d1 = localSize1.pixels();
/* 1012 */     double d2 = clamp(0.0D, localSize2.pixels(), 1.0D);
/* 1013 */     double d3 = clamp(0.0D, localSize3.pixels(), 1.0D);
/*      */ 
/* 1015 */     Object localObject = localToken4 != null ? size(localToken4) : null;
/* 1016 */     double d4 = localObject != null ? clamp(0.0D, localObject.pixels(), 1.0D) : 1.0D;
/*      */ 
/* 1018 */     return new ParsedValue(Color.hsb(d1, d2, d3, d4), null);
/*      */   }
/*      */ 
/*      */   private ParsedValue<ParsedValue[], Color> derive(Term paramTerm)
/*      */     throws CSSParser.ParseException
/*      */   {
/* 1026 */     String str = paramTerm.token != null ? paramTerm.token.getText() : null;
/* 1027 */     if ((str == null) || (!"derive".regionMatches(true, 0, str, 0, 6)))
/*      */     {
/* 1029 */       error(paramTerm, "Expected 'derive'");
/*      */     }
/*      */ 
/* 1032 */     Term localTerm1 = paramTerm;
/* 1033 */     if ((localTerm1 = localTerm1.firstArg) == null) error(paramTerm, "Expected '<color>'");
/*      */ 
/* 1035 */     ParsedValue localParsedValue1 = parseColor(localTerm1);
/*      */ 
/* 1037 */     Term localTerm2 = localTerm1;
/* 1038 */     if ((localTerm1 = localTerm1.nextArg) == null) error(localTerm2, "Expected '<percent'");
/*      */ 
/* 1040 */     ParsedValue localParsedValue2 = parseSize(localTerm1);
/*      */ 
/* 1042 */     ParsedValue[] arrayOfParsedValue = { localParsedValue1, localParsedValue2 };
/* 1043 */     return new ParsedValue(arrayOfParsedValue, DeriveColorConverter.getInstance());
/*      */   }
/*      */ 
/*      */   private ParsedValue<ParsedValue[], Color> ladder(Term paramTerm)
/*      */     throws CSSParser.ParseException
/*      */   {
/* 1050 */     String str = paramTerm.token != null ? paramTerm.token.getText() : null;
/* 1051 */     if ((str == null) || (!"ladder".regionMatches(true, 0, str, 0, 6)))
/*      */     {
/* 1053 */       error(paramTerm, "Expected 'ladder'");
/*      */     }
/*      */ 
/* 1056 */     if (LOGGER.isLoggable(900)) {
/* 1057 */       LOGGER.warning(formatDeprecatedMessage(paramTerm, "ladder"));
/*      */     }
/*      */ 
/* 1060 */     Term localTerm1 = paramTerm;
/*      */ 
/* 1062 */     if ((localTerm1 = localTerm1.nextInSeries) == null) error(paramTerm, "Expected '<color>'");
/* 1063 */     ParsedValue localParsedValue1 = parse(localTerm1);
/*      */ 
/* 1065 */     Term localTerm2 = localTerm1;
/*      */ 
/* 1067 */     if ((localTerm1 = localTerm1.nextInSeries) == null) error(localTerm2, "Expected 'stops'");
/* 1068 */     if ((localTerm1.token == null) || (localTerm1.token.getType() != 11) || (!"stops".equalsIgnoreCase(localTerm1.token.getText())))
/*      */     {
/* 1070 */       error(localTerm1, "Expected 'stops'");
/*      */     }
/* 1072 */     localTerm2 = localTerm1;
/*      */ 
/* 1074 */     if ((localTerm1 = localTerm1.nextInSeries) == null) error(localTerm2, "Expected '(<number>, <color>)'");
/*      */ 
/* 1076 */     int i = 0;
/* 1077 */     Term localTerm3 = localTerm1;
/*      */     do {
/* 1079 */       i++;
/*      */     }
/* 1081 */     while (((localTerm3 = localTerm3.nextInSeries) != null) && (localTerm3.token != null) && (localTerm3.token.getType() == 34));
/*      */ 
/* 1084 */     ParsedValue[] arrayOfParsedValue = new ParsedValue[i + 1];
/* 1085 */     arrayOfParsedValue[0] = localParsedValue1;
/* 1086 */     int j = 1;
/*      */     do {
/* 1088 */       ParsedValue localParsedValue2 = stop(localTerm1);
/* 1089 */       if (localParsedValue2 != null) arrayOfParsedValue[(j++)] = localParsedValue2;
/* 1090 */       localTerm2 = localTerm1;
/* 1091 */     }while (((localTerm1 = localTerm1.nextInSeries) != null) && (localTerm1.token.getType() == 34));
/*      */ 
/* 1098 */     if (localTerm1 != null) {
/* 1099 */       paramTerm.nextInSeries = localTerm1;
/*      */     }
/*      */     else
/*      */     {
/* 1106 */       paramTerm.nextInSeries = null;
/* 1107 */       paramTerm.nextLayer = localTerm2.nextLayer;
/*      */     }
/*      */ 
/* 1110 */     return new ParsedValue(arrayOfParsedValue, LadderConverter.getInstance());
/*      */   }
/*      */ 
/*      */   private ParsedValue<ParsedValue[], Color> parseLadder(Term paramTerm)
/*      */     throws CSSParser.ParseException
/*      */   {
/* 1117 */     String str = paramTerm.token != null ? paramTerm.token.getText() : null;
/* 1118 */     if ((str == null) || (!"ladder".regionMatches(true, 0, str, 0, 6)))
/*      */     {
/* 1120 */       error(paramTerm, "Expected 'ladder'");
/*      */     }
/*      */ 
/* 1123 */     Term localTerm1 = paramTerm;
/*      */ 
/* 1125 */     if ((localTerm1 = localTerm1.firstArg) == null) error(paramTerm, "Expected '<color>'");
/* 1126 */     ParsedValue localParsedValue = parse(localTerm1);
/*      */ 
/* 1128 */     Term localTerm2 = localTerm1;
/*      */ 
/* 1130 */     if ((localTerm1 = localTerm1.nextArg) == null) {
/* 1131 */       error(localTerm2, "Expected '<color-stop>[, <color-stop>]+'");
/*      */     }
/* 1133 */     ParsedValue[] arrayOfParsedValue1 = parseColorStops(localTerm1);
/*      */ 
/* 1135 */     ParsedValue[] arrayOfParsedValue2 = new ParsedValue[arrayOfParsedValue1.length + 1];
/* 1136 */     arrayOfParsedValue2[0] = localParsedValue;
/* 1137 */     System.arraycopy(arrayOfParsedValue1, 0, arrayOfParsedValue2, 1, arrayOfParsedValue1.length);
/* 1138 */     return new ParsedValue(arrayOfParsedValue2, LadderConverter.getInstance());
/*      */   }
/*      */ 
/*      */   private ParsedValue<ParsedValue[], Stop> stop(Term paramTerm)
/*      */     throws CSSParser.ParseException
/*      */   {
/* 1148 */     Object localObject = paramTerm.token != null ? paramTerm.token.getText() : null;
/* 1149 */     if ((localObject == null) || (!"(".equals(localObject)))
/*      */     {
/* 1151 */       error(paramTerm, "Expected '('");
/*      */     }
/*      */ 
/* 1154 */     Term localTerm1 = null;
/*      */ 
/* 1156 */     if ((localTerm1 = paramTerm.firstArg) == null) error(paramTerm, "Expected '<number>'");
/*      */ 
/* 1158 */     ParsedValue localParsedValue1 = parseSize(localTerm1);
/*      */ 
/* 1160 */     Term localTerm2 = localTerm1;
/* 1161 */     if ((localTerm1 = localTerm1.nextArg) == null) error(localTerm2, "Expected '<color>'");
/*      */ 
/* 1163 */     ParsedValue localParsedValue2 = parseColor(localTerm1);
/*      */ 
/* 1165 */     ParsedValue[] arrayOfParsedValue = { localParsedValue1, localParsedValue2 };
/* 1166 */     return new ParsedValue(arrayOfParsedValue, StopConverter.getInstance());
/*      */   }
/*      */ 
/*      */   private ParsedValue<ParsedValue[], Stop>[] parseColorStops(Term paramTerm)
/*      */     throws CSSParser.ParseException
/*      */   {
/* 1175 */     int i = 1;
/* 1176 */     Term localTerm1 = paramTerm;
/* 1177 */     while (localTerm1 != null) {
/* 1178 */       if (localTerm1.nextArg != null) {
/* 1179 */         i++;
/* 1180 */         localTerm1 = localTerm1.nextArg; } else {
/* 1181 */         if (localTerm1.nextInSeries == null) break;
/* 1182 */         localTerm1 = localTerm1.nextInSeries;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1188 */     if (i < 2) {
/* 1189 */       error(paramTerm, "Expected '<color-stop>'");
/*      */     }
/*      */ 
/* 1192 */     ParsedValue[] arrayOfParsedValue1 = new ParsedValue[i];
/* 1193 */     Size[] arrayOfSize = new Size[i];
/* 1194 */     Arrays.fill(arrayOfSize, null);
/*      */ 
/* 1196 */     Term localTerm2 = paramTerm;
/* 1197 */     Object localObject1 = paramTerm;
/* 1198 */     Object localObject2 = null;
/* 1199 */     for (int j = 0; j < i; j++)
/*      */     {
/* 1201 */       arrayOfParsedValue1[j] = parseColor(localTerm2);
/*      */ 
/* 1203 */       localObject1 = localTerm2;
/* 1204 */       Term localTerm3 = localTerm2.nextInSeries;
/* 1205 */       if (localTerm3 != null) {
/* 1206 */         if (isSize(localTerm3.token)) {
/* 1207 */           arrayOfSize[j] = size(localTerm3.token);
/* 1208 */           if ((localObject2 != null) && 
/* 1209 */             (localObject2 != arrayOfSize[j].getUnits()))
/* 1210 */             error(localTerm3, "Parser unable to handle mixed '<percent>' and '<length>'");
/*      */         }
/*      */         else
/*      */         {
/* 1214 */           error((Term)localObject1, "Expected '<percent>' or '<length>'");
/*      */         }
/* 1216 */         localObject1 = localTerm3;
/* 1217 */         localTerm2 = localTerm3.nextArg;
/*      */       } else {
/* 1219 */         localObject1 = localTerm2;
/* 1220 */         localTerm2 = localTerm2.nextArg;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1232 */     if (arrayOfSize[0] == null) arrayOfSize[0] = new Size(0.0D, SizeUnits.PERCENT);
/* 1233 */     if (arrayOfSize[(i - 1)] == null) arrayOfSize[(i - 1)] = new Size(100.0D, SizeUnits.PERCENT);
/*      */ 
/* 1239 */     Object localObject3 = null;
/* 1240 */     for (int k = 1; k < i; k++) {
/* 1241 */       Size localSize1 = arrayOfSize[(k - 1)];
/* 1242 */       if (localSize1 != null) {
/* 1243 */         if ((localObject3 == null) || (localObject3.getValue() < localSize1.getValue()))
/*      */         {
/* 1245 */           localObject3 = localSize1;
/*      */         }
/* 1247 */         Size localSize2 = arrayOfSize[k];
/* 1248 */         if (localSize2 != null)
/*      */         {
/* 1250 */           if (localSize2.getValue() < localObject3.getValue()) arrayOfSize[k] = localObject3;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1257 */     Object localObject4 = null;
/* 1258 */     int m = -1;
/* 1259 */     for (int n = 0; n < i; n++) {
/* 1260 */       Size localSize3 = arrayOfSize[n];
/* 1261 */       if (localSize3 == null) {
/* 1262 */         if (m == -1) m = n;
/*      */ 
/*      */       }
/* 1265 */       else if (m > -1)
/*      */       {
/* 1267 */         int i2 = n - m;
/* 1268 */         double d1 = localObject4.getValue();
/* 1269 */         double d2 = (localSize3.getValue() - d1) / (i2 + 1);
/*      */ 
/* 1272 */         while (m < n) {
/* 1273 */           d1 += d2;
/* 1274 */           arrayOfSize[(m++)] = new Size(d1, localSize3.getUnits());
/*      */         }
/*      */ 
/* 1277 */         m = -1;
/* 1278 */         localObject4 = localSize3;
/*      */       } else {
/* 1280 */         localObject4 = localSize3;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1285 */     ParsedValue[] arrayOfParsedValue2 = new ParsedValue[i];
/* 1286 */     for (int i1 = 0; i1 < i; i1++) {
/* 1287 */       arrayOfParsedValue2[i1] = new ParsedValue(new ParsedValue[] { new ParsedValue(arrayOfSize[i1], null), arrayOfParsedValue1[i1] }, StopConverter.getInstance());
/*      */     }
/*      */ 
/* 1296 */     return arrayOfParsedValue2;
/*      */   }
/*      */ 
/*      */   private ParsedValue[] point(Term paramTerm)
/*      */     throws CSSParser.ParseException
/*      */   {
/* 1303 */     if ((paramTerm.token == null) || (paramTerm.token.getType() != 34)) {
/* 1304 */       error(paramTerm, "Expected '(<number>, <number>)'");
/*      */     }
/* 1306 */     String str = paramTerm.token.getText();
/* 1307 */     if ((str == null) || (!"(".equalsIgnoreCase(str)))
/*      */     {
/* 1309 */       error(paramTerm, "Expected '('");
/*      */     }
/*      */ 
/* 1312 */     Term localTerm1 = null;
/*      */ 
/* 1315 */     if ((localTerm1 = paramTerm.firstArg) == null) error(paramTerm, "Expected '<number>'");
/*      */ 
/* 1317 */     ParsedValue localParsedValue1 = parseSize(localTerm1);
/*      */ 
/* 1319 */     Term localTerm2 = localTerm1;
/*      */ 
/* 1321 */     if ((localTerm1 = localTerm1.nextArg) == null) error(localTerm2, "Expected '<number>'");
/*      */ 
/* 1323 */     ParsedValue localParsedValue2 = parseSize(localTerm1);
/*      */ 
/* 1325 */     return new ParsedValue[] { localParsedValue1, localParsedValue2 };
/*      */   }
/*      */ 
/*      */   private ParsedValue parseFunction(Term paramTerm)
/*      */     throws CSSParser.ParseException
/*      */   {
/* 1331 */     String str = paramTerm.token != null ? paramTerm.token.getText() : null;
/* 1332 */     if (str == null) {
/* 1333 */       error(paramTerm, "Expected function name"); } else {
/* 1334 */       if ("rgb".regionMatches(true, 0, str, 0, 3))
/* 1335 */         return rgb(paramTerm);
/* 1336 */       if ("hsb".regionMatches(true, 0, str, 0, 3))
/* 1337 */         return hsb(paramTerm);
/* 1338 */       if ("derive".regionMatches(true, 0, str, 0, 6))
/* 1339 */         return derive(paramTerm);
/* 1340 */       if ("innershadow".regionMatches(true, 0, str, 0, 11))
/* 1341 */         return innershadow(paramTerm);
/* 1342 */       if ("dropshadow".regionMatches(true, 0, str, 0, 10))
/* 1343 */         return dropshadow(paramTerm);
/* 1344 */       if ("linear-gradient".regionMatches(true, 0, str, 0, 15))
/* 1345 */         return parseLinearGradient(paramTerm);
/* 1346 */       if ("radial-gradient".regionMatches(true, 0, str, 0, 15))
/* 1347 */         return parseRadialGradient(paramTerm);
/* 1348 */       if ("ladder".regionMatches(true, 0, str, 0, 6))
/* 1349 */         return parseLadder(paramTerm);
/* 1350 */       if ("url".regionMatches(true, 0, str, 0, 3)) {
/* 1351 */         return parseURI(paramTerm);
/*      */       }
/* 1353 */       error(paramTerm, new StringBuilder().append("Unexpected function '").append(str).append("'").toString());
/*      */     }
/* 1355 */     return null;
/*      */   }
/*      */ 
/*      */   private ParsedValue<BlurType, BlurType> blurType(Term paramTerm) throws CSSParser.ParseException
/*      */   {
/* 1360 */     if (paramTerm == null) return null;
/* 1361 */     if ((paramTerm.token == null) || (paramTerm.token.getType() != 11) || (paramTerm.token.getText() == null) || (paramTerm.token.getText().isEmpty()))
/*      */     {
/* 1366 */       error(paramTerm, "Expected 'gaussian', 'one-pass-box', 'two-pass-box', or 'three-pass-box'");
/*      */     }
/* 1368 */     String str = paramTerm.token.getText().toLowerCase();
/* 1369 */     BlurType localBlurType = BlurType.THREE_PASS_BOX;
/* 1370 */     if ("gaussian".equals(str))
/* 1371 */       localBlurType = BlurType.GAUSSIAN;
/* 1372 */     else if ("one-pass-box".equals(str))
/* 1373 */       localBlurType = BlurType.ONE_PASS_BOX;
/* 1374 */     else if ("two-pass-box".equals(str))
/* 1375 */       localBlurType = BlurType.TWO_PASS_BOX;
/* 1376 */     else if ("three-pass-box".equals(str)) {
/* 1377 */       localBlurType = BlurType.THREE_PASS_BOX;
/*      */     }
/*      */     else {
/* 1380 */       error(paramTerm, "Expected 'gaussian', 'one-pass-box', 'two-pass-box', or 'three-pass-box'");
/*      */     }
/* 1382 */     return new ParsedValue(localBlurType, null);
/*      */   }
/*      */ 
/*      */   private ParsedValue innershadow(Term paramTerm)
/*      */     throws CSSParser.ParseException
/*      */   {
/* 1389 */     String str = paramTerm.token != null ? paramTerm.token.getText() : null;
/* 1390 */     if (!"innershadow".regionMatches(true, 0, str, 0, 11))
/*      */     {
/* 1392 */       error(paramTerm, "Expected 'innershadow'");
/*      */     }
/* 1397 */     Term localTerm1;
/* 1397 */     if ((localTerm1 = paramTerm.firstArg) == null) error(paramTerm, "Expected '<blur-type>'");
/* 1398 */     ParsedValue localParsedValue1 = blurType(localTerm1);
/*      */ 
/* 1400 */     Term localTerm2 = localTerm1;
/* 1401 */     if ((localTerm1 = localTerm1.nextArg) == null) error(localTerm2, "Expected '<color>'");
/*      */ 
/* 1403 */     ParsedValue localParsedValue2 = parseColor(localTerm1);
/*      */ 
/* 1405 */     localTerm2 = localTerm1;
/* 1406 */     if ((localTerm1 = localTerm1.nextArg) == null) error(localTerm2, "Expected '<number>'");
/*      */ 
/* 1408 */     ParsedValue localParsedValue3 = parseSize(localTerm1);
/*      */ 
/* 1410 */     localTerm2 = localTerm1;
/* 1411 */     if ((localTerm1 = localTerm1.nextArg) == null) error(localTerm2, "Expected '<number>'");
/*      */ 
/* 1413 */     ParsedValue localParsedValue4 = parseSize(localTerm1);
/*      */ 
/* 1415 */     localTerm2 = localTerm1;
/* 1416 */     if ((localTerm1 = localTerm1.nextArg) == null) error(localTerm2, "Expected '<number>'");
/*      */ 
/* 1418 */     ParsedValue localParsedValue5 = parseSize(localTerm1);
/*      */ 
/* 1420 */     localTerm2 = localTerm1;
/* 1421 */     if ((localTerm1 = localTerm1.nextArg) == null) error(localTerm2, "Expected '<number>'");
/*      */ 
/* 1423 */     ParsedValue localParsedValue6 = parseSize(localTerm1);
/*      */ 
/* 1425 */     ParsedValue[] arrayOfParsedValue = { localParsedValue1, localParsedValue2, localParsedValue3, localParsedValue4, localParsedValue5, localParsedValue6 };
/*      */ 
/* 1433 */     return new ParsedValue(arrayOfParsedValue, EffectConverter.InnerShadowConverter.getInstance());
/*      */   }
/*      */ 
/*      */   private ParsedValue dropshadow(Term paramTerm)
/*      */     throws CSSParser.ParseException
/*      */   {
/* 1440 */     String str = paramTerm.token != null ? paramTerm.token.getText() : null;
/* 1441 */     if (!"dropshadow".regionMatches(true, 0, str, 0, 10))
/*      */     {
/* 1443 */       error(paramTerm, "Expected 'dropshadow'");
/*      */     }
/* 1448 */     Term localTerm1;
/* 1448 */     if ((localTerm1 = paramTerm.firstArg) == null) error(paramTerm, "Expected '<blur-type>'");
/* 1449 */     ParsedValue localParsedValue1 = blurType(localTerm1);
/*      */ 
/* 1451 */     Term localTerm2 = localTerm1;
/* 1452 */     if ((localTerm1 = localTerm1.nextArg) == null) error(localTerm2, "Expected '<color>'");
/*      */ 
/* 1454 */     ParsedValue localParsedValue2 = parseColor(localTerm1);
/*      */ 
/* 1456 */     localTerm2 = localTerm1;
/* 1457 */     if ((localTerm1 = localTerm1.nextArg) == null) error(localTerm2, "Expected '<number>'");
/*      */ 
/* 1459 */     ParsedValue localParsedValue3 = parseSize(localTerm1);
/*      */ 
/* 1461 */     localTerm2 = localTerm1;
/* 1462 */     if ((localTerm1 = localTerm1.nextArg) == null) error(localTerm2, "Expected '<number>'");
/*      */ 
/* 1464 */     ParsedValue localParsedValue4 = parseSize(localTerm1);
/*      */ 
/* 1466 */     localTerm2 = localTerm1;
/* 1467 */     if ((localTerm1 = localTerm1.nextArg) == null) error(localTerm2, "Expected '<number>'");
/*      */ 
/* 1469 */     ParsedValue localParsedValue5 = parseSize(localTerm1);
/*      */ 
/* 1471 */     localTerm2 = localTerm1;
/* 1472 */     if ((localTerm1 = localTerm1.nextArg) == null) error(localTerm2, "Expected '<number>'");
/*      */ 
/* 1474 */     ParsedValue localParsedValue6 = parseSize(localTerm1);
/*      */ 
/* 1476 */     ParsedValue[] arrayOfParsedValue = { localParsedValue1, localParsedValue2, localParsedValue3, localParsedValue4, localParsedValue5, localParsedValue6 };
/*      */ 
/* 1484 */     return new ParsedValue(arrayOfParsedValue, EffectConverter.DropShadowConverter.getInstance());
/*      */   }
/*      */ 
/*      */   private ParsedValue<CycleMethod, CycleMethod> cycleMethod(Term paramTerm)
/*      */   {
/* 1489 */     CycleMethod localCycleMethod = null;
/* 1490 */     if ((paramTerm != null) && (paramTerm.token.getType() == 11))
/*      */     {
/* 1492 */       String str = paramTerm.token.getText().toLowerCase();
/* 1493 */       if ("repeat".equals(str))
/* 1494 */         localCycleMethod = CycleMethod.REPEAT;
/* 1495 */       else if ("reflect".equals(str))
/* 1496 */         localCycleMethod = CycleMethod.REFLECT;
/* 1497 */       else if ("no-cycle".equals(str)) {
/* 1498 */         localCycleMethod = CycleMethod.NO_CYCLE;
/*      */       }
/*      */     }
/* 1501 */     if (localCycleMethod != null) {
/* 1502 */       return new ParsedValue(localCycleMethod, null);
/*      */     }
/* 1504 */     return null;
/*      */   }
/*      */ 
/*      */   private ParsedValue<ParsedValue[], Paint> linearGradient(Term paramTerm)
/*      */     throws CSSParser.ParseException
/*      */   {
/* 1510 */     String str = paramTerm.token != null ? paramTerm.token.getText() : null;
/* 1511 */     if ((str == null) || (!"linear".equalsIgnoreCase(str)))
/*      */     {
/* 1513 */       error(paramTerm, "Expected 'linear'");
/*      */     }
/*      */ 
/* 1516 */     if (LOGGER.isLoggable(900)) {
/* 1517 */       LOGGER.warning(formatDeprecatedMessage(paramTerm, "linear gradient"));
/*      */     }
/*      */ 
/* 1520 */     Term localTerm1 = paramTerm;
/*      */ 
/* 1522 */     if ((localTerm1 = localTerm1.nextInSeries) == null) error(paramTerm, "Expected '(<number>, <number>)'");
/*      */ 
/* 1524 */     ParsedValue[] arrayOfParsedValue1 = point(localTerm1);
/*      */ 
/* 1526 */     Term localTerm2 = localTerm1;
/* 1527 */     if ((localTerm1 = localTerm1.nextInSeries) == null) error(localTerm2, "Expected 'to'");
/* 1528 */     if ((localTerm1.token == null) || (localTerm1.token.getType() != 11) || (!"to".equalsIgnoreCase(localTerm1.token.getText())))
/*      */     {
/* 1530 */       error(paramTerm, "Expected 'to'");
/*      */     }
/* 1532 */     localTerm2 = localTerm1;
/* 1533 */     if ((localTerm1 = localTerm1.nextInSeries) == null) error(localTerm2, "Expected '(<number>, <number>)'");
/*      */ 
/* 1535 */     ParsedValue[] arrayOfParsedValue2 = point(localTerm1);
/*      */ 
/* 1537 */     localTerm2 = localTerm1;
/* 1538 */     if ((localTerm1 = localTerm1.nextInSeries) == null) error(localTerm2, "Expected 'stops'");
/* 1539 */     if ((localTerm1.token == null) || (localTerm1.token.getType() != 11) || (!"stops".equalsIgnoreCase(localTerm1.token.getText())))
/*      */     {
/* 1541 */       error(localTerm1, "Expected 'stops'");
/*      */     }
/* 1543 */     localTerm2 = localTerm1;
/* 1544 */     if ((localTerm1 = localTerm1.nextInSeries) == null) error(localTerm2, "Expected '(<number>, <number>)'");
/*      */ 
/* 1546 */     int i = 0;
/* 1547 */     Term localTerm3 = localTerm1;
/*      */     do {
/* 1549 */       i++;
/*      */     }
/* 1551 */     while (((localTerm3 = localTerm3.nextInSeries) != null) && (localTerm3.token != null) && (localTerm3.token.getType() == 34));
/*      */ 
/* 1554 */     ParsedValue[] arrayOfParsedValue3 = new ParsedValue[i];
/* 1555 */     int j = 0;
/*      */     do {
/* 1557 */       localParsedValue = stop(localTerm1);
/* 1558 */       if (localParsedValue != null) arrayOfParsedValue3[(j++)] = localParsedValue;
/* 1559 */       localTerm2 = localTerm1;
/* 1560 */     }while (((localTerm1 = localTerm1.nextInSeries) != null) && (localTerm1.token.getType() == 34));
/*      */ 
/* 1564 */     ParsedValue localParsedValue = cycleMethod(localTerm1);
/*      */ 
/* 1566 */     if (localParsedValue == null)
/*      */     {
/* 1568 */       localParsedValue = new ParsedValue(CycleMethod.NO_CYCLE, null);
/*      */ 
/* 1572 */       if (localTerm1 != null) {
/* 1573 */         paramTerm.nextInSeries = localTerm1;
/*      */       }
/*      */       else
/*      */       {
/* 1580 */         paramTerm.nextInSeries = null;
/* 1581 */         paramTerm.nextLayer = localTerm2.nextLayer;
/*      */       }
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1591 */       paramTerm.nextInSeries = localTerm1.nextInSeries;
/* 1592 */       paramTerm.nextLayer = localTerm1.nextLayer;
/*      */     }
/*      */ 
/* 1595 */     ParsedValue[] arrayOfParsedValue4 = new ParsedValue[5 + arrayOfParsedValue3.length];
/* 1596 */     int k = 0;
/* 1597 */     arrayOfParsedValue4[(k++)] = (arrayOfParsedValue1 != null ? arrayOfParsedValue1[0] : null);
/* 1598 */     arrayOfParsedValue4[(k++)] = (arrayOfParsedValue1 != null ? arrayOfParsedValue1[1] : null);
/* 1599 */     arrayOfParsedValue4[(k++)] = (arrayOfParsedValue2 != null ? arrayOfParsedValue2[0] : null);
/* 1600 */     arrayOfParsedValue4[(k++)] = (arrayOfParsedValue2 != null ? arrayOfParsedValue2[1] : null);
/* 1601 */     arrayOfParsedValue4[(k++)] = localParsedValue;
/* 1602 */     for (int m = 0; m < arrayOfParsedValue3.length; m++) arrayOfParsedValue4[(k++)] = arrayOfParsedValue3[m];
/* 1603 */     return new ParsedValue(arrayOfParsedValue4, PaintConverter.LinearGradientConverter.getInstance());
/*      */   }
/*      */ 
/*      */   private ParsedValue parseLinearGradient(Term paramTerm)
/*      */     throws CSSParser.ParseException
/*      */   {
/* 1624 */     String str1 = paramTerm.token != null ? paramTerm.token.getText() : null;
/* 1625 */     if (!"linear-gradient".regionMatches(true, 0, str1, 0, 15))
/*      */     {
/* 1627 */       error(paramTerm, "Expected 'linear-gradient'");
/*      */     }
/*      */     Term localTerm1;
/* 1632 */     if (((localTerm1 = paramTerm.firstArg) == null) || (localTerm1.token == null) || (localTerm1.token.getText().isEmpty()))
/*      */     {
/* 1635 */       error(paramTerm, "Expected 'from <point> to <point>' or 'to <side-or-corner>' or '<cycle-method>' or '<color-stop>'");
/*      */     }
/*      */ 
/* 1640 */     Term localTerm2 = localTerm1;
/*      */ 
/* 1642 */     ParsedValue[] arrayOfParsedValue1 = null;
/* 1643 */     ParsedValue[] arrayOfParsedValue2 = null;
/*      */ 
/* 1645 */     if ("from".equalsIgnoreCase(localTerm1.token.getText()))
/*      */     {
/* 1647 */       localTerm2 = localTerm1;
/* 1648 */       if ((localTerm1 = localTerm1.nextInSeries) == null) error(localTerm2, "Expected '<point>'");
/*      */ 
/* 1650 */       ParsedValue localParsedValue1 = parseSize(localTerm1);
/*      */ 
/* 1652 */       localTerm2 = localTerm1;
/* 1653 */       if ((localTerm1 = localTerm1.nextInSeries) == null) error(localTerm2, "Expected '<point>'");
/*      */ 
/* 1655 */       ParsedValue localParsedValue2 = parseSize(localTerm1);
/*      */ 
/* 1657 */       arrayOfParsedValue1 = new ParsedValue[] { localParsedValue1, localParsedValue2 };
/*      */ 
/* 1659 */       localTerm2 = localTerm1;
/* 1660 */       if ((localTerm1 = localTerm1.nextInSeries) == null) error(localTerm2, "Expected 'to'");
/* 1661 */       if ((localTerm1.token == null) || (localTerm1.token.getType() != 11) || (!"to".equalsIgnoreCase(localTerm1.token.getText())))
/*      */       {
/* 1663 */         error(localTerm2, "Expected 'to'");
/*      */       }
/* 1665 */       localTerm2 = localTerm1;
/* 1666 */       if ((localTerm1 = localTerm1.nextInSeries) == null) error(localTerm2, "Expected '<point>'");
/*      */ 
/* 1668 */       localParsedValue1 = parseSize(localTerm1);
/*      */ 
/* 1670 */       localTerm2 = localTerm1;
/* 1671 */       if ((localTerm1 = localTerm1.nextInSeries) == null) error(localTerm2, "Expected '<point>'");
/*      */ 
/* 1673 */       localParsedValue2 = parseSize(localTerm1);
/*      */ 
/* 1675 */       arrayOfParsedValue2 = new ParsedValue[] { localParsedValue1, localParsedValue2 };
/*      */ 
/* 1677 */       localTerm2 = localTerm1;
/* 1678 */       localTerm1 = localTerm1.nextArg;
/*      */     }
/* 1680 */     else if ("to".equalsIgnoreCase(localTerm1.token.getText()))
/*      */     {
/* 1682 */       localTerm2 = localTerm1;
/* 1683 */       if ((localTerm1 = localTerm1.nextInSeries) != null) {
/* 1683 */         if ((((localTerm1.token == null ? 1 : 0) | (localTerm1.token.getType() != 11 ? 1 : 0)) == 0) && (!localTerm1.token.getText().isEmpty()));
/*      */       }
/*      */       else {
/* 1687 */         error(localTerm2, "Expected '<side-or-corner>'");
/*      */       }
/*      */ 
/* 1691 */       int i = 0;
/* 1692 */       int j = 0;
/* 1693 */       int k = 0;
/* 1694 */       m = 0;
/*      */ 
/* 1696 */       String str2 = localTerm1.token.getText().toLowerCase();
/*      */ 
/* 1698 */       if ("top".equals(str2))
/*      */       {
/* 1700 */         j = 100;
/* 1701 */         m = 0;
/*      */       }
/* 1703 */       else if ("bottom".equals(str2))
/*      */       {
/* 1705 */         j = 0;
/* 1706 */         m = 100;
/*      */       }
/* 1708 */       else if ("right".equals(str2))
/*      */       {
/* 1710 */         i = 0;
/* 1711 */         k = 100;
/*      */       }
/* 1713 */       else if ("left".equals(str2))
/*      */       {
/* 1715 */         i = 100;
/* 1716 */         k = 0;
/*      */       }
/*      */       else {
/* 1719 */         error(localTerm1, "Invalid '<side-or-corner>'");
/*      */       }
/*      */ 
/* 1722 */       localTerm2 = localTerm1;
/* 1723 */       if (localTerm1.nextInSeries != null) {
/* 1724 */         localTerm1 = localTerm1.nextInSeries;
/* 1725 */         if ((localTerm1.token != null) && (localTerm1.token.getType() == 11) && (!localTerm1.token.getText().isEmpty()))
/*      */         {
/* 1729 */           String str3 = localTerm1.token.getText().toLowerCase();
/*      */ 
/* 1733 */           if (("right".equals(str3)) && (i == 0) && (k == 0))
/*      */           {
/* 1736 */             i = 0;
/* 1737 */             k = 100;
/* 1738 */           } else if (("left".equals(str3)) && (i == 0) && (k == 0))
/*      */           {
/* 1741 */             i = 100;
/* 1742 */             k = 0;
/*      */           }
/* 1746 */           else if (("top".equals(str3)) && (j == 0) && (m == 0))
/*      */           {
/* 1749 */             j = 100;
/* 1750 */             m = 0;
/* 1751 */           } else if (("bottom".equals(str3)) && (j == 0) && (m == 0))
/*      */           {
/* 1754 */             j = 0;
/* 1755 */             m = 100;
/*      */           }
/*      */           else {
/* 1758 */             error(localTerm1, "Invalid '<side-or-corner>'");
/*      */           }
/*      */         }
/*      */         else {
/* 1762 */           error(localTerm2, "Expected '<side-or-corner>'");
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1767 */       arrayOfParsedValue1 = new ParsedValue[] { new ParsedValue(new Size(i, SizeUnits.PERCENT), null), new ParsedValue(new Size(j, SizeUnits.PERCENT), null) };
/*      */ 
/* 1772 */       arrayOfParsedValue2 = new ParsedValue[] { new ParsedValue(new Size(k, SizeUnits.PERCENT), null), new ParsedValue(new Size(m, SizeUnits.PERCENT), null) };
/*      */ 
/* 1777 */       localTerm2 = localTerm1;
/* 1778 */       localTerm1 = localTerm1.nextArg;
/*      */     }
/*      */ 
/* 1781 */     if ((arrayOfParsedValue1 == null) && (arrayOfParsedValue2 == null))
/*      */     {
/* 1783 */       arrayOfParsedValue1 = new ParsedValue[] { new ParsedValue(new Size(0.0D, SizeUnits.PERCENT), null), new ParsedValue(new Size(0.0D, SizeUnits.PERCENT), null) };
/*      */ 
/* 1788 */       arrayOfParsedValue2 = new ParsedValue[] { new ParsedValue(new Size(0.0D, SizeUnits.PERCENT), null), new ParsedValue(new Size(100.0D, SizeUnits.PERCENT), null) };
/*      */     }
/*      */ 
/* 1794 */     if ((localTerm1 == null) || (localTerm1.token == null) || (localTerm1.token.getText().isEmpty()))
/*      */     {
/* 1797 */       error(localTerm2, "Expected '<cycle-method>' or '<color-stop>'");
/*      */     }
/*      */ 
/* 1800 */     CycleMethod localCycleMethod = CycleMethod.NO_CYCLE;
/* 1801 */     if ("reflect".equalsIgnoreCase(localTerm1.token.getText())) {
/* 1802 */       localCycleMethod = CycleMethod.REFLECT;
/* 1803 */       localTerm2 = localTerm1;
/* 1804 */       localTerm1 = localTerm1.nextArg;
/* 1805 */     } else if ("repeat".equalsIgnoreCase(localTerm1.token.getText())) {
/* 1806 */       localCycleMethod = CycleMethod.REFLECT;
/* 1807 */       localTerm2 = localTerm1;
/* 1808 */       localTerm1 = localTerm1.nextArg;
/*      */     }
/*      */ 
/* 1811 */     if ((localTerm1 == null) || (localTerm1.token == null) || (localTerm1.token.getText().isEmpty()))
/*      */     {
/* 1814 */       error(localTerm2, "Expected '<color-stop>'");
/*      */     }
/*      */ 
/* 1817 */     ParsedValue[] arrayOfParsedValue3 = parseColorStops(localTerm1);
/*      */ 
/* 1819 */     ParsedValue[] arrayOfParsedValue4 = new ParsedValue[5 + arrayOfParsedValue3.length];
/* 1820 */     int m = 0;
/* 1821 */     arrayOfParsedValue4[(m++)] = (arrayOfParsedValue1 != null ? arrayOfParsedValue1[0] : null);
/* 1822 */     arrayOfParsedValue4[(m++)] = (arrayOfParsedValue1 != null ? arrayOfParsedValue1[1] : null);
/* 1823 */     arrayOfParsedValue4[(m++)] = (arrayOfParsedValue2 != null ? arrayOfParsedValue2[0] : null);
/* 1824 */     arrayOfParsedValue4[(m++)] = (arrayOfParsedValue2 != null ? arrayOfParsedValue2[1] : null);
/* 1825 */     arrayOfParsedValue4[(m++)] = new ParsedValue(localCycleMethod, null);
/* 1826 */     for (int n = 0; n < arrayOfParsedValue3.length; n++) arrayOfParsedValue4[(m++)] = arrayOfParsedValue3[n];
/* 1827 */     return new ParsedValue(arrayOfParsedValue4, PaintConverter.LinearGradientConverter.getInstance());
/*      */   }
/*      */ 
/*      */   private ParsedValue<ParsedValue[], Paint> radialGradient(Term paramTerm)
/*      */     throws CSSParser.ParseException
/*      */   {
/* 1836 */     String str = paramTerm.token != null ? paramTerm.token.getText() : null;
/* 1837 */     if ((str == null) || (!"radial".equalsIgnoreCase(str)))
/*      */     {
/* 1839 */       error(paramTerm, "Expected 'radial'");
/*      */     }
/*      */ 
/* 1842 */     if (LOGGER.isLoggable(900)) {
/* 1843 */       LOGGER.warning(formatDeprecatedMessage(paramTerm, "radial gradient"));
/*      */     }
/*      */ 
/* 1846 */     Term localTerm1 = paramTerm;
/* 1847 */     Term localTerm2 = paramTerm;
/*      */ 
/* 1849 */     if ((localTerm1 = localTerm1.nextInSeries) == null) error(paramTerm, "Expected 'focus-angle <number>', 'focus-distance <number>', 'center (<number>,<number>)' or '<size>'");
/* 1850 */     if (localTerm1.token == null) error(localTerm1, "Expected 'focus-angle <number>', 'focus-distance <number>', 'center (<number>,<number>)' or '<size>'");
/*      */ 
/* 1853 */     ParsedValue localParsedValue1 = null;
/* 1854 */     if (localTerm1.token.getType() == 11) {
/* 1855 */       localObject1 = localTerm1.token.getText().toLowerCase();
/* 1856 */       if ("focus-angle".equals(localObject1))
/*      */       {
/* 1858 */         localTerm2 = localTerm1;
/* 1859 */         if ((localTerm1 = localTerm1.nextInSeries) == null) error(localTerm2, "Expected '<number>'");
/* 1860 */         if (localTerm1.token == null) error(localTerm2, "Expected '<number>'");
/*      */ 
/* 1862 */         localParsedValue1 = parseSize(localTerm1);
/*      */ 
/* 1864 */         localTerm2 = localTerm1;
/* 1865 */         if ((localTerm1 = localTerm1.nextInSeries) == null) error(localTerm2, "Expected 'focus-distance <number>', 'center (<number>,<number>)' or '<size>'");
/* 1866 */         if (localTerm1.token == null) error(localTerm1, "Expected 'focus-distance <number>', 'center (<number>,<number>)' or '<size>'");
/*      */       }
/*      */     }
/*      */ 
/* 1870 */     Object localObject1 = null;
/* 1871 */     if (localTerm1.token.getType() == 11) {
/* 1872 */       localObject2 = localTerm1.token.getText().toLowerCase();
/* 1873 */       if ("focus-distance".equals(localObject2))
/*      */       {
/* 1875 */         localTerm2 = localTerm1;
/* 1876 */         if ((localTerm1 = localTerm1.nextInSeries) == null) error(localTerm2, "Expected '<number>'");
/* 1877 */         if (localTerm1.token == null) error(localTerm2, "Expected '<number>'");
/*      */ 
/* 1879 */         localObject1 = parseSize(localTerm1);
/*      */ 
/* 1881 */         localTerm2 = localTerm1;
/* 1882 */         if ((localTerm1 = localTerm1.nextInSeries) == null) error(localTerm2, "Expected  'center (<number>,<number>)' or '<size>'");
/* 1883 */         if (localTerm1.token == null) error(localTerm1, "Expected  'center (<number>,<number>)' or '<size>'");
/*      */       }
/*      */     }
/*      */ 
/* 1887 */     Object localObject2 = null;
/* 1888 */     if (localTerm1.token.getType() == 11) {
/* 1889 */       localObject3 = localTerm1.token.getText().toLowerCase();
/* 1890 */       if ("center".equals(localObject3))
/*      */       {
/* 1892 */         localTerm2 = localTerm1;
/* 1893 */         if ((localTerm1 = localTerm1.nextInSeries) == null) error(localTerm2, "Expected '(<number>,<number>)'");
/* 1894 */         if ((localTerm1.token == null) || (localTerm1.token.getType() != 34)) {
/* 1895 */           error(localTerm1, "Expected '(<number>,<number>)'");
/*      */         }
/* 1897 */         localObject2 = point(localTerm1);
/*      */ 
/* 1899 */         localTerm2 = localTerm1;
/* 1900 */         if ((localTerm1 = localTerm1.nextInSeries) == null) error(localTerm2, "Expected '<size>'");
/* 1901 */         if (localTerm1.token == null) error(localTerm1, "Expected '<size>'");
/*      */       }
/*      */     }
/*      */ 
/* 1905 */     Object localObject3 = parseSize(localTerm1);
/*      */ 
/* 1907 */     localTerm2 = localTerm1;
/* 1908 */     if ((localTerm1 = localTerm1.nextInSeries) == null) error(localTerm2, "Expected 'stops' keyword");
/* 1909 */     if ((localTerm1.token == null) || (localTerm1.token.getType() != 11)) {
/* 1910 */       error(localTerm1, "Expected 'stops' keyword");
/*      */     }
/* 1912 */     if (!"stops".equalsIgnoreCase(localTerm1.token.getText())) error(localTerm1, "Expected 'stops'");
/*      */ 
/* 1914 */     localTerm2 = localTerm1;
/* 1915 */     if ((localTerm1 = localTerm1.nextInSeries) == null) error(localTerm2, "Expected '(<number>, <number>)'");
/*      */ 
/* 1917 */     int i = 0;
/* 1918 */     Term localTerm3 = localTerm1;
/*      */     do {
/* 1920 */       i++;
/*      */     }
/* 1922 */     while (((localTerm3 = localTerm3.nextInSeries) != null) && (localTerm3.token != null) && (localTerm3.token.getType() == 34));
/*      */ 
/* 1925 */     ParsedValue[] arrayOfParsedValue1 = new ParsedValue[i];
/* 1926 */     int j = 0;
/*      */     do {
/* 1928 */       localParsedValue2 = stop(localTerm1);
/* 1929 */       if (localParsedValue2 != null) arrayOfParsedValue1[(j++)] = localParsedValue2;
/* 1930 */       localTerm2 = localTerm1;
/* 1931 */     }while (((localTerm1 = localTerm1.nextInSeries) != null) && (localTerm1.token.getType() == 34));
/*      */ 
/* 1935 */     ParsedValue localParsedValue2 = cycleMethod(localTerm1);
/*      */ 
/* 1937 */     if (localParsedValue2 == null)
/*      */     {
/* 1939 */       localParsedValue2 = new ParsedValue(CycleMethod.NO_CYCLE, null);
/*      */ 
/* 1943 */       if (localTerm1 != null) {
/* 1944 */         paramTerm.nextInSeries = localTerm1;
/*      */       }
/*      */       else
/*      */       {
/* 1951 */         paramTerm.nextInSeries = null;
/* 1952 */         paramTerm.nextLayer = localTerm2.nextLayer;
/*      */       }
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1962 */       paramTerm.nextInSeries = localTerm1.nextInSeries;
/* 1963 */       paramTerm.nextLayer = localTerm1.nextLayer;
/*      */     }
/*      */ 
/* 1966 */     ParsedValue[] arrayOfParsedValue2 = new ParsedValue[6 + arrayOfParsedValue1.length];
/* 1967 */     int k = 0;
/* 1968 */     arrayOfParsedValue2[(k++)] = localParsedValue1;
/* 1969 */     arrayOfParsedValue2[(k++)] = localObject1;
/* 1970 */     arrayOfParsedValue2[(k++)] = (localObject2 != null ? localObject2[0] : null);
/* 1971 */     arrayOfParsedValue2[(k++)] = (localObject2 != null ? localObject2[1] : null);
/* 1972 */     arrayOfParsedValue2[(k++)] = localObject3;
/* 1973 */     arrayOfParsedValue2[(k++)] = localParsedValue2;
/* 1974 */     for (int m = 0; m < arrayOfParsedValue1.length; m++) arrayOfParsedValue2[(k++)] = arrayOfParsedValue1[m];
/* 1975 */     return new ParsedValue(arrayOfParsedValue2, PaintConverter.RadialGradientConverter.getInstance());
/*      */   }
/*      */ 
/*      */   private ParsedValue parseRadialGradient(Term paramTerm)
/*      */     throws CSSParser.ParseException
/*      */   {
/* 1992 */     String str = paramTerm.token != null ? paramTerm.token.getText() : null;
/* 1993 */     if (!"radial-gradient".regionMatches(true, 0, str, 0, 15))
/*      */     {
/* 1995 */       error(paramTerm, "Expected 'radial-gradient'");
/*      */     }
/*      */     Term localTerm1;
/* 2000 */     if (((localTerm1 = paramTerm.firstArg) == null) || (localTerm1.token == null) || (localTerm1.token.getText().isEmpty()))
/*      */     {
/* 2003 */       error(paramTerm, "Expected 'focus-angle <angle>' or 'focus-distance <percentage>' or 'center <point>' or 'radius [<length> | <percentage>]'");
/*      */     }
/*      */ 
/* 2010 */     Term localTerm2 = localTerm1;
/* 2011 */     ParsedValue localParsedValue1 = null;
/* 2012 */     ParsedValue localParsedValue2 = null;
/* 2013 */     ParsedValue[] arrayOfParsedValue1 = null;
/* 2014 */     ParsedValue localParsedValue3 = null;
/*      */ 
/* 2016 */     if ("focus-angle".equalsIgnoreCase(localTerm1.token.getText()))
/*      */     {
/* 2018 */       localTerm2 = localTerm1;
/* 2019 */       if (((localTerm1 = localTerm1.nextInSeries) == null) || (!isSize(localTerm1.token))) {
/* 2020 */         error(localTerm2, "Expected '<angle>'");
/*      */       }
/* 2022 */       localObject1 = size(localTerm1.token);
/* 2023 */       switch (1.$SwitchMap$com$sun$javafx$css$SizeUnits[localObject1.getUnits().ordinal()]) {
/*      */       case 1:
/*      */       case 2:
/*      */       case 3:
/*      */       case 4:
/*      */       case 5:
/* 2029 */         break;
/*      */       default:
/* 2031 */         error(localTerm1, "Expected [deg | rad | grad | turn ]");
/*      */       }
/* 2033 */       localParsedValue1 = new ParsedValue(localObject1, null);
/*      */ 
/* 2035 */       localTerm2 = localTerm1;
/* 2036 */       if ((localTerm1 = localTerm1.nextArg) == null) {
/* 2037 */         error(localTerm2, "Expected 'focus-distance <percentage>' or 'center <point>' or 'radius [<length> | <percentage>]'");
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2043 */     if ("focus-distance".equalsIgnoreCase(localTerm1.token.getText()))
/*      */     {
/* 2045 */       localTerm2 = localTerm1;
/* 2046 */       if (((localTerm1 = localTerm1.nextInSeries) == null) || (!isSize(localTerm1.token))) {
/* 2047 */         error(localTerm2, "Expected '<percentage>'");
/*      */       }
/* 2049 */       localObject1 = size(localTerm1.token);
/*      */ 
/* 2053 */       switch (1.$SwitchMap$com$sun$javafx$css$SizeUnits[localObject1.getUnits().ordinal()]) {
/*      */       case 6:
/* 2055 */         break;
/*      */       default:
/* 2057 */         error(localTerm1, "Expected '%'");
/*      */       }
/* 2059 */       localParsedValue2 = new ParsedValue(localObject1, null);
/*      */ 
/* 2061 */       localTerm2 = localTerm1;
/* 2062 */       if ((localTerm1 = localTerm1.nextArg) == null) {
/* 2063 */         error(localTerm2, "Expected 'center <center>' or 'radius <length>'");
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2068 */     if ("center".equalsIgnoreCase(localTerm1.token.getText()))
/*      */     {
/* 2070 */       localTerm2 = localTerm1;
/* 2071 */       if ((localTerm1 = localTerm1.nextInSeries) == null) error(localTerm2, "Expected '<point>'");
/*      */ 
/* 2073 */       localObject1 = parseSize(localTerm1);
/*      */ 
/* 2075 */       localTerm2 = localTerm1;
/* 2076 */       if ((localTerm1 = localTerm1.nextInSeries) == null) error(localTerm2, "Expected '<point>'");
/*      */ 
/* 2078 */       localObject2 = parseSize(localTerm1);
/*      */ 
/* 2080 */       arrayOfParsedValue1 = new ParsedValue[] { localObject1, localObject2 };
/*      */ 
/* 2082 */       localTerm2 = localTerm1;
/* 2083 */       if ((localTerm1 = localTerm1.nextArg) == null) {
/* 2084 */         error(localTerm2, "Expected 'radius [<length> | <percentage>]'");
/*      */       }
/*      */     }
/* 2087 */     if ("radius".equalsIgnoreCase(localTerm1.token.getText()))
/*      */     {
/* 2089 */       localTerm2 = localTerm1;
/* 2090 */       if (((localTerm1 = localTerm1.nextInSeries) == null) || (!isSize(localTerm1.token))) {
/* 2091 */         error(localTerm2, "Expected '[<length> | <percentage>]'");
/*      */       }
/* 2093 */       localParsedValue3 = parseSize(localTerm1);
/*      */ 
/* 2095 */       localTerm2 = localTerm1;
/* 2096 */       if ((localTerm1 = localTerm1.nextArg) == null) {
/* 2097 */         error(localTerm2, "Expected 'radius [<length> | <percentage>]'");
/*      */       }
/*      */     }
/* 2100 */     Object localObject1 = CycleMethod.NO_CYCLE;
/* 2101 */     if ("reflect".equalsIgnoreCase(localTerm1.token.getText())) {
/* 2102 */       localObject1 = CycleMethod.REFLECT;
/* 2103 */       localTerm2 = localTerm1;
/* 2104 */       localTerm1 = localTerm1.nextArg;
/* 2105 */     } else if ("repeat".equalsIgnoreCase(localTerm1.token.getText())) {
/* 2106 */       localObject1 = CycleMethod.REFLECT;
/* 2107 */       localTerm2 = localTerm1;
/* 2108 */       localTerm1 = localTerm1.nextArg;
/*      */     }
/*      */ 
/* 2111 */     if ((localTerm1 == null) || (localTerm1.token == null) || (localTerm1.token.getText().isEmpty()))
/*      */     {
/* 2114 */       error(localTerm2, "Expected '<color-stop>'");
/*      */     }
/*      */ 
/* 2117 */     Object localObject2 = parseColorStops(localTerm1);
/*      */ 
/* 2119 */     ParsedValue[] arrayOfParsedValue2 = new ParsedValue[6 + localObject2.length];
/* 2120 */     int i = 0;
/* 2121 */     arrayOfParsedValue2[(i++)] = localParsedValue1;
/* 2122 */     arrayOfParsedValue2[(i++)] = localParsedValue2;
/* 2123 */     arrayOfParsedValue2[(i++)] = (arrayOfParsedValue1 != null ? arrayOfParsedValue1[0] : null);
/* 2124 */     arrayOfParsedValue2[(i++)] = (arrayOfParsedValue1 != null ? arrayOfParsedValue1[1] : null);
/* 2125 */     arrayOfParsedValue2[(i++)] = localParsedValue3;
/* 2126 */     arrayOfParsedValue2[(i++)] = new ParsedValue(localObject1, null);
/* 2127 */     for (int j = 0; j < localObject2.length; j++) arrayOfParsedValue2[(i++)] = localObject2[j];
/* 2128 */     return new ParsedValue(arrayOfParsedValue2, PaintConverter.RadialGradientConverter.getInstance());
/*      */   }
/*      */ 
/*      */   private ParsedValue<ParsedValue<?, Paint>[], Paint[]> parsePaintLayers(Term paramTerm)
/*      */     throws CSSParser.ParseException
/*      */   {
/* 2138 */     int i = numberOfLayers(paramTerm);
/*      */ 
/* 2140 */     ParsedValue[] arrayOfParsedValue = new ParsedValue[i];
/*      */ 
/* 2142 */     Term localTerm = paramTerm;
/* 2143 */     int j = 0;
/*      */     do
/*      */     {
/* 2146 */       if ((localTerm.token == null) || (localTerm.token.getText() == null) || (localTerm.token.getText().isEmpty()))
/*      */       {
/* 2148 */         error(localTerm, "Expected '<paint>'");
/*      */       }
/* 2150 */       arrayOfParsedValue[(j++)] = parse(localTerm);
/*      */ 
/* 2152 */       localTerm = nextLayer(localTerm);
/* 2153 */     }while (localTerm != null);
/*      */ 
/* 2155 */     return new ParsedValue(arrayOfParsedValue, PaintConverter.SequenceConverter.getInstance());
/*      */   }
/*      */ 
/*      */   private ParsedValue<?, Size>[] parseSizeSeries(Term paramTerm)
/*      */     throws CSSParser.ParseException
/*      */   {
/* 2164 */     Term localTerm = paramTerm;
/* 2165 */     ParsedValue[] arrayOfParsedValue = new ParsedValue[4];
/* 2166 */     int i = 0;
/*      */ 
/* 2168 */     while ((i < 4) && (localTerm != null)) {
/* 2169 */       arrayOfParsedValue[(i++)] = parseSize(localTerm);
/* 2170 */       localTerm = localTerm.nextInSeries;
/*      */     }
/*      */ 
/* 2173 */     if (i < 2) arrayOfParsedValue[1] = arrayOfParsedValue[0];
/* 2174 */     if (i < 3) arrayOfParsedValue[2] = arrayOfParsedValue[0];
/* 2175 */     if (i < 4) arrayOfParsedValue[3] = arrayOfParsedValue[1];
/*      */ 
/* 2177 */     return arrayOfParsedValue;
/*      */   }
/*      */ 
/*      */   private ParsedValue<ParsedValue<ParsedValue<?, Size>[], Insets>[], Insets[]> parseInsetsLayers(Term paramTerm)
/*      */     throws CSSParser.ParseException
/*      */   {
/* 2185 */     int i = numberOfLayers(paramTerm);
/*      */ 
/* 2187 */     Term localTerm = paramTerm;
/* 2188 */     int j = 0;
/* 2189 */     ParsedValue[] arrayOfParsedValue1 = new ParsedValue[i];
/*      */ 
/* 2191 */     while (localTerm != null) {
/* 2192 */       ParsedValue[] arrayOfParsedValue2 = parseSizeSeries(localTerm);
/* 2193 */       arrayOfParsedValue1[(j++)] = new ParsedValue(arrayOfParsedValue2, InsetsConverter.getInstance());
/* 2194 */       while (localTerm.nextInSeries != null) {
/* 2195 */         localTerm = localTerm.nextInSeries;
/*      */       }
/* 2197 */       localTerm = nextLayer(localTerm);
/*      */     }
/*      */ 
/* 2200 */     return new ParsedValue(arrayOfParsedValue1, InsetsConverter.SequenceConverter.getInstance());
/*      */   }
/*      */ 
/*      */   private ParsedValue<ParsedValue<ParsedValue<?, Size>[], Margins>[], Margins[]> parseMarginsLayers(Term paramTerm)
/*      */     throws CSSParser.ParseException
/*      */   {
/* 2207 */     int i = numberOfLayers(paramTerm);
/*      */ 
/* 2209 */     Term localTerm = paramTerm;
/* 2210 */     int j = 0;
/* 2211 */     ParsedValue[] arrayOfParsedValue1 = new ParsedValue[i];
/*      */ 
/* 2213 */     while (localTerm != null) {
/* 2214 */       ParsedValue[] arrayOfParsedValue2 = parseSizeSeries(localTerm);
/* 2215 */       arrayOfParsedValue1[(j++)] = new ParsedValue(arrayOfParsedValue2, Margins.Converter.getInstance());
/* 2216 */       while (localTerm.nextInSeries != null) {
/* 2217 */         localTerm = localTerm.nextInSeries;
/*      */       }
/* 2219 */       localTerm = nextLayer(localTerm);
/*      */     }
/*      */ 
/* 2222 */     return new ParsedValue(arrayOfParsedValue1, Margins.SequenceConverter.getInstance());
/*      */   }
/*      */ 
/*      */   private ParsedValue<ParsedValue<?, Size>[], BackgroundImage.BackgroundPosition> parseBackgroundPosition(Term paramTerm)
/*      */     throws CSSParser.ParseException
/*      */   {
/* 2256 */     if ((paramTerm.token == null) || (paramTerm.token.getText() == null) || (paramTerm.token.getText().isEmpty()))
/*      */     {
/* 2258 */       error(paramTerm, "Expected '<bg-position>'");
/*      */     }
/* 2260 */     Term localTerm1 = paramTerm;
/* 2261 */     Token localToken1 = paramTerm.token;
/*      */ 
/* 2263 */     Term localTerm2 = localTerm1.nextInSeries;
/* 2264 */     Token localToken2 = localTerm2 != null ? localTerm2.token : null;
/*      */ 
/* 2266 */     Term localTerm3 = localTerm2 != null ? localTerm2.nextInSeries : null;
/* 2267 */     Token localToken3 = localTerm3 != null ? localTerm3.token : null;
/*      */ 
/* 2269 */     Term localTerm4 = localTerm3 != null ? localTerm3.nextInSeries : null;
/* 2270 */     Token localToken4 = localTerm4 != null ? localTerm4.token : null;
/*      */     Object localObject4;
/*      */     Object localObject3;
/*      */     Object localObject2;
/* 2273 */     Object localObject1 = localObject2 = localObject3 = localObject4 = ZERO_PERCENT;
/*      */ 
/* 2279 */     if ((localToken2 != null) && (localToken3 != null))
/*      */     {
/* 2287 */       if (localToken2.getType() == 11) {
/* 2288 */         if (localToken4 != null) {
/* 2289 */           error(localTerm2, "Unexpected value in '<bg-position>'");
/*      */         }
/* 2291 */         localToken4 = localToken3; localTerm4 = localTerm3;
/* 2292 */         localToken3 = localToken2; localTerm3 = localTerm2;
/* 2293 */         localToken2 = null; localTerm2 = null;
/*      */       }
/*      */ 
/* 2298 */       if ((localToken1.getType() != 11) || (localToken1.getText() == null) || (localToken1.getText().isEmpty()))
/*      */       {
/* 2301 */         error(localTerm1, "Expected 'center', 'left' or 'right'");
/*      */       }
/* 2303 */       localObject5 = null;
/* 2304 */       if ((localToken2 != null) && (isSize(localToken2)))
/* 2305 */         localObject5 = parseSize(localTerm2);
/*      */       else {
/* 2307 */         error(localTerm2, "Expected '<size>'");
/*      */       }
/*      */ 
/* 2310 */       if ((localToken3.getType() != 11) || (localToken3.getText() == null) || (localToken3.getText().isEmpty()))
/*      */       {
/* 2313 */         error(localTerm3, "Expected 'center', 'left' or 'right'");
/*      */       }
/* 2315 */       ParsedValue localParsedValue = null;
/* 2316 */       if (localToken4 != null) {
/* 2317 */         if (isSize(localToken4))
/* 2318 */           localParsedValue = parseSize(localTerm4);
/*      */         else {
/* 2320 */           error(localTerm4, "Expected '<size>'");
/*      */         }
/*      */       }
/*      */ 
/* 2324 */       String str = localToken1.getText().toLowerCase();
/*      */ 
/* 2326 */       if ("center".equals(str))
/*      */       {
/* 2328 */         localObject4 = FIFTY_PERCENT;
/* 2329 */         if (localObject5 != null)
/* 2330 */           error(localTerm2, "Unexpected '<size>'");
/*      */       }
/* 2332 */       else if ("left".equals(str))
/*      */       {
/* 2334 */         if (localObject5 != null) localObject4 = localObject5; else
/* 2335 */           localObject4 = ZERO_PERCENT;
/*      */       }
/* 2337 */       else if ("right".equals(str))
/*      */       {
/* 2339 */         if (localObject5 != null) localObject2 = localObject5; else
/* 2340 */           localObject4 = ONE_HUNDRED_PERCENT;
/*      */       }
/*      */       else {
/* 2343 */         error(localTerm1, "Expected 'center', 'left' or 'right'");
/*      */       }
/*      */ 
/* 2346 */       str = localToken3.getText().toLowerCase();
/*      */ 
/* 2348 */       if ("center".equals(str))
/*      */       {
/* 2350 */         localObject1 = FIFTY_PERCENT;
/* 2351 */         if (localParsedValue != null)
/* 2352 */           error(localTerm4, "Unexpected '<size>'");
/*      */       }
/* 2354 */       else if ("top".equals(str))
/*      */       {
/* 2356 */         if (localParsedValue != null) localObject1 = localParsedValue; else
/* 2357 */           localObject1 = ZERO_PERCENT;
/*      */       }
/* 2359 */       else if ("bottom".equals(str))
/*      */       {
/* 2361 */         if (localParsedValue != null) localObject3 = localParsedValue; else
/* 2362 */           localObject1 = ONE_HUNDRED_PERCENT;
/*      */       }
/*      */       else {
/* 2365 */         error(localTerm3, "Expected 'center', 'left' or 'right'");
/*      */       }
/*      */ 
/*      */     }
/* 2377 */     else if (localToken2 != null)
/*      */     {
/* 2379 */       if (localToken1.getType() == 11)
/*      */       {
/* 2381 */         localObject5 = localToken1.getText() != null ? localToken1.getText().toLowerCase() : null;
/*      */ 
/* 2385 */         if ("center".equals(localObject5))
/*      */         {
/* 2387 */           localObject4 = FIFTY_PERCENT;
/*      */         }
/* 2389 */         else if ("left".equals(localObject5))
/*      */         {
/* 2391 */           localObject4 = ZERO_PERCENT;
/*      */         }
/* 2393 */         else if ("right".equals(localObject5))
/*      */         {
/* 2395 */           localObject4 = ONE_HUNDRED_PERCENT;
/*      */         }
/*      */         else
/*      */         {
/* 2399 */           error(localTerm1, "Expected 'center', 'left' or 'right'");
/*      */         }
/*      */ 
/*      */       }
/* 2403 */       else if (isSize(localToken1)) {
/* 2404 */         localObject4 = parseSize(localTerm1);
/*      */       } else {
/* 2406 */         error(localTerm1, "Expected '<size>', 'center', 'left' or 'right'");
/*      */       }
/*      */ 
/* 2410 */       if (localToken2.getType() == 11)
/*      */       {
/* 2412 */         localObject5 = localToken2.getText() != null ? localToken2.getText().toLowerCase() : null;
/*      */ 
/* 2416 */         if ("center".equals(localObject5))
/*      */         {
/* 2418 */           localObject1 = FIFTY_PERCENT;
/*      */         }
/* 2420 */         else if ("top".equals(localObject5))
/*      */         {
/* 2422 */           localObject1 = ZERO_PERCENT;
/*      */         }
/* 2424 */         else if ("bottom".equals(localObject5))
/*      */         {
/* 2426 */           localObject1 = ONE_HUNDRED_PERCENT;
/*      */         }
/*      */         else
/*      */         {
/* 2430 */           error(localTerm2, "Expected 'center', 'left' or 'right'");
/*      */         }
/*      */ 
/*      */       }
/* 2434 */       else if (isSize(localToken2))
/*      */       {
/* 2436 */         localObject1 = parseSize(localTerm2);
/*      */       }
/*      */       else {
/* 2439 */         error(localTerm2, "Expected '<size>', 'center', 'left' or 'right'");
/*      */       }
/*      */ 
/*      */     }
/* 2448 */     else if (localToken1.getType() == 11)
/*      */     {
/* 2450 */       localObject5 = localToken1.getText() != null ? localToken1.getText().toLowerCase() : null;
/*      */ 
/* 2454 */       if ("center".equals(localObject5))
/*      */       {
/* 2456 */         localObject4 = FIFTY_PERCENT;
/* 2457 */         localObject1 = FIFTY_PERCENT;
/*      */       }
/* 2459 */       else if ("left".equals(localObject5))
/*      */       {
/* 2461 */         localObject4 = ZERO_PERCENT;
/* 2462 */         localObject1 = FIFTY_PERCENT;
/*      */       }
/* 2464 */       else if ("right".equals(localObject5))
/*      */       {
/* 2466 */         localObject4 = ONE_HUNDRED_PERCENT;
/* 2467 */         localObject1 = FIFTY_PERCENT;
/*      */       }
/* 2469 */       else if ("top".equals(localObject5))
/*      */       {
/* 2471 */         localObject1 = ZERO_PERCENT;
/* 2472 */         localObject4 = FIFTY_PERCENT;
/*      */       }
/* 2474 */       else if ("bottom".equals(localObject5))
/*      */       {
/* 2476 */         localObject1 = ONE_HUNDRED_PERCENT;
/* 2477 */         localObject4 = FIFTY_PERCENT;
/*      */       }
/*      */       else
/*      */       {
/* 2481 */         error(localTerm1, "Expected 'center', 'left' or 'right'");
/*      */       }
/*      */ 
/*      */     }
/* 2485 */     else if (isSize(localToken1))
/*      */     {
/* 2487 */       localObject4 = parseSize(localTerm1);
/* 2488 */       localObject1 = FIFTY_PERCENT;
/*      */     }
/*      */     else {
/* 2491 */       error(localTerm1, "Expected '<size>', 'center', 'left' or 'right'");
/*      */     }
/*      */ 
/* 2496 */     Object localObject5 = { localObject1, localObject2, localObject3, localObject4 };
/* 2497 */     return new ParsedValue(localObject5, BackgroundImage.BackgroundPositionConverter.getInstance());
/*      */   }
/*      */ 
/*      */   private ParsedValue<ParsedValue<ParsedValue<?, Size>[], BackgroundImage.BackgroundPosition>[], BackgroundImage.BackgroundPosition[]> parseBackgroundPositionLayers(Term paramTerm)
/*      */     throws CSSParser.ParseException
/*      */   {
/* 2503 */     int i = numberOfLayers(paramTerm);
/* 2504 */     ParsedValue[] arrayOfParsedValue = new ParsedValue[i];
/* 2505 */     int j = 0;
/* 2506 */     Term localTerm = paramTerm;
/* 2507 */     while (localTerm != null) {
/* 2508 */       arrayOfParsedValue[(j++)] = parseBackgroundPosition(localTerm);
/* 2509 */       localTerm = nextLayer(localTerm);
/*      */     }
/* 2511 */     return new ParsedValue(arrayOfParsedValue, BackgroundImage.LayeredBackgroundPositionConverter.getInstance());
/*      */   }
/*      */ 
/*      */   private ParsedValue<Repeat, Repeat>[] parseRepeatStyle(Term paramTerm)
/*      */     throws CSSParser.ParseException
/*      */   {
/*      */     Repeat localRepeat2;
/* 2522 */     Repeat localRepeat1 = localRepeat2 = Repeat.NO_REPEAT;
/*      */ 
/* 2524 */     Term localTerm = paramTerm;
/*      */ 
/* 2526 */     if ((localTerm.token == null) || (localTerm.token.getType() != 11) || (localTerm.token.getText() == null) || (localTerm.token.getText().isEmpty()))
/*      */     {
/* 2529 */       error(localTerm, "Expected '<repeat-style>'");
/*      */     }
/* 2531 */     String str = localTerm.token.getText().toLowerCase();
/* 2532 */     if ("repeat-x".equals(str)) {
/* 2533 */       localRepeat1 = Repeat.REPEAT;
/* 2534 */       localRepeat2 = Repeat.NO_REPEAT;
/* 2535 */     } else if ("repeat-y".equals(str)) {
/* 2536 */       localRepeat1 = Repeat.NO_REPEAT;
/* 2537 */       localRepeat2 = Repeat.REPEAT;
/* 2538 */     } else if ("repeat".equals(str)) {
/* 2539 */       localRepeat1 = Repeat.REPEAT;
/* 2540 */       localRepeat2 = Repeat.REPEAT;
/* 2541 */     } else if ("space".equals(str)) {
/* 2542 */       localRepeat1 = Repeat.SPACE;
/* 2543 */       localRepeat2 = Repeat.SPACE;
/* 2544 */     } else if ("round".equals(str)) {
/* 2545 */       localRepeat1 = Repeat.ROUND;
/* 2546 */       localRepeat2 = Repeat.ROUND;
/* 2547 */     } else if ("no-repeat".equals(str)) {
/* 2548 */       localRepeat1 = Repeat.NO_REPEAT;
/* 2549 */       localRepeat2 = Repeat.NO_REPEAT;
/* 2550 */     } else if ("stretch".equals(str)) {
/* 2551 */       localRepeat1 = Repeat.NO_REPEAT;
/* 2552 */       localRepeat2 = Repeat.NO_REPEAT;
/*      */     } else {
/* 2554 */       error(localTerm, new StringBuilder().append("Expected  '<repeat-style>' ").append(str).toString());
/*      */     }
/*      */ 
/* 2557 */     if (((localTerm = localTerm.nextInSeries) != null) && (localTerm.token != null) && (localTerm.token.getType() == 11) && (localTerm.token.getText() != null) && (!localTerm.token.getText().isEmpty()))
/*      */     {
/* 2563 */       str = localTerm.token.getText().toLowerCase();
/* 2564 */       if ("repeat-x".equals(str))
/* 2565 */         error(localTerm, "Unexpected 'repeat-x'");
/* 2566 */       else if ("repeat-y".equals(str))
/* 2567 */         error(localTerm, "Unexpected 'repeat-y'");
/* 2568 */       else if ("repeat".equals(str))
/* 2569 */         localRepeat2 = Repeat.REPEAT;
/* 2570 */       else if ("space".equals(str))
/* 2571 */         localRepeat2 = Repeat.SPACE;
/* 2572 */       else if ("round".equals(str))
/* 2573 */         localRepeat2 = Repeat.ROUND;
/* 2574 */       else if ("no-repeat".equals(str))
/* 2575 */         localRepeat2 = Repeat.NO_REPEAT;
/* 2576 */       else if ("stretch".equals(str))
/* 2577 */         localRepeat2 = Repeat.NO_REPEAT;
/*      */       else {
/* 2579 */         error(localTerm, "Expected  '<repeat-style>'");
/*      */       }
/*      */     }
/*      */ 
/* 2583 */     return new ParsedValue[] { new ParsedValue(localRepeat1, null), new ParsedValue(localRepeat2, null) };
/*      */   }
/*      */ 
/*      */   private ParsedValue<ParsedValue<Repeat, Repeat>[][], BorderImage.BorderImageRepeat[]> parseBorderImageRepeatStyleLayers(Term paramTerm)
/*      */     throws CSSParser.ParseException
/*      */   {
/* 2592 */     int i = numberOfLayers(paramTerm);
/* 2593 */     ParsedValue[][] arrayOfParsedValue; = new ParsedValue[i][];
/* 2594 */     int j = 0;
/* 2595 */     Term localTerm = paramTerm;
/* 2596 */     while (localTerm != null) {
/* 2597 */       arrayOfParsedValue;[(j++)] = parseRepeatStyle(localTerm);
/* 2598 */       localTerm = nextLayer(localTerm);
/*      */     }
/* 2600 */     return new ParsedValue(arrayOfParsedValue;, BorderImage.RepeatConverter.getInstance());
/*      */   }
/*      */ 
/*      */   private ParsedValue<ParsedValue<Repeat, Repeat>[][], BackgroundImage.BackgroundRepeat[]> parseBackgroundRepeatStyleLayers(Term paramTerm)
/*      */     throws CSSParser.ParseException
/*      */   {
/* 2606 */     int i = numberOfLayers(paramTerm);
/* 2607 */     ParsedValue[][] arrayOfParsedValue; = new ParsedValue[i][];
/* 2608 */     int j = 0;
/* 2609 */     Term localTerm = paramTerm;
/* 2610 */     while (localTerm != null) {
/* 2611 */       arrayOfParsedValue;[(j++)] = parseRepeatStyle(localTerm);
/* 2612 */       localTerm = nextLayer(localTerm);
/*      */     }
/* 2614 */     return new ParsedValue(arrayOfParsedValue;, BackgroundImage.BackgroundRepeatConverter.getInstance());
/*      */   }
/*      */ 
/*      */   private ParsedValue<ParsedValue[], BackgroundImage.BackgroundSize> parseBackgroundSize(Term paramTerm)
/*      */     throws CSSParser.ParseException
/*      */   {
/* 2624 */     ParsedValue localParsedValue1 = ZERO_PERCENT; ParsedValue localParsedValue2 = ZERO_PERCENT;
/* 2625 */     int i = 0; int j = 0;
/*      */ 
/* 2627 */     Term localTerm = paramTerm;
/* 2628 */     if (localTerm.token == null) error(localTerm, "Expected '<bg-size>'");
/*      */ 
/* 2630 */     if (localTerm.token.getType() == 11) {
/* 2631 */       localObject = localTerm.token.getText() != null ? localTerm.token.getText().toLowerCase() : null;
/*      */ 
/* 2634 */       if ("auto".equals(localObject)) {
/* 2635 */         localParsedValue2 = ZERO_PERCENT;
/* 2636 */         localParsedValue1 = ZERO_PERCENT;
/* 2637 */       } else if ("cover".equals(localObject)) {
/* 2638 */         i = 1;
/* 2639 */       } else if ("contain".equals(localObject)) {
/* 2640 */         j = 1;
/* 2641 */       } else if ("stretch".equals(localObject)) {
/* 2642 */         localParsedValue2 = ONE_HUNDRED_PERCENT;
/* 2643 */         localParsedValue1 = ONE_HUNDRED_PERCENT;
/*      */       } else {
/* 2645 */         error(localTerm, "Expected 'auto', 'cover', 'contain', or  'stretch'");
/*      */       }
/* 2647 */     } else if (isSize(localTerm.token)) {
/* 2648 */       localParsedValue2 = parseSize(localTerm);
/* 2649 */       localParsedValue1 = ZERO_PERCENT;
/*      */     } else {
/* 2651 */       error(localTerm, "Expected '<bg-size>'");
/*      */     }
/*      */ 
/* 2654 */     if ((localTerm = localTerm.nextInSeries) != null) {
/* 2655 */       if ((i != 0) || (j != 0)) error(localTerm, "Unexpected '<bg-size>'");
/*      */ 
/* 2657 */       if (localTerm.token.getType() == 11) {
/* 2658 */         localObject = localTerm.token.getText() != null ? localTerm.token.getText().toLowerCase() : null;
/*      */ 
/* 2661 */         if ("auto".equals(localObject))
/* 2662 */           localParsedValue1 = ZERO_PERCENT;
/* 2663 */         else if ("cover".equals(localObject))
/* 2664 */           error(localTerm, "Unexpected 'cover'");
/* 2665 */         else if ("contain".equals(localObject))
/* 2666 */           error(localTerm, "Unexpected 'contain'");
/* 2667 */         else if ("stretch".equals(localObject))
/* 2668 */           localParsedValue1 = ONE_HUNDRED_PERCENT;
/*      */         else
/* 2670 */           error(localTerm, "Expected 'auto' or 'stretch'");
/*      */       }
/* 2672 */       else if (isSize(localTerm.token)) {
/* 2673 */         localParsedValue1 = parseSize(localTerm);
/*      */       } else {
/* 2675 */         error(localTerm, "Expected '<bg-size>'");
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2680 */     Object localObject = { localParsedValue2, localParsedValue1, new ParsedValue(i != 0 ? "true" : "false", BooleanConverter.getInstance()), new ParsedValue(j != 0 ? "true" : "false", BooleanConverter.getInstance()) };
/*      */ 
/* 2687 */     return new ParsedValue(localObject, BackgroundImage.BackgroundSizeConverter.getInstance());
/*      */   }
/*      */ 
/*      */   private ParsedValue<ParsedValue<ParsedValue[], BackgroundImage.BackgroundSize>[], BackgroundImage.BackgroundSize[]> parseBackgroundSizeLayers(Term paramTerm)
/*      */     throws CSSParser.ParseException
/*      */   {
/* 2693 */     int i = numberOfLayers(paramTerm);
/* 2694 */     ParsedValue[] arrayOfParsedValue = new ParsedValue[i];
/* 2695 */     int j = 0;
/* 2696 */     Term localTerm = paramTerm;
/* 2697 */     while (localTerm != null) {
/* 2698 */       arrayOfParsedValue[(j++)] = parseBackgroundSize(localTerm);
/* 2699 */       localTerm = nextLayer(localTerm);
/*      */     }
/* 2701 */     return new ParsedValue(arrayOfParsedValue, BackgroundImage.LayeredBackgroundSizeConverter.getInstance());
/*      */   }
/*      */ 
/*      */   private ParsedValue<ParsedValue<?, Paint>[], Paint[]> parseBorderPaint(Term paramTerm)
/*      */     throws CSSParser.ParseException
/*      */   {
/* 2707 */     Term localTerm = paramTerm;
/* 2708 */     ParsedValue[] arrayOfParsedValue = new ParsedValue[4];
/* 2709 */     int i = 0;
/*      */ 
/* 2711 */     while (localTerm != null) {
/* 2712 */       if (localTerm.token == null) error(localTerm, "Expected '<paint>'");
/* 2713 */       arrayOfParsedValue[(i++)] = parse(localTerm);
/* 2714 */       localTerm = localTerm.nextInSeries;
/*      */     }
/*      */ 
/* 2717 */     if (i < 2) arrayOfParsedValue[1] = arrayOfParsedValue[0];
/* 2718 */     if (i < 3) arrayOfParsedValue[2] = arrayOfParsedValue[0];
/* 2719 */     if (i < 4) arrayOfParsedValue[3] = arrayOfParsedValue[1];
/*      */ 
/* 2721 */     return new ParsedValue(arrayOfParsedValue, StrokeBorder.BorderPaintConverter.getInstance());
/*      */   }
/*      */ 
/*      */   private ParsedValue<ParsedValue<ParsedValue<?, Paint>[], Paint[]>[], Paint[][]> parseBorderPaintLayers(Term paramTerm)
/*      */     throws CSSParser.ParseException
/*      */   {
/* 2727 */     int i = numberOfLayers(paramTerm);
/* 2728 */     ParsedValue[] arrayOfParsedValue = new ParsedValue[i];
/* 2729 */     int j = 0;
/* 2730 */     Term localTerm = paramTerm;
/* 2731 */     while (localTerm != null) {
/* 2732 */       arrayOfParsedValue[(j++)] = parseBorderPaint(localTerm);
/* 2733 */       localTerm = nextLayer(localTerm);
/*      */     }
/* 2735 */     return new ParsedValue(arrayOfParsedValue, StrokeBorder.LayeredBorderPaintConverter.getInstance());
/*      */   }
/*      */ 
/*      */   private ParsedValue<ParsedValue<ParsedValue[], BorderStyle>[], BorderStyle[]> parseBorderStyleSeries(Term paramTerm)
/*      */     throws CSSParser.ParseException
/*      */   {
/* 2742 */     Term localTerm = paramTerm;
/* 2743 */     ParsedValue[] arrayOfParsedValue = new ParsedValue[4];
/* 2744 */     int i = 0;
/* 2745 */     while (localTerm != null) {
/* 2746 */       arrayOfParsedValue[(i++)] = parseBorderStyle(localTerm);
/* 2747 */       localTerm = localTerm.nextInSeries;
/*      */     }
/*      */ 
/* 2750 */     if (i < 2) arrayOfParsedValue[1] = arrayOfParsedValue[0];
/* 2751 */     if (i < 3) arrayOfParsedValue[2] = arrayOfParsedValue[0];
/* 2752 */     if (i < 4) arrayOfParsedValue[3] = arrayOfParsedValue[1];
/*      */ 
/* 2754 */     return new ParsedValue(arrayOfParsedValue, StrokeBorder.BorderStyleSequenceConverter.getInstance());
/*      */   }
/*      */ 
/*      */   private ParsedValue<ParsedValue<ParsedValue<ParsedValue[], BorderStyle>[], BorderStyle[]>[], BorderStyle[][]> parseBorderStyleLayers(Term paramTerm)
/*      */     throws CSSParser.ParseException
/*      */   {
/* 2761 */     int i = numberOfLayers(paramTerm);
/* 2762 */     ParsedValue[] arrayOfParsedValue = new ParsedValue[i];
/* 2763 */     int j = 0;
/* 2764 */     Term localTerm = paramTerm;
/* 2765 */     while (localTerm != null) {
/* 2766 */       arrayOfParsedValue[(j++)] = parseBorderStyleSeries(localTerm);
/* 2767 */       localTerm = nextLayer(localTerm);
/*      */     }
/* 2769 */     return new ParsedValue(arrayOfParsedValue, StrokeBorder.LayeredBorderStyleConverter.getInstance());
/*      */   }
/*      */ 
/*      */   private String getKeyword(Term paramTerm)
/*      */   {
/* 2774 */     if ((paramTerm != null) && (paramTerm.token != null) && (paramTerm.token.getType() == 11) && (paramTerm.token.getText() != null) && (!paramTerm.token.getText().isEmpty()))
/*      */     {
/* 2780 */       return paramTerm.token.getText().toLowerCase();
/*      */     }
/* 2782 */     return null;
/*      */   }
/*      */ 
/*      */   private ParsedValue<ParsedValue[], BorderStyle> parseBorderStyle(Term paramTerm)
/*      */     throws CSSParser.ParseException
/*      */   {
/* 2794 */     ParsedValue localParsedValue1 = null;
/* 2795 */     ParsedValue localParsedValue2 = null;
/* 2796 */     ParsedValue localParsedValue3 = null;
/* 2797 */     Object localObject1 = null;
/* 2798 */     Object localObject2 = null;
/* 2799 */     ParsedValue localParsedValue4 = null;
/*      */ 
/* 2801 */     Term localTerm1 = paramTerm;
/*      */ 
/* 2803 */     localParsedValue1 = dashStyle(localTerm1);
/*      */ 
/* 2805 */     Term localTerm2 = localTerm1;
/* 2806 */     localTerm1 = localTerm1.nextInSeries;
/* 2807 */     String str = getKeyword(localTerm1);
/*      */ 
/* 2810 */     if ("phase".equals(str))
/*      */     {
/* 2812 */       localTerm2 = localTerm1;
/* 2813 */       if (((localTerm1 = localTerm1.nextInSeries) == null) || (localTerm1.token == null) || (!isSize(localTerm1.token)))
/*      */       {
/* 2815 */         error(localTerm1, "Expected '<size>'");
/*      */       }
/* 2817 */       localObject3 = parseSize(localTerm1);
/* 2818 */       localParsedValue2 = new ParsedValue(localObject3, SizeConverter.getInstance());
/*      */ 
/* 2820 */       localTerm2 = localTerm1;
/* 2821 */       localTerm1 = localTerm1.nextInSeries;
/*      */     }
/*      */ 
/* 2825 */     localParsedValue3 = parseStrokeType(localTerm1);
/* 2826 */     if (localParsedValue3 != null) {
/* 2827 */       localTerm2 = localTerm1;
/* 2828 */       localTerm1 = localTerm1.nextInSeries;
/*      */     }
/*      */ 
/* 2831 */     str = getKeyword(localTerm1);
/*      */ 
/* 2833 */     if ("line-join".equals(str))
/*      */     {
/* 2835 */       localTerm2 = localTerm1;
/* 2836 */       localTerm1 = localTerm1.nextInSeries;
/*      */ 
/* 2838 */       localObject3 = parseStrokeLineJoin(localTerm1);
/* 2839 */       if (localObject3 != null) {
/* 2840 */         localObject1 = localObject3[0];
/* 2841 */         localObject2 = localObject3[1];
/*      */       } else {
/* 2843 */         error(localTerm1, "Expected 'miter <size>?', 'bevel' or 'round'");
/*      */       }
/* 2845 */       localTerm2 = localTerm1;
/* 2846 */       localTerm1 = localTerm1.nextInSeries;
/* 2847 */       str = getKeyword(localTerm1);
/*      */     }
/*      */ 
/* 2850 */     if ("line-cap".equals(str))
/*      */     {
/* 2852 */       localTerm2 = localTerm1;
/* 2853 */       localTerm1 = localTerm1.nextInSeries;
/*      */ 
/* 2855 */       localParsedValue4 = parseStrokeLineCap(localTerm1);
/* 2856 */       if (localParsedValue4 == null) {
/* 2857 */         error(localTerm1, "Expected 'square', 'butt' or 'round'");
/*      */       }
/*      */ 
/* 2860 */       localTerm2 = localTerm1;
/* 2861 */       localTerm1 = localTerm1.nextInSeries;
/*      */     }
/*      */ 
/* 2864 */     if (localTerm1 != null)
/*      */     {
/* 2867 */       paramTerm.nextInSeries = localTerm1;
/*      */     }
/*      */     else
/*      */     {
/* 2872 */       paramTerm.nextInSeries = null;
/* 2873 */       paramTerm.nextLayer = localTerm2.nextLayer;
/*      */     }
/*      */ 
/* 2876 */     Object localObject3 = { localParsedValue1, localParsedValue2, localParsedValue3, localObject1, localObject2, localParsedValue4 };
/*      */ 
/* 2885 */     return new ParsedValue(localObject3, StrokeBorder.BorderStyleConverter.getInstance());
/*      */   }
/*      */ 
/*      */   private ParsedValue<ParsedValue<?, Size>[], Double[]> dashStyle(Term paramTerm)
/*      */     throws CSSParser.ParseException
/*      */   {
/* 2893 */     if (paramTerm.token == null) error(paramTerm, "Expected '<dash-style>'");
/*      */ 
/* 2895 */     int i = paramTerm.token.getType();
/*      */ 
/* 2897 */     ParsedValue localParsedValue = null;
/* 2898 */     if (i == 11)
/* 2899 */       localParsedValue = borderStyle(paramTerm);
/* 2900 */     else if (i == 12)
/* 2901 */       localParsedValue = segments(paramTerm);
/*      */     else {
/* 2903 */       error(paramTerm, "Expected '<dash-style>'");
/*      */     }
/*      */ 
/* 2906 */     return localParsedValue;
/*      */   }
/*      */ 
/*      */   private ParsedValue<ParsedValue<?, Size>[], Double[]> borderStyle(Term paramTerm)
/*      */     throws CSSParser.ParseException
/*      */   {
/* 2940 */     if ((paramTerm.token == null) || (paramTerm.token.getType() != 11) || (paramTerm.token.getText() == null) || (paramTerm.token.getText().isEmpty()))
/*      */     {
/* 2943 */       error(paramTerm, "Expected '<border-style>'");
/*      */     }
/* 2945 */     String str = paramTerm.token.getText().toLowerCase();
/*      */ 
/* 2947 */     if ("none".equals(str))
/* 2948 */       return NONE;
/* 2949 */     if ("hidden".equals(str))
/* 2950 */       return NONE;
/* 2951 */     if ("dotted".equals(str))
/* 2952 */       return DOTTED;
/* 2953 */     if ("dashed".equals(str))
/* 2954 */       return DASHED;
/* 2955 */     if ("solid".equals(str))
/* 2956 */       return SOLID;
/* 2957 */     if ("double".equals(str))
/* 2958 */       error(paramTerm, "Unsupported <border-style> 'double'");
/* 2959 */     else if ("groove".equals(str))
/* 2960 */       error(paramTerm, "Unsupported <border-style> 'groove'");
/* 2961 */     else if ("ridge".equals(str))
/* 2962 */       error(paramTerm, "Unsupported <border-style> 'ridge'");
/* 2963 */     else if ("inset".equals(str))
/* 2964 */       error(paramTerm, "Unsupported <border-style> 'inset'");
/* 2965 */     else if ("outset".equals(str))
/* 2966 */       error(paramTerm, "Unsupported <border-style> 'outset'");
/*      */     else {
/* 2968 */       error(paramTerm, new StringBuilder().append("Unsupported <border-style> '").append(str).append("'").toString());
/*      */     }
/*      */ 
/* 2972 */     return SOLID;
/*      */   }
/*      */ 
/*      */   private ParsedValue<ParsedValue<?, Size>[], Double[]> segments(Term paramTerm)
/*      */     throws CSSParser.ParseException
/*      */   {
/* 2979 */     String str = paramTerm.token != null ? paramTerm.token.getText() : null;
/* 2980 */     if (!"segments".regionMatches(true, 0, str, 0, 8)) {
/* 2981 */       error(paramTerm, "Expected 'segments'");
/*      */     }
/*      */ 
/* 2984 */     Term localTerm = paramTerm.firstArg;
/* 2985 */     if (localTerm == null) error(localTerm, "Expected '<size>'");
/*      */ 
/* 2987 */     int i = numberOfArgs(paramTerm);
/* 2988 */     ParsedValue[] arrayOfParsedValue = new ParsedValue[i];
/* 2989 */     int j = 0;
/* 2990 */     while (localTerm != null) {
/* 2991 */       arrayOfParsedValue[(j++)] = parseSize(localTerm);
/* 2992 */       localTerm = localTerm.nextArg;
/*      */     }
/*      */ 
/* 2995 */     return new ParsedValue(arrayOfParsedValue, SizeConverter.SequenceConverter.getInstance());
/*      */   }
/*      */ 
/*      */   private ParsedValue<String, StrokeType> parseStrokeType(Term paramTerm)
/*      */     throws CSSParser.ParseException
/*      */   {
/* 3002 */     String str = getKeyword(paramTerm);
/*      */ 
/* 3005 */     if (("centered".equals(str)) || ("inside".equals(str)) || ("outside".equals(str)))
/*      */     {
/* 3009 */       return new ParsedValue(str, new EnumConverter(StrokeType.class));
/*      */     }
/*      */ 
/* 3012 */     return null;
/*      */   }
/*      */ 
/*      */   private ParsedValue[] parseStrokeLineJoin(Term paramTerm)
/*      */     throws CSSParser.ParseException
/*      */   {
/* 3023 */     String str = getKeyword(paramTerm);
/*      */ 
/* 3025 */     if (("miter".equals(str)) || ("bevel".equals(str)) || ("round".equals(str)))
/*      */     {
/* 3029 */       ParsedValue localParsedValue1 = new ParsedValue(str, new EnumConverter(StrokeLineJoin.class));
/*      */ 
/* 3032 */       ParsedValue localParsedValue2 = null;
/* 3033 */       if ("miter".equals(str))
/*      */       {
/* 3035 */         Term localTerm = paramTerm.nextInSeries;
/* 3036 */         if ((localTerm != null) && (localTerm.token != null) && (isSize(localTerm.token)))
/*      */         {
/* 3040 */           paramTerm.nextInSeries = localTerm.nextInSeries;
/* 3041 */           ParsedValue localParsedValue3 = parseSize(localTerm);
/* 3042 */           localParsedValue2 = new ParsedValue(localParsedValue3, SizeConverter.getInstance());
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 3047 */       return new ParsedValue[] { localParsedValue1, localParsedValue2 };
/*      */     }
/* 3049 */     return null;
/*      */   }
/*      */ 
/*      */   private ParsedValue<String, StrokeLineCap> parseStrokeLineCap(Term paramTerm)
/*      */     throws CSSParser.ParseException
/*      */   {
/* 3057 */     String str = getKeyword(paramTerm);
/*      */ 
/* 3059 */     if (("square".equals(str)) || ("butt".equals(str)) || ("round".equals(str)))
/*      */     {
/* 3063 */       return new ParsedValue(str, new EnumConverter(StrokeLineCap.class));
/*      */     }
/* 3065 */     return null;
/*      */   }
/*      */ 
/*      */   private ParsedValue<ParsedValue[], BorderImage.BorderImageSlice> parseBorderImageSlice(Term paramTerm)
/*      */     throws CSSParser.ParseException
/*      */   {
/* 3075 */     Term localTerm = paramTerm;
/* 3076 */     if ((localTerm.token == null) || (!isSize(localTerm.token))) {
/* 3077 */       error(localTerm, "Expected '<size>'");
/*      */     }
/* 3079 */     ParsedValue[] arrayOfParsedValue1 = new ParsedValue[4];
/* 3080 */     Boolean localBoolean = Boolean.FALSE;
/*      */ 
/* 3082 */     int i = 0;
/* 3083 */     while ((i < 4) && (localTerm != null)) {
/* 3084 */       arrayOfParsedValue1[(i++)] = parseSize(localTerm);
/*      */ 
/* 3086 */       if (((localTerm = localTerm.nextInSeries) != null) && (localTerm.token != null) && (localTerm.token.getType() == 11) && 
/* 3090 */         ("fill".equalsIgnoreCase(localTerm.token.getText()))) {
/* 3091 */         localBoolean = Boolean.TRUE;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 3097 */     if (i < 2) arrayOfParsedValue1[1] = arrayOfParsedValue1[0];
/* 3098 */     if (i < 3) arrayOfParsedValue1[2] = arrayOfParsedValue1[0];
/* 3099 */     if (i < 4) arrayOfParsedValue1[3] = arrayOfParsedValue1[1];
/*      */ 
/* 3101 */     ParsedValue[] arrayOfParsedValue2 = { new ParsedValue(arrayOfParsedValue1, InsetsConverter.getInstance()), new ParsedValue(localBoolean, null) };
/*      */ 
/* 3105 */     return new ParsedValue(arrayOfParsedValue2, BorderImage.SliceConverter.getInstance());
/*      */   }
/*      */ 
/*      */   private ParsedValue<ParsedValue<ParsedValue[], BorderImage.BorderImageSlice>[], BorderImage.BorderImageSlice[]> parseBorderImageSliceLayers(Term paramTerm)
/*      */     throws CSSParser.ParseException
/*      */   {
/* 3111 */     int i = numberOfLayers(paramTerm);
/* 3112 */     ParsedValue[] arrayOfParsedValue = new ParsedValue[i];
/* 3113 */     int j = 0;
/* 3114 */     Term localTerm = paramTerm;
/* 3115 */     while (localTerm != null) {
/* 3116 */       arrayOfParsedValue[(j++)] = parseBorderImageSlice(localTerm);
/* 3117 */       localTerm = nextLayer(localTerm);
/*      */     }
/* 3119 */     return new ParsedValue(arrayOfParsedValue, BorderImage.SliceSequenceConverter.getInstance());
/*      */   }
/*      */ 
/*      */   private ParsedValue<ParsedValue[], String> parseURI(Term paramTerm)
/*      */     throws CSSParser.ParseException
/*      */   {
/* 3130 */     String str1 = paramTerm.token != null ? paramTerm.token.getText() : null;
/* 3131 */     if (!"url".regionMatches(true, 0, str1, 0, 3)) {
/* 3132 */       error(paramTerm, "Expected 'url'");
/*      */     }
/*      */ 
/* 3135 */     Term localTerm = paramTerm.firstArg;
/* 3136 */     if (localTerm == null) error(paramTerm, "Expected 'url(\"<uri-string>\")'");
/*      */ 
/* 3138 */     if ((localTerm.token == null) || (localTerm.token.getType() != 10) || (localTerm.token.getText() == null) || (localTerm.token.getText().isEmpty()))
/*      */     {
/* 3141 */       error(localTerm, "Excpected 'url(\"<uri-string>\")'");
/*      */     }
/* 3143 */     String str2 = localTerm.token.getText();
/* 3144 */     ParsedValue[] arrayOfParsedValue = { new ParsedValue(str2, StringConverter.getInstance()), new ParsedValue(this.sourceOfStylesheet, null) };
/*      */ 
/* 3148 */     return new ParsedValue(arrayOfParsedValue, URLConverter.getInstance());
/*      */   }
/*      */ 
/*      */   private ParsedValue<ParsedValue<ParsedValue[], String>[], String[]> parseURILayers(Term paramTerm)
/*      */     throws CSSParser.ParseException
/*      */   {
/* 3156 */     int i = numberOfLayers(paramTerm);
/*      */ 
/* 3158 */     Term localTerm = paramTerm;
/* 3159 */     int j = 0;
/* 3160 */     ParsedValue[] arrayOfParsedValue = new ParsedValue[i];
/*      */ 
/* 3162 */     while (localTerm != null) {
/* 3163 */       arrayOfParsedValue[(j++)] = parseURI(localTerm);
/* 3164 */       localTerm = nextLayer(localTerm);
/*      */     }
/*      */ 
/* 3167 */     return new ParsedValue(arrayOfParsedValue, URLConverter.SequenceConverter.getInstance());
/*      */   }
/*      */ 
/*      */   private ParsedValue<ParsedValue<?, Size>, Double> parseFontSize(Term paramTerm)
/*      */     throws CSSParser.ParseException
/*      */   {
/* 3179 */     if (paramTerm == null) return null;
/* 3180 */     Token localToken = paramTerm.token;
/* 3181 */     if ((localToken == null) || (!isSize(localToken))) error(paramTerm, "Expected '<font-size>'");
/*      */ 
/* 3183 */     Size localSize = null;
/* 3184 */     if (localToken.getType() == 11) {
/* 3185 */       localObject = localToken.getText().toLowerCase();
/* 3186 */       double d = -1.0D;
/* 3187 */       if ("inherit".equals(localObject))
/* 3188 */         d = 100.0D;
/* 3189 */       else if ("xx-small".equals(localObject))
/* 3190 */         d = 60.0D;
/* 3191 */       else if ("x-small".equals(localObject))
/* 3192 */         d = 75.0D;
/* 3193 */       else if ("small".equals(localObject))
/* 3194 */         d = 80.0D;
/* 3195 */       else if ("medium".equals(localObject))
/* 3196 */         d = 100.0D;
/* 3197 */       else if ("large".equals(localObject))
/* 3198 */         d = 120.0D;
/* 3199 */       else if ("x-large".equals(localObject))
/* 3200 */         d = 150.0D;
/* 3201 */       else if ("xx-large".equals(localObject))
/* 3202 */         d = 200.0D;
/* 3203 */       else if ("smaller".equals(localObject))
/* 3204 */         d = 80.0D;
/* 3205 */       else if ("larger".equals(localObject)) {
/* 3206 */         d = 120.0D;
/*      */       }
/*      */ 
/* 3209 */       if (d > -1.0D) {
/* 3210 */         localSize = new Size(d, SizeUnits.PERCENT);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 3215 */     if (localSize == null) {
/* 3216 */       localSize = size(localToken);
/*      */     }
/*      */ 
/* 3219 */     Object localObject = new ParsedValue(localSize, null);
/* 3220 */     return new ParsedValue(localObject, FontConverter.SizeConverter.getInstance());
/*      */   }
/*      */ 
/*      */   private ParsedValue<FontUnits.Style, FontPosture> parseFontStyle(Term paramTerm)
/*      */     throws CSSParser.ParseException
/*      */   {
/* 3226 */     if (paramTerm == null) return null;
/* 3227 */     Token localToken = paramTerm.token;
/* 3228 */     if ((localToken == null) || (localToken.getType() != 11) || (localToken.getText() == null) || (localToken.getText().isEmpty()))
/*      */     {
/* 3231 */       error(paramTerm, "Expected '<font-style>'");
/*      */     }
/* 3233 */     String str = localToken.getText().toLowerCase();
/* 3234 */     FontUnits.Style localStyle = null;
/*      */ 
/* 3236 */     if ("normal".equals(str))
/* 3237 */       localStyle = FontUnits.Style.NORMAL;
/* 3238 */     else if ("italic".equals(str))
/* 3239 */       localStyle = FontUnits.Style.ITALIC;
/* 3240 */     else if ("oblique".equals(str))
/* 3241 */       localStyle = FontUnits.Style.OBLIQUE;
/* 3242 */     else if ("inherit".equals(str))
/* 3243 */       localStyle = FontUnits.Style.OBLIQUE;
/*      */     else {
/* 3245 */       return null;
/*      */     }
/*      */ 
/* 3248 */     return new ParsedValue(localStyle, FontConverter.StyleConverter.getInstance());
/*      */   }
/*      */ 
/*      */   private ParsedValue<FontUnits.Weight, FontWeight> parseFontWeight(Term paramTerm)
/*      */     throws CSSParser.ParseException
/*      */   {
/* 3254 */     if (paramTerm == null) return null;
/* 3255 */     Token localToken = paramTerm.token;
/* 3256 */     if ((localToken == null) || (localToken.getType() != 11) || (localToken.getText() == null) || (localToken.getText().isEmpty()))
/*      */     {
/* 3259 */       error(paramTerm, "Expected '<font-weight>'");
/*      */     }
/* 3261 */     String str = localToken.getText().toLowerCase();
/* 3262 */     FontUnits.Weight localWeight = null;
/*      */ 
/* 3264 */     if ("inherit".equals(str))
/* 3265 */       localWeight = FontUnits.Weight.INHERIT;
/* 3266 */     else if ("normal".equals(str))
/* 3267 */       localWeight = FontUnits.Weight.NORMAL;
/* 3268 */     else if ("bold".equals(str))
/* 3269 */       localWeight = FontUnits.Weight.BOLD;
/* 3270 */     else if ("bolder".equals(str))
/* 3271 */       localWeight = FontUnits.Weight.BOLDER;
/* 3272 */     else if ("lighter".equals(str))
/* 3273 */       localWeight = FontUnits.Weight.LIGHTER;
/* 3274 */     else if ("100".equals(str))
/* 3275 */       localWeight = FontUnits.Weight.SCALE_100;
/* 3276 */     else if ("200".equals(str))
/* 3277 */       localWeight = FontUnits.Weight.SCALE_200;
/* 3278 */     else if ("300".equals(str))
/* 3279 */       localWeight = FontUnits.Weight.SCALE_300;
/* 3280 */     else if ("400".equals(str))
/* 3281 */       localWeight = FontUnits.Weight.SCALE_400;
/* 3282 */     else if ("500".equals(str))
/* 3283 */       localWeight = FontUnits.Weight.SCALE_500;
/* 3284 */     else if ("600".equals(str))
/* 3285 */       localWeight = FontUnits.Weight.SCALE_600;
/* 3286 */     else if ("700".equals(str))
/* 3287 */       localWeight = FontUnits.Weight.SCALE_700;
/* 3288 */     else if ("800".equals(str))
/* 3289 */       localWeight = FontUnits.Weight.SCALE_800;
/*      */     else {
/* 3291 */       return null;
/*      */     }
/*      */ 
/* 3294 */     return new ParsedValue(localWeight, FontConverter.WeightConverter.getInstance());
/*      */   }
/*      */ 
/*      */   private ParsedValue<String, String> parseFontFamily(Term paramTerm) throws CSSParser.ParseException
/*      */   {
/* 3299 */     if (paramTerm == null) return null;
/* 3300 */     Token localToken = paramTerm.token;
/* 3301 */     String str1 = null;
/* 3302 */     if ((localToken == null) || ((localToken.getType() != 11) && (localToken.getType() != 10)) || ((str1 = localToken.getText()) == null) || (str1.isEmpty()))
/*      */     {
/* 3306 */       error(paramTerm, "Expected '<font-family>'");
/*      */     }
/* 3308 */     String str2 = stripQuotes(str1.toLowerCase());
/* 3309 */     if ("inherit".equals(str2))
/* 3310 */       return new ParsedValue("inherit", StringConverter.getInstance());
/* 3311 */     if (("serif".equals(str2)) || ("sans-serif".equals(str2)) || ("cursive".equals(str2)) || ("fantasy".equals(str2)) || ("monospace".equals(str2)))
/*      */     {
/* 3316 */       return new ParsedValue(str2, StringConverter.getInstance());
/*      */     }
/* 3318 */     return new ParsedValue(localToken.getText(), StringConverter.getInstance());
/*      */   }
/*      */ 
/*      */   private ParsedValue<ParsedValue[], Font> parseFont(Term paramTerm)
/*      */     throws CSSParser.ParseException
/*      */   {
/* 3328 */     Object localObject1 = paramTerm.nextInSeries;
/* 3329 */     paramTerm.nextInSeries = null;
/* 3330 */     while (localObject1 != null) {
/* 3331 */       localObject2 = ((Term)localObject1).nextInSeries;
/* 3332 */       ((Term)localObject1).nextInSeries = paramTerm;
/* 3333 */       paramTerm = (Term)localObject1;
/* 3334 */       localObject1 = localObject2;
/*      */     }
/*      */ 
/* 3338 */     Object localObject2 = paramTerm.token;
/* 3339 */     int i = ((Token)localObject2).getType();
/* 3340 */     if ((i != 11) && (i != 10))
/* 3341 */       error(paramTerm, "Expected '<font-family>'");
/* 3342 */     ParsedValue localParsedValue1 = parseFontFamily(paramTerm);
/*      */ 
/* 3344 */     Term localTerm1 = paramTerm;
/* 3345 */     if ((localTerm1 = localTerm1.nextInSeries) == null) error(paramTerm, "Expected '<size>'");
/* 3346 */     if ((localTerm1.token == null) || (!isSize(localTerm1.token))) error(localTerm1, "Expected '<size>'");
/*      */     Term localTerm2;
/* 3351 */     if (((localTerm2 = localTerm1.nextInSeries) != null) && (localTerm2.token != null) && (localTerm2.token.getType() == 32))
/*      */     {
/* 3354 */       paramTerm = localTerm2;
/*      */ 
/* 3356 */       if ((localTerm1 = localTerm2.nextInSeries) == null) error(paramTerm, "Expected '<size>'");
/* 3357 */       if ((localTerm1.token == null) || (!isSize(localTerm1.token))) error(localTerm1, "Expected '<size>'");
/*      */ 
/* 3359 */       localObject2 = localTerm1.token;
/*      */     }
/*      */ 
/* 3362 */     ParsedValue localParsedValue2 = parseFontSize(localTerm1);
/* 3363 */     if (localParsedValue2 == null) error(paramTerm, "Expected '<size>'");
/*      */ 
/* 3365 */     ParsedValue localParsedValue3 = null;
/* 3366 */     ParsedValue localParsedValue4 = null;
/* 3367 */     String str = null;
/*      */ 
/* 3369 */     while ((localTerm1 = localTerm1.nextInSeries) != null)
/*      */     {
/* 3371 */       if ((localTerm1.token == null) || (localTerm1.token.getType() != 11) || (localTerm1.token.getText() == null) || (localTerm1.token.getText().isEmpty()))
/*      */       {
/* 3375 */         error(localTerm1, "Expected '<font-weight>', '<font-style>' or '<font-variant>'");
/*      */       }
/* 3377 */       if ((localParsedValue3 != null) || ((localParsedValue3 = parseFontStyle(localTerm1)) == null))
/*      */       {
/* 3379 */         if ((str == null) && ("small-caps".equalsIgnoreCase(localTerm1.token.getText()))) {
/* 3380 */           str = localTerm1.token.getText();
/*      */         }
/* 3381 */         else if ((localParsedValue4 != null) || ((localParsedValue4 = parseFontWeight(localTerm1)) == null));
/*      */       }
/*      */     }
/*      */ 
/* 3386 */     ParsedValue[] arrayOfParsedValue = { localParsedValue1, localParsedValue2, localParsedValue4, localParsedValue3 };
/* 3387 */     return new ParsedValue(arrayOfParsedValue, FontConverter.getInstance());
/*      */   }
/*      */ 
/*      */   private Token nextToken(CSSLexer paramCSSLexer)
/*      */   {
/* 3398 */     Token localToken = null;
/*      */     do
/*      */     {
/* 3401 */       localToken = paramCSSLexer.nextToken();
/*      */     }
/* 3403 */     while (((localToken != null) && (localToken.getType() == 40)) || (localToken.getType() == 41));
/*      */ 
/* 3406 */     if (LOGGER.isLoggable(300)) {
/* 3407 */       LOGGER.finest(localToken.toString());
/*      */     }
/*      */ 
/* 3410 */     return localToken;
/*      */   }
/*      */ 
/*      */   private void parse(Stylesheet paramStylesheet, CSSLexer paramCSSLexer)
/*      */   {
/* 3417 */     this.currentToken = nextToken(paramCSSLexer);
/*      */ 
/* 3419 */     while ((this.currentToken != null) && (this.currentToken.getType() != -1))
/*      */     {
/* 3422 */       List localList1 = selectors(paramCSSLexer);
/* 3423 */       if (localList1 == null)
/*      */         return;
/*      */       int j;
/*      */       Object localObject;
/* 3425 */       if ((this.currentToken == null) || (this.currentToken.getType() != 28))
/*      */       {
/* 3427 */         int i = this.currentToken != null ? this.currentToken.getLine() : -1;
/* 3428 */         j = this.currentToken != null ? this.currentToken.getOffset() : -1;
/* 3429 */         String str = MessageFormat.format("Expected LBRACE at [{0,number,#},{1,number,#}]", new Object[] { Integer.valueOf(i), Integer.valueOf(j) });
/*      */ 
/* 3432 */         localObject = createError(str);
/* 3433 */         if (LOGGER.isLoggable(900)) {
/* 3434 */           LOGGER.warning(((CssError)localObject).toString());
/*      */         }
/* 3436 */         reportError((CssError)localObject);
/* 3437 */         this.currentToken = null;
/* 3438 */         return;
/*      */       }
/*      */ 
/* 3442 */       this.currentToken = nextToken(paramCSSLexer);
/*      */ 
/* 3444 */       List localList2 = declarations(paramCSSLexer);
/* 3445 */       if (localList2 == null) return;
/*      */ 
/* 3447 */       if ((this.currentToken != null) && (this.currentToken.getType() != 29))
/*      */       {
/* 3449 */         j = this.currentToken != null ? this.currentToken.getLine() : -1;
/* 3450 */         int k = this.currentToken != null ? this.currentToken.getOffset() : -1;
/* 3451 */         localObject = MessageFormat.format("Expected RBRACE at [{0,number,#},{1,number,#}]", new Object[] { Integer.valueOf(j), Integer.valueOf(k) });
/*      */ 
/* 3454 */         CssError localCssError = createError((String)localObject);
/* 3455 */         if (LOGGER.isLoggable(900)) {
/* 3456 */           LOGGER.warning(localCssError.toString());
/*      */         }
/* 3458 */         reportError(localCssError);
/* 3459 */         this.currentToken = null;
/* 3460 */         return;
/*      */       }
/*      */ 
/* 3463 */       paramStylesheet.getRules().add(new Rule(localList1, localList2));
/*      */ 
/* 3465 */       this.currentToken = nextToken(paramCSSLexer);
/*      */     }
/*      */ 
/* 3468 */     this.currentToken = null;
/*      */   }
/*      */ 
/*      */   private List<Selector> selectors(CSSLexer paramCSSLexer)
/*      */   {
/* 3473 */     ArrayList localArrayList = new ArrayList();
/*      */     while (true)
/*      */     {
/* 3476 */       Selector localSelector = selector(paramCSSLexer);
/* 3477 */       if (localSelector == null)
/*      */       {
/* 3480 */         while ((this.currentToken != null) && (this.currentToken.getType() != 29) && (this.currentToken.getType() != -1))
/*      */         {
/* 3482 */           this.currentToken = nextToken(paramCSSLexer);
/*      */         }
/*      */ 
/* 3487 */         this.currentToken = nextToken(paramCSSLexer);
/*      */ 
/* 3490 */         if ((this.currentToken == null) || (this.currentToken.getType() == -1)) {
/* 3491 */           this.currentToken = null;
/* 3492 */           return null;
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 3497 */         localArrayList.add(localSelector);
/*      */ 
/* 3499 */         if ((this.currentToken == null) || (this.currentToken.getType() != 36)) {
/*      */           break;
/*      */         }
/* 3502 */         this.currentToken = nextToken(paramCSSLexer);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 3511 */     return localArrayList;
/*      */   }
/*      */ 
/*      */   private Selector selector(CSSLexer paramCSSLexer)
/*      */   {
/* 3516 */     ArrayList localArrayList1 = null;
/* 3517 */     ArrayList localArrayList2 = null;
/*      */ 
/* 3519 */     SimpleSelector localSimpleSelector1 = simpleSelector(paramCSSLexer);
/* 3520 */     if (localSimpleSelector1 == null) return null;
/*      */     while (true)
/*      */     {
/* 3523 */       Combinator localCombinator = combinator(paramCSSLexer);
/* 3524 */       if (localCombinator == null) break;
/* 3525 */       if (localArrayList1 == null) {
/* 3526 */         localArrayList1 = new ArrayList();
/*      */       }
/* 3528 */       localArrayList1.add(localCombinator);
/* 3529 */       SimpleSelector localSimpleSelector2 = simpleSelector(paramCSSLexer);
/* 3530 */       if (localSimpleSelector2 == null) return null;
/* 3531 */       if (localArrayList2 == null) {
/* 3532 */         localArrayList2 = new ArrayList();
/* 3533 */         localArrayList2.add(localSimpleSelector1);
/*      */       }
/* 3535 */       localArrayList2.add(localSimpleSelector2);
/*      */     }
/*      */ 
/* 3544 */     if ((this.currentToken != null) && (this.currentToken.getType() == 41)) {
/* 3545 */       this.currentToken = nextToken(paramCSSLexer);
/*      */     }
/*      */ 
/* 3549 */     if (localArrayList2 == null) {
/* 3550 */       return localSimpleSelector1;
/*      */     }
/* 3552 */     return new CompoundSelector(localArrayList2, localArrayList1);
/*      */   }
/*      */ 
/*      */   private SimpleSelector simpleSelector(CSSLexer paramCSSLexer)
/*      */   {
/* 3559 */     String str1 = "*";
/* 3560 */     String str2 = "";
/* 3561 */     ArrayList localArrayList1 = null;
/* 3562 */     ArrayList localArrayList2 = null;
/*      */     while (true)
/*      */     {
/* 3566 */       int i = this.currentToken != null ? this.currentToken.getType() : 0;
/*      */ 
/* 3569 */       switch (i)
/*      */       {
/*      */       case 11:
/*      */       case 33:
/* 3573 */         str1 = this.currentToken.getText();
/* 3574 */         break;
/*      */       case 38:
/* 3578 */         this.currentToken = nextToken(paramCSSLexer);
/* 3579 */         if ((this.currentToken != null) && (this.currentToken.getType() == 11))
/*      */         {
/* 3581 */           if (localArrayList1 == null) {
/* 3582 */             localArrayList1 = new ArrayList();
/*      */           }
/* 3584 */           localArrayList1.add(this.currentToken.getText());
/*      */         } else {
/* 3586 */           this.currentToken = Token.INVALID_TOKEN;
/* 3587 */           return null;
/*      */         }
/*      */ 
/*      */         break;
/*      */       case 37:
/* 3593 */         str2 = this.currentToken.getText().substring(1);
/* 3594 */         break;
/*      */       case 31:
/* 3597 */         this.currentToken = nextToken(paramCSSLexer);
/* 3598 */         if ((this.currentToken != null) && (this.currentToken.getType() == 11))
/*      */         {
/* 3600 */           if (localArrayList2 == null) {
/* 3601 */             localArrayList2 = new ArrayList();
/*      */           }
/* 3603 */           localArrayList2.add(this.currentToken.getText());
/*      */         } else {
/* 3605 */           this.currentToken = Token.INVALID_TOKEN;
/* 3606 */           return null;
/*      */         }
/*      */ 
/*      */         break;
/*      */       case -1:
/*      */       case 27:
/*      */       case 28:
/*      */       case 36:
/*      */       case 40:
/*      */       case 41:
/* 3616 */         return new SimpleSelector(str1, localArrayList1, localArrayList2, str2);
/*      */       case 0:
/*      */       case 1:
/*      */       case 2:
/*      */       case 3:
/*      */       case 4:
/*      */       case 5:
/*      */       case 6:
/*      */       case 7:
/*      */       case 8:
/*      */       case 9:
/*      */       case 10:
/*      */       case 12:
/*      */       case 13:
/*      */       case 14:
/*      */       case 15:
/*      */       case 16:
/*      */       case 17:
/*      */       case 18:
/*      */       case 19:
/*      */       case 20:
/*      */       case 21:
/*      */       case 22:
/*      */       case 23:
/*      */       case 24:
/*      */       case 25:
/*      */       case 26:
/*      */       case 29:
/*      */       case 30:
/*      */       case 32:
/*      */       case 34:
/*      */       case 35:
/*      */       case 39:
/*      */       default:
/* 3619 */         return null;
/*      */       }
/*      */ 
/* 3626 */       this.currentToken = paramCSSLexer.nextToken();
/* 3627 */       if (LOGGER.isLoggable(300))
/* 3628 */         LOGGER.finest(this.currentToken.toString());
/*      */     }
/*      */   }
/*      */ 
/*      */   private Combinator combinator(CSSLexer paramCSSLexer)
/*      */   {
/* 3635 */     Combinator localCombinator = null;
/*      */     while (true)
/*      */     {
/* 3639 */       int i = this.currentToken != null ? this.currentToken.getType() : 0;
/*      */ 
/* 3642 */       switch (i)
/*      */       {
/*      */       case 40:
/* 3647 */         if ((localCombinator == null) && (" ".equals(this.currentToken.getText())))
/* 3648 */           localCombinator = Combinator.DESCENDANT; break;
/*      */       case 27:
/* 3654 */         localCombinator = Combinator.CHILD;
/* 3655 */         break;
/*      */       case 11:
/*      */       case 31:
/*      */       case 33:
/*      */       case 37:
/*      */       case 38:
/* 3662 */         return localCombinator;
/*      */       default:
/* 3666 */         return null;
/*      */       }
/*      */ 
/* 3671 */       this.currentToken = paramCSSLexer.nextToken();
/* 3672 */       if (LOGGER.isLoggable(300))
/* 3673 */         LOGGER.finest(this.currentToken.toString());
/*      */     }
/*      */   }
/*      */ 
/*      */   private List<Declaration> declarations(CSSLexer paramCSSLexer)
/*      */   {
/* 3680 */     ArrayList localArrayList = new ArrayList();
/*      */     do
/*      */     {
/* 3684 */       Declaration localDeclaration = declaration(paramCSSLexer);
/* 3685 */       if (localDeclaration != null) {
/* 3686 */         localArrayList.add(localDeclaration);
/*      */       }
/*      */       else
/*      */       {
/* 3691 */         while ((this.currentToken != null) && (this.currentToken.getType() != 30) && (this.currentToken.getType() != 29) && (this.currentToken.getType() != -1))
/*      */         {
/* 3693 */           this.currentToken = nextToken(paramCSSLexer);
/*      */         }
/*      */ 
/* 3697 */         if ((this.currentToken == null) && (this.currentToken.getType() != 30))
/*      */         {
/* 3699 */           return localArrayList;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 3704 */       while ((this.currentToken != null) && (this.currentToken.getType() == 30))
/*      */       {
/* 3706 */         this.currentToken = nextToken(paramCSSLexer);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 3711 */     while ((this.currentToken != null) && (this.currentToken.getType() == 11));
/*      */ 
/* 3719 */     return localArrayList;
/*      */   }
/*      */ 
/*      */   private Declaration declaration(CSSLexer paramCSSLexer)
/*      */   {
/* 3724 */     int i = this.currentToken != null ? this.currentToken.getType() : 0;
/*      */ 
/* 3727 */     if ((this.currentToken == null) || (this.currentToken.getType() != 11))
/*      */     {
/* 3742 */       return null;
/*      */     }
/*      */ 
/* 3745 */     String str1 = this.currentToken.getText();
/*      */ 
/* 3747 */     this.currentToken = nextToken(paramCSSLexer);
/*      */ 
/* 3749 */     if ((this.currentToken == null) || (this.currentToken.getType() != 31))
/*      */     {
/* 3751 */       int j = this.currentToken != null ? this.currentToken.getLine() : -1;
/* 3752 */       int k = this.currentToken != null ? this.currentToken.getOffset() : -1;
/* 3753 */       String str2 = MessageFormat.format("Expected COLON at [{0,number,#},{1,number,#}]", new Object[] { Integer.valueOf(j), Integer.valueOf(k) });
/*      */ 
/* 3756 */       localObject = createError(str2);
/* 3757 */       if (LOGGER.isLoggable(900)) {
/* 3758 */         LOGGER.warning(((CssError)localObject).toString());
/*      */       }
/* 3760 */       reportError((CssError)localObject);
/* 3761 */       return null;
/*      */     }
/*      */ 
/* 3764 */     this.currentToken = nextToken(paramCSSLexer);
/*      */ 
/* 3766 */     Term localTerm = expr(paramCSSLexer);
/* 3767 */     ParsedValue localParsedValue = null;
/*      */     try {
/* 3769 */       localParsedValue = localTerm != null ? valueFor(str1, localTerm) : null;
/*      */     } catch (ParseException localParseException) {
/* 3771 */       localObject = localParseException.tok;
/* 3772 */       int m = localObject != null ? ((Token)localObject).getLine() : -1;
/* 3773 */       int n = localObject != null ? ((Token)localObject).getOffset() : -1;
/* 3774 */       String str3 = MessageFormat.format("{2} while parsing ''{3}'' at [{0,number,#},{1,number,#}]", new Object[] { Integer.valueOf(m), Integer.valueOf(n), localParseException.getMessage(), str1 });
/*      */ 
/* 3777 */       CssError localCssError = createError(str3);
/* 3778 */       if (LOGGER.isLoggable(900)) {
/* 3779 */         LOGGER.warning(localCssError.toString());
/*      */       }
/* 3781 */       reportError(localCssError);
/* 3782 */       return null;
/*      */     }
/*      */ 
/* 3785 */     boolean bool = this.currentToken.getType() == 39;
/* 3786 */     if (bool) this.currentToken = nextToken(paramCSSLexer);
/*      */ 
/* 3788 */     Object localObject = localParsedValue != null ? new Declaration(str1, localParsedValue, bool) : null;
/*      */ 
/* 3790 */     return localObject;
/*      */   }
/*      */ 
/*      */   private Term expr(CSSLexer paramCSSLexer)
/*      */   {
/* 3795 */     Term localTerm1 = term(paramCSSLexer);
/* 3796 */     Term localTerm2 = localTerm1;
/*      */     while (true)
/*      */     {
/* 3800 */       int i = this.currentToken != null ? this.currentToken.getType() : 0;
/*      */ 
/* 3803 */       if (i == 0) {
/* 3804 */         skipExpr(paramCSSLexer);
/* 3805 */         return null;
/* 3806 */       }if ((i == 30) || (i == 39) || (i == 29) || (i == -1))
/*      */       {
/* 3810 */         return localTerm1;
/* 3811 */       }if (i == 36)
/*      */       {
/* 3815 */         this.currentToken = nextToken(paramCSSLexer);
/* 3816 */         localTerm2 = localTerm2.nextLayer = term(paramCSSLexer);
/*      */       } else {
/* 3818 */         localTerm2 = localTerm2.nextInSeries = term(paramCSSLexer);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void skipExpr(CSSLexer paramCSSLexer)
/*      */   {
/*      */     while (true)
/*      */     {
/* 3828 */       this.currentToken = nextToken(paramCSSLexer);
/*      */ 
/* 3830 */       int i = this.currentToken != null ? this.currentToken.getType() : 0;
/*      */ 
/* 3833 */       if ((i == 30) || (i == 29) || (i == -1))
/*      */       {
/* 3836 */         return;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private Term term(CSSLexer paramCSSLexer)
/*      */   {
/* 3843 */     int i = this.currentToken != null ? this.currentToken.getType() : 0;
/*      */     int j;
/* 3846 */     switch (i)
/*      */     {
/*      */     case 13:
/*      */     case 14:
/*      */     case 15:
/*      */     case 16:
/*      */     case 17:
/*      */     case 18:
/*      */     case 19:
/*      */     case 20:
/*      */     case 21:
/*      */     case 22:
/*      */     case 23:
/*      */     case 24:
/*      */     case 25:
/*      */     case 26:
/* 3862 */       break;
/*      */     case 10:
/* 3865 */       break;
/*      */     case 11:
/* 3867 */       break;
/*      */     case 37:
/* 3870 */       break;
/*      */     case 12:
/*      */     case 34:
/* 3875 */       localTerm1 = new Term(this.currentToken);
/* 3876 */       this.currentToken = nextToken(paramCSSLexer);
/*      */ 
/* 3878 */       Term localTerm2 = term(paramCSSLexer);
/* 3879 */       localTerm1.firstArg = localTerm2;
/*      */       while (true)
/*      */       {
/* 3883 */         j = this.currentToken != null ? this.currentToken.getType() : 0;
/*      */ 
/* 3886 */         if (j == 35) {
/* 3887 */           this.currentToken = nextToken(paramCSSLexer);
/* 3888 */           return localTerm1;
/* 3889 */         }if (j == 36)
/*      */         {
/* 3893 */           this.currentToken = nextToken(paramCSSLexer);
/* 3894 */           localTerm2 = localTerm2.nextArg = term(paramCSSLexer);
/*      */         }
/*      */         else {
/* 3897 */           localTerm2 = localTerm2.nextInSeries = term(paramCSSLexer); }  } case 27:
/*      */     case 28:
/*      */     case 29:
/*      */     case 30:
/*      */     case 31:
/*      */     case 32:
/*      */     case 33:
/*      */     case 35:
/*      */     case 36:
/*      */     default:
/* 3903 */       j = this.currentToken != null ? this.currentToken.getLine() : -1;
/* 3904 */       int k = this.currentToken != null ? this.currentToken.getOffset() : -1;
/* 3905 */       String str1 = this.currentToken != null ? this.currentToken.getText() : "";
/* 3906 */       String str2 = MessageFormat.format("Unexpected token {0}{1}{0} at [{2,number,#},{3,number,#}]", new Object[] { "'", str1, Integer.valueOf(j), Integer.valueOf(k) });
/*      */ 
/* 3909 */       CssError localCssError = createError(str2);
/* 3910 */       if (LOGGER.isLoggable(900)) {
/* 3911 */         LOGGER.warning(localCssError.toString());
/*      */       }
/* 3913 */       reportError(localCssError);
/* 3914 */       return null;
/*      */     }
/*      */ 
/* 3920 */     Term localTerm1 = new Term(this.currentToken);
/* 3921 */     this.currentToken = nextToken(paramCSSLexer);
/* 3922 */     return localTerm1;
/*      */   }
/*      */ 
/*      */   static
/*      */   {
/*  135 */     int i = LOGGER.getLevel();
/*  136 */     if ((LOGGER.getLevel() > 900) && (LOGGER.getLevel() != 2147483647))
/*      */     {
/*  138 */       LOGGER.setLevel(900);
/*      */     }
/*      */   }
/*      */ 
/*      */   static class Term
/*      */   {
/*      */     final Token token;
/*      */     Term nextInSeries;
/*      */     Term nextLayer;
/*      */     Term firstArg;
/*      */     Term nextArg;
/*      */ 
/*      */     Term(Token paramToken)
/*      */     {
/*  366 */       this.token = paramToken;
/*  367 */       this.nextLayer = null;
/*  368 */       this.nextInSeries = null;
/*  369 */       this.firstArg = null;
/*  370 */       this.nextArg = null;
/*      */     }
/*      */     Term() {
/*  373 */       this(null);
/*      */     }
/*      */ 
/*      */     public String toString() {
/*  377 */       StringBuilder localStringBuilder = new StringBuilder();
/*  378 */       if (this.token != null) localStringBuilder.append(String.valueOf(this.token.getText()));
/*  379 */       if (this.nextInSeries != null) {
/*  380 */         localStringBuilder.append("<nextInSeries>");
/*  381 */         localStringBuilder.append(this.nextInSeries.toString());
/*  382 */         localStringBuilder.append("</nextInSeries>\n");
/*      */       }
/*  384 */       if (this.nextLayer != null) {
/*  385 */         localStringBuilder.append("<nextLayer>");
/*  386 */         localStringBuilder.append(this.nextLayer.toString());
/*  387 */         localStringBuilder.append("</nextLayer>\n");
/*      */       }
/*  389 */       if (this.firstArg != null) {
/*  390 */         localStringBuilder.append("<args>");
/*  391 */         localStringBuilder.append(this.firstArg.toString());
/*  392 */         if (this.nextArg != null) {
/*  393 */           localStringBuilder.append(this.nextArg.toString());
/*      */         }
/*  395 */         localStringBuilder.append("</args>");
/*      */       }
/*      */ 
/*  398 */       return localStringBuilder.toString();
/*      */     }
/*      */   }
/*      */ 
/*      */   private static final class ParseException extends Exception
/*      */   {
/*      */     private final Token tok;
/*      */     private final String source;
/*      */ 
/*      */     ParseException(String paramString)
/*      */     {
/*  144 */       this(paramString, null, null);
/*      */     }
/*      */     ParseException(String paramString, Token paramToken, CSSParser paramCSSParser) {
/*  147 */       super();
/*  148 */       this.tok = paramToken;
/*  149 */       if (paramCSSParser.sourceOfStylesheet != null)
/*  150 */         this.source = paramCSSParser.sourceOfStylesheet.toExternalForm();
/*  151 */       else if (paramCSSParser.sourceOfInlineStyle != null)
/*  152 */         this.source = paramCSSParser.sourceOfInlineStyle.toString();
/*  153 */       else if (paramCSSParser.stylesheetAsText != null)
/*  154 */         this.source = paramCSSParser.stylesheetAsText;
/*      */       else
/*  156 */         this.source = "?";
/*      */     }
/*      */ 
/*      */     public String toString() {
/*  160 */       StringBuilder localStringBuilder = new StringBuilder(super.getMessage());
/*  161 */       localStringBuilder.append(this.source);
/*  162 */       if (this.tok != null) localStringBuilder.append(": ").append(this.tok.toString());
/*  163 */       return localStringBuilder.toString();
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class InstanceHolder
/*      */   {
/*   81 */     static final CSSParser INSTANCE = new CSSParser(null);
/*      */   }
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.css.parser.CSSParser
 * JD-Core Version:    0.6.2
 */