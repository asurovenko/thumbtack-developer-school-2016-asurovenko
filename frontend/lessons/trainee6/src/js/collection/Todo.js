var TodoModel = require('../model/Todo');

var TodoCollection = function (data) {
  this.data = [];
	
  if (data) {
    $.each(data, function (index, todoData) {
      this.add(todoData);
    }.bind(this));
  }
};

TodoCollection.prototype.add = function (todoData) {
  var todoModel;

  if (todoData instanceof TodoModel) {
    todoModel = todoData;
  } else {
    todoModel = new TodoModel(todoData);
  }

  this.data.push(todoModel);
  return this;
};

TodoCollection.prototype.remove = function (uniqueID) {
	if (this.data) {
		this.data = $.grep(this.data, function(model) {
			return model.uniqueId == uniqueID;
		}, true);
	}
	return this;
}

TodoCollection.prototype.checkedClick = function (uniqueID) {
  if (this.data) {
    $.each(this.data, function (index, todoData) {
      if (todoData.getUniqueId()==uniqueID) {
          todoData.setStatus(1);
      }
    }.bind(this));
  }

  return this;
};

TodoCollection.prototype.swip = function (uniqueID, p) {
  if (this.data) {
    $.each(this.data, function (index, todoData) {
      if (todoData.getUniqueId()==uniqueID) {
          if (p=='up' && index!=0) {
              this.swipeElement(index, index-1);
              return false; //exit each
          }
          if (p=='down' && index!=this.data.length-1) {
              this.swipeElement(index, index+1);
              return false; //exit each
          }
      }
    }.bind(this));
  }

  return this;
};

TodoCollection.prototype.swipeElement = function (pos1, pos2) {
    var tmp;
    tmp = this.data[pos1];
    this.data[pos1]=this.data[pos2];
    this.data[pos2]=tmp;
};


module.exports = TodoCollection;