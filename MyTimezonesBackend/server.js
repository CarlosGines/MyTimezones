// Create a new Express application.
var app = require('express')();

// Use application-level middleware for common functionality
app.use(require('morgan')('dev'));
var bodyParser = require('body-parser');
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

// Init Passport
var mypass = require('./mypass');
mypass.init(app);

// Define routes.
app.get('/', function(req, res) {
	res.send('Hello Toptaler');
});

app.post('/signin',
  mypass.localAuth,
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
