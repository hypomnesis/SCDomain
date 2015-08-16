/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scDomain.domain.valueObjects;

/**
 *
 * @author Morgan
 */
import java.util.Calendar;

public final class DateRange<D extends DomainDate> implements Comparable<DateRange> {
    private final D start;
    private final D end;
    
    private DateRange() { throw new IllegalAccessError(); }
    /*Immutable
    public DateRange(DateRange range) {
        this.start = new ScheduleDate(range.start);
        this.end = new ScheduleDate(range.end);
    }*/
    public DateRange(D start, D end) {
        this.start = start;
        this.end = end;
    }
    /*public DateRange(DomainDate date, ScheduleTime start, ScheduleTime end) {
        this.start = new ScheduleDate(date.getDateOnly(), start);
        this.end = new ScheduleDate(date.getDateOnly(), end);
    }
    public DateRange(Calendar date, ScheduleTime start, ScheduleTime end) {
            this.start = new ScheduleDate(date, start);
            this.end = new ScheduleDate(date, end);
    }*/
    public D getStart() { return start; }
    public D getEnd() { return end; }
    
    public long dateDifference(Part part) {
        return part.getDifference(start, end);
    }
    public Calendar getStartCalendar() { return (Calendar) start.getDatetime().clone(); }
    public Calendar getEndCalendar() { return (Calendar) end.getDatetime().clone(); }
    
    @Override
    public int compareTo(DateRange range) {
        int compare = this.start.compareTo(range.start);
        
        if (compare == 0) { compare = this.end.compareTo(range.end); }
        
        return compare;
    }
    
    public static enum Part {
        DAY (Calendar.DAY_OF_MONTH),
        HOUR (Calendar.HOUR_OF_DAY),
        MINUTE (Calendar.MINUTE);
        
        public final long MILLISECONDS;
        public final int CALENDAR_CONSTANT;
        
        Part(int calendarConstant) {
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
            return (end.getTimeInMillis() - start.getTimeInMillis()) / this.MILLISECONDS;
        }
        public long getDifference(Calendar start, DomainDate end) {
            return (end.getTimeInMillis() - start.getTimeInMillis()) / this.MILLISECONDS;
        }
        public long getDifference(DomainDate start, DomainDate end) {
            return (end.getTimeInMillis() - start.getTimeInMillis()) / this.MILLISECONDS;
        }
    }
}