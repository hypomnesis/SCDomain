package scDomain.domain.valueObjects;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DomainDate implements Comparable<DomainDate> {    
    private final GregorianCalendar actualDate;
    private int hashCode;
    
    protected static GregorianCalendar dateOnly(Calendar date) {
        return new GregorianCalendar(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));
    }
    
    private DomainDate() { throw new NullPointerException(); }
    //public DomainDate(DomainDate date) { this.actualDate = date.getDatetime(); }
    public DomainDate(Calendar date) {
        if (date == null) { throw new NullPointerException(); }
        this.actualDate = new GregorianCalendar(date.get(Calendar.YEAR),
                date.get(Calendar.MONTH),
                date.get(Calendar.DAY_OF_MONTH),
                date.get(Calendar.HOUR_OF_DAY),
                date.get(Calendar.MINUTE));
    }
    public DomainDate(Calendar date, int hour, int minute) {
        if (date == null) { throw new NullPointerException(); }
        actualDate = dateOnly(date);
        actualDate.add(Calendar.HOUR, hour);
        actualDate.add(Calendar.MINUTE, minute);
    }
    public DomainDate(DomainDate date, int hour, int minute) {
        if (date == null) { throw new NullPointerException(); }
        actualDate = date.getDateOnly();
        actualDate.add(Calendar.HOUR, hour);
        actualDate.add(Calendar.MINUTE, minute);
    }
    //test
    public DomainDate(Calendar date, ScheduleTime time) {
        this(date, time.getHourOfDay(), time.getMinute());
    }
    public DomainDate(DomainDate date, ScheduleTime time) {
        this(date.actualDate, time.getHourOfDay(), time.getMinute());
    }
    public DomainDate(Calendar date, int hour) { this(date, hour, 0); }
    public DomainDate(DomainDate date, int hour) { this(date.actualDate, hour); }

    public final GregorianCalendar getDateOnly() {
        return new GregorianCalendar(actualDate.get(Calendar.YEAR),
                actualDate.get(Calendar.MONTH),
                actualDate.get(Calendar.DAY_OF_MONTH));
    }
    public final GregorianCalendar getDatetime() { return (GregorianCalendar) actualDate.clone(); }
    public final int getHour() { return actualDate.get(Calendar.HOUR_OF_DAY); }
    public final int getMinute() { return actualDate.get(Calendar.MINUTE); }
    public final long getTimeInMillis() { return actualDate.getTimeInMillis(); }

    @Override
    public boolean equals(Object object) {
        if (object == null || !(object instanceof DomainDate)) { return false; }
        
        if (object instanceof DomainDate) {
            return this.actualDate.equals(((DomainDate)object).actualDate);
        } else if (object instanceof Calendar) {
            return this.actualDate.equals(object);
        } else {
            return false;
        }
    }
    @Override
    public int hashCode() {
        if (hashCode == 0) { hashCode = actualDate.hashCode(); }
        
        return hashCode;
    }
    public int compareTo(Calendar date) {
        if (date == null) { throw new NullPointerException(); }
        
        return actualDate.compareTo(date); }
    @Override
    public int compareTo(DomainDate date) {
        if (date == null) { throw new NullPointerException(); }
        
        return this.actualDate.compareTo(date.actualDate);
    }
}