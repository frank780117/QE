var app = angular.module('myApp', [ 'ngAnimate', 'ui.bootstrap', 'ngRoute' ], function($routeProvider) {
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

	obj.executeSql = function(sql, params, pageNo) {
		return $http({
			url : '/executeSqlPageable',
			method : "GET",
			params : {
				sql : sql,
				params : params,
				pageNo : pageNo || 1
			}
		});
	}

	return obj;
} ]);

app.filter('startFrom', function() {
    return function(input, start) {
        if(input) {
            start = +start; //parse to int
            return input.slice(start);
        }
        return [];
    }
});

app.controller('listCtrl', function($scope, services, filterFilter) {
	services.getsqlConfigs().then(function(result) {
		$scope.sqlConfigs = result.data;
		
		$scope.$watch('searchText', function(term) {
	        $scope.filtered = filterFilter($scope.sqlConfigs, term);
	        $scope.totalItems = $scope.filtered.length;
	        $scope.currentPage = 1;
			$scope.maxSize = 10;
			console.log("length: " + $scope.totalItems);
	    });
	});
	
	
	$scope.entryLimit = 10;
	
});

app.controller('executeCtrl', function($scope, $uibModal, services, sqlConfig) {
	var sqlConfig = sqlConfig.data;

	$scope.showSql = false;
	$scope.config = {};
	$scope.config.params = new Array();
	$scope.config.sql = sqlConfig.sqlValue;
	$scope.config.comment = sqlConfig.comment;

	$scope.animationsEnabled = true;

	  $scope.openSql = function (size) {

	    var modalInstance = $uibModal.open({
	      animation: $scope.animationsEnabled,
	      templateUrl: 'sqlModal.html',
	      controller: 'sqlModalCtrl',
	      size: size,
	      resolve: {
	        sql: function () {
	          return $scope.config.sql;
	        }
	      }
	    });
	  }
	
	if (sqlConfig.keyValue) {
		sqlConfig.keyValue.split(',').forEach(function(e) {
			var param = {};
			param.key = e.split(':')[0];
			param.value = e.split(':')[1];
			$scope.config.params.push(param);
		});
	}
	$scope.pageChanged = function() {
		$scope.executeSql($scope.currentPage);
	}
	
	$scope.executeSql = function(pageNo) {
		var paramvalues = new Array();
		$scope.config.params.forEach(function(e) {
			paramvalues.push(e.value);
		});
		mask.open("Loading on server.");
		services.executeSql($scope.config.sql, paramvalues, pageNo).then(
				function(response) {
					mask.close();
					var result = response.data;
					$scope.sqlResult = {};
					if (result && Object.keys(result).length !== 0) {
						$scope.sqlResult = {};
						$scope.sqlResult.header = Object.keys(result.pageItems[0]);
						$scope.sqlResult.body = result.pageItems;
						$scope.pagesAvailable = result.pagesAvailable;
						$scope.currentPage=pageNo;
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

app.controller('sqlModalCtrl', function ($scope, $uibModalInstance, sql) {

	$scope.sql = sql;
	$scope.cancel = function () {
		$uibModalInstance.dismiss('cancel');
	};
});

app.run([ '$location', '$rootScope', function($location, $rootScope) {
	$rootScope.$on('$stateChangeSuccess', function(event, current, previous) {
		$rootScope.title = current.$$route.title;
	});
} ]);