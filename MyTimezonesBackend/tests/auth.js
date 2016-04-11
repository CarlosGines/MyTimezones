var app = require('../server').app;
var assert = require('chai').assert;
var request = require('superagent');

var user1 = request.agent();
var port = app.get('port');
var url = 'http://localhost:' + port;

app.listen(port, '0.0.0.0', function(){
	console.info('Express server listening on port ' + port +  '...');
});

// AUTH & IDs
var username = "testuser1";
var password = "testpass1";
var user_id = "";
var token = "";

function getAuth() {
  return "Bearer " + token;
}

describe('Auth test suite', function() {

  it('shoud access /', function(done) {
    request
    .get(url)
    .end(function(err, res) {
      assert.equal(res.status, 200);
      done();
    });
  });

  it('shoud register', function(done) {
    request
    .post(url + '/register').send({
      username: username,
      password: password
    }).end(function(err, res) {
      assert.equal(res.status, 200);
      token = res.body.token;
      assert.notEqual(token, undefined);
      assert.equal(res.body.username, username);
      assert.equal(res.body.admin, false);
      user_id = res.body._id;
      assert.notEqual(user_id, undefined);
      done();
    });
  });

  it('shoud return conflict error', function(done) {
    request.post(url + '/register').send({
      username: username,
      password: password
    }).end(function(err, res) {
      assert.equal(res.status, 409);
      done();
    });
  });

  it('shoud fail signin', function(done) {
    request.post(url + '/signin').send({
      username: "foo",
      password: "baz"
    }).end(function(err, res) {
      assert.equal(res.status, 403);
      done();
    });
  });

  it('shoud signin', function(done) {
    request.post(url + '/signin').send({
      username: username,
      password: password
    }).end(function(err, res) {
      assert.equal(res.status, 200);
      token = res.body.token;
      assert.notEqual(token, undefined);
      assert.equal(res.body.username, username);
      assert.equal(res.body.admin, false);
      user_id = res.body._id;
      assert.notEqual(user_id, undefined);
      done();
    });
  });

  it('shoud delete user account', function(done) {
    request.post(url + '/deleteAccount')
    .set('Authorization', getAuth())
    .end(function(err, res) {
      assert.equal(res.status, 200);
      done();
    });
  });

});
