<template>
    <div class="py-6 min-[1800px]:px-40 xl:px-20 px-4 h-full">
        <div class="flex h-full xl:flex-row flex-col">

            <!-- Coluna esquerda -->
            <div class="xl:w-[50%] xl:pr-6 w-full py-4">
                <div class="flex flex-col gap-y-2 xl:pr-2">
                    <h1 class="xl:text-end text-start xl:text-[30px] text-[20px]" v-if="!jogosReview">
                        CARREGANDO JOGOS COM MAIS REVIEWS <span class="loading loading-dots loading-xl"></span></h1>
                    <h1 class="xl:text-end text-start xl:text-[30px] text-[20px]" v-else>📈 MAIS REVIEWS</h1>
                    <!-- Card -->

                    <div class="mt-[1px] w-full h-[120px] flex rounded-2xl overflow-hidden"
                        v-for="(jogo, index) in jogosReview" :key="jogo.id">

                        <router-link :to="`/game/${jogo.id}`" class="w-full h-full p-0">
                            <div class="w-full h-full relative overflow-hidden">

                                <div class="absolute inset-0 z-10">
                                    <img :src="capasJogos[jogo.cover]" class="w-full h-full object-cover blur-lg" />
                                </div>

                                <div class="absolute inset-0 z-20 w-full h-full bg-black/60"></div>

                                <div
                                    class="absolute top-1/2 start-[15px] z-30 -translate-y-1/2 w-[65px] h-[90px] overflow-hidden rounded-md shadow-md">
                                    <img :src="capasJogos[jogo.cover]" class="w-full h-full object-cover" />
                                </div>

                                <div class="w-full pl-[90px] h-full z-[100] relative">
                                    <div class="flex absolute end-[20px] xl:top-[20px] bottom-[10px] items-center">
                                        <img src="../assets/Imagens/stack.svg" class="w-[17px] h-auto filtro-branco">
                                        <span class="ml-2 text-[12px] xl:text-[15px]">{{ jogo.total_rating_count
                                            }}</span>
                                    </div>
                                    <div class="flex flex-col w-full justify-center h-full">
                                        <div class="flex items-center">
                                            <div
                                                class="w-[29px] h-[29px] rounded-lg bg-zinc-800/30 flex items-center justify-center shrink-0">
                                                <span class="text-zinc-50 text-[10px] xl:text-[14px]">{{ index + 1
                                                    }}</span>
                                            </div>
                                            <div class="max-w-[55%] line-clamp-1 text-left">
                                                <span class="text-sm text-start ml-2 pr-4 xl:text-md">{{ jogo.name
                                                    }}</span>
                                            </div>
                                            <div class="pl-4 border-l-[1px] border-zinc-400"
                                                v-if="jogo.first_release_date">
                                                <span class="text-xs text-zinc-400 xl:text-md">{{
                                                    formataDataUnix(jogo.first_release_date) }}</span>
                                            </div>
                                        </div>
                                        <div class="flex w-full" v-if="jogo.genres">
                                            <div class="text-start w-full line-clamp-1 text-zinc-400 max-w-[60%]">
                                                <span v-for="(genreId, index) in jogo.genres" :key="genreId"
                                                    class="text-[10px] xl:text-[13px]">{{
                                                        genresNomes[genreId] == "Role-playing (RPG)" ? "RPG" :
                                                            genresNomes[genreId]
                                                    }}<span v-if="index !== jogo.genres.length - 1">, </span></span>
                                            </div>
                                        </div>

                                        <div class="flex items-center gap-x-2 w-full pt-2" v-if="jogo.platforms">
                                            <img src="../assets/Imagens/ps_logo.png"
                                                v-if="jogo.platforms.some(plataforma => plataformasJogos[plataforma] == 1)"
                                                class="xl:w-[20px] w-[17px] h-auto filtro-branco">
                                            <img src="../assets/Imagens/xbox_logo.svg"
                                                v-if="jogo.platforms.some(plataforma => plataformasJogos[plataforma] == 2)"
                                                class="xl:w-[16px] w-[13px] h-auto filtro-branco">
                                            <img src="../assets/Imagens/pc_logo.png"
                                                v-if="jogo.platforms.some(plataforma => plataformasJogos[plataforma] == 3)"
                                                class="xl:w-[20px] w-[17px] h-auto filtro-branco">
                                            <img src="../assets/Imagens/ios_logo.png"
                                                v-if="jogo.platforms.some(plataforma => plataformasJogos[plataforma] == 4)"
                                                class="xl:w-[20px] w-[17px] h-auto filtro-branco">
                                            <img src="../assets/Imagens/switch_logo.png"
                                                v-if="jogo.platforms.some(plataforma => plataformasJogos[plataforma] == 5)"
                                                class="xl:w-[20px] w-[17px] h-auto filtro-branco">
                                            <img src="../assets/Imagens/stadia_logo.png"
                                                v-if="jogo.platforms.some(plataforma => plataformasJogos[plataforma] == 6)"
                                                class="xl:w-[20px] w-[17px] h-auto filtro-branco">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </router-link>
                    </div>
                </div>
            </div>

            <!-- Coluna direita -->
            <div class="xl:w-[50%] w-full xl:pl-6 py-4 xl:border-l-[1px] border-zinc-800">
                <div class="flex flex-col gap-y-2 xl:pl-2">
                    <h1 class="text-start xl:text-[30px] text-[20px]" v-if="!jogosNota">
                        CARREGANDO JOGOS COM MAIOR NOTA <span class="loading loading-dots loading-xl"></span></h1>
                    <h1 class="text-start xl:text-[30px] text-[20px]" v-else>MELHORES NOTAS 💎</h1>
                    <!-- Card -->
                    <div class="mt-[1px] w-full h-[120px] flex rounded-2xl overflow-hidden"
                        v-for="(jogo, index) in jogosNota" :key="jogo.id">

                        <router-link :to="`/game/${jogo.id}`" class="w-full h-full p-0">
                            <div class="w-full h-full relative overflow-hidden">

                                <div class="absolute inset-0 z-10">
                                    <img :src="capasJogos[jogo.cover]" class="w-full h-full object-cover blur-lg" />
                                </div>

                                <div class="absolute inset-0 z-20 w-full h-full bg-black/60"></div>

                                <div
                                    class="absolute top-1/2 start-[15px] z-30 -translate-y-1/2 w-[65px] h-[90px] overflow-hidden rounded-md shadow-md">
                                    <img :src="capasJogos[jogo.cover]" class="w-full h-full object-cover" />
                                </div>

                                <div class="w-full pl-[90px] h-full z-[100] relative">
                                    <div class="flex absolute end-[20px] xl:top-[20px] bottom-[10px] items-center">
                                        <img src="../assets/Imagens/star.png" class="w-[17px] h-auto filtro-branco">
                                        <span class="ml-2 text-[12px]">{{ (jogo.rating / 10).toFixed(1) }}</span>
                                    </div>
                                    <div class="flex flex-col w-full justify-center h-full">
                                        <div class="flex items-center">
                                            <div
                                                class="w-[29px] h-[29px] rounded-lg bg-zinc-800/30 flex items-center justify-center shrink-0">
                                                <span class="text-zinc-50 text-[10px] xl:text-[14px]">{{ index + 1
                                                    }}</span>
                                            </div>
                                            <div class="max-w-[55%] line-clamp-1 break-all text-left">
                                                <span class="text-sm text-start ml-2 pr-4 xl:text-md w-full">{{
                                                    jogo.name
                                                    }}</span>
                                            </div>
                                            <div class="pl-4 border-l-[1px] border-zinc-400"
                                                v-if="jogo.first_release_date">
                                                <span class="text-xs text-zinc-400 xl:text-md">{{
                                                    formataDataUnix(jogo.first_release_date) }}</span>
                                            </div>
                                        </div>
                                        <div class="flex w-full" v-if="jogo.genres">
                                            <div class="text-start w-full line-clamp-1 text-zinc-400 max-w-[60%]">
                                                <span v-for="(genreId, index) in jogo.genres" :key="genreId"
                                                    class="text-[10px] xl:text-[13px]">{{
                                                        genresNomes[genreId] == "Role-playing (RPG)" ? "RPG" :
                                                            genresNomes[genreId]
                                                    }}<span v-if="index !== jogo.genres.length - 1">, </span></span>
                                            </div>
                                        </div>

                                        <div class="flex items-center gap-x-2 w-full pt-2" v-if="jogo.platforms">
                                            <img src="../assets/Imagens/ps_logo.png"
                                                v-if="jogo.platforms.some(plataforma => plataformasJogos[plataforma] == 1)"
                                                class="xl:w-[20px] w-[17px] h-auto filtro-branco">
                                            <img src="../assets/Imagens/xbox_logo.svg"
                                                v-if="jogo.platforms.some(plataforma => plataformasJogos[plataforma] == 2)"
                                                class="xl:w-[16px] w-[13px] h-auto filtro-branco">
                                            <img src="../assets/Imagens/pc_logo.png"
                                                v-if="jogo.platforms.some(plataforma => plataformasJogos[plataforma] == 3)"
                                                class="xl:w-[20px] w-[17px] h-auto filtro-branco">
                                            <img src="../assets/Imagens/ios_logo.png"
                                                v-if="jogo.platforms.some(plataforma => plataformasJogos[plataforma] == 4)"
                                                class="xl:w-[20px] w-[17px] h-auto filtro-branco">
                                            <img src="../assets/Imagens/switch_logo.png"
                                                v-if="jogo.platforms.some(plataforma => plataformasJogos[plataforma] == 5)"
                                                class="xl:w-[20px] w-[17px] h-auto filtro-branco">
                                            <img src="../assets/Imagens/stadia_logo.png"
                                                v-if="jogo.platforms.some(plataforma => plataformasJogos[plataforma] == 6)"
                                                class="xl:w-[20px] w-[17px] h-auto filtro-branco">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </router-link>
                    </div>

                </div>
            </div>

        </div>



    </div>
