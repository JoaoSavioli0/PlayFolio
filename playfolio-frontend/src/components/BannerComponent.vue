<template>
    <div
        class="w-full h-screen bg-[#161616] flex justify-end items-center flex-col pb-48 xl:pb-40 relative overflow-hidden">
        <img src="../assets/Imagens/kratos.png"
            class="absolute left-1/2 -translate-x-1/2 bottom-[-250px] w-[500px] xl:w-[600px] h-auto z-20">
        <div class="relative z-40 flex flex-col items-center">
            <h1 class="text-[90px] xl:text-[180px] leading-[0.85]">PLAYFOLIO</h1>
            <h2 class="text-[35px] text-start w-full px-2">Seus gostos, seus jogos.</h2>
            <h2 class="w-[400px] xl:w-full rounded-full py-2 xl:py-4 text-[#161616] bg-zinc-50 mt-12 xl:text-[20px]">
                COMECE JÁ
            </h2>
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