package net.mojeto.timelapsecalc.app;

import static java.lang.Math.round;

/**
 * Created by honza on 20.6.14.
 */
public class FrameSet {

    public enum Change {
        FRAME_DURATION, SUM_OF_FRAMES, DURATION
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
        return 1000 / getFrameDuration().getValue();
    }

    public void setFrameRate(double frameRate, Change change) {
        setFrameDuration(new Duration(1000 / frameRate), change);
    }

    public long getSumOfFrames() {
        return mFrames;
    }

    protected void setSumOfFrames(long frames) {
        if (frames < 1) {
            throw new IllegalArgumentException("sum of frames have to be greater then zero");
        }
        mFrames = frames;
    }

    public void setSumOfFrames(long frames, Change change) {
        switch (change) {
            case FRAME_DURATION:
                setFrameDuration(countFrameDuration(getDuration(), frames));
            case DURATION:
                setSumOfFrames(frames);
                break;
            default:
                throw new IllegalArgumentException(
                        "on set sum of frames change can be only (FRAME_DURATION or DURATION)");
        }
    }

    public Duration getFrameDuration() {
        return mFrameDuration;
    }

    protected void setFrameDuration(Duration duration) {
        mFrameDuration = duration;
    }

    public void setFrameDuration(Duration duration, Change change) {
        switch (change) {
            case SUM_OF_FRAMES:
                setSumOfFrames(countSumOfFrames(getDuration(), duration));
            case DURATION:
                setFrameDuration(duration);
                break;
            default:
                throw new IllegalArgumentException(
                        "on set frame duration change can be only (SUM_OF_FRAMES or DURATION)");
        }
    }

    public Duration getDuration() {
        return countDuration(getFrameDuration(), getSumOfFrames());
    }

    public void setDuration(Duration duration, Change change) {
        switch (change) {
            case FRAME_DURATION:
                setFrameDuration(countFrameDuration(duration, getSumOfFrames()));
                break;
            case SUM_OF_FRAMES:
                setSumOfFrames(countSumOfFrames(duration, getFrameDuration()));
                break;
            default:
                throw new IllegalArgumentException(
                        "on set duration change can be only (SUM_OF_FRAMES or FRAME_DURATION)");
        }
    }

    public Duration countDuration(Duration frameDuration, long sumOfFrames) {
        return new Duration(sumOfFrames * frameDuration.getValue());
    }

    public Duration countFrameDuration(Duration duration, long sumOfFrames) {
        return new Duration(duration.getValue() / sumOfFrames);
    }

    public long countSumOfFrames(Duration duration, Duration frameDuration) {
        return round(duration.getValue() / frameDuration.getValue());
    }

}
