import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import 'bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'tui-grid/dist/tui-grid.css';
import "tui-pagination/dist/tui-pagination.css";
import ToastService from 'primevue/toastservice';
const app = createApp(App);

app.use(ToastService);
app.use(router);
app.mount('#app');
