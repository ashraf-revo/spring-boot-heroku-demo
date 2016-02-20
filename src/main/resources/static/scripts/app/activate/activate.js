'use strict';

angular.module('revolovexApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('activate', {
                parent: 'site',
                url: '/activate?key',
                data: {
                    authorities: [],
                    pageTitle: 'Activate'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/activate/activate.html',
                        controller: 'ActivationController'
                    }
                }
            });
    });
