<script setup>
import { ref, computed, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { fetchCarById } from '@/services/carService';
import { createBooking } from '@/services/bookingService';
import { formatDate, formatRate } from '@/utils/format.js';

const route = useRoute();
const router = useRouter();

const loading = ref(true);
const error = ref(null);
const bookingLoading = ref(false);
const bookingConfirmed = ref(false);
const car = ref(null);

// Prefill from HomePage query params
const bookingDetails = ref({
  from: route.query.from || '',
  to: route.query.to || '',
  firstName: '',
  lastName: '',
  email: '',
  phone: ''
});

// Always use carId from route param
const carId = computed(() => route.params.carId);

const fetchCarDetails = async () => {
  if (!carId.value) {
    error.value = 'No car selected for booking.';
    loading.value = false;
    return;
  }

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
  if (!bookingDetails.value.from || !bookingDetails.value.to) return false;
  return new Date(bookingDetails.value.from) <= new Date(bookingDetails.value.to);
});

const totalDays = computed(() => {
  if (!datesValid.value) return 0;
  const from = new Date(bookingDetails.value.from);
  const to = new Date(bookingDetails.value.to);
  const diffTime = Math.abs(to - from);
  return Math.ceil(diffTime / (1000 * 60 * 60 * 24)) + 1;
});

