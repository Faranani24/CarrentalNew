<script setup>
export default {
  name: "BranchesPage",
  data() {
    return {
      branches: [
        {
          id: 1,
          name: "Cape Town Central",
          address: "123 Long Street",
          city: "Cape Town",
          country: "South Africa",
          phone: "+27 21 123 4567",
        },
        {
          id: 2,
          name: "Johannesburg Airport",
          address: "456 Airport Road",
          city: "Johannesburg",
          country: "South Africa",
          phone: "+27 11 987 6543",
        },
        {
          id: 3,
          name: "Durban North",
          address: "789 Beach Drive",
          city: "Durban",
          country: "South Africa",
          phone: "+27 31 555 7890",
        },
      ],
    };
  },
};
</script>

<template>
  <div class="min-h-screen bg-gray-100 flex flex-col items-center py-12 px-6">
    <!-- Page Header -->
    <div class="w-full max-w-3xl bg-white rounded-2xl shadow-lg p-6 mb-8">
      <h1 class="text-2xl font-bold text-gray-800 text-center">Our Branches</h1>
      <p class="text-gray-500 text-center mt-2">
        Find the nearest branch to you for pickups and returns
      </p>
    </div>

    <!-- Branch Cards -->
    <div class="grid grid-cols-1 md:grid-cols-2 gap-6 w-full max-w-4xl">
      <div
          v-for="branch in branches"
          :key="branch.id"
          class="bg-white rounded-2xl shadow-md hover:shadow-xl transition-shadow p-6"
      >
        <h2 class="text-xl font-semibold text-gray-800">{{ branch.name }}</h2>
        <p class="text-gray-600 mt-2">{{ branch.address }}</p>
        <p class="text-gray-600">{{ branch.city }}, {{ branch.country }}</p>
        <p class="text-gray-600 mt-2"><strong>Phone:</strong> {{ branch.phone }}</p>
        <button
            class="mt-4 px-4 py-2 bg-indigo-600 text-white rounded-xl shadow hover:bg-indigo-700 transition"
        >
          View on Map
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* Optional - can reuse fonts/colors from Payment page if you had custom ones */
</style>
