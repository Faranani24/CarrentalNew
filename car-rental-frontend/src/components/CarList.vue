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
        <th>Picture</th>
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
        <td>
          <img
              :src="c.imageUrl || c.image_url || 'https://via.placeholder.com/100x60?text=No+Image'"
              alt="Car image"
              style="width: 100px; height: 60px; object-fit: cover; border-radius: 6px;"
          />
        </td>
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

<script setup>
import { ref, onMounted } from 'vue'

const loading = ref(false)
const error = ref('')
const cars = ref([])

function formatRate(rate) {
  return rate ? `R${rate}` : ''
}

async function load() {
  loading.value = true
  error.value = ''
  try {
    // Replace the URL below with your actual API endpoint
    const response = await fetch('/api/cars')
    if (!response.ok) throw new Error('Failed to fetch cars')
    cars.value = await response.json()
  } catch (e) {
    error.value = e.message || 'Failed to load cars'
    cars.value = []
  } finally {
    loading.value = false
  }
}

onMounted(load)
</script>

<style scoped>
.car-list {
  max-width: 1000px;
  margin: 0 auto;
  padding: 1rem;
}
.toolbar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 1rem;
}
.err {
  color: #c00;
  margin-bottom: 1rem;
}
table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 1rem;
}
th, td {
  padding: 0.5rem;
  border: 1px solid #ddd;
  text-align: left;
}
th {
  background: #f5f5f5;
}
</style>