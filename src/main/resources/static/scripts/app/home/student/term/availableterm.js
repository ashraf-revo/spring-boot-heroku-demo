'use strict';

angular.module('revolovexApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('availableterm', {
                parent: 'home',
                url: '/availableterm',
                data: {
                    roles: ['ROLE_STUDENT'],
                    pageTitle: 'AvailableTerm'
                },
                views: {
                    'homeContent@home': {
                        templateUrl: 'scripts/app/home/student/term/availableterm.html',
                        controller: 'AvailableTermController'
                    }
                }
            });
    });
