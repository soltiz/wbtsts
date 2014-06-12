from flask import Flask
from flask import jsonify
from flask.ext.restful import Api,Resource,fields, marshal, marshal_with
from flask.ext.restful import reqparse


app = Flask(__name__)

@app.route('/')       
def index():
    return "Hello, World!"

scenario_fields = {
    'current_state': fields.String,
    'current_activity_description': fields.String,
    'next_state': fields.String
}

class UserAPI (Resource):
    current_state='WaitingDemoStart'
    next_state='/comingNext'
    current_activity_description='not much'
    
    def currentRepresentation(self):
        self.next_state=api.url_for(self)+"?requestedState="+"after_"+self.current_state
        return ( self)
    
    def __init__(self):
        self.reqparse = reqparse.RequestParser()
        self.reqparse.add_argument('requestedState', type = str)
        super(UserAPI, self).__init__()
    
    @marshal_with(scenario_fields)    
    def get (self):
        self.reqparse.parse_args()
        return (self.currentRepresentation() )
    
    @marshal_with(scenario_fields)    
    def put (self):
        args = self.reqparse.parse_args()
        self.current_state=args.requestedState
        return ( self.currentRepresentation()  )


if __name__ == '__main__':
    api=Api(app)
    api.add_resource(UserAPI, '/scenario', endpoint = 'scenario') 
    app.run(debug = True)

