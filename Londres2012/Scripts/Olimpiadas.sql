/*Script de creacion de la base de datos*/

CREATE DATABASE olimpiadas TEMPLATE template1; /* No importa si no esta en mayusculas*/

\c olimpiadas

/* Script de creacion del esquema*/ 

CREATE SCHEMA o;

CREATE DOMAIN o.Tipo_s_n char
check(VALUE in('S','N'));

CREATE DOMAIN o.Tipo_m_f char
check(VALUE in('M','F'));

CREATE DOMAIN o.Tipo_Medalla varchar(6)
check(VALUE in('Oro','Bronce','Plata'));

CREATE DOMAIN o.Tipo_Genero varchar(9)
check(VALUE in('Masculino','Femenino','Mixto'));

CREATE DOMAIN o.Clase_Tiempo varchar(8)
check(VALUE in('Verano','Invierno','Multiple'));

CREATE DOMAIN o.Tipo_Olimpiada varchar(8)
check(VALUE in('Verano','Invierno'));

CREATE DOMAIN o.Tipo_Deporte varchar(8)
check(VALUE in('Olimpico','Exhibicion'));

CREATE DOMAIN o.Numero_positivo integer  
check (VALUE > 0);
/* Script de creacion de las Tablas */

CREATE TABLE o.deporte(
	nombre_d varchar(30) not null,
	federacion varchar(80) not null,
	tipo o.Tipo_Deporte not null,
	tiempo o.Clase_Tiempo not null,
	primary key(nombre_d)
);
CREATE TABLE o.disciplina(
	nombre_dis varchar(60) not null,
	genero o.Tipo_Genero not null,
	nombre_d varchar(30) not null,
	foreign key(nombre_d) references o.deporte
	on delete cascade
	on update cascade,
	primary key(nombre_dis)
);
CREATE TABLE o.olimpiada(
	sede varchar(20) not null,
	ano_o o.Numero_positivo not null,
	tipo_o o.Tipo_olimpiada not null,
	llama varchar(50) not null,
	nombre varchar(80) not null,
	caract varchar(120) not null,
	primary key(ano_o)
);
CREATE TABLE o.atleta(
	nombre_a varchar(50) not null,
	pais_a varchar(20) not null,
	sexo o.Tipo_m_f not null,
	primary key(nombre_a,pais_a)
);
CREATE TABLE o.biografia(
	ci o.Numero_positivo not null,
	nivel_e varchar(50)  not null,
	lugar_residencia varchar(20) not null,
	nivel_socio_e varchar(50) not null,
	padre varchar(50) not null,
	madre varchar(50) not null,
	pseudonimo varchar(40), 
	motivo varchar(120),
	fecha_nacimiento date not null,
	lugar_nacimiento varchar(20) not null,
	nombre_a varchar(50) not null,
	pais_a varchar(20) not null,
	foreign key(nombre_a,pais_a) references o.atleta
	on delete cascade
	on update cascade,
	primary key(ci)
);
CREATE TABLE o.hecho_anecdotico(
	ano_de_o o.Numero_positivo not null,
	hecho varchar(120) not null,
	foreign key(ano_de_o) references o.olimpiada
	on delete cascade
	on update cascade,
	primary key(ano_de_o,hecho)
);
CREATE TABLE o.categoria_disciplina(
	nombre_disc varchar(60) not null,
	categoria varchar(30) not null,
	foreign key(nombre_disc) references o.disciplina
	on delete cascade
	on update cascade,
	primary key(nombre_disc,categoria)
);
CREATE TABLE o.hermano(
	nom_hermano varchar(50) not null,
	ci_atle o.Numero_positivo not null, 
	foreign key(ci_atle) references o.biografia
	on delete cascade
	on update cascade,
	primary key(ci_atle,nom_hermano)
);
CREATE TABLE o.torneo_ganado(
	torneo varchar(50) not null,
	ci_atlet o.Numero_positivo not null,
	fecha date not null, 
	foreign key(ci_atlet) references o.biografia
	on delete cascade
	on update cascade,
	primary key(ci_atlet,torneo,fecha)
);
CREATE TABLE o.practica(
	p_disciplina varchar(60) not null,
	ci_atleta o.Numero_positivo not null,
	fecha_i date not null, 
	foreign key(ci_atleta) references o.biografia
	on delete cascade
	on update cascade,
	primary key(ci_atleta,p_disciplina)
);
CREATE TABLE o.record_p(
	cedula_atleta o.Numero_positivo not null,
	record_atl varchar(120) not null,
	foreign key(cedula_atleta) references o.biografia
	on delete cascade
	on update cascade,
	primary key(cedula_atleta,record_atl)
);
CREATE TABLE o.preparativo( 
	evento varchar(100) not null,
	cedula_atlet o.Numero_positivo not null,
	an_olimpiada o.Numero_positivo not null, 
	foreign key(cedula_atlet) references o.biografia
	on delete cascade
	on update cascade,
	foreign key(an_olimpiada) references o.olimpiada
	on delete cascade
	on update cascade,
	primary key(evento,cedula_atlet,an_olimpiada)
);
CREATE TABLE o.entrenador(
	nom_entrenador varchar(50) not null,
	a_olimpiada o.Numero_positivo not null, 
	cedula_at o.Numero_positivo not null,
	evento_entrena varchar(100) not null,
	foreign key(evento_entrena,cedula_at,a_olimpiada) references o.preparativo
	on delete cascade
	on update cascade,
	primary key(nom_entrenador,a_olimpiada,cedula_at,evento_entrena)
);
CREATE TABLE o.es_conformada(
	ano_o o.Numero_positivo not null, 
	nombre_d varchar(30) not null,	
	foreign key(nombre_d) references o.deporte
	on delete cascade
	on update cascade,
	foreign key(ano_o) references o.olimpiada
	on delete cascade
	on update cascade,
	primary key(nombre_d,ano_o)
);
CREATE TABLE o.participa(
	nombre_a varchar(50) not null,
	pais_a varchar(20) not null,
	ano_o integer not null,
	discip varchar(60) not null,
	categoria varchar(30) not null,
	abanderado o.Tipo_s_n not null,
	descripcion varchar(120),
	delegacion varchar,
	foreign key(nombre_a,pais_a) references o.atleta
	on delete cascade
	on update cascade,
	foreign key(ano_o) references o.olimpiada
	on delete cascade
	on update cascade,
	foreign key(discip,categoria) references o.categoria_disciplina
	on delete cascade
	on update cascade,
	primary key(nombre_a,pais_a,ano_o,discip,categoria)
);
CREATE TABLE o.medalla_a(
	atleta_nombre varchar(50) not null,
	pais_d_at varchar(20) not null,
	ano_olimpiada o.Numero_positivo not null,
	medalla o.Tipo_Medalla not null,
	discip varchar(60) not null, 
	categoria varchar(30) not null,
	foreign key(atleta_nombre,pais_d_at,ano_olimpiada,discip,categoria) references o.participa
	on delete cascade
	on update cascade,
	primary key(atleta_nombre,pais_d_at,ano_olimpiada,discip,categoria,medalla)
);
CREATE TABLE o.record_a(
	atleta_nomb varchar(50) not null,
	ano_olim o.Numero_positivo not null, 
	pais_atleta varchar(20) not null,
	record varchar(120) not null,
	disciplin varchar(60) not null,
	categ varchar(30) not null,
	foreign key(atleta_nomb,pais_atleta,ano_olim,disciplin,categ) references o.participa
	on delete cascade
	on update cascade,
	primary key(atleta_nomb,ano_olim,pais_atleta,record,disciplin,categ)
);
CREATE TABLE o.ronda_de(
	atleta_nom varchar(50) not null,
	at_pais varchar(20) not null,
	ano_ol o.Numero_positivo not null, 
	ronda varchar(120) not null,
	discip varchar(60) not null,
	categoria varchar(30) not null,
	foreign key(atleta_nom,at_pais,ano_ol,discip,categoria) references o.participa
	on delete cascade
	on update cascade,
	primary key(atleta_nom,ano_ol,at_pais,discip,categoria,ronda)
);

CREATE ROLE servidor nosuperuser;

GRANT ALL on SCHEMA o to servidor;

GRANT ALL ON TABLE 	 o.olimpiada, o.atleta, o.deporte, o.disciplina, o.categoria_disciplina, o.medalla_a, o.biografia, o.participa	to servidor;

CREATE USER dueno NOSUPERUSER IN ROLE servidor PASSWORD 'dueno';



CREATE ROLE cliente NOSUPERUSER;

GRANT ALL on SCHEMA o to cliente;

GRANT SELECT ON TABLE  o.olimpiada, o.atleta, o.deporte, o.disciplina, o.categoria_disciplina, o.medalla_a, o.biografia, o.participa,
					   o.record_a, o.record_p to cliente;

CREATE USER usuario NOSUPERUSER IN ROLE cliente PASSWORD 'usuario';
				
				
