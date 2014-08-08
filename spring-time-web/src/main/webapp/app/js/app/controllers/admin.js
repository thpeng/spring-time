'use strict';

angular.module('time')
    .controller('AdminCtrl', ['$scope', 'users', "UserResource", function ( $scope, users, UserResource) {
            $scope.users = users; 
    
            $scope.update = function (selectedUser) {
                UserResource.update(selectedUser);
        };
    }]);