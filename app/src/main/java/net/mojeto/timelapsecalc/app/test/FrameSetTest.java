package net.mojeto.timelapsecalc.app.test;

import junit.framework.TestCase;

import net.mojeto.timelapsecalc.app.Duration;
import net.mojeto.timelapsecalc.app.FrameSet;

/**
 * Created by honza on 24.6.14.
 */
public class FrameSetTest extends TestCase {

    public void testFrameDuration() {
        FrameSet fr = new FrameSet(40, 1000);
        assertEquals(new Duration(40), fr.getFrameDuration());

        fr.setFrameDuration(new Duration(50.3));
        assertEquals(new Duration(50.3), fr.getFrameDuration());

        fr.setFrameRate(29.9);
        assertEquals(new Duration(1000/29.9), fr.getFrameDuration());

        fr.setDuration(new Duration(50000), FrameSet.Change.FRAME_DURATION);
        // duration/sum of frames = 50000/1000 = duration(50)
        assertEquals(new Duration(50), fr.getFrameDuration());
        assertEquals(new Duration(50000), fr.getDuration());
    }

    public void testFrameRate() {
        FrameSet fr = new FrameSet(40, 1000);
        assertEquals(25.0, fr.getFrameRate());

        fr.setFrameRate(23.4);
        assertEquals(23.4, fr.getFrameRate());
        // sum of frames/frame rate = 1000/23.4
        assertEquals(new Duration(1000/23.4), fr.getFrameDuration());

        fr.setFrameDuration(new Duration(50));
        // 1000ms/frame duration = frames per second = 1000/50 = 20
        assertEquals(20.0, fr.getFrameRate());

        fr.setDuration(new Duration(50000), FrameSet.Change.FRAME_DURATION);
        // duration/sum of frames = 50000/1000 = duration(50)
        assertEquals(new Duration(50), fr.getFrameDuration());

    }

    public void testDuration() {
        FrameSet fr = new FrameSet(40, 1000);
        assertEquals(new Duration(40000), fr.getDuration());

        assertEquals(1000, fr.getSumOfFrames());
        assertEquals(new Duration(40), fr.getFrameDuration());
        fr.setDuration(new Duration(50000), FrameSet.Change.SUM_OF_FRAMES);
        assertEquals(1250, fr.getSumOfFrames());
        assertEquals(new Duration(40), fr.getFrameDuration());

    }

}
