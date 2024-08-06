package jp.wapio.calendar.weather;

import jp.wapio.calendar.Country;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;

import static java.util.Objects.requireNonNull;
import static org.springframework.http.ResponseEntity.badRequest;

@CrossOrigin(origins = {"http://localhost:5173"})
@RestController
@RequestMapping("weather")
@RequiredArgsConstructor
public class WeatherController {

    private static final String WEATHER_API_URL = "https://api.open-meteo.com/v1/forecast?latitude={latitude}&longitude={longitude}&daily=weather_code&start_date={startDate}&end_date={endDate}";

    private final RestClient restClient;

    @GetMapping
    public ResponseEntity<Map<LocalDate, Weather>> get(
            @RequestParam("date") String dateString,
            @RequestParam("country") String paramCountry) {

        Country country;
        LocalDate date;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd");

        if (dateString == null) {
            date = LocalDate.now();
        } else {
            date = LocalDate.parse(dateString, formatter);
        }

        if (paramCountry == null) {
            country = Country.TOKYO;
        } else {
            Optional<Country> optional = Country.of(paramCountry);
            if (optional.isPresent()) {
                country = optional.get();
            } else {
                return badRequest().build();
            }
        }

        WeatherApiResponse response = restClient.get()
                .uri(WEATHER_API_URL,
                        country.getLatitude(),
                        country.getLongitude(),
                        date.format(formatter),
                        date.plusDays(4).format(formatter))
                .retrieve()
                .body(WeatherApiResponse.class);

        Map<LocalDate, Weather> weatherOnDates = requireNonNull(response).getDaily().toWeatherOnDates();

        return ResponseEntity.ok(weatherOnDates);
    }
}
