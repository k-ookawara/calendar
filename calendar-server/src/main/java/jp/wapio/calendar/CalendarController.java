package jp.wapio.calendar;

import jp.wapio.calendar.holiday.Holiday;
import jp.wapio.calendar.holiday.HolidayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.List;

import static org.springframework.http.ResponseEntity.badRequest;

@CrossOrigin(origins = {"http://localhost:5173"})
@RestController
@RequestMapping("/calendar")
@RequiredArgsConstructor
public class CalendarController {

    private final HolidayRepository holidayRepository;

    @GetMapping
    public ResponseEntity<Calendar> get(
            @RequestParam("year") String paramYear,
            @RequestParam("month") String paramMonth) {

        // パラメータの取得
        Year year;
        Month month;
        LocalDate now = LocalDate.now();
        try {

            if (paramYear == null) {
                year = Year.of(now.getYear());
            } else {
                year = Year.of(Integer.parseInt(paramYear));
            }

            if (paramMonth == null) {
                month = now.getMonth();
            } else {
                month = Month.of(Integer.parseInt(paramMonth));
            }
        } catch (NumberFormatException e) {
            return badRequest().build();
        }

        // カレンダーの作成
        Calendar calendar = new Calendar(year, month);

        // 祝日の取得
        LocalDate start = LocalDate.of(year.getValue(), month.getValue(), 1);
        LocalDate end = start.plusMonths(1).minusDays(1);

        List<Holiday> holidays = holidayRepository.findByDateBetween(start, end);
        if (!holidays.isEmpty()) {
            calendar.setHolidays(holidays);
        }

        return ResponseEntity.ok(calendar);
    }
}
