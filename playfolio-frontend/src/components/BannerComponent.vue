<template>
    <div class="w-full h-screen bg-[#161616] flex justify-end items-center flex-col pb-48 relative overflow-hidden">
        <img src="../assets/Imagens/kratos.png"
            class="absolute left-1/2 -translate-x-1/2 bottom-[-250px] w-[500px] h-auto z-20">
        <div class="relative z-40">
            <h1 class="text-[90px] leading-[0.75]">SEUS</h1>
            <h1 class="text-[90px]">JOGOS</h1>
            <h2 class="w-[400px] rounded-full py-2 text-[#161616] bg-zinc-50">COMECE JÁ</h2>
        </div>
        <div class="w-full h-full absolute gradiente-banner bottom-0 z-30"></div>
    </div>
</template>

<script>
export default {
    name: "Banner",
    methods: {

    }
}
</script>

<style scoped>
.gradiente-banner {
    background: rgb(22, 22, 22);
    background: linear-gradient(0deg, rgba(22, 22, 22, 1) 0%, rgba(0, 0, 0, 0) 100%);
}
</style>