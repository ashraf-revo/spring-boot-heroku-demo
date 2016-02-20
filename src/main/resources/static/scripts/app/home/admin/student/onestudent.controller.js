'use strict';

angular.module('revolovexApp')
    .controller('OneStudentController', function ($scope, $http, dialogs, $stateParams) {
        $http.get(document.location.origin + '/api/admin/student/' + $stateParams.id)
            .success(function (student) {
                $scope.student = student;
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


        $scope.delete = function (ps) {
            $http.delete(document.location.origin + "/api/admin/ps/" + ps.id).success(function () {
                for (var i = $scope.student.pt.length - 1; i >= 0; i--) {
                    for (var j = $scope.student.pt[i].ps.length - 1; j >= 0; j--) {
                        if ($scope.student.pt[i].ps[j].id == ps.id) {
                            $scope.student.pt[i].ps.splice(j, 1);
                        }
                    }
                }
            });
        };
        $scope.update = function (ps) {


            $http.get(document.location.origin + "/api/admin/ps/" + ps.id).success(function (data) {
                var dlg = dialogs.create('scripts/components/dialogs/updateps.html', 'updatePs', data, {size: 'lg'});
                dlg.result.then(function (result) {
                    for (var i = $scope.student.pt.length - 1; i >= 0; i--) {
                        for (var j = $scope.student.pt[i].ps.length - 1; j >= 0; j--) {
                            if ($scope.student.pt[i].ps[j].id == ps.id) {
                                $scope.student.pt[i].ps[j] = result;
                            }
                        }
                    }
                });
            });
        };
    }).controller('updatePs', function ($scope, $uibModalInstance, data, $http) {
        $scope.ps = data;
        $scope.cancel = function () {
            $uibModalInstance.dismiss();
        };
        $scope.save = function () {
            $http.post(document.location.origin + "/api/admin/ps/", $scope.ps).success(function (ps) {
                $uibModalInstance.close(ps);
            });
        };
    });