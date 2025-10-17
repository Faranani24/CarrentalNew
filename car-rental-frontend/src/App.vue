<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRouter, RouterLink, RouterView } from 'vue-router'
import AuthService from './services/auth.js'

const authService = AuthService
const currentUser = ref(null)
const isAuthenticated = computed(() => {
  console.log('[App.vue] isAuthenticated check:', currentUser.value !== null)
  return currentUser.value !== null
})
const isAdmin = computed(() => {
  const result = currentUser.value && currentUser.value.role === 'ADMIN'
  console.log('[App.vue] isAdmin check:', {
    user: currentUser.value,
    role: currentUser.value?.role,
    isAdmin: result
  })
  return result
})
const router = useRouter()

const initAuth = () => {
  // Get the current user from localStorage
  const user = authService.getCurrentUser()
  currentUser.value = user
  console.log('[App.vue] initAuth - User loaded:', user)
  console.log('[App.vue] initAuth - User role:', user?.role)
  console.log('[App.vue] initAuth - isAdmin computed:', isAdmin.value)
}

const handleLogout = () => {
  authService.logout()
  currentUser.value = null
  console.log('User logged out')
  router.push('/login')
}

// Initialize auth on mount
onMounted(() => {
  initAuth()
})

// Watch for storage changes (in case user logs in/out in another tab)
if (typeof window !== 'undefined') {
  window.addEventListener('storage', (e) => {
    if (e.key === 'user') {
      initAuth()
    }
  })
}
</script>

<template>
  <div class="min-h-screen flex flex-col bg-gradient-to-b from-amber-50 via-white to-neutral-100 text-neutral-900">
    <!-- NAV -->
    <nav class="relative z-30 backdrop-blur-md/40 bg-white/70 border-b border-amber-200/60 shadow-sm">
      <div class="mx-auto max-w-6xl px-6 py-4 flex items-center justify-between">
        <router-link to="/">
          <div class="flex items-center gap-3">
            <div class="h-10 w-10 rounded-xl bg-gradient-to-br from-amber-400 to-orange-500 shadow-lg flex items-center justify-center font-black text-gray-900 tracking-tighter" aria-label="CarRental logo">
              CR
            </div>
            <h1 class="text-2xl font-bold tracking-tight">
              <span class="bg-gradient-to-r from-amber-500 via-orange-500 to-rose-400 bg-clip-text text-transparent drop-shadow-sm">CarRental</span>
            </h1>
          </div>
        </router-link>

        <!-- Navigation Menu -->
        <div class="flex items-center gap-4">
          <template v-if="isAuthenticated">
            <!-- Authenticated Navigation -->
            <span class="text-neutral-700 text-sm">
              Welcome, {{ currentUser.firstName || currentUser.username || 'User' }}!
            </span>
            <router-link to="/cars" class="text-neutral-900 font-semibold hover:text-orange-500 transition">
              Browse Cars
            </router-link>
            <router-link v-if="isAdmin" to="/admin" class="text-neutral-900 font-semibold hover:text-orange-500 transition">
              Admin Panel
            </router-link>
            <router-link to="/booking" class="text-neutral-900 font-semibold hover:text-orange-500 transition">
              My Bookings
            </router-link>
            <button @click="handleLogout" class="px-4 py-2 rounded-lg font-semibold tracking-wide bg-gradient-to-r from-red-400 to-red-500 text-white shadow-lg hover:scale-[1.01] active:scale-[0.98] transition">
              Logout
            </button>
          </template>

          <template v-else>
            <!-- Guest Navigation -->
            <router-link to="/login" class="text-neutral-900 font-semibold hover:text-orange-500 transition">Login</router-link>
            <router-link to="/signup" class="px-4 py-2 rounded-lg font-semibold tracking-wide bg-gradient-to-r from-amber-400 to-orange-500 text-gray-900 shadow-lg hover:scale-[1.01] active:scale-[0.98] transition">Sign Up</router-link>
          </template>
        </div>
      </div>
    </nav>
    <main class="flex-1">
      <router-view @login="initAuth" />
    </main>
    <!-- FOOTER -->
    <footer class="relative z-10 border-t border-amber-200/70 bg-white/80 backdrop-blur text-center py-6 text-sm text-neutral-600">
      <div class="max-w-6xl mx-auto px-6 flex flex-col md:flex-row items-center justify-between gap-4">
        <span>© 2025 CarRental</span>
        <span class="text-xs tracking-wide uppercase opacity-70">Crafted with Vue 3 & Vite</span>
      </div>
    </footer>
  </div>
</template>

<style>
/* You may need to define these styles elsewhere */
.animate-fade-in { animation: fadeIn 0.8s ease-in-out both; }
@keyframes fadeIn { from { opacity:0; transform:translateY(20px); } to { opacity:1; transform:translateY(0); } }

.animate-pan { animation: pan 40s linear infinite; }
@keyframes pan { 0%{transform:scale(1.15) translate(0,0);} 50%{transform:scale(1.18) translate(-2%,-2%);} 100%{transform:scale(1.15) translate(0,0);} }

.animated-grid {
  background: linear-gradient(rgba(255,180,60,0.15) 1px, transparent 1px),
  linear-gradient(90deg, rgba(255,180,60,0.15) 1px, transparent 1px);
  background-size: 40px 40px;
  mask: linear-gradient(to bottom, transparent, black 25%, black 75%, transparent);
  animation: grid-move 25s linear infinite;
}
@keyframes grid-move { 0%{background-position:0 0,0 0;} 100%{background-position:0 40px,40px 0;} }

.gradient-text-light {
  background: linear-gradient(90deg,#f59e0b,#fb923c,#f97316,#fbbf24);
  -webkit-background-clip: text; color: transparent;
  background-size: 300% 100%; animation: gradientShift 8s ease infinite;
}
@keyframes gradientShift {
  0%{background-position:0% 50%} 50%{background-position:100% 50%} 100%{background-position:0% 50%}
}

.card-view-details {
  display:block; width:100%; text-align:center;
  padding:0.75rem 1.5rem; border-radius:0.75rem;
  border:2px solid #fbbf24;
  background:linear-gradient(90deg,#fbbf24 0%,#fde68a 50%,#fb923c 100%);
  color:#1a202c; font-weight:600;
  transition: transform 0.1s, box-shadow 0.2s, border-color 0.2s;
  text-decoration: none;
}
.card-view-details:hover { transform:scale(1.03); border-color:#fb923c; box-shadow:0 4px 16px rgba(251,146,60,0.18);}

.card-view-details-guest {
  display:block; width:100%; text-align:center;
  padding:0.75rem 1.5rem; border-radius:0.75rem;
  border:2px solid #6b7280;
  background:linear-gradient(90deg,#6b7280 0%,#9ca3af 50%,#6b7280 100%);
  color:white; font-weight:600;
  transition: transform 0.1s, box-shadow 0.2s, border-color 0.2s;
  text-decoration: none;
}

.card-view-details-guest:hover { transform:scale(1.03); border-color:#374151; box-shadow:0 4px 16px rgba(107,114,128,0.18);}
</style>