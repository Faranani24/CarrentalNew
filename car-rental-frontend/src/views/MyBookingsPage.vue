<script setup>
import { ref, onMounted } from 'vue';
import { getAllBookings } from '@/services/bookingService';
import { cancelBooking } from '@/services/bookingService';
import { formatDate, formatRate } from '@/utils/format.js';

const bookings = ref([]);
const loading = ref(true);
const error = ref(null);
const cancelling = ref(null); // Track which booking is being cancelled

const fetchBookings = async () => {
  loading.value = true;
  error.value = null;

  try {
    const data = await getAllBookings();
    // Add image URLs to each booking's car
    bookings.value = (data || []).map(booking => ({
      ...booking,
      car: booking.car ? {
        ...booking.car,
        imageUrl: `http://localhost:8082/api/cars/${booking.car.carId}/image`
      } : null
    }));
  } catch (e) {
    error.value = 'Failed to load bookings. Please try again.';
    console.error('Error fetching bookings:', e);
  } finally {
    loading.value = false;
  }
};

const handleCancelBooking = async (bookingId) => {
  if (!confirm('Are you sure you want to cancel this booking? This action cannot be undone.')) {
    return;
  }

  cancelling.value = bookingId;
  try {
    await cancelBooking(bookingId);

    // Update the booking in the list
    const booking = bookings.value.find(b => b.bookingId === bookingId);
    if (booking) {
      booking.status = 'CANCELLED';
    }

    alert('Booking cancelled successfully! The car is now available for other users.');
  } catch (e) {
    alert('Error cancelling booking: ' + e.message);
    console.error('Error:', e);
  } finally {
    cancelling.value = null;
  }
};

const getStatusClass = (status) => {
  const statusMap = {
    'CONFIRMED': 'bg-green-100 text-green-800 border-green-300',
    'PENDING': 'bg-yellow-100 text-yellow-800 border-yellow-300',
    'ACTIVE': 'bg-blue-100 text-blue-800 border-blue-300',
    'COMPLETED': 'bg-gray-100 text-gray-800 border-gray-300',
    'CANCELLED': 'bg-red-100 text-red-800 border-red-300',
    'REJECTED': 'bg-red-100 text-red-800 border-red-300'
  };
  return statusMap[status] || 'bg-gray-100 text-gray-800 border-gray-300';
};

const handleImageError = (event) => {
  event.target.src = 'https://via.placeholder.com/400x300?text=No+Image';
};

const canCancelBooking = (status) => {
  return status !== 'CANCELLED' && status !== 'COMPLETED';
};

onMounted(() => {
  fetchBookings();
});
</script>

