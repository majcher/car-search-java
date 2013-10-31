'use strict';

// Declare module which depends on filters, and services
var notifications = angular.module('notificationWidget', []);

notifications.constant('_START_REQUEST_', '_START_REQUEST_');
notifications.constant('_END_REQUEST_', '_END_REQUEST_');

// Declare an http interceptor that will signal the start and end of each request
notifications.config(['$httpProvider', '_START_REQUEST_', '_END_REQUEST_', function ($httpProvider, _START_REQUEST_, _END_REQUEST_) {
	var $http,
		interceptor = ['$q', '$injector', '$rootScope', function ($q, $injector, $rootScope) {
			function success(response) {
				// get $http via $injector because of circular dependency problem
				$http = $http || $injector.get('$http');
				// don't send notification until all requests are complete
				if ($http.pendingRequests.length < 1) {
					$rootScope.$broadcast(_END_REQUEST_);
				}
				return response;
			}

			function error(response) {
				// get $http via $injector because of circular dependency problem
				$http = $http || $injector.get('$http');
				// don't send notification until all requests are complete
				if ($http.pendingRequests.length < 1) {
					$rootScope.$broadcast(_END_REQUEST_);
				}
				return $q.reject(response);
			}

			return function (promise) {
				$rootScope.$broadcast(_START_REQUEST_);
				return promise.then(success, error);
			}
		}];

	$httpProvider.responseInterceptors.push(interceptor);
}]);

notifications.directive("loader", ['_START_REQUEST_', '_END_REQUEST_', function (_START_REQUEST_, _END_REQUEST_) {
	link: return function ($scope, element) {
		$scope.$on(_START_REQUEST_, function () {
			return element.show();
		});
		return $scope.$on(_END_REQUEST_, function () {
			return element.hide();
		});
	};
}]);