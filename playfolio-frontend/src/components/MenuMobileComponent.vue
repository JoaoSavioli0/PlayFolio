<template>
    <PesquisaJogoBoxComponent v-if="pesquisaJogoBoxOpen" @fechar-box="pesquisaJogoBoxOpen = false" />
    <div class="w-full relative min-xl:hidden z-[1000]">
        <div class="w-full px-2 flex items-center py-4 justify-between ">
            <router-link to="/" class="flex items-center">
                <img src="../assets/Imagens/logo.png" class="size-[20px] filtro-zinc-50">
                <h1 class="text-zinc-50 ml-2 text-lg">PlayFolio</h1>
            </router-link>

            <button @click="menuMobileOpen = true" ref="menuMobileButton">
                <img src="../assets/Imagens/menu.svg" class="size-[30px] filtro-zinc-50">
            </button>
        </div>

        <div class="w-full h-screen bg-black/80 flex justify-center items-center fixed start-0 top-0 z-[200]"
            v-if="menuMobileOpen">

            <div class="w-[90%] bg-zinc-900 rounded-lg flex flex-col relative z-[300]" ref="menuMobile">
                <button class="absolute top-[20px] end-[20px]" @click="menuMobileOpen = false">
                    <img src="../assets/Imagens/close.svg" class="size-[25px] filtro-zinc-50">
                </button>
                <div class="w-full pt-6 flex flex-col px-6" v-if="usuario">
                    <router-link class="w-full p-0" :to="`/account/profile/${usuario.usuario}`"
                        @click="menuMobileOpen = false">
                        <div class="flex w-full h-max hover:cursor-pointer">
                            <div
                                class="flex items-center justify-center size-[45px] rounded-full bg-gradient-to-br from-amber-500 to-pink-500 ">
                                <div
                                    class="size-[41px] bg-zinc-900 text-black rounded-full flex items-center justify-center overflow-hidden">
                                    <img :src="`data:image/png;base64,${usuario.imagem}`"
                                        class="w-full h-full object-cover" v-if="usuario.imagem">
                                    <h1 class="text-2xl text-zinc-50" v-else>{{ primeiraLetraUsuario }}</h1>
                                </div>
                            </div>
                            <div class="flex flex-col ml-2 text-start justify-center">
                                <span class="text-zinc-50 text-sm line-clamp-1">{{ usuario.nome }}</span>
                                <span class="text-zinc-400 text-[10px]">@{{ usuario.usuario }}</span>
                            </div>
                        </div>
                    </router-link>
                </div>

                <div class="w-full flex flex-col rounded-xl overflow-hidden relative shadow-lg" v-else>
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

                <div class="w-full px-6 mt-4">
                    <button @click="() => { pesquisaJogoBoxOpen = true, menuMobileOpen = false }"
                        class="w-max flex items-center justify-center bg-zinc-50 rounded-full py-1 px-4">
                        <span class="text-sm text-zinc-900">Pesquisar</span>
                        <img src="../assets/Imagens/lupa.svg" class="size-[15px] ml-2 rotate-90">
                    </button>
                </div>

                <div class="w-full flex flex-col py-4">
                    <ul class="w-full">

                        <router-link :to="item.rota" v-for="item in itemsNavFiltrados" class="w-full ">
                            <li @click="itemAtivo = item.id"
                                class="group flex hover:bg-zinc-800 py-[10px] mt-2 text-zinc-400 hover:text-zinc-50 transition-all cursor-pointer text-start px-8 w-full"
                                :class="{ 'bg-zinc-800': itemAtivo == item.id }">
                                <img :src="item.img" class="w-[23px] h-auto transition-all duration-200"
                                    :class="[itemAtivo == item.id ? 'filtro-zinc-50' : 'filtro-cinza']">
                                <span class="ml-4 transition" :class="{ 'text-zinc-50': itemAtivo == item.id }">{{
                                    item.name
                                }}</span>
                            </li>
                        </router-link>

                        <li @click="deslogaUsuario"
                            class="group flex hover:bg-zinc-800 py-[10px] mt-2 text-zinc-400 hover:text-zinc-50 transition-all cursor-pointer text-start px-8 w-full"
                            v-if="this.usuario">
                            <img src="../assets/Imagens/exit.svg"
                                class="w-[23px] h-auto filtro-cinza transition-all duration-200">
                            <span class="ml-4 transition">Sair</span>
                        </li>

                    </ul>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { useUserStore } from '@/stores/UserStore'
