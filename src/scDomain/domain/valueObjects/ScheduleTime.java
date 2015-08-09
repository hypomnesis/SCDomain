package scDomain.domain.valueObjects;

public final class ScheduleTime implements Cloneable, Comparable<ScheduleTime> {
	private byte hour;
	private boolean halfHour;
	private byte hourOfDay;
	
	public static byte calculateHourOfDay(int hour, int minute) {
		int hourOfDay = hour % 24;
		
		if (hourOfDay < 0) { hourOfDay = hour - hourOfDay; }
		
		return (byte) hourOfDay;
	}
	
	@SuppressWarnings("unused")
	private ScheduleTime() {}
	public ScheduleTime(ScheduleTime time) {
		this.hour = time.hour;
		this.halfHour = time.halfHour;
		this.hourOfDay = time.hourOfDay;
	}
	public ScheduleTime(int hour, boolean halfHour) {
		this.hour = (byte) hour;
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
		
		this.hour = (byte) hour;
		halfHour = (minute >= 15);
		hourOfDay = calculateHourOfDay(hour, ( halfHour ? 30 : 0 ));
	}
	public ScheduleTime(int hour) {
		this(hour, 0);
	}
	
	public byte getRelativeHour() { return hour; }
	public byte getHourOfDay() { return hourOfDay; }
	public boolean getHalfHour() { return halfHour; }
	public byte getMinute() { return (byte) ( halfHour ? 30 : 0 );
	
	}
	protected String baseTimeString() {
		return hourOfDay + ":" + ( halfHour ? "30" : "00" );
	}
	
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
			builder.append(dayCount + " Days " + ( hour > 0 ? "Ahead" : "Ago" ));
		}
		
		builder.append(')');
		
		return builder.toString();
	}
	
	public boolean equals(Object object) {
		if (object.getClass() != ScheduleTime.class) { return false; }
		
		return (compareTo((ScheduleTime) object) == 0);
	}
	
	public ScheduleTime clone() {
		return new ScheduleTime(this);
	}
	
	public int compareTo(ScheduleTime other) {
		if (hour == other.getRelativeHour() && halfHour == other.getHalfHour()) {
			return 0;
		} else if (hour == other.hour) {
			return ( halfHour ? 1 : -1 );
		} else {
			return hour - other.hour;
		}
	}
}