<template>
  <div>
    <h2>Admins</h2>

    <!-- Add / Edit Admin Form -->
    <form @submit.prevent="submitAdmin">
      <input v-model="form.name" placeholder="Name" required />
      <input v-model="form.email" placeholder="Email" required />
      <button type="submit">{{ form.id ? 'Update' : 'Add' }} Admin</button>
      <button v-if="form.id" type="button" @click="cancelEdit">Cancel</button>
    </form>

    <!-- Admin List -->
    <ul>
      <li v-for="admin in admins" :key="admin.id">
        {{ admin.name }} - {{ admin.email }}
        <button @click="editAdmin(admin)">Edit</button>
        <button @click="deleteAdmin(admin.id)">Delete</button>
      </li>
    </ul>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';

const admins = ref([]);
const form = ref({ id: null, name: '', email: '' });

const fetchAdmins = async () => {
  try {
    const res = await axios.get('http://localhost:8080/api/admins');
    admins.value = res.data;
  } catch (err) {
    console.error(err);
  }
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
