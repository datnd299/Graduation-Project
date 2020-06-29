function debounce(func, wait) {
    var timeout;
  
    return function() {
      var context = this,
          args = arguments;
  
      var executeFunction = function() {
        func.apply(context, args);
      };
  
      clearTimeout(timeout);
      timeout = setTimeout(executeFunction, wait);
    };
  };
  function calcCrow(lat1, lon1, lat2, lon2) 
  {

    var R = 6371000; // km
    var dLat = toRad(lat2-lat1);
    var dLon = toRad(lon2-lon1);
    lat1 = toRad(lat1);
    lat2 = toRad(lat2);

    var a = Math.sin(dLat/2) * Math.sin(dLat/2) +
      Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat2); 
    var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
    var d = R * c;
    return d.toFixed(2);
  }

  // Converts numeric degrees to radians
  function toRad(Value) 
  {
      return Value * Math.PI / 180;
  }
  module.exports={
    //  BASE_API:'http://103.35.64.5:8086/api/v1/',

     debounce_func:debounce,
     calcCrow:calcCrow
    // BASE_API:'http://192.168.0.104:8086/api/v1/'


}