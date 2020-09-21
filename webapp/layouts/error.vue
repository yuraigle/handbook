<template>
  <section class="hero is-dark is-fullheight">
    <div class="hero-body">
      <div class="container">
        <div class="columns is-centered">
          <div class="has-text-centered">
            <h1 class="title is-1">{{ error.statusCode }}</h1>
            <p class="subtitle is-5">{{ error.message }}</p>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
export default {
  props: {
    error: { type: Object, required: true },
  },
  layout: 'empty',
}
</script>
