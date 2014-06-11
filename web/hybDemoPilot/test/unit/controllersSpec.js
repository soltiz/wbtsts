'use strict';
describe('Test of controllers - ', function() {
	describe('VmListCtrl', function() {
		var scope = {},http,httpBackend,controller;
		beforeEach(function() {
			module('hybridDemoApp');
			inject(function($controller,$httpBackend,$http) {
				
				httpBackend = $httpBackend;
				controller=$controller;
				http=$http;
          
				

			});
		});

		it('should return 3 vms', function() {
			  httpBackend.when("GET", "vms.json").respond([{},{},{}]);	            
				
			   httpBackend.expectGET('vms.json');
				controller('vmListCtrl', {
					$scope : scope,
					$http : http 
				});
				httpBackend.flush();
			expect(scope.vms.length).toBe(3);
		});
		
		it('should return json', function() {
			  httpBackend.when("GET", "vms.json").respond([{'name':'alpha' }]);	            
			  httpBackend.expectGET('vms.json');
				
			 controller('vmListCtrl', {
				$scope : scope,
				$http : http 
			});
			 httpBackend.flush();
			expect(scope.vms).toEqual([{'name':'alpha'}]);
		});
			
	});

});
