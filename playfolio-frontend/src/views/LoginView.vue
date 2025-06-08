<template>
    <div class="w-full min-h-screen flex justify-center xl:items-center">
        <div
            class="flex min-[1800px]:w-[70%] xl:w-[85%] xl:h-[75%] w-full xl:rounded-3xl max-xl:min-h-screen xl:flex-row flex-col overflow-hidden shadow-lg ">
            <div class="xl:h-full h-[120px] overflow-hidden relative w-full">
                <div class="w-full h-full bg-black/80 absolute z-20"></div>

                <div class="w-full top-[40px] xl:start-[40px] start-[20px] absolute z-30 flex justify-start">
                    <router-link to="/">
                        <button
                            class="pr-4 xl:pl-2 py-[3px] rounded-full text-zinc-900 flex items-end cursor-pointer shadow-md">
                            <img src="../assets/Imagens/arrow-2.svg" class="w-[21px] h-auto filtro-branco">
                            <span class="text-sm text-zinc-50">Voltar</span>
                        </button>
                    </router-link>
                </div>

                <div
                    class="w-full absolute xl:start-[40px] xl:bottom-[20px] start-[45%] max-xl:top-[40px] z-30 flex xl:flex-col flex-row">
                    <img src="../assets/Imagens/logo.png"
                        class="xl:size-[90px] size-[21px]  filtro-cinza xl:ml-[4px] mr-2 shrink-0">
                    <h1 class="xl:text-[80px] text-xl text-start text-zinc-50">PLAYFOLIO</h1>
                </div>

                <img src="../assets/Imagens/login_image.jpeg" class="w-full h-full object-cover relative z-10">
            </div>

            <div class="px-6 xl:w-[500px] w-full bg-[#262729] max-xl:h-full xl:rounded-r-3xl flex items-center"
                v-if="tipo == 'login'">

                <div class="w-full max-xl:py-4">
                    <div class="w-full text-start">
                        <img src="../assets/Imagens/logo.png" class="w-[30px] h-auto filtro-cinza">
                        <h1 class="text-xl xl:text-4xl mt-2">BEM VINDO DE VOLTA!</h1>
                        <span class="text-zinc-500 text-[12px]">Faça login com seus dados</span>
                    </div>
                    <div class="w-full flex flex-col py-4">
                        <label class="w-full text-start">
                            <span class="text-zinc-400 text-[13px] ml-4">Email</span>
                            <input type="text" v-model="emailLogin"
                                class="w-full rounded-full text-sm px-4 py-[10px] text-zinc-50 border-[1px] border-zinc-500 outline-none focus:border-zinc-400 transition-all">
                        </label>
                        <label class="w-full text-start mt-4 relative">
                            <span class="text-zinc-400 text-[13px] ml-4">Senha</span>
                            <input :type="senhaVisivel ? 'text' : 'password'" v-model="senhaLogin" maxlength="20"
                                class="w-full rounded-full text-sm px-4 py-[10px] text-zinc-50 border-[1px] border-zinc-500 outline-none focus:border-zinc-400 transition-all pr-[35px]">
                            <button class="absolute end-[15px] top-[35px] z-[100] cursor-pointer"
                                @click="senhaVisivel = !senhaVisivel">
                                <img src="../assets/Imagens/eye.svg" class="w-[20px] h-auto" v-if="senhaVisivel">
                                <img src="../assets/Imagens/eye-closed.svg" class="w-[20px] h-auto" v-else>
                            </button>
                        </label>

                        <div class="w-full pt-1 text-start" v-if="erroAviso">
                            <span class="text-[10px] text-rose-700">{{ erroAviso }}</span>
                        </div>

                        <button
                            class="rounded-full w-full min-[1800px]:py-3 py-[10px] text-center bg-zinc-50 mt-8 cursor-pointer"
                            @click="fazLogin()">
                            <h2 class="text-zinc-800">LOGIN</h2>
                        </button>
                    </div>
                    <div class="w-full py-2 flex justify-center items-center">
                        <div class="grow w-full bg-zinc-500 h-[1px]"></div>
                        <span class="text-[12px] text-zinc-50 px-4 inline-block whitespace-nowrap">Não tem uma
                            conta?</span>
                        <div class="grow w-full bg-zinc-500 h-[1px]"></div>
                    </div>
                    <div class="w-full mt-4">
                        <router-link to="/account/registro" class="p-0">
                            <button
                                class="rounded-full w-full py-2 text-center border-[1px] border-zinc-50 cursor-pointer">
                                <h2 class="text-zinc-50 text-xs">CRIE UMA CONTA</h2>
                            </button>
                        </router-link>
                    </div>
                </div>
            </div>

            <div class="px-6 xl:w-[600px] w-full max-xl:h-full bg-[#262729] xl:rounded-r-3xl flex items-center"
                v-if="tipo == 'registro'">
                <div class="w-full max-xl:py-4">
                    <div class="w-full text-start">
                        <img src="../assets/Imagens/logo.png" class="xl:size-[30px] size-[20px] filtro-cinza">
                        <h1 class="text-xl xl:text-4xl mt-2">FAÇA PARTE!</h1>
                        <span class="text-zinc-500 text-[12px]">Registre-se com seus dados</span>
                    </div>
                    <div class="w-full flex flex-col py-4 grid xl:grid-cols-2 grid-cols-1 gap-y-2 gap-x-4"
                        v-if="!contaCriada">
                        <label class="w-full text-start max-xl:col-span-2">
                            <span class="text-zinc-400 text-[13px] ml-4">Nome pessoal</span>
                            <input type="text" v-model="nomeRegistro" maxlength="20"
                                class="w-full rounded-full text-sm px-4 py-[10px] text-zinc-50 border-[1px] outline-none focus:border-zinc-400 transition-all"
                                :class="nomeValido ? 'border-zinc-500' : 'border-rose-700'">
                        </label>
                        <div class="w-full text-start -mt-1" v-if="erroNomeRegistro">
                            <span class="text-[10px] text-rose-700">{{ erroNomeRegistro }}</span>
                        </div>

                        <label class="w-full text-start max-xl:col-span-2">
                            <span class="text-zinc-400 text-[13px] ml-4">Nome de usuário</span>
                            <div class="relative w-full">
                                <input type="text" v-model="userRegistro" maxlength="16"
                                    class="w-full rounded-full text-sm pl-8 pr-4 py-[10px] text-zinc-50 border-[1px] outline-none focus:border-zinc-400 transition-all"
                                    :class="userValido ? 'border-zinc-500' : 'border-rose-700'">
                                <span class="absolute top-[11px] start-[11px] text-zinc-500 text-sm">@</span>
                            </div>
                        </label>
                        <div class="w-full text-start -mt-1" v-if="erroUsuarioRegistro">
                            <span class="text-[10px] text-rose-700">{{ erroUsuarioRegistro }}</span>
                        </div>

                        <label class="w-full text-start col-span-2">
                            <span class="text-zinc-400 text-[13px] ml-4">Email</span>
                            <input type="text" v-model="emailRegistro"
                                class="w-full rounded-full text-sm px-4 py-[10px] text-zinc-50 border-[1px] outline-none focus:border-zinc-400 transition-all"
                                :class="emailValido ? 'border-zinc-500' : 'border-rose-700'">
                        </label>
                        <div class="w-full text-start -mt-1" v-if="erroEmailRegistro">
                            <span class="text-[10px] text-rose-700">{{ erroEmailRegistro }}</span>
                        </div>

                        <label class="w-full text-start relative max-xl:col-span-2">
                            <span class="text-zinc-400 text-[13px] ml-4">Senha</span>
                            <input :type="senhaRegistroVisivel ? 'text' : 'password'" v-model="senhaRegistro"
                                maxlength="20"
                                class="w-full rounded-full text-sm px-4 py-[10px] text-zinc-50 border-[1px] outline-none focus:border-zinc-400 transition-all pr-[45px]"
                                :class="senhaValido ? 'border-zinc-500' : 'border-rose-700'">
                            <ul class="mt-1 ml-2">
                                <li v-for="erroSenha in errosSenhaRegistro"
                                    class="text-start text-[10px] text-rose-700">-{{
                                        erroSenha
                                    }}</li>
                            </ul>
                            <button class="absolute end-[15px] top-[35px] z-[100] cursor-pointer"
                                @click="senhaRegistroVisivel = !senhaRegistroVisivel">
                                <img src="../assets/Imagens/eye.svg" class="w-[20px] h-auto"
                                    v-if="senhaRegistroVisivel">
                                <img src="../assets/Imagens/eye-closed.svg" class="w-[20px] h-auto" v-else>
                            </button>
                        </label>
                        <label class="w-full text-start relative max-xl:col-span-2">
                            <span class="text-zinc-400 text-[13px] ml-4">Confirme a senha</span>
                            <input :type="confirmaSenhaRegistroVisivel ? 'text' : 'password'"
                                v-model="confirmaSenhaRegistro" maxlength="20"
                                class="w-full rounded-full text-sm px-4 py-[10px] text-zinc-50 border-[1px] outline-none focus:border-zinc-400 transition-all"
                                :class="senhaConfirmaValido ? 'border-zinc-500' : 'border-rose-700'">
                            <ul class="mt-1 px-2">
                                <li v-for="erroSenha in errosConfirmaSenhaRegistro"
                                    class="text-start text-[10px] text-rose-700">-{{
                                        erroSenha
                                    }}</li>
                            </ul>
                            <button class="absolute end-[15px] top-[35px] z-[100] cursor-pointer"
                                @click="confirmaSenhaRegistroVisivel = !confirmaSenhaRegistroVisivel">
                                <img src="../assets/Imagens/eye.svg" class="w-[20px] h-auto"
                                    v-if="confirmaSenhaRegistroVisivel">
                                <img src="../assets/Imagens/eye-closed.svg" class="w-[20px] h-auto" v-else>
                            </button>
                        </label>
                        <button
                            class="mt-4 col-span-2 rounded-full w-full py-3 py-[10px] text-center bg-zinc-50 cursor-pointer"
                            @click="fazCadastro()">
                            <h2 class="text-zinc-800">CADASTRAR</h2>
                        </button>
                    </div>

                    <div class="w-full flex items-center justify-center pt-32 pb-20" v-if="contaCriada">
                        <h1 class="text-zinc-50 text-3xl text-start">Sua conta foi criada com sucesso!<br><span
                                class="text-sm">Faça login para acessar sua
                                conta.</span></h1>
                    </div>

                    <div class="w-full flex justify-center items-center" v-if="!contaCriada">
                        <div class="grow-1 w-full bg-zinc-500 h-[1px]"></div>
                        <span class="text-[12px] text-zinc-50 px-4 inline-block whitespace-nowrap">Já possui uma
                            conta?</span>
                        <div class="grow-1 w-full bg-zinc-500 h-[1px]"></div>
                    </div>
                    <div class="w-full mt-4">
                        <router-link to="/account/login" class="p-0">
                            <button
                                class="rounded-full w-full py-2 text-center border-[1px] border-zinc-50 cursor-pointer">
                                <h2 class="text-zinc-50 text-xs">FAÇA LOGIN</h2>
                            </button>
                        </router-link>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { useUserStore } from '@/stores/UserStore';
