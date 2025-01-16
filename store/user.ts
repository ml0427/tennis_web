import { defineStore } from 'pinia'

export const useUserStore = defineStore('user', {
  state: () => ({
    userInfo: null
  }),
  
  actions: {
    setUserInfo(userInfo: any) {
      this.userInfo = userInfo
    },
    
    async fetchUserInfo() {
      try {
        const response = await fetch('/api/user/info')
        const data = await response.json()
        this.setUserInfo(data)
      } catch (error) {
        console.error('獲取用戶資料失敗:', error)
      }
    }
  }
})
