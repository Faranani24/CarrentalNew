// vite.config.js
import { fileURLToPath, URL } from 'node:url'
import { defineConfig, loadEnv } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'

export default defineConfig(({ mode }) => {
  const env = loadEnv(mode, process.cwd(), '')
  const devPort = Number(env.FRONTEND_PORT || 5173)
  const backendHost = env.BACKEND_URL || `http://localhost:${env.BACKEND_PORT || 8081}`
  return {
    base: './',
    plugins: [vue(), vueDevTools()],
    resolve: {
      alias: { '@': fileURLToPath(new URL('./src', import.meta.url)) }
    },
    server: {
      port: devPort,
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