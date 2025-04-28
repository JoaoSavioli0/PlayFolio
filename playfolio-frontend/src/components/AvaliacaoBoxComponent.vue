<template>
    <div class="w-full h-screen flex justify-center items-center fixed start-0 top-0 overflow-y-hidden z-[1000]">
        <div class="w-full h-full bg-black/80 z-[100] absolute top-0 start-0" @click="fechaBox"></div>

        <div class="w-[600px] h-max z-[110] rounded-2xl flex flex-col overflow-hidden">
            <div class="w-full flex flex-col relative min-h-[60px] items-start">
                <div class="w-full pt-6 flex justify-between z-50">
                    <h1 class="pl-4 max-w-[85%] line-clamp-2 text-start z-50 text-zinc-50 text-lg">{{ dados.name }}
                    </h1>
                    <button class=""><img src="../assets/Imagens/close.svg"
                            class="size-[28px] mr-4 filtro-branco cursor-pointer" @click="fechaBox"></button>
                </div>

                <span class="z-50 ml-4 text-sm text-zinc-300 pb-6">{{ formataDataUnix(dados.first_release_date)
                }}</span>

                <div class="w-full h-full bg-[#262729]/80 z-40 absolute"></div>
                <img :src="imagem" class="w-full h-full object-cover absolute z-30">
            </div>


            <div class="w-full py-4 h-auto bg-[#262729]">
                <div class="flex px-4 gap-x-2 h-[30px] items-center mt-4">

                    <button
                        class="flex items-center rounded-full border py-[2px] px-2 bg-zinc-700 transition cursor-pointer"
                        @click="() => { status != 1 ? status = 1 : status = 0 }"
                        :class="status == 1 ? 'border-[#c31ef1] text-[#c31ef1] shadow-[0px_0px_26px_-4px_#c31ef1] -translate-y-[2px]' : 'border-zinc-600 text-zinc-400'">

                        <img src="../assets/Imagens/joystick-2.svg" class="w-[20px] h-auto"
                            :class="{ 'filtro-cinza': status != 1 }">
                        <span class="ml-[4px] max-[1800px]:text-sm">Jogando</span>
                    </button>

                    <button
                        class="flex h-min items-center rounded-full border py-[2px] px-2 bg-zinc-700 transition cursor-pointer"
                        @click="() => { status != 2 ? status = 2 : status = 0 }"
                        :class="status == 2 ? 'border-green-600 text-green-600 shadow-[0px_0px_26px_-4px_#0AA63E] -translate-y-[2px]' : 'border-zinc-600 text-zinc-400'">

                        <img src="../assets/Imagens/flag.svg" class="w-[20px] h-auto"
                            :class="{ 'filtro-cinza-verde': status != 2 }">
                        <span class="ml-[4px] max-[1800px]:text-sm">Zerado</span>
                    </button>

                    <button
                        class="flex items-center rounded-full border py-[2px] px-2 bg-zinc-700 transition cursor-pointer"
                        @click="() => { status != 3 ? status = 3 : status = 0 }"
                        :class="status == 3 ? 'border-[#d22d56] text-[#d22d56] shadow-[0px_0px_26px_-4px_#d22d56] -translate-y-[2px]' : 'border-zinc-600 text-zinc-400'">

                        <img src="../assets/Imagens/skull.svg" class="w-[18px] h-auto "
                            :class="{ 'filtro-cinza': status != 3 }">
                        <span class="ml-[4px] max-[1800px]:text-sm">Dropado</span>
                    </button>

                    <button
                        class="flex items-center rounded-full border py-[2px] px-2 bg-zinc-700 transition cursor-pointer"
                        @click="() => { status != 4 ? status = 4 : status = 0 }"
                        :class="status == 4 ? 'border-[#eebb17] text-[#eebb17] shadow-[0px_0px_26px_-4px_#eebb17] -translate-y-[2px]' : 'border-zinc-600 text-zinc-400'">

                        <img src="../assets/Imagens/shine.svg" class="w-[18px] h-auto pr-[2px]"
                            :class="{ 'filtro-cinza': status != 4 }">
                        <span class="ml-[4px] max-[1800px]:text-sm">Quero jogar</span>
                    </button>

                </div>

                <div class="w-full mt-8 text-start px-4" v-if="status != 4">
                    <h1 class="text-3xl max-[1800px]:text-xl text-zinc-50 pr-3">NOTA</h1>

                </div>

                <div class="w-full px-4 mt-4 grid grid-cols-4 gap-4" v-if="status != 4">
                    <div class="flex justify-center grow-1">
                        <img :src="capa" class="w-[110px] h-auto rounded-md shadow-md">
                    </div>

                    <div class="col-span-3 relative flex h-[150px] ">
                        <div class="h-[110px] w-[125px]"></div>
                        <img v-for="(img, index) in imagens" :key="index" class="top-1/2 -translate-y-1/2"
                            :src="require(`../assets/Imagens/expressoes/${img}`)" :style="{
                                opacity: opacidades[index],
                                position: 'absolute',
                                left: 0,
                                transition: 'opacity 0.4s ease',
                                width: '100px',
                                height: '100px',
                                filter: 'drop-shadow(0 0 0.3rem black)'
                            }" />

                        <div class="grow-1 py-2">
                            <div class="w-full h-[36px]"></div>

                            <div class="pl-[125px] w-[90%] absolute start-0 top-[10px]">
                                <div class="w-full relative px-2 text-2xl">
                                    <h1 class="block transition-opacity duration-500 absolute degradeVermelho"
                                        :style="{ left: posicaoNota - 5 + '%' }" :class="{
                                            'opacity-100 ': nota <= 3,
                                            'opacity-0': nota > 3
                                        }">{{ nota }}</h1>

                                    <h1 class="block transition-opacity duration-500 absolute degradeAmarelo"
                                        :style="{ left: posicaoNota - 5 + '%' }" :class="{
                                            'opacity-100 ': nota > 3 && nota <= 5,
                                            'opacity-0': nota <= 3 || nota > 5
                                        }">{{ nota }}</h1>

                                    <h1 class="block transition-opacity duration-500 absolute degradeAzul"
                                        :style="{ left: posicaoNota - 5 + '%' }" :class="{
                                            'opacity-100 ': nota > 5 && nota <= 8,
                                            'opacity-0': nota <= 5 || nota > 8
                                        }">{{ nota }}</h1>

                                    <h1 class="block transition-opacity duration-500 absolute degradeRoxo"
                                        :style="{ left: posicaoNota - 5 + '%' }" :class="{
                                            'opacity-100 ': nota > 8,
                                            'opacity-0': nota <= 8
                                        }">{{ nota }}</h1>
                                </div>
                            </div>

                            <div class="relative w-full mt-[4px]">
                                <input type="range" min="0" max="10" step="0.1" v-model="nota" class="inputRange w-full"
                                    @input="atualizarImagem" />

                                <div class="w-full absolute top-0 start-0">
                                    <div class="w-full relative">
                                        <span
                                            class="absolute top-[10px] start-[16px] pointer-events-none text-zinc-500">0</span>
                                        <span
                                            class="absolute top-[10px] end-[12px] pointer-events-none text-zinc-500">10</span>
                                        <div class="absolute top-[14px] start-[60px] flex gap-x-2">
                                            <span class="text-zinc-500 text-xs">Deslize</span>
                                            <span class="opacity-75 text-zinc-500 text-xs">></span>
                                            <span class="opacity-50 text-zinc-500 text-xs">></span>
                                            <span class="opacity-25 text-zinc-500 text-xs">></span>
                                        </div>
                                    </div>
                                </div>

                                <div class="w-full absolute bottom-0 start-0 h-[5px]">
                                    <div class="w-full relative">
                                        <span
                                            class="absolute whitespace-nowrap text-zinc-400 flex flex-col items-center"
                                            :style="{ 'left': posicaoMedia * 0.85 + '%' }">
                                            <img src="../assets/Imagens/arrow-2.svg"
                                                class="-rotate-270 filtro-cinza w-[25px] h-auto mt-2">
                                            <h2 class="block text-[12px] -mt-[4px]">MÉDIA</h2>
                                            <h2 class="text-sm text-[14px]">{{ (dados.total_rating / 10).toFixed(1) }}
                                            </h2>
                                        </span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="w-full mt-12 text-start px-4" v-if="status != 4">
                    <h1 class="text-3xl text-zinc-50 bg-[#262729] max-[1800px]:text-xl">COMENTÁRIO</h1>
                </div>

                <div class="w-full mt-4 px-4" v-if="status != 4">
                    <textarea
                        class="w-full p-2 rounded-xl bg-zinc-900 text-zinc-50 text-sm border border-zinc-700 outline-none min-h-[100px]"
                        maxlength="1000"></textarea>
                </div>

                <div class="w-full mt-2 flex justify-end px-4" v-if="status != 4">
                    <button
                        class="px-4 py-2 text-zinc-50 border border-zinc-50 rounded-full text-xs cursor-pointer">ENVIAR</button>
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
export default {
    name: "AvaliacaoBox",
    props: ["dados", "imagem", "statusDefault", "capa"],
    mounted() {
        console.log("dados: ", this.dados)
    },
    data() {
        return {
            status: this.statusDefault,
            nota: 0,
            imagens: [
                '1.png',
                '3.png',
                '4.png',
                '6.png',
                '7.png',
            ],
            opacidades: [1, 0, 0, 0, 0],
        }
    },
    methods: {
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
        }
    },
    computed: {
        posicaoMedia() {
            if (this.dados.total_rating > 90) return 88
            else if (this.dados.total_rating < 10) return 10
            return this.dados.total_rating.toFixed(1)
        },
        posicaoNota() {
            if (this.nota > 9) return 95
            else if (this.nota < 1) return 10
            return this.nota * 10
        }
    }
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
    border: 1px solid #363636;
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
    border: 2px solid #363636;
    border-radius: 9999px;
    background: #fafafa;
    box-shadow: none;
}

/* Thumb: for Firefox */
.inputRange::-moz-range-thumb {
    width: 38px;
    height: 38px;
    border: 2px solid #363636;
    border-radius: 9999px;
    background: #fafafa;
    box-shadow: none;
}
</style>