'use strict';

angular.module('revolovexApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('settings', {
                parent: 'home',
                url: '/settings',
                data: {
                    roles: ['ROLE_ADMIN','ROLE_SETTINGS'],
                    pageTitle: 'Settings'
                },
                views: {
                    'homeContent@home': {
                        templateUrl: 'scripts/app/home/admin/settings/settings.html',
                        controller: 'SettingsController'
                    }
                }
            });
    });
