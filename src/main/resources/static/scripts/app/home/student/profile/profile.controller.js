'use strict';

angular.module('revolovexApp')
    .controller('ProfileController', function ($scope, $http) {
        $http.get(document.location.origin + '/api/student/student')
            .success(function (student) {
                $scope.student = student;
            });
        $scope.hours = function () {
            var hours = 0;
            if (angular.isDefined($scope.student))
                for (var x = 0; x < $scope.student.pt.length; x++) {
                    for (var k = 0; k < $scope.student.pt[x].ps.length; k++) {
                        if ($scope.student.pt[x].ps[k].state == "success") hours += $scope.student.pt[x].ps[k].subject.hour
                    }
                }
            return hours;
        };
    });
