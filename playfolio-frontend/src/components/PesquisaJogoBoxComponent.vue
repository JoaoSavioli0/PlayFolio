<template>
    <div class="w-full h-screen fixed start-0 top-0 overflow-y-hidden z-[2000]">
        <div class="w-full h-full bg-black/80 z-[100] absolute top-0 start-0" @click="fechaBox"></div>
        <div
            class="xl:w-[500px] w-[90%] max-h-[600px] z-[110] fixed top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 rounded-2xl flex flex-col overflow-hidden border border-zinc-600 px-4 py-4 bg-zinc-900/90">
            <div class="w-full flex justify-between items-center px-2">
                <h1 class="text-start xl:text-xl text-lg">Pesquisar</h1>
                <button @click="fechaBox" class="cursor-pointer">
                    <img src="../assets/Imagens/close.svg" class="size-[25px] filtro-branco">
                </button>
            </div>
            <div class="w-full rounded-xl border-[1px] border-zinc-800 bg-zinc-900/70 my-1 flex items-center mt-4">
                <input type="text" v-model="tituloPesquisado" @input="parouDeDigitar"
                    placeholder="Pesquise seus jogos..."
                    class="w-full py-3 w-full outline-0 border-0 pl-4 pr-8 text-xs text-zinc-50">
                <div class="px-4 ml-auto"><img src="../assets/Imagens/lupa.svg"
                        class="w-[18px] h-auto filtro-cinza rotate-y-180">
                </div>
            </div>

            <div class="w-full flex justify-center" v-if="carregandoPesquisa">
                <span class="loading loading-ring loading-xl py-8"></span>
            </div>

            <div class="w-full flex justify-center items-center xl:h-[100px] h-[80px]"
                v-else-if="!carregandoPesquisa && tituloPesquisado && jogosPesquisados.length == 0">
                <span class="text-zinc-50 text-xs">Nenhum título encontrado!</span>
            </div>

            <div class="grid grid-cols-1 mt-6 gap-2 overflow-y-scroll" v-else>
                <div class="mt-[1px] w-full h-[90px] flex rounded-2xl overflow-hidden shadow-md border border-zinc-600"
                    v-for="jogo in jogosPesquisados" :key="jogo.id">

                    <router-link @click="fechaBox" :to="`/game/${jogo.id}`" class="w-full h-full p-0">
                        <div class="w-full h-full relative overflow-hidden">

                            <!-- 🎯 BG principal com blur -->
                            <div class="absolute inset-0 z-10">
                                <img :src="capasJogosPesquisados[jogo.cover]"
                                    class="w-full h-full object-cover blur-lg" />
                            </div>

                            <!-- 🌫️ Gradiente por cima -->
                            <div class="absolute inset-0 z-20 w-full h-full bg-black/60"></div>

                            <!-- 🖼️ Miniatura frontal -->
                            <div
                                class="absolute top-1/2 start-[15px] z-30 -translate-y-1/2 w-[55px] h-[75px] overflow-hidden rounded-md shadow-md">
                                <img :src="capasJogosPesquisados[jogo.cover]" class="w-full h-full object-cover"
                                    v-if="capasJogosPesquisados[jogo.cover]" />

                                <div class="w-full h-full flex justify-center items-center">
                                    <img src="../assets/Imagens/no_image.png" class="w-[25px] h-auto filtro-cinza">
                                </div>
                            </div>

                            <div class="w-full pl-[90px] h-full z-[100] relative">
                                <div class="flex flex-col w-full justify-center h-full">
                                    <div class="flex flex-col">
                                        <div class="max-w-[55%] line-clamp-2 text-left">
                                            <h1 class="text-start pr-4 text-md min-[1800px]:text-lg">{{
                                                jogo.name
                                            }}</h1>
                                        </div>
                                        <div v-if="jogo.first_release_date" class="w-fit">
                                            <h2 class="text-left text-zinc-500 text-xs min-[1800px]:text-sm">{{
                                                formataDataUnix(jogo.first_release_date) }}</h2>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </router-link>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { useTwitchTokenStore } from '@/stores/TwitchTokenStore'
