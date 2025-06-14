<template>
    <div class="w-screen h-screen bg-black/50 flex justify-center items-center top-0 start-0 fixed z-[100]"
        v-if="exibeExcluiReviewBox">
        <div class="bg-zinc-800 rounded-lg px-6  xl:w-[400px] w-[90%] py-12" ref="excluirBox">
            <h2 class="text-zinc-50 text-center">Deseja mesmo excluir?</h2>
            <div class="flex w-full justify-center mt-6">
                <button @click="exibeExcluiReviewBox = false"
                    class="w-[40%] rounded-md bg-zinc-700 text-zinc-50 h-[50px] text-sm cursor-pointer">Cancelar</button>
                <button @click="() => { wishlist ? excluirWishlist() : excluirReview() }"
                    class="w-[40%] rounded-md bg-zinc-700 text-rose-600 h-[50px] text-sm cursor-pointer ml-2 border-rose-700 border-2">Excluir</button>
            </div>
        </div>
    </div>

    <a ref="menu"
        class="ml-auto mb-auto cursor-pointer size-[25px] flex justify-center items-center rounded-full hover:bg-zinc-700/50 relative -mt-2"
        :class="{ 'bg-zinc-700/50': exibeSub }" @click="exibeSub = !exibeSub">
        <img src="../assets/Imagens/dots.svg" class="xl:w-[15px] w-[12px] h-auto">
        <div class="flex flex-col top-0 mt-[25px] end-0 absolute" v-if="exibeSub">
            <ul
                class="*:px-2 *:py-1 *:first:rounded-t-lg *:last:rounded-b-lg text-zinc-400 text-[12px] w-max rounded-lg shadow-md bg-zinc-900 text-start">
                <li class="hover:bg-zinc-800" @click="denunciaReviewBox"
                    v-if="idUsuarioAutor != idUsuarioLogado && !wishlist">
                    Denunciar
                </li>
                <li class="hover:bg-zinc-800" @click="excluiReviewBox"
                    v-if="idUsuarioAutor == idUsuarioLogado && !wishlist">
                    Excluir
                </li>
                <li class="hover:bg-zinc-800" @click="excluiReviewBox"
                    v-if="idUsuarioAutor == idUsuarioLogado && wishlist">
                    Remover
                </li>
            </ul>
        </div>
    </a>
</template>

<script>
import { api } from '@/services/api'

export default {
    name: "SubmenuReview",
    props: ['idUsuarioAutor', 'idUsuarioLogado', 'review', 'wishlist'],
    emits: ['recarrega-dados'],
    data() {
        return {
            exibeSub: false,
            exibeExcluiReviewBox: false,
            exibeDenunciaReviewBox: false,
        }
    },
    methods: {
        verificaClick(event) {
            if (this.$refs.menu && !this.$refs.menu.contains(event.target)) {
                this.exibeSub = false
            }
            if (this.exibeExcluiReviewBox && this.$refs.excluirBox && !this.$refs.excluirBox.contains(event.target)) {
                this.exibeExcluiReviewBox = false
            }
        },
        denunciaReviewBox() {
            this.exibeDenunciaReviewBox = true
        },
        excluiReviewBox() {
            if (this.idUsuario != this.review?.usuario?.id && this.idUsuario != this.review.idUsuario) return
            setTimeout(() => {
                this.exibeExcluiReviewBox = true
            }, 100)
        },
        async excluirReview() {
            try {
                console.log("Entrou")
                await api.get(`/review/delete/${this.review?.id || this.review.idReview}`)
            } catch (error) {
                console.log("Erro ao excluir review: ", error)
            } finally {
                this.exibeExcluiReviewBox = false
                this.$emit("recarrega-dados", "Exclusao")
            }
        },
        async excluirWishlist() {
            try {
                console.log("excluir wishlist")
                await api.get(`/wishlist/delete/${this.wishlist}`)
            } catch (error) {
                console.log("Erro ao excluir wishlist: ", error)
            } finally {
                this.exibeExcluiReviewBox = false
                this.$emit("recarrega-dados", "Exclusao")
            }
        }
    },
    mounted() {
        document.addEventListener('click', this.verificaClick)
        console.log("É wishlist: ", this.wishlist)
    },
    beforeUnmount() {
        document.removeEventListener('click', this.verificaClick)
    }
}
</script>