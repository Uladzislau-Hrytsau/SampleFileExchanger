<template>
  <div class="container-fluid">
    <mdb-container>
      <mdb-modal v-if="enabledApprove" @close="cancel" size="sm" class="text-center" dark>
        <mdb-modal-header center>
          <p class="heading">Are you sure?</p>
        </mdb-modal-header>
        <mdb-modal-body>
          <mdb-icon icon="trash-alt" size="4x" class="animated rotateIn"/>
        </mdb-modal-body>
        <mdb-modal-footer center>
          <mdb-btn outline="dark" @click="approve">Yes</mdb-btn>
          <mdb-btn color="dark" @click="cancel">No</mdb-btn>
        </mdb-modal-footer>
      </mdb-modal>
    </mdb-container>
  </div>
</template>

<script>
  import {
    mdbAlert,
    mdbBtn,
    mdbContainer,
    mdbModal,
    mdbModalHeader,
    mdbModalBody,
    mdbModalFooter,
    mdbIcon
  } from 'mdbvue'
  import {
    mapState,
    mapMutations,
    mapActions
  } from 'vuex'

  export default {
    name: "ApproveDelete",
    components: {
      mdbAlert,
      mdbBtn,
      mdbContainer,
      mdbModal,
      mdbModalHeader,
      mdbModalBody,
      mdbModalFooter,
      mdbIcon
    },
    computed: {
      ...mapState([
        'id',
        'isUserDelete',
        'isFileDelete',
        'enabledApprove'
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
        'enableTableFiles',
        'disableUserDelete',
        'disableFileDelete'
      ]),
      async approve() {
        if (this.isUserDelete) {
          await this.deleteUser(this.id);
          this.retrieveUsers();
          this.disableUserDelete();
        }
        if (this.isFileDelete) {
          await this.deleteFile(this.id);
          this.retrieveFiles();
          this.disableFileDelete();
        }
        this.disableApprove();
        this.destroyId();
      },
      cancel() {
        if (this.isUserDelete) {
          this.disableUserDelete();
        }
        if (this.isFileDelete) {
          this.disableFileDelete();
        }
        this.disableApprove();
      }
    }
  }
</script>

<style scoped>

</style>