import axios from 'axios'
export default {
    name: "PesquisaJogoBox",
    data() {
        return {
            tituloPesquisado: '',
            jogosPesquisados: [],
            capasJogosPesquisados: {},
            carregandoPesquisa: false,
            twitchToken: useTwitchTokenStore()
        }
    },
    async mounted() {
        if (this.twitchToken.access_token == '') await this.twitchToken.buscaToken()
    },
    methods: {
        fechaBox() {
            this.$emit("fechar-box")
        },
        parouDeDigitar() {
            this.carregandoPesquisa = true
            let tituloBackup = ''
            this.jogosPesquisados = []

            if (tituloBackup == '') tituloBackup = this.tituloPesquisado

            setTimeout(() => {
                if (tituloBackup == this.tituloPesquisado) {
                    if (this.tituloPesquisado != '') {
                        this.pesquisaTitulo()
                    }
                    return
                }
                tituloBackup = this.tituloPesquisado
            }, 2000)
        },

        async pesquisaTitulo() {
            const tituloFormatado = this.tituloPesquisado.toLowerCase().replace(/" /g, '\\"')
            const body = `fields name, first_release_date, cover, total_rating_count; where name ~ *"${tituloFormatado}"*; sort total_rating_count desc; limit 50;`
            try {
                const response = await axios.post("/v4/games", body, {
                    headers: {
                        'Accept': 'application/json',
                        'Client-ID': "i79ndcjylui2396ezi2v752sc9dze0",
                        'Authorization': `Bearer ${this.twitchToken.access_token}`,
                        'Content-Type': 'text/plain'
                    }
                })

                this.jogosPesquisados = response.data

                this.jogosPesquisados = response.data.sort((a, b) => {
                    const aCount = a.total_rating_count ?? 0;
                    const bCount = b.total_rating_count ?? 0;

                    if (aCount === 0 && bCount > 0) return 1;
                    if (bCount === 0 && aCount > 0) return -1;
                    return bCount - aCount;
                });

                this.carregandoPesquisa = false

                await this.carregaCapas(this.jogosPesquisados.map(j => j.id))

            } catch (error) {
                console.error("Erro pesquisando titulo: " + error)
            }
        },

        async carregaCapas(jogosId) {
            console.log("jogosId:", jogosId)

            const body = `fields url; where game = (${jogosId.join(", ")}); limit 50;`
            try {
                const response = await axios.post("/v4/covers", body, {
                    headers: {
                        'Accept': 'application/json',
                        'Client-ID': "i79ndcjylui2396ezi2v752sc9dze0",
                        'Authorization': `Bearer ${this.twitchToken.access_token}`,
                        'Content-Type': 'text/plain'
                    }
                })

                console.log("responsedata: ", response.data)
                for (let data of response.data) {
                    if (data && data.url) {
                        this.capasJogosPesquisados[data.id] = data.url.replace("thumb", "720p")
                    }
                }
                console.log("capas: ", this.capasJogosPesquisados)
            } catch (error) {
                console.error("Erro carregando imagem: " + error)
            }
        },

        formataDataUnix(dataUnix) {
            const timestamp = dataUnix;
            const data = new Date(timestamp * 1000);
            const options = { year: 'numeric' };
            return data.toLocaleString('pt-BR', options)
        },

    }
}
</script>

<style scoped>
.filtro-cinza {
    filter: brightness(0) saturate(100%) invert(45%) sepia(6%) saturate(508%) hue-rotate(202deg) brightness(95%) contrast(84%);
}

.filtro-branco {
    filter: brightness(0) invert(1);
}

/* ===== Scrollbar CSS ===== */
/* Firefox */
* {
    scrollbar-width: thin;
    scrollbar-color: #9f9fa9 #262729;
}

/* Chrome, Edge, and Safari */
*::-webkit-scrollbar {
    width: 16px;
}

*::-webkit-scrollbar-track {
    background: #262729;
}

*::-webkit-scrollbar-thumb {
    background-color: #9f9fa9;
    border-radius: 10px;
    border: 3px none #272729;
}
</style>