<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import { fetchCars } from '@/services/carService.js'
import { fetchReviewsByCarId } from '@/services/reviewService.js'
import { formatDate } from '@/utils/format.js'
import { AuthService } from '@/services/auth.js'

const cars = ref([])
const loading = ref(true)
const error = ref(null)
const router = useRouter()
const authService = new AuthService()

const currentUser = ref(null)
const isAuthenticated = computed(() => currentUser.value && currentUser.value.isAuthenticated)

const initAuth = () => {
  currentUser.value = authService.getCurrentUser()
}

const fetchAllCars = async () => {
  loading.value = true
  error.value = null
  try {
    const fetchedCars = await fetchCars()
    cars.value = fetchedCars

    const carsWithReviews = []
    for (const car of fetchedCars) {
      // Note: fetchReviewsByCarId is not implemented yet, so we will skip it for now.
      // When you implement it, you can uncomment this part.
      // const reviews = await fetchReviewsByCarId(car.id)
      // const totalRating = reviews.reduce((sum, r) => sum + r.rating, 0)
      // const averageRating = reviews.length > 0 ? (totalRating / reviews.length).toFixed(1) : 0
      // carsWithReviews.push({ ...car, averageRating, reviewCount: reviews.length })

      // Temporary
      carsWithReviews.push({ ...car, averageRating: 0, reviewCount: 0 })
    }
    cars.value = carsWithReviews
  } catch (e) {
    error.value = 'Failed to load cars.'
    console.error('Error fetching cars:', e)
  } finally {
    loading.value = false
  }
}

function formatStars(rating) {
  const fullStars = Math.floor(rating)
  let starsHtml = ''
  for (let i = 0; i < 5; i++) {
    if (i < fullStars) {
      starsHtml += `<i class="fas fa-star"></i>`
    } else {
      starsHtml += `<i class="far fa-star"></i>`
    }
  }
  return starsHtml
}

const handleLogout = () => {
  authService.logout()
  currentUser.value = null
  console.log('User logged out')
}

onMounted(() => {
  initAuth()
  fetchAllCars()
})

function formatRate(val) {
  if (val == null) return ''
  return new Intl.NumberFormat('en-ZA', { style: 'currency', currency: 'ZAR' }).format(val)
}
</script>

