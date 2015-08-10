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

final class DateRange<K extends DomainDate> {
    private K start = null;
    private K end = null;
    
    private DateRange() {}
    public DateRange(DomainDate start, DomainDate end) {
        if (ScheduleDate.class == this.start.getClass()) {
            this.start = (K) new ScheduleDate(start);
            this.end = (K) new ScheduleDate(end);
        } else {
            this.start = (K) new DomainDate(start);
            this.end = (K) new DomainDate(end);
        }
    }
    public DateRange(Calendar start, DomainDate end) {
        if (ScheduleDate.class == this.start.getClass()) {
            this.start = (K) new ScheduleDate(start);
            this.end = (K) new ScheduleDate(end);
        } else {
            this.start = (K) new DomainDate(start);
            this.end = (K) new DomainDate(end);
        }
    }
    public DateRange(DomainDate start, Calendar end) {
        if (ScheduleDate.class == this.start.getClass()) {
            this.start = (K) new ScheduleDate(start);
            this.end = (K) new ScheduleDate(end);
        } else {
            this.start = (K) new DomainDate(start);
            this.end = (K) new DomainDate(end);
        }
    }
    public DateRange(Calendar start, Calendar end) {
        if (ScheduleDate.class == this.start.getClass()) {
            this.start = (K) new ScheduleDate(start);
            this.end = (K) new ScheduleDate(end);
        } else {
            this.start = (K) new DomainDate(start);
            this.end = (K) new DomainDate(end);
        }
    }
    public DateRange(DomainDate date, ScheduleTime start, ScheduleTime end) {
        this.start = (K) new ScheduleDate(date.getDateOnly(), start);
        this.end = (K) new ScheduleDate(date.getDateOnly(), end);
    }
    public DateRange(Calendar date, ScheduleTime start, ScheduleTime end) {
            this.start = (K) new ScheduleDate(date, start);
            this.end = (K) new ScheduleDate(date, end);
    }
    public DateRange(DomainDate date, int startHour, int startMinute, int endHour, int endMinute) {
            this.start = (K) new ScheduleDate(date.getDateOnly(), new ScheduleTime (startHour, startMinute));
            this.end = (K) new ScheduleDate(date.getDateOnly(), new ScheduleTime (endHour, endMinute));
    }
    public DateRange(Calendar date, int startHour, int startMinute, int endHour, int endMinute) {
            this.start = (K) new ScheduleDate(date, new ScheduleTime (startHour, startMinute));
            this.end = (K) new ScheduleDate(date, new ScheduleTime (endHour, endMinute));
    }
    public DateRange(DomainDate date, int hour, int startMinute, int endMinute) {
            this.start = (K) new ScheduleDate(date.getDateOnly(), new ScheduleTime (hour, startMinute));
            this.end = (K) new ScheduleDate(date.getDateOnly(), new ScheduleTime (hour, endMinute));
    }
    public DateRange(Calendar date, int hour, int startMinute, int endMinute) {
            this.start = (K) new ScheduleDate(date, new ScheduleTime (hour, startMinute));
            this.end = (K) new ScheduleDate(date, new ScheduleTime (hour, endMinute));
    }
    public DateRange(DomainDate date, int startHour, int endHour) {
            this.start = (K) new ScheduleDate(date.getDateOnly(), new ScheduleTime (startHour));
            this.end = (K) new ScheduleDate(date.getDateOnly(), new ScheduleTime (endHour));
    }
    public DateRange(Calendar date, int startHour, int endHour) {
            this.start = (K) new ScheduleDate(date, new ScheduleTime (startHour));
            this.end = (K) new ScheduleDate(date, new ScheduleTime (endHour));
    }
    
    public long dateDifference(DomainDate.DateRangePart part) {
        return part.getDifference(start, end);
    }
    public Calendar getStartCalendar() { return (Calendar) start.actualDate.clone(); }
    public Calendar getEndCalendar() { return (Calendar) end.actualDate.clone(); }
    public K getStart() { return (K) start.clone(); }
    public K getEnd() { return (K) end.clone(); }
}