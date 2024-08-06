package jp.wapio.calendar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class CalendarServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CalendarServerApplication.class, args);
    }

}
