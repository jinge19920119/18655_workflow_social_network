/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Workflow {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String author;
	private int authorId;
	private String name;
	private String purpose;
	private String input;
	private String output;
	private int popularity;
	@Column(columnDefinition="MEDIUMBLOB")
	private byte[] image;
	private String contributors;
	private String linksInstructions;
	private Date createTime;
	private String versionNo;
	private String dataset;
	private String otherWorkflows;
	@Column(columnDefinition="TINYINT DEFAULT 0")
	private int isQuestion;
	@Column(columnDefinition="INTEGER DEFAULT 0")
	private int answerId;

	//	private long rootWorkflowId;
	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
	@JoinTable(name = "WorkflowAndUser", joinColumns = { @JoinColumn(name ="workflowId", referencedColumnName = "id")}, inverseJoinColumns = { @JoinColumn(name = "userId", referencedColumnName = "id") })
	private List<User> userSet;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
	@JoinTable(name = "WorkflowAndClimateService", joinColumns = { @JoinColumn(name ="workflowId", referencedColumnName = "id")}, inverseJoinColumns = { @JoinColumn(name = "climateServiceId", referencedColumnName = "id") })
	private List<ClimateService> climateServiceSet;
	
	public Workflow() {
	}
	
	public Workflow(String author, int authorId, String name, String purpose, String input, String output, byte[] image, String contributors, String linksInstructions, Date createTime,
			String versionNo, String dataset, String otherWorkflows, List<User> userSet,
			List<ClimateService> climateServiceSet, int isQuestion, int answerId) {
		super();
		this.author = author;
		this.authorId = authorId;
		this.name = name;
		this.purpose = purpose;
		this.input = input;
		this.output = output;
		this.image = image;
		this.contributors = contributors;
		this.linksInstructions = linksInstructions;
		this.createTime = createTime;
		this.versionNo = versionNo;
//		this.rootWorkflowId = rootWorkflowId;
		this.dataset = dataset;
		this.otherWorkflows = otherWorkflows;
		this.userSet = userSet;
		this.climateServiceSet = climateServiceSet;
		this.isQuestion = isQuestion;
		this.setAnswerId(answerId);
		this.popularity= 1;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getDataset() {
		return dataset;
	}

	public void setDataset(String dataset) {
		this.dataset = dataset;
	}

	public void setPopularity(int popularity){
		this.popularity= popularity;
	}
	
	public int getPopularity(){
		return this.popularity;
	}
	
	public void addPopularity(){
		this.popularity+= 1;
	}
	
	public String getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

//	public long getRootWorkflowId() {
//		return rootWorkflowId;
//	}
//
//	public void setRootWorkflowId(long rootWorkflowId) {
//		this.rootWorkflowId = rootWorkflowId;
//	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getContributors() {
		return contributors;
	}

	public void setContributors(String contributors) {
		this.contributors = contributors;
	}

	public String getLinksInstructions() {
		return linksInstructions;
	}

	public void setLinksInstructions(String linksInstructions) {
		this.linksInstructions = linksInstructions;
	}

	public List<User> getUserSet() {
		return userSet;
	}

	public void setUserSet(List<User> userSet) {
		this.userSet = userSet;
	}

	public List<ClimateService> getClimateServiceSet() {
		return climateServiceSet;
	}

	public void setClimateServiceSet(List<ClimateService> climateServiceSet) {
		this.climateServiceSet = climateServiceSet;
	}
	
	public String getOtherWorkflows() {
		return otherWorkflows;
	}

	public void setOtherWorkflows(String otherWorkflows) {
		this.otherWorkflows = otherWorkflows;
	}

	public long getId() {
		return id;
	}
	
	public int getAnswerId() {
		return answerId;
	}

	public void setAnswerId(int answerId) {
		this.answerId = answerId;
	}
	
	public int getIsQuestion() {
		return isQuestion;
	}

	public void setIsQuestion(int isQuestion) {
		this.isQuestion = isQuestion;
	}

	@Override
	public String toString() {
		return "Workflow [id=" + id + ", name=" + name + ", purpose=" + purpose
				+ ", createTime=" + createTime + ", versionNo=" + versionNo
				+ ", userSet=" + userSet //+ ", rootWorkflowId=" + rootWorkflowId 
				+ ", climateServiceSet=" + climateServiceSet +", popularity="+ popularity + "]";
	}
	
}