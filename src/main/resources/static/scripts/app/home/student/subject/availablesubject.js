'use strict';

angular.module('revolovexApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('availablesubject', {
                parent: 'home',
                url: '/availablesubject',
                data: {
                    roles: ['ROLE_STUDENT'],
                    pageTitle: 'AvailableSubject'
                },
                views: {
                    'homeContent@home': {
                        templateUrl: 'scripts/app/home/student/subject/availablesubject.html',
                        controller: 'AvailableSubjectController'
                    }
                }
            });
    });
