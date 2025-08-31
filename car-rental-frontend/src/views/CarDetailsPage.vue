<script setup>
import { ref, onMounted, computed } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { fetchCarById } from '@/services/carService';
import { fetchReviewsByCarId} from '@/services/reviewService.js';

const route = useRoute();
const router = useRouter();

const car = ref(null);
const loading = ref(true);
const error = ref('');
const reviews = ref([]);
const reviewsLoading = ref(true);

const averageRating = computed(() => {
  if (reviews.value.length === 0) return 0;
  const total = reviews.value.reduce((sum, r) => sum + r.rating, 0);
  return (total / reviews.value.length).toFixed(1);
});

async function loadCarAndReviews() {
  loading.value = true;
  reviewsLoading.value = true;
  error.value = '';
  try {
    const carId = route.params.id;
    if (!carId) {
      router.push({ name: 'home' });
      return;
    }
    car.value = await fetchCarById(carId);

    reviews.value = await fetchReviewsByCarId(carId);
  } catch (e) {
    error.value = e.message || 'Failed to load car details.';
    console.error('[CarDetailsPage] load failed:', e);
  } finally {
    loading.value = false;
    reviewsLoading.value = false;
  }
}
function formatStars(rating) {
  const fullStars = Math.floor(rating);
  const stars = [];
  for (let i = 0; i < 5; i++) {
    if (i < fullStars) {
      stars.push('<i class="fas fa-star"></i>');
    } else {
      stars.push('<i class="far fa-star"></i>');
    }
  }
  return stars.join('');
}
onMounted(async () => {
  const carId = route.params.id;
  if (!carId) {
    router.push({ name: 'home' });
    return;
  }

  loading.value = true;
  error.value = '';
  try {
    car.value = await fetchCarById(carId);
  } catch (e) {
    error.value = e.message || 'Failed to load car details.';
    console.error('[CarDetailsPage] load failed:', e);
  } finally {
    loading.value = false;
  }
});

function formatRate(val) {
  if (val == null) return '';
  return new Intl.NumberFormat('en-ZA', { style: 'currency', currency: 'ZAR' }).format(val);
}
</script>

<template>
  <div class="min-h-screen flex flex-col bg-gradient-to-b from-amber-50 via-white to-neutral-100 text-neutral-900">
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
      </div>
    </nav>

    <main class="relative z-10 flex-1 w-full py-12">
      <div class="mx-auto max-w-4xl px-6">
        <div v-if="loading" class="text-center text-neutral-500">
          Loading car details...
        </div>
        <div v-else-if="error" class="text-center text-rose-600">
          {{ error }}
        </div>
        <div v-else-if="car" class="backdrop-blur-xl/30 bg-white/90 border border-amber-200/70 shadow-xl rounded-2xl p-8 md:p-12 animate-fade-in">
          <div class="grid md:grid-cols-2 gap-8 md:gap-12">
            <div>
              <img :src="car.imageUrl" :alt="`${car.make} ${car.model}`" class="rounded-xl object-cover w-full h-auto shadow-md">
            </div>
            <div class="space-y-4">
              <h2 class="text-3xl md:text-4xl font-extrabold tracking-tight leading-tight gradient-text-light">
                {{ car.make }} {{ car.model }} ({{ car.year }})
              </h2>
              <p class="text-lg text-neutral-600">{{ car.description }}</p>
              <div class="flex items-center gap-2 text-neutral-700 font-semibold">
                <span class="text-3xl font-bold bg-gradient-to-r from-amber-500 via-orange-500 to-rose-500 bg-clip-text text-transparent">
                  {{ formatRate(car.dailyRate) }}
                </span>
                <span class="text-sm font-medium text-neutral-500">/ day</span>
              </div>
              <div>
                <h3 class="font-semibold text-neutral-700 mb-2">Key Features:</h3>
                <ul class="list-disc list-inside space-y-1 text-neutral-600">
                  <li v-for="feature in car.features" :key="feature">{{ feature }}</li>
                </ul>
              </div>
              <div class="pt-4">
                <router-link :to="{ name: 'booking', params: { id: car.id, location: 'City or airport', from: '2025-01-01', to: '2025-01-03' } }"
                             class="group w-full relative overflow-hidden px-6 py-3 rounded-lg font-semibold tracking-wide bg-gradient-to-r from-amber-400 via-yellow-400 to-orange-500 text-gray-900 shadow-lg hover:scale-[1.01] active:scale-[0.98] transition flex justify-center items-center">
                  <span class="relative z-10">Proceed to Booking</span>
                </router-link>
              </div>
            </div>
          </div>
        </div>
      </div>
    </main>

    <footer class="relative z-10 border-t border-amber-200/70 bg-white/80 backdrop-blur text-center py-6 text-sm text-neutral-600">
      <div class="max-w-6xl mx-auto px-6 flex flex-col md:flex-row items-center justify-between gap-4">
        <span>© 2025 CarRental</span>
        <span class="text-xs tracking-wide uppercase opacity-70">Crafted with Vue 3 & Vite</span>
      </div>
    </footer>
  </div>
</template>

<style scoped>
.animate-fade-in { animation: fadeIn 0.8s ease-in-out both; }
@keyframes fadeIn {
  from { opacity: 0; transform: translateY(20px); }
  to { opacity: 1; transform: translateY(0); }
}

.gradient-text-light {
  background: linear-gradient(90deg,#f59e0b,#fb923c,#f97316,#fbbf24);
  -webkit-background-clip: text;
  color: transparent;
  background-size: 300% 100%;
  animation: gradientShift 8s ease infinite;
}
@keyframes gradientShift {
  0% { background-position: 0% 50%; }
  50% { background-position: 100% 50%; }
  100% { background-position: 0% 50%; }
}
</style>