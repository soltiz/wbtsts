var demoServices = angular.module('demoServices', ['ngResource']);

demoServices.factory('Vm', ['$resource',
  function($resource){
    return $resource('http://localhost/rest/vms', {}, {
      query: {method:'GET', params:{}, isArray:true}
    });
  }]);
