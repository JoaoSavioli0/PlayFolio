import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import GameView from "@/views/GameView.vue";
import LoginView from "@/views/LoginView.vue";
import ProfileView from "@/views/ProfileView.vue";
import SettingsView from "@/views/SettingsView.vue";
import CommunityView from "@/views/CommunityView.vue";

const routes = [
  {
    path: "/",
    name: "home",
    component: HomeView,
  },
  {
    path: "/game/:id",
    name: "game",
    component: GameView,
    props: (route) => ({
      id: route.params.id,
      avaliacaoBoxOpenDefault: route.query.avaliacaoBoxOpenDefault === "true",
    }),
  },
  {
    path: "/account/:tipo",
    name: "login",
    component: LoginView,
    props: true,
  },
  {
    path: "/account/profile/:username",
    name: "profile",
    component: ProfileView,
    props: (route) => ({
      filtro: route.query.filtro,
      username: route.params.username,
    }),
  },
  {
    path: "/account/settings",
    name: "settings",
    component: SettingsView,
  },
  {
    path: "/community",
    name: "community",
    component: CommunityView,
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
  scrollBehavior(to, from, savedPosition) {
    return { top: 0 };
  },
});

export default router;
