<template>
    <div class="w-full h-full pb-6 pt-4 px-24 flex justify-center gap-x-4">
        <MenuComponent :selected="2" />
        <div class="w-[820px] h-min flex flex-col">
            <div
                class="w-full h-min rounded-xl pt-4 border-[1px] border-zinc-700 overflow-hidden flex flex-col bg-zinc-900">
                <div class="flex w-full px-4 h-[80px] items-center relative">
                    <button
                        class="absolute top-0 end-[15px] p-[3px] hover:bg-zinc-600/50 rounded-full cursor-pointer transition-all duration-100">
                        <img src="../assets/Imagens/pencil.svg" class="w-[20px] h-auto filtro-cinza">
                    </button>
                    <div
                        class="flex items-center justify-center size-[75px] rounded-full bg-gradient-to-br from-amber-500 to-pink-500 ">
                        <div class="size-[68px] bg-zinc-900 text-black rounded-full flex items-center justify-center">
                            <h1 class="text-3xl text-zinc-50">{{ primeiraLetraUsuario }}</h1>
                        </div>
                    </div>
                    <div class="flex flex-col text-start ml-2 h-full justify-center">
                        <h1 class="text-lg text-zinc-50">{{ usuario.nome }}</h1>
                        <span class="text-xs text-zinc-500">@{{ usuario.usuario }}</span>
                    </div>
                </div>
                <div class="mt-4 flex *:w-[100px] *:px-4 *:cursor-pointer">

                    <button @click="selectedStatus = 1" class="flex flex-col justify-center border-r border-zinc-600">
                        <div class="w-full pb-2" :class="{ 'border-b-2 border-zinc-50': selectedStatus == 1 }">
                            <div class="w-full flex items-center justify-center"><span class="text-[10px]">Todos</span>
                            </div>
                            <h2 class="text-[13px] mt-1">{{ jogosReviewBackup.length }}</h2>
                        </div>
                    </button>

                    <button @click="selectedStatus = 2"
                        class="flex flex-col justify-center transition-all duration-400 group"
                        :class="{ 'text-[#d438ff]': selectedStatus == 2 }">
                        <div class="w-full pb-2" :class="{ 'border-b-2 border-[#d438ff]': selectedStatus == 2 }">
                            <div class="w-full flex items-center justify-center">
                                <span class="text-[10px]">Jogando</span>
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
                        :class="{ 'text-green-600': selectedStatus == 3 }">
                        <div class="w-full pb-2" :class="{ 'border-b-2 border-green-600': selectedStatus == 3 }">
                            <div class="w-full flex items-center justify-center">
                                <span class="text-[10px]">Zerado</span>
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
                        :class="{ 'text-[#d22d56]': selectedStatus == 4 }">
                        <div class="w-full pb-2" :class="{ 'border-b-2 border-[#d22d56]': selectedStatus == 4 }">
                            <div class="w-full flex items-center justify-center">
                                <span class="text-[10px]">Dropado</span>
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
                        :class="{ 'text-[#eebb17]': selectedStatus == 5 }">
                        <div class="w-full pb-2" :class="{ 'border-b-2 border-[#eebb17]': selectedStatus == 5 }">
                            <div class="w-full flex items-center justify-center">
                                <span class="text-[10px]">Na fila</span>
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
            <div class="w-full rounded-xl border-[1px] border-zinc-800 bg-zinc-900/70 my-1 flex items-center">
                <input type="text" v-model="pesquisaText" placeholder="Pesquise seus jogos..."
                    class="w-full py-3 w-full outline-0 border-0 pl-4 pr-8 text-xs text-zinc-50">
                <div class="px-4 ml-auto"><img src="../assets/Imagens/lupa.svg"
                        class="w-[18px] h-auto filtro-cinza rotate-y-180">
                </div>
            </div>

            <div class="w-full h-[250px] flex items-center justify-center" v-if="carregando">
                <span class="loading loading-ring loading-xl"></span>
            </div>

            <div class="w-full grid-cols-1 grid gap-y-1" v-if="selectedStatus != 5 && !carregando">
                <div class="mt-[1px] w-full min-h-[120px] flex rounded-2xl overflow-hidden"
                    v-for="(jogo, index) in jogosReview" :key="jogo.id">


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
                                                <div class="max-w-[70%] line-clamp-2 break-all text-left">
                                                    <span class="text-sm text-start pr-4 xl:text-md w-full">{{
                                                        jogo.name
                                                        }}</span>
                                                </div>
                                                <div class="pl-2 border-l-[1px] border-zinc-500"
                                                    v-if="jogo.first_release_date">
                                                    <span class="text-xs text-zinc-500 xl:text-md">{{
                                                        formataDataUnix(jogo.first_release_date) }}</span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                            </div>
                        </router-link>
                        <ProfileReviewTextComponent :texto="reviews[jogo.id].texto" />
                    </div>

                </div>
            </div>

            <div class="w-full grid-cols-1 grid gap-y-1" v-else-if="selectedStatus == 5 && !carregando">
                <div class="mt-[1px] w-full h-[120px] flex rounded-2xl overflow-hidden"
                    v-for="(jogo, index) in jogosWishlist" :key="jogo.id">

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
                                                <div class="max-w-[70%] line-clamp-2 break-all text-left">
                                                    <span class="text-sm text-start pr-4 xl:text-md w-full">{{
                                                        jogo.name
                                                        }}</span>
                                                </div>
                                                <div class="pl-2 border-l-[1px] border-zinc-500"
                                                    v-if="jogo.first_release_date">
                                                    <span class="text-xs text-zinc-500 xl:text-md">{{
                                                        formataDataUnix(jogo.first_release_date) }}</span>
                                                </div>
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
import ProfileReviewTextComponent from '@/components/ProfileReviewTextComponent.vue';
import { useUserStore } from '@/stores/UserStore';
import axios from 'axios';