import PesquisaJogoBoxComponent from './PesquisaJogoBoxComponent.vue'

export default {
    name: "menumobile",
    props: ["selected"],
    data() {
        return {
            itemsNav: [],
            itemAtivo: this.selected,
            usuario: null,
            pesquisaJogoBoxOpen: false,
            carregouUsuario: false,
            userStore: useUserStore(),
            menuMobileOpen: false
        }
    },
    components: {
        PesquisaJogoBoxComponent
    },
    async mounted() {
        document.addEventListener("click", this.verificaClick)
        if (this.userStore.usuario != null) {
            this.usuario = this.userStore.usuario
            console.log("usuario> ", this.usuario)
        } else {
            await this.userStore.reconectaSessao()
            if (this.userStore.usuario != null) {
                this.usuario = this.userStore.usuario
            }
        }
        this.carregouUsuario = true

        this.itemsNav = [
            { id: 1, name: "Início", img: require("../assets/Imagens/home.svg"), needLogin: false, rota: "/" },
            { id: 2, name: "Meus jogos", img: require("../assets/Imagens/joystick.svg"), needLogin: true, rota: `/account/profile/${this.usuario?.usuario}` },
            { id: 3, name: "Comunidade", img: require("../assets/Imagens/community.svg"), needLogin: false, rota: "/community" },
            { id: 4, name: "Configurações", img: require("../assets/Imagens/settings.svg"), needLogin: true, rota: "/account/settings" },
        ]
    },
    beforeUnmount() {
        document.removeEventListener('click', this.verificaClick)
    },
    methods: {
        voltarRota() {
            if (window.history.length > 1) {
                this.$router.back()
            } else {
                this.$router.push('/')
            }
        },
        verificaClick(event) {
            if (this.$refs.menuMobile && !this.$refs.menuMobile.contains(event.target) && this.$refs.menuMobileButton && !this.$refs.menuMobileButton.contains(event.target)) {
                console.log("checa")
                this.menuMobileOpen = false
            }
        },
        deslogaUsuario() {
            useUserStore().logout()
            window.location.reload()
        }
    },
    computed: {
        itemsNavFiltrados() {
            if (!this.usuario) {
                return this.itemsNav.filter(i => !i.needLogin)
            }
            return this.itemsNav
        },

        primeiraLetraUsuario() {
            return this.usuario?.usuario?.charAt(0).toUpperCase() || ''
        }

    },
}
</script>

<style scoped>
.filtro-zinc-50 {
    filter: brightness(0) saturate(100%) invert(100%) sepia(12%) saturate(151%) hue-rotate(234deg) brightness(113%) contrast(96%);
}

.filtro-cinza {
    filter: brightness(0) saturate(100%) invert(44%) sepia(8%) saturate(376%) hue-rotate(202deg) brightness(98%) contrast(90%);
}

.gradiente-cinza-transparente {
    background: rgb(237, 237, 237);
    background: linear-gradient(180deg, rgba(237, 237, 237, 0) 0%, rgba(27, 29, 31, 1) 85%);
}

.gradiente-cinza-escuro {
    background: #191a1c;
    background: linear-gradient(90deg, rgba(25, 26, 28, 0.7) 21%, rgba(25, 26, 28, 0.4) 70%, rgba(25, 26, 28, 0.55) 85%, rgba(25, 26, 28, 0) 100%);
}

.gradiente-cinza-claro {
    background: rgb(26, 27, 30);
    background: linear-gradient(0deg, rgba(26, 27, 30, 1) 1%, rgba(43, 44, 48, 1) 100%);
}
</style>