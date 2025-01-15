<template>
  <div class="max-w-7xl mx-auto">
    <el-card class="mb-4">
      <template #header>
        <h1 class="text-3xl font-bold text-gray-900">網球教學平台</h1>
      </template>
      <div class="space-y-4">
        <p class="text-gray-600">
          歡迎來到網球教學平台！這裡提供最專業的網球教學服務，
          無論您是初學者還是進階玩家，都能找到適合的教練。
        </p>
        
        <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
          <el-card shadow="hover" class="text-center">
            <el-icon class="text-4xl text-indigo-600 mb-2"><Trophy /></el-icon>
            <h3 class="text-xl font-semibold mb-2">專業教練</h3>
            <p class="text-gray-600">嚴選優質教練，確保教學品質</p>
          </el-card>

          <el-card shadow="hover" class="text-center">
            <el-icon class="text-4xl text-indigo-600 mb-2"><Calendar /></el-icon>
            <h3 class="text-xl font-semibold mb-2">彈性排課</h3>
            <p class="text-gray-600">自由選擇上課時間和地點</p>
          </el-card>

          <el-card shadow="hover" class="text-center">
            <el-icon class="text-4xl text-indigo-600 mb-2"><Star /></el-icon>
            <h3 class="text-xl font-semibold mb-2">課程評價</h3>
            <p class="text-gray-600">真實學員回饋，參考選擇</p>
          </el-card>
        </div>

        <div class="flex justify-center mt-8">
          <el-button 
            type="primary" 
            size="large"
            @click="$router.push('/coaches')"
          >
            立即尋找教練
          </el-button>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { Trophy, Calendar, Star } from '@element-plus/icons-vue'
</script> 