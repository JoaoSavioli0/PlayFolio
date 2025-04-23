<template>
    <div class="w-full py-12 border-t border-zinc-600 grid grid-cols-2 px-40 mt-12">
        <div class="flex items-center h-full">
            <img src="../assets/Imagens/logo.png" class="size-[45px] shrink-0 filtro-cinza">
            <h1 class="text-zinc-50 ml-2 lg:text-[60px]">PlayFolio</h1>
        </div>
        <div class="grid grid-cols-2 gap-4">
            <div class="px-6">
                <ul>
                    <li v-for="social in socials"
                        class="text-start text-zinc-50 hover:bg-zinc-50 hover:text-[#1A1B1E] w-fit cursor-pointer">
                        <span class="text-xs">[<img :src="social.icon"
                                class="inline-block w-[10px] h-auto
                            invert-[.99] sepia-[.01] saturate-[.01] hue-rotate-[162deg] brightness-[1.07] contrast-[.96]">]
                            {{
                                social.nome }}
                        </span>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: "Footer",
    data() {
        return {
            socials: [
                { id: 1, icon: require('../assets/Imagens/social_discord.png'), nome: "Discord", href: "/" },
                { id: 2, icon: require('../assets/Imagens/social_instagram.png'), nome: "Instagram", href: "/" },
                { id: 3, icon: require('../assets/Imagens/social_reddit.png'), nome: "Reddit", href: "/" },
                { id: 4, icon: require('../assets/Imagens/social_x.png'), nome: "X", href: "/" },
            ]
        }
    }
}
</script>

<style scoped>
.filtro-cinza {
    filter: brightness(0) saturate(100%) invert(99%) sepia(93%) saturate(127%) hue-rotate(221deg) brightness(114%) contrast(96%);
}
</style>