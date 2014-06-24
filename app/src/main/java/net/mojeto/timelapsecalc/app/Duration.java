package net.mojeto.timelapsecalc.app;

import android.content.res.Resources;

/**
 * Created by honza on 20.6.14.
 */
public class Duration {

    public enum Format {

        WITH_DAYS(R.string.duration_with_days), WITH_HOURS(R.string.duration_with_hours);

        private int code;

        private Format(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    private final double value; //time duration in milliseconds

    public Duration() {
        this(0);
    }

    public Duration(int duration) {
        this((double) duration);
    }

    public Duration(long duration) {
        this((double) duration);
    }

    public Duration(double duration) {
        value = duration;
    }

    public double getValue() {
        return value;
    }

    public double getMicroseconds() {
        return (value % 1) * 1000;
    }

    public int getMilliseconds() {
        return (int) getMilliseconds(false);
    }

    public long getMilliseconds(boolean all) {
        if (!all) {
            return (long) value % 1000;
        }
        return (long) value;
    }

    public Duration addMilliseconds(int milliseconds) {
        return new Duration(value + milliseconds);
    }

    public Duration setMilliseconds(int milliseconds) {
        return addMilliseconds(milliseconds - getMilliseconds());
    }

    public int getSeconds() {
        return (int) getSeconds(false);
    }

    public long getSeconds(boolean all) {
        double sec = value / 1000;
        if (!all) {
            sec = sec % 60;
        }
        return (long) sec;
    }

    public Duration setSeconds(int seconds) {
        return new Duration(value + (seconds - getSeconds()) * 1000l);
    }

    public int getMinutes() {
        return (int) getMinutes(false);
    }

    public long getMinutes(boolean all) {
        double min = value / 60000;
        if (!all) {
            min = min % 60;
        }
        return (long) min;
    }

    public Duration setMinutes(int minutes) {
        return new Duration(value + (minutes - getMinutes()) * 60000l);
    }

    public int getHours() {
        return (int) getHours(false);
    }

    public long getHours(boolean all) {
        double hours = value / 3600000;
        if (!all) {
            hours = hours % 24;
        }
        return (long) hours;
    }

    public Duration setHours(int hours) {
        return new Duration(value + (hours - getHours()) * 3600000l);
    }

    public int getDays() {
        return (int) getDays(false);
    }

    public long getDays(boolean all) {
        double days = value / 86400000;
        if (!all) {
            days = days % 365;
        }
        return (long) days;
    }

    public Duration setDays(int days) {
        return new Duration(value + (days - getDays()) * 86400000l);
    }

    public int getYears() {
        return (int) (value / 31600000000l);
    }

    public Duration setYears(int years) {
        return new Duration(value + (years - getYears()) * 31600000000l);
    }


    public String format(Resources r, Format format) {
        switch (format) {
            case WITH_HOURS:
                return r.getString(format.getCode(), getHours(true), getMinutes(), getSeconds(),
                        getMilliseconds());
            case WITH_DAYS:
            default:
                return r.getString(format.getCode(), getDays(true), getHours(), getMinutes(),
                        getSeconds(), getMilliseconds());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o == this) return true;
        if (!(o instanceof Duration))return false;
        return this.getValue() == ((Duration)o).getValue();
    }

    /*@Override
    public String toString() {
        return super.toString();
        //%1$d days %2$02d:%3$02d:%4$02d:%5$03d
    }*/

    @Override
    public String toString() {
        return String.format("Duration{" +
                "value=" + value +
                "}=%1$d days %2$02d:%3$02d:%4$02d:%5$03d " + getMicroseconds(), getDays(true), getHours(), getMinutes(),
                getSeconds(), getMilliseconds());
    }
}
