<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()

const bookingId = computed(() => route.params.bookingId)

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
      <div class="mx-auto max-w-xl px-6">
        <div class="backdrop-blur-xl/30 bg-white/90 border border-amber-200/70 shadow-xl rounded-2xl p-8 md:p-12 text-center">
          <div class="flex flex-col items-center">
            <div class="h-16 w-16 rounded-full bg-emerald-500 flex items-center justify-center mb-6 animate-pop">
              <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="white" class="w-8 h-8">
                <path fill-rule="evenodd" d="M19.916 4.626a.75.75 0 0 1 .208 1.04l-9.5 13.5a.75.75 0 0 1-1.12.083L4.25 12.25a.75.75 0 0 1 1.06-1.06l3.012 3.011 8.766-12.495a.75.75 0 0 1 1.04-.208Z" clip-rule="evenodd" />
              </svg>
            </div>
            <h2 class="text-3xl md:text-4xl font-extrabold tracking-tight mb-4">
              <span class="gradient-text-light">Booking Confirmed!</span>
            </h2>
            <p class="text-neutral-600 max-w-md mx-auto mb-6">
              Thank you! Your car rental booking has been successfully created.
            </p>
            <p class="text-lg font-bold text-neutral-800 mb-6">
              Booking ID: <span class="text-orange-500">{{ bookingId }}</span>
            </p>
            <router-link to="/" class="group relative overflow-hidden px-6 py-3 rounded-lg font-semibold tracking-wide bg-gradient-to-r from-amber-400 via-yellow-400 to-orange-500 text-gray-900 shadow-lg hover:scale-[1.01] active:scale-[0.98] transition">
              <span class="relative z-10">Back to Homepage</span>
              <span class="absolute inset-0 opacity-0 group-hover:opacity-100 bg-gradient-to-r from-yellow-200 via-white/60 to-yellow-200 mix-blend-overlay transition"></span>
            </router-link>
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
@import './HomePage.vue';
</style>