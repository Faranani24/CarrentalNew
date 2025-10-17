<script setup>
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { fetchCarById, submitReview } from '@/services/carService';

const route = useRoute();
const router = useRouter();

const car = ref(null);
const loading = ref(true);
const submitting = ref(false);
const error = ref('');

const review = ref({
  rating: 0,
  comment: ''
});

const canSubmit = ref(false);


function setRating(rating) {
  review.value.rating = rating;
  updateCanSubmit();
}

function updateCanSubmit() {
  canSubmit.value = review.value.rating > 0 && review.value.comment.trim() !== '' && !submitting.value;
}

async function submitReviewForm() {
  if (!canSubmit.value) return;

  submitting.value = true;
  error.value = '';

  try {
    const reviewData = {
      carId: car.value.id,
      rating: review.value.rating,
      comment: review.value.comment,
    };
    await submitReview(reviewData);

    router.push({ name: 'review-success' });

  } catch (e) {
    error.value = e?.message || 'Failed to submit review. Please try again.';
    console.error('[ReviewPage] submission failed:', e);
  } finally {
    submitting.value = false;
  }
}

onMounted(async () => {
  const carId = route.params.id;
  if (!carId) {
    router.push({ name: 'home' });
    return;
  }

  loading.value = true;
  error.value = '';
  try {
    car.value = await fetchCarById(carId);
  } catch (e) {
    error.value = e.message || 'Failed to load car details for review.';
    console.error('[ReviewPage] load failed:', e);
  } finally {
    loading.value = false;
  }
});
</script>

<template>
  <div class="min-h-screen flex flex-col bg-gradient-to-b from-amber-50 via-white to-neutral-100 text-neutral-900">
    <nav class="relative z-30 backdrop-blur-md/40 bg-white/70 border-b border-amber-200/60 shadow-sm">
      <div class="mx-auto max-w-6xl px-6 py-4 flex items-center justify-between">
        <router-link to="/" class="flex items-center gap-3">
          <div class="h-10 w-10 rounded-xl bg-gradient-to-br from-amber-400 to-orange-500 shadow-lg flex items-center justify-center font-black text-gray-900 tracking-tighter">CR</div>
          <h1 class="text-2xl font-bold tracking-tight">
            <span class="bg-gradient-to-r from-amber-500 via-orange-500 to-rose-400 bg-clip-text text-transparent drop-shadow-sm">CarRental</span>
          </h1>
        </router-link>
      </div>
    </nav>
    <main class="relative z-10 flex-1 w-full py-12">
      <div class="mx-auto max-w-2xl px-6">
        <h2 class="text-3xl md:text-4xl font-extrabold tracking-tight text-center mb-10">
          <span class="gradient-text-light">How was your ride?</span>
        </h2>

        <div v-if="loading" class="text-center p-8">
          <div class="loader spinner size-8 mx-auto"></div>
          <p class="mt-4 text-neutral-500">Loading car details...</p>
        </div>

        <div v-else-if="error" class="text-center p-8 text-rose-600 font-medium animate-shake">
          <p>{{ error }}</p>
        </div>

        <div v-else-if="car" class="backdrop-blur-xl/30 bg-white/90 border border-amber-200/70 shadow-xl rounded-2xl p-6 md:p-8 space-y-6">
          <div class="flex items-center gap-4 border-b border-amber-200 pb-4">
            <img :src="car.imageUrl" alt="Car" class="h-24 w-24 rounded-lg object-cover shadow" />
            <div>
              <h3 class="font-semibold text-lg">{{ car.make }} {{ car.model }} ({{ car.year }})</h3>
              <p class="text-sm text-neutral-500">{{ car.description }}</p>
            </div>
          </div>

          <form @submit.prevent="submitReviewForm" class="space-y-6">
            <div>
              <label class="text-sm uppercase tracking-wider text-neutral-600 font-medium block mb-2">Your Rating</label>
              <div class="flex items-center gap-2">
                <span v-for="n in 5" :key="n" @click="setRating(n)" class="cursor-pointer text-2xl transition"
                      :class="{'text-amber-400': n <= review.rating, 'text-gray-300 hover:text-amber-300': n > review.rating}">
                  <i class="fas fa-star"></i>
                </span>
              </div>
            </div>

            <div>
              <label for="comment" class="text-sm uppercase tracking-wider text-neutral-600 font-medium block mb-2">Your Review</label>
              <textarea id="comment" v-model="review.comment" @input="updateCanSubmit" required
                        class="w-full h-32 p-4 rounded-lg bg-white border border-amber-200 focus:border-amber-400 outline-none transition placeholder:text-neutral-400 resize-none"
                        placeholder="Tell us about your experience..."
              ></textarea>
            </div>

            <p v-if="error" class="text-rose-600 font-medium text-sm animate-shake">{{ error }}</p>

            <button type="submit" :disabled="!canSubmit"
                    class="w-full relative overflow-hidden px-6 py-3 rounded-lg font-semibold tracking-wide bg-gradient-to-r from-amber-400 via-yellow-400 to-orange-500 text-gray-900 shadow-lg hover:scale-[1.01] active:scale-[0.98] transition disabled:opacity-60 disabled:cursor-not-allowed">
              <span class="relative z-10 flex items-center justify-center gap-2">
                <span v-if="submitting" class="loader spinner size-4"></span>
                {{ submitting ? 'Submitting...' : 'Submit Review' }}
              </span>
            </button>
          </form>
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
  Remember to copy the shared Tailwind CSS animations/utilities
  from your other files here.
*/
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

.animate-shake {
  animation: shake 0.82s cubic-bezier(.36,.07,.19,.97) both;
  transform: translate3d(0, 0, 0);
  backface-visibility: hidden;
  perspective: 1000px;
}
@keyframes shake {
  10%, 90% { transform: translate3d(-1px, 0, 0); }
  20%, 80% { transform: translate3d(2px, 0, 0); }
  30%, 50%, 70% { transform: translate3d(-4px, 0, 0); }
  40%, 60% { transform: translate3d(4px, 0, 0); }
}
</style>