<template>
    <div class="w-full h-full pb-6 pt-4 xl:px-24 px-4 flex xl:flex-row flex-col justify-center gap-x-4">
        <MenuComponent :selected="2" @recarrega-usuario="carregaUsuario" />
        <MenuMobileComponent :selected="2" @recarrega-usuario="carregaUsuario" />
        <div class="xl:w-[820px] w-full xl:h-min max-xl:min-h-screen flex flex-col">
            <div
                class="w-full h-min rounded-xl pt-4 border-[1px] border-zinc-700 overflow-hidden flex flex-col bg-zinc-900">

                <div class="w-full px-4 pb-2">
                    <button @click="voltarRota"
                        class="w-[90px] py-1 bg-zinc-50 px-2 rounded-full flex justify-around items-center cursor-pointer min-xl:hidden">
                        <img src="../assets/Imagens/arrow.svg" class="w-[21px] h-auto">
                        <span class="text-xs text-[#1b1d1f]">Voltar</span>
                    </button>
                </div>

                <div class="flex w-full px-4 h-[80px] items-center relative">
                    <router-link to="/account/settings" v-if="usuario.id == usuarioProfile.id"
                        class="absolute top-0 end-[15px] p-[3px] hover:bg-zinc-600/50 rounded-full cursor-pointer transition-all duration-100">
                        <img src="../assets/Imagens/pencil.svg" class="w-[20px] h-auto filtro-cinza">
                    </router-link>
                    <div
                        class="flex items-center justify-center xl:size-[75px] size-[65px] rounded-full bg-gradient-to-br from-amber-500 to-pink-500 ">
                        <div
                            class="flex items-center justify-center xl:size-[75px] size-[65px] rounded-full bg-gradient-to-br from-amber-500 to-pink-500 ">
                            <div
                                class="xl:size-[69px] size-[57px] bg-zinc-900 text-black rounded-full flex items-center justify-center overflow-hidden">
                                <img :src="`data:image/png;base64,${usuarioProfile.imagem}`"
                                    class="w-full h-full object-cover" v-if="usuarioProfile.imagem">
                                <h1 class="xl:text-5xl text-2xl text-zinc-50" v-else>{{ primeiraLetraUsuario }}</h1>
                            </div>
                        </div>
                    </div>
                    <div class="flex flex-col text-start ml-2 h-full justify-center">
                        <h1 class="text-lg text-zinc-50">{{ usuarioProfile.nome }}</h1>
                        <span class="text-xs text-zinc-500">@{{ usuarioProfile.usuario }}</span>
                    </div>
                </div>
                <div class="w-full py-4 flex flex-col text-start px-4" v-if="usuarioProfile.bio">
                    <span class="text-[10px] text-zinc-400">
                        {{ usuarioProfile.bio }}
                    </span>
                </div>
                <div class="mt-4 flex *:w-[100px] *:px-4 *:cursor-pointer max-xl:overflow-x-scroll">

                    <button @click="selectedStatus = 1" class="flex flex-col justify-center border-r border-zinc-600">
                        <div class="w-full pb-2" :class="{ 'border-b-2 border-zinc-50': selectedStatus == 1 }">
                            <div class="w-full flex items-center justify-center"><span class="text-[10px]"
                                    :class="selectedStatus == 1 ? 'text-zinc-50' : 'text-zinc-300'">Todos</span>
                            </div>
                            <h2 class="text-[13px] mt-1"
                                :class="selectedStatus == 1 ? 'text-zinc-50' : 'text-zinc-300'">{{
                                    usuarioProfile.numJogos || 0
                                }}</h2>
                        </div>
                    </button>

                    <button @click="selectedStatus = 2"
                        class="flex flex-col justify-center transition-all duration-400 group"
                        :class="selectedStatus == 2 ? 'text-[#d438ff]' : 'text-zinc-300'">
                        <div class="w-full pb-2" :class="{ 'border-b-2 border-[#d438ff]': selectedStatus == 2 }">
                            <div class="w-full flex items-center justify-center">
                                <span class="text-[10px] whitespace-nowrap">Jogando</span>

                                <img src="../assets/Imagens/joystick-2.svg"
                                    class="w-[15px] h-auto ml-1 transition-all duration-100"
                                    :class="{ 'filtro-cinza': selectedStatus != 2 }">
                            </div>
                            <h2 class="text-[13px] mt-1 group-hover:text-[#d438ff] transition-all duration-300">{{
                                quantidadeReviewsPorStatus["Jogando"] }}</h2>
                        </div>
                    </button>

                    <button @click="selectedStatus = 3"
                        class="flex flex-col justify-center transition-all duration-400 group"
                        :class="selectedStatus == 3 ? 'text-green-600' : 'text-zinc-300'">
                        <div class="w-full pb-2" :class="{ 'border-b-2 border-green-600': selectedStatus == 3 }">
                            <div class="w-full flex items-center justify-center">
                                <span class="text-[10px] whitespace-nowrap">Zerado</span>
                                <img src="../assets/Imagens/flag.svg"
                                    class="w-[15px] h-auto ml-1 transition-all duration-100"
                                    :class="{ 'filtro-cinza': selectedStatus != 3 }">
                            </div>
                            <h2 class="text-[13px] mt-1 group-hover:text-green-600 transition-all duration-300">{{
                                quantidadeReviewsPorStatus["Zerado"] }}</h2>
                        </div>
                    </button>

                    <button @click="selectedStatus = 4"
                        class="flex flex-col justify-center transition-all duration-400 group"
                        :class="selectedStatus == 4 ? 'text-[#d22d56]' : 'text-zinc-300'">
                        <div class="w-full pb-2" :class="{ 'border-b-2 border-[#d22d56]': selectedStatus == 4 }">
                            <div class="w-full flex items-center justify-center">
                                <span class="text-[10px] whitespace-nowrap">Dropado</span>
                                <img src="../assets/Imagens/skull.svg"
                                    class="w-[15px] h-auto ml-1 transition-all duration-100"
                                    :class="{ 'filtro-cinza': selectedStatus != 4 }">
                            </div>
                            <h2 class="text-[13px] mt-1 group-hover:text-[#d22d56] transition-all duration-300">{{
                                quantidadeReviewsPorStatus["Dropado"] }}</h2>
                        </div>
                    </button>

                    <button @click="selectedStatus = 5"
                        class="flex flex-col justify-center transition-all duration-400 group"
                        :class="selectedStatus == 5 ? 'text-[#eebb17]' : 'text-zinc-300'">
                        <div class="w-full pb-2" :class="{ 'border-b-2 border-[#eebb17]': selectedStatus == 5 }">
                            <div class="w-full flex items-center justify-center">
                                <span class="text-[10px] whitespace-nowrap">Na fila</span>
                                <img src="../assets/Imagens/shine.svg"
                                    class="w-[13px] h-auto ml-1 transition-all duration-100"
                                    :class="{ 'filtro-cinza': selectedStatus != 5 }">
                            </div>
                            <h2 class="text-[13px] mt-1 group-hover:text-[#eebb17] transition-all duration-300">{{
                                jogosWishlistBackup.length }}</h2>
                        </div>
                    </button>
                </div>

            </div>
            <div class="w-full my-1 flex gap-x-1">
                <div class="w-full rounded-xl border-[1px] border-zinc-800 bg-zinc-900/70 flex items-center">
                    <input type="text" v-model="pesquisaText" placeholder="Pesquise seus jogos..."
                        class="w-full py-3 w-full outline-0 border-0 pl-4 xl:pr-8 pr-4 text-xs text-zinc-50">
                    <div class="px-4 ml-auto"><img src="../assets/Imagens/lupa.svg"
                            class="w-[18px] h-auto filtro-cinza rotate-y-180">
                    </div>
                </div>

                <!-- Filtros -->
                <div
                    class="w-[150px]  rounded-xl border-[1px] border-zinc-800 bg-zinc-900/70 flex text-xs text-zinc-200 justify-between items-center relative">
                    <div class="w-full px-4 text-start cursor-pointer py-auto flex items-center justify-between"
                        ref="filtroBoxButton" @click="filtroBoxOpen = !filtroBoxOpen">
                        <span v-if="filtroReviews == 1 || filtroReviews == 2">Nome</span>
                        <span v-if="filtroReviews == 3 || filtroReviews == 4">Nota</span>
                        <span v-if="filtroReviews == 5 || filtroReviews == 6">Data</span>


                        <img v-if="filtroReviews == 1" src="../assets/Imagens/filtros/letter_up.svg"
                            class="filtro-cinza-claro w-[20px] h-auto">
                        <img v-else-if="filtroReviews == 2" src="../assets/Imagens/filtros/letter_down.svg"
                            class="filtro-cinza-claro w-[20px] h-auto">
                        <img v-else-if="filtroReviews == 3" src="../assets/Imagens/filtros/arrow_down.svg"
                            class="filtro-cinza-claro w-[20px] h-auto">
                        <img v-else-if="filtroReviews == 4" src="../assets/Imagens/filtros/arrow_up.svg"
                            class="filtro-cinza-claro w-[20px] h-auto">
                        <img v-else-if="filtroReviews == 5" src="../assets/Imagens/filtros/calendar_down.svg"
                            class="filtro-cinza-claro w-[20px] h-auto">
                        <img v-else-if="filtroReviews == 6" src="../assets/Imagens/filtros/calendar_up.svg"
                            class="filtro-cinza-claro w-[20px] h-auto">
                    </div>

                    <div v-if="filtroBoxOpen" ref="filtroBox"
                        class="absolute top-[45px] end-0 w-max flex flex-col gap-y-3 p-4 rounded-2xl border border-zinc-700 bg-zinc-800 z-[1100] shadow-md">
                        <div class="grid grid-cols-3 gap-x-1 items-center">
                            <span class="pr-4 text-start">Nome</span>
                            <button @click="filtroReviews = 1"
                                class="filter-button px-4 py-1 rounded-full hover:bg-zinc-600 cursor-pointer flex items-center justify-center"
                                :class="{ 'bg-zinc-600': filtroReviews == 1 }">
                                <img src="../assets/Imagens/filtros/letter_up.svg" class="w-[20px] h-auto"
                                    :class="filtroReviews == 1 ? 'filtro-cinza-claro' : 'filtro-cinza'">
                            </button>
                            <button @click="filtroReviews = 2"
                                class="filter-button px-4 py-1 rounded-full hover:bg-zinc-600 cursor-pointer flex items-center justify-center"
                                :class="{ 'bg-zinc-600': filtroReviews == 2 }">
                                <img src="../assets/Imagens/filtros/letter_down.svg" class="w-[20px] h-auto"
                                    :class="filtroReviews == 2 ? 'filtro-cinza-claro' : 'filtro-cinza'"></button>
                        </div>

                        <div class="grid grid-cols-3 gap-x-1 items-center">
                            <span class="pr-4 text-start">Nota</span>
                            <button @click="filtroReviews = 3"
                                class="filter-button px-4 py-1 rounded-full hover:bg-zinc-600 cursor-pointer flex items-center justify-center"
                                :class="{ 'bg-zinc-600': filtroReviews == 3 }">
                                <img src="../assets/Imagens/filtros/arrow_down.svg" class="w-[20px] h-auto"
                                    :class="filtroReviews == 3 ? 'filtro-cinza-claro' : 'filtro-cinza'">
                            </button>
                            <button @click="filtroReviews = 4"
                                class="filter-button px-4 py-1 rounded-full hover:bg-zinc-600 cursor-pointer flex items-center justify-center"
                                :class="{ 'bg-zinc-600': filtroReviews == 4 }">
                                <img src="../assets/Imagens/filtros/arrow_up.svg" class="w-[20px] h-auto"
                                    :class="filtroReviews == 4 ? 'filtro-cinza-claro' : 'filtro-cinza'"></button>
                        </div>

                        <div class="grid grid-cols-3 gap-x-1 items-center">
                            <span class="pr-4 text-start">Data</span>
                            <button @click="filtroReviews = 5"
                                class="filter-button px-4 py-1 rounded-full hover:bg-zinc-600 cursor-pointer flex items-center justify-center"
                                :class="{ 'bg-zinc-600': filtroReviews == 5 }">
                                <img src="../assets/Imagens/filtros/calendar_down.svg" class="w-[20px] h-auto"
                                    :class="filtroReviews == 5 ? 'filtro-cinza-claro' : 'filtro-cinza'">
                            </button>
                            <button @click="filtroReviews = 6"
                                class="filter-button px-4 py-1 rounded-full hover:bg-zinc-600 cursor-pointer flex items-center justify-center"
                                :class="{ 'bg-zinc-600': filtroReviews == 6 }">
                                <img src="../assets/Imagens/filtros/calendar_up.svg" class="w-[20px] h-auto"
                                    :class="filtroReviews == 6 ? 'filtro-cinza-claro' : 'filtro-cinza'"></button>
                        </div>
                    </div>
                </div>
            </div>

            <div class="w-full h-[250px] flex items-center justify-center" v-if="carregando">
                <span class="loading loading-ring loading-xl"></span>
            </div>


            <!-- Reviews -->
            <div class="w-full grid-cols-1 grid gap-y-1" v-if="selectedStatus != 5 && !carregando">
                <div class="w-full flex h-[250px] flex-col items-center mt-12" v-if="jogosReview.length == 0">
                    <span class="text-zinc-400 italic font-xl">Sem reviews por enquanto...</span>
                    <img src="../assets/Imagens/ghost.svg" class="w-[50px] h-auto filtro-cinza mt-4">
                </div>
                <div class="mt-[1px] w-full min-h-[120px] flex rounded-2xl overflow-hidden relative" v-else
                    v-for="(jogo, index) in jogosReview" :key="jogo.id">

                    <div class="absolute top-[15px] end-[15px] z-[300]">
                        <SubmenuReviewComponent :idUsuarioAutor="usuarioProfile.id" :idUsuarioLogado="usuario.id"
                            :review="reviews[jogo.id]" @recarrega-dados="recarregaDados" />
                    </div>

                    <div class="w-full h-full relative overflow-hidden">

                        <div class="absolute inset-0 z-10">
                            <img :src="capasJogos[jogo.cover]" class="w-full h-full object-cover blur-lg" />
                        </div>

                        <div class="absolute inset-0 z-20 w-full h-full bg-black/60"></div>

                        <div
                            class="absolute top-[15px] start-[15px] z-30 w-[65px] h-[90px] overflow-hidden rounded-md shadow-md">
                            <img :src="capasJogos[jogo.cover]" class="w-full h-full object-cover" />
                        </div>
                        <router-link :to="`/game/${jogo.id}`" class="w-full h-full p-0">
                            <div class="w-full pl-[90px] z-[100] h-[120px] relative">
                                <div class="flex flex-col w-full justify-center h-full">
                                    <div class="flex items-center h-full w-full">
                                        <div
                                            class="py-2 w-[50px] rounded-lg bg-zinc-800/30 flex items-center justify-center shrink-0">
                                            <span class="text-zinc-50 text-xl">{{
                                                reviews[jogo.id]?.nota
                                            }}</span>
                                        </div>
                                        <div class="flex flex-col items-center ml-2 w-full">
                                            <div class="w-full flex justify-start">
                                                <div class="px-[5px] py-[1px] rounded-lg text-zinc-50 flex items-center"
                                                    :class="[{ 'bg-[#d438ff]': reviews[jogo.id].status == 1 },
                                                    { 'bg-green-600': reviews[jogo.id]?.status == 2 },
                                                    { 'bg-[#d22d56]': reviews[jogo.id]?.status == 3 }]">
                                                    <span class="text-[10px] text-start">{{
                                                        statusMapping[reviews[jogo.id]?.status] }}</span>
                                                </div>
                                            </div>
                                            <div class="flex w-full">
                                                <div class="max-w-[70%] w-fit line-clamp-2 pr-2 break-all text-left">
                                                    <span class="text-sm text-start xl:text-md w-full">{{
                                                        jogo.name
                                                    }}</span>
                                                </div>
                                                <div class="pl-2 border-l-[1px] border-zinc-400 flex items-center"
                                                    v-if="jogo.first_release_date">
                                                    <span class="text-xs text-zinc-400 xl:text-md">{{
                                                        formataDataUnix(jogo.first_release_date) }}</span>
                                                </div>
                                            </div>
                                            <div v-if="reviews[jogo.id] && reviews[jogo.id].dataInclusao"
                                                class="block text-start w-full -mt-1">
                                                <span class="text-[9px] text-zinc-400 text-start">{{ new
                                                    Intl.DateTimeFormat('pt-BR').format(new
                                                        Date(reviews[jogo.id].dataInclusao))
                                                }}</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                            </div>
                        </router-link>
                        <ProfileReviewTextComponent :texto="reviews[jogo.id].texto" :usuario="usuarioProfile"
                            v-if="reviews[jogo.id].texto" />
                    </div>

                </div>
            </div>

            <!-- Wishlist -->
            <div class="w-full grid-cols-1 grid gap-y-1" v-else-if="selectedStatus == 5 && !carregando">
                <div class="w-full flex h-[250px] flex-col items-center mt-12" v-if="jogosWishlist.length == 0">
                    <span class="text-zinc-400 italic font-xl">Lista vazia por enquanto...</span>
                    <img src="../assets/Imagens/ghost.svg" class="w-[50px] h-auto filtro-cinza mt-4">
                </div>
                <div class="mt-[1px] w-full h-[120px] flex rounded-2xl overflow-hidden relative" v-else
                    v-for="(jogo, index) in jogosWishlist" :key="jogo.id">

                    <div class="absolute top-[15px] end-[15px] z-[300]" v-if="usuario.id == usuarioProfile.id">
                        <SubmenuReviewComponent :idUsuarioAutor="usuarioProfile.id" :idUsuarioLogado="usuario.id"
                            :review="reviews[jogo.id]" @recarrega-dados="carregaInformacoesUsuario"
                            :wishlist="wishlists[jogo.id].id" />
                    </div>

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
                                <div class="flex flex-col w-full justify-center h-full">
                                    <div class="flex items-center h-full w-full">
                                        <div class="flex flex-col items-center ml-2 w-full">
                                            <div class="w-full flex justify-start">
                                                <div
                                                    class="px-[5px] py-[1px] bg-[#eebb17] rounded-lg text-zinc-50 flex items-center">
                                                    <span class="text-[10px] text-start">Na fila</span>
                                                </div>
                                            </div>
                                            <div class="flex w-full">
                                                <div class="max-w-[70%] line-clamp-2 pr-2 break-all text-left">
                                                    <span class="text-sm text-start xl:text-md w-full">{{
                                                        jogo.name
                                                    }}</span>
                                                </div>
                                                <div class="pl-2 border-l-[1px] border-zinc-400 flex items-center"
                                                    v-if="jogo.first_release_date">
                                                    <span class="text-xs text-zinc-400 xl:text-md">{{
                                                        formataDataUnix(jogo.first_release_date) }}</span>
                                                </div>
                                            </div>
                                            <div v-if="wishlists[jogo.id]" class="block text-start w-full -mt-1">
                                                <span class="text-[9px] text-zinc-400 text-start">{{ new
                                                    Intl.DateTimeFormat('pt-BR').format(new
                                                        Date(wishlists[jogo.id].dataInclusao))
                                                }}</span>
                                            </div>
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
import MenuComponent from '@/components/MenuComponent.vue';
import MenuMobileComponent from '@/components/MenuMobileComponent.vue';
import ProfileReviewTextComponent from '@/components/ProfileReviewTextComponent.vue';
import SubmenuReviewComponent from '@/components/SubmenuReviewComponent.vue';
import { useTwitchTokenStore } from '@/stores/TwitchTokenStore';
import { useUserStore } from '@/stores/UserStore';
import { api } from '@/services/api'

