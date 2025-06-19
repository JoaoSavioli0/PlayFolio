<template>
    <div class="w-screen h-screen bg-black/50 flex justify-center items-center top-0 start-0 fixed z-[2100]"
        v-if="exibeExcluiReviewBox">
        <div class="bg-zinc-800 rounded-lg px-6 xl:w-[400px] w-[90%] py-12" ref="excluirBox">
            <h2 class="text-zinc-50 text-center">Deseja mesmo excluir?</h2>
            <div class="flex w-full justify-center mt-6">
                <button @click="exibeExcluiReviewBox = false"
                    class="w-[40%] rounded-md bg-zinc-700 text-zinc-50 h-[50px] text-sm cursor-pointer">Cancelar</button>
                <button @click="() => { wishlist ? excluirWishlist() : excluirReview() }"
                    class="w-[40%] rounded-md bg-zinc-700 text-rose-600 h-[50px] text-sm cursor-pointer ml-2 border-rose-700 border-2">Excluir</button>
            </div>
        </div>
    </div>

    <div
        class="w-full h-screen flex justify-center items-center fixed start-0 top-0 overflow-y-hidden z-[2000] max-xl:px-2">
        <div class="w-full h-full bg-black/80 z-[100] absolute top-0 start-0" @click="fechaBox"></div>

        <div class="xl:w-[700px] w-full h-max z-[110] rounded-2xl flex flex-col overflow-hidden">
            <div class="w-full flex flex-col relative xl:min-h-[80px] items-start">
                <div class="w-full xl:py-6 py-2 flex justify-between z-50">
                    <div class="px-4 max-xl:flex xl:grid grid-cols-4 items-center xl:gap-4 z-[60]">
                        <div class="flex justify-center shrink-0">
                            <img :src="capa" class="xl:w-[110px] w-[90px] h-[130px] xl:h-auto rounded-md shadow-md">
                        </div>

                        <div class="col-span-3 relative flex flex-col justify-center h-[150px] max-xl:ml-2">
                            <div class="flex w-full text-start items-center relative h-[40px]">
                                <div class="xl:size-[42px] size-[35px] overflow-hidden">
                                    <img src="../assets/Imagens/expressoes/1.png" v-if="nota == 0"
                                        class="w-full h-full objet-cover">
                                    <img src="../assets/Imagens/expressoes/2.png" v-else-if="nota > 0 && nota <= 4"
                                        class="w-full h-full objet-cover">
                                    <img src="../assets/Imagens/expressoes/3.png" v-else-if="nota > 4 && nota <= 6.5"
                                        class="w-full h-full objet-cover">
                                    <img src="../assets/Imagens/expressoes/4.png" v-else-if="nota > 6.5 && nota <= 8.5"
                                        class="w-full h-full objet-cover">
                                    <img src="../assets/Imagens/expressoes/5.png" v-else-if="nota > 8.5"
                                        class="w-full h-full objet-cover">
                                </div>
                                <div
                                    class="relative overflow-hidden h-[80px] w-[120px] z-[60] xl:text-4xl text-2xl top-[20px] start-[10px]">

                                    <h1 class="absolute start-0 top-0 transition-opacity duration-500" :class="{
                                        'opacity-100 degradeCinza': nota == 0,
                                        'opacity-0': nota != 0
                                    }">{{ nota }}</h1>

                                    <h1 class="absolute start-0 top-0 transition-opacity duration-500 degradeVermelho"
                                        :class="{
                                            'opacity-100 ': nota > 0 && nota <= 4,
                                            'opacity-0': nota == 0 || nota > 4
                                        }">{{ nota }}</h1>

                                    <h1 class="absolute start-0 top-0 transition-opacity duration-500 degradeAmarelo"
                                        :class="{
                                            'opacity-100 ': nota > 4 && nota <= 6.5,
                                            'opacity-0': nota < 4 || nota > 6.5
                                        }">{{ nota }}</h1>

                                    <h1 class="absolute transition-opacity duration-500 degradeAzul" :class="{
                                        'opacity-100 ': nota > 6.5 && nota <= 8.5,
                                        'opacity-0': nota <= 6.5 || nota > 8.5
                                    }">{{ nota }}</h1>

                                    <h1 class="absolute transition-opacity duration-500 degradeRoxo" :class="{
                                        'opacity-100 ': nota > 8.5,
                                        'opacity-0': nota <= 8.5
                                    }">{{ nota }}</h1>
                                </div>
                            </div>
                            <h1
                                class="w-full text-start xl:text-xl text-md mt-2 xl:max-w-[80%] max-w-[90%] line-clamp-2">
                                {{ dados.name
                                }}</h1>
                            <span class="w-full text-start text-md text-zinc-500">{{
                                formataDataUnix(dados.first_release_date)
                            }}</span>
                        </div>
                    </div>
                    <button class="self-start"><img src="../assets/Imagens/close.svg"
                            class="size-[28px] mr-4 filtro-branco cursor-pointer" @click="fechaBox"></button>
                </div>



                <div class="w-full h-full bg-[#262729]/80 z-40 absolute"></div>
                <img v-if="imagem" :src="imagem" class="w-full h-full object-cover absolute z-30">
            </div>

            <!-- Status -->
            <div class="w-full py-4 h-auto bg-[#262729]">
                <div
                    class="flex px-4 gap-x-2 xl:h-[30px] h-[70px] items-center justify-between xl:mt-4 max-xl:overflow-x-scroll">

                    <button :disabled="statusDefault == 5"
                        class="flex grow-1 justify-center items-center rounded-full border py-[4px] px-4 bg-zinc-800 transition"
                        @click="status = 1" :class="[
                            status == 1 ? 'border-[#c31ef1] text-[#c31ef1] shadow-[0px_0px_26px_-4px_#c31ef1] -translate-y-[2px]' : 'border-zinc-500 text-zinc-400',
                            statusDefault == 5 ? 'border-zinc-700 text-zinc-700' : 'cursor-pointer'
                        ]">
                        <img src="../assets/Imagens/joystick-2.svg" class="w-[20px] h-auto"
                            :class="{ 'filtro-cinza': status != 1, 'filtro-cinza-escuro': statusDefault == 5 }">
                        <span class="ml-[6px] max-[1800px]:text-sm whitespace-nowrap">Jogando</span>
                    </button>

                    <button :disabled="statusDefault == 5"
                        class="flex grow-1 justify-center h-min items-center rounded-full border py-[4px] px-4 bg-zinc-800 transition"
                        @click="status = 2" :class="[status == 2 ? 'border-green-600 text-green-600 shadow-[0px_0px_26px_-4px_#0AA63E] -translate-y-[2px]' : 'border-zinc-500 text-zinc-400',
                        statusDefault == 5 ? 'border-zinc-700 text-zinc-700' : 'cursor-pointer']">
                        <img src="../assets/Imagens/flag.svg" class="w-[17px] h-auto"
                            :class="{ 'filtro-cinza-verde': status != 2, 'filtro-cinza-escuro': statusDefault == 5 }">
                        <span class="ml-[4px] max-[1800px]:text-sm whitespace-nowrap">Zerado</span>
                    </button>

                    <button :disabled="statusDefault == 5"
                        class="flex grow-1 justify-center items-center rounded-full border py-[4px] px-4 bg-zinc-800 transition"
                        @click="status = 3" :class="[status == 3 ? 'border-[#d22d56] text-[#d22d56] shadow-[0px_0px_26px_-4px_#d22d56] -translate-y-[2px]' : 'border-zinc-500 text-zinc-400',
                        statusDefault == 5 ? 'border-zinc-700 text-zinc-700' : 'cursor-pointer']">
                        <img src="../assets/Imagens/skull.svg" class="w-[18px] h-auto "
                            :class="{ 'filtro-cinza': status != 3, 'filtro-cinza-escuro': statusDefault == 5 }">
                        <span class="ml-[6px] max-[1800px]:text-sm whitespace-nowrap">Dropado</span>
                    </button>

                    <button
                        class="flex grow-1 justify-center items-center rounded-full border py-[4px] px-4 bg-zinc-800 transition cursor-pointer"
                        @click="status = 4"
                        :class="status == 4 ? 'border-[#eebb17] text-[#eebb17] shadow-[0px_0px_26px_-4px_#eebb17] -translate-y-[2px]' : 'border-zinc-500 text-zinc-400'">

                        <img src="../assets/Imagens/shine.svg" class="w-[18px] h-auto pr-[2px]"
                            :class="{ 'filtro-cinza': status != 4 }">
                        <span class="ml-[6px] max-[1800px]:text-sm whitespace-nowrap">Quero jogar</span>
                    </button>

                </div>

                <!-- Nota -->
                <div class="w-full mt-4 px-4" v-if="status != 4">
                    <div class="relative w-full mt-[4px]">
                        <input type="range" min="0" max="10" step="0.1" v-model="nota"
                            class="inputRange w-full relative z-30" @input="atualizarImagem" @change="updateOffset()" />

                        <div class="w-full absolute top-0 start-0 z-10">
                            <div class="w-full relative">
                                <div class="absolute top-[14px] start-[60px] flex gap-x-2">
                                    <span class="text-zinc-700 text-xs">Deslize</span>
                                    <span class="opacity-75 text-zinc-700 text-xs">></span>
                                    <span class="opacity-50 text-zinc-700 text-xs">></span>
                                    <span class="opacity-25 text-zinc-700 text-xs">></span>
                                </div>
                            </div>
                        </div>

                        <div class="w-full absolute top-0 start-0 z-40">
                            <div class="w-full relative">
                                <span
                                    class="absolute top-[10px] start-[16px] pointer-events-none text-zinc-500">0</span>
                                <span class="absolute top-[10px] end-[12px] pointer-events-none text-zinc-500">10</span>
                            </div>
                        </div>

                        <div class="w-full absolute top-[8px] start-0 h-[5px] z-20" v-if="dados.total_rating">
                            <div class="w-full relative">
                                <span class="absolute whitespace-nowrap text-zinc-400 flex flex-col items-center"
                                    :style="{ 'left': posicaoMedia * 0.85 + '%' }">
                                    <h2 class="block text-[10px]">MÉDIA</h2>
                                    <span class="text-sm text-[11px]">{{ (dados.total_rating / 10).toFixed(1) }}
                                    </span>
                                </span>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Comentário -->
                <div class="w-full px-4 mt-2">
                    <div class="w-full relative rounded-xl border border-zinc-500" v-if="status != 4">

                        <form>
                            <div class="w-full h-full">
                                <div class="flex items-center justify-between px-3 pt-2 h-full">
                                    <div
                                        class="flex flex-wrap items-center divide-gray-200 sm:divide-x sm:rtl:divide-x-reverse dark:divide-gray-600">
                                    </div>

                                </div>
                                <div class="px-4 pt-2 rounded-b-lg h-full">
                                    <textarea id="editor" rows="8" v-model="reviewText"
                                        class="block w-full text-sm bg-transparent border-0 outline-none text-zinc-50 resize-none"
                                        placeholder="Escreva sua review..." required></textarea>
                                </div>
                            </div>

                        </form>

                        <div class="w-full pb-2 flex justify-start px-4">
                            <span class="text-zinc-100 text-[10px]">{{ reviewText.length
                            }}/5000</span>
                        </div>
                    </div>
                </div>

                <div class="w-full mt-2 flex px-4" v-if="status != 4"
                    :class="avaliacaoUsuario ? 'justify-between' : 'justify-end'">
                    <button
                        class="px-8 xl:py-4 py-2 border-zinc-400 border text-zinc-50 rounded-full text-xs cursor-pointer"
                        v-if="avaliacaoUsuario" @click="excluiReviewBox">Excluir</button>
                    <button class="px-8 xl:py-4 py-2 text-zinc-900 bg-zinc-50 rounded-full text-xs cursor-pointer"
                        @click="salvaRegistro">{{ avaliacaoUsuario ? 'Editar' : 'Enviar' }}</button>
                </div>

                <div class="w-full mt-10 grid grid-cols-4 px-4 gap-4" v-if="status == 4">
                    <div>
                        <img :src="capa" class="w-[150px] h-auto rounded-md shadow-md">
                    </div>
                    <div class="col-span-3 flex flex-col justify-center text-start">
                        <h1 class="xl:text-2xl text-sm line-clamp-2">{{ dados.name }}</h1>
                        <span v-if="!estaNaWishlist && !avaliacaoUsuario"
                            class="text-zinc-300 xl:text-md text-xs">Adicione {{ dados.name }}
                            à sua
                            Wishlist</span>
                        <span v-else class="text-zinc-300 xl:text-md text-xs">{{ dados.name }} já
                            está na sua Wishlist ou já foi avaliado</span>
                        <div class="w-[90%] flex mt-4">
                            <button
                                class="grow-1 rounded-full text-zinc-900 bg-zinc-50 px-4 xl:py-2 py-1 xl:text-sm text-xs cursor-pointer hover:-translate-y-[2px] hover:shadow-lg transition-all"
                                @click="salvaWishlist" v-if="!estaNaWishlist && !avaliacaoUsuario">Adicionar</button>
                            <router-link class="p-0 grow-1"
                                :to="{ path: `/account/profile/${usuario.usuario}`, query: { filtro: 5 } }">
                                <button
                                    class="w-full rounded-full text-zinc-50 border border-zinc-50 px-4 xl:py-2 py-1 xl:text-sm text-xs ml-2 cursor-pointer hover:-translate-y-[2px] hover:shadow-lg transition-all">Ver
                                    lista</button>
                            </router-link>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>