export default {
    name: 'Profile',
    props: ["filtro"],
    data() {
        return {
            usuario: {},
            selectedStatus: this.filtro ?? 1,
            reviews: {},
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
        }
    },
    components: {
        MenuComponent,
        ProfileReviewTextComponent
    },
    async mounted() {
        this.carregando = true
        if (useUserStore().usuario != null) {
            this.usuario = useUserStore().usuario
        } else {
            await useUserStore().reconectaSessao()
            if (useUserStore().usuario != null) {
                this.usuario = useUserStore().usuario
            }
        }
        await this.carregaListaDeDesejo()
        await this.carregaReviews()
        setTimeout(() => this.carregaJogos(this.jogosIds), 1500)
    },
    methods: {
        async carregaListaDeDesejo() {
            try {
                const response = await axios.get(`http://localhost:5000/wishlist/get/user?idUsuario=${this.usuario.id}`)
                this.wishlistIds = response.data.map(w => w.idJogo)
                this.jogosIds.push(...this.wishlistIds.toString().split(","))
            } catch (error) {
                console.log("Erro ao carregar wishlist do usuário: ", error)
            }
        },
        async carregaReviews() {
            try {
                const response = await axios.get(`http://localhost:5000/review/user?id=${this.usuario.id}`)

                response.data.forEach(r => {
                    this.reviews[r.idJogo] = r
                })
                console.log("RRRreviews: ", this.reviews)
                this.jogosIds.push(...Object.keys(this.reviews))

                this.quantidadeReviewsPorStatus["Jogando"] = response.data.filter(r => r.status == 1).length
                this.quantidadeReviewsPorStatus["Zerado"] = response.data.filter(r => r.status == 2).length
                this.quantidadeReviewsPorStatus["Dropado"] = response.data.filter(r => r.status == 3).length

            } catch (error) {
                console.log("Erro ao carregar reviews do usuário: ", error)
            }
        },

        async carregaJogos(jogosId) {
            const body = `fields *; where id = (${jogosId.join(', ')});`;

            try {
                const response = await axios.post("/v4/games", body, {
                    headers: {
                        'Accept': 'application/json',
                        'Client-ID': "i79ndcjylui2396ezi2v752sc9dze0",
                        'Authorization': `Bearer h6v8ywcqhwyyhj140u70v95rss6sga`,
                        'Content-Type': 'text/plain'
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
                const response = await axios.post("/v4/covers", body, {
                    headers: {
                        'Accept': 'application/json',
                        'Client-ID': "i79ndcjylui2396ezi2v752sc9dze0",
                        'Authorization': `Bearer h6v8ywcqhwyyhj140u70v95rss6sga`,
                        'Content-Type': 'text/plain'
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
        }
    },
    computed: {
        primeiraLetraUsuario() {
            return this.usuario?.usuario?.charAt(0).toUpperCase() || ''
        }
    }
}
</script>

<style scoped>
.filtro-cinza {
    filter: brightness(0) saturate(100%) invert(69%) sepia(3%) saturate(706%) hue-rotate(201deg) brightness(92%) contrast(89%);
}

.gradiente-texto {
    background: #18181B;
    background: linear-gradient(0deg, rgba(24, 24, 27, 1) 0%, rgba(24, 24, 27, 0) 100%);
}
</style>