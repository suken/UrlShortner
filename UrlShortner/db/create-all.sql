
create table URL_DETAILS (
    id number not null AUTO_INCREMENT,
    longUrl varchar2(512) not null,
    created timestamp not null,

    PRIMARY KEY (id)
);