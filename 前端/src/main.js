// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})

router.beforeEach(function(to, from, next) {
  if (to.meta.needLogin) {
    if (sessionStorage.getItem("user") == null) {
      router.replace('/');
      alert('请登录！');
    } else {
      next();
    }
  } else {
    next();
  }
});