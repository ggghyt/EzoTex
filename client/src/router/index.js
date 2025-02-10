import { createRouter, createWebHistory } from 'vue-router'

//뷰
import driverLogin from '@/views/driver/driverLogin.vue';

//라우터
import driverRoutes from './driverRoute';

const router = createRouter({
  scrollBehavior() {
    return {top: 0};
  },
  history: createWebHistory(),
  routes: [
      {
        path: '/',
        redirect : {name: 'driver'}
      },
      {
        path: '/driver',
        name: 'driver',
        component: driverLogin,
        children : driverRoutes
      },
    ]
})


export default router
