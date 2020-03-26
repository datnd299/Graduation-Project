<template>
    <div v-if="isLoading" style="    position: absolute;
    width: 100%;
    height: 100%;
    z-index: 10;
    text-align:center;
    background-color: rgba(255, 255, 255, 0.5);">
        <v-progress-circular 
      :size="70"
      :width="7"
      color="purple"
      indeterminate
    ></v-progress-circular>
    </div>
</template>
<script>
export default {
    props:{
        isLoading:Boolean
    }
}
</script>