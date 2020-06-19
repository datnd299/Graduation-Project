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

module.exports.changeAlias = (alias)=> {
   
   
   var str = alias;
   str = str.toLowerCase();
   str = str.replace(/à|á|ạ|ả|ã|â|ầ|ấ|ậ|ẩ|ẫ|ă|ằ|ắ|ặ|ẳ|ẵ/g,"a"); 
   str = str.replace(/è|é|ẹ|ẻ|ẽ|ê|ề|ế|ệ|ể|ễ/g,"e"); 
   str = str.replace(/ì|í|ị|ỉ|ĩ/g,"i"); 
   str = str.replace(/ò|ó|ọ|ỏ|õ|ô|ồ|ố|ộ|ổ|ỗ|ơ|ờ|ớ|ợ|ở|ỡ/g,"o"); 
   str = str.replace(/ù|ú|ụ|ủ|ũ|ư|ừ|ứ|ự|ử|ữ/g,"u"); 
   str = str.replace(/ỳ|ý|ỵ|ỷ|ỹ/g,"y"); 
   str = str.replace(/đ/g,"d");
   str = str.replace(/!|@|%|\^|\*|\(|\)|\+|\=|\<|\>|\?|\/|,|\.|\:|\;|\'|\"|\&|\#|\[|\]|~|\$|_|`|-|{|}|\||\\/g," ");
   str = str.replace(/ + /g," ");
   str = str.trim(); 
   return str;
}