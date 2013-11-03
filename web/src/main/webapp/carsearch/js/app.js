'use strict';

var CarSearchApp = {};

var App = angular.module('CarSearchApp', ['CarSearchApp.filters', 'CarSearchApp.services', 'CarSearchApp.directives',
	'notificationWidget', 'angularSpinner']);

App.config(['$routeProvider', function ($routeProvider) {

    $routeProvider.when('/search', {
        templateUrl: 'search.html',
        controller: 'CarSearchCriteriaController'
    });

	$routeProvider.when('/cars/:make/:model/:color', {
		templateUrl: 'cars.html',
		controller: 'CarSearchResultsController'
	});

    $routeProvider.otherwise({redirectTo: '/search'});
}]);
