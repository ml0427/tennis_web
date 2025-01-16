import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import Register from '../views/Register.vue'
import CoachesList from '../views/CoachesList.vue'
import Profile from '../views/Profile.vue'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/register',
    name: 'Register',
    component: Register
  },
  {
    path: '/coaches',
    name: 'CoachesList',
    component: CoachesList,
    meta: { requiresAuth: true }
  },
  {
    path: '/profile',
    name: 'Profile',
    component: Profile,
    meta: { requiresAuth: true }
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

import { useUserStore } from '../store/user'



router.beforeEach(async (to, from, next) => {
  const requiresAuth = to.matched.some(record => record.meta.requiresAuth)
  const userStore = useUserStore()

  if (requiresAuth) {
    try {
      // 嘗試獲取用戶信息，如果 session 無效會拋出錯誤
      if (!userStore.user) {
        await userStore.fetchUserInfo()
      }
      next()
    } catch (error) {
      userStore.isAuthenticated = false
      next('/login')
    }
  } else {
    next()
  }
})

export default router
