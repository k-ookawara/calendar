package jp.wapio.calendar.weather;

import lombok.ToString;

@ToString
public enum Weather {
    SUNNY(0, 1),
    CLOUDY(2, 3),
    RAINY(51, 53, 55, 56, 57, 61, 63, 65, 66, 67, 80, 81, 82),
    SNOWY(66, 67, 71, 73, 75, 77, 85, 86),
    FOG(45, 48),
    THUNDERSTORM(95),
    HAIL(96, 99),
    UNKNOWN(-1);

    private final int[] values;
    private final int minValue;
    private final int maxValue;

    private Weather(int... values) {
        this.values = values;
        this.minValue = values[0];
        this.maxValue = values[values.length - 1];
    }

    public static Weather of(int value) {
        for (Weather weather : values()) {
            if (weather.minValue <= value && value <= weather.maxValue) {
                for (int v : weather.values) {
                    if (v == value) {
                        return weather;
                    }
                }
            }
        }
        return Weather.UNKNOWN;
    }
}
