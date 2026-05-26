<template>
  <main>
    <div class="day-half">
      <div class="site-shell">
        <nav class="topbar" aria-label="主要導覽">
          <a class="brand" href="#top" aria-label="拜六網球首頁">
            <span class="brand-mark">六</span>
            <span>拜六網球</span>
          </a>
          <div class="nav-links">
            <a href="#course">課程</a>
            <a href="#booking">預約</a>
            <a href="#faq">FAQ</a>
          </div>
        </nav>

        <section id="top" class="hero-section">
          <div class="hero-copy">
            <p class="eyebrow">成人網球教學 / 彩虹網球場 / 週六限定</p>
            <h1>拜六來打球。</h1>
            <p class="hero-lead">
              想學網球，不用等到裝備齊全、動作漂亮才開始。拜六網球提供成人初學與動作調整課，
              一堂 1 小時 800 元，可先上一次看看，再決定適不適合你。
            </p>
            <div class="hero-actions">
              <a class="primary-action" href="#booking">預約嘗試上課一次</a>
              <a class="secondary-action" href="#details">先看課程規則</a>
            </div>
          </div>

          <aside class="court-ticket" aria-label="課程重點">
            <div>
              <span>Lesson</span>
              <strong>1 小時</strong>
            </div>
            <div>
              <span>Fee</span>
              <strong>800 元</strong>
            </div>
            <div>
              <span>Time</span>
              <strong>只收週六</strong>
            </div>
            <div>
              <span>Place</span>
              <strong>彩虹網球場</strong>
            </div>
          </aside>
        </section>

        <section id="details" class="notice-band">
          <p>
            學生需先自行租借彩虹網球場並確認時間，再填寫預約資訊。課程以教學為主，
            不提供單純陪練，也歡迎你跟其他教練比較後再決定。
          </p>
        </section>

        <section id="course" class="section-grid">
          <div class="section-heading">
            <p class="eyebrow">Course</p>
            <h2>這堂課適合誰</h2>
          </div>
          <div class="feature-grid">
            <article class="feature-card">
              <span class="card-number">01</span>
              <h3>成人初學者</h3>
              <p>第一次拿球拍也可以，從握拍、站姿、揮拍路徑、擊球點開始建立基本動作。</p>
            </article>
            <article class="feature-card">
              <span class="card-number">02</span>
              <h3>想調整動作</h3>
              <p>如果你常常打飛、打不準或施力不順，可以針對正手、反手、發球做基礎修正。</p>
            </article>
            <article class="feature-card">
              <span class="card-number">03</span>
              <h3>先試一堂</h3>
              <p>不用一次承諾長期課程。先上一堂，感受教學方式，再選擇最適合自己的教練。</p>
            </article>
          </div>
        </section>
      </div>
    </div>

    <div class="night-half">
      <div class="site-shell">
        <section class="split-section">
          <div>
            <p class="eyebrow">Rules</p>
            <h2>先講清楚，才好上課</h2>
          </div>
          <ul class="rule-list" aria-label="課程規則">
            <li>目前只接受成年人預約。</li>
            <li>僅開放週六上課。</li>
            <li>一堂課 1 小時，課程費用 800 元。</li>
            <li>球場租借費由學生自行負擔。</li>
            <li>學生需自行租借彩虹網球場並確認時間。</li>
            <li>不提供單純陪練服務。</li>
          </ul>
        </section>

        <section id="booking" class="booking-section">
          <div class="booking-copy">
            <p class="eyebrow">Booking</p>
            <h2>預約一堂網球課</h2>
            <p>
              請先確認週六可上課時段，並自行完成彩虹網球場租借。確認場地日期與時間後，
              再填寫 Google 表單，送出前也會再提醒一次不適合的條件。
            </p>
          </div>
          <div class="booking-panel">
            <ol>
              <li>確認你想上課的週六時段</li>
              <li>自行向彩虹網球場租借場地</li>
              <li>填寫預約表單並等待確認</li>
            </ol>
            <button class="primary-action full-width" type="button" @click="showBookingConfirm = true">
              填寫 Google 預約表單
            </button>
            <p class="form-note">表單連結目前先保留為佔位網址，取得正式 Google 表單後可直接替換。</p>
          </div>
        </section>

        <section id="faq" class="faq-section">
          <div class="section-heading">
            <p class="eyebrow">FAQ</p>
            <h2>常見問題</h2>
          </div>
          <div class="faq-list">
            <article>
              <h3>完全沒打過可以嗎？</h3>
              <p>可以。課程可以從握拍、準備姿勢與基礎擊球開始。</p>
            </article>
            <article>
              <h3>可以只上一堂看看嗎？</h3>
              <p>可以，這個網站就是先讓你預約嘗試上一堂課。</p>
            </article>
            <article>
              <h3>費用包含球場嗎？</h3>
              <p>不包含。課程費用為 1 小時 800 元，球場租借費由學生自行負擔。</p>
            </article>
            <article>
              <h3>可以陪練或單純對打嗎？</h3>
              <p>不提供單純陪練，課程以教學與動作調整為主。</p>
            </article>
          </div>
        </section>
      </div>
    </div>

    <div v-if="showBookingConfirm" class="modal-backdrop" role="presentation" @click.self="showBookingConfirm = false">
      <section
        class="confirm-dialog"
        role="dialog"
        aria-modal="true"
        aria-labelledby="booking-confirm-title"
      >
        <p class="eyebrow">Before Booking</p>
        <h2 id="booking-confirm-title">預約前再確認一次</h2>
        <p class="confirm-lead">
          如果下面條件不符合，這堂課可能暫時不適合你。確認都了解後，再前往 Google 表單。
        </p>
        <ul class="confirm-list">
          <li>未滿 18 歲，或不是成年人。</li>
          <li>需要非週六時段上課。</li>
          <li>只想找陪練、陪打或單純對打。</li>
          <li>尚未自行租借彩虹網球場並確認時間。</li>
          <li>無法接受課程費 1 小時 800 元，球場費另計。</li>
        </ul>
        <div class="dialog-actions">
          <button class="secondary-action" type="button" @click="showBookingConfirm = false">先不要預約</button>
          <a class="primary-action" :href="bookingUrl" target="_blank" rel="noreferrer">
            我了解，前往表單
          </a>
        </div>
      </section>
    </div>
  </main>
</template>

<script setup lang="ts">
import { ref } from 'vue'

const bookingUrl = 'https://forms.gle/your-google-form-url'
const showBookingConfirm = ref(false)
</script>
