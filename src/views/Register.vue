<template>
  <div class="min-h-[calc(100vh-64px)] bg-gradient-to-r from-blue-500 to-indigo-600 flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8">
    <el-card class="w-full max-w-md shadow-xl">
      <template #header>
        <div class="text-center">
          <h2 class="text-3xl font-bold text-gray-900">註冊帳號</h2>
          <p class="mt-2 text-gray-600">加入我們的網球教學平台</p>
        </div>
      </template>
      
      <el-form 
        :model="form" 
        :rules="rules" 
        ref="formRef"
        class="space-y-6"
      >
        <el-form-item prop="username">
          <el-input 
            v-model="form.username"
            :prefix-icon="User"
            placeholder="使用者名稱"
          />
        </el-form-item>

        <el-form-item prop="email">
          <el-input 
            v-model="form.email"
            :prefix-icon="Message"
            placeholder="電子郵件"
          />
        </el-form-item>
        
        <el-form-item prop="password">
          <el-input 
            v-model="form.password"
            type="password"
            :prefix-icon="Lock"
            placeholder="密碼"
          />
        </el-form-item>

        <el-form-item prop="role">
          <el-select 
            v-model="form.role" 
            placeholder="選擇身份"
            class="w-full"
          >
            <el-option label="學生" value="ROLE_STUDENT" />
            <el-option label="教練" value="ROLE_COACH" />
          </el-select>
        </el-form-item>

        <el-button 
          type="primary" 
          class="w-full"
          :loading="loading"
          @click="handleRegister"
        >
          註冊
        </el-button>

        <div class="text-center text-sm text-gray-600">
          已有帳號？
          <router-link to="/login" class="text-indigo-600 hover:text-indigo-500">
            立即登入
          </router-link>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { User, Message, Lock } from '@element-plus/icons-vue'
import type { FormInstance, FormRules } from 'element-plus'
import { ElMessage } from 'element-plus'
import { register } from '@/api/auth'

const router = useRouter()
const formRef = ref<FormInstance>()
const loading = ref(false)

const form = reactive({
  username: '',
  email: '',
  password: '',
  role: 'ROLE_STUDENT'
})

const rules: FormRules = {
  username: [
    { required: true, message: '請輸入使用者名稱', trigger: 'blur' },
    { min: 3, message: '使用者名稱至少需要3個字符', trigger: 'blur' }
  ],
  email: [
    { required: true, message: '請輸入電子郵件', trigger: 'blur' },
    { type: 'email', message: '請輸入有效的電子郵件地址', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '請輸入密碼', trigger: 'blur' },
    { min: 6, message: '密碼至少需要6個字符', trigger: 'blur' }
  ],
  role: [
    { required: true, message: '請選擇身份', trigger: 'change' }
  ]
}

const handleRegister = async () => {
  if (!formRef.value) return
  
  try {
    await formRef.value.validate()
    loading.value = true
    
    await register({
      username: form.username,
      password: form.password,
      email: form.email,
      role: form.role
    })
    
    ElMessage.success('註冊成功，請登入')
    router.push('/login')
  } catch (error: any) {
    ElMessage.error(error.response?.data || '註冊失敗')
  } finally {
    loading.value = false
  }
}
</script> 