<template>
  <div class="admin-panel">
    <h2 class="main-title">Add New Car</h2>

    <!-- Success & Error Messages -->
    <p v-if="successMessage" class="success">{{ successMessage }}</p>
    <p v-if="error" class="error">{{ error }}</p>

    <form @submit.prevent="submitForm">
      <div class="form-group">
        <label>Car ID</label>
        <input v-model="car.carId" placeholder="e.g. BMW123" />
      </div>
      <div class="form-group">
        <label>Make</label>
        <input v-model="car.make" placeholder="e.g. BMW" />
      </div>
      <div class="form-group">
        <label>Model</label>
        <input v-model="car.model" placeholder="e.g. X5" />
      </div>
      <div class="form-group">
        <label>Year</label>
        <input v-model="car.year" type="number" placeholder="e.g. 2025" />
      </div>
      <div class="form-group">
        <label>License Plate</label>
        <input v-model="car.licensePlate" placeholder="e.g. CAA123" />
      </div>
      <div class="form-group">
        <label>Daily Rate</label>
        <input v-model="car.dailyRate" type="number" placeholder="e.g. 350" />
      </div>
      <div class="form-group">
        <label>Status</label>
        <select v-model="car.status">
          <option value="AVAILABLE">Available</option>
          <option value="RENTED">Rented</option>
          <option value="MAINTENANCE">Maintenance</option>
        </select>
      </div>
      <div class="form-group">
        <label>Car Type</label>
        <select v-model="car.carType.typeId">
          <option value="T001">SUV</option>
          <option value="T002">Sedan</option>
          <option value="T003">Coupe</option>
          <option value="T004">Hatchback</option>
        </select>
      </div>
      <div class="form-group" style="flex-basis:100%;">
        <label>Description</label>
        <textarea v-model="car.description" placeholder="e.g. Family car"></textarea>
      </div>
      <div class="form-group">
        <label>Car Image</label>
        <input type="file" @change="handleImageUpload" ref="fileInputRef" />
      </div>
      <div class="form-actions">
        <button type="button" class="btn-orange" @click="resetForm">Reset</button>
        <button type="submit" class="btn-orange">Add Car</button>
      </div>
    </form>

    <hr />

    <h2>All Cars</h2>
    <button @click="fetchAndDisplayCars">Refresh</button>

    <table>
      <thead>
      <tr>
        <th>Image</th>
        <th>Car ID</th>
        <th>Make</th>
        <th>Model</th>
        <th>Year</th>
        <th>Daily Rate</th>
        <th>Status</th>
        <th>Car Type</th>
        <th>Actions</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="c in cars" :key="c.id">
        <td>
          <img v-if="c.imageUrl" :src="c.imageUrl" alt="Car Image" width="80" />
          <span v-else>No Image</span>
        </td>
        <td>{{ c.carId }}</td>
        <td>{{ c.make }}</td>
        <td>{{ c.model }}</td>
        <td>{{ c.year }}</td>
        <td>R {{ c.dailyRate.toFixed(2) }}</td>
        <td>{{ c.status }}</td>
        <td>{{ c.carType?.name || c.carType?.typeId }}</td>
        <td>
          <button class="btn-orange" @click="removeCar(c.carId)">Delete</button>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { addCar, fetchAllCars, deleteCar } from '@/services/carAdminService.js';

const car = ref(createEmptyCar());
const imageFile = ref(null);
const fileInputRef = ref(null);
const cars = ref([]);
const error = ref('');
const successMessage = ref('');

function createEmptyCar() {
  return {
    carId: '',
    make: '',
    model: '',
    year: null,
    licensePlate: '',
    dailyRate: null,
    status: 'AVAILABLE',
    carType: { typeId: 'T001' },
    description: ''
  };
}

const handleImageUpload = (e) => {
  const file = e.target?.files?.[0] || null;
  imageFile.value = file;
};

const fetchAndDisplayCars = async () => {
  try {
    const fetched = await fetchAllCars();
    cars.value = fetched.map((c) => {
      const imageUrl = `http://localhost:8082/api/cars/${c.carId}/image`;
      return { ...c, imageUrl };
    });
  } catch (e) {
    error.value = 'Failed to fetch cars. Please check your backend.';
    console.error(e);
  }
};


