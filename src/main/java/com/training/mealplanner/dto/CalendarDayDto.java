package com.training.mealplanner.dto;

import java.time.LocalDate;

public class CalendarDayDto {
    private LocalDate date;
    private String meal;
    private String kcalProtein;
    private String snack;

    public CalendarDayDto(LocalDate date, String meal, String kcalProtein, String snack) {
        this.date = date;
        this.meal = meal;
        this.kcalProtein = kcalProtein;
        this.snack = snack;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getMeal() {
        return meal;
    }

    public String getKcalProtein() {
        return kcalProtein;
    }

    public String getSnack() {
        return snack;
    }
}
