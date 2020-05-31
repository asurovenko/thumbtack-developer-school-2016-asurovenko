var $ = require('jquery');

var todoList = {
	init: function () {
        $('.todo-list').append('<table class="table"><tr><th>№</th><th>Описание</th><th>Выполнено</th></tr></table>');
        $.getJSON('table.json', function(data) {
	 	$.each(data, function(key, val) {
			val.forEach(function(item, i, arr) {
                if (item.checked=='true') {
				$('.todo-list table').append('<tr style="background-color: gray;"><td>'+(i+1)+'</td><td>'+item.des+'</td><td><input class="disabl" disabled="" type="checkbox" checked></td></tr>');
                } else {
                    $('.todo-list table').append('<tr><td>'+(i+1)+'</td><td>'+item.des+'</td><td><input class="check" type="checkbox" ></td></tr>');
                }
			});
		});
	});


        
	}
};

module.exports = todoList;
