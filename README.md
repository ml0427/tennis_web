# 拜六網球

拜六網球是成人網球教學的靜態介紹網站，主打週六於彩虹網球場上課。網站用途是讓訪客了解課程條件、費用與預約流程，最後透過 Google 表單送出預約。

網站目前部署目標：

```text
https://ml0427.github.io/tennis_web/
```

## 課程資訊

- 課程名稱：拜六網球
- 對象：成年人
- 時間：僅開放週六
- 地點：彩虹網球場
- 費用：1 小時 800 元
- 場地：學生需自行租借球場並確認時間
- 類型：網球教學與動作調整，不提供單純陪練
- 預約：可先嘗試上課一次，再決定是否繼續

## 技術架構

這是一個 Vue 3 + Vite 靜態網站。

主要檔案：

```text
src/App.vue          頁面內容與預約確認視窗
src/assets/main.css  頁面樣式、日夜背景與響應式設計
vite.config.ts       GitHub Pages base path 與 build 輸出設定
docs/                GitHub Pages 使用的靜態輸出資料夾
```

網站不需要後端、資料庫或登入系統。預約功能由 Google 表單處理。

## 本地開發

安裝依賴：

```bash
npm install
```

啟動本地開發伺服器：

```bash
npm run dev
```

預設可在瀏覽器打開：

```text
http://localhost:5173/
```

## 建置

產生 GitHub Pages 用的靜態檔：

```bash
npm run build
```

建置後會輸出到：

```text
docs/
```

## GitHub Pages 設定

在 GitHub repo 進入：

```text
Settings -> Pages
```

設定：

```text
Source: Deploy from a branch
Branch: master
Folder: /docs
```

按下 Save 後，GitHub Pages 會部署 `docs/` 裡的靜態檔。

## Google 表單連結

目前預約表單連結在 `src/App.vue`：

```ts
const bookingUrl = 'https://forms.gle/your-google-form-url'
```

取得正式 Google 表單網址後，把這段換成正式連結，再重新 build 並 push。

## 發布流程

一般更新流程：

```bash
npm run build
git status
git add .
git commit -m "Update site"
git push origin master
```

更新推上 GitHub 後，GitHub Pages 會重新部署 `/docs`。
