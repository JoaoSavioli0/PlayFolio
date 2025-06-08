<template>
    <div class="w-full flex flex-col xl:px-24 mt-12">
        <div class="flex w-full items-center">
            <div
                class="shrink-0 flex items-center justify-center size-[120px] rounded-full bg-gradient-to-br from-amber-500 to-pink-500 ">
                <div
                    class="size-[113px] bg-zinc-900 text-black rounded-full flex items-center justify-center overflow-hidden">
                    <img :src="imagemPreview" class="w-full h-full object-cover" v-if="imagemPreview">
                    <img :src="`data:image/png;base64,${usuario.imagem}`" class="w-full h-full object-cover"
                        v-else-if="usuario.imagem">
                    <h1 class="text-[60px] text-zinc-50" v-else>{{ primeiraLetraUsuario }}</h1>
                </div>
            </div>
            <label class="flex flex-col ml-6 text-start w-2/3" for="pfp-upload">
                <span class="text-[11px] text-zinc-400">Recomenda-se usar uma imagem com pelo menos 98 x 98 pixels e 2MB
                    ou menos. Use um arquivo PNG ou JPG.</span>
                <div
                    class="px-4 h-[35px] border border-white/30 rounded-2xl w-max flex items-center mt-3 cursor-pointer">
                    <span class="text-[10px]">Enviar imagem</span>
                </div>
            </label>
            <input type="file" id="pfp-upload" class="hidden" @change="fotoUpload">
        </div>

        <div class="w-full pt-2 text-start" v-if="mensagemErroFoto">
            <span class="text-[10px] text-rose-700">{{ mensagemErroFoto }}</span>
        </div>
        <div class="w-full pt-2 text-start" v-if="mensagemSucessoFoto">
            <span class="text-[10px] text-lime-600">{{ mensagemSucessoFoto }}</span>
        </div>

        <div class="w-full mt-12 text-start">
            <label class="text-xs text-zinc-400 px-4">Nome de usuário
            </label>
            <div class="w-full border-b border-white/10 outline-none">
                <input type="text" class="w-full py-2 px-4 outline-none border-none max-xl:text-sm"
                    v-model="nomeUsuario">
            </div>
        </div>
        <div class="w-full text-start">
            <span class="text-[8px] text-zinc-400 leading-[0.3]">Esse nome será único e utilizado para identificar seu
                perfil.</span>
        </div>

        <div class="w-full mt-8 text-start">
            <label class="text-xs text-zinc-400 px-4">Nome de exibição
            </label>
            <div class="w-full border-b border-white/10 outline-none">
                <input type="text" class="w-full py-2 px-4 outline-none border-none max-xl:text-sm"
                    v-model="nomeExibicao">
            </div>
        </div>
        <div class="w-full text-start">
            <span class="text-[8px] text-zinc-400 leading-[0.3]">Esse será o nome principal usado para exibição do seu
                perfil.</span>
        </div>

        <div class="w-full mt-8 text-start">
            <label class="text-xs text-zinc-400 px-4 w-full flex justify-between">
                <span>Bio</span>
                <span>{{ bio.length }}/100</span>
            </label>
            <div class="w-full border-b border-white/10 outline-none">
                <textarea type="text" rows="3" v-model="bio" placeholder="Se resuma em até 100 caracteres"
                    class="w-full py-2 px-4 outline-none border-none resize-none max-xl:text-sm"
                    maxlength="100"></textarea>
            </div>
        </div>
        <div class="w-full pt-2 text-start" v-if="mensagemErro">
            <span class="text-[10px] text-rose-700">{{ mensagemErro }}</span>
        </div>
        <div class="w-full pt-2 text-start" v-if="mensagemSucesso">
            <span class="text-[10px] text-lime-600">{{ mensagemSucesso }}</span>
        </div>
        <div class="w-full mt-8 flex justify-end">
            <button class="text-sm rounded-full bg-zinc-200 text-zinc-900 py-2 w-1/2 cursor-pointer"
                @click="atualizarDados">Salvar</button>
        </div>
    </div>
