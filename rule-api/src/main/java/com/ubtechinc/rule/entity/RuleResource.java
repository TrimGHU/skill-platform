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
@TableName("rule_resource")
public class RuleResource extends Model<RuleResource> {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Long id;
	@TableField("rule_id")
	private Long ruleId;
	@TableField("resource_ids")
	private String resourceIds;
	/**
	 * ALL全部返回，RANDOM随机返回一个
	 */
	@TableField("return_type")
	private String returnType;
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
		return "RuleResource{" + "id=" + id + ", ruleId=" + ruleId + ", resourceIds=" + resourceIds + ", returnType="
				+ returnType + ", createTime=" + createTime + ", updateTime=" + updateTime + "}";
	}
}
