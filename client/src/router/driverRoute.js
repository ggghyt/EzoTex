import driverLogin from "@/views/driverLogin.vue";
import driverMenu from "@/views/driverMenu.vue";
import modalTest from "@/views/modalTest.vue";

const driverRoutes = [
    {
        path: '',
        redirect: { name : 'login' }
    },
    {
        path: 'login',
        name: 'login',
        component: driverLogin
    },
    {
        path: 'deliveryList',
        name: 'deliveryList',
        component: driverMenu
    },
    {
        path: 'modalTest',
        name: 'modalTest',
        component: modalTest
    }
];

export default driverRoutes;