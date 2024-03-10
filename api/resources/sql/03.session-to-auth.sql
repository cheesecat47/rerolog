use rerolog;

alter table Session
    change session_id refresh_token varchar(512) null;

alter table Session
    drop foreign key Session_id_fk;

rename table Session to Auth;

alter table Auth
    add constraint Auth_id_fk
        foreign key (id) references User (id);

