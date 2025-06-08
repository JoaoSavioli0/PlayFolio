<template>
    <div
        class="w-full h-full max-xl:min-h-screen pb-6 pt-4 xl:px-24 px-4 flex xl:flex-row flex-col xl:justify-center gap-x-4">
        <MenuComponent :selected="4" :key="menuKey" />
        <MenuMobileComponent :selected="4" :key="menuMobileKey" />
        <div class="xl:w-[820px] w-full h-min flex flex-col items-center">
            <div class="xl:w-2/3 w-full grid grid-cols-2">
                <button @click="menuSelected = 1"
                    class="text-center flex items-center justify-center py-4 border-b-2 cursor-pointer"
                    :class="menuSelected == 1 ? 'border-amber-600' : 'border-transparent'">
                    <img src="../assets/Imagens/user.svg" class="w-[18px] h-auto"
                        :class="menuSelected == 1 ? 'filtro-zinc-50' : 'filtro-zinc-400'">
                    <span class="ml-1 max-xl:text-sm"
                        :class="menuSelected == 1 ? 'text-zinc-50' : 'text-zinc-400'">Perfil</span>
                </button>
                <button @click="menuSelected = 2"
                    class="text-center flex items-center justify-center py-4 border-b-2 cursor-pointer"
                    :class="menuSelected == 2 ? 'border-amber-600' : 'border-transparent'">
                    <img src="../assets/Imagens/settings.svg" class="w-[22px] h-auto"
                        :class="menuSelected == 2 ? 'filtro-zinc-50' : 'filtro-zinc-400'">
                    <span class="ml-1 max-xl:text-sm"
                        :class="menuSelected == 2 ? 'text-zinc-50' : 'text-zinc-400'">Configurações</span>
                </button>
            </div>
            <SettingsProfileComponent :usuario="usuario" v-if="menuSelected == 1 && usuario.id"
                @atualiza-menu="() => { menuKey++; menuMobileKey++ }" />
            <SettingsDataComponentCopy :usuario="usuario" v-else-if="menuSelected == 2 && usuario.id" />
        </div>
    </div>
</template>

<script>
import MenuComponent from '@/components/MenuComponent.vue';
import MenuMobileComponent from '@/components/MenuMobileComponent.vue';
import SettingsDataComponentCopy from '@/components/SettingsDataComponent copy.vue';
import SettingsProfileComponent from '@/components/SettingsProfileComponent.vue';
import { useUserStore } from '@/stores/UserStore';

export default {
    name: "settings",
    components: {
        MenuComponent,
        MenuMobileComponent,
        SettingsProfileComponent,
        SettingsDataComponentCopy
    },
    data() {
        return {
            menuSelected: 1,
            usuario: {},
            userStore: useUserStore(),
            menuKey: 0,
            menuMobileKey: 0
        }
    },
    async mounted() {
        if (this.userStore.usuario != null) {
            this.usuario = this.userStore.usuario
        } else {
            await this.userStore.reconectaSessao()
            if (this.userStore.usuario != null) {
                this.usuario = this.userStore.usuario
            }
        }
    }
}
</script>

<style scoped>
.filtro-zinc-50 {
    filter: brightness(0) saturate(100%) invert(93%) sepia(2%) saturate(1259%) hue-rotate(3deg) brightness(107%) contrast(96%);
}

.filtro-zinc-400 {
    filter: brightness(0) saturate(100%) invert(65%) sepia(10%) saturate(216%) hue-rotate(202deg) brightness(97%) contrast(90%);
}
</style>