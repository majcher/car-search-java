'use strict';

function CarSearchCriteria() {
	this.make = "";
	this.model = "";
	this.color = "";
}

App.controller('CarSearchCriteriaController', function($scope, $http, $routeParams, $location) {
	$scope.searchCriteria = new CarSearchCriteria();
	$scope.searchCriteria.make = $routeParams.make || ''
	$scope.searchCriteria.model = $routeParams.model || ''
	$scope.searchCriteria.color = $routeParams.color || ''

	$scope.search = function(searchCriteria) {
		$location.url('/cars/'+searchCriteria.make+'/'+searchCriteria.model+'/'+searchCriteria.color);
	}
});

App.controller('CarSearchResultsController', function($scope, $http, $routeParams, $location) {
	$scope.search = function() {
		var url = 'core/cars/search?make=' + $routeParams.make +
			'&model=' + $routeParams.model +
			'&color=' + $routeParams.color;

		$http.get(url)
			.success(function(carList){
				$scope.cars = carList.items;
			}
		);
	}

	$scope.backToSearch = function() {
		$location.url('/search/'+$routeParams.make+'/'+$routeParams.model+'/'+$routeParams.color);
	}

	$scope.search();
});