'use strict';

angular.module('revolovexApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('register', {
                parent: 'site',
                url: '/register',
                data: {
                    authorities: [],
                    pageTitle: 'register'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/register/register.html',
                        controller: 'RegisterController'
                    }
                }
            });
    });
