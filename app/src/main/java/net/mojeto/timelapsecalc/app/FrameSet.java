package net.mojeto.timelapsecalc.app;

import static java.lang.Math.round;

/**
 * Created by honza on 20.6.14.
 */
public class FrameSet {

    public enum Change {
        FRAME_DURATION, SUM_OF_FRAMES
    }

    private Duration mFrameDuration; //between frames in milliseconds
    private long mFrames; //no of frames

    public FrameSet() {
        this(1, 1);
    }

    public FrameSet(long frameLength, long frames) {
        this((double) frameLength, frames);
    }

    public FrameSet(double frameLength, long frames) {
        this(new Duration(frameLength), frames);
    }

    public FrameSet(Duration frameDuration, long frames) {
        setFrameDuration(frameDuration);
        setSumOfFrames(frames);
    }

    public double getFrameRate() {
        return 1000 / mFrameDuration.getValue();
    }

    public void setFrameRate(double frameRate) {
        setFrameDuration(new Duration((1000 / frameRate)));
    }

    public long getSumOfFrames() {
        return mFrames;
    }

    public void setSumOfFrames(long frames) {
        if (frames < 1) {
            throw new IllegalArgumentException("sum of frames have to be greater then zero");
        }
        mFrames = frames;
    }

    public Duration getFrameDuration() {
        return mFrameDuration;
    }

    public void setFrameDuration(Duration duration) {
        mFrameDuration = duration;
    }

    public Duration getDuration() {
        return new Duration(getSumOfFrames() * getFrameDuration().getValue());
    }

    public void setDuration(Duration duration, Change change) {
        switch (change) {
            case FRAME_DURATION:
                setFrameDuration(new Duration(duration.getValue() / getSumOfFrames()));
                break;
            case SUM_OF_FRAMES:
            default:
                setSumOfFrames(round(
                        duration.getValue() / getFrameDuration().getValue()));
                break;
        }
    }

}
