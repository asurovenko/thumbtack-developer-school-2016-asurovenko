var uniqueId = require('../utils/unique-id');

var TodoModel = function (data) {
  this.text = data.text || '';
  this.status = data.status || 0;
  this.hot = data.hot || 0;
  this.uniqueId = uniqueId('model-');
};

TodoModel.prototype.setStatus = function (stat) {
  this.status = stat;
};

TodoModel.prototype.getUniqueId = function () {
  return this.uniqueId;
};

module.exports = TodoModel;