     'use strict';
/* Configuration of other Modules */
angular.module('LocalStorageModule')
        .value('prefix', 'time')
        ;

angular.module('services.navigation.config')
        .value('NAVIGATION_CONFIG', {
            loginState: 'login',
            errorState: 'error'
        })
        ;

angular.module('time', [
    'ui.router',
    'ngCookies',
    'ngResource',
    'LocalStorageModule',
    'services.authentication',
    'services.navigation',
    'services.navigation.config',
    'services.timesheet',
    'common.filters',
    'resources.user']
        )
        .config(function($stateProvider, $urlRouterProvider) {

            $urlRouterProvider.otherwise("/dashboard");

            $stateProvider
                    .state('login', {
                        url: "/login",
                        templateUrl: "partials/login.html",
                        controller: 'LoginCtrl'
                    }).state('dashboard', {
                        url: '/dashboard',
                        templateUrl: 'partials/dashboard.html',
                        controller: 'DashboardCtrl',
                        resolve: {
                            currentUser: function(AuthService) {
                                return AuthService.getCurrentUser();
                            },
                            timesheet: function(CurrentSheetService, currentUser) {
                                return CurrentSheetService.loadSheet(currentUser);                        
                            }
                        }
                    })
                    .state('admin', {
                        url: '/admin',
                        templateUrl: 'partials/admin.html',
                        controller: 'AdminCtrl',
                        resolve: {  
                            currentUser: function(AuthService) {
                                return AuthService.getCurrentUser();
                            },
                            users : function(UserResource) {
                                return UserResource.query().$promise; 
                            }
                        
                        }
                    })
        });
