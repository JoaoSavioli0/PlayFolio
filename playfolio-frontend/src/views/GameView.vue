<template>
    <div class="w-full h-full pb-6 pt-4 px-24 flex justify-center gap-x-4">

        <div class="w-[300px] h-screen relative pt-4">
            <div class="fixed w-[280px]">
                <div class="flex w-full justify-between items-center">
                    <router-link to="/" class="p-0 cursor-pointer">
                        <button
                            class="w-[90px] py-2 bg-zinc-50 px-2 rounded-full flex justify-around items-center cursor-pointer">
                            <img src="../assets/Imagens/arrow.svg" class="w-[21px] h-auto">
                            <span class="text-sm text-[#1b1d1f]">Voltar</span>
                        </button>
                    </router-link>
                    <h1 class="text-xl inline">PlayFolio</h1>
                </div>

                <div class="w-full mt-8 flex flex-col">
                    <div class="flex w-full h-max">
                        <div class="size-[45px] rounded-full bg-zinc-500 border-[1px] border-zinc-300"></div>
                        <div class="flex flex-col ml-2 text-start justify-center">
                            <span class="text-zinc-50 text-sm">Nome</span>
                            <span class="text-zinc-400 text-[10px]">@Apelido</span>
                        </div>
                    </div>
                    <div class="w-full mt-8">
                        <button
                            class="w-full rounded-full bg-zinc-50 py-[10px] text-[#1b1d1f] flex justify-center cursor-pointer">
                            <img src="../assets/Imagens/lupa.svg" class="w-[15px] h-auto">
                            <span class="ml-2">Buscar jogos</span>
                        </button>
                    </div>
                </div>

                <div class="w-full flex flex-col mt-4">
                    <ul
                        class="w-full *:rounded-full *:hover:bg-zinc-800 *:w-full *:py-[10px] *:mt-2 *:text-zinc-400 *:hover:text-zinc-50 *:transition-all *:cursor-pointer *:text-start *:px-8 *:flex ">

                        <li @click="itemAtivo = item.id" class="group" :class="{ 'bg-zinc-800': itemAtivo == item.id }"
                            v-for="item in itemsNav">
                            <img :src="item.img" class="w-[23px] h-auto transition-all duration-200"
                                :class="[itemAtivo == item.id ? 'filtro-branco' : 'filtro-cinza']">
                            <span class="ml-4 transition" :class="{ 'text-zinc-50': itemAtivo == item.id }">{{ item.name
                            }}</span>
                        </li>

                    </ul>
                </div>
            </div>
        </div>

        <GameComponent :id="id" />

    </div>
</template>

<script>
import GameComponent from '@/components/GameComponent.vue'
import axios from 'axios'

export default {
    name: "BaseView",
    props: ['id'],
    components: {
        GameComponent
    },
    data() {
        return {
            itemsNav: [
                { id: 1, name: "Início", img: require("../assets/Imagens/home.svg") },
                { id: 2, name: "Meus jogos", img: require("../assets/Imagens/joystick.svg") },
                { id: 3, name: "Comunidade", img: require("../assets/Imagens/community.svg") },
                { id: 4, name: "Configurações", img: require("../assets/Imagens/settings.svg") },
                { id: 5, name: "Sair", img: require("../assets/Imagens/exit.svg") }
            ],
            itemAtivo: 0
        }
    }
}
</script>

<style>
.gradiente-cinza-transparente {
    background: rgb(237, 237, 237);
    background: linear-gradient(180deg, rgba(237, 237, 237, 0) 0%, rgba(27, 29, 31, 1) 85%);
}

.gradiente-cinza-claro {
    background: rgb(26, 27, 30);
    background: linear-gradient(0deg, rgba(26, 27, 30, 1) 1%, rgba(43, 44, 48, 1) 100%);
}

.filtro-branco {
    filter: brightness(0) invert(1);
}


.filtro-cinza {
    filter: brightness(0) saturate(100%) invert(44%) sepia(8%) saturate(376%) hue-rotate(202deg) brightness(98%) contrast(90%);
}
</style>