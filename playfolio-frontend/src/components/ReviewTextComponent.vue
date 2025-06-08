<template>
    <!-- Review  -->
    <div class="w-full flex mt-2">
        <!-- Texto -->
        <div class="w-full flex flex-col">
            <p ref="textoRef" class="text-zinc-400 xl:text-xs text-[9px] break-all w-full"
                :class="{ 'line-clamp-5': !mostrarMais }">
                {{ review.review }}
            </p>
            <a class="xl:text-[12px] w-fit text-[8px] cursor-pointer px-2 py-[2px] rounded-sm text-zinc-900 bg-zinc-50 mt-[5px] ml-auto"
                @click="mostrarMais = !mostrarMais" v-if="mostrarBotao">
                <span>{{ mostrarMais ? 'Mostrar menos' : 'Mostrar mais' }}</span>
            </a>
        </div>

    </div>
</template>

<script>
import SubmenuReviewComponent from '@/components/SubmenuReviewComponent.vue'

export default {
    props: ["usuario", "review"],
    name: "ReviewText",
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
    components: {
        SubmenuReviewComponent
    },
    methods: {
        verificarAlturaTexto() {
            this.$nextTick(() => {
                const el = this.$refs.textoRef
                if (!el) return

                const alturaLinha = parseFloat(getComputedStyle(el).lineHeight)
                const alturaMaxima = alturaLinha * 5
                this.mostrarMais = !(el.scrollHeight > alturaMaxima)
                this.mostrarBotao = el.scrollHeight > alturaMaxima
            })
        }
    },
    computed: {
        primeiraLetraUsuario() {
            return this.usuario?.usuario?.charAt(0).toUpperCase() || 'P'
        }
    }
}
</script>

<style scoped>
.gradiente-texto {
    background: #18181B;
    background: linear-gradient(0deg, rgba(24, 24, 27, 1) 0%, rgba(24, 24, 27, 0) 100%);
}

.degradeVermelho {
    background: #FF0000;
    background: linear-gradient(to right, #FF0000 0%, #FF9500 100%);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
}

.degradeAmarelo {
    background: #ECE80D;
    background: linear-gradient(to right, #ECE80D 35%, #FF9604 100%);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
}

.degradeAzul {
    background: #10AAD5;
    background: linear-gradient(to right, #10AAD5 0%, #0BFBFF 100%);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
}

.degradeRoxo {
    background: #DB1BEC;
    background: linear-gradient(to right, #DB1BEC 35%, #FF09FB 100%);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
}
</style>