var express = require('express');
var app = express();

app.get('/', function(req, res) {
	res.send('Hello Toptaler');
});

app.listen(8080, '0.0.0.0', function(){
	console.log('Listening on port 8080...');
});
