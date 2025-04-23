<template>
  <NavbarComponent />
  <BannerComponent />
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

export default {

  name: 'HomeView',
  components: {
    InfoComponent,
    BannerComponent,
    NavbarComponent,
    DestaqueComponent,
    JogosComponent,
    FooterComponent
  },

}
</script>

<style scoped></style>
