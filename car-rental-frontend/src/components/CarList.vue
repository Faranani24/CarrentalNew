<!-- File: src/components/CarList.vue -->
<script setup>
import { ref, onMounted } from 'vue'
import { fetchCars } from '../services/carService'

const cars = ref([])
const loading = ref(false)
const error = ref('')

function formatRate(val) {
  if (val == null) return ''
  return Intl.NumberFormat(undefined, { style: 'currency', currency: 'USD' }).format(val)
}

async function load() {
  error.value = ''
  loading.value = true
  try {
    const data = await fetchCars()
    cars.value = Array.isArray(data) ? data : []
  } catch (e) {
    cars.value = []
    error.value = e?.message || 'Failed to load cars'
  } finally {
    loading.value = false
  }
}

onMounted(load)
</script>

<template>
  <section class="car-list" :aria-busy="loading ? 'true' : 'false'">
    <header class="toolbar">
      <h2>Cars</h2>
      <button @click="load" :disabled="loading">
        {{ loading ? 'Loading...' : 'Refresh' }}
      </button>
    </header>

    <p v-if="error" class="err">{{ error }}</p>

    <table v-if="cars.length && !error">
      <thead>
      <tr>
        <th>ID</th>
        <th>Car ID</th>
        <th>Make</th>
        <th>Model</th>
        <th>Year</th>
        <th>Plate</th>
        <th>Status</th>
        <th>Type</th>
        <th>Rate</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="c in cars" :key="c.id ?? c.carId">
        <td>{{ c.id }}</td>
        <td>{{ c.carId }}</td>
        <td>{{ c.make }}</td>
        <td>{{ c.model }}</td>
        <td>{{ c.year }}</td>
        <td>{{ c.licensePlate }}</td>
        <td>{{ c.status }}</td>
        <td>{{ c.carTypeName || c.carTypeId }}</td>
        <td>{{ formatRate(c.dailyRate) }}</td>
      </tr>
      </tbody>
    </table>

    <p v-else-if="!loading && !error">No cars found.</p>
  </section>
</template>

<style scoped>
.car-list { margin-top: 0.5rem; }
.toolbar { display: flex; align-items: center; gap: 1rem; }
.err { color: red; }
table { border-collapse: collapse; margin-top: 0.75rem; width: 100%; }
th, td { border: 1px solid #ccc; padding: 4px 8px; font-size: 14px; text-align: left; }
button { padding: 4px 10px; font-size: 14px; }
</style>