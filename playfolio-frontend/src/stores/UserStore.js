import { defineStore } from 'pinia';
import axios from 'axios';

export const useUserStore = defineStore('usuario', {
  state: () => ({
    usuario: null, // Armazena informações do usuário
    token: localStorage.getItem("token") || null
  }),
  actions: {
    async login(usuarioData) {
      try {
        const response = await axios.post("http://localhost:5000/usuario/login", {
          email: usuarioData.email,
          senha: usuarioData.senha
        },
          {
            headers: {
              "Content-Type": "application/json",
            },
          })

        if(response.data != null){
            this.usuario = response.data.usuario
            this.token = response.data.token
            localStorage.setItem("token", this.token)
            axios.defaults.headers.common["Authorization"] = `Bearer ${this.token}`;
        }

      } catch (error) {
        console.error("Erro ao efeturar login: " + error)
      }
    },
    logout() {
      this.usuario = null
      this.token = null
      localStorage.removeItem("token")
      delete axios.defaults.headers.common["Authorization"]
    },
    async reconectaSessao() {
      if (!this.token) {
        console.log("Sem token")
        return
    }

      try {
        const response = await axios.get("http://localhost:5000/usuario/me", {
          headers: { Authorization: `Bearer ${this.token}` },
        })

        this.usuario = response.data
      } catch (error) {
        console.error("Sua sessão está expirada, deslogando...")
        this.logout()
      }
    }
  },
});