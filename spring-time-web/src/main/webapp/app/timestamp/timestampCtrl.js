'use strict';

angular.module('time.timestamp')
    .controller('TimestampCtrl', ['$scope','timesheet', 'EntryResource', function ( $scope, timesheet, EntryResource) {
          $scope.timesheet = timesheet; 
  
          $scope.alerts = [];

            $scope.addAlert = function() {
                $scope.alerts.push({msg: 'Another alert!'});
            };

            $scope.closeAlert = function(index) {
                $scope.alerts.splice(index, 1);
            };
           
    }]);
