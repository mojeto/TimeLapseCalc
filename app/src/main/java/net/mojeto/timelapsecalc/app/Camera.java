package net.mojeto.timelapsecalc.app;

/**
 * Created by honza on 20.6.14.
 */
public class Camera extends FrameSet {

    public Camera() {
        super();
    }

    public Camera(long frameLength, long frames) {
        super(frameLength, frames);
    }

    public Camera(double frameLength, long frames) {
        super(frameLength, frames);
    }

    public Camera(Duration frameLength, long frames) {
        super(frameLength, frames);
    }
}
