from flask import Flask, request, render_template
import json

app = Flask('flask-application')


@app.route('/')
def default():
	return (json.dumps({'name':'KevinBlandy'}),200,{'Content-Type':'application/json;charset=utf-8'})


if __name__ == '__main__':
	app.run(debug=True)
