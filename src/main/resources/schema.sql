-- 創建用戶表
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    role VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL
);

-- 創建教練表
CREATE TABLE IF NOT EXISTS coaches (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    experience TEXT,
    certificates TEXT,
    specialties TEXT,
    locations TEXT,
    hourly_rate DECIMAL(10,2),
    available_time TEXT,
    rating DOUBLE NOT NULL DEFAULT 0,
    total_ratings INTEGER NOT NULL DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- 創建預約表
CREATE TABLE IF NOT EXISTS bookings (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    coach_id BIGINT NOT NULL,
    date DATE NOT NULL,
    time TIME NOT NULL,
    duration INTEGER NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    created_at TIMESTAMP NOT NULL,
    FOREIGN KEY (student_id) REFERENCES users(id),
    FOREIGN KEY (coach_id) REFERENCES users(id)
); 