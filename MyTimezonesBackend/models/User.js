var mongoose = require('mongoose');

module.exports = new mongoose.Schema(
  {
    username: {
      type: String,
      required: true,
      trim: true
    },
    admin: {
      type: Boolean,
      default: false,
    },
    password: {
      type: String,
      required: true
    },
    token: {
      type: String,
      required: true
    },
    created: {
      type: Date,
      default: Date.now
    },
    updated:  {
      type: Date,
      default: Date.now
    }
  },
  {
    versionKey: false
  }
);
