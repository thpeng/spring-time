'use strict';

angular.module('time.timestamp')
    .controller('TimestampMasterCtrl', ['$scope', 'entries','timesheet', 'EntryResource', function ( $scope, entries, timesheet, EntryResource) {
          $scope.entries = entries;
  
          $scope.delete = function (entry) {
                EntryResource.delete_entry({tsId: timesheet.timesheetId.uuId},{uuId: entry.uuId},
                    function(response){
                        var index = entries.indexOf(entry);
                        if (index > -1) {
                            entries.splice(index, 1);
                        }
                        $scope.alerts.push({type : 'success', msg : 'EntryId ' +entry.uuId+ ' deleted!'});
                        
                    },function(error){
                        $scope.alerts.push({type : 'danger', msg : 'Error code: '+error.status});
                    });
                };   
    }]);