</template>

<script>
import { useUserStore } from '@/stores/UserStore'
import { api } from '@/services/api'

export default {
    name: 'settingsprofile',
    props: ['usuario'],
    data() {
        return {
            nomeUsuario: this.usuario.usuario,
            nomeExibicao: this.usuario.nome,
            bio: this.usuario?.bio || '',
            mensagemErro: '',
            mensagemErroFoto: '',
            mensagemSucessoFoto: '',
            mensagemSucesso: '',
            imagemPreview: null
        }
    },
    mounted() {
        console.log(this.usuario)
    },
    computed: {
        primeiraLetraUsuario() {
            return this.usuario?.usuario?.charAt(0).toUpperCase() || ''
        }
    },
    methods: {
        async atualizarDados() {
            if (!this.verificaDados) return

            try {
                const response = await api.post("/usuario/update-data", {
                    id: this.usuario.id,
                    nome: this.nomeExibicao,
                    usuario: this.nomeUsuario,
                    bio: this.bio
                })
                this.mensagemSucesso = "Usuário atualizado!"
                useUserStore().usuario = response.data
                this.$emit("atualiza-menu")
            } catch (error) {
                if (error.response.status == 409) this.mensagemErro = error.response.data
                else console.log("Erro ao atualizar informações: ", error.response.data)
            }
        },
        verificaDados() {
            if (this.nomeUsuario.length < 3) return false
            if (this.nomeExibicao.length < 5) return false
            return true
        },
        fotoUpload(event) {
            if (!event.target?.files[0]) return
            const tipo = event.target.files[0].type
            const tamanho = event.target.files[0].size
            let larguraImagem = 0
            let alturaImagem = 0

            let objectUrl = URL.createObjectURL(event.target.files[0])

            const img = new Image()
            img.src = objectUrl
            img.onload = () => {
                larguraImagem = img.width
                alturaImagem = img.height

                if (tipo !== "image/png" && tipo !== "image/jpg" && tipo !== "image/jpeg") {
                    this.mensagemErroFoto = "A imagem precisa ser em formato JPG, JPEG ou PNG"
                    return
                } else if (tamanho > 2100000) {
                    this.mensagemErroFoto = "A imagem deve ter menos de 2mb!"
                    return
                } else if (larguraImagem < 98 || alturaImagem < 98) {
                    this.mensagemErroFoto = "A imagem deve ter um tamanho mínimo de 98x98!"
                    return
                }
                this.imagemPreview = objectUrl
                this.salvarFoto(event.target.files[0])
            }

            img.onerror = () => {
                this.mensagemErroFoto = "Erro ao carregar imagem.";
                URL.revokeObjectURL(objectUrl);
            };
        },
        async salvarFoto(imagemUpada) {
            if (imagemUpada) {
                const formData = new FormData()
                formData.append("file", imagemUpada)
                try {
                    const response = await api.post(`/usuario/foto-perfil/${this.usuario.id}/nova`,
                        formData,
                        {
                            headers: { "Content-Type": "multipart/form-data" }
                        }
                    )
                    this.mensagemSucessoFoto = "Imagem alterada com sucesso!"
                    useUserStore().usuario = response.data

                    this.$emit("atualiza-menu")
                } catch (error) {
                    console.error("Erro ao salvar imagem: ", error.response.data)
                    this.mensagemErroFoto = "Erro ao salvar imagem."
                }
            }
        },
    },
    watch: {
        mensagemErro() {
            setTimeout(() => {
                this.mensagemErro = ''
            }, 5000)
        },
        mensagemSucesso() {
            setTimeout(() => {
                this.mensagemSucesso = ''
            }, 5000)
        },
        mensagemSucessoFoto() {
            setTimeout(() => {
                this.mensagemSucessoFoto = ''
            }, 5000)
        },
        mensagemErroFoto() {
            setTimeout(() => {
                this.mensagemErroFoto = ''
            }, 5000)
        }
    }
}
</script>