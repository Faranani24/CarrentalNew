<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { fetchCarById, bookCar } from '@/services/carService'
import { formatDate } from '@/utils/format'

const route = useRoute()
const router = useRouter()

const car = ref(null)
const loading = ref(true)
const bookingLoading = ref(false)
const error = ref('')

const bookingDetails = ref({
  fullName: '',
  email: '',
  phone: '',
  from: route.query.from || '',
  to: route.query.to || ''
})

const datesValid = computed(() => {
  if (!bookingDetails.value.from || !bookingDetails.value.to) return false
  return new Date(bookingDetails.value.from) <= new Date(bookingDetails.value.to)
})

const durationInDays = computed(() => {
  if (!datesValid.value) return 0
  const from = new Date(bookingDetails.value.from)
  const to = new Date(bookingDetails.value.to)
  const diffTime = Math.abs(to - from)
  const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24))
  return diffDays + 1 // Add 1 to include the last day
})

const totalCost = computed(() => {
  if (!car.value || !datesValid.value) return 0
  return car.value.dailyRate * durationInDays.value
})

const canBook = computed(() => {
  return !bookingLoading.value &&
      datesValid.value &&
      bookingDetails.value.fullName &&
      bookingDetails.value.email &&
      !loading.value
})

async function loadCar() {
  loading.value = true
  error.value = ''
  try {
    const carId = route.params.id
    if (!carId) {
      router.push({ name: 'home' })
      return
    }
    car.value = await fetchCarById(carId)
  } catch (e) {
    error.value = e?.message || 'Failed to load car details.'
    console.error('[booking] load car failed:', e)
  } finally {
    loading.value = false
  }
}

async function submitBooking(e) {
  e.preventDefault()
  if (!canBook.value) return

  bookingLoading.value = true
  error.value = ''

  try {
    const bookingData = {
      carId: car.value.id || car.value.carId,
      ...bookingDetails.value
    }
    const response = await bookCar(bookingData)

    // Redirect to a confirmation page or show a success message
    router.push({ name: 'confirmation', params: { bookingId: response.bookingId } })

  } catch (e) {
    error.value = e?.message || 'Booking failed. Please try again.'
    console.error('[booking] submission failed:', e)
  } finally {
    bookingLoading.value = false
  }
}

function formatRate(val) {
  if (val == null) return ''
  return Intl.NumberFormat(undefined, { style: 'currency', currency: 'ZAR' }).format(val)
}

