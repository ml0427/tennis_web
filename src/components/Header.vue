<template>
  <el-header class="bg-white shadow">
    <div class="max-w-7xl mx-auto px-4 flex justify-between items-center h-full">
      <router-link to="/" class="text-2xl font-bold">網球教學平台</router-link>
      
      <div class="flex items-center gap-4">
        <template v-if="isAuthenticated">
          <el-dropdown>
            <span class="el-dropdown-link cursor-pointer flex items-center">
              {{ email }}
              <el-icon class="ml-1"><arrow-down /></el-icon>
            </span>
            
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item>
                  <router-link to="/profile">個人資料</router-link>
                </el-dropdown-item>
                <el-dropdown-item v-if="isCoach">
                  <router-link to="/coach/bookings">預約管理</router-link>
                </el-dropdown-item>
                <el-dropdown-item v-else>
                  <router-link to="/student/bookings">我的預約</router-link>
                </el-dropdown-item>
                <el-dropdown-item divided @click="handleLogout">
                  登出
                </el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </template>
        
        <template v-else>
          <router-link to="/login">
            <el-button type="primary">登入</el-button>
          </router-link>
          <router-link to="/register">
            <el-button>註冊</el-button>
          </router-link>
        </template>
      </div>
    </div>
  </el-header>
</template>

<script setup lang="ts">
import { ArrowDown } from '@element-plus/icons-vue'
import { useUserStore } from '@/store/user'
import { storeToRefs } from 'pinia'
import { email } from '@vuelidate/validators'

const userStore = useUserStore()
const { isAuthenticated, isCoach } = storeToRefs(userStore)

const handleLogout = () => {
  userStore.logout()
}
</script> 