'use strict';

describe('vmListCtrl', function(){
	var scope={};
	beforeEach(module('hybridDemoApp')); 
	
    it('should return 4 VMs', inject(function($controller) {
  
         $controller('VmListCtrl', { $scope: scope });
  
      
      expect(scope.vms.length).toBe(3);

    }));
  });
