package com.SCM.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Notification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String status;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	private int w_id;

	private int p_id;

	private int dc_id;

	private int so_id;

	private int ret_id;

	private int dist_id;

	private int aseid;

	private int asmid;

	private int rsmid;

	private int nsmid;

	private String distributor_read;

	private String retailer_read;

	private String nsm_read;

	private String rsm_read;

	private String asm_read;

	private String ase_read;

	private String admin_read;

	public Notification(int id, String status, Date createdAt, int w_id, int p_id, int dc_id, int so_id, int ret_id,
			int dist_id, int aseid, int asmid, int rsmid, int nsmid, String distributor_read, String retailer_read,
			String nsm_read, String rsm_read, String asm_read, String ase_read, String admin_read) {
		super();
		this.id = id;
		this.status = status;
		this.createdAt = createdAt;
		this.w_id = w_id;
		this.p_id = p_id;
		this.dc_id = dc_id;
		this.so_id = so_id;
		this.ret_id = ret_id;
		this.dist_id = dist_id;
		this.aseid = aseid;
		this.asmid = asmid;
		this.rsmid = rsmid;
		this.nsmid = nsmid;
		this.distributor_read = distributor_read;
		this.retailer_read = retailer_read;
		this.nsm_read = nsm_read;
		this.rsm_read = rsm_read;
		this.asm_read = asm_read;
		this.ase_read = ase_read;
		this.admin_read = admin_read;
	}

	public Notification() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public int getW_id() {
		return w_id;
	}

	public void setW_id(int w_id) {
		this.w_id = w_id;
	}

	public int getP_id() {
		return p_id;
	}

	public void setP_id(int p_id) {
		this.p_id = p_id;
	}

	public int getDc_id() {
		return dc_id;
	}

	public void setDc_id(int dc_id) {
		this.dc_id = dc_id;
	}

	public int getSo_id() {
		return so_id;
	}

	public void setSo_id(int so_id) {
		this.so_id = so_id;
	}

	public int getRet_id() {
		return ret_id;
	}

	public void setRet_id(int ret_id) {
		this.ret_id = ret_id;
	}

	public int getDist_id() {
		return dist_id;
	}

	public void setDist_id(int dist_id) {
		this.dist_id = dist_id;
	}

	public int getAseid() {
		return aseid;
	}

	public void setAseid(int aseid) {
		this.aseid = aseid;
	}

	public int getAsmid() {
		return asmid;
	}

	public void setAsmid(int asmid) {
		this.asmid = asmid;
	}

	public int getRsmid() {
		return rsmid;
	}

	public void setRsmid(int rsmid) {
		this.rsmid = rsmid;
	}

	public int getNsmid() {
		return nsmid;
	}

	public void setNsmid(int nsmid) {
		this.nsmid = nsmid;
	}

	public String getDistributor_read() {
		return distributor_read;
	}

	public void setDistributor_read(String distributor_read) {
		this.distributor_read = distributor_read;
	}

	public String getRetailer_read() {
		return retailer_read;
	}

	public void setRetailer_read(String retailer_read) {
		this.retailer_read = retailer_read;
	}

	public String getNsm_read() {
		return nsm_read;
	}

	public void setNsm_read(String nsm_read) {
		this.nsm_read = nsm_read;
	}

	public String getRsm_read() {
		return rsm_read;
	}

	public void setRsm_read(String rsm_read) {
		this.rsm_read = rsm_read;
	}

	public String getAsm_read() {
		return asm_read;
	}

	public void setAsm_read(String asm_read) {
		this.asm_read = asm_read;
	}

	public String getAse_read() {
		return ase_read;
	}

	public void setAse_read(String ase_read) {
		this.ase_read = ase_read;
	}

	public String getAdmin_read() {
		return admin_read;
	}

	public void setAdmin_read(String admin_read) {
		this.admin_read = admin_read;
	}

}
