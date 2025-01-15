import api from './index'

export const register = (data: any) => {
    return api.post('/api/users/register', data)
}

export const login = (data: any) => {
    return api.post('/api/users/login', data)
}

export const getProfile = () => {
    return api.get('/api/users/profile')
}

export const updateProfile = (data: any) => {
    return api.put('/api/users/profile', data)
}

// 教練相關 API
export const getCoaches = () => {
    return api.get('/api/coaches')
}

export const bookLesson = (data: any) => {
    return api.post('/api/bookings', data)
} 