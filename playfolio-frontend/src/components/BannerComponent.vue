<template>
    <div
        class="w-full h-screen bg-[#161616] flex justify-end items-center flex-col pb-48 xl:pb-12 relative overflow-hidden">
        <img src="../assets/Imagens/masterchief.png"
            class="absolute left-[-100px] bottom-[-100px] xl:left-[43%] xl:-translate-x-1/2 xl:bottom-[-240px] w-[550px] min-[1800px]:w-[740px] h-auto z-20">
        <div
            class="absolute left-[50%] -translate-x-1/2 sombra bottom-[300px] min-[1800px]:bottom-[520px] xl:size-[150px] size-[90px] rounded-full z-30 bg-transparent">
        </div>
        <img src="../assets/Imagens/kratos.png"
            class="absolute left-[10%] bottom-[-100px] xl:left-[35%] xl:bottom-[-250px] w-[500px] min-[1800px]:w-[600px] h-auto z-40">
        <div
            class="absolute left-1/2 -translate-x-1/2 brilho-cinza bottom-[120px] min-[1800px]:bottom-[320px] xl:size-[350px] size-[200px] rounded-full z-10 bg-transparent">
        </div>
        <div class="relative z-[100] flex flex-col items-center">
            <h1 class="text-[40px] min-[1800px]:text-[160px] xl:text-[100px] leading-[0.85] text-zinc-50">PLAYFOLIO</h1>
            <h2 class="text-[12px] xl:text-[35px] xl:text-start max-xl:mt-2 w-full px-2 text-zinc-100">Seus gostos, seus
                jogos.</h2>
            <router-link to="/account/login" class="p-0 w-full">
                <button
                    class="xl:w-[400px] w-[95%] xl:w-full rounded-md py-2 xl:py-4 text-[#161616] bg-zinc-50 mt-4 xl:mt-12 xl:text-[20px] cursor-pointer">
                    <h2>COMECE JÁ</h2>
                </button>
            </router-link>
        </div>
        <div class="w-full h-full absolute gradiente-banner bottom-0 z-60"></div>
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

.brilho-cinza {
    -webkit-box-shadow: 0px 0px 105px 45px rgba(112, 112, 112, 0.9);
    -moz-box-shadow: 0px 0px 105px 45px rgba(112, 112, 112, 0.9);
    box-shadow: 0px 0px 105px 45px rgba(112, 112, 112, 0.9);
}

.sombra {
    -webkit-box-shadow: 0px 0px 105px 45px rgba(0, 0, 0, 0.9);
    -moz-box-shadow: 0px 0px 105px 45px rgba(0, 0, 0, 0.9);
    box-shadow: 0px 0px 105px 45px rgba(0, 0, 0, 0.9);
}
</style>