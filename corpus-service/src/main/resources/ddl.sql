#################################
#DATE	: 2018-4-2
#UPDATE	: 语料服务的领域，意图，关键字表
#################################
DROP TABLE domain;
CREATE TABLE domain (
  id          BIGINT(11) NOT NULL AUTO_INCREMENT, 
  name        VARCHAR(100) NOT NULL UNIQUE, 
  owner_id    BIGINT(11) NOT NULL, 
  create_time TIMESTAMP NULL, 
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, 
  PRIMARY KEY (id)
);

DROP TABLE intent;
CREATE TABLE intent (
  id          BIGINT(11) NOT NULL AUTO_INCREMENT, 
  name        VARCHAR(255) NOT NULL UNIQUE, 
  domain_id   BIGINT(11) NOT NULL, 
  create_time TIMESTAMP NULL, 
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, 
  PRIMARY KEY (id)
);

DROP TABLE corpus;
CREATE TABLE corpus (
  id          BIGINT(11) NOT NULL AUTO_INCREMENT, 
  content     VARCHAR(255) NOT NULL, 
  owner_id    BIGINT(11) NOT NULL, 
  domain_id   BIGINT(11) NOT NULL, 
  intent_id   BIGINT(11) NOT NULL, 
  slots       VARCHAR(255) NOT NULL, 
  create_time TIMESTAMP NULL, 
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, 
  PRIMARY KEY (id)
);
