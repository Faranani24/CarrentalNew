import { createRouter, createWebHistory } from 'vue-router'
import HomePage from '@/views/HomePage.vue'
import CarDetailsPage from '@/views/CarDetailsPage.vue'
import BookingPage from '@/views/BookingPage.vue'

const routes = [
  { path: '/', name: 'home', component: HomePage },
  { path: '/cars/:id', name: 'carDetails', component: CarDetailsPage },
  { path: '/booking/:id', name: 'booking', component: BookingPage },
]

export default createRouter({
  history: createWebHistory(),
  routes,
})