package net.mojeto.timelapsecalc.app;

/**
 * Created by honza on 25.6.14.
 */
public class TimeLapseCalc {

    private final Camera mCamera;
    private final Video mVideo;

    public TimeLapseCalc(Camera camera, Video video) {
        mCamera = camera;
        mVideo = video;
    }

    public Video getVideo() {
        return mVideo;
    }

    public Camera getCamera() {
        return mCamera;
    }

    public void setVideoFrameRateChangeCameraFrameDuration(double frameRate) {
        //set video frame rate change sum of frames
        Duration videoDuration = mVideo.getDuration();
        mVideo.setFrameRate(frameRate);
        mVideo.setDuration(videoDuration, FrameSet.Change.SUM_OF_FRAMES);

        //set camera sum of frames change frame duration
        Duration cameraDuration = mCamera.getDuration();
        mCamera.setSumOfFrames(mVideo.getSumOfFrames());
        mCamera.setDuration(cameraDuration, FrameSet.Change.FRAME_DURATION);
    }

    public void setVideoFrameRateChangeCameraDuration(double frameRate) {
        //set video frame rate change sum of frames
        Duration videoDuration = mVideo.getDuration();
        mVideo.setFrameRate(frameRate);
        mVideo.setDuration(videoDuration, FrameSet.Change.SUM_OF_FRAMES);

        //set camera sum of frames, change frame rate
        mCamera.setSumOfFrames(mVideo.getSumOfFrames());
    }

    public void setVideoFrameRateChangeVideoDuration(double frameRate) {
        mVideo.setFrameRate(frameRate);
    }
}
