'''
Created on 13 juin 2014

@author: bnkeez
'''
from flask import Flask
from flask import jsonify, abort, make_response
from flask.ext.restful import Api,Resource,fields, marshal, marshal_with
from flask.ext.restful import reqparse

vm_fields = {
    'name': fields.String,
    'network': fields.String,
    'status' : fields.String
}

class HybVmsAPI(Resource):
    '''
    classdocs
    '''
    
    def __init__(self):

        super(HybVmsAPI, self).__init__()

    
 
    @marshal_with(vm_fields)
    def get (self):
        vms= [
             {
                "name":"alpha",
                "network":"Clt1-CW-priv1",
                "status":"ACTIVE"
            },
            {
                "name":"beta",
                "network":"Clt1-CW-priv1",
                "status":"BUILD"
            },
            {
                "name":"zeta",
                "network":"Clt1-CW-priv1",
                "status":"BUILD"
            },
            {
             "name":"gamma",
             "network":"Clt1-CW-priv1",
             "status":"ACTIVE"}            ]
        return (vms)
 
 #       return make_response(jsonify( { 'error': "Request argument 'requested_state' is mandatory for PUT method" } ), 400)
            
       

