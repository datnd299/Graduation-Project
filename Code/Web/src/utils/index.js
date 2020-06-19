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

  module.exports={
    //  BASE_API:'http://103.35.64.5:8086/api/v1/',

     debounce_func:debounce
    // BASE_API:'http://192.168.0.104:8086/api/v1/'


}