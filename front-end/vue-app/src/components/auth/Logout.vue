<template>

</template>

<script>
  export default {
    created() {
      this.$store.dispatch('destroyToken')
        .then(response => {
          this.$router.push({name: 'Main'})
        })
    }
  }
</script>

<style scoped>

</style>
