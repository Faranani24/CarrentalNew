// src/router/index.js
import { createRouter, createWebHistory } from 'vue-router'

// Lazy load to avoid alias issues during build
const HomePage = () => import('../views/HomePage.vue')

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: '/', name: 'home', component: HomePage }
  ]
})

export default router