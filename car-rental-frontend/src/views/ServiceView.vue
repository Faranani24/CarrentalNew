<template>
  <div class="container">
    <h2>Services</h2>

    <!-- Add / Edit Form -->
    <form @submit.prevent="submitService" class="form-card">
      <input v-model="form.name" placeholder="Service Name" required />
      <input v-model.number="form.costPerDay" placeholder="Cost Per Day" required />
      <div class="buttons">
        <button type="submit">{{ form.id ? 'Update' : 'Add' }} Service</button>
        <button v-if="form.id" type="button" @click="cancelEdit">Cancel</button>
      </div>
    </form>

    <!-- Service List -->
    <div class="cards">
      <div v-for="service in services" :key="service.id" class="card">
        <h3>{{ service.name }}</h3>
        <p>Cost per day: ${{ service.costPerDay }}</p>
        <div class="buttons">
          <button @click="editService(service)">Edit</button>
          <button @click="deleteService(service.id)">Delete</button>
        </div>
      </div>
    </div>
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
      await axios.put(`http://localhost:8080/api/services/${form.value.id}`, form.value);
    } else {
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
.container {
  max-width: 900px;
  margin: auto;
  padding: 2rem;
}
h2 {
  margin-bottom: 1rem;
  color: #2c3e50;
}
.form-card {
  display: flex;
  gap: 1rem;
  margin-bottom: 2rem;
  padding: 1rem;
  background: #f9f9f9;
  border-radius: 8px;
}
.form-card input {
  flex: 1;
  padding: 0.5rem;
}
.buttons button {
  margin-left: 0.5rem;
  padding: 0.5rem 1rem;
  cursor: pointer;
}
.cards {
  display: flex;
  flex-wrap: wrap;
  gap: 1rem;
}
.card {
  background: #fff;
  padding: 1rem;
  border-radius: 8px;
  flex: 1 1 250px;
  box-shadow: 0 2px 6px rgba(0,0,0,0.1);
}
.card h3 {
  margin: 0 0 0.5rem;
}
.card p {
  margin: 0 0 1rem;
  color: #555;
}
</style>  }
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
