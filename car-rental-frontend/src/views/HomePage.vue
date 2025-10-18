<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import { fetchAvailableCars } from '@/services/carService'
import AuthService from '@/services/auth.js'

const cars = ref([])
const loading = ref(false)
const error = ref(null)
const router = useRouter()
const authService = AuthService

const currentUser = ref(null)
const isAuthenticated = computed(() => currentUser.value !== null)

const startDate = ref('')
const endDate = ref('')
const filtered = ref(false)

const initAuth = () => {
  currentUser.value = authService.getCurrentUser()
  console.log('HomePage - Current user:', currentUser.value)
  console.log('HomePage - Is authenticated:', isAuthenticated.value)
}

const filterCars = async () => {
  if (!startDate.value || !endDate.value) {
    alert('Please select both start and end dates.')
    return
  }

  filtered.value = true
  loading.value = true
  error.value = null

  try {
    const fetched = await fetchAvailableCars(startDate.value, endDate.value)
    cars.value = fetched.map(car => ({
      ...car,
      imageUrl: car.image
          ? `data:image/jpeg;base64,${arrayBufferToBase64(car.image)}`
          : `${import.meta.env.VITE_API_URL || 'http://localhost:8082/api/cars'}/${car.carId}/image`
    }))
  } catch (e) {
    console.error(e)
    error.value = 'Failed to fetch cars for the selected dates.'
  } finally {
    loading.value = false
  }
}

const goToBooking = (carId) => {
  console.log('Booking car:', carId, 'Authenticated:', isAuthenticated.value)
  router.push({
    name: 'booking',
    params: { carId },
    query: {
      from: startDate.value,
      to: endDate.value
    }
  })
}

const handleLogout = () => {
  authService.logout()
  currentUser.value = null
  console.log('User logged out')
}

onMounted(() => {
  initAuth()
})

function formatRate(val) {
  if (val == null) return ''
  return new Intl.NumberFormat('en-ZA', { style: 'currency', currency: 'ZAR' }).format(val)
}

function arrayBufferToBase64(buffer) {
  if (!buffer) return ''
  if (typeof buffer === 'string') return buffer
  if (buffer.data && Array.isArray(buffer.data)) buffer = Uint8Array.from(buffer.data).buffer
  let binary = ''
  const bytes = new Uint8Array(buffer)
  for (let i = 0; i < bytes.byteLength; i++) binary += String.fromCharCode(bytes[i])
  return window.btoa(binary)
}
</script>

