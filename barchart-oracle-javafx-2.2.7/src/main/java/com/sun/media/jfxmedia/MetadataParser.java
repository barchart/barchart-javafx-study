package com.sun.media.jfxmedia;

import com.sun.media.jfxmedia.events.MetadataListener;
import java.io.IOException;

public abstract interface MetadataParser
{
  public static final String DURATION_TAG_NAME = "duration";
  public static final String CREATIONDATE_TAG_NAME = "creationdate";
  public static final String WIDTH_TAG_NAME = "width";
  public static final String HEIGHT_TAG_NAME = "height";
  public static final String FRAMERATE_TAG_NAME = "framerate";
  public static final String VIDEOCODEC_TAG_NAME = "video codec";
  public static final String AUDIOCODEC_TAG_NAME = "audio codec";
  public static final String IMAGE_TAG_NAME = "image";
  public static final String ALBUMARTIST_TAG_NAME = "album artist";
  public static final String ALBUM_TAG_NAME = "album";
  public static final String ARTIST_TAG_NAME = "artist";
  public static final String COMMENT_TAG_NAME = "comment";
  public static final String COMPOSER_TAG_NAME = "composer";
  public static final String GENRE_TAG_NAME = "genre";
  public static final String TITLE_TAG_NAME = "title";
  public static final String TRACKNUMBER_TAG_NAME = "track number";
  public static final String TRACKCOUNT_TAG_NAME = "track count";
  public static final String DISCNUMBER_TAG_NAME = "disc number";
  public static final String DISCCOUNT_TAG_NAME = "disc count";
  public static final String YEAR_TAG_NAME = "year";
  public static final String TEXT_TAG_NAME = "text";
  public static final String RAW_METADATA_TAG_NAME = "raw metadata";
  public static final String RAW_FLV_METADATA_NAME = "FLV";
  public static final String RAW_ID3_METADATA_NAME = "ID3";

  public abstract void addListener(MetadataListener paramMetadataListener);

  public abstract void removeListener(MetadataListener paramMetadataListener);

  public abstract void startParser()
    throws IOException;

  public abstract void stopParser();
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.media.jfxmedia.MetadataParser
 * JD-Core Version:    0.6.2
 */