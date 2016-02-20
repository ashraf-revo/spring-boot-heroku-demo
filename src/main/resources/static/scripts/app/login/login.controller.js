'use strict';

angular.module('revolovexApp')
    .controller('LoginController', function ($rootScope, $scope, $state, Auth) {
        $scope.user = {};
        $scope.errors = {};

        $scope.rememberMe = true;
        $scope.submit = function () {
            Auth.login({
                username: $scope.username,
                password: $scope.password,
                rememberMe: $scope.rememberMe
            }).then(function () {
                $state.go('home');
                $scope.authenticationError = false;
            }).catch(function () {
                $scope.authenticationError = true;
            });
        };
    });
