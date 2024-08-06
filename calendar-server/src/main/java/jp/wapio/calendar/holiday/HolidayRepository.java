package jp.wapio.calendar.holiday;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface HolidayRepository extends CrudRepository<Holiday, LocalDate> {

    @Cacheable("holidays")
    List<Holiday> findByDateBetween(LocalDate start, LocalDate end);
}
