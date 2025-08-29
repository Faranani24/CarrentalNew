// src/router/index.js
import { createRouter, createWebHistory } from 'vue-router'

// Lazy load components for better performance
const HomePage = () => import('../views/HomePage.vue')
const BookingPage = () => import('../views/BookingPage.vue')
const ConfirmationPage = () => import('../views/ConfirmationPage.vue')

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomePage
    },
    {
      // The :id is a route parameter that captures the car's ID
      path: '/booking/:id',
      name: 'booking',
      component: BookingPage
    },
    {
      // This route will show the booking confirmation, using a booking ID
      path: '/confirmation/:bookingId',
      name: 'confirmation',
      component: ConfirmationPage
    }
  ]
})

export default router