<template>
  <div>
    <h2>Services</h2>

    <!-- Add Service Form -->
    <form @submit.prevent="addService">
      <input v-model="newService.name" placeholder="Service Name" required />
      <input v-model.number="newService.costPerDay" placeholder="Cost Per Day" required />
      <button type="submit">Add Service</button>
    </form>

    <!-- Service List -->
    <ul>
      <li v-for="service in services" :key="service.id">
        {{ service.name }} - ${{ service.costPerDay }}
        <button @click="deleteService(service.id)">Delete</button>
      </li>
    </ul>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';

const services = ref([]);
const newService = ref({ name: '', costPerDay: 0 });

const fetchServices = async () => {
  try {
    const res = await axios.get('http://localhost:8080/api/services');
    services.value = res.data;
  } catch (err) {
    console.error(err);
  }
};

const addService = async () => {
  try {
    await axios.post('http://localhost:8080/api/services', newService.value);
    newService.value = { name: '', costPerDay: 0 };
    fetchServices();
  } catch (err) {
    console.error(err);
  }
};

const deleteService = async (id) => {
  try {
    await axios.delete(`http://localhost:8080/api/services/${id}`);
    fetchServices();
  } catch (err) {
    console.error(err);
  }
};

onMounted(fetchServices);
</script>

<style scoped>
form {
  margin-bottom: 1rem;
}
button {
  margin-left: 10px;
}
</style>
