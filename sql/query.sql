create database dish_dash;

create table kitchen(
    id serial primary key,
    name varchar not null,
    img varchar not null
);

insert into kitchen (name, img) VALUES ('korean','korean'),('kazakh','kazakh'),('uzbek','uzbek'),('usa','usa'),('ukraine','ukraine');

-- create table category (
--     id serial primary key,
--     id_kitchen int references kitchen (id),
--     name varchar not null
-- );
--
-- insert into category (id_kitchen, name) VALUES (1,'Блюда'),(1,'Салаты'),(1,'Десерты'),(1,'Напитки');

create table food(
    id serial primary key,
    id_kitchen int references kitchen (id),
    name_category varchar not null,
    name varchar not null,
    information varchar not null,
    img varchar not null,
    price int not null,
    popular int
);

insert into food (id_kitchen,name_category, name, information, img, price,popular) VALUES (1,'Блюда','Шин рамён','Лапша рамён, острый бульон по южнокорейски, зелень, яйцо, морские водоросли',
                                                                      'kor1',900,35);
insert into food (id_kitchen,name_category, name, information, img, price,popular) VALUES (1,'Блюда','Мисо широ','с креветками, Классический бульон мисо, лосось, лапша удон, грибы',
                                                                      'kor2',1200,44);
insert into food (id_kitchen,name_category, name, information, img, price,popular) VALUES (1,'Салаты','Фунчоза','с телятиной, овощами и рисом, с курицей, овощами и рисом',
                                                                      'kor3',1530,74);
insert into food (id_kitchen,name_category, name, information, img, price,popular) VALUES (1,'Блюда','Теплое хе','из телятины с рисом',
                                                                      'kor4',1850,135);
insert into food (id_kitchen,name_category, name, information, img, price,popular) VALUES (1,'Десерты','Пампушки','сделано из пшеница',
                                                                      'kor5',330,358);
insert into food (id_kitchen,name_category, name, information, img, price,popular) VALUES (1,'Салаты','Накчи топпаб','осьминог с овощами в остром соусе',
                                                                      'kor6',1990,235);
insert into food (id_kitchen,name_category, name, information, img, price,popular) VALUES (1,'Блюда','Удон','с курицей, с телятиной, с морепродуктами',
                                                                      'kor7',1480,359);
insert into food (id_kitchen,name_category, name, information, img, price,popular) VALUES (1,'Блюда','Ким чикен','курица во фринтюре',
                                                                      'kor8',1180,12);
insert into food (id_kitchen,name_category, name, information, img, price,popular) VALUES (1,'Салаты','Ттокпокки','морковка с корицей',
                                                                      'kor9',1590,5);

create table users (
  id serial primary key,
  contact varchar not null,
  phone int not null,
  email varchar not null,
  country varchar not null,
  city varchar not null,
  password varchar not null
);