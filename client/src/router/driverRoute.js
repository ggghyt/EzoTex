import driverLogin from "@/views/driver/driverLogin.vue";
import driverMenu from "@/views/driver/driverMenu.vue";

const driverRoutes = [
    {
        path: '',
        redirect: { name : 'login' }
    },
    {
        path: '/login',
        name: 'login',
        component: driverLogin
    },
    {
        path: '/deliveryList',
        name: 'deliveryList',
        component: driverMenu
    }
];

export default driverRoutes;