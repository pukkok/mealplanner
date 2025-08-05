package com.training.mealplanner.controller;

import com.training.mealplanner.dto.CalendarDayDto;
import com.training.mealplanner.dto.WeekDto;
import com.training.mealplanner.service.CalendarService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class MealPlanController {

    private final CalendarService calendarService;

    public MealPlanController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @GetMapping("/mealplan")
    public String getMealPlan(Model model) {
        LocalDate today = LocalDate.now();

        List<WeekDto> weeks = calendarService.generateCalendar(today);
        model.addAttribute("weeks", weeks);
        model.addAttribute("currentMonth", today.getMonthValue());
        model.addAttribute("currentYear", today.getYear());

        return "mealplan"; // templates/mealplan.html
    }
}
