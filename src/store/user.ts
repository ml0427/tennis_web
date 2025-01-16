import { defineStore } from 'pinia'
import api from '@/api'
import router from '@/router'

// 定義用戶狀態介面
interface UserState {
  user: any | null
  isAuthenticated: boolean
}

export const useUserStore = defineStore('user', {
  // 初始狀態
  state: (): UserState => ({
    user: null,
    isAuthenticated: false
  }),

  // 定義操作方法
  actions: {
    // 用戶登入
    async login(username: string, password: string) {
      try {
        // 準備登入參數
        const params = new URLSearchParams();
        params.append('username', username);
        params.append('password', password);
        // 發送登入請求
        await api.post('/api/auth/login', params, {
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
          }
        })
        
        this.isAuthenticated = true
        await this.fetchUserInfo() // 登入成功後獲取用戶資料
        router.push('/profile')  // 導向個人資料頁
      } catch (error: any) {
        console.error('Login failed:', error)
        // 處理錯誤訊息
        if (error.response?.data?.message) {
          throw new Error(error.response.data.message)
        } else {
          throw new Error('登入失敗，請稍後再試')
        }
      }
    },

    // 用戶登出
    async logout() {
      try {
        await api.post('/api/auth/logout')
        // 清除用戶狀態
        this.user = null
        this.isAuthenticated = false
        router.push('/login')  // 導向登入頁
      } catch (error: any) {
        console.error('Logout failed:', error)
        throw error
      }
    },

    // 獲取用戶資料
    async fetchUserInfo() {
      try {
        const response = await api.get('/api/users/profile')
        this.user = response.data
        return response.data
      } catch (error: any) {
        console.error('Fetch user info failed:', error)
        throw error
      }
    },

    // 更新用戶資料
    async updateProfile(data: any) {
      try {
        const response = await api.put('/api/users/profile', data)
        this.user = response.data
        return response.data
      } catch (error: any) {
        console.error('Update profile failed:', error)
        throw error
      }
    },

    // 用戶註冊
    async register(data: { email: string; password: string; role: string }) {
      try {
        await api.post('/api/auth/register', data)
      } catch (error: any) {
        console.error('Register failed:', error)
        throw error
      }
    }
  },

  // 定義 getter 方法
  getters: {
    isCoach: (state) => state.user?.role === 'ROLE_COACH',  // 檢查用戶是否為教練
    email: (state) => state.user?.email  // 獲取用戶電子郵件
  }
})
