<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import axios from 'axios'

const route = useRoute()
const router = useRouter()

const bookingId = computed(() => route.params.bookingId)
const totalCost = computed(() => parseFloat(route.query.totalCost) || 0)
const carDetails = computed(() => route.query.carDetails || 'Vehicle')

const paymentLoading = ref(false)
const error = ref('')

const promotions = ref([])
const promotionsLoading = ref(false)
const promotionsError = ref('')

const paymentDetails = ref({
  cardNumber: '',
  expiryDate: '',
  cvv: '',
  cardholderName: '',
  paymentMethod: 'credit_card'
})

const formatCardNumber = (value) => {
  const v = value.replace(/\s+/g, '').replace(/[^0-9]/gi, '')
  const match = v.match(/\d{4,16}/g)?.[0] || ''
  const parts = []
  for (let i = 0; i < match.length; i += 4) {
    parts.push(match.substring(i, i + 4))
  }
  return parts.length ? parts.join(' ') : v
}

const formatExpiryDate = (value) => {
  const v = value.replace(/\s+/g, '').replace(/[^0-9]/gi, '')
  if (v.length >= 2) {
    return v.substring(0, 2) + (v.length > 2 ? '/' + v.substring(2, 4) : '')
  }
  return v
}

const onCardNumberInput = (e) => {
  const formatted = formatCardNumber(e.target.value)
  paymentDetails.value.cardNumber = formatted
  e.target.value = formatted
}

const onExpiryInput = (e) => {
  const formatted = formatExpiryDate(e.target.value)
  paymentDetails.value.expiryDate = formatted
  e.target.value = formatted
}

const isFormValid = computed(() => {
  return paymentDetails.value.cardNumber.replace(/\s/g, '').length >= 13 &&
      paymentDetails.value.expiryDate.length === 5 &&
      paymentDetails.value.cvv.length >= 3 &&
      paymentDetails.value.cardholderName.trim().length > 0
})

async function processPayment() {
  if (!isFormValid.value) return
  paymentLoading.value = true
  error.value = ''

  try {
    await new Promise(resolve => setTimeout(resolve, 2000))
    if (Math.random() < 0.1) {
      throw new Error('Payment declined. Please check your card details.')
    }
    router.push({
      name: 'confirmation',
      params: { bookingId: bookingId.value },
      query: {
        totalCost: totalCost.value,
        carDetails: carDetails.value
      }
    })
  } catch (e) {
    error.value = e.message || 'Payment processing failed. Please try again.'
  } finally {
    paymentLoading.value = false
  }
}

async function fetchPromotions() {
  promotionsLoading.value = true
  promotionsError.value = ''
  try {
    await new Promise(resolve => setTimeout(resolve, 800));
    const mockPromotions = [
      { promotionId: 'promo_123', title: 'Weekend Special', description: '20% off all weekend rentals!', discountPercentage: 20 },
      { promotionId: 'promo_456', title: 'First-time Renter Discount', description: 'Save 10% on your first booking.', discountPercentage: 10 },
      { promotionId: 'promo_789', title: 'Summer Sale', description: 'Get a 15% discount on bookings in December.', discountPercentage: 15 }
    ];
    if (Math.random() < 0.1) {
      throw new Error('Server error: Failed to fetch promotions.');
    }
    promotions.value = mockPromotions;
  } catch (err) {
    promotionsError.value = err.message || 'Failed to load promotions. Please try again later.';
    console.error(err);
  } finally {
    promotionsLoading.value = false;
  }
}

function formatRate(val) {
  if (val == null) return ''
  return new Intl.NumberFormat('en-ZA', { style: 'currency', currency: 'ZAR' }).format(val)
}

