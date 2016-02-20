'use strict';

angular.module('revolovexApp')
    .controller('RegisterController', function ($scope, Auth) {
        $scope.submit = function () {
            Auth.createAccount($scope.form)
                .then(function () {
                });
        };
    });
