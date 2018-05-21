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
@TableName("corpus")
public class Corpus extends Model<Corpus> {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Long id;
	@TableField("content")
	private String content;
	@TableField("domain_id")
	private Long domainId;
	@TableField("intent_id")
	private Long intentId;
	@TableField("slots")
	private String slots;
	@TableField("owner_id")
	private Long ownerId;
	@TableField("create_time")
	private Date createTime;
	@TableField("update_time")
	private Date updateTime;

	public Corpus(String content, Long domainId, Long intentId, String slots, Long ownerId) {
		super();
		this.content = content;
		this.domainId = domainId;
		this.intentId = intentId;
		this.slots = slots;
		this.ownerId = ownerId;
		this.createTime = new Date();
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Corpus{" + "id=" + id + ", content=" + content + ", domainId=" + domainId + ", intentId=" + intentId
				+ ", slots=" + slots + ", createTime=" + createTime + ", updateTime=" + updateTime + "}";
	}

}
