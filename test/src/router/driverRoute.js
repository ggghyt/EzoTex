import driverLogin from "@/views/driverLogin.vue";
import driverMenu from "@/views/driverMenu.vue";

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