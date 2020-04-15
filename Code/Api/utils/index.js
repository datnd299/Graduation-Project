function makeid(length) {
    var result           = '';
    var characters       = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    var charactersLength = characters.length;
    for ( var i = 0; i < length; i++ ) {
       result += characters.charAt(Math.floor(Math.random() * charactersLength));
    }
    return result;
 }

 module.exports.makeCode =()=>{
    return makeid(6);
}

module.exports.getSecond =(val,unit)=>{
   if(unit=='second'){
      return val;
   }else  if(unit=='minute'){
      return val*60;
   }else  if(unit=='hour'){
      return val*3600;
   }else  if(unit=='day'){
      return val*3600*24;
   }else  if(unit=='month'||unit=='moth'){
      return val*3600*24*30;
   }else  if(unit=='year'){
      return vaval*3600*24*30*365;
   }

}