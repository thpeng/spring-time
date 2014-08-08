'use strict';

angular.module('resources.user', []);

angular.module('resources.user').factory("UserResource", ['$resource', function ($resource) {
    return $resource(
        "../secure/user/:Id",
        {Id: "@userId.uuId" },
        {
            "update": {method: "PUT"}
        }
    );
}]);