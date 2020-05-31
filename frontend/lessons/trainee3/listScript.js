$(document).ready(function () {
	var count=0;
	var des = [];
	$('#btn_add').click(function (){
		count++;	
		$('#table').append('<tr><td>'+count+'</td><td>'+$('#text_name').val()+'</td></tr>');	
	});
	$('#btn_remove').click(function (){
		if (count>0) {
			count--;
			$('#table tr:last').remove();
		}	
	});

	$.getJSON('table.json', function(data) {
	 	$.each(data, function(key, val) {
			val.forEach(function(item, i, arr) {
				count++;
				$('#table').append('<tr><td>'+count+'</td><td>'+item.name+'</td></tr>');
				des.push(item.description);
			});
		});
	});


	$('#table').click(function (e) {
		var target = e.target;
		
		var i = target.parentNode.rowIndex - 1;
		if (i>=0 && des.length>i) {
			$(".item-description").html('<h4>'+des[i]+'</h4>');
		} else {
		$(".item-description").html('');
		}
		$('td').css("background-color", "white");
		$(target).css("background-color", "pink");
	}
	);




});
