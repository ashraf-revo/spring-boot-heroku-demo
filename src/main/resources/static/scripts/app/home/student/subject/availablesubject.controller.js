'use strict';

angular.module('revolovexApp')
    .controller('AvailableSubjectController', function ($scope, $http) {
        $http.get(document.location.origin + '/api/student/subject')
            .success(function (subject) {
                $scope.subject = subject;
            });
    });
