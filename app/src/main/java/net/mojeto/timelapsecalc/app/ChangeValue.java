package net.mojeto.timelapsecalc.app;

/**
 * Created by honza on 25.6.14.
 */
public interface ChangeValue {

    public void setVideoFrameRate(double frameRate, ValueForChange recount);
    public void setCameraSumOfFrames(long sumOfFrames, ValueForChange cameraRecount,
                                     ValueForChange videoRecount);
}
