<template>
  <ul class="kt-menu__nav">
    <template v-for="(menu, i) in menuItems">
      <KTMenuItem v-if="!menu.section" :menu="menu" :key="i"></KTMenuItem>
      <KTMenuSection v-if="menu.section" :menu="menu" :key="i"></KTMenuSection>
    </template>
  </ul>
</template>

<script>
import KTMenuItem from "@/views/theme/aside/MenuItem.vue";
import KTMenuSection from "@/views/theme/aside/MenuSection.vue";
import menuConfig from "@/common/config/menu.config.json";

import {GetRole} from '@/utils/auth';
export default {
  name: "KTAsideMenu",
  components: {
    KTMenuItem,
    KTMenuSection
  },
  computed: {
    menuItems () {
      var role = GetRole()
      if(role=="admin"){
        role = "1";
      }else if(role =="partyAAdmin"){
        role ="2";
      }else if(role =="partyAEm"){
        role = "3";
      }else{
        role = "4";
      }
      console.log(role);
      
      return menuConfig.aside.items.filter(e=>{
        return e.role.includes(role)
      });
    }
  }
};
</script>
