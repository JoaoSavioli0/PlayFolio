<template>
    <VideoPlayerComponent v-if="videoPlayerUrl" :url="videoPlayerUrl" @fechar-video="videoPlayerUrl = ''" />
    <ImageComponent v-if="imagemUrl" :url="imagemUrl" @fechar-imagem="imagemUrl = ''" />
    <AvaliacaoBoxComponent v-if="avaliacaoBoxOpen" :dados="dados" :imagem="imagens[0]" :statusDefault="statusDefault"
        :usuario="usuario" :capa="capasJogos[id]" @fecha-avaliacaoBox="recarregaDados"
        :avaliacaoUsuario="reviewDoUsuario" />

    <div class="w-screen h-screen bg-black/50 flex justify-center items-center top-0 start-0 fixed z-[400]"
        v-if="boxSucessoExclusao">
        <div class="bg-zinc-800 rounded-lg px-6 w-[400px] py-12">
            <h2 class="text-zinc-50 text-center">Review excluída!</h2>
            <div class="flex w-full justify-center mt-6">
                <button @click="() => { console.log(boxSucessoExclusao); boxSucessoExclusao = false }"
                    class="w-[40%] rounded-md bg-zinc-700 text-zinc-50 h-[50px] text-sm cursor-pointer">Ok</button>
            </div>
        </div>
    </div>

    <div class="w-full h-full pb-6 pt-4 xl:px-24 px-4 flex xl:flex-row flex-col justify-center xl:gap-x-4">
        <MenuMobileComponent />
        <MenuComponent />
        <div
            class="xl:w-[820px] w-full h-min rounded-2xl border-[1px] border-zinc-600 overflow-hidden flex flex-col bg-[#1B1D1F]">
            <div class="w-full xl:h-[450px] h-[210px] z-40 relative">

                <button @click="voltarRota"
                    class="w-[90px] py-1 bg-zinc-50 px-2 rounded-full flex justify-around items-center cursor-pointer absolute top-[10px] start-[10px] min-xl:hidden">
                    <img src="../assets/Imagens/arrow.svg" class="w-[21px] h-auto">
                    <span class="text-xs text-[#1b1d1f]">Voltar</span>
                </button>

                <img :src="imagens[0]" class="w-full h-full" v-if="!carregandoDados && imagens[0]">

                <div class="w-full h-full flex justify-center items-center" v-if="!carregandoDados && !imagens[0]">
                    <img src="../assets/Imagens/no_image.png" class="w-[40px] h-auto filtro-cinza">
                </div>

                <div class="w-full h-full flex justify-center items-center" v-if="carregandoDados">
                    <span class="loading loading-ring loading-xl"></span>
                </div>
                <div class="absolute bottom-0 w-full h-[25%] gradiente-cinza-transparente"></div>
            </div>
            <div class="w-full z-50 flex px-2 xl:flex-row flex-col">

                <!-- Caixa esquerda -->
                <div class="xl:w-max w-full xl:pl-4 xl:pr-2 px-2">
                    <div
                        class="w-full h-[105px] px-4 flex items-center rounded-2xl border-[2px] border-zinc-700 xl:mt-[-100px] mt-[-50px] bg-[#1b1d1f]/60">

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

                <!-- Não está logado -->
                <div class="xl:w-1/2 w-full xl:pl-4 xl:pr-2 px-2 max-xl:mt-4" v-if="!usuarioLogado">
                    <div
                        class="w-full h-[105px] px-4 py-2 flex justify-around flex-col rounded-2xl border-[2px] border-zinc-700 xl:mt-[-100px] bg-[#1b1d1f]/60 relative overflow-hidden">

                        <div class="w-full text-start">
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

                        <img src="../assets/Imagens/ghost.svg"
                            class="absolute end-[-40px] xl:bottom-[-40px] bottom-[-20px] xl:w-[150px] w-[120px] h-auto opacity-[0.5] z-10 brilhoCinza filtro-cinza rotate-[-35deg]">
                    </div>
                </div>

                <!-- Está logado mas não tem review -->
                <div class="xl:w-1/2 w-full xl:pl-4 xl:pr-2 px-2 max-xl:mt-4" v-else-if="usuario && !reviewDoUsuario">
                    <div
                        class="w-full relative h-[105px] px-4 py-2 flex justify-around flex-col rounded-2xl border-[2px] border-zinc-700 xl:mt-[-100px] bg-[#1b1d1f]/90 overflow-hidden">

                        <div class="w-full text-start z-20">
                            <span class="text-[12px] text-zinc-200">
                                {{ jogoLancado ? "Avalie esse jogo" : "Adicione à lista de desejos" }}</span>
                            <span v-if="estaNaWishlist" class="block text-[10px] text-zinc-400">Esse jogo já esta na sua
                                wishlist!</span>
                        </div>
                        <div class="w-full flex justify-start items-center z-20" :class="{ 'mt-2': estaNaWishlist }">
                            <button class="py-2 px-4 rounded-xl border-[1px] border-zinc-500 text-sm cursor-pointer"
                                @click="() => { avaliacaoBoxOpen = true, statusDefault = 1 }"
                                v-if="jogoLancado">Avaliar</button>
                            <button class="py-2 px-4 rounded-xl border-[1px] border-zinc-500 text-sm cursor-pointer"
                                v-if="!estaNaWishlist" :class="{ 'ml-2': jogoLancado }"
                                @click="() => { avaliacaoBoxOpen = true, statusDefault = jogoLancado ? 4 : 5 }">Quero
                                jogar</button>

                            <router-link :to="{ path: `/account/profile/${usuario.usuario}`, query: { filtro: 5 } }"
                                class="p-0" v-else>
                                <button class="py-2 px-4 rounded-xl border-[1px] border-zinc-500 text-sm cursor-pointer"
                                    :class="{ 'ml-2': jogoLancado }"
                                    @click="() => { avaliacaoBoxOpen = true, statusDefault = jogoLancado ? 4 : 5 }">Ver
                                    wishlist</button>
                            </router-link>
                        </div>

                        <img src="../assets/Imagens/expressoes/6.png" v-if="estaNaWishlist"
                            class="absolute end-[-40px] xl:bottom-[-40px] bottom-[-20px] xl:w-[150px] w-[120px] h-auto opacity-[0.5] z-10 brilhoCinza">
                        <img src="../assets/Imagens/expressoes/1.png" v-else
                            class="absolute end-[-40px] xl:bottom-[-40px] bottom-[-20px] xl:w-[150px] w-[120px] h-auto opacity-[0.5] z-10 brilhoCinza">
                    </div>
                </div>

                <!-- Está logado e tem review -->
                <div class="xl:w-1/2 w-full xl:pl-4 xl:pr-2 px-2 max-xl:mt-4 cursor-pointer"
                    v-else-if="usuario && reviewDoUsuario"
                    @click="() => { avaliacaoBoxOpen = true, statusDefault = reviewDoUsuario.status }">
                    <div
                        class="w-full h-[105px] px-2 py-2 flex justify-around flex-col rounded-2xl border-[2px] border-zinc-700 xl:mt-[-100px] bg-[#1b1d1f]/90 relative overflow-hidden">

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
                            class="absolute xl:end-[-40px] end-[-35px] xl:bottom-[-40px] bottom-[-20px] xl:w-[150px] w-[100px] h-auto opacity-[0.5] z-10 brilhoCinza">
                        <img src="../assets/Imagens/expressoes/2.png"
                            v-if="reviewDoUsuario.nota > 0 && reviewDoUsuario.nota < 4"
                            class="absolute xl:end-[-40px] end-[-35px] xl:bottom-[-40px] bottom-[-20px] xl:w-[150px] w-[100px] h-auto opacity-[0.5] z-10 brilhoVermelho">
                        <img src="../assets/Imagens/expressoes/3.png"
                            v-if="reviewDoUsuario.nota >= 4 && reviewDoUsuario.nota < 7"
                            class="absolute xl:end-[-40px] end-[-35px] xl:bottom-[-40px] bottom-[-20px] xl:w-[150px] w-[100px] h-auto opacity-[0.5] z-10 brilhoAmarelo">
                        <img src="../assets/Imagens/expressoes/4.png"
                            v-if="reviewDoUsuario.nota >= 7 && reviewDoUsuario.nota < 9"
                            class="absolute xl:end-[-40px] end-[-35px] xl:bottom-[-40px] bottom-[-20px] xl:w-[150px] w-[100px] h-auto opacity-[0.5] z-10 brilhoAzul">
                        <img src="../assets/Imagens/expressoes/5.png" v-if="reviewDoUsuario.nota >= 9"
                            class="absolute xl:end-[-40px] end-[-35px] xl:bottom-[-40px] bottom-[-20px] xl:w-[150px] w-[100px] h-auto opacity-[0.5] z-10 brilhoRoxo">
                    </div>
                </div>
            </div>

            <div class="py-6 px-6 text-start">
                <div class="flex items-center">
                    <h2 class="xl:text-3xl text-xl pr-2 xl:max-w-[70%] w-fit line-clamp-3">{{ dados.name }}
                    </h2>
                    <span class="xl:text-xl text-md text-zinc-400 pl-2 border-l-[2px] border-zinc-400"
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
                            <span class="loading loading-dots loading-xs"
                                v-else-if="!dados.first_release_date && carregandoInformacoesAdicionais"></span>
                            <span class="inline text-zinc-50 text-xs" v-else>Não encontrado</span>
                        </li>
                        <li>
                            <span class="text-xs text-zinc-400">Gêneros: </span>
                            <span class="loading loading-dots loading-xs"
                                v-if="!tags.length && carregandoInformacoesAdicionais"></span>
                            <span class="inline text-zinc-50 text-xs" v-else> {{ tags?.join(", ") || "Não encontrado"
                            }}</span>
                        </li>
                        <li>
                            <span class="text-xs text-zinc-400">Plataformas: </span>
                            <span class="loading loading-dots loading-xs"
                                v-if="!plataformas.length && carregandoInformacoesAdicionais"></span>
                            <span class="inline text-zinc-50 text-xs" v-else>
                                {{ plataformas?.join(", ") || "Não encontrado"
                                }}</span>
                        </li>
                        <li>
                            <span class="text-xs text-zinc-400">Desenvolvedores: </span>
                            <span class="loading loading-dots loading-xs"
                                v-if="!empresas.length && carregandoInformacoesAdicionais"></span>
                            <span class="inline text-zinc-50 text-xs" v-else> {{empresas.filter(e =>
                                e.developer)?.map(e => e.name).join(", ") || "Não encontrado"}}</span>
                        </li>
                        <li>
                            <span class="text-xs text-zinc-400">Publicadoras: </span>
                            <span class="loading loading-dots loading-xs"
                                v-if="!empresas.length && carregandoInformacoesAdicionais"></span>
                            <span class="inline text-zinc-50 text-xs" v-else> {{empresas.filter(e =>
                                e.publisher)?.map(e => e.name).join(", ") || "Não encontrado"}}</span>
                        </li>
                        <li>
                            <span class="text-xs text-zinc-400">Suporte: </span>
                            <span class="loading loading-dots loading-xs"
                                v-if="!empresas.length && carregandoInformacoesAdicionais"></span>
                            <span class="inline text-zinc-50 text-xs" v-else> {{empresas.filter(e =>
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
                                <div class="embla__slide aspect-video rounded-xl overflow-hidden cursor-pointer"
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
                    <div class="w-full flex flex-col pb-3 border-b border-zinc-800 " v-for="review in reviews">

                        <!-- Não tem texto -->
                        <div class="w-full">
                            <div class="w-full flex justify-between relative items-center">
                                <router-link :to="`/account/profile/${review.usuario.usuario}`"
                                    class="flex gap-x-2 cursor-pointer">
                                    <div
                                        class="xl:size-[32px] size-[28px] bg-zinc-800 text-black rounded-full flex items-center justify-center overflow-hidden">
                                        <img :src="`data:image/png;base64,${review.usuario.imagem}`"
                                            class="w-full h-full object-cover" v-if="review.usuario.imagem">
                                        <h1 class="text-[12px] text-zinc-50" v-else>{{
                                            primeiraLetraUsuario(review.usuario.usuario) }}</h1>
                                    </div>
                                    <div class="flex flex-col justify-center">
                                        <h2 class="text-zinc-50 xl:text-xs text-[11px]">{{ review.usuario.nome }}</h2>
                                        <span class="xl:text-[10px] text-[8px] text-zinc-500">@{{ review.usuario.usuario
                                            }}</span>
                                    </div>
                                </router-link>

                                <div class="flex items-center">
                                    <div class="w-fit flex flex-col items-center justify-center">
                                        <h1 class="xl:text-2xl text-xl" :class="{
                                            'degradeVermelho': review.nota < 4,
                                            'degradeAmarelo': review.nota >= 4 && review.nota < 7,
                                            'degradeAzul': review.nota >= 7 && review.nota < 9,
                                            'degradeRoxo': review.nota >= 9
                                        }">
                                            {{ review.nota }}
                                        </h1>
                                        <!-- Status -->
                                        <div class="w-full border rounded-md flex items-center justify-center py-[2px] xl:px-2 px-[15px]"
                                            :class="{
                                                'border-[#c31ef1]': review.status == 1,
                                                'border-zinc-300': review.status == 2,
                                                'border-[#d22d56]': review.status == 3,
                                            }">
                                            <span class="text-[#c31ef1] text-[8px] xl:text-[10px]"
                                                v-if="review.status == 1">Jogando</span>
                                            <span class="text-zinc-300 text-[8px] xl:text-[10px]"
                                                v-else-if="review.status == 2">Concluído</span>
                                            <span class="text-[#d22d56] text-[8px] xl:text-[10px]"
                                                v-else-if="review.status == 3">Dropado</span>
                                        </div>
                                    </div>

                                    <SubmenuReviewComponent :idUsuario="usuario.id" :review="review"
                                        @recarrega-dados="recarregaDados" />
                                </div>
                            </div>
                            <div class="w-full mt-2" v-if="review.review">
                                <ReviewTextComponent :usuario="usuario" :review="review" />
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import EmblaCarousel from 'embla-carousel'
import VideoPlayerComponent from '@/components/VideoPlayerComponent.vue'
import ReviewTextComponent from '@/components/ReviewTextComponent.vue'
import ImageComponent from '@/components/ImageComponent.vue'
import AvaliacaoBoxComponent from '@/components/AvaliacaoBoxComponent.vue'
import SubmenuReviewComponent from '@/components/SubmenuReviewComponent.vue'
import { useUserStore } from '@/stores/UserStore'
import MenuComponent from '@/components/MenuComponent.vue'
import { useTwitchTokenStore } from '@/stores/TwitchTokenStore'
import MenuMobileComponent from '@/components/MenuMobileComponent.vue'
import { api } from '@/services/api'

export default {
    name: "Game",
    props: {
        id: {
            type: String,
            required: true
        },
        avaliacaoBoxOpenDefault: {
            type: Boolean,
        }
    },
    components: {
        VideoPlayerComponent,
        ImageComponent,
        AvaliacaoBoxComponent,
        SubmenuReviewComponent,
        MenuComponent,
        MenuMobileComponent,
        ReviewTextComponent,
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
            carregandoInformacoesAdicionais: false,
            reviews: [],
            reviewDoUsuario: {},
            reviewMapping: {
                1: 'Jogando',
                2: 'Zerado',
                3: 'Dropado',
                4: 'Quero jogar'
            },
            exibeSub: false,
            twitchToken: useTwitchTokenStore(),
            estaNaWishlist: false,
            boxSucessoExclusao: false,
        }
    },

    async mounted() {
        if (this.twitchToken.access_token == '') await this.twitchToken.buscaToken()
        if (this.userStore.usuario != null) {
            this.usuario = this.userStore.usuario
        } else {
            await this.userStore.reconectaSessao()
            if (this.userStore.usuario != null) {
                this.usuario = this.userStore.usuario
            }
        }
        this.usuarioLogado = this.userStore.usuario ? true : false

        this.carregaDados()
        this.carregaReviewDoUsuario()
        this.verificaSeEstaNaWishlist()
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

        if (this.usuarioLogado && this.avaliacaoBoxOpenDefault) this.avaliacaoBoxOpen = true
    },
    methods: {
        voltarRota() {
            if (window.history.length > 1) {
                this.$router.back()
            } else {
                this.$router.push('/')
            }
        },
        async verificaSeEstaNaWishlist() {
            try {
                const response = await api.get(`/wishlist/get?idUsuario=${this.usuario.id}&idJogo=${this.id}`)
                this.estaNaWishlist = response.data
            } catch (error) {
                console.log("Erro ao verificar se jogo está na wishlist do usuário: ", error)
            }
        },
        async carregaDados() {
            this.carregandoDados = true
            this.carregandoInformacoesAdicionais = true
            const body = `fields name, first_release_date, parent_game,summary, storyline, genres, platforms; where id = ${this.id};`;

            try {
                const response = await api.post("/api/igdb/proxy", body, {
                    headers: {
                        "igdb-endpoint": "/v4/games",
                        "Content-Type": "text/plain"
                    }
                })

                this.dados = response.data[0]

                await this.carregaImagens()
                await this.carregaCapa()
                this.carregandoDados = false
                await this.carregaTags()
                await this.carregaPlataformas()
                await this.carregaEmpresas()
                await this.carregaVideos()
                await this.carregaReviews()
                this.carregandoInformacoesAdicionais = false

            } catch (error) {
                console.error("Erro ao carregar dados do jogo: " + error)
            } finally {
                this.carregandoDados = false
                console.log(this.dados)
            }
        },

        async carregaReviewDoUsuario() {
            if (!this.id || !this.usuarioLogado) return

            try {
                const response = await api.get(`/review/get?idJogo=${this.id}&idUsuario=${this.usuario.id}`)
                this.reviewDoUsuario = response.data
                console.log(this.reviewDoUsuario)
            } catch (error) {
                console.log("Erro ao carregar avaliação de usuário: ", error)
            }
        },

        async carregaReviews() {
            if (!this.id) return

            try {
                const response = await api.get(`/review/get/${this.id}`)
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
                const response = await api.post("/api/igdb/proxy", body, {
                    headers: {
                        "igdb-endpoint": "/v4/game_videos",
                        "Content-Type": "text/plain"
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
                const response = await api.post("/api/igdb/proxy", body, {
                    headers: {
                        "igdb-endpoint": "/v4/screenshots",
                        "Content-Type": "text/plain"
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
                const response = await api.post("/api/igdb/proxy", body, {
                    headers: {
                        "igdb-endpoint": "/v4/covers",
                        "Content-Type": "text/plain"
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
                const response = await api.post("/api/igdb/proxy", body, {
                    headers: {
                        "igdb-endpoint": "/v4/genres",
                        "Content-Type": "text/plain"
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
                const response = await api.post("/api/igdb/proxy", body, {
                    headers: {
                        "igdb-endpoint": "/v4/platforms",
                        "Content-Type": "text/plain"
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
                const response = await api.post("/api/igdb/proxy", body, {
                    headers: {
                        "igdb-endpoint": "/v4/involved_companies",
                        "Content-Type": "text/plain"
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
                const response = await api.post("/api/igdb/proxy", body, {
                    headers: {
                        "igdb-endpoint": "/v4/companies",
                        "Content-Type": "text/plain"
                    }
                })

                for (let data of response.data) {
                    this.empresas.filter(e => e.company == data.id).forEach(e => e.name = data.name)
                }

            } catch (error) {
                console.error("Erro carregando nomes das empresas: " + error)
            }
        },

        recarregaDados(acao) {
            this.avaliacaoBoxOpen = false
            this.verificaSeEstaNaWishlist()
            this.carregaReviewDoUsuario()
            this.carregaReviews()
            if (acao == "Exclusao") this.boxSucessoExclusao = true
        },

        formataDataUnix(dataUnix, tipo) {
            const timestamp = dataUnix;
            const data = new Date(timestamp * 1000);
            const options = tipo == 1
                ? { year: 'numeric' }
                : { day: '2-digit', month: '2-digit', year: 'numeric' };
            return data.toLocaleString('pt-BR', options)
        },
        primeiraLetraUsuario(usuario) {
            return usuario.charAt(0).toUpperCase() || ''
        }
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