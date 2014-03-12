/**
 * 
 */
var hybridDemoApp = angular.module('hybridDemoApp',[]);

hybridDemoApp.controller('VmListCtrl',function ($scope) {
	$scope.vms= [
	             {'name':'apache1', 'network':'Clt1-CW-priv1', 'status':'ACTIVE'},
	             {'name':'alfresco1', 'network':'Clt1-CW-priv1', 'status':'ACTIVE'},
	             {'name':'mysql1', 'network':'Clt1-TS-priv2', 'status':'ACTIVE'}]
	
	$scope.step=1;
	$scope.phase="Ready to start";
});
