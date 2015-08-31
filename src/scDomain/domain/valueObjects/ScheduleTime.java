package scDomain.domain.valueObjects;

import java.math.BigDecimal;

public final class ScheduleTime implements Comparable<ScheduleTime> {
    private int hour;
    private boolean halfHour;
    private int hourOfDay;
    
    public static int calculateHourOfDay(int hour, int minute) {
        int hourOfDay = (hour + (minute / 60)) % 24;
        
        if (hourOfDay < 0) { hourOfDay = hour - hourOfDay; }
        
        return hourOfDay;
    }

    @SuppressWarnings("unused")
    private ScheduleTime() {}
    /*Immutable -- I don't think I need this.
    public ScheduleTime(ScheduleTime time) {
        this.hour = time.hour;
        this.halfHour = time.halfHour;
        this.hourOfDay = time.hourOfDay;
    }*/
    public ScheduleTime(int hour, boolean halfHour) {
        this.hour = hour;
        this.halfHour = halfHour;
        hourOfDay = calculateHourOfDay(hour, ( halfHour ? 30 : 0 ));
    }
    public ScheduleTime(int hour, int minute) {
        //rolling minute overflow values into hour
        hour += minute / 60;
        minute = minute % 60;
        
        if (minute < 0) {
            minute = 60 - minute;
            hour--;
        }
        //rounding
        if (minute >= 45) {
            hour++;
            minute = 0;
        }
        this.hour = hour;
        halfHour = (minute >= 15);
        hourOfDay = calculateHourOfDay(hour, ( halfHour ? 30 : 0 ));
    }
    public ScheduleTime(int hour) { this(hour, 0); }
    public ScheduleTime(float hour) {
        this((int)hour, (int)((hour - (int)hour) * 60));
    }
    public ScheduleTime(BigDecimal hour) { this(hour.floatValue()); }
    
    public int getRelativeHour() { return hour; }
    public int getHourOfDay() { return hourOfDay; }
    public boolean getHalfHour() { return halfHour; }
    public int getMinute() { return ( halfHour ? 30 : 0 ); }
    protected String baseTimeString() {
        return hourOfDay + ":" + ( halfHour ? "30" : "00" );
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(baseTimeString() + " (");
        int dayCount = hour / 24;
        
        if (dayCount == 0) {
            builder.append("Current Day");
        } else if (dayCount == 1) {
            builder.append("Next Day");
        } else if (dayCount == -1) {
            builder.append("Previous Day");
        } else {
            builder.append(dayCount).append(" Days ").append((hour > 0 ? "Ahead" : "Ago" ));
        }
        
        builder.append(')');
        
        return builder.toString();
    }
    @Override
    public boolean equals(Object object) {
        if (object == null || object.getClass() != ScheduleTime.class) { return false; }
        
        return (compareTo((ScheduleTime) object) == 0);
    }
    @Override
    public int hashCode() {
        return 31 * ((31 * 17) + hour) + (halfHour ? 30 : 00);
    }
    @Override
    public int compareTo(ScheduleTime time) {
        if (time == null) { throw new NullPointerException(); }
        
        if (hour == time.getRelativeHour() && halfHour == time.getHalfHour()) {
            return 0;
        } else if (hour == time.hour) {
            return ( halfHour ? 1 : -1 );
        } else {
            return hour - time.hour;
        }
    }
}