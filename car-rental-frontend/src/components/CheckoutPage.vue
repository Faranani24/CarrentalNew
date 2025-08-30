<template>
  <div class="mx-auto max-w-5xl px-6 w-full">
    <div class="grid grid-cols-1 md:grid-cols-2 gap-8 w-full">
      <div class="space-y-6">
        <div class="p-6 bg-white/90 backdrop-blur-xl/30 rounded-2xl border border-amber-200/70 shadow-xl animate-fade-down">
          <h2 class="text-xl font-bold mb-4 flex items-center gap-2">
            <span class="text-amber-500">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-car"><path d="M19 17h2c.6 0 1-.4 1-1v-3c0-.9-.7-1.7-1.5-1.9L14 6L4 6L2 14v3h2"/><circle cx="7" cy="17" r="2"/><path d="M9 17h6"/><circle cx="17" cy="17" r="2"/></svg>
            </span>
            Car Details
          </h2>
          <div v-if="car" class="flex items-center space-x-4">
            <img :src="car.image" alt="Car" class="h-24 w-24 rounded-xl object-cover border border-amber-200" />
            <div>
              <h3 class="text-lg font-semibold">{{ car.make }} {{ car.model }}</h3>
              <p class="text-neutral-500 text-sm">{{ car.year }}</p>
            </div>
          </div>
          <div class="mt-4 space-y-2 text-sm text-neutral-600">
            <p><strong>Pick-up:</strong> {{ booking.pickupDate }} @ {{ booking.pickupLocation }}</p>
            <p><strong>Drop-off:</strong> {{ booking.dropoffDate }} @ {{ booking.dropoffLocation }}</p>
            <p><strong>Duration:</strong> {{ booking.duration }} days</p>
          </div>
        </div>

        <div class="p-6 bg-white/90 backdrop-blur-xl/30 rounded-2xl border border-amber-200/70 shadow-xl animate-fade-down">
          <h2 class="text-xl font-bold mb-4 flex items-center gap-2">
            <span class="text-amber-500">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-credit-card"><rect x="1" y="4" width="22" height="16" rx="2"/><path d="M1 10h22"/></svg>
            </span>
            Payment Information
          </h2>
          <form class="space-y-4">
            <input v-model="payment.name" type="text" placeholder="Name on card" class="w-full px-4 py-3 rounded-lg bg-white border border-amber-200 focus:border-amber-400 outline-none transition placeholder:text-neutral-400" required />
            <input v-model="payment.cardNumber" type="text" placeholder="Card number" class="w-full px-4 py-3 rounded-lg bg-white border border-amber-200 focus:border-amber-400 outline-none transition placeholder:text-neutral-400" required />
            <div class="grid grid-cols-2 gap-4">
              <input v-model="payment.expiry" type="text" placeholder="MM/YY" class="w-full px-4 py-3 rounded-lg bg-white border border-amber-200 focus:border-amber-400 outline-none transition placeholder:text-neutral-400" required />
              <input v-model="payment.cvv" type="password" placeholder="CVV" class="w-full px-4 py-3 rounded-lg bg-white border border-amber-200 focus:border-amber-400 outline-none transition placeholder:text-neutral-400" required />
            </div>
          </form>
        </div>
      </div>

      <div class="space-y-6">
        <div class="p-6 bg-white/90 backdrop-blur-xl/30 rounded-2xl border border-amber-200/70 shadow-xl animate-fade-down">
          <h2 class="text-xl font-bold mb-4 flex items-center gap-2">
            <span class="text-amber-500">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-receipt-text"><path d="M4 2v20l2-1 2 1 2-1 2 1 2-1 2 1 2-1 2 1V2l-2 1-2-1-2 1-2-1-2 1-2-1-2 1Z"/><path d="M12 8h6"/><path d="M12 12h6"/><path d="M12 16h6"/></svg>
            </span>
            Summary of Charges
          </h2>
          <div class="space-y-3 text-neutral-700">
            <div v-if="car" class="flex justify-between items-center text-sm">
              <span>{{ booking.duration }} days x {{ formatRate(car.dailyRate) }}</span>
              <span>{{ formatRate(booking.duration * car.dailyRate) }}</span>
            </div>
            <div class="flex justify-between items-center text-sm">
              <span>Taxes and fees</span>
              <span>{{ formatRate(taxes) }}</span>
            </div>
            <hr class="border-amber-100/70" />
            <div class="flex justify-between font-bold text-lg pt-2">
              <span>Total Due:</span>
              <span class="bg-gradient-to-r from-amber-500 via-orange-500 to-rose-500 bg-clip-text text-transparent">{{ formatRate(total) }}</span>
            </div>
          </div>
        </div>

        <button @click="submitPayment"
                type="button"
                class="group w-full relative overflow-hidden px-6 py-3 rounded-lg font-semibold tracking-wide bg-gradient-to-r from-amber-400 via-yellow-400 to-orange-500 text-gray-900 shadow-lg hover:scale-[1.02] active:scale-[0.98] transition disabled:opacity-60 disabled:cursor-not-allowed"
                :disabled="!canSubmit">
          <span class="relative z-10 flex items-center justify-center gap-2">
            <span v-if="processing" class="loader spinner size-4" aria-hidden="true"></span>
            {{ processing ? 'Processing...' : 'Book & Pay' }}
          </span>
          <span class="absolute inset-0 opacity-0 group-hover:opacity-100 bg-gradient-to-r from-yellow-200 via-white/60 to-yellow-200 mix-blend-overlay transition"></span>
        </button>
        <p v-if="error" class="text-center text-rose-600 font-medium text-sm mt-2">
          <i class="fas fa-exclamation-circle mr-1"></i> {{ error }}
        </p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { fetchCarById } from '@/services/carService'
