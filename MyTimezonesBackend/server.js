var express = require('express');
var mongoose = require('mongoose');
var morgan = require('morgan');
var bodyParser = require('body-parser');
var routes = require('./routes');
var models = require('./models');

var app = express();

app.use(morgan('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

var connection = mongoose.createConnection('mongodb://localhost:27017/test');
connection.on('error', console.error.bind(console, 'connection error:'));
connection.once('open', function() {
  console.info('Connected to database')
});

var User = connection.model('User', models.User, 'users');
function db (req, res, next) {
  req.db = {
    User: User,
  };
  return next();
}

routes.auth.init(app, User);

app.get('/', function(req, res) {
  res.send('Hello Toptaler');
});

app.post('/register', db, routes.auth.register);
app.post('/signin', routes.auth.localAuth, db, routes.auth.signin);

app.use(function(req, res){
    res.sendStatus(404);
});

app.listen(8080, '0.0.0.0', function(){
	console.log('Listening on port 8080...');
});
