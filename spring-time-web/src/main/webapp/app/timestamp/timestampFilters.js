angular.module('timestamp.filters', []).filter('pretifyDuration', function() {
  return function(input) {
    return input.replace("PT","").toLowerCase();
  };
})