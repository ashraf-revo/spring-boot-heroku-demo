'use strict';

angular.module('revolovexApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


