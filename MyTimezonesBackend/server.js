var passport = require('passport');
var LocalStrategy = require('passport-local').Strategy;

// Configure Passport local strategy
passport.use(new LocalStrategy(
  function(username, password, done) {
    // Mock auth
    if(username == "foo") {
      var user = {name: "Foo"};
      if (password != "bar") {
        return done(null, false, { message: 'Incorrect password.' });
      }
      return done(null, user);
    }
    return done(null, false, { message: 'Incorrect username.' });
  }
));

var passportLocalAuth = passport.authenticate('local', {session: false});

// Create a new Express application.
var app = require('express')();

// Use application-level middleware for common functionality
app.use(require('morgan')('dev'));
var bodyParser = require('body-parser');
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

// Init Passport
app.use(passport.initialize());

// Define routes.
app.get('/', function(req, res) {
	res.send('Hello Toptaler');
});

app.post('/signin',
  passportLocalAuth,
  function(req, res) {
    res.send('Signed in as ' +  req.user.name);
  }
);

app.use(function(req, res){
    res.sendStatus(404);
});

// Start listening
app.listen(8080, '0.0.0.0', function(){
	console.log('Listening on port 8080...');
});