import { createPayment } from '@/services/paymentService'

const router = useRouter()
const route = useRoute()
const car = ref(null)
const processing = ref(false)
const error = ref('')

const booking = ref({
  pickupDate: "2025-09-01",
  pickupLocation: "Los Angeles Airport",
  dropoffDate: "2025-09-08",
  dropoffLocation: "San Francisco Downtown",
  duration: 7,
});

const payment = ref({
  name: "",
  cardNumber: "",
  expiry: "",
  cvv: "",
});

const taxes = ref(16.92);

const total = computed(() => {
  return (booking.value.duration * (car.value?.dailyRate || 0)) + taxes.value;
});

const canSubmit = computed(() => {
  return !processing.value &&
      payment.value.name &&
      payment.value.cardNumber &&
      payment.value.expiry &&
      payment.value.cvv;
});

async function fetchCar() {
  const carId = route.params.id;
  try {
    car.value = await fetchCarById(carId);
  } catch (err) {
    console.error("Failed to fetch car details:", err);
    error.value = "Failed to load car details. Please try again.";
  }
}

async function submitPayment() {
  if (!canSubmit.value) return;

  processing.value = true;
  error.value = '';

  const paymentDetails = {
    carId: car.value.id,
    ...booking.value,
    ...payment.value,
    totalAmount: total.value
  };

  try {
    await createPayment(paymentDetails);
    alert("Booking confirmed! You will be redirected to the review page.");
    router.push({ name: 'review', params: { id: car.value.id } });
  } catch (e) {
    error.value = 'Payment failed. Please try again.';
  } finally {
    processing.value = false;
  }
}

function formatRate(val) {
  return Intl.NumberFormat('en-US', { style: 'currency', currency: 'USD' }).format(val);
}

fetchCar();
</script>

<style scoped>
.animate-fade-down { animation: fadeDown .9s cubic-bezier(.16,.8,.43,1) both; }
@keyframes fadeDown {
  0% { opacity:0; transform:translateY(-24px) scale(.97); }
  100% { opacity:1; transform:translateY(0) scale(1); }
}
.loader.spinner {
  border: 3px solid rgba(0,0,0,0.15);
  border-top-color: #f59e0b;
  border-radius: 50%;
  width: 1rem;
  height: 1rem;
  animation: spin .8s linear infinite;
}
@keyframes spin { to { transform: rotate(360deg); } }
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