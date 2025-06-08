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
                <h1 class="text-zinc-50 text-2xl text-start">COMUNIDADE</h1>
            </div>
            <div class="w-full grid grid-cols-1 gap-y-[4px]">
                <router-link v-for="usuario in usuarios" :to="`/account/profile/${usuario.usuario}`"
                    class="w-full border border-white/10 rounded-lg bg-zinc-900 flex px-4 py-4 items-center">
                    <div
                        class="flex items-center justify-center size-[40px] rounded-full bg-gradient-to-br from-amber-500 to-pink-500 ">
                        <div
                            class="size-[36px] bg-zinc-900 text-black rounded-full flex items-center justify-center overflow-hidden">
                            <img :src="`data:image/png;base64,${usuario.imagem}`" class="w-full h-full object-cover"
                                v-if="usuario.imagem">
                            <h1 class="text-2xl text-zinc-50" v-else>{{ primeiraLetraUsuario(usuario.usuario) }}</h1>
                        </div>
                    </div>
                    <div class="flex flex-col ml-2 text-start">
                        <h1 class="text-zinc-50 text-xs">{{ usuario.nome }}</h1>
                        <span class="text-zinc-400 text-[9px]">@{{ usuario.usuario }}</span>
                        <span class="text-[9px] text-zinc-400 mt-[2px] tracking-wide">{{ usuario.numJogos }}
                            reviews</span>
                    </div>
                    <div class="flex h-full ml-6 items-center italic text-[9px] text-zinc-400" v-if="usuario.bio">
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
                console.log("Usuarios: ", this.usuarios)
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