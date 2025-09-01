<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();
const promotions = ref([]);
const loading = ref(true);
const error = ref(null);

async function fetchAllPromotions() {
  loading.value = true;
  error.value = null;
  try {
    await new Promise(resolve => setTimeout(resolve, 1000));
    const mockData = [
      { id: 'p01', title: 'Summer Travel Deal', description: '25% off all SUV rentals for a limited time!', discountPercentage: 25 },
      { id: 'p02', title: 'Winter Getaway', description: 'Book a full-size sedan and get a 15% discount.', discountPercentage: 15 },
      { id: 'p03', title: 'First-Time Renter', description: 'Get 10% off your first car rental with us.', discountPercentage: 10 },
      { id: 'p04', title: 'Local Explorer', description: 'Unlock 20% off when you rent a car for 5 days or more.', discountPercentage: 20 },
    ];
    promotions.value = mockData;
  } catch (err) {
    error.value = 'Failed to load promotions. Please try again later.';
    console.error('Error fetching promotions:', err);
  } finally {
    loading.value = false;
  }
}

onMounted(() => {
  fetchAllPromotions();
});
</script>

<template>
  <div class="min-h-screen flex flex-col bg-gradient-to-b from-amber-50 via-white to-neutral-100 text-neutral-900">
    <nav class="relative z-30 backdrop-blur-md/40 bg-white/70 border-b border-amber-200/60 shadow-sm">
      <div class="mx-auto max-w-6xl px-6 py-4 flex items-center justify-between">
        <div class="flex items-center gap-3">
          <router-link to="/">
            <div class="h-10 w-10 rounded-xl bg-gradient-to-br from-amber-400 to-orange-500 shadow-lg flex items-center justify-center font-black text-gray-900 tracking-tighter" aria-label="CarRental logo">
              CR
            </div>
          </router-link>
          <h1 class="text-2xl font-bold tracking-tight">
            <span class="bg-gradient-to-r from-amber-500 via-orange-500 to-rose-400 bg-clip-text text-transparent drop-shadow-sm">CarRental</span>
          </h1>
        </div>
      </div>
    </nav>
    <main class="relative z-10 flex-1 w-full py-12">
      <div class="mx-auto max-w-4xl px-6">
        <h2 class="text-3xl md:text-4xl font-extrabold tracking-tight text-center mb-10">
          <span class="gradient-text-light">Current Promotions</span>
        </h2>
        <div v-if="loading" class="text-center text-neutral-500">
          Loading promotions...
        </div>
        <p v-if="error" class="text-center text-rose-600 font-medium my-8">
          {{ error }}
        </p>
        <div v-if="!loading && promotions.length" class="space-y-6">
          <div v-for="promo in promotions" :key="promo.id"
               class="backdrop-blur-xl/30 bg-white/90 border border-amber-200/70 shadow-xl rounded-2xl p-6 hover:shadow-lg transition">
            <h3 class="text-2xl font-bold mb-2 text-amber-700">{{ promo.title }}</h3>
            <p class="text-neutral-600 mb-4">{{ promo.description }}</p>
            <div class="flex justify-between items-center border-t border-amber-200 pt-4 mt-4">
              <span class="text-lg font-bold">Discount:</span>
              <span class="text-2xl font-bold text-green-600">
                -{{ promo.discountPercentage }}%
              </span>
            </div>
          </div>
        </div>
        <p v-if="!loading && !promotions.length" class="text-center text-neutral-500 my-8">
          No promotions available at this time.
        </p>
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
.gradient-text-light {
  background: linear-gradient(90deg,#f59e0b,#fb923c,#f97316,#fbbf24);
  -webkit-background-clip: text;
  color: transparent;
  background-size: 300% 100%;
  animation: gradientShift 8s ease infinite;
}
@keyframes gradientShift { 0% { background-position: 0% 50%; } 50% { background-position: 100% 50%; } 100% { background-position: 0% 50%; } }
</style>