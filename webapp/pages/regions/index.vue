<template>
  <div>
    <h3 class="title is-3">All regions</h3>

    <nav class="level">
      <div class="level-left">
        <div class="level-item">
          <b-button
            type="is-primary"
            icon-left="plus"
            @click="$store.commit('modals/setShown', 'New Region')"
          >
            Add
          </b-button>
        </div>
        <div class="level-item">
          <b-button
            :disabled="!selected.id"
            type="is-warning"
            icon-left="edit"
            @click="$store.commit('modals/setShown', 'Edit Region')"
          >
            Edit
          </b-button>
        </div>
        <div class="level-item">
          <b-button
            :disabled="!selected.id"
            type="is-danger"
            icon-left="trash-alt"
            @click="handleDeleteRequest"
          >
            Delete
          </b-button>
        </div>
        <div class="level-item">
          <b-button type="is-dark" icon-left="sync-alt" @click="handleRefresh">
          </b-button>
        </div>
      </div>
      <div class="level-right">
        <div class="level-item">
          <b-field>
            <b-input
              v-model="query"
              placeholder="Search..."
              type="search"
              icon="search"
            ></b-input>
          </b-field>
        </div>
      </div>
    </nav>

    <b-table :data="list" :loading="loading" :selected.sync="selected">
      <b-table-column v-slot="props" field="id" label="ID" sortable numeric>
        {{ props.row.id }}
      </b-table-column>
      <b-table-column v-slot="props" field="id" label="Name" sortable>
        {{ props.row.name }}
      </b-table-column>
      <b-table-column v-slot="props" field="capital" label="Capital">
        {{ props.row.capital }}
      </b-table-column>
      <b-table-column v-slot="props" field="area" label="Area" sortable numeric>
        {{ props.row.area }}
      </b-table-column>
    </b-table>

    <RegionForm title="New Region" @submit="handleCreate"></RegionForm>

    <RegionForm
      title="Edit Region"
      :obj="selected"
      @submit="handleUpdate"
    ></RegionForm>
  </div>
</template>

<script>
import { mapState } from 'vuex'

export default {
  fetch() {
    this.handleRefresh()
  },

  data: () => ({
    selected: {},
    query: '',
  }),

  computed: {
    ...mapState({
      list: (state) => state.regions.list,
      loading: (state) => state.regions.loading,
    }),
  },

  methods: {
    handleRefresh() {
      this.$store.dispatch('regions/fetchList')
    },

    handleCreate(formData) {
      this.$store.dispatch('regions/create', formData).then(() => {
        this.$store.commit('modals/setShown', '')
        this.showInfo('Successfully created')
      })
    },

    handleUpdate(formData) {
      this.$store.dispatch('regions/update', formData).then(() => {
        this.$store.commit('modals/setShown', '')
        this.showInfo('Successfully saved')
      })
    },

    handleDeleteRequest() {
      this.$buefy.dialog.confirm({
        message: 'Delete this region?',
        type: 'is-danger',
        confirmText: 'Delete',
        onConfirm: () => {
          this.$store
            .dispatch('regions/delete', { id: this.selected.id })
            .then(() => {
              this.showInfo('Successfully deleted')
            })
        },
      })
    },
  },
}
</script>
