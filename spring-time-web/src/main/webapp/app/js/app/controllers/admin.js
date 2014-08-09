'use strict';

angular.module('time')
    .controller('AdminCtrl', ['$scope', 'users', "UserResource", function ( $scope, users, UserResource) {
            $scope.users = users; 
    
            $scope.update = function (
                selectedUser) {
                UserResource.update(selectedUser, 
                    function(response){
                        $scope.alerts.push({type : 'success', msg : 'UserId ' +response.userId.uuId+ ' updated!'});
                    },function(error){
                        $scope.alerts.push({type : 'danger', msg : 'Error code: '+error.status});
                    });
                };
            $scope.alerts = [];

            $scope.addAlert = function() {
                $scope.alerts.push({msg: 'Another alert!'});
            };

            $scope.closeAlert = function(index) {
                $scope.alerts.splice(index, 1);
            };
    }]);