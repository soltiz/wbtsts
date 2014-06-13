from flask import Flask
from flask import jsonify, abort, make_response
from flask.ext.restful import Api,Resource,fields, marshal, marshal_with
from flask.ext.restful import reqparse
from HybStateMachine import HybStateMachine
from HybVmsAPI import HybVmsAPI

app = Flask(__name__)
state_machine=HybStateMachine()

@app.route('/')       
def index():
    return "Hello, World!"

scenario_fields = {
    'current_state': fields.String,
    'current_activity_description': fields.String,
    'counter':fields.Integer,
    'next_state': fields.String
}

class UserAPI (Resource):
    current_state='WaitingDemoStart'
    next_state='/comingNext'
    counter=state_machine.get_silo()
    current_activity_description='not much'
    
    def currentRepresentation(self):
        self.next_state=api.url_for(self)+"?requestedState="+"after_"+self.current_state
        self.current_activity_description="COUNTER="+str(self.counter)
        return ( marshal(self,scenario_fields))
    
    def __init__(self):
        self.reqparse = reqparse.RequestParser()
        self.reqparse.add_argument('requested_state', type = str)
        super(UserAPI, self).__init__()
    
    @marshal_with(scenario_fields)    
    def get (self):
        UserAPI.counter=state_machine.get_silo()
        self.reqparse.parse_args()
        return (self.currentRepresentation() )
    
    def put (self):
        args = self.reqparse.parse_args()
        print (args.requested_state)
        if args.requested_state==None:
            return make_response(jsonify( { 'error': "Request argument 'requested_state' is mandatory for PUT method" } ), 400)
            pass
#            return(make_response(jsonify({}))
        UserAPI.current_state=args.requested_state
        return ( self.currentRepresentation()  )



if __name__ == '__main__':
    api=Api(app)
    api.add_resource(UserAPI, '/scenario', endpoint = 'scenario') 
    api.add_resource(HybVmsAPI, '/vms', endpoint = 'vms') 
    
    app.run(debug = True)

