CREATE TABLE USER(
    ID      BIGINT       NOT NULL,
    NOME    	VARCHAR(60)      NOT NULL,
    CPF      VARCHAR(11)    NOT NULL,
    DATA_NASCIMENTO		TIMESTAMP,
    PRIMARY KEY(ID)
);


CREATE SEQUENCE sequence_user
	START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    NO CYCLE
    NO CACHE
;

insert into USER values (sequence_user.NEXTVAL, 'João da Silva', '38545517842', '2018-12-28 02:00:21');
insert into USER values (sequence_user.NEXTVAL, 'Fernando Cardoso', '12354469875', '2018-12-28 02:00:21');
insert into USER values (sequence_user.NEXTVAL, 'Gustavo Santos', '54213325687', '2018-12-28 02:00:21');
insert into USER values (sequence_user.NEXTVAL, 'Guilherme Bezerra', '53254463100', '2018-12-28 02:00:21');
insert into USER values (sequence_user.NEXTVAL, 'Antonio Silveira', '45233365248', '2018-12-28 02:00:21');
