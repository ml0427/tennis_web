<template>
  <div class="register-container">
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

      <el-form-item label="身份" prop="role">
        <el-radio-group v-model="form.role">
          <el-radio label="ROLE_STUDENT">學生</el-radio>
          <el-radio label="ROLE_COACH">教練</el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item>
        <el-button 
          type="primary" 
          @click="handleRegister" 
          :loading="loading"
          class="w-full"
        >
          註冊
        </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/store/user'

const router = useRouter()
const userStore = useUserStore()
const formRef = ref<FormInstance>()
const loading = ref(false)

const form = reactive({
  email: '',
  password: '',
  role: 'ROLE_STUDENT'
})

const rules: FormRules = {
  email: [
    { required: true, message: '請輸入電子郵件', trigger: 'blur' },
    { type: 'email', message: '請輸入有效的電子郵件地址', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '請輸入密碼', trigger: 'blur' },
    { min: 6, message: '密碼長度至少為 6 個字符', trigger: 'blur' }
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
    
    await userStore.register(form)
    ElMessage.success('註冊成功')
    router.push('/login')
  } catch (err: any) {
    ElMessage.error(err.response?.data?.message || '註冊失敗')
  } finally {
    loading.value = false
  }
}
</script> 