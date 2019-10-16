import Vue from 'vue'
import Router from 'vue-router'
import Home from "../components/page-addons/Home";
import ArtistsPage from "../components/artists/ArtistsPage";
import SongsPage from "../components/songs/SongsPage";

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
      //space is important
      redirect: '/artists/ ',
    },
    {
      path: '/artists/:subpage',
      name: 'Artists',
      component: ArtistsPage,
      props: true,
    },
    {
      path: '/songs',
      //space is important
      redirect: '/songs/ ',

    },
    {
      path: '/songs/:subpage',
      name: 'Songs',
      component: SongsPage,
      props: true,
    }
  ]
})
