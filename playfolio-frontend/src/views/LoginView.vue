<template>
    <div class="w-full h-screen flex justify-center items-center ">
        <div class="bg-glass flex w-[70%] h-[75%] rounded-3xl overflow-hidden shadow-lg">
            <div class="grow-1 h-full overflow-hidden relative">
                <div class="w-full h-full bg-black/80 absolute z-20"></div>

                <div class="w-full top-[40px] start-[40px] absolute z-30 flex justify-start">
                    <router-link to="/">
                        <button
                            class="pr-4 pl-2 py-[3px] bg-zinc-50 rounded-full text-zinc-900 flex items-end cursor-pointer shadow-md">
                            <img src="../assets/Imagens/arrow-2.svg" class="w-[21px] h-auto">
                            <h2 class="text-sm">Voltar</h2>
                        </button>
                    </router-link>
                </div>

                <div class="w-full absolute start-[40px] bottom-[20px] z-30">
                    <img src="../assets/Imagens/logo.png" class="w-[90px] h-auto filtro-cinza ml-[4px]">
                    <h1 class="text-[80px] text-start text-zinc-50">PLAYFOLIO</h1>
                </div>

                <img src="../assets/Imagens/login_image.jpeg" class="w-full h-full object-cover relative z-10">
            </div>

            <div class="p-6 w-[500px] h-full bg-[#262729] rounded-3xl rounded-l-none flex items-center">

                <div class="w-full">
                    <div class="pt-4 w-full text-start">
                        <img src="../assets/Imagens/logo.png" class="w-[30px] h-auto filtro-cinza">
                        <h1 class="text-xl xl:text-4xl mt-2">BEM VINDO DE VOLTA!</h1>
                        <span class="text-zinc-500 text-[12px]">Faça login com seus dados</span>
                    </div>
                    <div class="w-full flex flex-col py-4">
                        <label class="w-full text-start">
                            <span class="text-zinc-400 text-[13px] ml-4">Email</span>
                            <input type="text" v-model="emailLogin"
                                class="w-full rounded-full text-sm p-4 text-zinc-50 border-[1px] border-zinc-500 outline-none focus:border-zinc-400 transition-all">
                        </label>
                        <label class="w-full text-start mt-4">
                            <span class="text-zinc-400 text-[13px] ml-4">Senha</span>
                            <input type="text" v-model="senhaLogin"
                                class="w-full rounded-full text-sm p-4 text-zinc-50 border-[1px] border-zinc-500 outline-none focus:border-zinc-400 transition-all">
                        </label>
                        <button class="rounded-full w-full py-4 text-center bg-zinc-50 mt-8 cursor-pointer"
                            @click="fazLogin()">
                            <h2 class="text-zinc-800">LOGIN</h2>
                        </button>
                    </div>
                    <div class="w-full py-2 flex justify-center items-center">
                        <div class="grow-1 w-full bg-zinc-500 h-[1px]"></div>
                        <span class="text-[12px] text-zinc-50 px-4 inline-block whitespace-nowrap">Não tem uma
                            conta?</span>
                        <div class="grow-1 w-full bg-zinc-500 h-[1px]"></div>
                    </div>
                    <div class="w-full mt-4">
                        <button class="rounded-full w-full py-2 text-center border-[1px] border-zinc-50 cursor-pointer">
                            <h2 class="text-zinc-50 text-xs">CRIE UMA CONTA</h2>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
import { useUserStore } from '@/stores/UserStore';
import { useRouter } from "vue-router"

export default {
    name: "Login",
    data() {
        return {
            usuarioObj: {
                email: "",
                senha: ""
            },
            emailLogin: "",
            senhaLogin: ""
        }
    },
    mounted() {
        useUserStore().reconectaSessao()
        console.log("Usuario: " + useUserStore().usuario)
        if (useUserStore().usuario != null) {
            this.$router.push("/account")
        }
    },
    methods: {
        async fazLogin() {
            if (!this.emailLogin) {
                return
            }
            if (!this.senhaLogin) {
                return
            }

            this.usuarioObj.email = this.emailLogin
            this.usuarioObj.senha = this.senhaLogin

            await useUserStore().login(this.usuarioObj)

            console.log(this.usuarioObj)
            console.log(useUserStore().usuario)

            if (useUserStore().usuario != null) {
                console.log("logou")
                this.$router.push("/")
            }
        }
    }
}
</script>

<style scoped>
.brilho {
    background: #292a2e;
    background: linear-gradient(0deg, rgba(41, 42, 46, 1) 0%, rgba(0, 0, 0, 0) 29%);
}

.gradiente-preto {
    background: #1A1B1E;
    background: linear-gradient(0deg, rgba(26, 27, 30, 1) 0%, rgba(26, 27, 30, 0.54) 72%, rgba(26, 27, 30, 0) 100%);
}

.filtro-cinza {
    filter: brightness(0) saturate(100%) invert(99%) sepia(93%) saturate(127%) hue-rotate(221deg) brightness(114%) contrast(96%);
}
</style>