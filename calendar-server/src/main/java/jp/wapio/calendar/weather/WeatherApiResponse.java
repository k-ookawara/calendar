package jp.wapio.calendar.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Getter
@ToString
public class WeatherApiResponse {

    private Daily daily;

    @Getter
    @ToString
    public static class Daily {

        @JsonProperty("time")
        private List<LocalDate> time;

        @JsonProperty("weather_code")
        private List<Integer> weatherCodes;

        public Map<LocalDate, Weather> toWeatherOnDates() {
            Map<LocalDate, Weather> map = new HashMap<>();
            for (int i = 0; i < time.size(); i++) {
                LocalDate t = time.get(i);
                Integer w = weatherCodes.get(i);
                map.put(t, Weather.of(w));
            }
            return map;
        }
    }
}
