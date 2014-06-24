package net.mojeto.timelapsecalc.app.test;

import junit.framework.TestCase;

import net.mojeto.timelapsecalc.app.Duration;

/**
 * Created by honza on 24.6.14.
 */
public class DurationTest extends TestCase {

    public void testMilliseconds() {
        Duration d1 = new Duration(4321);

        assertEquals(321, d1.getMilliseconds());
        assertEquals(4321, d1.getMilliseconds(true));

        Duration d2 = new Duration(34.43);

        assertEquals(34, d2.getMilliseconds());
        assertEquals(34, d2.getMilliseconds(true));
    }

    public void testSeconds() {
        Duration d1 = new Duration(1255345);

        assertEquals(55, d1.getSeconds());
        assertEquals(1255, d1.getSeconds(true));
    }

    public void testMinutes() {

        Duration d1 = new Duration(1255345);

        assertEquals(20, d1.getMinutes());
        assertEquals(20, d1.getMinutes(true));

        Duration d2 = new Duration(8455345);

        assertEquals(20, d2.getMinutes());
        assertEquals(140, d2.getMinutes(true));

    }

    public void testHours() {
        Duration d1 = new Duration(100800000);
        assertEquals(4, d1.getHours());
        assertEquals(28, d1.getHours(true));
    }

    public void testDays() {
        Duration d1 = new Duration(172800000);
        assertEquals(2, d1.getDays());
        assertEquals(2, d1.getDays(true));
        Duration d2 = new Duration(31772800000l);
        assertEquals(2, d2.getDays());
        assertEquals(367, d2.getDays(true));

    }

    public void testAll() {
        Duration d1 = new Duration(317728567293l);
        assertEquals(317728567293l, d1.getMilliseconds(true));
        assertEquals(293, d1.getMilliseconds());
        assertEquals(7, d1.getSeconds());
        assertEquals(56, d1.getMinutes());
        assertEquals(9, d1.getHours());
        assertEquals(27, d1.getDays());
        assertEquals(10, d1.getYears());


        Duration d2 = new Duration();
        d2 = d2.setMilliseconds(293);
        assertEquals(293, d2.getMilliseconds());

        d2 = d2.setSeconds(7);
        assertEquals(293, d2.getMilliseconds());
        assertEquals(7, d2.getSeconds());

        d2 = d2.setMinutes(56);
        assertEquals(293, d2.getMilliseconds());
        assertEquals(7, d2.getSeconds());
        assertEquals(56, d2.getMinutes());

        d2 = d2.setHours(9);
        assertEquals(293, d2.getMilliseconds());
        assertEquals(7, d2.getSeconds());
        assertEquals(56, d2.getMinutes());
        assertEquals(9, d2.getHours());

        d2 = d2.setDays(27);
        assertEquals(293, d2.getMilliseconds());
        assertEquals(7, d2.getSeconds());
        assertEquals(56, d2.getMinutes());
        assertEquals(9, d2.getHours());
        assertEquals(27, d2.getDays());

        //TODO change duration to work properly with years
//        d2 = d2.setYears(10);
//        assertEquals(317728567293.0, d2.getValue());
//        assertEquals(293, d2.getMilliseconds());
//        assertEquals(7, d2.getSeconds());
//        assertEquals(56, d2.getMinutes());
//        assertEquals(9, d2.getHours());
//        assertEquals(27, d2.getDays());
//        assertEquals(10, d2.getYears());
    }

}
