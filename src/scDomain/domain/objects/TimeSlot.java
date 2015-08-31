/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scDomain.domain.objects;

import scDomain.domain.valueObjects.*;

/**
 *
 * @author Morgan
 */
public final class TimeSlot extends AbstractDomainObject<TimeSlot> {
    //long or int for ids... probably int in my setup.
    private final long id;
    /*
     *Want to figure out to to pull this from type...
     *That's how it was originall designed, but that complicates
     *the fetching of objects from providers in the framework
     *I've designed...  Similar problem as storing roles in Agents.
     */
    private final Category.Key category;
    private final Type.Key type;
    private final ScheduleDate start;
    private final ScheduleDate end;
    private final Status status;
    private final DomainDate creationTime;
    private final Agent.Key createdBy;
    //Bid
    private final DomainDate assignTime;
    private final Agent.Key assignedBy;
    private final boolean confirmed;
    private final DomainDate confirmTime;
    private final DomainDate lastEmail;
    private final DomainDate lastConfirmEmail;
    private final boolean requireExactMatch;
    private final boolean isFragment;
    //Derived:
    private final DateRange<ScheduleDate> range;
    private final String description;
    
    //Going to try instantiating the variables in the builder...
    private TimeSlot(Builder builder) {
        super(builder);
            
        this.id = builder.id;
        this.category = builder.category;
        this.type = builder.type;
        this.start = builder.start;
        this.end = builder.end;
        this.status = builder.status;
        this.creationTime = builder.creationTime;
        this.createdBy = builder.createdBy;
        //Bid
        this.assignTime = builder.assignTime;
        this.assignedBy = builder.assignedBy;
        this.confirmed = builder.confirmed;
        this.confirmTime = builder.confirmTime;
        this.lastEmail = builder.lastEmail;
        this.lastConfirmEmail = builder.lastConfirmEmail;
        this.requireExactMatch = builder.requireExactMatch;
        this.isFragment = builder.isFragment;
        //Derived:
        range = new DateRange<>(start, end);
        description = "Slot #" + id + ": " + start.toString() + " - " + end.getScheduleTime().toString();
    }
    
    public long getId() { return id; }
    public Category.Key getCategory() { return category; }
    public Type.Key getType() { return type; }
    //I've made these immutable so I can pass them.
    public ScheduleDate getStart() { return start; }
    public ScheduleDate getEnd() { return end; }
    public DateRange getRange() { return range; }
    public Status getStatus() { return status; }
    public DomainDate getCreationTime() { return creationTime; }
    public Agent.Key getCreatedBy() { return createdBy; }
    public String getCreatedById() { return createdBy.id; }
    public DomainDate getAssignTime() { return assignTime; }
    public Agent.Key getAssignedBy() { return assignedBy; }
    public boolean isConfirmed() { return confirmed; }
    public DomainDate getConfirmTime() { return confirmTime; }
    public DomainDate getLastEmailed() { return lastEmail; }
    public DomainDate getLastConfirmationEmail() { return lastConfirmEmail; }
    public boolean getRequiresExactMatch() { return requireExactMatch; }
    public boolean isFragment() { return isFragment; }
    
    @Override
    public String toString() { return description; }
    
    public static class Key extends LongDomainKey<TimeSlot> {
        public Key(Long id) { super(id); }
        Key(TimeSlot.Builder builder) { 
            super(builder);
            if (builder.id<= 0) { throw new IllegalArgumentException(); }
            
            id = builder.id;
        }
        @Override
        public DomainObject.Type getDomainType() { return DomainObject.Type.TIME_SLOT; }
    }
    public static final class Builder extends AbstractDomainObject.Builder<TimeSlot> {
        private long id;
        private Category.Key category;
        private Type.Key type;
        private ScheduleDate start;
        private ScheduleDate end;
        private Status status;
        private DomainDate creationTime;
        private Agent.Key createdBy;
        //Bid
        private DomainDate assignTime;
        private Agent.Key assignedBy;
        private boolean confirmed;
        private DomainDate confirmTime;
        private DomainDate lastEmail;
        private DomainDate lastConfirmEmail;
        private boolean requireExactMatch;
        private boolean isFragment;
        
