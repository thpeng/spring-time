angular.module('common.filters', []).filter('addHourSymbol', function() {
  return function(input) {
    return input +"h";
  };
}).filter('doubleToPercentage', function() {
  return function(input) {
    return (input*100)+'%';
  };
});