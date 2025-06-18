<template>
    <div class="w-full h-full pb-6 pt-4 xl:px-24 px-4 flex xl:flex-row flex-col justify-center gap-x-4">
        <MenuMobileComponent :selected="3" />
        <MenuComponent :selected="3" />
        <div class="xl:w-[820px] w-full xl:h-min max-xl:min-h-screen overflow-hidden flex flex-col">
            <button @click="voltarRota"
                class="w-[90px] py-1 bg-zinc-50 px-2 rounded-full flex justify-around items-center cursor-pointer min-xl:hidden">
                <img src="../assets/Imagens/arrow.svg" class="w-[21px] h-auto">
                <span class="text-xs text-[#1b1d1f]">Voltar</span>
            </button>
            <div class="w-full py-4">
                <h1 class="text-zinc-50 text-2xl text-start" v-if="!usuarios.length">
                    CARREGANDO COMUNIDADE <span class="loading loading-dots loading-xl"></span></h1>
                <h1 class="text-zinc-50 text-2xl text-start" v-else>COMUNIDADE</h1>
            </div>
            <div class="w-full grid grid-cols-1 gap-y-[4px]">
                <router-link v-for="usuario in usuarios" :to="`/account/profile/${usuario.usuario}`"
                    class="w-full border border-white/10 rounded-lg bg-zinc-900 flex px-4 py-4 items-center shrink-0">
                    <div
                        class="relative size-[43px] rounded-full bg-gradient-to-br from-amber-500 to-pink-500 p-[3px] flex shrink-0 ">
                        <div
                            class="w-full h-full bg-zinc-900 rounded-full flex shrink-0 items-center justify-center overflow-hidden relative">
                            <img :src="`data:image/png;base64,${usuario.imagem}`"
                                class="w-full h-full object-cover absolute start-1/2 -translate-x-1/2 top-1/2 -translate-y-1/2"
                                v-if="usuario.imagem" />
                            <h1 v-else
                                class="text-2xl text-zinc-50 absolute start-1/2 -translate-x-1/2 top-1/2 -translate-y-1/2">
                                {{ primeiraLetraUsuario(usuario.usuario) }}
                            </h1>
                        </div>
                    </div>
                    <div class="flex flex-col ml-2 text-start min-w-[100px]">
                        <h1 class="text-zinc-50 text-xs">{{ usuario.nome }}</h1>
                        <span class="text-zinc-400 text-[9px]">@{{ usuario.usuario }}</span>
                        <span class="text-[9px] text-zinc-400 mt-[2px] tracking-wide">{{ usuario.numJogos }}
                            reviews</span>
                    </div>
                    <div class="flex h-full ml-6 items-center italic text-[9px] text-zinc-400 max-md:text-[7px] max-w-[300px] xl:ml-auto"
                        v-if="usuario.bio">
                        {{ usuario.bio }}
                    </div>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script>
import MenuComponent from '@/components/MenuComponent.vue';
import MenuMobileComponent from '@/components/MenuMobileComponent.vue';
import { api } from '@/services/api'

export default {
    name: 'Community',
    components: {
        MenuComponent,
        MenuMobileComponent
    },
    data() {
        return {
            usuarios: []
        }
    },
    mounted() {
        this.carregaUsuarios()
    },
    methods: {
        voltarRota() {
            if (window.history.length > 1) {
                this.$router.back()
            } else {
                this.$router.push('/')
            }
        },
        async carregaUsuarios() {
            try {
                const response = await api.get("/usuario/all")
                this.usuarios = response.data
            } catch (error) {
                console.log("Erro ao carregar usuários: ", error)
            }
        },
        primeiraLetraUsuario(usuario) {
            return usuario.charAt(0).toUpperCase() || ''
        }
    }
}
</script>