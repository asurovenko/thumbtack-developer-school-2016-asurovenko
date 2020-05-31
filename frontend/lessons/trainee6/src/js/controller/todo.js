var TodoModel = require('../model/Todo');
var TodoCollection = require('../collection/Todo');
var TodoView = require('../view/Todo');

module.exports = function () {

  var model = new TodoModel({
    status: 1,
    text: '1223'
  });

  var collection = new TodoCollection([
    {
      status: 0,
      text: 'The second todo item'
    },
	{
		status: 1,
		text: 'The third todo item'
	},
    model
  ]);

  var todoView = new TodoView({
    collection: collection
  });

  $('.todo-view').append(todoView.render().$el);
};
