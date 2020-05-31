var s = prompt('Введите числа через пробел');

var array_s = s.split(' ');

var max=array_s[0];
var min=max;

var min = array_s[0];
var max = min;
var summ=0;
for (var i = 1; i < array_s.length; ++i) {
     	 if (+array_s[i] > +max) max = +array_s[i];
      	if (+array_s[i] < +min) min = +array_s[i];
	summ+=+array_s[i];
}
console.log(min);
console.log(max);
alert('Сумма: ' + summ);
