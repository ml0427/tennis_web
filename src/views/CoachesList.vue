<template>
  <div class="max-w-7xl mx-auto">
    <el-card class="mb-4">
      <template #header>
        <div class="flex justify-between items-center">
          <h2 class="text-2xl font-bold text-gray-900">教練列表</h2>
          <el-input
            v-model="search"
            placeholder="搜尋教練"
            class="w-64"
            clearable
            :prefix-icon="Search"
          />
        </div>
      </template>

      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        <el-card 
          v-for="coach in filteredCoaches" 
          :key="coach.id"
          shadow="hover"
          class="coach-card"
        >
          <div class="flex items-start space-x-4">
            <el-avatar :size="64" :icon="UserFilled" />
            <div class="flex-1">
              <h3 class="text-lg font-semibold">{{ coach.username }}</h3>
              <p class="text-gray-600 text-sm mt-1">
                教學經驗：{{ coach.experience || '尚未填寫' }}
              </p>
              <p class="text-gray-600 text-sm">
                證照：{{ coach.certificates || '尚未填寫' }}
              </p>
              <p class="text-gray-600 text-sm">
                專長：{{ coach.specialties || '尚未填寫' }}
              </p>
              <p class="text-gray-600 text-sm">
                上課地點：{{ coach.locations || '尚未填寫' }}
              </p>
              <p class="text-gray-600 text-sm">
                可授課時間：{{ coach.availableTime || '尚未填寫' }}
              </p>
              <div class="mt-2">
                <el-rate 
                  v-model="coach.rating" 
                  disabled 
                  show-score 
                  text-color="#ff9900"
                />
              </div>
              <div class="mt-3 flex justify-between items-center">
                <span class="text-indigo-600 font-medium">
                  NT$ {{ coach.hourlyRate || '未設定' }}/小時
                </span>
                <el-button type="primary" size="small" @click="bookLesson(coach)">
                  預約課程
                </el-button>
              </div>
            </div>
          </div>
        </el-card>
      </div>

      <div v-if="filteredCoaches.length === 0" class="text-center py-8">
        <el-empty description="暫無符合條件的教練" />
      </div>
    </el-card>

    <!-- 預約課程對話框 -->
    <el-dialog
      v-model="dialogVisible"
      title="預約課程"
      width="500px"
    >
      <el-form :model="bookingForm" :rules="bookingRules" ref="bookingFormRef">
        <el-form-item label="上課日期" prop="date">
          <el-date-picker
            v-model="bookingForm.date"
            type="date"
            placeholder="選擇日期"
            :disabled-date="disabledDate"
            class="w-full"
          />
        </el-form-item>
        <el-form-item label="上課時間" prop="time">
          <el-time-select
            v-model="bookingForm.time"
            start="09:00"
            step="01:00"
            end="21:00"
            placeholder="選擇時間"
            class="w-full"
          />
        </el-form-item>
        <el-form-item label="課程時長" prop="duration">
          <el-select v-model="bookingForm.duration" placeholder="選擇時長" class="w-full">
            <el-option label="1小時" :value="1" />
            <el-option label="2小時" :value="2" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="confirmBooking" :loading="bookingLoading">
            確認預約
          </el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { Search, UserFilled } from '@element-plus/icons-vue'
import type { FormInstance, FormRules } from 'element-plus'
import { ElMessage } from 'element-plus'
import api from '@/api'

interface Coach {
  id: number
  username: string
  experience?: string
  rating: number
  hourlyRate?: number
}

const search = ref('')
const coaches = ref<Coach[]>([])
const dialogVisible = ref(false)
const bookingLoading = ref(false)
const bookingFormRef = ref<FormInstance>()
const selectedCoach = ref<Coach | null>(null)

const bookingForm = ref({
  date: '',
  time: '',
  duration: 1
})

const bookingRules: FormRules = {
  date: [{ required: true, message: '請選擇日期', trigger: 'change' }],
  time: [{ required: true, message: '請選擇時間', trigger: 'change' }],
  duration: [{ required: true, message: '請選擇時長', trigger: 'change' }]
}

const filteredCoaches = computed(() => {
  if (!search.value) return coaches.value
  const searchLower = search.value.toLowerCase()
  return coaches.value.filter(coach => 
    coach.username.toLowerCase().includes(searchLower)
  )
})

const fetchCoaches = async () => {
  try {
    const response = await api.get('/api/coaches')
    coaches.value = response.data
  } catch (err: any) {
    ElMessage.error('獲取教練列表失敗')
  }
}

const bookLesson = async (coachId: number) => {
  try {
    await api.post('/api/bookings', {
      coachId,
      // ... 其他預約資料
    })
    ElMessage.success('預約成功')
  } catch (err: any) {
    ElMessage.error(err.response?.data || '預約失敗')
  }
}

const disabledDate = (time: Date) => {
  return time.getTime() < Date.now() - 8.64e7 // 禁用過去的日期
}

const confirmBooking = async () => {
  if (!bookingFormRef.value || !selectedCoach.value) return
  
  try {
    await bookingFormRef.value.validate()
    bookingLoading.value = true
    
    await api.post('/api/bookings', {
      coachId: selectedCoach.value.id,
      ...bookingForm.value
    })
    
    ElMessage.success('預約成功')
    dialogVisible.value = false
  } catch (err: any) {
    ElMessage.error(err.response?.data || '預約失敗')
  } finally {
    bookingLoading.value = false
  }
}

onMounted(fetchCoaches)
</script>

<style scoped>
.coach-card {
  transition: transform 0.2s;
}

.coach-card:hover {
  transform: translateY(-4px);
}
</style> 