function BaseCalculator() {
	this.value1 = 0;
	this.value2 =  0;
	this.read =  function() {
		value1 = +prompt('Введите 1 значение', 0);
		value2 = +prompt('Введите 2 значение', 0);
	}
    
    this.calculate = function(str) {
        var s = str.split(' ');
        if (s[1]=='+') return +s[0]+(+s[2]);
        if (s[1]=='-') return +s[0]-(+s[2]);
        if (s[1]=='*') return +s[0]*(+s[2]);
        if (s[1]=='/') return +s[0]/(+s[2]);
    }
	
	
};

var Summator = {
	sum: function() {
		return value1+value2;
	}
};

var calc = new BaseCalculator;


calc.__proto__=Summator;
calc.read();
alert(calc.sum());







