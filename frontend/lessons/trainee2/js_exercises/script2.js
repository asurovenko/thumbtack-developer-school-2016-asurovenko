var sp = document.getElementById('spase_table');

var r = prompt('Кол-во столбцов:');
var c = prompt('Кол-во строк:');

var d = screen.width / r;

var t = document.createElement('table');
t.setAttribute("border", "1");

var tr;
var td;
for (var i = 0; i < +c; i++) {
    tr = document.createElement('tr');
    for (var j = 0; j < +r; j++) {
        td = document.createElement('td');
        td.setAttribute('width', d);
        td.setAttribute('height', '100');
        td.setAttribute('style', 'background-color: pink;');
        tr.appendChild(td);
    }
    t.appendChild(tr);
}
sp.appendChild(t);


