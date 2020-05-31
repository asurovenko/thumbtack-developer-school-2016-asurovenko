USE netexam3;
/*	1	*/
DROP TABLE if exists books;
DROP TABLE if exists binding_link;
CREATE TABLE books (
	id INT(11) NOT NULL auto_increment,
	title varchar(50) NOT NULL,
	year INT(4) NOT NULL,
	pages INT(5) NOT NULL,
	PRIMARY KEY (id),
    KEY title(title),
    KEY year(year),
    KEY pages(pages)
);
/*	7	*/
alter Table books add publishing_house varchar(50) NOT NULL;
alter table books add binding int(2) not null default 1;
create table binding_link (
	id int(2) NOT NULL auto_increment,
    title_binding varchar(50) NOT NULL,
    PRIMARY KEY (id)
);
insert into binding_link values(null, 'Hard');
insert into binding_link values(null, 'Mild');
insert into binding_link values(null, 'Without binding');
alter table books add constraint  book_binding_fk FOREIGN KEY (binding) REFERENCES binding_link(id);
/*	2	*/
insert into books(title, year, pages, publishing_house, binding) VALUES('Sobache Serdce', 1925, 350, 'Omsk', 1);
insert into books(title, year, pages, publishing_house, binding) VALUES('Master i Magrarita', 1970, 400,'Moskow, 32', 2);
insert into books(title, year, pages, publishing_house, binding) VALUES('SKAZKI', 1900, 20, 'Luzino',1);
insert into books(title, year, pages, publishing_house, binding) VALUES('kabsw', 2000, 1, 'Omsk, 2003', 1);
insert into books(title, year, pages, publishing_house, binding) VALUES('wabsa', 2001, 1, 'Omsk, 1983', 1);
insert into books(title, year, pages, publishing_house, binding) VALUES('Kotelok patronov', 2007, 700, 'Omsk, 233t3', 2);
/*	3	*/
select * from books;
select * from books limit 3;
SELECT * from books WHERE id=2;
SELECT * from books WHERE title='SKAZKI';
SELECT * from books WHERE title like 'w%';
SELECT * from books WHERE title like '%w';
SELECT * from books where year>1950 and year<2003;
SELECT * from books where pages>100 and pages<500;
SELECT * from books where pages>100 and pages<500 and year>1950 and year<2003;
/*	4	*/
UPDATE books set pages = 5;
UPDATE books set pages = 5 where pages>100;
UPDATE books set pages = 5 where pages>100 and title like 'w%';
/*	5	*/
DELETE from books where pages>100;
DELETE from books where title like 'w%';
DELETE from books where id>2 and id<5;
DELETE from books where title = 'SKAZKI';
DELETE from books where title IN ('SKAZKI', 'wabsa', 'Sobache Serdce');
DELETE from books;
/*	8	*/
UPDATE books set binding=3;
/*	9	*/
UPDATE books set publishing_house='Omsk Sity' WHERE  id>2 and id<5;
/*	10 */
ALTER TABLE books CHANGE title bookname varchar(50) NOT NULL;
/*	11 */
ALTER TABLE books rename note;
/*	12 */
DELETE from note;





