'use strict';

angular.module('time')
    .controller('DashboardCtrl', ['$scope','timesheet', function ( $scope, timesheet) {
          $scope.timesheet = timesheet; 
            
    }]);
