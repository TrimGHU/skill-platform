package com.ubtechinc.corpus.entity;

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
 * @since 2018-04-02
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("domain")
public class Domain extends Model<Domain> {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Long id;
	@TableField("name")
	private String name;
	@TableField("owner_id")
	private Long ownerId;
	@TableField("create_time")
	private Date createTime;
	@TableField("update_time")
	private Date updateTime;

	public Domain(String name, Long ownerId) {
		super();
		this.name = name;
		this.ownerId = ownerId;
		this.createTime = new Date();
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Domain{" + "id=" + id + ", name=" + name + ", ownerId=" + ownerId + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + "}";
	}
}
