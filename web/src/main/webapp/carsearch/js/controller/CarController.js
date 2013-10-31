'use strict';

var CarController = function($scope, $http) {
    $scope.fetchCarsList = function() {
        $http.get('core/cars/list').success(function(carList){
            $scope.cars = carList;
        });
    }

	$scope.search = function(carName) {
		if ($scope.searchForm.$invalid) {
			return;
		}

		$http.get('core/cars/search?name='+carName).success(function(carList){
			$scope.cars = carList;
		});
	}

    $scope.fetchCarsList();
}