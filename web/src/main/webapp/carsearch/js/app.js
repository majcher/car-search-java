'use strict';

var CarSearchApp = {};

var App = angular.module('CarSearchApp', ['CarSearchApp.filters', 'CarSearchApp.services', 'CarSearchApp.directives',
	'notificationWidget', 'angularSpinner']);

App.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/cars', {
        templateUrl: 'cars.html',
        controller: CarController
    });

    $routeProvider.otherwise({redirectTo: '/cars'});
}]);
