package com.training.mealplanner.dto;

import java.util.List;

public class WeekDto {
    private List<CalendarDayDto> days;

    public WeekDto(List<CalendarDayDto> days) {
        this.days = days;
    }

    public List<CalendarDayDto> getDays() {
        return days;
    }
}