        @Override
        DomainObject.Type getDomainType() { return DomainObject.Type.TIME_SLOT; }
        @Override
        TimeSlot.Key getKey() { return new TimeSlot.Key(this); }
        @Override
        TimeSlot doGetObject() { return new TimeSlot(this); }
        
        @Override
        boolean isValid() {
            //How do I handle the key for new objects?  Difference between autoincrement and saving key...
            //Also, do I return false, or throw an exception telling what's wrong?
            //Required Fields:
            if (type == null ||
                    start == null ||
                    end == null ||
                    status == null ||
                    creationTime == null ||
                    createdBy == null) {
                return false;
            } else if (start.compareTo(end) >= 0) {
                return false;
            } else if (!((assignTime == null && assignedBy == null) || (assignTime != null && assignedBy != null))) {
                return false;
            } else if (confirmed && confirmTime == null) {
                return false;
            } else {
                return true;
            }
        }
        
        public Builder id(long id) { this.id = id; return this; }
        public Builder category(Category.Key category) { this.category = category; return this; }
        public Builder type(Type.Key type) { this.type = type; return this; }
        public Builder start(ScheduleDate start ) { this.start = start; return this; }
        public Builder end(ScheduleDate end) { this.end = end; return this; }
        public Builder status(Status status) { this.status = status; return this; }
        public Builder creationTime(DomainDate creationTime) { this.creationTime = creationTime; return this; }
        public Builder createdBy(Agent.Key createdBy) { this.createdBy = createdBy; return this; }
        //BID
        public Builder assignTime(DomainDate assignTime) { this.assignTime = assignTime; return this; }
        public Builder assignedBy(Agent.Key assignedBy) { this.assignedBy = assignedBy; return this; }
        public Builder confirmed(boolean confirmed) { this.confirmed = confirmed; return this; }
        public Builder confirmTime(DomainDate confirmTime) { this.confirmTime = confirmTime; return this; }
        public Builder lastEmail(DomainDate lastEmail) { this.lastEmail = lastEmail; return this; }
        public Builder lastConfirmEmail(DomainDate lastConfirmEmail) { this.lastConfirmEmail = lastConfirmEmail; return this; }
        public Builder requireExactMatch(boolean requireExactMatch) { this.requireExactMatch = requireExactMatch; return this; }
        public Builder isFragment(boolean isFragment) { this.isFragment = isFragment; return this; }
    }
    
    public static class Category extends AbstractDomainObject<Category> {
        public enum ScheduleType { INNER, OUTER; }
        
        private final int id;
        private final String description;
        private final ScheduleType scheduleType;
        private final int dayRange;
        private final int rankType;
        private final boolean isMandatory;
        private final int mandatoryRank;
        private final boolean requiresConfirmation;
        private final boolean canFragment;
        private final int allowedGap;
        //storing as float for now, want better method.
        private final float worktimeValue;
        private final float overtimeValue;
        
        private Category(Builder builder) {
            super(builder);
            
            id = builder.id;
            description = builder.description;
            scheduleType = builder.scheduleType;
            dayRange = builder.dayRange;
            rankType = builder.rankType;
            isMandatory = builder.isMandatory;
            mandatoryRank = builder.mandatoryRank;
            requiresConfirmation = builder.requiresConfirmation;
            canFragment = builder.canFragment;
            allowedGap = builder.allowedGap;
            worktimeValue = builder.worktimeValue;
            overtimeValue = builder.overtimeValue;
        }
        
        public int getId() { return id; }
        public String getDescription() { return description; }
        //TODO:  Verify that you can't change the Category objects variable after returning it.
        public ScheduleType getScheduleType() { return scheduleType; }
        public int getDayRange() { return dayRange; }
        public int getRankType() { return rankType; }
        public boolean isMandatory() { return isMandatory; }
        public int getMandatoryRank() { return mandatoryRank; }
        public boolean requiresConfirmation() { return requiresConfirmation; }
        public boolean canFragment() { return canFragment; }
        public int getAllowedGap() { return allowedGap; }
        public float getWorktimeValue() { return worktimeValue; }
        public float getOvertimeValue() { return overtimeValue; }
        
