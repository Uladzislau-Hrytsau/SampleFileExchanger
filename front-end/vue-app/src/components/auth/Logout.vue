<template>

</template>

<script>
  export default {
    created() {
      this.$store.dispatch('destroyToken')
        .then(response => {
          this.$router.push({name: 'Main'})
        })
        .catch(error => {
          console.log(error)
        })
    }
  }
</script>

<style scoped>

</style>
