'use strict';
describe('Test of EslPod controller - ', function() {
	describe('eslpodctrl', function() {
		var scope = {},http,httpBackend,controller;
		beforeEach(function() {
			module('hybridDemoApp.eslpod');
			inject(function($controller,$httpBackend,$http) {
				
				httpBackend = $httpBackend;
				controller=$controller;
				http=$http;
			});
		});

		it('should return jsonified body', function() {
			  httpBackend.when("GET", "http://www.eslpod.com/website/").respond("<HTML><BODY>Hello</BODY></HTML>");	            
				
			   httpBackend.expectGET('http://www.eslpod.com/website/');
				controller('eslpodctrl', {
					$scope : scope,
					$http : http 
				});
				httpBackend.flush();
			expect(scope.raw).toEqual({HTML:{BODY:"Hello"}});
		});
	});
});