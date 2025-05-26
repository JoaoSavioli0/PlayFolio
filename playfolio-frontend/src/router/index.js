import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import GameView from '@/views/GameView.vue'
import LoginView from '@/views/LoginView.vue'
import ProfileView from '@/views/ProfileView.vue'

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/game/:id',
    name: 'game',
    component: GameView,
    props: true,
  },
  {
    path: '/account/:tipo',
    name: 'login',
    component: LoginView,
    props: true
  },
  {
    path: '/account/profile/:filtro',
    name: 'profile',
    component: ProfileView,
    props: true
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
  scrollBehavior(to, from, savedPosition) {
    return { top: 0 }
  }
})

export default router
