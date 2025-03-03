import driverLogin from "@/views/driverLogin.vue";
import driverMenu from "@/views/driverMenu.vue";
import modalTest from "@/views/modalTest.vue";

const driverRoutes = [
    {
        path: '',
        redirect: { name : 'driveList' }
    },
    {
        path: 'login',
        name: 'login',
        component: driverLogin
    },
    {
        path: 'driveList',
        name: 'driveList',
        component: driverMenu
    },
    {
        path: 'modalTest',
        name: 'modalTest',
        component: modalTest
    }
];

export default driverRoutes;