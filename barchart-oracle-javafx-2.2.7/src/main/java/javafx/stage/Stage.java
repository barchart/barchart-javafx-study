/*      */ package javafx.stage;
/*      */ 
/*      */ import com.sun.javafx.collections.TrackableObservableList;
/*      */ import com.sun.javafx.robot.impl.FXRobotHelper;
/*      */ import com.sun.javafx.robot.impl.FXRobotHelper.FXRobotStageAccessor;
/*      */ import com.sun.javafx.stage.StageHelper;
/*      */ import com.sun.javafx.stage.StageHelper.StageAccessor;
/*      */ import com.sun.javafx.stage.StagePeerListener;
/*      */ import com.sun.javafx.stage.StagePeerListener.StageAccessor;
/*      */ import com.sun.javafx.tk.TKStage;
/*      */ import com.sun.javafx.tk.Toolkit;
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
/*      */ import javafx.beans.property.BooleanProperty;
/*      */ import javafx.beans.property.DoubleProperty;
/*      */ import javafx.beans.property.DoublePropertyBase;
/*      */ import javafx.beans.property.ReadOnlyBooleanProperty;
/*      */ import javafx.beans.property.ReadOnlyBooleanWrapper;
/*      */ import javafx.beans.property.SimpleBooleanProperty;
/*      */ import javafx.beans.property.StringProperty;
/*      */ import javafx.beans.property.StringPropertyBase;
/*      */ import javafx.collections.FXCollections;
/*      */ import javafx.collections.ListChangeListener.Change;
/*      */ import javafx.collections.ObservableList;
/*      */ import javafx.scene.Scene;
/*      */ import javafx.scene.image.Image;
/*      */ 
/*      */ public class Stage extends Window
/*      */ {
/*  156 */   private boolean inNestedEventLoop = false;
/*      */   private static ObservableList<Stage> stages;
/*  173 */   private static final StagePeerListener.StageAccessor STAGE_ACCESSOR = new StagePeerListener.StageAccessor()
/*      */   {
/*      */     public void setIconified(Stage paramAnonymousStage, boolean paramAnonymousBoolean)
/*      */     {
/*  177 */       paramAnonymousStage.iconifiedPropertyImpl().set(paramAnonymousBoolean);
/*      */     }
/*      */ 
/*      */     public void setResizable(Stage paramAnonymousStage, boolean paramAnonymousBoolean)
/*      */     {
/*  182 */       paramAnonymousStage.resizableProperty().set(paramAnonymousBoolean);
/*      */     }
/*      */ 
/*      */     public void setFullScreen(Stage paramAnonymousStage, boolean paramAnonymousBoolean)
/*      */     {
/*  187 */       paramAnonymousStage.fullScreenPropertyImpl().set(paramAnonymousBoolean);
/*      */     }
/*  173 */   };
/*      */ 
/*  232 */   private boolean primary = false;
/*      */ 
/*  265 */   private boolean important = true;
/*      */   private StageStyle style;
/*  430 */   private Modality modality = Modality.NONE;
/*      */ 
/*  467 */   private Window owner = null;
/*      */   private ReadOnlyBooleanWrapper fullScreen;
/*  614 */   private ObservableList<Image> icons = new TrackableObservableList() {
/*      */     protected void onChanged(ListChangeListener.Change<Image> paramAnonymousChange) {
/*  616 */       ArrayList localArrayList = new ArrayList();
/*  617 */       for (Image localImage : Stage.this.icons) {
/*  618 */         localArrayList.add(localImage.impl_getPlatformImage());
/*      */       }
/*  620 */       if (Stage.this.impl_peer != null)
/*  621 */         Stage.this.impl_peer.setIcons(localArrayList);
/*      */     }
/*  614 */   };
/*      */   private StringProperty title;
/*      */   private ReadOnlyBooleanWrapper iconified;
/*      */   private BooleanProperty resizable;
/*      */   private DoubleProperty minWidth;
/*      */   private DoubleProperty minHeight;
/*      */   private DoubleProperty maxWidth;
/*      */   private DoubleProperty maxHeight;
/*      */ 
/*      */   public Stage()
/*      */   {
/*  198 */     this(StageStyle.DECORATED);
/*      */   }
/*      */ 
/*      */   public Stage(StageStyle paramStageStyle)
/*      */   {
/*  212 */     Toolkit.getToolkit().checkFxUserThread();
/*      */ 
/*  215 */     initStyle(paramStageStyle);
/*      */   }
/*      */ 
/*      */   public final void setScene(Scene paramScene)
/*      */   {
/*  222 */     super.setScene(paramScene);
/*      */   }
/*      */ 
/*      */   public final void show()
/*      */   {
/*  229 */     super.show();
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public void impl_setPrimary(boolean paramBoolean)
/*      */   {
/*  242 */     this.primary = paramBoolean;
/*      */   }
/*      */ 
/*      */   boolean isPrimary()
/*      */   {
/*  252 */     return this.primary;
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public String impl_getMXWindowType()
/*      */   {
/*  262 */     return this.primary ? "PrimaryStage" : getClass().getSimpleName();
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public void impl_setImportant(boolean paramBoolean)
/*      */   {
/*  277 */     this.important = paramBoolean;
/*      */   }
/*      */ 
/*      */   private boolean isImportant() {
/*  281 */     return this.important;
/*      */   }
/*      */ 
/*      */   public void showAndWait()
/*      */   {
/*  380 */     Toolkit.getToolkit().checkFxUserThread();
/*      */ 
/*  382 */     if (isPrimary()) {
/*  383 */       throw new IllegalStateException("Cannot call this method on primary stage");
/*      */     }
/*      */ 
/*  386 */     if (isShowing()) {
/*  387 */       throw new IllegalStateException("Stage already visible");
/*      */     }
/*      */ 
/*  393 */     assert (!this.inNestedEventLoop);
/*      */ 
/*  395 */     show();
/*  396 */     this.inNestedEventLoop = true;
/*  397 */     Toolkit.getToolkit().enterNestedEventLoop(this);
/*      */   }
/*      */ 
/*      */   public final void initStyle(StageStyle paramStageStyle)
/*      */   {
/*  415 */     if (this.hasBeenVisible) {
/*  416 */       throw new IllegalStateException("Cannot set style once stage has been set visible");
/*      */     }
/*  418 */     this.style = paramStageStyle;
/*      */   }
/*      */ 
/*      */   public final StageStyle getStyle()
/*      */   {
/*  427 */     return this.style;
/*      */   }
/*      */ 
/*      */   public final void initModality(Modality paramModality)
/*      */   {
/*  447 */     if (this.hasBeenVisible) {
/*  448 */       throw new IllegalStateException("Cannot set modality once stage has been set visible");
/*      */     }
/*      */ 
/*  451 */     if (isPrimary()) {
/*  452 */       throw new IllegalStateException("Cannot set modality for the primary stage");
/*      */     }
/*      */ 
/*  455 */     this.modality = paramModality;
/*      */   }
/*      */ 
/*      */   public final Modality getModality()
/*      */   {
/*  464 */     return this.modality;
/*      */   }
/*      */ 
/*      */   public final void initOwner(Window paramWindow)
/*      */   {
/*  483 */     if (this.hasBeenVisible) {
/*  484 */       throw new IllegalStateException("Cannot set owner once stage has been set visible");
/*      */     }
/*      */ 
/*  487 */     if (isPrimary()) {
/*  488 */       throw new IllegalStateException("Cannot set owner for the primary stage");
/*      */     }
/*      */ 
/*  491 */     this.owner = paramWindow;
/*      */   }
/*      */ 
/*      */   public final Window getOwner()
/*      */   {
/*  501 */     return this.owner;
/*      */   }
/*      */ 
/*      */   public final void setFullScreen(boolean paramBoolean)
/*      */   {
/*  586 */     Toolkit.getToolkit().checkFxUserThread();
/*  587 */     fullScreenPropertyImpl().set(paramBoolean);
/*  588 */     if (this.impl_peer != null)
/*  589 */       this.impl_peer.setFullScreen(paramBoolean);
/*      */   }
/*      */ 
/*      */   public final boolean isFullScreen() {
/*  593 */     return this.fullScreen == null ? false : this.fullScreen.get();
/*      */   }
/*      */ 
/*      */   public final ReadOnlyBooleanProperty fullScreenProperty() {
/*  597 */     return fullScreenPropertyImpl().getReadOnlyProperty();
/*      */   }
/*      */ 
/*      */   private ReadOnlyBooleanWrapper fullScreenPropertyImpl() {
/*  601 */     if (this.fullScreen == null) {
/*  602 */       this.fullScreen = new ReadOnlyBooleanWrapper(this, "fullScreen");
/*      */     }
/*  604 */     return this.fullScreen;
/*      */   }
/*      */ 
/*      */   public final ObservableList<Image> getIcons()
/*      */   {
/*  633 */     return this.icons;
/*      */   }
/*      */ 
/*      */   public final void setTitle(String paramString)
/*      */   {
/*  644 */     titleProperty().set(paramString);
/*      */   }
/*      */ 
/*      */   public final String getTitle() {
/*  648 */     return this.title == null ? null : (String)this.title.get();
/*      */   }
/*      */ 
/*      */   public final StringProperty titleProperty() {
/*  652 */     if (this.title == null) {
/*  653 */       this.title = new StringPropertyBase()
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/*  657 */           if (Stage.this.impl_peer != null)
/*  658 */             Stage.this.impl_peer.setTitle(get());
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/*  664 */           return Stage.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/*  669 */           return "title";
/*      */         }
/*      */       };
/*      */     }
/*  673 */     return this.title;
/*      */   }
/*      */ 
/*      */   public final void setIconified(boolean paramBoolean)
/*      */   {
/*  688 */     iconifiedPropertyImpl().set(paramBoolean);
/*  689 */     if (this.impl_peer != null)
/*  690 */       this.impl_peer.setIconified(paramBoolean);
/*      */   }
/*      */ 
/*      */   public final boolean isIconified() {
/*  694 */     return this.iconified == null ? false : this.iconified.get();
/*      */   }
/*      */ 
/*      */   public final ReadOnlyBooleanProperty iconifiedProperty() {
/*  698 */     return iconifiedPropertyImpl().getReadOnlyProperty();
/*      */   }
/*      */ 
/*      */   private final ReadOnlyBooleanWrapper iconifiedPropertyImpl() {
/*  702 */     if (this.iconified == null) {
/*  703 */       this.iconified = new ReadOnlyBooleanWrapper(this, "iconified");
/*      */     }
/*  705 */     return this.iconified;
/*      */   }
/*      */ 
/*      */   public final void setResizable(boolean paramBoolean)
/*      */   {
/*  719 */     resizableProperty().set(paramBoolean);
/*  720 */     if (this.impl_peer != null) {
/*  721 */       applyBounds();
/*  722 */       this.impl_peer.setResizable(paramBoolean);
/*      */     }
/*      */   }
/*      */ 
/*      */   public final boolean isResizable() {
/*  727 */     return this.resizable == null ? true : this.resizable.get();
/*      */   }
/*      */ 
/*      */   public final BooleanProperty resizableProperty() {
/*  731 */     if (this.resizable == null) {
/*  732 */       this.resizable = new SimpleBooleanProperty(this, "resizable", true);
/*      */     }
/*  734 */     return this.resizable;
/*      */   }
/*      */ 
/*      */   public final void setMinWidth(double paramDouble)
/*      */   {
/*  745 */     minWidthProperty().set(paramDouble);
/*      */   }
/*      */ 
/*      */   public final double getMinWidth() {
/*  749 */     return this.minWidth == null ? 0.0D : this.minWidth.get();
/*      */   }
/*      */ 
/*      */   public final DoubleProperty minWidthProperty() {
/*  753 */     if (this.minWidth == null) {
/*  754 */       this.minWidth = new DoublePropertyBase(0.0D)
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/*  758 */           if (Stage.this.impl_peer != null) {
/*  759 */             Stage.this.impl_peer.setMinimumSize((int)Math.ceil(get()), (int)Math.ceil(Stage.this.getMinHeight()));
/*      */           }
/*      */ 
/*  762 */           if (Stage.this.getWidth() < Stage.this.getMinWidth())
/*  763 */             Stage.this.setWidth(Stage.this.getMinWidth());
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/*  769 */           return Stage.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/*  774 */           return "minWidth";
/*      */         }
/*      */       };
/*      */     }
/*  778 */     return this.minWidth;
/*      */   }
/*      */ 
/*      */   public final void setMinHeight(double paramDouble)
/*      */   {
/*  789 */     minHeightProperty().set(paramDouble);
/*      */   }
/*      */ 
/*      */   public final double getMinHeight() {
/*  793 */     return this.minHeight == null ? 0.0D : this.minHeight.get();
/*      */   }
/*      */ 
/*      */   public final DoubleProperty minHeightProperty() {
/*  797 */     if (this.minHeight == null) {
/*  798 */       this.minHeight = new DoublePropertyBase(0.0D)
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/*  802 */           if (Stage.this.impl_peer != null) {
/*  803 */             Stage.this.impl_peer.setMinimumSize((int)Math.ceil(Stage.this.getMinWidth()), (int)Math.ceil(get()));
/*      */           }
/*      */ 
/*  807 */           if (Stage.this.getHeight() < Stage.this.getMinHeight())
/*  808 */             Stage.this.setHeight(Stage.this.getMinHeight());
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/*  814 */           return Stage.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/*  819 */           return "minHeight";
/*      */         }
/*      */       };
/*      */     }
/*  823 */     return this.minHeight;
/*      */   }
/*      */ 
/*      */   public final void setMaxWidth(double paramDouble)
/*      */   {
/*  834 */     maxWidthProperty().set(paramDouble);
/*      */   }
/*      */ 
/*      */   public final double getMaxWidth() {
/*  838 */     return this.maxWidth == null ? 1.7976931348623157E+308D : this.maxWidth.get();
/*      */   }
/*      */ 
/*      */   public final DoubleProperty maxWidthProperty() {
/*  842 */     if (this.maxWidth == null) {
/*  843 */       this.maxWidth = new DoublePropertyBase(1.7976931348623157E+308D)
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/*  847 */           if (Stage.this.impl_peer != null) {
/*  848 */             Stage.this.impl_peer.setMaximumSize((int)Math.floor(get()), (int)Math.floor(Stage.this.getMaxHeight()));
/*      */           }
/*      */ 
/*  851 */           if (Stage.this.getWidth() > Stage.this.getMaxWidth())
/*  852 */             Stage.this.setWidth(Stage.this.getMaxWidth());
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/*  858 */           return Stage.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/*  863 */           return "maxWidth";
/*      */         }
/*      */       };
/*      */     }
/*  867 */     return this.maxWidth;
/*      */   }
/*      */ 
/*      */   public final void setMaxHeight(double paramDouble)
/*      */   {
/*  878 */     maxHeightProperty().set(paramDouble);
/*      */   }
/*      */ 
/*      */   public final double getMaxHeight() {
/*  882 */     return this.maxHeight == null ? 1.7976931348623157E+308D : this.maxHeight.get();
/*      */   }
/*      */ 
/*      */   public final DoubleProperty maxHeightProperty() {
/*  886 */     if (this.maxHeight == null) {
/*  887 */       this.maxHeight = new DoublePropertyBase(1.7976931348623157E+308D)
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/*  891 */           if (Stage.this.impl_peer != null) {
/*  892 */             Stage.this.impl_peer.setMaximumSize((int)Math.floor(Stage.this.getMaxWidth()), (int)Math.floor(get()));
/*      */           }
/*      */ 
/*  896 */           if (Stage.this.getHeight() > Stage.this.getMaxHeight())
/*  897 */             Stage.this.setHeight(Stage.this.getMaxHeight());
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/*  903 */           return Stage.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/*  908 */           return "maxHeight";
/*      */         }
/*      */       };
/*      */     }
/*  912 */     return this.maxHeight;
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   protected void impl_visibleChanging(boolean paramBoolean)
/*      */   {
/*  922 */     super.impl_visibleChanging(paramBoolean);
/*  923 */     Toolkit localToolkit = Toolkit.getToolkit();
/*  924 */     if ((paramBoolean) && (this.impl_peer == null))
/*      */     {
/*  926 */       Window localWindow = getOwner();
/*  927 */       TKStage localTKStage = localWindow == null ? null : localWindow.impl_getPeer();
/*  928 */       this.impl_peer = localToolkit.createTKStage(getStyle(), isPrimary(), getModality(), localTKStage);
/*      */ 
/*  930 */       this.peerListener = new StagePeerListener(this, STAGE_ACCESSOR);
/*      */ 
/*  933 */       stages.add(this);
/*      */     }
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   protected void impl_visibleChanged(boolean paramBoolean)
/*      */   {
/*  944 */     super.impl_visibleChanged(paramBoolean);
/*      */ 
/*  946 */     if (paramBoolean)
/*      */     {
/*  948 */       this.impl_peer.setImportant(isImportant());
/*  949 */       this.impl_peer.setResizable(isResizable());
/*  950 */       this.impl_peer.setFullScreen(isFullScreen());
/*  951 */       this.impl_peer.setIconified(isIconified());
/*  952 */       this.impl_peer.setTitle(getTitle());
/*  953 */       this.impl_peer.setMinimumSize((int)Math.ceil(getMinWidth()), (int)Math.ceil(getMinHeight()));
/*      */ 
/*  955 */       this.impl_peer.setMaximumSize((int)Math.floor(getMaxWidth()), (int)Math.floor(getMaxHeight()));
/*      */ 
/*  958 */       ArrayList localArrayList = new ArrayList();
/*  959 */       for (Image localImage : this.icons) {
/*  960 */         localArrayList.add(localImage.impl_getPlatformImage());
/*      */       }
/*  962 */       if (this.impl_peer != null) {
/*  963 */         this.impl_peer.setIcons(localArrayList);
/*      */       }
/*      */     }
/*      */ 
/*  967 */     if ((!paramBoolean) && (this.impl_peer != null))
/*      */     {
/*  969 */       stages.remove(this);
/*  970 */       this.peerListener = null;
/*  971 */       this.impl_peer = null;
/*      */     }
/*      */ 
/*  974 */     if ((!paramBoolean) && (this.inNestedEventLoop)) {
/*  975 */       this.inNestedEventLoop = false;
/*  976 */       Toolkit.getToolkit().exitNestedEventLoop(this, null);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void toFront()
/*      */   {
/*  985 */     if (this.impl_peer != null)
/*  986 */       this.impl_peer.toFront();
/*      */   }
/*      */ 
/*      */   public void toBack()
/*      */   {
/*  997 */     if (this.impl_peer != null)
/*  998 */       this.impl_peer.toBack();
/*      */   }
/*      */ 
/*      */   public void close()
/*      */   {
/* 1007 */     hide();
/*      */   }
/*      */ 
/*      */   static
/*      */   {
/*  158 */     stages = FXCollections.observableArrayList();
/*      */ 
/*  161 */     FXRobotHelper.setStageAccessor(new FXRobotHelper.FXRobotStageAccessor() {
/*      */       public ObservableList<Stage> getStages() {
/*  163 */         return Stage.stages;
/*      */       }
/*      */     });
/*  166 */     StageHelper.setStageAccessor(new StageHelper.StageAccessor() {
/*      */       public ObservableList<Stage> getStages() {
/*  168 */         return Stage.stages;
/*      */       }
/*      */     });
/*      */   }
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.stage.Stage
 * JD-Core Version:    0.6.2
 */