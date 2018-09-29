var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var errorSchema = new Schema({
    error: String,
    date: String,
    name: String,
    email: String
})

module.exports = mongoose.model('Error', errorSchema);
