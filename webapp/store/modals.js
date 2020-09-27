export const state = () => ({
  shown: '',
})

export const mutations = {
  setShown(state, p) {
    state.shown = p
  },
}
