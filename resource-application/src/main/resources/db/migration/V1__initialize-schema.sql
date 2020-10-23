create sequence hibernate_sequence;

create table resources
(
    id           uuid not null,
    checksum     varchar(255),
    content      BYTEA,
    content_type  varchar(64),
    created      timestamp,
    deleted_flag bool,
    description  varchar(1024),
    filename     varchar(1024),
    filepath     varchar(4096),
    filesize     bigint,
    primary key (id)
);
