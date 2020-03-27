<template>
  <v-app class="v-wrap" xasdf>
    <transition name="fade-in-up">
      <router-view></router-view>
    </transition>
  </v-app>
</template>

<style lang="scss" scoped>
.theme--light.v-application {
  background: inherit;
}
</style>

<script>
export default {
  components: {}
};
</script>
<style  >
.v-wrap[xasdf] .v-application--wrap {
    min-height: unset;
}
</style>
