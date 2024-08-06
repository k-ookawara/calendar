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
            points="440.189,92.085 256.019,276.255 71.83,92.085 0,163.915 256.019,419.915 512,163.915 	"
            style="fill: rgb(75, 75, 75);"
        ></polygon>
    </g>
</svg>
