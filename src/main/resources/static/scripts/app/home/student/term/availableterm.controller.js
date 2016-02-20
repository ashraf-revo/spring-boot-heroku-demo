'use strict';

angular.module('revolovexApp')
    .controller('AvailableTermController', function ($scope, $http, dialogs) {
        $http.get(document.location.origin + '/api/student/pt')
            .success(function (pt) {
                $scope.pt = pt;
            });
        $scope.load = function (pt) {
            $http.get(document.location.origin + '/api/student/pt/' + pt.id)
                .success(function (one) {
                    var dlg = dialogs.create('scripts/components/dialogs/updatept.html', 'pt', one);
                    dlg.result.then(function (result) {
                        $http.post(document.location.origin + '/api/student/pt', result)
                            .success(function (out) {
                            });
                    });
                });
        };
    }).controller('pt', function ($scope, $uibModalInstance, data, $http) {
        $scope.data = data;
        $scope.cancel = function () {
            $uibModalInstance.dismiss();
        };
        $scope.save = function () {
            for (var i = $scope.data.ps.length - 1; i >= 0; i--) {
                if ($scope.data.ps[i].subject.selected != true) {
                    $scope.data.ps.splice(i, 1);
                }
            }
            $uibModalInstance.close($scope.data);
        };
    }
);
