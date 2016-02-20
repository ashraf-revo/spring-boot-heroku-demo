'use strict';

angular.module('revolovexApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('profile', {
                parent: 'home',
                url: '/profile',
                data: {
                    roles: ['ROLE_STUDENT'],
                    pageTitle: 'Profile'
                },
                views: {
                    'homeContent@home': {
                        templateUrl: 'scripts/app/home/student/profile/profile.html',
                        controller: 'ProfileController'
                    }
                }
            });
    });
