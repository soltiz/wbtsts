// declare a module
var FirstModule = angular.module('FirstModule', []);
 
// configure the module.
// in this example we will create a greeting filter
FirstModule.filter('greet', function() {
 return function(name) {
    return 'Hello, ' + name + '!';
  };
});