'use strict';
describe('Test of controllers - ', function() {
	describe('VmListCtrl', function() {
		var scope = {};

		beforeEach(function() {
			module('hybridDemoApp');
			inject(function($controller) {

				$controller('VmListCtrl', {
					$scope : scope
				});
			});
		});

		it('should return 3 vms', function() {
			expect(scope.vms.length).toBe(3);
		});
	});
});
