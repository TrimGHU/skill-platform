#################################
#DATE	: 2018-5-2
#UPDATE	: 资源服务的内容和资源类型表
#################################
DROP TABLE resource;
CREATE TABLE resource( 
    id BIGINT(11) NOT NULL AUTO_INCREMENT, 
    type_code VARCHAR(50) NOT NULL, 
    name VARCHAR(100) NOT NULL, 
    owner_id BIGINT(11) NOT NULL, 
    content VARCHAR(255) NOT NULL, 
    create_time TIMESTAMP NULL, 
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, 
    PRIMARY KEY (id) 
) ENGINE=INNODB CHARSET=utf8 COLLATE=utf8_bin;



DROP TABLE resource_type;
CREATE TABLE resource_type( 
    id INT(10) NOT NULL AUTO_INCREMENT, 
    type_code VARCHAR(50) NOT NULL, 
    type_desc VARCHAR(100) NOT NULL, 
    create_time TIMESTAMP NULL, 
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, 
    PRIMARY KEY (id) 
) ENGINE=INNODB CHARSET=utf8 COLLATE=utf8_bin;
INSERT INTO resource_type(type_code,type_desc) VALUES ('TEXT','文本资源');
INSERT INTO resource_type(type_code,type_desc) VALUES ('MUSIC','音乐资源');
INSERT INTO resource_type(type_code,type_desc) VALUES ('ACTION','动作文件资源');