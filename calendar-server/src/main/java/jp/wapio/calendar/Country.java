package jp.wapio.calendar;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Optional;

@ToString
@AllArgsConstructor
@Getter
public enum Country {
    TOKYO("Tokyo", 35.6785, 139.6823),
    NEW_YORK("New_York", 40.71, -74.01);

    private final String id;
    private final double latitude;
    private final double longitude;

    public static Optional<Country> of(String id) {
        for (Country c : values()) {
            if (c.id.equals(id)) {
                return Optional.of(c);
            }
        }
        return Optional.empty();
    }
}
