import { createRouter, createWebHistory } from 'vue-router'

const routes = [
    {
        path: '/',
        name: 'Home',
        component: () => import(/* webpackChunkName: "Home" */ '../views/Home')
    },
    {
        path: '/logout',
        name: 'Logout',
        component: () => import(/* webpackChunkName: "register" */ '../views/Logout')
    },
    {
        path: '/register',
        name: 'Register',
        component: () => import(/* webpackChunkName: "register" */ '../views/Register')
    },
    {
        path: '/login',
        name: 'Login',
        component: () => import(/* webpackChunkName: "login" */ '../views/Login')
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router