onMounted(() => {
  console.log('Payment page loaded with:')
  console.log('bookingId:', bookingId.value)
  console.log('totalCost:', totalCost.value)
  console.log('carDetails:', carDetails.value)
  if (!bookingId.value) {
    console.warn('No booking ID found, redirecting to home')
    router.push({ name: 'home' })
  }
  fetchPromotions()
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
          <span class="gradient-text-light">Secure Payment</span>
        </h2>
        <div class="grid grid-cols-1 lg:grid-cols-2 gap-8">
          <div class="order-1 lg:order-2">
            <div class="backdrop-blur-xl/30 bg-white/90 border border-amber-200/70 shadow-xl rounded-2xl p-6 sticky top-6">
              <h3 class="text-xl font-bold mb-4 gradient-text-light">Order Summary</h3>
              <div class="space-y-3 mb-6">
                <div class="flex justify-between items-center text-sm">
                  <span class="text-neutral-600">Vehicle:</span>
                  <span class="font-medium">{{ carDetails }}</span>
                </div>
                <div class="flex justify-between items-center text-sm">
                  <span class="text-neutral-600">Booking ID:</span>
                  <span class="font-mono font-medium text-orange-500">{{ bookingId }}</span>
                </div>
                <div class="border-t border-amber-200 pt-3 mt-4">
                  <div class="flex justify-between items-center">
                    <span class="text-lg font-bold">Total Amount:</span>
                    <span class="text-2xl font-bold bg-gradient-to-r from-amber-500 via-orange-500 to-rose-500 bg-clip-text text-transparent">
                      {{ formatRate(totalCost) }}
                    </span>
                  </div>
                </div>
              </div>
              <div class="flex items-center justify-center gap-4 pt-4 border-t border-amber-200">
                <div class="flex items-center gap-2 text-xs text-neutral-500">
                  <svg class="w-4 h-4 text-green-500" fill="currentColor" viewBox="0 0 20 20">
                    <path fill-rule="evenodd" d="M5 9V7a5 5 0 0110 0v2a2 2 0 012 2v5a2 2 0 01-2 2H5a2 2 0 01-2-2v-5a2 2 0 012-2zm8-2v2H7V7a3 3 0 016 0z" clip-rule="evenodd"></path>
                  </svg>
                  <span>SSL Secured</span>
                </div>
                <div class="flex items-center gap-2 text-xs text-neutral-500">
                  <svg class="w-4 h-4 text-blue-500" fill="currentColor" viewBox="0 0 20 20">
                    <path fill-rule="evenodd" d="M2.166 4.999A11.954 11.954 0 0010 1.944 11.954 11.954 0 0017.834 5c.11.65.166 1.32.166 2.001 0 5.225-3.34 9.67-8 11.317C5.34 16.67 2 12.225 2 7c0-.682.057-1.35.166-2.001zm11.541 3.708a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clip-rule="evenodd"></path>
                  </svg>
                  <span>PCI Compliant</span>
                </div>
              </div>
            </div>
          </div>
          <div class="mt-8">
            <h3 class="text-xl font-bold mb-4 gradient-text-light">Available Promotions</h3>
            <div v-if="promotionsLoading" class="text-sm text-neutral-500">Loading promotions...</div>
            <p v-if="promotionsError" class="text-sm text-rose-600">{{ promotionsError }}</p>
            <ul v-if="!promotionsLoading && promotions.length" class="space-y-3">
              <li v-for="promo in promotions" :key="promo.promotionId"
                  class="border border-amber-200 rounded-lg p-4 shadow-sm bg-amber-50/70 hover:bg-amber-100/70 transition">
                <div class="flex justify-between items-center">
                  <div>
                    <p class="font-semibold text-amber-700">{{ promo.title }}</p>
                    <p class="text-xs text-neutral-600">{{ promo.description }}</p>
                  </div>
                  <span class="text-green-600 font-bold">
          -{{ promo.discountPercentage }}%
        </span>
                </div>
              </li>
            </ul>
            <p v-if="!promotionsLoading && !promotions.length" class="text-sm text-neutral-500">
              No promotions available at the moment.
            </p>
          </div>
          <div class="order-2 lg:order-1">
            <div class="backdrop-blur-xl/30 bg-white/90 border border-amber-200/70 shadow-xl rounded-2xl p-6 md:p-8">
              <form @submit.prevent="processPayment" class="space-y-6">
                <div>
                  <h4 class="text-lg font-semibold mb-4">Payment Method</h4>
                  <div class="grid grid-cols-2 gap-3">
                    <label class="relative cursor-pointer">
                      <input type="radio" v-model="paymentDetails.paymentMethod" value="credit_card" class="sr-only">
                      <div class="flex items-center justify-center p-4 border-2 rounded-lg transition"
                           :class="paymentDetails.paymentMethod === 'credit_card' ? 'border-amber-400 bg-amber-50' : 'border-amber-200 hover:border-amber-300'">
                        <svg class="w-6 h-6 mr-2" fill="currentColor" viewBox="0 0 24 24">
                          <path d="M2 4a2 2 0 012-2h16a2 2 0 012 2v16a2 2 0 01-2 2H4a2 2 0 01-2-2V4zm2 3v2h16V7H4zm0 4v6h16v-6H4z"></path>
                        </svg>
                        <span class="font-medium">Credit Card</span>
                      </div>
                    </label>
                    <label class="relative cursor-pointer">
                      <input type="radio" v-model="paymentDetails.paymentMethod" value="debit_card" class="sr-only">
                      <div class="flex items-center justify-center p-4 border-2 rounded-lg transition"
                           :class="paymentDetails.paymentMethod === 'debit_card' ? 'border-amber-400 bg-amber-50' : 'border-amber-200 hover:border-amber-300'">
                        <svg class="w-6 h-6 mr-2" fill="currentColor" viewBox="0 0 24 24">
                          <path d="M2 4a2 2 0 012-2h16a2 2 0 012 2v16a2 2 0 01-2 2H4a2 2 0 01-2-2V4zm2 3v2h16V7H4zm0 4v6h16v-6H4z"></path>
                        </svg>
                        <span class="font-medium">Debit Card</span>
                      </div>
                    </label>
                  </div>
                </div>
                <div class="space-y-4">
                  <div>
                    <label for="cardholderName" class="text-xs uppercase tracking-wider text-neutral-600 font-medium">Cardholder Name</label>
                    <input id="cardholderName" v-model="paymentDetails.cardholderName" type="text" required
                           class="mt-1 w-full px-4 py-3 rounded-lg bg-white border border-amber-200 focus:border-amber-400 outline-none transition placeholder:text-neutral-400"
                           placeholder="John Doe" />
                  </div>
                  <div>
                    <label for="cardNumber" class="text-xs uppercase tracking-wider text-neutral-600 font-medium">Card Number</label>
                    <input id="cardNumber" @input="onCardNumberInput" type="text" required maxlength="19"
                           class="mt-1 w-full px-4 py-3 rounded-lg bg-white border border-amber-200 focus:border-amber-400 outline-none transition placeholder:text-neutral-400 font-mono"
                           placeholder="1234 5678 9012 3456" />
                  </div>
                  <div class="grid grid-cols-2 gap-4">
                    <div>
                      <label for="expiryDate" class="text-xs uppercase tracking-wider text-neutral-600 font-medium">Expiry Date</label>
                      <input id="expiryDate" @input="onExpiryInput" type="text" required maxlength="5"
                             class="mt-1 w-full px-4 py-3 rounded-lg bg-white border border-amber-200 focus:border-amber-400 outline-none transition placeholder:text-neutral-400 font-mono"
                             placeholder="MM/YY" />
                    </div>
                    <div>
                      <label for="cvv" class="text-xs uppercase tracking-wider text-neutral-600 font-medium">CVV</label>
                      <input id="cvv" v-model="paymentDetails.cvv" type="text" required maxlength="4"
                             class="mt-1 w-full px-4 py-3 rounded-lg bg-white border border-amber-200 focus:border-amber-400 outline-none transition placeholder:text-neutral-400 font-mono"
                             placeholder="123" />
                    </div>
                  </div>
                </div>
                <p v-if="error" class="text-rose-600 font-medium text-sm animate-shake bg-rose-50 border border-rose-200 rounded-lg p-3">
                  {{ error }}
                </p>
                <div class="bg-green-50 border border-green-200 rounded-lg p-4">
                  <div class="flex items-start gap-3">
                    <svg class="w-5 h-5 text-green-500 mt-0.5 flex-shrink-0" fill="currentColor" viewBox="0 0 20 20">
                      <path fill-rule="evenodd" d="M5 9V7a5 5 0 0110 0v2a2 2 0 012 2v5a2 2 0 01-2 2H5a2 2 0 01-2-2v-5a2 2 0 012-2zm8-2v2H7V7a3 3 0 016 0z" clip-rule="evenodd"></path>
                    </svg>
                    <div>
                      <p class="text-sm font-medium text-green-800">Your payment is secure</p>
                      <p class="text-xs text-green-600 mt-1">All card details are encrypted and processed securely. We never store your payment information.</p>
                    </div>
                  </div>
                </div>
                <button type="submit"
                        class="w-full relative overflow-hidden px-6 py-4 rounded-lg font-semibold tracking-wide bg-gradient-to-r from-green-500 via-emerald-500 to-green-600 text-white shadow-lg hover:scale-[1.01] active:scale-[0.98] transition disabled:opacity-60 disabled:cursor-not-allowed"
                        :disabled="!isFormValid || paymentLoading">
                  <span class="relative z-10 flex items-center justify-center gap-2">
                    <span v-if="paymentLoading" class="loader spinner size-4" aria-hidden="true"></span>
                    <svg v-if="!paymentLoading" class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20">
                      <path fill-rule="evenodd" d="M5 9V7a5 5 0 0110 0v2a2 2 0 012 2v5a2 2 0 01-2 2H5a2 2 0 01-2-2v-5a2 2 0 012-2zm8-2v2H7V7a3 3 0 016 0z" clip-rule="evenodd"></path>
                    </svg>
                    {{ paymentLoading ? 'Processing Payment...' : `Pay ${formatRate(totalCost)}` }}
                  </span>
                </button>
              </form>
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
.animate-shake { animation: shake 0.5s ease-in-out; }
@keyframes shake { 0%, 100% { transform: translateX(0); } 25% { transform: translateX(-5px); } 75% { transform: translateX(5px); } }
.gradient-text-light {
  background: linear-gradient(90deg,#f59e0b,#fb923c,#f97316,#fbbf24);
  -webkit-background-clip: text;
  color: transparent;
  background-size: 300% 100%;
  animation: gradientShift 8s ease infinite;
}
@keyframes gradientShift { 0% { background-position: 0% 50%; } 50% { background-position: 100% 50%; } 100% { background-position: 0% 50%; } }
.loader.spinner {
  border: 3px solid rgba(255,255,255,0.3);
  border-top-color: #ffffff;
  border-radius: 50%;
  width: 1rem;
  height: 1rem;
  animation: spin .8s linear infinite;
}
@keyframes spin { to { transform: rotate(360deg); } }
input:focus { box-shadow: 0 0 0 3px rgba(251, 191, 36, 0.1); }
</style>