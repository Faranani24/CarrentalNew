<script setup>
import { ref, computed, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { fetchCarById } from '@/services/carService';
import { createBooking } from '@/services/bookingService'; // Import the new service
import { formatDate } from '@/utils/format.js';

const route = useRoute();
const router = useRouter();

const loading = ref(true);
const error = ref(null);
const bookingLoading = ref(false);
const carId = computed(() => route.params.id);
const car = ref(null);

const bookingDetails = ref({
  from: '',
  to: '',
  firstName: '',
  lastName: '',
  email: '',
  phone: ''
});

const fetchCarDetails = async () => {
  loading.value = true;
  error.value = null;
  try {
    const fetchedCar = await fetchCarById(carId.value);
    car.value = fetchedCar;
  } catch (e) {
    error.value = 'Failed to load car details.';
    console.error('Error fetching car details:', e);
  } finally {
    loading.value = false;
  }
};

const datesValid = computed(() => {
  if (!bookingDetails.value.from || !bookingDetails.value.to) return false
  return new Date(bookingDetails.value.from) <= new Date(bookingDetails.value.to)
})

const totalDays = computed(() => {
  if (!datesValid.value) return 0
  const from = new Date(bookingDetails.value.from)
  const to = new Date(bookingDetails.value.to)
  const diffTime = Math.abs(to - from)
  return Math.ceil(diffTime / (1000 * 60 * 60 * 24)) + 1
});

const totalCost = computed(() => {
  if (!car.value || !datesValid.value) return 0
  return car.value.dailyRate * totalDays.value
});

const isFormValid = computed(() => {
  return datesValid.value &&
      bookingDetails.value.firstName &&
      bookingDetails.value.lastName &&
      bookingDetails.value.email &&
      bookingDetails.value.phone;
});

const canBook = computed(() => isFormValid.value && !bookingLoading.value);

const bookCar = async () => {
  if (!isFormValid.value || !car.value) {
    error.value = 'Please fill out all required fields.';
    return;
  }

  bookingLoading.value = true;
  error.value = null;

  // IMPORTANT: Prepare data to match the backend's BookingRequest DTO
  const bookingData = {
    carId: car.value.id,
    from: bookingDetails.value.from,
    to: bookingDetails.value.to,
    fullName: `${bookingDetails.value.firstName} ${bookingDetails.value.lastName}`,
    email: bookingDetails.value.email,
    phone: bookingDetails.value.phone,
    totalCost: totalCost.value
  };

  try {
    // Call the service to create the booking
    const response = await createBooking(bookingData);

    // Redirect to the payment page with the bookingId
    router.push({
      name: 'payment',
      params: { bookingId: response.bookingId },
      query: {
        totalCost: totalCost.value,
        carDetails: `${car.value.make} ${car.value.model} (${car.value.year})`
      }
    });
  } catch (e) {
    error.value = 'Failed to process booking. Please try again.';
    console.error('Booking error:', e);
  } finally {
    bookingLoading.value = false;
  }
};

function formatRate(val) {
  if (val == null) return ''
  return new Intl.NumberFormat('en-ZA', { style: 'currency', currency: 'ZAR' }).format(val)
}

onMounted(() => {
  if (carId.value) {
    fetchCarDetails();
  }
});
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
        <h2 class="text-3xl md:text-4xl font-extrabold tracking-tight text-center mb-10">
          <span class="gradient-text-light">Book Your Ride</span>
        </h2>

        <div v-if="loading" class="flex flex-col items-center justify-center p-8 bg-white/80 rounded-xl shadow-xl border border-amber-200/60 backdrop-blur-xl/30">
          <div class="loader-spinner mb-4"></div>
          <p class="text-neutral-500">Loading car details...</p>
        </div>

        <div v-else-if="error" class="bg-rose-50 border border-rose-200 rounded-lg p-6 text-center shadow-lg">
          <p class="text-rose-600 font-medium">{{ error }}</p>
        </div>

        <div v-else-if="car" class="grid grid-cols-1 lg:grid-cols-2 gap-8">
          <div class="order-2 lg:order-1">
            <div class="backdrop-blur-xl/30 bg-white/90 border border-amber-200/70 shadow-xl rounded-2xl p-6 md:p-8">
              <form @submit.prevent="bookCar" class="space-y-6">
                <div class="grid grid-cols-2 gap-4">
                  <div>
                    <label for="from" class="text-xs uppercase tracking-wider text-neutral-600 font-medium">From</label>
                    <input id="from" type="date" v-model="bookingDetails.from" required
                           class="mt-1 w-full px-4 py-3 rounded-lg bg-white border border-amber-200 focus:border-amber-400 outline-none transition" />
                  </div>
                  <div>
                    <label for="to" class="text-xs uppercase tracking-wider text-neutral-600 font-medium">To</label>
                    <input id="to" type="date" v-model="bookingDetails.to" required
                           :min="bookingDetails.from || undefined"
                           class="mt-1 w-full px-4 py-3 rounded-lg bg-white border border-amber-200 focus:border-amber-400 outline-none transition" />
                  </div>
                </div>

                <p v-if="!datesValid && bookingDetails.from && bookingDetails.to" class="text-xs text-rose-600 font-medium">
                  End date must be after start date.
                </p>

                <div class="space-y-4">
                  <h4 class="text-lg font-semibold">Your Details</h4>
                  <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                    <div>
                      <label for="firstName" class="text-xs uppercase tracking-wider text-neutral-600 font-medium">First Name</label>
                      <input id="firstName" v-model="bookingDetails.firstName" type="text" required
                             class="mt-1 w-full px-4 py-3 rounded-lg bg-white border border-amber-200 focus:border-amber-400 outline-none transition placeholder:text-neutral-400" />
                    </div>
                    <div>
                      <label for="lastName" class="text-xs uppercase tracking-wider text-neutral-600 font-medium">Last Name</label>
                      <input id="lastName" v-model="bookingDetails.lastName" type="text" required
                             class="mt-1 w-full px-4 py-3 rounded-lg bg-white border border-amber-200 focus:border-amber-400 outline-none transition placeholder:text-neutral-400" />
                    </div>
                  </div>
                  <div>
                    <label for="email" class="text-xs uppercase tracking-wider text-neutral-600 font-medium">Email</label>
                    <input id="email" v-model="bookingDetails.email" type="email" required
                           class="mt-1 w-full px-4 py-3 rounded-lg bg-white border border-amber-200 focus:border-amber-400 outline-none transition placeholder:text-neutral-400" />
                  </div>
                  <div>
                    <label for="phone" class="text-xs uppercase tracking-wider text-neutral-600 font-medium">Phone Number</label>
                    <input id="phone" v-model="bookingDetails.phone" type="tel" required
                           class="mt-1 w-full px-4 py-3 rounded-lg bg-white border border-amber-200 focus:border-amber-400 outline-none transition placeholder:text-neutral-400"
                           placeholder="(+27)" />
                  </div>
                </div>

                <button type="submit"
                        class="w-full px-6 py-4 rounded-lg font-semibold tracking-wide bg-gradient-to-r from-amber-500 via-orange-500 to-rose-400 text-white shadow-lg hover:scale-[1.01] active:scale-[0.98] transition disabled:opacity-60 disabled:cursor-not-allowed"
                        :disabled="!canBook">
                  <span class="flex items-center justify-center gap-2">
                    <span v-if="bookingLoading" class="loader spinner size-4" aria-hidden="true"></span>
                    {{ bookingLoading ? 'Booking...' : `Proceed to Payment - ${formatRate(totalCost)}` }}
                  </span>
                </button>
              </form>
            </div>
          </div>

          <div class="order-1 lg:order-2">
            <div class="backdrop-blur-xl/30 bg-white/90 border border-amber-200/70 shadow-xl rounded-2xl p-6 sticky top-6">
              <h3 class="text-xl font-bold mb-4 gradient-text-light">Booking Summary</h3>
              <div class="space-y-4">
                <div class="flex flex-col md:flex-row items-start md:items-center gap-4">
                  <div class="w-full md:w-32 h-20 rounded-lg overflow-hidden flex-shrink-0">
                    <img :src="car.imageUrl" alt="" class="w-full h-full object-cover">
                  </div>
                  <div>
                    <h4 class="text-lg font-semibold">{{ car.make }} {{ car.model }}</h4>
                    <p class="text-sm text-neutral-500">{{ car.year }} · {{ car.fuelType }}</p>
                  </div>
                </div>
                <div class="space-y-2">
                  <div class="flex justify-between items-center text-sm text-neutral-600">
                    <span>From:</span>
                    <span class="font-medium">{{ bookingDetails.from ? formatDate(bookingDetails.from) : 'Select Date' }}</span>
                  </div>
                  <div class="flex justify-between items-center text-sm text-neutral-600">
                    <span>To:</span>
                    <span class="font-medium">{{ bookingDetails.to ? formatDate(bookingDetails.to) : 'Select Date' }}</span>
                  </div>
                  <div class="flex justify-between items-center text-sm text-neutral-600">
                    <span>Total Days:</span>
                    <span class="font-medium">{{ totalDays }}</span>
                  </div>
                </div>
                <div class="border-t border-amber-200 pt-3 mt-4">
                  <div class="flex justify-between items-center">
                    <span class="text-lg font-bold">Total Cost:</span>
                    <span class="text-2xl font-bold bg-gradient-to-r from-amber-500 via-orange-500 to-rose-500 bg-clip-text text-transparent">
                      {{ formatRate(totalCost) }}
                    </span>
                  </div>
                </div>
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
/* Animations and effects */
.animate-fade-in { animation: fadeIn 0.8s ease-in-out both; }
@keyframes fadeIn { from { opacity:0; transform:translateY(20px); } to { opacity:1; transform:translateY(0); } }

.animate-shake { animation: shake 0.5s ease-in-out; }
@keyframes shake { 0%, 100% { transform: translateX(0); } 25% { transform: translateX(-5px); } 75% { transform: translateX(5px); } }

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

.loader.spinner {
  border: 3px solid rgba(255,255,255,0.3);
  border-top-color: #ffffff;
  border-radius: 50%;
  width: 1rem;
  height: 1rem;
  animation: spin .8s linear infinite;
}
@keyframes spin { to { transform: rotate(360deg); } }

.loader-spinner {
  border: 3px solid rgba(107, 114, 128, 0.3);
  border-top-color: #6b7280;
  border-radius: 50%;
  width: 2rem;
  height: 2rem;
  animation: spin .8s linear infinite;
}

/* Card input focus effects */
input:focus {
  box-shadow: 0 0 0 3px rgba(251, 191, 36, 0.1);
}
</style>