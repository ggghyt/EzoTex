import driverLogin from "@/views/driver/driverLogin.vue";

const driverRoutes = [
    {
        path: '',
        redirect: { name : 'login' }
    },
    {
        path: 'login',
        name: 'login',
        component: driverLogin
    }
];

export default driverRoutes;