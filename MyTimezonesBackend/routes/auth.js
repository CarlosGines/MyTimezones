var passport = require('passport');
var LocalStrategy = require('passport-local').Strategy;
var BearerStrategy = require('passport-http-bearer').Strategy;
var uuid = require('node-uuid');

exports.init = function(app, User) {
  passport.use(new LocalStrategy(
    function(username, password, done) {
      User.findOne(
        {username: username,},
        function(err, user) {
          if (err) {return done(err);}
          if (!user) {return done(null, false);}
          if (!(password === user.password)) {return done(null, false);}
          return done(null, user);
        }
      );
    }
  ));
  passport.use(new BearerStrategy(
    function(token, done) {
      User.findOne(
        {token: token},
        function (err, user) {
          if (err) { return done(err); }
          if (!user) { return done(null, false); }
          return done(null, user);
        }
      );
    }
  ));
  app.use(passport.initialize());
};

exports.localAuth = passport.authenticate('local', {session: false});
exports.tokenAuth = passport.authenticate('bearer', {session: false});

exports.signin = function(req, res) {
  res.json({token: req.user.token})
};

exports.register = function(req, res, next) {
  req.db.User.findOne({
      username: req.body.username
    },
    function(err, user) {
      if (err) {return next(err);}
      if (user) {return res.sendStatus(409);}
      req.db.User.create({
        username: req.body.username,
        password: req.body.password,
        token: uuid.v4()
      }, function(err, doc) {
        if (err) {
          console.error(err);
          return next(err);
        }
        res.json({token: doc.token})
      });
    }
  );
}
