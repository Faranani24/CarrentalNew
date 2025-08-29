<!-- src/views/HomePage.vue -->
<script setup>
import { ref, computed, onMounted, nextTick, onBeforeUnmount } from 'vue'
import { fetchCars } from '@/services/carService'

const cars = ref([])
const loading = ref(false)
const error = ref('')
const searching = ref(false)
const search = ref({ location: '', from: '', to: '' })
const abortRef = ref(null)

const hasSearchInput = computed(() =>
    !!(search.value.location || search.value.from || search.value.to)
)

const datesValid = computed(() => {
  if (!search.value.from || !search.value.to) return true
  return new Date(search.value.from) <= new Date(search.value.to)
})

const canSubmit = computed(() =>
    !loading.value && !searching.value && datesValid.value
)

function formatRate(val) {
  if (val == null) return ''
  return Intl.NumberFormat(undefined, { style: 'currency', currency: 'USD' }).format(val)
}

async function load(isSearch = false) {
  if (isSearch && !datesValid.value) return
  if (abortRef.value) abortRef.value.abort()
  abortRef.value = new AbortController()

  if (isSearch) searching.value = true
  loading.value = true
  error.value = ''

  try {
    const params = isSearch && hasSearchInput.value
        ? {
          location: search.value.location || undefined,
          from: search.value.from || undefined,
          to: search.value.to || undefined
        }
        : undefined

    const data = await fetchCars(params, { signal: abortRef.value.signal })
    cars.value = Array.isArray(data) ? data : []
    console.log(cars.value) // Add this line to log the car data
    await nextTick()
  } catch (e) {
    if (e.name === 'AbortError') return
    cars.value = []
    error.value = e?.message || 'Network Error'
    console.error('[cars] load failed:', e)
  } finally {
    loading.value = false
    searching.value = false
  }
}

function submitSearch(e) {
  e.preventDefault()
  load(true)
}

function retry() {
  load(false)
}

onMounted(() => {
  const t = setTimeout(() => load(false), 150)
  abortRef.value = { abort: () => clearTimeout(t) }
})

onBeforeUnmount(() => {
  if (abortRef.value) abortRef.value.abort()
})

const skeletonItems = computed(() => Array.from({ length: 6 }, (_, i) => i))
</script>

