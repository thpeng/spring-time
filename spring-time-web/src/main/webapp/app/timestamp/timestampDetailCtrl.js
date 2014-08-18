'use strict';

angular.module('time.timestamp')
    .controller('TimestampDetailCtrl', ['$scope', 'entry', 'timesheet','EntryResource', function ( $scope, entry, timesheet, EntryResource) {
            $scope.entry = entry;
  
            $scope.update = function (
                entry) {
                EntryResource.update({tsId: timesheet.timesheetId.uuId, id: entry.uuId}, entry,
                    function(response){
                        $scope.alerts.push({type : 'success', msg : 'EntryId ' +response.uuId+ ' updated!'});
                    },function(error){
                        $scope.alerts.push({type : 'danger', msg : 'Error code: '+error.status});
                    });
                }; 
                
                
            
    }]);
