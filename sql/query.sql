create database dish_dash;

create table kitchen
(
    id      serial primary key,
    name    varchar not null,
    name_ru varchar not null,
    img     varchar not null
);

insert into kitchen (name, img, name_ru)
VALUES ('korean', 'korean', 'Корейская'),
       ('kazakh', 'kazakh', 'Казахская'),
       ('uzbek', 'uzbek', 'Узбекская'),
       ('usa', 'usa', 'Американская'),
       ('ukraine', 'ukraine', 'Украинская');

-- create table category (
--     id serial primary key,
--     id_kitchen int references kitchen (id),
--     name varchar not null
-- );
--
-- insert into category (id_kitchen, name) VALUES (1,'Блюда'),(1,'Салаты'),(1,'Десерты'),(1,'Напитки');

create table food
(
    id            serial primary key,
    id_kitchen    int references kitchen (id),
    name_category varchar not null,
    name          varchar not null,
    information   varchar not null,
    img           varchar not null,
    price         int     not null,
    popular       int
);

insert into food (id_kitchen, name_category, name, information, img, price, popular)
VALUES (1, 'Блюда', 'Шин рамён', 'Лапша рамён, острый бульон по южнокорейски, зелень, яйцо, морские водоросли',
        'kor1', 900, 35);
insert into food (id_kitchen, name_category, name, information, img, price, popular)
VALUES (1, 'Блюда', 'Мисо широ', 'с креветками, Классический бульон мисо, лосось, лапша удон, грибы',
        'kor2', 1200, 44);
insert into food (id_kitchen, name_category, name, information, img, price, popular)
VALUES (1, 'Салаты', 'Фунчоза', 'с телятиной, овощами и рисом, с курицей, овощами и рисом',
        'kor3', 1530, 74);
insert into food (id_kitchen, name_category, name, information, img, price, popular)
VALUES (1, 'Блюда', 'Теплое хе', 'из телятины с рисом',
        'kor4', 1850, 135);
insert into food (id_kitchen, name_category, name, information, img, price, popular)
VALUES (1, 'Десерты', 'Пампушки', 'сделано из пшеница',
        'kor5', 330, 358);
insert into food (id_kitchen, name_category, name, information, img, price, popular)
VALUES (1, 'Салаты', 'Накчи топпаб', 'осьминог с овощами в остром соусе',
        'kor6', 1990, 235);
insert into food (id_kitchen, name_category, name, information, img, price, popular)
VALUES (1, 'Блюда', 'Удон', 'с курицей, с телятиной, с морепродуктами',
        'kor7', 1480, 359);
insert into food (id_kitchen, name_category, name, information, img, price, popular)
VALUES (1, 'Блюда', 'Ким чикен', 'курица во фринтюре',
        'kor8', 1180, 12);
insert into food (id_kitchen, name_category, name, information, img, price, popular)
VALUES (1, 'Салаты', 'Ттокпокки', 'морковка с корицей',
        'kor9', 1590, 5);

create table users
(
    id       serial primary key,
    contact  varchar,
    phone    int8,
    email    varchar unique,
    country  varchar,
    city     varchar,
    password varchar
);

create table cart
(
    id          serial primary key,
    id_users    int references users (id),
    id_food     int references food (id),
    count       int not null,
    total_price int not null
);

create table order_user
(
    id      serial primary key,
    phone   int8    not null,
    status  varchar not null,
    address varchar not null
);

create table users_telegram
(
    id       serial primary key,
    id_users int  not null unique,
    status   bool not null default false
);