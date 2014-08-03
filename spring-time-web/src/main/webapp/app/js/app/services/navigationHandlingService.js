'use strict';

angular.module('services.navigation.config', [])
    .value('NAVIGATION_CONFIG', {
        loginState: undefined,
        errorState: undefined
    });

angular.module('services.navigation', ['ui.router', 'services.authentication', 'services.navigation.config'])

    .run(['$rootScope', '$state', 'AuthService', 'NAVIGATION_CONFIG', function ($rootScope, $state, AuthService, NAVIGATION_CONFIG ) {

        var stateChangeInProcess = false;

        $rootScope.$on("$stateChangeStart", function (event, toState, toParams, fromState, fromParams) {
            console.debug("Navigating from '" + fromState.name + "' to '" + toState.name + "'");
            stateChangeInProcess = true;
            AuthService.loadAuthHeader();
        });

        $rootScope.$on("$stateChangeSuccess", function (event, toState, toParams, fromState, fromParams) {
            console.debug("Navigated from '" + fromState.name + "' to '" + toState.name + "'");
            stateChangeInProcess = false;
        });

        $rootScope.$on("$stateChangeError", function (event, toState, toParams, fromState, fromParams, error) {
            console.warn('Could not change to state: ' + toState.name + '\'');
            stateChangeInProcess = false;
            if (error.state === 401) {
                AuthService.logout();
                console.debug('Saving Navigation Request: ' + toState.name);
                $rootScope.savedState = {state: toState.name, params: toParams};
                $state.go(NAVIGATION_CONFIG.loginState);
            } else {
                console.debug(error); 
                var message = error.data.message || '';
                console.debug('error occured: '+message);
                $state.go(NAVIGATION_CONFIG.errorState);
            }
        });

        $rootScope.$on('Unauthorized', function () {
            // only redirect if we are not within at state change-process
            if (!stateChangeInProcess) {
                console.warn('received unauthorized Event: Redirect to Login Page');
                $state.go(NAVIGATION_CONFIG.loginState);
            }
        });

        $rootScope.$on("LoginSuccess", function () {
            console.info('User is now logged in');
            var savedState = $rootScope.savedState;
            if (savedState) {
                console.debug('Restoring saved navigation to: ' + savedState.state.name);
                $state.go(savedState.state, savedState.params);
                $rootScope.savedState = undefined;
            } else {
                $state.go('dashboard');
            }
        })
    }])

    .config(["$httpProvider", function ($httpProvider, $rootScope) {
        $httpProvider.interceptors.push(function ($q) {
            return {
                'responseError': function (rejection) {
                    if (rejection.status === 401) {
                        $rootScope.$broadcast("Unauthorized");
                    }
                    return $q.reject(rejection);
                }
            };
        });
    }])
;