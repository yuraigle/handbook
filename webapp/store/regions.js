export const state = () => ({
  list: [],
  loading: false,
})

export const mutations = {
  setList(state, p) {
    state.list = p
  },

  setLoading(state, p) {
    state.loading = p
  },
}

export const actions = {
  fetchList({ commit, dispatch }) {
    return new Promise((resolve) => {
      commit('setList', [])
      commit('setLoading', true)

      this.$axios
        .get('/api/regions')
        .then(({ data }) => {
          commit('setList', data._embedded.regions)
          resolve()
        })
        .catch((err) => dispatch('warnings/apiError', err, { root: true }))
        .finally(() => commit('setLoading', false))
    })
  },

  create({ commit, dispatch }, payload) {
    return new Promise((resolve) => {
      commit('setLoading', true)

      this.$axios
        .post('/api/regions', payload)
        .then(() => {
          dispatch('fetchList')
          resolve()
        })
        .catch((err) => dispatch('warnings/apiError', err, { root: true }))
        .finally(() => commit('setLoading', false))
    })
  },

  delete({ commit, dispatch }, payload) {
    return new Promise((resolve) => {
      commit('setLoading', true)

      this.$axios
        .delete(`/api/regions/${payload.id}`)
        .then(() => {
          dispatch('fetchList')
          resolve()
        })
        .catch((err) => dispatch('warnings/apiError', err, { root: true }))
        .finally(() => commit('setLoading', false))
    })
  },
}
