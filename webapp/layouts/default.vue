<template>
  <div class="container">
    <div class="columns">
      <div class="column is-2 pt-6">
        <aside class="menu">
          <p class="menu-label">Menu</p>
          <ul class="menu-list">
            <li>
              <nuxt-link :to="{ path: '/' }" exact> General </nuxt-link>
            </li>
            <li>
              <nuxt-link :to="{ path: '/regions/' }"> Regions </nuxt-link>
            </li>
          </ul>
        </aside>
      </div>

      <div class="content column is-10">
        <b-navbar type="is-white">
          <template slot="end">
            <div class="navbar-item">
              {{ this.$auth.user.username }}
            </div>
            <div class="navbar-item">
              <div class="buttons">
                <a class="button is-light" @click="handleLogout">
                  <b-icon icon="sign-out-alt"></b-icon>
                </a>
              </div>
            </div>
          </template>
        </b-navbar>

        <nuxt />
      </div>
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    handleLogout() {
      this.$auth.logout().then(() => {
        this.$router.push('/login/')
        this.showWarn('Bye!')
      })
    },
  },
}
</script>

<style>
body {
  background-color: whitesmoke !important;
}

.content {
  background-color: #fff;
  min-height: 700px;
}
</style>
