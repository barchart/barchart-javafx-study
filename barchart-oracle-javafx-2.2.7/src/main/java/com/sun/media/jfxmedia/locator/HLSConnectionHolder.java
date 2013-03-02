/*     */ package com.sun.media.jfxmedia.locator;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.nio.channels.Channels;
/*     */ import java.nio.channels.ReadableByteChannel;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.BlockingQueue;
/*     */ import java.util.concurrent.CountDownLatch;
/*     */ import java.util.concurrent.LinkedBlockingQueue;
/*     */ import java.util.concurrent.Semaphore;
/*     */ 
/*     */ public class HLSConnectionHolder extends ConnectionHolder
/*     */ {
/*  80 */   private URLConnection urlConnection = null;
/*  81 */   private PlaylistThread playlistThread = new PlaylistThread();
/*  82 */   private VariantPlaylist variantPlaylist = null;
/*  83 */   private Playlist currentPlaylist = null;
/*  84 */   private int mediaFileIndex = -1;
/*  85 */   private CountDownLatch readySignal = new CountDownLatch(1);
/*  86 */   private Semaphore liveSemaphore = new Semaphore(0);
/*  87 */   private boolean isPlaylistClosed = false;
/*  88 */   private boolean isBitrateAdjustable = false;
/*  89 */   private long startTime = -1L;
/*     */   private static final long HLS_VALUE_FLOAT_MULTIPLIER = 1000L;
/*     */   private static final int HLS_PROP_GET_DURATION = 1;
/*     */   private static final int HLS_PROP_GET_HLS_MODE = 2;
/*     */   private static final int HLS_PROP_GET_MIMETYPE = 3;
/*     */   private static final int HLS_VALUE_MIMETYPE_MP2T = 1;
/*     */   private static final int HLS_VALUE_MIMETYPE_MP3 = 2;
/*     */ 
/*     */   HLSConnectionHolder(URI uri)
/*     */     throws IOException
/*     */   {
/*  98 */     this.playlistThread.setPlaylistURI(uri);
/*  99 */     init();
/*     */   }
/*     */ 
/*     */   private void init() {
/* 103 */     this.playlistThread.putState(0);
/* 104 */     this.playlistThread.start();
/*     */   }
/*     */ 
/*     */   public int readNextBlock() throws IOException
/*     */   {
/* 109 */     if ((this.isBitrateAdjustable) && (this.startTime == -1L)) {
/* 110 */       this.startTime = System.currentTimeMillis();
/*     */     }
/*     */ 
/* 113 */     int read = super.readNextBlock();
/* 114 */     if ((this.isBitrateAdjustable) && (read == -1)) {
/* 115 */       long readTime = System.currentTimeMillis() - this.startTime;
/* 116 */       this.startTime = -1L;
/* 117 */       adjustBitrate(readTime);
/*     */     }
/*     */ 
/* 120 */     return read;
/*     */   }
/*     */ 
/*     */   int readBlock(long position, int size) throws IOException {
/* 124 */     throw new IOException();
/*     */   }
/*     */ 
/*     */   boolean needBuffer() {
/* 128 */     return true;
/*     */   }
/*     */ 
/*     */   boolean isSeekable() {
/* 132 */     return true;
/*     */   }
/*     */ 
/*     */   boolean isRandomAccess() {
/* 136 */     return false;
/*     */   }
/*     */ 
/*     */   public long seek(long position) {
/*     */     try {
/* 141 */       this.readySignal.await();
/*     */     } catch (Exception e) {
/* 143 */       return -1L;
/*     */     }
/*     */ 
/* 146 */     return ()(this.currentPlaylist.seek(position) * 1000.0D);
/*     */   }
/*     */ 
/*     */   public void closeConnection()
/*     */   {
/* 151 */     this.currentPlaylist.close();
/* 152 */     super.closeConnection();
/* 153 */     resetConnection();
/* 154 */     this.playlistThread.putState(1);
/*     */   }
/*     */ 
/*     */   public int property(int prop, int value)
/*     */   {
/*     */     try {
/* 160 */       this.readySignal.await();
/*     */     } catch (Exception e) {
/* 162 */       return -1;
/*     */     }
/*     */ 
/* 165 */     if (prop == 1)
/* 166 */       return (int)(this.currentPlaylist.getDuration() * 1000.0D);
/* 167 */     if (prop == 2)
/* 168 */       return 1;
/* 169 */     if (prop == 3) {
/* 170 */       return this.currentPlaylist.getMimeType();
/*     */     }
/*     */ 
/* 173 */     return -1;
/*     */   }
/*     */ 
/*     */   public int getStreamSize()
/*     */   {
/*     */     try {
/* 179 */       this.readySignal.await();
/*     */     } catch (Exception e) {
/* 181 */       return -1;
/*     */     }
/*     */ 
/* 184 */     return loadNextSegment();
/*     */   }
/*     */ 
/*     */   private void resetConnection() {
/* 188 */     super.closeConnection();
/*     */ 
/* 191 */     if ((this.urlConnection != null) && ((this.urlConnection instanceof HttpURLConnection))) {
/* 192 */       ((HttpURLConnection)this.urlConnection).disconnect();
/* 193 */       this.urlConnection = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   private int loadNextSegment()
/*     */   {
/* 201 */     resetConnection();
/*     */ 
/* 203 */     String mediaFile = this.currentPlaylist.getNextMediaFile();
/* 204 */     if (mediaFile == null) {
/* 205 */       return -1;
/*     */     }
/*     */     try
/*     */     {
/* 209 */       URI uri = new URI(mediaFile);
/* 210 */       this.urlConnection = uri.toURL().openConnection();
/* 211 */       this.channel = openChannel();
/*     */     } catch (Exception e) {
/* 213 */       return -1;
/*     */     }
/*     */ 
/* 216 */     if (this.currentPlaylist.isCurrentMediaFileDiscontinuity()) {
/* 217 */       return -1 * this.urlConnection.getContentLength();
/*     */     }
/* 219 */     return this.urlConnection.getContentLength();
/*     */   }
/*     */ 
/*     */   private ReadableByteChannel openChannel() throws IOException
/*     */   {
/* 224 */     ReadableByteChannel ch = null;
/*     */     try
/*     */     {
/* 227 */       ch = (ReadableByteChannel)AccessController.doPrivileged(new PrivilegedExceptionAction()
/*     */       {
/*     */         public ReadableByteChannel run() throws IOException {
/* 230 */           return Channels.newChannel(HLSConnectionHolder.this.urlConnection.getInputStream());
/*     */         } } );
/*     */     }
/*     */     catch (PrivilegedActionException e) {
/* 234 */       throw ((IOException)e.getException());
/*     */     }
/*     */ 
/* 237 */     return ch;
/*     */   }
/*     */ 
/*     */   private void adjustBitrate(long readTime) {
/* 241 */     int avgBitrate = (int)(this.urlConnection.getContentLength() * 8L * 1000L / readTime);
/*     */ 
/* 243 */     Playlist playlist = this.variantPlaylist.getPlaylistBasedOnBitrate(avgBitrate);
/* 244 */     if ((playlist != null) && (playlist != this.currentPlaylist)) {
/* 245 */       if (this.currentPlaylist.isLive()) {
/* 246 */         playlist.update(this.currentPlaylist.getNextMediaFile());
/* 247 */         this.playlistThread.setReloadPlaylist(playlist);
/*     */       }
/*     */ 
/* 250 */       playlist.setForceDiscontinuity(true);
/* 251 */       this.currentPlaylist = playlist;
/*     */     }
/*     */   }
/*     */ 
/*     */   private class Playlist
/*     */   {
/* 682 */     private boolean isLive = false;
/* 683 */     private long targetDuration = 0L;
/* 684 */     private URI playlistURI = null;
/* 685 */     private final Object lock = new Object();
/* 686 */     private List<String> mediaFiles = new ArrayList();
/* 687 */     private List<Double> mediaFilesStartTimes = new ArrayList();
/* 688 */     private List<Boolean> mediaFilesDiscontinuities = new ArrayList();
/* 689 */     private boolean needBaseURI = true;
/* 690 */     private String baseURI = null;
/* 691 */     private double duration = 0.0D;
/* 692 */     private int sequenceNumber = -1;
/* 693 */     private int sequenceNumberStart = -1;
/* 694 */     private boolean sequenceNumberUpdated = false;
/* 695 */     private boolean forceDiscontinuity = false;
/*     */ 
/*     */     Playlist(boolean isLive, int targetDuration) {
/* 698 */       this.isLive = isLive;
/* 699 */       this.targetDuration = (targetDuration * 1000);
/*     */ 
/* 701 */       if (isLive)
/* 702 */         this.duration = -1.0D;
/*     */     }
/*     */ 
/*     */     Playlist(URI uri)
/*     */     {
/* 707 */       this.playlistURI = uri;
/*     */     }
/*     */ 
/*     */     public void update(String nextMediaFile) {
/* 711 */       HLSConnectionHolder.PlaylistParser parser = new HLSConnectionHolder.PlaylistParser(HLSConnectionHolder.this, null);
/* 712 */       parser.load(this.playlistURI);
/*     */ 
/* 714 */       this.isLive = parser.isLivePlaylist();
/* 715 */       this.targetDuration = (parser.getTargetDuration() * 1000);
/*     */ 
/* 717 */       if (this.isLive) {
/* 718 */         this.duration = -1.0D;
/*     */       }
/*     */ 
/* 721 */       if (setSequenceNumber(parser.getSequenceNumber())) {
/* 722 */         while (parser.hasNext()) {
/* 723 */           addMediaFile(parser.getString(), parser.getDouble().doubleValue(), parser.getBoolean().booleanValue());
/*     */         }
/*     */       }
/*     */ 
/* 727 */       if (nextMediaFile != null)
/* 728 */         synchronized (this.lock) {
/* 729 */           for (int i = 0; i < this.mediaFiles.size(); i++) {
/* 730 */             String mediaFile = (String)this.mediaFiles.get(i);
/* 731 */             if (nextMediaFile.endsWith(mediaFile)) {
/* 732 */               HLSConnectionHolder.this.mediaFileIndex = (i - 1);
/* 733 */               break;
/*     */             }
/*     */           }
/*     */         }
/*     */     }
/*     */ 
/*     */     public boolean isLive()
/*     */     {
/* 741 */       return this.isLive;
/*     */     }
/*     */ 
/*     */     public long getTargetDuration() {
/* 745 */       return this.targetDuration;
/*     */     }
/*     */ 
/*     */     public void setPlaylistURI(URI uri) {
/* 749 */       this.playlistURI = uri;
/*     */     }
/*     */ 
/*     */     public URI getPlaylistURI() {
/* 753 */       return this.playlistURI;
/*     */     }
/*     */ 
/*     */     public void addMediaFile(String URI, double duration, boolean isDiscontinuity) {
/* 757 */       synchronized (this.lock)
/*     */       {
/* 759 */         if (this.needBaseURI) {
/* 760 */           setBaseURI(this.playlistURI.toString(), URI);
/*     */         }
/*     */ 
/* 763 */         if (this.isLive) {
/* 764 */           if (this.sequenceNumberUpdated) {
/* 765 */             int index = this.mediaFiles.indexOf(URI);
/* 766 */             if (index != -1) {
/* 767 */               for (int i = 0; i < index; i++) {
/* 768 */                 this.mediaFiles.remove(0);
/* 769 */                 this.mediaFilesDiscontinuities.remove(0);
/* 770 */                 if (HLSConnectionHolder.this.mediaFileIndex == -1) {
/* 771 */                   this.forceDiscontinuity = true;
/*     */                 }
/* 773 */                 if (HLSConnectionHolder.this.mediaFileIndex >= 0) {
/* 774 */                   HLSConnectionHolder.access$610(HLSConnectionHolder.this);
/*     */                 }
/*     */               }
/*     */             }
/* 778 */             this.sequenceNumberUpdated = false;
/*     */           }
/*     */ 
/* 781 */           if (this.mediaFiles.contains(URI)) {
/* 782 */             return;
/*     */           }
/*     */         }
/*     */ 
/* 786 */         this.mediaFiles.add(URI);
/* 787 */         this.mediaFilesDiscontinuities.add(Boolean.valueOf(isDiscontinuity));
/*     */ 
/* 789 */         if (this.isLive) {
/* 790 */           int permits = this.mediaFiles.size() - (HLSConnectionHolder.this.mediaFileIndex + 1);
/* 791 */           HLSConnectionHolder.this.liveSemaphore.drainPermits();
/* 792 */           HLSConnectionHolder.this.liveSemaphore.release(permits);
/*     */         } else {
/* 794 */           this.mediaFilesStartTimes.add(Double.valueOf(this.duration));
/* 795 */           this.duration += duration;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     public String getNextMediaFile() {
/* 801 */       if (this.isLive) {
/*     */         try {
/* 803 */           HLSConnectionHolder.this.liveSemaphore.acquire();
/*     */         } catch (InterruptedException e) {
/* 805 */           return null;
/*     */         }
/*     */       }
/*     */ 
/* 809 */       if (HLSConnectionHolder.this.isPlaylistClosed) {
/* 810 */         return null;
/*     */       }
/*     */ 
/* 813 */       synchronized (this.lock) {
/* 814 */         HLSConnectionHolder.access$608(HLSConnectionHolder.this);
/* 815 */         if (HLSConnectionHolder.this.mediaFileIndex < this.mediaFiles.size()) {
/* 816 */           if (this.baseURI != null) {
/* 817 */             return this.baseURI + (String)this.mediaFiles.get(HLSConnectionHolder.this.mediaFileIndex);
/*     */           }
/* 819 */           return (String)this.mediaFiles.get(HLSConnectionHolder.this.mediaFileIndex);
/*     */         }
/*     */ 
/* 822 */         return null;
/*     */       }
/*     */     }
/*     */ 
/*     */     public double getDuration()
/*     */     {
/* 828 */       return this.duration;
/*     */     }
/*     */ 
/*     */     public void setForceDiscontinuity(boolean value) {
/* 832 */       this.forceDiscontinuity = value;
/*     */     }
/*     */ 
/*     */     public boolean isCurrentMediaFileDiscontinuity() {
/* 836 */       if (this.forceDiscontinuity) {
/* 837 */         this.forceDiscontinuity = false;
/* 838 */         return true;
/*     */       }
/* 840 */       return ((Boolean)this.mediaFilesDiscontinuities.get(HLSConnectionHolder.this.mediaFileIndex)).booleanValue();
/*     */     }
/*     */ 
/*     */     public double seek(long time)
/*     */     {
/* 845 */       synchronized (this.lock) {
/* 846 */         if (this.isLive) {
/* 847 */           if (time == 0L) {
/* 848 */             HLSConnectionHolder.this.mediaFileIndex = -1;
/* 849 */             HLSConnectionHolder.this.liveSemaphore.drainPermits();
/* 850 */             HLSConnectionHolder.this.liveSemaphore.release(this.mediaFiles.size());
/*     */           }
/*     */         } else {
/* 853 */           time += this.targetDuration / 2000L;
/*     */ 
/* 855 */           int mediaFileStartTimeSize = this.mediaFilesStartTimes.size();
/*     */ 
/* 857 */           for (int index = 0; index < mediaFileStartTimeSize; index++) {
/* 858 */             if (time >= ((Double)this.mediaFilesStartTimes.get(index)).doubleValue()) {
/* 859 */               if (index + 1 < mediaFileStartTimeSize) {
/* 860 */                 if (time < ((Double)this.mediaFilesStartTimes.get(index + 1)).doubleValue()) {
/* 861 */                   HLSConnectionHolder.this.mediaFileIndex = (index - 1);
/* 862 */                   return ((Double)this.mediaFilesStartTimes.get(index)).doubleValue();
/*     */                 }
/*     */               } else {
/* 865 */                 if (time - this.targetDuration / 2000L < this.duration) {
/* 866 */                   HLSConnectionHolder.this.mediaFileIndex = (index - 1);
/* 867 */                   return ((Double)this.mediaFilesStartTimes.get(index)).doubleValue();
/* 868 */                 }if (time - this.targetDuration / 2000L == this.duration) {
/* 869 */                   return this.duration;
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 877 */       return -1.0D;
/*     */     }
/*     */ 
/*     */     public int getMimeType() {
/* 881 */       synchronized (this.lock) {
/* 882 */         if (this.mediaFiles.size() > 0) {
/* 883 */           if (((String)this.mediaFiles.get(0)).endsWith(".ts"))
/* 884 */             return 1;
/* 885 */           if (((String)this.mediaFiles.get(0)).endsWith(".mp3")) {
/* 886 */             return 2;
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 891 */       return -1;
/*     */     }
/*     */ 
/*     */     public String getMediaFileExtension() {
/* 895 */       synchronized (this.lock) {
/* 896 */         if (this.mediaFiles.size() > 0) {
/* 897 */           int index = ((String)this.mediaFiles.get(0)).lastIndexOf(".");
/* 898 */           if (index != -1) {
/* 899 */             return ((String)this.mediaFiles.get(0)).substring(index);
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 904 */       return null;
/*     */     }
/*     */ 
/*     */     public boolean setSequenceNumber(int value) {
/* 908 */       if (this.sequenceNumberStart == -1) {
/* 909 */         this.sequenceNumberStart = value;
/* 910 */       } else if (this.sequenceNumber != value) {
/* 911 */         this.sequenceNumberUpdated = true;
/* 912 */         this.sequenceNumber = value;
/*     */       } else {
/* 914 */         return false;
/*     */       }
/*     */ 
/* 917 */       return true;
/*     */     }
/*     */ 
/*     */     public void close() {
/* 921 */       if (this.isLive) {
/* 922 */         HLSConnectionHolder.this.isPlaylistClosed = true;
/* 923 */         HLSConnectionHolder.this.liveSemaphore.release();
/*     */       }
/*     */     }
/*     */ 
/*     */     private void setBaseURI(String playlistURI, String URI) {
/* 928 */       if (!URI.startsWith("http://")) {
/* 929 */         this.baseURI = playlistURI.substring(0, playlistURI.lastIndexOf("/") + 1);
/*     */       }
/* 931 */       this.needBaseURI = false;
/*     */     }
/*     */   }
/*     */ 
/*     */   private class VariantPlaylist
/*     */   {
/* 583 */     public volatile int playlistsSize = 0;
/* 584 */     private URI playlistURI = null;
/* 585 */     private int infoIndex = -1;
/* 586 */     private List<String> playlistsLocations = new ArrayList();
/* 587 */     private List<Integer> playlistsBitrates = new ArrayList();
/* 588 */     private List<HLSConnectionHolder.Playlist> playlists = new ArrayList();
/* 589 */     private String mediaFileExtension = null;
/*     */ 
/*     */     VariantPlaylist(URI uri) {
/* 592 */       this.playlistURI = uri;
/*     */     }
/*     */ 
/*     */     public void addPlaylistInfo(String location, int bitrate) {
/* 596 */       this.playlistsLocations.add(location);
/* 597 */       this.playlistsBitrates.add(Integer.valueOf(bitrate));
/*     */     }
/*     */ 
/*     */     public void addPlaylist(HLSConnectionHolder.Playlist playlist) {
/* 601 */       if (this.mediaFileExtension == null) {
/* 602 */         this.mediaFileExtension = playlist.getMediaFileExtension();
/*     */       }
/* 604 */       else if (!this.mediaFileExtension.equals(playlist.getMediaFileExtension())) {
/* 605 */         this.playlistsLocations.remove(this.infoIndex);
/* 606 */         this.playlistsBitrates.remove(this.infoIndex);
/* 607 */         this.infoIndex -= 1;
/* 608 */         return;
/*     */       }
/*     */ 
/* 611 */       this.playlists.add(playlist);
/* 612 */       this.playlistsSize = this.playlists.size();
/*     */     }
/*     */ 
/*     */     public HLSConnectionHolder.Playlist getPlaylist(int index) {
/* 616 */       if (this.playlists.size() > index) {
/* 617 */         return (HLSConnectionHolder.Playlist)this.playlists.get(index);
/*     */       }
/* 619 */       return null;
/*     */     }
/*     */ 
/*     */     public boolean hasNext()
/*     */     {
/* 624 */       this.infoIndex += 1;
/* 625 */       if ((this.playlistsLocations.size() > this.infoIndex) && (this.playlistsBitrates.size() > this.infoIndex)) {
/* 626 */         return true;
/*     */       }
/* 628 */       return false;
/*     */     }
/*     */ 
/*     */     public URI getPlaylistURI() throws URISyntaxException, MalformedURLException
/*     */     {
/* 633 */       String location = (String)this.playlistsLocations.get(this.infoIndex);
/* 634 */       if (location.startsWith("http://")) {
/* 635 */         return new URI(location);
/*     */       }
/* 637 */       return new URI(this.playlistURI.toURL().toString().substring(0, this.playlistURI.toURL().toString().lastIndexOf("/") + 1) + location);
/*     */     }
/*     */ 
/*     */     public HLSConnectionHolder.Playlist getPlaylistBasedOnBitrate(int bitrate)
/*     */     {
/* 642 */       int playlistIndex = -1;
/* 643 */       int playlistBitrate = 0;
/*     */ 
/* 646 */       for (int i = 0; i < this.playlistsBitrates.size(); i++) {
/* 647 */         int b = ((Integer)this.playlistsBitrates.get(i)).intValue();
/* 648 */         if (b < bitrate) {
/* 649 */           if (playlistIndex != -1) {
/* 650 */             if (b > playlistBitrate) {
/* 651 */               playlistBitrate = b;
/* 652 */               playlistIndex = i;
/*     */             }
/*     */           }
/* 655 */           else playlistIndex = i;
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 661 */       if (playlistIndex == -1) {
/* 662 */         for (int i = 0; i < this.playlistsBitrates.size(); i++) {
/* 663 */           int b = ((Integer)this.playlistsBitrates.get(i)).intValue();
/* 664 */           if ((b < playlistBitrate) || (playlistIndex == -1)) {
/* 665 */             playlistBitrate = b;
/* 666 */             playlistIndex = i;
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 672 */       if ((playlistIndex < 0) || (playlistIndex >= this.playlists.size())) {
/* 673 */         return null;
/*     */       }
/* 675 */       return (HLSConnectionHolder.Playlist)this.playlists.get(playlistIndex);
/*     */     }
/*     */   }
/*     */ 
/*     */   private class PlaylistParser
/*     */   {
/* 390 */     private boolean isFirstLine = true;
/* 391 */     private boolean isLineMediaFileURI = false;
/* 392 */     private boolean isEndList = false;
/* 393 */     private boolean isLinePlaylistURI = false;
/* 394 */     private boolean isVariantPlaylist = false;
/* 395 */     private boolean isDiscontinuity = false;
/* 396 */     private int targetDuration = 0;
/* 397 */     private int sequenceNumber = 0;
/* 398 */     private int dataListIndex = -1;
/* 399 */     private List<String> dataListString = new ArrayList();
/* 400 */     private List<Integer> dataListInteger = new ArrayList();
/* 401 */     private List<Double> dataListDouble = new ArrayList();
/* 402 */     private List<Boolean> dataListBoolean = new ArrayList();
/*     */ 
/*     */     private PlaylistParser() {  } 
/* 405 */     public void load(URI uri) { URLConnection connection = null;
/* 406 */       BufferedReader reader = null;
/*     */       try {
/* 409 */         connection = uri.toURL().openConnection();
/* 410 */         reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
/*     */         boolean result;
/*     */         do
/* 414 */           result = parseLine(reader.readLine());
/* 415 */         while (result);
/*     */       } catch (MalformedURLException e) {
/*     */       } catch (IOException e) {
/*     */       } finally {
/* 419 */         if (reader != null) {
/*     */           try {
/* 421 */             reader.close();
/*     */           }
/*     */           catch (IOException e) {
/*     */           }
/* 425 */           if ((connection != null) && ((connection instanceof HttpURLConnection)))
/* 426 */             ((HttpURLConnection)connection).disconnect();
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     public boolean isVariantPlaylist()
/*     */     {
/* 433 */       return this.isVariantPlaylist;
/*     */     }
/*     */ 
/*     */     public boolean isLivePlaylist() {
/* 437 */       return !this.isEndList;
/*     */     }
/*     */ 
/*     */     public int getTargetDuration() {
/* 441 */       return this.targetDuration;
/*     */     }
/*     */ 
/*     */     public int getSequenceNumber() {
/* 445 */       return this.sequenceNumber;
/*     */     }
/*     */ 
/*     */     public boolean hasNext() {
/* 449 */       this.dataListIndex += 1;
/* 450 */       if ((this.dataListString.size() > this.dataListIndex) || (this.dataListInteger.size() > this.dataListIndex) || (this.dataListDouble.size() > this.dataListIndex) || (this.dataListBoolean.size() > this.dataListIndex)) {
/* 451 */         return true;
/*     */       }
/* 453 */       return false;
/*     */     }
/*     */ 
/*     */     public String getString()
/*     */     {
/* 458 */       return (String)this.dataListString.get(this.dataListIndex);
/*     */     }
/*     */ 
/*     */     public Integer getInteger() {
/* 462 */       return (Integer)this.dataListInteger.get(this.dataListIndex);
/*     */     }
/*     */ 
/*     */     public Double getDouble() {
/* 466 */       return (Double)this.dataListDouble.get(this.dataListIndex);
/*     */     }
/*     */ 
/*     */     public Boolean getBoolean() {
/* 470 */       return (Boolean)this.dataListBoolean.get(this.dataListIndex);
/*     */     }
/*     */ 
/*     */     public void reset() {
/* 474 */       this.isFirstLine = true;
/* 475 */       this.isLineMediaFileURI = false;
/* 476 */       this.isEndList = false;
/* 477 */       this.isLinePlaylistURI = false;
/* 478 */       this.isVariantPlaylist = false;
/* 479 */       this.isDiscontinuity = false;
/*     */ 
/* 481 */       this.targetDuration = 0;
/* 482 */       this.sequenceNumber = 0;
/*     */ 
/* 484 */       this.dataListIndex = -1;
/*     */ 
/* 486 */       this.dataListString.clear();
/* 487 */       this.dataListInteger.clear();
/* 488 */       this.dataListDouble.clear();
/* 489 */       this.dataListBoolean.clear();
/*     */     }
/*     */ 
/*     */     public boolean parseLine(String line) {
/* 493 */       if (line == null) {
/* 494 */         return false;
/*     */       }
/*     */ 
/* 498 */       if (this.isFirstLine) {
/* 499 */         if (line.compareTo("#EXTM3U") != 0) {
/* 500 */           return false;
/*     */         }
/*     */ 
/* 503 */         this.isFirstLine = false;
/* 504 */         return true;
/*     */       }
/*     */ 
/* 508 */       if ((line.isEmpty()) || ((line.startsWith("#")) && (!line.startsWith("#EXT")))) {
/* 509 */         return true;
/*     */       }
/*     */ 
/* 512 */       if (line.startsWith("#EXTINF"))
/*     */       {
/* 514 */         String[] s1 = line.split(":");
/* 515 */         if ((s1.length == 2) && (s1[1].length() > 0)) {
/* 516 */           String[] s2 = s1[1].split(",");
/* 517 */           if (s2.length >= 1) {
/* 518 */             this.dataListDouble.add(Double.valueOf(Double.parseDouble(s2[0])));
/*     */           }
/*     */         }
/*     */ 
/* 522 */         this.isLineMediaFileURI = true;
/* 523 */       } else if (line.startsWith("#EXT-X-TARGETDURATION"))
/*     */       {
/* 525 */         String[] s1 = line.split(":");
/* 526 */         if ((s1.length == 2) && (s1[1].length() > 0))
/* 527 */           this.targetDuration = Integer.parseInt(s1[1]);
/*     */       }
/* 529 */       else if (line.startsWith("#EXT-X-MEDIA-SEQUENCE"))
/*     */       {
/* 531 */         String[] s1 = line.split(":");
/* 532 */         if ((s1.length == 2) && (s1[1].length() > 0))
/* 533 */           this.sequenceNumber = Integer.parseInt(s1[1]);
/*     */       }
/* 535 */       else if (line.startsWith("#EXT-X-STREAM-INF"))
/*     */       {
/* 537 */         this.isVariantPlaylist = true;
/*     */ 
/* 539 */         int bitrate = 0;
/* 540 */         String[] s1 = line.split(":");
/* 541 */         if ((s1.length == 2) && (s1[1].length() > 0)) {
/* 542 */           String[] s2 = s1[1].split(",");
/* 543 */           if (s2.length > 0) {
/* 544 */             for (int i = 0; i < s2.length; i++) {
/* 545 */               s2[i] = s2[i].trim();
/* 546 */               if (s2[i].startsWith("BANDWIDTH")) {
/* 547 */                 String[] s3 = s2[i].split("=");
/* 548 */                 if ((s3.length == 2) && (s3[1].length() > 0)) {
/* 549 */                   bitrate = Integer.parseInt(s3[1]);
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */ 
/* 556 */         if (bitrate < 1) {
/* 557 */           return false;
/*     */         }
/*     */ 
/* 560 */         this.dataListInteger.add(Integer.valueOf(bitrate));
/*     */ 
/* 562 */         this.isLinePlaylistURI = true;
/* 563 */       } else if (line.startsWith("#EXT-X-ENDLIST")) {
/* 564 */         this.isEndList = true;
/* 565 */       } else if (line.startsWith("#EXT-X-DISCONTINUITY")) {
/* 566 */         this.isDiscontinuity = true;
/* 567 */       } else if (this.isLinePlaylistURI) {
/* 568 */         this.isLinePlaylistURI = false;
/* 569 */         this.dataListString.add(line);
/* 570 */       } else if (this.isLineMediaFileURI) {
/* 571 */         this.isLineMediaFileURI = false;
/* 572 */         this.dataListString.add(line);
/* 573 */         this.dataListBoolean.add(Boolean.valueOf(this.isDiscontinuity));
/* 574 */         this.isDiscontinuity = false;
/*     */       }
/*     */ 
/* 577 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */   private class PlaylistThread extends Thread
/*     */   {
/*     */     public static final int STATE_INIT = 0;
/*     */     public static final int STATE_EXIT = 1;
/*     */     public static final int STATE_RELOAD_PLAYLIST = 2;
/* 260 */     private BlockingQueue<Integer> stateQueue = new LinkedBlockingQueue();
/* 261 */     private URI playlistURI = null;
/* 262 */     private HLSConnectionHolder.Playlist reloadPlaylist = null;
/* 263 */     private final Object reloadLock = new Object();
/* 264 */     private volatile boolean stopped = false;
/*     */ 
/*     */     PlaylistThread() {
/* 267 */       setName("JFXMedia HLS Playlist Thread");
/* 268 */       setDaemon(true);
/*     */     }
/*     */ 
/*     */     public void setPlaylistURI(URI playlistURI) {
/* 272 */       this.playlistURI = playlistURI;
/*     */     }
/*     */ 
/*     */     public void setReloadPlaylist(HLSConnectionHolder.Playlist playlist) {
/* 276 */       synchronized (this.reloadLock) {
/* 277 */         this.reloadPlaylist = playlist;
/*     */       }
/*     */     }
/*     */ 
/*     */     public void run()
/*     */     {
/* 283 */       while (!this.stopped)
/*     */         try {
/* 285 */           int state = ((Integer)this.stateQueue.take()).intValue();
/* 286 */           switch (state) {
/*     */           case 0:
/* 288 */             stateInit();
/* 289 */             break;
/*     */           case 1:
/* 291 */             this.stopped = true;
/* 292 */             break;
/*     */           case 2:
/* 294 */             stateReloadPlaylist();
/*     */           }
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/*     */         }
/*     */     }
/*     */ 
/*     */     public void putState(int state)
/*     */     {
/* 305 */       if (this.stateQueue != null)
/* 306 */         this.stateQueue.offer(Integer.valueOf(state));
/*     */     }
/*     */ 
/*     */     private void stateInit()
/*     */     {
/* 311 */       if (this.playlistURI == null) {
/* 312 */         return;
/*     */       }
/*     */ 
/* 315 */       HLSConnectionHolder.PlaylistParser parser = new HLSConnectionHolder.PlaylistParser(HLSConnectionHolder.this, null);
/* 316 */       parser.load(this.playlistURI);
/*     */ 
/* 318 */       if (parser.isVariantPlaylist()) {
/* 319 */         HLSConnectionHolder.this.variantPlaylist = new HLSConnectionHolder.VariantPlaylist(HLSConnectionHolder.this, this.playlistURI);
/*     */ 
/* 321 */         while (parser.hasNext()) {
/* 322 */           HLSConnectionHolder.this.variantPlaylist.addPlaylistInfo(parser.getString(), parser.getInteger().intValue());
/*     */         }
/*     */       }
/* 325 */       if (HLSConnectionHolder.this.currentPlaylist == null) {
/* 326 */         HLSConnectionHolder.this.currentPlaylist = new HLSConnectionHolder.Playlist(HLSConnectionHolder.this, parser.isLivePlaylist(), parser.getTargetDuration());
/* 327 */         HLSConnectionHolder.this.currentPlaylist.setPlaylistURI(this.playlistURI);
/*     */       }
/*     */ 
/* 330 */       if (HLSConnectionHolder.this.currentPlaylist.setSequenceNumber(parser.getSequenceNumber())) {
/* 331 */         while (parser.hasNext()) {
/* 332 */           HLSConnectionHolder.this.currentPlaylist.addMediaFile(parser.getString(), parser.getDouble().doubleValue(), parser.getBoolean().booleanValue());
/*     */         }
/*     */       }
/*     */ 
/* 336 */       if (HLSConnectionHolder.this.variantPlaylist != null) {
/* 337 */         HLSConnectionHolder.this.variantPlaylist.addPlaylist(HLSConnectionHolder.this.currentPlaylist);
/*     */       }
/*     */ 
/* 342 */       if (HLSConnectionHolder.this.variantPlaylist != null)
/* 343 */         while (HLSConnectionHolder.this.variantPlaylist.hasNext())
/*     */           try {
/* 345 */             HLSConnectionHolder.this.currentPlaylist = new HLSConnectionHolder.Playlist(HLSConnectionHolder.this, HLSConnectionHolder.this.variantPlaylist.getPlaylistURI());
/* 346 */             HLSConnectionHolder.this.currentPlaylist.update(null);
/* 347 */             HLSConnectionHolder.this.variantPlaylist.addPlaylist(HLSConnectionHolder.this.currentPlaylist);
/*     */           }
/*     */           catch (URISyntaxException e)
/*     */           {
/*     */           }
/*     */           catch (MalformedURLException e)
/*     */           {
/*     */           }
/* 355 */       if (HLSConnectionHolder.this.variantPlaylist != null) {
/* 356 */         HLSConnectionHolder.this.currentPlaylist = HLSConnectionHolder.this.variantPlaylist.getPlaylist(0);
/* 357 */         HLSConnectionHolder.this.isBitrateAdjustable = true;
/*     */       }
/*     */ 
/* 361 */       if (HLSConnectionHolder.this.currentPlaylist.isLive()) {
/* 362 */         setReloadPlaylist(HLSConnectionHolder.this.currentPlaylist);
/* 363 */         putState(2);
/*     */       }
/*     */ 
/* 366 */       HLSConnectionHolder.this.readySignal.countDown();
/*     */     }
/*     */ 
/*     */     private void stateReloadPlaylist()
/*     */     {
/*     */       try
/*     */       {
/*     */         long timeout;
/* 372 */         synchronized (this.reloadLock) {
/* 373 */           timeout = this.reloadPlaylist.getTargetDuration() / 2L;
/*     */         }
/* 375 */         Thread.sleep(timeout);
/*     */       } catch (Exception e) {
/* 377 */         return;
/*     */       }
/*     */ 
/* 380 */       synchronized (this.reloadLock) {
/* 381 */         this.reloadPlaylist.update(null);
/*     */       }
/*     */ 
/* 384 */       putState(2);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.media.jfxmedia.locator.HLSConnectionHolder
 * JD-Core Version:    0.6.2
 */