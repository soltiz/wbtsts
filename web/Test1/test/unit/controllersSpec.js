'use strict';
describe('Test of controllers - ', function() {
	describe('VmListCtrl', function() {
		var scope = {};

		beforeEach(function() {
			module('hybridDemoApp');
			inject(function($controller) {

				$controller('vmListCtrl', {
					$scope : scope
				});
			});
		});

		it('should return 3 vms', function() {
			expect(scope.vms.length).toBe(3);
		});
		
		it('should return json', function() {
			expect(scope.vms).toEqual([
			 	             {'name':'apache1', 'network':'Clt1-CW-priv1', 'status':'ACTIVE'},
				             {'name':'alfresco1', 'network':'Clt1-CW-priv1', 'status':'ACTIVE'},
				             {'name':'mysql1', 'network':'Clt1-TS-priv2', 'status':'ACTIVE'}]);
		});
			
	});

});
