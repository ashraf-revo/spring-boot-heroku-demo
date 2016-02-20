'use strict';

angular.module('revolovexApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('error', {
                parent: 'site',
                url: '/error',
                data: {
                    roles: [],
                    pageTitle: 'Error'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/error/error.html'
                    }
                }
            })
            .state('accessdenied', {
                parent: 'site',
                url: '/accessdenied',
                data: {
                    roles: [],
                    pageTitle: 'Access Denied'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/error/accessdenied.html'
                    }
                }
            });
    });
