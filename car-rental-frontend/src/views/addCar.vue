<script setup>
import { ref, onMounted } from 'vue';
import { addCar, fetchAllCars } from '@/services/carAdminService.js';

const car = ref({
  carId: '',
  make: '',
  model: '',
  year: null,
  licensePlate: '',
  dailyRate: null,
  status: 'available',
  carType: { typeId: 'SUV' }
});

const imageFile = ref(null);
const loading = ref(true);
const cars = ref([]);
const error = ref(null);
const successMessage = ref('');

const carTypes = [
  { typeId: 'SUV', name: 'SUV' },
  { typeId: 'SEDAN', name: 'Sedan' },
  { typeId: 'COUPE', name: 'Coupe' },
  { typeId: 'HATCHBACK', name: 'Hatchback' }
];

const handleImageUpload = (event) => {
  imageFile.value = event.target.files[0];
};

const fetchAndDisplayCars = async () => {
  loading.value = true;
  try {
    const fetchedCars = await fetchAllCars();
    cars.value = fetchedCars.map(car => ({
      ...car,
      imageUrl: car.image ? `data:image/jpeg;base64,${arrayBufferToBase64(car.image)}` : 'https://via.placeholder.com/150?text=No+Image'
    }));
  } catch (err) {
    error.value = 'Failed to fetch cars. Please check your backend connection.';
    console.error(err);
  } finally {
    loading.value = false;
  }
};

const arrayBufferToBase64 = (buffer) => {
  // Check if buffer is a string (base64 already) or an object
  if (typeof buffer === 'string') {
    return buffer;
  }
  let binary = '';
  const bytes = new Uint8Array(buffer);
  const len = bytes.byteLength;
  for (let i = 0; i < len; i++) {
    binary += String.fromCharCode(bytes[i]);
  }
  return window.btoa(binary);
};

const submitForm = async () => {
  try {
    await addCar(car.value, imageFile.value);
    successMessage.value = 'Car added successfully!';
    error.value = null;
    await fetchAndDisplayCars();
    // Reset form fields
    car.value = {
      carId: '', make: '', model: '', year: null, licensePlate: '', dailyRate: null, status: 'available', carType: { typeId: 'SUV' }
    };
    imageFile.value = null;
    document.getElementById('image-upload').value = '';
  } catch (err) {
    successMessage.value = '';
    error.value = err.message;
    console.error(err);
  }
};

// Placeholder for formatRate function to prevent errors
const formatRate = (value) => {
  if (value === null || value === undefined) {
    return 'N/A';
  }
  return new Intl.NumberFormat('en-ZA', { style: 'currency', currency: 'ZAR' }).format(value);
};


onMounted(() => {
  fetchAndDisplayCars();
});
</script>

