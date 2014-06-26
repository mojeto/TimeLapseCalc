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
        assertEquals(1000, fr.getSumOfFrames());
        assertEquals(new Duration(40000), fr.getDuration());

        fr.setFrameDuration(new Duration(50.3), FrameSet.Change.DURATION);
        assertEquals(new Duration(50.3), fr.getFrameDuration());
        assertEquals(1000, fr.getSumOfFrames());
        assertEquals(new Duration(50300), fr.getDuration());

        fr.setFrameDuration(new Duration(25.0), FrameSet.Change.SUM_OF_FRAMES);
        assertEquals(new Duration(25.0), fr.getFrameDuration());
        assertEquals(2012, fr.getSumOfFrames());
        assertEquals(new Duration(50300), fr.getDuration());
    }

    public void testFrameRate() {
        FrameSet fr = new FrameSet(40, 1000);
        assertEquals(25.0, fr.getFrameRate());

        fr.setFrameRate(20.0, FrameSet.Change.DURATION);
        assertEquals(20.0, fr.getFrameRate());
        assertEquals(1000, fr.getSumOfFrames());
        assertEquals(new Duration(50000), fr.getDuration());

        fr.setFrameRate(10.0, FrameSet.Change.SUM_OF_FRAMES);
        assertEquals(10.0, fr.getFrameRate());
        assertEquals(500, fr.getSumOfFrames());
        assertEquals(new Duration(50000), fr.getDuration());
    }

    public void testDuration() {
        FrameSet fr = new FrameSet(40, 1000);
        assertEquals(new Duration(40000), fr.getDuration());

        fr.setDuration(new Duration(50000), FrameSet.Change.FRAME_DURATION);
        assertEquals(new Duration(50000), fr.getDuration());
        assertEquals(1000, fr.getSumOfFrames());
        assertEquals(new Duration(50), fr.getFrameDuration());

        fr.setDuration(new Duration(30000), FrameSet.Change.SUM_OF_FRAMES);
        assertEquals(new Duration(30000), fr.getDuration());
        assertEquals(600, fr.getSumOfFrames());
        assertEquals(new Duration(50), fr.getFrameDuration());
    }

}
