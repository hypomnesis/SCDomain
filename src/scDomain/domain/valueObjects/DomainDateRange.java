package scDomain.domain.valueObjects;

import java.util.Calendar;

public class DomainDateRange {
	DomainDate start = null;
	DomainDate end = null;

	@SuppressWarnings("unused")
	private DomainDateRange() {}
	public DomainDateRange(DomainDate start, DomainDate end) {
		this.start = start.clone();
		this.end = end.clone();
	}
	public DomainDateRange(Calendar start, DomainDate end) {
		this.start = new DomainDate(start);
		this.end = end.clone();
	}
	public DomainDateRange(DomainDate start, Calendar end) {
		this.start = start.clone();
		this.end = new DomainDate(end);
	}
	public DomainDateRange(DomainDate date, ScheduleTime start, ScheduleTime end) {
		this.start = new ScheduleDate(date.getDateOnly(), start);
		this.end = new ScheduleDate(date.getDateOnly(), end);
	}
	public DomainDateRange(Calendar date, ScheduleTime start, ScheduleTime end) {
		this.start = new ScheduleDate(date, start);
		this.end = new ScheduleDate(date, end);
	}
	public DomainDateRange(DomainDate date, int startHour, int startMinute, int endHour, int endMinute) {
		this.start = new ScheduleDate(date.getDateOnly(), new ScheduleTime (startHour, startMinute));
		this.end = new ScheduleDate(date.getDateOnly(), new ScheduleTime (endHour, endMinute));
	}
	public DomainDateRange(Calendar date, int startHour, int startMinute, int endHour, int endMinute) {
		this.start = new ScheduleDate(date, new ScheduleTime (startHour, startMinute));
		this.end = new ScheduleDate(date, new ScheduleTime (endHour, endMinute));
	}
	public DomainDateRange(DomainDate date, int hour, int startMinute, int endMinute) {
		this.start = new ScheduleDate(date.getDateOnly(), new ScheduleTime (hour, startMinute));
		this.end = new ScheduleDate(date.getDateOnly(), new ScheduleTime (hour, endMinute));
	}
	public DomainDateRange(Calendar date, int hour, int startMinute, int endMinute) {
		this.start = new ScheduleDate(date, new ScheduleTime (hour, startMinute));
		this.end = new ScheduleDate(date, new ScheduleTime (hour, endMinute));
	}
	public DomainDateRange(DomainDate date, int startHour, int endHour) {
		this.start = new ScheduleDate(date.getDateOnly(), new ScheduleTime (startHour));
		this.end = new ScheduleDate(date.getDateOnly(), new ScheduleTime (endHour));
	}
	public DomainDateRange(Calendar date, int startHour, int endHour) {
		this.start = new ScheduleDate(date, new ScheduleTime (startHour));
		this.end = new ScheduleDate(date, new ScheduleTime (endHour));
	}
}