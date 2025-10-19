<script setup>
const features = [
  {
    title: 'Easy Booking',
    description: 'Simple online platform to search and book vehicles in minutes'
  },
  {
    title: 'Wide Selection',
    description: 'Choose from a diverse fleet of well-maintained vehicles'
  },
  {
    title: 'Competitive Pricing',
    description: 'Transparent rates with no hidden charges'
  },
  {
    title: '24/7 Support',
    description: 'Professional customer service available round the clock'
  },
  {
    title: 'Flexible Terms',
    description: 'Customizable rental periods to suit your needs'
  },
  {
    title: 'Safe & Reliable',
    description: 'All vehicles regularly inspected and fully insured'
  }
]
</script>

<template>
  <div class="min-h-screen flex flex-col bg-gradient-to-b from-amber-50 via-white to-neutral-100">


    <!-- Main Content -->
    <main class="flex-1 py-20 px-6">
      <div class="mx-auto max-w-6xl">

        <!-- About Section -->
        <section class="mb-20">
          <div class="grid md:grid-cols-2 gap-12 items-center">
            <div>
              <h1 class="text-5xl font-bold text-neutral-900 mb-6">About CarRental</h1>
              <p class="text-xl text-neutral-600 mb-6 leading-relaxed">
                CarRental is a professional car rental service dedicated to providing reliable, affordable, and convenient transportation solutions to customers across multiple cities.
              </p>
              <p class="text-lg text-neutral-600 mb-4 leading-relaxed">
                Whether you need a vehicle for business travel, weekend getaways, or daily commuting, we offer a diverse fleet of well-maintained cars to meet your requirements. Our simple booking process and transparent pricing make renting a car hassle-free.
              </p>
              <p class="text-lg text-neutral-600 leading-relaxed">
                With years of industry experience, we're committed to delivering excellent customer service and ensuring every rental experience exceeds expectations.
              </p>
            </div>
            <div class="hidden md:block">
              <div class="bg-gradient-to-br from-amber-100 to-orange-100 rounded-2xl h-80 overflow-hidden">
                <img
                    src="/About.png"
                    alt="Luxury rental car"
                    class="w-full h-full object-cover"
                />
              </div>
            </div>
          </div>
        </section>

        <!-- Core Features -->
        <section class="mb-20">
          <h2 class="text-4xl font-bold text-neutral-900 mb-12 text-center">Why Choose Us</h2>
          <div class="grid md:grid-cols-3 gap-8">
            <div v-for="feature in features" :key="feature.title" class="bg-white border border-amber-100 rounded-xl p-8 hover:shadow-lg transition">
              <h3 class="text-xl font-bold text-neutral-900 mb-3">{{ feature.title }}</h3>
              <p class="text-neutral-600">{{ feature.description }}</p>
            </div>
          </div>
        </section>

        <!-- Get Started Section -->
        <section class="bg-gradient-to-r from-amber-400 via-orange-500 to-rose-400 rounded-2xl p-12 text-center">
          <h2 class="text-4xl font-bold text-white mb-4">Ready to Get Started?</h2>
          <p class="text-lg text-white/90 mb-8 max-w-2xl mx-auto">
            Browse our fleet and find the perfect vehicle for your needs. Book in minutes with our easy online platform.
          </p>
          <router-link to="/" class="inline-block px-8 py-3 bg-white text-orange-600 font-bold rounded-lg hover:scale-105 transition shadow-lg">
            Browse Cars Now
          </router-link>
        </section>

      </div>
    </main>
  </div>
</template>

<style scoped>
/* Simple styling */
</style>