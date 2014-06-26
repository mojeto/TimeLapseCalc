package net.mojeto.timelapsecalc.app.test;

import junit.framework.TestCase;

import net.mojeto.timelapsecalc.app.Camera;
import net.mojeto.timelapsecalc.app.Duration;
import net.mojeto.timelapsecalc.app.TimeLapseCalc;
import net.mojeto.timelapsecalc.app.ValueForChange;
import net.mojeto.timelapsecalc.app.Video;

/**
 * Created by honza on 26.6.14.
 */
public class TimeLapseCalcTest extends TestCase {

    TimeLapseCalc mCalc;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mCalc = new TimeLapseCalc(new Camera(2000l, 600), new Video(40l, 600));
    }

    public void testSetCameraFrameDuration() {
        mCalc.setCameraFrameDuration(new Duration(1000l), ValueForChange.CAMERA_RECORD_DURATION);
        assertEquals(new Duration(1000), mCalc.getCamera().getFrameDuration());
        assertEquals(600, mCalc.getCamera().getSumOfFrames());
        assertEquals(new Duration(600000), mCalc.getCamera().getDuration());
        assertEquals(new Duration(40), mCalc.getVideo().getFrameDuration());
        assertEquals(600, mCalc.getVideo().getSumOfFrames());
        assertEquals(new Duration(24000), mCalc.getVideo().getDuration());

        mCalc.setCameraFrameDuration(new Duration(2000l), ValueForChange.VIDEO_FRAME_RATE);
        assertEquals(new Duration(2000l), mCalc.getCamera().getFrameDuration());
        assertEquals(300, mCalc.getCamera().getSumOfFrames());
        assertEquals(new Duration(600000), mCalc.getCamera().getDuration());
        assertEquals(new Duration(80), mCalc.getVideo().getFrameDuration());
        assertEquals(300, mCalc.getVideo().getSumOfFrames());
        assertEquals(new Duration(24000), mCalc.getVideo().getDuration());

        mCalc.setCameraFrameDuration(new Duration(1000l), ValueForChange.VIDEO_DURATION);
        assertEquals(new Duration(1000l), mCalc.getCamera().getFrameDuration());
        assertEquals(600, mCalc.getCamera().getSumOfFrames());
        assertEquals(new Duration(600000), mCalc.getCamera().getDuration());
        assertEquals(new Duration(80), mCalc.getVideo().getFrameDuration());
        assertEquals(600, mCalc.getVideo().getSumOfFrames());
        assertEquals(new Duration(48000), mCalc.getVideo().getDuration());
    }

    public void testSetCameraRecordDuration() {
        mCalc.setCameraRecordDuration(new Duration(30000), ValueForChange.CAMERA_FRAME_DURATION);
        assertEquals(new Duration(30000), mCalc.getCamera().getDuration());
        assertEquals(new Duration(50), mCalc.getCamera().getFrameDuration());
        assertEquals(600, mCalc.getCamera().getSumOfFrames());
        assertEquals(new Duration(24000), mCalc.getVideo().getDuration());
        assertEquals(new Duration(40l), mCalc.getVideo().getFrameDuration());
        assertEquals(600, mCalc.getVideo().getSumOfFrames());

        mCalc.setCameraRecordDuration(new Duration(100000), ValueForChange.VIDEO_DURATION);
        assertEquals(new Duration(100000), mCalc.getCamera().getDuration());
        assertEquals(new Duration(50), mCalc.getCamera().getFrameDuration());
        assertEquals(2000, mCalc.getCamera().getSumOfFrames());
        assertEquals(new Duration(80000), mCalc.getVideo().getDuration());
        assertEquals(new Duration(40l), mCalc.getVideo().getFrameDuration());
        assertEquals(2000, mCalc.getVideo().getSumOfFrames());

        mCalc.setCameraRecordDuration(new Duration(50000), ValueForChange.VIDEO_FRAME_RATE);
        assertEquals(new Duration(50000), mCalc.getCamera().getDuration());
        assertEquals(new Duration(50), mCalc.getCamera().getFrameDuration());
        assertEquals(1000, mCalc.getCamera().getSumOfFrames());
        assertEquals(new Duration(80000), mCalc.getVideo().getDuration());
        assertEquals(new Duration(80l), mCalc.getVideo().getFrameDuration());
        assertEquals(1000, mCalc.getVideo().getSumOfFrames());
    }

    public void testSetSumOfFrames() {
        mCalc.setSumOfFrames(1000, ValueForChange.CAMERA_RECORD_DURATION, ValueForChange.VIDEO_DURATION);
        assertEquals(new Duration(2000l), mCalc.getCamera().getFrameDuration());
        assertEquals(1000, mCalc.getCamera().getSumOfFrames());
        assertEquals(new Duration(2000000), mCalc.getCamera().getDuration());
        assertEquals(new Duration(40l), mCalc.getVideo().getFrameDuration());
        assertEquals(1000, mCalc.getVideo().getSumOfFrames());
        assertEquals(new Duration(40000), mCalc.getVideo().getDuration());

        mCalc.setSumOfFrames(2000, ValueForChange.CAMERA_FRAME_DURATION, ValueForChange.VIDEO_FRAME_RATE);
        assertEquals(new Duration(1000l), mCalc.getCamera().getFrameDuration());
        assertEquals(2000, mCalc.getCamera().getSumOfFrames());
        assertEquals(new Duration(2000000), mCalc.getCamera().getDuration());
        assertEquals(new Duration(20l), mCalc.getVideo().getFrameDuration());
        assertEquals(2000, mCalc.getVideo().getSumOfFrames());
        assertEquals(new Duration(40000), mCalc.getVideo().getDuration());
    }

    public void testSetVideoFrameRate() {
        mCalc.setVideoFrameRate(50.0, ValueForChange.VIDEO_DURATION);
        assertEquals(new Duration(20l), mCalc.getVideo().getFrameDuration());
        assertEquals(600, mCalc.getVideo().getSumOfFrames());
        assertEquals(new Duration(12000), mCalc.getVideo().getDuration());
        assertEquals(new Duration(2000l), mCalc.getCamera().getFrameDuration());
        assertEquals(600, mCalc.getCamera().getSumOfFrames());
        assertEquals(new Duration(1200000), mCalc.getCamera().getDuration());

        mCalc.setVideoFrameRate(25.0, ValueForChange.CAMERA_RECORD_DURATION);
        assertEquals(new Duration(40l), mCalc.getVideo().getFrameDuration());
        assertEquals(300, mCalc.getVideo().getSumOfFrames());
        assertEquals(new Duration(12000), mCalc.getVideo().getDuration());
        assertEquals(new Duration(2000l), mCalc.getCamera().getFrameDuration());
        assertEquals(300, mCalc.getCamera().getSumOfFrames());
        assertEquals(new Duration(600000), mCalc.getCamera().getDuration());

        mCalc.setVideoFrameRate(50.0, ValueForChange.CAMERA_FRAME_DURATION);
        assertEquals(new Duration(20l), mCalc.getVideo().getFrameDuration());
        assertEquals(600, mCalc.getVideo().getSumOfFrames());
        assertEquals(new Duration(12000), mCalc.getVideo().getDuration());
        assertEquals(new Duration(1000l), mCalc.getCamera().getFrameDuration());
        assertEquals(600, mCalc.getCamera().getSumOfFrames());
        assertEquals(new Duration(600000), mCalc.getCamera().getDuration());
    }
}