</template>

<script>
import { api } from '@/services/api';

export default {
    name: "AvaliacaoBox",
    props: ["dados", "imagem", "statusDefault", "capa", "usuario", "avaliacaoUsuario"],
    emits: ['fecha-avaliacaoBox'],
    data() {
        return {
            status: this.statusDefault == 5 ? 4 : this.statusDefault || 1,
            nota: this.avaliacaoUsuario?.nota || 0,
            imagens: [
                '1.png',
                '3.png',
                '4.png',
                '6.png',
                '7.png',
            ],
            opacidades: [1, 0, 0, 0, 0],
            reviewText: this.avaliacaoUsuario?.texto || '',
            estaNaWishlist: false,
            exibeExcluiReviewBox: false,
        }
    },
    async mounted() {
        this.updateOffset()
        this.estaNaWishlist = await this.verificaWishlist()
    },
    methods: {
        updateOffset() {
            const h1 = this.$refs.notaRef;
            if (h1) {
                this.offset = h1.offsetWidth + 10; // 10px de espaçamento
            }
        },
        fechaBox() {
            this.$emit("fecha-avaliacaoBox")
        },
        formataDataUnix(dataUnix) {
            const timestamp = dataUnix;
            const data = new Date(timestamp * 1000);
            const options = { year: 'numeric' };
            return data.toLocaleString('pt-BR', options)
        },
        atualizarImagem() {
            const total = this.imagens.length - 1;
            const progresso = (this.nota / 10) * total;
            const indexBase = Math.floor(progresso);
            const transicao = progresso - indexBase;

            const limiar = 0.8; // Quando começar o fade (80%)

            this.opacidades = this.imagens.map((_, i) => {
                if (i === indexBase) {
                    if (transicao < limiar) {
                        return 1; // 100% visível até o limiar
                    } else {
                        // Depois do limiar, começa a desaparecer
                        return 1 - ((transicao - limiar) / (1 - limiar));
                    }
                }

                if (i === indexBase + 1) {
                    if (transicao < limiar) {
                        return 0; // Próxima imagem ainda invisível
                    } else {
                        // Depois do limiar, começa a aparecer
                        return (transicao - limiar) / (1 - limiar);
                    }
                }

                return 0; // Todas as outras imagens ficam invisíveis
            });
        },
        async salvaRegistro() {
            if (!this.avaliacaoUsuario) {
                try {
                    const response = await api.post("/review/new", {
                        texto: this.reviewText,
                        nota: this.nota,
                        status: this.status,
                        idJogo: this.dados.id,
                        idUsuario: this.usuario.id
                    })

                    this.fechaBox()
                } catch (error) {
                    console.log("Erro ao salvar registro: " + error)
                }
            } else {
                try {
                    await api.post("/review/update", {
                        idUsuario: this.avaliacaoUsuario.idUsuario,
                        idJogo: this.avaliacaoUsuario.idJogo,
                        texto: this.reviewText,
                        nota: this.nota,
                        status: this.status,
                    })

                    this.fechaBox()
                } catch (error) {
                    console.log("Erro ao atualizar registro: " + error)
                }
            }
        },

        async salvaWishlist() {
            try {
                await api.post("/wishlist/adiciona", {
                    idUsuario: this.usuario.id,
                    idJogo: this.dados.id
                })

            } catch (error) {
                console.log("Erro ao adicionar jogo à wishlist: ", error)
            }
            this.fechaBox()
        },

        async verificaWishlist() {
            try {
                const response = await api.get(`/wishlist/get?idUsuario=${this.usuario.id}&idJogo=${this.dados.id}`)
                return response.data
            } catch (error) {
                console.log("Erro ao verificar estado do jogo na wishlist: ", error)
                return false
            }
        },

        excluiReviewBox() {
            if (this.usuario.id != this.avaliacaoUsuario?.idUsuario) return
            setTimeout(() => {
                this.exibeExcluiReviewBox = true
            }, 100)
        },
        async excluirReview() {
            try {
                await api.get(`/review/delete/${this.avaliacaoUsuario?.idReview}`)
            } catch (error) {
                console.log("Erro ao excluir review: ", error)
            } finally {
                this.exibeExcluiReviewBox = false
                this.fechaBox()
            }
        },
    },
    computed: {
        posicaoMedia() {
            if (this.dados.total_rating > 95) return 98
            else if (this.dados.total_rating < 10) return 10
            return this.dados.total_rating.toFixed(1)
        },
        posicaoNota() {
            if (this.nota > 9) return 95
            else if (this.nota < 1) return 10
            return this.nota * 10
        }
    },
}
</script>

