<template>
  <div class="car-list">
    <div
        v-for="car in cars"
        :key="car.carId"
        class="car-card"
    >
      <img
          :src="car.imageUrl || fallback"
          :alt="car.model || 'Car'"
          @error="onImgError"
      />
      <h3>{{ car.model || car.carId }}</h3>
      <p>Daily: {{ car.dailyRate }}</p>
    </div>
  </div>
</template>

<script setup>
import { defineProps } from 'vue';
import fallback from '@/assets/no-image.png';

defineProps({
  cars: {
    type: Array,
    required: true
  }
});

const onImgError = (e) => {
  e.target.src = fallback;
};
</script>

<style scoped>
.car-list { display: flex; flex-wrap: wrap; gap: .75rem; }
.car-card { border: 1px solid #ccc; padding: .5rem; width: 160px; }
.car-card img { width: 150px; height: 100px; object-fit: cover; background: #eee; }
</style>