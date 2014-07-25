'use strict';

angular.module('time')
    .controller('LoginCtrl', ['AuthService', '$scope', '$http', '$rootScope', function (auth, $scope, $http, $rootScope) {

        if ($rootScope.currentUser) {
            alert("You are already logged in");
            $rootScope.$broadcast("LoginSuccess", $rootScope.currentUser);
        }

        $scope.login = function () {
            auth.login($scope.username, $scope.password).then(
                function (currentUser) {
                    $rootScope.$broadcast("LoginSuccess", currentUser);
                },
                function () {
                    $scope.alert = {
                        msg: "Login Failed"
                    };
                }
            );
        };
    }]);
