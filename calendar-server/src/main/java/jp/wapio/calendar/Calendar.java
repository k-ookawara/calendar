package jp.wapio.calendar;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jp.wapio.calendar.holiday.Holiday;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
public class Calendar implements Serializable {

    @JsonIgnore
    private final Year year;

    @JsonIgnore
    private final Month month;

    private final List<Day> days = new ArrayList<>();

    public Calendar(Year year, Month month) {
        this.year = year;
        this.month = month;

        // 当月の初日
        LocalDate firstDate = LocalDate.of(year.getValue(), month.getValue(), 1);

        // カレンダー開始日
        LocalDate startDate;
        if (firstDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
            startDate = firstDate;
        } else {
            startDate = firstDate.minusDays(firstDate.getDayOfWeek().getValue());
        }

        // 当月の月末日
        LocalDate lastDate = firstDate.plusMonths(1).minusDays(1);

        // カレンダー最終日
        LocalDate endDate;
        if (lastDate.getDayOfWeek() == DayOfWeek.SATURDAY) {
            endDate = lastDate;
        } else {
            if (lastDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
                endDate = lastDate.plusDays(6);
            } else {
                endDate = lastDate.plusDays(6 - lastDate.getDayOfWeek().getValue());
            }
        }

        LocalDate date = startDate;
        while (!date.isAfter(endDate)) {
            days.add(new Day(date));
            date = date.plusDays(1);
        }
    }

    public int year() {
        return year.getValue();
    }

    public int month() {
        return month.getValue();
    }

    public void setHolidays(List<Holiday> holidays) {
        holidays.forEach(holiday -> days.stream()
                .filter(day -> day.equals(holiday.getDate()))
                .findFirst()
                .ifPresent(day -> day.setHoliday(holiday)));
    }
}
