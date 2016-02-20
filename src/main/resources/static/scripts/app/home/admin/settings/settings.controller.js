'use strict';
angular.module('revolovexApp')
    .controller('SettingsController', function ($scope, $http, dialogs, $window) {
        $scope.lastPaymentPlus1 = function (last) {
            if (last == 0)last = new Date();
            last = new Date(last);
            last.setFullYear(last.getFullYear() + 1);
            return last;
        };
        $http.get(document.location.origin + '/api/admin/admin')
            .success(function (admin) {
                $scope.admin = admin;
                $scope.admin.oldplane = admin.plane;
                $scope.admin.lastPaymentPlus1 = $scope.lastPaymentPlus1($scope.admin.lastPayment);

                $scope.minPlane = function () {
                    return Math.ceil($scope.admin.studentCount / $scope.admin.rate);
                };
                /**
                 * @return {boolean}
                 */
                $scope.IsScaleDisable = function () {
                    if ($scope.IsNewYear())
                        return false;
                    else
                        return $scope.admin.oldplane != $scope.admin.plane;
                };


                /**
                 * @return {boolean}
                 */
                $scope.IsNewYear = function () {
                    return new Date().getTime() > $scope.admin.lastPaymentPlus1.getTime();
                };

                /**
                 * @return {number}
                 */
                $scope.count = function () {
                    var number = new Date().getDate() - $scope.admin.lastPaymentPlus1.getDate();
                    return number;
                };

                /**
                 * @return {number}
                 */
                $scope.ScaleCost = function () {
                    if ($scope.admin.plane < $scope.admin.oldplane) {
                        return 0;
                    }
                    else {
                        return $scope.admin.cost * $scope.admin.plane - $scope.admin.cost * $scope.admin.oldplane;
                    }
                };
                /**
                 * @return {number}
                 */
                $scope.YearCost = function () {
                    return $scope.admin.cost * $scope.admin.plane;
                };

                $scope.pay = function (method) {
                    $http.post(document.location.origin + '/api/admin/admin/' + method, {"plane": $scope.admin.plane})
                        .success(function (result) {
                            if (angular.isDefined(result.links))
                                if (result.links.length >= 0)
                                    for (var i = 0; i < result.links.length; i++) {
                                        if (result.links[i].rel == "approval_url") {
                                            $window.location.href = result.links[i].href;
                                        }
                                    }
                                else $window.location.reload();
                            else $window.location.reload();
                        });
                };
            }
        );
    });