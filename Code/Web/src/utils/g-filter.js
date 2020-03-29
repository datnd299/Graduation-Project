import {RoleMap,PartyStatusMap} from './mapping'
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

Vue.filter('partyStatusText', function(role) {
    if(PartyStatusMap[role]){
        return PartyStatusMap[role].text;
    }
    return PartyStatusMap[1].text;
 });

 Vue.filter('partyStatusColor', function(role) {
     if(PartyStatusMap[role]){
         return PartyStatusMap[role].color;
     }
     return PartyStatusMap[1].color;
 })