package net.mojeto.timelapsecalc.app;

//import net.mojeto.timelapsecalc.app.fragments.FrameRateFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by honza on 21.6.14.
 */
public enum ValueForChange
{

    CAMERA_FRAME_DURATION(0),
    CAMERA_RECORD_DURATION(1),
    VIDEO_FRAME_RATE(2),
    VIDEO_DURATION(3),
    CAMERA_FRAMES(4),
    VIDEO_FRAMES(5);


    private static Map<Integer, ValueForChange> enumMap = new HashMap<Integer,ValueForChange>(6);
    private int code;

    static {
        for (ValueForChange value: ValueForChange.values() ) {
            enumMap.put(value.getCode(), value);
        }
    }

    public static ValueForChange getInstanceForCode(int code) {
        return enumMap.get(code);
    }

    private ValueForChange(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
