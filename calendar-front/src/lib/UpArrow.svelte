<script>
    import { createEventDispatcher } from 'svelte';

    let dispatcher = createEventDispatcher();

    function click() {
        dispatcher('click');
    }
</script>

<svg
    id="_x32_"
    xmlns="http://www.w3.org/2000/svg"
    x="0px"
    y="0px"
    viewBox="0 0 512 512"
    style="width: 25px; height: 25px; opacity: 1;"
    xml:space="preserve"
    class="cursor-pointer"
    on:click={click}
>
    <style type="text/css">
        .st0 {
            fill: #4b4b4b;
        }
    </style>
    <g>
        <polygon
            class="st0"
            points="255.992,92.089 0,348.081 71.821,419.911 255.992,235.74 440.18,419.911 512,348.081"
            style="fill: rgb(75, 75, 75);"
        ></polygon>
    </g>
</svg>
