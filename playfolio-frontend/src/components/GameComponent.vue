<template>
    <VideoPlayerComponent v-if="videoPlayerUrl" :url="videoPlayerUrl" @fechar-video="videoPlayerUrl = ''" />
    <ImageComponent v-if="imagemUrl" :url="imagemUrl" @fechar-imagem="imagemUrl = ''" />
    <AvaliacaoBoxComponent v-if="avaliacaoBoxOpen" :dados="dados" :imagem="imagens[0]" :statusDefault="statusDefault"
        :capa="capasJogos[id]" @fecha-avaliacaoBox="avaliacaoBoxOpen = false" />

    <div class="w-[820px] h-min rounded-2xl border-[1px] border-zinc-600 overflow-hidden flex flex-col bg-[#1B1D1F]">
        <div class="w-full h-[450px] z-40 relative">
            <img :src="imagens[0]" class="w-full h-full" v-if="!carregandoDados && imagens[0]">

            <div class="w-full h-full flex justify-center items-center" v-if="!carregandoDados && !imagens[0]">
                <img src="../assets/Imagens/no_image.png" class="w-[40px] h-auto filtro-cinza">
            </div>

            <div class="w-full h-full flex justify-center items-center" v-if="carregandoDados">
                <span class="loading loading-ring loading-xl"></span>
            </div>
            <div class="absolute bottom-0 w-full h-[25%] gradiente-cinza-transparente"></div>
        </div>
        <div class="w-full z-50 flex px-2">

            <!-- Caixa esquerda -->
            <div class="w-max pl-4 pr-2">
                <div
                    class="w-full h-[105px] px-4 flex items-center rounded-2xl border-[2px] border-zinc-700 mt-[-100px] bg-[#1b1d1f]/60">

                    <img :src="capasJogos[id]" class="w-[65px] h-auto rounded-lg shadow-md"
                        v-if="!carregandoDados && capasJogos[id]">

                    <div class="size-[65px] flex justify-center items-center"
                        v-else-if="!carregandoDados && !capasJogos[id]">
                        <img src="../assets/Imagens/no_image.png" class="w-[25px] h-auto filtro-cinza">
                    </div>

                    <div class="w-[65px] h-full flex items-center justify-center" v-else-if="carregandoDados">
                        <span class="loading loading-ring loading-lg"></span>
                    </div>

                    <div class="flex w-full h-full pl-2" v-if="dados">
                        <div class="flex flex-col text-start px-4 justify-center h-full">
                            <span class="text-[12px] text-zinc-300">NOTA</span>
                            <h2 class="text-2xl" v-if="dados.total_rating && !carregandoDados"
                                :class="[{ 'text-lime-400': dados.total_rating >= 90 }, { 'text-red-600': dados.total_rating <= 50 }, { 'text-teal-300': dados.total_rating >= 70 && dados.total_rating < 90 }, { 'text-amber-400': dados.rating > 50 && dados.total_rating < 70 }]">
                                {{ (dados.total_rating / 10).toFixed(1) }}</h2>
                            <h2 v-else>
                                N/A
                            </h2>
                        </div>

                        <div class="flex flex-col text-start px-4 justify-center h-full">
                            <span class="text-[12px] text-zinc-300">AVALIAÇÕES</span>
                            <h2 class="text-2xl" v-if="dados.total_rating && !carregandoDados">
                                {{ dados.total_rating_count }}</h2>
                            <h2 v-else>N/A</h2>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Caixa direita -->
            <div class="w-max pl-4 pr-2" v-if="usuario != 0">
                <div
                    class="w-full h-[105px] px-4 py-2 flex justify-around flex-col rounded-2xl border-[2px] border-zinc-700 mt-[-100px] bg-[#1b1d1f]/60">

                    <div class="w-full">
                        <span class="text-[10px] text-zinc-400">Crie uma conta e salve seus jogos favoritos</span>
                    </div>
                    <div class="w-full flex justify-start items-center">
                        <button
                            class="py-2 px-4 rounded-xl border-[1px] border-zinc-500 text-sm cursor-pointer">Login</button>
                        <span class="px-4 text-zinc-400 text-sm">Ou</span>
                        <button
                            class="p-2 px-4 rounded-xl border-[1px] border-zinc-500 text-sm cursor-pointer">Cadastre-se</button>
                    </div>
                </div>
            </div>

            <div class="w-max pl-4 pr-2" v-else>
                <div
                    class="w-full h-[105px] px-4 py-2 flex justify-around flex-col rounded-2xl border-[2px] border-zinc-700 mt-[-100px] bg-[#1b1d1f]/90">

                    <div class="w-full text-start">
                        <span class="text-[10px] text-zinc-400">Avalie esse jogo</span>
                    </div>
                    <div class="w-full flex justify-start items-center">
                        <button class="py-2 px-4 rounded-xl border-[1px] border-zinc-500 text-sm cursor-pointer"
                            @click="() => { avaliacaoBoxOpen = true, statusDefault = 1 }">Avaliar</button>
                        <button class="py-2 px-4 rounded-xl border-[1px] border-zinc-500 text-xs cursor-pointer ml-4"
                            @click="() => { avaliacaoBoxOpen = true, statusDefault = 4 }">Quero
                            jogar</button>
                    </div>
                </div>
            </div>
        </div>

        <div class="py-6 px-6 text-start">
            <div class="flex items-center">
                <h2 class="text-3xl pr-2">{{ dados.name }}</h2>
                <span class="text-xl text-zinc-400 pl-2 border-l-[2px] border-zinc-400"
                    v-if="dados.first_release_date">{{
                        formataDataUnix(dados.first_release_date, 1) }}</span>

                <span class="text-xl text-zinc-400 pl-2 border-l-[2px] border-zinc-400" v-else>N/A</span>
            </div>
            <div class="w-full">
                <span class="text-[10px] text-zinc-400">{{ dados.parent_game ? "Jogo Relacionado" : "Jogo Principal"
                    }}</span>
            </div>
            <div class="w-full mt-4" v-if="!carregandoDados">
                <ul>
                    <li>
                        <span class="text-xs text-zinc-400">Data de Lançamento: </span>
                        <span class="inline text-zinc-50 text-xs" v-if="dados.first_release_date"> {{
                            formataDataUnix(dados.first_release_date, 2)
                            }}</span>
                        <span class="inline text-zinc-50 text-xs" v-else>Não encontrado</span>
                    </li>
                    <li>
                        <span class="text-xs text-zinc-400">Gêneros: </span>
                        <span class="inline text-zinc-50 text-xs"> {{ tags?.join(", ") || "Não encontrado" }}</span>
                    </li>
                    <li>
                        <span class="text-xs text-zinc-400">Plataformas: </span>
                        <span class="inline text-zinc-50 text-xs"> {{ plataformas?.join(", ") || "Não encontrado"
                            }}</span>
                    </li>
                    <li>
                        <span class="text-xs text-zinc-400">Desenvolvedores: </span>
                        <span class="inline text-zinc-50 text-xs"> {{empresas.filter(e =>
                            e.developer)?.map(e => e.name).join(", ") || "Não encontrado"}}</span>
                    </li>
                    <li>
                        <span class="text-xs text-zinc-400">Publicadoras: </span>
                        <span class="inline text-zinc-50 text-xs"> {{empresas.filter(e =>
                            e.publisher)?.map(e => e.name).join(", ") || "Não encontrado"}}</span>
                    </li>
                    <li>
                        <span class="text-xs text-zinc-400">Suporte: </span>
                        <span class="inline text-zinc-50 text-xs"> {{empresas.filter(e =>
                            e.supporting)?.map(e => e.name).join(", ") || "Não encontrado"}}</span>
                    </li>
                </ul>
            </div>

            <div class="w-full h-[300px] flex justify-center items-center" v-if="carregandoDados">
                <span class="loading loading-ring loading-xl"></span>
            </div>

            <div class="w-full mt-8" v-if="dados.summary && !carregandoDados">
                <span class="text-xs text-zinc-50 block">Sumário</span>
                <span class="text-xs text-zinc-400">{{ dados.summary }}</span>
            </div>
            <div class="w-full mt-8" v-if="dados.storyline && !carregandoDados">
                <span class="text-xs text-zinc-50 block">História</span>
                <span class="text-xs text-zinc-400">{{ dados.storyline }}</span>
            </div>

            <div class="w-full mt-8 relative">
                <div class="flex items-center">
                    <img src="../assets/Imagens/camera.svg" class="filtro-cinza w-[19px] h-auto">
                    <span class="text-zinc-50 text-xs ml-2">Capturas de tela ({{ imagens.length }})</span>
                </div>

                <div class="embla mt-2">
                    <div class="embla__viewport" ref="viewport">
                        <div class="embla__container">
                            <div class="embla__slide h-[450px] rounded-xl overflow-hidden cursor-pointer"
                                v-for="img in imagens" @click="imagemUrl = img">
                                <img :src="img" class="w-full h-full object-fit rounded-xl" />
                            </div>
                        </div>
                        <div class="absolute top-0 end-4 *:hover:bg-zinc-800">
                            <button class="embla__prev rounded-full p-[2px]" ref="embla__prev"><img
                                    src="../assets/Imagens/arrow-2.svg"
                                    class="w-[26px] h-auto cursor-pointer filtro-branco"></button>
                            <button class="embla__next ml-2 rounded-full p-[2px]" ref="embla__next"><img
                                    src="../assets/Imagens/arrow-2.svg"
                                    class="w-[26px] h-auto cursor-pointer filtro-branco -rotate-180"></button>
                        </div>
                    </div>
                </div>

            </div>

            <div class="w-full mt-8 relative">
                <div class="flex items-center">
                    <img src="../assets/Imagens/video.svg" class="filtro-cinza w-[19px] h-auto">
                    <span class="text-zinc-50 text-xs ml-2">Vídeos ({{ videos.length }})</span>
                </div>

                <div class="w-full mt-2 grid grid-cols-3 gap-4" v-if="!carregandoDados">
                    <a class="aspect-video w-full h-auto overflow-hidden rounded-lg cursor-pointer"
                        v-for="video in videos" @click="videoPlayerUrl = video.video_id">
                        <img :src="capasVideos[video.id]" class="w-full h-full object-cover">
                    </a>
                </div>

                <div class="w-full mt-2 flex justify-center items-center h-[300px]" v-else>
                    <span class="loading loading-ring loading-xl"></span>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import EmblaCarousel from 'embla-carousel'
