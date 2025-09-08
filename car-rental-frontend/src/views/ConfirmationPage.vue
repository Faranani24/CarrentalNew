<script setup>
import { computed } from 'vue';
import { useRoute, useRouter } from 'vue-router';

const route = useRoute();
const router = useRouter();

const bookingId = computed(() => route.params.bookingId);
const totalCost = computed(() => parseFloat(route.query.totalCost) || 0);
const carDetails = computed(() => route.query.carDetails || 'Vehicle');

const formatRate = (val) => {
  if (val == null) return '';
  return new Intl.NumberFormat('en-ZA', { style: 'currency', currency: 'ZAR' }).format(val);
};

const navigateToHome = () => {
  router.push({ name: 'home' });
};
</script>


<template>
  <div class="min-h-screen flex flex-col items-center justify-center bg-gradient-to-b from-amber-50 via-white to-neutral-100 text-neutral-900">
    <div class="backdrop-blur-xl/30 bg-white/90 border border-amber-200/70 shadow-xl rounded-2xl p-8 text-center max-w-md w-full animate-fade-in">
      <div class="flex items-center justify-center mb-4">
        <div class="h-16 w-16 rounded-full bg-green-500 flex items-center justify-center">
          <svg class="w-8 h-8 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"></path>
          </svg>
        </div>
      </div>
      <h2 class="text-3xl font-bold mb-2 gradient-text-light">Booking Confirmed!</h2>
      <p class="text-neutral-600 mb-6">Thank you, your car booking has been successfully processed.</p>

      <div class="bg-amber-50 border border-amber-200 rounded-lg p-4 mb-6 text-left">
        <div class="flex justify-between items-center text-sm mb-2">
          <span class="text-neutral-600">Booking ID:</span>
          <span class="font-mono font-medium text-orange-600">{{ bookingId }}</span>
        </div>
        <div class="flex justify-between items-center text-sm mb-2">
          <span class="text-neutral-600">Vehicle:</span>
          <span class="font-medium">{{ carDetails }}</span>
        </div>
        <div class="flex justify-between items-center text-sm">
          <span class="text-neutral-600">Total Cost:</span>
          <span class="font-medium">{{ formatRate(totalCost) }}</span>
        </div>
      </div>

      <button @click="navigateToHome" class="w-full px-6 py-3 rounded-lg font-semibold tracking-wide bg-gradient-to-r from-amber-500 via-orange-500 to-rose-400 text-white shadow-lg hover:scale-[1.01] active:scale-[0.98] transition">
        Back to Homepage
      </button>
    </div>
  </div>
</template>

<style scoped>
.gradient-text-light {
  background: linear-gradient(90deg, #f59e0b, #fb923c, #f97316, #fbbf24);
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
.animate-fade-in { animation: fadeIn 0.8s ease-in-out both; }
@keyframes fadeIn { from { opacity:0; transform:translateY(20px); } to { opacity:1; transform:translateY(0); } }
</style>

After replacing the contents of your `ConfirmationPage.vue` with this new code, the page will now correctly display the confirmation message and booking details after a successful payment.