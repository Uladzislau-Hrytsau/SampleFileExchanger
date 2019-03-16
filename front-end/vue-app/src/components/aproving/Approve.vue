<template>
  <mdb-alert color="primary">
    <button class="btn" v-on:click="approve">approve</button>
    <button class="btn" v-on:click="cancel">cancel</button>
  </mdb-alert>
</template>

<script>
  import {mdbAlert, mdbBtn} from 'mdbvue'
  import {mapState, mapMutations, mapActions} from 'vuex'

  export default {
    name: "ApproveDelete",
    components: {
      mdbAlert,
      mdbBtn
    },
    computed: {
      ...mapState(['id'])
    },
    methods: {
      ...mapActions([
        'deleteUser',
        'retrieveUsers'
      ]),
      ...mapMutations([
        'enableTableUsers',
        'enablePagination',
        'disableApprove'
      ]),
      async approve() {
        await this.deleteUser(this.id);
        this.retrieveUsers();
        this.enableTableUsers();
        this.enablePagination();
        this.disableApprove();
      },
      cancel() {
        this.enableTableUsers();
        this.enablePagination();
        this.disableApprove();
      }
    }
  }
</script>

<style scoped>

</style>
