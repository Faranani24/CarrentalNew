<template>
  <div class="flex items-center justify-center min-h-screen bg-neutral-100">
    <div class="w-full max-w-md p-8 space-y-8 bg-white rounded-xl shadow-lg">
      <h2 class="text-3xl font-bold text-center text-neutral-900">Sign up</h2>
      <form @submit.prevent="handleSignup" class="space-y-6">
        <div>
          <label for="email" class="sr-only">Email address</label>
          <input id="email" name="email" type="email" autocomplete="email" required
                 v-model="email"
                 class="relative block w-full px-3 py-2 border border-neutral-300 placeholder-neutral-500 text-neutral-900 rounded-md focus:outline-none focus:ring-amber-500 focus:border-amber-500 focus:z-10 sm:text-sm"
                 placeholder="Email address"
          />
        </div>
        <div>
          <label for="password" class="sr-only">Password</label>
          <input id="password" name="password" type="password" autocomplete="current-password" required
                 v-model="password"
                 class="relative block w-full px-3 py-2 border border-neutral-300 placeholder-neutral-500 text-neutral-900 rounded-md focus:outline-none focus:ring-amber-500 focus:border-amber-500 focus:z-10 sm:text-sm"
                 placeholder="Password"
          />
        </div>
        <button type="submit"
                class="group relative w-full flex justify-center py-2 px-4 border border-transparent text-sm font-medium rounded-md text-white bg-amber-600 hover:bg-amber-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-amber-500">
          Sign up
        </button>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';

const email = ref('');
const password = ref('');
const router = useRouter();

const handleSignup = async () => {
  try {
    const response = await axios.post('http://localhost:8082/api/auth/signup', {
      email: email.value,
      password: password.value,
    });
    console.log('Signup successful:', response.data);
    router.push({ name: 'login' }); // Redirect to login page after successful signup
  } catch (error) {
    console.error('Signup failed:', error);
    // You could add an error message to the UI here
  }
};
</script>