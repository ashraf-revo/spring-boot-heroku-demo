'use strict';

angular.module('revolovexApp')
    .controller('TermController', function ($scope, $http, dialogs) {
        $http.get(document.location.origin + '/api/admin/term')
            .success(function (term) {
                $scope.term = term;
            });
        $scope.form = {minHour: 15, maxHour: 20, enabled: 'true'};
        $scope.save = function () {
            $http.post(document.location.origin + "/api/admin/term", $scope.form).success(function (term) {
                $scope.term.push(term);
                $scope.form = {minHour: 15, maxHour: 20, enabled: 'true'};
            });

        };
        $scope.delete = function (term) {
            $http.delete(document.location.origin + "/api/admin/term/" + term.id).success(function () {
                for (var i = $scope.term.length - 1; i >= 0; i--) {
                    if ($scope.term[i].id == term.id) {
                        $scope.term.splice(i, 1);
                    }
                }
            });
        };
        $scope.update = function (term) {
            $http.get(document.location.origin + "/api/admin/term/" + term.id).success(function (data) {
                var dlg = dialogs.create('scripts/components/dialogs/updateterm.html', 'updateterm', data);
                dlg.result.then(function (result) {
                    for (var i = $scope.term.length - 1; i >= 0; i--) {
                        if ($scope.term[i].id == result.id) {
                            $scope.term[i] = result;
                        }
                    }

                });
            });
        };
    }).controller('updateterm', function ($scope, $uibModalInstance, data, $http) {
        $scope.form = data;
        $scope.form.enabled = String(data.enabled);
        $scope.cancel = function () {
            $uibModalInstance.dismiss();
        };
        $scope.save = function () {
            $http.post(document.location.origin + "/api/admin/term", $scope.form).success(function (term) {
                $uibModalInstance.close(term);
            });
        };
    });