        @Override
        public String toString() { return description; }
        
        public static class Key extends IntegerDomainKey<TimeSlot.Category> {
            public Key(Integer id) { super(id); }
            Key(TimeSlot.Category.Builder builder) { 
                super(builder);
                if (builder.id<= 0) { throw new IllegalArgumentException(); }

                id = builder.id;
            }
            @Override
            public DomainObject.Type getDomainType() { return DomainObject.Type.TIME_SLOT_CATEGORY; }
        }
        
        public static final class Builder extends AbstractDomainObject.Builder<TimeSlot.Category> {
            private int id;
            private String description;
            private ScheduleType scheduleType;
            private int dayRange;
            private int rankType;
            private boolean isMandatory;
            private int mandatoryRank;
            private boolean requiresConfirmation;
            private boolean canFragment;
            private int allowedGap;
            //storing as float for now, want better method.
            private float worktimeValue;
            private float overtimeValue;

            @Override
            DomainObject.Type getDomainType() { return DomainObject.Type.TIME_SLOT_CATEGORY; }
            @Override
            TimeSlot.Category.Key getKey() { return new TimeSlot.Category.Key(this); }
            @Override
            TimeSlot.Category doGetObject() { return new TimeSlot.Category(this); }

            @Override
            boolean isValid() {
                //TODO OMFG
                return true;
            }

            public Builder id(int id) { this.id = id; return this; }
            public Builder description(String description) { this.description = description; return this; }
            public Builder scheduleType(TimeSlot.Category.ScheduleType scheduleType) {
                this.scheduleType = scheduleType; return this;
            }
            public Builder dayRange(int dayRange) { this.dayRange = dayRange; return this; }
            public Builder rankType(int rankType) { this.rankType = rankType; return this; }
            public Builder isMandatory(boolean isMandatory) { this.isMandatory = isMandatory; return this; }
            public Builder mandatoryRank(int mandatoryRank) { this.mandatoryRank = mandatoryRank; return this; }
            public Builder requiresConfirmation(boolean requiresConfirmation) {
                this.requiresConfirmation = requiresConfirmation;
                return this;
            }
            public Builder canFragment(boolean canFragment) { this.canFragment = canFragment; return this; }
            public Builder allowedGap(int allowedGap) { this.allowedGap = allowedGap; return this; }
            public Builder worktimeValue(float worktimeValue) { this.worktimeValue = worktimeValue; return this; }
            public Builder overtimeValue(float overtimeValue) { this.overtimeValue = overtimeValue; return this; }
        }
    }
    
    public static class Type extends AbstractDomainObject<Type> {
        private final int id;
        private final String description;
        private final Category.Key category;
        
        private Type(Builder builder) {
            super(builder);
            
            id = builder.id;
            description = builder.description;
            category = builder.category;
        }
        
        public int getId() { return id; }
        public String getDescription() { return description; }
        public Category.Key getCategory() { return category; }
        
        @Override
        public String toString() { return description; }
        
        public static class Key extends IntegerDomainKey<TimeSlot.Type> {
            public Key(Integer id) { super(id); }
            Key(TimeSlot.Type.Builder builder) { 
                super(builder);
                if (builder.id<= 0) { throw new IllegalArgumentException(); }

                id = builder.id;
            }
            @Override
            public DomainObject.Type getDomainType() { return DomainObject.Type.TIME_SLOT_TYPE; }
        }
        
        public static final class Builder extends AbstractDomainObject.Builder<TimeSlot.Type> {
            private int id;
            private String description;
            private Category.Key category;

            @Override
            DomainObject.Type getDomainType() { return DomainObject.Type.TIME_SLOT_TYPE; }
            @Override
            TimeSlot.Type.Key getKey() { return new TimeSlot.Type.Key(this); }
            @Override
            TimeSlot.Type doGetObject() { return new TimeSlot.Type(this); }

            @Override
            boolean isValid() {
                //TODO OMFG
                return true;
            }

            public Builder id(int id) { this.id = id; return this; }
            public Builder description(String description) { this.description = description; return this; }
            public Builder category(Category.Key category) { this.category = category; return this; }
        }
    }
}