<template>
    <NavbarComponent />
    <div class="w-full h-full pb-6 pt-32 px-24 flex items-center justify-center">
        <div
            class="w-[1100px] h-min rounded-2xl border-[2px] border-zinc-600 overflow-hidden flex flex-col bg-[#262729]">
            <div class="w-full h-[450px] z-40 relative">
                <img :src="imagens[0]" class="w-full h-full">
                <div class="absolute bottom-0 w-full h-[25%] gradiente-cinza-transparente"></div>
            </div>
            <div class="w-full z-50 flex">

                <!-- Caixa esquerda -->
                <div class="w-max pl-4 pr-2">
                    <div
                        class="w-full h-[105px] px-4 flex items-center rounded-2xl border-[2px] border-zinc-700 mt-[-100px] bg-[#262729]">

                        <img :src="capasJogos[id]" class="w-[65px] h-auto rounded-lg shadow-md">

                        <div class="flex w-full h-full pl-2" v-if="dados">
                            <div class="flex flex-col text-start px-4 justify-center h-full">
                                <span class="text-[12px] text-zinc-300">NOTA</span>
                                <h2 class="text-2xl"
                                    :class="[{ 'text-lime-400': dados.total_rating >= 90 }, { 'text-red-600': dados.total_rating <= 50 }, { 'text-teal-300': dados.total_rating >= 70 && dados.total_rating < 90 }, { 'text-amber-400': dados.rating > 50 && dados.total_rating < 70 }]">
                                    {{ (dados.total_rating / 10).toFixed(1) }}</h2>
                                <span class="text-[10px] text-zinc-300"></span>
                            </div>

                            <div class="flex flex-col text-start px-4 justify-center h-full">
                                <span class="text-[12px] text-zinc-300">AVALIAÇÕES</span>
                                <h2 class="text-2xl">
                                    {{ dados.total_rating_count }}</h2>
                                <span class="text-[10px] text-zinc-300"></span>
                            </div>
                        </div>
                    </div>
                </div>

            </div>

            <div class="py-6 px-4 text-start">
                <div class="flex items-center">
                    <h2 class="text-3xl pr-2">{{ dados.name }}</h2>
                    <span class="text-xl text-zinc-400 pl-2 border-l-[2px] border-zinc-400">{{
                        formataDataUnix(dados.first_release_date, 1) }}</span>
                </div>
                <div class="w-full">
                    <span class="text-[10px] text-zinc-400">{{ dados.parent_game ? "Expansão" : "Jogo Principal"
                    }}</span>
                </div>
                <div class="w-full">
                    <ul>
                        <li>
                            <span class="text-xs text-zinc-400">Data de Lançamento: </span>
                            <span class="inline text-zinc-50 text-xs"> {{ formataDataUnix(dados.first_release_date, 2)
                            }}</span>
                        </li>
                        <li>
                            <span class="text-xs text-zinc-400">Gêneros: </span>
                            <span class="inline text-zinc-50 text-xs"> {{ tags.join(", ") }}</span>
                        </li>
                        <li>
                            <span class="text-xs text-zinc-400">Plataformas: </span>
                            <span class="inline text-zinc-50 text-xs"> {{ plataformas.join(", ") }}</span>
                        </li>
                        <li>
                            <span class="text-xs text-zinc-400">Desenvolvedora: </span>
                            <span class="inline text-zinc-50 text-xs"> {{empresas.find(e =>
                                e.developer)?.name || "Não encontrado"}}</span>
                        </li>
                        <li>
                            <span class="text-xs text-zinc-400">Desenvolvedora: </span>
                            <span class="inline text-zinc-50 text-xs"> {{empresas.find(e =>
                                e.developer)?.name || "Não encontrado"}}</span>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import NavbarComponent from '@/components/NavbarComponent.vue'
import axios from 'axios'

export default {
    name: "Game",
    props: ['id'],
    data() {
        return {
            imagens: [],
            capasJogos: [],
            dados: {},
            plataformas: [],
            tags: [],
            empresas: [],
            nomeEmpresas: []
        }
    },
    mounted() {
        this.carregaDados()
    },
    components: {
        NavbarComponent
    },
    methods: {
        async carregaDados() {
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


            } catch (error) {
                console.error("Erro ao carregar dados do jogo: " + error)
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
    }
}
</script>

<style>
.gradiente-cinza-transparente {
    background: rgb(38, 39, 41);
    background: linear-gradient(0deg, rgba(38, 39, 41, 1) 0%, rgba(20, 20, 21, 0.4565475848542542) 47%, rgba(0, 0, 0, 0) 83%);
}

.gradiente-cinza-claro {
    background: rgb(26, 27, 30);
    background: linear-gradient(0deg, rgba(26, 27, 30, 1) 1%, rgba(43, 44, 48, 1) 100%);
}
</style>