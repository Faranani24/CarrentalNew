import { createRouter, createWebHistory } from 'vue-router'
import HomePage from '@/views/HomePage.vue'
import { AuthService } from '@/services/auth.js'

const authService = new AuthService()

const routes = [
  { path: '/', name: 'home', component: HomePage },
  { path: '/cars', name: 'cars', component: () => import('@/components/CarList.vue') },
  { path: '/admin', name: 'admin', component: () => import('@/views/AdminPanel.vue') },
  // { path: '/booking', name: 'booking', component: () => import('@/views/BookingPage.vue') },
  { path: '/login', name: 'login', component: () => import('@/views/LoginPage.vue') },
  { path: '/signup', name: 'signup', component: () => import('@/views/SignUpPage.vue') },
  { path: '/car-details/:id', name: 'carDetails', component: () => import('@/components/CarDetailsPage.vue') },
]


const router = createRouter({
  history: createWebHistory(),
  routes,
})


router.beforeEach((to, from, next) => {
  const requiresAuth = to.matched.some(record => record.meta.requiresAuth);
  const isAuthenticated = authService.isAuthenticated();

  if (requiresAuth && !isAuthenticated) {
    next('/login');
  } else {
    next();
  }
});

export default router