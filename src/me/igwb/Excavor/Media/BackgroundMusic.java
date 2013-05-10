package me.igwb.Excavor.Media;

import javax.sound.sampled.Clip;

public class BackgroundMusic {

	private Clip MusicClip;
	
	private TimeSpan intro, loop, outro;
	
	public BackgroundMusic(Clip musicClip) {
		MusicClip = musicClip;
		if(musicClip == null) return;
		intro = new TimeSpan(0, 25);
		loop = new TimeSpan(26, 78);
		outro = new TimeSpan(79, -1);
	}

	public void play(TimeSpan part, boolean loop) {
		if(MusicClip == null) return;
		MusicClip.setFramePosition(part.begin);
		MusicClip.setLoopPoints(part.begin, part.end);		
		MusicClip.loop(loop ? Clip.LOOP_CONTINUOUSLY : 0);
	}
	
	public void play() {
		if(MusicClip == null) return;
		MusicClip.start();
	}
	
	public void stop(boolean flush) {
		if(MusicClip == null) return;
		MusicClip.stop();
		if(flush) MusicClip.flush();
	}
	
	public void play(TimeSpan span, TimeSpan loop) {
		if(MusicClip == null) return;
		MusicClip.setFramePosition(span.begin);
		MusicClip.setLoopPoints(loop.begin, loop.end);
		MusicClip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public Clip getMusicClip() {
		return MusicClip;
	}

	public void setMusicClip(Clip musicClip) {
		MusicClip = musicClip;
	}


	public TimeSpan getIntro() {
		return intro;
	}


	public void setIntro(TimeSpan intro) {
		this.intro = intro;
	}


	public TimeSpan getLoop() {
		return loop;
	}


	public void setLoop(TimeSpan loop) {
		this.loop = loop;
	}


	public TimeSpan getOutro() {
		return outro;
	}


	public void setOutro(TimeSpan outro) {
		this.outro = outro;
	}


	class TimeSpan {
		
		public int begin;
		public int end;
		
		public TimeSpan(int Begin, int End) {
			int frameRate = (int) MusicClip.getFormat().getFrameRate();
			this.begin = (Begin * frameRate);
			this.end = (End == -1 ? -1 : End * frameRate);
		}
		
		public void Add(TimeSpan timeSpan) {
			end += timeSpan.end;
		}
	}
}
