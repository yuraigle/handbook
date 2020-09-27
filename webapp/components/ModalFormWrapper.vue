<template>
  <b-modal :active="modal === title" has-modal-card @close="handleClose">
    <form @submit.prevent="$emit('submit')">
      <div class="modal-card" style="width: auto">
        <header class="modal-card-head">
          <p class="modal-card-title">{{ title }}</p>
        </header>

        <section class="modal-card-body">
          <slot></slot>
        </section>

        <footer class="modal-card-foot">
          <div class="container">
            <div class="buttons is-right">
              <button class="button" type="button" @click="handleClose">
                Cancel
              </button>
              <button class="button is-primary">{{ okText }}</button>
            </div>
          </div>
        </footer>
      </div>
    </form>
  </b-modal>
</template>

<script>
import { mapState } from 'vuex'

export default {
  props: {
    obj: { type: Object, default: () => ({}) },
    title: { type: String, required: true },
    okay: { type: String, default: '' },
  },

  computed: {
    okText() {
      return this.okay ? this.okay : this.obj.id ? 'Edit' : 'Create'
    },
    ...mapState({
      modal: (state) => state.modals.shown,
    }),
  },

  methods: {
    handleClose() {
      this.$store.commit('modals/setShown', '')
    },
  },
}
</script>
