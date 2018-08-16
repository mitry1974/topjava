package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalTime;

public class FilterData {
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;

    public FilterData() {
        set(LocalDate.MIN, LocalDate.MAX,LocalTime.MIN, LocalTime.MAX);
    }

    public void set(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime){
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }


}
