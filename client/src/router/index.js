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
        path: '/delivery',
        name: 'delivery',
        children : driverRoutes
      },
    ]
})


export default router
