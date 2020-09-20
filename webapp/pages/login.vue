<template>
  <section class="hero is-dark is-fullheight">
    <div class="hero-body">
      <div class="container">
        <div class="columns is-centered">
          <div class="column is-5-tablet is-4-desktop is-3-widescreen">
            <form class="box" @submit.prevent="handleLogin">
              <b-field
                label="Username"
                label-position="on-border"
                :type="fieldType('username')"
                :message="fieldMsg('username')"
              >
                <b-input
                  v-model="username"
                  icon="user"
                  @input="$v.$touch()"
                ></b-input>
              </b-field>
              <b-field
                label="Password"
                label-position="on-border"
                :type="fieldType('password')"
                :message="fieldMsg('password')"
              >
                <b-input
                  v-model="password"
                  type="password"
                  icon="lock"
                  @input="$v.$touch()"
                ></b-input>
              </b-field>
              <b-field>
                <b-button expanded type="is-info" @click="handleLogin">
                  Login
                </b-button>
              </b-field>
            </form>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
import { required, minLength, maxLength } from 'vuelidate/lib/validators'

const schema = {
  username: { required },
  password: {
    required,
    minLength: minLength(6),
    maxLength: maxLength(20),
  },
}

export default {
  layout: 'empty',

  data: () => {
    const formFields = {}
    Object.keys(schema).forEach((key) => (formFields[key] = undefined))
    return formFields
  },

  validations: schema,

  methods: {
    handleLogin() {
      this.$v.$touch()
      if (this.$v.$invalid) {
        return
      }

      const data = {}
      Object.keys(schema).forEach((key) => (data[key] = this[key]))

      this.$auth
        .loginWith('local', { data })
        .then((resp) => this.$auth.setUser(resp.data.user))
        .catch((err) => this.$store.dispatch('warnings/apiError', err))
    },
  },
}
</script>
