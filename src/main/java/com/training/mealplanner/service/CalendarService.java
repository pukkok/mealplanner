package com.training.mealplanner.service;

import com.training.mealplanner.dto.CalendarDayDto;
import com.training.mealplanner.dto.WeekDto;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class CalendarService {

    public List<WeekDto> generateCalendar(LocalDate baseDate) {
        List<CalendarDayDto> allDays = new ArrayList<>();

        YearMonth yearMonth = YearMonth.from(baseDate);
        LocalDate firstDayOfMonth = yearMonth.atDay(1);
        LocalDate lastDayOfMonth = yearMonth.atEndOfMonth();

        // 월요일부터 시작하는 달력 기준으로 첫 주의 시작일 계산
        DayOfWeek firstDayWeek = firstDayOfMonth.getDayOfWeek();
        int daysToSubtract = (firstDayWeek.getValue() + 7 - DayOfWeek.MONDAY.getValue()) % 7;
        LocalDate startDate = firstDayOfMonth.minusDays(daysToSubtract);

        // 평일(월~금)만 추가
        LocalDate current = startDate;
        while (!current.isAfter(lastDayOfMonth)) {
            DayOfWeek day = current.getDayOfWeek();

            if (day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY) {
                if (current.getMonth() == baseDate.getMonth()) {
                    allDays.add(new CalendarDayDto(current, "", "", ""));
                } else {
                    allDays.add(new CalendarDayDto(null, "", "", ""));
                }
            }

            current = current.plusDays(1);
        }

        // 마지막 주가 평일 5일이 안되면 null로 채워서 맞추기
        while (allDays.size() % 5 != 0) {
            allDays.add(new CalendarDayDto(null, "", "", ""));
        }

        // 5일씩 끊어서 WeekDto 리스트 만들기
        List<WeekDto> weeks = new ArrayList<>();
        int totalWeeks = allDays.size() / 5;
        for (int i = 0; i < totalWeeks; i++) {
            List<CalendarDayDto> weekDays = allDays.subList(i * 5, (i + 1) * 5);
            weeks.add(new WeekDto(weekDays));
        }

        return weeks;
    }
}