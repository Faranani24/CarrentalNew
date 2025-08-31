<template>
  <div>
    <h2>Admins</h2>

    <!-- Add Admin Form -->
    <form @submit.prevent="addAdmin">
      <input v-model="newAdmin.name" placeholder="Name" required />
      <input v-model="newAdmin.email" placeholder="Email" required />
      <button type="submit">Add Admin</button>
    </form>

    <!-- Admin List -->
    <ul>
      <li v-for="admin in admins" :key="admin.id">
        {{ admin.name }} - {{ admin.email }}
        <button @click="deleteAdmin(admin.id)">Delete</button>
      </li>
    </ul>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';

const admins = ref([]);
const newAdmin = ref({ name: '', email: '' });

const fetchAdmins = async () => {
  try {
    const res = await axios.get('http://localhost:8080/api/admins');
    admins.value = res.data;
  } catch (err) {
    console.error(err);
  }
};

const addAdmin = async () => {
  try {
    await axios.post('http://localhost:8080/api/admins', newAdmin.value);
    newAdmin.value = { name: '', email: '' };
    fetchAdmins();
  } catch (err) {
    console.error(err);
  }
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
/* Simple styling */
form {
  margin-bottom: 1rem;
}
button {
  margin-left: 10px;
}
</style>
