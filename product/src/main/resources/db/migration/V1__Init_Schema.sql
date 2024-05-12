create table product
(
    price decimal(15),
    id    bigint not null auto_increment,
    name  varchar(255),
    primary key (id)
);

create table product_item
(
    optionKey   enum ('COLOR', 'SIZE'),
    stock       integer not null,
    id          bigint  not null auto_increment,
    productId   bigint,
    optionValue varchar(255),
    primary key (id)
)
