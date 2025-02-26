import { createRouter, createWebHistory } from 'vue-router'


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
        children : driverRoutes
      },
    ]
})


export default router
