<script setup>
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

// Your original data converted to Vue 3 Composition API
const car = ref({
  name: "Toyota Corolla",
  type: "Sedan",
  dailyRate: 104.81,
  image: "https://via.placeholder.com/150",
  review: "Smooth drive, fuel efficient, and comfortable for long journeys.",
  rating: 4,
});

const booking = ref({
  pickupDate: "2025-09-01",
  pickupLocation: "Cape Town International",
  dropoffDate: "2025-09-08",
  dropoffLocation: "Cape Town International",
  duration: 7,
});

const payment = ref({
  name: "",
  cardNumber: "",
  expiry: "",
  cvv: "",
});

const billing = ref({
  address: "",
  country: "South Africa",
  zip: "",
  city: "",
  email: "",
  countryCode: "+1",
  phone: "",
});

const taxes = ref(16.92);

const processing = ref(false)
const error = ref('')

const total = computed(() => {
  return booking.value.duration * car.value.dailyRate + taxes.value;
});

const canSubmit = computed(() => {
  return !processing.value &&
      payment.value.name &&
      payment.value.cardNumber &&
      payment.value.expiry &&
      payment.value.cvv &&
      billing.value.address &&
      billing.value.zip &&
      billing.value.city &&
      billing.value.email &&
      billing.value.phone;
});

function formatRate(val) {
  if (val == null) return ''
  return Intl.NumberFormat(undefined, { style: 'currency', currency: 'ZAR' }).format(val)
}

function submitForm() {
  if (!canSubmit.value) return;

  processing.value = true;
  error.value = '';

  // Simulate an async operation
  setTimeout(() => {
    try {
      // Basic validation
      if (payment.value.cardNumber.replace(/\s/g, '').length !== 16 || payment.value.cvv.length !== 3) {
        throw new Error('Invalid card details. Please check the information and try again.')
      }

      console.log("Booking confirmed and payment submitted! (Integrate with backend)");
      console.log("Car:", car.value);
      console.log("Booking:", booking.value);
      console.log("Payment:", payment.value);
      console.log("Billing:", billing.value);

      // Navigate to a confirmation page or similar
      alert("Booking confirmed and payment submitted!");
      // router.push({ name: 'confirmation-page' }); // Example navigation
    } catch (e) {
      error.value = e.message || 'Payment failed. Please try again.';
    } finally {
      processing.value = false;
    }
  }, 2000);
}
</script>