<template>
  <div class="min-h-screen flex flex-col bg-gradient-to-b from-amber-50 via-white to-neutral-100 text-neutral-900">
    <nav class="relative z-30 backdrop-blur-md/40 bg-white/70 border-b border-amber-200/60 shadow-sm">
      <div class="mx-auto max-w-6xl px-6 py-4 flex items-center justify-between">
        <div class="flex items-center gap-3">
          <div class="h-10 w-10 rounded-xl bg-gradient-to-br from-amber-400 to-orange-500 shadow-lg flex items-center justify-center font-black text-gray-900 tracking-tighter" aria-label="CarRental logo">
            CR
          </div>
          <h1 class="text-2xl font-bold tracking-tight">
            <span class="bg-gradient-to-r from-amber-500 via-orange-500 to-rose-400 bg-clip-text text-transparent drop-shadow-sm">CarRental</span>
          </h1>
        </div>
        <div class="flex items-center gap-4">
          <button type="button" class="group relative overflow-hidden px-5 py-2 rounded-lg font-medium bg-white border border-amber-200 text-neutral-800 hover:bg-amber-50 transition">
            <span class="relative z-10">Login</span>
            <span class="absolute inset-0 bg-gradient-to-r from-amber-300/0 via-amber-300/30 to-amber-300/0 translate-x-[-100%] group-hover:translate-x-[100%] transition-transform duration-700"></span>
          </button>
        </div>
      </div>
    </nav>

    <!-- Hero centered -->
    <section class="hero relative flex items-center">
      <div class="absolute inset-0">
        <div class="absolute inset-0 bg-[url('https://images.unsplash.com/photo-1503376780353-7e6692767b70?auto=format&fit=crop&w=1650&q=60')] bg-cover bg-center animate-pan opacity-50"></div>
        <div class="absolute inset-0 bg-gradient-to-b from-white/85 via-white/70 to-amber-100/60"></div>
        <div class="absolute inset-0 mix-blend-overlay bg-[radial-gradient(circle_at_30%_30%,rgba(255,180,60,0.20),transparent_60%)]"></div>
        <div class="absolute inset-0 animated-grid pointer-events-none opacity-40"></div>
      </div>

      <div class="relative z-10 w-full">
        <div class="mx-auto max-w-5xl px-6">
          <div class="text-center space-y-6 animate-fade-down mb-10">
            <h2 id="hero-title" class="text-4xl md:text-5xl font-extrabold tracking-tight leading-tight">
              <span class="inline-block hero-title-gradient-light shine">Find Your Perfect Ride</span>
            </h2>
            <p class="text-lg md:text-xl text-neutral-700 max-w-2xl mx-auto font-light">
              Premium vehicles. Transparent pricing. Effortless booking.
            </p>
          </div>

          <!-- Centered search card -->
          <form @submit="submitSearch"
                class="mx-auto max-w-3xl backdrop-blur-xl/30 bg-white/90 border border-amber-200/70 shadow-xl rounded-2xl p-6 md:p-8 grid gap-4 md:grid-cols-5 animate-fade-up"
                novalidate
                aria-describedby="search-hint"
                :aria-busy="searching">
            <div class="md:col-span-2">
              <label for="loc" class="text-xs uppercase tracking-wider text-neutral-600 font-medium">Pickup Location</label>
              <input id="loc" v-model.trim="search.location" type="text" placeholder="City or airport"
                     class="mt-1 w-full px-4 py-3 rounded-lg bg-white border border-amber-200 focus:border-amber-400 outline-none transition placeholder:text-neutral-400"
                     autocomplete="off">
            </div>
            <div>
              <label for="from" class="text-xs uppercase tracking-wider text-neutral-600 font-medium">From</label>
              <input id="from" v-model="search.from" type="date"
                     class="mt-1 w-full px-4 py-3 rounded-lg bg-white border border-amber-200 focus:border-amber-400 outline-none transition">
            </div>
            <div>
              <label for="to" class="text-xs uppercase tracking-wider text-neutral-600 font-medium">To</label>
              <input id="to" v-model="search.to" type="date"
                     :min="search.from || undefined"
                     class="mt-1 w-full px-4 py-3 rounded-lg bg-white border border-amber-200 focus:border-amber-400 outline-none transition">
            </div>
            <div class="flex items-end">
              <button type="submit"
                      class="group w-full relative overflow-hidden px-6 py-3 rounded-lg font-semibold tracking-wide bg-gradient-to-r from-amber-400 via-yellow-400 to-orange-500 text-gray-900 shadow-lg hover:scale-[1.02] active:scale-[0.98] transition disabled:opacity-60"
                      :disabled="!canSubmit">
                <span class="relative z-10 flex items-center justify-center gap-2">
                  <span v-if="searching" class="loader spinner size-4" aria-hidden="true"></span>
                  {{ searching ? 'Searching...' : 'Search' }}
                </span>
                <span class="absolute inset-0 opacity-0 group-hover:opacity-100 bg-gradient-to-r from-yellow-200 via-white/60 to-yellow-200 mix-blend-overlay transition"></span>
              </button>
            </div>
            <p id="search-hint" class="sr-only">Enter optional filters then press search.</p>
            <p v-if="!datesValid" class="md:col-span-5 text-xs text-rose-600 font-medium -mt-1">End date must be after start date.</p>
          </form>
        </div>
      </div>

      <div class="pointer-events-none absolute inset-x-0 bottom-0 h-24 bg-gradient-to-b from-transparent to-neutral-100"></div>
    </section>

    <!-- Main content centered -->
    <main class="relative z-10 flex-1 w-full">
      <div class="mx-auto max-w-5xl px-6 py-12">
        <div class="flex flex-col items-center gap-4 mb-10 text-center">
          <h3 class="text-2xl md:text-3xl font-bold tracking-tight flex items-center gap-3">
            <span class="inline-flex h-10 w-10 items-center justify-center rounded-full bg-gradient-to-br from-amber-400 to-orange-500 text-gray-900 font-black text-lg shadow-lg animate-pop" aria-hidden="true">
              <span>🚗</span>
            </span>
            <span class="gradient-text-light">Available Cars</span>
          </h3>
          <button @click="retry" :disabled="loading"
                  class="relative overflow-hidden px-5 py-2.5 rounded-lg font-medium border border-amber-200 bg-white hover:bg-amber-50 transition disabled:opacity-50"
                  aria-label="Refresh car list">
            <span v-if="loading" class="loader spinner mr-2" aria-hidden="true"></span>
            {{ loading ? 'Loading...' : 'Refresh' }}
          </button>
        </div>

        <p v-if="error" class="text-rose-600 font-medium mb-6 animate-shake flex items-center justify-center gap-3">
          <span>{{ error }}</span>
          <button @click="retry"
                  class="px-3 py-1 text-xs font-semibold rounded-md border border-rose-300 bg-white hover:bg-rose-50 transition">
            Retry
          </button>
        </p>

        <div v-if="loading && !error" class="grid gap-6 sm:grid-cols-2 lg:grid-cols-3 justify-center" aria-live="polite">
          <div v-for="n in skeletonItems" :key="n" class="rounded-xl overflow-hidden border border-amber-100 bg-white p-4 animate-pulse">
            <div class="h-40 rounded-lg bg-amber-100 mb-4"></div>
            <div class="h-4 w-3/4 bg-amber-100 rounded mb-2"></div>
            <div class="h-3 w-1/2 bg-amber-100 rounded mb-2"></div>
            <div class="h-3 w-2/3 bg-amber-100 rounded mb-4"></div>
            <div class="h-9 w-full bg-amber-100 rounded"></div>
          </div>
        </div>

        <transition-group name="card" tag="div"
                          class="grid gap-6 sm:grid-cols-2 lg:grid-cols-3 justify-center"
                          appear
                          v-if="!loading && !error && cars.length"
                          aria-live="polite">
          <div v-for="(c,i) in cars"
               :key="c.id || c.carId || i"
               class="group relative rounded-xl overflow-hidden border border-amber-200 bg-white shadow-sm hover:shadow-lg transition flex flex-col">
            <div class="relative h-44 overflow-hidden">
              <img :src="c.imageUrl || c.image_url || 'https://via.placeholder.com/400x200?text=No+Image'"
                   class="w-full h-full object-cover scale-110 group-hover:scale-100 transition-transform duration-700 ease-out"
                   :alt="`${c.make} ${c.model}`"
                   loading="lazy" />
              <div class="absolute inset-0 bg-gradient-to-b from-black/10 via-black/0 to-black/50"></div>
              <div class="absolute top-3 left-3 px-3 py-1 rounded-full text-xs font-semibold tracking-wide bg-white/80 backdrop-blur border border-amber-200 uppercase text-neutral-700">
                {{ c.status }}
              </div>
            </div>
            <div class="p-5 flex flex-col flex-1">
              <h4 class="font-semibold text-lg tracking-tight mb-1 text-neutral-900">
                {{ c.make }} {{ c.model }} <span class="text-sm text-neutral-500">({{ c.year }})</span>
              </h4>
              <p class="text-xs font-mono text-neutral-500 mb-1">Plate: {{ c.licensePlate }}</p>
              <p class="text-xs text-neutral-500 mb-4">ID: {{ c.carId || c.id }}</p>
              <div class="mt-auto">
                <p class="mb-4 text-2xl font-bold">
                  <span class="bg-gradient-to-r from-amber-500 via-orange-500 to-rose-500 bg-clip-text text-transparent">
                    {{ formatRate(c.dailyRate) }}
                  </span>
                  <span class="text-xs font-medium text-neutral-500"> /day</span>
                </p>
                <button type="button"
                        class="relative w-full overflow-hidden px-4 py-2.5 rounded-lg font-semibold tracking-wide bg-gradient-to-r from-amber-400 via-yellow-400 to-orange-500 text-gray-900 shadow group hover:shadow-amber-500/40 hover:scale-[1.015] active:scale-[0.97] transition">
                  <span class="relative z-10 flex items-center justify-center gap-1">
                    <span class="text-sm">Rent Now</span>
                    <span class="opacity-0 group-hover:opacity-100 translate-x-[-4px] group-hover:translate-x-0 transition-all text-base">→</span>
                  </span>
                  <span class="absolute inset-0 bg-gradient-to-r from-yellow-200/0 via-white/50 to-yellow-200/0 opacity-0 group-hover:opacity-30 transition"></span>
                </button>
              </div>
            </div>
          </div>
        </transition-group>

        <p v-else-if="!loading && !error" class="text-neutral-500 mt-8 text-center animate-fade-up">
          No cars found.
        </p>
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