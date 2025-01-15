-- 初始化用戶數據
MERGE INTO users (username, password, email, role, created_at) 
VALUES 
('coach1', '$2a$10$xn3LI/AjqicFYZFruSwve.681477XaVNaUQbr1gioaWPn4t1KsnmG', 'coach1@example.com', 'ROLE_COACH', CURRENT_TIMESTAMP),
('coach2', '$2a$10$xn3LI/AjqicFYZFruSwve.681477XaVNaUQbr1gioaWPn4t1KsnmG', 'coach2@example.com', 'ROLE_COACH', CURRENT_TIMESTAMP);

-- 為教練創建教練資料
INSERT INTO coaches (user_id, rating, total_ratings) 
SELECT id, 0, 0 
FROM users 
WHERE role = 'ROLE_COACH' 
AND NOT EXISTS (
    SELECT 1 FROM coaches c WHERE c.user_id = users.id
); 