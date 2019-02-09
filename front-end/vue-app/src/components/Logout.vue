<template>

</template>

<script>
  export default {
    created() {
      this.$store.dispatch('destroyToken')
        .then(response => {
          this.$router.push({name: 'Index'})
        })
    }
  }
</script>

<style scoped>

</style>
