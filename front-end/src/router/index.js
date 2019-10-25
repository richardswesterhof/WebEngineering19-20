import Vue from 'vue'
import Router from 'vue-router'
import ArtistsPage from "../components/artists/ArtistsPage";
import SongsPage from "../components/songs/SongsPage";
import ImportPage from "../components/ImportPage";
import NotFoundPage from "../components/page-addons/NotFoundPage";

Vue.use(Router);

export default new Router({
  mode: 'history',

  routes: [
    {
      path: '/',
      redirect: '/artists/all',
    },
    {
      path: '/artists',
      redirect: '/artists/all',
    },
    {
      path: '/artists/:subpage',
      name: 'Artists',
      component: ArtistsPage,
      props: true,
    },
    {
      path: '/songs',
      redirect: '/songs/all',
    },
    {
      path: '/songs/:subpage',
      name: 'Songs',
      component: SongsPage,
      props: true,
    },
    {
      path: '/import',
      name: 'Import',
      component : ImportPage,
    },
    {
      path: '*',
      name: 'NotFound',
      component: NotFoundPage,
    },
  ],
});
