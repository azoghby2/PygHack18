module.exports = function(db) {
  var express = require('express');
  var path = require('path');
  var favicon = require('serve-favicon');
  var cookieParser = require('cookie-parser');
  var mongoose = require('mongoose');
  var session = require('express-session');
  var http = require('http');
  var errorHandler = require('errorhandler');
  var backendServices = require('./routes/backendServices');
  // var debug = require('debug');

  var app = express();

  app.set('views', path.join(__dirname, 'views'));
  app.set('view engine', 'jade');

  app.use('/backendServices', backendServices(db));
  app.get('/', function(req,res) {
    if (req.session.user == null){
      res.sendFile(__dirname + '/public/index.html');
    }else{
      res.render('/home', { title: 'PizzaFM - Welcome!' });
    }
  })


  return app;
}