<template>
  <div class="min-h-screen flex flex-col bg-gradient-to-b from-amber-50 via-white to-neutral-100 text-neutral-900">
    <!-- HERO -->
    <section class="relative flex items-center min-h-[70vh] overflow-hidden">
      <!-- Background Image & Effects -->
      <div class="absolute inset-0">
        <img src="https://images.unsplash.com/photo-1503376780353-7e6692767b70?auto=format&fit=crop&w=1650&q=60" class="w-full h-full object-cover animate-pan opacity-50" alt="hero"/>
        <div class="absolute inset-0 bg-gradient-to-b from-white/85 via-white/70 to-amber-100/60"></div>
        <div class="absolute inset-0 mix-blend-overlay bg-[radial-gradient(circle_at_30%_30%,rgba(255,180,60,0.20),transparent_60%)]"></div>
        <div class="absolute inset-0 pointer-events-none animated-grid opacity-40"></div>
      </div>

      <div class="relative z-10 w-full mx-auto max-w-5xl px-6 text-center">
        <h1 class="text-4xl md:text-6xl font-extrabold tracking-tight leading-tight mb-4 gradient-text-light drop-shadow-lg animate-fade-in">
          Find Your Perfect Ride
        </h1>
        <p class="text-lg md:text-xl text-neutral-700 max-w-2xl mx-auto mb-8 drop-shadow-md animate-fade-in">
          Premium vehicles. Transparent pricing. Effortless booking.
        </p>

        <!-- Search Card -->
        <div class="inline-block backdrop-blur-md bg-white/70 rounded-xl p-4 shadow-lg border border-white/30 animate-fade-in">
          <div class="flex flex-col md:flex-row items-center gap-4">
            <input type="text" placeholder="City or airport" class="px-4 py-2 rounded-md border border-neutral-300 focus:outline-none focus:ring-2 focus:ring-amber-500 transition">
            <input type="date" class="px-4 py-2 rounded-md border border-neutral-300 focus:outline-none focus:ring-2 focus:ring-amber-500 transition">
            <input type="date" class="px-4 py-2 rounded-md border border-neutral-300 focus:outline-none focus:ring-2 focus:ring-amber-500 transition">
            <button class="px-6 py-2 rounded-lg font-semibold bg-gradient-to-r from-amber-400 to-orange-500 text-gray-900 shadow-md hover:scale-[1.01] active:scale-[0.98] transition">Search</button>
          </div>
        </div>
      </div>
    </section>

    <!-- AVAILABLE CARS -->
    <main class="relative z-10 flex-1 w-full py-12">
      <div class="mx-auto max-w-6xl px-6">
        <section class="mt-16">
          <div class="flex flex-col md:flex-row justify-between items-center mb-8">
            <h2 class="text-3xl font-bold tracking-tight mb-4 md:mb-0">Available Cars</h2>
            <button @click="fetchAllCars" class="flex items-center gap-2 px-4 py-2 rounded-lg font-semibold text-white bg-orange-500 hover:bg-orange-600 transition">
              <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="2" stroke="currentColor" class="w-5 h-5">
                <path stroke-linecap="round" stroke-linejoin="round" d="M16.023 9.348h4.992v-.001M2.985 19.644v-4.992m0 0h4.992m-4.993 0l3.181 3.183a8.25 8.25 0 0013.803-3.7M4.031 9.865a8.25 8.25 0 0113.803-3.7l3.181 3.182m0-4.991v4.99" />
              </svg>
              Refresh
            </button>
          </div>

          <div v-if="loading" class="text-center text-neutral-500">Loading cars...</div>
          <div v-else-if="error" class="text-center text-rose-600">{{ error }}</div>
          <div v-else class="grid gap-6 sm:grid-cols-2 lg:grid-cols-3 justify-center">
            <div v-for="car in cars" :key="car.id" class="group relative rounded-xl overflow-hidden border border-amber-200 bg-white shadow-sm hover:shadow-lg transition flex flex-col animate-fade-in">
              <div class="relative h-48 overflow-hidden">
                <img :src="car.imageUrl || 'https://via.placeholder.com/400x200?text=No+Image'" :alt="`${car.make} ${car.model}`" class="w-full h-full object-cover scale-110 group-hover:scale-100 transition-transform duration-700 ease-out"/>
                <div class="absolute inset-0 bg-gradient-to-b from-black/10 via-black/0 to-black/50"></div>
                <div class="absolute top-3 left-3 px-3 py-1 rounded-full text-xs font-semibold tracking-wide bg-green-300 backdrop-blur border border-amber-200 uppercase text-neutral-700">
                  AVAILABLE
                </div>
              </div>
              <div class="p-5 flex flex-col flex-1">
                <h3 class="font-semibold text-lg tracking-tight mb-1 text-neutral-900">{{ car.make }} {{ car.model }} ({{ car.year }})</h3>
                <p class="text-xs text-neutral-500 mb-4">{{ car.description || '' }}</p>
                <div class="mt-auto mb-4">
                  <p class="mb-4 text-2xl font-bold bg-gradient-to-r from-amber-500 via-orange-500 to-rose-500 bg-clip-text text-transparent">
                    {{ formatRate(car.dailyRate) }}
                  </p>

                  <!-- Show different buttons based on auth status -->
                  <template v-if="isAuthenticated">
                    <router-link :to="{ name: 'carDetails', params: { id: car.id } }" class="card-view-details">
                      View Details →
                    </router-link>
                  </template>
                  <template v-else>
                    <router-link to="/login" class="card-view-details-guest">
                      Login to Book →
                    </router-link>
                  </template>
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
