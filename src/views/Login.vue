<template>
  <div class="min-h-screen bg-gradient-to-r from-blue-500 to-indigo-600 flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8">
    <el-card class="w-full max-w-md shadow-xl">
      <template #header>
        <div class="text-center">
          <h2 class="text-3xl font-bold text-gray-900">網球教學平台</h2>
          <p class="mt-2 text-gray-600">歡迎回來，請登入您的帳號</p>
        </div>
      </template>
      
      <el-form 
        :model="loginForm" 
        :rules="rules" 
        ref="formRef"
        class="space-y-6"
      >
        <el-form-item prop="username">
          <el-input 
            v-model="loginForm.username"
            :prefix-icon="User"
            class="rounded-lg"
            placeholder="使用者名稱"
          />
        </el-form-item>
        
        <el-form-item prop="password">
          <el-input 
            v-model="loginForm.password"
            type="password"
            :prefix-icon="Lock"
            class="rounded-lg"
            placeholder="密碼"
            @keyup.enter="handleLogin"
          />
        </el-form-item>

        <div class="flex items-center justify-between">
          <el-checkbox v-model="rememberMe">記住我</el-checkbox>
          <a href="#" class="text-sm text-indigo-600 hover:text-indigo-500">
            忘記密碼？
          </a>
        </div>

        <el-button 
          type="primary" 
          class="w-full !bg-indigo-600 hover:!bg-indigo-700"
          :loading="loading"
          @click="handleLogin"
        >
          登入
        </el-button>

        <div class="text-center text-sm text-gray-600">
          還沒有帳號？
          <router-link to="/register" class="text-indigo-600 hover:text-indigo-500">
            立即註冊
          </router-link>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { User, Lock } from '@element-plus/icons-vue'
import type { FormInstance, FormRules } from 'element-plus'
import { ElMessage } from 'element-plus'
import { login } from '@/api/auth'

const router = useRouter()
const formRef = ref<FormInstance>()
const loading = ref(false)
const rememberMe = ref(false)

const loginForm = reactive({
  username: '',
  password: ''
})

const rules: FormRules = {
  username: [
    { required: true, message: '請輸入使用者名稱', trigger: 'blur' },
    { min: 3, message: '使用者名稱至少需要3個字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '請輸入密碼', trigger: 'blur' },
    { min: 6, message: '密碼至少需要6個字符', trigger: 'blur' }
  ]
}

const handleLogin = async () => {
  if (!formRef.value) return
  
  try {
    await formRef.value.validate()
    loading.value = true
    
    const response = await login({
      username: loginForm.username,
      password: loginForm.password
    })
    // 保存 token
    localStorage.setItem('token', response.data.token)
    ElMessage.success('登入成功')
    router.push('/profile')
  } catch (error: any) {
    ElMessage.error(error.response?.data || '登入失敗')
  } finally {
    loading.value = false
  }
}
</script> 