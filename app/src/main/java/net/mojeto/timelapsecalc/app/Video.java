package net.mojeto.timelapsecalc.app;

/**
 * Created by honza on 20.6.14.
 */
public class Video extends FrameSet {

    public Video() {
        super();
    }

    public Video(long frameLength, long frames) {
        super(frameLength, frames);
    }

    public Video(double frameLength, long frames) {
        super(frameLength, frames);
    }

    public Video(Duration frameLength, long frames) {
        super(frameLength, frames);
    }
}
