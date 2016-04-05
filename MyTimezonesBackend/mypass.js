var passport = require('passport');
var LocalStrategy = require('passport-local').Strategy;

// Passport cinfiguration and initialization
exports.init = function(app) {
  // Configure Passport local strategy
  passport.use(new LocalStrategy(
    function(username, password, done) {
      // Mock auth
      if(username == "fooo") {
        var user = {name: "Foo"};
        if (password != "barr") {
          return done(null, false, { message: 'Incorrect password.' });
        }
        return done(null, user);
      }
      return done(null, false, { message: 'Incorrect username.' });
    }
  ));
  // Init Passport
  app.use(passport.initialize());
};

// Passport local auth middleware
exports.localAuth = passport.authenticate('local', {session: false});


