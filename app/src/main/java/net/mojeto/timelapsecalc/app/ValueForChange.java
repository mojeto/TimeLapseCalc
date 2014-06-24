package net.mojeto.timelapsecalc.app;

//import net.mojeto.timelapsecalc.app.fragments.FrameRateFragment;

/**
 * Created by honza on 21.6.14.
 */
public enum ValueForChange
{
    CAMERA_FRAME_DURATION(0, null),
    CAMERA_RECORD_DURATION(1, null),
    CAMERA_FRAMES(2, null),
    VIDEO_FRAME_RATE(3, null),
    VIDEO_DURATION(4, null),
    VIDEO_FRAMES(5, null);

    private int code;
    private java.lang.Class cls;

    private ValueForChange(int code, java.lang.Class<?> cls) {
        this.code = code;
        this.cls = cls;
    }

    private ValueForChange(int code) {
        this(code, null);
    }

    public int getCode() {
        return code;
    }

    public java.lang.Class getCls() {
        return cls;
    }

    public Object getClsInstance() {
        try {
            return getCls().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
