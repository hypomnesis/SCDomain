package scDomain.domain.valueObjects;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DomainDate implements Comparable<DomainDate>, Cloneable {
    public static enum DateRangePart {
        DAY (Calendar.DAY_OF_MONTH),
        HOUR (Calendar.HOUR_OF_DAY),
        MINUTE (Calendar.MINUTE);
        
        public final long MILLISECONDS;
        public final int CALENDAR_CONSTANT;
        
        DateRangePart(int calendarConstant) {
            CALENDAR_CONSTANT = calendarConstant;
            
            long localMilli = 1000;
            
            switch (this) {
                case DAY:
                    localMilli *= 24;
                case HOUR:
                    localMilli *= 60;
                default:
                    localMilli *= 60;
                    this.MILLISECONDS = localMilli;
            }
        }
        public long getDifference(Calendar start, Calendar end) {
            return (end.getTimeInMillis() - start.getTimeInMillis()) / this.MILLISECONDS;
        }
        public long getDifference(DomainDate start, Calendar end) {
            return getDifference(start.actualDate, end);
        }
        public long getDifference(Calendar start, DomainDate end) {
            return getDifference(start, end.actualDate);
        }
        public long getDifference(DomainDate start, DomainDate end) {
            return getDifference(start.actualDate, end.actualDate);
        }
    }
    
    protected GregorianCalendar actualDate = null;
    protected int hashCode;
    
    protected static GregorianCalendar dateOnly(Calendar date) {
        return new GregorianCalendar(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));
    }

    protected DomainDate() {}
    public DomainDate(DomainDate date) {
        this.actualDate = date.getDatetime();
    }
    public DomainDate(Calendar date) {
        this.actualDate = new GregorianCalendar(date.get(Calendar.YEAR),
                date.get(Calendar.MONTH),
                date.get(Calendar.DAY_OF_MONTH),
                date.get(Calendar.HOUR_OF_DAY),
                date.get(Calendar.MINUTE));
    }
    public DomainDate(Calendar date, ScheduleTime time) {
        this(date, time.getHourOfDay(), time.getMinute());
    }
    public DomainDate(Calendar date, int hour, boolean halfHour) {
        this(date, hour, ( halfHour ? 30 : 0 ));
    }
    public DomainDate(Calendar date, int hour, int minute) {
        actualDate = dateOnly(date);
        actualDate.add(Calendar.HOUR, hour);
        actualDate.add(Calendar.MINUTE, minute);
    }
    public DomainDate(Calendar date, int hour) {
        this(date, hour, 0);
    }

    public GregorianCalendar getDateOnly() {
        return new GregorianCalendar(actualDate.get(Calendar.YEAR), actualDate.get(Calendar.MONTH), actualDate.get(Calendar.DAY_OF_MONTH));
    }
    public GregorianCalendar getDatetime() { return (GregorianCalendar) actualDate.clone(); }
    public byte getHour() { return (byte)actualDate.get(Calendar.HOUR_OF_DAY); }
    public byte getMinute() { return (byte)actualDate.get(Calendar.MINUTE); }
    public ScheduleTime getScheduleTime() { return new ScheduleTime(getHour(), getMinute()); }
    public byte getRelativeHour() { return (byte) (getHour() + ( getMinute() >= 45 ? 1 : 0 )); }
    public boolean getHalfHour() {
        int minute = getMinute();

        return  (minute >= 15 && minute < 45);
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || !(object instanceof DomainDate)) { return false; }

        DomainDate date = (DomainDate) object;

        return this.actualDate.equals(date.actualDate);
    }
    @Override
    public int hashCode() {
    if (hashCode == 0) { hashCode = actualDate.hashCode(); }

    return hashCode;
    }
    public int compareTo(Calendar date) {
        return actualDate.compareTo(date);
    }
    @Override
    public int compareTo(DomainDate date) {
        return this.actualDate.compareTo(date.actualDate);
    }
    @Override
    @SuppressWarnings("CloneDoesntCallSuperClone")
    public DomainDate clone() {
        return new DomainDate(this);
    }
}