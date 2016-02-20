'use strict';

angular.module('revolovexApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('subject', {
                parent: 'home',
                url: '/subject',
                data: {
                    roles: ['ROLE_ADMIN'],
                    pageTitle: 'subject'
                },
                views: {
                    'homeContent@home': {
                        templateUrl: 'scripts/app/home/admin/subject/subject.html',
                        controller: 'SubjectController'
                    }
                }
            });
    });