import axios from 'axios'
import VideoPlayerComponent from './VideoPlayerComponent.vue'
import ImageComponent from './ImageComponent.vue'
import AvaliacaoBoxComponent from './AvaliacaoBoxComponent.vue'


export default {
    name: "Game",
    props: {
        id: {
            type: String,
            required: true
        },
        usuario: {
            type: BigInt,
            required: true
        }
    },
    components: {
        VideoPlayerComponent,
        ImageComponent,
        AvaliacaoBoxComponent
    },
    data() {
        return {
            imagens: [],
            videos: [],
            capasJogos: [],
            capasVideos: {},
            dados: {},
            plataformas: [],
            tags: [],
            empresas: [],
            nomeEmpresas: [],
            usuarioLogado: false,
            embla: null,
            videoPlayerUrl: "",
            imagemUrl: "",
            avaliacaoBoxOpen: false,
            statusDefault: 0,
            carregandoDados: false
        }
    },

    mounted() {
        this.carregaDados()
        this.$nextTick(() => {
            const viewportNode = this.$refs.viewport
            if (viewportNode) {
                this.embla = EmblaCarousel(viewportNode, {
                    loop: true,
                    align: 'start'
                })

                const prevButtonNode = this.$refs.embla__prev
                const nextButtonNode = this.$refs.embla__next

                prevButtonNode.addEventListener("click", this.embla.scrollPrev, false)
                nextButtonNode.addEventListener("click", this.embla.scrollNext, false)
            }
        })
        console.log("UsuarioId: " + this.usuario)
    },
    methods: {
        async carregaDados() {
            this.carregandoDados = true
            const body = `fields *; where id = ${this.id};`;

            try {
                const response = await axios.post("/v4/games", body, {
                    headers: {
                        'Accept': 'application/json',
                        'Client-ID': "i79ndcjylui2396ezi2v752sc9dze0",
                        'Authorization': `Bearer h6v8ywcqhwyyhj140u70v95rss6sga`,
                        'Content-Type': 'text/plain'
                    }
                })

                this.dados = response.data[0]

                await this.carregaImagens()
                await this.carregaCapa()
                await this.carregaTags()
                await this.carregaPlataformas()
                await this.carregaEmpresas()
                this.carregandoDados = false
                await this.carregaVideos()

            } catch (error) {
                console.error("Erro ao carregar dados do jogo: " + error)
            } finally {
                this.carregandoDados = false
            }
        },

        async carregaVideos() {
            if (!this.id) return

            const body = `fields video_id; where game = ${this.id};`;
            try {
                const response = await axios.post("/v4/game_videos", body, {
                    headers: {
                        'Accept': 'application/json',
                        'Client-ID': "i79ndcjylui2396ezi2v752sc9dze0",
                        'Authorization': `Bearer h6v8ywcqhwyyhj140u70v95rss6sga`,
                        'Content-Type': 'text/plain'
                    }
                })
                let data = response.data
                this.videos = []
                data.forEach((e) => {
                    this.videos.push(e);
                    this.capasVideos[e.id] = `https://i.ytimg.com/vi/${e.video_id}/hqdefault.jpg`
                    console.log(e.video_id)
                })

            } catch (error) {
                console.error("Erro ao carregar screenshots: " + error)
            }
        },

        async carregaImagens() {
            if (!this.id) return

            const body = `fields url; where game = ${this.id};`;
            try {
                const response = await axios.post("/v4/screenshots", body, {
                    headers: {
                        'Accept': 'application/json',
                        'Client-ID': "i79ndcjylui2396ezi2v752sc9dze0",
                        'Authorization': `Bearer h6v8ywcqhwyyhj140u70v95rss6sga`,
                        'Content-Type': 'text/plain'
                    }
                })
                let data = response.data
                this.imagens = data.map(d => d.url.replace("thumb", "1080p"))

            } catch (error) {
                console.error("Erro ao carregar screenshots: " + error)
            }
        },

        async carregaCapa() {
            const body = `fields url; where game = ${this.id}; limit 1;`
            try {
                const response = await axios.post("/v4/covers", body, {
                    headers: {
                        'Accept': 'application/json',
                        'Client-ID': "i79ndcjylui2396ezi2v752sc9dze0",
                        'Authorization': `Bearer h6v8ywcqhwyyhj140u70v95rss6sga`,
                        'Content-Type': 'text/plain'
                    }
                })
                let imagem = response.data[0]
                if (imagem && imagem.url) {
                    this.capasJogos[this.id] = imagem.url.replace("thumb", "720p")
                }

            } catch (error) {
                console.error("Erro carregando imagem: " + error)
            }
        },

        async carregaTags() {
            let ids = []

            for (let genreId of this.dados.genres) {
                ids.push(genreId)
            }

            const body = `fields name; where id = (${ids.join(", ")}); limit 5;`
            try {
                const response = await axios.post("/v4/genres", body, {
                    headers: {
                        'Accept': 'application/json',
                        'Client-ID': "i79ndcjylui2396ezi2v752sc9dze0",
                        'Authorization': `Bearer h6v8ywcqhwyyhj140u70v95rss6sga`,
                        'Content-Type': 'text/plain'
                    }
                })

                for (let data of response.data) {
                    this.tags.push(data.name)
                }

            } catch (error) {
                console.error("Erro carregando generos: " + error)
            }
        },

        async carregaPlataformas() {
            this.plataformas = []
            let platformIds = []
            for (let platformId of this.dados.platforms) {
                platformIds.push(platformId)
            }

            const body = `fields name; where id = (${platformIds.join(", ")});`

            try {
                const response = await axios.post("/v4/platforms", body, {
                    headers: {
                        'Accept': 'application/json',
                        'Client-ID': "i79ndcjylui2396ezi2v752sc9dze0",
                        'Authorization': `Bearer h6v8ywcqhwyyhj140u70v95rss6sga`,
                        'Content-Type': 'text/plain'
                    }
                })

                for (let data of response.data) {
                    this.plataformas.push(data.name)
                }

            } catch (error) {
                console.error("Erro carregando plataformas: " + error)
            }
        },

        async carregaEmpresas() {
            this.empresas = []
            const body = `fields company, developer, porting, publisher, supporting; where game = ${this.id};`
            try {
                const response = await axios.post("/v4/involved_companies", body, {
                    headers: {
                        'Accept': 'application/json',
                        'Client-ID': "i79ndcjylui2396ezi2v752sc9dze0",
                        'Authorization': `Bearer h6v8ywcqhwyyhj140u70v95rss6sga`,
                        'Content-Type': 'text/plain'
                    }
                })

                for (let data of response.data) {
                    this.empresas.push(data)
                }

                await this.carregaNomesEmpresas(this.empresas.map(e => e.company))

            } catch (error) {
                console.error("Erro carregando empresas: " + error)
            }
        },

        async carregaNomesEmpresas(ids) {
            const body = `fields name; where id = (${ids.join(", ")});`

            try {
                const response = await axios.post("/v4/companies", body, {
                    headers: {
                        'Accept': 'application/json',
                        'Client-ID': "i79ndcjylui2396ezi2v752sc9dze0",
                        'Authorization': `Bearer h6v8ywcqhwyyhj140u70v95rss6sga`,
                        'Content-Type': 'text/plain'
                    }
                })


                for (let data of response.data) {
                    this.empresas.filter(e => e.company == data.id).forEach(e => e.name = data.name)
                }

            } catch (error) {
                console.error("Erro carregando nomes das empresas: " + error)
            }
        },

        formataDataUnix(dataUnix, tipo) {
            const timestamp = dataUnix;
            const data = new Date(timestamp * 1000);
            const options = tipo == 1
                ? { year: 'numeric' }
                : { day: '2-digit', month: '2-digit', year: 'numeric' };
            return data.toLocaleString('pt-BR', options)
        },
    },
    computed: {
        dataAtualUnix() {
            return Math.floor(Date.now() / 1000)
        }
    },
    watch: {
        id() {
            this.carregaDados()
        }
    }
}
</script>

<style scoped>
.filtro-branco {
    filter: brightness(0) saturate(100%) invert(100%) sepia(0%) saturate(0%) hue-rotate(168deg) brightness(103%) contrast(103%);
}

.gradiente-cinza-transparente {
    background: rgb(237, 237, 237);
    background: linear-gradient(180deg, rgba(237, 237, 237, 0) 0%, rgba(27, 29, 31, 1) 85%);
}

.embla {
    overflow: hidden;
}

.embla__viewport {
    overflow: hidden;
    width: 100%;
}

.embla__container {
    display: flex;
}

.embla__slide {
    position: relative;
    flex: 0 0 100%;
    padding: 10px;

}

.filtro-cinza {
    filter: brightness(0) saturate(100%) invert(77%) sepia(7%) saturate(302%) hue-rotate(202deg) brightness(82%) contrast(87%);
}
</style>