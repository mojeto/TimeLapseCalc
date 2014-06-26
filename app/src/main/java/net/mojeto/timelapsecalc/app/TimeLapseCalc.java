package net.mojeto.timelapsecalc.app;

/**
 * Created by honza on 25.6.14.
 */
public class TimeLapseCalc {

    private final Camera mCamera;
    private final Video mVideo;

    public TimeLapseCalc(Camera camera, Video video) {
        if (camera.getSumOfFrames() != video.getSumOfFrames())
            throw new IllegalArgumentException("camera and video must have equal sum of frames");
        mCamera = camera;
        mVideo = video;
    }

    public Video getVideo() {
        return mVideo;
    }

    public Camera getCamera() {
        return mCamera;
    }

    public void setCameraFrameDuration(Duration time, ValueForChange recount) {
        switch(recount) {
            case CAMERA_RECORD_DURATION:
                mCamera.setFrameDuration(time, FrameSet.Change.DURATION);
                break;
            case VIDEO_FRAME_RATE:
                mCamera.setFrameDuration(time, FrameSet.Change.SUM_OF_FRAMES);
                mVideo.setSumOfFrames(mCamera.getSumOfFrames(), FrameSet.Change.FRAME_DURATION);
                break;
            case VIDEO_DURATION:
                mCamera.setFrameDuration(time, FrameSet.Change.SUM_OF_FRAMES);
                mVideo.setSumOfFrames(mCamera.getSumOfFrames(), FrameSet.Change.DURATION);
                break;
            default:
                throw new IllegalArgumentException(
                    "recount must be CAMERA_RECORD_DURATION or VIDEO_FRAME_RATE or VIDEO_DURATION");
        }
    }

    public void setCameraRecordDuration(Duration time, ValueForChange recount) {
        switch (recount) {
            case CAMERA_FRAME_DURATION:
                mCamera.setDuration(time, FrameSet.Change.FRAME_DURATION);
                break;
            case VIDEO_FRAME_RATE:
                mCamera.setDuration(time, FrameSet.Change.SUM_OF_FRAMES);
                mVideo.setSumOfFrames(mCamera.getSumOfFrames(), FrameSet.Change.FRAME_DURATION);
                break;
            case VIDEO_DURATION:
                mCamera.setDuration(time, FrameSet.Change.SUM_OF_FRAMES);
                mVideo.setSumOfFrames(mCamera.getSumOfFrames(), FrameSet.Change.DURATION);
                break;
            default:
                throw new IllegalArgumentException(
                    "recount must be CAMERA_FRAME_DURATION or VIDEO_FRAME_RATE or VIDEO_DURATION");
        }
    }

    public void setSumOfFrames(long sumOfFrames, ValueForChange cameraRecount,
                               ValueForChange videoRecount) {
        FrameSet.Change cameraChange, videoChange;

        switch (cameraRecount) {
            case CAMERA_FRAME_DURATION:
                cameraChange = FrameSet.Change.FRAME_DURATION;
                break;
            case CAMERA_RECORD_DURATION:
                cameraChange = FrameSet.Change.DURATION;
                break;
            default:
                throw new IllegalArgumentException(
                        "camera recount must be CAMERA_RECORD_DURATION or CAMERA_FRAME_DURATION");
        }

        switch (videoRecount) {
            case VIDEO_DURATION:
                videoChange = FrameSet.Change.DURATION;
                break;
            case VIDEO_FRAME_RATE:
                videoChange = FrameSet.Change.FRAME_DURATION;
                break;
            default:
                throw new IllegalArgumentException(
                        "video recount must be VIDEO_DURATION or VIDEO_FRAME_RATE");
        }

        mCamera.setSumOfFrames(sumOfFrames, cameraChange);
        mVideo.setSumOfFrames(sumOfFrames, videoChange);
    }

    public void setVideoFrameRate(double frameRate, ValueForChange recount) {
        switch (recount) {
            case CAMERA_FRAME_DURATION:
                mVideo.setFrameRate(frameRate, FrameSet.Change.SUM_OF_FRAMES);
                mCamera.setSumOfFrames(mVideo.getSumOfFrames(), FrameSet.Change.FRAME_DURATION);
                break;
            case CAMERA_RECORD_DURATION:
                mVideo.setFrameRate(frameRate, FrameSet.Change.SUM_OF_FRAMES);
                mCamera.setSumOfFrames(mVideo.getSumOfFrames(), FrameSet.Change.DURATION);
                break;
            case VIDEO_DURATION:
                mVideo.setFrameRate(frameRate, FrameSet.Change.DURATION);
                break;
            default:
                throw new IllegalArgumentException(
                        "recount must be CAMERA_RECORD_DURATION or CAMERA_FRAME_DURATION or VIDEO_DURATION");
        }
    }
}