<template>
  <div class="min-h-screen flex flex-col bg-gradient-to-b from-amber-50 via-white to-neutral-100 text-neutral-900">
    <!-- HERO / SEARCH -->
    <section class="relative flex items-center min-h-[70vh] overflow-hidden">
      <div class="absolute inset-0">
        <img
            src="https://images.unsplash.com/photo-1503376780353-7e6692767b70?auto=format&fit=crop&w=1650&q=60"
            class="w-full h-full object-cover animate-pan opacity-50"
            alt="hero"
        />
        <div class="absolute inset-0 bg-gradient-to-b from-white/85 via-white/70 to-amber-100/60"></div>
      </div>

      <div class="relative z-10 w-full mx-auto max-w-5xl px-6 text-center">
        <h1 class="text-4xl md:text-6xl font-extrabold tracking-tight leading-tight mb-4 gradient-text-light drop-shadow-lg animate-fade-in">
          Find Your Perfect Ride
        </h1>

        <!-- Search Card -->
        <div class="inline-block backdrop-blur-md bg-white/70 rounded-xl p-4 shadow-lg border border-white/30 animate-fade-in">
          <div class="flex flex-col md:flex-row items-center gap-4">
            <input type="date" v-model="startDate" class="px-4 py-2 rounded-md border border-neutral-300 focus:outline-none focus:ring-2 focus:ring-amber-500 transition">
            <input type="date" v-model="endDate" class="px-4 py-2 rounded-md border border-neutral-300 focus:outline-none focus:ring-2 focus:ring-amber-500 transition">
            <button @click="filterCars" class="px-6 py-2 rounded-lg font-semibold bg-gradient-to-r from-amber-400 to-orange-500 text-gray-900 shadow-md hover:scale-[1.01] active:scale-[0.98] transition">
              Search
            </button>
          </div>
        </div>
      </div>
    </section>

    <!-- AVAILABLE CARS -->
    <main class="relative z-10 flex-1 w-full py-12">
      <div class="mx-auto max-w-6xl px-6">
        <section class="mt-16">
          <h2 class="text-3xl font-bold tracking-tight mb-4">Available Cars</h2>

          <div v-if="!filtered" class="text-center text-neutral-500">
            Please select dates and click "Search" to see available cars.
          </div>

          <div v-else-if="loading" class="text-center text-neutral-500">
            Loading cars...
          </div>

          <div v-else-if="error" class="text-center text-rose-600">{{ error }}</div>

          <div v-else-if="cars.length === 0" class="text-center text-neutral-500">
            No cars available for the selected dates.
          </div>

          <div v-else class="grid gap-6 sm:grid-cols-2 lg:grid-cols-3 justify-center">
            <div
                v-for="car in cars"
                :key="car.carId"
                class="group relative rounded-xl overflow-hidden border border-amber-200 bg-white shadow-sm hover:shadow-lg transition flex flex-col animate-fade-in"
            >
              <div class="relative h-48 overflow-hidden">
                <img
                    :src="car.imageUrl || 'https://via.placeholder.com/400x200?text=No+Image'"
                    :alt="`${car.make} ${car.model}`"
                    class="w-full h-full object-cover scale-110 group-hover:scale-100 transition-transform duration-700 ease-out"
                />
                <div class="absolute inset-0 bg-gradient-to-b from-black/10 via-black/0 to-black/50"></div>
                <div class="absolute top-3 left-3 px-3 py-1 rounded-full text-xs font-semibold tracking-wide bg-green-300 backdrop-blur border border-amber-200 uppercase text-neutral-700">
                  {{ car.status.toUpperCase() }}
                </div>
              </div>

              <div class="p-5 flex flex-col flex-1">
                <h3 class="font-semibold text-lg tracking-tight mb-1 text-neutral-900">
                  {{ car.make }} {{ car.model }} ({{ car.year }})
                </h3>

                <!-- Description with ellipsis for long text -->
                <p class="text-xs text-neutral-500 mb-4 line-clamp-2 min-h-[2.5rem]">
                  {{ car.description || 'No description available' }}
                </p>

                <div class="mt-auto mb-4">
                  <p class="mb-4 text-2xl font-bold bg-gradient-to-r from-amber-500 via-orange-500 to-rose-500 bg-clip-text text-transparent">
                    {{ formatRate(car.dailyRate) }}
                  </p>

                  <!-- Show Book Now button if authenticated, otherwise show Login prompt -->
                  <button
                      v-if="isAuthenticated"
                      @click="goToBooking(car.carId)"
                      class="w-full text-white bg-orange-500 px-4 py-2 rounded-lg font-semibold hover:bg-orange-600 transition"
                  >
                    Book Now →
                  </button>
                  <router-link
                      v-else
                      to="/login"
                      class="block w-full text-center text-gray-800 bg-gray-200 px-4 py-2 rounded-lg font-semibold hover:bg-gray-300 transition"
                  >
                    Login to Book →
                  </router-link>
                </div>
              </div>
            </div>
          </div>

        </section>
      </div>
    </main>
  </div>
</template>

<style scoped>
.hero {
  min-height: 72vh;
}

