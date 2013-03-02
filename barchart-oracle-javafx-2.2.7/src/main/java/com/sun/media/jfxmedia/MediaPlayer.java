package com.sun.media.jfxmedia;

import com.sun.media.jfxmedia.control.VideoRenderControl;
import com.sun.media.jfxmedia.effects.AudioEqualizer;
import com.sun.media.jfxmedia.effects.AudioSpectrum;
import com.sun.media.jfxmedia.events.AudioSpectrumListener;
import com.sun.media.jfxmedia.events.BufferListener;
import com.sun.media.jfxmedia.events.MarkerListener;
import com.sun.media.jfxmedia.events.MediaErrorListener;
import com.sun.media.jfxmedia.events.PlayerStateEvent.PlayerState;
import com.sun.media.jfxmedia.events.PlayerStateListener;
import com.sun.media.jfxmedia.events.PlayerTimeListener;
import com.sun.media.jfxmedia.events.VideoTrackSizeListener;

public abstract interface MediaPlayer
{
  public abstract void addMediaErrorListener(MediaErrorListener paramMediaErrorListener);

  public abstract void removeMediaErrorListener(MediaErrorListener paramMediaErrorListener);

  public abstract void addMediaPlayerListener(PlayerStateListener paramPlayerStateListener);

  public abstract void removeMediaPlayerListener(PlayerStateListener paramPlayerStateListener);

  public abstract void addMediaTimeListener(PlayerTimeListener paramPlayerTimeListener);

  public abstract void removeMediaTimeListener(PlayerTimeListener paramPlayerTimeListener);

  public abstract void addVideoTrackSizeListener(VideoTrackSizeListener paramVideoTrackSizeListener);

  public abstract void removeVideoTrackSizeListener(VideoTrackSizeListener paramVideoTrackSizeListener);

  public abstract void addMarkerListener(MarkerListener paramMarkerListener);

  public abstract void removeMarkerListener(MarkerListener paramMarkerListener);

  public abstract void addBufferListener(BufferListener paramBufferListener);

  public abstract void removeBufferListener(BufferListener paramBufferListener);

  public abstract void addAudioSpectrumListener(AudioSpectrumListener paramAudioSpectrumListener);

  public abstract void removeAudioSpectrumListener(AudioSpectrumListener paramAudioSpectrumListener);

  public abstract VideoRenderControl getVideoRenderControl();

  public abstract Media getMedia();

  public abstract void setAudioSyncDelay(long paramLong);

  public abstract long getAudioSyncDelay();

  public abstract void play();

  public abstract void stop();

  public abstract void pause();

  public abstract float getRate();

  public abstract void setRate(float paramFloat);

  public abstract double getPresentationTime();

  public abstract float getVolume();

  public abstract void setVolume(float paramFloat);

  public abstract boolean getMute();

  public abstract void setMute(boolean paramBoolean);

  public abstract float getBalance();

  public abstract void setBalance(float paramFloat);

  public abstract AudioEqualizer getEqualizer();

  public abstract AudioSpectrum getAudioSpectrum();

  public abstract double getDuration();

  public abstract double getStartTime();

  public abstract void setStartTime(double paramDouble);

  public abstract double getStopTime();

  public abstract void setStopTime(double paramDouble);

  public abstract void seek(double paramDouble);

  public abstract PlayerStateEvent.PlayerState getState();

  public abstract void dispose();
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.media.jfxmedia.MediaPlayer
 * JD-Core Version:    0.6.2
 */