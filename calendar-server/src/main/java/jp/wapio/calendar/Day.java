package jp.wapio.calendar;

import jp.wapio.calendar.holiday.Holiday;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@ToString
@AllArgsConstructor
@Builder(toBuilder = true)
public class Day implements Serializable {

    private LocalDate value;

    @Setter
    private Holiday holiday;

    public Day(LocalDate value) {
        assert value != null : "Invalid argument";
        this.value = value;
    }

    public boolean equals(LocalDate arg) {
        return value.equals(arg);
    }
}
