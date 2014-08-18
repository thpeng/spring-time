     'use strict';

angular.module('time.timestamp', [
    'ui.router',
    'ngResource',
    'services.authentication',
    'services.timesheet',
    'resources.user',
    'ui.bootstrap',
    'resources.timesheet.entry',
    'timestamp.filters'
]
        )
        .config(function($stateProvider) {

            $stateProvider
                    .state('timestamp', {
                        abstract: true, 
                        controller: 'TimestampCtrl',
                         templateUrl: 'timestamp/timestamp.html', 
                         resolve: {
                            currentUser: function(AuthService) {
                                return AuthService.getCurrentUser();
                            },
                            timesheet: function(CurrentSheetService, currentUser) {
                                return CurrentSheetService.loadSheet(currentUser);                        
                            }
                            
                            
                        }
                    })
                    .state('timestamp.master', {
                        url: "/stamp",
                        templateUrl: "timestamp/timestampMaster.html",
                        controller: 'TimestampMasterCtrl',
                         resolve: {
                            entries: function(EntryResource, timesheet){
                                return EntryResource.query({tsId: timesheet.timesheetId.uuId});
                            }
                            
                        }
                    }).state('timestamp.detail', {
                        url: "/stamp/:id",
                        templateUrl: "timestamp/timestampDetail.html",
                        controller: 'TimestampDetailCtrl',
                        resolve : {
                            entry: function(EntryResource,  $stateParams, timesheet){
                                return EntryResource.get({tsId: timesheet.timesheetId.uuId, Id: $stateParams.id});
                            }
                        }
                    })
                    
        });
