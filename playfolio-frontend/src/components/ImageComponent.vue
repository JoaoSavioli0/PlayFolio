<template>
    <div class="w-full h-screen flex justify-center items-center fixed start-0 top-0 overflow-y-hidden z-[2000]">
        <div class="w-full h-full bg-black/80 z-[100] absolute top-0 start-0" @click="fechaImagem"></div>
        <button class="absolute top-[25px] end-[25px] z-[110] cursor-pointer" @click="fechaImagem">
            <img src="../assets/Imagens/close.svg" class="w-[30px] h-auto filtro-cinza">
        </button>

        <div class="w-max h-max z-[110]">
            <img :src="url" class="w-full xl:max-w-[1300px] h-auto">
        </div>
    </div>
</template>

<script>
export default {
    name: "Image",
    props: ["url"],
    methods: {
        fechaImagem() {
            this.$emit("fechar-imagem")
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