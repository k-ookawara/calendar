<script lang="ts">
    import SunnyIcon from './SunnyIcon.svelte';
    import { Weather } from './type';
    import FogIcon from './FogIcon.svelte';
    import CloudyIcon from './CloudyIcon.svelte';
    import RainyIcon from './RainyIcon.svelte';
    import ThunderStormIcon from './ThunderStormIcon.svelte';
    import SnowyIcon from './SnowyIcon.svelte';
    import HailIcon from './HailIcon.svelte';

    export let weather: Weather;
</script>

{#if weather === Weather.SUNNY}
    <SunnyIcon />
{:else if weather === Weather.CLOUDY}
    <CloudyIcon />
{:else if weather === Weather.RAINY}
    <RainyIcon />
{:else if weather === Weather.SNOWY}
    <SnowyIcon />
{:else if weather === Weather.THUNDERSTORM}
    <ThunderStormIcon />
{:else if weather === Weather.FOG}
    <FogIcon />
{:else if weather === Weather.HAIL}
    <HailIcon />
{/if}
