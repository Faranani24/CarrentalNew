import { createRouter, createWebHistory } from 'vue-router';
import HomePage from '@/views/HomePage.vue';
import CarDetailsPage from '@/views/CarDetailsPage.vue';
import BookingPage from '@/views/BookingPage.vue';
import ConfirmationPage from '@/views/ConfirmationPage.vue';
import SignupPage from '@/views/SignupPage.vue'; // Import the new components
import LoginPage from '@/views/LoginPage.vue';   // Import the new components

const routes = [
  { path: '/', name: 'home', component: HomePage },
  { path: '/cars/:id', name: 'carDetails', component: CarDetailsPage },
  { path: '/booking/:id', name: 'booking', component: BookingPage },
  { path: '/confirmation/:bookingId', name: 'confirmation', component: ConfirmationPage },
  { path: '/signup', name: 'signup', component: SignupPage }, // Add the new signup route
  { path: '/login', name: 'login', component: LoginPage },     // Add the new login route
];

export default createRouter({
  history: createWebHistory(),
  routes,
});