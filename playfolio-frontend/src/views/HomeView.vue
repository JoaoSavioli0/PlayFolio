<template>
  <NavbarComponent />
  <BannerComponent />
  <TutorialComponent />
  <InfoComponent />
  <DestaqueComponent />
  <JogosComponent />
  <FooterComponent />
</template>

<script>

import InfoComponent from '@/components/InfoComponent.vue';
import BannerComponent from '@/components/BannerComponent.vue';
import NavbarComponent from '@/components/NavbarComponent.vue';
import DestaqueComponent from '@/components/DestaqueComponent.vue';
import JogosComponent from '@/components/JogosComponent.vue';
import FooterComponent from '@/components/FooterComponent.vue';
import { useHomePageInfoStore } from '@/stores/HomePageInfoStore';
import TutorialComponent from '@/components/TutorialComponent.vue';

export default {

  name: 'HomeView',
  components: {
    InfoComponent,
    BannerComponent,
    TutorialComponent,
    NavbarComponent,
    DestaqueComponent,
    JogosComponent,
    FooterComponent
  },
  data() {
    return {
      homePageInfoStore: useHomePageInfoStore()
    }
  },
  async mounted() {
    alert("Este projeto usa o plano gratuito do render para hospedar sua API, por conta disso, a API pode demorar até 1 minuto para ser iniciada e os dados serem carregados.")
    if (
      this.homePageInfoStore.jogosMaisReviews.length === 0 ||
      this.homePageInfoStore.jogosMaiorNota.length === 0 ||
      this.homePageInfoStore.jogosEmBreve.length === 0 ||
      this.homePageInfoStore.jogosEmDestaque.length === 0
    ) {
      await this.homePageInfoStore.carregaHomePageInfo()
    }
  }
}
</script>

<style scoped></style>
