module.exports = function() {
  var mongoose = require('mongoose');

  var express = require('express');
  var router = express.Router();
  var errorSchema     = require('../models/error');

  return router;
}
