<template>
  <div class="min-h-screen bg-gray-50">
    <el-menu 
      mode="horizontal" 
      class="border-b shadow-sm bg-white"
      :router="true"
    >
      <el-menu-item index="/">
        <template #title>
          <span class="flex items-center">
            <el-icon><HomeFilled /></el-icon>
            <span class="ml-1">首頁</span>
          </span>
        </template>
      </el-menu-item>
      
      <el-menu-item index="/coaches">
        <template #title>
          <span class="flex items-center">
            <el-icon><UserFilled /></el-icon>
            <span class="ml-1">教練列表</span>
          </span>
        </template>
      </el-menu-item>

      <div class="flex-grow" />

      <template v-if="!isLoggedIn">
        <el-menu-item index="/login">
          <template #title>
            <span class="flex items-center">
              <el-icon><Key /></el-icon>
              <span class="ml-1">登入</span>
            </span>
          </template>
        </el-menu-item>
        <el-menu-item index="/register">
          <template #title>
            <span class="flex items-center">
              <el-icon><Plus /></el-icon>
              <span class="ml-1">註冊</span>
            </span>
          </template>
        </el-menu-item>
      </template>
      
      <template v-else>
        <el-menu-item index="/profile">
          <template #title>
            <span class="flex items-center">
              <el-icon><User /></el-icon>
              <span class="ml-1">個人資料</span>
            </span>
          </template>
        </el-menu-item>
        <el-menu-item @click="handleLogout">
          <template #title>
            <span class="flex items-center text-red-500">
              <el-icon><SwitchButton /></el-icon>
              <span class="ml-1">登出</span>
            </span>
          </template>
        </el-menu-item>
      </template>
    </el-menu>

    <router-view class="p-4" />
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import {
  HomeFilled,
  UserFilled,
  Key,
  Plus,
  User,
  SwitchButton
} from '@element-plus/icons-vue'
import { useUserStore } from '@/store/user'

const router = useRouter()
const isLoggedIn = computed(() => !!localStorage.getItem('token'))
const userStore = useUserStore()

const handleLogout = () => {
  localStorage.removeItem('token')
  sessionStorage.removeItem('token')
  ElMessage.success('已成功登出')
  router.push('/login')
}

onMounted(async () => {
  if (userStore.isAuthenticated) {
    try {
      await userStore.fetchUserInfo()
    } catch (error) {
      console.error('Failed to fetch user info:', error)
    }
  }
})
</script>

<style>
.flex-grow {
  flex: 1;
}
</style> 