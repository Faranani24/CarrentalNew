<template>
  <div class="mx-auto max-w-xl px-6 w-full">
    <div class="bg-white/90 backdrop-blur-xl/30 border border-amber-200/70 shadow-2xl rounded-2xl p-6 md:p-8 animate-fade-up text-center">
      <h2 class="text-3xl md:text-4xl font-extrabold tracking-tight mb-4 gradient-text-light">
        How was your trip?
      </h2>
      <p class="text-lg text-neutral-600 mb-8">
        Please take a moment to rate and review your rental experience.
      </p>

      <div v-if="car" class="inline-flex items-center gap-4 mb-8 rounded-xl overflow-hidden bg-neutral-50 border border-amber-100 p-4 shadow-sm animate-pop">
        <img :src="car.image" class="w-24 h-24 object-cover rounded-lg" :alt="`${car.make} ${car.model}`" />
        <div class="flex-1 text-left">
          <p class="font-semibold text-lg">{{ car.make }} {{ car.model }}</p>
          <p class="text-neutral-500 text-sm">Reviewing your rental</p>
        </div>
      </div>

      <form @submit.prevent="submitReview" class="space-y-6 text-left">
        <div class="space-y-2">
          <label class="block text-sm uppercase tracking-wider text-neutral-600 font-medium">Your Rating</label>
          <div class="flex items-center gap-1.5 justify-center">
            <button v-for="n in 5" :key="n" @click="setRating(n)" type="button" class="p-2 transition-transform hover:scale-110 active:scale-95 text-yellow-400 focus:outline-none focus:ring-2 focus:ring-yellow-500 rounded-full"
                    :class="{'text-yellow-500 scale-110': review.rating >= n}">
              <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 24 24" fill="currentColor" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-star" :class="{'fill-yellow-400': review.rating >= n}">
                <polygon points="12 2 15.09 8.26 22 9.27 17 14.14 18.18 21.02 12 17.77 5.82 21.02 7 14.14 2 9.27 8.91 8.26 12 2"/>
              </svg>
            </button>
          </div>
        </div>

        <div class="space-y-2">
          <label for="review-comment" class="block text-sm uppercase tracking-wider text-neutral-600 font-medium">Your Review</label>
          <textarea id="review-comment" v-model="review.comment" rows="5" placeholder="Tell us about your experience..."
                    class="w-full px-4 py-3 rounded-lg bg-white border border-amber-200 focus:border-amber-400 outline-none transition placeholder:text-neutral-400" required></textarea>
        </div>

        <p v-if="error" class="text-rose-600 font-medium text-sm mt-2 animate-shake flex items-center justify-center gap-2">
          <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="12" cy="12" r="10"/><path d="M12 8v4"/><path d="M12 16h.01"/></svg>
          {{ error }}
        </p>
        <p v-if="success" class="text-emerald-600 font-medium text-sm mt-2 animate-pulse flex items-center justify-center gap-2">
          <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"/><polyline points="22 4 12 14.01 9 11.01"/></svg>
          {{ success }}
        </p>

        <button type="submit"
                class="group w-full relative overflow-hidden px-6 py-3 rounded-lg font-semibold tracking-wide bg-gradient-to-r from-amber-400 via-yellow-400 to-orange-500 text-gray-900 shadow-lg hover:scale-[1.02] active:scale-[0.98] transition disabled:opacity-60 disabled:cursor-not-allowed"
                :disabled="!canSubmit">
          <span class="relative z-10 flex items-center justify-center gap-2">
            <span v-if="processing" class="loader spinner size-4" aria-hidden="true"></span>
            {{ processing ? 'Submitting...' : 'Submit Review' }}
          </span>
          <span class="absolute inset-0 opacity-0 group-hover:opacity-100 bg-gradient-to-r from-yellow-200 via-white/60 to-yellow-200 mix-blend-overlay transition"></span>
        </button>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRoute } from 'vue-router'
import { fetchCarById } from '@/services/carService'
import { submitReview } from '@/services/reviewService'

const route = useRoute()
const car = ref(null)
const review = ref({ rating: 0, comment: '' })
const processing = ref(false)
const error = ref('')
const success = ref('')

const canSubmit = computed(() => {
  return !processing.value && review.value.rating > 0 && review.value.comment.length > 10
})

function setRating(rating) {
  review.value.rating = rating
}

async function fetchCar() {
  const carId = route.params.id;
  try {
    car.value = await fetchCarById(carId);
  } catch (err) {
    console.error("Failed to fetch car details:", err);
    error.value = "Failed to load car details. Please try again.";
  }
}

async function submitReview() {
  if (!canSubmit.value) return;

  processing.value = true;
  error.value = '';
  success.value = '';

  try {
    await submitReview({ carId: car.value.id, ...review.value });
    success.value = 'Review submitted successfully! Thank you!';
  } catch (e) {
    error.value = 'Failed to submit review. Please try again.';
  } finally {
    processing.value = false;
  }
}

fetchCar();
</script>

<style scoped>
.animate-fade-up { animation: fadeUp 1s cubic-bezier(.16,.8,.43,1) both; }
.animate-pop { animation: pop .8s .25s cubic-bezier(.34,1.56,.64,1) both; }

@keyframes fadeUp {
  0% { opacity:0; transform:translateY(30px) scale(.96); }
  100% { opacity:1; transform:translateY(0) scale(1); }
}
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
</style>