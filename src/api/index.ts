import axios from 'axios'

const api = axios.create({
    baseURL: 'http://localhost:8081',
    timeout: 5000,
    withCredentials: true
})

// 請求攔截器
api.interceptors.request.use(
    (config) => {
        console.log('Request:', {
            url: config.url,
            method: config.method,
            data: config.data,
            headers: config.headers
        });
        return config
    },
    (error) => {
        console.error('Request error:', error)
        return Promise.reject(error)
    }
)

// 響應攔截器
api.interceptors.response.use(
    (response) => {
        console.log('Response:', {
            url: response.config.url,
            status: response.status,
            data: response.data
        });
        return response
    },
    (error) => {
        console.error('Response error:', {
            error,
            config: error.config,
            response: error.response,
            data: error.response?.data
        });
        // 401錯誤由組件自行處理，不在此強制重定向
        return Promise.reject(error)
    }
)

export default api
