import { defineStore } from 'pinia'
import { getProfile, updateProfile } from '@/api/auth'

interface UserInfo {
  id: number;
  username: string;
  email: string;
  role: string;
  experience?: string;
  certificates?: string;
  specialties?: string;
  locations?: string;
  hourlyRate?: number;
  availableTime?: string;
}

interface UserState {
  userInfo: UserInfo | null;
}

export const useUserStore = defineStore('user', {
  state: (): UserState => ({
    userInfo: null
  }),
  
  actions: {
    async fetchUserInfo() {
      try {
        const response = await getProfile()
        this.userInfo = response.data
        return response.data
      } catch (error) {
        console.error('獲取用戶信息失敗:', error)
        this.userInfo = null
        throw error
      }
    },

    async updateProfile(data: Partial<UserInfo>) {
      const response = await updateProfile(data)
      this.userInfo = response.data
      return response.data
    }
  }
}) 