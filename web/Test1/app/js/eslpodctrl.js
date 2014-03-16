var hybridDemoApp = angular.module('hybridDemoApp.eslpod',[]);

hybridDemoApp.controller('eslpodctrl',function ($scope,$http) {
	
	$http.get('http://www.eslpod.com/website/').success(function(data,status,headers,config) {
		var x2js = new X2JS();
        var json = x2js.xml_str2json( data );
		$scope.raw=json;
	});
});