import { api } from '@/services/api'

export default {
    name: "Login",
    props: ["tipo"],
    data() {
        return {
            usuarioObj: {
                email: "",
                senha: ""
            },
            emailLogin: "",
            senhaLogin: "",
            nomeRegistro: "",
            nomeValido: true,
            userRegistro: "",
            userValido: true,
            emailRegistro: "",
            emailValido: true,
            senhaRegistro: "",
            senhaValido: true,
            confirmaSenhaRegistro: "",
            senhaConfirmaValido: true,
            errosSenhaRegistro: [],
            errosConfirmaSenhaRegistro: [],
            contaCriada: false,
            senhaRegistroVisivel: false,
            confirmaSenhaRegistroVisivel: false,
            senhaVisivel: false,
            erroAviso: '',
            userStore: useUserStore(),

            erroUsuarioRegistro: '',
            erroEmailRegistro: '',
            erroNomeRegistro: '',
            erroRegistro: ''
        }
    },
    mounted() {
        this.userStore.reconectaSessao()
        if (this.userStore.usuario != null) {
            this.$router.push("/account")
        }
    },
    methods: {
        async fazLogin() {
            this.erroAviso = ''

            if (!this.emailLogin) {
                this.erroAviso = "Insira um email válido"
                return
            }

            this.usuarioObj.email = this.emailLogin
            this.usuarioObj.senha = this.senhaLogin

            try {
                const response = await this.userStore.login(this.usuarioObj)
                if (response != null) this.erroAviso = response.data

                if (this.userStore.usuario != null) {
                    this.$router.push("/")
                }
            } catch (error) {
                this.erroAviso = error
            }


        },

        async fazCadastro() {
            if (!this.validaCampos()) {
                return
            }
            try {
                const response = await api.post("/usuario/registro", {
                    email: this.emailRegistro,
                    senha: this.senhaRegistro,
                    nome: this.nomeRegistro,
                    usuario: this.userRegistro
                })

                if (response != null) {
                    this.contaCriada = true
                }
            } catch (error) {
                if (error.response) {
                    console.log("Status -> ", error.response.status, "\nData -> ", error.response.data)
                    if (error.response.status == 409) this.erroEmailRegistro = error.response.data
                    if (error.response.status == 403) this.erroUsuarioRegistro = error.response.data
                }
            }
        },

        validaCampos() {
            if (!this.validaSenha() || !this.validaEmail || !this.validaNome || !this.validaUser) {
                this.userValido = this.validaUser
                this.nomeValido = this.validaNome
                this.emailValido = this.validaEmail
                this.senhaValido = this.validaSenha()
                this.senhaConfirmaValido = !this.errosConfirmaSenhaRegistro.length > 0
                return false
            }
            return true
        },

        validaSenha() {
            this.errosSenhaRegistro = []
            this.errosConfirmaSenhaRegistro = []

            if (!this.senhaRegistro) {
                this.errosSenhaRegistro.push("Digite uma senha")
                return false;
            }

            if (this.senhaRegistro.length < 8) this.errosSenhaRegistro.push("Mínimo de 8 caracteres")
            if (this.senhaRegistro.toLowerCase() === this.senhaRegistro) this.errosSenhaRegistro.push("Pelo menos 1 caracter maíusculo")
            if (!/\d/.test(this.senhaRegistro)) this.errosSenhaRegistro.push("Pelo menos 1 número")
            if (/^\d+$/.test(this.senhaRegistro)) this.errosSenhaRegistro.push("Pelo menos 1 letra")
            if (this.confirmaSenhaRegistro !== this.senhaRegistro) this.errosConfirmaSenhaRegistro.push("As senhas devem ser iguais")
            if (this.errosSenhaRegistro.length > 0 || this.errosConfirmaSenhaRegistro.length > 0) return false
            return true
        },

    },
    computed: {

        validaNome() {
            if (!this.nomeRegistro || this.nomeRegistro.length < 3) {
                this.erroNomeRegistro = "Seu nome deve ter no mínimo 3 caracteres!"
                return false
            }
            return true
        },

        validaUser() {
            if (!this.userRegistro) {
                this.erroUsuarioRegistro = "Insira um nome de usuário!"
                return false
            }
            return true
        },

        validaEmail() {
            const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (!regex.test(this.emailRegistro) || this.emailRegistro.split("@")[0].length < 3) {
                this.erroEmailRegistro = "Insira um email válido!"
                return false
            }
            return true
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

.filtro-branco {
    filter: brightness(0) invert(1);
}
</style>