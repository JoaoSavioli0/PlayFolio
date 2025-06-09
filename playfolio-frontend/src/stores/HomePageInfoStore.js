import { defineStore } from "pinia";
import { api } from "@/services/api";

export const useHomePageInfoStore = defineStore("HomePageInfo", {
  state: () => ({
    //Listas de jogos
    jogosEmDestaque: [],
    jogosEmBreve: [],
    jogosMaisReviews: [],
    jogosMaiorNota: [],

    //Sublistas
    plataformasJogos: {},
    capasJogos: {},
    generosNomes: {},

    //Listas gerais (junta os ids, para carregar ambos em apenas uma requisição)
    jogosIds: [],
    generosIds: [],
    capasIds: [],
    plataformasIds: [],
  }),
  actions: {
    // Método para carregar tudo
    async carregaHomePageInfo() {
      await this.carregaJogosMaisReviews();
      await this.carregaJogosMaiorNota();
      await this.carregaCapas();
      await this.carregaGeneros();
      await this.carregaPlataformas();

      console.log("==========HomePageInfoStore=========");
      console.log("JogosMaisReviews: ", this.jogosMaisReviews);
      console.log("JogosMaiorNota: ", this.jogosMaiorNota);
      console.log("plataformasJogos: ", this.plataformasJogos);
      console.log("capasJogos: ", this.capasJogos);
      console.log("generosNomes: ", this.generosNomes);
    },

    // Carrega jogos com mais reviews e alimenta listas gerais
    async carregaJogosMaisReviews() {
      const body = `fields *; sort value desc; limit 6; sort total_rating_count desc;`;

      try {
        const response = await api.post("/api/igdb/proxy", body, {
          headers: {
            "igdb-endpoint": "/v4/games",
            "Content-Type": "text/plain",
          },
        });

        this.jogosMaisReviews = response.data;

        this.jogosMaisReviews.map((jogo) => {
          if (!this.jogosIds.includes(jogo.id)) this.jogosIds.push(jogo.id);
        });

        this.jogosMaisReviews.forEach((jogo) => {
          jogo.genres.forEach((genreId) => {
            if (!this.generosIds.includes(genreId))
              this.generosIds.push(genreId);
          });
        });

        this.jogosMaisReviews.forEach((jogo) => {
          jogo.platforms.forEach((platformId) => {
            if (!this.plataformasIds.includes(platformId))
              this.plataformasIds.push(platformId);
          });
        });
      } catch (error) {
        console.error("Erro carregando jogos com mais review: " + error);
      }
    },

    // Carrega jogos com maiores notas e alimenta listas gerais
    async carregaJogosMaiorNota() {
      const body = `fields *; limit 6; where rating_count > 300 & version_parent = null & parent_game = null; sort rating desc;`;

      try {
        const response = await api.post("/api/igdb/proxy", body, {
          headers: {
            "igdb-endpoint": "/v4/games",
            "Content-Type": "text/plain",
          },
        });

        this.jogosMaiorNota = response.data;

        this.jogosMaiorNota.map((jogo) => {
          if (!this.jogosIds.includes(jogo.id)) this.jogosIds.push(jogo.id);
        });

        this.jogosMaiorNota.forEach((jogo) => {
          jogo.genres.forEach((genreId) => {
            if (!this.generosIds.includes(genreId))
              this.generosIds.push(genreId);
          });
        });

        this.jogosMaiorNota.forEach((jogo) => {
          jogo.platforms.forEach((platformId) => {
            if (!this.plataformasIds.includes(platformId))
              this.plataformasIds.push(platformId);
          });
        });
      } catch (error) {
        console.error("Erro carregando jogos com maior nota: " + error);
      }
    },

    // Carrega informações das sublistas
    // Carrega capas da lista geral
    async carregaCapas() {
      const body = `fields url; where game = (${this.jogosIds.join(
        ", "
      )}); limit ${this.jogosIds.length};`;
      try {
        const response = await api.post("/api/igdb/proxy", body, {
          headers: {
            "igdb-endpoint": "/v4/covers",
            "Content-Type": "text/plain",
          },
        });

        for (let imagem of response.data) {
          if (imagem && imagem.url) {
            this.capasJogos[imagem.id] = imagem.url.replace("thumb", "720p");
          }
        }
      } catch (error) {
        console.error("Erro carregando imagens: " + error);
      }
    },

    // Carrega generos da lista geral
    async carregaGeneros() {
      const body = `fields name; where id = (${this.generosIds.join(
        ", "
      )}); limit ${this.generosIds.length};`;
      try {
        const response = await api.post("/api/igdb/proxy", body, {
          headers: {
            "igdb-endpoint": "/v4/genres",
            "Content-Type": "text/plain",
          },
        });

        for (let genero of response.data) {
          this.generosNomes[genero.id] = genero.name;
        }
      } catch (error) {
        console.error("Erro carregando generos: " + error);
      }
    },

    // Carrega plataformas da lista geral
    async carregaPlataformas() {
      const body = `fields abbreviation; where id = (${this.plataformasIds.join(
        ", "
      )}); limit ${this.plataformasIds.length};`;

      try {
        const response = await api.post("/api/igdb/proxy", body, {
          headers: {
            "igdb-endpoint": "/v4/platforms",
            "Content-Type": "text/plain",
          },
        });

        for (let plataforma of response.data) {
          const abbreviation = plataforma.abbreviation;
          const id = 0;
          if (abbreviation.includes("PS") || abbreviation.includes("Vita"))
            id = 1;
          else if (
            abbreviation.includes("Series X|S") ||
            abbreviation.includes("XBOX") ||
            abbreviation.includes("X360")
          )
            id = 2;
          else if (abbreviation.includes("PC")) id = 3;
          else if (abbreviation.includes("iOS")) id = 4;
          else if (abbreviation.includes("Switch")) id = 5;
          else if (abbreviation.includes("Stadia")) id = 6;

          this.plataformasJogos[plataforma.id] = id;
        }
      } catch (error) {
        console.error("Erro carregando plataformas: " + error);
      }
    },
  },
});
