export type Holiday = {
    date: string;
    nameJa: string;
    nameEn: string;
};

export type Day = {
    value: string;
    weather: Weather;
    holiday: Holiday;
};

export type Calendar = {
    year: number;
    month: number;
    days: Day[];
};

export type WeatherOnDates = {
    [date: string]: Weather;
};

export enum Country {
    TOKYO = 'Tokyo',
    NEW_YORK = 'NewYork'
}

export enum Language {
    JA = 'ja',
    EN = 'en'
}

export enum Weather {
    SUNNY = 'SUNNY',
    CLOUDY = 'CLOUDY',
    RAINY = 'RAINY',
    SNOWY = 'SNOWY',
    FOG = 'FOG',
    THUNDERSTORM = 'THUNDERSTORM',
    HAIL = 'HAIL',
    UNKNOWN = 'UNKNOWN'
}
