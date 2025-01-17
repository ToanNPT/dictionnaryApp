CREATE TABLE "word_descr"
(
    "id"          INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    "word_id"     int,
    "word_type"   varchar(255),
    "description" text,
    "sec"         int,
    "is_hidden"   bool,
    "reg_time"    timestamp default now(),
    "reg_user"    int,
    "update_time" timestamp default now(),
    "update_user" int
);
