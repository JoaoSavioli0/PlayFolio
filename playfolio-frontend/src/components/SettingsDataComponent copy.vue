<template>
    <div class="w-full flex flex-col xl:px-24 mt-12">

        <div class="w-full xl:mt-12 text-start">
            <label class="text-xs text-zinc-400 px-4">Email
            </label>
            <div class="w-full border-b border-white/10 outline-none">
                <input type="text" class="w-full py-2 px-4 outline-none border-none" v-model="email">
            </div>
        </div>
        <div class="w-full pt-2 text-start" v-if="erroEmail">
            <span class="text-[10px] text-rose-700">{{ erroEmail }}</span>
        </div>
        <div class="w-full pt-2 text-start" v-if="sucessoAtualizacaoEmail">
            <span class="text-[10px] text-lime-600">{{ sucessoAtualizacaoEmail }}</span>
        </div>

        <div class="w-full xl:mt-8 mt-4 flex justify-end">
            <button class="text-sm rounded-full text-zinc-900 py-2 w-1/2"
                :class="!emailAlterado ? 'bg-zinc-500' : 'bg-zinc-200 cursor-pointer'" :disabled="!emailAlterado"
                @click="atualizaEmail">
                Atualizar email</button>
        </div>

        <div class="w-full mt-8 text-start">
            <label class="text-xs text-zinc-400 px-4">Senha atual
            </label>
            <div class="w-full border-b border-white/10 outline-none">
                <input type="text" class="w-full py-2 px-4 outline-none border-none" v-model="senhaAtual">
            </div>
        </div>
        <div class="w-full pt-2 text-start" v-if="errosSenhaAtual">
            <span class="text-[10px] text-rose-700">{{ errosSenhaAtual }}</span>
        </div>

        <div class="w-full mt-8 text-start">
            <label class="text-xs text-zinc-400 px-4">Nova senha
            </label>
            <div class="w-full border-b border-white/10 outline-none">
                <input type="text" class="w-full py-2 px-4 outline-none border-none" v-model="novaSenha">
            </div>
        </div>
        <ul class="mt-1 px-2">
            <li v-for="erroSenha in errosNovaSenha" class="text-start text-[10px] text-rose-700">-{{
                erroSenha
            }}</li>
        </ul>

        <div class="w-full mt-8 text-start">
            <label class="text-xs text-zinc-400 px-4">Confirmar senha
            </label>
            <div class="w-full border-b border-white/10 outline-none">
                <input type="text" class="w-full py-2 px-4 outline-none border-none" v-model="novaSenhaConfirma">
            </div>
        </div>
        <div class="w-full pt-2 text-start" v-if="errosConfirmaNovaSenha">
            <span class="text-[10px] text-rose-700">{{ errosConfirmaNovaSenha }}</span>
        </div>
        <div class="w-full pt-2 text-start" v-if="sucessoAtualizacaoSenha">
            <span class="text-[10px] text-lime-600">{{ sucessoAtualizacaoSenha }}</span>
        </div>

        <div class="w-full xl:mt-8 mt-4 flex justify-end">
            <button class="text-sm rounded-full text-zinc-900 py-2 w-1/2"
                :class="!camposSenhaPreenchidos ? 'bg-zinc-500' : 'bg-zinc-200 cursor-pointer'"
                :disabled="!camposSenhaPreenchidos" @click="atualizaSenha">
                Alterar senha</button>
        </div>
    </div>
</template>

<script>
import axios from 'axios'
import { useUserStore } from '@/stores/UserStore'
export default {
    name: 'settingsdata',
    props: ['usuario'],
    data() {
        return {
            email: this.usuario.email,
            senhaAtual: '',
            novaSenha: '',
            novaSenhaConfirma: '',

            senhaAtualValida: false,

            sucessoAtualizacaoSenha: "",
            sucessoAtualizacaoEmail: "",

            errosSenhaAtual: "",
            errosNovaSenha: [],
            errosConfirmaNovaSenha: '',
            erroEmail: ''
        }
    },
    methods: {
        validaSenha() {
            this.errosNovaSenha = []
            this.errosConfirmaNovaSenha = ''

            if (this.novaSenha.length < 8) this.errosNovaSenha.push("Mínimo de 8 caracteres")
            if (this.novaSenha.toLowerCase() === this.novaSenha) this.errosNovaSenha.push("Pelo menos 1 caracter maíusculo")
            if (!/\d/.test(this.novaSenha)) this.errosNovaSenha.push("Pelo menos 1 número")
            if (/^\d+$/.test(this.novaSenha)) this.errosNovaSenha.push("Pelo menos 1 letra")
            if (this.errosNovaSenha.length > 0) return false

            if (this.novaSenhaConfirma !== this.novaSenha) this.errosConfirmaNovaSenha = "As senhas devem ser iguais"
            if (this.errosConfirmaNovaSenha.length > 0) return false

            return true
        },
        validaEmail() {
            this.erroEmail = ''
            const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            return regex.test(this.email);
        },
        async atualizaSenha() {
            if (!this.validaSenha()) return
            this.errosSenhaAtual = ''
            try {
                const response = await axios.post("http://localhost:5000/usuario/update-password", {
                    id: this.usuario.id,
                    senhaAtual: this.senhaAtual,
                    novaSenha: this.novaSenha
                })

                this.sucessoAtualizacaoSenha = response.data

                this.senhaAtual = ''
                this.novaSenha = ''
                this.novaSenhaConfirma = ''

            } catch (error) {
                if (!error?.response) this.errosSenhaAtual = "Erro ao atualizar senha!"
                else if (error.response.status == 403) this.errosSenhaAtual = error.response.data
                else if (error.response.status == 404) this.errosSenhaAtual = error.response.data
            }
        },
        async atualizaEmail() {
            if (!this.validaEmail()) {
                this.erroEmail = "Email inválido"
                return
            }

            try {
                const response = await axios.post("http://localhost:5000/usuario/update-email", {
                    id: this.usuario.id,
                    email: this.email
                })

                this.sucessoAtualizacaoEmail = response.data

                this.usuario.email = this.email

            } catch (error) {
                if (!error?.response) this.erroEmail = "Erro ao atualizar email!"
                else if (error.response.status == 404) this.erroEmail = error.response.data
            }
        }
    },
    computed: {
        primeiraLetraUsuario() {
            return this.usuario?.usuario?.charAt(0).toUpperCase() || ''
        },
        camposSenhaPreenchidos() {
            return this.senhaAtual.length > 0 && this.novaSenha.length > 0 && this.novaSenhaConfirma.length > 0
        },
        emailAlterado() {
            return this.email !== this.usuario.email && this.email != ''
        }
    },
    watch: {
        sucessoAtualizacaoSenha() {
            setTimeout(() => {
                this.sucessoAtualizacaoSenha = ''
            }, 5000)
        },
        sucessoAtualizacaoEmail() {
            setTimeout(() => {
                this.sucessoAtualizacaoEmail = ''
            }, 5000)
        },
    }
}
</script>