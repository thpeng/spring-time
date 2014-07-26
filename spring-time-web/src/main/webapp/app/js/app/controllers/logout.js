'use strict';

angular.module('time')
    .controller('LogoutCtrl', ['AuthService', '$scope', '$state', '$rootScope', function (auth, $scope, $state, $rootScope) {

        if ($rootScope.currentUser === null) {
            alert("no currentUser set!");
        }

        $scope.logout = function () {
            auth.logout();
            console.debug('logout');
            $state.transitionTo('login');
        };
    }]);
