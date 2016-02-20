'use strict';

angular.module('revolovexApp', ['LocalStorageModule',
    'ngResource', 'ui.router', 'ngCookies', 'ngCacheBuster', 'ui.bootstrap', 'dialogs.main'])

    .run(function ($rootScope, $window, $http, $state, Auth, Principal) {
        $rootScope.$on('$stateChangeStart', function (event, toState, toStateParams) {
            $rootScope.toState = toState;
            $rootScope.toStateParams = toStateParams;

            if (Principal.isIdentityResolved()) {
                Auth.authorize();
            }
        });

        $rootScope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams) {
            var titleKey = 'any tilte ';
            if ((toState.name == 'login' ) && Principal.isAuthenticated()) {
                $state.go('home');
            }

            $rootScope.previousStateName = fromState.name;
            $rootScope.previousStateParams = fromParams;

            if (toState.data.pageTitle) {
                titleKey = toState.data.pageTitle;
            }

            $window.document.title = titleKey;
        });
    })

    .config(function ($stateProvider, $urlRouterProvider, $httpProvider, $locationProvider, httpRequestInterceptorCacheBusterProvider) {
        //enable CSRF
        $httpProvider.defaults.xsrfCookieName = 'CSRF-TOKEN';
        $httpProvider.defaults.xsrfHeaderName = 'X-CSRF-TOKEN';

        //Cache everything except rest api requests
        httpRequestInterceptorCacheBusterProvider.setMatchlist([/.*api.*/, /.*protected.*/], true);

        $urlRouterProvider.otherwise('/');
        $stateProvider.state('site', {
            'abstract': true,
            resolve: {
                authorize: ['Auth',
                    function (Auth) {
                        return Auth.authorize();
                    }
                ]
            }
        });
    }).config(['dialogsProvider', function (dialogsProvider) {
        dialogsProvider.useBackdrop('static');
        dialogsProvider.useEscClose(false);
        dialogsProvider.useCopy(false);
        dialogsProvider.setSize('sm');
    }]);