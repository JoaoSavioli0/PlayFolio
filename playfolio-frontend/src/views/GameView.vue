<template>
    <VideoPlayerComponent v-if="videoPlayerUrl" :url="videoPlayerUrl" @fechar-video="videoPlayerUrl = ''" />
    <ImageComponent v-if="imagemUrl" :url="imagemUrl" @fechar-imagem="imagemUrl = ''" />
    <AvaliacaoBoxComponent v-if="avaliacaoBoxOpen" :dados="dados" :imagem="imagens[0]" :statusDefault="statusDefault"
        :usuario="usuario" :capa="capasJogos[id]" @fecha-avaliacaoBox="recarregaDados"
        :avaliacaoUsuario="reviewDoUsuario" />
    <div class="w-full h-full pb-6 pt-4 px-24 flex justify-center gap-x-4">
        <MenuComponent />
        <div
            class="w-[820px] h-min rounded-2xl border-[1px] border-zinc-600 overflow-hidden flex flex-col bg-[#1B1D1F]">
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
                <div class="w-max pl-4 pr-2" v-if="!usuarioLogado">
                    <div
                        class="w-full h-[105px] px-4 py-2 flex justify-around flex-col rounded-2xl border-[2px] border-zinc-700 mt-[-100px] bg-[#1b1d1f]/60">

                        <div class="w-full">
                            <span class="text-[10px] text-zinc-400">Crie uma conta e salve seus jogos favoritos</span>
                        </div>
                        <div class="w-full flex justify-start items-center">
                            <router-link to="/account/login" class="p-0">
                                <button
                                    class="py-2 px-4 rounded-xl border-[1px] border-zinc-500 text-sm cursor-pointer">Login</button>
                                <span class="px-4 text-zinc-400 text-sm">Ou</span>
                            </router-link>
                            <router-link to="/account/registro" class="p-0">
                                <button
                                    class="p-2 px-4 rounded-xl border-[1px] border-zinc-500 text-sm cursor-pointer">Cadastre-se</button>
                            </router-link>
                        </div>
                    </div>
                </div>

                <div class="w-max pl-4 pr-2" v-else-if="usuario && !reviewDoUsuario">
                    <div
                        class="w-full h-[105px] px-4 py-2 flex justify-around flex-col rounded-2xl border-[2px] border-zinc-700 mt-[-100px] bg-[#1b1d1f]/90">

                        <div class="w-full text-start">
                            <span class="text-[10px] text-zinc-400">
                                {{ jogoLancado ? "Avalie esse jogo" : "Adicione à lista de desejos" }}</span>
                        </div>
                        <div class="w-full flex justify-start items-center">
                            <button class="py-2 px-4 rounded-xl border-[1px] border-zinc-500 text-sm cursor-pointer"
                                @click="() => { avaliacaoBoxOpen = true, statusDefault = 1 }"
                                v-if="jogoLancado">Avaliar</button>
                            <button
                                class="py-2 px-4 rounded-xl border-[1px] border-zinc-500 text-xs cursor-pointer ml-4"
                                @click="() => { avaliacaoBoxOpen = true, statusDefault = jogoLancado ? 4 : 5 }">Quero
                                jogar</button>
                        </div>
                    </div>
                </div>

                <div class="w-[50%] pl-4 pr-2 cursor-pointer" v-else-if="usuario && reviewDoUsuario"
                    @click="() => { avaliacaoBoxOpen = true, statusDefault = reviewDoUsuario.status }">
                    <div
                        class="w-full h-[105px] px-2 py-2 flex justify-around flex-col rounded-2xl border-[2px] border-zinc-700 mt-[-100px] bg-[#1b1d1f]/90 relative overflow-hidden">

                        <div class="w-full flex items-center text-start z-20">
                            <div class="px-4">
                                <span class="text-xs text-zinc-500">Status</span>
                                <h2 class="text-2xl mt-1">{{ reviewMapping[reviewDoUsuario.status] }}</h2>
                            </div>
                            <div class="border-l border-zinc-700 px-4">
                                <span class="text-xs text-zinc-500">Sua nota</span>
                                <h1 class="text-3xl mt-1 w-fit" :class="{
                                    'degradeVermelho': reviewDoUsuario.nota < 4,
                                    'degradeAmarelo': reviewDoUsuario.nota >= 4 && reviewDoUsuario.nota < 7,
                                    'degradeAzul': reviewDoUsuario.nota >= 7 && reviewDoUsuario.nota < 9,
                                    'degradeRoxo': reviewDoUsuario.nota >= 9
                                }">{{ reviewDoUsuario.nota }}</h1>
                            </div>
                        </div>

                        <img src="../assets/Imagens/expressoes/1.png" v-if="reviewDoUsuario.nota == 0"
                            class="absolute end-[-40px] bottom-[-40px] w-[150px] h-auto opacity-[0.5] z-10 brilhoCinza">
                        <img src="../assets/Imagens/expressoes/2.png"
                            v-if="reviewDoUsuario.nota > 0 && reviewDoUsuario.nota < 4"
                            class="absolute end-[-40px] bottom-[-40px] w-[150px] h-auto opacity-[0.5] z-10 brilhoVermelho">
                        <img src="../assets/Imagens/expressoes/3.png"
                            v-if="reviewDoUsuario.nota >= 4 && reviewDoUsuario.nota < 7"
                            class="absolute end-[-40px] bottom-[-40px] w-[150px] h-auto opacity-[0.5] z-10 brilhoAmarelo">
                        <img src="../assets/Imagens/expressoes/4.png"
                            v-if="reviewDoUsuario.nota >= 7 && reviewDoUsuario.nota < 9"
                            class="absolute end-[-40px] bottom-[-40px] w-[150px] h-auto opacity-[0.5] z-10 brilhoAzul">
                        <img src="../assets/Imagens/expressoes/5.png" v-if="reviewDoUsuario.nota >= 9"
                            class="absolute end-[-40px] bottom-[-40px] w-[150px] h-auto opacity-[0.5] z-10 brilhoRoxo">
                    </div>
                </div>
            </div>

            <div class="py-6 px-6 text-start">
                <div class="flex items-center">
                    <h2 class="text-3xl pr-2 max-w-[70%] line-clamp-3">{{ dados.name }}</h2>
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

                <div class="w-full mt-8 relative">
                    <div class="flex items-center">
                        <img src="../assets/Imagens/message.svg" class="filtro-cinza w-[19px] h-auto">
                        <span class="text-zinc-50 text-xs ml-2">Reviews ({{ reviews.length }})</span>
                    </div>
                </div>

                <div class="w-full grid grid-cols-1 gap-4 mt-6">
                    <div class="w-full flex flex-col pb-8 border-b border-zinc-800 " v-for="review in reviews">
                        <!-- Dados usuário -->
                        <div class="w-full">
                            <div class="flex gap-x-2">
                                <div class="rounded-full size-[40px] bg-zinc-400"></div>
                                <div class="flex flex-col justify-center">
                                    <h2 class="text-zinc-50 text-sm">{{ review.usuario.nome }}</h2>
                                    <span class="text-[10px] text-zinc-500">@{{ review.usuario.usuario }}</span>
                                </div>
                            </div>
                        </div>

                        <!-- Review  -->
                        <div class="w-full flex mt-2">
                            <!-- Nota -->
                            <div class="flex flex-col items-center gap-y-2 pr-4 border-r min-w-[100px]">
                                <h1 class="text-4xl" :class="{
                                    'degradeVermelho': review.nota < 4,
                                    'degradeAmarelo': review.nota >= 4 && review.nota < 7,
                                    'degradeAzul': review.nota >= 7 && review.nota < 9,
                                    'degradeRoxo': review.nota >= 9
                                }">
                                    {{ review.nota }}
                                </h1>
                                <!-- Status -->
                                <div class="w-full border rounded-md flex items-center justify-center py-[2px] px-2"
                                    :class="{
                                        'border-[#c31ef1]': review.status == 1,
                                        'border-zinc-300': review.status == 2,
                                        'border-[#d22d56]': review.status == 3,
                                    }">
                                    <span class="text-zinc-300 text-[10px]" v-if="review.status == 1">Jogando</span>
                                    <span class="text-zinc-300 text-[10px]"
                                        v-else-if="review.status == 2">Concluído</span>
                                    <span class="text-[#d22d56] text-[10px]"
                                        v-else-if="review.status == 3">Dropado</span>
                                </div>
                            </div>
                            <!-- Texto -->
                            <div class="pl-4 text-zinc-400 text-xs">
                                {{ review.review }}
                            </div>

                            <SubmenuReviewComponent :idUsuario="usuario.id" :review="review"
                                @recarrega-dados="recarregaDados" />
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import EmblaCarousel from 'embla-carousel'
import axios from 'axios'
import VideoPlayerComponent from '@/components/VideoPlayerComponent.vue'
import ImageComponent from '@/components/ImageComponent.vue'
import AvaliacaoBoxComponent from '@/components/AvaliacaoBoxComponent.vue'
import SubmenuReviewComponent from '@/components/SubmenuReviewComponent.vue'
import { useUserStore } from '@/stores/UserStore'
import MenuComponent from '@/components/MenuComponent.vue'

