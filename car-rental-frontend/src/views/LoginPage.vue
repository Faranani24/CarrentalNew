<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import * as auth from '@/utils/auth.js';

const email = ref('');
const password = ref('');
const loading = ref(false);
const error = ref('');
const router = useRouter();

const handleLogin = async () => {
  loading.value = true;
  error.value = '';

  try {
    const data = await auth.loginUser(email.value, password.value);
    router.push({ name: 'home' });
  } catch (err) {
    console.error(err);
    error.value = err.response?.data?.message || 'Login failed. Please try again.';
  } finally {
    loading.value = false;
  }
};
</script>
<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import * as auth from '@/utils/auth.js'; // use centralized auth.js

const email = ref('');
const password = ref('');
const loading = ref(false);
const error = ref('');
const router = useRouter();

const handleLogin = async () => {
  loading.value = true;
  error.value = '';

  try {
    // In this example, simulate login check (replace with real API if needed)
    const users = JSON.parse(localStorage.getItem('users') || '[]');
    const user = users.find(u => u.email.toLowerCase() === email.value.toLowerCase());

    if (user && user.password === password.value) {
      auth.saveToken(user.email); // store user session in auth.js
      router.push({ name: 'home' });
    } else {
      error.value = 'Invalid email or password';
    }
  } catch (err) {
    console.error('Login failed:', err);
    error.value = 'Login failed. Please try again.';
  } finally {
    loading.value = false;
  }
};
</script>
