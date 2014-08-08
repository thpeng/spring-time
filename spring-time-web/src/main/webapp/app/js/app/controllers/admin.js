'use strict';

angular.module('time')
    .controller('AdminCtrl', ['$scope', 'users', function ( $scope, users) {
            $scope.users = users; 
    }]);
