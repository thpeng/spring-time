'use strict';

angular.module('resources.timesheet.entry', []);

angular.module('resources.timesheet.entry').factory("EntryResource", ['$resource', function ($resource) {
    return $resource(
        "../secure/timesheet/:tsId/entry/:Id",
        {Id: "@uuId",
         tsId: "@tsId"},
        {
            "update": {method: "PUT"},
            "delete_entry": {  method: 'DELETE'}
        }
        
                
    );
}]);