export default {
    name: 'Profile',
    props: ["filtro", "username"],
    data() {
        return {
            usuario: {},
            usuarioProfile: {},
            selectedStatus: this.filtro ?? 1,
            reviews: {},
            wishlists: {},
            wishlistIds: [],
            jogosReview: [],
            jogosReviewBackup: [],
            jogosWishlist: [],
            jogosWishlistBackup: [],
            jogosIds: [],
            capasJogos: {},
            quantidadeReviewsPorStatus: {},
            statusMapping: {
                1: "Jogando",
                2: "Zerado",
                3: "Dropado"
            },
            pesquisaText: '',
            carregando: false,
            twitchTokenStore: useTwitchTokenStore(),
            filtroBoxOpen: false,
            filtroReviews: 5,
            semReviews: false,
            semWishlists: false
        }
    },
    components: {
        MenuComponent,
        MenuMobileComponent,
        ProfileReviewTextComponent,
        SubmenuReviewComponent
    },
    async mounted() {

        this.carregando = true
        if (this.twitchTokenStore.access_token == '') {
            await this.twitchTokenStore.buscaToken()
        }
        if (useUserStore().usuario != null) {
            this.usuario = useUserStore().usuario
        } else {
            await useUserStore().reconectaSessao()
            if (useUserStore().usuario != null) {
                this.usuario = useUserStore().usuario
            }
        }

        await this.carregaUsuario()

        document.addEventListener("click", this.verificaClick)
    },
    methods: {
        async carregaUsuario() {
            await this.procuraUsuario()

            this.carregaInformacoesUsuario()
        },
        voltarRota() {
            if (window.history.length > 1) {
                this.$router.back()
            } else {
                this.$router.push('/')
            }
        },
        async carregaInformacoesUsuario() {
            await this.carregaListaDeDesejo()
            await this.carregaReviews()
            if (this.usuarioProfile != null && (this.jogosIds.length > 0)) {
                setTimeout(() => this.carregaJogos(this.jogosIds), 1500)
            } else {
                this.carregando = false
            }

            this.semReviews = !this.reviews.length > 0
            this.semWishlists = !this.wishlists.length > 0
        },
        verificaClick(event) {
            if (this.$refs.filtroBox && !this.$refs.filtroBox.contains(event.target) && this.$refs.filtroBoxButton && !this.$refs.filtroBoxButton.contains(event.target)) {
                this.filtroBoxOpen = false
            }
        },
        async procuraUsuario() {
            console.log("Procurausuario")
            try {
                const response = await api.get(`/usuario/fromUsername/${this.username}`)
                this.usuarioProfile = response.data
                console.log("Usuario profile: ", this.usuarioProfile)
            } catch (error) {
                console.log("Erro ao carregar informações de usuário: ", error)
            }
        },

        async carregaListaDeDesejo() {
            try {
                const response = await api.get(`/wishlist/get/user?idUsuario=${this.usuarioProfile.id}`)

                this.wishlistIds = response.data.map(w => w.idJogo)

                response.data.forEach(w => {
                    this.wishlists[w.idJogo] = { dataInclusao: w.dataInclusao, id: w.id }
                })

                this.jogosIds.push(...this.wishlistIds.toString().split(",").filter(w => w.length > 0))
            } catch (error) {
                console.log("Erro ao carregar wishlist do usuário: ", error)
            }
        },
        async carregaReviews() {
            try {
                const response = await api.get(`/review/user?id=${this.usuarioProfile.id}`)

                response.data.forEach(r => {
                    this.reviews[r.idJogo] = r
                })

                this.jogosIds.push(...Object.keys(this.reviews).filter(r => r.length > 0))

                this.quantidadeReviewsPorStatus["Jogando"] = response.data.filter(r => r.status == 1)?.length ?? 0
                this.quantidadeReviewsPorStatus["Zerado"] = response.data.filter(r => r.status == 2)?.length ?? 0
                this.quantidadeReviewsPorStatus["Dropado"] = response.data.filter(r => r.status == 3)?.length ?? 0

            } catch (error) {
                console.log("Erro ao carregar reviews do usuário: ", error)
            }
        },

        async carregaJogos(jogosId) {
            const body = `fields *; where id = (${jogosId.join(', ')});`;

            try {
                const response = await api.post("/api/igdb/proxy", body, {
                    headers: {
                        "igdb-endpoint": "/v4/games",
                        "Content-Type": "text/plain"
                    }
                })

                this.jogosReviewBackup = this.jogosReview = response.data.filter(j => !this.wishlistIds.includes(j.id))
                this.jogosWishlistBackup = this.jogosWishlist = response.data.filter(j => this.wishlistIds.includes(j.id))

                this.jogosReview.forEach((e) => {
                    if (!this.jogosIds.includes(e.id)) this.jogosIds.push(e.id)
                })

                await this.carregaCapas(this.jogosIds)
            } catch (error) {
                console.error("Erro: " + error)
            } finally {
                this.carregando = false
            }
        },

        async carregaCapas(jogosId) {
            const body = `fields url; where game = (${jogosId.join(", ")}); limit 12;`
            try {
                const response = await api.post("/api/igdb/proxy", body, {
                    headers: {
                        "igdb-endpoint": "/v4/covers",
                        "Content-Type": "text/plain"
                    }
                })

                for (let data of response.data) {
                    if (data && data.url) {
                        this.capasJogos[data.id] = data.url.replace("thumb", "720p")
                    }
                }
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
    },
    watch: {
        selectedStatus() {
            if (this.selectedStatus - 1 != 0) {
                this.jogosReview = this.jogosReviewBackup.filter(r => {
                    return Object.entries(this.reviews).filter(([chave, valor]) => chave == r.id && valor.status == this.selectedStatus - 1).length > 0
                })
            } else {
                this.jogosReview = this.jogosReviewBackup
            }
        },
        pesquisaText() {
            var pesquisaTextFormatado = this.pesquisaText.trim().toLowerCase()

            if (this.selectedStatus != 5) {
                if (this.pesquisaText) {
                    this.jogosReview = this.jogosReviewBackup.filter(r => pesquisaTextFormatado.includes(r.name.toLowerCase().trim()) || r.name.toLowerCase().trim().includes(pesquisaTextFormatado))
                } else {
                    this.jogosReview = this.jogosReviewBackup
                }
                if (this.selectedStatus - 1 > 0) {
                    this.jogosReview = this.jogosReview.filter(r => {
                        return Object.entries(this.reviews).filter(([chave, valor]) => chave == r.id && valor.status == this.selectedStatus - 1).length > 0
                    })
                }
            } else {
                if (this.pesquisaText) {
                    this.jogosWishlist = this.jogosWishlistBackup.filter(r => pesquisaTextFormatado.includes(r.name.toLowerCase().trim()) || r.name.toLowerCase().trim().includes(pesquisaTextFormatado))
                } else {
                    this.jogosWishlist = this.jogosWishlistBackup
                }
            }
        },
        filtroReviews() {
            console.log(this.jogosReviewBackup)
            if (this.filtroReviews == 1) {
                this.jogosReview = [...this.jogosReviewBackup].sort((a, b) => a.name.localeCompare(b.name, 'pt-BR', { sensitivity: 'base' }));
            } else if (this.filtroReviews == 2) {
                this.jogosReview = [...this.jogosReviewBackup].sort((a, b) => b.name.localeCompare(a.name, 'pt-BR', { sensitivity: 'base' }));
            } else if (this.filtroReviews == 3) {
                this.jogosReview = [...this.jogosReviewBackup].sort((a, b) => {
                    const notaA = this.reviews[a.id]?.nota ?? 0
                    const notaB = this.reviews[b.id]?.nota ?? 0
                    return notaB - notaA
                });
            } else if (this.filtroReviews == 4) {
                this.jogosReview = [...this.jogosReviewBackup].sort((a, b) => {
                    const notaA = this.reviews[a.id]?.nota ?? 0
                    const notaB = this.reviews[b.id]?.nota ?? 0
                    return notaA - notaB
                });
            } else if (this.filtroReviews == 5) {
                this.jogosReview = [...this.jogosReviewBackup].sort((a, b) => new Date(this.reviews[a.id].dataInclusao) - new Date(this.reviews[b.id].dataInclusao));
            } else if (this.filtroReviews == 6) {
                this.jogosReview = [...this.jogosReviewBackup].sort((a, b) => b.name.localeCompare(a.name, 'pt-BR', { sensitivity: 'base' }));
            }
        },
        '$route.params.username'(novo, antigo) {
            if (novo != antigo) {
                this.carregaUsuario()
            }
        }
    },
    computed: {
        primeiraLetraUsuario() {
            return this.usuarioProfile?.usuario?.charAt(0).toUpperCase() || ''
        }
    }
}
</script>

<style scoped>
.filtro-cinza {
    filter: brightness(0) saturate(100%) invert(69%) sepia(3%) saturate(706%) hue-rotate(201deg) brightness(92%) contrast(89%);
}

.filtro-cinza-claro {
    filter: brightness(0) saturate(100%) invert(100%) sepia(0%) saturate(7479%) hue-rotate(180deg) brightness(82%) contrast(132%);
}

.filter-button:hover img {
    filter: brightness(0) saturate(100%) invert(100%) sepia(0%) saturate(7479%) hue-rotate(180deg) brightness(82%) contrast(132%);
}

.gradiente-texto {
    background: #18181B;
    background: linear-gradient(0deg, rgba(24, 24, 27, 1) 0%, rgba(24, 24, 27, 0) 100%);
}
</style>