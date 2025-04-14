<template>
    <div class="w-full pt-6 min-[1800px]:px-40 lg:px-20">
        <div class="w-full">
            <h1 class="text-start text-[30px] py-8">PRÓXIMOS LANÇAMENTOS ⏳</h1>
        </div>
        <div class="grid lg:grid-cols-2 grid-cols-1 gap-4 min-[1800px]:px-40 lg:px-20">

            <div class="min-[1800px]:h-[300px] h-[240px] p-2" :class="`grid-div${index + 1}`"
                v-for="(jogo, index) in jogosEsperados" :key="index">
                <div class="rounded-3xl h-full overflow-hidden relative flex items-center">
                    <img :src="capasJogos[jogo.cover]" class="w-full h-full object-cover absolute z-10">
                    <div class="absolute w-full h-full bg-[#161616]/80 z-20"></div>
                    <div class="px-12 text-left relative z-30">
                        <h1 class="relative min-[1800px]:text-3xl text-xl line-clamp-2">{{ jogo.name }}</h1>
                        <span class="text-zinc-400">{{ formataDataUnix(jogo.first_release_date, 1) }}</span>
                    </div>
                    <div class="px-12 border-l-[1px] border-zinc-400 relative z-30 flex flex-col">

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

        </div>
    </div>
</template>

<script>
import axios from 'axios';
import dayjs from 'dayjs'
import duration from 'dayjs/plugin/duration'
import relativeTime from 'dayjs/plugin/relativeTime'

export default {
    name: "Info",
    data() {
        return {
            jogosEsperados: [],
            capasJogos: {},
        }
    },
    mounted() {
        this.encontraJogos()
    },
    methods: {
        async encontraJogos() {
            const body = `fields name, first_release_date, cover; sort first_release_date; where first_release_date > ${this.dataAtualUnix} & hypes > 100; limit 4;`
            try {
                const response = await axios.post("/v4/games", body, {
                    headers: {
                        'Accept': 'application/json',
                        'Client-ID': "i79ndcjylui2396ezi2v752sc9dze0",
                        'Authorization': `Bearer h6v8ywcqhwyyhj140u70v95rss6sga`,
                        'Content-Type': 'text/plain'
                    }
                })

                this.jogosEsperados = response.data
                setTimeout(await this.carregaCapas(this.jogosEsperados.map(e => e.id)), 1000)

            } catch (error) {
                console.error("Erro carregando hypes: " + error)
            }
        },

        async carregaCapas(jogosId) {

            const body = `fields url, game; where game = (${jogosId.join(", ")}); limit ${jogosId.length};`
            try {
                const response = await axios.post("/v4/covers", body, {
                    headers: {
                        'Accept': 'application/json',
                        'Client-ID': "i79ndcjylui2396ezi2v752sc9dze0",
                        'Authorization': `Bearer h6v8ywcqhwyyhj140u70v95rss6sga`,
                        'Content-Type': 'text/plain'
                    }
                })
                let imagens = response.data
                for (let imagem of imagens) {
                    this.capasJogos[imagem.id] = imagem.url.replace("thumb", "1080p")
                }

            } catch (error) {
                console.error("Erro carregando imagem: " + error)
            }
        },
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
</style>