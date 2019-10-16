import Vue from 'vue'
import Router from 'vue-router'
import Home from "../components/Home";
import Artists from "../components/Artists";
import Songs from "../components/Songs";

Vue.use(Router);

export default new Router({
  mode: 'history',

  routes: [
    {
      path: '/',
      name: 'HomePage',
      component: Home,
    },
    {
      path: '/artists',
      name: 'Artists',
      component: Artists,
    },
    {
      path: '/songs',
      name: 'Songs',
      component: Songs,
    }
  ]
})
