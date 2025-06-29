-- 재고 테이블 생성
create table inventory
(
    id               bigint  not null auto_increment,
    product_item_id  bigint  not null,
    stock            integer not null,
    primary key (id)
);

-- 기존 product_item 테이블의 재고 데이터를 inventory 테이블로 이동
insert into inventory (product_item_id, stock)
select id, stock from product_item;

-- product_item 테이블에서 stock 컬럼 제거 (선택사항)
-- alter table product_item drop column stock;