<template>
  <div class="min-h-screen flex flex-col bg-neutral-100 p-8">
    <div class="max-w-4xl mx-auto w-full">
      <!-- The h1 heading has been moved to AdminPanel.vue to follow best practice. -->

      <!-- Add Car Form -->
      <section class="bg-white rounded-xl shadow-lg p-6 mb-8 border border-neutral-200">
        <h2 class="text-2xl font-semibold mb-6 text-neutral-700">Add New Car</h2>
        <form @submit.prevent="submitForm" class="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div class="flex flex-col">
            <label for="carId" class="text-sm font-medium text-neutral-600 mb-1">Car ID</label>
            <input id="carId" v-model="car.carId" type="text" required class="form-input">
          </div>
          <div class="flex flex-col">
            <label for="make" class="text-sm font-medium text-neutral-600 mb-1">Make</label>
            <input id="make" v-model="car.make" type="text" required class="form-input">
          </div>
          <div class="flex flex-col">
            <label for="model" class="text-sm font-medium text-neutral-600 mb-1">Model</label>
            <input id="model" v-model="car.model" type="text" required class="form-input">
          </div>
          <div class="flex flex-col">
            <label for="year" class="text-sm font-medium text-neutral-600 mb-1">Year</label>
            <input id="year" v-model.number="car.year" type="number" required class="form-input">
          </div>
          <div class="flex flex-col">
            <label for="licensePlate" class="text-sm font-medium text-neutral-600 mb-1">License Plate</label>
            <input id="licensePlate" v-model="car.licensePlate" type="text" required class="form-input">
          </div>
          <div class="flex flex-col">
            <label for="dailyRate" class="text-sm font-medium text-neutral-600 mb-1">Daily Rate</label>
            <input id="dailyRate" v-model.number="car.dailyRate" type="number" step="0.01" required class="form-input">
          </div>
          <div class="flex flex-col">
            <label for="status" class="text-sm font-medium text-neutral-600 mb-1">Status</label>
            <select id="status" v-model="car.status" class="form-input">
              <option value="available">Available</option>
              <option value="booked">Booked</option>
              <option value="maintenance">Maintenance</option>
            </select>
          </div>
          <div class="flex flex-col">
            <label for="carType" class="text-sm font-medium text-neutral-600 mb-1">Car Type</label>
            <select id="carType" v-model="car.carType.typeId" class="form-input">
              <option v-for="type in carTypes" :key="type.typeId" :value="type.typeId">{{ type.name }}</option>
            </select>
          </div>
          <div class="flex flex-col md:col-span-2">
            <label for="image-upload" class="text-sm font-medium text-neutral-600 mb-1">Car Image</label>
            <input id="image-upload" type="file" @change="handleImageUpload" required class="form-input file:mr-4 file:py-2 file:px-4 file:rounded-full file:border-0 file:text-sm file:font-semibold file:bg-orange-50 file:text-orange-600 hover:file:bg-orange-100 cursor-pointer">
          </div>

          <div class="md:col-span-2 flex justify-end">
            <button type="submit" class="px-6 py-3 bg-neutral-900 text-white rounded-lg font-semibold tracking-wide hover:bg-neutral-700 transition">
              Add Car
            </button>
          </div>
        </form>
        <div v-if="successMessage" class="mt-4 text-green-600 font-semibold">{{ successMessage }}</div>
        <div v-if="error" class="mt-4 text-rose-600 font-semibold">{{ error }}</div>
      </section>

      <!-- Cars Table -->
      <section class="bg-white rounded-xl shadow-lg p-6 border border-neutral-200">
        <h2 class="text-2xl font-semibold mb-6 text-neutral-700">All Cars</h2>
        <div v-if="loading" class="text-center text-neutral-500">Loading cars...</div>
        <div v-else-if="cars.length === 0" class="text-center text-neutral-500">No cars found. Add some using the form above.</div>
        <div v-else class="overflow-x-auto">
          <table class="min-w-full divide-y divide-neutral-200">
            <thead class="bg-neutral-50">
            <tr>
              <th class="px-4 py-2 text-left text-xs font-medium text-neutral-500 uppercase">Image</th>
              <th class="px-4 py-2 text-left text-xs font-medium text-neutral-500 uppercase">Car ID</th>
              <th class="px-4 py-2 text-left text-xs font-medium text-neutral-500 uppercase">Make</th>
              <th class="px-4 py-2 text-left text-xs font-medium text-neutral-500 uppercase">Model</th>
              <th class="px-4 py-2 text-left text-xs font-medium text-neutral-500 uppercase">Year</th>
              <th class="px-4 py-2 text-left text-xs font-medium text-neutral-500 uppercase">Daily Rate</th>
              <th class="px-4 py-2 text-left text-xs font-medium text-neutral-500 uppercase">Status</th>
            </tr>
            </thead>
            <tbody class="bg-white divide-y divide-neutral-200">
            <tr v-for="carItem in cars" :key="carItem.id">
              <td class="px-4 py-2 whitespace-nowrap"><img :src="carItem.imageUrl" :alt="carItem.make" class="w-16 h-12 object-cover rounded-md shadow-sm"></td>
              <td class="px-4 py-2 whitespace-nowrap text-sm text-neutral-700">{{ carItem.carId }}</td>
              <td class="px-4 py-2 whitespace-nowrap text-sm text-neutral-700">{{ carItem.make }}</td>
              <td class="px-4 py-2 whitespace-nowrap text-sm text-neutral-700">{{ carItem.model }}</td>
              <td class="px-4 py-2 whitespace-nowrap text-sm text-neutral-700">{{ carItem.year }}</td>
              <td class="px-4 py-2 whitespace-nowrap text-sm text-neutral-700">{{ formatRate(carItem.dailyRate) }}</td>
              <td class="px-4 py-2 whitespace-nowrap">
                  <span :class="{'bg-green-100 text-green-800': carItem.status === 'available', 'bg-red-100 text-red-800': carItem.status === 'booked'}" class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full capitalize">
                    {{ carItem.status }}
                  </span>
              </td>
            </tr>
            </tbody>
          </table>
        </div>
      </section>
    </div>
  </div>

</template>

<style scoped>
.form-input {
  @apply px-3 py-2 border border-neutral-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-amber-500 transition duration-150;
}
</style>
