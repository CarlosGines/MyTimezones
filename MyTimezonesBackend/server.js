var express = require('express');
var mongoose = require('mongoose');
var morgan = require('morgan');
var bodyParser = require('body-parser');
var routes = require('./routes');
var models = require('./models');

var app = express();

app.use(morgan('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));

var connection = mongoose.createConnection('mongodb://localhost:27017/test');
connection.on('error', console.error.bind(console, 'connection error:'));
connection.once('open', function() {
  console.info('Connected to database')
});

var User = connection.model('User', models.User, 'users');
function db (req, res, next) {
  req.db = {
    User: User,
    Timezone: connection.model('Timezone', models.Timezone, 'tzs')
  };
  return next();
}

routes.auth.init(app, User);
var localAuth = routes.auth.localAuth;
var tokenAuth = routes.auth.tokenAuth;

app.get('/', function(req, res) {
  res.send('Hello Toptaler');
});

// AUTH
app.post('/register', db, routes.auth.register);
app.post('/signin', localAuth, db, routes.auth.signin);
app.use('/authFail', routes.auth.fail);

// TIMEZONES
app.get('/timezones', tokenAuth, db, routes.tzs.getTzs);
app.post('/timezones', tokenAuth, db, routes.tzs.add);
app.get('/timezones/:id', tokenAuth, db, routes.tzs.checkTz, routes.tzs.getTz);
app.put('/timezones/:id', tokenAuth, db, routes.tzs.checkTz, routes.tzs.updateTz);
app.delete('/timezones/:id', tokenAuth, db, routes.tzs.checkTz, routes.tzs.del);
app.get('/timezones/search/:text', tokenAuth, db, routes.tzs.search);

app.use(function(req, res){
    res.sendStatus(404);
});

app.listen(8080, '0.0.0.0', function(){
	console.log('Listening on port 8080...');
});
