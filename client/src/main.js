import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import 'bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css'
import TuiGrid from "vue3-tui-grid"
import 'tui-grid/dist/tui-grid.css';
import "tui-date-picker/dist/tui-date-picker.css"; // use datepicker
const app = createApp(App);

app.use(router);
app.use(TuiGrid);
app.mount('#app');
