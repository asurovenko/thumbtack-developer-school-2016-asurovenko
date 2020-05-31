var $ = require('jquery');

module.exports = {
	init: function() {
		this.$addButton = $('<button>Add</button>');
		this.$removeButton = $('<button>Remove</button>');
		
		$('.todo-controls')
			.html(this.$addButton)
			.append(this.$removeButton);
			
		this.initHandlers();
	},
	
	initHandlers: function() {
		this.$addButton.on('click', this.addButtonClicked);
		this.$removeButton.on('click', this.removeButtonClicked);
        $('.table').on('click', '.check', { }, this.checkClick);
	},
	
	addButtonClicked: function() {
		var tableRows = $('.todo-list tr').length;
		var $row = $('<tr><td>'+tableRows+'</td><td>DESCRIPTION</td><td><input class="check" type="checkbox" ></td></tr>');
		
		$('.todo-list table').append($row);
	},
	
	removeButtonClicked: function() {
		$('.todo-list .check:last').parent().parent().remove();
	},
    
    checkClick: function( event ) {
		$( ".check" ).each(function ( i ) { 
            if ( this == event.target ) {
                $(event.target).prop( "disabled", true ).removeClass('check');
                
                $(event.target.parentElement.parentElement)
                    .css("background-color", "gray" ); 
            }
	    });
	}
};