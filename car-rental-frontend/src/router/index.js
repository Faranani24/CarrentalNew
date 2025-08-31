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
  { path: '/', name: 'home', component: HomePage },
  { path: '/cars/:id', name: 'carDetails', component: CarDetailsPage },
  { path: '/booking/:id', name: 'booking', component: BookingPage },
  { path: '/confirmation/:bookingId', name: 'confirmation', component: ConfirmationPage },
  { path: '/signup', name: 'signup', component: SignupPage }, // Add the new signup route
  { path: '/login', name: 'login', component: LoginPage },
  { path: '/payment/:bookingId', name: 'payment', component: PaymentPage },
  { path: '/review/:id', name: 'review', component: ReviewPage},// Add the new login route
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

  {
  path: '/admins',
  name: 'admins',
  component: () => import('@/views/AdminView.vue'),
  beforeEnter: requireAuth // only logged-in users can manage admins
},
{
  path: '/services',
  name: 'services',
  component: () => import('@/views/ServiceView.vue'),
  beforeEnter: requireAuth // only logged-in users can manage services
    }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;
