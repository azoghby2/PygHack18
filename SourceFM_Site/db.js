var mongoose = require('mongoose');

var url = 'mongodb://sourcefmuser:s1urcefmmus1er@ds119343.mlab.com:19343/sourcefm';
mongoose.connect(url);

module.exports = mongoose.connection;
