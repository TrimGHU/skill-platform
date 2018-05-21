#################################
#DATE	: 2018-5-14
#UPDATE	: 技能逻辑规则和规则返回资源表
#################################
DROP TABLE rule;
CREATE TABLE rule( 
    id BIGINT(11) NOT NULL AUTO_INCREMENT, 
    owner_id BIGINT(11) NOT NULL, 
    main_rule_id BIGINT(11) COMMENT '多轮对话的首轮逻辑规则ID', 
    domain_name VARCHAR(100) NOT NULL, 
    intent_name VARCHAR(255) NOT NULL, 
    round INT(10) NOT NULL, 
    is_multiple TINYINT(1) NOT NULL DEFAULT 0 COMMENT '1为多轮，0为单轮', 
    create_time TIMESTAMP NULL, 
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, 
    PRIMARY KEY (id) 
) ENGINE=INNODB CHARSET=utf8 COLLATE=utf8_bin; 
    

DROP TABLE rule_resource;
CREATE TABLE rule_resource( 
    id BIGINT(11) NOT NULL AUTO_INCREMENT, 
    rule_id BIGINT(11) NOT NULL, 
    resource_ids VARCHAR(255) NOT NULL, 
    return_type VARCHAR(16) NOT NULL DEFAULT 'ALL' COMMENT 'ALL全部返回，RANDOM随机返回一个',
    create_time TIMESTAMP NULL, 
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, 
    PRIMARY KEY (id) 
) ENGINE=INNODB CHARSET=utf8 COLLATE=utf8_bin;