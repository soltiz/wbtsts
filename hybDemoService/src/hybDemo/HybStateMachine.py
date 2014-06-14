'''
Created on 13 juin 2014

@author: bnkeez
'''
from HybStateMachineStatus   import HybStateMachineStatus



class HybStateMachine(object):
    '''
    classdocs
    '''

    def get_silo(self, ):
        HybStateMachineStatus.silo=HybStateMachineStatus.silo+1
        return HybStateMachineStatus.silo

    def __init__(self):
        '''
        Constructor
        '''
        