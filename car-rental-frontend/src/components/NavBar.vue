<!-- src/components/NavBar.vue -->
<template>
  <nav>
    <router-link to="/">Home</router-link> |
    <router-link to="/admins">Admins</router-link> |
    <router-link to="/services">Services</router-link>
  </nav>
</template>

<script setup>
</script>

<style>
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
