var express = require('express');
var http = require('http');
var mongoose = require('mongoose');
var morgan = require('morgan');
var bodyParser = require('body-parser');
var routes = require('./routes');
var models = require('./models');

var app = express();

var port = 8080;
app.set('port', port);
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
  res.send('Hello buddy!');
});

// AUTH
app.post('/register', db, routes.auth.register);
app.post('/signin', localAuth, db, routes.auth.signin);
app.use('/authFail', routes.auth.fail);
app.post('/deleteAccount', tokenAuth, db, routes.auth.deleteAccount);

// TIMEZONES
app.route('/timezones')
  .get(tokenAuth, db, routes.tzs.getTzs)
  .post(tokenAuth, db, routes.tzs.add)
app.route('/timezones/:id')
  .get(tokenAuth, db, routes.tzs.checkTz, routes.tzs.getTz)
  .put(tokenAuth, db, routes.tzs.checkTz, routes.tzs.updateTz)
  .delete(tokenAuth, db, routes.tzs.checkTz, routes.tzs.del)
app.get('/timezones/search/:text', tokenAuth, db, routes.tzs.search);

app.use(function(req, res){
    res.sendStatus(404);
});

http.createServer(app);
if (require.main === module) {
	app.listen(port, '0.0.0.0', function(){
    console.info('Express server listening on port ' + port +  '...');
  });
}
else {
  console.info('Running app as a module')
  exports.app = app;
}