<style scoped>
.filtro-branco {
    filter: brightness(0) saturate(100%) invert(100%) sepia(0%) saturate(0%) hue-rotate(168deg) brightness(103%) contrast(103%);
}

.filtro-cinza-verde {
    filter: grayscale(100%) brightness(130%);
}

.filtro-cinza {
    filter: brightness(0) saturate(100%) invert(69%) sepia(3%) saturate(706%) hue-rotate(201deg) brightness(92%) contrast(89%);
}

.filtro-cinza-escuro {
    filter: brightness(0) saturate(100%) invert(22%) sepia(7%) saturate(759%) hue-rotate(202deg) brightness(94%) contrast(86%);
}

.degradeCinza {
    background: #3b3b3b;
    background: linear-gradient(to right, #313131 0%, #9c9c9c 100%);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
}

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

.inputRange {
    appearance: none;
    height: 44px;
    border: 1px solid #71717B;
    border-radius: 9999px;
    background: linear-gradient(90deg, transparent 0%, transparent 0%);
    cursor: pointer;
    padding-left: 3px;
    padding-right: 3px;
}

/* Thumb: for Chrome, Safari, Edge */
.inputRange::-webkit-slider-thumb {
    -webkit-appearance: none;
    appearance: none;
    width: 38px;
    height: 38px;
    border: 3px solid #363636;
    border-radius: 9999px;
    background: #fafafa;
    box-shadow: none;
}

/* Thumb: for Firefox */
.inputRange::-moz-range-thumb {
    width: 38px;
    height: 38px;
    border: 3px solid #262729;
    border-radius: 9999px;
    background: #fafafa;
    box-shadow: none;
}
</style>