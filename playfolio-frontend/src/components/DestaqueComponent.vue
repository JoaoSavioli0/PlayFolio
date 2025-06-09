<template>
    <div class="w-full min-[1800px]:px-40 xl:px-20 px-6 xl:py-16 pt-16 pb-6" v-if="jogosDestaque.length">
        <div class="flex w-full items-center">
            <div class="whitespace-nowrap flex items-center justify-between w-full">
                <div class="flex">
                    <h1 class="xl:text-[30px] text-[20px] text-start text-zinc-50">DESTAQUES 🔥</h1>
                </div>

                <div class="flex justify-end gap-2">
                    <button @click="embla.scrollPrev()" class="p-[4px] rounded-full hover:bg-zinc-800">
                        <img src="../assets/Imagens/arrow-2.svg"
                            class="xl:w-[35px] w-[25px] h-auto cursor-pointer filtro-branco">
                    </button>
                    <button @click="embla.scrollNext()" class="p-[4px] rounded-full hover:bg-zinc-800">
                        <img src="../assets/Imagens/arrow-2.svg"
                            class="xl:w-[35px] w-[25px] h-auto cursor-pointer filtro-branco -rotate-180">
                    </button>
                </div>

            </div>
        </div>

        <div class="embla mt-6">
            <div class="embla__viewport" ref="viewport">
                <div class="embla__container">
                    <div class="embla__slide xl:h-[420px] h-[320px] clip-card bg-[#262729] flex flex-col shrink-0 relative card-area rounded-xl overflow-hidden border-[1px] border-zinc-600"
                        v-for="(jogo, index) in jogosDestaque" :key="index">
                        <router-link class="w-full h-[400px] xl:mb-2 relative overflow-hidden relative image-area z-40"
                            :to="`/game/${jogo.id}`">

                            <img :src="capasJogos[jogo.id]" alt="">
                            <div class="w-full h-full absolute gradiente-card top-0"></div>

                            <div class="end-0 top-0 px-[10px] py-[4px] justify-center flex absolute shrink-0 items-center bg-[#1A1B1E] z-50 rounded-bl-xl border-l-[1px] border-b-[1px] border-zinc-600 shadow-lg"
                                v-if="jogo.rating">
                                <img src="../assets/Imagens/star.png" class="size-[12px] xl:size-[16px]">
                                <span class="ml-2 text-xs xl:text-sm">{{ (jogo.rating / 10).toFixed(1) }}</span>
                            </div>

                        </router-link>

                        <div
                            class="w-full h-[150px] px-4 py-2 flex flex-col justify-between pb-4 text-area transition-all">
                            <div class="w-full h-[110px]">
                                <span
                                    class="text-start text-zinc-50 text-sm xl:text-md font-medium line-clamp-2 break-words hyphens-auto w-full">
                                    {{ jogo.name }}</span>
                                <div v-if="jogo.genres" class="text-start w-full line-clamp-1 text-zinc-500 leading-4">
                                    <span v-for="(genreId, index) in jogo.genres" :key="genreId"
                                        class="text-[10px] xl:text-xs">
                                        {{ genresNomes[genreId] == "Role-playing (RPG)" ? "RPG" :
                                            genresNomes[genreId]
                                        }}
                                        <span v-if="index !== jogo.genres.length - 1">
                                            ,
                                        </span>
                                    </span>
                                </div>

                            </div>
                            <div class="w-full relative flex items-center justify-between">
                                <div v-if="jogo.first_release_date"
                                    class="mt-2 flex items-center w-full justify-start transition-all duration-300 bg-transparent">
                                    <img src="../assets/calendario.svg" class="w-[14px] h-auto filtro-cinza">

                                    <span class="text-[10px] xl:text-xs text-zinc-500 ml-2"
                                        v-if="jogo.first_release_date">{{
                                            formataDataUnix(jogo.first_release_date) }}</span>
                                </div>

                                <router-link class="p-0"
                                    :to="{ path: `/game/${jogo.id}`, query: { avaliacaoBoxOpenDefault: true } }">
                                    <button
                                        class="border-[1px] w-[40px] h-[40px] shrink-0 flex items-center justify-center border-zinc-500 rounded-full cursor-pointer shadow-sm transition-all peer hover:border-zinc-50">
                                        <span class="text-zinc-200 text-xs peer:hover:text-white xl:text-xl">+</span>
                                    </button>
                                </router-link>
                            </div>
                        </div>

                    </div>
                </div>
            </div>

            <!-- Botões (caso queira) -->

        </div>

    </div>
</template>

<script>
import { useTwitchTokenStore } from '@/stores/TwitchTokenStore';
import EmblaCarousel from 'embla-carousel'
import { api } from '@/services/api';

