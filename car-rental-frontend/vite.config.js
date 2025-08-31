// vite.config.js
import { fileURLToPath, URL } from 'node:url'
import { defineConfig, loadEnv } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'

export default defineConfig(({ mode }) => {
  // Only VITE_* env vars are exposed to client by default
  const env = loadEnv(mode, process.cwd())
  //const devPort = Number(env.VITE_FRONTEND_PORT) || 5173
  const backendPort = env.VITE_BACKEND_PORT || 8082
  const backendHost = env.VITE_BACKEND_URL || `http://localhost:${backendPort}`

  return {
    base: './',
    plugins: [vue(), vueDevTools()],
    resolve: {
      alias: { '@': fileURLToPath(new URL('./src', import.meta.url)) }
    },
    server: {
      port: 5173,
      strictPort: true,
      proxy: {
        '/api': {
          target: backendHost,
          changeOrigin: true,
          timeout: 30000,
          configure: proxy => {
            proxy.on('error', err => console.error('[proxy] error:', err.message))
          }
        }
      }
    },
    build: { outDir: 'dist', emptyOutDir: true }
  }
})