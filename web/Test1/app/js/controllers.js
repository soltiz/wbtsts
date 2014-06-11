/**
 * 
 */
var hybridDemoApp = angular.module('hybridDemoApp',[]);

hybridDemoApp.controller('vmListCtrl',function ($scope,$http) {
	$scope.oldvms= [ 
	             {'name':'apache1', 'network':'Clt1-CW-priv1', 'status':'ACTIVE'},
	             {'name':'alfresco1', 'network':'Clt1-CW-priv1', 'status':'ACTIVE'},
	             {'name':'mysql1', 'network':'Clt1-TS-priv2', 'status':'ACTIVE'}];
	$scope.vms=$http.get('vms.json').success(function(data,status,headers,config) {
		$scope.vms=data;
	});
	$scope.step=1;
	$scope.doNextPhase = function(event) { $scope.step=$scope.step+1; };
	$scope.phase="Ready to start";
});