</template>

<script>
import { useTwitchTokenStore } from '@/stores/TwitchTokenStore';
import { api } from '@/services/api';
import { useHomePageInfoStore } from '@/stores/HomePageInfoStore';
import { watchEffect } from 'vue';

export default {
    name: "Jogos",
    data() {
        return {
            twitchTokenStore: useTwitchTokenStore(),
            homePageInfoStore: useHomePageInfoStore(),

            jogosReview: null,
            jogosNota: null,

            genresNomes: null,
            capasJogos: null,
            plataformasJogos: null,

            timeOutId: null
        }
    },
    async mounted() {
        if (this.twitchTokenStore.access_token == '') {
            await this.twitchTokenStore.buscaToken()
        }

        watchEffect(() => {
            this.jogosReview = this.homePageInfoStore?.jogosMaisReviews || null
            this.jogosNota = this.homePageInfoStore?.jogosMaiorNota || null
            this.genresNomes = this.homePageInfoStore?.generosNomes || null
            this.capasJogos = this.homePageInfoStore?.capasJogos || null
            this.plataformasJogos = this.homePageInfoStore?.plataformasJogos || null
        })

    },
    beforeUnmount() {
        clearTimeout(this.timeoutId)
    },
    methods: {
        formataDataUnix(dataUnix) {
            const timestamp = dataUnix;
            const data = new Date(timestamp * 1000);
            const options = { year: 'numeric' };
            return data.toLocaleString('pt-BR', options)
        },
    },
    computed: {
        dataAtualUnix() {
            return Math.floor(Date.now() / 1000)
        }
    }
}
</script>

<style scoped>
.gradiente-card {
    background: rgb(29, 30, 34);
    background: linear-gradient(90deg, rgba(29, 30, 34, 0) 0%, rgba(29, 30, 34, 0.4990371148459384) 15%, rgba(29, 30, 34, 0.700717787114846) 40%, rgba(29, 30, 34, 0.896796218487395) 60%, rgba(29, 30, 34, 1) 90%);
}

.filtro-branco {
    filter: brightness(0) saturate(100%) invert(100%) sepia(0%) saturate(0%) hue-rotate(168deg) brightness(103%) contrast(103%);
}
</style>