export default {
    name: "Game",
    props: {
        id: {
            type: String,
            required: true
        },
    },
    components: {
        VideoPlayerComponent,
        ImageComponent,
        AvaliacaoBoxComponent,
        SubmenuReviewComponent,
        MenuComponent
    },
    data() {
        return {
            userStore: useUserStore(),
            usuario: {},
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
            carregandoDados: false,
            reviews: [],
            reviewDoUsuario: {},
            reviewMapping: {
                1: 'Jogando',
                2: 'Zerado',
                3: 'Dropado',
                4: 'Quero jogar'
            },
            exibeSub: false,
        }
    },

    async mounted() {

        if (this.userStore.usuario != null) {
            this.usuario = this.userStore.usuario
        } else {
            await this.userStore.reconectaSessao()
            if (this.userStore.usuario != null) {
                this.usuario = this.userStore.usuario
            }
        }
        this.usuarioLogado = this.userStore.usuario ? true : false
        console.log("usuarioLogado: " + this.usuarioLogado + "\nUsuario: ", this.usuario.id != null)
        this.carregaDados()
        this.carregaReviewDoUsuario()
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
                await this.carregaReviews()

            } catch (error) {
                console.error("Erro ao carregar dados do jogo: " + error)
            } finally {
                this.carregandoDados = false
                console.log(this.dados)
            }
        },

        async carregaReviewDoUsuario() {
            console.log("Id: ", this.id, "\nusuario: ", this.usuario, "\nUsuario logado: ", this.usuarioLogado)
            if (!this.id || !this.usuarioLogado) return
            try {
                const response = await axios.get(`http://localhost:5000/review/get?idJogo=${this.id}&idUsuario=${this.usuario.id}`)
                this.reviewDoUsuario = response.data
                console.log(this.reviewDoUsuario)
            } catch (error) {
                console.log("Erro ao carregar avaliação de usuário: ", error)
            }
        },

        async carregaReviews() {
            if (!this.id) return

            try {
                const response = await axios.get(`http://localhost:5000/review/get/${this.id}`)
                this.reviews = response.data
                console.log("Reviews: ", this.reviews)
            } catch (error) {
                console.log("Erro ao carregar reviews: ", error)
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

        recarregaDados() {
            this.avaliacaoBoxOpen = false
            this.carregaReviewDoUsuario()
            this.carregaReviews()
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
        },
        jogoLancado() {
            return this.dados.first_release_date < this.dataAtualUnix
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
.degradeVermelho {
    background: #FF0000;
    background: linear-gradient(to right, #FF0000 0%, #FF9500 100%);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
}

.degradeAmarelo {
    background: #ECE80D;
    background: linear-gradient(to right, #ECE80D 35%, #FF9604 100%);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
}

.degradeAzul {
    background: #10AAD5;
    background: linear-gradient(to right, #10AAD5 0%, #0BFBFF 100%);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
}

.degradeRoxo {
    background: #DB1BEC;
    background: linear-gradient(to right, #DB1BEC 35%, #FF09FB 100%);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
}

.brilhoAzul {
    filter: drop-shadow(0px 0px 26px #10AAD5);
}

.brilhoCinza {
    filter: drop-shadow(0px 0px 26px #878787);
}

.brilhoVermelho {
    filter: drop-shadow(0px 0px 26px #c90303);
}

.brilhoAmarelo {
    filter: drop-shadow(0px 0px 26px #e38a0d);
}

.brilhoRoxo {
    filter: drop-shadow(0px 0px 26px #a800b8);
}

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