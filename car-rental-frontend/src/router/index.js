// router/index.js
import { createRouter, createWebHistory } from 'vue-router';
import { AuthService } from '@/services/auth.js';

// Import your existing components
import HomePage from '@/views/HomePage.vue';
import CarDetailsPage from '@/views/CarDetailsPage.vue';
import BookingPage from '@/views/BookingPage.vue';
import PaymentPage from '@/views/PaymentPage.vue';
import ConfirmationPage from '@/views/ConfirmationPage.vue';
import SignupPage from '@/views/SignupPage.vue';
import LoginPage from '@/views/LoginPage.vue';
import ReviewPage from "@/views/ReviewPage.vue";

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
    beforeEnter: requireAuth
  },
  {
    path: '/booking/:id',
    name: 'booking',
    component: BookingPage,
    beforeEnter: requireAuth
  },
  {
    path: '/payment/:bookingId',
    name: 'payment',
    component: PaymentPage,
    beforeEnter: requireAuth
  },
  {
    path: '/confirmation/:bookingId',
    name: 'confirmation',
    component: ConfirmationPage,
    beforeEnter: requireAuth
  },
  {
    path: '/review/:id',
    name: 'review',
    component: ReviewPage,
    beforeEnter: requireAuth
  },
  {
    path: '/signup',
    name: 'signup',
    component: SignupPage,
    beforeEnter: requireGuest
  },
  {
    path: '/login',
    name: 'login',
    component: LoginPage,
    beforeEnter: requireGuest
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;