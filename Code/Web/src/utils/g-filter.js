import {RoleMap,PartyStatusMap,PlaceRentalStatusMap,TimeUnit,TaskType,TaskStatus,RepeatType} from './mapping'
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

 Vue.filter('taskStatusColor', function(role) {
     if(TaskStatus[role]){
         return TaskStatus[role].color;
     }
     return TaskStatus[1].color;
 })

 Vue.filter('repeatTypeText', function(type) {
    if(RepeatType[type]){
        return RepeatType[type].text;
    }
    return RepeatType['once'].text;
 });

 Vue.filter('repeatTypeColor', function(type) {
     if(RepeatType[type]){
         return RepeatType[type].color;
     }
     return RepeatType['once'].color;
 })

 Vue.filter('taskStatusText', function(role) {
    if(TaskStatus[role]){
        return TaskStatus[role].text;
    }
    return TaskStatus[1].text;
 });

 Vue.filter('partyStatusColor', function(role) {
     if(PartyStatusMap[role]){
         return PartyStatusMap[role].color;
     }
     return PartyStatusMap[1].color;
 })

 Vue.filter('placeRentalStatusText', function(status) {
    if(PlaceRentalStatusMap[status]){
        return PlaceRentalStatusMap[status].text;
    }
    return PlaceRentalStatusMap[1].text;
 });

 Vue.filter('placeRentalStatusColor', function(status) {
     if(PlaceRentalStatusMap[status]){
         return PlaceRentalStatusMap[status].color;
     }
     return PlaceRentalStatusMap[1].color;
 })


 Vue.filter('taskTypeText', function(type) {
    if(TaskType[type]){
        return TaskType[type].text;
    }
    return '';
 });

 Vue.filter('taskTypeColor', function(type) {
     if(TaskType[type]){
         return TaskType[type].color;
     }
     return '';
 })
 Vue.filter('taskTypeIcon', function(type) {
    if(TaskType[type]){
        return TaskType[type].icon;
    }
    return '';
})
 
 
 Vue.filter('timeUnitText', function(val) {
    if(TimeUnit[val]){
        return TimeUnit[val].text;
    }
    return '---';
 });
 Vue.filter('numberF', function(val) {
   
    return val.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
   //return val.toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$&,');
 });

 Vue.filter('codeSignboard', function(code) {
    if(code.indexOf('__')){
        return(code.substr(code.indexOf('__')+2))
    }
    return ''
 });


