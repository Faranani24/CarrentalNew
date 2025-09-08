import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import './assets/main.css'; // This is the correct line

const app = createApp(App);
app.use(router);

app.mount('#app');