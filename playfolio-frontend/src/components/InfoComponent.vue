<template>
    <div class="w-full py-6 px-24">
        <div class="grid-parent">
            <div class="grid-div1 h-[400px] p-2">
                <div class="grid-content rounded-xl h-full  bg-[#161616]"></div>
            </div>
            <div class="grid-div h-[400px] grid-div2 p-2">
                <div class="grid-content rounded-xl h-full bg-[#161616]"></div>
            </div>
            <div class="grid-div grid-div3 p-2">
                <div class="grid-content rounded-xl h-full bg-[#161616]"></div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: "info"
}
</script>

<style scoped>
.grid-parent {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    grid-template-rows: repeat(3, 1fr);
    grid-column-gap: 0px;
    grid-row-gap: 0px;
}

.grid-content {
    -webkit-box-shadow: inset 0px 0px 31px 11px rgba(0, 0, 0, 0.75);
    -moz-box-shadow: inset 0px 0px 31px 11px rgba(0, 0, 0, 0.75);
    box-shadow: inset 0px 0px 31px 11px rgba(0, 0, 0, 0.75);
}

.grid-div1 {
    grid-area: 1 / 1 / 3 / 3;
}

.grid-div2 {
    grid-area: 1 / 3 / 3 / 5;
}

.grid-div3 {
    grid-area: 3 / 1 / 4 / 5;
}
</style>