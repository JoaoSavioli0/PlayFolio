<template>
    <div
        class="w-full h-screen bg-[#161616] flex justify-end items-center flex-col pb-48 xl:pb-12 relative overflow-hidden">
        <img src="../assets/Imagens/masterchief.png"
            class="absolute left-[43%] -translate-x-1/2 bottom-[-240px] w-[550px] min-[1800px]:w-[740px] h-auto z-20">
        <div
            class="absolute left-[50%] -translate-x-1/2 sombra bottom-[300px] min-[1800px]:bottom-[520px] size-[150px] rounded-full z-30 bg-black">
        </div>
        <img src="../assets/Imagens/kratos.png"
            class="absolute left-[35%] bottom-[-250px] w-[500px] min-[1800px]:w-[600px] h-auto z-40">
        <div
            class="absolute left-1/2 -translate-x-1/2 brilho-cinza bottom-[120px] min-[1800px]:bottom-[320px] size-[350px] rounded-full z-10 bg-[#707070]">
        </div>
        <div class="relative z-[100] flex flex-col items-center">
            <h1 class="text-[90px] min-[1800px]:text-[160px] min-[1400px]:text-[100px] leading-[0.85]">PLAYFOLIO</h1>
            <h2 class="text-[35px] text-start w-full px-2">Seus gostos, seus jogos.</h2>
            <router-link to="/account/login" class="p-0 w-full">
                <button
                    class="w-[400px] xl:w-full rounded-full py-2 xl:py-4 text-[#161616] bg-zinc-50 mt-12 xl:text-[20px] cursor-pointer">
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