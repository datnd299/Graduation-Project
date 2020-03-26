<template>
  <v-app>
    <div style=" background:#dfe6e9;height: 100%">
      <div style="max-width:960px;     margin:0 auto">
        <v-card
          style="background:white; border-radius:5px; margin-top:40px;margin-left:5px;margin-right:5px"
        >
          <v-row no-gutters>
            <v-col cols="12" xs="0" sm="6" md="6">
              <div
                style="background-image: url(https://cdn.shopify.com/s/files/1/1991/1709/products/Conected-tile-800x800.jpg?v=1575931838); height:100%;
              padding:20px"
              >
                <img src="/assets/media/logos/logo-mini-md.png" alt />
                <span style="font-size: 30px;
    font-weight: 600;
    color: #636e72;">SignboardMn</span>
              </div>
            </v-col>
            <v-col cols="12" xs="24" sm="6" md="6" style="padding:40px">
             <router-view></router-view>
            </v-col>
          </v-row>
        </v-card>
      </div>
    </div>
  </v-app>
</template>
<script>
export default {
  
}
</script>