<template>
  <div class="min-h-screen flex flex-col bg-gradient-to-b from-amber-50 via-white to-neutral-100 text-neutral-900">
    <nav class="relative z-30 backdrop-blur-md/40 bg-white/70 border-b border-amber-200/60 shadow-sm">
      <div class="mx-auto max-w-6xl px-6 py-4 flex items-center justify-between">
        <div class="flex items-center gap-3">
          <div class="h-10 w-10 rounded-xl bg-gradient-to-br from-amber-400 to-orange-500 shadow-lg flex items-center justify-center font-black text-gray-900 tracking-tighter" aria-label="CarRental logo">CR</div>
          <h1 class="text-2xl font-bold tracking-tight">
            <span class="bg-gradient-to-r from-amber-500 via-orange-500 to-rose-400 bg-clip-text text-transparent drop-shadow-sm">CarRental</span>
          </h1>
        </div>
        <div class="flex items-center gap-4">
          <button @click="router.back()" type="button" class="group relative overflow-hidden px-5 py-2 rounded-lg font-medium bg-white border border-amber-200 text-neutral-800 hover:bg-amber-50 transition">
            <span class="relative z-10">Back</span>
            <span class="absolute inset-0 bg-gradient-to-r from-amber-300/0 via-amber-300/30 to-amber-300/0 translate-x-[-100%] group-hover:translate-x-[100%] transition-transform duration-700"></span>
          </button>
        </div>
      </div>
    </nav>

    <main class="relative z-10 flex-1 w-full flex items-center justify-center py-12">
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
              <div class="flex items-center space-x-4">
                <img :src="car.image" alt="Car" class="h-24 w-24 rounded-xl object-cover border border-amber-200" />
                <div>
                  <h3 class="text-lg font-semibold">{{ car.name }}</h3>
                  <p class="text-neutral-500 text-sm">{{ car.type }}</p>
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

            <div class="p-6 bg-white/90 backdrop-blur-xl/30 rounded-2xl border border-amber-200/70 shadow-xl animate-fade-down">
              <h2 class="text-xl font-bold mb-4 flex items-center gap-2">
                                <span class="text-amber-500">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-map-pin"><path d="M12 18s-4-4.8-4-8.8A8 8 0 0 1 12 2a8 8 0 0 1 4 8.2c0 4-4 8.8-4 8.8z"/><circle cx="12" cy="10" r="2"/></svg>
                                </span>
                Billing Information
              </h2>
              <form class="space-y-4">
                <input v-model="billing.address" type="text" placeholder="Street address" class="w-full px-4 py-3 rounded-lg bg-white border border-amber-200 focus:border-amber-400 outline-none transition placeholder:text-neutral-400" required />
                <input v-model="billing.city" type="text" placeholder="City" class="w-full px-4 py-3 rounded-lg bg-white border border-amber-200 focus:border-amber-400 outline-none transition placeholder:text-neutral-400" required />
                <div class="grid grid-cols-2 gap-4">
                  <input v-model="billing.country" type="text" value="United States" class="w-full px-4 py-3 rounded-lg bg-white border border-amber-200 focus:border-amber-400 outline-none transition placeholder:text-neutral-400" readonly />
                  <input v-model="billing.zip" type="text" placeholder="Zip code" class="w-full px-4 py-3 rounded-lg bg-white border border-amber-200 focus:border-amber-400 outline-none transition placeholder:text-neutral-400" required />
                </div>
                <input v-model="billing.email" type="email" placeholder="Email address" class="w-full px-4 py-3 rounded-lg bg-white border border-amber-200 focus:border-amber-400 outline-none transition placeholder:text-neutral-400" required />
                <div class="flex">
                  <input v-model="billing.countryCode" type="text" value="+1" class="w-20 px-4 py-3 rounded-l-lg bg-white border border-amber-200 focus:border-amber-400 outline-none transition placeholder:text-neutral-400" readonly />
                  <input v-model="billing.phone" type="text" placeholder="Mobile phone number" class="w-full px-4 py-3 rounded-r-lg bg-white border border-amber-200 focus:border-amber-400 outline-none transition placeholder:text-neutral-400" required />
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
                <div class="flex justify-between items-center text-sm">
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
                <p class="text-xs text-neutral-500">Prices include all taxes and fees.</p>
              </div>
            </div>

            <button @click="submitForm"
                    type="submit"
                    class="group w-full relative overflow-hidden px-6 py-3 rounded-lg font-semibold tracking-wide bg-gradient-to-r from-amber-400 via-yellow-400 to-orange-500 text-gray-900 shadow-lg hover:scale-[1.02] active:scale-[0.98] transition disabled:opacity-60 disabled:cursor-not-allowed"
                    :disabled="!canSubmit">
                            <span class="relative z-10 flex items-center justify-center gap-2">
                                <span v-if="processing" class="loader spinner size-4" aria-hidden="true"></span>
                                {{ processing ? 'Processing...' : 'Book & Pay' }}
                            </span>
              <span class="absolute inset-0 opacity-0 group-hover:opacity-100 bg-gradient-to-r from-yellow-200 via-white/60 to-yellow-200 mix-blend-overlay transition"></span>
            </button>
          </div>

          <div class="col-span-1 md:col-span-2 p-6 mt-8 rounded-2xl bg-white/90 backdrop-blur-xl/30 border border-amber-200/70 shadow-xl animate-fade-up">
            <h2 class="text-2xl font-bold mb-6 text-center gradient-text-light">Related Vehicles</h2>
            <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
              <div class="bg-neutral-50 p-4 rounded-xl shadow-md flex flex-col items-center text-center border border-amber-100">
                <img src="https://via.placeholder.com/200x150" alt="Related Car" class="w-full h-auto rounded-md mb-2 object-cover">
                <h4 class="font-bold text-base mt-2">Toyota Starlet Cross</h4>
                <p class="text-sm text-neutral-500">or similar - Auto</p>
              </div>
              <div class="bg-neutral-50 p-4 rounded-xl shadow-md flex flex-col items-center text-center border border-amber-100">
                <img src="https://via.placeholder.com/200x150" alt="Related Car" class="w-full h-auto rounded-md mb-2 object-cover">
                <h4 class="font-bold text-base mt-2">Suzuki Jimny 5-Door</h4>
                <p class="text-sm text-neutral-500">Exclusive - Auto</p>
              </div>
              <div class="bg-neutral-50 p-4 rounded-xl shadow-md flex flex-col items-center text-center border border-amber-100">
                <img src="https://via.placeholder.com/200x150" alt="Related Car" class="w-full h-auto rounded-md mb-2 object-cover">
                <h4 class="font-bold text-base mt-2">Volkswagen Polo Vivo</h4>
                <p class="text-sm text-neutral-500">or similar - Auto</p>
              </div>
              <div class="bg-neutral-50 p-4 rounded-xl shadow-md flex flex-col items-center text-center border border-amber-100">
                <img src="https://via.placeholder.com/200x150" alt="Related Car" class="w-full h-auto rounded-md mb-2 object-cover">
                <h4 class="font-bold text-base mt-2">Audi A3 Sedan</h4>
                <p class="text-sm text-neutral-500">or similar - Auto</p>
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
/* Re-used styles from the original component */
.animate-fade-down { animation: fadeDown .9s cubic-bezier(.16,.8,.43,1) both; }
.animate-fade-up { animation: fadeUp 1s cubic-bezier(.16,.8,.43,1) both; }
@keyframes fadeDown {
  0% { opacity:0; transform:translateY(-24px) scale(.97); }
  100% { opacity:1; transform:translateY(0) scale(1); }
}
@keyframes fadeUp {
  0% { opacity:0; transform:translateY(30px) scale(.96); }
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