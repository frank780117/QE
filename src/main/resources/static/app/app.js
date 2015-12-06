var app = angular.module('myApp', [ 'ngRoute' ], function($routeProvider) {
	$routeProvider.when('/', {
		title : '列表',
		templateUrl : 'partials/list.html',
		controller : 'listCtrl'
	}).when('/execute/:id', {
		title : 'execute',
		templateUrl : 'partials/execute.html',
		controller : 'executeCtrl',
		resolve : {
			sqlConfig : function(services, $route) {
				var id = $route.current.params.id;
				return services.getsqlConfig(id);
			}
		}
	}).otherwise({
		redirectTo : '/'
	});
});
app.factory("services", [ '$http', function($http) {

	var obj = {};
	obj.getsqlConfigs = function() {
		return $http.get('/sqlConfig');
	}
	obj.getsqlConfig = function(id) {
		return $http.get('/sqlConfig/' + id);
	}

	obj.executeSql = function(sql, params) {
		return $http({
			url : '/executeSql',
			method : "GET",
			params : {
				sql : sql,
				params : params
			}
		});
	}

	return obj;
} ]);

app.controller('listCtrl', function($scope, services) {
	services.getsqlConfigs().then(function(data) {
		console.log(data.data);
		$scope.sqlConfigs = data.data;
	});
});

app.controller('executeCtrl', function($scope, services, sqlConfig) {
	var sqlConfig = sqlConfig.data;

	$scope.showSql = false;
	$scope.config = {};
	$scope.config.params = new Array();
	$scope.config.sql = sqlConfig.sqlValue;
	$scope.config.comment = sqlConfig.comment;

	if (sqlConfig.keyValue) {
		sqlConfig.keyValue.split(',').forEach(function(e) {
			var param = {};
			param.key = e.split(':')[0];
			param.value = e.split(':')[1];
			$scope.config.params.push(param);
		});
	}

	$scope.executeSql = function() {
		var paramvalues = new Array();
		$scope.config.params.forEach(function(e) {
			paramvalues.push(e.value);
		});
		mask.open("Loading on server.");
		services.executeSql($scope.config.sql, paramvalues).then(
				function(response) {
					mask.close();
					var result = response.data;
					$scope.sqlResult = {};
					if (result && Object.keys(result).length !== 0) {
						$scope.sqlResult = {};
						$scope.sqlResult.header = Object.keys(result[0]);
						$scope.sqlResult.body = result;
					} else {
						$scope.sqlResult = null;
					}
				}, function errorCallback(response) {
					mask.close();
					swal({
						title: "error!",
						animation: "slide-from-top",
						html: "<p> type: " + response.data.exception + "</p>" + 
						      "<p> message: " + response.data.message + "</p>",
						width: 800
					});
				});
	}
});

app.run([ '$location', '$rootScope', function($location, $rootScope) {
	$rootScope.$on('$stateChangeSuccess', function(event, current, previous) {
		$rootScope.title = current.$$route.title;
	});
} ]);