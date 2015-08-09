package scDomain.domain.valueObjects;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DomainDate implements Cloneable, Comparable<DomainDate> {
	protected GregorianCalendar date = null;
	protected GregorianCalendar actualDate = null;

	protected static GregorianCalendar dateOnly(Calendar date) {
		return new GregorianCalendar(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));
	}
	
	protected DomainDate() {}
	public DomainDate(DomainDate date) {
		this.date = date.getDateOnly();
		this.actualDate = date.getDatetime();
	}
	public DomainDate(Calendar date) {
		this.date = dateOnly(date);
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
		this.date = dateOnly(date);
		actualDate = dateOnly(date);
		actualDate.add(Calendar.HOUR, hour);
		actualDate.add(Calendar.MINUTE, minute);
	}
	public DomainDate(Calendar date, int hour) {
		this(date, hour, 0);
	}
	
	public GregorianCalendar getDateOnly() { return (GregorianCalendar) date.clone(); }
	public GregorianCalendar getDatetime() { return (GregorianCalendar) actualDate.clone(); }
	public byte getHour() { return (byte)actualDate.get(Calendar.HOUR_OF_DAY); }
	public byte getMinute() { return (byte)actualDate.get(Calendar.MINUTE); }
	
	public boolean equals(Object object) {
		if (object == null || !(object instanceof DomainDate)) { return false; }
		
		DomainDate date = (DomainDate) object;
		
		return this.actualDate.equals(date.actualDate);
	}
	public int compareTo(Calendar date) {
		return actualDate.compareTo(date);
	}
	public int compareTo(DomainDate date) {
		return this.actualDate.compareTo(date.actualDate);
	}
	public DomainDate clone() {
		return new DomainDate(this);
	}
}