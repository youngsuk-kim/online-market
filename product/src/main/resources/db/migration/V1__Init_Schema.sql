create table product
(
    price decimal(15),
    id    bigint not null auto_increment,
    name  varchar(255),
    primary key (id)
);

create table product_item
(
    option_key   enum ('COLOR', 'SIZE'),
    stock       integer not null,
    id          bigint  not null auto_increment,
    product_id   bigint,
    option_value varchar(255),
    primary key (id)
)