const totalCost = computed(() => {
  if (!car.value || !datesValid.value) return 0;
  return car.value.dailyRate * totalDays.value;
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

  const bookingData = {
    carId: car.value.carId,
    from: bookingDetails.value.from,
    to: bookingDetails.value.to,
    fullName: `${bookingDetails.value.firstName} ${bookingDetails.value.lastName}`,
    email: bookingDetails.value.email,
    phone: bookingDetails.value.phone,
    totalCost: totalCost.value
  };

  try {
    await createBooking(bookingData);
    bookingConfirmed.value = true;
  } catch (e) {
    error.value = 'Failed to process booking. Please try again.';
    console.error('Booking error:', e);
  } finally {
    bookingLoading.value = false;
  }
};

onMounted(() => {
  fetchCarDetails();
});
</script>

<template>
  <div class="min-h-screen flex flex-col bg-neutral-50 text-neutral-900">
    <main class="flex-1 w-full py-16">
      <div class="mx-auto max-w-6xl px-4 sm:px-6 lg:px-8">
        <h2 class="text-4xl md:text-5xl font-extrabold text-center mb-12">
          Confirm Your Booking
        </h2>

        <div v-if="loading" class="flex flex-col items-center justify-center p-8 bg-white rounded-xl shadow-lg">
          <div class="loader-spinner mb-4"></div>
          <p class="text-neutral-500">Loading car details...</p>
        </div>

        <div v-else-if="error" class="bg-rose-50 border border-rose-200 rounded-lg p-6 text-center shadow-md">
          <p class="text-rose-600 font-medium">{{ error }}</p>
        </div>

        <div v-else-if="bookingConfirmed" class="max-w-md mx-auto bg-white rounded-2xl shadow-xl p-8 flex flex-col items-center gap-6">
          <svg class="h-20 w-20 text-green-500" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2l4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
          </svg>
          <h3 class="text-3xl font-bold text-center text-green-700">Booking Confirmed!</h3>
          <p class="text-center text-neutral-600">Your reservation is all set. We look forward to seeing you!</p>
          <img :src="car.imageUrl" :alt="`${car.make} ${car.model}`" class="w-full h-48 object-cover rounded-lg shadow-inner" />
          <div class="w-full text-center space-y-2 font-medium">
            <p><strong>Car:</strong> {{ car.make }} {{ car.model }} ({{ car.year }})</p>
            <p><strong>Rental Dates:</strong> {{ formatDate(bookingDetails.from) }} - {{ formatDate(bookingDetails.to) }}</p>
            <p><strong>Total Days:</strong> {{ totalDays }}</p>
            <p><strong>Total Cost:</strong> {{ formatRate(totalCost) }}</p>
          </div>
          <button @click="router.push({ name: 'home' })"
                  class="mt-6 w-full px-6 py-3 rounded-xl bg-orange-600 text-white font-semibold shadow-lg hover:bg-orange-700 transition duration-300 transform hover:scale-105">
            Back to Home
          </button>
        </div>

        <div v-else class="grid grid-cols-1 md:grid-cols-2 gap-10">
          <div class="bg-white rounded-3xl shadow-2xl p-8 flex flex-col justify-between">
            <form @submit.prevent="bookCar" class="space-y-6">
              <h3 class="text-2xl font-bold text-neutral-800 mb-6">Your Information</h3>
              <div class="grid grid-cols-1 sm:grid-cols-2 gap-6">
                <div>
                  <label for="firstName" class="block text-sm font-medium text-neutral-700">First Name</label>
                  <input id="firstName" v-model="bookingDetails.firstName" type="text" required class="mt-1 block w-full rounded-md border-neutral-300 shadow-sm focus:border-orange-500 focus:ring focus:ring-orange-500 focus:ring-opacity-50" />
                </div>
                <div>
                  <label for="lastName" class="block text-sm font-medium text-neutral-700">Last Name</label>
                  <input id="lastName" v-model="bookingDetails.lastName" type="text" required class="mt-1 block w-full rounded-md border-neutral-300 shadow-sm focus:border-orange-500 focus:ring focus:ring-orange-500 focus:ring-opacity-50" />
                </div>
              </div>
              <div>
                <label for="email" class="block text-sm font-medium text-neutral-700">Email</label>
                <input id="email" v-model="bookingDetails.email" type="email" required class="mt-1 block w-full rounded-md border-neutral-300 shadow-sm focus:border-orange-500 focus:ring focus:ring-orange-500 focus:ring-opacity-50" />
              </div>
              <div>
                <label for="phone" class="block text-sm font-medium text-neutral-700">Phone Number</label>
                <input id="phone" v-model="bookingDetails.phone" type="tel" required class="mt-1 block w-full rounded-md border-neutral-300 shadow-sm focus:border-orange-500 focus:ring focus:ring-orange-500 focus:ring-opacity-50" />
              </div>

              <h3 class="text-2xl font-bold text-neutral-800 mt-8 mb-6">Rental Dates</h3>
              <div class="grid grid-cols-1 sm:grid-cols-2 gap-6">
                <div>
                  <label for="from" class="block text-sm font-medium text-neutral-700">From</label>
                  <input id="from" type="date" v-model="bookingDetails.from" required class="mt-1 block w-full rounded-md border-neutral-300 shadow-sm focus:border-orange-500 focus:ring focus:ring-orange-500 focus:ring-opacity-50" />
                </div>
                <div>
                  <label for="to" class="block text-sm font-medium text-neutral-700">To</label>
                  <input id="to" type="date" v-model="bookingDetails.to" required :min="bookingDetails.from || undefined" class="mt-1 block w-full rounded-md border-neutral-300 shadow-sm focus:border-orange-500 focus:ring focus:ring-orange-500 focus:ring-opacity-50" />
                </div>
              </div>

              <button
                  type="submit"
                  :disabled="!canBook"
                  class="w-full mt-8 px-6 py-4 rounded-xl font-bold text-lg text-white shadow-lg transition duration-300 transform"
                  :class="{'bg-orange-600 hover:bg-orange-700 hover:scale-105': canBook, 'bg-neutral-300 cursor-not-allowed': !canBook}"
              >
                {{ bookingLoading ? 'Processing...' : `Confirm Booking - ${formatRate(totalCost)}` }}
              </button>
            </form>
          </div>

          <div class="bg-white rounded-3xl shadow-2xl p-8 sticky top-20 h-fit">
            <h3 class="text-2xl font-bold text-neutral-800 mb-6">Booking Summary</h3>
            <img :src="car.imageUrl" :alt="`${car.make} ${car.model}`" class="w-full h-64 object-cover rounded-2xl shadow-lg mb-6" />
            <div class="space-y-4">
              <h4 class="text-3xl font-bold text-neutral-900">{{ car.make }} {{ car.model }}</h4>
              <p class="text-lg text-neutral-500">{{ car.year }} · {{ car.fuelType }} · {{ car.transmission }}</p>

              <div class="border-t border-b border-neutral-200 py-4 space-y-2">
                <div class="flex justify-between items-center text-neutral-700">
                  <span class="font-medium">Daily Rate</span>
                  <span>{{ formatRate(car.dailyRate) }}</span>
                </div>
                <div class="flex justify-between items-center text-neutral-700">
                  <span class="font-medium">Total Days</span>
                  <span>{{ totalDays }}</span>
                </div>
              </div>

              <div class="flex justify-between items-center font-bold text-2xl text-neutral-900 pt-2">
                <span>Total Cost</span>
                <span>{{ formatRate(totalCost) }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<style scoped>
/* Custom styles for the loader and input fields */
.loader-spinner {
  border: 4px solid rgba(0,0,0,0.1);
  border-top-color: #f97316;
  border-radius: 50%;
  width: 3rem;
  height: 3rem;
  animation: spin 0.8s linear infinite;
}

@keyframes spin {
  to { transform: rotate(360deg); }
}

input {
  transition: all 0.3s ease;
}

input:focus {
  border-color: #f97316;
  box-shadow: 0 0 0 3px rgba(249, 115, 22, 0.2);
}
</style>