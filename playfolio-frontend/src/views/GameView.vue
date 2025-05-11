<template>
    <PesquisaJogoBoxComponent v-if="pesquisaJogoBoxOpen" @fechar-box="pesquisaJogoBoxOpen = false" />
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
                    <div class="flex items-center">
                        <img src="../assets/Imagens/logo.png" class="size-[16px] shrink-0 filtro-branco">

                        <h1 class="text-xl inline ml-[3px]">PlayFolio</h1>
                    </div>
                </div>

                <div class="w-full mt-8 flex flex-col" v-if="usuario">
                    <div class="flex w-full h-max">
                        <div class="size-[45px] rounded-full bg-zinc-500 border-[1px] border-zinc-300"></div>
                        <div class="flex flex-col ml-2 text-start justify-center">
                            <span class="text-zinc-50 text-sm line-clamp-1">{{ usuario.nome }}</span>
                            <span class="text-zinc-400 text-[10px]">@{{ usuario.usuario }}</span>
                        </div>
                    </div>
                </div>

                <div class="w-full mt-8 flex flex-col rounded-xl border-[1px] border-zinc-500 overflow-hidden relative shadow-lg"
                    v-if="!usuario">
                    <div class="w-full h-full relative z-50 p-4">
                        <div class="w-full">
                            <h1 class="text-sm text-start">FAÇA LOGIN E SALVE SEUS JOGOS</h1>
                        </div>
                        <div class="flex mt-4">
                            <router-link to="/account/login" class="p-0">
                                <button class="rounded-full bg-zinc-50 px-4 py-[2px] text-center cursor-pointer">
                                    <span class="text-[#1b1d1f] text-sm">Login</span>
                                </button>
                            </router-link>
                            <router-link to="/account/registro" class="p-0">
                                <button
                                    class="rounded-full border-[1px] border-zinc-50 px-4 py-[2px] text-center ml-2 cursor-pointer">
                                    <span class="text-zinc-50 text-sm">Cadastro</span>
                                </button>
                            </router-link>
                        </div>
                    </div>

                    <div class="absolute w-full h-full gradiente-cinza-escuro z-30"></div>

                    <img src="../assets/Imagens/ori_background.jpg" class="absolute w-full h-full object-cover z-20">
                </div>

                <div class="w-full mt-8">
                    <button @click="pesquisaJogoBoxOpen = true"
                        class="w-full rounded-full bg-zinc-50 py-[10px] text-[#1b1d1f] flex justify-center cursor-pointer">
                        <img src="../assets/Imagens/lupa.svg" class="w-[15px] h-auto">
                        <span class="ml-2">Buscar jogos</span>
                    </button>
                </div>

                <div class="w-full flex flex-col mt-4">
                    <ul
                        class="w-full *:rounded-full *:hover:bg-zinc-800 *:w-full *:py-[10px] *:mt-2 *:text-zinc-400 *:hover:text-zinc-50 *:transition-all *:cursor-pointer *:text-start *:px-8 *:flex ">

                        <li @click="itemAtivo = item.id" class="group" :class="{ 'bg-zinc-800': itemAtivo == item.id }"
                            v-for="item in itemsNavFiltrados">
                            <img :src="item.img" class="w-[23px] h-auto transition-all duration-200"
                                :class="[itemAtivo == item.id ? 'filtro-branco' : 'filtro-cinza']">
                            <span class="ml-4 transition" :class="{ 'text-zinc-50': itemAtivo == item.id }">{{ item.name
                                }}</span>
                        </li>

                        <li @click="deslogaUsuario" class="group" v-if="this.usuario">
                            <img src="../assets/Imagens/exit.svg"
                                class="w-[23px] h-auto filtro-cinza transition-all duration-200">
                            <span class="ml-4 transition">Sair</span>
                        </li>

                    </ul>
                </div>
            </div>
        </div>

        <GameComponent v-if="carregouUsuario" :id="id" :usuario="usuario ? usuario : null" />

    </div>
</template>

<script>
import GameComponent from '@/components/GameComponent.vue'
import PesquisaJogoBoxComponent from '@/components/PesquisaJogoBoxComponent.vue'
import { useUserStore } from '@/stores/UserStore'
import axios from 'axios'

export default {
    name: "BaseView",
    props: ['id'],
    components: {
        GameComponent,
        PesquisaJogoBoxComponent
    },
    data() {
        return {
            itemsNav: [
                { id: 1, name: "Início", img: require("../assets/Imagens/home.svg"), needLogin: false },
                { id: 2, name: "Meus jogos", img: require("../assets/Imagens/joystick.svg"), needLogin: true },
                { id: 3, name: "Comunidade", img: require("../assets/Imagens/community.svg"), needLogin: false },
                { id: 4, name: "Configurações", img: require("../assets/Imagens/settings.svg"), needLogin: true },
            ],
            itemAtivo: 0,
            usuario: null,
            pesquisaJogoBoxOpen: false,
            carregouUsuario: false
        }
    },
    async mounted() {

        if (useUserStore().usuario != null) {
            this.usuario = useUserStore().usuario
        } else {
            await useUserStore().reconectaSessao()
            if (useUserStore().usuario != null) {
                this.usuario = useUserStore().usuario
            }
        }
        this.carregouUsuario = true
        setTimeout(() => {
            console.log("Usuario View> ", this.usuario)
        }, 2000)

    },
    methods: {
        deslogaUsuario() {
            useUserStore().logout()
            this.$router.push("/")
        }
    },
    computed: {
        itemsNavFiltrados() {
            if (!this.usuario) {
                return this.itemsNav.filter(i => !i.needLogin)
            }
            return this.itemsNav
        }
    },
}
</script>

<style scoped>
.gradiente-cinza-transparente {
    background: rgb(237, 237, 237);
    background: linear-gradient(180deg, rgba(237, 237, 237, 0) 0%, rgba(27, 29, 31, 1) 85%);
}

.gradiente-cinza-escuro {
    background: #191a1c;
    background: linear-gradient(90deg, rgba(25, 26, 28, 1) 21%, rgba(25, 26, 28, 0.88) 70%, rgba(25, 26, 28, 0.55) 85%, rgba(25, 26, 28, 0) 100%);
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