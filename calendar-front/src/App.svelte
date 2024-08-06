<script lang="ts">
    import { type Calendar, Country, type Day, Language, type WeatherOnDates } from './lib/type';
    import WeatherIcon from './lib/WeatherIcon.svelte';
    import UpArrow from './lib/UpArrow.svelte';
    import DownArrow from './lib/DownArrow.svelte';
    import { onMount } from 'svelte';

    const months: {[month: number]: string} = {
        1: 'January',
        2: 'February',
        3: 'March',
        4: 'April',
        5: 'May',
        6: 'June',
        7: 'July',
        8: 'August',
        9: 'September',
        10: 'October',
        11: 'November',
        12: 'December'
    };

    const weeks = {
        ja: ['日', '月', '火', '水', '木', '金', '土'],
        en: ['Sun', 'Mon', 'Tue', 'Wed', 'Tur', 'Fri', 'Sat']
    };

    const languageLabel = {
        ja: '言語',
        en: 'Language'
    };

    const weatherLable = {
        ja: '天気',
        en: 'Weather'
    };

    const languages = [
        {
            value: 'ja',
            name: '日本語'
        },
        {
            value: 'en',
            name: 'English'
        }
    ];

    const countries = [
        {
            value: 'Tokyo',
            name: {
                ja: '東京',
                en: 'Tokyo'
            }
        },
        {
            value: 'New_York',
            name: {
                ja: 'ニューヨーク',
                en: 'NewYork'
            }
        }
    ];

    const today: Date = new Date();
    let year: number = today.getFullYear();
    let month: number = today.getMonth() + 1;
    let country: Country = Country.TOKYO;
    let lang: Language = Language.JA;
    let calendar: Calendar;

    onMount(() => showCalendar());

    async function showCalendar() {
        const [c, w]: [Calendar | undefined, WeatherOnDates | undefined] = await Promise.all([
            fetchCalendar(),
            fetchWeather()
        ]);
        if (!c) {
            return false;
        }
        setWeather(c, w);
        calendar = c;
    }

    function setWeather(calendar: Calendar, weatherOnDates: WeatherOnDates | undefined) {
        if (!weatherOnDates) {
            return;
        }
        Object.keys(weatherOnDates).forEach((date) => {
            const day: Day | undefined = calendar.days.find((c) => c.value == date);
            if (day) {
                day.weather = weatherOnDates[date];
            }
        });
    }

    async function fetchCalendar(): Promise<Calendar | undefined> {
        try {
            const response = await fetch(
                `http://localhost:8080/calendar?year=${year}&month=${month}`,
                {
                    method: 'GET'
                }
            );
            if (response.ok) {
                return response.json();
            } else {
                console.error('failed fetch calendar');
            }
        } catch (error) {
            console.error(error);
        }
    }

    async function fetchWeather(): Promise<WeatherOnDates | undefined> {
        if (today.getFullYear() !== year || today.getMonth() + 1 !== month) {
            return undefined;
        }

        const y = today.getFullYear();
        const m = (today.getMonth() + 1).toString().padStart(2, '0');
        const d = today.getDate().toString().padStart(2, '0');
        try {
            const res = await fetch(
                `http://localhost:8080/weather?date=${y}-${m}-${d}&country=${country}`,
                {
                    method: 'GET'
                }
            );
            if (res.ok) {
                return res.json();
            } else {
                console.error('failed fetch weather');
            }
        } catch (error) {
            console.error(error);
        }
    }

    function nextYearMonth() {
        if (month === 12) {
            year++;
            month = 1;
        } else {
            month++;
        }
        showCalendar();
    }

    function previousYearMonth() {
        if (month === 1) {
            year--;
            month = 12;
        } else {
            month--;
        }
        showCalendar();
    }

    function formatDate(dateString: string, lang: Language, index: number): string {
        const date = new Date(dateString);
        if (index === 0 || date.getDate() === 1) {
            return `${date.getMonth() + 1}/${date.getDate()}`;
        } else if (lang === Language.JA) {
            return `${date.getDate()}日`;
        } else {
            return `${date.getDate()}`;
        }
    }
</script>

<div class="h-dvh w-full">
    <div class="mx-10 my-4 flex items-center justify-between">
        <div class="flex items-center">
            <div class="mx-2">
                <UpArrow on:click={nextYearMonth} />
            </div>
            <div class="mx-2">
                <DownArrow on:click={previousYearMonth} />
            </div>
            <p class="w-56 p-3">
                {lang === Language.JA
                    ? `${year}年${month}月`
                    : `${months[month]} ${year}`}
            </p>
        </div>
        <div>
            <div>
                <label for="language" class="w-fit p-3">{languageLabel[lang]}</label>
                <select id="language" class="w-56 p-3" bind:value={lang}>
                    {#each languages as language}
                        <option value={language.value} selected>{language.name}</option>
                    {/each}
                </select>
            </div>
            <div>
                <label for="weather" class="w-fit p-3">{weatherLable[lang]}</label>
                <select
                    id="weather"
                    class="w-56 p-3"
                    bind:value={country}
                    on:change={() => showCalendar()}
                >
                    {#each countries as country}
                        <option value={country.value} selected>{country.name[lang]}</option>
                    {/each}
                </select>
            </div>
        </div>
    </div>
    <div class="mx-10 border">
        <div id="weeks" class="grid grid-cols-7">
            {#each weeks[lang] as dayOfWeek}
                <div class="border p-3 text-center">{dayOfWeek}</div>
            {/each}
        </div>
        {#if calendar}
            <div id="calendar" class="grid md:grid-cols-7">
                {#each calendar.days as day, i}
                    <div class="relative h-40 border p-4">
                        <div class:holiday={day.holiday != null}>
                            {formatDate(day.value, lang, i)}
                        </div>
                        {#if day.holiday}
                            <div class="font-bold text-red-400">
                                {lang === Language.JA ? day.holiday.nameJa : day.holiday.nameEn}
                            </div>
                        {/if}
                        <div class="absolute bottom-2 right-2">
                            <WeatherIcon weather={day.weather} />
                        </div>
                    </div>
                {/each}
            </div>
        {/if}
    </div>
</div>

<style>
    #weeks > *:nth-child(7n + 1),
    #calendar > *:nth-child(7n + 1),
    .holiday {
        color: red;
    }

    #weeks > *:nth-child(7n + 7),
    #calendar > *:nth-child(7n + 7) {
        color: blue;
    }
</style>
