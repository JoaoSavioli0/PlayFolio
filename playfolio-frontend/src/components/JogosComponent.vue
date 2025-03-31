<template>
    <div class="py-6 px-24">
        <div class="flex">
            <div class="w-[50%] p-6">
                <div class="flex flex-col gap-y-2 p-4">
                    <!-- Card -->
                    <div class="rounded-xl w-full h-[110px] flex overflow-hidden outline-2 outline-[#1a1b1e] border-2 border-[#1a1b1e]"
                        v-for="(jogo, index) in jogos" :key="jogo.id">
                        <div class="w-full relative h-full">
                            <div
                                class="rounded-md w-[65px] absolute h-auto overflow-hidden z-50 top-1/2 start-[20px] -translate-y-1/2 shadow-md">
                                <img :src="capasJogos[jogo.id]" class="object-fit w-full h-full">
                            </div>
                            <div class="w-full h-full z-30 bg-[#262729]/80 backdrop-blur-lg absolute"></div>
                            <img :src="capasJogos[jogo.id]"
                                class="object-fit absolute w-[90%] h-auto z-20 -translate-y-1/2 top-1/2">
                            <div class="absolute w-full h-full gradiente-card z-40"></div>

                            <div class="w-full pl-24 h-full z-[100] relative">
                                <div class="flex flex-col w-full justify-center h-full">
                                    <div class="flex items-center">
                                        <div
                                            class="w-[29px] h-[29px] rounded-lg bg-zinc-800/30 flex items-center justify-center shrink-0">
                                            <span class="text-zinc-50 text-[10px]">{{ index + 1 }}</span>
                                        </div>
                                        <span class="text-sm text-start ml-2 pr-4">{{ jogo.name }}</span>
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
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="w-[40%] p-6"></div>
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

            const body = `fields abbreviation; where id = (${platformIds.join(", ")});`

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
                    if (abbreviation.includes("PS")) id = 1
                    if (abbreviation.includes("Series X|S") || abbreviation.includes("XBOX") || abbreviation.includes("X360")) id = 2
                    if (abbreviation.includes("PC")) id = 3
                    if (abbreviation.includes("iOS")) id = 4

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
    }
}
</script>

<style scoped>
.gradiente-card {
    background: rgb(38, 39, 41);
    background: linear-gradient(270deg, rgba(38, 39, 41, 1) 0%, rgba(0, 0, 0, 0) 76%);
}
</style>