import { defineStore } from "pinia";
import { api } from "@/services/api";

export const useTwitchTokenStore = defineStore("TwitchToken", {
    state: () => ({
        access_token: "",
        expires_in: 0,
    }),
    actions: {
        async buscaToken() {
            try {
                const response = await api.post("/api/twitch/token")

                this.access_token = response.data.access_token
                this.expires_in = response.data.expires_in
            } catch (error) {
                console.error("Erro ao buscar token da Twitch:", error)
            }
        }
    }
})