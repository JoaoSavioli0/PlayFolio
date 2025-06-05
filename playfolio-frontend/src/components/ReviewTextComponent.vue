<template>
    <!-- Review  -->
    <div class="w-full flex mt-2">
        <!-- Nota -->
        <div
            class="flex flex-col items-center xl:gap-y-2 xl:pr-4 pr-2 border-r border-zinc-500 xl:min-w-[100px] min-w-[60px]">
            <h1 class="xl:text-4xl text-xl" :class="{
                'degradeVermelho': review.nota < 4,
                'degradeAmarelo': review.nota >= 4 && review.nota < 7,
                'degradeAzul': review.nota >= 7 && review.nota < 9,
                'degradeRoxo': review.nota >= 9
            }">
                {{ review.nota }}
            </h1>
            <!-- Status -->
            <div class="w-full border rounded-md flex items-center justify-center py-[2px] xl:px-2 px-[15px]" :class="{
                'border-[#c31ef1]': review.status == 1,
                'border-zinc-300': review.status == 2,
                'border-[#d22d56]': review.status == 3,
            }">
                <span class="text-[#c31ef1] text-[8px] xl:text-[10px]" v-if="review.status == 1">Jogando</span>
                <span class="text-zinc-300 text-[8px] xl:text-[10px]" v-else-if="review.status == 2">Concluído</span>
                <span class="text-[#d22d56] text-[8px] xl:text-[10px]" v-else-if="review.status == 3">Dropado</span>
            </div>
        </div>
        <!-- Texto -->
        <div class="w-full flex flex-col pl-4">
            <p ref="textoRef" class="text-zinc-400 xl:text-xs text-[9px]" :class="{ 'line-clamp-5': !mostrarMais }">
                {{ review.review }}
            </p>
            <a class="xl:text-[12px] w-fit text-[8px] cursor-pointer px-2 py-[2px] rounded-sm text-zinc-900 bg-zinc-50 mt-[3px]"
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
            mostrarMais: false,
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