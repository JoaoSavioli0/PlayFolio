<template>
    <PesquisaJogoBoxComponent v-if="pesquisaJogoBoxOpen" @fechar-box="pesquisaJogoBoxOpen = false" />
    <div class="w-full h-[80px] flex justify-between px-40 items-center fixed z-[200] bg-[#161616]/60 backdrop-blur-md">
        <div class="flex items-center">
            <img src="../assets/Imagens/logo.png" class="size-[13px] shrink-0 filtro-cinza">
            <h1 class="text-zinc-50 ml-2">PlayFolio</h1>
        </div>
        <div class="flex">
            <button
                class="rounded-full w-[140px] h-[35px] bg-zinc-50 flex items-center justify-between pl-2 pr-3 cursor-pointer"
                @click="pesquisaJogoBoxOpen = true">
                <h2 class="text-zinc-900 text-[11px] ml-2">Pesquisar</h2>
                <img src="../assets/Imagens/lupa.svg" class="w-[17px] h-auto shrink-0 scale-x-[-1]">
            </button>
            <router-link tipo="login" to="/account/login" class="p-0">
                <div class="size-[35px] rounded-full bg-zinc-50 ml-2"></div>
            </router-link>
        </div>
    </div>
</template>

<script>
import PesquisaJogoBoxComponent from './PesquisaJogoBoxComponent.vue';

export default {
    name: "Navbar",
    components: {
        PesquisaJogoBoxComponent
    },
    data() {
        return {
            pesquisaJogoBoxOpen: false
        }
    }
}
</script>

<style scoped>
.filtro-cinza {
    filter: brightness(0) saturate(100%) invert(99%) sepia(93%) saturate(127%) hue-rotate(221deg) brightness(114%) contrast(96%);
}
</style>