import axios from 'axios'

const api = axios.create({
    baseURL: 'http://localhost:8081',
    timeout: 5000,
    withCredentials: true
})

// 添加請求攔截器
api.interceptors.request.use(config => {
    const token = localStorage.getItem('token')
    if (token) {
        config.headers.Authorization = `Bearer ${token}`
    }
    return config
}, error => {
    return Promise.reject(error)
})

// 添加響應攔截器
api.interceptors.response.use(
    response => response,
    error => {
        if (error.response?.status === 401 || error.response?.status === 403) {
            localStorage.removeItem('token')
            window.location.href = '/login'
        }
        return Promise.reject(error)
    }
)

export default api
