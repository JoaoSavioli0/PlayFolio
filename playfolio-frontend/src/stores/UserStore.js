import { defineStore } from 'pinia';
import { api } from '@/services/api';

export const useUserStore = defineStore('usuario', {
  state: () => ({
    usuario: null,
    token: localStorage.getItem("token") || null
  }),
  actions: {
    async login(usuarioData) {
      try {
        const response = await api.post("/usuario/login", {
          email: usuarioData.email,
          senha: usuarioData.senha
        },
          {
            headers: {
              "Content-Type": "application/json",
            },
          })
          console.log("Response.data ",response?.data)
        if(response.data != null){
            this.usuario = response.data.usuario
            this.token = response.data.token
            localStorage.setItem("token", this.token)
            api.defaults.headers.common["Authorization"] = `Bearer ${this.token}`;
        }

      } catch (error) {
        return error.response
      }
    },
    logout() {
      this.usuario = null
      this.token = null
      localStorage.removeItem("token")
      delete api.defaults.headers.common["Authorization"]
    },
    async reconectaSessao() {
      if (!this.token) {
        console.log("Sem token")
        return
    }

      try {
        const response = await api.get("/usuario/me", {
          headers: { Authorization: `Bearer ${this.token}` },
        })
        if(response.data && response.data.id)
        this.usuario = response.data
      
      } catch (error) {
        console.error("Sua sessão está expirada, deslogando...")
        this.logout()
      }
    }
  },
});