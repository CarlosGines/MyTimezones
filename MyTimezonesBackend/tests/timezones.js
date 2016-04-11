var app = require('../server').app;
var assert = require('chai').assert;
var request = require('superagent');
var async = require('async');

var user1 = request.agent();
var port = app.get('port');
var url = 'http://localhost:' + port;

app.listen(port, '0.0.0.0', function(){
	console.info('Express server listening on port ' + port +  '...');
});

// AUTH & IDs
var username = "testuser2";
var password = "testpass2";
var user_id = "";
var token = "";
var token2 = "";
var tz_id = "";

function getAuth(tkn) {
  if (!tkn) {
    tkn = token;
  }
  return "Bearer " + tkn;
}

// SAMPLE RECORDS
var tz1 = {
  name : "XXX",
  city : "Xanadu",
  timeDiff : -5
};

var tz2 = {
  name : "ZZZ",
  city : "Zanadu",
  timeDiff : 9
};

var tz3 = {
  name : "ZX",
  city : "ZanaduX",
  timeDiff : 3
};

var tz4 = {
  name : "Zz xX",
  city : "ZzanaduxX",
  timeDiff : 0
};

describe('Timezones tests suite', function() {

  it('shoud access /', function(done) {
    request
    .get(url)
    .end(function(err, res) {
      assert.equal(res.status, 200);
      done();
    });
  });

  it('shoud register new user', function(done) {
    request
    .post(url + '/register').send({
      username: username,
      password: password
    }).end(function(err, res) {
      assert.equal(res.status, 200);
      token = res.body.token;
      user_id = res.body._id;
      done();
    });
  });

  it('shoud return empty tz list', function(done) {
    checkTzListLength(0, done);
  });

  it('shoud add tz record', function(done) {
    request.post(url + '/timezones')
    .set('Authorization', getAuth())
    .send(tz1).end(function(err, res) {
      assert.equal(res.status, 201);
      tz_id = res.body._id;
      assert.notEqual(tz_id, undefined);
      done();
    });
  });

  it('shoud get tz record', function(done) {
    request.get(url + '/timezones/' + tz_id)
    .set('Authorization', getAuth())
    .end(function(err, res) {
      assert.equal(res.status, 200);
      assert.equal(res.body._id, tz_id);
      assert.equal(res.body.name, tz1.name);
      assert.equal(res.body.city, tz1.city);
      assert.equal(res.body.timeDiff, tz1.timeDiff);
      assert.equal(res.body.author.username, username);
      assert.equal(res.body.author._id, user_id);
      done();
    });
  });

  it('shoud get tz list', function(done) {
    checkTzListLength(1, done);
  });

  it('shoud update tz record', function(done) {
    async.series(
      [
        function(cb) {
          request.put(url + '/timezones/' + tz_id)
          .set('Authorization', getAuth())
          .send(tz2)
          .end(function(err, res) {
            assert.equal(res.status, 200);
            cb(err, res);
          }) 
        },
        function(cb) {
          request.get(url + '/timezones/' + tz_id)
          .set('Authorization', getAuth())
          .end(function(err, res) {
            assert.equal(res.status, 200);
            tz_id = res.body._id;
            assert.notEqual(tz_id, undefined);
            assert.equal(res.body.name, tz2.name);
            assert.equal(res.body.city, tz2.city);
            assert.equal(res.body.timeDiff, tz2.timeDiff);
            cb(err, res);
          });
        }
      ],
      done);
  });

  it('shoud delete tz record', function(done) {
    async.series(
      [
        function(cb) {
          request.delete(url + '/timezones/' + tz_id)
          .set('Authorization', getAuth())
          .end(function(err, res){
            assert.equal(res.status, 200);
            cb(err, res);
          }) 
        },
        function(cb) { checkTzListLength(0, cb); },
      ],
      done);
  });

  it('shoud search tz records', function(done) {
    async.series(
      [
        function(cb) { checkTzListLength(0, cb); },
        function(cb) { 
          createTzRecord(
            tz1,
            function(err, res) {
              tz_id = res.body._id;
              cb(err, res);
            }
          ); 
        },
        function(cb) { createTzRecord(tz2, cb); },
        function(cb) { createTzRecord(tz3, cb); },
        function(cb) { createTzRecord(tz4, cb); },
        function(cb) { checkTzListLength(4, cb); },
        function(cb) { checkSearchTzRecordsLength("XX", 1, cb) },
        function(cb) { checkSearchTzRecordsLength("ZZ", 1, cb) },
        function(cb) { checkSearchTzRecordsLength("X", 3, cb) },
        function(cb) { checkSearchTzRecordsLength("Z", 3, cb) },
        function(cb) { checkSearchTzRecordsLength("z x", 1, cb) },
      ],
      done);
  });

  it('shoud admin signin', function(done) {
    request.post(url + '/signin')
    .send({
      username: 'admin',
      password: 'admin'
    }).end(function(err, res) {
      assert.equal(res.status, 200);
      token2 = token;
      token = res.body.token;
      done();
    });
  });

  it('shoud admin read others tz list', function(done) {
    request.get(url + '/timezones')
    .set('Authorization', getAuth())
    .end(function(err, res) {
      assert.equal(res.status, 200);
      assert.isAtLeast(res.body.tzs.length, 4);
      done();
    });
  });

  it('shoud admin update others tz record', function(done) {
    async.series(
      [
        function(cb) {
          request.put(url + '/timezones/' + tz_id)
          .set('Authorization', getAuth())
          .send(tz4)
          .end(function(err, res) {
            assert.equal(res.status, 200);
            cb(err, res);
          }) 
        },
        function(cb) {
          request.get(url + '/timezones/' + tz_id)
          .set('Authorization', getAuth())
          .end(function(err, res) {
            assert.equal(res.status, 200);
            tz_id = res.body._id;
            assert.notEqual(tz_id, undefined);
            assert.equal(res.body.name, tz4.name);
            assert.equal(res.body.city, tz4.city);
            assert.equal(res.body.timeDiff, tz4.timeDiff);
            cb(err, res);
          });
        }
      ],
      done);
  });

  it('shoud admin delete others tz record', function(done) {
    async.series(
      [
        function(cb) {
          request.delete(url + '/timezones/' + tz_id)
          .set('Authorization', getAuth())
          .end(function(err, res){
            assert.equal(res.status, 200);
            cb(err, res);
          }) 
        },
        function(cb) {
          request.get(url + '/timezones/' + tz_id)
          .set('Authorization', getAuth())
          .end(function(err, res){
            assert.equal(res.status, 404);
            cb(null);
          }) 
        }
      ],
      done);
  });

  it('shoud delete user account', function(done) {
    request.post(url + '/deleteAccount')
    .set('Authorization', getAuth(token2))
    .end(function(err, res) {
      assert.equal(res.status, 200);
      done();
    });
  });

  function checkTzListLength(length, cb) {
    request.get(url + '/timezones')
    .set('Authorization', getAuth())
    .end(function(err, res) {
      assert.equal(res.status, 200);
      assert.equal(res.body.tzs.length, length);
      cb(err, res);
    });
  }

  function createTzRecord(tz, cb) {
    request.post(url + '/timezones')
    .set('Authorization', getAuth())
    .send(tz)
    .end(function(err, res) {
      assert.equal(res.status, 201);
      cb(err, res);
    });
  }

  function checkSearchTzRecordsLength(text, length, cb) {
    request.get(url + '/timezones/search/' + text)
    .set('Authorization', getAuth())
    .end(function(err, res) {
      assert.equal(res.status, 200);
      assert.equal(res.body.tzs.length, length);
      cb(err, res);
    });
  }
});
