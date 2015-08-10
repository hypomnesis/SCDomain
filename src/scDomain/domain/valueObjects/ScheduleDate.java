package scDomain.domain.valueObjects;

import java.util.Calendar;
import java.util.GregorianCalendar;

@SuppressWarnings("EqualsAndHashcode")
//Needs own implementation of enquals, but superclass has valid hashCode method.
public final class ScheduleDate extends DomainDate {
    private GregorianCalendar date = null;
    private ScheduleTime time = null;
    
    public static GregorianCalendar calculateActualDate(Calendar date, ScheduleTime time) {
        GregorianCalendar newDate = dateOnly(date);

        newDate.add(Calendar.HOUR, time.getRelativeHour());

        if (time.getHalfHour()) { newDate.add(Calendar.MINUTE, 30); }

        return newDate;
    }

    @SuppressWarnings("unused")
    private ScheduleDate() {}
    public ScheduleDate(DomainDate date) {
        this(date.actualDate);
    }
    public ScheduleDate(ScheduleDate date) {
        this.date = date.getDateOnly();
        this.time = date.getScheduleTime();
        this.actualDate = date.getDatetime();
    }
    public ScheduleDate(Calendar date) {
        this.date = dateOnly(date);
        
        byte hour = (byte)date.get(Calendar.HOUR);
        int minute = date.get(Calendar.MINUTE);
        
        if (minute >= 45) {
            hour++;
            minute = 0;
        }
        
        time = new ScheduleTime(hour, (minute >= 15));
    }
    public ScheduleDate(Calendar date, ScheduleTime time) {
        this.date = dateOnly(date);
        this.time = time.clone();
    }
    public ScheduleDate(DomainDate date, ScheduleTime time) {
        this.date = date.getDateOnly();
        this.time = time.clone();
    }
    public ScheduleDate(Calendar date, int hour, boolean halfHour) {
        this.date = dateOnly(date);
        this.time = new ScheduleTime(hour, halfHour);
    }
    public ScheduleDate(DomainDate date, int hour, boolean halfHour) {
        this.date = date.getDateOnly();
        this.time = new ScheduleTime(hour, halfHour);
    }
    public ScheduleDate(Calendar date, int hour, int minute) {
        this.date = dateOnly(date);
        this.time = new ScheduleTime(hour, minute);
    }
    public ScheduleDate(DomainDate date, int hour, int minute) {
        this.date = date.getDateOnly();
        this.time = new ScheduleTime(hour, minute);
    }
    public ScheduleDate(Calendar date, int hour) {
        this.date = dateOnly(date);
        this.time = new ScheduleTime(hour);
    }
    public ScheduleDate(DomainDate date, int hour) {
        this.date = date.getDateOnly();
        this.time = new ScheduleTime(hour);
    }

    @Override
    public GregorianCalendar getDatetime() {
        if (actualDate == null) { actualDate = calculateActualDate(date, time); }
        
        return super.getDatetime();
    }
    @Override
    public byte getHour() { return time.getHourOfDay(); }
    @Override
    public byte getMinute() { return (byte)( time.getHalfHour() ? 30 : 0 ); }
    @Override
    public ScheduleTime getScheduleTime() { return time.clone(); }
    @Override
    public byte getRelativeHour() { return time.getRelativeHour(); }
    @Override
    public boolean getHalfHour() { return time.getHalfHour(); }
    
    @Override
    public boolean equals(Object object) {
        if (object == null || !(object instanceof DomainDate)) { return false; }
        
        if (object instanceof ScheduleDate) {
            ScheduleDate other = (ScheduleDate) object;
            
            return this.date.equals(other.date) && this.time.equals(other.time);
        } else {
            DomainDate other = (DomainDate) object;
            
            return this.actualDate.equals(other.actualDate);
        }
    }
    @Override
    public int compareTo(Calendar date) {
        return super.compareTo(date);
    }
    @Override
    public int compareTo(DomainDate date) {
        return super.compareTo(date);
    }
    public int compareTo(ScheduleDate date) {
        if (this.date.equals(date.date)) {
                return this.time.compareTo(date.time);
        } else {
            return this.date.compareTo(date.date);
        }
    }
    @Override
    @SuppressWarnings("CloneDoesntCallSuperClone")
    public ScheduleDate clone() {
        return new ScheduleDate(this);
    }
}