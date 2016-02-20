'use strict';

angular.module('revolovexApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('student', {
                parent: 'home',
                url: '/student',
                data: {
                    roles: ['ROLE_ADMIN'],
                    pageTitle: 'student'
                },
                views: {
                    'homeContent@home': {
                        templateUrl: 'scripts/app/home/admin/student/student.html',
                        controller: 'StudentController'
                    }
                }
            }).state('studentterm', {
                parent: 'student',
                url: '/?term'
            })
            .state('onestudent', {
                parent: 'home',
                url: '/student/:id',
                data: {
                    roles: ['ROLE_AUTHENTICATED','ROLE_ADMIN'],
                    pageTitle: 'student'
                },
                views: {
                    'homeContent@home': {
                        templateUrl: 'scripts/app/home/admin/student/onestudent.html',
                        controller: 'OneStudentController'
                    }
                }
            });
    });
