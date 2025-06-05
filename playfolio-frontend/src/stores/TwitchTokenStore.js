import { defineStore } from "pinia";
import axios from "axios";

export const useTwitchTokenStore = defineStore("TwitchToken", {
    state: () => ({
        access_token: "",
        expires_in: 0,
    }),
    actions: {
        async buscaToken() {
            try {
                const response = await axios.post("http://localhost:5000/api/twitch/token")

                this.access_token = response.data.access_token
                this.expires_in = response.data.expires_in
            } catch (error) {
                console.error("Erro ao buscar token da Twitch:", error)
            }
        }
    }
})