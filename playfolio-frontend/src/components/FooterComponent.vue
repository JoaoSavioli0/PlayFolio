<template>
    <div
        class="w-full xl:py-12 py-6 border-t border-zinc-600 grid xl:grid-cols-2 grid-cols-1 xl:px-40 px-4 xl:mt-12 mt-4">
        <div class="flex items-center h-full">
            <img src="../assets/Imagens/logo.png" class="xl:size-[45px] size-[25px] shrink-0 filtro-cinza">
            <h1 class="text-zinc-50 ml-2 xl:text-[60px] text-[25px]">PlayFolio</h1>
        </div>
        <div class="grid grid-cols-2 gap-4 max-xl:mt-4">

            <div class="xl:px-6">
                <h2 class="text-start">[Sociais]</h2>
                <ul>
                    <li v-for="social in socials" :key="social.id"
                        class="group text-start text-zinc-50 hover:bg-zinc-50 hover:text-[#1A1B1E] w-fit cursor-pointer py-0 leading-[0.85]">
                        <span class="text-xs">
                            [<img :src="social.icon" class="social inline-block w-[10px] h-auto
            group-hover:invert-0 group-hover:sepia-0 group-hover:saturate-100 group-hover:hue-rotate-0 group-hover:brightness-100 group-hover:contrast-100
            invert-[.99] sepia-[.01] saturate-[.01] hue-rotate-[162deg] brightness-[1.07] contrast-[.96]">]
                            {{ social.nome }}
                        </span>
                    </li>
                </ul>
            </div>

            <div class="px-6">
                <h2 class="text-start">[Sobre]</h2>
                <ul>
                    <li v-for="link in sobre" :key="link.id"
                        class="group text-start text-zinc-50 hover:bg-zinc-50 hover:text-[#1A1B1E] w-fit cursor-pointer py-0 leading-[0.85]">
                        <span class="text-xs">
                            [<img src="../assets/Imagens/arrow-3.svg" class="social inline-block w-[13px] h-auto -rotate-45
            group-hover:invert-0 group-hover:sepia-0 group-hover:saturate-100 group-hover:hue-rotate-0 group-hover:brightness-100 group-hover:contrast-100
            invert-[.99] sepia-[.01] saturate-[.01] hue-rotate-[162deg] brightness-[1.07] contrast-[.96]">]
                            {{ link.nome }}
                        </span>
                    </li>
                </ul>
            </div>

            <div class="xl:px-6 pt-2 col-span-2 text-start">
                <span class="text-zinc-500 text-left text-xs">Desenvolvido por: João Savioli</span>
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
            ],
            sobre: [
                { id: 1, nome: "Sobre", href: "/" },
                { id: 2, nome: "Criadores", href: "/" },
                { id: 3, nome: "Termos de uso", href: "/" },
            ]
        }
    }
}
</script>

<style scoped>
.filtro-cinza {
    filter: brightness(0) saturate(100%) invert(99%) sepia(93%) saturate(127%) hue-rotate(221deg) brightness(114%) contrast(96%);
}

.li:hover span img {
    filter: brightness(0) saturate(100%)
}
</style>