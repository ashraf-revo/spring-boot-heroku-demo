'use strict';

angular.module('revolovexApp')
    .controller('SubjectController', function ($scope, $http, dialogs) {
        $http.get(document.location.origin + '/api/admin/subject/')
            .success(function (subject) {
                $scope.subject = subject;
            });
        $scope.form = {hour: 3, maxGrade: 100};
        $scope.save = function () {
            $http.post(document.location.origin + "/api/admin/subject", $scope.form).success(function (subject) {
                $scope.subject.push(subject);
                $scope.form = {hour: 3, maxGrade: 100};
            });

        };
        $scope.delete = function (subject) {
            $http.delete(document.location.origin + "/api/admin/subject/" + subject.id).success(function () {
                for (var i = $scope.subject.length - 1; i >= 0; i--) {
                    if ($scope.subject[i].id == subject.id) {
                        $scope.subject.splice(i, 1);
                    }
                }
            });
        };
        $scope.update = function (subject) {
            $http.get(document.location.origin + "/api/admin/subject/" + subject.id).success(function (data) {
                var dlg = dialogs.create('scripts/components/dialogs/updatesubject.html', 'updatesubject', data);
                dlg.result.then(function (result) {
                    for (var i = $scope.subject.length - 1; i >= 0; i--) {
                        if ($scope.subject[i].id == result.id)
                            $scope.subject[i] = result;
                        for (var k = $scope.subject[i].required.length - 1; k >= 0; k--) {
                            if ($scope.subject[i].required[k].id == result.id)
                                $scope.subject[i].required[k] = result;
                        }
                    }
                });
            });
        };
        $scope.change = function (subject) {

            $http.get(document.location.origin + "/api/admin/subject/" + subject.id).success(function (data) {
                var dlg = dialogs.create('scripts/components/dialogs/updaterequired.html', 'updaterequired', {
                    'one': data,
                    'all': $scope.subject
                });
                dlg.result.then(function (result) {
                    for (var i = $scope.subject.length - 1; i >= 0; i--) {
                        if ($scope.subject[i].id == result.id)
                            $scope.subject[i] = result;
                    }
                });
            });
        };

    }).controller('updaterequired', function ($scope, $uibModalInstance, data, $filter, $http) {
        $scope.form = data.one;
        $scope.all = data.all;
        for (var ki = 0; ki < $scope.all.length; ki++) {
            $scope.all[ki].selected = false;
        }
        for (var i = 0; i < $scope.form.required.length; i++) {
            for (var k = 0; k < $scope.all.length; k++) {
                if ($scope.all[k].id == $scope.form.required[i].id) {
                    $scope.all[k].selected = true;
                }
            }
        }
        $scope.selectChange = function () {
            $scope.form.required = $filter('filter')($scope.all, {selected: true});
        };
        $scope.cancel = function () {
            $uibModalInstance.dismiss();
        };
        $scope.save = function () {
            $http.put(document.location.origin + "/api/admin/subject/", $scope.form).success(function (subject) {
                $uibModalInstance.close(subject);
            });
        };
    }).controller('updatesubject', function ($scope, $uibModalInstance, data, $http) {
        $scope.form = data;
        $scope.cancel = function () {
            $uibModalInstance.dismiss();
        };
        $scope.save = function () {
            $http.post(document.location.origin + "/api/admin/subject", $scope.form).success(function (subject) {
                $uibModalInstance.close(subject);
            });
        };
    });