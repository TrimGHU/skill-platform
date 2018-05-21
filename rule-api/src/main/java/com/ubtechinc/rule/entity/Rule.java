package com.ubtechinc.rule.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 
 * </p>
 *
 * @author hugui
 * @since 2018-05-14
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("rule")
public class Rule extends Model<Rule> {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Long id;
	@TableField("owner_id")
	private Long ownerId;
	/**
	 * 多轮对话的首轮逻辑规则ID
	 */
	@TableField("main_rule_id")
	private Long mainRuleId;
	@TableField("domain_name")
	private String domainName;
	@TableField("intent_name")
	private String intentName;
	@TableField("round")
	private Integer round;
	/**
	 * 1为多轮，0为单轮
	 */
	@TableField("is_multiple")
	private Integer isMultiple;
	@TableField("create_time")
	private Date createTime;
	@TableField("update_time")
	private Date updateTime;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Rule{" + "id=" + id + ", ownerId=" + ownerId + ", mainRuleId=" + mainRuleId + ", intentName="
				+ intentName + ", round=" + round + ", isMultiple=" + isMultiple + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + "}";
	}
}