onMounted(() => {
  loadCar()
})
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
          <span class="gradient-text-light">Complete Your Booking</span>
        </h2>

        <div v-if="loading" class="text-center p-8">
          <div class="loader spinner size-8 mx-auto" aria-hidden="true"></div>
          <p class="mt-4 text-neutral-500">Loading car details...</p>
        </div>

        <div v-else-if="error" class="text-center p-8 text-rose-600 font-medium animate-shake">
          <p>{{ error }}</p>
        </div>

        <div v-else-if="car" class="grid grid-cols-1 md:grid-cols-2 gap-8 backdrop-blur-xl/30 bg-white/90 border border-amber-200/70 shadow-xl rounded-2xl p-6 md:p-8">

          <div>
            <div class="rounded-xl overflow-hidden border border-amber-200 shadow-sm">
              <div class="relative h-56 overflow-hidden">
                <img :src="car.imageUrl" class="w-full h-full object-cover" :alt="`${car.make} ${car.model}`" />
              </div>
              <div class="p-4 bg-white">
                <h4 class="font-semibold text-xl tracking-tight mb-1 text-neutral-900">
                  {{ car.make }} {{ car.model }} <span class="text-sm text-neutral-500">({{ car.year }})</span>
                </h4>
                <p class="text-xs font-mono text-neutral-500">Plate: {{ car.licensePlate }}</p>
                <div class="flex items-center justify-between mt-4 border-t border-amber-100 pt-4">
                  <p class="text-sm text-neutral-500">Daily Rate</p>
                  <p class="text-xl font-bold">
                    <span class="bg-gradient-to-r from-amber-500 via-orange-500 to-rose-500 bg-clip-text text-transparent">
                      {{ formatRate(car.dailyRate) }}
                    </span>
                  </p>
                </div>
              </div>
            </div>

            <div class="mt-6 p-5 rounded-xl border border-amber-200 bg-amber-50 shadow-inner">
              <h5 class="text-lg font-semibold mb-3">Booking Summary</h5>
              <div class="space-y-2 text-sm text-neutral-700">
                <div class="flex justify-between items-center">
                  <span>From:</span>
                  <span class="font-medium">{{ bookingDetails.from ? formatDate(bookingDetails.from) : 'N/A' }}</span>
                </div>
                <div class="flex justify-between items-center">
                  <span>To:</span>
                  <span class="font-medium">{{ bookingDetails.to ? formatDate(bookingDetails.to) : 'N/A' }}</span>
                </div>
                <div class="flex justify-between items-center">
                  <span>Duration:</span>
                  <span class="font-medium">{{ durationInDays }} day<span v-if="durationInDays !== 1">s</span></span>
                </div>
                <div class="flex justify-between items-center pt-3 border-t border-amber-200 mt-3">
                  <span class="text-base font-bold">Total Cost:</span>
                  <span class="text-2xl font-bold bg-gradient-to-r from-amber-500 via-orange-500 to-rose-500 bg-clip-text text-transparent">
                    {{ formatRate(totalCost) }}
                  </span>
                </div>
              </div>
            </div>
          </div>

          <div class="flex flex-col">
            <h5 class="text-xl font-semibold mb-4">Your Details</h5>
            <form @submit="submitBooking" class="grid gap-4 flex-1">
              <div>
                <label for="fullName" class="text-xs uppercase tracking-wider text-neutral-600 font-medium">Full Name</label>
                <input id="fullName" v-model="bookingDetails.fullName" type="text" required
                       class="mt-1 w-full px-4 py-3 rounded-lg bg-white border border-amber-200 focus:border-amber-400 outline-none transition placeholder:text-neutral-400"
                       placeholder="John Doe" />
              </div>
              <div>
                <label for="email" class="text-xs uppercase tracking-wider text-neutral-600 font-medium">Email Address</label>
                <input id="email" v-model="bookingDetails.email" type="email" required
                       class="mt-1 w-full px-4 py-3 rounded-lg bg-white border border-amber-200 focus:border-amber-400 outline-none transition placeholder:text-neutral-400"
                       placeholder="john.doe@example.com" />
              </div>
              <div>
                <label for="phone" class="text-xs uppercase tracking-wider text-neutral-600 font-medium">Phone Number</label>
                <input id="phone" v-model="bookingDetails.phone" type="tel"
                       class="mt-1 w-full px-4 py-3 rounded-lg bg-white border border-amber-200 focus:border-amber-400 outline-none transition placeholder:text-neutral-400"
                       placeholder="+1 555 123 4567" />
              </div>
              <div class="grid grid-cols-2 gap-4">
                <div>
                  <label for="from" class="text-xs uppercase tracking-wider text-neutral-600 font-medium">From</label>
                  <input id="from" v-model="bookingDetails.from" type="date" required
                         class="mt-1 w-full px-4 py-3 rounded-lg bg-white border border-amber-200 focus:border-amber-400 outline-none transition">
                </div>
                <div>
                  <label for="to" class="text-xs uppercase tracking-wider text-neutral-600 font-medium">To</label>
                  <input id="to" v-model="bookingDetails.to" type="date" required
                         :min="bookingDetails.from || undefined"
                         class="mt-1 w-full px-4 py-3 rounded-lg bg-white border border-amber-200 focus:border-amber-400 outline-none transition">
                </div>
              </div>

              <p v-if="!datesValid" class="text-xs text-rose-600 font-medium -mt-1">End date must be after start date.</p>

              <p v-if="error" class="text-rose-600 font-medium mt-4 animate-shake text-sm">{{ error }}</p>

              <button type="submit"
                      class="mt-6 w-full relative overflow-hidden px-6 py-3 rounded-lg font-semibold tracking-wide bg-gradient-to-r from-amber-400 via-yellow-400 to-orange-500 text-gray-900 shadow-lg hover:scale-[1.01] active:scale-[0.98] transition disabled:opacity-60"
                      :disabled="!canBook">
                <span class="relative z-10 flex items-center justify-center gap-2">
                  <span v-if="bookingLoading" class="loader spinner size-4" aria-hidden="true"></span>
                  {{ bookingLoading ? 'Booking...' : `Book for ${formatRate(totalCost)}` }}
                </span>
                <span class="absolute inset-0 opacity-0 group-hover:opacity-100 bg-gradient-to-r from-yellow-200 via-white/50 to-yellow-200 mix-blend-overlay transition"></span>
              </button>
            </form>
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
/*
  The CSS from HomePage.vue should be copied and pasted here,
  as you cannot import a Vue file directly as a stylesheet.
*/
.hero { min-height: 70vh; }
.hero-title-gradient-light {
  background: linear-gradient(90deg,#fbbf24 0%,#f59e0b 30%,#fb923c 60%,#f97316 80%,#fbbf24 100%);
  -webkit-background-clip: text;
  color: transparent;
  background-size: 200% 100%;
  animation: shine 8s linear infinite;
}
@keyframes shine {
  0% { background-position: 0% 50%; }
  100% { background-position: -200% 50%; }
}
.animate-pan { animation: pan 40s linear infinite; }
@keyframes pan {
  0% { transform: scale(1.15) translate(0,0); }
  50% { transform: scale(1.18) translate(-2%, -2%); }
  100% { transform: scale(1.15) translate(0,0); }
}
.animated-grid {
  background:
      linear-gradient(rgba(255,180,60,0.15) 1px, transparent 1px),
      linear-gradient(90deg, rgba(255,180,60,0.15) 1px, transparent 1px);
  background-size: 40px 40px;
  mask: linear-gradient(to bottom, transparent, black 25%, black 75%, transparent);
  animation: grid-move 25s linear infinite;
}
@keyframes grid-move {
  0% { background-position: 0 0, 0 0; }
  100% { background-position: 0 40px, 40px 0; }
}
.card-enter-from,
.card-leave-to { opacity: 0; transform: translateY(18px) scale(.96); }
.card-enter-active { transition: all .60s cubic-bezier(.16,.8,.43,1.01); }
.card-leave-active { transition: all .35s ease; }
.card-enter-to { opacity: 1; transform: translateY(0) scale(1); }
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
.animate-pop { animation: pop .8s .25s cubic-bezier(.34,1.56,.64,1) both; }
@keyframes pop {
  0% { opacity:0; transform:scale(.4) rotate(-8deg); }
  100% { opacity:1; transform:scale(1) rotate(0); }
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
@media (max-width: 640px) {
  .hero { min-height: 64vh; }
}
</style>