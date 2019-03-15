<template>
  <div>
    <nav aria-label="Page navigation example">
      <ul class="pagination pagination-circle pg-blue justify-content-center">

        <li class="page-item">
          <a class="page-link" v-on:click="setCurrentPosition(1)">1</a>
        </li>

        <li class="page-item">
          <a class="page-link" aria-label="Previous" v-on:click="setCurrentPosition(parseInt(page) - 1)">
            <span aria-hidden="true">&laquo;</span>
            <span class="sr-only">Previous</span>
          </a>
        </li>

        <div v-for="item in paginationItem">
          <div v-if="(parseInt(item) - 1) === parseInt(page) || parseInt(item) === parseInt(page) || (parseInt(item) + 1) === parseInt(page)">

            <li v-if="parseInt(item) === parseInt(page)" class="page-item active">
              <a class="page-link" v-text="item" v-on:click="setCurrentPosition(item)"></a>
            </li>

            <li v-else class="page-item">
              <a class="page-link" v-text="item" v-on:click="setCurrentPosition(item)"></a>
            </li>

          </div>
        </div>

        <li class="page-item">
          <a class="page-link" aria-label="Next" v-on:click="setCurrentPosition(parseInt(page) + 1)">
            <span aria-hidden="true">&raquo;</span>
            <span class="sr-only">Next</span>
          </a>
        </li>

        <li class="page-item">
          <a class="page-link" v-on:click="setCurrentPosition(paginationItem)" v-text="paginationItem"></a>
        </li>

      </ul>
    </nav>
  </div>
</template>

<script>
  import {mapState, mapMutations, mapActions} from 'vuex'

  export default {
    name: "Pagination",
    data() {
      return {
      }
    },
    computed: {
      ...mapState(["paginationItem", "page"])
    },
    methods: {
      ...mapMutations(['setPage']),
      ...mapActions(['retrieveUsers']),
      setCurrentPosition(position) {
        if (position <= this.paginationItem && position >= 1) {
          this.setPage(position);
          this.retrieveUsers();
        }
      },
    },
  }
</script>

<style scoped>

</style>
