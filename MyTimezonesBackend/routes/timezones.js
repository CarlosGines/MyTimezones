// var LIMIT = 10;
// var SKIP = 0;

exports.add = function(req, res, next) {
  if (!req.body) return next(new Error('No data'));
  req.db.Timezone.create(
    {
      name: req.body.name,
      city: req.body.city,
      timeDiff: req.body.timeDiff,
      author: {
        id: req.user._id,
        username: req.user.username
      }
    }, function(err, tz) {
      if (err) {
        console.error(err);
        next(err);
      } else {
        res.status(201).json(tz);
      }
    }
  );
};

exports.getTzs = function(req, res, next) {
  // var limit = req.query.limit || LIMIT;
  // var skip = req.query.skip || SKIP;
  req.db.Timezone.find({}, {__v:0}, {
    // limit: limit,
    // skip: skip,
    // sort: {
    //   '_id': -1
    // }
  }, function(err, tzs) {
    if (!tzs) return res.sendStatus(404);
    res.json(tzs);
  });
};


exports.getTz = function(req, res, next) {
  req.db.Timezone.findById(
    req.params.id,
    {__v:0},
    function(err, tz) {
      if (err) return next(err);
      if (!tz) return res.sendStatus(404);
      if (req.user.admin || req.user._id.equals(tz.author.id)) {
        res.json(tz);
      } else {
        next('User is not authorized to get timezone.');
      }
    }
  );
};

exports.updateTz = function(req, res, next) {
  req.db.Timezone.findById(req.params.id, function(err, tz) {
    if (err) return next(err);
    if (!tz) return res.sendStatus(404);
    if (req.user.admin || req.user._id.equals(tz.author.id)) {
      tz.name = req.body.name;
      tz.city = req.body.city;
      tz.timeDiff = req.body.timeDiff;
      tz.save(function(err, tz) {
        if (err) {
          console.log(err);
          return next(err);
        }
        res.json(tz);
      });
    } else {
      next('User is not authorized to delete timezone.');
    }
  })
};

exports.del = function(req, res, next) {
  req.db.Timezone.findById(req.params.id, function(err, tz) {
    if (err) next(err);
    if (!tz) return res.sendStatus(404);
    if (req.user.admin || req.user._id.equals(tz.author.id)) {
      tz.remove();
      res.json(tz);
    } else {
      next('User is not authorized to delete timezone.');
    }
  })
};
