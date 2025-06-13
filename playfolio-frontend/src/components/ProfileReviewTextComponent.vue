<template>
    <div class="w-full flex py-3 texto z-50 relative bg-zinc-900">
        <div class="absolute w-full h-[50px] top-[-50px] gradiente-texto"></div>
        <div class="flex px-4 gap-x-2">
            <div
                class="size-[28px] bg-zinc-800 text-black rounded-full flex items-center shrink-0 justify-center overflow-hidden">
                <img :src="`data:image/png;base64,${usuario.imagem}`" class="w-full h-full object-cover"
                    v-if="usuario.imagem">
                <h1 class="text-[12px] text-zinc-50" v-else>{{ primeiraLetraUsuario }}</h1>
            </div>
            <div class="w-full text-start">
                <p ref="textoRef" class="text-[11px] text-zinc-100 break-all w-full"
                    :class="{ 'line-clamp-5': !mostrarMais }">
                    {{ texto }}
                </p>
                <a class="text-[12px] cursor-pointer px-2 py-[2px] rounded-sm text-zinc-900 bg-zinc-50"
                    @click="mostrarMais = !mostrarMais" v-if="mostrarBotao">
                    <span>{{ mostrarMais ? 'Mostrar menos' : 'Mostrar mais' }}</span>
                </a>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    props: ["texto", "usuario"],
    name: "ProfileReviewText",
    data() {
        return {
            mostrarMais: true,
            mostrarBotao: false
        }
    },
    mounted() {
        this.verificarAlturaTexto()
        window.addEventListener('resize', this.verificarAlturaTexto)
    },
    beforeUnmount() {
        window.removeEventListener('resize', this.verificarAlturaTexto)
    },
    methods: {
        verificarAlturaTexto() {
            this.$nextTick(() => {
                const el = this.$refs.textoRef
                if (!el) return

                const alturaLinha = parseFloat(getComputedStyle(el).lineHeight)
                const alturaMaxima = alturaLinha * 5
                this.mostrarBotao = el.scrollHeight > alturaMaxima
                this.mostrarMais = !(el.scrollHeight > alturaMaxima)
            })
        }
    },
    computed: {
        primeiraLetraUsuario() {
            return this.usuario?.usuario?.charAt(0).toUpperCase() || 'P'
        }
    },
    watch: {

    }
}
</script>

<style scoped>
.gradiente-texto {
    background: #18181B;
    background: linear-gradient(0deg, rgba(24, 24, 27, 1) 0%, rgba(24, 24, 27, 0) 100%);
}
</style>