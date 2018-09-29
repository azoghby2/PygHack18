module.exports = function(db) {
  var express = require('express');
  var path = require('path');
  var favicon = require('serve-favicon');
  var logger = require('morgan');
  var bodyParser = require('body-parser');
  var cookieParser = require('cookie-parser');
  var mongoose = require('mongoose');
  var session = require('express-session');
  var MongoStore = require('connect-mongo')(session);
  var http = require('http');
  var errorHandler = require('errorhandler');
  var backendServices = require('./routes/backendServices');
  // var debug = require('debug');

  var app = express();

  app.set('views', path.join(__dirname, 'views'));
  app.set('view engine', 'jade');

  app.use(logger('dev'));
  app.use(bodyParser.json());
  app.use(bodyParser.urlencoded({ extended: true }));
  app.use(cookieParser());
  app.use(express.static(path.join(__dirname, 'public')));
  app.locals.pretty = true;
  app.use('/public', express.static(__dirname + '/public'));


  app.use('/backendServices', backendServices(db));
  var dbURL = 'mongodb://sourcefmuser:s1urcefmmus1er@ds119343.mlab.com:19343/sourcefm';


  app.use(session({
    secret: 'ccx3m953e5d14fe6h6d09637f720h7vr5c73d1b4',
    proxy: true,
    resave: true,
    saveUninitialized: true,
    store: new MongoStore({ url: dbURL })
    })
  );


  app.get('/', function(req,res) {
    if (req.session.user == null){
      res.sendFile(__dirname + '/public/index.html');
    }else{
      res.render('/home', { title: 'PizzaFM - Welcome!' });
    }
  });

  // catch 404 and forward to error handler
    app.use(function(req, res, next) {
      var err = new Error('Not Found');
      err.status = 404;
      next(err);
    });

    // error handlers

    // development error handler
    // will print stacktrace
    if (app.get('env') === 'development') {
      app.use(function(err, req, res, next) {
        res.status(err.status || 500);
        res.render('error', {
          message: err.message,
          error: err
        });
      });
    }

  // production error handler
  // no stacktraces leaked to user
  app.use(function(err, req, res, next) {
  res.status(err.status || 500);
  res.render('error', {
    message: err.message,
    error: {}
  });
});


  http.createServer(app).listen(app.get('port'), function(){
    console.log('Express server listening on port ' + app.get('port'));
  });

  return app;
}
