'use strict';
describe('Test of EslPod controller - ', function() {
	describe('eslpodctrl', function() {
		var scope = {},http,httpBackend,controller,ctrl;
		beforeEach(function() {
			module('hybridDemoApp.eslpod');
			inject(function($controller,$httpBackend,$http) {
				
				httpBackend = $httpBackend;
				controller=$controller;
				http=$http;
			});
			 localStorage.clear();
		});
		
		it('should learn html', function() {
			jasmine.getFixtures().fixturesPath='base/test/unit/mock';
			var fixture = readFixtures('out.html');
			var x2js = new X2JS();
			var json = x2js.xml_str2json( fixture );
			expect(json).toEqual('nothing');

		})
		

		it('should return jsonified body', function() {
			  httpBackend.when("GET", "http://www.eslpod.com/website/").respond("<HTML><BODY>Hello</BODY></HTML>");	            
				
			   httpBackend.expectGET('http://www.eslpod.com/website/');
				ctrl=controller('eslpodctrl', {
					$scope : scope,
					$http : http 
				});
				expect(scope.count).toEqual(2);
				httpBackend.flush();
			expect(scope.json).toEqual({HTML:{BODY:"Hello"}});
		});
		
		it('should be able to decode the test web page',function() {
			jasmine.getFixtures().fixturesPath='base/test/unit/mock';
			var fixture = readFixtures('eslpodlist.html');
		     //jasmine.getJSONFixtures().fixturesPath='base/test/mock';
				
			  httpBackend.when("GET","http://www.eslpod.com/website/").respond(fixture);
			   httpBackend.expectGET('http://www.eslpod.com/website/');
				ctrl=controller('eslpodctrl', {
					$scope : scope,
					$http : http 
				});
				expect(scope.count).toEqual(2);
				
			  scope.refresh();
				expect(scope.count).toEqual(3);
				httpBackend.flush();
		//	    var bar= $('/HTML/HEAD/TITLE', scope.raw); 
		//	  expect(bar).toEqual('Nothing');
				});
		
		it('should return the correct number of podcasts', function() {
			expect(42).toEqual(42);
		});
		
		
	});
	
	describe('sampletests', function() {
		it('array first element is 42', function() {
			var table=[42,45,57];
			expect(table[0]).toEqual(42);
		});
		it('map attribue toto value is demon', function() {
			var map={'mark':'angel','toto':'demon','mike':'god'};
			expect(map['toto']).toEqual('demon');
		});
	});
		
});