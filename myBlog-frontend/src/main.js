import './style.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'

// FontAwesome 불러오기
import { library } from '@fortawesome/fontawesome-svg-core'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import { faEnvelope, faLink } from '@fortawesome/free-solid-svg-icons'
import { faGithub, faLinkedin } from '@fortawesome/free-brands-svg-icons'

const app = createApp(App)

app.use(createPinia())
app.use(router)

// 사용할 아이콘 등록
library.add(faEnvelope, faGithub, faLinkedin, faLink)
app.component('font-awesome-icon', FontAwesomeIcon)

app.mount('#app')