.hero-title-gradient {
  background: linear-gradient(90deg, #fde68a, #fbbf24, #fb923c, #f59e0b, #fbbf24);
  -webkit-background-clip: text;
  color: transparent;
  position: relative;
}

.shine {
  background-size: 200% 100%;
  animation: shine 8s linear infinite;
}

@keyframes shine {
  0% { background-position: 0% 50%; }
  100% { background-position: -200% 50%; }
}

.animate-pan {
  animation: pan 40s linear infinite;
}

@keyframes pan {
  0% { transform: scale(1.15) translate(0, 0); }
  50% { transform: scale(1.18) translate(-2%, -2%); }
  100% { transform: scale(1.15) translate(0, 0); }
}

.animated-grid {
  background: linear-gradient(rgba(255, 255, 255, 0.05) 1px, transparent 1px),
  linear-gradient(90deg, rgba(255, 255, 255, 0.05) 1px, transparent 1px);
  background-size: 40px 40px;
  mask: linear-gradient(to bottom, transparent, black 30%, black 70%, transparent);
  animation: grid-move 25s linear infinite;
}

@keyframes grid-move {
  0% { background-position: 0 0, 0 0; }
  100% { background-position: 0 40px, 40px 0; }
}

.card-enter-from,
.card-leave-to {
  opacity: 0;
  transform: translateY(18px) scale(0.96);
}

.card-enter-active {
  transition: all 0.6s cubic-bezier(0.16, 0.8, 0.43, 1.01);
}

.card-leave-active {
  transition: all 0.35s ease;
}

.card-enter-to {
  opacity: 1;
  transform: translateY(0) scale(1);
}

.animate-fade-down {
  animation: fadeDown 0.9s cubic-bezier(0.16, 0.8, 0.43, 1) both;
}

.animate-fade-up {
  animation: fadeUp 1s cubic-bezier(0.16, 0.8, 0.43, 1) both;
}

@keyframes fadeDown {
  0% { opacity: 0; transform: translateY(-24px) scale(0.97); }
  100% { opacity: 1; transform: translateY(0) scale(1); }
}

@keyframes fadeUp {
  0% { opacity: 0; transform: translateY(30px) scale(0.96); }
  100% { opacity: 1; transform: translateY(0) scale(1); }
}

.animate-pop {
  animation: pop 0.8s 0.25s cubic-bezier(0.34, 1.56, 0.64, 1) both;
}

@keyframes pop {
  0% { opacity: 0; transform: scale(0.4) rotate(-8deg); }
  100% { opacity: 1; transform: scale(1) rotate(0); }
}

.animate-shake {
  animation: shake 0.5s ease;
}

@keyframes shake {
  0%, 100% { transform: translateX(0); }
  25% { transform: translateX(-5px); }
  50% { transform: translateX(5px); }
  75% { transform: translateX(-3px); }
}

.loader.spinner {
  border: 3px solid rgba(255, 255, 255, 0.2);
  border-top-color: #fbbf24;
  border-radius: 50%;
  width: 1rem;
  height: 1rem;
  animation: spin 0.8s linear infinite;
}

@keyframes spin {
  to { transform: rotate(360deg); }
}

.gradient-text-light {
  background: linear-gradient(90deg, #fbbf24, #fb923c, #f59e0b, #fbbf24);
  -webkit-background-clip: text;
  color: transparent;
  animation: hue 10s linear infinite;
  background-size: 300% 100%;
  animation: gradientShift 8s ease infinite;
}

@keyframes gradientShift {
  0% { background-position: 0% 50%; }
  50% { background-position: 100% 50%; }
  100% { background-position: 0% 50%; }
}

.car-card::after {
  content: '';
  position: absolute;
  inset: 0;
  background: radial-gradient(circle at 30% 20%, rgba(255, 255, 255, 0.15), transparent 60%);
  opacity: 0;
  transition: opacity 0.5s;
  pointer-events: none;
}

.car-card:hover::after {
  opacity: 1;
}

.car-card {
  animation: cardAppear 0.9s cubic-bezier(0.16, 0.8, 0.43, 1) both;
}

@keyframes cardAppear {
  0% { opacity: 0; transform: translateY(24px) scale(0.95); }
  100% { opacity: 1; transform: translateY(0) scale(1); }
}

.car-card:nth-child(1) { animation-delay: 0.05s; }
.car-card:nth-child(2) { animation-delay: 0.1s; }
.car-card:nth-child(3) { animation-delay: 0.15s; }
.car-card:nth-child(4) { animation-delay: 0.2s; }
.car-card:nth-child(5) { animation-delay: 0.25s; }
.car-card:nth-child(6) { animation-delay: 0.3s; }

/* Line clamp utility for description truncation */
.line-clamp-2 {
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
  text-overflow: ellipsis;
}

@media (max-width: 640px) {
  .hero { min-height: 68vh; }
}
</style>