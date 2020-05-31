var tplTodo = require('../tpl/todo.hbs');

var View = function (data) {
  this.$el = $('<div>');

  this.collection = data.collection;
  this.template = data.template ? data.template : tplTodo;

  this.$el.on('click', '.button-add', this.addItem.bind(this));
  this.$el.on('click', '.button-remove', this.removeItem.bind(this));
  this.$el.on('click', '.checkbox', this.checkClick.bind(this));
  this.$el.on('click', '.button-up', this.up.bind(this));
  this.$el.on('click', '.button-down', this.down.bind(this));
};

View.prototype.addItem = function () {
  var text = this.$el.find('.text').val();

  this.collection.add({
    text: text
  });

  this.render();
};

View.prototype.render = function () {
  var html = this.template({
    collection: this.collection
  });

  this.$el.html(html);

  return this;
};

View.prototype.removeItem = function (event) {
	var $removeButton = $(event.currentTarget);
	var uniqueID = $removeButton.parents('tr').data('id');
	
	this.collection.remove(uniqueID);
	this.render();
}

View.prototype.checkClick = function (event) {
	var $removeButton = $(event.currentTarget);
	var uniqueID = $removeButton.parents('tr').data('id');
    
	this.collection.checkedClick(uniqueID);
	this.render();
}

View.prototype.up = function (event) {
	var $removeButton = $(event.currentTarget);
	var uniqueID = $removeButton.parents('tr').data('id');
    
	this.collection.swip(uniqueID, 'up');
	this.render();
}

View.prototype.down = function (event) {
	var $removeButton = $(event.currentTarget);
	var uniqueID = $removeButton.parents('tr').data('id');
    
	this.collection.swip(uniqueID, 'down');
	this.render();
}

module.exports = View;
