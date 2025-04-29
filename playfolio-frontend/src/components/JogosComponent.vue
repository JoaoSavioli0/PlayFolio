<template>
    <div class="py-6 min-[1800px]:px-40 px-20 h-full">
        <div class="flex h-full">

            <!-- Coluna esquerda -->
            <div class="w-[50%] pr-6 py-4">
                <div class="flex flex-col gap-y-2 pr-2">
                    <h1 class="text-end text-[30px]">📈 MAIS REVIEWS</h1>
                    <!-- Card -->

                    <div class="mt-[1px] w-full h-[120px] flex rounded-2xl overflow-hidden"
                        v-for="(jogo, index) in jogosReview" :key="jogo.id">

                        <router-link :to="`/game/${jogo.id}`" class="w-full h-full p-0">
                            <div class="w-full h-full relative overflow-hidden">

                                <!-- 🎯 BG principal com blur -->
                                <div class="absolute inset-0 z-10">
                                    <img :src="capasJogos[jogo.cover]" class="w-full h-full object-cover blur-lg" />
                                </div>

                                <!-- 🌫️ Gradiente por cima -->
                                <div class="absolute inset-0 z-20 w-full h-full bg-black/60"></div>

                                <!-- 🖼️ Miniatura frontal -->
                                <div
                                    class="absolute top-1/2 start-[15px] z-30 -translate-y-1/2 w-[65px] h-[90px] overflow-hidden rounded-md shadow-md">
                                    <img :src="capasJogos[jogo.cover]" class="w-full h-full object-cover" />
                                </div>

                                <div class="w-full pl-[90px] h-full z-[100] relative">
                                    <div class="flex absolute end-[20px] top-[20px] items-center">
                                        <img src="../assets/Imagens/stack.svg" class="w-[17px] h-auto filtro-branco">
                                        <span class="ml-2 text-[12px] xl:text-[15px]">{{ jogo.total_rating_count
                                            }}</span>
                                    </div>
                                    <div class="flex flex-col w-full justify-center h-full">
                                        <div class="flex items-center">
                                            <div
                                                class="w-[29px] h-[29px] rounded-lg bg-zinc-800/30 flex items-center justify-center shrink-0">
                                                <span class="text-zinc-50 text-[10px] xl:text-[14px]">{{ index + 1
                                                    }}</span>
                                            </div>
                                            <div class="max-w-[55%] line-clamp-1 text-left">
                                                <span class="text-sm text-start ml-2 pr-4 xl:text-md">{{ jogo.name
                                                    }}</span>
                                            </div>
                                            <div class="pl-4 border-l-[1px] border-zinc-500"
                                                v-if="jogo.first_release_date">
                                                <span class="text-xs text-zinc-500 xl:text-md">{{
                                                    formataDataUnix(jogo.first_release_date) }}</span>
                                            </div>
                                        </div>
                                        <div class="flex w-full" v-if="jogo.genres">
                                            <div class="text-start w-full line-clamp-1 text-zinc-500 max-w-[60%]">
                                                <span v-for="(genreId, index) in jogo.genres" :key="genreId"
                                                    class="text-[10px] xl:text-[13px]">{{
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
            <div class="w-[50%] pl-6 py-4 border-l-[1px] border-zinc-800">
                <div class="flex flex-col gap-y-2 pl-2">
                    <h1 class="text-start text-[30px]">MELHORES NOTAS 💎</h1>
                    <!-- Card -->
                    <div class="mt-[1px] w-full h-[120px] flex rounded-2xl overflow-hidden"
                        v-for="(jogo, index) in jogosNota" :key="jogo.id">

                        <router-link :to="`/game/${jogo.id}`" class="w-full h-full p-0">
                            <div class="w-full h-full relative overflow-hidden">

                                <!-- 🎯 BG principal com blur -->
                                <div class="absolute inset-0 z-10">
                                    <img :src="capasJogos[jogo.cover]" class="w-full h-full object-cover blur-lg" />
                                </div>

                                <!-- 🌫️ Gradiente por cima -->
                                <div class="absolute inset-0 z-20 w-full h-full bg-black/60"></div>

                                <!-- 🖼️ Miniatura frontal -->
                                <div
                                    class="absolute top-1/2 start-[15px] z-30 -translate-y-1/2 w-[65px] h-[90px] overflow-hidden rounded-md shadow-md">
                                    <img :src="capasJogos[jogo.cover]" class="w-full h-full object-cover" />
                                </div>

                                <div class="w-full pl-[90px] h-full z-[100] relative">
                                    <div class="flex absolute end-[20px] top-[20px] items-center">
                                        <img src="../assets/Imagens/star.png" class="w-[17px] h-auto filtro-branco">
                                        <span class="ml-2 text-[12px]">{{ (jogo.rating / 10).toFixed(1) }}</span>
                                    </div>
                                    <div class="flex flex-col w-full justify-center h-full">
                                        <div class="flex items-center">
                                            <div
                                                class="w-[29px] h-[29px] rounded-lg bg-zinc-800/30 flex items-center justify-center shrink-0">
                                                <span class="text-zinc-50 text-[10px] xl:text-[14px]">{{ index + 1
                                                    }}</span>
                                            </div>
                                            <div class="max-w-[55%] line-clamp-1 break-all text-left">
                                                <span class="text-sm text-start ml-2 pr-4 xl:text-md w-full">{{
                                                    jogo.name
                                                }}</span>
                                            </div>
                                            <div class="pl-4 border-l-[1px] border-zinc-500"
                                                v-if="jogo.first_release_date">
                                                <span class="text-xs text-zinc-500 xl:text-md">{{
                                                    formataDataUnix(jogo.first_release_date) }}</span>
                                            </div>
                                        </div>
                                        <div class="flex w-full" v-if="jogo.genres">
                                            <div class="text-start w-full line-clamp-1 text-zinc-500 max-w-[60%]">
                                                <span v-for="(genreId, index) in jogo.genres" :key="genreId"
                                                    class="text-[10px] xl:text-[13px]">{{
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

        </div>



    </div>
</template>

<script>
import axios from 'axios';

export default {
    name: "Jogos",
    data() {
        return {
            jogosReview: [],
            jogosNota: [],
            jogosGenres: [],
            jogosPlatforms: [],
            genreIds: [],
            genresNomes: {},
            capasJogos: {},
            plataformasJogos: {},
            plataformasMapping: {
                1: "PlayStation",
                2: "Xbox",
                3: "PC",
                4: "Mobile"
            },
            jogosIds: []
        }
    },
    mounted() {
        this.carregaDados()
    },
    methods: {
        async carregaDados() {
            await this.carregaJogos()
            await this.carregaJogos2()
            setTimeout(await this.carregaInfo(), 1000)
        },

        async carregaJogos() {
            const body = `fields *; sort value desc; limit 6; sort total_rating_count desc;`;

            try {
                const response = await axios.post("/v4/games", body, {
                    headers: {
                        'Accept': 'application/json',
                        'Client-ID': "i79ndcjylui2396ezi2v752sc9dze0",
                        'Authorization': `Bearer h6v8ywcqhwyyhj140u70v95rss6sga`,
                        'Content-Type': 'text/plain'
                    }
                })
                this.jogosReview = response.data

                this.jogosReview.map((e) => { if (!this.jogosIds.includes(e.id)) { console.log("entrou: " + e.name); this.jogosIds.push(e.id) } })

                this.jogosReview.forEach(e => {
                    e.genres.forEach(genreId => {
                        if (!this.jogosGenres.includes(genreId)) {
                            this.jogosGenres.push(genreId)
                        }
                    });
                });

                this.jogosReview.forEach(e => {
                    e.platforms.forEach(platformId => {
                        if (!this.jogosPlatforms.includes(platformId)) {
                            this.jogosPlatforms.push(platformId)
                        }
                    })
                })

                console.log("jogosGenres: " + this.jogosGenres)


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

                this.jogosNota = response.data


                this.jogosNota.map((e) => { if (!this.jogosIds.includes(e.id)) this.jogosIds.push(e.id) })

                this.jogosNota.forEach(e => {
                    e.genres.forEach(genreId => {
                        if (!this.jogosGenres.includes(genreId)) {
                            this.jogosGenres.push(genreId);
                        }
                    });
                });

                this.jogosNota.forEach(e => {
                    e.platforms.forEach(platformId => {
                        if (!this.jogosPlatforms.includes(platformId)) {
                            this.jogosPlatforms.push(platformId)
                        }
                    })
                })

            } catch (error) {
                console.error("Erro: " + error)
            }
        },

        async carregaInfo() {
            await this.carregaCapas(this.jogosIds)
            setTimeout(await this.carregaTags(this.jogosGenres), 3000)
            await this.carregaPlataformas(this.jogosPlatforms)
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

        async carregaTags(generos) {

            const body = `fields name; where id = (${generos.join(", ")}); limit 50;`
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
        },

        formataDataUnix(dataUnix) {
            const timestamp = dataUnix;
            const data = new Date(timestamp * 1000);
            const options = { year: 'numeric' };
            return data.toLocaleString('pt-BR', options)
        },

        async carregaPlataformas(plataformas) {
            let id = 0

            const body = `fields abbreviation; where id = (${plataformas.join(", ")}); limit 50;`

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
    background: rgb(29, 30, 34);
    background: linear-gradient(90deg, rgba(29, 30, 34, 0) 0%, rgba(29, 30, 34, 0.4990371148459384) 15%, rgba(29, 30, 34, 0.700717787114846) 40%, rgba(29, 30, 34, 0.896796218487395) 60%, rgba(29, 30, 34, 1) 90%);
}

.filtro-branco {
    filter: brightness(0) saturate(100%) invert(100%) sepia(0%) saturate(0%) hue-rotate(168deg) brightness(103%) contrast(103%);
}
</style>