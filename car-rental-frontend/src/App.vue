<!-- src/App.vue -->
<template>
  <div id="app">
    <NavBar />       <!-- Navigation bar -->
    <router-view />  <!-- Routed pages -->
  </div>
</template>

<script setup>
import NavBar from './components/NavBar.vue';
</script>

<style>
/* Optional global App styles */
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  color: #2c3e50;
  min-height: 100vh;
  margin: 0;
}
</style>
