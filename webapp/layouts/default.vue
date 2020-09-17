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

      <div class="content column is-10 pt-6">
        <nuxt />
      </div>
    </div>
  </div>
</template>

<script>
export default {
  methods: {},
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
