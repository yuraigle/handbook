<template>
  <ModalFormWrapper v-bind="$props" @submit="handleSubmit">
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
  </ModalFormWrapper>
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

  watch: {
    obj(val) {
      Object.keys(schema).forEach((k) => (this[k] = val[k]))
    },
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
