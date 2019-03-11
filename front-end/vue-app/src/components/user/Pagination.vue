<template>
  <div>
    <nav aria-label="Page navigation example">
      <ul class="pagination pagination-circle pg-blue">
        <li class="page-item disabled">
          <a class="page-link">First</a>
        </li>
        <li class="page-item disabled">
          <a class="page-link" aria-label="Previous">
            <span aria-hidden="true">&laquo;</span>
            <span class="sr-only">Previous</span>
          </a>
        </li>

        <div v-for="record in recordAmount" v-bind:key="record">
          <li class="page-item active"
              v-if="(recordAmount === record)"
              v-on:click="setCurrentRecord(record)">
            <a class="page-link " v-text="record"></a>
          </li>
          <li class="page-item"
              v-else
              v-on:click="setCurrentRecord(record)">
            <a class="page-link " v-text="record"></a>
          </li>
        </div>

        <li class="page-item">
          <a class="page-link" aria-label="Next">
            <span aria-hidden="true">&raquo;</span>
            <span class="sr-only">Next</span>
          </a>
        </li>
        <li class="page-item"><a class="page-link">Last</a></li>
      </ul>
    </nav>
  </div>
</template>

<script>

  export default {
    name: "Pagination",
    data() {
      return {
        recordAmount: this.getRecordAmount / this.getPaginationSize,
        currentRecord: 1,
      }
    },
    computed: {
      getRecordAmount() {
        return this.$store.getters.getRecordAmount();
      },
      getPaginationSize() {
        return this.$store.getters.getPaginationSize();
      }
    },
    methods: {
      setCurrentRecord(currentRecord) {
        this.$store.commit('currentPagination', currentRecord);
        this.currentRecord = currentRecord;
      },
    },
  }
</script>

<style scoped>

</style>
