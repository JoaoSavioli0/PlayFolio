<template>
    <div class="w-full px-24 pt-12 pb-6" v-if="jogosDestaque.length">
        <div class="flex w-full items-center">
            <div class="whitespace-nowrap flex items-center">
                <h2 class="text-2xl text-start text-zinc-50">DESTAQUES</h2>
                <img src="../assets/fogo.svg" class="filtro-branco ml-2 w-[30px] h-auto">
            </div>
        </div>
        <div class="w-full overflow-hidden cursor-grab">
            <div ref="scrollContent" class="scroll-content flex overflow-x-auto whitespace-nowrap flex gap-4 py-8"
                @mousedown="startScroll" @mousemove="moveScroll" @mouseup="stopScroll" @mouseleave="stopScroll">



                <div class="relative group select-none" v-for="jogo in jogosDestaque" :key="jogo.id">

                    <div
                        class="h-[320px] w-[200px] clip-card bg-[#262729] flex flex-col shrink-0 relative card-area rounded-xl overflow-hidden border-[1px] border-zinc-600">

                        <div class="w-full h-[200px] relative overflow-hidden relative image-area z-40">
                            <img :src="capasJogos[jogo.id]" alt="">
                            <div class="w-full h-full absolute gradiente-card top-0"></div>

                            <div class="end-0 top-0 px-[10px] py-[4px] justify-center flex absolute shrink-0 items-center bg-[#1A1B1E] z-50 rounded-bl-xl border-l-[1px] border-b-[1px] border-zinc-600 shadow-lg"
                                v-if="jogo.rating">
                                <img src="../assets/star.png" class="size-[12px]">
                                <span class="ml-2 text-xs">{{ (jogo.rating / 10).toFixed(1) }}</span>
                            </div>
                        </div>

                        <div
                            class="w-full h-[150px] px-4 py-2 flex flex-col justify-between pb-4 text-area transition-all">
                            <div>
                                <span
                                    class="text-start text-zinc-50 text-sm font-medium line-clamp-2 break-words hyphens-auto w-full">
                                    {{ jogo.name }}</span>
                                <div v-if="jogo.genres" class="text-start w-full line-clamp-2 text-zinc-500 ">
                                    <span v-for="(genreId, index) in jogo.genres" :key="genreId" class="text-[10px] ">{{
                                        genresNomes[genreId] == "Role-playing (RPG)" ? "RPG" : genresNomes[genreId]
                                        }}<span v-if="index !== jogo.genres.length - 1">, </span></span>
                                </div>
                                <div v-if="jogo.first_release_date"
                                    class="mt-2 flex items-center w-full justify-start opacity-0 group-hover:opacity-100 transition-all duration-300 bg-transparent">
                                    <img src="../assets/calendario.svg" class="w-[15px] h-auto filtro-cinza">

                                    <span class="text-[10px] text-zinc-500 ml-2" v-if="jogo.first_release_date">{{
                                        formataDataUnix(jogo.first_release_date) }}</span>
                                </div>
                            </div>
                            <div class="w-full relative">

                                <button
                                    class="w-full border-2 border-zinc-500 rounded-xl cursor-pointer opacity-0 absolute translate-y-[5px] group-hover:opacity-100 group-hover:-translate-y-[40px] transition-all duration-300"><span
                                        class="text-zinc-300 text-xs">QUERO JOGAR</span></button>
                                <button
                                    class="w-full border-2 border-zinc-500 rounded-xl cursor-pointer shadow-sm hover:-translate-y-[4px] transition-all peer">
                                    <span class="text-zinc-200 text-xs py-[8px] peer:hover:text-white">JÁ
                                        JOGUEI</span>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';

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
            scrollLeft: 0
        }
    },
    mounted() {
        this.carregaJogosDestaque()
    },
    methods: {
        async carregaJogosDestaque() {
            const body = `
      fields game_id; sort value desc; limit 10; where popularity_type = 1;
      `;

            try {
                const response = await axios.post("/v4/popularity_primitives", body, {
                    headers: {
                        'Accept': 'application/json',
                        'Client-ID': "i79ndcjylui2396ezi2v752sc9dze0",
                        'Authorization': `Bearer h6v8ywcqhwyyhj140u70v95rss6sga`,
                        'Content-Type': 'text/plain'
                    }
                })
                let jogosDestaqueId = response.data
                jogosDestaqueId = jogosDestaqueId.map(e => e.game_id)
                this.jogosDestaque = await this.encontraJogos(jogosDestaqueId)

                await this.carregaTags(this.jogosDestaque)

            } catch (error) {
                console.error("Erro: " + error)
            }
        },
        async encontraJogos(jogosId) {
            const body = `fields *; where id = (${jogosId.join(', ')});`

            try {
                const response = await axios.post("/v4/games", body, {
                    headers: {
                        'Accept': 'application/json',
                        'Client-ID': "i79ndcjylui2396ezi2v752sc9dze0",
                        'Authorization': `Bearer h6v8ywcqhwyyhj140u70v95rss6sga`,
                        'Content-Type': 'text/plain'
                    }
                })
                await this.carregaCapas(jogosId)

                return response.data
            } catch (error) {
                console.error("Erro: " + error)
            }

        },

        async carregaCapas(jogosId) {
            const requests = jogosId.map(async (id) => {
                const body = `fields url; where game = ${id}; limit 1;`
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

            const body = `fields name; where id = (${this.genreIds.join(", ")});`
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
                    this.genresNomes[data.id] = data.name
                }

            } catch (error) {
                console.error("Erro carregando generos: " + error)
            }
            console.log("genreNomes: " + this.genresNomes)
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

.card-area:hover .text-area {
    height: 300px;
    transition: all;
    transition-duration: 300ms;
}

.card-area:hover .button-quero {
    opacity: 1;
    transition-duration: 300ms;
    transform: translateY(-50px)
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
</style>