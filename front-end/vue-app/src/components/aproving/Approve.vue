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
      ...mapState([
        'id',
        'isUserDelete',
        'isFileDelete'
      ])
    },
    methods: {
      ...mapActions([
        'deleteUser',
        'retrieveUsers',
        'deleteFile',
        'retrieveFiles'
      ]),
      ...mapMutations([
        'enableTableUsers',
        'enablePagination',
        'disableApprove',
        'destroyId',
        'enableTableFiles'
      ]),
      async approve() {
        if (this.isUserDelete) {
          await this.deleteUser(this.id);
          this.retrieveUsers();
          this.enableTableUsers();
        }
        if (this.isFileDelete) {
          await this.deleteFile(this.id);
          this.retrieveFiles();
          this.enableTableFiles();
        }
        this.enablePagination();
        this.disableApprove();
        this.destroyId();
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
