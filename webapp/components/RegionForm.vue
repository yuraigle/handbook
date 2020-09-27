<template>
  <form @submit.prevent="handleSubmit">
    <div class="modal-card" style="width: 280px">
      <header class="modal-card-head">
        <p class="modal-card-title">{{ title }}</p>
      </header>
      <section class="modal-card-body">
        <b-field
          label="Name"
          label-position="on-border"
          :type="fieldType('name')"
          :message="fieldMsg('name')"
        >
          <b-input v-model="name" @input="$v.$touch()"></b-input>
        </b-field>

        <b-field
          label="Capital"
          label-position="on-border"
          :type="fieldType('capital')"
          :message="fieldMsg('capital')"
        >
          <b-input v-model="capital" @input="$v.$touch()"></b-input>
        </b-field>

        <b-field
          label="Area"
          label-position="on-border"
          :type="fieldType('area')"
          :message="fieldMsg('area')"
        >
          <b-input v-model="area" @input="$v.$touch()"></b-input>
        </b-field>
      </section>
      <footer class="modal-card-foot">
        <div class="container">
          <div class="buttons is-right">
            <button class="button" type="button" @click="$emit('close')">
              Cancel
            </button>
            <button class="button is-primary">{{ okText }}</button>
          </div>
        </div>
      </footer>
    </div>
  </form>
</template>

<script>
import { required, helpers } from 'vuelidate/lib/validators'

const schema = {
  id: {},
  name: { required },
  capital: { required },
  area: { rx: helpers.regex('Numeric', /^[0-9]+$/) },
}

export default {
  props: {
    obj: { type: Object, default: () => ({}) },
    title: { type: String, required: true },
    okay: { type: String, default: '' },
  },

  data: () => {
    const formFields = {}
    Object.keys(schema).forEach((key) => (formFields[key] = undefined))
    return formFields
  },

  validations: schema,

  computed: {
    okText() {
      return this.okay ? this.okay : this.obj.id ? 'Edit' : 'Create'
    },
  },

  created() {
    if (this.obj) {
      Object.keys(schema).forEach((k) => (this[k] = this.obj[k]))
    }
  },

  methods: {
    handleSubmit() {
      this.$v.$touch()

      if (!this.$v.$invalid) {
        const formData = {}
        Object.keys(schema).forEach((key) => (formData[key] = this[key]))
        this.$emit('submit', formData)
      }
    },
  },
}
</script>
