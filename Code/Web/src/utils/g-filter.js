import {RoleMap} from './mapping'
import Vue from "vue";
Vue.filter('roleStatusText', function(role) {
   if(RoleMap[role]){
       return RoleMap[role].text;
   }
   return '---'
})
Vue.filter('roleStatusColor', function(role) {
    if(RoleMap[role]){
        return RoleMap[role].color;
    }
    return 'grey'
})