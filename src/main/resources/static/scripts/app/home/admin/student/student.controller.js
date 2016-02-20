'use strict';

angular.module('revolovexApp')
    .controller('StudentController', function ($scope, $http,dialogs,$state) {
        var f = 0;
        $scope.term = "";
        $scope.textterm = "";
        $scope.form = {};
        if (!isNaN($state.params.term)) {
            if (!isNaN(parseInt($state.params.term))) {
                $scope.term = "?term=" + parseInt($state.params.term);
                $scope.textterm = "From Term " + parseInt($state.params.term);
                $http.get(document.location.origin + '/api/admin/student/?term=' + $state.params.term)
                    .success(function (student) {
                        $scope.student = student;
                    });
            } else f = 1;
        } else f = 1;

        if (f == 1) {
            $http.get(document.location.origin + '/api/admin/student')
                .success(function (student) {
                    $scope.student = student;
                });
        }
        $scope.save = function () {
            $http.post(document.location.origin + "/api/admin/student", $scope.form).success(function (student) {
                if ($scope.form.id == null) {
                    $scope.form = {};
                    $scope.student.push(student);
                }
                else {
                    $scope.form = {};
                }
            });
        };
        $scope.delete = function (student) {
            $http.delete(document.location.origin + "/api/admin/student/" + student.id + $scope.term).success(function () {
                for (var i = $scope.student.length - 1; i >= 0; i--) {
                    if ($scope.student[i].id == student.id) {
                        $scope.student.splice(i, 1);
                    }
                }
            });
        };
        $scope.update = function (student) {
            $http.get(document.location.origin + "/api/admin/student/" + student.id).success(function (data) {
                var dlg = dialogs.create('scripts/components/dialogs/updatestudent.html', 'updatestudent', data);
                dlg.result.then(function (result) {
                    for (var i = $scope.student.length - 1; i >= 0; i--) {
                        if ($scope.student[i].id == result.id) {
                            $scope.student[i] = result;
                        }
                    }

                });
            });
        };
    }).controller('updatestudent', function ($scope, $uibModalInstance, data, $http) {
        $scope.form = data;
        $scope.cancel = function () {
            $uibModalInstance.dismiss();
        };
        $scope.save = function () {
            $http.post(document.location.origin + "/api/admin/student", $scope.form).success(function (student) {
                $uibModalInstance.close(student);
            });
        };
    });