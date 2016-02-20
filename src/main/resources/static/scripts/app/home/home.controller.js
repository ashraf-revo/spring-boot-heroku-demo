'use strict';

angular.module('revolovexApp')
    .controller('HomeController', function ($scope, $state, Principal,Auth) {
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });
        $scope.logout = function () {
            Auth.logout();
            $state.go('login');
        };
    });
