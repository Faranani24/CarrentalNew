<template>
  <div class="p-6">
    <h2 class="text-2xl font-bold mb-4">Services</h2>

    <!-- Add Service Form -->
    <form @submit.prevent="addService" class="mb-4 flex gap-2">
      <input v-model="newService.name" placeholder="Service Name" required class="input"/>
      <input v-model.number="newService.costPerDay" type="number" placeholder="Cost per Day" required class="input"/>
      <button type="submit" class="btn">Add Service</button>
    </form>

    <!-- Service List -->
    <ul>
      <li v-for="service in services" :key="service.id" class="flex justify-between items-center mb-2">
        <span>{{ service.name }} - ${{ service.costPerDay }}</span>
        <button @click="deleteService(service.id)" class="btn btn-red">Delete</button>
      </li>
    </ul>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import * as auth from '@/utils/auth.js';

const services = ref([]);
const newService = ref({ name: '', costPerDay: 0 });

// Axios instance with auth token
const api = axios.create({
  baseURL: '/api',
  headers: { Authorization: `Bearer ${auth.getToken()}` }
});

// Fetch services
const fetchServices = async () => {
  try {
    const res = await api.get('/services');
    services.value = res.data;
  } catch (err) {
    console.error(err);
  }
};

// Add new service
const addService = async () => {
  try {
    const res = await api.post('/services', newService.value);
    services.value.push(res.data);
    newService.value = { name: '', costPerDay: 0 };
  } catch (err) {
    console.error(err);
  }
};

// Delete service
const deleteService = async (id) => {
  try {
    await api.delete(`/services/${id}`);
    services.value = services.value.filter(s => s.id !== id);
  } catch (err) {
    console.error(err);
  }
};

onMounted(fetchServices);
</script>
