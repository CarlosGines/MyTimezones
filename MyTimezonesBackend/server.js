var http = require('http');

http.createServer(function(request, response) {
	response.writeHead(200);
	response.write("Hello Toptaler!");
	response.end();
}).listen(8080, '0.0.0.0', function(){
	console.log('Listening on port 8080...');
});
