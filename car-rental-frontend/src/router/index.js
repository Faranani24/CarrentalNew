import { createRouter, createWebHistory } from 'vue-router';
import HomePage from '@/views/HomePage.vue';
import AdminPanel from '@/views/AdminPanel.vue';
import LoginPage from '@/views/LoginPage.vue';
import SignupPage from '@/views/SignupPage.vue';
import BookingPage from '@/views/BookingPage.vue';

const routes = [
  { path: '/', name: 'home', component: HomePage },
  { path: '/admin', name: 'admin', component: AdminPanel },
  { path: '/login', name: 'login', component: LoginPage },
  { path: '/signup', name: 'signup', component: SignupPage },

  // My Bookings (no param)
  { path: '/booking', name: 'my-bookings', component: BookingPage },

  // Booking a specific car
  { path: '/booking/:carId', name: 'booking', component: BookingPage, props: true },

  // Redirect /cars to home since cars display there
  { path: '/cars', redirect: '/' },

  // Catch-all fallback (optional, prevents other 404s)
  { path: '/:catchAll(.*)', redirect: '/' }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;
