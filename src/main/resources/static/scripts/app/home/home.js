'use strict';

angular.module('revolovexApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('home', {
                url: '/home',
                data: {
                    roles: ['ROLE_AUTHENTICATED'],
                    pageTitle:"Welcome"
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/home/home.html',
                        controller: 'HomeController'

                    },
                    'homeNavbar@home': {
                        templateUrl: 'scripts/components/navbar/homeNavbar.html'
                    },
                    'homeContent@home': {
                        templateUrl: 'scripts/app/home/homeContent.html'
                    }
                },parent: 'site'
            });
    });
