<template>
    <div class="w-full pt-6 xl:px-20 px-6">
        <div class="w-full">
            <h1 class="text-start xl:text-[30px] text-[20px] py-8" v-if="!jogosEsperados.length">
                CARREGANDO PRÓXIMOS LANÇAMENTOS <span class="loading loading-dots loading-xl"></span></h1>
            <h1 class="text-start xl:text-[30px] text-[20px] py-8" v-else>PRÓXIMOS LANÇAMENTOS ⏳</h1>
        </div>
        <div class="grid xl:grid-cols-3 grid-cols-1 gap-4 min-[1500px]:px-20" v-if="jogosEsperados.length">

            <router-link v-for="(jogo, index) in jogosEsperados" :key="index" :to="`/game/${jogo.id}`" class="p-0"
                :class="`grid-div-${index + 1}`">
                <div class="min-[1800px]:h-[300px] xl:h-[240px] h-[160px] xl:p-2">
                    <div class="rounded-3xl h-full overflow-hidden relative flex items-center justify-between">
                        <img :src="capasJogos[jogo.cover]" class="w-full h-full object-cover absolute z-10">
                        <div class="absolute w-full h-full bg-[#161616]/80 z-20"></div>
                        <div class="xl:px-12 px-4 text-left relative z-30 max-w-[60%]">
                            <h1 class="relative min-[1800px]:text-3xl min-md:text-xl text-sm line-clamp-2">{{ jogo.name
                                }}</h1>
                            <span class="text-zinc-400 min-[1800px]:text-xl min-md:text-lg text-xs">{{
                                formataDataUnix(jogo.first_release_date, 1) }}</span>
                        </div>
                        <div
                            class="xl:px-12 px-6 min-w-max shrink-0 border-l-[1px] border-zinc-400 relative z-30 flex flex-col">

                            <div class="w-full text-end" v-if="formataDataDiferencaUnix(jogo.first_release_date).mes >
                                0">
                                <h2 class="min-[1800px]:text-5xl text-3xl">{{
                                    formataDataDiferencaUnix(jogo.first_release_date).mes }}</h2>
                                <span>
                                    {{ formataDataDiferencaUnix(jogo.first_release_date)?.mes > 1 ? 'Meses' : 'Mês' }}
                                </span>
                            </div>

                            <div class="w-full mt-4 text-end"
                                v-if="formataDataDiferencaUnix(jogo.first_release_date).dia > 0">
                                <h2 class="min-[1800px]:text-5xl text-3xl">{{
                                    formataDataDiferencaUnix(jogo.first_release_date).dia }}</h2>
                                <span>
                                    {{ formataDataDiferencaUnix(jogo.first_release_date)?.dia > 1 ? 'Dias' : 'Dia' }}
                                </span>
                            </div>
                        </div>
                    </div>
                </div>
            </router-link>

        </div>
    </div>
</template>

<script>
import dayjs from 'dayjs'
import duration from 'dayjs/plugin/duration'
import relativeTime from 'dayjs/plugin/relativeTime'
import { useTwitchTokenStore } from '@/stores/TwitchTokenStore';
import { api } from '@/services/api';
import { useHomePageInfoStore } from '@/stores/HomePageInfoStore';
import { watchEffect } from 'vue';

export default {
    name: "Info",
    data() {
        return {
            twitchTokenStore: useTwitchTokenStore(),
            homePageInfoStore: useHomePageInfoStore(),

            jogosEsperados: [],
            capasJogos: {}
        }
    },
    async mounted() {
        if (this.twitchTokenStore.access_token == '') {
            await this.twitchTokenStore.buscaToken()
        }

        watchEffect(() => {
            this.jogosEsperados = this.homePageInfoStore?.jogosEmBreve || []
            this.capasJogos = this.homePageInfoStore?.capasJogos || {}
        })

    },
    methods: {
        formataDataUnix(dataUnix) {
            const timestamp = dataUnix;
            const data = new Date(timestamp * 1000);
            const options = { day: '2-digit', month: '2-digit', year: 'numeric' };
            return data.toLocaleString('pt-BR', options)
        },

        formataDataDiferencaUnix(dataUnix) {
            dayjs.extend(duration)
            dayjs.extend(relativeTime)

            const hoje = dayjs()
            const lancamento = dayjs.unix(dataUnix)

            const diferenca = lancamento.diff(hoje)

            const duracao = dayjs.duration(diferenca)

            const meses = Math.floor(duracao.asMonths())
            const dias = duracao.subtract(dayjs.duration({ months: meses })).days()

            return { 'mes': meses, 'dia': dias }
        }
    },
    computed: {
        dataAtualUnix() {
            return Math.floor(Date.now() / 1000)
        }
    }
}
</script>

<style scoped>
.grid-content {
    -webkit-box-shadow: inset 0px 0px 31px 11px rgba(0, 0, 0, 0.75);
    -moz-box-shadow: inset 0px 0px 31px 11px rgba(0, 0, 0, 0.75);
    box-shadow: inset 0px 0px 31px 11px rgba(0, 0, 0, 0.75);
}

.grid-div-1,
.grid-div-4 {
    grid-column: span 1 / span 1;
}

.grid-div-2,
.grid-div-3 {
    grid-column: span 2 / span 2;
}

@media screen and (max-width: 1280px) {

    .grid-div-1,
    .grid-div-4,
    .grid-div-2,
    .grid-div-3 {
        grid-column: span 1 / span 1;
    }
}
</style>