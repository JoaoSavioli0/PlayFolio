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
            <div class="w-full py-4 bg-[#262729]">
                <div class="flex px-4 gap-x-2 h-[30px] items-center">

                    <button
                        class="flex items-center rounded-full border py-[2px] px-2 bg-zinc-700 text-[11px] transition cursor-pointer"
                        @click="() => { status != 1 ? status = 1 : status = 0 }"
                        :class="status == 1 ? 'border-[#c31ef1] text-[#c31ef1] shadow-[0px_0px_26px_-4px_#c31ef1] -translate-y-[2px]' : 'border-zinc-600 text-zinc-400'">

                        <img src="../assets/Imagens/joystick-2.svg" class="w-[14px] h-auto"
                            :class="{ 'filtro-cinza': status != 1 }">
                        <span class="ml-[2px]">Jogando</span>
                    </button>

                    <button
                        class="flex h-min items-center rounded-full border py-[2px] px-2 bg-zinc-700 text-[11px] transition cursor-pointer"
                        @click="() => { status != 2 ? status = 2 : status = 0 }"
                        :class="status == 2 ? 'border-green-600 text-green-600 shadow-[0px_0px_26px_-4px_#0AA63E] -translate-y-[2px]' : 'border-zinc-600 text-zinc-400'">

                        <img src="../assets/Imagens/flag.svg" class="w-[14px] h-auto"
                            :class="{ 'filtro-cinza-verde': status != 2 }">
                        <span class="ml-[2px]">Zerado</span>
                    </button>

                    <button
                        class="flex items-center rounded-full border py-[2px] px-2 bg-zinc-700 text-[11px] transition cursor-pointer"
                        @click="() => { status != 3 ? status = 3 : status = 0 }"
                        :class="status == 3 ? 'border-[#d22d56] text-[#d22d56] shadow-[0px_0px_26px_-4px_#d22d56] -translate-y-[2px]' : 'border-zinc-600 text-zinc-400'">

                        <img src="../assets/Imagens/skull.svg" class="w-[12px] h-auto "
                            :class="{ 'filtro-cinza': status != 3 }">
                        <span class="ml-[2px]">Dropado</span>
                    </button>

                    <button
                        class="flex items-center rounded-full border py-[2px] px-2 bg-zinc-700 text-[11px] transition cursor-pointer"
                        @click="() => { status != 4 ? status = 4 : status = 0 }"
                        :class="status == 4 ? 'border-[#eebb17] text-[#eebb17] shadow-[0px_0px_26px_-4px_#eebb17] -translate-y-[2px]' : 'border-zinc-600 text-zinc-400'">

                        <img src="../assets/Imagens/shine.svg" class="w-[12px] h-auto pr-[2px]"
                            :class="{ 'filtro-cinza': status != 4 }">
                        <span class="ml-[2px]">Quero jogar</span>
                    </button>

                </div>



            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: "AvaliacaoBox",
    props: ["dados", "imagem"],
    mounted() {
        console.log("dados: ", this.dados)
    },
    data() {
        return {
            status: 0,
            nota: 5
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
    },
    computed: {

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

/*generated with Input range slider CSS style generator (version 20211225)
https://toughengineer.github.io/demo/slider-styler*/
input[type=range].styled-slider {
    height: 2.2em;
    -webkit-appearance: none;
}

/*progress support*/
input[type=range].styled-slider.slider-progress {
    --range: calc(var(--max) - var(--min));
    --ratio: calc((var(--value) - var(--min)) / var(--range));
    --sx: calc(0.5 * 10px + var(--ratio) * (100% - 10px));
}

input[type=range].styled-slider:focus {
    outline: none;
}

/*webkit*/
input[type=range].styled-slider::-webkit-slider-thumb {
    -webkit-appearance: none;
    width: 10px;
    height: 34px;
    border-radius: 1em;
    background: #F9F9F9;
    border: none;
    box-shadow: 0 0 2px black;
    margin-top: calc(max((1.1em - 1px - 1px) * 0.5, 0px) - 34px * 0.5);
}

input[type=range].styled-slider::-webkit-slider-runnable-track {
    height: 1.1em;
    border: 1px solid #b2b2b2;
    border-radius: 0.5em;
    background: #EFEFEF00;
    box-shadow: none;
}

input[type=range].styled-slider.slider-progress::-webkit-slider-runnable-track {
    background: linear-gradient(to right, #F84100, #3FADD9) 0/var(--sx) 100% no-repeat, #EFEFEF00;
}

/*mozilla*/
input[type=range].styled-slider::-moz-range-thumb {
    width: 10px;
    height: 34px;
    border-radius: 1em;
    background: #F9F9F9;
    border: none;
    box-shadow: 0 0 2px black;
}

input[type=range].styled-slider::-moz-range-track {
    height: max(calc(1.1em - 1px - 1px), 0px);
    border: 1px solid #b2b2b2;
    border-radius: 0.5em;
    background: #EFEFEF00;
    box-shadow: none;
}

input[type=range].styled-slider.slider-progress::-moz-range-track {
    background: linear-gradient(to right, #F84100, #3FADD9) 0/var(--sx) 100% no-repeat, #EFEFEF00;
}

/*ms*/
input[type=range].styled-slider::-ms-fill-upper {
    background: transparent;
    border-color: transparent;
}

input[type=range].styled-slider::-ms-fill-lower {
    background: transparent;
    border-color: transparent;
}

input[type=range].styled-slider::-ms-thumb {
    width: 10px;
    height: 34px;
    border-radius: 1em;
    background: #F9F9F9;
    border: none;
    box-shadow: 0 0 2px black;
    margin-top: 0;
    box-sizing: border-box;
}

input[type=range].styled-slider::-ms-track {
    height: 1.1em;
    border-radius: 0.5em;
    background: #EFEFEF00;
    border: 1px solid #b2b2b2;
    box-shadow: none;
    box-sizing: border-box;
}

input[type=range].styled-slider.slider-progress::-ms-fill-lower {
    height: max(calc(1.1em - 1px - 1px), 0px);
    border-radius: 0.5em 0 0 0.5em;
    margin: -1px 0 -1px -1px;
    background: linear-gradient(to right, #F84100, #3FADD9);
    border: 1px solid #b2b2b2;
    border-right-width: 0;
}
</style>