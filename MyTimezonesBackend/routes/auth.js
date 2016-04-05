var passport = require('passport');
var LocalStrategy = require('passport-local').Strategy;
var uuid = require('node-uuid');

exports.init = function(app, User) {
  // Configure Passport local strategy
  passport.use(new LocalStrategy(
    function(username, password, done) {
      User.findOne({
          username: username,
        },
        function(err, user) {
          if (err) {return done(err);}
          if (!user) {return done(null, false);}
          if (!(password === user.password)) {return done(null, false);}
          return done(null, user);
        }
      );
    }
  ));
  // Init Passport
  app.use(passport.initialize());
};

exports.localAuth = passport.authenticate('local', {session: false});

exports.signin = function(req, res) {
  res.json({token: req.user.token})
};

exports.register = function(req, res, next) {
  req.db.User.findOne({
      username: req.body.username
    },
    function(err, user) {
      if (err) {return next(err);}
      if (user) {
        res.sendStatus(409);
        return;
      }
      var newuser = new req.db.User({
        username: req.body.username,
        password: req.body.password,
        token: uuid.v4()
      });
      newuser.save(function (err, fluffy) {
        if (err) {return next(err);}
          res.json({token: newuser.token})
      });
    }
  );
}