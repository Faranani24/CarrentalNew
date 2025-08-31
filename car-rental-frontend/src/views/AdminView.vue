<template>
  <div class="p-6">
    <h2 class="text-2xl font-bold mb-4">Admins</h2>

    <!-- Add Admin Form -->
    <form @submit.prevent="addAdmin" class="mb-4 flex gap-2">
      <input v-model="newAdmin.firstName" placeholder="First Name" required class="input"/>
      <input v-model="newAdmin.lastName" placeholder="Last Name" required class="input"/>
      <input v-model="newAdmin.email" placeholder="Email" type="email" required class="input"/>
      <button type="submit" class="btn">Add Admin</button>
    </form>

    <!-- Admin List -->
    <ul>
      <li v-for="admin in admins" :key="admin.id" class="flex justify-between items-center mb-2">
        <span>{{ admin.firstName }} {{ admin.lastName }} ({{ admin.email }})</span>
        <button @click="deleteAdmin(admin.id)" class="btn btn-red">Delete</button>
      </li>
    </ul>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import * as auth from '@/utils/auth.js';

const admins = ref([]);
const newAdmin = ref({ firstName: '', lastName: '', email: '' });

// Axios instance with auth token
const api = axios.create({
  baseURL: '/api',
  headers: { Authorization: `Bearer ${auth.getToken()}` }
});

// Fetch admins
const fetchAdmins = async () => {
  try {
    const res = await api.get('/admins');
    admins.value = res.data;
  } catch (err) {
    console.error(err);
  }
};

// Add new admin
const addAdmin = async () => {
  try {
    const res = await api.post('/admins', newAdmin.value);
    admins.value.push(res.data);
    newAdmin.value = { firstName: '', lastName: '', email: '' };
  } catch (err) {
    console.error(err);
  }
};

// Delete admin
const deleteAdmin = async (id) => {
  try {
    await api.delete(`/admins/${id}`);
    admins.value = admins.value.filter(a => a.id !== id);
  } catch (err) {
    console.error(err);
  }
};

onMounted(fetchAdmins);
</script>const fetchAdmins = async () => {
  try {
    const res = await api.getAdmins();
    admins.value = res.data;
  } catch (err) {
    console.error(err);
  }
};

// Add / Update
const submitAdmin = async () => {
  try {
    if (form.value.id) {
      await api.updateAdmin(form.value.id, form.value);
    } else {
      await api.addAdmin(form.value);
    }
    form.value = { id: null, name: '', email: '' };
    fetchAdmins();
  } catch (err) {
    console.error(err);
  }
};

// Edit
const editAdmin = (admin) => {
  form.value = { ...admin };
};

// Cancel edit
const cancelEdit = () => {
  form.value = { id: null, name: '', email: '' };
};

// Delete
const deleteAdmin = async (id) => {
  try {
    await api.deleteAdmin(id);
    fetchAdmins();
  } catch (err) {
    console.error(err);
  }
};

onMounted(fetchAdmins);
</script>

<style scoped>
/* same styles as before */
.container { max-width: 900px; margin: auto; padding: 2rem; }
h2 { margin-bottom: 1rem; color: #2c3e50; }
.form-card { display: flex; gap: 1rem; margin-bottom: 2rem; padding: 1rem; background: #f9f9f9; border-radius: 8px; }
.form-card input { flex: 1; padding: 0.5rem; }
.buttons button { margin-left: 0.5rem; padding: 0.5rem 1rem; cursor: pointer; }
.cards { display: flex; flex-wrap: wrap; gap: 1rem; }
.card { background: #fff; padding: 1rem; border-radius: 8px; flex: 1 1 250px; box-shadow: 0 2px 6px rgba(0,0,0,0.1); }
.card h3 { margin: 0 0 0.5rem; }
.card p { margin: 0 0 1rem; color: #555; }
</style>  try {
    const res = await axios.get('http://localhost:8080/api/admins');
    admins.value = res.data;
  } catch (err) {
    console.error(err);
  }
};

const submitAdmin = async () => {
  try {
    if (form.value.id) {
      await axios.put(`http://localhost:8080/api/admins/${form.value.id}`, form.value);
    } else {
      await axios.post('http://localhost:8080/api/admins', form.value);
    }
    form.value = { id: null, name: '', email: '' };
    fetchAdmins();
  } catch (err) {
    console.error(err);
  }
};

const editAdmin = (admin) => {
  form.value = { ...admin };
};

const cancelEdit = () => {
  form.value = { id: null, name: '', email: '' };
};

const deleteAdmin = async (id) => {
  try {
    await axios.delete(`http://localhost:8080/api/admins/${id}`);
    fetchAdmins();
  } catch (err) {
    console.error(err);
  }
};

onMounted(fetchAdmins);
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

const submitAdmin = async () => {
  try {
    if (form.value.id) {
      // Update existing admin
      await axios.put(`http://localhost:8080/api/admins/${form.value.id}`, form.value);
    } else {
      // Add new admin
      await axios.post('http://localhost:8080/api/admins', form.value);
    }
    form.value = { id: null, name: '', email: '' };
    fetchAdmins();
  } catch (err) {
    console.error(err);
  }
};

const editAdmin = (admin) => {
  form.value = { ...admin };
};

const cancelEdit = () => {
  form.value = { id: null, name: '', email: '' };
};

const deleteAdmin = async (id) => {
  try {
    await axios.delete(`http://localhost:8080/api/admins/${id}`);
    fetchAdmins();
  } catch (err) {
    console.error(err);
  }
};

onMounted(fetchAdmins);
</script>

<style scoped>
form {
  margin-bottom: 1rem;
}
button {
  margin-left: 10px;
}
</style>
