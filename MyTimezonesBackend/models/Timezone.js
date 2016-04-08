var mongoose = require('mongoose');
var Schema = mongoose.Schema;

module.exports = make();

function make() {
  var schema = new Schema(
    {
      name: {
        type: String,
        required: true,
        trim: true
      },
      city: {
        type: String,
        required: true,
        trim: true
      },
      timeDiff: {
        type: String,
        required: true,
        trim: true
      },
      author: {
        type: {
          _id: { 
            type: Schema.Types.ObjectId, 
            ref: 'User',
            required: true
          },
          username: {
            type: String,
            required: true
          }
        },
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
  schema.index({name: "text", city: "text"});
  return schema;
}
