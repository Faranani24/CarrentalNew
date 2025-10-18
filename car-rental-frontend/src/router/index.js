import { createRouter, createWebHistory } from 'vue-router';
import HomePage from '@/views/HomePage.vue';
import AdminPanel from '@/views/AdminPanel.vue';
import LoginPage from '@/views/LoginPage.vue';
import SignupPage from '@/views/SignupPage.vue';
import BookingPage from '@/views/BookingPage.vue';
import MyBookingsPage from '@/views/MyBookingsPage.vue';
import AuthService from '@/services/auth.js';

const routes = [
    { path: '/', name: 'home', component: HomePage },
    { path: '/admin', name: 'admin', component: AdminPanel, meta: { requiresAdmin: true }},
    { path: '/login', name: 'login', component: LoginPage },
    { path: '/signup', name: 'signup', component: SignupPage },

    // My Bookings - view all user bookings
    {
        path: '/bookings',
        name: 'my-bookings',
        component: () => import('@/views/MyBookingsPage.vue'),
        meta: { requiresAuth: true }
    },

    // Create new booking for a specific car
    { path: '/booking/:carId', name: 'booking', component: BookingPage, props: true, meta: { requiresAuth: true } },

    { path: '/cars', redirect: '/' },
    { path: '/:catchAll(.*)', redirect: '/' }
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

router.beforeEach((to, from, next) => {
    const currentUser = AuthService.getCurrentUser();

    // Check if route requires authentication
    if (to.matched.some(record => record.meta.requiresAuth)) {
        if (!currentUser) {
            next({
                name: 'login',
                query: { redirect: to.fullPath }
            });
            return;
        }
    }

    // Check if route requires admin privileges
    if (to.matched.some(record => record.meta.requiresAdmin)) {
        if (!currentUser) {
            next({
                name: 'login',
                query: { redirect: to.fullPath }
            });
        } else if (currentUser.role !== 'ADMIN') {
            alert('Access denied. Admin privileges required.');
            next({ name: 'home' });
        } else {
            next();
        }
    } else {
        next();
    }
});

export default router;