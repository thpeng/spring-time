'use strict';

angular.module('services.timesheet', []);

angular.module('services.timesheet')
        .factory('CurrentSheetService', ['$http', '$q', function($http, $q) {
                return{
                    loadSheet: function(currentUser)
                    {
                        var deferredData = $q.defer();

                        $http({method: 'GET', url: '../secure/timesheet', params: {
                                user: currentUser.user.userId.uuId
                            }}).
                                success(function(data, status, headers, config) {
                                    deferredData.resolve(data);
                                    console.debug(data)
                                }).
                                error(function(data, status, headers, config) {
                                    deferredData.reject(data);
                                });

                        return deferredData.promise;
                    }
                }
            }
        ]);