<template>
    <div class="w-full xl:h-[60vh] bg-[#161616] flex items-center relative">
        <div class="w-full grid xl:grid-cols-3 grid-cols-1 gap-2 xl:px-[200px] px-4 relative z-50">
            <div class="xl:h-[400px] xl:pb-[30px] w-full flex justify-end">
                <div
                    class="rounded-3xl xl:h-[290px] h-[260px] xl:w-[250px] w-full bg-zinc-900 bg-[#262729] relative overflow-hidden p-8">
                    <h1 class="text-zinc-50 xl:text-start text-center xl:text-xl text-lg">Encontre seus jogos favoritos
                    </h1>
                    <div
                        class="size-[180px] brilho-cinza xl:start-[20px] start-1/2 max-xl:-translate-x-1/2 bottom-[-50px] absolute opacity-[0.8]">
                    </div>

                    <img src="../assets/Imagens/tss1.jpg"
                        class="w-[240px] h-auto rounded-t-xl shadow-md absolute xl:start-[-10px] start-1/2 max-xl:-translate-x-1/2 bottom-[-10px]">
                </div>
            </div>

            <div class="xl:h-[400px] xl:pt-[30px] justify-center flex">
                <div
                    class="rounded-3xl xl:w-[320px] w-full xl:h-[350px] h-[320px] bg-zinc-900 bg-[#262729] relative overflow-hidden p-8">
                    <h1 class="text-zinc-50 text-center xl:text-3xl text-lg">Dê a sua avaliação</h1>
                    <div class="size-[160px] brilho-cinza start-1/2 -translate-x-1/2 bottom-0 absolute opacity-[0.8]">
                    </div>

                    <img src="../assets/Imagens/tss2.jpg"
                        class="w-[250px] h-auto rounded-tl-xl absolute start-1/2 -translate-x-1/2 bottom-0 shadow-md">
                </div>
            </div>

            <div class="xl:h-[400px] xl:pt-[10px] flex justify-start">
                <div
                    class="rounded-3xl xl:h-[280px] h-[250px] xl:w-[250px] w-full bg-zinc-900 bg-[#262729] relative overflow-hidden p-8">
                    <h1 class="text-zinc-50 xl:text-start text-center xl:text-xl text-lg">Crie seu portfólio</h1>
                    <div
                        class="size-[180px] brilho-cinza xl:end-[20px] start-1/2 max-xl:-translate-x-1/2 bottom-[-50px] absolute opacity-[0.8]">
                    </div>

                    <img src="../assets/Imagens/tts3.jpg"
                        class="xl:w-[300px] w-[250px] h-auto xl:rounded-tl-xl max-xl:rounded-t-xl shadow-md absolute xl:end-[-30px] end-1/2 max-xl:translate-x-1/2 bottom-[-10px]">
                </div>
            </div>
        </div>

        <div class="w-full bottom-0 h-[60%] absolute degrade z-40"></div>
    </div>
</template>

<script>
export default {
    name: "TutorialComponent"
}
</script>

<style scoped>
.brilho-cinza {
    -webkit-box-shadow: 0px 0px 105px 45px rgba(112, 112, 112, 0.9);
    -moz-box-shadow: 0px 0px 105px 45px rgba(112, 112, 112, 0.9);
    box-shadow: 0px 0px 105px 45px rgba(112, 112, 112, 0.9);
}

.degrade {
    background: #1A1B1E;
    background: linear-gradient(0deg, rgba(26, 27, 30, 1) 0%, rgba(22, 22, 22, 1) 100%);
}
</style>