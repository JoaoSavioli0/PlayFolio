<template>
    <div class="py-6 px-24">
        <div class="flex">

            <!-- Coluna esquerda -->
            <div class="w-[50%] p-6">
                <div class="flex flex-col gap-y-2 p-4">
                    <h1 class="text-start">MAIS REVIEWS</h1>
                    <!-- Card -->

                    <div class="rounded-xl mt-4 w-full h-[110px] flex overflow-hidden outline-2 outline-[#1a1b1e] border-2 border-[#1a1b1e]"
                        v-for="(jogo, index) in jogos" :key="jogo.id">
                        <router-link :to="`/game/${jogo.id}`" class="p-0 w-full">
                            <div class="w-full relative h-full">
                                <div
                                    class="rounded-md w-[65px] absolute h-auto overflow-hidden z-50 top-1/2 start-[20px] -translate-y-1/2 shadow-md">
                                    <img :src="capasJogos[jogo.id]" class="object-fit w-full h-full">
                                </div>
                                <div class="w-full h-full z-30 bg-[#262729]/80 backdrop-blur-sm absolute"></div>
                                <img :src="capasJogos[jogo.id]"
                                    class="object-fit absolute w-[90%] h-auto z-20 -translate-y-1/2 top-1/2">
                                <div class="absolute w-full h-full gradiente-card z-40"></div>

                                <div class="w-full pl-24 h-full z-[100] relative">
                                    <div class="flex absolute end-[20px] top-[20px] items-center">
                                        <img src="../assets/Imagens/stack.svg" class="w-[17px] h-auto filtro-branco">
                                        <span class="ml-2 text-[12px]">{{ jogo.rating_count }}</span>
                                    </div>
                                    <div class="flex flex-col w-full justify-center h-full">
                                        <div class="flex items-center">
                                            <div
                                                class="w-[29px] h-[29px] rounded-lg bg-zinc-800/30 flex items-center justify-center shrink-0">
                                                <span class="text-zinc-50 text-[10px]">{{ index + 1 }}</span>
                                            </div>
                                            <span class="text-sm text-start ml-2 pr-4">{{ jogo.name }}</span>
                                            <div class="pl-4 border-l-[1px] border-zinc-500"
                                                v-if="jogo.first_release_date">
                                                <span class="text-xs text-zinc-500">{{
                                                    formataDataUnix(jogo.first_release_date) }}</span>
                                            </div>
                                        </div>
                                        <div class="flex w-full" v-if="jogo.genres">
                                            <div class="text-start w-full line-clamp-1 text-zinc-500 max-w-[60%]">
                                                <span v-for="(genreId, index) in jogo.genres" :key="genreId"
                                                    class="text-[10px] ">{{
                                                        genresNomes[genreId] == "Role-playing (RPG)" ? "RPG" :
                                                            genresNomes[genreId]
                                                    }}<span v-if="index !== jogo.genres.length - 1">, </span></span>
                                            </div>
                                        </div>

                                        <div class="flex items-center gap-x-2 w-full pt-2" v-if="jogo.platforms">
                                            <img src="../assets/Imagens/ps_logo.png"
                                                v-if="jogo.platforms.some(plataforma => plataformasJogos[plataforma] == 1)"
                                                class="w-[20px] h-auto filtro-branco">
                                            <img src="../assets/Imagens/xbox_logo.svg"
                                                v-if="jogo.platforms.some(plataforma => plataformasJogos[plataforma] == 2)"
                                                class="w-[16px] h-auto filtro-branco">
                                            <img src="../assets/Imagens/pc_logo.png"
                                                v-if="jogo.platforms.some(plataforma => plataformasJogos[plataforma] == 3)"
                                                class="w-[20px] h-auto filtro-branco">
                                            <img src="../assets/Imagens/ios_logo.png"
                                                v-if="jogo.platforms.some(plataforma => plataformasJogos[plataforma] == 4)"
                                                class="w-[20px] h-auto filtro-branco">
                                            <img src="../assets/Imagens/switch_logo.png"
                                                v-if="jogo.platforms.some(plataforma => plataformasJogos[plataforma] == 5)"
                                                class="w-[20px] h-auto filtro-branco">
                                            <img src="../assets/Imagens/stadia_logo.png"
                                                v-if="jogo.platforms.some(plataforma => plataformasJogos[plataforma] == 6)"
                                                class="w-[20px] h-auto filtro-branco">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </router-link>
                    </div>
                </div>
            </div>

            <!-- Coluna direita -->
            <div class="w-[50%] p-6">
                <div class="flex flex-col gap-y-2 p-4">
                    <h1 class="text-start">MELHORES NOTAS</h1>
                    <!-- Card -->
                    <div class="rounded-xl mt-4 w-full h-[110px] flex overflow-hidden outline-2 outline-[#1a1b1e] border-2 border-[#1a1b1e]"
                        v-for="(jogo, index) in jogosRecentes" :key="jogo.id">
                        <div class="w-full relative h-full">
                            <div
                                class="rounded-md w-[65px] absolute h-auto overflow-hidden z-50 top-1/2 start-[20px] -translate-y-1/2 shadow-md">
                                <img :src="capasJogos[jogo.id]" class="object-fit w-full h-full">
                            </div>
                            <div class="w-full h-full z-30 bg-[#262729]/80 backdrop-blur-sm absolute"></div>
                            <img :src="capasJogos[jogo.id]"
                                class="object-fit absolute w-[90%] h-auto z-20 -translate-y-1/2 top-1/2">
                            <div class="absolute w-full h-full gradiente-card z-40"></div>

                            <div class="w-full pl-24 h-full z-[100] relative">
                                <div class="flex absolute end-[20px] top-[20px] items-center">
                                    <img src="../assets/Imagens/star.png" class="w-[17px] h-auto filtro-branco">
                                    <span class="ml-2 text-[12px]">{{ (jogo.rating / 10).toFixed(1) }}</span>
                                </div>
                                <div class="flex flex-col w-full justify-center h-full">
                                    <div class="flex items-center">
                                        <div
                                            class="w-[29px] h-[29px] rounded-lg bg-zinc-800/30 flex items-center justify-center shrink-0">
                                            <span class="text-zinc-50 text-[10px]">{{ index + 1 }}</span>
                                        </div>
                                        <div class="max-w-[55%] line-clamp-1">
                                            <span class="text-sm text-start ml-2 pr-4">{{ jogo.name }}</span>
                                        </div>
                                        <div class="pl-4 border-l-[1px] border-zinc-500" v-if="jogo.first_release_date">
                                            <span class="text-xs text-zinc-500">{{
                                                formataDataUnix(jogo.first_release_date) }}</span>
                                        </div>
                                    </div>
                                    <div class="flex w-full">
                                        <div v-if="jogo.genres"
                                            class="text-start w-full line-clamp-1 text-zinc-500 max-w-[60%]">
                                            <span v-for="(genreId, index) in jogo.genres" :key="genreId"
                                                class="text-[10px] ">{{
                                                    genresNomes[genreId] == "Role-playing (RPG)" ? "RPG" :
                                                        genresNomes[genreId]
                                                }}<span v-if="index !== jogo.genres.length - 1">, </span></span>
                                        </div>
                                    </div>

                                    <div class="flex items-center gap-x-2 w-full pt-2" v-if="jogo.platforms">
                                        <img src="../assets/Imagens/ps_logo.png"
                                            v-if="jogo.platforms.some(plataforma => plataformasJogos[plataforma] == 1)"
                                            class="w-[20px] h-auto filtro-branco">
                                        <img src="../assets/Imagens/xbox_logo.svg"
                                            v-if="jogo.platforms.some(plataforma => plataformasJogos[plataforma] == 2)"
                                            class="w-[16px] h-auto filtro-branco">
                                        <img src="../assets/Imagens/pc_logo.png"
                                            v-if="jogo.platforms.some(plataforma => plataformasJogos[plataforma] == 3)"
                                            class="w-[20px] h-auto filtro-branco">
                                        <img src="../assets/Imagens/ios_logo.png"
                                            v-if="jogo.platforms.some(plataforma => plataformasJogos[plataforma] == 4)"
                                            class="w-[20px] h-auto filtro-branco">
                                        <img src="../assets/Imagens/switch_logo.png"
                                            v-if="jogo.platforms.some(plataforma => plataformasJogos[plataforma] == 5)"
                                            class="w-[20px] h-auto filtro-branco">
                                        <img src="../assets/Imagens/stadia_logo.png"
                                            v-if="jogo.platforms.some(plataforma => plataformasJogos[plataforma] == 6)"
                                            class="w-[20px] h-auto filtro-branco">
                                    </div>
                                </div>
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
    name: "Jogos",
    data() {
        return {
            jogos: [],
            jogosRecentes: [],
            genreIds: [],
            genresNomes: {},
            capasJogos: {},
            plataformasJogos: {},
            plataformasMapping: {
                1: "PlayStation",
                2: "Xbox",
                3: "PC",
                4: "Mobile"
            }
        }
    },
    mounted() {
        this.carregaJogos()
        this.carregaJogos2()
    },
    methods: {
        async carregaJogos() {
            const body = `fields *; sort value desc; limit 6; sort rating_count desc;`;

            try {
                const response = await axios.post("/v4/games", body, {
                    headers: {
                        'Accept': 'application/json',
                        'Client-ID': "i79ndcjylui2396ezi2v752sc9dze0",
                        'Authorization': `Bearer h6v8ywcqhwyyhj140u70v95rss6sga`,
                        'Content-Type': 'text/plain'
                    }
                })
                this.jogos = response.data
                console.log("this.jogos: " + JSON.stringify(this.jogos))
                await this.carregaCapas(this.jogos.map((e) => e.id))
                await this.carregaTags(this.jogos)
                await this.carregaPlataformas(this.jogos)


            } catch (error) {
                console.error("Erro: " + error)
            }
        },

        async carregaJogos2() {
            const body = `fields *; limit 6; where rating_count > 300 & version_parent = null & parent_game = null; sort rating desc;`;

            try {
                const response = await axios.post("/v4/games", body, {
                    headers: {
                        'Accept': 'application/json',
                        'Client-ID': "i79ndcjylui2396ezi2v752sc9dze0",
                        'Authorization': `Bearer h6v8ywcqhwyyhj140u70v95rss6sga`,
                        'Content-Type': 'text/plain'
                    }
                })
                this.jogosRecentes = response.data
                await this.carregaCapas(this.jogosRecentes.map((e) => e.id))
                await this.carregaTags(this.jogosRecentes)
                await this.carregaPlataformas(this.jogosRecentes)

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
                    console.log("this.capaJogos: " + this.capasJogos)
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
            const options = { year: 'numeric' };
            return data.toLocaleString('pt-BR', options)
        },

        async carregaPlataformas(jogos) {
            let id = 0
            let platformIds = []
            for (let jogo of jogos) {
                for (let platformId of jogo.platforms) {
                    if (!platformIds.includes(platformId)) {
                        platformIds.push(platformId)
                    }
                }
            }

            const body = `fields abbreviation; where id = (${platformIds.join(", ")}); limit 50;`

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
                    let abbreviation = data.abbreviation
                    if (abbreviation.includes("PS") || abbreviation.includes("Vita")) id = 1
                    if (abbreviation.includes("Series X|S") || abbreviation.includes("XBOX") || abbreviation.includes("X360")) id = 2
                    if (abbreviation.includes("PC")) id = 3
                    if (abbreviation.includes("iOS")) id = 4
                    if (abbreviation.includes("Switch")) id = 5
                    if (abbreviation.includes("Stadia")) id = 6

                    this.plataformasJogos[data.id] = id
                }

                console.log("Plataformas: " + JSON.stringify(this.plataformasJogos))

            } catch (error) {
                console.error("Erro carregando plataformas: " + error)
            }
        },

        encontraPlataforma(id) {
            idMapping = this.plataformasJogos[id]
            return this.plataformasMapping[idMapping]
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
.gradiente-card {
    background: rgb(38, 39, 41);
    background: linear-gradient(270deg, rgba(38, 39, 41, 1) 0%, rgba(0, 0, 0, 0) 76%);
}

.filtro-branco {
    filter: brightness(0) saturate(100%) invert(100%) sepia(0%) saturate(0%) hue-rotate(168deg) brightness(103%) contrast(103%);
}
</style>