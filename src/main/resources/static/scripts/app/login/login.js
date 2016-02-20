'use strict';

angular.module('revolovexApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('login', {
                parent: 'site',
                url: '/',
                data: {
                    roles: [], 
                    pageTitle: 'login'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/login/login.html',
                        controller: 'LoginController'
                    }
                }
            });
    });
