var hybridDemoApp = angular.module('hybridDemoApp.eslpod',[]);

var ctrl=hybridDemoApp.controller('eslpodctrl',function ($scope,$http) {
	$scope.count=1;
	ctrl.http=$http;
	
	$scope.refresh=function() {
		ctrl.http.get('http://www.eslpod.com/website/').success(function(data,status,headers,config) {
			$scope.raw=data
			var x2js = new X2JS();
			var json = x2js.xml_str2json( data );

			$scope.json=json;
		});
		$scope.count = $scope.count+1;
	};
		
	   
	$scope.refresh(); 
	
});


