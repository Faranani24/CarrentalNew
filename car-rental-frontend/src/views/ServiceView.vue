<template>
  <div>
    <h2>Services</h2>

    <!-- Add / Edit Service Form -->
    <form @submit.prevent="submitService">
      <input v-model="form.name" placeholder="Service Name" required />
      <input v-model.number="form.costPerDay" placeholder="Cost Per Day" required />
      <button type="submit">{{ form.id ? 'Update' : 'Add' }} Service</button>
      <button v-if="form.id" type="button" @click="cancelEdit">Cancel</button>
    </form>

    <!-- Service List -->
    <ul>
      <li v-for="service in services" :key="service.id">
        {{ service.name }} - ${{ service.costPerDay }}
        <button @click="editService(service)">Edit</button>
        <button @click="deleteService(service.id)">Delete</button>
      </li>
    </ul>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';

const services = ref([]);
const form = ref({ id: null, name: '', costPerDay: 0 });

const fetchServices = async () => {
  try {
    const res = await axios.get('http://localhost:8080/api/services');
    services.value = res.data;
  } catch (err) {
    console.error(err);
  }
};

const submitService = async () => {
  try {
    if (form.value.id) {
      // Update existing service
      await axios.put(`http://localhost:8080/api/services/${form.value.id}`, form.value);
    } else {
      // Add new service
      await axios.post('http://localhost:8080/api/services', form.value);
    }
    form.value = { id: null, name: '', costPerDay: 0 };
    fetchServices();
  } catch (err) {
    console.error(err);
  }
};

const editService = (service) => {
  form.value = { ...service };
};

const cancelEdit = () => {
  form.value = { id: null, name: '', costPerDay: 0 };
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
