<template>
  <nav>
    <router-link to="/">Home</router-link>

    <!-- Show only if logged in -->
    <template v-if="isAuthenticated">
      | <router-link to="/admins">Admins</router-link>
      | <router-link to="/services">Services</router-link>
      | <a href="#" @click.prevent="logout">Logout</a>
    </template>

    <!-- Show only if not logged in -->
    <template v-else>
      | <router-link to="/signup">Sign Up</router-link>
      | <router-link to="/login">Login</router-link>
    </template>
  </nav>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import * as auth from '@/utils/auth.js'; // centralized auth helpers

const router = useRouter();
const isAuthenticated = ref(false);

// Check auth status
const checkAuth = () => {
  isAuthenticated.value = auth.isAuthenticated();
};

// Logout
const logout = () => {
  auth.logout();
  checkAuth();
  router.push('/login');
};

onMounted(checkAuth);
</script>

<style scoped>
nav {
  padding: 1rem;
  background: #2c3e50;
}
nav a {
  color: white;
  margin: 0 10px;
  text-decoration: none;
}
nav a.router-link-active {
  font-weight: bold;
  border-bottom: 2px solid #42b983;
}
</style>
