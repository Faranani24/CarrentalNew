<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import * as auth from '@/utils/auth.js';

const firstName = ref('');
const lastName = ref('');
const username = ref('');
const email = ref('');
const password = ref('');
const loading = ref(false);
const error = ref('');
const success = ref('');
const router = useRouter();

const handleSignup = async () => {
  loading.value = true;
  error.value = '';
  success.value = '';

  try {
    const userData = {
      firstName: firstName.value,
      lastName: lastName.value,
      username: username.value,
      email: email.value,
      password: password.value
    };

    const res = await auth.signupUser(userData);
    success.value = 'Account created successfully! Logging in...';

    // Auto-login after signup
    await auth.loginUser(email.value, password.value);
    setTimeout(() => router.push({ name: 'home' }), 1000);

  } catch (err) {
    console.error(err);
    error.value = err.response?.data?.message || 'Signup failed. Please try again.';
  } finally {
    loading.value = false;
  }
};
</script>      email: email.value,
      password: password.value
    };

    users.push(newUser);
    localStorage.setItem('users', JSON.stringify(users));

    auth.saveToken(email.value); // auto-login after signup
    success.value = 'Account created successfully! Redirecting...';

    setTimeout(() => {
      router.push({ name: 'home' });
    }, 1500);

  } catch (err) {
    console.error('Signup failed:', err);
    error.value = 'Signup failed. Please try again.';
  } finally {
    loading.value = false;
  }
};
</script>          <input id="username" name="username" type="text" required
                 v-model="username"
                 class="relative block w-full px-3 py-2 border border-neutral-300 placeholder-neutral-500 text-neutral-900 rounded-md focus:outline-none focus:ring-amber-500 focus:border-amber-500 focus:z-10 sm:text-sm"
                 placeholder="Username"
          />
        </div>
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
          <input id="password" name="password" type="password" autocomplete="new-password" required
                 v-model="password"
                 class="relative block w-full px-3 py-2 border border-neutral-300 placeholder-neutral-500 text-neutral-900 rounded-md focus:outline-none focus:ring-amber-500 focus:border-amber-500 focus:z-10 sm:text-sm"
                 placeholder="Password"
          />
        </div>

        <!-- Error message -->
        <div v-if="error" class="text-center text-red-600 text-sm">
          {{ error }}
        </div>

        <!-- Success message -->
        <div v-if="success" class="text-center text-green-600 text-sm">
          {{ success }}
        </div>

        <button type="submit" :disabled="loading"
                class="group relative w-full flex justify-center py-2 px-4 border border-transparent text-sm font-medium rounded-md text-white bg-amber-600 hover:bg-amber-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-amber-500 disabled:opacity-50 disabled:cursor-not-allowed">
          {{ loading ? 'Creating account...' : 'Sign up' }}
        </button>
      </form>

      <p class="text-center text-sm text-neutral-600">
        Already have an account?
        <router-link to="/login" class="text-amber-600 hover:text-amber-700 font-medium">
          Log in
        </router-link>
      </p>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { AuthService } from '@/services/auth.js';

const firstName = ref('');
const lastName = ref('');
const username = ref('');
const email = ref('');
const password = ref('');
const loading = ref(false);
const error = ref('');
const success = ref('');
const router = useRouter();
const authService = new AuthService();

const handleSignup = async () => {
  loading.value = true;
  error.value = '';
  success.value = '';

  try {
    // Check if email already exists
    if (authService.emailExists(email.value)) {
      error.value = 'Email already exists. Please use a different email.';
      return;
    }

    // Create new user locally
    const userData = {
      firstName: firstName.value,
      lastName: lastName.value,
      username: username.value,
      email: email.value,
      password: password.value // Note: In production, this should be hashed
    };

    const newUser = authService.signup(userData);

    if (newUser) {
      success.value = 'Account created successfully! Redirecting...';
      console.log('Signup successful:', newUser);

      // Redirect to home page after successful signup and auto-login
      setTimeout(() => {
        router.push({ name: 'home' });
      }, 1500);
    } else {
      error.value = 'Failed to create account. Please try again.';
    }
  } catch (err) {
    console.error('Signup failed:', err);
    error.value = 'Signup failed. Please try again.';
  } finally {
    loading.value = false;
  }
};
</script>
