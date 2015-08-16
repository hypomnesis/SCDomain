package scDomain.domain.valueObjects;

import java.util.Calendar;
import java.util.GregorianCalendar;

//Needs own implementation of equals, but superclass has valid hashCode method.
public final class ScheduleDate extends DomainDate {
    private final GregorianCalendar date;
    private final ScheduleTime time;
    
    public static GregorianCalendar calculateActualDate(Calendar date, ScheduleTime time) {
        GregorianCalendar newDate = dateOnly(date);

        newDate.add(Calendar.HOUR, time.getRelativeHour());

        if (time.getHalfHour()) { newDate.add(Calendar.MINUTE, 30); }

        return newDate;
    }
    
    public ScheduleDate(DomainDate date) { this(date.getDatetime()); }
    /*Immutable.
    public ScheduleDate(ScheduleDate date) {
        this.date = date.getDateOnly();
        this.time = date.getScheduleTime();
        this.actualDate = date.getDatetime();
    }*/
    public ScheduleDate(Calendar date) {
        super(date);
        this.date = dateOnly(date);
        time = new ScheduleTime(date.get(Calendar.HOUR), date.get(Calendar.MINUTE));
    }
    public ScheduleDate(Calendar date, ScheduleTime time) {
        super(date, time);
        this.date = dateOnly(date);
        this.time = time;
    }
    public ScheduleDate(DomainDate date, ScheduleTime time) {
        super(date, time);
        this.date = date.getDateOnly();
        this.time = time;
    }
    public ScheduleDate(Calendar date, int hour, int minute) {
        super(date, hour, minute);
        this.date = dateOnly(date);
        this.time = new ScheduleTime(hour, minute);
    }
    public ScheduleDate(DomainDate date, int hour, int minute) {
        super(date, hour, minute);
        this.date = date.getDateOnly();
        this.time = new ScheduleTime(hour, minute);
    }
    public ScheduleDate(Calendar date, int hour, boolean halfHour) {
        super(date, hour, (halfHour ? 30 : 0));
        this.date = dateOnly(date);
        this.time = new ScheduleTime(hour, halfHour);
    }
    public ScheduleDate(DomainDate date, int hour, boolean halfHour) {
        super(date, hour, (halfHour ? 30 : 0));
        this.date = date.getDateOnly();
        this.time = new ScheduleTime(hour, halfHour);
    }
    public ScheduleDate(Calendar date, int hour) {
        super(date, hour);
        this.date = dateOnly(date);
        this.time = new ScheduleTime(hour);
    }
    public ScheduleDate(DomainDate date, int hour) {
        super(date, hour);
        this.date = date.getDateOnly();
        this.time = new ScheduleTime(hour);
    }
    
    public ScheduleTime getScheduleTime() { return time; }
    public Calendar getRelativeCalendar() { return (GregorianCalendar)date.clone(); }
    public int getRelativeHour() { return time.getRelativeHour(); }
    public boolean getHalfHour() { return time.getHalfHour(); }
    
    @Override
    public boolean equals(Object object) {
        if (!super.equals(object)) { return false; }
        
        if (object instanceof ScheduleDate) {
            ScheduleDate other = (ScheduleDate) object;
            
            return this.date.equals(other.date) && this.time.equals(other.time);
        } else {
            //Know from super that it is a DomainDate and the actualTime property matches.
            return true;
        }
    }
    @Override
    public int hashCode() {
        return 31 * ((31 * 17) + date.hashCode()) + time.hashCode();
    }
    /*@Override
    public int compareTo(Calendar date) {
        return super.compareTo(date);
    }*/
    @Override
    public int compareTo(DomainDate date) {
        int compare;
        
        if (date == null) { throw new NullPointerException(); }
        if (date instanceof ScheduleDate) {
            ScheduleDate other = (ScheduleDate)date;
            
            compare = this.date.compareTo(other.date);
            
            if (compare == 0) { compare = this.time.compareTo(other.time); }
        } else {
            compare = super.compareTo(date);
        }
        return compare;
    }
}