var passport = require('passport');
var LocalStrategy = require('passport-local').Strategy;
var BearerStrategy = require('passport-http-bearer').Strategy;
var uuid = require('node-uuid');
var passwordHash = require('password-hash');

exports.init = function(app, User) {
  passport.use(new LocalStrategy(
    function(username, password, done) {
      User.findOne(
        {username: username},
        function(err, user) {
          if (err) {return done(err);}
          if (!user) {return done(null, false);}
          if (!passwordHash.verify(password, user.password)) {
            return done(null, false);
          }
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

exports.localAuth = passport.authenticate(
  'local', 
  {
    session: false,
    failureRedirect: '/authFail'
  }
);
exports.tokenAuth = passport.authenticate(
  'bearer',
  {
    session: false,
    failureRedirect: '/authFail'
  }
);

exports.fail = function(req, res) {
  res.sendStatus(403);
};

exports.signin = function(req, res) {
  res.json(
    {
      _id: req.user._id,
      username: req.user.username,
      admin: req.user.admin,
      token: req.user.token
    }
  )
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
        password: passwordHash.generate(req.body.password),
        token: uuid.v4()
      }, function(err, user) {
        if (err) {
          console.error(err);
          return next(err);
        }
        res.json(
          {
            _id: user._id,
            username: user.username,
            admin: user.admin,
            token: user.token
          }
        );
      });
    }
  );
}

exports.deleteAccount = function(req, res) {
  req.db.Timezone.find({'author._id': req.user._id}).remove().exec();
  req.user.remove();
  res.json(
    {
      _id: req.user._id,
      username: req.user.username,
      admin: req.user.admin,
    }
  );
};
