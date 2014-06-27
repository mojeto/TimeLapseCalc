package net.mojeto.timelapsecalc.app;

/**
 * Created by honza on 25.6.14.
 */
public interface ChangeValue {

    public void setCameraFrameDuration(Duration time, ValueForChange recount);
    public void setCameraRecordDuration(Duration time, ValueForChange recount);
    public void setVideoDuration(Duration time, ValueForChange recount);
    public void setVideoFrameRate(double frameRate, ValueForChange recount);
    public void setCameraSumOfFrames(long sumOfFrames, ValueForChange cameraRecount,
                                     ValueForChange videoRecount);
}
