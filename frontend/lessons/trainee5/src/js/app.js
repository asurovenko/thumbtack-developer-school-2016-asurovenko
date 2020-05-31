var $ = require('jquery');
var todoList = require('./todoList.js');
var todoControls = require('./todoControls.js');

var app = {
	init: function() {
		todoList.init();
        todoControls.init();
	}
};

$(function(){
	app.init();
})