export default {
    name: "Destaque",
    data() {
        return {
            jogosDestaque: [],
            capasJogos: {},
            genreIds: [],
            genresNomes: {},
            isDown: false,
            startX: 0,
            scrollLeft: 0,
            embla: null,
            twitchTokenStore: useTwitchTokenStore(),
            timeOutId: null
        }
    },
    async mounted() {
        if (this.twitchTokenStore.access_token == '') {
            await this.twitchTokenStore.buscaToken()
        }
        this.timeOutId = setTimeout(() => {
            this.encontraJogos()
        }, 1500)
    },
    beforeUnmount() {
        clearTimeout(this.timeoutId)
    },
    methods: {
        async encontraJogos() {
            const body = `fields *; sort first_release_date desc; where rating_count > 25; limit 15;`
            try {
                const response = await api.post("/api/igdb/proxy", body, {
                    headers: {
                        "igdb-endpoint": "/v4/games",
                        "Content-Type": "text/plain"
                    }
                })

                this.jogosDestaque = response.data
                let jogosDestaqueId = this.jogosDestaque.map(e => e.id)

                await this.carregaCapas(jogosDestaqueId)
                this.carregaCarrossel()
                await this.carregaTags(this.jogosDestaque)

            } catch (error) {
                console.error("Erro: " + error)
            }
        },

        carregaCarrossel() {
            const viewportNode = this.$refs.viewport
            if (viewportNode) {
                this.embla = EmblaCarousel(viewportNode, {
                    loop: false,
                    align: 'start',
                    slidesToScroll: 1,
                    dragFree: true
                })
            }
        },

        async carregaCapas(jogosId) {
            const requests = jogosId.map(async (id) => {
                const body = `fields url; where game = ${id}; limit 1;`
                try {
                    const response = await api.post("/api/igdb/proxy", body, {
                        headers: {
                            "igdb-endpoint": "/v4/covers",
                            "Content-Type": "text/plain"
                        }
                    })
                    let imagem = response.data[0]
                    if (imagem && imagem.url) {
                        this.capasJogos[id] = imagem.url.replace("thumb", "720p")
                    }
                } catch (error) {
                    console.error("Erro carregando imagem: " + error)
                }
            })
            await Promise.all(requests)
        },

        async carregaTags(jogos) {
            for (let jogo of jogos) {
                for (let genreId of jogo.genres) {
                    if (!this.genreIds.includes(genreId)) {
                        this.genreIds.push(genreId)
                    }
                }
            }

            const body = `fields name; where id = (${this.genreIds.join(", ")}); limit 50;`
            try {
                const response = await api.post("/api/igdb/proxy", body, {
                    headers: {
                        "igdb-endpoint": "/v4/genres",
                        "Content-Type": "text/plain"
                    }
                })

                for (let data of response.data) {
                    this.genresNomes[data.id] = data.name
                }


            } catch (error) {
                console.error("Erro carregando generos: " + error)
            }
            console.log("genreNomes: " + JSON.stringify(this.genresNomes))
        },

        formataDataUnix(dataUnix) {
            const timestamp = dataUnix;
            const data = new Date(timestamp * 1000);
            const options = { day: '2-digit', month: '2-digit', year: 'numeric' };
            return data.toLocaleString('pt-BR', options)
        },

        startScroll(e) {
            this.isDown = true;
            this.startX = e.pageX - this.$refs.scrollContent.offsetLeft;
            this.scrollLeft = this.$refs.scrollContent.scrollLeft;
        },

        moveScroll(e) {
            if (!this.isDown) return;
            e.preventDefault();
            const x = e.pageX - this.$refs.scrollContent.offsetLeft;
            const walk = (x - this.startX) * 2; // Velocidade do arrasto
            this.$refs.scrollContent.scrollLeft = this.scrollLeft - walk;
        },

        stopScroll() {
            this.isDown = false;
        }
    },
}
</script>

<style scoped>
.gradiente-card {
    background: rgb(38, 39, 41);
    background: linear-gradient(0deg, rgba(38, 39, 41, 1) 0%, rgba(0, 0, 0, 0) 51%);
}

.filtro-branco {
    filter: brightness(0) saturate(100%) invert(100%) sepia(0%) saturate(0%) hue-rotate(168deg) brightness(103%) contrast(103%);
}

.filtro-cinza {
    filter: brightness(0) saturate(100%) invert(46%) sepia(3%) saturate(1218%) hue-rotate(202deg) brightness(95%) contrast(88%);
}

.scroll-content {
    scrollbar-width: none;
    /* Firefox */
    -ms-overflow-style: none;
    /* Edge */
    scroll-behavior: smooth;
}

.scroll-content::-webkit-scrollbar {
    display: none;
    /* Chrome e Safari */
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
    gap: 1rem;
}

.embla__slide {
    flex: 0 0 250px;
    /* Largura do card */
    min-width: 0;
}

@media screen and (max-width: 1280px) {
    .embla__slide {
        flex: 0 0 200px;
    }
}
</style>
