<template>
    <div class="w-full h-screen flex justify-center items-center fixed start-0 top-0 overflow-y-hidden z-[1000]">
        <div class="w-full h-full bg-black/80 z-[100] absolute top-0 start-0" @click="fechaBox"></div>

        <div class="w-[700px] h-max z-[110] rounded-2xl flex flex-col overflow-hidden">
            <div class="w-full flex flex-col relative min-h-[80px] items-start">
                <div class="w-full py-6 flex justify-between z-50">
                    <div class="px-4 grid grid-cols-4 gap-4 z-[60]">
                        <div class="flex justify-center">
                            <img :src="capa" class="w-[110px] h-auto rounded-md shadow-md">
                        </div>

                        <div class="col-span-3 relative flex flex-col justify-center h-[150px] ">
                            <div class="flex w-full text-start items-center relative h-[40px]">
                                <div class="size-[42px] overflow-hidden">
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
                                    class="relative overflow-hidden h-[80px] w-[120px] z-[60] text-4xl top-[20px] start-[10px]">

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
                            <h1 class="w-full text-start text-xl mt-2 max-w-[80%] line-clamp-2">{{ dados.name }}</h1>
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
                <div class="flex px-4 gap-x-2 h-[30px] items-center justify-between mt-4">

                    <button
                        class="flex grow-1 justify-center items-center rounded-full border py-[4px] px-4 bg-zinc-800 transition cursor-pointer"
                        @click="() => { status != 1 ? status = 1 : status = 0 }"
                        :class="status == 1 ? 'border-[#c31ef1] text-[#c31ef1] shadow-[0px_0px_26px_-4px_#c31ef1] -translate-y-[2px]' : 'border-zinc-500 text-zinc-400'">

                        <img src="../assets/Imagens/joystick-2.svg" class="w-[20px] h-auto"
                            :class="{ 'filtro-cinza': status != 1 }">
                        <span class="ml-[6px] max-[1800px]:text-sm">Jogando</span>
                    </button>

                    <button
                        class="flex grow-1 justify-center h-min items-center rounded-full border py-[4px] px-4 bg-zinc-800 transition cursor-pointer"
                        @click="() => { status != 2 ? status = 2 : status = 0 }"
                        :class="status == 2 ? 'border-green-600 text-green-600 shadow-[0px_0px_26px_-4px_#0AA63E] -translate-y-[2px]' : 'border-zinc-500 text-zinc-400'">

                        <img src="../assets/Imagens/flag.svg" class="w-[17px] h-auto"
                            :class="{ 'filtro-cinza-verde': status != 2 }">
                        <span class="ml-[4px] max-[1800px]:text-sm">Zerado</span>
                    </button>

                    <button
                        class="flex grow-1 justify-center items-center rounded-full border py-[4px] px-4 bg-zinc-800 transition cursor-pointer"
                        @click="() => { status != 3 ? status = 3 : status = 0 }"
                        :class="status == 3 ? 'border-[#d22d56] text-[#d22d56] shadow-[0px_0px_26px_-4px_#d22d56] -translate-y-[2px]' : 'border-zinc-500 text-zinc-400'">

                        <img src="../assets/Imagens/skull.svg" class="w-[18px] h-auto "
                            :class="{ 'filtro-cinza': status != 3 }">
                        <span class="ml-[6px] max-[1800px]:text-sm">Dropado</span>
                    </button>

                    <button
                        class="flex grow-1 justify-center items-center rounded-full border py-[4px] px-4 bg-zinc-800 transition cursor-pointer"
                        @click="() => { status != 4 ? status = 4 : status = 0 }"
                        :class="status == 4 ? 'border-[#eebb17] text-[#eebb17] shadow-[0px_0px_26px_-4px_#eebb17] -translate-y-[2px]' : 'border-zinc-500 text-zinc-400'">

                        <img src="../assets/Imagens/shine.svg" class="w-[18px] h-auto pr-[2px]"
                            :class="{ 'filtro-cinza': status != 4 }">
                        <span class="ml-[6px] max-[1800px]:text-sm">Quero jogar</span>
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
                        <textarea
                            class="resize-none w-full p-2 rounded-xl text-zinc-50 text-sm border-none outline-none h-[200px]"
                            maxlength="5000" v-model="reviewText"
                            placeholder="Escreva sua review em até 5000 caracteres"></textarea>
                        <div class="w-full py-2 flex justify-start px-2">
                            <span class="text-zinc-400 text-[12px]">{{ reviewText.length
                            }}/5000</span>
                        </div>
                    </div>
                </div>

                <div class="w-full mt-2 flex justify-end px-4" v-if="status != 4">
                    <button class="px-4 py-2 text-zinc-50 border border-zinc-50 rounded-full text-xs cursor-pointer"
                        @click="salvaRegistro">ENVIAR</button>
                </div>

                <div class="w-full mt-10 grid grid-cols-4 px-4 gap-4" v-if="status == 4">
                    <div>
                        <img :src="capa" class="w-[150px] h-auto rounded-md shadow-md">
                    </div>
                    <div class="col-span-3 flex flex-col justify-center text-start">
                        <h1 class="text-2xl max-[1800px]:text-xl">{{ dados.name }}</h1>
                        <span class="text-zinc-300 max-[1800px]:text-sm">Adicione {{ dados.name }} à sua lista de
                            espera</span>
                        <button
                            class="rounded-full text-zinc-900 bg-zinc-50 px-4 py-2 text-sm w-[90%] mt-4 cursor-pointer hover:-translate-y-[2px] hover:shadow-lg transition-all">Adicionar</button>
                    </div>
                </div>

            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    name: "AvaliacaoBox",
    props: ["dados", "imagem", "statusDefault", "capa", "usuario", "avaliacaoUsuario"],

    mounted() {
        console.log("avaliacaoUsuario: ", this.avaliacaoUsuario)
        this.updateOffset();
    },
    data() {
        return {
            status: this.statusDefault,
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
        }
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
            try {
                const response = await axios.post("http://localhost:5000/review/new", {
                    texto: this.reviewText,
                    nota: this.nota,
                    status: this.status,
                    idJogo: this.dados.id,
                    idUsuario: this.usuario.id
                })

                this.fechaBox()
                console.log(response.data)
            } catch (error) {
                console.log("Erro ao salvar registro: " + error)
            }
        }
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