<template>
  <div class="min-h-screen flex flex-col bg-gradient-to-b from-amber-50 via-white to-neutral-100 text-neutral-900">
    <main class="flex-1 w-full py-16">
      <div class="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8">
        <div class="mb-8">
          <h2 class="text-4xl md:text-5xl font-extrabold text-center mb-4">
            My Bookings
          </h2>
          <p class="text-center text-neutral-600">View and manage your car rental bookings</p>
        </div>

        <!-- Loading State -->
        <div v-if="loading" class="flex flex-col items-center justify-center p-12 bg-white rounded-xl shadow-lg">
          <div class="loader-spinner mb-4"></div>
          <p class="text-neutral-500">Loading your bookings...</p>
        </div>

        <!-- Error State -->
        <div v-else-if="error" class="bg-rose-50 border border-rose-200 rounded-lg p-6 text-center shadow-md">
          <p class="text-rose-600 font-medium">{{ error }}</p>
          <button @click="fetchBookings" class="mt-4 px-6 py-2 bg-orange-600 text-white rounded-lg hover:bg-orange-700 transition">
            Try Again
          </button>
        </div>

        <!-- Empty State -->
        <div v-else-if="bookings.length === 0" class="bg-white rounded-2xl shadow-xl p-12 text-center">
          <svg class="mx-auto h-24 w-24 text-neutral-300 mb-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2" />
          </svg>
          <h3 class="text-2xl font-bold text-neutral-700 mb-2">No Bookings Yet</h3>
          <p class="text-neutral-600 mb-6">You haven't made any car rental bookings yet.</p>
          <router-link to="/" class="inline-block px-6 py-3 bg-orange-600 text-white rounded-lg font-semibold hover:bg-orange-700 transition">
            Browse Available Cars
          </router-link>
        </div>

        <!-- Bookings List -->
        <div v-else class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          <div
              v-for="booking in bookings"
              :key="booking.bookingId"
              class="bg-white rounded-2xl shadow-lg overflow-hidden hover:shadow-xl transition-shadow duration-300"
          >
            <!-- Status Badge - Absolute positioned over image -->
            <div class="relative">
              <!-- Car Image -->
              <div class="h-48 bg-gradient-to-br from-amber-100 to-orange-100 overflow-hidden">
                <img
                    v-if="booking.car?.imageUrl"
                    :src="booking.car.imageUrl"
                    :alt="`${booking.car.make} ${booking.car.model}`"
                    @error="handleImageError"
                    class="w-full h-full object-cover"
                />
                <div v-else class="w-full h-full flex items-center justify-center">
                  <svg class="w-16 h-16 text-orange-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 19l9 2-9-18-9 18 9-2zm0 0v-8" />
                  </svg>
                </div>
              </div>

              <!-- Status Badge Overlay -->
              <div class="absolute top-3 right-3">
                <span
                    :class="getStatusClass(booking.status)"
                    class="px-3 py-1 rounded-full text-xs font-semibold border backdrop-blur-sm"
                >
                  {{ booking.status }}
                </span>
              </div>

              <!-- Booking ID -->
              <div class="absolute top-3 left-3">
                <span class="px-3 py-1 rounded-full text-xs font-mono bg-black/50 text-white backdrop-blur-sm">
                  #{{ booking.bookingId }}
                </span>
              </div>
            </div>

            <!-- Card Content -->
            <div class="p-6">
              <!-- Car Details -->
              <div class="mb-4">
                <h3 class="text-xl font-bold text-neutral-900 mb-1">
                  {{ booking.car?.make || 'Vehicle' }} {{ booking.car?.model || '' }}
                </h3>
                <p class="text-sm text-neutral-500">
                  {{ booking.car?.year || '' }} • {{ booking.car?.licensePlate || 'N/A' }}
                </p>
              </div>

              <!-- Booking Dates -->
              <div class="border-t border-neutral-200 pt-4 mb-4">
                <div class="flex items-center gap-2 text-sm text-neutral-600 mb-2">
                  <svg class="w-4 h-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z" />
                  </svg>
                  <span class="font-medium">Rental Period</span>
                </div>
                <p class="text-sm text-neutral-700 ml-6">
                  {{ formatDate(booking.startDate) }}
                </p>
                <p class="text-sm text-neutral-700 ml-6">
                  to {{ formatDate(booking.endDate) }}
                </p>
              </div>

              <!-- Price -->
              <div class="border-t border-neutral-200 pt-4 mb-4">
                <div class="flex items-center justify-between">
                  <span class="text-sm text-neutral-600 font-medium">Total Cost</span>
                  <span class="text-2xl font-bold text-orange-600">
                    {{ formatRate(booking.totalCost) }}
                  </span>
                </div>
              </div>

              <!-- Cancel Button -->
              <div v-if="canCancelBooking(booking.status)" class="border-t border-neutral-200 pt-4">
                <button
                    @click="handleCancelBooking(booking.bookingId)"
                    :disabled="cancelling === booking.bookingId"
                    class="w-full px-4 py-2 rounded-lg font-semibold text-white bg-rose-500 hover:bg-rose-600 disabled:opacity-50 disabled:cursor-not-allowed transition"
                >
                  <span v-if="cancelling === booking.bookingId" class="flex items-center justify-center gap-2">
                    <span class="loader spinner size-4"></span>
                    Cancelling...
                  </span>
                  <span v-else>Cancel Booking</span>
                </button>
                <p class="text-xs text-neutral-500 mt-2 text-center">Car will become available for other users</p>
              </div>

              <!-- Cancelled Info -->
              <div v-else-if="booking.status === 'CANCELLED'" class="border-t border-neutral-200 pt-4">
                <p class="text-sm text-rose-600 text-center font-medium">This booking has been cancelled</p>
              </div>

              <!-- Completed Info -->
              <div v-else-if="booking.status === 'COMPLETED'" class="border-t border-neutral-200 pt-4">
                <p class="text-sm text-green-600 text-center font-medium">This booking has been completed</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<style scoped>
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

.loader.spinner {
  border: 3px solid rgba(255,255,255,0.3);
  border-top-color: #ffffff;
  border-radius: 50%;
  width: 1rem;
  height: 1rem;
  animation: spin .8s linear infinite;
}
</style>