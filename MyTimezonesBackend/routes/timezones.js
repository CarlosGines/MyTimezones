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
        _id: req.user._id,
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
    res.json({tzs: tzs});
  });
};

exports.checkTz = function(req, res, next) {
  req.db.Timezone.findById(req.params.id, function(err, tz) {
    if (err) return next(err);
    if (!tz) return res.sendStatus(404);
    if (!req.user.admin && !req.user._id.equals(tz.author._id)) {
      return res.status(401)
        .json('User not authorized to access/modify this timezone');
    }
    req.tz = tz;
    next();
  });
}

exports.getTz = function(req, res, next) {
  res.json(req.tz);
};

exports.updateTz = function(req, res, next) {
  req.tz.name = req.body.name;
  req.tz.city = req.body.city;
  req.tz.timeDiff = req.body.timeDiff;
  req.tz.updated = Date.now();
  req.tz.save(function(err, tz) {
    if (err) {
      console.log(err);
      return next(err);
    }
    res.json(tz);
  });
};

exports.del = function(req, res, next) {
  req.tz.remove();
  res.json(req.tz);
};
