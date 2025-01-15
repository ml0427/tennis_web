<template>
  <div class="max-w-4xl mx-auto">
    <el-card class="mb-4">
      <template #header>
        <div class="flex justify-between items-center">
          <h2 class="text-2xl font-bold text-gray-900">個人資料</h2>
          <el-button type="primary" @click="isEditing = true" v-if="!isEditing">
            編輯資料
          </el-button>
        </div>
      </template>

      <el-form 
        :model="form" 
        :rules="rules" 
        ref="formRef"
        :disabled="!isEditing"
        label-position="top"
      >
        <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
          <el-form-item label="用戶名" prop="username">
            <el-input v-model="form.username" disabled />
          </el-form-item>

          <el-form-item label="電子郵件" prop="email">
            <el-input v-model="form.email" />
          </el-form-item>

          <el-form-item label="身份">
            <el-tag :type="form.role === 'ROLE_COACH' ? 'success' : 'info'">
              {{ form.role === 'ROLE_COACH' ? '教練' : '學生' }}
            </el-tag>
          </el-form-item>
        </div>

        <div v-if="form.role === 'ROLE_COACH'" class="mt-6">
          <h3 class="text-xl font-semibold mb-4">教練資料</h3>
          <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
            <el-form-item label="教學經驗" prop="experience">
              <el-input 
                v-model="form.experience" 
                type="textarea" 
                :rows="3"
                placeholder="請描述您的教學經驗"
              />
            </el-form-item>

            <el-form-item label="證照" prop="certificates">
              <el-input 
                v-model="form.certificates" 
                type="textarea" 
                :rows="3"
                placeholder="請列出您的相關證照"
              />
            </el-form-item>

            <el-form-item label="專長" prop="specialties">
              <el-input 
                v-model="form.specialties" 
                type="textarea" 
                :rows="3"
                placeholder="請描述您的專長領域"
              />
            </el-form-item>

            <el-form-item label="上課地點" prop="locations">
              <el-input 
                v-model="form.locations" 
                type="textarea" 
                :rows="3"
                placeholder="請列出可提供授課的地點"
              />
            </el-form-item>

            <el-form-item label="時薪" prop="hourlyRate">
              <el-input-number 
                v-model="form.hourlyRate"
                :min="0"
                :step="100"
                class="w-full"
              />
            </el-form-item>

            <el-form-item label="可授課時間" prop="availableTime">
              <el-input 
                v-model="form.availableTime" 
                type="textarea" 
                :rows="3"
                placeholder="請說明您可以授課的時間"
              />
            </el-form-item>
          </div>
        </div>

        <div class="flex justify-end gap-4 mt-6" v-if="isEditing">
          <el-button @click="cancelEdit">取消</el-button>
          <el-button type="primary" @click="saveProfile" :loading="loading">
            儲存
          </el-button>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'
import { ElMessage } from 'element-plus'
import { useUserStore } from '@/store/user'

const userStore = useUserStore()
const formRef = ref<FormInstance>()
const loading = ref(false)
const isEditing = ref(false)

const form = reactive({
  username: '',
  email: '',
  role: '',
  experience: '',
  certificates: '',
  specialties: '',
  locations: '',
  hourlyRate: 0,
  availableTime: ''
})

const rules: FormRules = {
  email: [
    { required: true, message: '請輸入電子郵件', trigger: 'blur' },
    { type: 'email', message: '請輸入有效的電子郵件地址', trigger: 'blur' }
  ]
}

const fetchProfile = async () => {
  try {
    const data = await userStore.fetchUserInfo()
    Object.assign(form, data)
  } catch (err) {
    ElMessage.error('獲取個人資料失敗')
  }
}

const saveProfile = async () => {
  if (!formRef.value) return
  
  try {
    await formRef.value.validate()
    loading.value = true
    
    await userStore.updateProfile(form)
    ElMessage.success('更新成功')
    isEditing.value = false
  } catch (err: any) {
    ElMessage.error(err.response?.data || '更新失敗')
  } finally {
    loading.value = false
  }
}

const cancelEdit = () => {
  isEditing.value = false
  fetchProfile() // 重新獲取資料，放棄修改
}

onMounted(fetchProfile)
</script>