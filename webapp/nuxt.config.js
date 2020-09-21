export default {
  /*
   ** Nuxt rendering mode
   ** See https://nuxtjs.org/api/configuration-mode
   */
  ssr: false,
  /*
   ** Nuxt target
   ** See https://nuxtjs.org/api/configuration-target
   */
  target: 'static',
  /*
   ** Headers of the page
   ** See https://nuxtjs.org/api/configuration-head
   */
  head: {
    title: 'Handbook',
    meta: [
      { charset: 'utf-8' },
      { name: 'viewport', content: 'width=device-width, initial-scale=1' },
    ],
    link: [
      { rel: 'icon', type: 'image/x-icon', href: '/favicon.ico' },
      { rel: 'stylesheet', href: '/fontawesome/css/fontawesome.min.css' },
      { rel: 'stylesheet', href: '/fontawesome/css/solid.min.css' },
    ],
  },
  /*
   ** Global CSS
   */
  css: [],
  /*
   ** Plugins to load before mounting the App
   ** https://nuxtjs.org/guide/plugins
   */
  plugins: [{ src: '~/plugins/vuelidate' }, { src: '~/plugins/messages' }],
  /*
   ** Auto import components
   ** See https://nuxtjs.org/api/configuration-components
   */
  components: true,
  /*
   ** Nuxt.js dev-modules
   */
  buildModules: [
    // Doc: https://github.com/nuxt-community/eslint-module
    '@nuxtjs/eslint-module',
  ],
  /*
   ** Nuxt.js modules
   */
  modules: [
    // Doc: https://buefy.github.io/#/documentation
    'nuxt-buefy',
    // Doc: https://axios.nuxtjs.org/usage
    '@nuxtjs/axios',
    '@nuxtjs/auth',
  ],
  router: {
    base: '/',
    middleware: ['auth'],
    trailingSlash: true,
    linkActiveClass: 'is-active',
  },
  /*
   ** Axios module configuration
   ** See https://axios.nuxtjs.org/options
   */
  axios: {
    baseURL: 'http://localhost:8080',
  },

  auth: {
    localStorage: false,
    cookie: {
      options: {
        expires: 7,
      },
    },
    strategies: {
      local: {
        token: { property: 'token' },
        autoFetchUser: false,
        endpoints: {
          login: { url: '/api/auth/login', method: 'post' },
          user: { url: '/api/auth/me', method: 'get', propertyName: 'user' },
          logout: false,
        },
      },
    },
    redirect: {
      login: '/login/',
      logout: false,
      callback: false,
      home: false,
    },
  },

  generate: {
    dir: '../src/main/resources/webapp-dist',
    fallback: true,
  },

  buefy: {
    materialDesignIcons: false,
    defaultIconPack: 'fas',
  },
}
