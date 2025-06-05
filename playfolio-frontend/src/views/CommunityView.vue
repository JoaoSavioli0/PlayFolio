<template>
    <div class="w-full h-full pb-6 pt-4 xl:px-24 px-4 flex xl:flex-row flex-col justify-center gap-x-4">
        <MenuMobileComponent />
        <MenuComponent :selected="3" />
        <div class="xl:w-[820px] w-full xl:h-min max-xl:min-h-screen overflow-hidden flex flex-col">
            <div class="w-full py-4">
                <h1 class="text-zinc-50 text-2xl text-start">COMUNIDADE</h1>
            </div>
            <div class="w-full grid grid-cols-1 gap-y-[4px]">
                <router-link v-for="usuario in usuarios" :to="`/account/profile/${usuario.usuario}`"
                    class="w-full border border-white/10 rounded-lg bg-zinc-900 flex px-4 py-4 items-center">
                    <div class="size-[40px] rounded-full bg-zinc-400"></div>
                    <div class="flex flex-col ml-2 text-start">
                        <h1 class="text-zinc-50 text-xs">{{ usuario.nome }}</h1>
                        <span class="text-zinc-400 text-[9px]">@{{ usuario.usuario }}</span>
                        <span class="text-[9px] text-zinc-400 mt-[2px] tracking-wide">{{ usuario.numJogos }}
                            reviews</span>
                    </div>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script>
import MenuComponent from '@/components/MenuComponent.vue';
import MenuMobileComponent from '@/components/MenuMobileComponent.vue';
import axios from 'axios';

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
        async carregaUsuarios() {
            try {
                const response = await axios.get("http://localhost:5000/usuario/all")
                this.usuarios = response.data
                console.log(this.usuarios)
            } catch (error) {
                console.log("Erro ao carregar usuários: ", error)
            }
        }
    }
}
</script>