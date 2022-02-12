--テーブルがあった場合は事前に削除
DROP TABLE IF EXISTS administrators CASCADE;
DROP TABLE IF EXISTS employees CASCADE;

-- 管理者情報テーブル
create table administrators(
  id serial primary key,
  name text not null,
  mail_address text not null unique,
  password text not null
);

-- 従業員情報テーブル
create table employees (
  id integer primary key,
  name text not null,
  image text not null,
  gender text not null,
  hire_date timestamp not null,
  mail_address text not null unique,
  zip_code text not null,
  address text not null,
  telephone text not null,
  salary integer not null,
  characteristics text not null,
  dependents_count integer not null default 0
);
