// router/index.js
import { createRouter, createWebHistory } from 'vue-router';
import { AuthService } from '@/services/auth.js';

// Import your existing components
import HomePage from '@/views/HomePage.vue';
import CarDetailsPage from '@/views/CarDetailsPage.vue';
import BookingPage from '@/views/BookingPage.vue';
import ConfirmationPage from '@/views/ConfirmationPage.vue';
import SignupPage from '@/views/SignupPage.vue';
import LoginPage from '@/views/LoginPage.vue';

// Auth guards
const requireAuth = (to, from, next) => {
  const authService = new AuthService();
  if (authService.isAuthenticated()) {
    next();
  } else {
    next('/login');
  }
};

const requireGuest = (to, from, next) => {
  const authService = new AuthService();
  if (!authService.isAuthenticated()) {
    next();
  } else {
    next('/');
  }
};

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomePage
  },
  {
    path: '/cars/:id',
    name: 'carDetails',
    component: CarDetailsPage,
    beforeEnter: requireAuth // Require login to view car details
  },
  {
    path: '/booking/:id',
    name: 'booking',
    component: BookingPage,
    beforeEnter: requireAuth // Require login to book
  },
  {
    path: '/confirmation/:bookingId',
    name: 'confirmation',
    component: ConfirmationPage,
    beforeEnter: requireAuth // Require login to see confirmation
  },
  {
    path: '/signup',
    name: 'signup',
    component: SignupPage,
    beforeEnter: requireGuest // Redirect to home if already logged in
  },
  {
    path: '/login',
    name: 'login',
    component: LoginPage,
    beforeEnter: requireGuest // Redirect to home if already logged in
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;