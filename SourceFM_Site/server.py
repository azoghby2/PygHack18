from flask import Flask, request
import json
import os
app = Flask(__name__)


song_list = []
song_dict = {}

@app.route('/')
def index():
    return "Flask Server"

@app.route('/vote', methods = ['GET'])
def postdata():
    data = request.get_json()
    song_id = request.args.get('id')
    print(song_id)
    if song_id and song_id != "":
        if song_id not in song_dict:
            song_dict[song_id] = 1
        else:
            song_dict[song_id] = song_dict[song_id] + 1

    return json.dumps(song_dict)

if __name__ == "__main__":
    app.run(port=5000, host='10.194.211.136')
