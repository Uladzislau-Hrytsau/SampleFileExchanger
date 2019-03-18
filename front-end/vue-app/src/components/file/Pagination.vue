<template>
  <div>
    <nav aria-label="Page navigation example">
      <ul class="pagination pagination-circle pg-dark justify-content-center">

        <li class="page-item">
          <a class="page-link" v-on:click="setCurrentPosition(1)">1</a>
        </li>

        <li class="page-item">
          <a class="page-link" aria-label="Previous" v-on:click="setCurrentPosition(parseInt(pageFile) - 1)">
            <span aria-hidden="true">&laquo;</span>
            <span class="sr-only">Previous</span>
          </a>
        </li>

        <div v-for="item in paginationItem">
          <div v-if="(parseInt(item) - 1) === parseInt(pageFile) || parseInt(item) === parseInt(pageFile) || (parseInt(item) + 1) === parseInt(pageFile)">

            <li v-if="parseInt(item) === parseInt(pageFile)" class="page-item active">
              <a class="page-link" v-text="item" v-on:click="setCurrentPosition(item)"></a>
            </li>

            <li v-else class="page-item">
              <a class="page-link" v-text="item" v-on:click="setCurrentPosition(item)"></a>
            </li>

          </div>
        </div>

        <li class="page-item">
          <a class="page-link" aria-label="Next" v-on:click="setCurrentPosition(parseInt(pageFile) + 1)">
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
  import {mapState, mapGetters, mapActions, mapMutations} from 'vuex'

  export default {
    name: "Pagination",
    computed: {
      ...mapState(["paginationItem", "pageFile"])
    },
    methods: {
      ...mapMutations(['setPageFile']),
      ...mapActions(['retrieveFiles']),
      setCurrentPosition(position) {
        if (position <= this.paginationItem && position >= 1 && position !== this.pageFile) {
          this.setPageFile(position);
          this.retrieveFiles();
        }
      },
    },
  }
</script>

<style scoped>

</style>