function arrayBufferToBase64(buffer) {
  if (!buffer) return '';
  if (typeof buffer === 'string') return buffer;
  if (buffer.data && Array.isArray(buffer.data)) {
    buffer = Uint8Array.from(buffer.data).buffer;
  }
  let binary = '';
  const bytes = new Uint8Array(buffer);
  for (let i = 0; i < bytes.byteLength; i++)
    binary += String.fromCharCode(bytes[i]);
  return window.btoa(binary);
}

const resetForm = () => {
  car.value = createEmptyCar();
  imageFile.value = null;
  if (fileInputRef.value) fileInputRef.value.value = '';
};

const submitForm = async () => {
  error.value = '';
  successMessage.value = '';

  if (!car.value.carId.trim()) {
    error.value = 'Car ID is required.';
    return;
  }
  if (!imageFile.value) {
    error.value = 'Please select an image.';
    return;
  }

  car.value.year = car.value.year ? Number(car.value.year) : null;
  car.value.dailyRate = car.value.dailyRate ? Number(car.value.dailyRate) : null;

  try {
    const created = await addCar(car.value, imageFile.value);
    successMessage.value = `Car "${created.make} ${created.model}" added successfully!`;
    resetForm();
    await fetchAndDisplayCars();
  } catch (e) {
    console.error(e);
    error.value =
        e?.response?.data?.message || e.message || 'Failed to add car.';
  }
};

const removeCar = async (carId) => {
  error.value = '';
  successMessage.value = '';
  try {
    await deleteCar(carId);
    successMessage.value = `Car with ID "${carId}" deleted successfully!`;
    await fetchAndDisplayCars();
  } catch (e) {
    error.value = e?.response?.data?.message || e.message || 'Failed to delete car.';
    console.error(e);
  }
};

onMounted(fetchAndDisplayCars);
</script>


<style>
.admin-panel {
  max-width: 900px;
  margin: 32px auto;
  padding: 24px;
  background: #fafbfc;
  border-radius: 12px;
  box-shadow: 0 2px 8px #0001;
}

.main-title {
  text-align: center;
  font-size: 2.2rem;
  font-weight: 700;
  color: #ff9800;
  letter-spacing: 1px;
  margin-bottom: 24px;
  margin-top: 0;
}

form {
  display: flex;
  flex-wrap: wrap;
  gap: 24px;
  margin-bottom: 24px;
}

.form-group {
  flex: 1 1 320px;
  display: flex;
  flex-direction: column;
}

form label {
  font-weight: 500;
  margin-bottom: 6px;
}

form input,
form select,
form textarea {
  padding: 8px;
  border: 1px solid #bbb;
  border-radius: 4px;
  font-size: 1rem;
}

form textarea {
  min-height: 60px;
  resize: vertical;
}

.form-actions {
  flex-basis: 100%;
  display: flex;
  gap: 12px;
  margin-top: 8px;
}

.btn-orange {
  background: linear-gradient(90deg, #ff9800 0%, #ffb74d 100%);
  color: #fff;
  border: none;
  border-radius: 6px;
  padding: 10px 24px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  transition: background 0.2s, box-shadow 0.2s;
  box-shadow: 0 2px 6px #ff980033;
}

.btn-orange:hover,
.btn-orange:focus {
  background: linear-gradient(90deg, #fb8c00 0%, #ffa726 100%);
  box-shadow: 0 4px 12px #ff980055;
  outline: none;
}

.success {
  color: #1a7f37;
  margin-bottom: 8px;
}

.error {
  color: #d32f2f;
  margin-bottom: 8px;
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 16px;
  background: #fff;
  border-radius: 8px;
  overflow: hidden;
}

th,
td {
  border: 1px solid #e0e0e0;
  padding: 10px;
  text-align: left;
}

th {
  background: #f5f5f5;
}

@media (max-width: 700px) {
  form {
    flex-direction: column;
    gap: 12px;
  }
  .form-group {
    flex-basis: 100%;
  }
}
</style>
