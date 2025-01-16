<template>
  <div class="login-container">
    <el-form 
      :model="form" 
      :rules="rules" 
      ref="formRef"
      label-position="top"
    >
      <el-form-item label="電子郵件" prop="email">
        <el-input 
          v-model="form.email" 
          placeholder="請輸入電子郵件"
        />
      </el-form-item>

      <el-form-item label="密碼" prop="password">
        <el-input 
          v-model="form.password" 
          type="password" 
          placeholder="請輸入密碼"
        />
      </el-form-item>

      <el-form-item>
        <el-button 
          type="primary" 
          @click="handleLogin" 
          :loading="loading"
          class="w-full"
        >
          登入
        </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import type { FormInstance, FormRules } from 'element-plus'
import { ElMessage } from 'element-plus'
import { useUserStore } from '@/store/user'

const router = useRouter()
const userStore = useUserStore()
const formRef = ref<FormInstance>()
const loading = ref(false)

const form = reactive({
  email: '',
  password: ''
})

const rules: FormRules = {
  email: [
    { required: true, message: '請輸入電子郵件', trigger: 'blur' },
    { type: 'email', message: '請輸入有效的電子郵件地址', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '請輸入密碼', trigger: 'blur' },
    { min: 6, message: '密碼長度至少為 6 個字符', trigger: 'blur' }
  ]
}

const handleLogin = async (e: Event) => {
  e.preventDefault()  // 防止表單默認提交
  if (!formRef.value) return
  
  try {
    await formRef.value.validate()
    loading.value = true
    
    await userStore.login(form.email, form.password)
    ElMessage.success('登入成功')
    console.log('Login success')
    router.push('/profile')
  } catch (err: any) {
    console.error('Login error:', err)
    ElMessage.error(err.message || '登入失敗')
  } finally {
    loading.value = false
  }
}
</script>
