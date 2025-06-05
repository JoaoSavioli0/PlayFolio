<template>
    <div class="w-full h-screen flex justify-center items-center fixed start-0 top-0 overflow-y-hidden z-[1000]">
        <div class="w-full h-full bg-black/80 z-[100] absolute top-0 start-0" @click="fechaPlayer"></div>
        <button class="absolute top-[25px] end-[25px] z-[110] cursor-pointer" @click="fechaPlayer">
            <img src="../assets/Imagens/close.svg" class="w-[30px] h-auto filtro-cinza">
        </button>

        <div class="aspect-video xl:w-[800px] w-[95%] h-auto z-[110]">
            <iframe v-if="url" class="top-0 left-0 w-full h-full" :src="`https://www.youtube.com/embed/${url}`"
                title="YouTube video player" frameborder="0"
                allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
                allowfullscreen></iframe>
        </div>
    </div>
</template>

<script>
export default {
    name: "VideoPlayer",
    props: ["url"],
    methods: {
        fechaPlayer() {
            this.$emit("fechar-video")
        }
    },
    mounted() {
        console.log("Url: " + this.url)
    }
}
</script>

<style scoped>
.filtro-cinza {
    filter: brightness(0) saturate(100%) invert(77%) sepia(7%) saturate(302%) hue-rotate(202deg) brightness(82%) contrast(87%);
}
</style>