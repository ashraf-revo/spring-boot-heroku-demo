'use strict';

angular.module('revolovexApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('term', {
                parent: 'home',
                url: '/term',
                data: {
                    roles: ['ROLE_ADMIN'],
                    pageTitle: 'term'
                },
                views: {
                    'homeContent@home': {
                        templateUrl: 'scripts/app/home/admin/term/term.html',
                        controller: 'TermController'
                    }
                }
            });
    });
