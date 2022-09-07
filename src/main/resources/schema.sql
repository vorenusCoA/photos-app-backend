CREATE TABLE IF NOT EXISTS ROLES (
  id BINARY(16) not null,
  type varchar(255) not null,
  primary key (id),
  unique (type)
);

CREATE TABLE IF NOT EXISTS USERS (
  id BINARY(16) not null,
  email varchar(255) not null,
  password varchar(255) not null,
  primary key (id),
  unique (email)
);

CREATE TABLE IF NOT EXISTS USERS_ROLES (
  user_id BINARY(16) not null,
  role_id BINARY(16) not null,
  primary key (user_id, role_id),
  foreign key (user_id) references USERS(id),
  foreign key (role_id) references ROLES(id)
);

CREATE TABLE IF NOT EXISTS PHOTOS (
  id BINARY(16) not null,
  user_id BINARY(16) not null,
  name varchar(255) not null,
  data blob,
  primary key (id),
  foreign key (user_id) references USERS(id)
);