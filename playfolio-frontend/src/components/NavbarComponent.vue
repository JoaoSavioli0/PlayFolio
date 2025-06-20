<template>
    <PesquisaJogoBoxComponent v-if="pesquisaJogoBoxOpen" @fechar-box="pesquisaJogoBoxOpen = false" />
    <div
        class="w-full xl:h-[80px] xl:py-0 py-4 flex justify-between min-[1800px]:px-40 xl:px-20 px-4 items-center fixed z-[200] bg-[#161616]/60 backdrop-blur-md">
        <div class="flex items-center">
            <img src="../assets/Imagens/logo.png" class="size-[13px] shrink-0 filtro-cinza">
            <h1 class="text-zinc-50 ml-2">PlayFolio</h1>
        </div>
        <div class="flex">
            <button
                class="rounded-full xl:w-[140px] px-[9px] h-[35px] bg-zinc-50 flex items-center justify-between xl:pl-2 xl:pr-3 cursor-pointer"
                @click="pesquisaJogoBoxOpen = true">
                <h2 class="text-zinc-900 text-[11px] ml-2 max-xl:hidden">Pesquisar</h2>
                <img src="../assets/Imagens/lupa.svg" class="w-[17px] h-auto shrink-0 scale-x-[-1]">
            </button>
            <router-link tipo="login" to="/account/login" v-if="!usuario?.id" class="p-0 flex">
                <div class="ml-2 flex items-center justify-center size-[35px] rounded-full bg-zinc-50">
                    <div
                        class="size-[31px] bg-zinc-900 text-black rounded-full flex items-center justify-center relative overflow-hidden">
                        <img src="../assets/Imagens/ghost.svg"
                            class="w-[33px] h-auto filtro-cinza absolute -translate-x-1/2 bottom-[-7px] start-1/2 ">
                    </div>
                </div>
            </router-link>
            <div class="flex px-2 relative" v-else>
                <div ref="profile" class="flex cursor-pointer shrink-0"
                    @click="profileSubmenuOpen = !profileSubmenuOpen">
                    <div v-if="usuario.id"
                        class="relative size-[35px] rounded-full bg-gradient-to-br from-amber-500 to-pink-500 p-[2px]">
                        <div
                            class="w-full h-full bg-zinc-900 rounded-full flex items-center justify-center overflow-hidden relative">
                            <img :src="`data:image/png;base64,${usuario.imagem}`"
                                class="w-full h-full object-cover absolute start-1/2 -translate-x-1/2 top-1/2 -translate-y-1/2"
                                v-if="usuario.imagem" />
                            <h1 v-else
                                class="text-2xl text-zinc-50 absolute start-1/2 -translate-x-1/2 top-1/2 -translate-y-1/2">
                                {{ primeiraLetraUsuario }}
                            </h1>
                        </div>
                    </div>
                    <div class="flex flex-col ml-2 text-start h-full justify-center max-xl:hidden max-w-[160px]">
                        <h1 class="text-xs text-zinc-50 truncate ">{{ usuario.nome }}</h1>
                        <span class="text-zinc-400 text-[10px]">@{{ usuario.usuario }}</span>
                    </div>
                </div>
                <div ref="menu"
                    class="absolute w-max rounded-lg bg-zinc-800 xl:start-2 max-xl:end-2 top-[50px] opacity-0 transition-all duration-100"
                    :class="{ 'opacity-100': profileSubmenuOpen }">
                    <ul class="*:text-xs *:text-zinc-200 text-start *:cursor-pointer">
                        <router-link :to="`/account/profile/${usuario.usuario}`" class="*:py-3 *:px-3">
                            <li class="hover:bg-zinc-400/50 transition-all duration-200 rounded-t-lg">
                                <div class="flex items-center w-full">
                                    <div class="size-[20px] flex items-center justify-center">
                                        <img src="../assets/Imagens/user.svg" class="w-[16px] h-auto filtro-cinza-2">
                                    </div>
                                    <span class="ml-1">Perfil</span>
                                </div>
                            </li>
                        </router-link>
                        <router-link to="/account/settings" class="*:py-3 *:px-3">
                            <li class="hover:bg-zinc-400/50 transition-all duration-200">
                                <div class="flex items-center w-full">
                                    <div class="size-[20px] flex items-center justify-center">
                                        <img src="../assets/Imagens/settings.svg"
                                            class="w-[18px] h-auto filtro-cinza-2">
                                    </div>
                                    <span class="ml-1">Configurações</span>
                                </div>
                            </li>
                        </router-link>
                        <div class="*:py-3 *:px-3" @click="deslogaUsuario">
                            <li class="hover:bg-zinc-400/50 transition-all duration-200 rounded-b-lg">
                                <div class="flex items-center w-full">
                                    <div class="size-[20px] flex items-center justify-center">
                                        <img src="../assets/Imagens/exit.svg"
                                            class="w-[15px] h-auto filtro-cinza-2 -rotate-180 ml-[2px]">
                                    </div>
                                    <span class="ml-1">Sair</span>
                                </div>
                            </li>
                        </div>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import PesquisaJogoBoxComponent from './PesquisaJogoBoxComponent.vue';
import { useUserStore } from '@/stores/UserStore';

export default {
    name: "Navbar",
    components: {
        PesquisaJogoBoxComponent
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

        document.addEventListener("click", this.verificaClick)
    },
    methods: {
        verificaClick(event) {
            if ((this.$refs.menu && !this.$refs.menu.contains(event.target)) && (this.$refs.profile && !this.$refs.profile.contains(event.target))) {
                this.profileSubmenuOpen = false
            }
        },
        deslogaUsuario() {
            useUserStore().logout()
            window.location.reload()
        },
    },
    data() {
        return {
            pesquisaJogoBoxOpen: false,
            usuario: {},
            profileSubmenuOpen: false
        }
    },
    computed: {
        primeiraLetraUsuario() {
            return this.usuario?.usuario?.charAt(0).toUpperCase() || ''
        }
    }
}
</script>

<style scoped>
.filtro-cinza {
    filter: brightness(0) saturate(100%) invert(99%) sepia(93%) saturate(127%) hue-rotate(221deg) brightness(114%) contrast(96%);
}

.filtro-cinza-2 {
    filter: brightness(0) saturate(100%) invert(89%) sepia(6%) saturate(942%) hue-rotate(344deg) brightness(114%) contrast(79%);
}

.filtro-cinza-3 {
    filter: brightness(0) saturate(100%) invert(72%) sepia(8%) saturate(277%) hue-rotate(201deg) brightness(87%) contrast(89%);
}
</style>